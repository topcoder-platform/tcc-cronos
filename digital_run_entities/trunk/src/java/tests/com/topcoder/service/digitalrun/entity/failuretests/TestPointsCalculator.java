/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.service.digitalrun.entity.failuretests;


import com.topcoder.service.digitalrun.entity.PointsCalculator;

import junit.framework.TestCase;


/**
 * <p>
 * This class contains the failure test cases for PointsCalculator class.
 * </p>
 *
 * @author akhil_bansal
 * @version 1.0
 */
public class TestPointsCalculator extends TestCase {

    /**
     * <p>
     * Represents the instance of PointsCalculator to be used.
     * </p>
     */
    private PointsCalculator pointsCal = null;

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
        this.pointsCal = new PointsCalculator();
    }

    /**
     * <p>
     * This method tests the setClassName() method to throw IllegalArgumentException
     * for null className.
     * </p>
     */
    public void testSetClassNameNullClassName() {
        try {
            this.pointsCal.setClassName(null);
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
            this.pointsCal.setClassName("");
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
            this.pointsCal.setClassName("  \t  \n");
            fail("IllegalArgumentException Expected");
        } catch (IllegalArgumentException iae) {
            // pass
        }
    }

}
