/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved.
 */
package com.orpheus.auction.accuracytests;

import java.util.Arrays;
import java.util.Comparator;

import com.orpheus.auction.persistence.CustomBid;
import com.topcoder.util.auction.Auction;
import com.topcoder.util.auction.Bid;
import com.topcoder.util.auction.IllegalBidException;
import com.topcoder.util.auction.impl.AuctionImpl;
import com.topcoder.util.auction.impl.BaseAuctionStrategy;
import com.topcoder.util.auction.impl.MinimumIncrementCalculator;

/**
 * The custom AuctionStrategy that need to be used instead the standard one. The reason is that the
 * Bid objects need to be of CustomBid type. Most of the code came from Auction Framework class and only
 * few methods were adjusted to work with new requirements.
 * 
 * @author humblefool, colesbury, kr00tki
 * @version 1.0
 */
public class DummyAuctionStrategy extends BaseAuctionStrategy {

    /**
     * Constructor.
     * 
     * @param incrementCalculator calculator.
     */
    public DummyAuctionStrategy(MinimumIncrementCalculator incrementCalculator) {
        super(incrementCalculator);
    }
   
    /**
     * Processes the placement of the given bid in the auction and returns a new
     * <code>Auction</code> object representing the current state of the auction.
     *
     * @param auction the auction in which the bid is placed
     * @param bid the bid to be placed in the auction
     * @return a new <code>Auction</code> object representing the current state of the auction
     * @throws IllegalBidException if the new bid cannot be legally placed in the auction
     * @throws IllegalArgumentException if either argument is null
     * @see BaseAuctionStrategy#getUpdatedBids(Auction, Bid)
     */
    public Auction processNewBid(Auction auction, Bid bid) {
        // delegate to subclass
        Bid[] updatedBids = getUpdatedBids(auction, bid);

        // create auction from bids
        return createAuction(auction, updatedBids);
    }

    /**
     * Processes the update of the given bid and returns a new <code>Auction</code> object
     * representing the current state of the auction. The new maximum amount must be greater than
     * the original bid's effective amount.
     *
     * @param auction the auction in which the bid is updated
     * @param originalBid the original bid
     * @param newBid the updated bid
     * @return a new <code>Auction</code> object representing the current state of the auction
     * @throws IllegalBidException if the original bid cannot be legally updated in the auction or
     *             if the new maximum amount is not greater than the old bid's effective amount
     * @throws IllegalArgumentException if any argument is null
     * @see BaseAuctionStrategy#getUpdatedBids(Auction, Bid, Bid)
     */
    public Auction processBidUpdate(Auction auction, Bid originalBid, Bid newBid) {
        Bid[] bids = auction.getBids();
        boolean valid = false;
        for (int i = 0; i < bids.length; i++) {
            Integer amount = bids[i].getEffectiveAmount();
            if (isSameBid(bids[i], originalBid)
                    && (amount == null || amount.intValue() < newBid.getMaxAmount())) {
                valid = true;
                break;
            }
        }

        if (!valid) {
            throw new IllegalArgumentException(
                    "The maximum amount of new bid must be greater than the effective amount of the original bid");
        }

        // delegate to subclass
        Bid[] updatedBids = getUpdatedBids(auction, originalBid, newBid);

        // create auction from bids
        return createAuction(auction, updatedBids);
    }   
    /**
     * Calculates and returns an updated list of bids after a new bid has been placed in the given
     * auction.
     * <p>
     * This implementation checks that the new bid meets the minimum bid increment. If it meets the
     * minimum bid increment, the bid's effective amount is set to its maximum amount and the bid is
     * added to the auction's list of bids. The effective amount of all losing bids is then set to
     * -1.
     * </p>
     *
     * @param auction the auction in which the bid is being placed
     * @param bid the bid to place in the auction
     * @return the updated list of bids in the auction after the new bid has been placed
     * @throws IllegalArgumentException if any argument is null
     */
    protected Bid[] getUpdatedBids(Auction auction, Bid bid) {
        Bid[] leading = selectLeadingBids(auction);
        if (leading.length > 0
                && bid.getMaxAmount() < leading[leading.length - 1].getMaxAmount()
                        + getMinimumIncrement(auction)) {
            // bid does not meet minimum increment
            bid = createBid(bid, -1);
        } else {
            // set the effective amount to the maximum amount
            bid = createBid(bid, bid.getMaxAmount());
        }

        // copy in the new bid
        Bid[] bids = appendBid(auction.getBids(), bid);
        // for all non-leading bids
        markLosingBids(bids, auction.getItemCount());

        return bids;
    }

    /**
     * Calculates and returns an updated list of bids after a bid in the auction has been updated.
     *
     * @param auction the auction in which the bid is being updated
     * @param originalBid the original bid
     * @param newBid the updated bid
     * @return the updated list of bids in the auction after the specified bid has been updated
     * @throws IllegalArgumentException if any of the arguments is null
     */
    protected Bid[] getUpdatedBids(Auction auction, Bid originalBid, Bid newBid) {
        // set the effective amount to the maximum amount
        newBid = createBid(newBid, newBid.getMaxAmount());

        Bid[] bids = auction.getBids();
        Bid[] removed = new CustomBid[bids.length - 1];

        // replace the original bid with the new bid
        for (int i = 0; i < bids.length; i++) {
            if (isSameBid(bids[i], originalBid)) {
                System.arraycopy(bids, 0, removed, 0, i);
                System.arraycopy(bids, i + 1, removed, i, removed.length - i);
                return getUpdatedBids(createAuction(auction, removed), newBid);
            }
        }

        throw new IllegalArgumentException("Sanity Check: originalBid not found");
    }

