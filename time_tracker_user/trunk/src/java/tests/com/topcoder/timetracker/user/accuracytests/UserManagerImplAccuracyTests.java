/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user.accuracytests;

import java.util.Arrays;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.security.authorization.AuthorizationPersistence;
import com.topcoder.security.authorization.SecurityRole;
import com.topcoder.security.authorization.persistence.GeneralSecurityRole;
import com.topcoder.security.authorization.persistence.SQLAuthorizationPersistence;
import com.topcoder.timetracker.audit.AuditManager;
import com.topcoder.timetracker.contact.AddressManager;
import com.topcoder.timetracker.contact.ContactManager;
import com.topcoder.timetracker.user.DataAccessException;
import com.topcoder.timetracker.user.Status;
import com.topcoder.timetracker.user.StringMatchType;
import com.topcoder.timetracker.user.User;
import com.topcoder.timetracker.user.UserDAO;
import com.topcoder.timetracker.user.UserFilterFactory;
import com.topcoder.timetracker.user.UserManagerImpl;
import com.topcoder.timetracker.user.db.DbUserDAO;
import com.topcoder.timetracker.user.filterfactory.MappedUserFilterFactory;

/**
 * <p>
 * Accuracy Unit test cases for UserManagerImpl.
 * </p>
 *
 * @author victorsam
 * @version 3.2
 * @author Chenhong
 * @version 3.2.1
 */
public class UserManagerImplAccuracyTests extends TestCase {
    /**
     * <p>
     * UserManagerImpl instance for testing.
     * </p>
     */
    private UserManagerImpl instance;

    /**
     * <p>
     * AuthorizationPersistence instance for testing.
     * </p>
     */
    private AuthorizationPersistence authPersistence;

    /**
     * <p>
     * Setup test environment.
     * </p>
     * @throws Exception to JUnit
     *
     */
    protected void setUp() throws Exception {
        AccuracyTestHelper.clearConfig();
        AccuracyTestHelper.loadXMLConfig(AccuracyTestHelper.CONFIG_FILE);
        AccuracyTestHelper.loadXMLConfig(AccuracyTestHelper.CONFIG_FILE_ADD);
        AccuracyTestHelper.setUpDataBase();

        DBConnectionFactory dbFactory = new DBConnectionFactoryImpl(AccuracyTestHelper.DB_FACTORY_NAMESPACE);
        AuditManager auditManager = new MockAuditManager();
        ContactManager contactManager = new MockContactManager();
        authPersistence = new SQLAuthorizationPersistence("com.topcoder.timetracker.application.authorization");
        AddressManager addressManager = new MockAddressManager();
        UserDAO userDao = new DbUserDAO(dbFactory, "tt_user", "com.topcoder.timetracker.user.User",
            "com.topcoder.search.builder.database.DatabaseSearchStrategy", auditManager, contactManager,
            authPersistence, addressManager, AccuracyTestHelper.getUserStatusDAO(), AccuracyTestHelper.getUserTypeDAO(), true);

        instance = new UserManagerImpl(userDao, authPersistence, "Default_TT_UserAuthenticator");
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     * @throws Exception to JUnit
     *
     */
    protected void tearDown() throws Exception {
        instance = null;
        authPersistence = null;

        AccuracyTestHelper.tearDownDataBase();
        AccuracyTestHelper.clearConfig();
    }

    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(UserManagerImplAccuracyTests.class);
    }

    /**
     * <p>
     * Tests ctor UserManagerImpl#UserManagerImpl(UserDAO,AuthorizationPersistence,String) for accuracy.
     * </p>
     */
    public void testCtor1() {
        assertNotNull("Failed to create UserManagerImpl instance.", instance);
    }

    /**
     * <p>
     * Tests UserManagerImpl#getUserFilterFactory() for accuracy.
     * </p>
     */
    public void testGetUserFilterFactory() {
        UserFilterFactory factory = instance.getUserFilterFactory();
        assertEquals("Failed to get user filter factory.", MappedUserFilterFactory.class, factory.getClass());
    }

