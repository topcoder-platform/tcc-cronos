/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.report;

import junit.framework.TestCase;

import java.util.List;


/**
 * This class contains the unit tests for {@link ReportCategory}.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ReportCategoryTest extends TestCase {

    /**
     * This method tests {@link ReportCategory#getCategory()} for correctness.
     */
    public void testGetCategory() {
        assertEquals("EXPENSE", ReportCategory.EXPENSE.getCategory());
        assertEquals("TIME", ReportCategory.TIME.getCategory());
        assertEquals("TIMEEXPENSE", ReportCategory.TIMEEXPENSE.getCategory());
    }

    /**
     * This method tests {@link ReportCategory#toString()} for correctness.
     */
    public void testToString() {
        assertEquals("EXPENSE", ReportCategory.EXPENSE.toString());
        assertEquals("TIME", ReportCategory.TIME.toString());
        assertEquals("TIMEEXPENSE", ReportCategory.TIMEEXPENSE.toString());
    }

    /**
     * This method tests {@link ReportCategory#getReportCategories()}.
     */
    public void testGetReportCategories() {
        final List reportCategories = ReportCategory.getReportCategories();
        assertTrue(reportCategories.contains(ReportCategory.EXPENSE));
        assertTrue(reportCategories.contains(ReportCategory.TIME));
        assertTrue(reportCategories.contains(ReportCategory.TIMEEXPENSE));
        assertEquals(3, reportCategories.size());
        try {
            reportCategories.add(new Object());
            fail("should throw");
        } catch (UnsupportedOperationException expected) {
            //expected
        }

    }

}