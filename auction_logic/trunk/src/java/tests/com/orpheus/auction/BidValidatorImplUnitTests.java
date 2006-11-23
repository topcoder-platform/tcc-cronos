/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.auction;

import java.util.Calendar;
import java.util.Date;

import junit.framework.TestCase;

import com.orpheus.auction.persistence.CustomBid;
import com.topcoder.util.auction.Bid;

/**
 * <p>
 * Unit test cases for the BidValidatorImpl class.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class BidValidatorImplUnitTests extends TestCase {
    /**
     * <p>
     * BidValidatorImpl instance. Used for testing.
     * </p>
     */
    private BidValidatorImpl validator;

    /**
     * <p>
     * Holds the date two days before the current one.
     * </p>
     */
    private Calendar twoDaysBefore;

    /**
     * <p>
     * Holds the date two days after the current one.
     * </p>
     */
    private Calendar twoDaysAfter;

    /**
     * <p>
     * Holds the date one week before the current date.
     * </p>
     */
    private Calendar weekBefore;

    /**
     * <p>
     * Holds the date one week after the current one.
     * </p>
     */
    private Calendar weekAfter;

    /**
     * <p>
     * Creates new required instances.
     * </p>
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        validator = new BidValidatorImpl();

        // get current date
        Calendar curDate = Calendar.getInstance();
        curDate.setTimeInMillis(System.currentTimeMillis());

        twoDaysBefore = (Calendar) curDate.clone();
        twoDaysBefore.add(Calendar.DATE, -2);
        twoDaysAfter = (Calendar) curDate.clone();
        twoDaysAfter.add(Calendar.DATE, 2);

        weekBefore = (Calendar) curDate.clone();
        weekBefore.add(Calendar.DATE, -7);
        weekAfter = (Calendar) curDate.clone();
        weekAfter.add(Calendar.DATE, 7);
    }

    /**
     * <p>
     * Tests that BidValidatorImpl() instance is created.
     * </p>
     */
    public void testConstructor1() {
        BidValidatorImpl i = new BidValidatorImpl();
        assertNotNull("Unable to instantiate BidValidatorImpl", i);
    }

    /**
     * <p>
     * Tests isBidUpdateValid(Auction, Bid, Bid) for failure. Passes null as first argument,
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testIsBidUpdateValid1() {
        try {
            validator.isBidUpdateValid(null, new CustomBid(1, 2, 3, new Date()), new CustomBid(9,
                8, 7, new Date()));
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // do nothing
        }
    }

    /**
     * <p>
     * Tests isBidUpdateValid(Auction, Bid, Bid) for failure. Passes null as second argument,
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testIsBidUpdateValid2() {
        try {
            validator.isBidUpdateValid(new MockAuction(), null, new CustomBid(9, 8, 7, new Date()));
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // do nothing
        }
    }

    /**
     * <p>
     * Tests isBidUpdateValid(Auction, Bid, Bid) for failure. Passes null as third argument,
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testIsBidUpdateValid3() {
        try {
            validator.isBidUpdateValid(new MockAuction(), new CustomBid(9, 8, 7, new Date()), null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // do nothing
        }
    }

    /**
     * <p>
     * Tests that isBidUpdateValid(Auction, Bid, Bid) returns correct value. Passes not open
     * auction. False is expected.
     * </p>
     */
    public void testIsBidUpdateValid4() {
        CustomBid originalBid = new CustomBid(1, 2, 3, new Date());
        originalBid.setId(239);
        CustomBid newBid = new CustomBid(1, 2, 4, new Date());
        newBid.setId(239);

        MockAuction auction = new MockAuction();
        auction.setMinimumBid(0);
        auction.setBids(new Bid[] {originalBid});

        auction.setStartDate(weekBefore.getTime());
        auction.setEndDate(twoDaysBefore.getTime());
        assertFalse("The returned value is incorrect", validator.isBidUpdateValid(auction,
            originalBid, newBid));

        auction.setStartDate(twoDaysAfter.getTime());
        auction.setEndDate(weekAfter.getTime());
        assertFalse("The returned value is incorrect", validator.isBidUpdateValid(auction,
            originalBid, newBid));
    }

    /**
     * <p>
     * Tests that isBidUpdateValid(Auction, Bid, Bid) returns correct value. Bid has incorrect new
     * maximum amount. False is expected.
     * </p>
     */
    public void testIsBidUpdateValid5() {
        CustomBid originalBid = new CustomBid(1, 2, 3, new Date());
        originalBid.setId(239);
        CustomBid newBid = new CustomBid(1, 2, 3, new Date());
        newBid.setId(239);

        MockAuction auction = new MockAuction();
        auction.setMinimumBid(0);
        auction.setBids(new Bid[] {originalBid});
        auction.setStartDate(twoDaysBefore.getTime());
        auction.setEndDate(twoDaysAfter.getTime());

        assertFalse("The returned value is incorrect", validator.isBidUpdateValid(auction,
            originalBid, newBid));
    }

    /**
     * <p>
     * Tests that isBidUpdateValid(Auction, Bid, Bid) returns correct value. New bid has incorrect
     * id. False is expected.
     * </p>
     */
    public void testIsBidUpdateValid6() {
        CustomBid originalBid = new CustomBid(1, 2, 3, new Date());
        originalBid.setId(239);
        CustomBid newBidA = new CustomBid(1, 2, 4, new Date());
        newBidA.setId(35);
        CustomBid newBidB = new CustomBid(1, 2, 4, new Date());

        MockAuction auction = new MockAuction();
        auction.setMinimumBid(0);
        auction.setBids(new Bid[] {originalBid});
        auction.setStartDate(twoDaysBefore.getTime());
        auction.setEndDate(twoDaysAfter.getTime());

        assertFalse("The returned value is incorrect", validator.isBidUpdateValid(auction,
            originalBid, newBidA));
        assertFalse("The returned value is incorrect", validator.isBidUpdateValid(auction,
            originalBid, newBidB));
    }

    /**
     * <p>
     * Tests that isBidUpdateValid(Auction, Bid, Bid) returns correct value. New bid has incorrect
     * bidder or image id. False is expected.
     * </p>
     */
    public void testIsBidUpdateValid7() {
        CustomBid originalBid = new CustomBid(1, 2, 3, new Date());
        originalBid.setId(239);
        CustomBid newBidA = new CustomBid(111, 2, 4, new Date());
        newBidA.setId(239);
        CustomBid newBidB = new CustomBid(1, 222, 4, new Date());
        newBidB.setId(239);

        MockAuction auction = new MockAuction();
        auction.setMinimumBid(0);
        auction.setBids(new Bid[] {originalBid});
        auction.setStartDate(twoDaysBefore.getTime());
        auction.setEndDate(twoDaysAfter.getTime());

        assertFalse("The returned value is incorrect", validator.isBidUpdateValid(auction,
            originalBid, newBidA));
        assertFalse("The returned value is incorrect", validator.isBidUpdateValid(auction,
            originalBid, newBidB));
    }

    /**
     * <p>
     * Tests that isBidUpdateValid(Auction, Bid, Bid) returns correct value. Auction doesn't have
     * the original bid. False is expected.
     * </p>
     */
    public void testIsBidUpdateValid8() {
        CustomBid originalBid = new CustomBid(1, 2, 3, new Date());
        originalBid.setId(239);
        CustomBid newBid = new CustomBid(1, 2, 4, new Date());
        newBid.setId(239);

        MockAuction auction = new MockAuction();
        auction.setMinimumBid(0);
        auction.setBids(new Bid[] {new CustomBid(10, 11, 12, new Date())});
        auction.setStartDate(twoDaysBefore.getTime());
        auction.setEndDate(twoDaysAfter.getTime());

        assertFalse("The returned value is incorrect", validator.isBidUpdateValid(auction,
            originalBid, newBid));
    }

    /**
     * <p>
     * Tests that isBidUpdateValid(Auction, Bid, Bid) returns correct value. True is expected.
     * </p>
     */
    public void testIsBidUpdateValid9() {
        CustomBid originalBid = new CustomBid(1, 2, 3, new Date());
        originalBid.setId(239);
        CustomBid newBid = new CustomBid(1, 2, 4, new Date());
        newBid.setId(239);

        MockAuction auction = new MockAuction();
        auction.setMinimumBid(0);
        auction.setBids(new Bid[] {originalBid});
        auction.setStartDate(twoDaysBefore.getTime());
        auction.setEndDate(twoDaysAfter.getTime());

        assertTrue("The returned value is incorrect", validator.isBidUpdateValid(auction,
            originalBid, newBid));
    }

    /**
     * <p>
     * Tests isNewBidValid(Auction, Bid) for failure. Passes null as first argument,
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testIsNewBidValid1() {
        try {
            validator.isNewBidValid(null, new CustomBid(9, 8, 7, new Date()));
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // do nothing
        }
    }

    /**
     * <p>
     * Tests isNewBidValid(Auction, Bid) for failure. Passes null as second argument,
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testIsNewBidValid2() {
        try {
            validator.isNewBidValid(new MockAuction(), null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // do nothing
        }
    }

    /**
     * <p>
     * Tests that isNewBidValid(Auction, Bid) returns correct value. Passes not open auction. False
     * is expected.
     * </p>
     */
    public void testIsNewBidValid3() {
        MockAuction auction = new MockAuction();
        auction.setMinimumBid(0);
        auction.setBids(new Bid[0]);

        CustomBid bid = new CustomBid(1, 2, 3, new Date());

        auction.setStartDate(weekBefore.getTime());
        auction.setEndDate(twoDaysBefore.getTime());
        assertFalse("The returned value is incorrect", validator.isNewBidValid(auction, bid));

        auction.setStartDate(twoDaysAfter.getTime());
        auction.setEndDate(weekAfter.getTime());
        assertFalse("The returned value is incorrect", validator.isNewBidValid(auction, bid));
    }

    /**
     * <p>
     * Tests that isNewBidValid(Auction, Bid) returns correct value. Passes incorrect maximum
     * amount. False is expected.
     * </p>
     */
    public void testIsNewBidValid4() {
        MockAuction auction = new MockAuction();
        auction.setStartDate(twoDaysBefore.getTime());
        auction.setEndDate(twoDaysAfter.getTime());
        auction.setMinimumBid(4);
        auction.setBids(new Bid[0]);

        CustomBid bid = new CustomBid(1, 2, 3, new Date());

        assertFalse("The returned value is incorrect", validator.isNewBidValid(auction, bid));
    }

    /**
     * <p>
     * Tests that isNewBidValid(Auction, Bid) returns correct value. True is expected.
     * </p>
     */
    public void testIsNewBidValid5() {
        MockAuction auction = new MockAuction();
        auction.setStartDate(twoDaysBefore.getTime());
        auction.setEndDate(twoDaysAfter.getTime());
        auction.setMinimumBid(4);
        auction.setBids(new Bid[0]);

        CustomBid bid = new CustomBid(1, 2, 4, new Date());

        assertTrue("The returned value is incorrect", validator.isNewBidValid(auction, bid));
    }
}
