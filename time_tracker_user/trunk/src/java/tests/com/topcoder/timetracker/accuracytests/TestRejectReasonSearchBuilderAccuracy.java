/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved
 */
package com.topcoder.timetracker.accuracytests;

import java.util.Date;

import com.topcoder.timetracker.common.RejectReasonSearchBuilder;
import com.topcoder.search.builder.filter.AndFilter;
import com.topcoder.search.builder.filter.BetweenFilter;
import com.topcoder.search.builder.filter.EqualToFilter;

import junit.framework.TestCase;

/**
 * Accuracy test for class <code>RejectReasonSearchBuilder</code>.
 *
 * @author Chenhong
 * @version 2.0
 */
public class TestRejectReasonSearchBuilderAccuracy extends TestCase {
    /**
     * The <code>RejectReasonSearchBuilder</code> instance to test.
     */
    private RejectReasonSearchBuilder builder = null;

    /**
     * Represents the begin date for test.
     */
    private static Date begin_date = new Date(System.currentTimeMillis() - 10000);

    /**
     * Represents the end date for test.
     */
    private static Date end_date = new Date(System.currentTimeMillis());

    /**
     * <p>
     * Sets up test environment. Creates a new <code>RejectReasonSearchBuilder</code> instance.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    protected void setUp() throws Exception {
        builder = new RejectReasonSearchBuilder();
    }

    /**
     * test constructor.
     */
    public void testRejectReasonSearchBuilder() {
        assertNotNull("The instance should be created", builder);
    }

    /**
     * test method hasCompanyId.
     *
     */
    public void testHasCompanyId() {
        builder.hasCompanyId(10);

        EqualToFilter filter = (EqualToFilter) ((AndFilter) builder.buildFilter()).getFilters().get(0);

        assertEquals("Equal is expected.", new Long(10), filter.getValue());
    }

    /**
     * Test method createdWithinDateRange.
     *
     */
    public void testCreatedWithinDateRange() {
        builder.createdWithinDateRange(begin_date, end_date);

        BetweenFilter filter = (BetweenFilter) ((AndFilter) builder.buildFilter()).getFilters().get(0);

        assertEquals("Equal is expected.", begin_date, filter.getLowerThreshold());

        assertEquals("Equal is expected.", end_date, filter.getUpperThreshold());
    }

    /**
     * test method createdByUser.
     *
     */
    public void testCreatedByUser() {
        builder.createdByUser("user");

        EqualToFilter filter = (EqualToFilter) ((AndFilter) builder.buildFilter()).getFilters().get(0);

        assertEquals("Equal is expected.", "user", filter.getValue());
    }

    /**
     * test method modifiedWithDateRange.
     *
     */
    public void testModifiedWithinDateRange_1() {
        Date m = new Date(System.currentTimeMillis() + 1000000);

        builder.modifiedWithinDateRange(begin_date, m);

        BetweenFilter filter = (BetweenFilter) ((AndFilter) builder.buildFilter()).getFilters().get(0);

        assertEquals("Equal is expected.", m, filter.getUpperThreshold());

    }

    /**
     * test method modifiedWithDateRange.
     *
     */
    public void testModifiedWithinDateRange_2() {
        Date s = new Date(System.currentTimeMillis() - 1000000);

        builder.modifiedWithinDateRange(s, end_date);

        BetweenFilter filter = (BetweenFilter) ((AndFilter) builder.buildFilter()).getFilters().get(0);

        assertEquals("Equal is expected.", s, filter.getLowerThreshold());
    }

    /**
     * Test method modifiedByUser.
     *
     */
    public void testModifiedByUser() {
        builder.modifiedByUser("userName");

        EqualToFilter filter = (EqualToFilter) ((AndFilter) builder.buildFilter()).getFilters().get(0);

        assertEquals("Equal is expected.", "userName", filter.getValue());

    }

    /**
     * test method buildFilter.
     *
     */
    public void testBuildFilter() {
        builder.createdByUser("test");
        assertNotNull("Should not be null.", builder.buildFilter());
    }

    /**
     * Test method reset. It is very simple. Just invoke it.
     *
     */
    public void testReset() {
        builder.reset();
    }
}
