/*
 * <p>Copyright (c) 2005, TopCoder, Inc. All rights reserved</p>
 */
package com.topcoder.timetracker.entry.expense.accuracytests;

import com.topcoder.timetracker.entry.expense.ExpenseEntry;
import com.topcoder.timetracker.entry.expense.ExpenseEntryManager;
import com.topcoder.timetracker.entry.expense.ExpenseEntryStatus;
import com.topcoder.timetracker.entry.expense.ExpenseEntryType;
import com.topcoder.timetracker.entry.expense.persistence.ExpenseEntryPersistence;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.math.BigDecimal;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>
 * This class contains the accuracy unit tests for ExpenseEntryManager.java
 * </p>
 *
 * @author PE
 * @version 1.0
 */
public class ExpenseEntryManagerTest extends TestCase {
    /** Represents the ExpenseEntryManager instance for testing. */
    private ExpenseEntryManager manager = null;

    /**
     * <p>
     * Creates a test suite of the tests contained in this class.
     * </p>
     *
     * @return a test suite of the tests contained in this class.
     */
    public static Test suite() {
        return new TestSuite(ExpenseEntryManagerTest.class);
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
        manager = new ExpenseEntryManager(TestHelper.NAMESPACE);
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

        ExpenseEntryPersistence persistence = manager.getEntryPersistence();
        assertNotNull(persistence);
    }

