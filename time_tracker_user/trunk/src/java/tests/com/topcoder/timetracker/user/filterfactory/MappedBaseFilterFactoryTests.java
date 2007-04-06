/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user.filterfactory;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.topcoder.search.builder.filter.BetweenFilter;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.search.builder.filter.GreaterThanOrEqualToFilter;
import com.topcoder.search.builder.filter.LessThanOrEqualToFilter;
import com.topcoder.search.builder.filter.LikeFilter;
import com.topcoder.timetracker.user.StringMatchType;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

/**
 * <p>
 * Unit test cases for MappedBaseFilterFactory.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class MappedBaseFilterFactoryTests extends TestCase {
    /**
     * <p>
     * The MappedBaseFilterFactory instance for testing.
     * </p>
     */
    private MappedBaseFilterFactory factory;

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
        columnNames.put("CREATION_DATE_COLUMN_NAME", "CreationDate");
        columnNames.put("MODIFICATION_DATE_COLUMN_NAME", "ModificationDate");
        columnNames.put("CREATION_USER_COLUMN_NAME", "CreationUser");
        columnNames.put("MODIFICATION_USER_COLUMN_NAME", "ModificationUser");
        factory = new MappedBaseFilterFactory(columnNames);
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
        return new TestSuite(MappedBaseFilterFactoryTests.class);
    }

    /**
     * <p>
     * Tests ctor MappedBaseFilterFactory#MappedBaseFilterFactory(Map) for accuracy.
     * </p>
     *
     * <p>
     * It verifies the newly created MappedBaseFilterFactory instance should not be null.
     * </p>
     */
    public void testCtor() {
        assertNotNull("Failed to create a new MappedBaseFilterFactory instance.", factory);
    }

    /**
     * <p>
     * Tests ctor MappedBaseFilterFactory#MappedBaseFilterFactory(Map) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when columnNames is null and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor_NullColumnNames() {
        try {
            new MappedBaseFilterFactory(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor MappedBaseFilterFactory#MappedBaseFilterFactory(Map) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when key is null and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor_NullKey() {
        columnNames.put(null, "date");
        try {
            new MappedBaseFilterFactory(columnNames);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor MappedBaseFilterFactory#MappedBaseFilterFactory(Map) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when key is empty and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor_EmptyKey() {
        columnNames.put(" ", "date");
        try {
            new MappedBaseFilterFactory(columnNames);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor MappedBaseFilterFactory#MappedBaseFilterFactory(Map) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when key is not String type and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor_KeyNotString() {
        columnNames.put(new Long(8), "date");
        try {
            new MappedBaseFilterFactory(columnNames);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor MappedBaseFilterFactory#MappedBaseFilterFactory(Map) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when key is missing and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor_MissingKey() {
        columnNames.remove("MODIFICATION_USER_COLUMN_NAME");
        try {
            new MappedBaseFilterFactory(columnNames);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor MappedBaseFilterFactory#MappedBaseFilterFactory(Map) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when value is null and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor_NullValue() {
        columnNames.put("CREATION_USER_COLUMN_NAME", null);
        try {
            new MappedBaseFilterFactory(columnNames);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor MappedBaseFilterFactory#MappedBaseFilterFactory(Map) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when value is empty and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor_EmptyValue() {
        columnNames.put("CREATION_USER_COLUMN_NAME", " ");
        try {
            new MappedBaseFilterFactory(columnNames);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor MappedBaseFilterFactory#MappedBaseFilterFactory(Map) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when value is not String type and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor_ValueNotString() {
        columnNames.put("CREATION_USER_COLUMN_NAME", new Long(8));
        try {
            new MappedBaseFilterFactory(columnNames);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests MappedBaseFilterFactory#createCreationDateFilter(Date,Date) for accuracy.
     * </p>
     *
     * <p>
     * It verifies MappedBaseFilterFactory#createCreationDateFilter(Date,Date) is correct.
     * </p>
     */
    public void testCreateCreationDateFilter() {
        BetweenFilter filter = (BetweenFilter) factory.createCreationDateFilter(new Date(10000), new Date(20000));
        assertEquals("Failed to create the creation date filter correctly.", "CreationDate", filter.getName());
    }

    /**
     * <p>
     * Tests MappedBaseFilterFactory#createCreationDateFilter(Date,Date) for accuracy.
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
        assertEquals("Failed to create the creation date filter correctly.", "CreationDate",
            ((LessThanOrEqualToFilter) filter).getName());

    }

    /**
     * <p>
     * Tests MappedBaseFilterFactory#createCreationDateFilter(Date,Date) for accuracy.
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
        assertEquals("Failed to create the creation date filter correctly.", "CreationDate",
            ((GreaterThanOrEqualToFilter) filter).getName());
    }

    /**
     * <p>
     * Tests MappedBaseFilterFactory#createCreationDateFilter(Date,Date) for failure.
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
     * Tests MappedBaseFilterFactory#createCreationDateFilter(Date,Date) for failure.
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
     * Tests MappedBaseFilterFactory#createModificationDateFilter(Date,Date) for accuracy.
     * </p>
     *
     * <p>
     * It verifies MappedBaseFilterFactory#createModificationDateFilter(Date,Date) is correct.
     * </p>
     */
    public void testCreateModificationDateFilter() {
        BetweenFilter filter = (BetweenFilter) factory.createModificationDateFilter(new Date(10000), new Date(20000));
        assertEquals("Failed to create the modification date filter correctly.", "ModificationDate", filter.getName());
    }

    /**
     * <p>
     * Tests MappedBaseFilterFactory#createModificationDateFilter(Date,Date) for accuracy.
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
        assertEquals("Failed to create the creation date filter correctly.", "ModificationDate",
            ((LessThanOrEqualToFilter) filter).getName());
    }

    /**
     * <p>
     * Tests MappedBaseFilterFactory#createModificationDateFilter(Date,Date) for accuracy.
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
        assertEquals("Failed to create the creation date filter correctly.", "ModificationDate",
            ((GreaterThanOrEqualToFilter) filter).getName());
    }

    /**
     * <p>
     * Tests MappedBaseFilterFactory#createModificationDateFilter(Date,Date) for failure.
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
     * Tests MappedBaseFilterFactory#createModificationDateFilter(Date,Date) for failure.
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
     * Tests MappedBaseFilterFactory#createCreationUserFilter(StringMatchType,String) for accuracy.
     * </p>
     *
     * <p>
     * It verifies MappedBaseFilterFactory#createCreationUserFilter(StringMatchType,String) is correct.
     * </p>
     */
    public void testCreateCreationUserFilter() {
        LikeFilter filter = (LikeFilter) factory.createCreationUserFilter(StringMatchType.ENDS_WITH, "username");
        assertEquals("Failed to create the creation user filter correctly.", "CreationUser", filter.getName());
    }

    /**
     * <p>
     * Tests MappedBaseFilterFactory#createCreationUserFilter(StringMatchType,String) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when matchType is null and expects IllegalArgumentException.
     * </p>
     */
    public void testCreateCreationUserFilter_NullMatchType() {
        try {
            factory.createCreationUserFilter(null, "username");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests MappedBaseFilterFactory#createCreationUserFilter(StringMatchType,String) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when username is null and expects IllegalArgumentException.
     * </p>
     */
    public void testCreateCreationUserFilter_NullUsername() {
        try {
            factory.createCreationUserFilter(StringMatchType.ENDS_WITH, null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests MappedBaseFilterFactory#createCreationUserFilter(StringMatchType,String) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when username is empty and expects IllegalArgumentException.
     * </p>
     */
    public void testCreateCreationUserFilter_EmptyUsername() {
        try {
            factory.createCreationUserFilter(StringMatchType.ENDS_WITH, " ");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests MappedBaseFilterFactory#createModificationUserFilter(StringMatchType,String) for accuracy.
     * </p>
     *
     * <p>
     * It verifies MappedBaseFilterFactory#createModificationUserFilter(StringMatchType,String) is correct.
     * </p>
     */
    public void testCreateModificationUserFilter() {
        EqualToFilter filter = (EqualToFilter) factory.createModificationUserFilter(StringMatchType.EXACT_MATCH,
            "username");
        assertEquals("Failed to create the modification user filter correctly.", "ModificationUser", filter.getName());
    }

    /**
     * <p>
     * Tests MappedBaseFilterFactory#createModificationUserFilter(StringMatchType,String) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when matchType is null and expects IllegalArgumentException.
     * </p>
     */
    public void testCreateModificationUserFilter_NullMatchType() {
        try {
            factory.createModificationUserFilter(null, "username");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests MappedBaseFilterFactory#createModificationUserFilter(StringMatchType,String) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when username is null and expects IllegalArgumentException.
     * </p>
     */
    public void testCreateModificationUserFilter_NullUsername() {
        try {
            factory.createModificationUserFilter(StringMatchType.STARTS_WITH, null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests MappedBaseFilterFactory#createModificationUserFilter(StringMatchType,String) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when username is empty and expects IllegalArgumentException.
     * </p>
     */
    public void testCreateModificationUserFilter_EmptyUsername() {
        try {
            factory.createModificationUserFilter(StringMatchType.STARTS_WITH, " ");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests MappedBaseFilterFactory#getColumnNames() for accuracy.
     * </p>
     *
     * <p>
     * It verifies MappedBaseFilterFactory#getColumnNames() is correct.
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