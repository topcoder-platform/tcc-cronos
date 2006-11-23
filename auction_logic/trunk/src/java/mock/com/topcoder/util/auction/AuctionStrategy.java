/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.auction;

/**
 * <p>
 * This interface is referenced from the Auction Logic component but the Auction Framework component
 * where it is defined is still under development, so default implementation is used here. After the
 * Auction Framework will be released this interface can be removed. The documentation is left as it
 * was in the development distribution of the Auction Framework component.
 * </p>
 * <p>
 * Purpose: This interface defines the contract for processing a bid. The contract consists of four
 * methods - getting the minimum increment for an auction, getting the leading bids of an auction,
 * and processing new bids and bid updates. The two bid process methods may throw exceptions to
 * indicate that a bid is illegal.
 * </p>
 * <p>
 * Implementation: Implementations may further abstract out the minimum increment calculation and
 * concern themselves immediately with the processing. Processing could involve simply taking the
 * new bid and seeing if it beats any of the existing leaders to automatic proxy bidding where bids
 * are updated based on bidders' pre-stated maximum amounts.
 * </p>
 * <p>
 * Thread-Safety: Implementations are expected to be thread safe.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public interface AuctionStrategy {
    /**
     * <p>
     * Purpose: Calculate the minimum increment for the given auction.
     * </p>
     * <p>
     * Args: auction - The Auction whose minimum bid increment is required. Must not be null.
     * </p>
     * <p>
     * Returns: The minimum increment for the given auction.
     * </p>
     * <p>
     * Exceptions: IllegalArgumentException - If the given argument is null.
     * </p>
     * @param auction
     * @return
     */
    public int getMinimumIncrement(Auction auction);

    /**
     * <p>
     * Purpose: Process the given new bid and return the updated Auction object.
     * </p>
     * <p>
     * Args: auction - The Auction in which a new bid is being placed. Must not be null.
     * </p>
     * <p>
     * bid - The new bid being placed. Must not be null.
     * </p>
     * <p>
     * Returns: The updated Auction object.
     * </p>
     * <p>
     * Exceptions: IllegalBidException - If the new bid cannot be legally placed in the auction.
     * </p>
     * <p>
     * IllegalArgumentException - If any of the arguments is null.
     * </p>
     * @param auction
     * @param bid
     * @return
     */
    public Auction processNewBid(Auction auction, Bid bid);

    /**
     * <p>
     * Purpose: Process the given bid update and return the updated Auction object.
     * </p>
     * <p>
     * Args: auction - The Auction in which a bid is being updated. Must not be null.
     * </p>
     * <p>
     * originalBid - The bid being updated. Must not be null.
     * </p>
     * <p>
     * newBid - The new bid to which the original is being updated. Must not be null.
     * </p>
     * <p>
     * Returns: The updated Auction object.
     * </p>
     * <p>
     * Exceptions: IllegalBidException - If the original bid cannot be legally updated in the
     * auction.
     * </p>
     * <p>
     * IllegalArgumentException - If any of the arguments is null.
     * </p>
     * <p>
     * </p>
     * @param auction
     * @param originalBid
     * @param newBid
     * @return
     */
    public Auction processBidUpdate(Auction auction, Bid originalBid, Bid newBid);

    /**
     * <p>
     * Purpose: Selects and returns the leading bids of the given auction.
     * </p>
     * <p>
     * Args: auction - The Auction whose leading bids are desired. Must not be null.
     * </p>
     * <p>
     * Returns: The leading bids of the given auction.
     * </p>
     * <p>
     * Exceptions: IllegalArgumentException - If the given argument is null.
     * </p>
     * @param auction
     * @return
     */
    public Bid[] selectLeadingBids(Auction auction);
}