    /**
     * <p>
     * Tests accuracy of the addEntry() method.
     * </p>
     *
     * @throws Exception any exception
     */
    public void testAddEntryAccuracy() throws Exception {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = TestHelper.getConnection(TestHelper.DB_NAMESPACE);
            TestHelper.insertExpenseType(connection);
            TestHelper.insertExpenseStatus(connection);

            ExpenseEntry entry = TestHelper.createExpenseEntry();

            assertTrue("entry should be successfully added", manager.addEntry(entry));

            assertNotNull("creation date should be changed", entry.getCreationDate());
            assertNotNull("modification date should be changed", entry.getModificationDate());
            assertTrue("other field of entry should not be changed",
                TestHelper.isExpenseEntryEqual(entry, TestHelper.createExpenseEntry()));

            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM ExpenseEntries");

            assertTrue("should be one record", resultSet.next());

            assertEquals("wrong id", entry.getId(), resultSet.getInt("ExpenseEntriesID"));
            assertEquals("wrong description", entry.getDescription(), resultSet.getString("Description"));
            assertEquals("wrong creation date", TestHelper.convertDate(entry.getCreationDate()),
                TestHelper.convertDate(resultSet.getDate("CreationDate")));
            assertEquals("wrong creation user", entry.getCreationUser(), resultSet.getString("CreationUser"));
            assertEquals("wrong modification date", TestHelper.convertDate(entry.getModificationDate()),
                TestHelper.convertDate(resultSet.getDate("ModificationDate")));
            assertEquals("wrong modification user", entry.getModificationUser(), resultSet.getString("ModificationUser"));
            assertTrue("wrong amount",
                entry.getAmount().subtract(resultSet.getBigDecimal("Amount")).abs().doubleValue() < 1e-8);
            assertEquals("wrong billable", entry.isBillable(), resultSet.getBoolean("Billable"));
            assertEquals("wrong expenseType", entry.getExpenseType().getId(), resultSet.getInt("ExpenseTypesID"));
            assertEquals("wrong expenseStatus", entry.getStatus().getId(), resultSet.getInt("ExpenseStatusesID"));
            assertEquals("wrong date", TestHelper.convertDate(entry.getDate()),
                TestHelper.convertDate(resultSet.getDate("EntryDate")));

            assertFalse("should be one record", resultSet.next());
        } finally {
            TestHelper.closeResources(resultSet, statement, connection);
        }
    }

    /**
     * <p>
     * Tests accuracy of the addEntry() method.
     * </p>
     *
     * @throws Exception any exception
     */
    public void testAddEntryExistAccuracy() throws Exception {
        Connection connection = null;

        try {
            connection = TestHelper.getConnection(TestHelper.DB_NAMESPACE);
            TestHelper.insertExpenseType(connection);
            TestHelper.insertExpenseStatus(connection);
            TestHelper.insertExpenseEntry(connection);

            ExpenseEntry entry = TestHelper.createExpenseEntry();

            assertFalse("entry should be unsuccessfully added", manager.addEntry(entry));
            assertNull("creation date should not be changed", entry.getCreationDate());
            assertNull("modification date should not be changed", entry.getModificationDate());
        } finally {
            TestHelper.closeResources(null, null, connection);
        }
    }

    /**
     * <p>
     * Tests accuracy of the updateEntry() method.
     * </p>
     *
     * @throws Exception any exception
     */
    public void testUpdateEntryAccuracy() throws Exception {
        ExpenseEntry update = new ExpenseEntry(1);
        update.setCreationUser("Create");
        update.setModificationUser("Modify");
        update.setDescription("Modified");
        update.setAmount(new BigDecimal(6.28));
        update.setBillable(false);
        update.setDate(TestHelper.createDate(2005, 7, 16, 1, 1, 1));
        update.setExpenseType(TestHelper.createExpenseType());
        update.setStatus(TestHelper.createExpenseStatus());
        update.setCreationDate(TestHelper.createDate(2005, 7, 16, 1, 1, 1));
        update.setModificationDate(TestHelper.createDate(2005, 7, 16, 1, 1, 1));

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = TestHelper.getConnection(TestHelper.DB_NAMESPACE);
            TestHelper.insertExpenseType(connection);
            TestHelper.insertExpenseStatus(connection);

            assertFalse("entry should be unsuccessfully updated", manager.updateEntry(update));
            assertTrue("modification date should not be changed",
                TestHelper.convertDate(TestHelper.createDate(2005, 7, 16, 1, 1, 1)) == TestHelper.convertDate(
                    update.getModificationDate()));

            TestHelper.insertExpenseEntry(connection);

            assertTrue("entry should be successfully updated", manager.updateEntry(update));

            assertEquals("creation date should not be changed",
                TestHelper.convertDate(TestHelper.createDate(2005, 7, 16, 1, 1, 1)),
                TestHelper.convertDate(update.getCreationDate()));
            assertFalse("modification date should be changed",
                TestHelper.convertDate(TestHelper.createDate(2005, 7, 16, 1, 1, 1)) == TestHelper.convertDate(
                    update.getModificationDate()));

            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM ExpenseEntries");

            assertTrue("should one record", resultSet.next());

            assertEquals("wrong id", update.getId(), resultSet.getInt("ExpenseEntriesID"));
            assertEquals("wrong description", "Modified", resultSet.getString("Description"));
            assertEquals("wrong creation date", TestHelper.convertDate(TestHelper.createDate(2001, 1, 1, 1, 1, 1)),
                TestHelper.convertDate(resultSet.getDate("CreationDate")));
            assertEquals("wrong modification date", TestHelper.convertDate(update.getModificationDate()),
                TestHelper.convertDate(resultSet.getDate("ModificationDate")));
            assertEquals("wrong creation user", "PE", resultSet.getString("CreationUser"));
            assertEquals("wrong modification user", "Modify", resultSet.getString("ModificationUser"));
            assertEquals("wrong billable", false, resultSet.getBoolean("Billable"));
            assertEquals("wrong expenseTypesID", 1, resultSet.getInt("ExpenseTypesID"));
            assertEquals("wrong expenseStatusID", 1, resultSet.getInt("ExpenseStatusesID"));

            assertFalse("Only one record should exist.", resultSet.next());
        } finally {
            TestHelper.closeResources(resultSet, statement, connection);
        }
    }

    /**
     * <p>
     * Tests accuracy of the deleteEntry() method.
     * </p>
     *
     * @throws Exception any exception
     */
    public void testDeleteEntryAccuracy() throws Exception {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            assertFalse("entry should be unsuccessfully deleted", manager.deleteEntry(1));

            connection = TestHelper.getConnection(TestHelper.DB_NAMESPACE);
            TestHelper.insertExpenseType(connection);
            TestHelper.insertExpenseStatus(connection);
            TestHelper.insertExpenseEntry(connection);

            // Delete
            assertTrue("entry should be successfully deleted", manager.deleteEntry(1));

            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM ExpenseEntries");

            assertFalse("should no records", resultSet.next());
        } finally {
            TestHelper.closeResources(resultSet, statement, connection);
        }
    }

    /**
     * <p>
     * Tests accuracy of the deleteAllEntry() method.
     * </p>
     *
     * @throws Exception any exception
     */
    public void testDeleteAllEntryAccuracy() throws Exception {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = TestHelper.getConnection(TestHelper.DB_NAMESPACE);
            TestHelper.insertExpenseType(connection);
            TestHelper.insertExpenseStatus(connection);
            TestHelper.insertExpenseEntry(connection);

            // Delete
            manager.deleteAllEntries();

            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM ExpenseEntries");

            assertFalse("should no records", resultSet.next());
        } finally {
            TestHelper.closeResources(resultSet, statement, connection);
        }
    }

    /**
     * <p>
     * Tests accuracy of the retrieveEntry() method.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testRetrieveEntryAccuracy() throws Exception {
        Connection connection = null;

        try {
            connection = TestHelper.getConnection(TestHelper.DB_NAMESPACE);
            TestHelper.insertExpenseType(connection);
            TestHelper.insertExpenseStatus(connection);
            TestHelper.insertExpenseEntry(connection);

            assertTrue("entry should be successfully retrieved",
                TestHelper.isExpenseEntryEqual(manager.retrieveEntry(1), TestHelper.createExpenseEntry()));
        } finally {
            TestHelper.closeResources(null, null, connection);
        }
    }

    /**
     * <p>
     * Tests accuracy of the retrieveAllEntry() method.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testRetrieveAllEntryesAccuracy() throws Exception {
        List expected = new ArrayList();
        ExpenseEntryType type = TestHelper.createExpenseType();
        ExpenseEntryStatus status = TestHelper.createExpenseStatus();

        ExpenseEntry entry = null;
        Connection connection = null;

        try {
            connection = TestHelper.getConnection(TestHelper.DB_NAMESPACE);
            TestHelper.insertExpenseType(connection);
            TestHelper.insertExpenseStatus(connection);
        } finally {
            TestHelper.closeResources(null, null, connection);
        }

        for (int i = 0; i < 10; ++i) {
            entry = new ExpenseEntry(i);
            entry.setDescription("Description" + i);
            entry.setCreationUser("Create" + i);
            entry.setModificationUser("Create" + i);
            entry.setAmount(new BigDecimal(i));
            entry.setBillable(true);
            entry.setDate(TestHelper.createDate(2001, 2, i, i, i, i));
            entry.setExpenseType(type);
            entry.setStatus(status);

            manager.addEntry(entry);
            expected.add(entry);
        }

        List actual = manager.retrieveAllEntries();

        assertEquals("entry should be successfully retrieved", expected.size(), actual.size());

        for (int i = 0; i < expected.size(); ++i) {
            assertTrue("entry should be successfully retrieved",
                TestHelper.isExpenseEntryEqual((ExpenseEntry) expected.get(i), (ExpenseEntry) actual.get(i)));
        }
    }

    /**
     * <p>
     * Tests accuracy of the getEntryPersistence() method.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testGetEntryPersistenceAccuracy() throws Exception {
        assertNotNull(manager.getEntryPersistence());
    }
}
