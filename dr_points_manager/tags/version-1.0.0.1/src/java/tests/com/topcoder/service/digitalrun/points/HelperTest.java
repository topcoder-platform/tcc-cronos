/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.points;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Unit test cases for class Helper. All the method are tested.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class HelperTest extends TestCase {

    /**
     * Get this test suite.
     * @return this test suite
     */
    public static Test suite() {
        return new TestSuite(HelperTest.class);
    }

    /**
     * <p>
     * Accuracy test for the <code>checkNull(Object param, String paramName)</code> method,
     * expects no error occurs.
     * </p>
     */
    public void testCheckNull_Accuracy() {
        Helper.checkNull(new Object(), "Object");
    }

    /**
     * <p>
     * Failure test for the <code>checkNull(Object param, String paramName)</code> method with the
     * param is null.<br>
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testCheckNull_WithNull() {
        try {
            Helper.checkNull(null, "Null");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Accuracy test for the <code>checkNullAndEmpty(String param, String paramName)</code>
     * method, expects no error occurs.
     * </p>
     */
    public void testCheckNullAndEmpty_Accuracy() {
        Helper.checkNullAndEmpty("str", "string");
    }

    /**
     * <p>
     * Failure test for the <code>checkNullAndEmpty(String param, String paramName)</code> method
     * with the param is null.<br>
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testCheckNullAndEmpty_WithNull() {
        try {
            Helper.checkNullAndEmpty(null, "Null");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>checkNullAndEmpty(String param, String paramName)</code> method
     * with the param is empty.<br>
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testCheckNullAndEmpty_WithEmpty() {
        try {
            Helper.checkNullAndEmpty("", "Empty");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>checkNullAndEmpty(String param, String paramName)</code> method
     * with the param is trimmed empty.<br>
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testCheckNullAndEmpty_WithTrimmedEmpty() {
        try {
            Helper.checkNullAndEmpty("    ", "Trimmed Empty");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

}
