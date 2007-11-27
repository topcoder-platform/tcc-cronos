/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user.db;

import java.util.Date;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.security.authorization.AuthorizationPersistence;
import com.topcoder.security.authorization.persistence.SQLAuthorizationPersistence;
import com.topcoder.timetracker.audit.AuditManager;
import com.topcoder.timetracker.contact.AddressManager;
import com.topcoder.timetracker.contact.ContactManager;
import com.topcoder.timetracker.user.BatchOperationException;
import com.topcoder.timetracker.user.ConfigurationException;
import com.topcoder.timetracker.user.DataAccessException;
import com.topcoder.timetracker.user.MockAddressManager;
import com.topcoder.timetracker.user.MockAuditManager;
import com.topcoder.timetracker.user.MockContactManager;
import com.topcoder.timetracker.user.Status;
import com.topcoder.timetracker.user.StringMatchType;
import com.topcoder.timetracker.user.TestHelper;
import com.topcoder.timetracker.user.UnrecognizedEntityException;
import com.topcoder.timetracker.user.User;
import com.topcoder.timetracker.user.UserFilterFactory;
import com.topcoder.timetracker.user.UserStatus;
import com.topcoder.timetracker.user.UserStatusDAO;
import com.topcoder.timetracker.user.UserType;
import com.topcoder.timetracker.user.UserTypeDAO;
import com.topcoder.timetracker.user.filterfactory.MappedUserFilterFactory;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

/**
 * <p>
 * Unit test cases for DbUserDAO.
 * </p>
 *
 * @author biotrail, enefem21
 * @version 3.2.1
 * @since 3.2
 */
public class DbUserDAOTests extends TestCase {
    /**
     * <p>
     * The DbUserDAO instance for testing.
     * </p>
     */
    private DbUserDAO dao;

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
     * The AuthorizationPersistence instance for testing.
     * </p>
     */
    private AuthorizationPersistence authPersistence;

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
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    protected void tearDown() throws Exception {

        dbFactory = null;
        auditManager = null;
        contactManager = null;
        authPersistence = null;
        addressManager = null;
        dao = null;
        userStatusDAO = null;
        userTypeDAO = null;

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
        return new TestSuite(DbUserDAOTests.class);
    }

    /**
     * <p>
     * Tests ctor DbUserDAO#DbUserDAO(DBConnectionFactory,String,String,String,AuditManager,ContactManager,
     * AuthorizationPersistence,AddressManager,boolean) for accuracy.
     * </p>
     *
     * <p>
     * It verifies the newly created DbUserDAO instance should not be null.
     * </p>
     */
    public void testCtor() {
        assertNotNull("Failed to create a new DbUserDAO instance.", dao);
    }

