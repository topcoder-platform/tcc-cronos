/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.timetracker.entry.expense.accuracytests;

import java.util.Date;

import junit.framework.TestCase;

import com.topcoder.timetracker.entry.expense.ExpenseEntryStatus;
import com.topcoder.timetracker.entry.expense.persistence.ExpenseEntryStatusDbPersistence;
import com.topcoder.timetracker.entry.expense.search.Criteria;
import com.topcoder.timetracker.entry.expense.search.FieldBetweenCriteria;
import com.topcoder.timetracker.entry.expense.search.FieldLikeCriteria;
import com.topcoder.timetracker.entry.expense.search.FieldMatchCriteria;

/**
 * The accuracy tests for ExpenseEntryStatus class. Only new 2.0 requirements are tested here.
 *
 * @author kr00tki
 * @version 2.0
 */
public class ExpenseEntryStatusDbPersistenceAccuracyTest extends TestCase {
    /**
     * Represents the namespace to load DB connection factory configuration.
     */
    private static final String DB_NAMESPACE = "com.topcoder.timetracker.entry.expense.connection";

    /**
     * The ExpenseEntryStatusDbPersistence instance to test on.
     */
    private ExpenseEntryStatusDbPersistence persistence = null;

    /**
     * Sets up the test environment.
     *
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        TestHelper.clearConfiguration();
        TestHelper.addValidConfiguration();

        // Create the persistence
        persistence = new ExpenseEntryStatusDbPersistence("Connection", DB_NAMESPACE);
        persistence.initConnection("Connection");
        TestHelper.clearDatabase(persistence.getConnection());
        persistence.addStatus(createStatus(1));
        persistence.addStatus(createStatus(2));
    }

    /**
     * Clears after test.
     *
     * @throws Exception to JUnit.
     */
    protected void tearDown() throws Exception {
        TestHelper.clearDatabase(persistence.getConnection());
        persistence.closeConnection();
    }

    /**
     * Creates ExpenseEntryStatus instance.
     *
     * @param id the id of status.
     * @return the ExpenseEntryStatus object.
     */
    private ExpenseEntryStatus createStatus(int id) {
        ExpenseEntryStatus status = new ExpenseEntryStatus(id);
        status.setDescription("desc");
        status.setCreationUser("user" + id);
        status.setModificationUser("user2" + id);

        return status;
    }

    /**
     * Tests {@link ExpenseEntryStatusDbPersistence#searchEntries(Criteria)} method accuracy.
     * Checks the description search.
     *
     * @throws Exception to JUnit.
     */
    public void testSearchEntries_Description() throws Exception {
        Criteria criteria = FieldLikeCriteria.getExpenseStatusDescriptionContainsCriteria("de");
        ExpenseEntryStatus[] result = persistence.searchEntries(criteria);
        assertEquals("Should have 2 entries.", 2, result.length);
    }

    /**
     * Tests {@link ExpenseEntryStatusDbPersistence#searchEntries(Criteria)} method accuracy.
     * Checks the creation user search.
     *
     * @throws Exception to JUnit.
     */
    public void testSearchEntries_CreationUser() throws Exception {
        Criteria criteria = FieldMatchCriteria.getExpenseStatusCreationUserMatchCriteria("user1");
        ExpenseEntryStatus[] result = persistence.searchEntries(criteria);
        assertEquals("Should have 1 entry.", 1, result.length);
    }

    /**
     * Tests {@link ExpenseEntryStatusDbPersistence#searchEntries(Criteria)} method accuracy.
     * Checks the modification user search.
     *
     * @throws Exception to JUnit.
     */
    public void testSearchEntries_ModificationUser() throws Exception {
        Criteria criteria = FieldMatchCriteria.getExpenseStatusModificationUserMatchCriteria("user21");
        ExpenseEntryStatus[] result = persistence.searchEntries(criteria);
        assertEquals("Should have 1 entry.", 1, result.length);
    }

    /**
     * Tests {@link ExpenseEntryStatusDbPersistence#searchEntries(Criteria)} method accuracy.
     * Checks the creation date search.
     *
     * @throws Exception to JUnit.
     */
    public void testSearchEntries_CreationDate() throws Exception {
        Date date = new Date(System.currentTimeMillis() + 3000);
        Criteria criteria = FieldBetweenCriteria.getExpenseStatusCreateDateBetweenCriteria(null, date);
        ExpenseEntryStatus[] result = persistence.searchEntries(criteria);
        assertEquals("Should have 2 entries.", 2, result.length);
    }

    /**
     * Tests {@link ExpenseEntryStatusDbPersistence#searchEntries(Criteria)} method accuracy.
     * Checks the modification date search.
     *
     * @throws Exception to JUnit.
     */
    public void testSearchEntries_ModificationDate() throws Exception {
        Date date = new Date(System.currentTimeMillis() + 3000);
        Criteria criteria = FieldBetweenCriteria.getExpenseStatusModificationDateBetweenCriteria(null, date);
        ExpenseEntryStatus[] result = persistence.searchEntries(criteria);
        assertEquals("Should have 2 entries.", 2, result.length);
    }

}
