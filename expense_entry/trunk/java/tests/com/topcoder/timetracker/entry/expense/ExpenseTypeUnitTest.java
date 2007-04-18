/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense;

import com.topcoder.timetracker.common.TimeTrackerBean;

import junit.framework.TestCase;


/**
 * <p>
 * Tests functionality and error cases of <code>ExpenseType</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class ExpenseTypeUnitTest extends TestCase {
    /** Represents the <code>ExpenseType</code> instance used for testing. */
    private ExpenseType expenseType = null;

    /**
     * <p>
     * Sets up the test environment. The test instance is created.
     * </p>
     */
    protected void setUp() {
        expenseType = new ExpenseType();
    }

    /**
     * <p>
     * Tests the accuracy of constructor <code>ExpenseType()</code>.
     * </p>
     */
    public void testExpenseType_Accuracy() {
        assertNotNull("The ExpenseType instance should be created.", expenseType);
        assertTrue("The ExpenseType instance should be instance of TimeTrackerBean.",
            expenseType instanceof TimeTrackerBean);
        assertEquals("The ID should be correct.", -1, expenseType.getId());
    }

    /**
     * <p>
     * Tests the constructor <code>ExpenseType(long id)</code> when the given id is not positive,
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testExpenseType_LongInvalidId() {
        try {
            new ExpenseType(-1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the accuracy of constructor <code>ExpenseType(long id)</code>.
     * </p>
     */
    public void testExpenseType_LongAccuracy() {
        expenseType = new ExpenseType(1);
        assertNotNull("The ExpenseType instance should be created.", expenseType);
        assertTrue("The ExpenseType instance should be instance of TimeTrackerBean.",
            expenseType instanceof TimeTrackerBean);
        assertEquals("The ID should be correct.", 1, expenseType.getId());
    }

    /**
     * <p>
     * Tests the accuracy of <code>getActive()</code>.
     * </p>
     */
    public void testGetActive_Accuracy() {
        assertFalse("Failed to get the active", expenseType.getActive());
    }

    /**
     * <p>
     * Tests the accuracy of <code>setActive(boolean active)</code>.
     * </p>
     */
    public void testSetActive_Accuracy() {
        expenseType.setActive(true);
        assertTrue("Failed to set the active", expenseType.getActive());
    }

    /**
     * <p>
     * Tests the accuracy of <code>getCompanyId()</code>.
     * </p>
     */
    public void testGetCompanyId_Accuracy() {
        assertEquals("Failed to get the companyId", -1, expenseType.getCompanyId());
    }

    /**
     * <p>
     * Tests the method <code>setCompanyId(long companyId)</code> when the given companyId is not positive,
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testSetCompanyId_InvalidCompanyId() {
        try {
            expenseType.setCompanyId(-1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the accuracy of <code>setCompanyId(long companyId)</code>.
     * </p>
     */
    public void testSetCompanyId_Accuracy() {
        expenseType.setCompanyId(1);
        assertEquals("Failed to set the companyId", 1, expenseType.getCompanyId());
    }

    /**
     * <p>
     * Tests the accuracy of <code>getDescription()</code>.
     * </p>
     */
    public void testGetDescription_Accuracy() {
        assertEquals("Failed to get the description", null, expenseType.getDescription());
    }

    /**
     * <p>
     * Tests the method <code>setDescription(String description)</code> when the given description is null, no
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testSetDescription_NullDescription() {
        expenseType.setDescription(null);
        assertEquals("Failed to set the description", null, expenseType.getDescription());
    }

    /**
     * <p>
     * Tests the method <code>setDescription(String description)</code> when the given description is empty string, no
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testSetDescription_EmptyDescription1() {
        expenseType.setDescription("");
        assertEquals("Failed to set the description", "", expenseType.getDescription());
    }

    /**
     * <p>
     * Tests the method <code>setDescription(String description)</code> when the given description is empty string, no
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testSetDescription_EmptyDescription2() {
        expenseType.setDescription(" ");
        assertEquals("Failed to set the description", " ", expenseType.getDescription());
    }

    /**
     * <p>
     * Tests the accuracy of <code>setDescription(String description)</code>.
     * </p>
     */
    public void testSetDescription_Accuracy() {
        expenseType.setDescription("description");
        assertEquals("Failed to set the description", "description", expenseType.getDescription());
    }
}
