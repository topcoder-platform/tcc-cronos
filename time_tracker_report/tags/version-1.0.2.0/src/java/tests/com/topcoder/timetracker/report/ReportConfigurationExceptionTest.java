/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.report;

import junit.framework.TestCase;


/**
 * This class contains the unit tests for {@link ReportConfigurationException}.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ReportConfigurationExceptionTest extends TestCase {

    /**
     * This method tests the constructors of {@link ReportConfigurationException} using valid values and checks the
     * state of the instances created is as expected.
     */
    public void testConstructors() {
        final String message = "dummy";
        final String causeMessge = "xy";
        final RuntimeException cause = new RuntimeException(causeMessge);
        //this message is generated automatically by BaseException constructor from cause parameter
        final String baseExceptionGeneratedCauseMessage = ", caused by " + causeMessge;
        ReportConfigurationException reportConfigurationException;

        //design does specify null as valid message and as valid cause
        reportConfigurationException = new ReportConfigurationException(null);
        assertNull(reportConfigurationException.getMessage());
        assertNull(reportConfigurationException.getCause());

        reportConfigurationException = new ReportConfigurationException(message);
        assertEquals(message, reportConfigurationException.getMessage());
        assertNull(reportConfigurationException.getCause());

        reportConfigurationException = new ReportConfigurationException(null, null);
        assertNull(reportConfigurationException.getMessage());
        assertNull(reportConfigurationException.getCause());

        reportConfigurationException = new ReportConfigurationException(message, null);
        assertEquals(message, reportConfigurationException.getMessage());
        assertNull(reportConfigurationException.getCause());

        reportConfigurationException = new ReportConfigurationException(null, cause);
        assertEquals("null" + baseExceptionGeneratedCauseMessage, reportConfigurationException.getMessage());
        assertEquals(cause, reportConfigurationException.getCause());

        reportConfigurationException = new ReportConfigurationException(message, cause);
        assertEquals(message + baseExceptionGeneratedCauseMessage, reportConfigurationException.getMessage());
        assertEquals(cause, reportConfigurationException.getCause());
    }

}
