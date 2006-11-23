/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.auction.persistence.impl;

import com.orpheus.auction.persistence.AuctionPersistenceHelper;
import com.orpheus.auction.persistence.AuctionTranslator;
import com.orpheus.auction.persistence.CustomBid;
import com.orpheus.auction.persistence.ejb.AuctionDTO;
import com.orpheus.auction.persistence.ejb.BidDTO;

import com.topcoder.util.auction.Auction;
import com.topcoder.util.auction.Bid;
import com.topcoder.util.auction.impl.AuctionImpl;


/**
 * <p>
 * The default implementation of the <code>AuctionTranslator</code> instance. It translates between the
 * <code>Auction</code> and the <code>AuctionDTO</code> as well as between <code>CustomBid</code> and
 * <code>BidDTO</code>. It is plugged into the <code>CustomAuctionPersistence</code> class to perform these
 * translations. The translation is a simple 1-1 mapping between these entities.
 * </p>
 *
 * <p>
 * <b>Thread Safety: </b>This class is immutable and thread-safe.
 * </p>
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public class DefaultAuctionTranslator implements AuctionTranslator {
    /**
     * <p>
     * Empty Constructor.
     * </p>
     */
    public DefaultAuctionTranslator() {
    }

    /**
     * <p>
     * Assembles the <code>Auction</code> from the <code>AuctionDTO</code>. The DTO is used as the custom transfer
     * object inside this component to ensure serializability.
     * </p>
     *
     * <p>
     * The <code>Auction</code> implementation is <code>AuctionImpl</code>, and the <code>Bid</code> is
     * <code>CustomBid</code>. The mapping is 1-1 for both, so it is easy to accomplish.
     * </p>
     *
     * @param auctionDTO the <code>AuctionDTO</code> instance to assemble.
     *
     * @return auction the assembled <code>AuctionImpl</code> instance.
     *
     * @throws IllegalArgumentException If the parameter is null.
     */
    public Auction assembleAuction(AuctionDTO auctionDTO) {
        AuctionPersistenceHelper.validateNotNull(auctionDTO, "auctionDTO");

        BidDTO[] bidDTOs = auctionDTO.getBids();
        Bid[] bids = new Bid[bidDTOs.length];

        for (int i = 0; i < bidDTOs.length; i++) {
            bids[i] = assembleBid(bidDTOs[i]);
        }

        return new AuctionImpl(auctionDTO.getId(), auctionDTO.getSummary(), auctionDTO.getDescription(),
            auctionDTO.getItemCount(), auctionDTO.getMinimumBid(), auctionDTO.getStartDate(), auctionDTO.getEndDate(),
            bids);
    }

    /**
     * <p>
     * Assembles the <code>AuctionDTO</code> from the <code>Auction</code>. The DTO is used as the custom transfer
     * object inside this component to ensure serializability.
     * </p>
     *
     * <p>
     * The <code>Auction</code> implementation is <code>AuctionImpl</code>, and the <code>Bid</code> is
     * <code>CustomBid</code>. The mapping is 1-1 for both, so it is easy to accomplish.
     * </p>
     *
     * @param auction the <code>Auction</code> instance to assemble.
     *
     * @return auctionDTO the assembled <code>AuctionDTO</code> instance.
     *
     * @throws IllegalArgumentException If the parameter is null or the Bids are not CustomBids.
     */
    public AuctionDTO assembleAuctionDTO(Auction auction) {
        AuctionPersistenceHelper.validateNotNull(auction, "auction");

        Bid[] bids = auction.getBids();
        BidDTO[] bidDTOs = new BidDTO[bids.length];

        for (int i = 0; i < bids.length; i++) {
            bidDTOs[i] = assembleBidDTO(bids[i]);
        }

        AuctionDTO auctionDTO = new AuctionDTO();
        auctionDTO.setId(auction.getId());
        auctionDTO.setSummary(auction.getSummary());
        auctionDTO.setDescription(auction.getDescription());
        auctionDTO.setItemCount(auction.getItemCount());
        auctionDTO.setMinimumBid(auction.getMinimumBid());
        auctionDTO.setStartDate(auction.getStartDate());
        auctionDTO.setEndDate(auction.getEndDate());
        auctionDTO.setBids(bidDTOs);

        return auctionDTO;
    }

    /**
     * <p>
     * Assembles the <code>Bid</code> from the <code>BidDTO</code>. The DTO is used as the custom transfer object
     * inside this component to ensure serializability.
     * </p>
     *
     * <p>
     * The <code>Bid</code> is <code>CustomBid</code>. The mapping is 1-1, so it is easy to accomplish.
     * </p>
     *
     * @param bidDTO the <code>BidDTO</code> instance to assemble.
     *
     * @return bid the assembled <code>Bid</code> instance.
     *
     * @throws IllegalArgumentException If the parameter is null.
     */
    public Bid assembleBid(BidDTO bidDTO) {
        AuctionPersistenceHelper.validateNotNull(bidDTO, "bidDTO");

        CustomBid bid = new CustomBid(bidDTO.getBidderId(), bidDTO.getImageId(), bidDTO.getMaxAmount(),
                bidDTO.getTimestamp());

        if (bidDTO.getEffectiveAmount() != null) {
            bid.setEffectiveAmount(bidDTO.getEffectiveAmount().intValue());
        }

        if (bidDTO.getId() != null) {
            bid.setId(bidDTO.getId().longValue());
        }

        return bid;
    }

    /**
     * <p>
     * Assembles the <code>BidDTO</code> from the <code>Bid</code>. The DTO is used as the custom transfer object
     * inside this component to ensure serializability.
     * </p>
     *
     * <p>
     * The <code>Bid</code> is <code>CustomBid</code>. The mapping is 1-1, so it is easy to accomplish.
     * </p>
     *
     * @param bid the <code>Bid</code> instance to assemble.
     *
     * @return bidDTO the assembled <code>BidDTO</code> instance.
     *
     * @throws IllegalArgumentException If the parameter is null or not a <code>CustomBid</code>.
     */
    public BidDTO assembleBidDTO(Bid bid) {
        AuctionPersistenceHelper.validateNotNull(bid, "bid");

        if (!(bid instanceof CustomBid)) {
            throw new IllegalArgumentException("Bid must be an instanceof of CustomBid.");
        }

        CustomBid custonBid = (CustomBid) bid;
        BidDTO bidDTO = new BidDTO();
        bidDTO.setBidderId(bid.getBidderId());
        bidDTO.setEffectiveAmount(bid.getEffectiveAmount());
        bidDTO.setMaxAmount(bid.getMaxAmount());
        bidDTO.setTimestamp(bid.getTimestamp());
        bidDTO.setId(custonBid.getId());
        bidDTO.setImageId(custonBid.getImageId());

        return bidDTO;
    }
}
