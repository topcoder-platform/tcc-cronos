/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user;

import java.util.Arrays;

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
import com.topcoder.timetracker.user.db.DbUserDAO;
import com.topcoder.timetracker.user.db.DbUserStatusDAO;
import com.topcoder.timetracker.user.db.DbUserTypeDAO;
import com.topcoder.timetracker.user.filterfactory.MappedUserFilterFactory;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

/**
 * <p>
 * Unit test cases for UserManagerImpl.
 * </p>
 *
 * @author biotrail, enefem21
 * @version 3.2.1
 * @since 3.2
 */
public class UserManagerImplTests extends TestCase {

    /**
     * <p>
     * The UserManagerImpl instance for testing.
     * </p>
     */
    private UserManagerImpl userManager;

    /**
     * <p>
     * The UserDAO instance for testing.
     * </p>
     */
    private UserDAO dao;

    /**
     * <p>
     * The AuthorizationPersistence instance for testing.
     * </p>
     */
    private AuthorizationPersistence authPersistence;

    /**
     * <p>
     * The AuditManager instance for testing.
     * </p>
     */
    private AuditManager auditManager;

    /**
     * <p>
     * The ContactManager instance for testing.
     * </p>
     */
    private ContactManager contactManager;

    /**
     * <p>
     * The AddressManager instance for testing.
     * </p>
     */
    private AddressManager addressManager;

    /**
     * <p>
     * The UserStatusDAO instance for testing.
     * </p>
     */
    private UserStatusDAO userStatusDAO;

    /**
     * <p>
     * The UserTypeDAO instance for testing.
     * </p>
     */
    private UserTypeDAO userTypeDAO;

    /**
     * <p>
     * The DBConnectionFactory instance for testing.
     * </p>
     */
    private DBConnectionFactory dbFactory;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        TestHelper.loadXMLConfig(TestHelper.CONFIG_FILE);
        TestHelper.loadXMLConfig(TestHelper.CONFIG_FILE_3_2_1);
        TestHelper.setUpDataBase();

        dbFactory = new DBConnectionFactoryImpl(TestHelper.DB_FACTORY_NAMESPACE);
        auditManager = new MockAuditManager();
        contactManager = new MockContactManager();
        authPersistence =
            new SQLAuthorizationPersistence("com.topcoder.timetracker.application.authorization");
        addressManager = new MockAddressManager();
        userStatusDAO =
            new DbUserStatusDAO(dbFactory, "tt_user", "com.topcoder.timetracker.user.UserStatus",
                "com.topcoder.search.builder", "userStatusSearchBundle");
        userTypeDAO =
            new DbUserTypeDAO(dbFactory, "tt_user", "com.topcoder.timetracker.user.UserType",
                "com.topcoder.search.builder", "userTypeSearchBundle");
        dao =
            new DbUserDAO(dbFactory, "tt_user", "com.topcoder.timetracker.user.User",
                "com.topcoder.search.builder.database.DatabaseSearchStrategy", auditManager, contactManager,
                authPersistence, addressManager, userStatusDAO, userTypeDAO, true);