    /**
     * <p>
     * Tests UserManagerImpl#searchUsers(Filter) for accuracy.
     * </p>
     * @throws DataAccessException to JUnit
     */
    public void testSearchUsers() throws DataAccessException {
        Filter filter = instance.getUserFilterFactory().createUsernameFilter(StringMatchType.ENDS_WITH, "admin");
        User[] users = instance.searchUsers(filter);

        assertEquals("Expected one user should be in the database.", 1, users.length);
        assertEquals("The user names are not equals.", "admin", users[0].getUsername());
        assertEquals("The passwords are not equals.", "tc_super", users[0].getPassword());
        assertEquals("The statuses are not equals.", 1, users[0].getStatus().getId());
        assertEquals("The addresses are not equals.", 1, users[0].getAddress().getId());
        assertEquals("The company ids are not equals.", 1, users[0].getCompanyId());
        assertEquals("The contacts are not equals.", 1, users[0].getContact().getId());
    }

    /**
     * <p>
     * Tests UserManagerImpl#addUsers(User[],boolean) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddUsers() throws Exception {
        User user = AccuracyTestHelper.createUser();
        instance.addUsers(new User[] {user}, true);

        User actualUser = instance.getUsers(new long[] {user.getId()})[0];
        AccuracyTestHelper.assertUserEquals(user, actualUser);
    }

    /**
     * <p>
     * Tests UserManagerImpl#updateUsers(User[],boolean) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateUsers() throws Exception {
        User user = AccuracyTestHelper.createUser();
        instance.addUsers(new User[] {user}, true);
        user.setUsername("new");
        user.setStatus(Status.ACTIVE);
        instance.updateUsers(new User[] {user}, true);

        User actualUser = instance.getUsers(new long[] {user.getId()})[0];
        AccuracyTestHelper.assertUserEquals(user, actualUser);
    }

    /**
     * <p>
     * Tests UserManagerImpl#removeUsers(long[],boolean) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemoveUsers() throws Exception {
        User user = AccuracyTestHelper.createUser();
        instance.addUsers(new User[] {user}, true);
        instance.removeUsers(new long[] {user.getId()}, true);

        User[] users = instance.getAllUsers();
        for (int i = 0; i < users.length; i++) {
            if (users[i].getId() == user.getId()) {
                fail("Failed to remove the user with id " + user.getId() + ".");
            }
        }
    }

    /**
     * <p>
     * Tests UserManagerImpl#getUsers(long[]) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetUsers() throws Exception {
        User user = instance.getUsers(new long[] {1})[0];

        assertEquals("The user names are not equals.", "admin", user.getUsername());
        assertEquals("The passwords are not equals.", "tc_super", user.getPassword());
        assertEquals("The statuses are not equals.", 1, user.getStatus().getId());
        assertEquals("The addresses are not equals.", 1, user.getAddress().getId());
        assertEquals("The company ids are not equals.", 1, user.getCompanyId());
        assertEquals("The contacts are not equals.", 1, user.getContact().getId());
    }

    /**
     * <p>
     * Tests UserManagerImpl#getAllUsers() for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetAllUsers() throws Exception {
        User[] users = instance.getAllUsers();

        assertEquals("Expected one user should be in the database.", 1, users.length);
        assertEquals("The user names are not equals.", "admin", users[0].getUsername());
        assertEquals("The passwords are not equals.", "tc_super", users[0].getPassword());
        assertEquals("The statuses are not equals.", 1, users[0].getStatus().getId());
        assertEquals("The addresses are not equals.", 1, users[0].getAddress().getId());
        assertEquals("The company ids are not equals.", 1, users[0].getCompanyId());
        assertEquals("The contacts are not equals.", 1, users[0].getContact().getId());
    }

    /**
     * <p>
     * Tests UserManagerImpl#createUser(User,boolean) for accuracy.
     * </p>
     * @throws Exception to JUnit
     */
    public void testCreateUser() throws Exception {
        User user = AccuracyTestHelper.createUser();
        instance.createUser(user, true);

        User actualUser = instance.getUser(user.getId());
        AccuracyTestHelper.assertUserEquals(user, actualUser);
    }

