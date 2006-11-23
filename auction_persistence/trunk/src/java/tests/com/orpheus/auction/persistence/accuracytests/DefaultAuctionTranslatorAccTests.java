/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.auction.persistence.accuracytests;

import com.orpheus.auction.persistence.CustomBid;
import com.orpheus.auction.persistence.ejb.AuctionDTO;
import com.orpheus.auction.persistence.ejb.BidDTO;
import com.orpheus.auction.persistence.impl.DefaultAuctionTranslator;

import com.topcoder.util.auction.Auction;
import com.topcoder.util.auction.Bid;

import junit.framework.TestCase;

import java.util.Date;


/**
 * <p>
 * Accuracy test case for class <code>DefaultAuctionTranslator</code>.
 * </p>
 */
public class DefaultAuctionTranslatorAccTests extends TestCase {
    /** DefaultAuctionTranslator instance to test against. */
    private DefaultAuctionTranslator translator = null;

    /** AuctionDTO instance for testing. */
    private AuctionDTO auctionDTO = null;

    /** Auction instance for testing. */
    private Auction auction = null;

    /** timestamp for bidDTO. */
    private Date timestamp = null;

    /** start date. */
    private Date startDate = null;

    /** end date. */
    private Date endDate = null;

    /** BidDTO array. */
    private BidDTO[] bidDTOs = null;

    /**
     * create instance for testing.
     */
    protected void setUp() throws Exception {
        this.translator = new DefaultAuctionTranslator();
        //get instances
        bidDTOs = new BidDTO[1];
        timestamp = new Date();
        bidDTOs[0] = TestHelper.getBidDTO(1, 1, 1, new Integer(1), 1, timestamp);
        startDate = new Date();
        endDate = new Date();
        auctionDTO = TestHelper.getAuctionDTO(1, "desc", "summary", 1, 1, startDate, endDate, bidDTOs);
    }

    /**
     * Test the assembleBid method.
     */
    public void testAssembleBid() {
        Bid bid = translator.assembleBid(this.bidDTOs[0]);
        assertEquals("The bidderId is not the same", 1, bid.getBidderId());
        assertEquals("The maxAmount is not the same", 1, bid.getMaxAmount());
        assertEquals("The effectiveAmount is not the same", 1, bid.getEffectiveAmount().intValue());
        assertEquals("The time stamp is not the same.", timestamp, bid.getTimestamp());
        assertEquals("The id is not the same.", 1, ((CustomBid)bid).getId().longValue());
    }

    /**
     * test the assembleBidDTO method.
     */
    public void testAssembleBidDTO() {
        Bid bid = translator.assembleBid(this.bidDTOs[0]);
        BidDTO dto = this.translator.assembleBidDTO(bid);

        assertEquals("The bidderId is not the same", 1, dto.getBidderId());
        assertEquals("The maxAmount is not the same", 1, dto.getMaxAmount());
        assertEquals("The effectiveAmount is not the same", 1, dto.getEffectiveAmount().intValue());
        assertEquals("The time stamp is not the same.", timestamp, dto.getTimestamp());
    }

    /**
     * Test the assembleAuction method.
     */
    public void testAssembleAuction() {
        //translate
        auction = translator.assembleAuction(auctionDTO);
        //verify the auction
        assertEquals("The id is not the same", 1, auction.getId().longValue());
        assertEquals("The desc is not the same", "desc", auction.getDescription());
        assertEquals("The summary is not the same", "summary", auction.getSummary());
        assertEquals("The itemCount is not the same", 1, auction.getItemCount());
        assertEquals("The minimunBid is not the same", 1, auction.getMinimumBid());
        assertEquals("The startDate is not the same", startDate, auction.getStartDate());
        assertEquals("The endDate is not null.", endDate, auction.getEndDate());

        //verify the BidDTO array
        Bid bid = auction.getBids()[0];
        assertEquals("The bidderId is not the same", 1, bid.getBidderId());
        assertEquals("The maxAmount is not the same", 1, bid.getMaxAmount());
        assertEquals("The effectiveAmount is not the same", 1, bid.getEffectiveAmount().intValue());
        assertEquals("The time stamp is not the same.", timestamp, bid.getTimestamp());
    }

    /**
     * test the assembleAuctionDTO method.
     */
    public void testAssembleAuctionDTO() {
        //translate
        auction = translator.assembleAuction(auctionDTO);

        //translat from Auction to AuctionDTO
        AuctionDTO translated = translator.assembleAuctionDTO(auction);

        //verify
        assertEquals("The id is not the same", 1, translated.getId().longValue());
        assertEquals("The desc is not the same", "desc", translated.getDescription());
        assertEquals("The summary is not the same", "summary", translated.getSummary());
        assertEquals("The itemCount is not the same", 1, translated.getItemCount());
        assertEquals("The minimunBid is not the same", 1, translated.getMinimumBid());
        assertEquals("The startDate is not the same", startDate, translated.getStartDate());
        assertEquals("The endDate is not null.", endDate, translated.getEndDate());

        BidDTO bid = translated.getBids()[0];
        assertEquals("The bidderId is not the same", 1, bid.getBidderId());
        assertEquals("The maxAmount is not the same", 1, bid.getMaxAmount());
        assertEquals("The effectiveAmount is not the same", 1, bid.getEffectiveAmount().intValue());
        assertEquals("The time stamp is not the same.", timestamp, bid.getTimestamp());
    }
}
