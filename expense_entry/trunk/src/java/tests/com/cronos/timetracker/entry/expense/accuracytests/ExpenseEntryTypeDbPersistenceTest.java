/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved.
 */
package com.cronos.timetracker.entry.expense.accuracytests;

import java.util.Date;

import junit.framework.TestCase;

import com.cronos.timetracker.entry.expense.ExpenseEntryType;
import com.cronos.timetracker.entry.expense.persistence.ExpenseEntryTypeDbPersistence;
import com.cronos.timetracker.entry.expense.search.Criteria;
import com.cronos.timetracker.entry.expense.search.FieldBetweenCriteria;
import com.cronos.timetracker.entry.expense.search.FieldLikeCriteria;
import com.cronos.timetracker.entry.expense.search.FieldMatchCriteria;

/**
 * <p>
 * The accuracy tests for the {@link com.cronos.timetracker.entry.expense.ExpenseEntryType } class.
 * It covers only new 2.0 requirements.
 * </p>
 *
 * @author kr00tki
 * @version 2.0
 */
public class ExpenseEntryTypeDbPersistenceTest extends TestCase {
    /**
     * Represents the namespace to load DB connection factory configuration.
     */
    private static final String DB_NAMESPACE = "com.topcoder.timetracker.entry.expense.connection";

    /**
     * The ExpenseEntryTypeDbPersistence instance to test on.
     */
    private ExpenseEntryTypeDbPersistence persistence = null;

    /**
     * Sets up the test environment.
     *
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        TestHelper.clearConfiguration();
        TestHelper.addValidConfiguration();

        // Create the persistence
        persistence = new ExpenseEntryTypeDbPersistence("Connection", DB_NAMESPACE);
        persistence.initConnection("Connection");
        TestHelper.clearDatabase(persistence.getConnection());
        TestHelper.initDatabase();
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
     * Creates the test ExpenseEntryType object.
     *
     * @param typeId the id.
     * @param companyId the company id.
     * @return the ExpenseEntryType object.
     */
    private ExpenseEntryType createType(int typeId, int companyId) {
        ExpenseEntryType type = new ExpenseEntryType(typeId);
        type.setDescription("desc");
        type.setCompanyId(companyId);
        type.setCreationUser("user");
        type.setModificationUser("user2");

        return type;
    }


    /**
     * Tests {@link ExpenseEntryTypeDbPersistence#addType(ExpenseEntryType)} method accuracy.
     *
     * @throws Exception to JUnit.
     */
    public void testAddType() throws Exception {
        ExpenseEntryType type = createType(5, 10);
        // FIXME: setActive should take boolean
        type.setActive((short) 1);
        persistence.addType(type);

        ExpenseEntryType type2 = persistence.retrieveType(type.getId());
        assertEquals("Incorrect company.", type.getCompanyId(), type2.getCompanyId());
        assertEquals("Incorrect active flag", type.getActive(), type2.getActive());
        TestHelper.assertEquals(type, type2);
    }

    /**
     * Tests {@link ExpenseEntryTypeDbPersistence#updateType(ExpenseEntryType)} method accuracy.
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateType() throws Exception {
        ExpenseEntryType type = createType(5, 10);
        // FIXME: setActive should take boolean
        type.setActive((short) 0);
        persistence.addType(type);

        type.setCompanyId(20);
        // FIXME: setActive should take boolean
        type.setActive((short) 1);
        assertTrue("Should be updated.", persistence.updateType(type));

        ExpenseEntryType type2 = persistence.retrieveType(type.getId());
        assertEquals("Incorrect company.", type.getCompanyId(), type2.getCompanyId());
        assertEquals("Incorrect active flag", type.getActive(), type2.getActive());
        TestHelper.assertEquals(type, type2);

    }


    /**
     * Tests {@link ExpenseEntryTypeDbPersistence#searchEntries(Criteria)} method accuracy.
     * Check the company search.
     *
     * @throws Exception to JUnit.
     */
    public void testSearchEntriesByCompany() throws Exception {
        persistence.addType(createType(10, 30));
        persistence.addType(createType(20, 30));
        persistence.addType(createType(30, 20));

        Criteria criteria = FieldMatchCriteria.getExpenseTypeCompanyIdMatchCriteria(30);
        ExpenseEntryType[] result = persistence.searchEntries(criteria);
        assertEquals("Should have 2 entries", 2, result.length);
        assertEquals("Incorrect id.", 10, result[0].getId());
        assertEquals("Incorrect id.", 20, result[1].getId());
    }

