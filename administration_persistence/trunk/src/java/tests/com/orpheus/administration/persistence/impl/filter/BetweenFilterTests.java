/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.administration.persistence.impl.filter;

import junit.framework.TestCase;

/**
 * Unit tests for the <code>BetweenFilter</code> class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */

public class BetweenFilterTests extends TestCase {
    /**
     * Tests that the constructor throws <code>IllegalArgumentException</code> when passed a <code>null</code>
     * name.
     */
    public void test_ctor_null_name() {
        try {
            new BetweenFilter(null, "oh no", "oh yes");
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests that the constructor throws <code>IllegalArgumentException</code> when passed an empty name.
     */
    public void test_ctor_empty_name() {
        try {
            new BetweenFilter("  ", "oh no", "oh yes");
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests the normal operation of the constructor.
     */
    public void test_ctor() {
        BetweenFilter filter = new BetweenFilter("field", "upper", "lower");
        assertEquals("getUpperThreshold() should return the thresold passed to the constructor",
                     "upper", filter.getUpperThreshold());
        assertEquals("getLowerThreshold() should return the thresold passed to the constructor",
                     "lower", filter.getLowerThreshold());
    }
}
