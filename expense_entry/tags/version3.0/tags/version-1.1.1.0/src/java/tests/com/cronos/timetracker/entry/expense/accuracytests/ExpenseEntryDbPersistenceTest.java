/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.entry.expense.accuracytests;

import com.cronos.timetracker.entry.expense.ExpenseEntry;
import com.cronos.timetracker.entry.expense.ExpenseEntryRejectReason;
import com.cronos.timetracker.entry.expense.ExpenseEntryStatus;
import com.cronos.timetracker.entry.expense.ExpenseEntryType;
import com.cronos.timetracker.entry.expense.persistence.ExpenseEntryDbPersistence;
import com.cronos.timetracker.entry.expense.persistence.ExpenseEntryPersistence;
import com.cronos.timetracker.entry.expense.persistence.PersistenceException;
import com.cronos.timetracker.entry.expense.search.CompositeCriteria;
import com.cronos.timetracker.entry.expense.search.Criteria;
import com.cronos.timetracker.entry.expense.search.FieldBetweenCriteria;
import com.cronos.timetracker.entry.expense.search.FieldLikeCriteria;
import com.cronos.timetracker.entry.expense.search.FieldMatchCriteria;
import com.cronos.timetracker.entry.expense.search.NotCriteria;
import com.cronos.timetracker.entry.expense.search.RejectReasonCriteria;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.math.BigDecimal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


/**
 * <p>
 * Accuracy tests for class ExpenseEntryDbPersistence. Here only the new methods not in version 1.0 are tested.
 * </p>
 *
 * @author -oo-
 * @version 1.1
 */
public class ExpenseEntryDbPersistenceTest extends TestCase {
    /** Represents the namespace to load DB connection factory configuration. */
    private static final String DB_NAMESPACE = "com.topcoder.timetracker.entry.expense.connection";

    /** Represents the ExpenseEntryPersistence instance used in tests. */
    private ExpenseEntryPersistence persistence;

    /** Represents the database connection to access database. */
    private Connection connection;

    /** Represents a valid expense entry instance. */
    private ExpenseEntry entry;

    /** Represents a valid expense entries array instance. */
    private ExpenseEntry[] entries;

    /** Represents a valid expense entry type instance. */
    private ExpenseEntryType type;

    /** Represents a valid expense entry status instance. */
    private ExpenseEntryStatus status;

    /**
     * <p>
     * Creates a test suite of the tests contained in this class.
     * </p>
     *
     * @return a test suite of the tests contained in this class.
     */
    public static Test suite() {
        return new TestSuite(ExpenseEntryDbPersistenceTest.class);
    }

    /**
     * Initialize the connection field.
     *
     * @throws Exception to JUnit
     */
    private void initConnection() throws Exception {
        if (connection == null) {
            connection = TestHelper.getConnection();
        }
    }

    /**
     * Close the connection field.
     *
     * @throws Exception to JUnit
     */
    private void closeConnection() throws Exception {
        if (connection != null) {
            connection.close();
            connection = null;
        }
    }

    /**
     * <p>
     * Sets up the test environment. Valid configurations are loaded. A valid persistence is created. The data table is
     * truncated. A valid expense entry entry is created. One expense type and one expense status are added into the
     * database.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        TestHelper.clearConfiguration();
        TestHelper.addValidConfiguration();

        // Create the persistence
        persistence = new ExpenseEntryDbPersistence("Connection", DB_NAMESPACE);

        TestHelper.initDatabase();

        type = TestHelper.createTypeInstance();
        status = TestHelper.createStatusInstance();

        entry = new ExpenseEntry(100);
        entry.setCreationDate(new Date());
        entry.setModificationDate(new Date());
        entry.setDescription("description");
        entry.setCreationUser("create");
        entry.setModificationUser("modify");
        entry.setAmount(new BigDecimal(100.10));
        entry.setBillable(true);
        entry.setDate(TestHelper.createDate(2005, 7, 29));
        entry.setExpenseType(type);
        entry.setStatus(status);

        for (int i = 0; i < 3; i++) {
            entry.addRejectReason(new ExpenseEntryRejectReason(i));
        }

        entries = new ExpenseEntry[3];

        for (int i = 0; i < entries.length; ++i) {
            entries[i] = new ExpenseEntry(i * 10);
            entries[i].setCreationUser("create" + i);
            entries[i].setModificationUser("modify" + i);
            entries[i].setDescription("description" + i);
            entries[i].setCreationDate(new Date(1000 + (i * 1000)));
            entries[i].setModificationDate(new Date());
            entries[i].setAmount(new BigDecimal(i * 1000));
            entries[i].setBillable(true);
            entries[i].setDate(TestHelper.createDate(2005, 7, i));
            entries[i].setExpenseType(type);
            entries[i].setStatus(status);
            entries[i].addRejectReason(new ExpenseEntryRejectReason(i));
        }

        initConnection();
    }

    /**
     * <p>
     * Cleans up the test environment. The configurations are removed. The data table is truncated.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    protected void tearDown() throws Exception {
        TestHelper.clearConfiguration();
        TestHelper.clearDatabase(connection);
        closeConnection();
        persistence.closeConnection();
    }

    /**
     * Accuracy tests for addEntry().
     *
     * @throws Exception to JUnit
     */
    public void testAddEntry() throws Exception {
        // test while the entry not existed
        assertTrue("the return value is not correct", persistence.addEntry(entry));
        assertExpenseEntryExist(entry);
        assertRejectReasonsExist(entry);

        // test while the entry already existed
        assertFalse("the return value is not correct", persistence.addEntry(entry));
    }

