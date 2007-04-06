/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.base;

import junit.framework.TestCase;


/**
 * Test case for helper class ParameterCheck.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ParameterCheckTest extends TestCase {
    /**
     * Tests {@link ParameterCheck#checkEmpty(String, String)} with not empty string, will be success.
     */
    public void testCheckEmpty() {
        ParameterCheck.checkEmpty("name", "not_empty");

        //success
    }

    /**
     * Tests {@link ParameterCheck#checkEmpty(String, String)} with empty string, IAE is expected.
     */
    public void testCheckEmptyEmpty() {
        try {
            ParameterCheck.checkEmpty("name", "");
            fail("argument is empty string and IAE is expected");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * Tests {@link ParameterCheck#checkEmpty(String, String)} with null, will be success.
     */
    public void testCheckEmptyNull() {
        ParameterCheck.checkEmpty("name", null);

        //null is allowed and no exception is expected
    }

    /**
     * Tests {@link ParameterCheck#checkNotPositive(String, long)} with positive, will be success.
     */
    public void testCheckNotPositive() {
        ParameterCheck.checkNotPositive("name", 1);

        //success
    }

    /**
     * Tests {@link ParameterCheck#checkNotPositive(String, long)} with not positive long, IAE is expected.
     */
    public void testCheckNotPositiveInvalid() {
        try {
            ParameterCheck.checkNotPositive("name", 0);
            fail("argument is not positive and IAE is expected");
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
