/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices.webserviceclients;

import junit.framework.TestCase;

/**
 * Test for {@link ProjectServiceClientCreationException}.
 *
 * @author  CaDenza
 * @version 1.0
 */
public class ProjectServiceClientCreationExceptionTest extends TestCase {

    /**
     * Represents the error message.
     */
    private static final String ERR_MESSAGE = "error message";

    /**
     * Represents the inner failure.
     */
    private static final Throwable INNER_ERROR = new RuntimeException();

    /**
     * Test for {@link ProjectServiceClientCreationException#
     * ProjectServiceClientCreationException(String)}.
     */
    public void testProjectServiceClientCreationExceptionString() {
        ProjectServiceClientCreationException err =
            new ProjectServiceClientCreationException(ERR_MESSAGE);

        assertNotNull("Fail create new instance.", err);
        assertEquals("Fail configure error message.", ERR_MESSAGE, err.getMessage());
        assertNull("Fail configure inner failure.", err.getCause());
        assertTrue("Parent instance is invalid.", err instanceof ServiceClientCreationException);
    }

    /**
     * Test for {@link ProjectServiceClientCreationException#
     * ProjectServiceClientCreationException(String, Throwable)}.
     */
    public void testProjectServiceClientCreationExceptionStringThrowable() {
        ProjectServiceClientCreationException err =
            new ProjectServiceClientCreationException(ERR_MESSAGE, INNER_ERROR);

        assertNotNull("Fail create new instance.", err);
        assertEquals("Fail configure error message.", ERR_MESSAGE, err.getMessage());
        assertEquals("Fail configure inner failure.", INNER_ERROR, err.getCause());
        assertTrue("Parent instance is invalid.", err instanceof ServiceClientCreationException);
    }
}
