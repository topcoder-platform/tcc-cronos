/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time.db;

import java.util.Date;

import com.topcoder.search.builder.filter.BetweenFilter;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.search.builder.filter.GreaterThanOrEqualToFilter;
import com.topcoder.search.builder.filter.LessThanOrEqualToFilter;
import com.topcoder.timetracker.entry.time.StringMatchType;
import com.topcoder.timetracker.entry.time.TaskType;
import com.topcoder.timetracker.entry.time.TimeStatus;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

/**
 * <p>
 * Unit test cases for DbTimeEntryFilterFactory.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class DbTimeEntryFilterFactoryTests extends TestCase {
    /**
     * <p>
     * The DbTimeEntryFilterFactory instance for testing.
     * </p>
     */
    private DbTimeEntryFilterFactory factory;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     */
    protected void setUp() {
        factory = new DbTimeEntryFilterFactory();
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     */
    protected void tearDown() {
        factory = null;
    }

    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(DbTimeEntryFilterFactoryTests.class);
    }

    /**
     * <p>
     * Tests ctor DbTimeEntryFilterFactory#DbTimeEntryFilterFactory(Map) for accuracy.
     * </p>
     *
     * <p>
     * It verifies the newly created DbTimeEntryFilterFactory instance should not be null.
     * </p>
     */
    public void testCtor() {
        assertNotNull("Failed to create a new DbTimeEntryFilterFactory instance.", factory);
    }

    /**
     * <p>
     * Tests DbTimeEntryFilterFactory#createDescriptionFilter(String,StringMatchType) for accuracy.
     * </p>
     *
     * <p>
     * It verifies DbTimeEntryFilterFactory#createDescriptionFilter(String,StringMatchType) is correct.
     * </p>
     */
    public void testCreateDescriptionFilter() {
        EqualToFilter filter = (EqualToFilter) factory.createDescriptionFilter("description",
            StringMatchType.EXACT_MATCH);
        assertEquals("Failed to create the description filter correctly.", "DESCRIPTION", filter.getName());
        assertEquals("Failed to create the description filter correctly.", "description", filter.getValue());
    }

    /**
     * <p>
     * Tests DbTimeEntryFilterFactory#createDescriptionFilter(String,StringMatchType) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when description is null and expects IllegalArgumentException.
     * </p>
     */
    public void testCreateDescriptionFilter_NullDescription() {
        try {
            factory.createDescriptionFilter(null, StringMatchType.EXACT_MATCH);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeEntryFilterFactory#createDescriptionFilter(String,StringMatchType) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when description is empty and expects IllegalArgumentException.
     * </p>
     */
    public void testCreateDescriptionFilter_EmptyDescription() {
        try {
            factory.createDescriptionFilter(" ", StringMatchType.EXACT_MATCH);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeEntryFilterFactory#createDescriptionFilter(String,StringMatchType) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when matchtype is null and expects IllegalArgumentException.
     * </p>
     */
    public void testCreateDescriptionFilter_NullMatchtype() {
        try {
            factory.createDescriptionFilter("description", null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeEntryFilterFactory#createInvoiceIdFilter(long) for accuracy.
     * </p>
     *
     * <p>
     * It verifies DbTimeEntryFilterFactory#createInvoiceIdFilter(long) is correct.
     * </p>
     */
    public void testCreateInvoiceIdFilter() {
        EqualToFilter filter = (EqualToFilter) factory.createInvoiceIdFilter(8);
        assertEquals("Failed to create the invoice id filter correctly.", "INVOICE_ID", filter.getName());
        assertEquals("Failed to create the invoice id filter correctly.", new Long(8), filter.getValue());
    }

    /**
     * <p>
     * Tests DbTimeEntryFilterFactory#createInvoiceIdFilter(long) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when invoiceId is negative and expects IllegalArgumentException.
     * </p>
     */
    public void testCreateInvoiceIdFilter_Negative() {
        try {
            factory.createInvoiceIdFilter(-8);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeEntryFilterFactory#createEntryDateFilter(Date,Date) for accuracy.
     * </p>
     *
     * <p>
     * It verifies DbTimeEntryFilterFactory#createEntryDateFilter(Date,Date) is correct.
     * </p>
     */
    public void testCreateEntryDateFilter() {
        BetweenFilter filter = (BetweenFilter) factory.createEntryDateFilter(new Date(10000), new Date(20000));
        assertEquals("Failed to create the creation date filter correctly.", "ENTRY_DATE", filter.getName());
        assertEquals("Failed to create the creation date filter correctly.", new Date(10000),
            filter.getLowerThreshold());
        assertEquals("Failed to create the creation date filter correctly.", new Date(20000),
            filter.getUpperThreshold());
    }

    /**
     * <p>
     * Tests DbTimeEntryFilterFactory#createEntryDateFilter(Date,Date) for accuracy.
     * </p>
     *
     * <p>
     * It tests the case when range start is null and expects success.
     * </p>
     */
    public void testCreateEntryDateFilter_NullRangeStart() {
        Filter filter = factory.createEntryDateFilter(null, new Date(20000));
        assertEquals("Failed to create the creation date filter correctly.", LessThanOrEqualToFilter.class,
            filter.getClass());
        assertEquals("Failed to create the creation date filter correctly.", "ENTRY_DATE",
            ((LessThanOrEqualToFilter) filter).getName());

    }

    /**
     * <p>
     * Tests DbTimeEntryFilterFactory#createEntryDateFilter(Date,Date) for accuracy.
     * </p>
     *
     * <p>
     * It tests the case when range end is null and expects success.
     * </p>
     */
    public void testCreateEntryDateFilter_NullRangeEnd() {
        Filter filter = factory.createEntryDateFilter(new Date(20000), null);
        assertEquals("Failed to create the creation date filter correctly.", GreaterThanOrEqualToFilter.class,
            filter.getClass());
        assertEquals("Failed to create the creation date filter correctly.", "ENTRY_DATE",
            ((GreaterThanOrEqualToFilter) filter).getName());
    }

    /**
     * <p>
     * Tests DbTimeEntryFilterFactory#createEntryDateFilter(Date,Date) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when both arguments are null and expects IllegalArgumentException.
     * </p>
     */
    public void testCreateEntryDateFilter_NullBothArguments() {
        try {
            factory.createEntryDateFilter(null, null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeEntryFilterFactory#createEntryDateFilter(Date,Date) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when rangeStart > rangeEnd and expects IllegalArgumentException.
     * </p>
     */
    public void testCreateEntryDateFilter_InvalidArguments() {
        try {
            factory.createEntryDateFilter(new Date(20000), new Date(10000));
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeEntryFilterFactory#createHoursFilter(double,double) for accuracy.
     * </p>
     *
     * <p>
     * It verifies DbTimeEntryFilterFactory#createHoursFilter(double,double) is correct.
     * </p>
     */
    public void testCreateHoursFilter() {
        BetweenFilter filter = (BetweenFilter) factory.createHoursFilter(5.0, 8.0);
        assertEquals("Failed to create the hours filter correctly.", "HOURS", filter.getName());
        assertEquals("Failed to create the hours filter correctly.", 5.0,
            ((Double) filter.getLowerThreshold()).doubleValue(), 0.01);
        assertEquals("Failed to create the hours filter correctly.", 8.0,
            ((Double) filter.getUpperThreshold()).doubleValue(), 0.01);
    }

    /**
     * <p>
     * Tests DbTimeEntryFilterFactory#createHoursFilter(double,double) for accuracy.
     * </p>
     *
     * <p>
     * It verifies DbTimeEntryFilterFactory#createHoursFilter(double,double) is correct.
     * </p>
     */
    public void testCreateHoursFilter_NaNRangeStart() {
        LessThanOrEqualToFilter filter = (LessThanOrEqualToFilter) factory.createHoursFilter(Double.NaN, 8.0);
        assertEquals("Failed to create the hours filter correctly.", "HOURS", filter.getName());
    }

    /**
     * <p>
     * Tests DbTimeEntryFilterFactory#createHoursFilter(double,double) for accuracy.
     * </p>
     *
     * <p>
     * It verifies DbTimeEntryFilterFactory#createHoursFilter(double,double) is correct.
     * </p>
     */
    public void testCreateHoursFilter_NaNRangeEnd() {
        GreaterThanOrEqualToFilter filter = (GreaterThanOrEqualToFilter) factory.createHoursFilter(5.0, Double.NaN);
        assertEquals("Failed to create the hours filter correctly.", "HOURS", filter.getName());
    }

    /**
     * <p>
     * Tests DbTimeEntryFilterFactory#createHoursFilter(double,double) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when both arguments are Double.NaN and expects IllegalArgumentException.
     * </p>
     */
    public void testCreateHoursFilter_BothNaN() {
        try {
            factory.createHoursFilter(Double.NaN, Double.NaN);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeEntryFilterFactory#createHoursFilter(double,double) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when rangeStart > rangeEnd and expects IllegalArgumentException.
     * </p>
     */
    public void testCreateHoursFilter_Invalid() {
        try {
            factory.createHoursFilter(8.0, 5.0);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeEntryFilterFactory#createTaskTypeFilter(TaskType) for accuracy.
     * </p>
     *
     * <p>
     * It verifies DbTimeEntryFilterFactory#createTaskTypeFilter(TaskType) is correct.
     * </p>
     */
    public void testCreateTaskTypeFilter() {
        EqualToFilter filter = (EqualToFilter) factory.createTaskTypeFilter(new TaskType());
        assertEquals("Failed to create the task type filter correctly.", "TASK_TYPE_ID", filter.getName());
        assertEquals("Failed to create the task type filter correctly.", new Long(-1), filter.getValue());
    }

    /**
     * <p>
     * Tests DbTimeEntryFilterFactory#createTaskTypeFilter(TaskType) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when taskType is null and expects IllegalArgumentException.
     * </p>
     */
    public void testCreateTaskTypeFilter_NullTaskType() {
        try {
            factory.createTaskTypeFilter(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeEntryFilterFactory#createTimeStatusFilter(TimeStatus) for accuracy.
     * </p>
     *
     * <p>
     * It verifies DbTimeEntryFilterFactory#createTimeStatusFilter(TimeStatus) is correct.
     * </p>
     */
    public void testCreateTimeStatusFilter() {
        EqualToFilter filter = (EqualToFilter) factory.createTimeStatusFilter(new TimeStatus());
        assertEquals("Failed to create the time status filter correctly.", "TIME_STATUS_ID", filter.getName());
        assertEquals("Failed to create the time status filter correctly.", new Long(-1), filter.getValue());
    }

    /**
     * <p>
     * Tests DbTimeEntryFilterFactory#createTimeStatusFilter(TimeStatus) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when timeStatus is null and expects IllegalArgumentException.
     * </p>
     */
    public void testCreateTimeStatusFilter_NullTimeStatus() {
        try {
            factory.createTimeStatusFilter(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeEntryFilterFactory#createBillableFilter(boolean) for accuracy.
     * </p>
     *
     * <p>
     * It verifies DbTimeEntryFilterFactory#createBillableFilter(boolean) is correct.
     * </p>
     */
    public void testCreateBillableFilter() {
        EqualToFilter filter = (EqualToFilter) factory.createBillableFilter(true);
        assertEquals("Failed to create the billable filter correctly.", "BILLABLE", filter.getName());
        assertEquals("Failed to create the billable filter correctly.", new Long(1), filter.getValue());
    }

    /**
     * <p>
     * Tests DbTimeEntryFilterFactory#createRejectReasonFilter(long) for accuracy.
     * </p>
     *
     * <p>
     * It verifies DbTimeEntryFilterFactory#createRejectReasonFilter(long) is correct.
     * </p>
     */
    public void testCreateRejectReasonFilter() {
        EqualToFilter filter = (EqualToFilter) factory.createRejectReasonFilter(8);
        assertEquals("Failed to create the reject reason filter correctly.", "REJECT_REASONS_ID", filter.getName());
        assertEquals("Failed to create the reject reason filter correctly.", new Long(8), filter.getValue());
    }

    /**
     * <p>
     * Tests DbTimeEntryFilterFactory#createRejectReasonFilter(long) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when rejectReasonId is negative and expects IllegalArgumentException.
     * </p>
     */
    public void testCreateRejectReasonFilter_Negative() {
        try {
            factory.createRejectReasonFilter(-8);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeEntryFilterFactory#createCompanyIdFilter(long) for accuracy.
     * </p>
     *
     * <p>
     * It verifies DbTimeEntryFilterFactory#createCompanyIdFilter(long) is correct.
     * </p>
     */
    public void testCreateCompanyIdFilter() {
        EqualToFilter filter = (EqualToFilter) factory.createCompanyIdFilter(8);
        assertEquals("Failed to create the company id filter correctly.", "COMPANY_ID", filter.getName());
        assertEquals("Failed to create the company id filter correctly.", new Long(8), filter.getValue());
    }

    /**
     * <p>
     * Tests DbTimeEntryFilterFactory#createCompanyIdFilter(long) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when companyId is negative and expects IllegalArgumentException.
     * </p>
     */
    public void testCreateCompanyIdFilter_Negative() {
        try {
            factory.createCompanyIdFilter(-8);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

}