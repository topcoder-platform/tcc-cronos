/**
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense.accuracytests;

import java.util.Arrays;
import java.util.List;

import com.topcoder.timetracker.entry.expense.ExpenseEntry;
import com.topcoder.timetracker.entry.expense.criteria.Criteria;
import com.topcoder.timetracker.entry.expense.criteria.FieldMatchCriteria;
import com.topcoder.timetracker.entry.expense.ejb.BasicExpenseEntryBean;


/**
 * The test of BasicExpenseEntryBean.
 *
 * @author brain_cn
 * @version 1.0
 */
public class BasicExpenseEntryBeanAccuracyTests extends PersistenceTestCase {
    /** Represents the BasicExpenseEntryBean instance used in tests. */
    private BasicExpenseEntryBean instance;

    /**
     * <p>
     * Sets up the test environment. Valid configurations are loaded. A valid manager is created. The data table is
     * truncated. A valid expense entry entry is created. One expense type and one expense status are added into the
     * database.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();

        // Create the instance
        instance = new BasicExpenseEntryBean();
        context = new SessionContextTester();
        instance.ejbCreate();
        instance.setSessionContext(context);
    }

    /**
     * Test method for 'BasicExpenseEntryBean()'
     *
     * @throws Exception to JUnit.
     */
    public void testBasicExpenseEntryBean() throws Exception {
        assertNotNull("The BasicExpenseEntryBean instance should be created.", instance);
    }

    /**
     * Test method for 'ejbCreate()'
     *
     * @throws Exception to JUnit.
     */
    public void testEjbCreate() throws Exception {
        assertNotNull("failed to create", getField(instance, "expenseEntryDAO"));
    }

    /**
     * Test method for 'ejbRemove()'
     *
     * @throws Exception to JUnit.
     */
    public void testEjbRemove() throws Exception {
    	instance.ejbRemove();
    }

    /**
     * Test method for 'ejbActivate()'
     *
     * @throws Exception to JUnit.
     */
    public void testEjbActivate() throws Exception {
    	instance.ejbActivate();
    }

    /**
     * Test method for 'ejbPassivate()'
     *
     * @throws Exception to JUnit.
     */
    public void testEjbPassivate() throws Exception {
    	instance.ejbPassivate();
    }

    /**
     * Test method for 'setSessionContext(SessionContext)'
     *
     * @throws Exception to JUnit.
     */
    public void testSetSessionContext() throws Exception {
    	instance.setSessionContext(context);
        assertEquals("The context should be set properly.", context, getField(instance, "sessionContext"));
    }

    /**
     * Test method for 'addEntry(ExpenseEntry, boolean)'
     *
     * @throws Exception to JUnit.
     */
    public void testAddEntry() throws Exception {
        assertTrue("the return value is not correct", instance.addEntry(entry, false));
        assertExpenseEntryExist(entry);

        entry.setCreationDate(null);
        entry.setModificationDate(null);
        // test while the entry already existed
        assertFalse("the return value is not correct", instance.addEntry(entry, false));
    }

    /**
     * Test method for 'updateEntry(ExpenseEntry, boolean)'
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateEntry() throws Exception {
        // test while the entry not existed
        assertFalse("the return value is not correct", instance.updateEntry(entry, false));

        // test while the entry already existed
        instance.addEntry(entry, false);
        entries[2].setId(entry.getId());
        entries[2].setCreationDate(entry.getCreationDate());
        entries[2].setCreationUser(entry.getCreationUser());
        assertTrue("the return value is not correct", instance.updateEntry(entries[2], false));

        ExpenseEntry[] actual = instance.retrieveAllEntries();
        assertEquals("The number of items in list should be correct.", 1, actual.length);
        TestHelper.assertEquals(entries[2], actual[0]);
    }

    /**
     * Test method for 'deleteEntry(long, boolean)'
     *
     * @throws Exception to JUnit.
     */
    public void testDeleteEntry() throws Exception {
        // test while the entry not existed
    	instance.addEntry(entry, false);
    	instance.deleteEntry(entry.getId(), false);
        assertNull("the return value is not correct", instance.retrieveEntry(entry.getId()));
    }

    /**
     * Test method for 'retrieveEntry(long)'
     *
     * @throws Exception to JUnit.
     */
    public void testRetrieveEntry() throws Exception {
        assertNull("the return value is not correct", instance.retrieveEntry(entry.getId()));

        // test while the entry not existed
    	instance.addEntry(entry, false);

        // test while the entry already existed
        ExpenseEntry actual = instance.retrieveEntry(entry.getId());
        TestHelper.assertEquals(entry, actual);
    }

    /**
     * Test method for 'deleteAllEntries(boolean)'
     *
     * @throws Exception to JUnit.
     */
    public void testDeleteAllEntries() throws Exception {
        instance.addEntry(entries[0], false);
        instance.addEntry(entries[1], false);
        instance.addEntry(entries[2], false);

        instance.deleteAllEntries(false);
        List result = Arrays.asList(instance.retrieveAllEntries());
        assertEquals("The number of items in list should be correct.", 0, result.size());
    }

    /**
     * Test method for 'retrieveAllEntries()'
     *
     * @throws Exception to JUnit.
     */
    public void testRetrieveAllEntries() throws Exception {
        instance.addEntry(entries[0], false);
        instance.addEntry(entries[1], false);
        instance.addEntry(entries[2], false);

        List result = Arrays.asList(instance.retrieveAllEntries());
        assertEquals("The number of items in list should be correct.", entries.length, result.size());

        for (int i = 0; i < entries.length; ++i) {
            TestHelper.assertEquals(entries[i], (ExpenseEntry) result.get(i));
        }
    }

    /**
     * Test method for 'searchEntries(Criteria)'
     *
     * @throws Exception to JUnit.
     */
    public void testSearchEntries() throws Exception {
        entries[2].setCompanyId(TestHelper.COMPANY_ID);
        instance.addEntry(entries[0], false);
        instance.addEntry(entries[1], false);
        instance.addEntry(entries[2], false);

        Criteria criteria = FieldMatchCriteria.getExpenseEntryCompanyIdMatchCriteria(TestHelper.COMPANY_ID);
        ExpenseEntry[] result = instance.searchEntries(criteria);
        assertEquals("Should return 3 entries", 3, result.length);
    }
}
