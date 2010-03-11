/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.actions.ajax;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Unit tests for <code>Helper</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class HelperUnitTest extends TestCase {

    /**
     * <p>
     * Returns the test suite of this class.
     * </p>
     *
     * @return the test suite of this class.
     */
    public static Test suite() {
        return new TestSuite(HelperUnitTest.class);
    }

    /**
     * <p>
     * Accuracy test for <code>checkNull</code> , if param is null, IllegalArgumentException should be thrown.
     * </p>
     */
    public void testCheckNull() {
        try {
            Helper.checkNull(null, "null");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok;
        }
    }

    /**
     * <p>
     * Accuracy test for <code>checkNull</code> , if param is not null, it should be ok.
     * </p>
     */
    public void testCheckNull_2() {
        Helper.checkNull("not null", "not null");

    }

    /**
     * <p>
     * Accuracy test for <code>checkString</code> , if param is null, IllegalArgumentException should be
     * thrown.
     * </p>
     */
    public void testCheckString_1() {
        try {
            Helper.checkString(null, "null");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok;
        }
    }

    /**
     * <p>
     * Accuracy test for <code>checkString</code> , if param is empty, IllegalArgumentException should be
     * thrown.
     * </p>
     */
    public void testCheckString_2() {
        try {
            Helper.checkString("   ", "empty");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok;
        }
    }

    /**
     * <p>
     * Accuracy test for <code>checkString</code> , if param is not null nor empty, it should be ok.
     * </p>
     */
    public void testCheckString_3() {
        Helper.checkString("ok", "ok");
    }
}
