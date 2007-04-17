/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.rejectreason.failuretests;

import java.sql.Date;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.rejectreason.RejectReasonSearchBuilder;

/**
 * Failure test for <code>{@link com.topcoder.timetracker.rejectreason.RejectReasonSearchBuilder}</code>
 * class.
 *
 * @author mittu
 * @version 1.0
 */
public class RejectReasonSearchBuilderTest extends TestCase {
    /**
     * <p>
     * Represents the RejectReasonSearchBuilder to test.
     * </p>
     */
    private RejectReasonSearchBuilder builder;

    /**
     * Sets up test environment.
     *
     * @throws Exception
     *             to junit.
     */
    protected void setUp() throws Exception {
        builder = new RejectReasonSearchBuilder();
    }

    /**
     * Failure test for <code>{@link RejectReasonSearchBuilder#hasActiveStatusFilter(int)}</code>.
     */
    public void testMethodHasActiveStatusFilter_IAE() {
        try {
            builder.hasActiveStatusFilter(2);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link RejectReasonSearchBuilder#or(Filter,Filter)}</code>.
     */
    public void testMethodOr_IAE() {
        try {
            builder.or(null, null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            builder.or(new EqualToFilter("dummy", new Long(1)), null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            builder.or(null, new EqualToFilter("dummy", new Long(1)));
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link RejectReasonSearchBuilder#or(Filter[])}</code>.
     */
    public void testMethodOr_Filter_IAE() {
        try {
            builder.or(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            builder.or(new Filter[] {});
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            builder.or(new Filter[] { null });
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link RejectReasonSearchBuilder#and(Filter,Filter)}</code>.
     */
    public void testMethodAnd_IAE() {
        try {
            builder.and(null, null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            builder.and(new EqualToFilter("dummy", new Long(1)), null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            builder.and(null, new EqualToFilter("dummy", new Long(1)));
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link RejectReasonSearchBuilder#and(Filter[])}</code>.
     */
    public void testMethodAnd_FilterArray_IAE() {
        try {
            builder.and(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            builder.and(new Filter[] {});
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            builder.and(new Filter[] { null });
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link RejectReasonSearchBuilder#modifiedByUserFilter(String)}</code>.
     */
    public void testMethodModifiedByUserFilter_IAE() {
        try {
            builder.modifiedByUserFilter(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            builder.modifiedByUserFilter(" ");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link RejectReasonSearchBuilder#hasDescriptionFilter(String)}</code>.
     */
    public void testMethodHasDescriptionFilter_IAE() {
        try {
            builder.hasDescriptionFilter(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            builder.hasDescriptionFilter(" ");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for
     * <code>{@link RejectReasonSearchBuilder#modifiedWithinDateRangeFilter(Date,Date)}</code>.
     */
    public void testMethodModifiedWithinDateRangeFilter_IAE() {
        try {
            builder.modifiedWithinDateRangeFilter(null, null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            builder.modifiedWithinDateRangeFilter(new Date(9999), new Date(8888));
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link RejectReasonSearchBuilder#createdWithinDateRangeFilter(Date,Date)}</code>.
     */
    public void testMethodCreatedWithinDateRangeFilter_IAE() {
        try {
            builder.createdWithinDateRangeFilter(null, null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            builder.createdWithinDateRangeFilter(new Date(9999), new Date(8888));
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link RejectReasonSearchBuilder#not(Filter)}</code>.
     */
    public void testMethodNot_IAE() {
        try {
            builder.not(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link RejectReasonSearchBuilder#createdByUserFilter(String)}</code>.
     */
    public void testMethodCreatedByUserFilter_IAE() {
        try {
            builder.createdByUserFilter(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            builder.createdByUserFilter(" ");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link RejectReasonSearchBuilder#hasCompanyIdFilter(long)}</code>.
     */
    public void testMethodHasCompanyIdFilter_IAE() {
        try {
            builder.hasCompanyIdFilter(0);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            builder.hasCompanyIdFilter(-1);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Returns all tests.
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(RejectReasonSearchBuilderTest.class);
    }
}
