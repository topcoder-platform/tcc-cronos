/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.service.digitalrun.entity.failuretests;


import com.topcoder.service.digitalrun.entity.DigitalRunPoints;

import junit.framework.TestCase;


/**
 * <p>
 * This class contains the failure test cases for DigitalRunPoints class.
 * </p>
 *
 * @author akhil_bansal
 * @version 1.0
 */
public class TestDigitalRunPoints extends TestCase {

    /**
     * <p>
     * Represents the instance of DigitalRunPoints to be used.
     * </p>
     */
    private DigitalRunPoints digitalRunPoints = null;

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
        this.digitalRunPoints = new DigitalRunPoints();
    }

    /**
     * <p>
     * This method tests the setTrack() method to throw IllegalArgumentException
     * for null track.
     * </p>
     */
    public void testSetTrackNull() {
        try {
            this.digitalRunPoints.setTrack(null);
            fail("IllegalArgumentException Expected");
        } catch (IllegalArgumentException iae) {
            // pass
        }
    }

    /**
     * <p>
     * This method tests the setDigitalRunPointsStatus() method to throw IllegalArgumentException
     * for null digitalRunPointsStatus.
     * </p>
     */
    public void testSetDigitalRunPointsStatusNull() {
        try {
            this.digitalRunPoints.setDigitalRunPointsStatus(null);
            fail("IllegalArgumentException Expected");
        } catch (IllegalArgumentException iae) {
            // pass
        }
    }

    /**
     * <p>
     * This method tests the setDigitalRunPointsType() method to throw IllegalArgumentException
     * for null digitalRunPointsType.
     * </p>
     */
    public void testSetDigitalRunPointsTypeNull() {
        try {
            this.digitalRunPoints.setDigitalRunPointsType(null);
            fail("IllegalArgumentException Expected");
        } catch (IllegalArgumentException iae) {
            // pass
        }
    }

    /**
     * <p>
     * This method tests the setDigitalRunPointsReferenceType() method to throw IllegalArgumentException
     * for null digitalRunPointsReferenceType.
     * </p>
     */
    public void testSetDigitalRunPointsReferenceTypeNull() {
        try {
            this.digitalRunPoints.setDigitalRunPointsReferenceType(null);
            fail("IllegalArgumentException Expected");
        } catch (IllegalArgumentException iae) {
            // pass
        }
    }

    /**
     * <p>
     * This method tests the setDigitalRunPointsOperation() method to throw IllegalArgumentException
     * for null digitalRunPointsOperation.
     * </p>
     */
    public void testSetDigitalRunPointsOperationNull() {
        try {
            this.digitalRunPoints.setDigitalRunPointsOperation(null);
            fail("IllegalArgumentException Expected");
        } catch (IllegalArgumentException iae) {
            // pass
        }
    }

    /**
     * <p>
     * This method tests the setUserId() method to throw IllegalArgumentException
     * for negative user id.
     * </p>
     */
    public void testSetUserIdNegative() {
        try {
            this.digitalRunPoints.setUserId(-1);
            fail("IllegalArgumentException Expected");
        } catch (IllegalArgumentException iae) {
            // pass
        }
    }

    /**
     * <p>
     * This method tests the setUserId() method to throw IllegalArgumentException
     * for zero user id.
     * </p>
     */
    public void testSetUserIdZero() {
        try {
            this.digitalRunPoints.setUserId(0);
            fail("IllegalArgumentException Expected");
        } catch (IllegalArgumentException iae) {
            // pass
        }
    }

    /**
     * <p>
     * This method tests the setAmount() method to throw IllegalArgumentException
     * for negative amount.
     * </p>
     */
    public void testSetAmountNegative() {
        try {
            this.digitalRunPoints.setAmount(-0.1);
            fail("IllegalArgumentException Expected");
        } catch (IllegalArgumentException iae) {
            // pass
        }
    }

    /**
     * <p>
     * This method tests the setAmount() method to throw IllegalArgumentException
     * for zero amount.
     * </p>
     */
    public void testSetAmountZero() {
        try {
            this.digitalRunPoints.setAmount(0);
            fail("IllegalArgumentException Expected");
        } catch (IllegalArgumentException iae) {
            // pass
        }
    }

    /**
     * <p>
     * This method tests the setApplicationDate() method to throw IllegalArgumentException
     * for null application date.
     * </p>
     */
    public void testSetApplicationDateNull() {
        try {
            this.digitalRunPoints.setApplicationDate(null);
            fail("IllegalArgumentException Expected");
        } catch (IllegalArgumentException iae) {
            // pass
        }
    }

    /**
     * <p>
     * This method tests the setAwardDate() method to throw IllegalArgumentException
     * for null award date.
     * </p>
     */
    public void testSetAwardDateNull() {
        try {
            this.digitalRunPoints.setAwardDate(null);
            fail("IllegalArgumentException Expected");
        } catch (IllegalArgumentException iae) {
            // pass
        }
    }

    /**
     * <p>
     * This method tests the setReferenceId() method to throw IllegalArgumentException
     * for negative reference ID.
     * </p>
     */
    public void testSetReferenceIdNegative() {
        try {
            this.digitalRunPoints.setReferenceId(-1);
            fail("IllegalArgumentException Expected");
        } catch (IllegalArgumentException iae) {
            // pass
        }
    }

    /**
     * <p>
     * This method tests the setReferenceId() method to throw IllegalArgumentException
     * for zero reference ID.
     * </p>
     */
    public void testSetReferenceIdZero() {
        try {
            this.digitalRunPoints.setReferenceId(0);
            fail("IllegalArgumentException Expected");
        } catch (IllegalArgumentException iae) {
            // pass
        }
    }

}
