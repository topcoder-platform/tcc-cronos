/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense.accuracytests;

import com.topcoder.timetracker.entry.expense.ExpenseEntry;
import com.topcoder.timetracker.entry.expense.persistence.InformixExpenseEntryDAO;
import com.topcoder.timetracker.entry.expense.persistence.ExpenseEntryDAO;
import com.topcoder.timetracker.entry.expense.criteria.CompositeCriteria;
import com.topcoder.timetracker.entry.expense.criteria.Criteria;
import com.topcoder.timetracker.entry.expense.criteria.FieldBetweenCriteria;
import com.topcoder.timetracker.entry.expense.criteria.FieldLikeCriteria;
import com.topcoder.timetracker.entry.expense.criteria.FieldMatchCriteria;
import com.topcoder.timetracker.entry.expense.criteria.NotCriteria;
import com.topcoder.timetracker.entry.expense.criteria.RejectReasonCriteria;
import com.topcoder.timetracker.rejectreason.RejectReason;

import junit.framework.Test;
import junit.framework.TestSuite;

import java.math.BigDecimal;

import java.util.Arrays;
import java.util.List;


/**
 * <p>
 * Accuracy tests for class ExpenseEntryDbPersistence. Here only the new methods not in version 1.0 are tested.
 * </p>
 *
 * @author -oo-
 * @author kr00tki, brain_cn
 * @version 3.2
 * @since 1.1
 */
public class InformixExpenseEntryDAOAccuracyTests extends PersistenceTestCase {
    /** Represents the ExpenseEntryDAO instance used in tests. */
    private ExpenseEntryDAO persistence;

    /**
     * <p>
     * Creates a test suite of the tests contained in this class.
     * </p>
     *
     * @return a test suite of the tests contained in this class.
     */
    public static Test suite() {
        return new TestSuite(InformixExpenseEntryDAOAccuracyTests.class);
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
    	super.setUp();
        // Create the persistence
        persistence = new InformixExpenseEntryDAO();
    }

    /**
     * Clears after test.
     *
     * @throws Exception to JUnit.
     */
    protected void tearDown() throws Exception {
    	super.tearDown();
    }

    /**
     * Accuracy tests for addEntry().
     *
     * @throws Exception to JUnit
     */
    public void testAddEntry() throws Exception {
        // test while the entry not existed
        assertTrue("the return value is not correct", persistence.addEntry(entry, false));
        assertExpenseEntryExist(entry);

        entry.setCreationDate(null);
        entry.setModificationDate(null);
        // test while the entry already existed
        assertFalse("the return value is not correct", persistence.addEntry(entry, false));
    }

    /**
     * Accuracy tests for deleteEntry().
     *
     * @throws Exception to JUnit
     */
    public void testDeleteEntry() throws Exception {
        // test while the entry not existed
        assertFalse("the return value is not correct", persistence.deleteEntry(entry.getId(), false));

        // test while the entry already existed
        persistence.addEntry(entry, false);
        assertTrue("the return value is not correct", persistence.deleteEntry(entry.getId(), false));
        assertExpenseEntryNotExist(entry);
    }

    /**
     * Accuracy tests for deleteAllEntries().
     *
     * @throws Exception to JUnit
     */
    public void testDeleteAllEntries() throws Exception {
        // test while the entry not existed
        persistence.deleteAllEntries(false);

        // test while the entry already existed
        persistence.addEntries(entries, true);
        persistence.deleteAllEntries(false);

        for (int i = 0; i < entries.length; ++i) {
            assertExpenseEntryNotExist(entries[i]);
        }
    }

    /**
     * Accuracy tests for updateEntry().
     *
     * @throws Exception to JUnit
     */
    public void testUpdateEntry() throws Exception {
        // test while the entry not existed
        assertFalse("the return value is not correct", persistence.updateEntry(entry, false));

        // test while the entry already existed
        persistence.addEntry(entry, false);
        entries[2].setId(entry.getId());
        entries[2].setCreationDate(entry.getCreationDate());
        entries[2].setCreationUser(entry.getCreationUser());
        assertTrue("the return value is not correct", persistence.updateEntry(entries[2], false));

        ExpenseEntry[] actual = persistence.retrieveAllEntries();
        assertEquals("The number of items in list should be correct.", 1, actual.length);
        TestHelper.assertEquals(entries[2], actual[0]);
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
    	ExpenseEntry[] actual = persistence.retrieveAllEntries();
        assertEquals("the return value is not correct", 0, actual.length);

        // test while some entries already existed
        persistence.addEntries(entries, true);
        actual = persistence.retrieveAllEntries();
        assertEquals("the return value is not correct", entries.length, actual.length);

        for (int i = 0; i < entries.length; ++i) {
            TestHelper.assertEquals(entries[i], actual[i]);
        }
    }