    /**
     * Accuracy tests for deleteEntry().
     *
     * @throws Exception to JUnit
     */
    public void testDeleteEntry() throws Exception {
        // test while the entry not existed
        assertFalse("the return value is not correct", persistence.deleteEntry(entry.getId()));

        // test while the entry already existed
        persistence.addEntry(entry);
        assertTrue("the return value is not correct", persistence.deleteEntry(entry.getId()));
        assertExpenseEntryNotExist(entry);
        assertRejectReasonsNotExist(entry);
    }

    /**
     * Accuracy tests for deleteAllEntries().
     *
     * @throws Exception to JUnit
     */
    public void testDeleteAllEntries() throws Exception {
        // test while the entry not existed
        persistence.deleteAllEntries();

        // test while the entry already existed
        persistence.addEntries(entries, true);
        persistence.deleteAllEntries();

        for (int i = 0; i < entries.length; ++i) {
            assertExpenseEntryNotExist(entries[i]);
            assertRejectReasonsNotExist(entries[i]);
        }
    }

    /**
     * Accuracy tests for updateEntry().
     *
     * @throws Exception to JUnit
     */
    public void testUpdateEntry() throws Exception {
        // test while the entry not existed
        assertFalse("the return value is not correct", persistence.updateEntry(entry));

        // test while the entry already existed
        persistence.addEntry(entry);
        entries[2].setId(entry.getId());
        entries[2].setCreationDate(entry.getCreationDate());
        entries[2].setCreationUser(entry.getCreationUser());
        assertTrue("the return value is not correct", persistence.updateEntry(entries[2]));

        List actual = persistence.retrieveAllEntries();
        assertEquals("The number of items in list should be correct.", 1, actual.size());
        TestHelper.assertEquals(entries[2], (ExpenseEntry) actual.get(0));
    }

    /**
     * Accuracy tests for retrieveEntry().
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveEntry() throws Exception {
        // test while the entry not existed
        persistence.addEntries(entries, true);
        assertNull("the return value is not correct", persistence.retrieveEntry(entry.getId()));

        // test while the entry already existed
        ExpenseEntry actual = persistence.retrieveEntry(entries[1].getId());
        TestHelper.assertEquals(entries[1], actual);
    }

    /**
     * Accuracy tests for retrieveAllEntries().
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveAllEntries() throws Exception {
        // test while no entry existed
        List actual = persistence.retrieveAllEntries();
        assertEquals("the return value is not correct", 0, actual.size());

        // test while some entries already existed
        persistence.addEntries(entries, true);
        actual = persistence.retrieveAllEntries();
        assertEquals("the return value is not correct", entries.length, actual.size());

        for (int i = 0; i < entries.length; ++i) {
            TestHelper.assertEquals(entries[i], (ExpenseEntry) actual.get(i));
        }
    }

    /**
     * Accuracy tests for addEntries() in atomic mode.
     *
     * @throws Exception to JUnit
     */
    public void testAddEntries1() throws Exception {
        // test while all things OK
        assertNull("the return value is not correct", persistence.addEntries(entries, true));

        List actual = persistence.retrieveAllEntries();
        assertEquals("The number of items in list should be correct.", entries.length, actual.size());
        TestHelper.sortDataObjects(actual);

        // Verify instances
        for (int i = 0; i < entries.length; ++i) {
            TestHelper.assertEquals(entries[i], (ExpenseEntry) actual.get(i));
        }
    }

