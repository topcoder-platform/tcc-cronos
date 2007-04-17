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
import com.topcoder.timetracker.entry.time.db.DbTimeStatusFilterFactory;

/**
 * <p>
 * Accuracy Unit test cases for DbTimeStatusFilterFactory.
 * </p>
 *
 * @author victorsam
 * @version 3.2
 */
public class DbTimeStatusFilterFactoryAccuracyTests extends TestCase {
    /**
     * <p>
     * DbTimeStatusFilterFactory instance for testing.
     * </p>
     */
    private DbTimeStatusFilterFactory instance;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     */
    protected void setUp() {
        Map columnNames = new HashMap();
        columnNames.put(DbTimeStatusFilterFactory.CREATION_DATE_COLUMN_NAME, "creation_date");
        columnNames.put(DbTimeStatusFilterFactory.MODIFICATION_DATE_COLUMN_NAME, "modification_date");
        columnNames.put(DbTimeStatusFilterFactory.CREATION_USER_COLUMN_NAME, "creation_user");
        columnNames.put(DbTimeStatusFilterFactory.MODIFICATION_USER_COLUMN_NAME, "modification_user");
        columnNames.put(DbTimeStatusFilterFactory.DESCRIPTION_COLUMN_NAME, "description");

        instance = new DbTimeStatusFilterFactory(columnNames);
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
        return new TestSuite(DbTimeStatusFilterFactoryAccuracyTests.class);
    }

    /**
     * <p>
     * Tests ctor DbTimeStatusFilterFactory#DbTimeStatusFilterFactory(Map) for accuracy.
     * </p>
     */
    public void testCtor1() {
        assertNotNull("Failed to create DbTimeStatusFilterFactory instance.", instance);
    }

    /**
     * <p>
     * Tests DbTimeStatusFilterFactory#createDescriptionFilter(String,StringMatchType) for accuracy.
     * </p>
     */
    public void testCreateDescriptionFilter() {
        EqualToFilter filter = (EqualToFilter) instance.createDescriptionFilter("description",
            StringMatchType.EXACT_MATCH);
        assertEquals("Failed to create the description filter correctly.", "description", filter.getName());
    }

}