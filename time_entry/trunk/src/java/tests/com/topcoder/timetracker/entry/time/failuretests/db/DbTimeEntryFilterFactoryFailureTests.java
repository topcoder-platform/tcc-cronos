/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time.failuretests.db;

import com.topcoder.timetracker.entry.time.StringMatchType;
import com.topcoder.timetracker.entry.time.db.DbTimeEntryFilterFactory;

import junit.framework.TestCase;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * <p>
 * Failure test cases for DbTimeEntryFilterFactory.
 * </p>
 *
 * @author KLW
 * @version 3.2
 */
public class DbTimeEntryFilterFactoryFailureTests extends TestCase {
    /**
     * <p>
     * The DbTimeEntryFilterFactory instance for testing.
     * </p>
     */
    private DbTimeEntryFilterFactory instance;

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
        columnNames = null;
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
    public void testCtor1() {
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
    public void testCtor2() {
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
    public void testCtor3() {
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
    public void testCtor4() {
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
    public void testCtor5() {
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
    public void testCtor6() {
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
     * Tests DbTimeEntryFilterFactory#createDescriptionFilter(String,StringMatchType) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when description is null and expects IllegalArgumentException.
     * </p>
     */
    public void testCreateDescriptionFilter1() {
        try {
            instance.createDescriptionFilter(null, StringMatchType.EXACT_MATCH);
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
    public void testCreateDescriptionFilter2() {
        try {
            instance.createDescriptionFilter(" ", StringMatchType.EXACT_MATCH);
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
    public void testCreateDescriptionFilter3() {
        try {
            instance.createDescriptionFilter("description", null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
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
    public void testCreateInvoiceIdFilter() {
        try {
            instance.createInvoiceIdFilter(-999);
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
     * It tests the case that when both arguments are null and expects IllegalArgumentException.
     * </p>
     */
    public void testCreateEntryDateFilter1() {
        try {
            instance.createEntryDateFilter(null, null);
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
    public void testCreateEntryDateFilter2() {
        try {
            instance.createEntryDateFilter(new Date(20000), new Date(10000));
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
     * It tests the case that when both arguments are Double.NaN and expects IllegalArgumentException.
     * </p>
     */
    public void testCreateHoursFilter1() {
        try {
            instance.createHoursFilter(Double.NaN, Double.NaN);
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
    public void testCreateHoursFilter2() {
        try {
            instance.createHoursFilter(8.0, 5.0);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
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
    public void testCreateTaskTypeFilter() {
        try {
            instance.createTaskTypeFilter(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
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
    public void testCreateTimeStatusFilter() {
        try {
            instance.createTimeStatusFilter(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
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
    public void testCreateRejectReasonFilter() {
        try {
            instance.createRejectReasonFilter(-8);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
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
    public void testCreateCompanyIdFilter() {
        try {
            instance.createCompanyIdFilter(-999);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }
}
