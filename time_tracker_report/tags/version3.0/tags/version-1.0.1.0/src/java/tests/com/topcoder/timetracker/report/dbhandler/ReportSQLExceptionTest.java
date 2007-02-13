/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.report.dbhandler;

import junit.framework.TestCase;


/**
 * This class contains the unit tests for {@link ReportSQLException}.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ReportSQLExceptionTest extends TestCase {

    /**
     * This method tests the constructors of {@link ReportSQLException} using valid values and checks the state of the
     * instances created is as expected.
     */
    public void testConstructors() {
        final String message = "dummy";
        final String causeMessge = "xy";
        final RuntimeException cause = new RuntimeException(causeMessge);
        //this message is generated automatically by BaseException constructor from cause parameter
        final String baseExceptionGeneratedCauseMessage = ", caused by " + causeMessge;
        ReportSQLException reportSQLException;

        //design does specify null as valid message and as valid cause
        reportSQLException = new ReportSQLException(null);
        assertNull(reportSQLException.getMessage());
        assertNull(reportSQLException.getCause());

        reportSQLException = new ReportSQLException(message);
        assertEquals(message, reportSQLException.getMessage());
        assertNull(reportSQLException.getCause());

        reportSQLException = new ReportSQLException(null, null);
        assertNull(reportSQLException.getMessage());
        assertNull(reportSQLException.getCause());

        reportSQLException = new ReportSQLException(message, null);
        assertEquals(message, reportSQLException.getMessage());
        assertNull(reportSQLException.getCause());

        reportSQLException = new ReportSQLException(null, cause);
        assertEquals("null" + baseExceptionGeneratedCauseMessage, reportSQLException.getMessage());
        assertEquals(cause, reportSQLException.getCause());

        reportSQLException = new ReportSQLException(message, cause);
        assertEquals(message + baseExceptionGeneratedCauseMessage, reportSQLException.getMessage());
        assertEquals(cause, reportSQLException.getCause());
    }

}
