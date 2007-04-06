/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user.accuracytests;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.security.authorization.AuthorizationPersistence;
import com.topcoder.security.authorization.persistence.SQLAuthorizationPersistence;
import com.topcoder.timetracker.audit.AuditManager;
import com.topcoder.timetracker.contact.AddressManager;
import com.topcoder.timetracker.contact.ContactManager;
import com.topcoder.timetracker.user.Status;
import com.topcoder.timetracker.user.StringMatchType;
import com.topcoder.timetracker.user.User;
import com.topcoder.timetracker.user.UserFilterFactory;
import com.topcoder.timetracker.user.db.DbUserDAO;
import com.topcoder.timetracker.user.filterfactory.MappedUserFilterFactory;

/**
 * <p>
 * Accuracy Unit test cases for DbUserDAO.
 * </p>
 *
 * @author victorsam
 * @version 3.2
 */
public class DbUserDAOAccuracyTests extends TestCase {
    /**
     * <p>
     * DbUserDAO instance for testing.
     * </p>
     */
    private DbUserDAO instance;

    /**
     * <p>
     * Setup test environment.
     * </p>
     * @throws Exception to JUnit
     *
     */
    protected void setUp() throws Exception {
        AccuracyTestHelper.loadXMLConfig(AccuracyTestHelper.CONFIG_FILE);
        AccuracyTestHelper.setUpDataBase();

        DBConnectionFactory dbFactory = new DBConnectionFactoryImpl(AccuracyTestHelper.DB_FACTORY_NAMESPACE);
        AuditManager auditManager = new MockAuditManager();
        ContactManager contactManager = new MockContactManager();
        AuthorizationPersistence authPersistence = new SQLAuthorizationPersistence(
            "com.topcoder.timetracker.application.authorization");
        AddressManager addressManager = new MockAddressManager();

        instance = new DbUserDAO(dbFactory, "tt_user", "com.topcoder.timetracker.user.User",
            "com.topcoder.search.builder.database.DatabaseSearchStrategy", auditManager, contactManager,
            authPersistence, addressManager, true);
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
        return new TestSuite(DbUserDAOAccuracyTests.class);
    }

    /**
     * <p>
     * Tests ctor DbUserDAO#DbUserDAO(DBConnectionFactory,String,String,String,AuditManager,
     * ContactManager,AuthorizationPersistence,AddressManager,boolean) for accuracy.
     * </p>
     */
    public void testCtor1() {
        assertNotNull("Failed to create DbUserDAO instance.", instance);
    }

    /**
     * <p>
     * Tests DbUserDAO#getUserFilterFactory() for accuracy.
     * </p>
     */
    public void testGetUserFilterFactory() {
        UserFilterFactory factory = instance.getUserFilterFactory();
        assertEquals("Failed to get user filter factory.", MappedUserFilterFactory.class, factory.getClass());
    }

    /**
     * <p>
     * Tests DbUserDAO#searchUsers(Filter) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testSearchUsers() throws Exception {
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
     * Tests DbUserDAO#addUsers(User[],boolean) for accuracy.
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
     * Tests DbUserDAO#updateUsers(User[],boolean) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateUsers() throws Exception {
        User user = AccuracyTestHelper.createUser();
        instance.addUsers(new User[] {user}, true);
        user.setUsername("new");
        user.setStatus(Status.LOCKED);
        instance.updateUsers(new User[] {user}, true);

        User actualUser = instance.getUsers(new long[] {user.getId()})[0];
        AccuracyTestHelper.assertUserEquals(user, actualUser);
    }

    /**
     * <p>
     * Tests DbUserDAO#removeUsers(long[],boolean) for accuracy.
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
     * Tests DbUserDAO#getUsers(long[]) for accuracy.
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
     * Tests DbUserDAO#getAllUsers() for accuracy.
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

}