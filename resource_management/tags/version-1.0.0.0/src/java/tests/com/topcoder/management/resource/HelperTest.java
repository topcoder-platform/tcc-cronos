/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource;

import junit.framework.TestCase;

/**
 * Unit tests for the class: Helper.
 *
 * @author kinfkong
 * @version 1.0
 */
public class HelperTest extends TestCase {

    /**
     * Tests method: checkLongPositive(long, String).
     *
     * Checks if the IllegalArgumentException could be thrown.
     */
    public void testCheckLongPositive() {
        try {
            Helper.checkLongPositive(-1, "test");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Tests method: checkNull(Object, String).
     *
     * Checks if the IllegalArgumentException could be thrown.
     */
    public void testCheckNull() {
        try {
            Helper.checkNull(null, "test");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

}
