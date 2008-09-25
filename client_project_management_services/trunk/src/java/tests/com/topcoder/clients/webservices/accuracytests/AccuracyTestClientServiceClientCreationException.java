/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices.accuracytests;

import junit.framework.TestCase;

import com.topcoder.clients.webservices.webserviceclients.ClientServiceClientCreationException;
import com.topcoder.clients.webservices.webserviceclients.ServiceClientCreationException;

/**
 * This class contains unit tests for <code>ClientServiceClientCreationException</code> class.
 * 
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AccuracyTestClientServiceClientCreationException extends TestCase {
    /**
     * Represents the error message.
     */
    private static final String MESSAGE = "Error Msg";

    /**
     * Represents the inner cause of exception.
     */
    private static final Throwable caused = new Exception();

    /**
     * Function test : Tests <code>ClientServiceClientCreationException(String message)</code>
     * method for accuracy.
     * 
     * @throws Exception to JUnit.
     */
    public void testClientServiceClientCreationExceptionAccuracy1() throws Exception {
        ClientServiceClientCreationException e = new ClientServiceClientCreationException(MESSAGE);

        assertNotNull("Should not be null.", e);
        assertEquals("should be MESSAGE.", MESSAGE, e.getMessage());
        assertNull("Should be null.", e.getCause());
        assertTrue("Should extend from Exception.", e instanceof ServiceClientCreationException);
    }

    /**
     * Function test : Tests
     * <code>ClientServiceClientCreationException(String message, Throwable cause)</code> method
     * for accuracy.
     * 
     * @throws Exception to JUnit.
     */
    public void testClientServiceClientCreationExceptionAccuracy2() throws Exception {
        ClientServiceClientCreationException e = new ClientServiceClientCreationException(MESSAGE,
            caused);

        assertNotNull("Should not be null.", e);
        assertEquals("Should be MESSAGE.", MESSAGE, e.getMessage());
        assertEquals("Should be caused.", caused, e.getCause());
        assertTrue("Should extend from Exception.", e instanceof ServiceClientCreationException);
    }
}