    /**
     * Accuracy tests for addEntries() in non-atomic mode.
     *
     * @throws Exception to JUnit
     */
    public void testAddEntries2() throws Exception {
        // test while all things OK
        ExpenseEntry[] ret = persistence.addEntries(entries, false);
        assertEquals("the return value is not correct", 0, ret.length);

        List actual = persistence.retrieveAllEntries();
        assertEquals("The number of items in list should be correct.", entries.length, actual.size());
        TestHelper.sortDataObjects(actual);

        // Verify instances
        for (int i = 0; i < entries.length; ++i) {
            TestHelper.assertEquals(entries[i], (ExpenseEntry) actual.get(i));
        }

        // test while some entries already existed
        TestHelper.initDatabase();
        persistence.addEntry(entries[0]);
        persistence.addEntry(entries[1]);

        ExpenseEntry[] fails = persistence.addEntries(entries, false);
        assertEquals("the array size is not correct", 2, fails.length);
        TestHelper.assertEquals(entries[0], fails[0]);
        TestHelper.assertEquals(entries[1], fails[1]);

        for (int i = 0; i < entries.length; ++i) {
            assertExpenseEntryExist(entries[i]);
            assertRejectReasonsExist(entries[i]);
        }
    }

    /**
     * Accuracy tests for deleteEntries() in atomic mode.
     *
     * @throws Exception to JUnit
     */
    public void testDeleteEntries1() throws Exception {
        // test while all entries already existed
        TestHelper.initDatabase();
        persistence.addEntries(entries, true);

        int[] ret = persistence.deleteEntries(new int[] { entries[0].getId(), entries[1].getId(), entries[2].getId() },
                true);
        assertNull("the return value is not correct", ret);

        for (int i = 0; i < entries.length; ++i) {
            assertExpenseEntryNotExist(entries[i]);
            assertRejectReasonsNotExist(entries[i]);
        }
    }

    /**
     * Accuracy tests for deleteEntries() in non-atomic mode.
     *
     * @throws Exception to JUnit
     */
    public void testDeleteEntries2() throws Exception {
        // test while some entries not existed
        persistence.addEntry(entries[1]);

        int[] ret = persistence.deleteEntries(new int[] { entries[0].getId(), entries[1].getId(), entries[2].getId() },
                false);
        assertEquals("The number of items in list should be correct.", 2, ret.length);
        assertEquals("The return value is not correct.", entries[0].getId(), ret[0]);
        assertEquals("The return value is not correct.", entries[2].getId(), ret[1]);

        for (int i = 0; i < entries.length; ++i) {
            assertExpenseEntryNotExist(entries[i]);
            assertRejectReasonsNotExist(entries[i]);
        }

        // test while all entries already existed
        TestHelper.initDatabase();
        persistence.addEntries(entries, true);
        ret = persistence.deleteEntries(new int[] { entries[0].getId(), entries[1].getId(), entries[2].getId() }, false);
        assertEquals("the return value is not correct", 0, ret.length);

        for (int i = 0; i < entries.length; ++i) {
            assertExpenseEntryNotExist(entries[i]);
            assertRejectReasonsNotExist(entries[i]);
        }
    }

    /**
     * Accuracy tests for updateEntries() in atomic mode.
     *
     * @throws Exception to JUnit
     */
    public void testUpdateEntries1() throws Exception {
        // test while all entries already existed
        TestHelper.initDatabase();
        persistence.addEntries(entries, true);

        for (int i = 0; i < entries.length; ++i) {
            entries[i].setAmount(entry.getAmount());
            entries[i].setDate(entry.getDate());
            entries[i].setDescription(entry.getDescription());
            entries[i].setExpenseType(entry.getExpenseType());
            entries[i].setStatus(entry.getStatus());
        }

        ExpenseEntry[] ret = persistence.updateEntries(entries, true);
        assertNull("the return value is not correct", ret);

        List result = persistence.retrieveAllEntries();
        assertEquals("The number of items in list should be correct.", entries.length, result.size());

        for (int i = 0; i < entries.length; ++i) {
            TestHelper.assertEquals(entries[i], (ExpenseEntry) result.get(i));
        }
    }

