/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user;

import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.mockejb.MockContainer;
import org.mockejb.SessionBeanDescriptor;
import org.mockejb.jndi.MockContextFactory;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.search.builder.filter.AndFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.security.authorization.AuthorizationPersistence;
import com.topcoder.security.authorization.SecurityRole;
import com.topcoder.security.authorization.persistence.SQLAuthorizationPersistence;
import com.topcoder.timetracker.audit.AuditManager;
import com.topcoder.timetracker.contact.AddressManager;
import com.topcoder.timetracker.contact.ContactManager;
import com.topcoder.timetracker.user.db.DbUserDAO;
import com.topcoder.timetracker.user.j2ee.MockUserTransaction;
import com.topcoder.timetracker.user.j2ee.UserManagerDelegate;
import com.topcoder.timetracker.user.j2ee.UserManagerLocal;
import com.topcoder.timetracker.user.j2ee.UserManagerLocalHome;
import com.topcoder.timetracker.user.j2ee.UserManagerSessionBean;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * The unit test class is used for component demonstration.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class DemoTests extends TestCase {
    /**
     * <p>
     * The AuthorizationPersistence instance for testing.
     * </p>
     */
    private AuthorizationPersistence authPersistence;

    /**
     * <p>
     * Setup test environment.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        TestHelper.loadXMLConfig(TestHelper.CONFIG_FILE);
        TestHelper.setUpDataBase();

        authPersistence = new SQLAuthorizationPersistence("com.topcoder.timetracker.application.authorization");
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    protected void tearDown() throws Exception {
        TestHelper.tearDownDataBase();
        TestHelper.clearConfig();

        authPersistence = null;
    }

    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(DemoTests.class);
    }

    /**
     * <p>
     * This test case demonstrates the usage of when the <code>UserManager</code> is used to access
     * directly.
     * </p>
     *
     * <p>
     * The CRUD, search, authorization operations are shown.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDemo1() throws Exception {
        DBConnectionFactory dbFactory = new DBConnectionFactoryImpl(TestHelper.DB_FACTORY_NAMESPACE);
        AuditManager auditManager = new MockAuditManager();
        ContactManager contactManager = new MockContactManager();
        AddressManager addressManager = new MockAddressManager();
        UserDAO dao = new DbUserDAO(dbFactory, "tt_user", "com.topcoder.timetracker.user.User",
            "com.topcoder.search.builder.database.DatabaseSearchStrategy", auditManager, contactManager,
            authPersistence, addressManager, true);

        // Create a UserManager using the DB DAOs, AuthPersistence and User Authenticator.
        UserManager userManager = new UserManagerImpl(dao, authPersistence, "Default_TT_UserAuthenticator");

        demoUserManager(userManager);
    }

    /**
     * <p>
     * This test case demonstrates the usage of when the <code>UserManager</code> to access is
     * inside an EJB container.
     * </p>
     *
     * <p>
     * The CRUD, search, authorization operations are shown.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDemo2() throws Exception {
        /* We need to set MockContextFactory as our JNDI provider.
         * This method sets the necessary system properties.
         */
        MockContextFactory.setAsInitial();

        // create the initial context that will be used for binding EJBs
        Context context = new InitialContext();

        // Create an instance of the MockContainer
        MockContainer mockContainer = new MockContainer(context);

        // we use MockTransaction outside of the app server
        MockUserTransaction mockTransaction = new MockUserTransaction();
        context.rebind("javax.transaction.UserTransaction", mockTransaction);

        /* Create deployment descriptor of our sample bean.
         * MockEjb uses it instead of XML deployment descriptors
         */
        SessionBeanDescriptor sampleServiceDescriptor = new SessionBeanDescriptor("UserManagerLocalHome",
            UserManagerLocalHome.class, UserManagerLocal.class, new UserManagerSessionBean());
        // Deploy operation creates Home and binds it to JNDI
        mockContainer.deploy(sampleServiceDescriptor);

        // 4.3.5 Creating a Delegate
        UserManager userManagerDelegate = new UserManagerDelegate(
            "com.topcoder.timetracker.user.j2ee.UserManagerDelegate");

        demoUserManager(userManagerDelegate);
    }

    /**
     * <p>
     * This method demos the CRUD, search, authorization operations provided by a <code>UserManager</code>
     * instance.
     * </p>
     *
     * @param userManager a <code>UserManager</code> instance to perform the actions
     *
     * @throws Exception to JUnit
     */
    private void demoUserManager(UserManager userManager) throws Exception {
        // 4.3.1 Creating a new User
        User newUser = new User();
        newUser.setUsername("TCSDESIGNER");
        newUser.setPassword("5yks809");
        newUser.setCompanyId(1);
        newUser.setContact(TestHelper.createTestingContact());
        newUser.setAddress(TestHelper.createTestingAddress());
        newUser.setStatus(Status.ACTIVE);
        newUser.setCreationUser("TCSADMIN");
        newUser.setModificationUser("TCSADMIN");

        // Register the user with the manager, with auditing.
        userManager.createUser(newUser, true);

        // 4.3.2 Changing the User Details

        // Retrieve a user from the manager
        User deactivatingUser = userManager.getUser(1);

        // Update the user's details.
        deactivatingUser.setStatus(Status.INACTIVE);
        deactivatingUser.setModificationUser("AUTOMATED_EXPIRY_TOOL");

        // Update the user in the manager
        userManager.updateUser(deactivatingUser, true);

        // 4.3.3 Search for a group of active users belonging to ACME company and delete them.
        // The delete operation should be atomic.

        // Define the search criteria.
        UserFilterFactory filterFactory = userManager.getUserFilterFactory();
        List criteria = new ArrayList();
        criteria.add(filterFactory.createCompanyIdFilter(1));
        criteria.add(filterFactory.createStatusFilter(Status.INACTIVE));

        // Create a search filter that aggregates the criteria.
        Filter searchFilter = new AndFilter(criteria);

        // Perform the actual search.
        User[] matchingUsers = userManager.searchUsers(searchFilter);
        long[] matchingUserIds = new long[matchingUsers.length];
        for (int i = 0; i < matchingUserIds.length; i++) {
            matchingUserIds[i] = matchingUsers[i].getId();
        }

        // Delete the users using atomic mode; auditing is performed.
        userManager.removeUsers(matchingUserIds, true);

        // 4.3.3 Manipulating User Authorization Roles

        // Make the new User into a SuperUser by giving it the SuperUser role;
        // We assume AuthorizationPersistence has been initialized.

        SecurityRole role = (SecurityRole) authPersistence.searchRoles("Employee").iterator().next();
        userManager.addRoleToUser(newUser, role);

        // Remove all the roles from the deactivatingUser
        userManager.clearRolesFromUser(newUser);

        // 4.3.4 User Authentication

        // Authenticate a username/password combination.
        userManager.authenticateUser("TCSDESIGNER", "TCSPASSWORD");
    }
}
