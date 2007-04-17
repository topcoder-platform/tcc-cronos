/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.client;

import junit.framework.TestCase;


/**
 * UnitTest for <code>ClientAuditException</code> class.
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class ClientAuditExceptionUnitTests extends TestCase {
    /**
     * Accuracy test of <code>ClientAuditException(String msg)</code> method.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testClientAuditExceptionAccuracy1() throws Exception {
        ClientAuditException e = new ClientAuditException("msg");
        assertEquals("message is incorrect.", "msg", e.getMessage());
    }

    /**
     * Accuracy test of <code>ClientAuditException(String msg, Throwable cause)</code> method.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testClientAuditExceptionAccuracy2() throws Exception {
        Exception cause = new Exception("cause");
        ClientAuditException e = new ClientAuditException("msg", cause);
        assertEquals("cause is incorrect.", cause, e.getCause());
        assertEquals("message is incorrect.", "msg, caused by cause", e.getMessage());
    }
}
