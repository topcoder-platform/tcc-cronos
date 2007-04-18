/**
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense.accuracytests;

import com.topcoder.timetracker.entry.expense.ExpenseType;
import com.topcoder.timetracker.entry.expense.ExpenseTypeManager;
import com.topcoder.timetracker.entry.expense.criteria.Criteria;
import com.topcoder.timetracker.entry.expense.criteria.FieldMatchCriteria;
import com.topcoder.timetracker.entry.expense.delegate.ExpenseTypeManagerLocalDelegate;


/**
 * The test of ExpenseTypeManagerLocalDelegate.
 *
 * @author brain_cn
 * @version 1.0
 */
public class ExpenseTypeManagerLocalDelegateAccuracyTests extends PersistenceTestCase {
    /** Represents the manager instance used in tests. */
    private ExpenseTypeManager instance;

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

        // Create the manager
        instance = new ExpenseTypeManagerLocalDelegate();
    }

    /**
     * Test method for 'addType(ExpenseType)'
     *
     * @throws Exception to JUnit.
     */
    public void testAddType() throws Exception {
		ExpenseType type = createType(10, 10);
		type.setActive(true);
		instance.addType(type);

		ExpenseType[] result = instance.retrieveAllTypes();
		assertEquals("Should have 2 active entries", 2, result.length);
    }

    /**
     * Test method for 'deleteType(long)'
     *
     * @throws Exception to JUnit.
     */
    public void testDeleteType() throws Exception {
		ExpenseType type = createType(10, 10);
		type.setActive(true);
		instance.addType(type);

		type = createType(20, 10);
		instance.addType(type);

		type = createType(30, 20);
		instance.addType(type);

		instance.deleteType(type.getId());
		ExpenseType[] result = instance.retrieveAllTypes();
		assertEquals("Should have 2 active entries", 3, result.length);
    }

    /**
     * Test method for 'deleteAllTypes()'
     *
     * @throws Exception to JUnit.
     */
    public void testDeleteAllTypes() throws Exception {
		ExpenseType type = createType(10, 10);
		type.setActive(true);
		instance.addType(type);

		type = createType(20, 10);
		instance.addType(type);

		type = createType(30, 20);
		instance.addType(type);

		instance.deleteAllTypes();
		ExpenseType[] result = instance.retrieveAllTypes();
		assertEquals("Should have 2 active entries", 0, result.length);
    }

    /**
     * Test method for 'updateType(ExpenseType)'
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateType() throws Exception {
		ExpenseType type = createType(10, 10);
		type.setActive(true);
		instance.addType(type);

		type.setActive(false);
		instance.updateType(type);
		ExpenseType result = instance.retrieveType(type.getId());
		TestHelper.assertEquals(type, result);
    }

    /**
     * Test method for 'retrieveType(long)'
     *
     * @throws Exception to JUnit.
     */
    public void testRetrieveType() throws Exception {
		ExpenseType type = createType(10, 10);
		type.setActive(true);
		instance.addType(type);

		ExpenseType result = instance.retrieveType(type.getId());
		TestHelper.assertEquals(type, result);
    }

    /**
     * Test method for 'retrieveAllTypes()'
     *
     * @throws Exception to JUnit.
     */
    public void testRetrieveAllTypes() throws Exception {
		ExpenseType type = createType(10, 10);
		type.setActive(true);
		instance.addType(type);

		type = createType(20, 10);
		instance.addType(type);

		type = createType(30, 20);
		instance.addType(type);

		ExpenseType[] result = instance.retrieveAllTypes();
		assertEquals("Should have 2 active entries", 4, result.length);
    }

    /**
     * Test method for 'searchEntries(Criteria)'
     *
     * @throws Exception to JUnit.
     */
    public void testSearchEntries() throws Exception {
		ExpenseType type = createType(10, 10);
		type.setActive(true);
		instance.addType(type);

		type = createType(20, 10);
		instance.addType(type);

		type = createType(30, 20);
		instance.addType(type);

		Criteria criteria = FieldMatchCriteria.getExpenseTypeActiveMatchCriteria(false);
		ExpenseType[] result = instance.searchEntries(criteria);
		assertEquals("Should have 2 active entries", 2, result.length);
    }
}
