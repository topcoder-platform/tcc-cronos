/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices;

import junit.framework.TestCase;

/**
 * Unit test for {@link ProjectServiceException}.
 *
 * @author  CaDenza
 * @version 1.0
 */
public class ProjectServiceExceptionTest extends TestCase {

    /**
     * Represents the error message.
     */
    private static final String ERR_MESSAGE = "error message";

    /**
     * Represents the inner failure.
     */
    private static final Throwable INNER_ERROR = new RuntimeException();

    /**
     * Test for {@link ProjectServiceException#ProjectServiceException(String)}.
     */
    public void testProjectServiceExceptionString() {
        ProjectServiceException err = new ProjectServiceException(ERR_MESSAGE);

        assertNotNull("Fail create new instance.", err);
        assertEquals("Fail configure error message.", ERR_MESSAGE, err.getMessage());
        assertNull("Fail configure inner failure.", err.getCause());
        assertTrue("Parent instance is invalid.", err instanceof ClientProjectManagementServicesException);
    }

    /**
     * Test for {@link ProjectServiceException#ProjectServiceException(String, Throwable)}.
     */
    public void testProjectServiceExceptionStringThrowable() {
        ProjectServiceException err = new ProjectServiceException(ERR_MESSAGE, INNER_ERROR);

        assertNotNull("Fail create new instance.", err);
        assertEquals("Fail configure error message.", ERR_MESSAGE, err.getMessage());
        assertEquals("Fail configure inner failure.", INNER_ERROR, err.getCause());
        assertTrue("Parent instance is invalid.", err instanceof ClientProjectManagementServicesException);
    }
}
