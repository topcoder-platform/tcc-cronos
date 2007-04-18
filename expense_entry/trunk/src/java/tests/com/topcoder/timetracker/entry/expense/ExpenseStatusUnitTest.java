/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense;

import com.topcoder.timetracker.common.TimeTrackerBean;

import junit.framework.TestCase;


/**
 * <p>
 * Tests functionality and error cases of <code>ExpenseStatus</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class ExpenseStatusUnitTest extends TestCase {
    /** Represents the <code>ExpenseStatus</code> instance used for testing. */
    private ExpenseStatus expenseStatus = null;

    /**
     * <p>
     * Sets up the test environment. The test instance is created.
     * </p>
     */
    protected void setUp() {
        expenseStatus = new ExpenseStatus();
    }

    /**
     * <p>
     * Tests the accuracy of constructor <code>ExpenseStatus()</code>.
     * </p>
     */
    public void testExpenseStatus_Accuracy() {
        assertNotNull("The ExpenseStatus instance should be created.", expenseStatus);
        assertTrue("The ExpenseStatus instance should be instance of TimeTrackerBean.",
            expenseStatus instanceof TimeTrackerBean);
        assertEquals("The ID should be correct.", -1, expenseStatus.getId());
    }

    /**
     * <p>
     * Tests the constructor <code>ExpenseStatus(long id)</code> when the given id is not positive,
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testExpenseStatus_LongInvalidId() {
        try {
            new ExpenseStatus(-1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the accuracy of constructor <code>ExpenseStatus(long id)</code>.
     * </p>
     */
    public void testExpenseStatus_LongAccuracy() {
        expenseStatus = new ExpenseStatus(1);
        assertNotNull("The ExpenseStatus instance should be created.", expenseStatus);
        assertTrue("The ExpenseStatus instance should be instance of TimeTrackerBean.",
            expenseStatus instanceof TimeTrackerBean);
        assertEquals("The ID should be correct.", 1, expenseStatus.getId());
    }

    /**
     * <p>
     * Tests the accuracy of <code>getDescription()</code>.
     * </p>
     */
    public void testGetDescription_Accuracy() {
        assertEquals("Failed to get the description", null, expenseStatus.getDescription());
    }

    /**
     * <p>
     * Tests the method <code>setDescription(String description)</code> when the given description is null, no
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testSetDescription_NullDescription() {
        expenseStatus.setDescription(null);
        assertEquals("Failed to set the description", null, expenseStatus.getDescription());
    }

    /**
     * <p>
     * Tests the method <code>setDescription(String description)</code> when the given description is empty string, no
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testSetDescription_EmptyDescription1() {
        expenseStatus.setDescription("");
        assertEquals("Failed to set the description", "", expenseStatus.getDescription());
    }

    /**
     * <p>
     * Tests the method <code>setDescription(String description)</code> when the given description is empty string, no
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testSetDescription_EmptyDescription2() {
        expenseStatus.setDescription(" ");
        assertEquals("Failed to set the description", " ", expenseStatus.getDescription());
    }

    /**
     * <p>
     * Tests the accuracy of <code>setDescription(String description)</code>.
     * </p>
     */
    public void testSetDescription_Accuracy() {
        expenseStatus.setDescription("description");
        assertEquals("Failed to set the description", "description", expenseStatus.getDescription());
    }
}