    /**
     * <p>
     * Tests ctor DbUserDAO#DbUserDAO(DBConnectionFactory,String,String,String,AuditManager,
     * ContactManager,AuthorizationPersistence,AddressManager,boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when connFactory is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtor_NullConnFactory() throws Exception {
        try {
            new DbUserDAO(null, "tt_user", "com.topcoder.timetracker.user.User",
                "com.topcoder.search.builder.database.DatabaseSearchStrategy", auditManager, contactManager,
                authPersistence, addressManager, userStatusDAO, userTypeDAO, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor DbUserDAO#DbUserDAO(DBConnectionFactory,String,String,String,AuditManager,
     * ContactManager,AuthorizationPersistence,AddressManager,boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when connName is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtor_NullConnName() throws Exception {
        assertNotNull("Failed to construct DbUserDAO when the connection name is null.", new DbUserDAO(
            dbFactory, null, "com.topcoder.timetracker.user.User",
            "com.topcoder.search.builder.database.DatabaseSearchStrategy", auditManager, contactManager,
            authPersistence, addressManager, userStatusDAO, userTypeDAO, true));
    }

    /**
     * <p>
     * Tests ctor DbUserDAO#DbUserDAO(DBConnectionFactory,String,String,String,AuditManager,
     * ContactManager,AuthorizationPersistence,AddressManager,boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when connName is empty and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtor_EmptyConnName() throws Exception {
        try {
            new DbUserDAO(dbFactory, "  ", "com.topcoder.timetracker.user.User",
                "com.topcoder.search.builder.database.DatabaseSearchStrategy", auditManager, contactManager,
                authPersistence, addressManager, userStatusDAO, userTypeDAO, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor DbUserDAO#DbUserDAO(DBConnectionFactory,String,String,String,AuditManager,
     * ContactManager,AuthorizationPersistence,AddressManager,boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when idGen is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtor_NullIdGen() throws Exception {
        try {
            new DbUserDAO(dbFactory, "tt_user", null,
                "com.topcoder.search.builder.database.DatabaseSearchStrategy", auditManager, contactManager,
                authPersistence, addressManager, userStatusDAO, userTypeDAO, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor DbUserDAO#DbUserDAO(DBConnectionFactory,String,String,String,AuditManager,
     * ContactManager,AuthorizationPersistence,AddressManager,boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when idGen is empty and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtor_EmptyIdGen() throws Exception {
        try {
            new DbUserDAO(dbFactory, "tt_user", "  ",
                "com.topcoder.search.builder.database.DatabaseSearchStrategy", auditManager, contactManager,
                authPersistence, addressManager, userStatusDAO, userTypeDAO, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor DbUserDAO#DbUserDAO(DBConnectionFactory,String,String,String,AuditManager,
     * ContactManager,AuthorizationPersistence,AddressManager,boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when searchsStrategyNamespace is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtor_NullSearchsStrategyNamespace() throws Exception {
        try {
            new DbUserDAO(dbFactory, "tt_user", "com.topcoder.timetracker.user.User", null, auditManager,
                contactManager, authPersistence, addressManager, userStatusDAO, userTypeDAO, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor DbUserDAO#DbUserDAO(DBConnectionFactory,String,String,String,AuditManager,
     * ContactManager,AuthorizationPersistence,AddressManager,boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when searchsStrategyNamespace is empty and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtor_EmptySearchsStrategyNamespace() throws Exception {
        try {
            new DbUserDAO(dbFactory, "tt_user", "com.topcoder.timetracker.user.User", "  ", auditManager,
                contactManager, authPersistence, addressManager, userStatusDAO, userTypeDAO, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor DbUserDAO#DbUserDAO(DBConnectionFactory,String,String,String,AuditManager,
     * ContactManager,AuthorizationPersistence,AddressManager,boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when auditManager is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtor_NullAuditManager() throws Exception {
        try {
            new DbUserDAO(dbFactory, "tt_user", "com.topcoder.timetracker.user.User",
                "com.topcoder.search.builder.database.DatabaseSearchStrategy", null, contactManager,
                authPersistence, addressManager, userStatusDAO, userTypeDAO, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor DbUserDAO#DbUserDAO(DBConnectionFactory,String,String,String,AuditManager,
     * ContactManager,AuthorizationPersistence,AddressManager,boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when contactManager is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtor_NullContactManager() throws Exception {
        try {
            new DbUserDAO(dbFactory, "tt_user", "com.topcoder.timetracker.user.User",
                "com.topcoder.search.builder.database.DatabaseSearchStrategy", auditManager, null,
                authPersistence, addressManager, userStatusDAO, userTypeDAO, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor DbUserDAO#DbUserDAO(DBConnectionFactory,String,String,String,AuditManager,
     * ContactManager,AuthorizationPersistence,AddressManager,boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when authPersistence is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtor_NullAuthPersistence() throws Exception {
        try {
            new DbUserDAO(dbFactory, "tt_user", "com.topcoder.timetracker.user.User",
                "com.topcoder.search.builder.database.DatabaseSearchStrategy", auditManager, contactManager,
                null, addressManager, userStatusDAO, userTypeDAO, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor DbUserDAO#DbUserDAO(DBConnectionFactory,String,String,String,AuditManager,
     * ContactManager,AuthorizationPersistence,AddressManager,boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when addressManager is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtor_NullAddressManager() throws Exception {
        try {
            new DbUserDAO(dbFactory, "tt_user", "com.topcoder.timetracker.user.User",
                "com.topcoder.search.builder.database.DatabaseSearchStrategy", auditManager, contactManager,
                authPersistence, null, userStatusDAO, userTypeDAO, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor DbUserDAO#DbUserDAO(DBConnectionFactory,String,String,String,AuditManager,
     * ContactManager,AuthorizationPersistence,AddressManager,boolean) for failure.
     * </p>
     *
     * <p>
     * Expects for ConfigurationException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtor_ConfigurationException() throws Exception {
        TestHelper.clearConfigFile("com.topcoder.search.builder.database.DatabaseSearchStrategy");

        try {
            new DbUserDAO(dbFactory, "tt_user", "com.topcoder.timetracker.user.User",
                "com.topcoder.search.builder.database.DatabaseSearchStrategy", auditManager, contactManager,
                authPersistence, addressManager, userStatusDAO, userTypeDAO, true);
            fail("ConfigurationException expected.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests DbUserDAO#addUsers(User[],boolean) for accuracy.
     * </p>
     *
     * <p>
     * It verifies DbUserDAO#addUsers(User[],boolean) is correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAddUsers1() throws Exception {
        User testingUser = TestHelper.createTestingUser(null);
        UserType userType = new UserType();
        userType.setDescription("description");
        userType.setCompanyId(5);
        userType.setCreationUser("test");
        userType.setModificationUser("test");
        userType.setCreationDate(new Date());
        userType.setModificationDate(new Date());
        testingUser.setUserType(userType);
        userTypeDAO.addUserTypes(new UserType[] {userType});

        UserStatus userStatus = new UserStatus();
        userStatus.setDescription("description");
        userStatus.setCompanyId(5);
        userStatus.setCreationUser("test");
        userStatus.setModificationUser("test");
        userStatus.setCreationDate(new Date());
        userStatus.setModificationDate(new Date());
        testingUser.setUserStatus(userStatus);
        userStatusDAO.addUserStatuses(new UserStatus[] {userStatus});

        dao.addUsers(new User[] {testingUser}, true);

        User actualUser = dao.getUsers(new long[] {testingUser.getId()})[0];

        TestHelper.assertUserEquals(testingUser, actualUser);
    }

    /**
     * <p>
     * Tests DbUserDAO#addUsers(User[],boolean) for accuracy.
     * </p>
     *
     * <p>
     * It verifies DbUserDAO#addUsers(User[],boolean) is correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAddUsers2() throws Exception {
        User testingUser = TestHelper.createTestingUser(null);
        UserType userType = new UserType();
        userType.setDescription("description");
        userType.setCompanyId(5);
        userType.setCreationUser("test");
        userType.setModificationUser("test");
        userType.setCreationDate(new Date());
        userType.setModificationDate(new Date());
        testingUser.setUserType(userType);
        userTypeDAO.addUserTypes(new UserType[] {userType});

        dao.addUsers(new User[] {testingUser}, true);

        User actualUser = dao.getUsers(new long[] {testingUser.getId()})[0];

        TestHelper.assertUserEquals(testingUser, actualUser);
    }

    /**
     * <p>
     * Tests DbUserDAO#addUsers(User[],boolean) for accuracy.
     * </p>
     *
     * <p>
     * It verifies DbUserDAO#addUsers(User[],boolean) is correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAddUsers3() throws Exception {
        User testingUser = TestHelper.createTestingUser(null);
        UserStatus userStatus = new UserStatus();
        userStatus.setDescription("description");
        userStatus.setCompanyId(5);
        userStatus.setCreationUser("test");
        userStatus.setModificationUser("test");
        userStatus.setCreationDate(new Date());
        userStatus.setModificationDate(new Date());
        testingUser.setUserStatus(userStatus);
        userStatusDAO.addUserStatuses(new UserStatus[] {userStatus});

        dao.addUsers(new User[] {testingUser}, true);

        User actualUser = dao.getUsers(new long[] {testingUser.getId()})[0];

        TestHelper.assertUserEquals(testingUser, actualUser);
    }

    /**
     * <p>
     * Tests DbUserDAO#addUsers(User[],boolean) for failure.
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
            dao.addUsers(null, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests DbUserDAO#addUsers(User[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when users is empty and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAddUsers_EmptyUsers() throws Exception {
        try {
            dao.addUsers(new User[0], true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests DbUserDAO#addUsers(User[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when some user has null user name and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAddUsers_UserWithNullUserName() throws Exception {
        User testingUser = TestHelper.createTestingUser("UserName");
        try {
            dao.addUsers(new User[] {testingUser}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests DbUserDAO#addUsers(User[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when some user has null password and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAddUsers_UserWithNullPassword() throws Exception {
        User testingUser = TestHelper.createTestingUser("Password");
        try {
            dao.addUsers(new User[] {testingUser}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests DbUserDAO#addUsers(User[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when some user has null status and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAddUsers_UserWithNullStatus() throws Exception {
        User testingUser = TestHelper.createTestingUser("Status");
        try {
            dao.addUsers(new User[] {testingUser}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests DbUserDAO#addUsers(User[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when some user has null address and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAddUsers_UserWithNullAddress() throws Exception {
        User testingUser = TestHelper.createTestingUser("Address");
        try {
            dao.addUsers(new User[] {testingUser}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests DbUserDAO#addUsers(User[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when some user has null contact and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAddUsers_UserWithNullContact() throws Exception {
        User testingUser = TestHelper.createTestingUser("Contact");
        try {
            dao.addUsers(new User[] {testingUser}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests DbUserDAO#addUsers(User[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when some user has null creation date and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAddUsers_UserWithNullCreationDate() throws Exception {
        User testingUser = TestHelper.createTestingUser("CreationDate");
        try {
            dao.addUsers(new User[] {testingUser}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests DbUserDAO#addUsers(User[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when some user has null creation user and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAddUsers_UserWithNullCreationUser() throws Exception {
        User testingUser = TestHelper.createTestingUser("CreationUser");
        try {
            dao.addUsers(new User[] {testingUser}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests DbUserDAO#addUsers(User[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when some user has null modification date and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAddUsers_UserWithNullModificationDate() throws Exception {
        User testingUser = TestHelper.createTestingUser("ModificationDate");
        try {
            dao.addUsers(new User[] {testingUser}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests DbUserDAO#addUsers(User[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when some user has null modification user and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAddUsers_UserWithNullModificationUser() throws Exception {
        User testingUser = TestHelper.createTestingUser("ModificationUser");
        try {
            dao.addUsers(new User[] {testingUser}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests DbUserDAO#addUsers(User[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when users contains null user and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAddUsers_NullUserInUsers() throws Exception {
        try {
            dao.addUsers(new User[] {null}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests DbUserDAO#addUsers(User[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case when the connection name is invalid and expects DataAccessException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAddUsers_InvalidConnectionName() throws Exception {
        dao =
            new DbUserDAO(dbFactory, "no_connection", "com.topcoder.timetracker.user.User",
                "com.topcoder.search.builder.database.DatabaseSearchStrategy", auditManager, contactManager,
                authPersistence, addressManager, userStatusDAO, userTypeDAO, true);

        try {
            dao.addUsers(new User[] {TestHelper.createTestingUser(null)}, true);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests DbUserDAO#updateUsers(User[],boolean) for accuracy.
     * </p>
     *
     * <p>
     * It verifies DbUserDAO#updateUsers(User[],boolean) is correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateUsers() throws Exception {
        User testingUser = TestHelper.createTestingUser(null);
        dao.addUsers(new User[] {testingUser}, true);

        testingUser.setUsername("helloWorld");
        testingUser.setStatus(Status.INACTIVE);

        dao.updateUsers(new User[] {testingUser}, true);

        User actualUser = dao.getUsers(new long[] {testingUser.getId()})[0];
        TestHelper.assertUserEquals(testingUser, actualUser);
    }

    /**
     * <p>
     * Tests DbUserDAO#updateUsers(User[],boolean) for failure.
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
            dao.updateUsers(null, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests DbUserDAO#updateUsers(User[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when users is empty and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateUsers_EmptyUsers() throws Exception {
        try {
            dao.updateUsers(new User[0], true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests DbUserDAO#updateUsers(User[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when some user has null user name and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateUsers_UserWithNullUserName() throws Exception {
        User testingUser = TestHelper.createTestingUser("UserName");
        try {
            dao.updateUsers(new User[] {testingUser}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests DbUserDAO#updateUsers(User[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when some user has null password and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateUsers_UserWithNullPassword() throws Exception {
        User testingUser = TestHelper.createTestingUser("Password");
        try {
            dao.updateUsers(new User[] {testingUser}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests DbUserDAO#updateUsers(User[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when some user has null status and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateUsers_UserWithNullStatus() throws Exception {
        User testingUser = TestHelper.createTestingUser("Status");
        try {
            dao.updateUsers(new User[] {testingUser}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests DbUserDAO#updateUsers(User[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when some user has null address and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateUsers_UserWithNullAddress() throws Exception {
        User testingUser = TestHelper.createTestingUser("Address");
        try {
            dao.updateUsers(new User[] {testingUser}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests DbUserDAO#updateUsers(User[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when some user has null contact and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateUsers_UserWithNullContact() throws Exception {
        User testingUser = TestHelper.createTestingUser("Contact");
        try {
            dao.updateUsers(new User[] {testingUser}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests DbUserDAO#updateUsers(User[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when some user has null creation date and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateUsers_UserWithNullCreationDate() throws Exception {
        User testingUser = TestHelper.createTestingUser("CreationDate");
        try {
            dao.updateUsers(new User[] {testingUser}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests DbUserDAO#updateUsers(User[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when some user has null creation user and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateUsers_UserWithNullCreationUser() throws Exception {
        User testingUser = TestHelper.createTestingUser("CreationUser");
        try {
            dao.updateUsers(new User[] {testingUser}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests DbUserDAO#updateUsers(User[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when some user has null modification date and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateUsers_UserWithNullModificationDate() throws Exception {
        User testingUser = TestHelper.createTestingUser("ModificationDate");
        try {
            dao.updateUsers(new User[] {testingUser}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests DbUserDAO#updateUsers(User[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when some user has null modification user and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateUsers_UserWithNullModificationUser() throws Exception {
        User testingUser = TestHelper.createTestingUser("ModificationUser");
        try {
            dao.updateUsers(new User[] {testingUser}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests DbUserDAO#updateUsers(User[],boolean) for failure.
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
    public void testUpdateUsers_UnrecognizedEntityException() throws Exception {
        User testingUser = TestHelper.createTestingUser(null);
        testingUser.setId(4000);
        try {
            dao.updateUsers(new User[] {testingUser}, true);
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
     * Tests DbUserDAO#updateUsers(User[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case when the connection name is invalid and expects DataAccessException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateUsers_InvalidConnectionName() throws Exception {
        dao =
            new DbUserDAO(dbFactory, "no_connection", "com.topcoder.timetracker.user.User",
                "com.topcoder.search.builder.database.DatabaseSearchStrategy", auditManager, contactManager,
                authPersistence, addressManager, userStatusDAO, userTypeDAO, true);

        User testingUser = TestHelper.createTestingUser(null);
        testingUser.setId(1);
        try {
            dao.updateUsers(new User[] {testingUser}, true);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests DbUserDAO#removeUsers(long[],boolean) for accuracy.
     * </p>
     *
     * <p>
     * It verifies DbUserDAO#removeUsers(long[],boolean) is correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRemoveUsers() throws Exception {
        User testingUser = TestHelper.createTestingUser(null);
        dao.addUsers(new User[] {testingUser}, true);

        dao.removeUsers(new long[] {1, testingUser.getId()}, true);

        User[] users = dao.getAllUsers();
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
     * Tests DbUserDAO#removeUsers(long[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case when the connection name is invalid and expects DataAccessException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRemoveUsers_InvalidConnectionName() throws Exception {
        dao =
            new DbUserDAO(dbFactory, "no_connection", "com.topcoder.timetracker.user.User",
                "com.topcoder.search.builder.database.DatabaseSearchStrategy", auditManager, contactManager,
                authPersistence, addressManager, userStatusDAO, userTypeDAO, true);

        try {
            dao.removeUsers(new long[] {1}, true);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests DbUserDAO#removeUsers(long[],boolean) for failure.
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
            dao.removeUsers(null, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests DbUserDAO#removeUsers(long[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when userIds is empty and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRemoveUsers_EmptyUserIds() throws Exception {
        try {
            dao.removeUsers(new long[0], true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests DbUserDAO#removeUsers(long[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when some user id is negative and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRemoveUsers_NegativeUserId() throws Exception {
        try {
            dao.removeUsers(new long[] {1, -8}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests DbUserDAO#removeUsers(long[],boolean) for failure.
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
    public void testRemoveUsers_UnrecognizedEntityException() throws Exception {
        try {
            dao.removeUsers(new long[] {1, 4342424}, true);
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
     * Tests DbUserDAO#getUsers(long[]) for accuracy.
     * </p>
     *
     * <p>
     * It verifies DbUserDAO#getUsers(long[]) is correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetUsers() throws Exception {
        User user = dao.getUsers(new long[] {1})[0];

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
     * Tests DbUserDAO#getUsers(long[]) for failure.
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
            dao.getUsers(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests DbUserDAO#getUsers(long[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when userIds is empty and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetUsers_EmptyUserIds() throws Exception {
        try {
            dao.getUsers(new long[0]);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests DbUserDAO#getUsers(long[]) for failure.
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
            dao.getUsers(new long[] {1, -8});
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests DbUserDAO#getUsers(long[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case when the connection name is invalid and expects DataAccessException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetUsers_InvalidConnectionName() throws Exception {
        dao =
            new DbUserDAO(dbFactory, "no_connection", "com.topcoder.timetracker.user.User",
                "com.topcoder.search.builder.database.DatabaseSearchStrategy", auditManager, contactManager,
                authPersistence, addressManager, userStatusDAO, userTypeDAO, true);

        try {
            dao.getUsers(new long[] {1});
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests DbUserDAO#searchUsers(Filter) for accuracy.
     * </p>
     *
     * <p>
     * It verifies DbUserDAO#searchUsers(Filter) is correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSearchUsers() throws Exception {
        Filter userNameFilter =
            dao.getUserFilterFactory().createUsernameFilter(StringMatchType.EXACT_MATCH, "admin");
        User[] users = dao.searchUsers(userNameFilter);

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
     * Tests DbUserDAO#searchUsers(Filter) for failure.
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
            dao.searchUsers(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests DbUserDAO#searchUsers(Filter) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when filter is null and expects DataAccessException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSearchUsers_InvalidFilter() throws Exception {
        try {
            dao.searchUsers(new EqualToFilter("no_column", "value"));
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests DbUserDAO#getAllUsers() for accuracy.
     * </p>
     *
     * <p>
     * It verifies DbUserDAO#getAllUsers() is correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetAllUsers() throws Exception {
        User[] users = dao.getAllUsers();

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
     * Tests DbUserDAO#getAllUsers() for failure.
     * </p>
     *
     * <p>
     * It tests the case when the connection name is invalid and expects DataAccessException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetAllUsers_InvalidConnectionName() throws Exception {
        dao =
            new DbUserDAO(dbFactory, "no_connection", "com.topcoder.timetracker.user.User",
                "com.topcoder.search.builder.database.DatabaseSearchStrategy", auditManager, contactManager,
                authPersistence, addressManager, userStatusDAO, userTypeDAO, true);

        try {
            dao.getAllUsers();
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests DbUserDAO#getUserFilterFactory() for accuracy.
     * </p>
     *
     * <p>
     * It verifies DbUserDAO#getUserFilterFactory() is correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetUserFilterFactory() throws Exception {
        UserFilterFactory factory = dao.getUserFilterFactory();

        assertEquals("Failed to get the user filter factory.", MappedUserFilterFactory.class, factory
            .getClass());
    }
}