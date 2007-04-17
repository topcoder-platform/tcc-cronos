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
import com.topcoder.timetracker.rejectreason.RejectEmailSearchBuilder;

/**
 * Failure test for <code>{@link com.topcoder.timetracker.rejectreason.RejectEmailSearchBuilder}</code>
 * class.
 *
 * @author mittu
 * @version 1.0
 */
public class RejectEmailSearchBuilderTest extends TestCase {
    /**
     * <p>
     * Represents the RejectEmailSearchBuilder to test.
     * </p>
     */
    private RejectEmailSearchBuilder builder;

    /**
     * Sets up test environment.
     *
     * @throws Exception
     *             to junit.
     */
    protected void setUp() throws Exception {
        builder = new RejectEmailSearchBuilder();
    }

    /**
     * Failure test for <code>{@link RejectEmailSearchBuilder#hasBodyFilter(String)}</code>.
     */
    public void testMethodHasBodyFilter_IAE() {
        try {
            builder.hasBodyFilter(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            builder.hasBodyFilter(" ");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link RejectEmailSearchBuilder#not(Filter)}</code>.
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
     * Failure test for <code>{@link RejectEmailSearchBuilder#hasCompanyIdFilter(long)}</code>.
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
     * Failure test for <code>{@link RejectEmailSearchBuilder#modifiedByUserFilter(String)}</code>.
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
     * Failure test for <code>{@link RejectEmailSearchBuilder#modifiedWithinDateRangeFilter(Date,Date)}</code>.
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
     * Failure test for <code>{@link RejectEmailSearchBuilder#createdByUserFilter(String)}</code>.
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
     * Failure test for <code>{@link RejectEmailSearchBuilder#and(Filter,Filter)}</code>.
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
     * Failure test for <code>{@link RejectEmailSearchBuilder#and(Filter[])}</code>.
     */
    public void testMethodAnd_FilterArray_IAE() {
        try {
            builder.and(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            builder.and(new Filter[]{});
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            builder.and(new Filter[]{null});
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link RejectEmailSearchBuilder#or(Filter,Filter)}</code>.
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
     * Failure test for <code>{@link RejectEmailSearchBuilder#or(Filter[])}</code>.
     */
    public void testMethodOr_FilterArray_IAE() {
        try {
            builder.or(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            builder.or(new Filter[]{});
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            builder.or(new Filter[]{null});
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link RejectEmailSearchBuilder#createdWithinDateRangeFilter(Date,Date)}</code>.
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
     * Returns all tests.
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(RejectEmailSearchBuilderTest.class);
    }
}