    /**
     * Tests {@link ExpenseEntryTypeDbPersistence#searchEntries(Criteria)} method accuracy.
     * Check the creation user search.
     *
     * @throws Exception to JUnit.
     */
    public void testSearchEntriesByCreationDate() throws Exception {
        persistence.addType(createType(10, 10));
        persistence.addType(createType(20, 10));
        persistence.addType(createType(30, 20));

        Thread.sleep(2000);
        Criteria criteria = FieldBetweenCriteria.getExpenseTypeCreateDateBetweenCriteria(null, new Date());
        ExpenseEntryType[] result = persistence.searchEntries(criteria);
        assertEquals("Should have 4 entries", 4, result.length);
    }

    /**
     * Tests {@link ExpenseEntryTypeDbPersistence#searchEntries(Criteria)} method accuracy.
     * Check the modification date search.
     *
     * @throws Exception to JUnit.
     */
    public void testSearchEntriesByModificationDate() throws Exception {
        persistence.addType(createType(10, 10));
        persistence.addType(createType(20, 10));
        persistence.addType(createType(30, 20));

        Thread.sleep(2000);
        Criteria criteria = FieldBetweenCriteria.getExpensetTypeModificationDateBetweenCriteria(null, new Date());
        ExpenseEntryType[] result = persistence.searchEntries(criteria);
        assertEquals("Should have 4 entries", 4, result.length);
    }

    /**
     * Tests {@link ExpenseEntryTypeDbPersistence#searchEntries(Criteria)} method accuracy.
     * Check the modification user search.
     *
     * @throws Exception to JUnit.
     */
    public void testSearchEntriesByModificationUser() throws Exception {
        persistence.addType(createType(10, 10));

        Criteria criteria = FieldMatchCriteria.getExpenseTypeModificationUserMatchCriteria("user2");
        ExpenseEntryType[] result = persistence.searchEntries(criteria);
        assertEquals("Should have 1 entry", 1, result.length);
    }

    /**
     * Tests {@link ExpenseEntryTypeDbPersistence#searchEntries(Criteria)} method accuracy.
     * Check the creation user search.
     *
     * @throws Exception to JUnit.
     */
    public void testSearchEntriesByCreationUser() throws Exception {
        persistence.addType(createType(10, 10));

        Criteria criteria = FieldMatchCriteria.getExpenseTypeCreationUserMatchCriteria("user");
        ExpenseEntryType[] result = persistence.searchEntries(criteria);
        assertEquals("Should have 1 entry", 1, result.length);
    }

    /**
     * Tests {@link ExpenseEntryTypeDbPersistence#searchEntries(Criteria)} method accuracy.
     * Check the description search.
     *
     * @throws Exception to JUnit.
     */
    public void testSearchEntriesByDescription() throws Exception {
        persistence.addType(createType(10, 10));

        Criteria criteria = FieldLikeCriteria.getExpenseTypeDescriptionContainsCriteria("de");
        ExpenseEntryType[] result = persistence.searchEntries(criteria);
        assertEquals("Should have 1 entry", 1, result.length);
    }

    /**
     * Tests {@link ExpenseEntryTypeDbPersistence#searchEntries(Criteria)} method accuracy.
     * Check the active flag search.
     *
     * @throws Exception to JUnit.
     */
    public void testSearchEntriesByActiveFlag() throws Exception {
        ExpenseEntryType type = createType(10, 10);
        type.setActive((short) 1);
        persistence.addType(type);

        type = createType(20, 10);
        persistence.addType(type);

        type = createType(30, 20);
        type.setActive((short) 1);
        persistence.addType(type);


        Criteria criteria = FieldMatchCriteria.getBillableMatchCriteria((short) 1);
        ExpenseEntryType[] result = persistence.searchEntries(criteria);
        assertEquals("Should have 2 active entries", 2, result.length);
    }
}
