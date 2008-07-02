/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.entity;

import java.util.Date;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Tests the functionality of {@link DigitalRunPoints} class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DigitalRunPointsTest extends TestCase {

    /**
     * Represents the <code>DigitalRunPoints</code> instance to test.
     */
    private DigitalRunPoints digitalRunPoints = null;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     * @throws Exception
     *             throws exception if any.
     */
    @Override
    protected void setUp() throws Exception {
        digitalRunPoints = new DigitalRunPoints();
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     * @throws Exception
     *             throws exception if any.
     */
    @Override
    protected void tearDown() throws Exception {
        digitalRunPoints = null;
    }

    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return Test suite of all tests of this class.
     */
    public static Test suite() {
        return new TestSuite(DigitalRunPointsTest.class);
    }

    /**
     * <p>
     * Accuracy test for {@link DigitalRunPoints#DigitalRunPoints()} constructor.
     * </p>
     * <p>
     * Creates an instance and checks its instantiation.
     * </p>
     *
     */
    public void test_accuracy_DigitalRunPoints() {
        // check for null
        assertNotNull("DigitalRunPoints creation failed", digitalRunPoints);
    }

    /**
     * <p>
     * Accuracy test for {@link DigitalRunPoints#setTrack(Track)} and {@link DigitalRunPoints#getTrack()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     *
     */
    public void test_accuracy_setTrack() {
        // set the value to test
        Track track = new Track();
        track.setId(10);
        digitalRunPoints.setTrack(track);
        assertEquals("getTrack and setTrack failure occured", track, digitalRunPoints.getTrack());
    }

    /**
     * <p>
     * Accuracy test for {@link DigitalRunPoints#setDigitalRunPointsStatus(DigitalRunPointsStatus)} and
     * {@link DigitalRunPoints#getDigitalRunPointsStatus()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     *
     */
    public void test_accuracy_setDigitalRunPointsStatus() {
        // set the value to test
        DigitalRunPointsStatus status = new DigitalRunPointsStatus();
        status.setId(20);
        digitalRunPoints.setDigitalRunPointsStatus(status);
        assertEquals("getDigitalRunPointsStatus and setDigitalRunPointsStatus failure occured", status,
                digitalRunPoints.getDigitalRunPointsStatus());
    }

    /**
     * <p>
     * Failure test for {@link DigitalRunPoints#setDigitalRunPointsStatus(DigitalRunPointsStatus)} method.
     * </p>
     * <p>
     * For the following inputs:
     * </p>
     *
     * <pre>
     * Input
     *      DigitalRunPointsStatus digitalRunPointsStatus : null value
     * </pre>
     *
     * <p>
     * Expected {@link IllegalArgumentException}.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void test_failure_setDigitalRunPointsStatus() throws Exception {
        try {
            digitalRunPoints.setDigitalRunPointsStatus(null);
            fail("IllegalArgumentException Expected.");
        } catch (IllegalArgumentException e) {
            // Check for the expected error message
            assertEquals("The Exception message for IllegalArgumentException is wrong", e.getMessage(),
                    "The parameter 'digitalRunPointsStatus' cannot be null.");
        }
    }

    /**
     * <p>
     * Accuracy test for {@link DigitalRunPoints#setDigitalRunPointsType(DigitalRunPointsType)} and
     * {@link DigitalRunPoints#getDigitalRunPointsType()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     *
     */
    public void test_accuracy_setDigitalRunPointsType() {
        // set the value to test
        DigitalRunPointsType type = new DigitalRunPointsType();
        type.setId(30);
        digitalRunPoints.setDigitalRunPointsType(type);
        assertEquals("getDigitalRunPointsType and setDigitalRunPointsType failure occured", type, digitalRunPoints
                .getDigitalRunPointsType());
    }

    /**
     * <p>
     * Failure test for {@link DigitalRunPoints#setDigitalRunPointsType(DigitalRunPointsType)} method.
     * </p>
     * <p>
     * For the following inputs:
     * </p>
     *
     * <pre>
     * Input
     *      DigitalRunPointsType digitalRunPointsType : null value
     * </pre>
     *
     * <p>
     * Expected {@link IllegalArgumentException}.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void test_failure_setDigitalRunPointsType() throws Exception {
        try {
            digitalRunPoints.setDigitalRunPointsType(null);
            fail("IllegalArgumentException Expected.");
        } catch (IllegalArgumentException e) {
            // Check for the expected error message
            assertEquals("The Exception message for IllegalArgumentException is wrong", e.getMessage(),
                    "The parameter 'digitalRunPointsType' cannot be null.");
        }
    }

    /**
     * <p>
     * Accuracy test for {@link DigitalRunPoints#setDigitalRunPointsReferenceType(DigitalRunPointsReferenceType)}
     * and {@link DigitalRunPoints#getDigitalRunPointsReferenceType()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     *
     */
    public void test_accuracy_setDigitalRunPointsReferenceType() {
        // set the value to test
        DigitalRunPointsReferenceType type = new DigitalRunPointsReferenceType();
        type.setId(40);
        digitalRunPoints.setDigitalRunPointsReferenceType(type);
        assertEquals("getDigitalRunPointsReferenceType and setDigitalRunPointsReferenceType failure occured",
                type, digitalRunPoints.getDigitalRunPointsReferenceType());
    }

    /**
     * <p>
     * Failure test for {@link DigitalRunPoints#setDigitalRunPointsReferenceType(DigitalRunPointsReferenceType)}
     * method.
     * </p>
     * <p>
     * For the following inputs:
     * </p>
     *
     * <pre>
     * Input
     *      DigitalRunPointsReferenceType digitalRunPointsReferenceType : null value
     * </pre>
     *
     * <p>
     * Expected {@link IllegalArgumentException}.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void test_failure_setDigitalRunPointsReferenceType() throws Exception {
        try {
            digitalRunPoints.setDigitalRunPointsReferenceType(null);
            fail("IllegalArgumentException Expected.");
        } catch (IllegalArgumentException e) {
            // Check for the expected error message
            assertEquals("The Exception message for IllegalArgumentException is wrong", e.getMessage(),
                    "The parameter 'digitalRunPointsReferenceType' cannot be null.");
        }
    }

    /**
     * <p>
     * Accuracy test for {@link DigitalRunPoints#setDigitalRunPointsOperation(DigitalRunPointsOperation)} and
     * {@link DigitalRunPoints#getDigitalRunPointsOperation()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     *
     */
    public void test_accuracy_setDigitalRunPointsOperation() {
        // set the value to test
        DigitalRunPointsOperation operation = new DigitalRunPointsOperation();
        operation.setId(50);
        digitalRunPoints.setDigitalRunPointsOperation(operation);
        assertEquals("getDigitalRunPointsOperation and setDigitalRunPointsOperation failure occured", operation,
                digitalRunPoints.getDigitalRunPointsOperation());
    }

    /**
     * <p>
     * Failure test for {@link DigitalRunPoints#setDigitalRunPointsOperation(DigitalRunPointsOperation)} method.
     * </p>
     * <p>
     * For the following inputs:
     * </p>
     *
     * <pre>
     * Input
     *      DigitalRunPointsOperation digitalRunPointsOperation : null value
     * </pre>
     *
     * <p>
     * Expected {@link IllegalArgumentException}.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void test_failure_setDigitalRunPointsOperation() throws Exception {
        try {
            digitalRunPoints.setDigitalRunPointsOperation(null);
            fail("IllegalArgumentException Expected.");
        } catch (IllegalArgumentException e) {
            // Check for the expected error message
            assertEquals("The Exception message for IllegalArgumentException is wrong", e.getMessage(),
                    "The parameter 'digitalRunPointsOperation' cannot be null.");
        }
    }

    /**
     * <p>
     * Accuracy test for {@link DigitalRunPoints#setUserId(long)} and {@link DigitalRunPoints#getUserId()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is 1.
     * </p>
     *
     */
    public void test_accuracy_setUserId() {
        // set the value to test
        digitalRunPoints.setUserId(1);
        assertEquals("getUserId and setUserId failure occured", 1, digitalRunPoints.getUserId());
    }

    /**
     * <p>
     * Failure test for {@link DigitalRunPoints#setUserId(long)} method.
     * </p>
     * <p>
     * For the following inputs:
     * </p>
     *
     * <pre>
     * Input
     *      long userId : negative value
     * </pre>
     *
     * <p>
     * Expected {@link IllegalArgumentException}.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void test_failure_setUserId() throws Exception {
        try {
            digitalRunPoints.setUserId(-1);
            fail("IllegalArgumentException Expected.");
        } catch (IllegalArgumentException e) {
            // Check for the expected error message
            assertEquals("The Exception message for IllegalArgumentException is wrong", e.getMessage(),
                    "The parameter 'userId' should be positive number");
        }
    }

    /**
     * <p>
     * Accuracy test for {@link DigitalRunPoints#setAmount(double)} and {@link DigitalRunPoints#getAmount()}
     * method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is 1.0.
     * </p>
     *
     */
    public void test_accuracy_setAmount() {
        // set the value to test
        digitalRunPoints.setAmount(1.0);
        assertEquals("getAmount and setAmount failure occured", 1.0, digitalRunPoints.getAmount());
    }

    /**
     * <p>
     * Failure test for {@link DigitalRunPoints#setAmount(double)} method.
     * </p>
     * <p>
     * For the following inputs:
     * </p>
     *
     * <pre>
     * Input
     *      double amount : null value
     * </pre>
     *
     * <p>
     * Expected {@link IllegalArgumentException}.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void test_failure_setAmount() throws Exception {
        try {
            digitalRunPoints.setAmount(-1.0);
            fail("IllegalArgumentException Expected.");
        } catch (IllegalArgumentException e) {
            // Check for the expected error message
            assertEquals("The Exception message for IllegalArgumentException is wrong", e.getMessage(),
                    "The parameter 'amount' should be positive number");
        }
    }

    /**
     * <p>
     * Accuracy test for {@link DigitalRunPoints#setApplicationDate(Date)} and
     * {@link DigitalRunPoints#getApplicationDate()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     *
     */
    public void test_accuracy_setApplicationDate() {
        // set the value to test
        Date date = new Date();
        digitalRunPoints.setApplicationDate(date);
        assertEquals("getApplicationDate and setApplicationDate failure occured", date, digitalRunPoints
                .getApplicationDate());
    }

    /**
     * <p>
     * Failure test for {@link DigitalRunPoints#setApplicationDate(Date)} method.
     * </p>
     * <p>
     * For the following inputs:
     * </p>
     *
     * <pre>
     * Input
     *      Date applicationDate : null value
     * </pre>
     *
     * <p>
     * Expected {@link IllegalArgumentException}.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void test_failure_setApplicationDate() throws Exception {
        try {
            digitalRunPoints.setApplicationDate(null);
            fail("IllegalArgumentException Expected.");
        } catch (IllegalArgumentException e) {
            // Check for the expected error message
            assertEquals("The Exception message for IllegalArgumentException is wrong", e.getMessage(),
                    "The parameter 'applicationDate' cannot be null.");
        }
    }

    /**
     * <p>
     * Accuracy test for {@link DigitalRunPoints#setAwardDate(Date)} and {@link DigitalRunPoints#getAwardDate()}
     * method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     *
     */
    public void test_accuracy_setAwardDate() {
        // set the value to test
        Date date = new Date();
        digitalRunPoints.setAwardDate(date);
        assertEquals("getAwardDate and setAwardDate failure occured", date, digitalRunPoints.getAwardDate());
    }

    /**
     * <p>
     * Failure test for {@link DigitalRunPoints#setAwardDate(Date)} method.
     * </p>
     * <p>
     * For the following inputs:
     * </p>
     *
     * <pre>
     * Input
     *      Date awardDate : null value
     * </pre>
     *
     * <p>
     * Expected {@link IllegalArgumentException}.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void test_failure_setAwardDate() throws Exception {
        try {
            digitalRunPoints.setAwardDate(null);
            fail("IllegalArgumentException Expected.");
        } catch (IllegalArgumentException e) {
            // Check for the expected error message
            assertEquals("The Exception message for IllegalArgumentException is wrong", e.getMessage(),
                    "The parameter 'awardDate' cannot be null.");
        }
    }

    /**
     * <p>
     * Accuracy test for {@link DigitalRunPoints#setReferenceId(long)} and
     * {@link DigitalRunPoints#getReferenceId()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is 1.
     * </p>
     *
     */
    public void test_accuracy_setReferenceId() {
        // set the value to test
        digitalRunPoints.setReferenceId(1);
        assertEquals("getReferenceId and setReferenceId failure occured", 1, digitalRunPoints.getReferenceId());
    }

    /**
     * <p>
     * Failure test for {@link DigitalRunPoints#setReferenceId(long)} method.
     * </p>
     * <p>
     * For the following inputs:
     * </p>
     *
     * <pre>
     * Input
     *      long referenceId : negative value
     * </pre>
     *
     * <p>
     * Expected {@link IllegalArgumentException}.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void test_failure_setReferenceId() throws Exception {
        try {
            digitalRunPoints.setReferenceId(-1);
            fail("IllegalArgumentException Expected.");
        } catch (IllegalArgumentException e) {
            // Check for the expected error message
            assertEquals("The Exception message for IllegalArgumentException is wrong", e.getMessage(),
                    "The parameter 'referenceId' should be positive number");
        }
    }

    /**
     * <p>
     * Accuracy test for {@link DigitalRunPoints#isPotential()} and {@link DigitalRunPoints#setPotential(boolean)}
     * method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is false.
     * </p>
     *
     */
    public void test_accuracy_isPotential() {
        // set the value to test
        digitalRunPoints.setPotential(false);
        assertEquals("isPotential and setPotential failure occured", false, digitalRunPoints.isPotential());
    }

    /**
     * <p>
     * Accuracy test for {@link DigitalRunPoints#setPotential(boolean)} and {@link DigitalRunPoints#isPotential()}
     * method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is true.
     * </p>
     *
     */
    public void test_accuracy_setPotential() {
        // set the value to test
        digitalRunPoints.setPotential(true);
        assertEquals("isPotential and setPotential failure occured", true, digitalRunPoints.isPotential());
    }
}
