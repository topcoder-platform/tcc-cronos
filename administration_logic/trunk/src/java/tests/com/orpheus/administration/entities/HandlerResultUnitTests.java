/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.entities;

import junit.framework.TestCase;

/**
 * <p>
 * Test the <code>HandlerResult</code> class.
 * </p>
 *
 * @author KKD
 * @version 1.0
 */
public class HandlerResultUnitTests extends TestCase {
    /**
     * will hold the ResultCode denoting the reason for failure.
     *
     */
    private final ResultCode resultCode = ResultCode.EXCEPTION_OCCURRED;

    /**
     * will hold the message explaining the result.
     *
     */
    private final String message = "msg";

    /**
     * will hold the ResultCode denoting the reason for failure.
     *
     */
    private final Exception cause = new Exception();

    /**
     * HandlerResult instance to test.
     */
    private HandlerResult target = null;

    /**
     * <p>
     * setUp() routine. Creates test HandlerResult instance.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        target = new HandlerResult(resultCode, message);
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>HandlerResult(ResultCode, String)</code> for
     * proper behavior. Verify that all fields are set correctly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConstructor_1_accuracy() throws Exception {
        assertNotNull("Failed to get HandlerResult instance.", target);
        assertEquals("resultCode set incorrectly.", resultCode, target
                .getResultCode());
        assertEquals("cause set incorrectly.", null, target.getCause());
        assertEquals("message set incorrectly.", message, target.getMessage());
    }

    /**
     * <p>
     * Failure test. Tests the <code>HandlerResult(ResultCode, String)</code> for
     * proper behavior.
     * Verify that IllegalArgumentException should be thrown if any argument is null, or if message is empty.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConstructor_1_failure() throws Exception {
        try {
            new HandlerResult(null, message);
            fail("IllegalArgumentException should be thrown if resultCode is null.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test. Tests the <code>HandlerResult(ResultCode, String)</code> for
     * proper behavior.
     * Verify that IllegalArgumentException should be thrown if any argument is null, or if message is empty.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConstructor_2_failure() throws Exception {
        try {
            new HandlerResult(resultCode, null);
            fail("IllegalArgumentException should be thrown if message is null.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test. Tests the <code>HandlerResult(ResultCode, String)</code> for
     * proper behavior.
     * Verify that IllegalArgumentException should be thrown if any argument is null, or if message is empty.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConstructor_3_failure() throws Exception {
        try {
            new HandlerResult(resultCode, "  ");
            fail("IllegalArgumentException should be thrown if message is empty.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>HandlerResult(ResultCode, String, Exception)</code> for
     * proper behavior. Verify that all fields are set correctly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConstructorWithCause_1_accuracy() throws Exception {
        target = new HandlerResult(resultCode, message, cause);
        assertNotNull("Failed to get HandlerResult instance.", target);
        assertEquals("resultCode set incorrectly.", resultCode, target
                .getResultCode());
        assertEquals("cause set incorrectly.", cause, target.getCause());
        assertEquals("message set incorrectly.", message, target.getMessage());
    }

    /**
     * <p>
     * Failure test. Tests the <code>HandlerResult(ResultCode, String)</code> for
     * proper behavior.
     * Verify that IllegalArgumentException should be thrown if any argument is null, or if message is empty.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConstructorWithCause_1_failure() throws Exception {
        try {
            new HandlerResult(null, message, cause);
            fail("IllegalArgumentException should be thrown if resultCode is null.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test. Tests the <code>HandlerResult(ResultCode, String)</code> for
     * proper behavior.
     * Verify that IllegalArgumentException should be thrown if any argument is null, or if message is empty.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConstructorWithCause_2_failure() throws Exception {
        try {
            new HandlerResult(resultCode, null, cause);
            fail("IllegalArgumentException should be thrown if message is null.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test. Tests the <code>HandlerResult(ResultCode, String)</code> for
     * proper behavior.
     * Verify that IllegalArgumentException should be thrown if any argument is null, or if message is empty.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConstructorWithCause_3_failure() throws Exception {
        try {
            new HandlerResult(resultCode, "  ", cause);
            fail("IllegalArgumentException should be thrown if message is empty.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test. Tests the <code>HandlerResult(ResultCode, String)</code> for
     * Verify that IllegalArgumentException should be thrown if any argument is null, or if message is empty.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConstructorWithCause_4_failure() throws Exception {
        try {
            new HandlerResult(resultCode, message, null);
            fail("IllegalArgumentException should be thrown if cause is null.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>getResultCode()</code> for
     * proper behavior. Verify that ResultCode got correctly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetResultCode_1_accuracy() throws Exception {
        assertEquals("ResultCode got incorrectly.", resultCode, target
                .getResultCode());
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>getMessage()</code> for
     * proper behavior. Verify that Message got correctly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetMessage_1_accuracy() throws Exception {
        assertEquals("Message got incorrectly.", message, target.getMessage());
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>getCause()</code> for
     * proper behavior. Verify that Cause got correctly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetCause_1_accuracy() throws Exception {
        target = new HandlerResult(resultCode, message, cause);
        assertEquals("Cause got incorrectly.", cause, target.getCause());
    }
}
