/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.report;

import junit.framework.TestCase;


/**
 * This class contains the unit tests for {@link ReportNotFoundException}.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ReportNotFoundExceptionTest extends TestCase {

    /**
     * This method tests the constructors of {@link ReportNotFoundException} using valid values and checks the state of
     * the instances created is as expected.
     */
    public void testConstructors() {
        final String message = "dummy";
        final String causeMessge = "xy";
        final RuntimeException cause = new RuntimeException(causeMessge);
        //this message is generated automatically by BaseException constructor from cause parameter
        final String baseExceptionGeneratedCauseMessage = ", caused by " + causeMessge;
        ReportNotFoundException reportNotFoundException;

        //design does specify null as valid message and as valid cause
        reportNotFoundException = new ReportNotFoundException(null);
        assertNull(reportNotFoundException.getMessage());
        assertNull(reportNotFoundException.getCause());

        reportNotFoundException = new ReportNotFoundException(message);
        assertEquals(message, reportNotFoundException.getMessage());
        assertNull(reportNotFoundException.getCause());

        reportNotFoundException = new ReportNotFoundException(null, null);
        assertNull(reportNotFoundException.getMessage());
        assertNull(reportNotFoundException.getCause());

        reportNotFoundException = new ReportNotFoundException(message, null);
        assertEquals(message, reportNotFoundException.getMessage());
        assertNull(reportNotFoundException.getCause());

        reportNotFoundException = new ReportNotFoundException(null, cause);
        assertEquals("null" + baseExceptionGeneratedCauseMessage, reportNotFoundException.getMessage());
        assertEquals(cause, reportNotFoundException.getCause());

        reportNotFoundException = new ReportNotFoundException(message, cause);
        assertEquals(message + baseExceptionGeneratedCauseMessage, reportNotFoundException.getMessage());
        assertEquals(cause, reportNotFoundException.getCause());
    }

}
