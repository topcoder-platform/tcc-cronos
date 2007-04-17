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

import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.LessThanOrEqualToFilter;
import com.topcoder.search.builder.filter.LikeFilter;
import com.topcoder.timetracker.entry.time.StringMatchType;
import com.topcoder.timetracker.entry.time.db.DbBaseFilterFactory;

/**
 * <p>
 * Accuracy Unit test cases for DbBaseFilterFactory.
 * </p>
 *
 * @author victorsam
 * @version 3.2
 */
public class DbBaseFilterFactoryAccuracyTests extends TestCase {
    /**
     * <p>
     * DbBaseFilterFactory instance for testing.
     * </p>
     */
    private DbBaseFilterFactory instance;

    /**
     * <p>
     * Setup test environment.
     * </p>
     *
     */
    protected void setUp() {
        Map columnNames = new HashMap();
        columnNames.put(DbBaseFilterFactory.CREATION_DATE_COLUMN_NAME, "creation_date");
        columnNames.put(DbBaseFilterFactory.MODIFICATION_DATE_COLUMN_NAME, "modification_date");
        columnNames.put(DbBaseFilterFactory.CREATION_USER_COLUMN_NAME, "creation_user");
        columnNames.put(DbBaseFilterFactory.MODIFICATION_USER_COLUMN_NAME, "modification_user");

        instance = new DbBaseFilterFactory(columnNames);
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
        return new TestSuite(DbBaseFilterFactoryAccuracyTests.class);
    }

    /**
     * <p>
     * Tests ctor DbBaseFilterFactory#DbBaseFilterFactory(Map) for accuracy.
     * </p>
     */
    public void testCtor1() {
        assertNotNull("Failed to create DbBaseFilterFactory instance.", instance);
    }

    /**
     * <p>
     * Tests DbBaseFilterFactory#createCreationDateFilter(Date,Date) for accuracy.
     * </p>
     */
    public void testCreateCreationDateFilter() {
        LessThanOrEqualToFilter filter = (LessThanOrEqualToFilter) instance.createCreationDateFilter(null, new Date());
        assertEquals("Failed to create the filter.", "creation_date", filter.getName());
    }

    /**
     * <p>
     * Tests DbBaseFilterFactory#createModificationDateFilter(Date,Date) for accuracy.
     * </p>
     */
    public void testCreateModificationDateFilter() {
        LessThanOrEqualToFilter filter = (LessThanOrEqualToFilter) instance.createModificationDateFilter(null,
            new Date());
        assertEquals("Failed to create the filter.", "modification_date", filter.getName());
    }

    /**
     * <p>
     * Tests DbBaseFilterFactory#createCreationUserFilter(String,StringMatchType) for accuracy.
     * </p>
     */
    public void testCreateCreationUserFilter() {
        EqualToFilter filter = (EqualToFilter) instance.createCreationUserFilter("name", StringMatchType.EXACT_MATCH);
        assertEquals("Failed to create the filter.", "creation_user", filter.getName());
    }

    /**
     * <p>
     * Tests DbBaseFilterFactory#createModificationUserFilter(String,StringMatchType) for accuracy.
     * </p>
     */
    public void testCreateModificationUserFilter() {
        LikeFilter filter = (LikeFilter) instance.createModificationUserFilter("name", StringMatchType.STARTS_WITH);
        assertEquals("Failed to create the filter.", "modification_user", filter.getName());
    }

}