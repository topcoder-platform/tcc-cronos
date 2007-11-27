/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user.failuretests;

import org.jmock.MockObjectTestCase;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.security.authorization.AuthorizationPersistence;
import com.topcoder.security.authorization.persistence.SQLAuthorizationPersistence;
import com.topcoder.timetracker.audit.AuditManager;
import com.topcoder.timetracker.contact.AddressManager;
import com.topcoder.timetracker.contact.ContactManager;
import com.topcoder.timetracker.user.ConfigurationException;
import com.topcoder.timetracker.user.User;
import com.topcoder.timetracker.user.UserDAO;
import com.topcoder.timetracker.user.UserStatusDAO;
import com.topcoder.timetracker.user.UserTypeDAO;
import com.topcoder.timetracker.user.db.DbUserDAO;
import com.topcoder.util.config.ConfigManager;

/**
 * <p>
 * Failure test for <code>{@link DbUserDAO}</code> class.
 * </p>
 *
 * @author FireIce
 * @version 1.0
 */
public class DbUserDAOFailureTests extends MockObjectTestCase {

    /**
     * <p>
     * Represents the DbUserDAO instance.
     * </p>
     */
    private UserDAO userDAO;

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
     * The DBConnectionFactory instance for testing.
     * </p>
     */
    private DBConnectionFactory dbFactory;

    /**
     * <p>
     * Represents the UserStatusDAO instance for testing.
     * </p>
     */
    private UserStatusDAO userStatusDAO;

    /**
     * <p>
     * Represents the UserTypeDAO instance for testing.
     * </p>
     */
    private UserTypeDAO userTypeDAO;

