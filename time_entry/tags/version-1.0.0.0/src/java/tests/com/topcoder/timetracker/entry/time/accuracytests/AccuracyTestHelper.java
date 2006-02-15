/*
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved
 */
package com.topcoder.timetracker.entry.time.accuracytests;

import java.util.Calendar;
import java.util.Date;

import junit.framework.Assert;

import com.topcoder.timetracker.entry.time.BaseDataObject;
import com.topcoder.timetracker.entry.time.TimeEntry;

/**
 * Defines helper methods used in tests.
 * @author fuyun
 * @version 1.0
 */
class AccuracyTestHelper {

    /**
     * Emtpy private constructor to make this class can not be instanced.
     */
    private AccuracyTestHelper() {

    }

    /**
     * <p>
     * Asserts that two <code>BaseDataObject</code> objects is the same.
     * </p>
     * @param msg the description.
     * @param expected the expected BaseDataObject instance.
     * @param actual the actual BaseDataObject instance.
     */
    public static void assertEquals(String msg, BaseDataObject expected, BaseDataObject actual) {
        assertEquals(msg, expected.getCreationDate(), actual.getCreationDate());
        Assert.assertEquals(msg, expected.getCreationUser(), actual.getCreationUser());
        Assert.assertEquals(msg, expected.getDescription(), actual.getDescription());
        assertEquals(msg, expected.getModificationDate(), actual.getModificationDate());
        Assert.assertEquals(msg, expected.getModificationUser(), actual.getModificationUser());
        Assert.assertEquals(msg, expected.getPrimaryId(), actual.getPrimaryId());

    }

    /**
     * <p>
     * Asserts that two given <code>TimeEntry</code> is the same.
     * </p>
     * @param msg the description.
     * @param expected the expected TimeEntry instance.
     * @param actual the actual TimeEntry instance.
     */
    public static void assertEquals(String msg, TimeEntry expected, TimeEntry actual) {
        assertEquals(msg, (BaseDataObject) expected, (BaseDataObject) actual);
        Assert.assertEquals(msg, expected.getTaskTypeId(), actual.getTaskTypeId());
        Assert.assertEquals(msg, expected.getTimeStatusId(), actual.getTimeStatusId());
        Assert.assertEquals(msg, expected.isBillable(), actual.isBillable());
        assertEquals(msg, expected.getDate(), actual.getDate());
        Assert.assertEquals(msg, expected.getHours(), actual.getHours(), 1E-9);
    }
    
    /**
     * <p>
     * Asserts that two given <code>Date</code> is the same.
     * @param msg the description.
     * @param expected the expected Date instance.
     * @param actual the actual Date instance.
     */
    public static void assertEquals(String msg, Date expected, Date actual) {
        Assert.assertEquals(msg, expected.getYear(), actual.getYear());
        Assert.assertEquals(msg, expected.getMonth(), actual.getMonth());
        Assert.assertEquals(msg, expected.getDay(), actual.getDay());
        Assert.assertEquals(msg, expected.getHours(), actual.getHours());
        Assert.assertEquals(msg, expected.getMinutes(), actual.getMinutes());
        Assert.assertEquals(msg, expected.getSeconds(), actual.getSeconds());
    }
    
    /**
     * <p>
     * Creates a <code>Date</code> instance representing the given year, month, date, hour, minute and second.
     * </p>
     *
     * @param year the year in the instance.
     * @param month the month in the instance.
     * @param date the date in the instance.
     * @param hour the hour in the instance.
     * @param minute the minute in the instance.
     * @param second the second in the instance.
     *
     * @return a <code>Date</code> instance representing the year, month and date.
     */
    public static Date generateDate(int year, int month, int day, int hour, int minute, int second) {
        Calendar calendar = Calendar.getInstance();

        calendar.set(year, month - 1, day, hour, minute, second);

        return calendar.getTime();
    }

}
