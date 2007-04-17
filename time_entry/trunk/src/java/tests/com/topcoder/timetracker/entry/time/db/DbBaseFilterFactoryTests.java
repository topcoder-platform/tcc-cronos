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
import com.topcoder.search.builder.filter.LikeFilter;
import com.topcoder.timetracker.entry.time.StringMatchType;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

/**
 * <p>
 * Unit test cases for DbBaseFilterFactory.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class DbBaseFilterFactoryTests extends TestCase {
    /**
     * <p>
     * The DbBaseFilterFactory instance for testing.
     * </p>
     */
    private DbBaseFilterFactory factory;

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
        columnNames.put(DbBaseFilterFactory.CREATION_DATE_COLUMN_NAME, "creation_date");
        columnNames.put(DbBaseFilterFactory.MODIFICATION_DATE_COLUMN_NAME, "modification_date");
        columnNames.put(DbBaseFilterFactory.CREATION_USER_COLUMN_NAME, "creation_user");
        columnNames.put(DbBaseFilterFactory.MODIFICATION_USER_COLUMN_NAME, "modification_user");

        factory = new DbBaseFilterFactory(columnNames);
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
        return new TestSuite(DbBaseFilterFactoryTests.class);
    }

    /**
     * <p>
     * Tests ctor DbBaseFilterFactory#DbBaseFilterFactory(Map) for accuracy.
     * </p>
     *
     * <p>
     * It verifies the newly created DbBaseFilterFactory instance should not be null.
     * </p>
     */
    public void testCtor() {
        assertNotNull("Failed to create a new DbBaseFilterFactory instance.", factory);
    }

    /**
     * <p>
     * Tests ctor DbBaseFilterFactory#DbBaseFilterFactory(Map) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when columnNames is null and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor_NullColumnNames() {
        try {
            new DbBaseFilterFactory(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DbBaseFilterFactory#DbBaseFilterFactory(Map) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when key is empty and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor_EmptyKey() {
        columnNames.put(" ", "modification_user");
        try {
            new DbBaseFilterFactory(columnNames);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DbBaseFilterFactory#DbBaseFilterFactory(Map) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when key is not String type and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor_KeyNotString() {
        columnNames.put(new Long(8), "modification_user");
        try {
            new DbBaseFilterFactory(columnNames);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DbBaseFilterFactory#DbBaseFilterFactory(Map) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when columnNames is missing some keys and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor_MissSomeKey() {
        columnNames.remove(DbBaseFilterFactory.MODIFICATION_USER_COLUMN_NAME);
        try {
            new DbBaseFilterFactory(columnNames);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DbBaseFilterFactory#DbBaseFilterFactory(Map) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when value is empty and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor_EmptyValue() {
        columnNames.put(DbBaseFilterFactory.MODIFICATION_USER_COLUMN_NAME, " ");
        try {
            new DbBaseFilterFactory(columnNames);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DbBaseFilterFactory#DbBaseFilterFactory(Map) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when value is not String type and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor_ValueNotString() {
        columnNames.put(DbBaseFilterFactory.MODIFICATION_USER_COLUMN_NAME, new Long(8));
        try {
            new DbBaseFilterFactory(columnNames);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbBaseFilterFactory#createCreationDateFilter(Date,Date) for accuracy.
     * </p>
     *
     * <p>
     * It verifies DbBaseFilterFactory#createCreationDateFilter(Date,Date) is correct.
     * </p>
     */
    public void testCreateCreationDateFilter() {
        BetweenFilter filter = (BetweenFilter) factory.createCreationDateFilter(new Date(10000), new Date(20000));
        assertEquals("Failed to create the creation date filter correctly.", "creation_date", filter.getName());
        assertEquals("Failed to create the creation date filter correctly.", new Date(10000), filter.getLowerThreshold());
        assertEquals("Failed to create the creation date filter correctly.", new Date(20000), filter.getUpperThreshold());
    }

    /**
     * <p>
     * Tests DbBaseFilterFactory#createCreationDateFilter(Date,Date) for accuracy.
     * </p>
     *
     * <p>
     * It tests the case when range start is null and expects success.
     * </p>
     */
    public void testCreateCreationDateFilter_NullRangeStart() {
        Filter filter = factory.createCreationDateFilter(null, new Date(20000));
        assertEquals("Failed to create the creation date filter correctly.", LessThanOrEqualToFilter.class,
            filter.getClass());
        assertEquals("Failed to create the creation date filter correctly.", "creation_date",
            ((LessThanOrEqualToFilter) filter).getName());

    }

    /**
     * <p>
     * Tests DbBaseFilterFactory#createCreationDateFilter(Date,Date) for accuracy.
     * </p>
     *
     * <p>
     * It tests the case when range end is null and expects success.
     * </p>
     */
    public void testCreateCreationDateFilter_NullRangeEnd() {
        Filter filter = factory.createCreationDateFilter(new Date(20000), null);
        assertEquals("Failed to create the creation date filter correctly.", GreaterThanOrEqualToFilter.class,
            filter.getClass());
        assertEquals("Failed to create the creation date filter correctly.", "creation_date",
            ((GreaterThanOrEqualToFilter) filter).getName());
    }

    /**
     * <p>
     * Tests DbBaseFilterFactory#createCreationDateFilter(Date,Date) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when both arguments are null and expects IllegalArgumentException.
     * </p>
     */
    public void testCreateCreationDateFilter_NullBothArguments() {
        try {
            factory.createCreationDateFilter(null, null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbBaseFilterFactory#createCreationDateFilter(Date,Date) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when rangeStart > rangeEnd and expects IllegalArgumentException.
     * </p>
     */
    public void testCreateCreationDateFilter_InvalidArguments() {
        try {
            factory.createCreationDateFilter(new Date(20000), new Date(10000));
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbBaseFilterFactory#createModificationDateFilter(Date,Date) for accuracy.
     * </p>
     *
     * <p>
     * It verifies DbBaseFilterFactory#createModificationDateFilter(Date,Date) is correct.
     * </p>
     */
    public void testCreateModificationDateFilter() {
        BetweenFilter filter = (BetweenFilter) factory.createModificationDateFilter(new Date(10000), new Date(20000));
        assertEquals("Failed to create the modification date filter correctly.", "modification_date", filter.getName());
        assertEquals("Failed to create the modification date filter correctly.", new Date(10000), filter.getLowerThreshold());
        assertEquals("Failed to create the modification date filter correctly.", new Date(20000), filter.getUpperThreshold());
    }

    /**
     * <p>
     * Tests DbBaseFilterFactory#createModificationDateFilter(Date,Date) for accuracy.
     * </p>
     *
     * <p>
     * It tests the case when range start is null and expects success.
     * </p>
     */
    public void testCreateModificationDateFilter_NullRangeStart() {
        Filter filter = factory.createModificationDateFilter(null, new Date(20000));
        assertEquals("Failed to create the creation date filter correctly.", LessThanOrEqualToFilter.class,
            filter.getClass());
        assertEquals("Failed to create the creation date filter correctly.", "modification_date",
            ((LessThanOrEqualToFilter) filter).getName());
    }

    /**
     * <p>
     * Tests DbBaseFilterFactory#createModificationDateFilter(Date,Date) for accuracy.
     * </p>
     *
     * <p>
     * It tests the case when range end is null and expects success.
     * </p>
     */
    public void testCreateModificationDateFilter_NullRangeEnd() {
        Filter filter = factory.createModificationDateFilter(new Date(20000), null);
        assertEquals("Failed to create the creation date filter correctly.", GreaterThanOrEqualToFilter.class,
            filter.getClass());
        assertEquals("Failed to create the creation date filter correctly.", "modification_date",
            ((GreaterThanOrEqualToFilter) filter).getName());
    }

    /**
     * <p>
     * Tests DbBaseFilterFactory#createModificationDateFilter(Date,Date) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when both arguments are null and expects IllegalArgumentException.
     * </p>
     */
    public void testCreateModificationDateFilter_NullBothArguments() {
        try {
            factory.createModificationDateFilter(null, null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbBaseFilterFactory#createModificationDateFilter(Date,Date) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when rangeStart &gt; rangeEnd and expects IllegalArgumentException.
     * </p>
     */
    public void testCreateModificationDateFilter_InvalidArguments() {
        try {
            factory.createModificationDateFilter(new Date(20000), new Date(10000));
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbBaseFilterFactory#createCreationUserFilter(String, StringMatchType) for accuracy.
     * </p>
     *
     * <p>
     * It verifies DbBaseFilterFactory#createCreationUserFilter(String, StringMatchType) is correct.
     * </p>
     */
    public void testCreateCreationUserFilter() {
        LikeFilter filter = (LikeFilter) factory.createCreationUserFilter("username", StringMatchType.ENDS_WITH);

        assertEquals("Failed to create the creation user filter correctly.", "creation_user", filter.getName());
        assertEquals("Failed to create the creation user filter correctly.", "EW:username", filter.getValue());
    }

    /**
     * <p>
     * Tests DbBaseFilterFactory#createCreationUserFilter(String, StringMatchType) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when matchType is null and expects IllegalArgumentException.
     * </p>
     */
    public void testCreateCreationUserFilter_NullMatchType() {
        try {
            factory.createCreationUserFilter("username", null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbBaseFilterFactory#createCreationUserFilter(String, StringMatchType) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when username is null and expects IllegalArgumentException.
     * </p>
     */
    public void testCreateCreationUserFilter_NullUsername() {
        try {
            factory.createCreationUserFilter(null, StringMatchType.ENDS_WITH);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbBaseFilterFactory#createCreationUserFilter(String, StringMatchType) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when username is empty and expects IllegalArgumentException.
     * </p>
     */
    public void testCreateCreationUserFilter_EmptyUsername() {
        try {
            factory.createCreationUserFilter(" ", StringMatchType.ENDS_WITH);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbBaseFilterFactory#createModificationUserFilter(String, StringMatchType) for accuracy.
     * </p>
     *
     * <p>
     * It verifies DbBaseFilterFactory#createModificationUserFilter(String, StringMatchType) is correct.
     * </p>
     */
    public void testCreateModificationUserFilter() {
        EqualToFilter filter = (EqualToFilter) factory.createModificationUserFilter("username",
            StringMatchType.EXACT_MATCH);
        assertEquals("Failed to create the modification user filter correctly.", "modification_user", filter.getName());
        assertEquals("Failed to create the modification user filter correctly.", "username", filter.getValue());
    }

    /**
     * <p>
     * Tests DbBaseFilterFactory#createModificationUserFilter(String, StringMatchType) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when matchType is null and expects IllegalArgumentException.
     * </p>
     */
    public void testCreateModificationUserFilter_NullMatchType() {
        try {
            factory.createModificationUserFilter("username", null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbBaseFilterFactory#createModificationUserFilter(String, StringMatchType) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when username is null and expects IllegalArgumentException.
     * </p>
     */
    public void testCreateModificationUserFilter_NullUsername() {
        try {
            factory.createModificationUserFilter(null, StringMatchType.STARTS_WITH);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbBaseFilterFactory#createModificationUserFilter(String, StringMatchType) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when username is empty and expects IllegalArgumentException.
     * </p>
     */
    public void testCreateModificationUserFilter_EmptyUsername() {
        try {
            factory.createModificationUserFilter(" ", StringMatchType.STARTS_WITH);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbBaseFilterFactory#getColumnNames() for accuracy.
     * </p>
     *
     * <p>
     * It verifies DbBaseFilterFactory#getColumnNames() is correct.
     * </p>
     */
    public void testGetColumnNames() {
        Map map = factory.getColumnNames();
        assertEquals("Expected the size of map is two.", 4, map.size());
        assertTrue("Failed to get the column names map correctly.", map.containsKey("CREATION_DATE_COLUMN_NAME"));
        assertTrue("Failed to get the column names map correctly.", map.containsKey("CREATION_USER_COLUMN_NAME"));
        assertTrue("Failed to get the column names map correctly.", map.containsKey("MODIFICATION_USER_COLUMN_NAME"));
        assertTrue("Failed to get the column names map correctly.", map.containsKey("MODIFICATION_DATE_COLUMN_NAME"));
    }

}