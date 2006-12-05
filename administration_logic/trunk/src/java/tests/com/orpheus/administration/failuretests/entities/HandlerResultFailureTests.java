/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.failuretests.entities;

import com.orpheus.administration.entities.HandlerResult;
import com.orpheus.administration.entities.ResultCode;
import junit.framework.TestCase;


/**
 * <p>
 * Failure test for <code>HandlerResult</code> class.
 * </p>
 *
 * @author tuenm
 * @version 1.0
 */
public class HandlerResultFailureTests extends TestCase {

    /**
     * The result code used in test cases.
     */
    private final ResultCode resultCode = ResultCode.EXCEPTION_OCCURRED;

    /**
     * The cause used in test cases.
     */
    private final Exception cause = new Exception();

    /**
     * <p>
     * Failure test of the constructor that receives 2 parameters.
     * In this test case the 'resultCode' parameter is null. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testConstructor1NullRC() throws Exception {
        try {
            new HandlerResult(null, "message");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // fine
        }
    }

    /**
     * <p>
     * Failure test of the constructor that receives 2 parameters.
     * In this test case the 'message' parameter is null. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testConstructor1NullMessage() throws Exception {
        try {
            new HandlerResult(resultCode, null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // fine
        }
    }

    /**
     * <p>
     * Failure test of the constructor that receives 2 parameters.
     * In this test case the 'message' parameter is empty. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testConstructor1EmptyMessage() throws Exception {
        try {
            new HandlerResult(resultCode, " ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // fine
        }
    }

    /**
     * <p>
     * Failure test of the constructor that receives 3 parameters.
     * In this test case the 'resultCode' parameter is null. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testConstructor2NullRC() throws Exception {
        try {
            new HandlerResult(null, "message", cause);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // fine
        }
    }

    /**
     * <p>
     * Failure test of the constructor that receives 3 parameters.
     * In this test case the 'message' parameter is null. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testConstructor2NullMessage() throws Exception {
        try {
            new HandlerResult(resultCode, null, cause);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // fine
        }
    }

    /**
     * <p>
     * Failure test of the constructor that receives 3 parameters.
     * In this test case the 'message' parameter is empty. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testConstructor2EmptyMessage() throws Exception {
        try {
            new HandlerResult(resultCode, " ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // fine
        }
    }

    /**
     * <p>
     * Failure test of the constructor that receives 3 parameters.
     * In this test case the 'message' parameter is empty. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testConstructor2NullCause() throws Exception {
        try {
            new HandlerResult(resultCode, "message", null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // fine
        }
    }
}
