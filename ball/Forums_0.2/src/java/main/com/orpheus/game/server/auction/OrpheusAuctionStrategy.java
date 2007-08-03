/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.auction;

import com.orpheus.auction.persistence.CustomBid;
import com.topcoder.util.auction.Auction;
import com.topcoder.util.auction.AuctionStrategy;
import com.topcoder.util.auction.Bid;
import com.topcoder.util.auction.impl.AuctionImpl;
import com.topcoder.util.auction.impl.BaseAuctionStrategy;
import com.topcoder.util.auction.impl.MinimumIncrementCalculator;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

/**
 * <p>A custom implementation of {@link AuctionStrategy} to be used in context of <code>Orpheus Game Server Auction
 * </code> module. It duplciates the logic from <code>AutomatedProxyBiddingAuctionStrategy</code> provided by <code>
 * Auction Framework</code> component. The only difference is that this implementation uses {@link CustomBid} class
 * when instantiating bids as requried by current project.</p>
 *
 * @author isv
 * @version 1.0
 */
public class OrpheusAuctionStrategy extends BaseAuctionStrategy implements AuctionStrategy {

    /**
     * Compares two bids first by effective amount and then by timestamp if the two effective
     * amounts are the same. A bid with a higher effective amount comes before a bid with a lower
     * effective amount, and a bid with an earlier timestamp comes before a bid with a later
     * timestamp.
     */
    private static final Comparator BID_COMPARATOR = new BidAmountComparator();

    /**
     * Creates a new <code>OrpheusAuctionStrategy</code> that uses the given increment calculator.
     *
     * @param incrementCalculator the increment calculator used to determine the minimum increment in an auction.
     * @throws IllegalArgumentException if incrementCalculator is null
     */
    public OrpheusAuctionStrategy(MinimumIncrementCalculator incrementCalculator) {
        super(incrementCalculator);
    }

    /**
     * Calculates and returns an updated list of bids after a new bid has been placed in the given
     * auction.
     * <p>
     * This algorithm only looks at leading bids, since losing bids will have been outbidden. It
     * sets the effective amount of the new bid to the minimum necessary for it to be a winning bid
     * and then inserts it into the list of leading bids. It then attempts to raise the effective
     * amount of the new losing bid and place it in the auction. This continues until the effective
     * amount of the current losing bid can not be raised enough.
     * </p>
     *
     * @param auction the auction in which the bid is being placed
     * @param newBid the bid to place in the auction
     * @return the updated list of bids in the auction after the new bid has been placed
     * @throws IllegalArgumentException if any argument is null
     */
    protected Bid[] getUpdatedBids(Auction auction, Bid newBid) {
        if (auction == null) {
            throw new IllegalArgumentException("The parameter [auction] is NULL");
        }
        if (newBid == null) {
            throw new IllegalArgumentException("The parameter [newBid] is NULL");
        }

        int itemCount = auction.getItemCount();
        Bid[] bids = auction.getBids();

        if (bids.length < itemCount) {

            // items are available, so bid the minimum amount
            return appendBid(bids, createBid(newBid, auction.getMinimumBid()));
        } else {
            Bid[] leadingBids = selectLeadingBids(auction);

	    // leadingBids.length is expected to be equal to itemCount

            for (;;) {
		Bid last = leadingBids[itemCount - 1];
                int minAmount = last.getEffectiveAmount().intValue() + getMinimumIncrement(auction);

                if (minAmount > newBid.getMaxAmount()) {
                    // outbid
                    break;
                }

                // insert the newBid and write over the last bid
                insertBid(leadingBids, createBid(newBid, minAmount));

                // create a new auction for the increment calculator
                auction = replaceLeadingBids(auction, leadingBids);
                newBid = last;
	    }

            // add the outbid bid back with a null effective amount
            return appendBid(auction.getBids(), createBid(newBid, null));
	}
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
        if (auction == null) {
            throw new IllegalArgumentException("The parameter [auction] is NULL");
        }
        if (originalBid == null) {
            throw new IllegalArgumentException("The parameter [originalBid] is NULL");
        }
        if (newBid == null) {
            throw new IllegalArgumentException("The parameter [newBid] is NULL");
        }

        Bid[] oldBids = auction.getBids();

        // find the original bid
        for (int i = 0; i < oldBids.length; i++) {
            if (isSameBid(oldBids[i], originalBid)) {
                if (oldBids[i].getEffectiveAmount() == null) {

                    // was not a leading bid => remove the original bid and treat the update as a new bid

                    Bid[] bids = new Bid[oldBids.length - 1];

                    System.arraycopy(oldBids, 0, bids, 0, i);
                    System.arraycopy(oldBids, i + 1, bids, i, bids.length - i);

                    // and add the new bid
                    return getUpdatedBids(createAuction(auction, bids), newBid);
                } else {

                    // replace the old bid with the new bid, but keep the effective amount

                    oldBids[i] = createBid(newBid, oldBids[i].getEffectiveAmount());

                    return oldBids;
                }
            }
        }

	// original bid not found
        throw new IllegalArgumentException("Sanity Check: originalBid not found");
    }

