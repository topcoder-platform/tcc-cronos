/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.report;

import junit.framework.TestCase;


/**
 * This class contains the unit tests for {@link ReportException}.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ReportExceptionTest extends TestCase {

    /**
     * This method tests the constructors of {@link ReportException} using valid values and checks the state of the
     * instances created is as expected.
     */
    public void testConstructors() {
        final String message = "dummy";
        final String causeMessge = "xy";
        final RuntimeException cause = new RuntimeException(causeMessge);
        //this message is generated automatically by BaseException constructor from cause parameter
        final String baseExceptionGeneratedCauseMessage = ", caused by " + causeMessge;
        ReportException reportException;

        //design does specify null as valid message and as valid cause
        reportException = new ReportException(null);
        assertNull(reportException.getMessage());
        assertNull(reportException.getCause());

        reportException = new ReportException(message);
        assertEquals(message, reportException.getMessage());
        assertNull(reportException.getCause());

        reportException = new ReportException(null, null);
        assertNull(reportException.getMessage());
        assertNull(reportException.getCause());

        reportException = new ReportException(message, null);
        assertEquals(message, reportException.getMessage());
        assertNull(reportException.getCause());

        reportException = new ReportException(null, cause);
        assertEquals("null" + baseExceptionGeneratedCauseMessage, reportException.getMessage());
        assertEquals(cause, reportException.getCause());

        reportException = new ReportException(message, cause);
        assertEquals(message + baseExceptionGeneratedCauseMessage, reportException.getMessage());
        assertEquals(cause, reportException.getCause());
    }

}