        userManager = new UserManagerImpl(dao, authPersistence, "Default_TT_UserAuthenticator");
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     *
     */
    protected void tearDown() throws Exception {
        userManager = null;
        dao = null;
        addressManager = null;
        authPersistence = null;
        contactManager = null;
        auditManager = null;
        dbFactory = null;

        TestHelper.tearDownDataBase();
        TestHelper.clearConfig();
    }

    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(UserManagerImplTests.class);
    }

    /**
     * <p>
     * Tests ctor UserManagerImpl#UserManagerImpl(UserDAO,AuthorizationPersistence,String) for accuracy.
     * </p>
     *
     * <p>
     * It verifies the newly created UserManagerImpl instance should not be null.
     * </p>
     */
    public void testCtor() {
        assertNotNull("Failed to create a new UserManagerImpl instance.", userManager);
    }

    /**
     * <p>
     * Tests ctor UserManagerImpl#UserManagerImpl(UserDAO,AuthorizationPersistence,String) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when userDao is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws ConfigurationException
     *             to JUnit
     */
    public void testCtor_NullUserDao() throws ConfigurationException {
        try {
            new UserManagerImpl(null, authPersistence, "Default_TT_UserAuthenticator");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor UserManagerImpl#UserManagerImpl(UserDAO,AuthorizationPersistence,String) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when authPersistence is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws ConfigurationException
     *             to JUnit
     */
    public void testCtor_NullAuthPersistence() throws ConfigurationException {
        try {
            new UserManagerImpl(dao, null, "Default_TT_UserAuthenticator");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor UserManagerImpl#UserManagerImpl(UserDAO,AuthorizationPersistence,String) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when authenticatorName is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws ConfigurationException
     *             to JUnit
     */
    public void testCtor_NullAuthenticatorName() throws ConfigurationException {
        try {
            new UserManagerImpl(dao, authPersistence, null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor UserManagerImpl#UserManagerImpl(UserDAO,AuthorizationPersistence,String) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when authenticatorName is empty and expects IllegalArgumentException.
     * </p>
     *
     * @throws ConfigurationException
     *             to JUnit
     */
    public void testCtor_EmptyAuthenticatorName() throws ConfigurationException {
        try {
            new UserManagerImpl(dao, authPersistence, " ");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor UserManagerImpl#UserManagerImpl(UserDAO,AuthorizationPersistence,String) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when authenticatorName is invalid and expects ConfigurationException.
     * </p>
     */
    public void testCtor_InvalidAuthenticatorName() {
        try {
            new UserManagerImpl(dao, authPersistence, "name");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerImpl#createUser(User,boolean) for accuracy.
     * </p>
     *
     * <p>
     * It verifies UserManagerImpl#createUser(User,boolean) is correct.
     * </p>
     *
     * @throws DataAccessException
     *             to JUnit
     */
    public void testCreateUser() throws DataAccessException {
        User testingUser = TestHelper.createTestingUser(null);

        userManager.createUser(testingUser, true);

        User actualUser = userManager.getUser(testingUser.getId());

        TestHelper.assertUserEquals(testingUser, actualUser);
    }

    /**
     * <p>
     * Tests UserManagerImpl#createUser(User,boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when user is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws DataAccessException
     *             to JUnit
     */
    public void testCreateUser_NullUser() throws DataAccessException {
        try {
            userManager.createUser(null, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerImpl#createUser(User,boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case when the connection name is invalid and expects DataAccessException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCreateUser_DataAccessException() throws Exception {
        dao =
            new DbUserDAO(dbFactory, "no_connection", "com.topcoder.timetracker.user.User",
                "com.topcoder.search.builder.database.DatabaseSearchStrategy", auditManager, contactManager,
                authPersistence, addressManager, userStatusDAO, userTypeDAO, true);
        userManager = new UserManagerImpl(dao, authPersistence, "Default_TT_UserAuthenticator");
        try {
            userManager.createUser(TestHelper.createTestingUser(null), true);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerImpl#addUsers(User[],boolean) for accuracy.
     * </p>
     *
     * <p>
     * It verifies UserManagerImpl#addUsers(User[],boolean) is correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAddUsers() throws Exception {
        User testingUser = TestHelper.createTestingUser(null);

        userManager.addUsers(new User[] {testingUser}, true);

        User actualUser = userManager.getUsers(new long[] {testingUser.getId()})[0];

        TestHelper.assertUserEquals(testingUser, actualUser);
    }

    /**
     * <p>
     * Tests UserManagerImpl#addUsers(User[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when users is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAddUsers_NullUsers() throws Exception {
        try {
            userManager.addUsers(null, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerImpl#addUsers(User[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when users contain null element and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAddUsers_NullInUsers() throws Exception {
        try {
            userManager.addUsers(new User[] {null}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerImpl#addUsers(User[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case when the connection name is invalid and expects DataAccessException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAddUsers_DataAccessException() throws Exception {
        dao =
            new DbUserDAO(dbFactory, "no_connection", "com.topcoder.timetracker.user.User",
                "com.topcoder.search.builder.database.DatabaseSearchStrategy", auditManager, contactManager,
                authPersistence, addressManager, userStatusDAO, userTypeDAO, true);
        userManager = new UserManagerImpl(dao, authPersistence, "Default_TT_UserAuthenticator");
        try {
            userManager.addUsers(new User[] {TestHelper.createTestingUser(null)}, true);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerImpl#updateUser(User,boolean) for accuracy.
     * </p>
     *
     * <p>
     * It verifies UserManagerImpl#updateUser(User,boolean) is correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateUser() throws Exception {
        User testingUser = TestHelper.createTestingUser(null);
        userManager.createUser(testingUser, true);

        testingUser.setUsername("helloWorld");
        testingUser.setStatus(Status.INACTIVE);

        userManager.updateUser(testingUser, true);

        User actualUser = userManager.getUser(testingUser.getId());
        TestHelper.assertUserEquals(testingUser, actualUser);
    }

    /**
     * <p>
     * Tests UserManagerImpl#updateUser(User,boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when user is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateUser_NullUser() throws Exception {
        try {
            userManager.updateUser(null, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerImpl#updateUser(User,boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case when the connection name is invalid and expects DataAccessException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateUser_DataAccessException() throws Exception {
        dao =
            new DbUserDAO(dbFactory, "no_connection", "com.topcoder.timetracker.user.User",
                "com.topcoder.search.builder.database.DatabaseSearchStrategy", auditManager, contactManager,
                authPersistence, addressManager, userStatusDAO, userTypeDAO, true);
        userManager = new UserManagerImpl(dao, authPersistence, "Default_TT_UserAuthenticator");
        User testingUser = TestHelper.createTestingUser(null);

        try {
            userManager.updateUser(testingUser, true);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerImpl#updateUser(User,boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case when the user is not persisted and expects UnrecognizedEntityException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateUser_UnrecognizedEntityException() throws Exception {
        try {
            userManager.updateUser(TestHelper.createTestingUser(null), true);
            fail("UnrecognizedEntityException expected.");
        } catch (UnrecognizedEntityException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerImpl#updateUsers(User[],boolean) for accuracy.
     * </p>
     *
     * <p>
     * It verifies UserManagerImpl#updateUsers(User[],boolean) is correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateUsers() throws Exception {
        User testingUser = TestHelper.createTestingUser(null);
        userManager.addUsers(new User[] {testingUser}, true);

        testingUser.setUsername("helloWorld");
        testingUser.setStatus(Status.INACTIVE);

        userManager.updateUsers(new User[] {testingUser}, true);

        User actualUser = userManager.getUsers(new long[] {testingUser.getId()})[0];
        TestHelper.assertUserEquals(testingUser, actualUser);
    }

    /**
     * <p>
     * Tests UserManagerImpl#updateUsers(User[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when users is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateUsers_NullUsers() throws Exception {
        try {
            userManager.updateUsers(null, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerImpl#updateUsers(User[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when users contains null element and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateUsers_NullInUsers() throws Exception {
        try {
            userManager.updateUsers(new User[] {null}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerImpl#updateUsers(User[],boolean) for failure.
     * </p>
     *
     * <p>
     * It test the case when some user id is unknown and expects BatchOperationException. And the cause of the
     * BatchOperationException should be UnrecognizedEntityException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateUsers_BatchOperationException() throws Exception {
        User testingUser = TestHelper.createTestingUser(null);
        testingUser.setId(4000);
        try {
            userManager.updateUsers(new User[] {testingUser}, true);
            fail("BatchOperationException expected.");
        } catch (BatchOperationException e) {
            Throwable[] causes = e.getCauses();
            assertEquals("Should be only one cause.", 1, causes.length);
            assertEquals("The cause should be UnrecognizedEntityException.",
                UnrecognizedEntityException.class, causes[0].getClass());
        }
    }

    /**
     * <p>
     * Tests UserManagerImpl#updateUsers(User[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case when the connection name is invalid and expects DataAccessException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateUsers_DataAccessException() throws Exception {
        dao =
            new DbUserDAO(dbFactory, "no_connection", "com.topcoder.timetracker.user.User",
                "com.topcoder.search.builder.database.DatabaseSearchStrategy", auditManager, contactManager,
                authPersistence, addressManager, userStatusDAO, userTypeDAO, true);
        userManager = new UserManagerImpl(dao, authPersistence, "Default_TT_UserAuthenticator");
        User testingUser = TestHelper.createTestingUser(null);
        testingUser.setId(1);

        try {
            userManager.updateUsers(new User[] {testingUser}, true);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerImpl#removeUser(long,boolean) for accuracy.
     * </p>
     *
     * <p>
     * It verifies UserManagerImpl#removeUser(long,boolean) is correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRemoveUser() throws Exception {
        userManager.removeUser(1, true);

        User[] users = userManager.getAllUsers();
        for (int i = 0; i < users.length; i++) {
            if (users[i].getId() == 1) {
                fail("Failed to remove the user with id [1].");
            }
        }
    }

    /**
     * <p>
     * Tests UserManagerImpl#removeUser(long,boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case when userId <= 0 and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRemoveUser_NegativeId() throws Exception {
        try {
            userManager.removeUser(-5, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerImpl#removeUser(long,boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case when id was not found in the data store and expects UnrecognizedEntityException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRemoveUser_UnrecognizedEntityException() throws Exception {
        try {
            userManager.removeUser(4000, true);
            fail("UnrecognizedEntityException expected.");
        } catch (UnrecognizedEntityException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerImpl#removeUser(long,boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case when the connection name is invalid and expects DataAccessException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRemoveUser_DataAccessException() throws Exception {
        dao =
            new DbUserDAO(dbFactory, "no_connection", "com.topcoder.timetracker.user.User",
                "com.topcoder.search.builder.database.DatabaseSearchStrategy", auditManager, contactManager,
                authPersistence, addressManager, userStatusDAO, userTypeDAO, true);
        userManager = new UserManagerImpl(dao, authPersistence, "Default_TT_UserAuthenticator");
        try {
            userManager.removeUser(1, true);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerImpl#removeUsers([J,boolean) for accuracy.
     * </p>
     *
     * <p>
     * It verifies UserManagerImpl#removeUsers([J,boolean) is correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRemoveUsers() throws Exception {
        User testingUser = TestHelper.createTestingUser(null);
        userManager.addUsers(new User[] {testingUser}, true);

        userManager.removeUsers(new long[] {1, testingUser.getId()}, true);

        User[] users = userManager.getAllUsers();
        for (int i = 0; i < users.length; i++) {
            if (users[i].getId() == 1) {
                fail("Failed to remove the user with id [1].");
            }

            if (users[i].getId() == testingUser.getId()) {
                fail("Failed to remove the user with id [" + testingUser.getId() + "].");
            }
        }
    }

    /**
     * <p>
     * Tests UserManagerImpl#removeUsers(long[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when userIds is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRemoveUsers_NullUserIds() throws Exception {
        try {
            userManager.removeUsers(null, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerImpl#removeUsers(long[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when userIds contains negative element and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRemoveUsers_NegativeUserIds() throws Exception {
        try {
            userManager.removeUsers(new long[] {1, -6}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerImpl#removeUsers(long[],boolean) for failure.
     * </p>
     *
     * <p>
     * It test the case when some user id is unknown and expects BatchOperationException. And the cause of the
     * BatchOperationException should be UnrecognizedEntityException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRemoveUsers_BatchOperationException() throws Exception {
        try {
            userManager.removeUsers(new long[] {1, 4342424}, true);
            fail("BatchOperationException expected.");
        } catch (BatchOperationException e) {
            Throwable[] causes = e.getCauses();
            assertEquals("Should be two causes.", 2, causes.length);
            assertNull("The first user should can be removed.", causes[0]);
            assertEquals("The second user should not be removed because of UnrecognizedEntityException.",
                UnrecognizedEntityException.class, causes[1].getClass());
        }
    }

    /**
     * <p>
     * Tests UserManagerImpl#removeUsers(long[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case when the connection name is invalid and expects DataAccessException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRemoveUsers_DataAccessException() throws Exception {
        dao =
            new DbUserDAO(dbFactory, "no_connection", "com.topcoder.timetracker.user.User",
                "com.topcoder.search.builder.database.DatabaseSearchStrategy", auditManager, contactManager,
                authPersistence, addressManager, userStatusDAO, userTypeDAO, true);
        userManager = new UserManagerImpl(dao, authPersistence, "Default_TT_UserAuthenticator");
        try {
            userManager.removeUsers(new long[] {1}, true);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerImpl#getUser(long) for accuracy.
     * </p>
     *
     * <p>
     * It verifies UserManagerImpl#getUser(long) is correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetUser() throws Exception {
        User user = userManager.getUser(1);

        assertEquals("The user names are not equals.", "admin", user.getUsername());
        assertEquals("The passwords are not equals.", "tc_super", user.getPassword());
        assertEquals("The statuses are not equals.", 1, user.getStatus().getId());
        assertEquals("The addresses are not equals.", 1, user.getAddress().getId());
        assertEquals("The company ids are not equals.", 1, user.getCompanyId());
        assertEquals("The contacts are not equals.", 1, user.getContact().getId());
        assertEquals("The creation users are not equals.", "", user.getCreationUser());
        assertEquals("The modification users are not equals.", "", user.getModificationUser());
    }

    /**
     * <p>
     * Tests UserManagerImpl#getUser(long) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when id was not found in the data store and expects UnrecognizedEntityException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetUser_UnrecognizedEntityException() throws Exception {
        try {
            userManager.getUser(40000);
            fail("UnrecognizedEntityException expected.");
        } catch (UnrecognizedEntityException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerImpl#getUser(long) for failure.
     * </p>
     *
     * <p>
     * It tests the case when the connection name is invalid and expects DataAccessException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetUser_DataAccessException() throws Exception {
        dao =
            new DbUserDAO(dbFactory, "no_connection", "com.topcoder.timetracker.user.User",
                "com.topcoder.search.builder.database.DatabaseSearchStrategy", auditManager, contactManager,
                authPersistence, addressManager, userStatusDAO, userTypeDAO, true);
        userManager = new UserManagerImpl(dao, authPersistence, "Default_TT_UserAuthenticator");
        try {
            userManager.getUser(1);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerImpl#getUsers(long[]) for accuracy.
     * </p>
     *
     * <p>
     * It verifies UserManagerImpl#getUsers(long[]) is correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetUsers() throws Exception {
        User user = userManager.getUsers(new long[] {1})[0];

        assertEquals("The user names are not equals.", "admin", user.getUsername());
        assertEquals("The passwords are not equals.", "tc_super", user.getPassword());
        assertEquals("The statuses are not equals.", 1, user.getStatus().getId());
        assertEquals("The addresses are not equals.", 1, user.getAddress().getId());
        assertEquals("The company ids are not equals.", 1, user.getCompanyId());
        assertEquals("The contacts are not equals.", 1, user.getContact().getId());
        assertEquals("The creation users are not equals.", "", user.getCreationUser());
        assertEquals("The modification users are not equals.", "", user.getModificationUser());
    }

    /**
     * <p>
     * Tests UserManagerImpl#getUsers(long[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when userIds is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetUsers_NullUserIds() throws Exception {
        try {
            userManager.getUsers(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerImpl#getUsers(long[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when some user id is negative and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetUsers_NegativeUserId() throws Exception {
        try {
            userManager.getUsers(new long[] {1, -8});
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerImpl#getUsers(long[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case when the connection name is invalid and expects DataAccessException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetUsers_DataAccessException() throws Exception {
        dao =
            new DbUserDAO(dbFactory, "no_connection", "com.topcoder.timetracker.user.User",
                "com.topcoder.search.builder.database.DatabaseSearchStrategy", auditManager, contactManager,
                authPersistence, addressManager, userStatusDAO, userTypeDAO, true);
        userManager = new UserManagerImpl(dao, authPersistence, "Default_TT_UserAuthenticator");
        try {
            userManager.getUsers(new long[] {1});
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerImpl#searchUsers(Filter) for accuracy.
     * </p>
     *
     * <p>
     * It verifies UserManagerImpl#searchUsers(Filter) is correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSearchUsers() throws Exception {
        Filter userNameFilter =
            userManager.getUserFilterFactory().createUsernameFilter(StringMatchType.EXACT_MATCH, "admin");
        User[] users = userManager.searchUsers(userNameFilter);

        assertEquals("Only one user should be in the database.", 1, users.length);

        assertEquals("The user names are not equals.", "admin", users[0].getUsername());
        assertEquals("The passwords are not equals.", "tc_super", users[0].getPassword());
        assertEquals("The statuses are not equals.", 1, users[0].getStatus().getId());
        assertEquals("The addresses are not equals.", 1, users[0].getAddress().getId());
        assertEquals("The company ids are not equals.", 1, users[0].getCompanyId());
        assertEquals("The contacts are not equals.", 1, users[0].getContact().getId());
        assertEquals("The creation users are not equals.", "", users[0].getCreationUser());
        assertEquals("The modification users are not equals.", "", users[0].getModificationUser());
    }

    /**
     * <p>
     * Tests UserManagerImpl#searchUsers(Filter) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when filter is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSearchUsers_NullFilter() throws Exception {
        try {
            userManager.searchUsers(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerImpl#searchUsers(Filter) for failure.
     * </p>
     *
     * <p>
     * It tests the case when the connection name is invalid and expects DataAccessException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSearchUsers_DataAccessException() throws Exception {
        dao =
            new DbUserDAO(dbFactory, "no_connection", "com.topcoder.timetracker.user.User",
                "com.topcoder.search.builder.database.DatabaseSearchStrategy", auditManager, contactManager,
                authPersistence, addressManager, userStatusDAO, userTypeDAO, true);
        userManager = new UserManagerImpl(dao, authPersistence, "Default_TT_UserAuthenticator");
        Filter userNameFilter =
            userManager.getUserFilterFactory().createUsernameFilter(StringMatchType.EXACT_MATCH, "admin");

        try {
            userManager.searchUsers(userNameFilter);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerImpl#addRoleToUser(User,SecurityRole) for accuracy.
     * </p>
     *
     * <p>
     * It verifies UserManagerImpl#addRoleToUser(User,SecurityRole) is correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAddRoleToUser() throws Exception {
        SecurityRole role = new GeneralSecurityRole("NewRole");
        authPersistence.addRole(role);
        User user = userManager.getUser(1);

        userManager.addRoleToUser(user, role);

        // verify the result
        SecurityRole[] roles = userManager.retrieveRolesForUser(user);
        assertTrue("Failed to add the role.", Arrays.asList(roles).contains(role));
    }

    /**
     * <p>
     * Tests UserManagerImpl#addRoleToUser(User,SecurityRole) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when user is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAddRoleToUser_NullUser() throws Exception {
        SecurityRole role = new GeneralSecurityRole("NewRole");
        authPersistence.addRole(role);

        try {
            userManager.addRoleToUser(null, role);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerImpl#addRoleToUser(User,SecurityRole) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when role hasn't been persisted and expects DataAccessException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAddRoleToUser_NotPersistedRole() throws Exception {
        User user = userManager.getUser(1);
        SecurityRole role = new GeneralSecurityRole("NewRole");

        try {
            userManager.addRoleToUser(user, role);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerImpl#addRoleToUser(User,SecurityRole) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when role is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAddRoleToUser_NullRole() throws Exception {
        User user = userManager.getUser(1);

        try {
            userManager.addRoleToUser(user, null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerImpl#removeRoleFromUser(User,SecurityRole) for accuracy.
     * </p>
     *
     * <p>
     * It verifies UserManagerImpl#removeRoleFromUser(User,SecurityRole) is correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRemoveRoleFromUser() throws Exception {
        User user = userManager.getUser(1);
        SecurityRole[] roles = userManager.retrieveRolesForUser(user);

        for (int i = 0; i < roles.length; i++) {
            userManager.removeRoleFromUser(user, roles[i]);
        }

        assertEquals("Failed to remove the roles for the user.", 0,
            userManager.retrieveRolesForUser(user).length);
    }

    /**
     * <p>
     * Tests UserManagerImpl#removeRoleFromUser(User,SecurityRole) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when user is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRemoveRoleFromUser_NullUser() throws Exception {
        User user = userManager.getUser(1);
        SecurityRole role = userManager.retrieveRolesForUser(user)[0];

        try {
            userManager.removeRoleFromUser(null, role);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerImpl#removeRoleFromUser(User,SecurityRole) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when role is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRemoveRoleFromUser_NullRole() throws Exception {
        User user = userManager.getUser(1);

        try {
            userManager.removeRoleFromUser(user, null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerImpl#removeRoleFromUser(User,SecurityRole) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when role does not belongs to the user and expects DataAccessException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRemoveRoleFromUser_UnknownRole() throws Exception {
        User user = userManager.getUser(1);
        SecurityRole newRole = new GeneralSecurityRole("NewRole");
        authPersistence.addRole(newRole);

        try {
            userManager.removeRoleFromUser(user, newRole);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerImpl#removeRoleFromUser(User,SecurityRole) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when role hasn't been persisted and expects DataAccessException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRemoveRoleFromUser_NotPersistedRole() throws Exception {
        User user = userManager.getUser(1);
        SecurityRole newRole = new GeneralSecurityRole("NewRole");

        try {
            userManager.removeRoleFromUser(user, newRole);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerImpl#retrieveRolesForUser(User) for accuracy.
     * </p>
     *
     * <p>
     * It verifies UserManagerImpl#retrieveRolesForUser(User) is correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRetrieveRolesForUser() throws Exception {
        User user = userManager.getUser(1);
        SecurityRole[] roles = userManager.retrieveRolesForUser(user);

        assertEquals("Failed to retrieve all the security roles for the admin user.", 1, roles.length);
        assertEquals("Failed to retrieve all the security roles for the admin user.", "Administrator",
            roles[0].getName());
    }

    /**
     * <p>
     * Tests UserManagerImpl#retrieveRolesForUser(User) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when user is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRetrieveRolesForUser_NullUser() throws Exception {
        try {
            userManager.retrieveRolesForUser(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerImpl#clearRolesFromUser(User) for accuracy.
     * </p>
     *
     * <p>
     * It verifies UserManagerImpl#clearRolesFromUser(User) is correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testClearRolesFromUser() throws Exception {
        User user = userManager.getUser(1);
        userManager.clearRolesFromUser(user);

        assertEquals("Failed to clear the roles.", 0, userManager.retrieveRolesForUser(user).length);
    }

    /**
     * <p>
     * Tests UserManagerImpl#clearRolesFromUser(User) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when user is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testClearRolesFromUser_NullUser() throws Exception {
        try {
            userManager.clearRolesFromUser(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerImpl#authenticateUser(String,String) for accuracy.
     * </p>
     *
     * <p>
     * It verifies UserManagerImpl#authenticateUser(String,String) is correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAuthenticateUser() throws Exception {
        assertTrue("Failed to authentication the user.", userManager.authenticateUser("admin", "tc_super"));
        assertFalse("Should fail authentication because of unknown user name.", userManager.authenticateUser(
            "new_comer", "tc_super"));
        assertFalse("Should fail authentication because of unknown user name.", userManager.authenticateUser(
            "admin", "try_it_out"));
    }

    /**
     * <p>
     * Tests UserManagerImpl#authenticateUser(String,String) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when username is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAuthenticateUser_NullUsername() throws Exception {
        try {
            userManager.authenticateUser(null, "tc_super");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerImpl#authenticateUser(String,String) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when username is empty and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAuthenticateUser_EmptyUsername() throws Exception {
        try {
            userManager.authenticateUser("  ", "tc_super");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerImpl#authenticateUser(String,String) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when password is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAuthenticateUser_NullPassword() throws Exception {
        try {
            userManager.authenticateUser("admin", null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerImpl#authenticateUser(String,String) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when password is empty and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAuthenticateUser_EmptyPassword() throws Exception {
        try {
            userManager.authenticateUser("admin", " ");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerImpl#getAllUsers() for accuracy.
     * </p>
     *
     * <p>
     * It verifies UserManagerImpl#getAllUsers() is correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetAllUsers() throws Exception {
        User[] users = userManager.getAllUsers();

        assertEquals("Only one user should be in the database.", 1, users.length);

        assertEquals("The user names are not equals.", "admin", users[0].getUsername());
        assertEquals("The passwords are not equals.", "tc_super", users[0].getPassword());
        assertEquals("The statuses are not equals.", 1, users[0].getStatus().getId());
        assertEquals("The addresses are not equals.", 1, users[0].getAddress().getId());
        assertEquals("The company ids are not equals.", 1, users[0].getCompanyId());
        assertEquals("The contacts are not equals.", 1, users[0].getContact().getId());
        assertEquals("The creation users are not equals.", "", users[0].getCreationUser());
        assertEquals("The modification users are not equals.", "", users[0].getModificationUser());
    }

    /**
     * <p>
     * Tests UserManagerImpl#getAllUsers() for failure.
     * </p>
     *
     * <p>
     * It tests the case when the connection name is invalid and expects DataAccessException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetAllUsers_DataAccessException() throws Exception {
        dao =
            new DbUserDAO(dbFactory, "no_connection", "com.topcoder.timetracker.user.User",
                "com.topcoder.search.builder.database.DatabaseSearchStrategy", auditManager, contactManager,
                authPersistence, addressManager, userStatusDAO, userTypeDAO, true);
        userManager = new UserManagerImpl(dao, authPersistence, "Default_TT_UserAuthenticator");
        try {
            userManager.getAllUsers();
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerImpl#getUserFilterFactory() for accuracy.
     * </p>
     *
     * <p>
     * It verifies UserManagerImpl#getUserFilterFactory() is correct.
     * </p>
     */
    public void testGetUserFilterFactory() {
        UserFilterFactory factory = userManager.getUserFilterFactory();

        assertEquals("Failed to get the user filter factory.", MappedUserFilterFactory.class, factory
            .getClass());
    }
}