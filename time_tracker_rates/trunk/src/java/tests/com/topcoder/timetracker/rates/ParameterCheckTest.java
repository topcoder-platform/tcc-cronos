/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.rates;

import junit.framework.TestCase;


/**
 * Test case for helper class ParameterCheck.
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class ParameterCheckTest extends TestCase {
    /**
     * Tests {@link ParameterCheck#checkArray(String, Object[])} with valid arguments, should be success.
     */
    public void testCheckArray() {
        Object[] array = new Object[] {"elem1", "elem2" };
        ParameterCheck.checkArray("array", array);

        //success since the argument is valid
    }

    /**
     * Tests {@link ParameterCheck#checkArray(String, Object[])} with empty array, IAE is expected.
     */
    public void testCheckArrayEmpty() {
        try {
            ParameterCheck.checkArray("array", new Object[] {});
            fail("array is empty and IAE is expected");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * Tests {@link ParameterCheck#checkArray(String, Object[])} with null, IAE is expected.
     */
    public void testCheckArrayNull() {
        try {
            ParameterCheck.checkArray("array", null);
            fail("array is null and IAE is expected");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * Tests {@link ParameterCheck#checkArray(String, Object[])} with array contains null, IAE is expected.
     */
    public void testCheckArrayNullElem() {
        try {
            ParameterCheck.checkArray("array", new Object[] {"obj1", null, "obj2" });
            fail("array contains null and IAE is expected");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * Tests {@link ParameterCheck#checkNull(String, Object)} with valid arguments, should be success.
     */
    public void testCheckNull() {
        ParameterCheck.checkNull("name", "obj");

        //success, since argument is valid
    }

    /**
     * Tests {@link ParameterCheck#checkNullEmpty(String, String)} with valid arguments, should be success.
     */
    public void testCheckNullEmpty() {
        ParameterCheck.checkNullEmpty("name", "string");

        //success, since argument is valid
    }

    /**
     * Tests {@link ParameterCheck#checkNullEmpty(String, String)} with empty string, IAE is expected.
     */
    public void testCheckNullEmptyEmpty() {
        try {
            ParameterCheck.checkNullEmpty("name", " ");
            fail("argument is empty and IAE is expected");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * Tests {@link ParameterCheck#checkNullEmpty(String, String)} with null string, IAE is expected.
     */
    public void testCheckNullEmptyNull() {
        try {
            ParameterCheck.checkNullEmpty("name", null);
            fail("argument is null and IAE is expected");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * Tests {@link ParameterCheck#checkNull(String, Object)} with null, IAE is expected.
     */
    public void testCheckNullNull() {
        try {
            ParameterCheck.checkNull("name", null);
            fail("argument is null and IAE is expected");
        } catch (IllegalArgumentException e) {
            //success
        }
    }
}
