/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.auction.persistence.failuretests;

import com.orpheus.auction.persistence.CustomBid;

import junit.framework.TestCase;

/**
 * <p>
 * Failure test for <code>{@link com.orpheus.auction.persistence.CustomBid}</code> class.
 * </p>
 * @author FireIce
 * @version 1.0
 */
public class CustomBidFailureTest extends TestCase {
    /**
     * <p>
     * Failure test for <code>{@link com.orpheus.auction.persistence.CustomBid#CustomBid(long, long, int, Date)}</code>
     * constructor.
     * </p>
     */
    public void testCtor_IAE() {
        try {
            new CustomBid(1, 1, 1, null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
