/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user.failuretests;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.timetracker.user.ConfigurationException;
import com.topcoder.timetracker.user.UserType;
import com.topcoder.timetracker.user.db.DbUserTypeDAO;

import junit.framework.TestCase;

/**
 * <p>
 * Failure test for <code>{@link DbUserTypeDAO}</code> class.
 * </p>
 *
 * @author FireIce
 * @version 1.0
 */
public class DbUserTypeDAOFailureTests extends TestCase {

    /** Unit under test. */
    private DbUserTypeDAO dbUserTypeDAO;

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

        dbUserTypeDAO = new DbUserTypeDAO(dbFactory, "tt_user", "com.topcoder.timetracker.user.UserType",
                "com.topcoder.search.builder", "userTypeSearchBundle");
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
     * <code>{@link DbUserTypeDAO#DbUserTypeDAO(DBConnectionFactory, String, String, String, String)}</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDbUserTypeDAO_NullConnectionFactory() throws Exception {
        try {
            new DbUserTypeDAO(null, "tt_user", "com.topcoder.timetracker.user.UserType", "com.topcoder.search.builder",
                    "userTypeSearchBundle");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link DbUserTypeDAO#DbUserTypeDAO(DBConnectionFactory, String, String, String, String)}</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDbUserTypeDAO_EmptyConnectionName() throws Exception {
        try {
            new DbUserTypeDAO(dbFactory, "", "com.topcoder.timetracker.user.UserType", "com.topcoder.search.builder",
                    "userTypeSearchBundle");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link DbUserTypeDAO#DbUserTypeDAO(DBConnectionFactory, String, String, String, String)}</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDbUserTypeDAO_TrimmedEmptyConnectionName() throws Exception {
        try {
            new DbUserTypeDAO(dbFactory, " ", "com.topcoder.timetracker.user.UserType", "com.topcoder.search.builder",
                    "userTypeSearchBundle");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link DbUserTypeDAO#DbUserTypeDAO(DBConnectionFactory, String, String, String, String)}</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDbUserTypeDAO_NullIDGen() throws Exception {
        try {
            new DbUserTypeDAO(dbFactory, "tt_user", null, "com.topcoder.search.builder", "userTypeSearchBundle");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link DbUserTypeDAO#DbUserTypeDAO(DBConnectionFactory, String, String, String, String)}</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDbUserTypeDAO_EmptyIDGen() throws Exception {
        try {
            new DbUserTypeDAO(dbFactory, "tt_user", "", "com.topcoder.search.builder", "userTypeSearchBundle");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link DbUserTypeDAO#DbUserTypeDAO(DBConnectionFactory, String, String, String, String)}</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDbUserTypeDAO_TrimmedEmptyIDGen() throws Exception {
        try {
            new DbUserTypeDAO(dbFactory, "tt_user", " ", "com.topcoder.search.builder", "userTypeSearchBundle");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link DbUserTypeDAO#DbUserTypeDAO(DBConnectionFactory, String, String, String, String)}</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDbUserTypeDAO_NotFoundIDGen() throws Exception {
        try {
            new DbUserTypeDAO(dbFactory, "tt_user", "NotExist", "com.topcoder.search.builder", "userTypeSearchBundle");
            fail("expect throw ConfigurationException.");
        } catch (ConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link DbUserTypeDAO#DbUserTypeDAO(DBConnectionFactory, String, String, String, String)}</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDbUserTypeDAO_NullSearchBundleManagerNamespace() throws Exception {
        try {
            new DbUserTypeDAO(dbFactory, "tt_user", "com.topcoder.timetracker.user.UserType", null,
                    "userTypeSearchBundle");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link DbUserTypeDAO#DbUserTypeDAO(DBConnectionFactory, String, String, String, String)}</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDbUserTypeDAO_EmptySearchBundleManagerNamespace() throws Exception {
        try {
            new DbUserTypeDAO(dbFactory, "tt_user", "com.topcoder.timetracker.user.UserType", "",
                    "userTypeSearchBundle");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link DbUserTypeDAO#DbUserTypeDAO(DBConnectionFactory, String, String, String, String)}</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDbUserTypeDAO_TrimmedEmptySearchBundleManagerNamespace() throws Exception {
        try {
            new DbUserTypeDAO(dbFactory, "tt_user", "com.topcoder.timetracker.user.UserType", " ",
                    "userTypeSearchBundle");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link DbUserTypeDAO#DbUserTypeDAO(DBConnectionFactory, String, String, String, String)}</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDbUserTypeDAO_NullSearchBundleName() throws Exception {
        try {
            new DbUserTypeDAO(dbFactory, "tt_user", "com.topcoder.timetracker.user.UserType",
                    "com.topcoder.search.builder", null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link DbUserTypeDAO#DbUserTypeDAO(DBConnectionFactory, String, String, String, String)}</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDbUserTypeDAO_EmptySearchBundleName() throws Exception {
        try {
            new DbUserTypeDAO(dbFactory, "tt_user", "com.topcoder.timetracker.user.UserType",
                    "com.topcoder.search.builder", "");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for
     * <code>{@link DbUserTypeDAO#DbUserTypeDAO(DBConnectionFactory, String, String, String, String)}</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDbUserTypeDAO_TrimmedEmptySearchBundleName() throws Exception {
        try {
            new DbUserTypeDAO(dbFactory, "tt_user", "com.topcoder.timetracker.user.UserType",
                    "com.topcoder.search.builder", " ");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DbUserTypeDAO#addUserTypees(UserType[])}</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testAddUserTypees_NullArray() throws Exception {
        try {
            dbUserTypeDAO.addUserTypes(null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DbUserTypeDAO#addUserTypees(UserType[])}</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testAddUserTypees_ArrayContainsNull() throws Exception {
        try {
            dbUserTypeDAO.addUserTypes(new UserType[] {null});
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DbUserTypeDAO#updateUserTypees(UserType[])}</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateUserTypees_NullArray() throws Exception {
        try {
            dbUserTypeDAO.updateUserTypes(null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DbUserTypeDAO#updateUserTypees(UserType[])}</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateUserTypees_ArrayContainsNull() throws Exception {
        try {
            dbUserTypeDAO.updateUserTypes(new UserType[] {null});
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DbUserTypeDAO#removeUserTypees(long[])}</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testRemoveUserTypees_NullArray() throws Exception {
        try {
            dbUserTypeDAO.removeUserTypes(null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DbUserTypeDAO#removeUserTypees(long[])}</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testRemoveUserTypees_ArrayContainsNonPositive() throws Exception {
        try {
            dbUserTypeDAO.removeUserTypes(new long[] {-1});
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DbUserTypeDAO#removeUserTypees(long[])}</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testRemoveUserTypees_ArrayContainsNonPositive1() throws Exception {
        try {
            dbUserTypeDAO.removeUserTypes(new long[] {0});
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DbUserTypeDAO#getUserTypees(long[])}</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetUserTypees_NullArray() throws Exception {
        try {
            dbUserTypeDAO.getUserTypes(null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DbUserTypeDAO#getUserTypees(long[])}</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetUserTypees_ArrayContainsNonPositive() throws Exception {
        try {
            dbUserTypeDAO.getUserTypes(new long[] {-1});
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DbUserTypeDAO#getUserTypees(long[])}</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetUserTypees_ArrayContainsNonPositive1() throws Exception {
        try {
            dbUserTypeDAO.getUserTypes(new long[] {0});
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link DbUserTypeDAO#searchUserTypees(com.topcoder.search.builder.filter.Filter)}</code>
     * method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testSearchFilter_NullFilter() throws Exception {
        try {
            dbUserTypeDAO.searchUserTypes(null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
