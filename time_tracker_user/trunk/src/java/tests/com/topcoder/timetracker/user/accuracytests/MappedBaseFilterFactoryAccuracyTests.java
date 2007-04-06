/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user.accuracytests;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.GreaterThanOrEqualToFilter;
import com.topcoder.search.builder.filter.LessThanOrEqualToFilter;
import com.topcoder.search.builder.filter.LikeFilter;
import com.topcoder.timetracker.user.StringMatchType;
import com.topcoder.timetracker.user.filterfactory.MappedBaseFilterFactory;
import com.topcoder.timetracker.user.filterfactory.MappedUserFilterFactory;

/**
 * <p>
 * Accuracy Unit test cases for MappedBaseFilterFactory.
 * </p>
 *
 * @author victorsam
 * @version 3.2
 */
public class MappedBaseFilterFactoryAccuracyTests extends TestCase {
    /**
     * <p>
     * MappedBaseFilterFactory instance for testing.
     * </p>
     */
    private MappedBaseFilterFactory instance;

    /**
     * <p>
     * Setup test environment.
     * </p>
     *
     */
    protected void setUp() {
        Map columnNames = new HashMap();
        columnNames.put(MappedUserFilterFactory.CREATION_DATE_COLUMN_NAME, "creation_date");
        columnNames.put(MappedUserFilterFactory.MODIFICATION_DATE_COLUMN_NAME, "modification_date");
        columnNames.put(MappedUserFilterFactory.CREATION_USER_COLUMN_NAME, "creation_user");
        columnNames.put(MappedUserFilterFactory.MODIFICATION_USER_COLUMN_NAME, "modification_user");

        instance = new MappedBaseFilterFactory(columnNames);
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
        return new TestSuite(MappedBaseFilterFactoryAccuracyTests.class);
    }

    /**
     * <p>
     * Tests ctor MappedBaseFilterFactory#MappedBaseFilterFactory(Map) for accuracy.
     * </p>
     */
    public void testCtor1() {
        assertNotNull("Failed to create MappedBaseFilterFactory instance.", instance);
    }

    /**
     * <p>
     * Tests MappedBaseFilterFactory#createCreationDateFilter(Date,Date) for accuracy.
     * </p>
     */
    public void testCreateCreationDateFilter() {
        LessThanOrEqualToFilter filter = (LessThanOrEqualToFilter) instance.createCreationDateFilter(null, new Date());
        assertEquals("Failed to create the creation date filter.", "creation_date", filter.getName());
    }

    /**
     * <p>
     * Tests MappedBaseFilterFactory#createModificationDateFilter(Date,Date) for accuracy.
     * </p>
     */
    public void testCreateModificationDateFilter() {
        GreaterThanOrEqualToFilter filter = (GreaterThanOrEqualToFilter) instance.createModificationDateFilter(
            new Date(), null);
        assertEquals("Failed to create the modification date filter.", "modification_date", filter.getName());
    }

    /**
     * <p>
     * Tests MappedBaseFilterFactory#createCreationUserFilter(StringMatchType,String) for accuracy.
     * </p>
     */
    public void testCreateCreationUserFilter() {
        LikeFilter filter = (LikeFilter) instance.createCreationUserFilter(StringMatchType.ENDS_WITH, "name");
        assertEquals("Failed to create the creation user filter.", "creation_user", filter.getName());
    }

    /**
     * <p>
     * Tests MappedBaseFilterFactory#createModificationUserFilter(StringMatchType,String) for accuracy.
     * </p>
     */
    public void testCreateModificationUserFilter() {
        EqualToFilter filter = (EqualToFilter) instance.createModificationUserFilter(StringMatchType.EXACT_MATCH,
            "name");
        assertEquals("Failed to create the modification user filter.", "modification_user", filter.getName());
    }

}