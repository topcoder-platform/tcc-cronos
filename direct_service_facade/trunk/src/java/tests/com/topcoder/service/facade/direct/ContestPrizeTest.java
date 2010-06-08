/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.facade.direct;

import junit.framework.TestCase;

/**
 * <p>
 * Unit tests for the <code>ContestPrize</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ContestPrizeTest extends TestCase {

    /**
     * <p>
     * Represents the <code>ContestPrize</code> instance for test.
     * </p>
     */
    private ContestPrize contestPrize;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        contestPrize = new ContestPrize();
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>ContestPrize</code>, expects the instance is created properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtorAccuracy() throws Exception {
        assertNotNull("Failed to create the ContestPrize instance.", contestPrize);
    }

    /**
     * <p>
     * Accuracy test for the <code>getContestId</code> method, expects the contestId is returned properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetContestIdAccuracy() throws Exception {
        assertEquals("Expects the contestId is 0 by default.", 0, contestPrize.getContestId());
    }

    /**
     * <p>
     * Accuracy test for the <code>setContestId</code> method, expects the contestId is set properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSetContestIdAccuracy() throws Exception {
        contestPrize.setContestId(1);
        assertEquals("Expects the contestId is set properly.", 1, contestPrize.getContestId());
    }

    /**
     * <p>
     * Accuracy test for the <code>isStudio</code> method, expects the studio is returned properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testIsStudioAccuracy() throws Exception {
        assertFalse("Expects the studio is false by default.", contestPrize.isStudio());
    }

    /**
     * <p>
     * Accuracy test for the <code>setStudio</code> method, expects the studio is set properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSetStudioAccuracy() throws Exception {
        contestPrize.setStudio(true);
        assertTrue("Expects the studio is set properly.", contestPrize.isStudio());
    }

    /**
     * <p>
     * Accuracy test for the <code>getContestPrizes</code> method, expects the contestPrizes is returned properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetContestPrizesAccuracy() throws Exception {
        assertNull("Expects the contestPrizes is null by default.", contestPrize.getContestPrizes());
    }

    /**
     * <p>
     * Accuracy test for the <code>setContestPrizes</code> method, expects the contestPrizes is set properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSetContestPrizesAccuracy() throws Exception {
        contestPrize.setContestPrizes(new double[] {123.456});

        double[] contestPrizes = contestPrize.getContestPrizes();
        assertEquals("Expects the contestPrizes is set properly.", 1, contestPrizes.length);
        assertEquals("Expects the contestPrizes is set properly.", 123.456, contestPrizes[0]);
    }

    /**
     * <p>
     * Accuracy test for the <code>getMilestonePrizes</code> method, expects the milestonePrizess is returned
     * properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetMilestonePrizesAccuracy() throws Exception {
        assertNull("Expects the milestonePrizess is null by default.", contestPrize.getMilestonePrizes());
    }

    /**
     * <p>
     * Accuracy test for the <code>setMilestonePrizes</code> method, expects the milestonePrizess is set properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSetMilestonePrizesAccuracy() throws Exception {
        contestPrize.setMilestonePrizes(new double[] {123.456});

        double[] milestonePrizes = contestPrize.getMilestonePrizes();
        assertEquals("Expects the milestonePrizess is set properly.", 1, milestonePrizes.length);
        assertEquals("Expects the milestonePrizess is set properly.", 123.456, milestonePrizes[0]);
    }

    /**
     * <p>
     * Accuracy test for the <code>isEqualMilestonePrize</code> method, expects the equalMilestonePrize is returned
     * properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testIsEqualMilestonePrizeAccuracy() throws Exception {
        assertFalse("Expects the equalMilestonePrize is false by default.", contestPrize.isEqualMilestonePrize());
    }

    /**
     * <p>
     * Accuracy test for the <code>setEqualMilestonePrize</code> method, expects the equalMilestonePrize is set
     * properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSetEqualMilestonePrizeAccuracy() throws Exception {
        contestPrize.setEqualMilestonePrize(true);
        assertTrue("Expects the equalMilestonePrize is set properly.", contestPrize.isEqualMilestonePrize());
    }
}