    /**
     * <p>
     * Tests UserManagerImpl#updateUser(User,boolean) for accuracy.
     * </p>
     * @throws Exception to JUnit
     */
    public void testUpdateUser() throws Exception {
        User user = AccuracyTestHelper.createUser();
        instance.createUser(user, true);
        user.setUsername("new");
        user.setStatus(Status.ACTIVE);
        instance.updateUser(user, true);

        User actualUser = instance.getUser(user.getId());
        AccuracyTestHelper.assertUserEquals(user, actualUser);
    }

    /**
     * <p>
     * Tests UserManagerImpl#removeUser(long,boolean) for accuracy.
     * </p>
     * @throws Exception to JUnit
     */
    public void testRemoveUser() throws Exception {
        instance.removeUser(1, true);

        User[] users = instance.getAllUsers();
        for (int i = 0; i < users.length; i++) {
            if (users[i].getId() == 1) {
                fail("Failed to remove the user with id 1.");
            }
        }
    }

    /**
     * <p>
     * Tests UserManagerImpl#getUser(long) for accuracy.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetUser() throws Exception {
        User user = AccuracyTestHelper.createUser();
        instance.createUser(user, true);

        User actualUser = instance.getUser(user.getId());
        AccuracyTestHelper.assertUserEquals(user, actualUser);
    }

    /**
     * <p>
     * Tests UserManagerImpl#addRoleToUser(User,SecurityRole) for accuracy.
     * </p>
     * @throws Exception to JUnit
     */
    public void testAddRoleToUser() throws Exception {
        SecurityRole role = new GeneralSecurityRole("NewRole");
        authPersistence.addRole(role);
        User user = instance.getUser(1);
        instance.addRoleToUser(user, role);

        SecurityRole[] roles = instance.retrieveRolesForUser(user);
        assertTrue("Failed to add the role.", Arrays.asList(roles).contains(role));
    }

    /**
     * <p>
     * Tests UserManagerImpl#removeRoleFromUser(User,SecurityRole) for accuracy.
     * </p>
     * @throws Exception to JUnit
     */
    public void testRemoveRoleFromUser() throws Exception {
        User user = instance.getUser(1);
        SecurityRole[] roles = instance.retrieveRolesForUser(user);
        for (int i = 0; i < roles.length; i++) {
            instance.removeRoleFromUser(user, roles[i]);
        }

        assertEquals("Failed to remove the roles for the user.", 0, instance.retrieveRolesForUser(user).length);
    }

    /**
     * <p>
     * Tests UserManagerImpl#retrieveRolesForUser(User) for accuracy.
     * </p>
     * @throws Exception to JUnit
     */
    public void testRetrieveRolesForUser() throws Exception {
        SecurityRole role = new GeneralSecurityRole("NewRole");
        authPersistence.addRole(role);
        User user = instance.getUser(1);
        instance.addRoleToUser(user, role);

        SecurityRole[] roles = instance.retrieveRolesForUser(user);
        assertTrue("Failed to add the role.", Arrays.asList(roles).contains(role));
    }

    /**
     * <p>
     * Tests UserManagerImpl#clearRolesFromUser(User) for accuracy.
     * </p>
     * @throws Exception to JUnit
     */
    public void testClearRolesFromUser() throws Exception {
        User user = instance.getUser(1);
        instance.clearRolesFromUser(user);

        assertEquals("Failed to clear the roles.", 0, instance.retrieveRolesForUser(user).length);
    }

    /**
     * <p>
     * Tests UserManagerImpl#authenticateUser(String,String) for accuracy.
     * </p>
     * @throws Exception to JUnit
     */
    public void testAuthenticateUser() throws Exception {
        assertTrue("Failed to authentication the user.", instance.authenticateUser("admin", "tc_super"));
    }

}