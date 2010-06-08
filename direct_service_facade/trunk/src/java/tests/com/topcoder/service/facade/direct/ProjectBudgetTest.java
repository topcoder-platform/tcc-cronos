/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.facade.direct;

import junit.framework.TestCase;

/**
 * <p>
 * Unit tests for the <code>ProjectBudget</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ProjectBudgetTest extends TestCase {

    /**
     * <p>
     * Represents the <code>ProjectBudget</code> instance for test.
     * </p>
     */
    private ProjectBudget projectBudget;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        projectBudget = new ProjectBudget();
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>ProjectBudget</code>, expects the instance is created properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtorAccuracy() throws Exception {
        assertNotNull("Failed to create the ProjectBudget instance.", projectBudget);
    }

    /**
     * <p>
     * Accuracy test for the <code>getBillingProjectName</code> method, expects the billingProjectName is returned
     * properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetBillingProjectNameAccuracy() throws Exception {
        assertNull("Expects the billingProjectName is null by default.", projectBudget.getBillingProjectName());
    }

    /**
     * <p>
     * Accuracy test for the <code>setBillingProjectName</code> method, expects the billingProjectName is set
     * properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSetBillingProjectNameAccuracy() throws Exception {
        projectBudget.setBillingProjectName("billingProjectName");
        assertEquals("Expects the billingProjectName is set properly.", "billingProjectName", projectBudget
                .getBillingProjectName());
    }

    /**
     * <p>
     * Accuracy test for the <code>getOldBudget</code> method, expects the oldBudget is returned properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetOldBudgetAccuracy() throws Exception {
        assertEquals("Expects the oldBudget is 0.0 by default.", 0.0, projectBudget.getOldBudget());
    }

    /**
     * <p>
     * Accuracy test for the <code>setOldBudget</code> method, expects the oldBudget is set properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSetOldBudgetAccuracy() throws Exception {
        projectBudget.setOldBudget(1.23);
        assertEquals("Expects the oldBudget is set properly.", 1.23, projectBudget.getOldBudget());
    }

    /**
     * <p>
     * Accuracy test for the <code>getNewBudget</code> method, expects the newBudget is returned properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetNewBudgetAccuracy() throws Exception {
        assertEquals("Expects the newBudget is 0.0 by default.", 0.0, projectBudget.getNewBudget());
    }

    /**
     * <p>
     * Accuracy test for the <code>setNewBudget</code> method, expects the newBudget is set properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSetNewBudgetAccuracy() throws Exception {
        projectBudget.setNewBudget(1.23);
        assertEquals("Expects the newBudget is set properly.", 1.23, projectBudget.getNewBudget());
    }

    /**
     * <p>
     * Accuracy test for the <code>getChangedAmount</code> method, expects the changedAmount is returned properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetChangedAmountAccuracy() throws Exception {
        assertEquals("Expects the changedAmount is 0.0 by default.", 0.0, projectBudget.getChangedAmount());
    }

    /**
     * <p>
     * Accuracy test for the <code>setChangedAmount</code> method, expects the changedAmount is set properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSetChangedAmountAccuracy() throws Exception {
        projectBudget.setChangedAmount(1);
        assertEquals("Expects the changedAmount is set properly.", 1.0, projectBudget.getChangedAmount());
    }
}
