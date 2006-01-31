/*
 * <p>Copyright (c) 2005, TopCoder, Inc. All rights reserved</p>
 */
package com.topcoder.timetracker.entry.expense.accuracytests;

import com.topcoder.timetracker.entry.expense.ExpenseEntryType;
import com.topcoder.timetracker.entry.expense.ExpenseEntryTypeManager;
import com.topcoder.timetracker.entry.expense.persistence.ExpenseEntryTypePersistence;

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
 * This class contains the accuracy unit tests for ExpenseEntryTypeManager.java
 * </p>
 *
 * @author PE
 * @version 1.0
 */
public class ExpenseEntryTypeManagerTest extends TestCase {
    /** Represents the ExpenseEntryTypeManager instance for testing. */
    private ExpenseEntryTypeManager manager = null;

    /**
     * <p>
     * Creates a test suite of the tests contained in this class.
     * </p>
     *
     * @return a test suite of the tests contained in this class.
     */
    public static Test suite() {
        return new TestSuite(ExpenseEntryTypeManagerTest.class);
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
        manager = new ExpenseEntryTypeManager(TestHelper.NAMESPACE);
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

        ExpenseEntryTypePersistence persistence = manager.getTypePersistence();
        assertNotNull(persistence);
    }

    /**
     * <p>
     * Tests accuracy of the addType() method.
     * </p>
     *
     * @throws Exception any exception
     */
    public void testAddTypeAccuracy() throws Exception {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = TestHelper.getConnection(TestHelper.DB_NAMESPACE);

            ExpenseEntryType type = TestHelper.createExpenseType();

            assertTrue("type should be successfully added", manager.addType(type));

            assertNotNull("creation date should be changed", type.getCreationDate());
            assertNotNull("modification date should be changed", type.getModificationDate());
            assertTrue("other field of entry should not be changed",
                TestHelper.isExpenseTypeEqual(type, TestHelper.createExpenseType()));

            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM ExpenseTypes");

            assertTrue("should be one record", resultSet.next());

            assertEquals("wrong id", type.getId(), resultSet.getInt("ExpenseTypesID"));
            assertEquals("wrong description", type.getDescription(), resultSet.getString("Description"));
            assertEquals("wrong creation date", TestHelper.convertDate(type.getCreationDate()),
                TestHelper.convertDate(resultSet.getDate("CreationDate")));
            assertEquals("wrong creation user", type.getCreationUser(), resultSet.getString("CreationUser"));
            assertEquals("wrong modification date", TestHelper.convertDate(type.getModificationDate()),
                TestHelper.convertDate(resultSet.getDate("ModificationDate")));
            assertEquals("wrong modification user", type.getModificationUser(), resultSet.getString("ModificationUser"));

            assertFalse("should be one record", resultSet.next());
        } finally {
            TestHelper.closeResources(resultSet, statement, connection);
        }
    }

    /**
     * <p>
     * Tests accuracy of the addType() method.
     * </p>
     *
     * @throws Exception any exception
     */
    public void testAddTypeExistAccuracy() throws Exception {
        Connection connection = null;

        try {
            connection = TestHelper.getConnection(TestHelper.DB_NAMESPACE);
            TestHelper.insertExpenseType(connection);

            ExpenseEntryType type = TestHelper.createExpenseType();

            assertFalse("type should be unsuccessfully added", manager.addType(type));
            assertNull("creation date should not be changed", type.getCreationDate());
            assertNull("modification date should not be changed", type.getModificationDate());
        } finally {
            TestHelper.closeResources(null, null, connection);
        }
    }

    /**
     * <p>
     * Tests accuracy of the updateType() method.
     * </p>
     *
     * @throws Exception any exception
     */
    public void testUpdateTypeAccuracy() throws Exception {
        ExpenseEntryType update = new ExpenseEntryType(1);
        update.setCreationUser("Create");
        update.setModificationUser("Modify");
        update.setDescription("Modified");
        update.setCreationDate(TestHelper.createDate(2005, 7, 16, 1, 1, 1));
        update.setModificationDate(TestHelper.createDate(2005, 7, 16, 1, 1, 1));

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            assertFalse("should be unsuccessful updated", manager.updateType(update));
            assertTrue("modification date should not be updated",
                TestHelper.convertDate(TestHelper.createDate(2005, 7, 16, 1, 1, 1)) == TestHelper.convertDate(
                    update.getModificationDate()));

            connection = TestHelper.getConnection(TestHelper.DB_NAMESPACE);
            TestHelper.insertExpenseType(connection);

            assertTrue("should be successful updated", manager.updateType(update));

            assertEquals("creation date should not be changed",
                TestHelper.convertDate(TestHelper.createDate(2005, 7, 16, 1, 1, 1)),
                TestHelper.convertDate(update.getCreationDate()));
            assertFalse("modification date should be changed",
                TestHelper.convertDate(TestHelper.createDate(2005, 7, 16, 1, 1, 1)) == TestHelper.convertDate(
                    update.getModificationDate()));

            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM ExpenseTypes");

            assertTrue("Only one record should exist.", resultSet.next());

            assertEquals("wrong id", update.getId(), resultSet.getInt("ExpenseTypesID"));
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
     * Tests accuracy of the deleteType() method.
     * </p>
     *
     * @throws Exception any exception
     */
    public void testDeleteTypeAccuracy() throws Exception {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            assertFalse("type should be unsuccessfully deleted", manager.deleteType(1));

            connection = TestHelper.getConnection(TestHelper.DB_NAMESPACE);
            TestHelper.insertExpenseType(connection);

            // Delete
            assertTrue("type should be successfully deleted", manager.deleteType(1));

            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM ExpenseTypes");

            assertFalse("should no records", resultSet.next());
        } finally {
            TestHelper.closeResources(resultSet, statement, connection);
        }
    }

    /**
     * <p>
     * Tests accuracy of the deleteAllType() method.
     * </p>
     *
     * @throws Exception any exception
     */
    public void testDeleteAllTypeAccuracy() throws Exception {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = TestHelper.getConnection(TestHelper.DB_NAMESPACE);
            TestHelper.insertExpenseType(connection);

            // Delete
            manager.deleteAllTypes();

            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM ExpenseTypes");

            assertFalse("should no records", resultSet.next());
        } finally {
            TestHelper.closeResources(resultSet, statement, connection);
        }
    }

    /**
     * <p>
     * Tests accuracy of the retrieveType() method.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testRetrieveTypeAccuracy() throws Exception {
        Connection connection = null;

        try {
            connection = TestHelper.getConnection(TestHelper.DB_NAMESPACE);
            TestHelper.insertExpenseType(connection);

            assertTrue("type should be successfully retrieved",
                TestHelper.isExpenseTypeEqual(manager.retrieveType(1), TestHelper.createExpenseType()));
        } finally {
            TestHelper.closeResources(null, null, connection);
        }
    }

    /**
     * <p>
     * Tests accuracy of the retrieveAllType() method.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testRetrieveAllTypeAccuracy() throws Exception {
        List expected = new ArrayList();
        ExpenseEntryType type = null;

        for (int i = 0; i < 10; ++i) {
            type = new ExpenseEntryType(i);
            type.setDescription("Description" + i);
            type.setCreationUser("Create" + i);
            type.setModificationUser("Create" + i);

            manager.addType(type);
            expected.add(type);
        }

        List actual = manager.retrieveAllTypes();

        assertEquals("entry should be successfully retrieved", expected.size(), actual.size());

        for (int i = 0; i < expected.size(); ++i) {
            assertTrue("type should be successfully retrieved",
                TestHelper.isExpenseTypeEqual((ExpenseEntryType) expected.get(i), (ExpenseEntryType) actual.get(i)));
        }
    }

    /**
     * <p>
     * Tests accuracy of the getTypePersistence() method.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testGetTypePersistenceAccuracy() throws Exception {
        assertNotNull(manager.getTypePersistence());
    }
}
