/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time.db;

import java.util.HashMap;
import java.util.Map;

import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.LikeFilter;
import com.topcoder.timetracker.entry.time.StringMatchType;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

/**
 * <p>
 * Unit test cases for DbTaskTypeFilterFactory.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class DbTaskTypeFilterFactoryTests extends TestCase {
    /**
     * <p>
     * The DbTaskTypeFilterFactory instance for testing.
     * </p>
     */
    private DbTaskTypeFilterFactory factory;

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
        columnNames.put(DbTaskTypeFilterFactory.CREATION_DATE_COLUMN_NAME, "creation_date");
        columnNames.put(DbTaskTypeFilterFactory.MODIFICATION_DATE_COLUMN_NAME, "modification_date");
        columnNames.put(DbTaskTypeFilterFactory.CREATION_USER_COLUMN_NAME, "creation_user");
        columnNames.put(DbTaskTypeFilterFactory.MODIFICATION_USER_COLUMN_NAME, "modification_user");
        columnNames.put(DbTaskTypeFilterFactory.COMPANY_ID_COLUMN_NAME, "comp_company_id");
        columnNames.put(DbTaskTypeFilterFactory.DESCRIPTION_COLUMN_NAME, "description");
        columnNames.put(DbTaskTypeFilterFactory.ACTIVE_COLUMN_NAME, "active");

        factory = new DbTaskTypeFilterFactory(columnNames);
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
        return new TestSuite(DbTaskTypeFilterFactoryTests.class);
    }

    /**
     * <p>
     * Tests ctor DbTaskTypeFilterFactory#DbTaskTypeFilterFactory(Map) for accuracy.
     * </p>
     *
     * <p>
     * It verifies the newly created DbTaskTypeFilterFactory instance should not be null.
     * </p>
     */
    public void testCtor() {
        assertNotNull("Failed to create a new DbTaskTypeFilterFactory instance.", factory);
    }

    /**
     * <p>
     * Tests ctor DbTaskTypeFilterFactory#DbTaskTypeFilterFactory(Map) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when columnNames is null and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor_NullColumnNames() {
        try {
            new DbTaskTypeFilterFactory(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DbTaskTypeFilterFactory#DbTaskTypeFilterFactory(Map) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when key is empty and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor_EmptyKey() {
        columnNames.put(" ", "modification_user");
        try {
            new DbTaskTypeFilterFactory(columnNames);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DbTaskTypeFilterFactory#DbTaskTypeFilterFactory(Map) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when key is not String type and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor_KeyNotString() {
        columnNames.put(new Long(8), "modification_user");
        try {
            new DbTaskTypeFilterFactory(columnNames);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DbTaskTypeFilterFactory#DbTaskTypeFilterFactory(Map) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when columnNames is missing some keys and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor_MissSomeKey() {
        columnNames.remove(DbTaskTypeFilterFactory.MODIFICATION_USER_COLUMN_NAME);
        try {
            new DbTaskTypeFilterFactory(columnNames);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DbTaskTypeFilterFactory#DbTaskTypeFilterFactory(Map) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when value is empty and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor_EmptyValue() {
        columnNames.put(DbTaskTypeFilterFactory.MODIFICATION_USER_COLUMN_NAME, " ");
        try {
            new DbTaskTypeFilterFactory(columnNames);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DbTaskTypeFilterFactory#DbTaskTypeFilterFactory(Map) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when value is not String type and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor_ValueNotString() {
        columnNames.put(DbTaskTypeFilterFactory.MODIFICATION_USER_COLUMN_NAME, new Long(8));
        try {
            new DbTaskTypeFilterFactory(columnNames);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTaskTypeFilterFactory#createDescriptionFilter(String,StringMatchType) for accuracy.
     * </p>
     *
     * <p>
     * It verifies DbTaskTypeFilterFactory#createDescriptionFilter(String,StringMatchType) is correct.
     * </p>
     */
    public void testCreateDescriptionFilter() {
        LikeFilter filter = (LikeFilter) factory.createDescriptionFilter("description", StringMatchType.ENDS_WITH);
        assertEquals("Failed to create the description filter correctly.", "description", filter.getName());
        assertEquals("Failed to create the description filter correctly.", "EW:description", filter.getValue());
    }

    /**
     * <p>
     * Tests DbTaskTypeFilterFactory#createDescriptionFilter(String,StringMatchType) for failure.
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
     * Tests DbTaskTypeFilterFactory#createDescriptionFilter(String,StringMatchType) for failure.
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
     * Tests DbTaskTypeFilterFactory#createDescriptionFilter(String,StringMatchType) for failure.
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

    /**
     * <p>
     * Tests DbTaskTypeFilterFactory#createCompanyIdFilter(long) for accuracy.
     * </p>
     *
     * <p>
     * It verifies DbTaskTypeFilterFactory#createCompanyIdFilter(long) is correct.
     * </p>
     */
    public void testCreateCompanyIdFilter() {
        EqualToFilter filter = (EqualToFilter) factory.createCompanyIdFilter(8);
        assertEquals("Failed to create the company id filter correctly.", "comp_company_id", filter.getName());
        assertEquals("Failed to create the company id filter correctly.", new Long(8), filter.getValue());
    }

    /**
     * <p>
     * Tests DbTaskTypeFilterFactory#createCompanyIdFilter(long) for failure.
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

    /**
     * <p>
     * Tests DbTaskTypeFilterFactory#createActiveFilter(boolean) for accuracy.
     * </p>
     *
     * <p>
     * It verifies DbTaskTypeFilterFactory#createActiveFilter(boolean) is correct.
     * </p>
     */
    public void testCreateActiveFilter() {
        EqualToFilter filter = (EqualToFilter) factory.createActiveFilter(true);
        assertEquals("Failed to create the active filter correctly.", "active", filter.getName());
        assertEquals("Failed to create the active filter correctly.", new Long(1), filter.getValue());
    }

}