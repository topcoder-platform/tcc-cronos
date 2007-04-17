/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time.db;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
     * The columnNames map for testing.
     * </p>
     */
    private Map columnNames;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     */
    protected void setUp() {
        columnNames = new HashMap();
        columnNames.put(DbTimeEntryFilterFactory.CREATION_DATE_COLUMN_NAME, "creation_date");
        columnNames.put(DbTimeEntryFilterFactory.MODIFICATION_DATE_COLUMN_NAME, "modification_date");
        columnNames.put(DbTimeEntryFilterFactory.CREATION_USER_COLUMN_NAME, "creation_user");
        columnNames.put(DbTimeEntryFilterFactory.MODIFICATION_USER_COLUMN_NAME, "modification_user");
        columnNames.put(DbTimeEntryFilterFactory.INVOICE_ID_COLUMN_NAME, "invoice_id");
        columnNames.put(DbTimeEntryFilterFactory.DESCRIPTION_COLUMN_NAME, "description");
        columnNames.put(DbTimeEntryFilterFactory.ENTRY_DATE_COLUMN_NAME, "entry_date");
        columnNames.put(DbTimeEntryFilterFactory.HOURS_COLUMN_NAME, "hours");
        columnNames.put(DbTimeEntryFilterFactory.TASK_TYPE_COLUMN_NAME, "task_type_id");
        columnNames.put(DbTimeEntryFilterFactory.TIME_STATUS_COLUMN_NAME, "time_status_id");
        columnNames.put(DbTimeEntryFilterFactory.BILLABLE_COLUMN_NAME, "billable");
        columnNames.put(DbTimeEntryFilterFactory.REJECT_REASONS_COLUMN_NAME, "reject_reason_id");
        columnNames.put(DbTimeEntryFilterFactory.COMPANY_ID_COLUMN_NAME, "company_id");

        factory = new DbTimeEntryFilterFactory(columnNames);
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     */
    protected void tearDown() {
        factory = null;
        columnNames = null;
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
     * Tests ctor DbTimeEntryFilterFactory#DbTimeEntryFilterFactory(Map) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when columnNames is null and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor_NullColumnNames() {
        try {
            new DbTimeEntryFilterFactory(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DbTimeEntryFilterFactory#DbTimeEntryFilterFactory(Map) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when key is empty and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor_EmptyKey() {
        columnNames.put(" ", "modification_user");
        try {
            new DbTimeEntryFilterFactory(columnNames);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DbTimeEntryFilterFactory#DbTimeEntryFilterFactory(Map) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when key is not String type and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor_KeyNotString() {
        columnNames.put(new Long(8), "modification_user");
        try {
            new DbTimeEntryFilterFactory(columnNames);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DbTimeEntryFilterFactory#DbTimeEntryFilterFactory(Map) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when columnNames is missing some keys and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor_MissSomeKey() {
        columnNames.remove(DbTimeEntryFilterFactory.MODIFICATION_USER_COLUMN_NAME);
        try {
            new DbTimeEntryFilterFactory(columnNames);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DbTimeEntryFilterFactory#DbTimeEntryFilterFactory(Map) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when value is empty and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor_EmptyValue() {
        columnNames.put(DbTimeEntryFilterFactory.MODIFICATION_USER_COLUMN_NAME, " ");
        try {
            new DbTimeEntryFilterFactory(columnNames);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DbTimeEntryFilterFactory#DbTimeEntryFilterFactory(Map) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when value is not String type and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor_ValueNotString() {
        columnNames.put(DbTimeEntryFilterFactory.MODIFICATION_USER_COLUMN_NAME, new Long(8));
        try {
            new DbTimeEntryFilterFactory(columnNames);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
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
        assertEquals("Failed to create the description filter correctly.", "description", filter.getName());
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
        assertEquals("Failed to create the invoice id filter correctly.", "invoice_id", filter.getName());
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
        assertEquals("Failed to create the creation date filter correctly.", "entry_date", filter.getName());
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
        assertEquals("Failed to create the creation date filter correctly.", "entry_date",
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
        assertEquals("Failed to create the creation date filter correctly.", "entry_date",
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
        assertEquals("Failed to create the hours filter correctly.", "hours", filter.getName());
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
        assertEquals("Failed to create the hours filter correctly.", "hours", filter.getName());
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
        assertEquals("Failed to create the hours filter correctly.", "hours", filter.getName());
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
        assertEquals("Failed to create the task type filter correctly.", "task_type_id", filter.getName());
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
        assertEquals("Failed to create the time status filter correctly.", "time_status_id", filter.getName());
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
        assertEquals("Failed to create the billable filter correctly.", "billable", filter.getName());
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
        assertEquals("Failed to create the reject reason filter correctly.", "reject_reason_id", filter.getName());
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
        assertEquals("Failed to create the company id filter correctly.", "company_id", filter.getName());
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