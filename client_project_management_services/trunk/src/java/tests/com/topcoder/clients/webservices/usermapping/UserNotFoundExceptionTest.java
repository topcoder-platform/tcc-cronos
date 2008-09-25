/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices.usermapping;

import junit.framework.TestCase;

/**
 * Unit test for {@link UserNotFoundException}.
 *
 * @author  CaDenza
 * @version 1.0
 */
public class UserNotFoundExceptionTest extends TestCase {

    /**
     * Represents the error message.
     */
    private static final String ERR_MESSAGE = "error message";

    /**
     * Represents the inner failure.
     */
    private static final Throwable INNER_ERROR = new RuntimeException();

    /**
     * Test for {@link UserNotFoundException#UserNotFoundException(String)}.
     */
    public void testUserNotFoundExceptionString() {
        UserNotFoundException err = new UserNotFoundException(ERR_MESSAGE);

        assertNotNull("Fail create new instance.", err);
        assertEquals("Fail configure error message.", ERR_MESSAGE, err.getMessage());
        assertNull("Fail configure inner failure.", err.getCause());
        assertTrue("Parent instance is invalid.", err instanceof UserMappingRetrievalException);
    }

    /**
     * Test for {@link UserNotFoundException#UserNotFoundException(String, Throwable)}.
     */
    public void testUserNotFoundExceptionStringThrowable() {
        UserNotFoundException err = new UserNotFoundException(ERR_MESSAGE, INNER_ERROR);

        assertNotNull("Fail create new instance.", err);
        assertEquals("Fail configure error message.", ERR_MESSAGE, err.getMessage());
        assertEquals("Fail configure inner failure.", INNER_ERROR, err.getCause());
        assertTrue("Parent instance is invalid.", err instanceof UserMappingRetrievalException);
    }
}
