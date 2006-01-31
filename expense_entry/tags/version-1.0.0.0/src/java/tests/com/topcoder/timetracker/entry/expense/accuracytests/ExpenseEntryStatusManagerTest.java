/*
 * <p>Copyright (c) 2005, TopCoder, Inc. All rights reserved</p>
 */
package com.topcoder.timetracker.entry.expense.accuracytests;

import com.topcoder.timetracker.entry.expense.ExpenseEntryStatus;
import com.topcoder.timetracker.entry.expense.ExpenseEntryStatusManager;
import com.topcoder.timetracker.entry.expense.persistence.ExpenseEntryStatusPersistence;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>
 * This class contains the accuracy unit tests for ExpenseEntryStatusManager.java
 * </p>
 *
 * @author PE
 * @version 1.0
 */
public class ExpenseEntryStatusManagerTest extends TestCase {
    /** Represents the ExpenseEntryStatusManager instance for testing. */
    private ExpenseEntryStatusManager manager = null;

    /**
     * <p>
     * Creates a test suite of the tests contained in this class.
     * </p>
     *
     * @return a test suite of the tests contained in this class.
     */
    public static Test suite() {
        return new TestSuite(ExpenseEntryStatusManagerTest.class);
    }

    /**
     * <p>
     * Sets up the fixture.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        TestHelper.addConfig();
        manager = new ExpenseEntryStatusManager(TestHelper.NAMESPACE);
    }

    /**
     * Removes the configuration if it exists.
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        Connection connection = TestHelper.getConnection(TestHelper.DB_NAMESPACE);
        TestHelper.clearConfig();
        TestHelper.clearRecords(connection);
        TestHelper.closeResources(null, null, connection);
    }

    /**
     * <p>
     * Tests accuracy of the constructor.
     * </p>
     *
     * @throws Exception any exception
     */
    public void testConstructorAccuracy() throws Exception {
        assertNotNull(manager);

        ExpenseEntryStatusPersistence persistence = manager.getStatusPersistence();
        assertNotNull(persistence);
    }

