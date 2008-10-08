/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices;

import junit.framework.TestCase;

/**
 * Unit test for {@link ClientServiceException}.
 *
 * @author  CaDenza
 * @version 1.0
 */
public class ClientServiceExceptionTest extends TestCase {
    /**
     * Represents the error message.
     */
    private static final String ERR_MESSAGE = "error message";

    /**
     * Represents the inner failure.
     */
    private static final Throwable INNER_ERROR = new RuntimeException();

    /**
     * Test for {@link ClientServiceException#ClientServiceException(String)}.
     */
    public void testClientServiceExceptionString() {
        ClientServiceException err = new ClientServiceException(ERR_MESSAGE);

        assertNotNull("Fail create new instance.", err);
        assertEquals("Fail configure error message.", ERR_MESSAGE, err.getMessage());
        assertNull("Fail configure inner failure.", err.getCause());
        assertTrue("Parent instance is invalid.", err instanceof ClientProjectManagementServicesException);
    }

    /**
     * Test for {@link ClientServiceException#ClientServiceException(String)}.
     */
    public void testClientServiceExceptionStringThrowable() {
        ClientServiceException err = new ClientServiceException(ERR_MESSAGE, INNER_ERROR);

        assertNotNull("Fail create new instance.", err);
        assertEquals("Fail configure error message.", ERR_MESSAGE, err.getMessage());
        assertEquals("Fail configure inner failure.", INNER_ERROR, err.getCause());
        assertTrue("Parent instance is invalid.", err instanceof ClientProjectManagementServicesException);
    }
}
