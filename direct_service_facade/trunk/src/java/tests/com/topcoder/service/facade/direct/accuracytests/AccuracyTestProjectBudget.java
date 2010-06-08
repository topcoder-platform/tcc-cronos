package com.topcoder.service.facade.direct.accuracytests;
/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

import junit.framework.TestCase;

import com.topcoder.service.facade.direct.ProjectBudget;
/**
 * This class contains unit tests for <code>ProjectBudget</code> class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AccuracyTestProjectBudget extends TestCase {
    /**
     * <p>
     * Represents ProjectBudget instance to test against.
     * </p>
     */
    private ProjectBudget instance = null;

    /**
     * Set Up the test environment before testing.
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        instance = new ProjectBudget();
    }

    /**
     * Clean up the test environment after testing.
     * @throws Exception to JUnit.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        instance = null;
    }

    /**
     * <p>
     * Test method for {@link ProjectBudget#ProjectBudget()}. It verifies the new instance is created.
     * </p>
     */
    public void testProjectBudget() {
        assertNotNull("Unable to instantiate ProjectBudget", instance);
    }
    /**
     * <p>
     * Test method for {@link ProjectBudget#getBillingProjectName()}. It verifies the returned value is correct.
     * </p>
     */
    public void testGetBillingProjectName() {
        assertNull("Incorrect default value for billingProjectName", instance.getBillingProjectName());

        // set a value
        instance.setBillingProjectName("myString");
        assertEquals("Incorrect billingProjectName", "myString", instance.getBillingProjectName());
    }

    /**
     * <p>
     * Test method for {@link ProjectBudget#setBillingProjectName(String)}. It verifies the assigned value is correct.
     * </p>
     */
    public void testSetBillingProjectName() {
        // set a value
        instance.setBillingProjectName("myString");
        assertEquals("Incorrect billingProjectName", "myString", instance.getBillingProjectName());

        // set to null
        instance.setBillingProjectName(null);
        assertNull("Incorrect billingProjectName after set to null", instance.getBillingProjectName());
    }
    /**
     * <p>
     * Test method for {@link ProjectBudget#getOldBudget()}. It verifies the returned value is correct.
     * </p>
     */
    public void testGetOldBudget() {
        assertEquals("Incorrect default value for oldBudget", 0D, instance.getOldBudget());

        // set a value
        instance.setOldBudget(8.888D);
        assertEquals("Incorrect oldBudget", 8.888D, instance.getOldBudget());
    }

    /**
     * <p>
     * Test method for {@link ProjectBudget#setOldBudget(double)}. It verifies the assigned value is correct.
     * </p>
     */
    public void testSetOldBudget() {
        // set a value
        instance.setOldBudget(8.888D);
        assertEquals("Incorrect oldBudget", 8.888D, instance.getOldBudget());

    }
    /**
     * <p>
     * Test method for {@link ProjectBudget#getNewBudget()}. It verifies the returned value is correct.
     * </p>
     */
    public void testGetNewBudget() {
        assertEquals("Incorrect default value for newBudget", 0D, instance.getNewBudget());

        // set a value
        instance.setNewBudget(8.888D);
        assertEquals("Incorrect newBudget", 8.888D, instance.getNewBudget());
    }

    /**
     * <p>
     * Test method for {@link ProjectBudget#setNewBudget(double)}. It verifies the assigned value is correct.
     * </p>
     */
    public void testSetNewBudget() {
        // set a value
        instance.setNewBudget(8.888D);
        assertEquals("Incorrect newBudget", 8.888D, instance.getNewBudget());

    }
    /**
     * <p>
     * Test method for {@link ProjectBudget#getChangedAmount()}. It verifies the returned value is correct.
     * </p>
     */
    public void testGetChangedAmount() {
        assertEquals("Incorrect default value for changedAmount", 0D, instance.getChangedAmount());

        // set a value
        instance.setChangedAmount(8.888D);
        assertEquals("Incorrect changedAmount", 8.888D, instance.getChangedAmount());
    }

    /**
     * <p>
     * Test method for {@link ProjectBudget#setChangedAmount(double)}. It verifies the assigned value is correct.
     * </p>
     */
    public void testSetChangedAmount() {
        // set a value
        instance.setChangedAmount(8.888D);
        assertEquals("Incorrect changedAmount", 8.888D, instance.getChangedAmount());

    }

}