    /**
     * <p>
     * Tests accuracy of the addStatus() method.
     * </p>
     *
     * @throws Exception any exception
     */
    public void testAddStatusAccuracy() throws Exception {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = TestHelper.getConnection(TestHelper.DB_NAMESPACE);

            ExpenseEntryStatus status = TestHelper.createExpenseStatus();

            assertTrue("status should be successfully added", manager.addStatus(status));

            assertNotNull("creation date should be changed", status.getCreationDate());
            assertNotNull("modification date should be changed", status.getModificationDate());
            assertTrue("other field of entry should not be changed",
                TestHelper.isExpenseStatusEqual(status, TestHelper.createExpenseStatus()));

            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM ExpenseStatuses");

            assertTrue("should be one record", resultSet.next());

            assertEquals("wrong id", status.getId(), resultSet.getInt("ExpenseStatusesID"));
            assertEquals("wrong description", status.getDescription(), resultSet.getString("Description"));
            assertEquals("wrong creation date", TestHelper.convertDate(status.getCreationDate()),
                TestHelper.convertDate(resultSet.getDate("CreationDate")));
            assertEquals("wrong creation user", status.getCreationUser(), resultSet.getString("CreationUser"));
            assertEquals("wrong modification date", TestHelper.convertDate(status.getModificationDate()),
                TestHelper.convertDate(resultSet.getDate("ModificationDate")));
            assertEquals("wrong modification user", status.getModificationUser(),
                resultSet.getString("ModificationUser"));

            assertFalse("should be one record", resultSet.next());
        } finally {
            TestHelper.closeResources(resultSet, statement, connection);
        }
    }

    /**
     * <p>
     * Tests accuracy of the addStatus() method.
     * </p>
     *
     * @throws Exception any exception
     */
    public void testAddStatusExistAccuracy() throws Exception {
        Connection connection = null;

        try {
            connection = TestHelper.getConnection(TestHelper.DB_NAMESPACE);
            TestHelper.insertExpenseStatus(connection);

            ExpenseEntryStatus status = TestHelper.createExpenseStatus();

            assertFalse("status should be unsuccessfully added", manager.addStatus(status));
            assertNull("creation date should not be changed", status.getCreationDate());
            assertNull("modification date should not be changed", status.getModificationDate());
        } finally {
            TestHelper.closeResources(null, null, connection);
        }
    }

    /**
     * <p>
     * Tests accuracy of the updateStatus() method.
     * </p>
     *
     * @throws Exception any exception
     */
    public void testUpdateStatusAccuracy() throws Exception {
        ExpenseEntryStatus update = new ExpenseEntryStatus(1);
        update.setCreationUser("Create");
        update.setModificationUser("Modify");
        update.setDescription("Modified");
        update.setCreationDate(TestHelper.createDate(2005, 7, 16, 1, 1, 1));
        update.setModificationDate(TestHelper.createDate(2005, 7, 16, 1, 1, 1));

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            assertFalse("should be unsuccessful updated", manager.updateStatus(update));
            assertTrue("modification date should not be updated",
                TestHelper.convertDate(TestHelper.createDate(2005, 7, 16, 1, 1, 1)) == TestHelper.convertDate(
                    update.getModificationDate()));

            connection = TestHelper.getConnection(TestHelper.DB_NAMESPACE);
            TestHelper.insertExpenseStatus(connection);

            assertTrue("should be successful updated", manager.updateStatus(update));

            assertEquals("creation date should not be changed",
                TestHelper.convertDate(TestHelper.createDate(2005, 7, 16, 1, 1, 1)),
                TestHelper.convertDate(update.getCreationDate()));
            assertFalse("modification date should be changed",
                TestHelper.convertDate(TestHelper.createDate(2005, 7, 16, 1, 1, 1)) == TestHelper.convertDate(
                    update.getModificationDate()));

            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM ExpenseStatuses");

            assertTrue("Only one record should exist.", resultSet.next());

            assertEquals("wrong id", update.getId(), resultSet.getInt("ExpenseStatusesID"));
            assertEquals("wrong description", "Modified", resultSet.getString("Description"));
            assertEquals("wrong creation date", TestHelper.convertDate(TestHelper.createDate(2001, 1, 1, 1, 1, 1)),
                TestHelper.convertDate(resultSet.getDate("CreationDate")));
            assertEquals("wrong modification date", TestHelper.convertDate(update.getModificationDate()),
                TestHelper.convertDate(resultSet.getDate("ModificationDate")));
            assertEquals("wrong creation user", "PE", resultSet.getString("CreationUser"));
            assertEquals("wrong modification user", "Modify", resultSet.getString("ModificationUser"));

            assertFalse("Only one record should exist.", resultSet.next());
        } finally {
            TestHelper.closeResources(resultSet, statement, connection);
        }
    }

    /**
     * <p>
     * Tests accuracy of the deleteStatus() method.
     * </p>
     *
     * @throws Exception any exception
     */
    public void testDeleteStatusAccuracy() throws Exception {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            assertFalse("status should be unsuccessfully deleted", manager.deleteStatus(1));

            connection = TestHelper.getConnection(TestHelper.DB_NAMESPACE);
            TestHelper.insertExpenseStatus(connection);

            // Delete
            assertTrue("status should be successfully deleted", manager.deleteStatus(1));

            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM ExpenseStatuses");

            assertFalse("should no records", resultSet.next());
        } finally {
            TestHelper.closeResources(resultSet, statement, connection);
        }
    }

    /**
     * <p>
     * Tests accuracy of the deleteAllStatus() method.
     * </p>
     *
     * @throws Exception any exception
     */
    public void testDeleteAllStatusAccuracy() throws Exception {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = TestHelper.getConnection(TestHelper.DB_NAMESPACE);
            TestHelper.insertExpenseStatus(connection);

            // Delete
            manager.deleteAllStatuses();

            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM ExpenseStatuses");

            assertFalse("should no records", resultSet.next());
        } finally {
            TestHelper.closeResources(resultSet, statement, connection);
        }
    }

    /**
     * <p>
     * Tests accuracy of the retrieveStatus() method.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testRetrieveStatusAccuracy() throws Exception {
        Connection connection = null;

        try {
            connection = TestHelper.getConnection(TestHelper.DB_NAMESPACE);
            TestHelper.insertExpenseStatus(connection);

            assertTrue("status should be successfully retrieved",
                TestHelper.isExpenseStatusEqual(manager.retrieveStatus(1), TestHelper.createExpenseStatus()));
        } finally {
            TestHelper.closeResources(null, null, connection);
        }
    }

    /**
     * <p>
     * Tests accuracy of the retrieveAllStatus() method.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testRetrieveAllStatusAccuracy() throws Exception {
        List expected = new ArrayList();
        ExpenseEntryStatus status = null;

        for (int i = 0; i < 10; ++i) {
            status = new ExpenseEntryStatus(i);
            status.setDescription("Description" + i);
            status.setCreationUser("Create" + i);
            status.setModificationUser("Create" + i);

            manager.addStatus(status);
            expected.add(status);
        }

        List actual = manager.retrieveAllStatuses();

        assertEquals("entry should be successfully retrieved", expected.size(), actual.size());

        for (int i = 0; i < expected.size(); ++i) {
            assertTrue("status should be successfully retrieved",
                TestHelper.isExpenseStatusEqual((ExpenseEntryStatus) expected.get(i), (ExpenseEntryStatus) actual.get(i)));
        }
    }

    /**
     * <p>
     * Tests accuracy of the getStatusPersistence() method.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testGetStatusPersistenceAccuracy() throws Exception {
        assertNotNull(manager.getStatusPersistence());
    }
}