    /**
     * <p>
     * Setup the testing environment.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();

        FailureTestHelper.clearNamespaces();
        ConfigManager.getInstance().add(FailureTestHelper.FAILURE_CONFIG_ROOT + "DbUserDAO.xml");
        FailureTestHelper.setUpDataBase();

        dbFactory = new DBConnectionFactoryImpl();
        auditManager = (AuditManager) mock(AuditManager.class).proxy();
        contactManager = (ContactManager) mock(ContactManager.class).proxy();
        authPersistence = new SQLAuthorizationPersistence("com.topcoder.timetracker.application.authorization");
        addressManager = (AddressManager) mock(AddressManager.class).proxy();
        userStatusDAO = (UserStatusDAO) mock(UserStatusDAO.class).proxy();
        userTypeDAO = (UserTypeDAO) mock(UserTypeDAO.class).proxy();

        userDAO = new DbUserDAO(dbFactory, "tt_user", "com.topcoder.timetracker.user.User",
                "com.topcoder.search.builder.database.DatabaseSearchStrategy", auditManager, contactManager,
                authPersistence, addressManager, userStatusDAO, userTypeDAO, true);
    }

    /**
     * <p>
     * Tear down the testing environment.
     * </p>
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        FailureTestHelper.tearDownDataBase();
        FailureTestHelper.clearNamespaces();
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link DbUserDAO#DbUserDAO(DBConnectionFactory, String, String, String, AuditManager, ContactManager, AuthorizationPersistence, AddressManager, UserStatusDAO, UserTypeDAO, boolean)}</code>
     * method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDbUserDAO_NullConnectionFactory() throws Exception {
        try {
            new DbUserDAO(null, "tt_user", "com.topcoder.timetracker.user.User",
                    "com.topcoder.search.builder.database.DatabaseSearchStrategy", auditManager, contactManager,
                    authPersistence, addressManager, userStatusDAO, userTypeDAO, true);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link DbUserDAO#DbUserDAO(DBConnectionFactory, String, String, String, AuditManager, ContactManager, AuthorizationPersistence, AddressManager, UserStatusDAO, UserTypeDAO, boolean)}</code>
     * method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDbUserDAO_EmptyConnectionName() throws Exception {
        try {
            new DbUserDAO(dbFactory, "", "com.topcoder.timetracker.user.User",
                    "com.topcoder.search.builder.database.DatabaseSearchStrategy", auditManager, contactManager,
                    authPersistence, addressManager, userStatusDAO, userTypeDAO, true);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link DbUserDAO#DbUserDAO(DBConnectionFactory, String, String, String, AuditManager, ContactManager, AuthorizationPersistence, AddressManager, UserStatusDAO, UserTypeDAO, boolean)}</code>
     * method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDbUserDAO_TrimmedEmptyConnectionName() throws Exception {
        try {
            new DbUserDAO(dbFactory, "  ", "com.topcoder.timetracker.user.User",
                    "com.topcoder.search.builder.database.DatabaseSearchStrategy", auditManager, contactManager,
                    authPersistence, addressManager, userStatusDAO, userTypeDAO, true);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link DbUserDAO#DbUserDAO(DBConnectionFactory, String, String, String, AuditManager, ContactManager, AuthorizationPersistence, AddressManager, UserStatusDAO, UserTypeDAO, boolean)}</code>
     * method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDbUserDAO_NullIDGen() throws Exception {
        try {
            new DbUserDAO(dbFactory, null, null, "com.topcoder.search.builder.database.DatabaseSearchStrategy",
                    auditManager, contactManager, authPersistence, addressManager, userStatusDAO, userTypeDAO, true);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link DbUserDAO#DbUserDAO(DBConnectionFactory, String, String, String, AuditManager, ContactManager, AuthorizationPersistence, AddressManager, UserStatusDAO, UserTypeDAO, boolean)}</code>
     * method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDbUserDAO_EmptyIDGen() throws Exception {
        try {
            new DbUserDAO(dbFactory, null, "", "com.topcoder.search.builder.database.DatabaseSearchStrategy",
                    auditManager, contactManager, authPersistence, addressManager, userStatusDAO, userTypeDAO, true);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link DbUserDAO#DbUserDAO(DBConnectionFactory, String, String, String, AuditManager, ContactManager, AuthorizationPersistence, AddressManager, UserStatusDAO, UserTypeDAO, boolean)}</code>
     * method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDbUserDAO_TrimmedEmptyIDGen() throws Exception {
        try {
            new DbUserDAO(dbFactory, null, " ", "com.topcoder.search.builder.database.DatabaseSearchStrategy",
                    auditManager, contactManager, authPersistence, addressManager, userStatusDAO, userTypeDAO, true);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link DbUserDAO#DbUserDAO(DBConnectionFactory, String, String, String, AuditManager, ContactManager, AuthorizationPersistence, AddressManager, UserStatusDAO, UserTypeDAO, boolean)}</code>
     * method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDbUserDAO_NotFoundIDGen() throws Exception {
        try {
            new DbUserDAO(dbFactory, null, "NotExist", "com.topcoder.search.builder.database.DatabaseSearchStrategy",
                    auditManager, contactManager, authPersistence, addressManager, userStatusDAO, userTypeDAO, true);
            fail("expect throw ConfigurationException.");
        } catch (ConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link DbUserDAO#DbUserDAO(DBConnectionFactory, String, String, String, AuditManager, ContactManager, AuthorizationPersistence, AddressManager, UserStatusDAO, UserTypeDAO, boolean)}</code>
     * method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDbUserDAO_NullSearchsStrategyNamespace() throws Exception {
        try {
            new DbUserDAO(dbFactory, null, "com.topcoder.timetracker.user.User", null, auditManager, contactManager,
                    authPersistence, addressManager, userStatusDAO, userTypeDAO, true);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link DbUserDAO#DbUserDAO(DBConnectionFactory, String, String, String, AuditManager, ContactManager, AuthorizationPersistence, AddressManager, UserStatusDAO, UserTypeDAO, boolean)}</code>
     * method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDbUserDAO_EmptySearchsStrategyNamespace() throws Exception {
        try {
            new DbUserDAO(dbFactory, null, "com.topcoder.timetracker.user.User", "", auditManager, contactManager,
                    authPersistence, addressManager, userStatusDAO, userTypeDAO, true);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link DbUserDAO#DbUserDAO(DBConnectionFactory, String, String, String, AuditManager, ContactManager, AuthorizationPersistence, AddressManager, UserStatusDAO, UserTypeDAO, boolean)}</code>
     * method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDbUserDAO_TrimmedEmptySearchsStrategyNamespace() throws Exception {
        try {
            new DbUserDAO(dbFactory, null, "com.topcoder.timetracker.user.User", " ", auditManager, contactManager,
                    authPersistence, addressManager, userStatusDAO, userTypeDAO, true);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link DbUserDAO#DbUserDAO(DBConnectionFactory, String, String, String, AuditManager, ContactManager, AuthorizationPersistence, AddressManager, UserStatusDAO, UserTypeDAO, boolean)}</code>
     * method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDbUserDAO_NotFoundSearchsStrategyNamespace() throws Exception {
        try {
            new DbUserDAO(dbFactory, null, "com.topcoder.timetracker.user.User", "NotFound", auditManager,
                    contactManager, authPersistence, addressManager, userStatusDAO, userTypeDAO, true);
            fail("expect throw ConfigurationException.");
        } catch (ConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link DbUserDAO#DbUserDAO(DBConnectionFactory, String, String, String, AuditManager, ContactManager, AuthorizationPersistence, AddressManager, UserStatusDAO, UserTypeDAO, boolean)}</code>
     * method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDbUserDAO_NullAuditManager() throws Exception {
        try {
            new DbUserDAO(dbFactory, null, "com.topcoder.timetracker.user.User",
                    "com.topcoder.search.builder.database.DatabaseSearchStrategy", null, contactManager,
                    authPersistence, addressManager, userStatusDAO, userTypeDAO, true);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link DbUserDAO#DbUserDAO(DBConnectionFactory, String, String, String, AuditManager, ContactManager, AuthorizationPersistence, AddressManager, UserStatusDAO, UserTypeDAO, boolean)}</code>
     * method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDbUserDAO_NullContactManager() throws Exception {
        try {
            new DbUserDAO(dbFactory, null, "com.topcoder.timetracker.user.User",
                    "com.topcoder.search.builder.database.DatabaseSearchStrategy", auditManager, null, authPersistence,
                    addressManager, userStatusDAO, userTypeDAO, true);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link DbUserDAO#DbUserDAO(DBConnectionFactory, String, String, String, AuditManager, ContactManager, AuthorizationPersistence, AddressManager, UserStatusDAO, UserTypeDAO, boolean)}</code>
     * method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDbUserDAO_NullAuthPersitence() throws Exception {
        try {
            new DbUserDAO(dbFactory, null, "com.topcoder.timetracker.user.User",
                    "com.topcoder.search.builder.database.DatabaseSearchStrategy", auditManager, contactManager, null,
                    addressManager, userStatusDAO, userTypeDAO, true);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link DbUserDAO#DbUserDAO(DBConnectionFactory, String, String, String, AuditManager, ContactManager, AuthorizationPersistence, AddressManager, UserStatusDAO, UserTypeDAO, boolean)}</code>
     * method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDbUserDAO_NullAddressManager() throws Exception {
        try {
            new DbUserDAO(dbFactory, null, "com.topcoder.timetracker.user.User",
                    "com.topcoder.search.builder.database.DatabaseSearchStrategy", auditManager, contactManager,
                    authPersistence, null, userStatusDAO, userTypeDAO, true);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link DbUserDAO#DbUserDAO(DBConnectionFactory, String, String, String, AuditManager, ContactManager, AuthorizationPersistence, AddressManager, UserStatusDAO, UserTypeDAO, boolean)}</code>
     * method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDbUserDAO_NullUserStatusDAO() throws Exception {
        try {
            new DbUserDAO(dbFactory, null, "com.topcoder.timetracker.user.User",
                    "com.topcoder.search.builder.database.DatabaseSearchStrategy", auditManager, contactManager,
                    authPersistence, addressManager, null, userTypeDAO, true);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link DbUserDAO#DbUserDAO(DBConnectionFactory, String, String, String, AuditManager, ContactManager, AuthorizationPersistence, AddressManager, UserStatusDAO, UserTypeDAO, boolean)}</code>
     * method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDbUserDAO_NullUserTypeDAO() throws Exception {
        try {
            new DbUserDAO(dbFactory, null, "com.topcoder.timetracker.user.User",
                    "com.topcoder.search.builder.database.DatabaseSearchStrategy", auditManager, contactManager,
                    authPersistence, addressManager, userStatusDAO, null, true);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DbUserDAO#addUsers(User[], boolean)}</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testAddUsers_NullUsers() throws Exception {
        try {
            userDAO.addUsers(null, false);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DbUserDAO#addUsers(User[], boolean)}</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testAddUsers_UsersContainsNull() throws Exception {
        try {
            userDAO.addUsers(new User[] {null}, false);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DbUserDAO#addUsers(User[], boolean)}</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testAddUsers_UsersContainsInvalidUser() throws Exception {
        User user = new User();
        try {
            userDAO.addUsers(new User[] {user}, false);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DbUserDAO#updateUsers(User[], boolean)}</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateUsers_NullUsers() throws Exception {
        try {
            userDAO.updateUsers(null, false);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DbUserDAO#updateUsers(User[], boolean)}</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateUsers_UsersContainsNull() throws Exception {
        try {
            userDAO.updateUsers(new User[] {null}, false);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DbUserDAO#updateUsers(User[], boolean)}</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateUsers_UsersContainsInvalidUser() throws Exception {
        User user = new User();
        try {
            userDAO.updateUsers(new User[] {user}, false);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DbUserDAO#removeUsers(long[], boolean)}</code> method.
     * </p>
     *
     * @throws Exception
     */
    public void testRemoveUsers_NullUserIds() throws Exception {
        try {
            userDAO.removeUsers(null, false);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DbUserDAO#removeUsers(long[], boolean)}</code> method.
     * </p>
     *
     * @throws Exception
     */
    public void testRemoveUsers_ZeroUserId() throws Exception {
        try {
            userDAO.removeUsers(new long[] {0}, false);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DbUserDAO#removeUsers(long[], boolean)}</code> method.
     * </p>
     *
     * @throws Exception
     */
    public void testRemoveUsers_NegativeUserId() throws Exception {
        try {
            userDAO.removeUsers(new long[] {-1}, false);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DbUserDAO#getUsers(long[], boolean)}</code> method.
     * </p>
     *
     * @throws Exception
     */
    public void testGetUsers_NullUserIds() throws Exception {
        try {
            userDAO.getUsers(null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DbUserDAO#getUsers(long[], boolean)}</code> method.
     * </p>
     *
     * @throws Exception
     */
    public void testGetUsers_ZeroUserId() throws Exception {
        try {
            userDAO.getUsers(new long[] {0});
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DbUserDAO#getUsers(long[], boolean)}</code> method.
     * </p>
     *
     * @throws Exception
     */
    public void testGetUsers_NegativeUserId() throws Exception {
        try {
            userDAO.getUsers(new long[] {-1});
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DbUserDAO#searchUsers(com.topcoder.search.builder.filter.Filter)}</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testSearchUsers_NullFilter() throws Exception {
        try {
            userDAO.searchUsers(null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }

    }
}