    /**
     * Accuracy tests for updateEntries() in non-atomic mode.
     *
     * @throws Exception to JUnit
     */
    public void testUpdateEntries2() throws Exception {
        // test while some entries not existed
        persistence.addEntry(entries[1]);
        entries[1].setAmount(entry.getAmount());
        entries[1].setDate(entry.getDate());
        entries[1].setDescription(entry.getDescription());
        entries[1].setExpenseType(entry.getExpenseType());
        entries[1].setStatus(entry.getStatus());

        ExpenseEntry[] ret = persistence.updateEntries(entries, false);
        assertEquals("The number of items in list should be correct.", 2, ret.length);
        TestHelper.assertEquals(entries[0], ret[0]);
        TestHelper.assertEquals(entries[2], ret[1]);

        List result = persistence.retrieveAllEntries();
        assertEquals("The number of items in list should be correct.", 1, result.size());
        TestHelper.assertEquals(entries[1], (ExpenseEntry) result.get(0));

        // test while all entries already existed
        TestHelper.initDatabase();
        persistence.addEntries(entries, true);

        for (int i = 0; i < entries.length; ++i) {
            entries[i].setAmount(entry.getAmount());
            entries[i].setDate(entry.getDate());
            entries[i].setDescription(entry.getDescription());
            entries[i].setExpenseType(entry.getExpenseType());
            entries[i].setStatus(entry.getStatus());
        }

        ret = persistence.updateEntries(entries, false);
        assertEquals("the return value is not correct", 0, ret.length);
        result = persistence.retrieveAllEntries();
        assertEquals("The number of items in list should be correct.", entries.length, result.size());

        for (int i = 0; i < entries.length; ++i) {
            TestHelper.assertEquals(entries[i], (ExpenseEntry) result.get(i));
        }
    }

    /**
     * Accuracy tests for retrieveEntries() in atomic mode.
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveEntries1() throws Exception {
        // test while some entries not existed
        persistence.addEntry(entries[0]);

        ExpenseEntry[] ret;

        try {
            ret = persistence.retrieveEntries(new int[] { entries[0].getId(), entries[1].getId(), entries[2].getId() },
                    true);
            fail("PersistenceException should be thrown");
        } catch (PersistenceException e) {
            // success
        }

        List result = persistence.retrieveAllEntries();
        assertEquals("The number of items in list should be correct.", 1, result.size());
        TestHelper.assertEquals(entries[0], (ExpenseEntry) result.get(0));

        // test while all entries already existed
        TestHelper.initDatabase();
        persistence.addEntries(entries, true);
        ret = persistence.retrieveEntries(new int[] { entries[0].getId(), entries[1].getId(), entries[2].getId() }, true);
        assertEquals("the return value is not correct", entries.length, ret.length);

        for (int i = 0; i < entries.length; ++i) {
            TestHelper.assertEquals(entries[i], ret[i]);
        }

        result = persistence.retrieveAllEntries();
        assertEquals("The number of items in list should be correct.", entries.length, result.size());

        for (int i = 0; i < entries.length; ++i) {
            TestHelper.assertEquals(entries[i], (ExpenseEntry) result.get(i));
        }
    }

    /**
     * Accuracy tests for retrieveEntries() in non-atomic mode.
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveEntries2() throws Exception {
        // test while some entries not existed
        persistence.addEntry(entries[1]);

        ExpenseEntry[] ret = persistence.retrieveEntries(new int[] {
                    entries[0].getId(), entries[1].getId(), entries[2].getId()
                }, false);
        assertEquals("The number of items in list should be correct.", 1, ret.length);
        TestHelper.assertEquals(entries[1], ret[0]);

        List result = persistence.retrieveAllEntries();
        assertEquals("The number of items in list should be correct.", 1, result.size());
        TestHelper.assertEquals(entries[1], (ExpenseEntry) result.get(0));

        // test while all entries already existed
        TestHelper.initDatabase();
        persistence.addEntries(entries, true);
        ret = persistence.retrieveEntries(new int[] { entries[0].getId(), entries[1].getId(), entries[2].getId() },
                false);
        assertEquals("the return value is not correct", entries.length, ret.length);

        for (int i = 0; i < entries.length; ++i) {
            TestHelper.assertEquals(entries[i], ret[i]);
        }

        result = persistence.retrieveAllEntries();
        assertEquals("The number of items in list should be correct.", entries.length, result.size());

        for (int i = 0; i < entries.length; ++i) {
            TestHelper.assertEquals(entries[i], (ExpenseEntry) result.get(i));
        }
    }

    /**
     * Accuracy tests for searchEntries() with FieldMatchCriteria.
     *
     * @throws Exception to JUnit
     */
    public void testSearchEntriesMatch() throws Exception {
        // prepare data
        entries[2].setCreationUser(entries[0].getCreationUser());
        persistence.addEntries(entries, true);

        // invoke
        Criteria criteria = new FieldMatchCriteria(FieldMatchCriteria.CREATION_USER_FIELD, entries[0].getCreationUser());
        ExpenseEntry[] ret = persistence.searchEntries(criteria);

        // check the search result
        assertEquals("the return value is not correct", 2, ret.length);

        List list = Arrays.asList(ret);
        TestHelper.sortDataObjects(list);
        TestHelper.assertEquals(entries[0], (ExpenseEntry) list.get(0));
        TestHelper.assertEquals(entries[2], (ExpenseEntry) list.get(1));

        // check the data in database
        list = persistence.retrieveAllEntries();
        TestHelper.sortDataObjects(list);
        assertEquals("the data is not correct", entries.length, list.size());

        for (int i = 0; i < entries.length; ++i) {
            TestHelper.assertEquals(entries[i], (ExpenseEntry) list.get(i));
        }
    }

