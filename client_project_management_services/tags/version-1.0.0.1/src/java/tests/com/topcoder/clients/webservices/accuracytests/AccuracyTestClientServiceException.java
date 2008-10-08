/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices.accuracytests;
import junit.framework.TestCase;

import com.topcoder.clients.webservices.ClientProjectManagementServicesException;
import com.topcoder.clients.webservices.ClientServiceException;
/**
 * This class contains unit tests for <code>ClientServiceException</code> class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AccuracyTestClientServiceException extends TestCase {
    /**
     * Represents the error message.
     */
    private static final String MESSAGE = "Error Msg";

    /**
     * Represents the inner cause of exception.
     */
    private static final Throwable caused = new Exception();

    /**
     * Function test : Tests <code>ClientServiceException(String message)</code> method for
     * accuracy.
     * 
     * @throws Exception to JUnit.
     */
    public void testClientServiceExceptionAccuracy1() throws Exception {
        ClientServiceException e = new ClientServiceException(MESSAGE);

        assertNotNull("Should not be null.", e);
        assertEquals("should be MESSAGE.", MESSAGE, e.getMessage());
        assertNull("Should be null.", e.getCause());
        assertTrue("Should extend from Exception.", e instanceof ClientProjectManagementServicesException);
    }

    /**
     * Function test : Tests
     * <code>ClientServiceException(String message, Throwable cause)</code> method for
     * accuracy.
     * 
     * @throws Exception to JUnit.
     */
    public void testClientServiceExceptionAccuracy2() throws Exception {
        ClientServiceException e = new ClientServiceException(MESSAGE, caused);

        assertNotNull("Should not be null.", e);
        assertEquals("Should be MESSAGE.", MESSAGE, e.getMessage());
        assertEquals("Should be caused.", caused, e.getCause());
        assertTrue("Should extend from Exception.", e instanceof ClientProjectManagementServicesException);
    }
}