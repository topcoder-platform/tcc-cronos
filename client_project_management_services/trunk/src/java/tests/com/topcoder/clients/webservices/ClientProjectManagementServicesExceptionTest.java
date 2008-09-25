/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices;

import junit.framework.TestCase;

/**
 * Unit test for {@link ClientProjectManagementServicesException}.
 *
 * @author  CaDenza
 * @version 1.0
 */
public class ClientProjectManagementServicesExceptionTest extends TestCase {

    /**
     * Represents the error message.
     */
    private static final String ERR_MESSAGE = "error message";

    /**
     * Represents the inner failure.
     */
    private static final Throwable INNER_ERROR = new RuntimeException();

    /**
     * Test for {@link ClientProjectManagementServicesException#
     * ClientProjectManagementServicesException(String)}.
     */
    public void testClientProjectManagementServicesExceptionString() {
        ClientProjectManagementServicesException err =
            new ClientProjectManagementServicesException(ERR_MESSAGE);

        assertNotNull("Fail create new instance.", err);
        assertEquals("Fail configure error message.", ERR_MESSAGE, err.getMessage());
        assertNull("Fail configure inner failure.", err.getCause());
        assertTrue("Parent instance is invalid.", err instanceof Exception);
    }

    /**
     * Test for {@link ClientProjectManagementServicesException#
     * ClientProjectManagementServicesException(String, Throwable)}.
     */
    public void testClientProjectManagementServicesExceptionStringThrowable() {
        ClientProjectManagementServicesException err =
            new ClientProjectManagementServicesException(ERR_MESSAGE, INNER_ERROR);

        assertNotNull("Fail create new instance.", err);
        assertEquals("Fail configure error message.", ERR_MESSAGE, err.getMessage());
        assertEquals("Fail configure inner failure.", INNER_ERROR, err.getCause());
        assertTrue("Parent instance is invalid.", err instanceof Exception);
    }
}
