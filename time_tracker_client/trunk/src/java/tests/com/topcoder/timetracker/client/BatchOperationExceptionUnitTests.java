/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.client;

import junit.framework.TestCase;


/**
 * UnitTest for <code>BatchOperationException</code> class.
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class BatchOperationExceptionUnitTests extends TestCase {
    /**
     * Accuracy test of <code>BatchOperationException(String msg, boolean[])</code> method.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testBatchOperationExceptionAccuracy1()
        throws Exception {
        BatchOperationException e = new BatchOperationException("msg", new boolean[] {false});
        assertEquals("message is incorrect.", "msg", e.getMessage());

        assertEquals("The result have length 1.", 1, e.getResult().length);
        assertFalse("The result is false.", e.getResult()[0]);
    }

    /**
     * Accuracy test of <code>BatchOperationException(String msg, Throwable cause, boolean[])</code> method.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testBatchOperationExceptionAccuracy2()
        throws Exception {
        Exception cause = new Exception("cause");
        BatchOperationException e = new BatchOperationException("msg", cause, new boolean[] {false});
        assertEquals("cause is incorrect.", cause, e.getCause());
        assertEquals("message is incorrect.", "msg, caused by cause", e.getMessage());
        assertEquals("The result have length 1.", 1, e.getResult().length);
        assertFalse("The result is false.", e.getResult()[0]);
    }
}
