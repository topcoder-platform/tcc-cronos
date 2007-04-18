/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.timetracker.entry.expense.accuracytests;

import java.util.Date;

import junit.framework.TestCase;

import com.topcoder.timetracker.entry.expense.ExpenseStatus;
import com.topcoder.timetracker.entry.expense.persistence.InformixExpenseStatusDAO;
import com.topcoder.timetracker.entry.expense.criteria.Criteria;
import com.topcoder.timetracker.entry.expense.criteria.FieldBetweenCriteria;
import com.topcoder.timetracker.entry.expense.criteria.FieldLikeCriteria;
import com.topcoder.timetracker.entry.expense.criteria.FieldMatchCriteria;

/**
 * The accuracy tests for ExpenseStatus class. Only new 2.0 requirements are tested here.
 *
 * @author kr00tki, brain_cn
 * @version 3.2
 * @version 2.0
 */
public class InformixExpenseStatusDAOAccuracyTest extends TestCase {
    /**
     * The ExpenseStatusDbPersistence instance to test on.
     */
    private InformixExpenseStatusDAO persistence = null;

    /**
     * Sets up the test environment.
     *
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
    	ConfigHelper.loadNamespaces();

        // Create the persistence
        persistence = new InformixExpenseStatusDAO();
        persistence.addStatus(createStatus(1));
        persistence.addStatus(createStatus(2));
    }

    /**
     * Clears after test.
     *
     * @throws Exception to JUnit.
     */
    protected void tearDown() throws Exception {
        TestHelper.clearDatabase();
    	ConfigHelper.releaseNamespaces();
    }

    /**
     * Creates ExpenseStatus instance.
     *
     * @param id the id of status.
     * @return the ExpenseStatus object.
     */
    private ExpenseStatus createStatus(int id) {
        ExpenseStatus status = new ExpenseStatus(id);
        status.setDescription("desc");
        status.setCreationUser("user" + id);
        status.setModificationUser("user2" + id);

        return status;
    }

    /**
     * Tests {@link ExpenseStatusDbPersistence#searchEntries(Criteria)} method accuracy.
     * Checks the description search.
     *
     * @throws Exception to JUnit.
     */
    public void testSearchEntries_Description() throws Exception {
        Criteria criteria = FieldLikeCriteria.getExpenseStatusDescriptionContainsCriteria("de");
        ExpenseStatus[] result = persistence.searchEntries(criteria);
        assertEquals("Should have 2 entries.", 2, result.length);
    }

    /**
     * Tests {@link ExpenseStatusDbPersistence#searchEntries(Criteria)} method accuracy.
     * Checks the creation user search.
     *
     * @throws Exception to JUnit.
     */
    public void testSearchEntries_CreationUser() throws Exception {
        Criteria criteria = FieldMatchCriteria.getExpenseStatusCreationUserMatchCriteria("user1");
        ExpenseStatus[] result = persistence.searchEntries(criteria);
        assertEquals("Should have 1 entry.", 1, result.length);
    }

    /**
     * Tests {@link ExpenseStatusDbPersistence#searchEntries(Criteria)} method accuracy.
     * Checks the modification user search.
     *
     * @throws Exception to JUnit.
     */
    public void testSearchEntries_ModificationUser() throws Exception {
        Criteria criteria = FieldMatchCriteria.getExpenseStatusModificationUserMatchCriteria("user21");
        ExpenseStatus[] result = persistence.searchEntries(criteria);
        assertEquals("Should have 1 entry.", 1, result.length);
    }

    /**
     * Tests {@link ExpenseStatusDbPersistence#searchEntries(Criteria)} method accuracy.
     * Checks the creation date search.
     *
     * @throws Exception to JUnit.
     */
    public void testSearchEntries_CreationDate() throws Exception {
        Date date = new Date(System.currentTimeMillis() + 3000);
        Criteria criteria = FieldBetweenCriteria.getExpenseStatusCreationDateBetweenCriteria(null, date);
        ExpenseStatus[] result = persistence.searchEntries(criteria);
        assertEquals("Should have 2 entries.", 2, result.length);
    }

    /**
     * Tests {@link ExpenseStatusDbPersistence#searchEntries(Criteria)} method accuracy.
     * Checks the modification date search.
     *
     * @throws Exception to JUnit.
     */
    public void testSearchEntries_ModificationDate() throws Exception {
        Date date = new Date(System.currentTimeMillis() + 3000);
        Criteria criteria = FieldBetweenCriteria.getExpenseStatusModificationDateBetweenCriteria(null, date);
        ExpenseStatus[] result = persistence.searchEntries(criteria);
        assertEquals("Should have 2 entries.", 2, result.length);
    }

}
