/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user.failuretests;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.timetracker.user.ConfigurationException;
import com.topcoder.timetracker.user.UserStatus;
import com.topcoder.timetracker.user.db.DbUserStatusDAO;

import junit.framework.TestCase;

/**
 * <p>
 * Failure test for <code>{@link DbUserStatusDAO}</code> class.
 * </p>
 *
 * @author FireIce
 * @version 1.0
 */
public class DbUserStatusDAOFailureTests extends TestCase {

    /** Unit under test. */
    private DbUserStatusDAO dbUserStatusDAO;

    /**
     * <p>
     * The DBConnectionFactory instance for testing.
     * </p>
     */
    private DBConnectionFactory dbFactory;

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
        FailureTestHelper.loadXMLConfig(FailureTestHelper.CONFIG_FILE);
        FailureTestHelper.setUpDataBase();

        dbFactory = new DBConnectionFactoryImpl();

        dbUserStatusDAO = new DbUserStatusDAO(dbFactory, "tt_user", "com.topcoder.timetracker.user.UserStatus",
                "com.topcoder.search.builder", "userStatusSearchBundle");
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
     * <code>{@link DbUserStatusDAO#DbUserStatusDAO(DBConnectionFactory, String, String, String, String)}</code>
     * method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDbUserStatusDAO_NullConnectionFactory() throws Exception {
        try {
            new DbUserStatusDAO(null, "tt_user", "com.topcoder.timetracker.user.UserStatus",
                    "com.topcoder.search.builder", "userStatusSearchBundle");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link DbUserStatusDAO#DbUserStatusDAO(DBConnectionFactory, String, String, String, String)}</code>
     * method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDbUserStatusDAO_EmptyConnectionName() throws Exception {
        try {
            new DbUserStatusDAO(dbFactory, "", "com.topcoder.timetracker.user.UserStatus",
                    "com.topcoder.search.builder", "userStatusSearchBundle");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link DbUserStatusDAO#DbUserStatusDAO(DBConnectionFactory, String, String, String, String)}</code>
     * method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDbUserStatusDAO_TrimmedEmptyConnectionName() throws Exception {
        try {
            new DbUserStatusDAO(dbFactory, " ", "com.topcoder.timetracker.user.UserStatus",
                    "com.topcoder.search.builder", "userStatusSearchBundle");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link DbUserStatusDAO#DbUserStatusDAO(DBConnectionFactory, String, String, String, String)}</code>
     * method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDbUserStatusDAO_NullIDGen() throws Exception {
        try {
            new DbUserStatusDAO(dbFactory, "tt_user", null, "com.topcoder.search.builder", "userStatusSearchBundle");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link DbUserStatusDAO#DbUserStatusDAO(DBConnectionFactory, String, String, String, String)}</code>
     * method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDbUserStatusDAO_EmptyIDGen() throws Exception {
        try {
            new DbUserStatusDAO(dbFactory, "tt_user", "", "com.topcoder.search.builder", "userStatusSearchBundle");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link DbUserStatusDAO#DbUserStatusDAO(DBConnectionFactory, String, String, String, String)}</code>
     * method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDbUserStatusDAO_TrimmedEmptyIDGen() throws Exception {
        try {
            new DbUserStatusDAO(dbFactory, "tt_user", " ", "com.topcoder.search.builder", "userStatusSearchBundle");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link DbUserStatusDAO#DbUserStatusDAO(DBConnectionFactory, String, String, String, String)}</code>
     * method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDbUserStatusDAO_NotFoundIDGen() throws Exception {
        try {
            new DbUserStatusDAO(dbFactory, "tt_user", "NotExist", "com.topcoder.search.builder",
                    "userStatusSearchBundle");
            fail("expect throw ConfigurationException.");
        } catch (ConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link DbUserStatusDAO#DbUserStatusDAO(DBConnectionFactory, String, String, String, String)}</code>
     * method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDbUserStatusDAO_NullSearchBundleManagerNamespace() throws Exception {
        try {
            new DbUserStatusDAO(dbFactory, "tt_user", "com.topcoder.timetracker.user.UserStatus", null,
                    "userStatusSearchBundle");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link DbUserStatusDAO#DbUserStatusDAO(DBConnectionFactory, String, String, String, String)}</code>
     * method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDbUserStatusDAO_EmptySearchBundleManagerNamespace() throws Exception {
        try {
            new DbUserStatusDAO(dbFactory, "tt_user", "com.topcoder.timetracker.user.UserStatus", "",
                    "userStatusSearchBundle");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link DbUserStatusDAO#DbUserStatusDAO(DBConnectionFactory, String, String, String, String)}</code>
     * method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDbUserStatusDAO_TrimmedEmptySearchBundleManagerNamespace() throws Exception {
        try {
            new DbUserStatusDAO(dbFactory, "tt_user", "com.topcoder.timetracker.user.UserStatus", " ",
                    "userStatusSearchBundle");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link DbUserStatusDAO#DbUserStatusDAO(DBConnectionFactory, String, String, String, String)}</code>
     * method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDbUserStatusDAO_NullSearchBundleName() throws Exception {
        try {
            new DbUserStatusDAO(dbFactory, "tt_user", "com.topcoder.timetracker.user.UserStatus",
                    "com.topcoder.search.builder", null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link DbUserStatusDAO#DbUserStatusDAO(DBConnectionFactory, String, String, String, String)}</code>
     * method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDbUserStatusDAO_EmptySearchBundleName() throws Exception {
        try {
            new DbUserStatusDAO(dbFactory, "tt_user", "com.topcoder.timetracker.user.UserStatus",
                    "com.topcoder.search.builder", "");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link DbUserStatusDAO#DbUserStatusDAO(DBConnectionFactory, String, String, String, String)}</code>
     * method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDbUserStatusDAO_TrimmedEmptySearchBundleName() throws Exception {
        try {
            new DbUserStatusDAO(dbFactory, "tt_user", "com.topcoder.timetracker.user.UserStatus",
                    "com.topcoder.search.builder", " ");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DbUserStatusDAO#addUserStatuses(UserStatus[])}</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testAddUserStatuses_NullArray() throws Exception {
        try {
            dbUserStatusDAO.addUserStatuses(null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DbUserStatusDAO#addUserStatuses(UserStatus[])}</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testAddUserStatuses_ArrayContainsNull() throws Exception {
        try {
            dbUserStatusDAO.addUserStatuses(new UserStatus[] {null});
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DbUserStatusDAO#updateUserStatuses(UserStatus[])}</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateUserStatuses_NullArray() throws Exception {
        try {
            dbUserStatusDAO.updateUserStatuses(null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DbUserStatusDAO#updateUserStatuses(UserStatus[])}</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateUserStatuses_ArrayContainsNull() throws Exception {
        try {
            dbUserStatusDAO.updateUserStatuses(new UserStatus[] {null});
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DbUserStatusDAO#removeUserStatuses(long[])}</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testRemoveUserStatuses_NullArray() throws Exception {
        try {
            dbUserStatusDAO.removeUserStatuses(null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DbUserStatusDAO#removeUserStatuses(long[])}</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testRemoveUserStatuses_ArrayContainsNonPositive() throws Exception {
        try {
            dbUserStatusDAO.removeUserStatuses(new long[] {-1});
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DbUserStatusDAO#removeUserStatuses(long[])}</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testRemoveUserStatuses_ArrayContainsNonPositive1() throws Exception {
        try {
            dbUserStatusDAO.removeUserStatuses(new long[] {0});
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DbUserStatusDAO#getUserStatuses(long[])}</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetUserStatuses_NullArray() throws Exception {
        try {
            dbUserStatusDAO.getUserStatuses(null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DbUserStatusDAO#getUserStatuses(long[])}</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetUserStatuses_ArrayContainsNonPositive() throws Exception {
        try {
            dbUserStatusDAO.getUserStatuses(new long[] {-1});
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DbUserStatusDAO#getUserStatuses(long[])}</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetUserStatuses_ArrayContainsNonPositive1() throws Exception {
        try {
            dbUserStatusDAO.getUserStatuses(new long[] {0});
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link DbUserStatusDAO#searchUserStatuses(com.topcoder.search.builder.filter.Filter)}</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testSearchFilter_NullFilter() throws Exception {
        try {
            dbUserStatusDAO.searchUserStatuses(null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
