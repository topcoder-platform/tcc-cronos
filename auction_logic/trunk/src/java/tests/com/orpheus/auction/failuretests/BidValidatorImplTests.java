/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.auction.failuretests;

import java.util.Date;

import junit.framework.TestCase;

import com.orpheus.auction.BidValidatorImpl;
import com.orpheus.auction.persistence.CustomBid;

/**
 * <p>
 * Unit test cases for the BidValidatorImpl class.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class BidValidatorImplTests extends TestCase {
    /**
     * <p>
     * BidValidatorImpl instance. Used for testing.
     * </p>
     */
    private BidValidatorImpl validator;

    /**
     * <p>
     * Creates new required instances.
     * </p>
     * @throws Exception
     *             to JUnit.
     */
    protected void setUp() throws Exception {
        validator = new BidValidatorImpl();
    }

    /**
     * Failure test for the method
     * <code>boolean isBidUpdateValid(Auction auction, Bid originalBid, Bid newBid)</code>.
     * <p>
     * auction is null. IllegalArgumentException is expected.
     */
    public void testIsBidUpdateValid1() {
        try {
            validator.isBidUpdateValid(null, new CustomBid(1, 2, 3, new Date()), new CustomBid(9, 8, 7, new Date()));
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Failure test for the method
     * <code>boolean isBidUpdateValid(Auction auction, Bid originalBid, Bid newBid)</code>.
     * <p>
     * originalBid is null. IllegalArgumentException is expected.
     * </p>
     */
    public void testIsBidUpdateValid2() {
        try {
            validator.isBidUpdateValid(new MockAuction(), null, new CustomBid(9, 8, 7, new Date()));
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Failure test for the method
     * <code>boolean isBidUpdateValid(Auction auction, Bid originalBid, Bid newBid)</code>.
     * <p>
     * newBid is null. IllegalArgumentException is expected.
     */
    public void testIsBidUpdateValid3() {
        try {
            validator.isBidUpdateValid(new MockAuction(), new CustomBid(9, 8, 7, new Date()), null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Failure test for the method <code>boolean isNewBidValid(Auction auction, Bid bid)</code>.
     * <p>
     * auction is null. IllegalArgumentException is expected.
     */
    public void testIsNewBidValid1() {
        try {
            validator.isNewBidValid(null, new CustomBid(9, 8, 7, new Date()));
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Failure test for the method <code>boolean isNewBidValid(Auction auction, Bid bid)</code>.
     * <p>
     * bid is null. IllegalArgumentException is expected.
     */
    public void testIsNewBidValid2() {
        try {
            validator.isNewBidValid(new MockAuction(), null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

}
