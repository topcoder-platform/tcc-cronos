/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.tc;

import junit.framework.JUnit4TestAdapter;

import org.junit.Test;

/**
 * <p>
 * Unit tests for class <code>Helper</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class HelperTest {
    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(HelperTest.class);
    }

    /**
     * <p>
     * Accuracy test for the method <code>checkNotNull(String, Object)</code>.<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testCheckNotNull() {
        Helper.checkNotNull("Object", new Object());
    }

    /**
     * <p>
     * Failure test for the method <code>checkNotNull(String, Object)</code>.<br>
     * The argument is null.<br>
     * Expect IllegalArgumentException.
     * </p>
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCheckNotNull_ParamNull() {
        Helper.checkNotNull("null object", null);
    }
}
