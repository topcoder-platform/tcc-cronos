/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.report;

import junit.framework.TestCase;

import java.util.List;


/**
 * This class contains the unit tests for {@link ReportType}.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ReportTypeTest extends TestCase {

    /**
     * This method tests {@link ReportType#getType()} for correctness.
     */
    public void testGetType() {
        assertEquals("CLIENT", ReportType.CLIENT.getType());
        assertEquals("EMPLOYEE", ReportType.EMPLOYEE.getType());
        assertEquals("PROJECT", ReportType.PROJECT.getType());
    }

    /**
     * This method tests {@link ReportType#toString()} for correctness.
     */
    public void testToString() {
        assertEquals("CLIENT", ReportType.CLIENT.toString());
        assertEquals("EMPLOYEE", ReportType.EMPLOYEE.toString());
        assertEquals("PROJECT", ReportType.PROJECT.toString());
    }

    /**
     * This method tests {@link ReportType#getReportTypes()}.
     */
    public void testGetReportTypes() {
        final List reportTypes = ReportType.getReportTypes();
        assertTrue(reportTypes.contains(ReportType.CLIENT));
        assertTrue(reportTypes.contains(ReportType.EMPLOYEE));
        assertTrue(reportTypes.contains(ReportType.PROJECT));
        assertEquals(3, reportTypes.size());
        try {
            reportTypes.add(new Object());
            fail("should throw");
        } catch (UnsupportedOperationException expected) {
            //expected
        }
    }

}
