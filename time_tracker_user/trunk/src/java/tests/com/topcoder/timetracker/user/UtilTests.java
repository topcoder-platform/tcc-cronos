/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.topcoder.search.builder.filter.BetweenFilter;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.LikeFilter;
import com.topcoder.timetracker.user.BatchOperationException;
import com.topcoder.timetracker.user.DataAccessException;
import com.topcoder.timetracker.user.StringMatchType;
import com.topcoder.timetracker.user.UserStatus;
import com.topcoder.timetracker.user.Util;
import com.topcoder.timetracker.user.j2ee.UserStatusManagerLocal;
import com.topcoder.timetracker.user.j2ee.UserStatusManagerLocalHome;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

/**
 * <p>
 * Unit test cases for Util.
 * </p>
 *
 * @author biotrail, enefem21
 * @version 3.2.1
 * @since 3.2
 */
public class UtilTests extends TestCase {
    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(UtilTests.class);
    }

    /**
     * <p>
     * Tests Util#checkNull(Object,String) method. It test the case when a not null object is passed in and expects
     * success.
     * </p>
     */
    public void testCheckNull_NotNullObject() {
        Util.checkNull("", "test");
    }

    /**
     * <p>
     * Tests Util#checkNull(Object,String) method. It test the case when a null object is passed in and expects
     * IllegalArgumentException.
     * </p>
     */
    public void testCheckNull_NullObject() {
        try {
            Util.checkNull(null, "test");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests Util#checkString(String,String) method. It test the case when a not null and not empty string is
     * passed in and expects success.
     * </p>
     */
    public void testCheckString_NotNullNotEmptyString() {
        Util.checkString("test", "test");
    }

    /**
     * <p>
     * Tests Util#checkString(String,String) method. It test the case when a null string is passed in and expects
     * IllegalArgumentException.
     * </p>
     */
    public void testCheckString_NullString() {
        try {
            Util.checkString(null, "test");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests Util#checkString(String,String) method. It test the case when an empty string is passed in and expects
     * IllegalArgumentException.
     * </p>
     */
    public void testCheckString_EmptyString() {
        try {
            Util.checkString("", "test");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests Util#checkMapForKeys(Map,String[]) for accuracy.
     * </p>
     *
     * <p>
     * It verifies Util#checkMapForKeys(Map,String[]) is correct.
     * </p>
     */
    public void testCheckMapForKeys() {
        Map map = new HashMap();
        map.put("one", "first");
        map.put("two", "secodn");
        Util.checkMapForKeys(map, new String[] {"one", "two"});
    }

    /**
     * <p>
     * Tests Util#checkMapForKeys(Map,String[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when the given map instance doesn't contains any key in the given string array and
     * expects IllegalArgumentException.
     * </p>
     */
    public void testCheckMapForKeys_NotContainKey() {
        Map map = new HashMap();
        map.put("one", "first");
        try {
            Util.checkMapForKeys(map, new String[] {"one", "two"});
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests Util#createFilter(StringMatchType,String,String) for accuracy.
     * </p>
     *
     * <p>
     * It verifies Util#createFilter(StringMatchType,String,String) is correct.
     * </p>
     */
    public void testCreateFilter() {
        LikeFilter filter = (LikeFilter) Util.createFilter(StringMatchType.ENDS_WITH, "name", "value");
        assertEquals("Failed to create filter correctly.", "name", filter.getName());

        EqualToFilter equalToFilter =
            (EqualToFilter) Util.createFilter(StringMatchType.EXACT_MATCH, "new", "value");
        assertEquals("Failed to create filter correctly.", "new", equalToFilter.getName());
    }

    /**
     * <p>
     * Tests Util#createFilter(StringMatchType,String,String) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when matchType is null and expects IllegalArgumentException.
     * </p>
     */
    public void testCreateFilter_NullMatchType() {
        try {
            Util.createFilter(null, "name", "value");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * Test <code>createLocalObject</code> for failure. Condition: namespace is null. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCreateLocalObjectNull() throws Exception {
        try {
            Util.createLocalObject(null, UserStatusManagerLocalHome.class, UserStatusManagerLocal.class);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }

    }

    /**
     * Test <code>updateDates</code> for accuracy. Condition: normal. Expect: normal.
     */
    public void testUpdateDatesAccuracy() {
        Util.updateDates(new UserStatus[] {new UserStatus()}, true);

    }

    /**
     * Test <code>updateDates</code> for failure. Condition: beans is null. Expect:
     * <code>IllegalArgumentException</code>.
     */
    public void testUpdateDatesNull() {
        try {
            Util.updateDates(null, true);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>updateDates</code> for failure. Condition: beans contains null. Expect:
     * <code>IllegalArgumentException</code>.
     */
    public void testUpdateDatesContainsNull() {
        try {
            Util.updateDates(new UserStatus[] {null}, true);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>createDateFilter</code> for accuracy. Condition: normal. Expect: normal.
     */
    public void testCreateDateFilterAccuracy() {
        BetweenFilter filter = (BetweenFilter) Util.createDateFilter(new Date(), new Date(), "columnName");
        assertEquals("The filter is not properly created", "columnName", filter.getName());
    }

    /**
     * Test <code>createBooleanFilter</code> for accuracy. Condition: normal. Expect: normal.
     */
    public void testCreateBooleanFilterAccuracy() {
        EqualToFilter filter = (EqualToFilter) Util.createBooleanFilter(true, "columnName");
        assertEquals("The filter is not properly created", new Integer(1), filter.getValue());
        assertEquals("The filter is not properly created", "columnName", filter.getName());
    }

    /**
     * Test <code>createLongFilter</code> for accuracy. Condition: normal. Expect: normal.
     */
    public void testCreateLongFilterAccuracy() {
        EqualToFilter filter = (EqualToFilter) Util.createLongFilter(1, "testName", "columnName");
        assertEquals("The filter is not properly created", new Long(1), filter.getValue());
        assertEquals("The filter is not properly created", "columnName", filter.getName());
    }

    /**
     * Test <code>createLongFilter</code> for failure. Condition: number is negative. Expect:
     * <code>IllegalArgumentException</code>.
     */
    public void testCreateLongFilterNegative() {
        try {
            Util.createLongFilter(-1, "testName", "columnName");
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>createStringFilter</code> for accuracy. Condition: normal. Expect: normal.
     */
    public void testCreateStringFilterAccuracy() {
        EqualToFilter filter = (EqualToFilter) Util.createStringFilter("test", "testName", "columnName");
        assertEquals("The filter is not properly created", "test", filter.getValue());
        assertEquals("The filter is not properly created", "columnName", filter.getName());
    }

    /**
     * Test <code>createStringFilter</code> for failure. Condition: name is null. Expect:
     * <code>IllegalArgumentException</code>.
     */
    public void testCreateStringFilterNull() {
        try {
            Util.createStringFilter(null, "testName", "columnName");
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>createStringFilter</code> for failure. Condition: name is empty. Expect:
     * <code>IllegalArgumentException</code>.
     */
    public void testCreateStringFilterEmpty() {
        try {
            Util.createStringFilter("  ", "testName", "columnName");
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>convertBatchExceptionToSingleException</code> for accuracy. Condition: normal. Expect: normal.
     */
    public void testConvertBatchExceptionToSingleExceptionAccuracy1() {
        BatchOperationException e = new BatchOperationException("message", new Throwable[] {new Exception()});
        Util.convertBatchExceptionToSingleException(e, "noMessage");
    }

    /**
     * Test <code>convertBatchExceptionToSingleException</code> for accuracy. Condition: normal. Expect: normal.
     */
    public void testConvertBatchExceptionToSingleExceptionAccuracy2() {
        BatchOperationException e =
            new BatchOperationException("message", new Throwable[] {new DataAccessException("test")});
        Util.convertBatchExceptionToSingleException(e, "noMessage");
    }

    /**
     * Test <code>convertBatchExceptionToSingleException</code> for failure. Condition: exception is null.
     * Expect: <code>NullPointerException</code>.
     */
    public void testConvertBatchExceptionToSingleExceptionNull() {
        try {
            Util.convertBatchExceptionToSingleException(null, "noMessage");
            fail("Should throw NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }

    }

}