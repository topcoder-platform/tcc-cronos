/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices.webserviceclients;

import junit.framework.TestCase;

/**
 * Unit test for {@link ServiceClientCreationException}.
 *
 * @author  CaDenza
 * @version 1.0
 */
public class ServiceClientCreationExceptionTest extends TestCase {

    /**
     * Represents the error message.
     */
    private static final String ERR_MESSAGE = "error message";

    /**
     * Represents the inner failure.
     */
    private static final Throwable INNER_ERROR = new RuntimeException();

    /**
     * Test for {@link ServiceClientCreationException#ServiceClientCreationException(String)}.
     */
    public void testServiceClientCreationExceptionString() {
        ServiceClientCreationException err =
            new ServiceClientCreationException(ERR_MESSAGE);

        assertNotNull("Fail create new instance.", err);
        assertEquals("Fail configure error message.", ERR_MESSAGE, err.getMessage());
        assertNull("Fail configure error causes.", err.getCause());
        assertTrue("Parent instance is invalid.", err instanceof RuntimeException);
    }

    /**
     * Test for {@link ServiceClientCreationException#ServiceClientCreationException(String, Throwable)}.
     */
    public void testServiceClientCreationExceptionStringThrowable() {
        ServiceClientCreationException err =
            new ServiceClientCreationException(ERR_MESSAGE, INNER_ERROR);

        assertNotNull("Fail create new instance", err);
        assertEquals("Fail configure error message.", ERR_MESSAGE, err.getMessage());
        assertEquals("Fail configure error causes.", INNER_ERROR, err.getCause());
        assertTrue("Parent instance is invalid.", err instanceof RuntimeException);
    }
}
