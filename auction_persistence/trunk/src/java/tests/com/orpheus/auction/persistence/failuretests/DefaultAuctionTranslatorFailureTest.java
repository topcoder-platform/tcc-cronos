/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.auction.persistence.failuretests;

import java.util.Date;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.orpheus.auction.persistence.impl.DefaultAuctionTranslator;
import com.topcoder.util.auction.Auction;
import com.topcoder.util.auction.Bid;

/**
 * <p>
 * The class <code>DefaultAuctionTranslatorFailureTest</code> contains tests for the class
 * {@link <code>DefaultAuctionTranslator</code>}
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
public class DefaultAuctionTranslatorFailureTest extends TestCase {

    /**
     * The object that is being tested.
     * @see com.orpheus.auction.persistence.impl.DefaultAuctionTranslator
     */
    private DefaultAuctionTranslator autionTranslator = new DefaultAuctionTranslator();

    /**
     * Run the Auction assembleAuction(AuctionDTO) method test.
     */
    public void testAssembleAuctionFailure() {
        try {
            autionTranslator.assembleAuction(null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Run the AuctionDTO assembleAuctionDTO(Auction) method test.
     */
    public void testAssembleAuctionDTOFailure() {
        try {
            autionTranslator.assembleAuctionDTO(null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Run the AuctionDTO assembleAuctionDTO(Auction) method test.
     */
    public void testAssembleAuctionDTOFailure2() {
        Auction auction = new MockAuction(new Long(1), "", "", 1, 1, null, null, new Bid[] {null});
        try {
            autionTranslator.assembleAuctionDTO(auction);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Run the Bid assembleBid(BidDTO) method test
     */
    public void testAssembleBidFailure() {
        try {
            autionTranslator.assembleBid(null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Run the BidDTO assembleBidDTO(Bid) method test
     */
    public void testAssembleBidDTOFailure() {
        try {
            autionTranslator.assembleBidDTO(null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Run the BidDTO assembleBidDTO(Bid) method test
     */
    public void testAssembleBidDTOFailure2() {
        try {
            autionTranslator.assembleBidDTO(new Bid() {

                public long getBidderId() {
                    return 0;
                }

                public Integer getEffectiveAmount() {
                    return null;
                }

                public int getMaxAmount() {
                    return 0;
                }

                public Date getTimestamp() {
                    return null;
                }

            });
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Aggregates all tests in this class.
     * </p>
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(DefaultAuctionTranslatorFailureTest.class);
    }
}
