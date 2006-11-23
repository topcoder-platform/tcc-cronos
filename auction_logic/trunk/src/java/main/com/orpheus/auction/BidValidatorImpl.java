/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.auction;

import com.orpheus.auction.persistence.CustomBid;
import com.topcoder.util.auction.Auction;
import com.topcoder.util.auction.Bid;
import com.topcoder.util.auction.BidValidator;

/**
 * <p>
 * This class is used by AuctionManager to validate the bid before creating a new bid or updating an
 * existing bid.
 * </p>
 * <p>
 * Thread safety: This class is thread-safe since it's immutable.
 * </p>
 * @author woodjhon, TCSDEVELOPER
 * @version 1.0
 */
public class BidValidatorImpl implements BidValidator {

    /**
     * <p>
     * Creates a new BidValidatorImpl instance. Does nothing.
     * </p>
     */
    public BidValidatorImpl() {
    }

    /**
     * <p>
     * Checks whether the bid placed in the given auction is valid.
     * </p>
     * <p>
     * New bid is invalid if:
     * <ul>
     * <li>the specified auction has not yet started or has already completed</li>
     * <li>it's maximum amount is not at least as great as the auction’s minimum bid</li>
     * </ul>
     * </p>
     * @param auction the auction in which the bid is being placed.
     * @param bid the bid being placed.
     * @return true if bid is valid and false otherwise.
     * @throws IllegalArgumentException if one of the arguments is null.
     */
    public boolean isNewBidValid(Auction auction, Bid bid) {
        Helper.checkNotNull(auction, "auction");
        Helper.checkNotNull(bid, "bid");

        // if auction is not open for bids then return false
        if (!isAuctionIsOpen(auction)) {
            return false;
        }

        // bid maximum amount should be greater or equal than the auction minimum amount
        if (bid.getMaxAmount() < auction.getMinimumBid()) {
            return false;
        }

        // if all checks are passed then return true
        return true;
    }

    /**
     * <p>
     * Checks whether the update of a bid placed in the given auction is valid.
     * </p>
     * <p>
     * Bid update is invalid if:
     * <ul>
     * <li>the specified auction has not yet started or has already completed</li>
     * <li>it's maximum amount is not greater than maximum amount of the original bid</li>
     * <li>it's id differs from the original bid id</li>
     * <li>it's bidder id differs from the original bid bidder id</li>
     * <li>it's image id differs from the original bid image id</li>
     * <li>the specified original bid is not among the bids in the specified auction</li>
     * </ul>
     * </p>
     * @param auction the auction in which the bid is being placed.
     * @param originalBid the bid being placed.
     * @param newBid the bid to which the original is being updated.
     * @return true if bid update is valid and false otherwise.
     * @throws IllegalArgumentException if one of the arguments is null.
     */
    public boolean isBidUpdateValid(Auction auction, Bid originalBid, Bid newBid) {
        Helper.checkNotNull(auction, "auction");
        Helper.checkNotNull(originalBid, "originalBid");
        Helper.checkNotNull(newBid, "newBid");

        // if auction is not open for bids then return false
        if (!isAuctionIsOpen(auction)) {
            return false;
        }

        // maximum amount of new bid must exceed maximum amount of the original one
        if (newBid.getMaxAmount() <= originalBid.getMaxAmount()) {
            return false;
        }

        // both bids should be instances of CustomBid
        if (!(originalBid instanceof CustomBid)) {
            return false;
        }
        if (!(newBid instanceof CustomBid)) {
            return false;
        }
        CustomBid originalCustomBid = (CustomBid) originalBid;
        CustomBid newCustomBid = (CustomBid) newBid;

        // id of the new bid cannot be null
        if (newCustomBid.getId() == null) {
            return false;
        }

        // check whether the ids are equal, return false if not
        if (!originalCustomBid.getId().equals(newCustomBid.getId())) {
            return false;
        }

        // check whether the bidder ids are equal, return false if not
        if (originalCustomBid.getBidderId() != newCustomBid.getBidderId()) {
            return false;
        }

        // check whether the image ids are equal, return false if not
        if (originalCustomBid.getImageId() != newCustomBid.getImageId()) {
            return false;
        }

        // check that specified original bid is among the auction's bids
        boolean ok = false;
        Bid[] bids = auction.getBids();
        for (int i = 0; i < bids.length; i++) {
            // auction bids must be CustomBid instances
            if (!(bids[i] instanceof CustomBid)) {
                return false;
            }
            // if bid in the array has the same id then we've found it
            if (newCustomBid.getId().equals(((CustomBid) bids[i]).getId())) {
                ok = true;
                break;
            }
        }

        // if bid is not found then return false
        if (!ok) {
            return false;
        }

        // if all checks are passed then return true
        return true;
    }

    /**
     * <p>
     * Checks whether the given auction is open for bids.
     * </p>
     * @param auction the auction to check.
     * @return true if auction is open and false otherwise.
     */
    private boolean isAuctionIsOpen(Auction auction) {
        long curTime = System.currentTimeMillis();
        return (auction.getStartDate().getTime() <= curTime && curTime <= auction.getEndDate()
                .getTime());
    }
}
