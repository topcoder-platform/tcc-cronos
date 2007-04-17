/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time.accuracytests;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

import com.topcoder.search.builder.filter.BetweenFilter;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.LessThanOrEqualToFilter;
import com.topcoder.search.builder.filter.LikeFilter;
import com.topcoder.timetracker.entry.time.StringMatchType;
import com.topcoder.timetracker.entry.time.TaskType;
import com.topcoder.timetracker.entry.time.TimeStatus;
import com.topcoder.timetracker.entry.time.db.DbTimeEntryFilterFactory;

/**
 * <p>
 * Accuracy Unit test cases for DbTimeEntryFilterFactory.
 * </p>
 *
 * @author victorsam
 * @version 3.2
 */
public class DbTimeEntryFilterFactoryAccuracyTests extends TestCase {
    /**
     * <p>
     * DbTimeEntryFilterFactory instance for testing.
     * </p>
     */
    private DbTimeEntryFilterFactory instance;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     */
    protected void setUp() {
        Map columnNames = new HashMap();
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

        instance = new DbTimeEntryFilterFactory(columnNames);
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     */
    protected void tearDown() {
        instance = null;
    }

    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(DbTimeEntryFilterFactoryAccuracyTests.class);
    }

    /**
     * <p>
     * Tests ctor DbTimeEntryFilterFactory#DbTimeEntryFilterFactory(Map) for accuracy.
     * </p>
     */
    public void testCtor1() {
        assertNotNull("Failed to create DbTimeEntryFilterFactory instance.", instance);
    }

    /**
     * <p>
     * Tests DbTimeEntryFilterFactory#createInvoiceIdFilter(long) for accuracy.
     * </p>
     */
    public void testCreateInvoiceIdFilter() {
        EqualToFilter filter = (EqualToFilter) instance.createInvoiceIdFilter(6);
        assertEquals("Failed to create the filter.", "invoice_id", filter.getName());
    }

    /**
     * <p>
     * Tests DbTimeEntryFilterFactory#createDescriptionFilter(String,StringMatchType) for accuracy.
     * </p>
     */
    public void testCreateDescriptionFilter() {
        LikeFilter filter = (LikeFilter) instance.createDescriptionFilter("description", StringMatchType.STARTS_WITH);
        assertEquals("Failed to create the filter.", "description", filter.getName());
    }

    /**
     * <p>
     * Tests DbTimeEntryFilterFactory#createEntryDateFilter(Date,Date) for accuracy.
     * </p>
     */
    public void testCreateEntryDateFilter() {
        LessThanOrEqualToFilter filter = (LessThanOrEqualToFilter) instance.createEntryDateFilter(null, new Date());
        assertEquals("Failed to create the filter.", "entry_date", filter.getName());
    }

    /**
     * <p>
     * Tests DbTimeEntryFilterFactory#createHoursFilter(double,double) for accuracy.
     * </p>
     */
    public void testCreateHoursFilter() {
        BetweenFilter filter = (BetweenFilter) instance.createHoursFilter(1.0, 5.0);
        assertEquals("Failed to create the filter.", "hours", filter.getName());
    }

    /**
     * <p>
     * Tests DbTimeEntryFilterFactory#createTaskTypeFilter(TaskType) for accuracy.
     * </p>
     */
    public void testCreateTaskTypeFilter() {
        EqualToFilter filter = (EqualToFilter) instance.createTaskTypeFilter(new TaskType());
        assertEquals("Failed to create the filter.", "task_type_id", filter.getName());
    }

    /**
     * <p>
     * Tests DbTimeEntryFilterFactory#createTimeStatusFilter(TimeStatus) for accuracy.
     * </p>
     */
    public void testCreateTimeStatusFilter() {
        EqualToFilter filter = (EqualToFilter) instance.createTimeStatusFilter(new TimeStatus());
        assertEquals("Failed to create the filter.", "time_status_id", filter.getName());
    }

    /**
     * <p>
     * Tests DbTimeEntryFilterFactory#createBillableFilter(boolean) for accuracy.
     * </p>
     */
    public void testCreateBillableFilter() {
        EqualToFilter filter = (EqualToFilter) instance.createBillableFilter(true);
        assertEquals("Failed to create the filter.", "billable", filter.getName());
    }

    /**
     * <p>
     * Tests DbTimeEntryFilterFactory#createRejectReasonFilter(long) for accuracy.
     * </p>
     */
    public void testCreateRejectReasonFilter() {
        EqualToFilter filter = (EqualToFilter) instance.createRejectReasonFilter(8);
        assertEquals("Failed to create the filter.", "reject_reason_id", filter.getName());
    }

    /**
     * <p>
     * </p>
     */
    public void testCreateCompanyIdFilter() {
        EqualToFilter filter = (EqualToFilter) instance.createCompanyIdFilter(8);
        assertEquals("Failed to create the filter.", "company_id", filter.getName());
    }

}