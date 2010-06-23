package com.topcoder.service.facade.direct.accuracytests;
/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

import junit.framework.TestCase;

import com.topcoder.service.facade.direct.ContestPrize;
/**
 * This class contains unit tests for <code>ContestPrize</code> class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AccuracyTestContestPrize extends TestCase {
    /**
     * <p>
     * Represents ContestPrize instance to test against.
     * </p>
     */
    private ContestPrize instance = null;

    /**
     * Set Up the test environment before testing.
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        instance = new ContestPrize();
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
     * Test method for {@link ContestPrize#ContestPrize()}. It verifies the new instance is created.
     * </p>
     */
    public void testContestPrize() {
        assertNotNull("Unable to instantiate ContestPrize", instance);
    }
    /**
     * <p>
     * Test method for {@link ContestPrize#getContestId()}. It verifies the returned value is correct.
     * </p>
     */
    public void testGetContestId() {
        assertEquals("Incorrect default value for contestId", 0, instance.getContestId());

        // set a value
        instance.setContestId(8L);
        assertEquals("Incorrect contestId", 8L, instance.getContestId());
    }

    /**
     * <p>
     * Test method for {@link ContestPrize#setContestId(long)}. It verifies the assigned value is correct.
     * </p>
     */
    public void testSetContestId() {
        // set a value
        instance.setContestId(8L);
        assertEquals("Incorrect contestId", 8L, instance.getContestId());

    }
    /**
     * <p>
     * Test method for {@link ContestPrize#isStudio()}. It verifies the returned value is correct.
     * </p>
     */
    public void testisStudio() {
        assertFalse("Incorrect default value for studio", instance.isStudio());

        // set a value
        instance.setStudio(true);
        assertEquals("Incorrect studio", true, instance.isStudio());
    }

    /**
     * <p>
     * Test method for {@link ContestPrize#setStudio(boolean)}. It verifies the assigned value is correct.
     * </p>
     */
    public void testSetStudio() {
        // set a value
        instance.setStudio(true);
        assertEquals("Incorrect studio", true, instance.isStudio());

    }
    /**
     * <p>
     * Test method for {@link ContestPrize#getContestPrizes()}. It verifies the returned value is correct.
     * </p>
     */
    public void testGetContestPrizes() {
        assertNull("Incorrect default value for contestPrizes", instance.getContestPrizes());

        double[] dd = new double[]{1.11};

        // set a value
        instance.setContestPrizes(dd);
        assertEquals("Incorrect contestPrizes", dd, instance.getContestPrizes());
    }

    /**
     * <p>
     * Test method for {@link ContestPrize#setContestPrizes(double[])}. It verifies the assigned value is correct.
     * </p>
     */
    public void testSetContestPrizes() {
        double[] dd = new double[]{1.11};

        // set a value
        instance.setContestPrizes(dd);
        assertEquals("Incorrect contestPrizes", dd, instance.getContestPrizes());

        // set to null
        instance.setContestPrizes(null);
        assertNull("Incorrect contestPrizes after set to null", instance.getContestPrizes());
    }
    /**
     * <p>
     * Test method for {@link ContestPrize#getMilestonePrizes()}. It verifies the returned value is correct.
     * </p>
     */
    public void testGetMilestonePrizes() {
        assertNull("Incorrect default value for milestonePrizes", instance.getMilestonePrizes());

        double[] dd = new double[]{1.11};

        // set a value
        instance.setMilestonePrizes(dd);
        assertEquals("Incorrect milestonePrizes", dd, instance.getMilestonePrizes());
    }

    /**
     * <p>
     * Test method for {@link ContestPrize#setMilestonePrizes(double[])}. It verifies the assigned value is correct.
     * </p>
     */
    public void testSetMilestonePrizes() {
        double[] dd = new double[]{1.11};

        // set a value
        instance.setMilestonePrizes(dd);
        assertEquals("Incorrect milestonePrizes", dd, instance.getMilestonePrizes());

        // set to null
        instance.setMilestonePrizes(null);
        assertNull("Incorrect milestonePrizes after set to null", instance.getMilestonePrizes());
    }
    /**
     * <p>
     * Test method for {@link ContestPrize#isEqualMilestonePrize()}. It verifies the returned value is correct.
     * </p>
     */
    public void testGetEqualMilestonePrize() {
        assertFalse("Incorrect default value for equalMilestonePrize", instance.isEqualMilestonePrize());

        // set a value
        instance.setEqualMilestonePrize(true);
        assertEquals("Incorrect equalMilestonePrize", true, instance.isEqualMilestonePrize());
    }

    /**
     * <p>
     * Test method for {@link ContestPrize#setEqualMilestonePrize(boolean)}. It verifies the assigned value is correct.
     * </p>
     */
    public void testSetEqualMilestonePrize() {
        // set a value
        instance.setEqualMilestonePrize(true);
        assertEquals("Incorrect equalMilestonePrize", true, instance.isEqualMilestonePrize());

    }

}