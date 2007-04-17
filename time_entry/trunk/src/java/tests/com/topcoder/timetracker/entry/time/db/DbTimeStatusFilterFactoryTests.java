/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time.db;

import java.util.HashMap;
import java.util.Map;

import com.topcoder.search.builder.filter.LikeFilter;
import com.topcoder.timetracker.entry.time.StringMatchType;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

/**
 * <p>
 * Unit test cases for DbTimeStatusFilterFactory.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class DbTimeStatusFilterFactoryTests extends TestCase {
    /**
     * <p>
     * The DbTimeStatusFilterFactory instance for testing.
     * </p>
     */
    private DbTimeStatusFilterFactory factory;

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
        columnNames.put(DbTimeStatusFilterFactory.CREATION_DATE_COLUMN_NAME, "creation_date");
        columnNames.put(DbTimeStatusFilterFactory.MODIFICATION_DATE_COLUMN_NAME, "modification_date");
        columnNames.put(DbTimeStatusFilterFactory.CREATION_USER_COLUMN_NAME, "creation_user");
        columnNames.put(DbTimeStatusFilterFactory.MODIFICATION_USER_COLUMN_NAME, "modification_user");
        columnNames.put(DbTimeStatusFilterFactory.DESCRIPTION_COLUMN_NAME, "description");

        factory = new DbTimeStatusFilterFactory(columnNames);
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
        return new TestSuite(DbTimeStatusFilterFactoryTests.class);
    }

    /**
     * <p>
     * Tests ctor DbTimeStatusFilterFactory#DbTimeStatusFilterFactory(Map) for accuracy.
     * </p>
     *
     * <p>
     * It verifies the newly created DbTimeStatusFilterFactory instance should not be null.
     * </p>
     */
    public void testCtor() {
        assertNotNull("Failed to create a new DbTimeStatusFilterFactory instance.", factory);
    }

    /**
     * <p>
     * Tests ctor DbTimeStatusFilterFactory#DbTimeStatusFilterFactory(Map) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when columnNames is null and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor_NullColumnNames() {
        try {
            new DbTimeStatusFilterFactory(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DbTimeStatusFilterFactory#DbTimeStatusFilterFactory(Map) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when key is empty and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor_EmptyKey() {
        columnNames.put(" ", "modification_user");
        try {
            new DbTimeStatusFilterFactory(columnNames);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DbTimeStatusFilterFactory#DbTimeStatusFilterFactory(Map) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when key is not String type and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor_KeyNotString() {
        columnNames.put(new Long(8), "modification_user");
        try {
            new DbTimeStatusFilterFactory(columnNames);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DbTimeStatusFilterFactory#DbTimeStatusFilterFactory(Map) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when columnNames is missing some keys and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor_MissSomeKey() {
        columnNames.remove(DbTimeStatusFilterFactory.MODIFICATION_USER_COLUMN_NAME);
        try {
            new DbTimeStatusFilterFactory(columnNames);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DbTimeStatusFilterFactory#DbTimeStatusFilterFactory(Map) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when value is empty and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor_EmptyValue() {
        columnNames.put(DbTimeStatusFilterFactory.MODIFICATION_USER_COLUMN_NAME, " ");
        try {
            new DbTimeStatusFilterFactory(columnNames);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DbTimeStatusFilterFactory#DbTimeStatusFilterFactory(Map) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when value is not String type and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor_ValueNotString() {
        columnNames.put(DbTimeStatusFilterFactory.MODIFICATION_USER_COLUMN_NAME, new Long(8));
        try {
            new DbTimeStatusFilterFactory(columnNames);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeStatusFilterFactory#createDescriptionFilter(String,StringMatchType) for accuracy.
     * </p>
     *
     * <p>
     * It verifies DbTimeStatusFilterFactory#createDescriptionFilter(String,StringMatchType) is correct.
     * </p>
     */
    public void testCreateDescriptionFilter() {
        LikeFilter filter = (LikeFilter) factory.createDescriptionFilter("description", StringMatchType.ENDS_WITH);
        assertEquals("Failed to create the description filter correctly.", "description", filter.getName());
        assertEquals("Failed to create the description filter correctly.", "EW:description", filter.getValue());
    }

    /**
     * <p>
     * Tests DbTimeStatusFilterFactory#createDescriptionFilter(String,StringMatchType) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when description is null and expects IllegalArgumentException.
     * </p>
     */
    public void testCreateDescriptionFilter_NullDescription() {
        try {
            factory.createDescriptionFilter(null, StringMatchType.ENDS_WITH);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeStatusFilterFactory#createDescriptionFilter(String,StringMatchType) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when description is empty and expects IllegalArgumentException.
     * </p>
     */
    public void testCreateDescriptionFilter_EmptyDescription() {
        try {
            factory.createDescriptionFilter(" ", StringMatchType.ENDS_WITH);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeStatusFilterFactory#createDescriptionFilter(String,StringMatchType) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when matchType is null and expects IllegalArgumentException.
     * </p>
     */
    public void testCreateDescriptionFilter_NullMatchType() {
        try {
            factory.createDescriptionFilter("description", null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

}