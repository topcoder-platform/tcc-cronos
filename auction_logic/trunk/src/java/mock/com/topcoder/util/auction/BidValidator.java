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
 * Purpose: This interface serves to validate a bid. Bid validation is expected to verify whether a
 * bid(or bid update) is valid or not. The contract consists of two methods to validate new bids and
 * bid updates.
 * </p>
 * <p>
 * Implementation: Implementations would use some kind of a validation scheme. This may range from a
 * simple verification of the bid amount with respect to auction minimum bid amount to a check that
 * ensures for example that this bidder has the required credentials to place bid for the specified
 * amount.
 * </p>
 * <p>
 * Thread-Safety: Implementation must be thread-safe.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public interface BidValidator {
    /**
     * <p>
     * Purpose: Verifies whether a new bid placed in an auction is valid.
     * </p>
     * <p>
     * Args: auction - The Auction in which the bid is being placed. Must not be null.
     * </p>
     * <p>
     * bid - The bid being placed. Must not be null.
     * </p>
     * <p>
     * Returns: Whether this bid is valid.
     * </p>
     * <p>
     * Exceptions: IllegalArgumentException - If any of the given arguments is null.
     * </p>
     * @param auction
     * @param bid
     * @return
     */
    public boolean isNewBidValid(Auction auction, Bid bid);

    /**
     * <p>
     * Purpose: Verifies whether the update of a bid placed in an auction is valid.
     * </p>
     * <p>
     * Args: auction - The Auction in which the bid is being updated. Must not be null.
     * </p>
     * <p>
     * originalBid - The bid being updated. Must not be null.
     * </p>
     * <p>
     * newBid - The bid to which the original is being updated. Must not be null.
     * </p>
     * <p>
     * Returns: Whether this bid update is valid.
     * </p>
     * <p>
     * Exceptions: IllegalArgumentException - If any of the given arguments is null.
     * </p>
     * @param auction
     * @param originalBid
     * @param newBid
     * @return
     */
    public boolean isBidUpdateValid(Auction auction, Bid originalBid, Bid newBid);
}
