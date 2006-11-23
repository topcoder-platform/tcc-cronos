/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.auction.persistence.impl;

import com.orpheus.auction.persistence.CustomBid;
import com.orpheus.auction.persistence.UnitTestHelper;
import com.orpheus.auction.persistence.ejb.AuctionDTO;
import com.orpheus.auction.persistence.ejb.BidDTO;

import com.topcoder.util.auction.Auction;
import com.topcoder.util.auction.Bid;
import com.topcoder.util.auction.impl.AuctionImpl;

import junit.framework.TestCase;

import java.util.Date;


/**
 * <p>
 * Tests functionality and error cases of <code>DefaultAuctionTranslator</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DefaultAuctionTranslatorUnitTest extends TestCase {
    /** Represents the bid id for testing. */
    private static final Long BID_ID = new Long(1);

    /** Represents the image id for testing. */
    private static final long IMAGE_ID = 2;

    /** Represents the bidder id for testing. */
    private static final long BIDDER_ID = 3;

    /** Represents the effective amount for testing. */
    private static final Integer EFFECTIVE_AMOUNT = new Integer(4);

    /** Represents the maximum amount for testing. */
    private static final int MAXIMUN_AMOUNT = 5;

    /** Represents the date and time for testing. */
    private static final Date TIME_STAMP = new Date();

    /** Represents the auction id for testing. */
    private static final Long AUCTION_ID = new Long(6);

    /** Represents the summary for testing. */
    private static final String SUMMARY = "summary";

    /** Represents the description for testing. */
    private static final String DESCRIPTION = "description";

    /** Represents the item count for testing. */
    private static final int ITEM_COUNT = 7;

    /** Represents the minimum BID for testing. */
    private static final int MINIMUN_DID = 8;

    /** Represents the start date for testing. */
    private static final Date START_DATE = new Date();

    /** Represents the end date for testing. */
    private static final Date END_DATE = new Date(START_DATE.getTime() + 100);

    /** Represents the bidDTOs for testing. */
    private static final BidDTO[] BID_DTOS = new BidDTO[1];

    /** Represents the bids for testing. */
    private static final Bid[] BIDS = new Bid[1];

    /** Represents the <code>CustomBid</code> instance used for testing. */
    private static final CustomBid BID = new CustomBid(BIDDER_ID, IMAGE_ID, MAXIMUN_AMOUNT, TIME_STAMP);

    /** Represents the <code>BidDTO</code> instance used for testing. */
    private static final BidDTO BID_DTO = new BidDTO();

    /** Represents the <code>AuctionDTO</code> instance used for testing. */
    private static final AuctionDTO AUCTION_DTO = new AuctionDTO();

    /** Represents the <code>DefaultAuctionTranslator</code> instance used for testing. */
    private static final DefaultAuctionTranslator TRANSLATOR = new DefaultAuctionTranslator();

    /** Represents the <code>Auction</code> instance used for testing. */
    private Auction auction = null;

    /**
     * <p>
     * Sets up the test environment. The test instance is created.
     * </p>
     */
    protected void setUp() {
        BID.setId(BID_ID.longValue());
        BID.setEffectiveAmount(EFFECTIVE_AMOUNT.intValue());
        BIDS[0] = BID;
        auction = new AuctionImpl(AUCTION_ID, SUMMARY, DESCRIPTION, ITEM_COUNT,
                MINIMUN_DID, START_DATE, END_DATE, BIDS);

        BID_DTO.setBidderId(BIDDER_ID);
        BID_DTO.setEffectiveAmount(EFFECTIVE_AMOUNT);
        BID_DTO.setId(BID_ID);
        BID_DTO.setImageId(IMAGE_ID);
        BID_DTO.setMaxAmount(MAXIMUN_AMOUNT);
        BID_DTO.setTimestamp(TIME_STAMP);
        BID_DTOS[0] = BID_DTO;

        AUCTION_DTO.setBids(BID_DTOS);
        AUCTION_DTO.setDescription(DESCRIPTION);
        AUCTION_DTO.setEndDate(END_DATE);
        AUCTION_DTO.setId(AUCTION_ID);
        AUCTION_DTO.setItemCount(ITEM_COUNT);
        AUCTION_DTO.setMinimumBid(MINIMUN_DID);
        AUCTION_DTO.setStartDate(START_DATE);
        AUCTION_DTO.setSummary(SUMMARY);
    }

    /**
     * <p>
     * Tests the accuracy of constructor <code>DefaultAuctionTranslator</code>. No exception is expected.
     * </p>
     */
    public void testDefaultAuctionTranslator_Accuracy() {
        new DefaultAuctionTranslator();
    }

    /**
     * <p>
     * Tests the method <code>assembleAuction(AuctionDTO)</code> when the auctionDTO is null, IllegalArgumentException
     * is expected.
     * </p>
     */
    public void testAssembleAuction_NullBid() {
        try {
            TRANSLATOR.assembleAuction(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the accuracy of method <code>assembleAuction(AuctionDTO)</code>.
     * </p>
     */
    public void testAssembleAuction_Accuracy() {
        UnitTestHelper.assertEquals(auction, TRANSLATOR.assembleAuction(AUCTION_DTO), false);
    }

    /**
     * <p>
     * Tests the method <code>assembleAuctionDTO(Auction)</code> when the auction is null, IllegalArgumentException is
     * expected.
     * </p>
     */
    public void testAssembleAuctionDTO_NullBid() {
        try {
            TRANSLATOR.assembleAuctionDTO(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method <code>assembleAuctionDTO(Auction)</code>  when the bids of auction contain element which is not
     * instance of CustomBid, IllegalArgumentException is expected.
     * </p>
     */
    public void testAssembleAuctionDTO_ContainNoCustomBidBid() {
        try {
            BIDS[0] = new MockBid();
            auction = new AuctionImpl(AUCTION_ID, SUMMARY, DESCRIPTION, ITEM_COUNT, MINIMUN_DID, START_DATE, END_DATE,
                    BIDS);
            TRANSLATOR.assembleAuctionDTO(auction);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the accuracy of method <code>assembleAuctionDTO(Auction)</code>.
     * </p>
     */
    public void testAssembleAuctionDTO_Accuracy() {
        UnitTestHelper.assertEquals(AUCTION_DTO, TRANSLATOR.assembleAuctionDTO(auction), false);
    }

    /**
     * <p>
     * Tests the method <code>assembleBid(bidDTO)</code> when the given bidDTO is null, IllegalArgumentException is
     * expected.
     * </p>
     */
    public void testAssembleBid_NullBidDTO() {
        try {
            TRANSLATOR.assembleBid(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the accuracy of method <code>assembleBid(BidDTO)</code>.
     * </p>
     */
    public void testAssembleBid_Accuracy() {
        Bid bid = TRANSLATOR.assembleBid(BID_DTO);
        assertTrue("bid should be a instance of CustomBid", bid instanceof CustomBid);

        UnitTestHelper.assertEquals(BID, (CustomBid) bid, false);
    }

    /**
     * <p>
     * Tests the method <code>assembleBidDTO(Bid)</code> when the given Bid is null, IllegalArgumentException is
     * expected.
     * </p>
     */
    public void testAssembleBidDTO_NullBid() {
        try {
            TRANSLATOR.assembleBidDTO(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the accuracy of method <code>assembleBidDTO(Bid)</code>.
     * </p>
     */
    public void testAssembleBidDTO_Accuracy() {
        BidDTO bidDTO = TRANSLATOR.assembleBidDTO(BID);

        UnitTestHelper.assertEquals(BID_DTO, bidDTO, false);
    }
}
