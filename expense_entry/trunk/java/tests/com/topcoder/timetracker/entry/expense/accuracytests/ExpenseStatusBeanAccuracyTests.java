/**
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense.accuracytests;

import com.topcoder.timetracker.entry.expense.ExpenseStatus;
import com.topcoder.timetracker.entry.expense.criteria.Criteria;
import com.topcoder.timetracker.entry.expense.criteria.FieldMatchCriteria;
import com.topcoder.timetracker.entry.expense.ejb.ExpenseStatusBean;


/**
 * The test of ExpenseStatusBean.
 *
 * @author brain_cn
 * @version 1.0
 */
public class ExpenseStatusBeanAccuracyTests extends PersistenceTestCase {
    /** Represents the ExpenseStatusBean instance used in tests. */
    private ExpenseStatusBean instance;

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
        instance = new ExpenseStatusBean();
        context = new SessionContextTester();
        instance.ejbCreate();
        instance.setSessionContext(context);
        instance.addStatus(createStatus(1));
        instance.addStatus(createStatus(2));
    }

    /**
     * Test method for 'ExpenseStatusBean()'
     *
     * @throws Exception to JUnit.
     */
    public void testExpenseStatusBean() throws Exception {
        assertNotNull("failed to create", instance);
    }

    /**
     * Test method for 'ejbCreate()'
     *
     * @throws Exception to JUnit.
     */
    public void testEjbCreate() throws Exception {
        assertNotNull("failed to create",
                getField(instance, "expenseStatusDAO"));
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
     * Test method for 'addStatus(ExpenseStatus)'
     *
     * @throws Exception to JUnit.
     */
    public void testAddStatus() throws Exception {
    	ExpenseStatus status = createStatus(3);
        instance.addStatus(status);
        ExpenseStatus result = instance.retrieveStatus(status.getId());
		TestHelper.assertEquals(status, result);
    }

    /**
     * Test method for 'deleteStatus(long)'
     *
     * @throws Exception to JUnit.
     */
    public void testDeleteStatus() throws Exception {
    	ExpenseStatus status = createStatus(3);
        instance.addStatus(status);
        instance.deleteStatus(status.getId());
        ExpenseStatus result = instance.retrieveStatus(status.getId());
		assertNull("failed to delete", result);
    }

    /**
     * Test method for 'deleteAllStatuses()'
     *
     * @throws Exception to JUnit.
     */
    public void testDeleteAllStatuses() throws Exception {
    	ExpenseStatus status = createStatus(3);
        instance.addStatus(status);
        instance.deleteAllStatuses();
        ExpenseStatus[] result = instance.retrieveAllStatuses();
		assertEquals("failed to delete", 0, result.length);
    }

    /**
     * Test method for 'updateStatus(ExpenseStatus)'
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateStatus() throws Exception {
    	ExpenseStatus status = createStatus(3);
        instance.addStatus(status);
        status.setDescription("changed");

        instance.updateStatus(status);
        ExpenseStatus result = instance.retrieveStatus(status.getId());
		TestHelper.assertEquals(status, result);
    }

    /**
     * Test method for 'retrieveStatus(long)'
     *
     * @throws Exception to JUnit.
     */
    public void testRetrieveStatus() throws Exception {
    	ExpenseStatus status = createStatus(3);
        instance.addStatus(status);

        ExpenseStatus result = instance.retrieveStatus(status.getId());
		TestHelper.assertEquals(status, result);
    }

    /**
     * Test method for 'retrieveAllStatuses()'
     *
     * @throws Exception to JUnit.
     */
    public void testRetrieveAllStatuses() throws Exception {
    	ExpenseStatus status = createStatus(3);
        instance.addStatus(status);
        
        ExpenseStatus[] result = instance.retrieveAllStatuses();
		assertEquals("failed to retrieveAllStatuses", 3, result.length);
    }

    /**
     * Test method for 'searchEntries(Criteria)'
     *
     * @throws Exception to JUnit.
     */
    public void testSearchEntries() throws Exception {
        Criteria criteria = FieldMatchCriteria.getExpenseStatusModificationUserMatchCriteria("user21");
        ExpenseStatus[] result = instance.searchEntries(criteria);
        assertEquals("Should have 1 entry.", 1, result.length);
    }
}
