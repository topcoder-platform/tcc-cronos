/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.service.digitalrun.entity.failuretests;


import com.topcoder.service.digitalrun.entity.TrackContestResultCalculator;

import junit.framework.TestCase;


/**
 * <p>
 * This class contains the failure test cases for TrackContestResultCalcuator class.
 * </p>
 *
 * @author akhil_bansal
 * @version 1.0
 */
public class TestTrackContestResultCalculator extends TestCase {

    /**
     * <p>
     * Represents the instance of TrackContestResultCalcuator to be used.
     * </p>
     */
    private TrackContestResultCalculator resultCalc = null;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     * @throws Exception
     *         throws exception if any, raise it to JUnit.
     */
    @Override
    public void setUp() throws Exception {
        this.resultCalc = new TrackContestResultCalculator();
    }

    /**
     * <p>
     * This method tests the setClassName() method to throw IllegalArgumentException
     * for null className.
     * </p>
     */
    public void testSetClassNameNullClassName() {
        try {
            this.resultCalc.setClassName(null);
            fail("IllegalArgumentException Expected");
        } catch (IllegalArgumentException iae) {
            // pass
        }
    }

    /**
     * <p>
     * This method tests the setClassName() method to throw IllegalArgumentException
     * for empty className.
     * </p>
     */
    public void testSetClassNameEmptyClassName() {
        try {
            this.resultCalc.setClassName("");
            fail("IllegalArgumentException Expected");
        } catch (IllegalArgumentException iae) {
            // pass
        }
    }

    /**
     * <p>
     * This method tests the setClassName() method to throw IllegalArgumentException
     * for trimmed empty className.
     * </p>
     */
    public void testSetClassNameTrimmedEmptyClassName() {
        try {
            this.resultCalc.setClassName("  \t  \n");
            fail("IllegalArgumentException Expected");
        } catch (IllegalArgumentException iae) {
            // pass
        }
    }

}
