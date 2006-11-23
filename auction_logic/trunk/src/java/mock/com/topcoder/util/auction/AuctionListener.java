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
 * Purpose: This interface defines the contract for objects that wish to listen to auction events
 * taking place in an auction manager. The contract consists of five methods to allow for receiving
 * notification of five different events - the start/completion of an auction, the out bidding of a
 * bid, the submission of a new bid and the update of an existing bid.
 * </p>
 * <p>
 * Implementation: Implementations will handle events depending on the purpose of the listener. For
 * example a listener used by an Online Auction GUI might update the leader board of bids whenever a
 * bid is outbid and may send notifications to registered users when an auction starts/completes.
 * </p>
 * <p>
 * It is vital that implementations return immediately from the methods of this interface, or they
 * will end up delaying the auction event broadcaster. Heavy-duty processing must be spun-off into
 * separate threads.
 * </p>
 * <p>
 * Thread Safety: Implementations are outside the scope of this component and can satisfy
 * tread-safety as they desire.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public interface AuctionListener {
    /**
     * <p>
     * Purpose: Alerts this listener that an auction has started.
     * </p>
     * <p>
     * Args: auction&nbsp; - The Auction that has started. Must not be null.
     * </p>
     * <p>
     * Returns: None.
     * </p>
     * <p>
     * Exceptions: IllegalArgumentException - If the given argument is null.
     * </p>
     * @param auction
     */
    public void auctionStarted(Auction auction);

    /**
     * <p>
     * Purpose: Alerts this listener that an auction has completed.
     * </p>
     * <p>
     * Args: auction&nbsp; - The Auction that has completed. Must not be null.
     * </p>
     * <p>
     * Returns: None.
     * </p>
     * <p>
     * Exceptions: IllegalArgumentException - If the given argument is null.
     * </p>
     * <p>
     * </p>
     * @param auction
     */
    public void auctionComplete(Auction auction);

    /**
     * <p>
     * Purpose: Alerts this listener that abid has been out bid.
     * </p>
     * <p>
     * Args: auction&nbsp; - The Auction where the outbidding took place. Must not be null.
     * </p>
     * <p>
     * bid - The bid that was out bid.Must not be null.
     * </p>
     * <p>
     * Returns: None.
     * </p>
     * <p>
     * Exceptions: IllegalArgumentException - If the given argument is null.
     * </p>
     * @param auction
     * @param bid
     */
    public void outBid(Auction auction, Bid bid);

    /**
     * <p>
     * Purpose: Alerts this listener that a bid has been submitted.
     * </p>
     * <p>
     * Args: auction&nbsp; - The Auction where the submission took place. Must not be null.
     * </p>
     * <p>
     * bid - The bid that was submitted.Must not be null.
     * </p>
     * <p>
     * Returns: None.
     * </p>
     * <p>
     * Exceptions: IllegalArgumentException - If the given argument is null.
     * </p>
     * <p>
     * </p>
     * @param auction
     * @param bid
     */
    public void bidSubmitted(Auction auction, Bid bid);

    /**
     * <p>
     * Purpose: Alerts this listener that a bid has been updated.
     * </p>
     * <p>
     * Args: auction&nbsp; - The Auction where the update took place. Must not be null.
     * </p>
     * <p>
     * bid - The updated bid. Must not be null.
     * </p>
     * <p>
     * Returns: None.
     * </p>
     * <p>
     * Exceptions: IllegalArgumentException - If the given argument is null.
     * </p>
     * <p>
     * </p>
     * @param auction
     * @param bid
     */
    public void bidUpdated(Auction auction, Bid bid);
}
