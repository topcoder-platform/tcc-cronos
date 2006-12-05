/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.accuracytests.entities;

import com.orpheus.administration.entities.HandlerResult;
import com.orpheus.administration.entities.ResultCode;

import junit.framework.TestCase;

/**
 * <p>
 * Test the <code>HandlerResult</code> class.
 * </p>
 *
 * @author KKD
 * @version 1.0
 */
public class HandlerResultAccuracyTests extends TestCase {
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
    public void testConstructorAccuracy() throws Exception {
        assertNotNull("Failed to get HandlerResult instance.", target);
        assertEquals("resultCode set incorrectly.", resultCode, target
                .getResultCode());
        assertEquals("cause set incorrectly.", null, target.getCause());
        assertEquals("message set incorrectly.", message, target.getMessage());
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
    public void testConstructorWithCauseAccuracy() throws Exception {
        target = new HandlerResult(resultCode, message, cause);
        assertNotNull("Failed to get HandlerResult instance.", target);
        assertEquals("resultCode set incorrectly.", resultCode, target
                .getResultCode());
        assertEquals("cause set incorrectly.", cause, target.getCause());
        assertEquals("message set incorrectly.", message, target.getMessage());
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
    public void testGetResultCodeAccuracy() throws Exception {
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
    public void testGetMessageAccuracy() throws Exception {
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
    public void testGetCauseAccuracy() throws Exception {
        target = new HandlerResult(resultCode, message, cause);
        assertEquals("Cause got incorrectly.", cause, target.getCause());
    }
}
