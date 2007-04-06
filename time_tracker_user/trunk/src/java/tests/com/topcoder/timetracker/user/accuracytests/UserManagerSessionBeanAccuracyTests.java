/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user.accuracytests;

import java.util.Arrays;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.mockejb.MockContainer;
import org.mockejb.SessionBeanDescriptor;
import org.mockejb.jndi.MockContextFactory;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

import com.topcoder.search.builder.filter.Filter;
import com.topcoder.security.authorization.AuthorizationPersistence;
import com.topcoder.security.authorization.SecurityRole;
import com.topcoder.security.authorization.persistence.GeneralSecurityRole;
import com.topcoder.security.authorization.persistence.SQLAuthorizationPersistence;
import com.topcoder.timetracker.user.Status;
import com.topcoder.timetracker.user.StringMatchType;
import com.topcoder.timetracker.user.User;
import com.topcoder.timetracker.user.UserFilterFactory;
import com.topcoder.timetracker.user.filterfactory.MappedUserFilterFactory;
import com.topcoder.timetracker.user.j2ee.UserManagerDelegate;
import com.topcoder.timetracker.user.j2ee.UserManagerLocal;
import com.topcoder.timetracker.user.j2ee.UserManagerLocalHome;
import com.topcoder.timetracker.user.j2ee.UserManagerSessionBean;

/**
 * <p>
 * Accuracy Unit test cases for UserManagerSessionBean.
 * </p>
 *
 * @author victorsam
 * @version 3.2
 */
public class UserManagerSessionBeanAccuracyTests extends TestCase {

    /**
     * <p>
     * UserManagerSessionBean instance for testing.
     * </p>
     */
    private UserManagerSessionBean instance;

    /**
     * <p>
     * UserManagerDelegate instance used to test the functionality of UserManagerSessionBean.
     * </p>
     */
    private UserManagerDelegate delegate;

    /**
     * <p>
     * AuthorizationPersistence instance for testing.
     * </p>
     */
    private AuthorizationPersistence authPersistence;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        AccuracyTestHelper.loadXMLConfig(AccuracyTestHelper.CONFIG_FILE);
        AccuracyTestHelper.setUpDataBase();
        authPersistence = new SQLAuthorizationPersistence("com.topcoder.timetracker.application.authorization");

        MockContextFactory.setAsInitial();
        Context context = new InitialContext();
        MockContainer mockContainer = new MockContainer(context);
        MockUserTransaction mockTransaction = new MockUserTransaction();
        context.rebind("javax.transaction.UserTransaction", mockTransaction);
        instance = new UserManagerSessionBean();
        SessionBeanDescriptor sampleServiceDescriptor = new SessionBeanDescriptor("UserManagerLocalHome",
            UserManagerLocalHome.class, UserManagerLocal.class, instance);
        mockContainer.deploy(sampleServiceDescriptor);