    /**
     * Accuracy tests for addEntries() in atomic mode.
     *
     * @throws Exception to JUnit
     */
    public void testAddEntries1() throws Exception {
        // test while all things OK
        persistence.addEntries(entries, true);

        ExpenseEntry[] actual = persistence.retrieveAllEntries();
        assertEquals("The number of items in list should be correct.", entries.length, actual.length);
        TestHelper.sortDataObjects(Arrays.asList(actual));

        // Verify instances
        for (int i = 0; i < entries.length; ++i) {
            TestHelper.assertEquals(entries[i], actual[i]);
        }
    }

    /**
     * Accuracy tests for addEntries() in non-atomic mode.
     *
     * @throws Exception to JUnit
     */
    public void testAddEntries2() throws Exception {
        // test while all things OK
        persistence.addEntries(entries, false);

        ExpenseEntry[] actual = persistence.retrieveAllEntries();
        assertEquals("The number of items in list should be correct.", entries.length, actual.length);
        TestHelper.sortDataObjects(Arrays.asList(actual));

        // Verify instances
        for (int i = 0; i < entries.length; ++i) {
            TestHelper.assertEquals(entries[i], actual[i]);
        }

        // test while some entries already existed
        TestHelper.initDatabase();

        TestHelper.resetCreateModificationDate(entries);
        persistence.addEntries(entries, false);

        for (int i = 0; i < entries.length; ++i) {
            assertExpenseEntryExist(entries[i]);
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

        persistence.deleteEntries(new long[] { entries[0].getId(), entries[1].getId(), entries[2].getId() },
                true);

        for (int i = 0; i < entries.length; ++i) {
            assertExpenseEntryNotExist(entries[i]);
        }
    }

    /**
     * Accuracy tests for deleteEntries() in non-atomic mode.
     *
     * @throws Exception to JUnit
     */
    public void testDeleteEntries2() throws Exception {
        // test while some entries not existed
        persistence.addEntry(entries[1], false);

        persistence.deleteEntries(new long[] { entries[1].getId()},
                false);

        for (int i = 0; i < entries.length; ++i) {
            assertExpenseEntryNotExist(entries[i]);
        }

        // test while all entries already existed
        TestHelper.initDatabase();
        TestHelper.resetCreateModificationDate(entries);
        persistence.addEntries(entries, true);
        persistence.deleteEntries(new long[] { entries[0].getId(), entries[1].getId(), entries[2].getId() }, false);
        
        for (int i = 0; i < entries.length; ++i) {
            assertExpenseEntryNotExist(entries[i]);
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

        persistence.updateEntries(entries, true);

        List result = Arrays.asList(persistence.retrieveAllEntries());
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
        persistence.addEntry(entries[1], false);
        entries[1].setAmount(entry.getAmount());
        entries[1].setDate(entry.getDate());
        entries[1].setDescription(entry.getDescription());
        entries[1].setExpenseType(entry.getExpenseType());
        entries[1].setStatus(entry.getStatus());

        persistence.updateEntries(new ExpenseEntry[] {entries[1]}, false);

        List result = Arrays.asList(persistence.retrieveAllEntries());
        assertEquals("The number of items in list should be correct.", 1, result.size());
        TestHelper.assertEquals(entries[1], (ExpenseEntry) result.get(0));

        // test while all entries already existed
        TestHelper.initDatabase();
        TestHelper.resetCreateModificationDate(entries);
        persistence.addEntries(entries, true);

        for (int i = 0; i < entries.length; ++i) {
            entries[i].setAmount(entry.getAmount());
            entries[i].setDate(entry.getDate());
            entries[i].setDescription(entry.getDescription());
            entries[i].setExpenseType(entry.getExpenseType());
            entries[i].setStatus(entry.getStatus());
        }

        persistence.updateEntries(entries, false);
        result = Arrays.asList(persistence.retrieveAllEntries());
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
        persistence.addEntry(entries[0], false);

        ExpenseEntry[] ret;

        List result = Arrays.asList(persistence.retrieveAllEntries());
        assertEquals("The number of items in list should be correct.", 1, result.size());
        TestHelper.assertEquals(entries[0], (ExpenseEntry) result.get(0));

        // test while all entries already existed
        TestHelper.initDatabase();
        TestHelper.resetCreateModificationDate(entries);
        persistence.addEntries(entries, true);
        ret = persistence.retrieveEntries(new long[] { entries[0].getId(), entries[1].getId(), entries[2].getId() });
        assertEquals("the return value is not correct", entries.length, ret.length);

        for (int i = 0; i < entries.length; ++i) {
            TestHelper.assertEquals(entries[i], ret[i]);
        }

        result = Arrays.asList(persistence.retrieveAllEntries());
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
        persistence.addEntry(entries[1], false);

        ExpenseEntry[] ret = persistence.retrieveEntries(new long[] {
                    entries[1].getId()
                });
        assertEquals("The number of items in list should be correct.", 1, ret.length);
        TestHelper.assertEquals(entries[1], ret[0]);

        List result = Arrays.asList(persistence.retrieveAllEntries());
        assertEquals("The number of items in list should be correct.", 1, result.size());
        TestHelper.assertEquals(entries[1], (ExpenseEntry) result.get(0));

        // test while all entries already existed
        TestHelper.initDatabase();
        TestHelper.resetCreateModificationDate(entries);
        persistence.addEntries(entries, true);
        ret = persistence.retrieveEntries(new long[] { entries[0].getId(), entries[1].getId(), entries[2].getId() });
        assertEquals("the return value is not correct", entries.length, ret.length);

        for (int i = 0; i < entries.length; ++i) {
            TestHelper.assertEquals(entries[i], ret[i]);
        }

        result = Arrays.asList(persistence.retrieveAllEntries());
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
        list = Arrays.asList(persistence.retrieveAllEntries());
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
        list = Arrays.asList(persistence.retrieveAllEntries());
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
    	RejectReason reason = (RejectReason) entries[0].getRejectReasons().values().iterator().next();
        entries[2].setRejectReasons(entries[0].getRejectReasons());
        persistence.addEntries(entries, true);

        // invoke
        Criteria criteria = new RejectReasonCriteria(reason.getId());
        ExpenseEntry[] ret = persistence.searchEntries(criteria);

        // check the search result
        assertEquals("the return value is not correct", 2, ret.length);

        List list = Arrays.asList(ret);
        TestHelper.sortDataObjects(list);
        TestHelper.assertEquals(entries[0], (ExpenseEntry) list.get(0));
        TestHelper.assertEquals(entries[2], (ExpenseEntry) list.get(1));

        // check the data in database
        list = Arrays.asList(persistence.retrieveAllEntries());
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
        Criteria criteria = FieldLikeCriteria.getDescriptionContainsCriteria("description");
        ExpenseEntry[] ret = persistence.searchEntries(criteria);

        // check the search result
        assertEquals("the return value is not correct", 2, ret.length);

        List list = Arrays.asList(ret);
        TestHelper.sortDataObjects(list);
        TestHelper.assertEquals(entries[0], (ExpenseEntry) list.get(0));
        TestHelper.assertEquals(entries[2], (ExpenseEntry) list.get(1));

        // check the data in database
        list = Arrays.asList(persistence.retrieveAllEntries());
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
        Criteria criteria = FieldBetweenCriteria.getAmountBetweenCriteria(
                entries[0].getAmount().add(new BigDecimal(10)), entries[2].getAmount().add(new BigDecimal(10)));
        ExpenseEntry[] ret = persistence.searchEntries(criteria);

        // check the search result
        assertEquals("the return value is not correct", 2, ret.length);

        List list = Arrays.asList(ret);
        TestHelper.sortDataObjects(list);
        TestHelper.assertEquals(entries[1], (ExpenseEntry) list.get(0));
        TestHelper.assertEquals(entries[2], (ExpenseEntry) list.get(1));

        // check the data in database
        list = Arrays.asList(persistence.retrieveAllEntries());
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
        list = Arrays.asList(persistence.retrieveAllEntries());
        TestHelper.sortDataObjects(list);
        assertEquals("the data is not correct", entries.length, list.size());

        for (int i = 0; i < entries.length; ++i) {
            TestHelper.assertEquals(entries[i], (ExpenseEntry) list.get(i));
        }
    }

    /**
     * Accuracy tests for searchEntries() with company criteria.
     *
     * @throws Exception to JUnit
     */
    public void testSearchEntries_Company() throws Exception {
        entries[2].setCompanyId(TestHelper.COMPANY_ID);
        persistence.addEntries(entries, false);

        Criteria criteria = FieldMatchCriteria.getExpenseEntryCompanyIdMatchCriteria(10);
        ExpenseEntry[] result = persistence.searchEntries(criteria);
        assertEquals("Should return 3 entries", 3, result.length);
    }
}
