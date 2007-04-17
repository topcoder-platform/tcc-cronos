/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time.accuracytests;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.timetracker.entry.time.StringMatchType;
import com.topcoder.timetracker.entry.time.db.DbTaskTypeFilterFactory;

/**
 * <p>
 * Accuracy Unit test cases for DbTaskTypeFilterFactory.
 * </p>
 *
 * @author victorsam
 * @version 3.2
 */
public class DbTaskTypeFilterFactoryAccuracyTests extends TestCase {
    /**
     * <p>
     * DbTaskTypeFilterFactory instance for testing.
     * </p>
     */
    private DbTaskTypeFilterFactory instance;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     */
    protected void setUp() {
        Map columnNames = new HashMap();
        columnNames.put(DbTaskTypeFilterFactory.CREATION_DATE_COLUMN_NAME, "creation_date");
        columnNames.put(DbTaskTypeFilterFactory.MODIFICATION_DATE_COLUMN_NAME, "modification_date");
        columnNames.put(DbTaskTypeFilterFactory.CREATION_USER_COLUMN_NAME, "creation_user");
        columnNames.put(DbTaskTypeFilterFactory.MODIFICATION_USER_COLUMN_NAME, "modification_user");
        columnNames.put(DbTaskTypeFilterFactory.COMPANY_ID_COLUMN_NAME, "comp_company_id");
        columnNames.put(DbTaskTypeFilterFactory.DESCRIPTION_COLUMN_NAME, "description");
        columnNames.put(DbTaskTypeFilterFactory.ACTIVE_COLUMN_NAME, "active");

        instance = new DbTaskTypeFilterFactory(columnNames);
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
        return new TestSuite(DbTaskTypeFilterFactoryAccuracyTests.class);
    }

    /**
     * <p>
     * Tests ctor DbTaskTypeFilterFactory#DbTaskTypeFilterFactory(Map) for accuracy.
     * </p>
     */
    public void testCtor1() {
        assertNotNull("Failed to create DbTaskTypeFilterFactory instance.", instance);
    }

    /**
     * <p>
     * Tests DbTaskTypeFilterFactory#createDescriptionFilter(String,StringMatchType) for accuracy.
     * </p>
     */
    public void testCreateDescriptionFilter() {
        EqualToFilter filter = (EqualToFilter) instance.createDescriptionFilter("description",
            StringMatchType.EXACT_MATCH);
        assertEquals("Failed to create the filter.", "description", filter.getName());
    }

    /**
     * <p>
     * Tests DbTaskTypeFilterFactory#createCompanyIdFilter(long) for accuracy.
     * </p>
     */
    public void testCreateCompanyIdFilter() {
        EqualToFilter filter = (EqualToFilter) instance.createCompanyIdFilter(8);
        assertEquals("Failed to create the filter.", "comp_company_id", filter.getName());
    }

    /**
     * <p>
     * Tests DbTaskTypeFilterFactory#createActiveFilter(boolean) for accuracy.
     * </p>
     */
    public void testCreateActiveFilter() {
        EqualToFilter filter = (EqualToFilter) instance.createActiveFilter(false);
        assertEquals("Failed to create the filter.", "active", filter.getName());
    }

}