    /**
     * Inserts a new bid into a sorted array of bids and shifts all bids after it down one. The last
     * bid in the array is removed.
     *
     * @param bids the sorted array of bids
     * @param newBid the new bid to insert
     */
    private void insertBid(Bid[] bids, Bid newBid) {
        int insertionPoint = searchBids(bids, newBid);  // result as described for Arrays.binarySearch()

        if (insertionPoint < 0) {
            // bid not found calculate insertionPoint (see Arrays documentation)
	    // this is the usual case
            insertionPoint = -(1 + insertionPoint);
        }

        // only need to do anything if the new bid is a leading bid
        if (insertionPoint < bids.length) {

            // shift all the bids down one, removing losing bid
            for (int i = bids.length - 1; i > insertionPoint; i--) {
		bids[i] = bids[i - 1];
	    }

            // insert the new bid
            bids[insertionPoint] = newBid;
        }
    }

    /**
     * Replaces the leading bids in an auction with the given bids and returns the updated auction.
     *
     * @param auction the original auction
     * @param leadingBids the new leading bids
     * @return the updated auction
     */
    private Auction replaceLeadingBids(Auction auction, Bid[] leadingBids) {
        Bid[] bids = auction.getBids();

        System.arraycopy(leadingBids, 0, bids, 0, leadingBids.length);

        return createAuction(auction, bids);
    }

    /**
     * Creates a new <code>Auction</code> with the specified bids and the auction id, summary,
     * description, item count, minimum bid, and starting and ending dates as the given auction.
     *
     * @param auction the original auction
     * @param bids the new array of bids
     * @return a new auction with given bids but otherwise equal to the original auction
     */
    private Auction createAuction(Auction auction, Bid[] bids) {
        return new AuctionImpl(auction.getId(), auction.getSummary(), auction.getDescription(), auction.getItemCount(),
                               auction.getMinimumBid(), auction.getStartDate(), auction.getEndDate(), bids);
    }

    /**
     * Creates a new <code>Bid</code> with the specified effective amount and the same bidder id,
     * maximum amount, and timestamp as the given bid.
     *
     * @param bid the original bid
     * @param effectiveAmount the effective amount of the new bid
     * @return a new bid with given effective amount but otherwise equal to the original bid
     */
    private static CustomBid createBid(Bid bid, Integer effectiveAmount) {
        CustomBid customBid = (CustomBid) bid;
        CustomBid newCustomBid
                = new CustomBid(bid.getBidderId(), customBid.getImageId(), bid.getMaxAmount(), bid.getTimestamp());

        if (customBid.getId() != null) {
            newCustomBid.setId(customBid.getId().longValue());
        }
        if (effectiveAmount != null) {
            newCustomBid.setEffectiveAmount(effectiveAmount.intValue());
        }

        return newCustomBid;
    }

    /**
     * Creates a new <code>Bid</code> with the specified effective amount and the same bidder id,
     * maximum amount, and timestamp as the given bid.
     *
     * @param bid the original bid
     * @param effectiveAmount the effective amount of the new bid
     * @return a new bid with given effective amount but otherwise equal to the original bid
     */
    private static CustomBid createBid(Bid bid, int effectiveAmount) {
        return createBid(bid, new Integer(effectiveAmount));
    }

    /**
     * Creates and returns a new <code>Bid</code> array by appending the given bid to the given
     * array <code>Bid</code>s.
     *
     * @param bids the original array of bids
     * @param bid the bid to append
     * @return an array containing bid and all the elements of bids
     */
    private static Bid[] appendBid(Bid[] bids, Bid bid) {
        Bid[] newBids = new Bid[bids.length + 1];

        System.arraycopy(bids, 0, newBids, 0, bids.length);
        newBids[bids.length] = bid;

        return newBids;
    }

    /**
     * Searches a sorted array of bids by effective amount and timestamp to determine where to
     * insert the given bid.
     *
     * @param bids the sorted array of bids
     * @param bid the bid to search for
     * @return either the insertionPoint or (-(insertion point) - 1)
     * @see Arrays#binarySearch(Object[], Object, Comparator)
     */
    public static int searchBids(Bid[] bids, Bid bid) {
        return Arrays.binarySearch(bids, bid, BID_COMPARATOR);
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

    /**
     * Tests if two bids are equivalent; that is, they have the same bidder id, maximum amount, and
     * timestamp.
     *
     * @param bid1 the first bid to compare
     * @param bid2 the second bid to compare
     * @return true if the two bids are have the same bidder id, maximum amount, and timestamp
     */
    private boolean isSameBid(Bid bid1, Bid bid2) {
        return bid1.getBidderId() == bid2.getBidderId() && bid1.getMaxAmount() == bid2.getMaxAmount()
               && isSameTime(bid1.getTimestamp(), bid2.getTimestamp());
    }

    /**
     * Tests if two dates are equivalent; that is, they are equal ignoring the milliseconds place.
     * This is used because many database systems do not store milliseconds.
     *
     * @param date1 the first date to compare
     * @param date2 the second date to compare
     * @return true if the two dates are the same ignoring the milliseconds place
     */
    private boolean isSameTime(Date date1, Date date2) {
        return date1.getTime() / 1000 == date2.getTime() / 1000;
    }
}
