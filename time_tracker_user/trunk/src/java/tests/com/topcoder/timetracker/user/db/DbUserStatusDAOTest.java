/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user.db;

import java.sql.Connection;
import java.sql.Statement;
import java.util.Date;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.timetracker.user.BatchOperationException;
import com.topcoder.timetracker.user.DataAccessException;
import com.topcoder.timetracker.user.TestHelper;
import com.topcoder.timetracker.user.UnrecognizedEntityException;
import com.topcoder.timetracker.user.UserStatus;
import com.topcoder.timetracker.user.filterfactory.UserStatusFilterFactory;
import com.topcoder.util.idgenerator.IDGenerationException;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for <code>DbUserStatusDAO</code>.
 *
 * @author enefem21
 * @version 3.2.1
 * @since 3.2.1
 */
public class DbUserStatusDAOTest extends TestCase {

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
     * Returns the suite for this unit test.
     * </p>
     *
     * @return the suite
     */
    public static Test suite() {
        return new TestSuite(DbUserStatusDAOTest.class);
    }

    /**
     * Sets the unit test up.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();
        TestHelper.loadXMLConfig(TestHelper.CONFIG_FILE);
        TestHelper.loadXMLConfig(TestHelper.CONFIG_FILE_3_2_1);
        TestHelper.setUpDataBase();

        dbFactory = new DBConnectionFactoryImpl(TestHelper.DB_FACTORY_NAMESPACE);

        dbUserStatusDAO =
            new DbUserStatusDAO(dbFactory, "tt_user", "com.topcoder.timetracker.user.UserStatus",
                "com.topcoder.search.builder", "userStatusSearchBundle");
    }

    /**
     * Tears the unit test down.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void tearDown() throws Exception {
        dbUserStatusDAO = null;

        TestHelper.tearDownDataBase();
        TestHelper.clearConfig();
        super.tearDown();
    }

    /**
     * Test constructor for accuracy. Condition: normal. Expect: the object is created properly.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testDbUserStatusDAOAccuracy() throws Exception {
        assertNotNull("The object can't be created properly", new DbUserStatusDAO(dbFactory, "tt_user",
            "com.topcoder.timetracker.user.UserStatus", "com.topcoder.search.builder",
            "userStatusSearchBundle"));
    }

    /**
     * Test constructor for failure. Condition: connFactory is null. Expect: <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testDbUserStatusConnFactoryNull() throws Exception {
        try {
            new DbUserStatusDAO(null, "tt_user", "com.topcoder.timetracker.user.UserStatus",
                "com.topcoder.search.builder", "userStatusSearchBundle");
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test constructor for failure. Condition: connName is empty. Expect: <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testDbUserStatusConnNameEmpty() throws Exception {
        try {
            new DbUserStatusDAO(dbFactory, " \n", "com.topcoder.timetracker.user.UserStatus",
                "com.topcoder.search.builder", "userStatusSearchBundle");
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test constructor for failure. Condition: idGen is null. Expect: <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testDbUserStatusIdGenNull() throws Exception {
        try {
            new DbUserStatusDAO(dbFactory, "tt_user", null, "com.topcoder.search.builder",
                "userStatusSearchBundle");
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test constructor for failure. Condition: idGen is empty. Expect: <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testDbUserStatusIdGenEmpty() throws Exception {
        try {
            new DbUserStatusDAO(dbFactory, "tt_user", "\t", "com.topcoder.search.builder",
                "userStatusSearchBundle");
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test constructor for failure. Condition: searchBundleManagerNamespace is null. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testDbUserStatusSearchBundleManagerNamespaceNull() throws Exception {
        try {
            new DbUserStatusDAO(dbFactory, "tt_user", "com.topcoder.timetracker.user.UserStatus", null,
                "userStatusSearchBundle");
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test constructor for failure. Condition: searchBundleManagerNamespace is empty. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testDbUserStatusSearchBundleManagerNamespaceEmpty() throws Exception {
        try {
            new DbUserStatusDAO(dbFactory, "tt_user", "com.topcoder.timetracker.user.UserStatus", "    ",
                "userStatusSearchBundle");
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test constructor for failure. Condition: searchBundleName is null. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testDbUserStatusSearchBundleNameNull() throws Exception {
        try {
            new DbUserStatusDAO(dbFactory, "tt_user", "com.topcoder.timetracker.user.UserStatus",
                "com.topcoder.search.builder", null);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test constructor for failure. Condition: searchBundleName is empty. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testDbUserStatusSearchBundleNameEmpty() throws Exception {
        try {
            new DbUserStatusDAO(dbFactory, "tt_user", "com.topcoder.timetracker.user.UserStatus",
                "com.topcoder.search.builder", "    ");
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>addUserStatuses</code> for accuracy. Condition: normal. Expect: the user statuses are added
     * properly.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAddUserStatusesAccuracy() throws Exception {
        UserStatus userStatus = new UserStatus();
        userStatus.setDescription("description");
        userStatus.setCompanyId(10);
        userStatus.setCreationUser("test");
        userStatus.setModificationUser("test");
        userStatus.setCreationDate(new Date());
        userStatus.setModificationDate(new Date());
        dbUserStatusDAO.addUserStatuses(new UserStatus[] {userStatus});

        UserStatus[] userStatuses = dbUserStatusDAO.getUserStatuses(new long[] {userStatus.getId()});

        assertEquals("Returned value is not as expected", 1, userStatuses.length);
        assertEquals("Returned description is not as expected", "description", userStatuses[0]
            .getDescription());
        assertEquals("Returned company id is not as expected", 10, userStatuses[0].getCompanyId());
        assertEquals("Returned creation user is not as expected", "test", userStatuses[0].getCreationUser());
        assertEquals("Returned modification user is not as expected", "test", userStatuses[0]
            .getModificationUser());
    }

    /**
     * Test <code>addUserStatuses</code> for failure. Condition: userStatuses is null. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAddUserStatusesNull() throws Exception {
        try {
            dbUserStatusDAO.addUserStatuses(null);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>addUserStatuses</code> for failure. Condition: userStatuses is empty. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAddUserStatusesEmpty() throws Exception {
        try {
            dbUserStatusDAO.addUserStatuses(new UserStatus[0]);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>addUserStatuses</code> for failure. Condition: some of userStatuses are not valid. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAddUserStatusesMissingDescription() throws Exception {
        UserStatus userStatus = new UserStatus();
        userStatus.setCompanyId(10);
        userStatus.setCreationUser("test");
        userStatus.setModificationUser("test");
        userStatus.setCreationDate(new Date());
        userStatus.setModificationDate(new Date());
        try {
            dbUserStatusDAO.addUserStatuses(new UserStatus[] {userStatus});
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>addUserStatuses</code> for failure. Condition: some of userStatuses are not valid. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAddUserStatusesMissingCompanyId() throws Exception {
        UserStatus userStatus = new UserStatus();
        userStatus.setDescription("description");
        userStatus.setCreationUser("test");
        userStatus.setModificationUser("test");
        userStatus.setCreationDate(new Date());
        userStatus.setModificationDate(new Date());
        try {
            dbUserStatusDAO.addUserStatuses(new UserStatus[] {userStatus});
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>addUserStatuses</code> for failure. Condition: some of userStatuses are already added. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAddUserStatusesAlreadyAdded() throws Exception {
        UserStatus userStatus = new UserStatus();
        userStatus.setDescription("description");
        userStatus.setCompanyId(10);
        userStatus.setCreationUser("test");
        userStatus.setModificationUser("test");
        userStatus.setCreationDate(new Date());
        userStatus.setModificationDate(new Date());
        dbUserStatusDAO.addUserStatuses(new UserStatus[] {userStatus});

        try {
            dbUserStatusDAO.addUserStatuses(new UserStatus[] {userStatus});
            fail("Should throw BatchOperationException");
        } catch (IllegalArgumentException e) {
            // expected
        }

    }

    /**
     * Test <code>addUserStatuses</code> for failure. Condition: id generation is not working correctly. Expect:
     * <code>BatchOperationException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAddUserStatusesIdGenerationTableNotPrepared() throws Exception {
        Connection conn = dbFactory.createConnection();
        Statement statement = conn.createStatement();
        statement.execute("delete from id_sequences");
        statement.close();
        conn.close();

        UserStatus userStatus = new UserStatus();
        userStatus.setDescription("description");
        userStatus.setCompanyId(10);
        userStatus.setCreationUser("test");
        userStatus.setModificationUser("test");
        userStatus.setCreationDate(new Date());
        userStatus.setModificationDate(new Date());

        try {
            dbUserStatusDAO.addUserStatuses(new UserStatus[] {userStatus});
            fail("Should throw BatchOperationException");
        } catch (BatchOperationException e) {
            if (!(e.getCauses()[0] instanceof IDGenerationException)) {
                fail("The error should be IDGenerationException");
            }
        }

    }

    /**
     * Test <code>updateUserStatuses</code> for accuracy. Condition: normal. Expect: the user statuses are
     * updated properly.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateUserStatusesAccuracy() throws Exception {
        UserStatus userStatus = new UserStatus();
        userStatus.setDescription("description");
        userStatus.setCompanyId(10);
        userStatus.setCreationUser("test");
        userStatus.setModificationUser("test");
        userStatus.setCreationDate(new Date());
        userStatus.setModificationDate(new Date());
        dbUserStatusDAO.addUserStatuses(new UserStatus[] {userStatus});

        userStatus.setCompanyId(15);
        dbUserStatusDAO.updateUserStatuses(new UserStatus[] {userStatus});

        UserStatus[] userStatuses = dbUserStatusDAO.getUserStatuses(new long[] {userStatus.getId()});

        assertEquals("Returned value is not as expected", 1, userStatuses.length);
        assertEquals("Returned description is not as expected", "description", userStatuses[0]
            .getDescription());
        assertEquals("Returned company id is not as expected", 15, userStatuses[0].getCompanyId());
        assertEquals("Returned creation user is not as expected", "test", userStatuses[0].getCreationUser());
        assertEquals("Returned modification user is not as expected", "test", userStatuses[0]
            .getModificationUser());
    }

    /**
     * Test <code>updateUserStatuses</code> for failure. Condition: userStatuses is null. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateUserStatusesNull() throws Exception {

        try {
            dbUserStatusDAO.updateUserStatuses(null);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>updateUserStatuses</code> for failure. Condition: userStatuses is empty. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateUserStatusesEmpty() throws Exception {

        try {
            dbUserStatusDAO.updateUserStatuses(new UserStatus[0]);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>updateUserStatuses</code> for failure. Condition: sore of the user statuses are unrecognized.
     * Expect: <code>BatchOperationException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateUserStatusesUnrecognized() throws Exception {
        UserStatus userStatus = new UserStatus();
        userStatus.setId(10);
        userStatus.setDescription("description");
        userStatus.setCompanyId(10);
        userStatus.setCreationUser("test");
        userStatus.setModificationUser("test");
        userStatus.setCreationDate(new Date());
        userStatus.setModificationDate(new Date());
        try {
            dbUserStatusDAO.updateUserStatuses(new UserStatus[] {userStatus});
            fail("Should throw BatchOperationException");
        } catch (BatchOperationException e) {
            if (!(e.getCauses()[0] instanceof UnrecognizedEntityException)) {
                fail("The error should be UnrecognizedEntityException");
            }
        }
    }

    /**
     * Test <code>removeUserStatuses</code> for accuracy. Condition: normal. Expect: the user statuses are
     * removed.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRemoveUserStatusesAccuracy() throws Exception {
        UserStatus userStatus = new UserStatus();
        userStatus.setDescription("description");
        userStatus.setCompanyId(10);
        userStatus.setCreationUser("test");
        userStatus.setModificationUser("test");
        userStatus.setCreationDate(new Date());
        userStatus.setModificationDate(new Date());
        dbUserStatusDAO.addUserStatuses(new UserStatus[] {userStatus});

        dbUserStatusDAO.removeUserStatuses(new long[] {userStatus.getId()});
        UserStatus[] userStatuses = dbUserStatusDAO.getAllUserStatuses();

        assertEquals("Returned value is not as expected", 0, userStatuses.length);
    }

    /**
     * Test <code>removeUserStatuses</code> for failure. Condition: userStatusIds is null. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRemoveUserStatusesNull() throws Exception {
        try {
            dbUserStatusDAO.removeUserStatuses(null);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>removeUserStatuses</code> for failure. Condition: userStatusIds is empty. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRemoveUserStatusesEmpty() throws Exception {
        try {
            dbUserStatusDAO.removeUserStatuses(new long[0]);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>removeUserStatuses</code> for failure. Condition: some of the user status ids are negative.
     * Expect: <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRemoveUserStatusesNegativeId() throws Exception {
        try {
            dbUserStatusDAO.removeUserStatuses(new long[] {-1});
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>removeUserStatuses</code> for failure. Condition: some of the user statuses can't be found in
     * the database. Expect: <code>BatchOperationException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRemoveUserStatusesUnrecognized() throws Exception {

        try {
            dbUserStatusDAO.removeUserStatuses(new long[] {10});
            fail("Should throw BatchOperationException");
        } catch (BatchOperationException e) {
            if (!(e.getCauses()[0] instanceof UnrecognizedEntityException)) {
                fail("The error should be UnrecognizedEntityException");
            }
        }
    }

    /**
     * Test <code>getUserStatuses</code> for accuracy. Condition: normal. Expect: returned value is as expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetUserStatusesAccuracy() throws Exception {
        UserStatus userStatus = new UserStatus();
        userStatus.setDescription("description");
        userStatus.setCompanyId(10);
        userStatus.setCreationUser("test");
        userStatus.setModificationUser("test");
        userStatus.setCreationDate(new Date());
        userStatus.setModificationDate(new Date());
        dbUserStatusDAO.addUserStatuses(new UserStatus[] {userStatus});

        UserStatus[] userStatuses = dbUserStatusDAO.getUserStatuses(new long[] {userStatus.getId()});

        assertEquals("Returned value is not as expected", 1, userStatuses.length);
        assertEquals("Returned description is not as expected", "description", userStatuses[0]
            .getDescription());
        assertEquals("Returned company id is not as expected", 10, userStatuses[0].getCompanyId());
        assertEquals("Returned creation user is not as expected", "test", userStatuses[0].getCreationUser());
        assertEquals("Returned modification user is not as expected", "test", userStatuses[0]
            .getModificationUser());
    }

    /**
     * Test <code>getUserStatuses</code> for failure. Condition: userStatusIds is null. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetUserStatusesNull() throws Exception {
        try {
            dbUserStatusDAO.getUserStatuses(null);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>getUserStatuses</code> for failure. Condition: userStatusIds is empty. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetUserStatusesEmpty() throws Exception {
        try {
            dbUserStatusDAO.getUserStatuses(new long[0]);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>getUserStatuses</code> for failure. Condition: some of the user statuses are not in the
     * database. Expect: <code>BatchOperationException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetUserStatusesUnrecognizedEntity() throws Exception {
        try {
            dbUserStatusDAO.getUserStatuses(new long[] {10});
            fail("Should throw BatchOperationException");
        } catch (BatchOperationException e) {
            if (!(e.getCauses()[0] instanceof UnrecognizedEntityException)) {
                fail("The error should be UnrecognizedEntityException");
            }
        }
    }

    /**
     * Test <code>searchUserStatuses</code> for accuracy. Condition: normal. Expect: the result should be empty.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSearchUserStatusesAccuracy1() throws Exception {
        UserStatus[] userStatuses =
            dbUserStatusDAO.searchUserStatuses(UserStatusFilterFactory.createCompanyIdFilter(10));

        assertEquals("Returned value is not as expected", 0, userStatuses.length);
    }

    /**
     * Test <code>searchUserStatuses</code> for accuracy. Condition: normal. Expect: returned value is as
     * expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSearchUserStatusesAccuracy2() throws Exception {
        UserStatus userStatus = new UserStatus();
        userStatus.setDescription("description");
        userStatus.setCompanyId(10);
        userStatus.setCreationUser("test");
        userStatus.setModificationUser("test");
        userStatus.setCreationDate(new Date());
        userStatus.setModificationDate(new Date());
        dbUserStatusDAO.addUserStatuses(new UserStatus[] {userStatus});

        UserStatus[] userStatuses =
            dbUserStatusDAO.searchUserStatuses(UserStatusFilterFactory.createCompanyIdFilter(10));

        assertEquals("Returned value is not as expected", 1, userStatuses.length);
        assertEquals("Returned description is not as expected", "description", userStatuses[0]
            .getDescription());
        assertEquals("Returned company id is not as expected", 10, userStatuses[0].getCompanyId());
        assertEquals("Returned creation user is not as expected", "test", userStatuses[0].getCreationUser());
        assertEquals("Returned modification user is not as expected", "test", userStatuses[0]
            .getModificationUser());
    }

    /**
     * Test <code>searchUserStatuses</code> for failure. Condition: the filter is null. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSearchUserStatusesNull() throws Exception {
        try {
            dbUserStatusDAO.searchUserStatuses(null);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>searchUserStatuses</code> for failure. Condition: the filter is wrong. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSearchUserStatusesWrongFilter() throws Exception {
        try {
            dbUserStatusDAO.searchUserStatuses(new EqualToFilter("no_column", "value"));
            fail("Should throw DataAccessException");
        } catch (DataAccessException e) {
            // expected
        }
    }

    /**
     * Test <code>getAllUserStatuses</code> for accuracy. Condition: normal. Expect: there is no user status.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetAllUserStatusesAccuracy1() throws Exception {
        UserStatus[] userStatuses = dbUserStatusDAO.getAllUserStatuses();

        assertEquals("Returned value is not as expected", 0, userStatuses.length);
    }

    /**
     * Test <code>getAllUserStatuses</code> for accuracy. Condition: normal. Expect: returned value is as
     * expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetAllUserStatusesAccuracy2() throws Exception {
        UserStatus userStatus = new UserStatus();
        userStatus.setDescription("description");
        userStatus.setCompanyId(10);
        userStatus.setCreationUser("test");
        userStatus.setModificationUser("test");
        userStatus.setCreationDate(new Date());
        userStatus.setModificationDate(new Date());
        dbUserStatusDAO.addUserStatuses(new UserStatus[] {userStatus});

        UserStatus[] userStatuses = dbUserStatusDAO.getAllUserStatuses();

        assertEquals("Returned value is not as expected", 1, userStatuses.length);

        assertEquals("Returned description is not as expected", "description", userStatuses[0]
            .getDescription());
        assertEquals("Returned company id is not as expected", 10, userStatuses[0].getCompanyId());
        assertEquals("Returned creation user is not as expected", "test", userStatuses[0].getCreationUser());
        assertEquals("Returned modification user is not as expected", "test", userStatuses[0]
            .getModificationUser());
    }

}