        delegate = new UserManagerDelegate("com.topcoder.timetracker.user.j2ee.UserManagerDelegate");
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        AccuracyTestHelper.tearDownDataBase();
        AccuracyTestHelper.clearConfig();
        authPersistence = null;
        delegate = null;
        instance = null;
    }

    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(UserManagerSessionBeanAccuracyTests.class);
    }

    /**
     * <p>
     * Tests ctor UserManagerSessionBean#UserManagerSessionBean() for accuracy.
     * </p>
     */
    public void testCtor1() {
        assertNotNull("Failed to create UserManagerSessionBean instance.", instance);
    }

    /**
     * <p>
     * Tests UserManagerSessionBean#getUserFilterFactory() for accuracy.
     * </p>
     */
    public void testGetUserFilterFactory() {
        UserFilterFactory factory = delegate.getUserFilterFactory();
        assertEquals("Failed to get user filter factory.", MappedUserFilterFactory.class, factory.getClass());
    }

    /**
     * <p>
     * Tests UserManagerSessionBean#searchUsers(Filter) for accuracy.
     * </p>
     * @throws Exception to JUnit
     */
    public void testSearchUsers() throws Exception {
        Filter filter = delegate.getUserFilterFactory().createUsernameFilter(StringMatchType.ENDS_WITH, "admin");
        User[] users = delegate.searchUsers(filter);

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
     * Tests UserManagerSessionBean#addUsers(User[],boolean) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddUsers() throws Exception {
        User user = AccuracyTestHelper.createUser();
        delegate.addUsers(new User[] {user}, true);

        User actualUser = delegate.getUsers(new long[] {user.getId()})[0];
        AccuracyTestHelper.assertUserEquals(user, actualUser);
    }

    /**
     * <p>
     * Tests UserManagerSessionBean#updateUsers(User[],boolean) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateUsers() throws Exception {
        User user = AccuracyTestHelper.createUser();
        delegate.addUsers(new User[] {user}, true);
        user.setUsername("new");
        user.setStatus(Status.LOCKED);
        delegate.updateUsers(new User[] {user}, true);

        User actualUser = delegate.getUsers(new long[] {user.getId()})[0];
        AccuracyTestHelper.assertUserEquals(user, actualUser);
    }

    /**
     * <p>
     * Tests UserManagerSessionBean#removeUsers([J,boolean) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemoveUsers() throws Exception {
        User user = AccuracyTestHelper.createUser();
        delegate.addUsers(new User[] {user}, true);
        delegate.removeUsers(new long[] {user.getId()}, true);

        User[] users = delegate.getAllUsers();
        for (int i = 0; i < users.length; i++) {
            if (users[i].getId() == user.getId()) {
                fail("Failed to remove the user with id " + user.getId() + ".");
            }
        }
    }

    /**
     * <p>
     * Tests UserManagerSessionBean#getUsers([J) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetUsers() throws Exception {
        User user = delegate.getUsers(new long[] {1})[0];

        assertEquals("The user names are not equals.", "admin", user.getUsername());
        assertEquals("The passwords are not equals.", "tc_super", user.getPassword());
        assertEquals("The statuses are not equals.", 1, user.getStatus().getId());
        assertEquals("The addresses are not equals.", 1, user.getAddress().getId());
        assertEquals("The company ids are not equals.", 1, user.getCompanyId());
        assertEquals("The contacts are not equals.", 1, user.getContact().getId());
    }

    /**
     * <p>
     * Tests UserManagerSessionBean#getAllUsers() for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetAllUsers() throws Exception {
        User[] users = delegate.getAllUsers();

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
     * Tests UserManagerSessionBean#createUser(User,boolean) for accuracy.
     * </p>
     * @throws Exception to JUnit
     */
    public void testCreateUser() throws Exception {
        User user = AccuracyTestHelper.createUser();
        delegate.createUser(user, true);

        User actualUser = delegate.getUser(user.getId());
        AccuracyTestHelper.assertUserEquals(user, actualUser);
    }

    /**
     * <p>
     * Tests UserManagerSessionBean#updateUser(User,boolean) for accuracy.
     * </p>
     * @throws Exception to JUnit
     */
    public void testUpdateUser() throws Exception {
        User user = AccuracyTestHelper.createUser();
        delegate.createUser(user, true);
        user.setUsername("new");
        user.setStatus(Status.LOCKED);
        delegate.updateUser(user, true);

        User actualUser = delegate.getUser(user.getId());
        AccuracyTestHelper.assertUserEquals(user, actualUser);
    }

    /**
     * <p>
     * Tests UserManagerSessionBean#removeUser(long,boolean) for accuracy.
     * </p>
     * @throws Exception to JUnit
     */
    public void testRemoveUser() throws Exception {
        delegate.removeUser(1, true);

        User[] users = delegate.getAllUsers();
        for (int i = 0; i < users.length; i++) {
            if (users[i].getId() == 1) {
                fail("Failed to remove the user with id 1.");
            }
        }
    }

    /**
     * <p>
     * Tests UserManagerSessionBean#getUser(long) for accuracy.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetUser() throws Exception {
        User user = AccuracyTestHelper.createUser();
        delegate.createUser(user, true);

        User actualUser = delegate.getUser(user.getId());
        AccuracyTestHelper.assertUserEquals(user, actualUser);
    }

    /**
     * <p>
     * Tests UserManagerSessionBean#addRoleToUser(User,SecurityRole) for accuracy.
     * </p>
     * @throws Exception to JUnit
     */
    public void testAddRoleToUser() throws Exception {
        SecurityRole role = new GeneralSecurityRole("NewRole");
        authPersistence.addRole(role);
        User user = delegate.getUser(1);
        delegate.addRoleToUser(user, role);

        SecurityRole[] roles = delegate.retrieveRolesForUser(user);
        assertTrue("Failed to add the role.", Arrays.asList(roles).contains(role));
    }

    /**
     * <p>
     * Tests UserManagerSessionBean#removeRoleFromUser(User,SecurityRole) for accuracy.
     * </p>
     * @throws Exception to JUnit
     */
    public void testRemoveRoleFromUser() throws Exception {
        User user = delegate.getUser(1);
        SecurityRole[] roles = delegate.retrieveRolesForUser(user);
        for (int i = 0; i < roles.length; i++) {
            delegate.removeRoleFromUser(user, roles[i]);
        }

        assertEquals("Failed to remove the roles for the user.", 0, delegate.retrieveRolesForUser(user).length);
    }

    /**
     * <p>
     * Tests UserManagerSessionBean#retrieveRolesForUser(User) for accuracy.
     * </p>
     * @throws Exception to JUnit
     */
    public void testRetrieveRolesForUser() throws Exception {
        SecurityRole role = new GeneralSecurityRole("NewRole");
        authPersistence.addRole(role);
        User user = delegate.getUser(1);
        delegate.addRoleToUser(user, role);

        SecurityRole[] roles = delegate.retrieveRolesForUser(user);
        assertTrue("Failed to add the role.", Arrays.asList(roles).contains(role));
    }

    /**
     * <p>
     * Tests UserManagerSessionBean#clearRolesFromUser(User) for accuracy.
     * </p>
     * @throws Exception to JUnit
     */
    public void testClearRolesFromUser() throws Exception {
        User user = delegate.getUser(1);
        delegate.clearRolesFromUser(user);

        assertEquals("Failed to clear the roles.", 0, delegate.retrieveRolesForUser(user).length);
    }

    /**
     * <p>
     * Tests UserManagerSessionBean#authenticateUser(String,String) for accuracy.
     * </p>
     * @throws Exception to JUnit
     */
    public void testAuthenticateUser() throws Exception {
        assertTrue("Failed to authentication the user.", delegate.authenticateUser("admin", "tc_super"));
    }

}