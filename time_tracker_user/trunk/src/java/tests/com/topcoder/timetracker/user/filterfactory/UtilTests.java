/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user.filterfactory;

import java.util.HashMap;
import java.util.Map;

import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.LikeFilter;
import com.topcoder.timetracker.user.StringMatchType;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

/**
 * <p>
 * Unit test cases for Util.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
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
     * Tests Util#checkNull(Object,String) method.
     * It test the case when a not null object is passed in and expects success.
     * </p>
     */
    public void testCheckNull_NotNullObject() {
        Util.checkNull("", "test");
    }

    /**
     * <p>
     * Tests Util#checkNull(Object,String) method.
     * It test the case when a null object is passed in and expects IllegalArgumentException.
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
     * Tests Util#checkString(String,String) method.
     * It test the case when a not null and not empty string is passed in and expects success.
     * </p>
     */
    public void testCheckString_NotNullNotEmptyString() {
        Util.checkString("test", "test");
    }

    /**
     * <p>
     * Tests Util#checkString(String,String) method.
     * It test the case when a null string is passed in and expects IllegalArgumentException.
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
     * Tests Util#checkString(String,String) method.
     * It test the case when an empty string is passed in and expects IllegalArgumentException.
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
     * It tests the case that when the given map instance doesn't contains any key in the given
     * string array and expects IllegalArgumentException.
     * </p>
     */
    public void testCheckMapForKeys_NotContainKey() {
        Map map = new HashMap();
        map.put("one", "first");
        try {
            Util.checkMapForKeys(map, new String[] {"one", "two"});
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
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

        EqualToFilter equalToFilter = (EqualToFilter) Util.createFilter(StringMatchType.EXACT_MATCH, "new", "value");
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
            //good
        }
    }

}