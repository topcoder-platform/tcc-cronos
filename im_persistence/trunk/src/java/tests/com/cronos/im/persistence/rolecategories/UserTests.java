/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.cronos.im.persistence.rolecategories;

import junit.framework.TestCase;

/**
 * Unit tests for the <code>User</code> class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */

public class UserTests extends TestCase {
    /**
     * Tests that the constructor throws <code>IllegalArgumentException</code> when passed a negative ID.
     */
    public void test_ctor_bad_id() {
        try {
            new User(-1, "username");
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    // the normal operation of the constructor is tested below, so there is no need for a special test case

    /**
     * Tests the <code>getName</code> method.
     */
    public void test_getName() {
        assertEquals("getName should return the name passed to the constructor", "username",
                     new User(5, "username").getName());
    }

    /**
     * Tests the <code>getId</code> method.
     */
    public void test_getId() {
        assertEquals("getId should return the ID passed to the constructor", 7, new User(7, "username").getId());
    }
}
