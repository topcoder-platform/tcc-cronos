/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.project.db;

import java.util.Date;

import com.topcoder.search.builder.filter.BetweenFilter;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.search.builder.filter.GreaterThanOrEqualToFilter;
import com.topcoder.search.builder.filter.LessThanOrEqualToFilter;
import com.topcoder.search.builder.filter.LikeFilter;
import com.topcoder.timetracker.project.StringMatchType;

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
     * Sets up test environment.
     * </p>
     */
    protected void setUp() {
        factory = new DbBaseFilterFactory();
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
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
     * Tests DbBaseFilterFactory#createCreationDateFilter(Date,Date) for accuracy.
     * </p>
     *
     * <p>
     * It verifies DbBaseFilterFactory#createCreationDateFilter(Date,Date) is correct.
     * </p>
     */
    public void testCreateCreationDateFilter() {
        BetweenFilter filter = (BetweenFilter) factory.createCreationDateFilter(new Date(10000), new Date(20000));
        assertEquals("Failed to create the creation date filter correctly.", "CREATION_DATE", filter.getName());
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
        assertEquals("Failed to create the creation date filter correctly.", "CREATION_DATE",
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
        assertEquals("Failed to create the creation date filter correctly.", "CREATION_DATE",
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
     * It tests the case that when rangeStart &gt; rangeEnd and expects IllegalArgumentException.
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
        assertEquals("Failed to create the modification date filter correctly.", "MODIFICATION_DATE", filter.getName());
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
        assertEquals("Failed to create the creation date filter correctly.", "MODIFICATION_DATE",
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
        assertEquals("Failed to create the creation date filter correctly.", "MODIFICATION_DATE",
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
     * Tests DbBaseFilterFactory#createCreationUserFilter(StringMatchType,String) for accuracy.
     * </p>
     *
     * <p>
     * It verifies DbBaseFilterFactory#createCreationUserFilter(StringMatchType,String) is correct.
     * </p>
     */
    public void testCreateCreationUserFilter() {
        LikeFilter filter = (LikeFilter) factory.createCreationUserFilter(StringMatchType.ENDS_WITH, "username");
        assertEquals("Failed to create the creation user filter correctly.", "CREATION_USER", filter.getName());
    }

    /**
     * <p>
     * Tests DbBaseFilterFactory#createCreationUserFilter(StringMatchType,String) for failure.
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
     * Tests DbBaseFilterFactory#createCreationUserFilter(StringMatchType,String) for failure.
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
     * Tests DbBaseFilterFactory#createCreationUserFilter(StringMatchType,String) for failure.
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
     * Tests DbBaseFilterFactory#createModificationUserFilter(StringMatchType,String) for accuracy.
     * </p>
     *
     * <p>
     * It verifies DbBaseFilterFactory#createModificationUserFilter(StringMatchType,String) is correct.
     * </p>
     */
    public void testCreateModificationUserFilter() {
        EqualToFilter filter = (EqualToFilter) factory.createModificationUserFilter(StringMatchType.EXACT_MATCH,
            "username");
        assertEquals("Failed to create the modification user filter correctly.", "MODIFICATION_USER", filter.getName());
    }

    /**
     * <p>
     * Tests DbBaseFilterFactory#createModificationUserFilter(StringMatchType,String) for failure.
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
     * Tests DbBaseFilterFactory#createModificationUserFilter(StringMatchType,String) for failure.
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
     * Tests DbBaseFilterFactory#createModificationUserFilter(StringMatchType,String) for failure.
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
}