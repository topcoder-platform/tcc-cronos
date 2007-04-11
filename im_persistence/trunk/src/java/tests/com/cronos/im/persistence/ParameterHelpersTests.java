/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.cronos.im.persistence;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

/**
 * Unit tests for the <code>ParameterHelpers</code> class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */

public class ParameterHelpersTests extends TestCase {
    /**
     * Tests that the <code>checkParameter(String)</code> method throws <code>IllegalArgumentException</code> when
     * passed a <code>null</code> string.
     */
    public void test_checkParameter_null_arg1() {
        try {
            ParameterHelpers.checkParameter((String) null, "string");
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests that the <code>checkParameter(String)</code> method throws <code>IllegalArgumentException</code> when
     * passed an empty string.
     */
    public void test_checkParameter_empty_arg1() {
        try {
            ParameterHelpers.checkParameter("  ", "string");
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests the <code>checkParameter(String)</code> method.
     */
    public void test_checkParameter() {
        ParameterHelpers.checkParameter("ok", "a string");
        // no exception should be thrown
    }

    /**
     * Tests that the <code>checkParameter(Object)</code> method throws <code>IllegalArgumentException</code> when
     * passed a <code>null</code> object.
     */
    public void test_checkParameter1_null_arg1() {
        try {
            ParameterHelpers.checkParameter((Integer) null, "an object");
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests the <code>checkParameter(Object)</code> method.
     */
    public void test_checkParameter2() {
        ParameterHelpers.checkParameter(new Integer(1), "an object");
    }

    /**
     * Tests that <code>validateCriteria</code> throws <code>IllegalArgumentException</code> when passed a bad
     * type.
     */
    public void test_validateCriteria_bad_type() {
        Map map = new HashMap();
        try {
            map.put(new Integer(1), "test");
            ParameterHelpers.validateCriteria(map);
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }
}