    /**
     * Sets the effective value of all losing (non-leading) bids to -1.
     *
     * @param bids an array of all the bids in an auction
     * @param itemCount the number of items in the auction
     */
    private void markLosingBids(Bid[] bids, int itemCount) {
        // sort bid's by effective amount
        Arrays.sort(bids, new BidAmountComparator());
        // for all losing bids
        for (int i = itemCount; i < bids.length; i++) {
            if (bids[i].getEffectiveAmount() != null) {
                // set the effective amount to -1
                bids[i] = createBid(bids[i], -1);
            }
        }
    }
    
    
    /**
     * Creates and returns a new <code>Bid</code> array by appending the given bid to the given
     * array <code>Bid</code>s.
     *
     * @param bids the original array of bids
     * @param bid the bid to append
     * @return an array containing bid and all the elements of bids
     */
    public static Bid[] appendBid(Bid[] bids, Bid bid) {
        CustomBid[] newBids = new CustomBid[bids.length + 1];
        System.arraycopy(bids, 0, newBids, 0, bids.length);
        newBids[bids.length] = (CustomBid) bid;
        return newBids;
    }

    /**
     * Creates a new <code>Bid</code> with the specified effective amount and the same bidder id,
     * maximum amount, and timestamp as the given bid.
     *
     * @param bid the original bid
     * @param effectiveAmount the effective amount of the new bid
     * @return a new bid with given effective amount but otherwise equal to the original bid
     */
    private static Bid createBid(Bid bid, int effectiveAmount) {
        return createBid((CustomBid) bid, new Integer(effectiveAmount));
    }

    /**
     * Creates a new <code>Bid</code> with the specified effective amount and the same bidder id,
     * maximum amount, and timestamp as the given bid.
     *
     * @param bid the original bid
     * @param effectiveAmount the effective amount of the new bid
     * @return a new bid with given effective amount but otherwise equal to the original bid
     */
    private static Bid createBid(CustomBid bid, Integer effectiveAmount) {
        CustomBid newBid = new CustomBid(bid.getBidderId(), ((CustomBid) bid).getImageId(), 
                bid.getMaxAmount(), bid.getTimestamp()); 
        newBid.setEffectiveAmount(effectiveAmount.intValue());
        if (bid.getId() != null) {
            newBid.setId(bid.getId().longValue());
        }
        return newBid;
    }

    /**
     * Creates a new <code>Auction</code> with the specified auction id and the same bids,
     * summary, description, item count, minimum bid, and starting and ending dates as the given
     * auction.
     *
     * @param auction the original auction
     * @param auctionId the new auction id
     * @return a new auction with given auction id but otherwise equal to the original auction
     */
    private static Auction createAuction(Auction auction, long auctionId) {
        return new AuctionImpl(new Long(auctionId), auction.getSummary(), auction.getDescription(), auction
            .getItemCount(), auction.getMinimumBid(), auction.getStartDate(), auction.getEndDate(), auction.getBids());
    }

    /**
     * Creates a new <code>Auction</code> with the specified bids and the auction id, summary,
     * description, item count, minimum bid, and starting and ending dates as the given auction.
     *
     * @param auction the original auction
     * @param bids the new array of bids
     * @return a new auction with given bids but otherwise equal to the original auction
     */
    private static Auction createAuction(Auction auction, Bid[] bids) {
        return new AuctionImpl(auction.getId(), auction.getSummary(), auction.getDescription(), auction.getItemCount(),
            auction.getMinimumBid(), auction.getStartDate(), auction.getEndDate(), bids);
    }
    
    /**
     * Tests if two bids are equivalent; that is, they have the same bidder id, maximum amount, and
     * timestamp.
     *
     * @param bid1 the first bid to compare
     * @param bid2 the second bid to compare
     * @return true if the two bids are have the same bidder id, maximum amount, and timestamp
     */
    private static boolean isSameBid(Bid bid1, Bid bid2) {
        return bid1.getBidderId() == bid2.getBidderId() && bid1.getMaxAmount() == bid2.getMaxAmount() 
            && (bid1.getTimestamp().getTime() == bid2.getTimestamp().getTime());
    }
    
    /**
     * Compares two bids first by effective amount and then by timestamp if the two effective
     * amounts are the same.
     */
    private static class BidAmountComparator implements Comparator {

        /**
         * See {@link Comparator#compare(Object, Object)}.
         *
         * @param o1 the first object to be compared.
         * @param o2 the second object to be compared.
         * @return a negative integer, zero, or a positive integer as the first argument is less
         *         than, equal to, or greater than the second.
         */
        public int compare(Object o1, Object o2) {
            Bid bid1 = (Bid) o1;
            Bid bid2 = (Bid) o2;
            int diff = getEffectiveAmount(bid2) - getEffectiveAmount(bid1);

            // same effective amount
            if (diff == 0) {
                // bid1 is ahead of bid2 if bid1 has a lower (earlier) timestamp
                return bid1.getTimestamp().compareTo(bid2.getTimestamp());
            }
            // bid1 is ahead of bid2 if bid1 has a higher effective amount
            return diff;
        }

        /**
         * Returns the effective amount of the bid or -1 if the bid does not have one.
         *
         * @param bid the passed bid
         * @return the effective amount of the bid or -1 if the bid does not have one.
         */
        private int getEffectiveAmount(Bid bid) {
            Integer value = bid.getEffectiveAmount();
            if (value == null) {
                return -1;
            }
            return value.intValue();
        }
    }
}