    /**
     * Accuracy tests for searchEntries() with NotCriteria.
     *
     * @throws Exception to JUnit
     */
    public void testSearchEntriesNot() throws Exception {
        // prepare data
        persistence.addEntries(entries, true);

        // invoke
        Criteria betweenCriteria = new FieldBetweenCriteria(FieldBetweenCriteria.AMOUNT_FIELD,
                entries[0].getAmount().add(new BigDecimal(10)), entries[2].getAmount().add(new BigDecimal(10)));
        Criteria criteria = new NotCriteria(betweenCriteria);
        ExpenseEntry[] ret = persistence.searchEntries(criteria);

        // check the search result
        assertEquals("the return value is not correct", 1, ret.length);

        List list = Arrays.asList(ret);
        TestHelper.sortDataObjects(list);
        TestHelper.assertEquals(entries[0], (ExpenseEntry) list.get(0));

        // check the data in database
        list = persistence.retrieveAllEntries();
        TestHelper.sortDataObjects(list);
        assertEquals("the data is not correct", entries.length, list.size());

        for (int i = 0; i < entries.length; ++i) {
            TestHelper.assertEquals(entries[i], (ExpenseEntry) list.get(i));
        }
    }

    /**
     * Accuracy tests for searchEntries() with RejectReasonCriteria.
     *
     * @throws Exception to JUnit
     */
    public void testSearchEntriesReject() throws Exception {
        // prepare data
        int[] reasons = entries[0].getRejectReasonIds();
        entries[2].addRejectReason(new ExpenseEntryRejectReason(reasons[0]));
        persistence.addEntries(entries, true);

        // invoke
        Criteria criteria = new RejectReasonCriteria(reasons[0]);
        ExpenseEntry[] ret = persistence.searchEntries(criteria);

        // check the search result
        assertEquals("the return value is not correct", 2, ret.length);

        List list = Arrays.asList(ret);
        TestHelper.sortDataObjects(list);
        TestHelper.assertEquals(entries[0], (ExpenseEntry) list.get(0));
        TestHelper.assertEquals(entries[2], (ExpenseEntry) list.get(1));

        // check the data in database
        list = persistence.retrieveAllEntries();
        TestHelper.sortDataObjects(list);
        assertEquals("the data is not correct", entries.length, list.size());

        for (int i = 0; i < entries.length; ++i) {
            TestHelper.assertEquals(entries[i], (ExpenseEntry) list.get(i));
        }
    }

