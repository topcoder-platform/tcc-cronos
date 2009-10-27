/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Some tests for Util class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class UtilTest extends TestCase {
    /**
     * <p>Returns the test suite of this class.</p>
     *
     * @return the test suite of this class.
     */
    public static Test suite() {
        return new TestSuite(UtilTest.class);
    }

    /**
     * <p>Sets up test environment.</p>
     *
     * @throws Exception to junit
     */
    protected void setUp() throws Exception {
    }

    /**
     * <p>Tears down test environment.</p>
     *
     * @throws Exception to junit
     */
    protected void tearDown() throws Exception {
    }

    /**
     * Tests checkNull method for valid data.
     */
    public void testCheckNull1() {
        Util.checkNull("name", "x");
    }

    /**
     * Tests checkNull method for null. IllegalArgumentException expected.
     */
    public void testCheckNull2() {
        try {
            Util.checkNull("name", null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }
}
