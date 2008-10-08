/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices.beans;

import junit.framework.TestCase;

/**
 * Unit test for {@link AuthorizationFailedException}.
 *s
 * @author  CaDenza
 * @version 1.0
 */
public class AuthorizationFailedExceptionTest extends TestCase {

    /**
     * Represents the error message.
     */
    private static final String ERR_MESSAGE = "error message";

    /**
     * Represents the inner failure.
     */
    private static final Throwable INNER_ERROR = new RuntimeException();

    /**
     * Test case for {@link AuthorizationFailedException#AuthorizationFailedException(String)}.
     */
    public void testAuthorizationFailedExceptionString() {
        AuthorizationFailedException err = new AuthorizationFailedException(ERR_MESSAGE);

        assertNotNull("Fail create new instance.", err);
        assertEquals("Fail setup error message.", ERR_MESSAGE, err.getMessage());
        assertNull("Fail setup inner failure.", err.getCause());
        assertTrue("Parent instance is invalid.", err instanceof Exception);
    }

    /**
     * Test case for {@link AuthorizationFailedException#AuthorizationFailedException(String, Throwable)}.
     */
    public void testAuthorizationFailedExceptionStringThrowable() {
        AuthorizationFailedException err = new AuthorizationFailedException(ERR_MESSAGE, INNER_ERROR);

        assertNotNull("Fail create new instance.", err);
        assertEquals("Fail setup error message.", ERR_MESSAGE, err.getMessage());
        assertEquals("Fail setup inner failure.", INNER_ERROR, err.getCause());
        assertTrue("Parent instance is invalid.", err instanceof Exception);
    }
}