    /**
     * Accuracy tests for searchEntries() with FieldLikeCriteria.
     *
     * @throws Exception to JUnit
     */
    public void testSearchEntriesLike() throws Exception {
        // prepare data
        entries[1].setDescription("abc");
        persistence.addEntries(entries, true);

        // invoke
        Criteria criteria = new FieldLikeCriteria(FieldLikeCriteria.DESCRIPTION_FIELD, "description%");
        ExpenseEntry[] ret = persistence.searchEntries(criteria);

        // check the search result
        assertEquals("the return value is not correct", 2, ret.length);

        List list = Arrays.asList(ret);
        TestHelper.sortDataObjects(list);
        TestHelper.assertEquals(entries[0], (ExpenseEntry) list.get(0));
        TestHelper.assertEquals(entries[2], (ExpenseEntry) list.get(1));

        // check the data in database
        list = persistence.retrieveAllEntries();
        TestHelper.sortDataObjects(list);
        assertEquals("the data is not correct", entries.length, list.size());

        for (int i = 0; i < entries.length; ++i) {
            TestHelper.assertEquals(entries[i], (ExpenseEntry) list.get(i));
        }
    }

    /**
     * Accuracy tests for searchEntries() with FieldBetweenCriteria.
     *
     * @throws Exception to JUnit
     */
    public void testSearchEntriesBetween() throws Exception {
        // prepare data
        persistence.addEntries(entries, true);

        // invoke
        Criteria criteria = new FieldBetweenCriteria(FieldBetweenCriteria.AMOUNT_FIELD,
                entries[0].getAmount().add(new BigDecimal(10)), entries[2].getAmount().add(new BigDecimal(10)));
        ExpenseEntry[] ret = persistence.searchEntries(criteria);

        // check the search result
        assertEquals("the return value is not correct", 2, ret.length);

        List list = Arrays.asList(ret);
        TestHelper.sortDataObjects(list);
        TestHelper.assertEquals(entries[1], (ExpenseEntry) list.get(0));
        TestHelper.assertEquals(entries[2], (ExpenseEntry) list.get(1));

        // check the data in database
        list = persistence.retrieveAllEntries();
        TestHelper.sortDataObjects(list);
        assertEquals("the data is not correct", entries.length, list.size());

        for (int i = 0; i < entries.length; ++i) {
            TestHelper.assertEquals(entries[i], (ExpenseEntry) list.get(i));
        }
    }

    /**
     * Accuracy tests for searchEntries() with CompositeCriteria.
     *
     * @throws Exception to JUnit
     */
    public void testSearchEntriesComposite() throws Exception {
        // prepare data
        persistence.addEntries(entries, true);

        // invoke
        Criteria criteria1 = new FieldBetweenCriteria(FieldBetweenCriteria.AMOUNT_FIELD,
                entries[0].getAmount().add(new BigDecimal(10)), entries[2].getAmount().subtract(new BigDecimal(10)));
        Criteria criteria2 = new FieldMatchCriteria(FieldMatchCriteria.CREATION_USER_FIELD, entries[0].getCreationUser());
        Criteria criteria3 = new FieldMatchCriteria(FieldMatchCriteria.MODIFICATION_USER_FIELD,
                entries[2].getModificationUser());
        Criteria criteria = CompositeCriteria.getOrCompositeCriteria(new Criteria[] { criteria1, criteria2, criteria3 });
        criteria1 = new FieldLikeCriteria(FieldLikeCriteria.DESCRIPTION_FIELD, "description1");
        criteria = CompositeCriteria.getAndCompositeCriteria(criteria, criteria1);

        // check the search result
        ExpenseEntry[] ret = persistence.searchEntries(criteria);
        assertEquals("the return value is not correct", 1, ret.length);

        List list = Arrays.asList(ret);
        TestHelper.sortDataObjects(list);
        TestHelper.assertEquals(entries[1], (ExpenseEntry) list.get(0));

        // check the data in database
        list = persistence.retrieveAllEntries();
        TestHelper.sortDataObjects(list);
        assertEquals("the data is not correct", entries.length, list.size());

        for (int i = 0; i < entries.length; ++i) {
            TestHelper.assertEquals(entries[i], (ExpenseEntry) list.get(i));
        }
    }

