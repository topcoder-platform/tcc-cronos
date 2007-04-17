/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.client;

import junit.framework.TestCase;


/**
 * UnitTest for <code>PropertyOperationException</code> class.
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class PropertyOperationExceptionUnitTests extends TestCase {
    /**
     * Accuracy test of <code>PropertyOperationException(String msg)</code> method.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testPropertyOperationExceptionAccuracy1()
        throws Exception {
        PropertyOperationException e = new PropertyOperationException("msg");
        assertEquals("message is incorrect.", "msg", e.getMessage());
    }

    /**
     * Accuracy test of <code>PropertyOperationException(String msg, Throwable cause)</code> method.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testPropertyOperationExceptionAccuracy2()
        throws Exception {
        Exception cause = new Exception("cause");
        PropertyOperationException e = new PropertyOperationException("msg", cause);
        assertEquals("cause is incorrect.", cause, e.getCause());
        assertEquals("message is incorrect.", "msg, caused by cause", e.getMessage());
    }
}
