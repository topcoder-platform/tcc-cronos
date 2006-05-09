/**
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved
 */
package com.topcoder.timetracker.user.failuretests;

import junit.framework.TestCase;
import com.topcoder.timetracker.user.*;

/**
 * Failure test cases for User.
 *
 * @author WishingBone
 * @version 1.0
 */
public class UserFailureTests extends TestCase {

    /**
     * Create with negative id.
     */
    public void testCreate_NegativeId() {
        try {
            new User(-1, "name", "store", null);
            fail("Should have thrown IllegalArgumentException.");
        } catch (IllegalArgumentException iae) {
        }
    }

    /**
     * Create with null name.
     */
    public void testCreate_NullName() {
        try {
            new User(1, null, "store", null);
            fail("Should have thrown NullPointerException.");
        } catch (NullPointerException npe) {
        }
    }

    /**
     * Create with empty name.
     */
    public void testCreate_EmptyName() {
        try {
            new User(1, "", "store", null);
            fail("Should have thrown IllegalArgumentException.");
        } catch (IllegalArgumentException iae) {
        }
    }

    /**
     * Create with null store name.
     */
    public void testCreate_NullStoreName() {
        try {
            new User(1, "name", null, null);
            fail("Should have thrown NullPointerException.");
        } catch (NullPointerException npe) {
        }
    }

    /**
     * Create with empty store name.
     */
    public void testCreate_EmptyStoreName() {
        try {
            new User(1, "name", "", null);
            fail("Should have thrown IllegalArgumentException.");
        } catch (IllegalArgumentException iae) {
        }
    }

}
