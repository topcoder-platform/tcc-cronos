/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.actions;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Unit test for <code>{@link DirectStrutsActionsHelper}</code> class.
 * </p>
 *
 * @author FireIce
 * @version 1.0
 */
public class DirectStrutsActionsHelperUnitTests extends TestCase {

    /**
     * <p>
     * Aggregates all tests in this class.
     * </p>
     *
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(DirectStrutsActionsHelperUnitTests.class);
    }

    /**
     * <p>
     * Tests the <code>checkNull(Object, String)</code> method.
     * </p>
     * <p>
     * if the object is null, should throw IllegalArgumentException.
     * </p>
     */
    public void testCheckNull_null() {
        try {
            DirectStrutsActionsHelper.checkNull(null, "name");

            fail("if the object is null, should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the <code>checkNull(Object, String)</code> method.
     * </p>
     */
    public void testCheckNull_accuracy() {
        DirectStrutsActionsHelper.checkNull(new Object(), "name");

        // no exception should be thrown.
    }
}
