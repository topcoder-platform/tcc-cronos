/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.report.accuracytests;

import com.cronos.timetracker.report.FilterCategory;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.List;


/**
 * Accuracy test for <code>FilterCategory</code>.
 *
 * @author TCSDEVELOPER
 * @version 2.0
 */
public class AccuracyTestFilterCategory extends TestCase {
    /**
     * Returns the suit to run the test.
     *
     * @return suite to run the test.
     */
    public static Test suite() {
        return new TestSuite(AccuracyTestFilterCategory.class);
    }

    /**
     * Tests getCategoryValue().
     *
     * @throws Exception to JUnit from test cases.
     */
    public void testGetCategoryValue() throws Exception {
        assertTrue("BILLABLE is not valid.", FilterCategory.BILLABLE.getCategoryValue().equals("FILTER_BILLABLE"));
        assertTrue("CLIENT is not valid.", FilterCategory.CLIENT.getCategoryValue().equals("FILTER_CLIENT"));
        assertTrue("DATE is not valid.", FilterCategory.DATE.getCategoryValue().equals("FILTER_DATE"));
        assertTrue("EMPLOYEE is not valid.", FilterCategory.EMPLOYEE.getCategoryValue().equals("FILTER_EMPLOYEE"));
        assertTrue("PROJECT is not valid.", FilterCategory.PROJECT.getCategoryValue().equals("FILTER_PROJECT"));
        assertTrue("COMPANY is not valid.", FilterCategory.COMPANY.getCategoryValue().equals("FILTER_COMPANY"));
    }

    /**
     * Tests toString().
     *
     * @throws Exception to JUnit from test cases.
     */
    public void testToString() throws Exception {
        assertTrue("BILLABLE is not valid.", FilterCategory.BILLABLE.toString().equals("FILTER_BILLABLE"));
        assertTrue("CLIENT is not valid.", FilterCategory.CLIENT.toString().equals("FILTER_CLIENT"));
        assertTrue("DATE is not valid.", FilterCategory.DATE.toString().equals("FILTER_DATE"));
        assertTrue("EMPLOYEE of employee is not valid.", FilterCategory.EMPLOYEE.toString().equals("FILTER_EMPLOYEE"));
        assertTrue("PROJECT is not valid.", FilterCategory.PROJECT.toString().equals("FILTER_PROJECT"));
        assertTrue("COMPANY is not valid.", FilterCategory.COMPANY.toString().equals("FILTER_COMPANY"));
    }

    /**
     * Tests getFilterCategories().
     *
     * @throws Exception to JUnit from test cases.
     */
    public void testGetFilterCategories() throws Exception {
        List categories = FilterCategory.getFilterCategories();

        assertTrue("There should be five members.", categories.size() == 6);

        assertTrue("BILLABLE is not valid.", categories.contains(FilterCategory.BILLABLE));
        assertTrue("CLIENT is not valid.", categories.contains(FilterCategory.CLIENT));
        assertTrue("DATE is not valid.", categories.contains(FilterCategory.DATE));
        assertTrue("EMPLOYEE of employee is not valid.", categories.contains(FilterCategory.EMPLOYEE));
        assertTrue("PROJECT is not valid.", categories.contains(FilterCategory.PROJECT));
        assertTrue("COMPANY is not valid.", categories.contains(FilterCategory.COMPANY));
    }
}
