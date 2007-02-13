/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user.failuretests;

import java.util.Date;

import junit.framework.TestCase;

import com.topcoder.timetracker.common.RejectEmailSearchBuilder;

/**
 * <p>
 * Failure unit test cases for the RejectEmailSearchBuilder class.
 * </p>
 * @author agh
 * @version 2.0
 * @since 2.0
 */
public class RejectEmailSearchBuilderFailureTests extends TestCase {

    /**
     * <p>
     * The RejectEmailSearchBuilder instance used for testing.
     * </p>
     */
    private RejectEmailSearchBuilder builder = null;

    /**
     * <p>
     * Creates RejectEmailSearchBuilder instance.
     * </p>
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        builder = new RejectEmailSearchBuilder();
    }

    /**
     * <p>
     * Tests buildFilter() for failure. No criteria has been set, IllegalStateException is expected.
     * </p>
     */
    public void testBuildFilter1() {
        try {
            builder.buildFilter();
            fail("IllegalStateException should be thrown");
        } catch (IllegalStateException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests createdByUser(String) for failure. Passes empty string, IllegalArgumentException is expected.
     * </p>
     */
    public void testCreatedByUser1() {
        try {
            builder.createdByUser(" ");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests createdWithinDateRange(Date, Date) for failure. Passes nulls, IllegalArgumentException is
     * expected.
     * </p>
     */
    public void testCreatedWithinDateRange1() {
        try {
            builder.createdWithinDateRange(null, null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests createdWithinDateRange(Date, Date) for failure. Passes endDate less than startDate,
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testCreatedWithinDateRange2() {
        try {
            builder.createdWithinDateRange(new Date(8111111), new Date(1111111));
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests modifiedByUser(String) for failure. Passes empty string, IllegalArgumentException is expected.
     * </p>
     */
    public void testModifiedByUser1() {
        try {
            builder.modifiedByUser(" ");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests modifiedWithinDateRange(Date, Date) for failure. Passes nulls, IllegalArgumentException is
     * expected.
     * </p>
     */
    public void testModifiedWithinDateRange1() {
        try {
            builder.modifiedWithinDateRange(null, null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests modifiedWithinDateRange(Date, Date) for failure. Passes endDate less than startDate,
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testModifiedWithinDateRange2() {
        try {
            builder.modifiedWithinDateRange(new Date(8111111), new Date(1111111));
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