    /**
     * Asserts the entry is consistent with the data in database.
     *
     * @param entry the expense entry to assert
     *
     * @throws Exception to JUnit
     */
    private void assertExpenseEntryExist(ExpenseEntry entry)
        throws Exception {
        // Verify record in database
        PreparedStatement ps = null;
        ResultSet resultSet = null;

        try {
            initConnection();
            ps = connection.prepareStatement("SELECT * FROM ExpenseEntries WHERE ExpenseEntriesID=?");
            ps.setInt(1, entry.getId());
            resultSet = ps.executeQuery();

            assertTrue("A record should exist.", resultSet.next());

            assertEquals("The ID should be correct.", entry.getId(), resultSet.getInt("ExpenseEntriesID"));
            assertEquals("The description should be correct.", entry.getDescription(),
                resultSet.getString("Description"));
            TestHelper.assertEquals("The creation date should be correct.", entry.getCreationDate(),
                resultSet.getDate("CreationDate"));
            TestHelper.assertEquals("The modification date should be correct.", entry.getModificationDate(),
                resultSet.getDate("ModificationDate"));
            assertEquals("The creation user should be correct.", entry.getCreationUser(),
                resultSet.getString("CreationUser"));
            assertEquals("The modification user should be correct.", entry.getModificationUser(),
                resultSet.getString("ModificationUser"));
            assertEquals("The amount of money should be correct.", entry.getAmount().doubleValue(),
                resultSet.getDouble("Amount"), 1E-9);
            assertEquals("The billable flag should be correct.", entry.isBillable() ? 1 : 0,
                resultSet.getShort("Billable"));
            assertEquals("The expense type ID should be correct.", entry.getExpenseType().getId(),
                resultSet.getInt("ExpenseTypesID"));
            assertEquals("The expense status ID should be correct.", entry.getStatus().getId(),
                resultSet.getInt("ExpenseStatusesID"));
            TestHelper.assertEquals("The date should be correct.", entry.getDate(), resultSet.getDate("EntryDate"));
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }

            if (ps != null) {
                ps.close();
            }
        }
    }

    /**
     * Asserts the entry does not exist in database.
     *
     * @param entry the expense entry to assert
     *
     * @throws Exception to JUnit
     */
    private void assertExpenseEntryNotExist(ExpenseEntry entry)
        throws Exception {
        // Verify record in database
        PreparedStatement ps = null;
        ResultSet resultSet = null;

        try {
            initConnection();
            ps = connection.prepareStatement("SELECT * FROM ExpenseEntries WHERE ExpenseEntriesID=?");
            ps.setInt(1, entry.getId());
            resultSet = ps.executeQuery();

            assertFalse("The record should not exist.", resultSet.next());
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }

            if (ps != null) {
                ps.close();
            }
        }
    }

    /**
     * Asserts the reject reasons of entry are consistent with the data in database.
     *
     * @param entry the expense entry to assert
     *
     * @throws Exception to JUnit
     */
    private void assertRejectReasonsExist(ExpenseEntry entry)
        throws Exception {
        PreparedStatement ps = null;
        ResultSet resultSet = null;

        try {
            initConnection();
            ps = connection.prepareStatement("SELECT * FROM exp_reject_reason WHERE ExpenseEntriesID=?");
            ps.setInt(1, entry.getId());
            resultSet = ps.executeQuery();

            List reasonIdList = new ArrayList();

            while (resultSet.next()) {
                TestHelper.assertEquals("The creation date should be correct.", entry.getCreationDate(),
                    resultSet.getDate("creation_date"));
                TestHelper.assertEquals("The modification date should be correct.", entry.getModificationDate(),
                    resultSet.getDate("modification_date"));
                reasonIdList.add(new Integer(resultSet.getInt("reject_reason_id")));
            }

            int[] reasonIds = new int[reasonIdList.size()];
            int index = 0;

            for (Iterator it = reasonIdList.iterator(); it.hasNext();) {
                reasonIds[index++] = ((Integer) it.next()).intValue();
            }

            Arrays.sort(reasonIds);

            int[] reasonIdsFromEntry = entry.getRejectReasonIds();
            Arrays.sort(reasonIdsFromEntry);
            assertTrue("The reason ids should be equal.", Arrays.equals(reasonIdsFromEntry, reasonIds));
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }

            if (ps != null) {
                ps.close();
            }
        }
    }

    /**
     * Asserts all of the reject reasons of entry do not exist in database.
     *
     * @param entry the expense entry to assert
     *
     * @throws Exception to JUnit
     */
    private void assertRejectReasonsNotExist(ExpenseEntry entry)
        throws Exception {
        PreparedStatement ps = null;
        ResultSet resultSet = null;

        try {
            initConnection();
            ps = connection.prepareStatement("SELECT * FROM exp_reject_reason WHERE ExpenseEntriesID=?");
            ps.setInt(1, entry.getId());
            resultSet = ps.executeQuery();

            assertFalse("The reject reasons should not exist.", resultSet.next());
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }

            if (ps != null) {
                ps.close();
            }
        }
    }
}
