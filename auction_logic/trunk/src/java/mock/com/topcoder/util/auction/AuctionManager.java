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
 * Purpose: This interface defines the contract for an auction manager. The auction manager acts as
 * the single point of access for applications wishing to use this component to conduct online
 * auctions or in support of real-world auctions. Through the manager, an application would create
 * auctions, place bids, update bids and attach listeners to listen to auction events. The
 * application can also get references to the persistence, bid validator and auction strategy used
 * by this manager to actually manage auctions.
 * </p>
 * <p>
 * Implementation: Implementations would use the persistence, validation and auction strategy
 * objects to accomplish the purpose of managing the auction. They would also employ a background
 * process, possibly, to broadcast the auction events. They are also expected to update auctions
 * automatically after changes so that in the event of sudden termination of the manager, the
 * auction may be resumed without any loss of data.
 * </p>
 * <p>
 * Thread Safety: It is not a requirement for implementations to be thread safe but it would be a
 * very useful functionality since synchronization is required only on a per-auction basis and this
 * can be more easily achieved by this component than externally. Also look at the demo section of
 * the component specification to see how external applications should use the manager.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public interface AuctionManager {
    /**
     * <p>
     * Purpose: Retrieves the AuctionPersistence object used by this manager to persist Auction and
     * Bid objects.
     * </p>
     * <p>
     * Args: None.
     * </p>
     * <p>
     * Returns: The AuctionPersistence object used by this manager to persist Auction and Bid
     * objects.
     * </p>
     * <p>
     * Exceptions: None.
     * </p>
     * @return
     */
    public AuctionPersistence getAuctionPersistence();

    /**
     * <p>
     * Purpose: Retrieves the BidValidator object used by this manager to validate bids.
     * </p>
     * <p>
     * Args: None.
     * </p>
     * <p>
     * Returns: The BidValidator object used by this manager to validate bids.
     * </p>
     * <p>
     * Exceptions: None.
     * </p>
     * @return
     */
    public BidValidator getBidValidator();

    /**
     * <p>
     * Purpose: Retrieves the AuctionStrategy object used by this manager to process bids.
     * </p>
     * <p>
     * Args: None.
     * </p>
     * <p>
     * Returns: The AuctionStrategy object used by this manager to process bids.
     * </p>
     * <p>
     * Exceptions: None.
     * </p>
     * @return
     */
    public AuctionStrategy getAuctionStrategy();

    /**
     * <p>
     * Purpose: Creates a new Auction in the underlying persistence and returns the created Auction
     * object. If the given Auction object has a null auctionId, a unique auctionId is automatically
     * generated and the returned Auction object contains the generated Id.
     * </p>
     * <p>
     * Args: auction - The Auction object to be created. Must not be null.
     * </p>
     * <p>
     * Returns: The Auction object that has been created in the underlying persistence.
     * </p>
     * <p>
     * Exceptions: AuctionException - If the creation of the Auction fails.
     * </p>
     * <p>
     * IllegalArgumentexception - If the given argument is null.
     * </p>
     * @param auction
     * @return
     */
    public Auction createAuction(Auction auction) throws AuctionException;

    /**
     * <p>
     * Purpose: Accepts a new bid for the given auction.
     * </p>
     * <p>
     * Args: auction - The id of the Auction for which a new bid must be accepted.
     * </p>
     * <p>
     * bid - The new bid being placed. Must not be null.
     * </p>
     * <p>
     * Returns: The Auction object after the Bid has been placed.
     * </p>
     * <p>
     * Exceptions: AuctionException - If the placing of the new bid fails.
     * </p>
     * <p>
     * IllegalArgumentexception - If any of the given arguments is null.
     * </p>
     * @param auction
     * @param bid
     * @return
     */
    public Auction acceptNewBid(long auction, Bid bid) throws AuctionException;

    /**
     * <p>
     * Purpose: Accepts a bid update for the given auction.
     * </p>
     * <p>
     * Args: auction - The id of the Auction for which a new bid must be accepted.
     * </p>
     * <p>
     * originalBid - The bid being updated. Must not be null.
     * </p>
     * <p>
     * newBid - The bid which is updating the original bid. Must not be null.
     * </p>
     * <p>
     * Returns: The Auction object after the Bid has been placed.
     * </p>
     * <p>
     * Exceptions: AuctionException - If the placing of the new bid fails.
     * </p>
     * <p>
     * IllegalArgumentexception - If any of the given arguments is null.
     * </p>
     * @param auction
     * @param originalBid
     * @param newBid
     * @return
     */
    public Auction acceptBidUpdate(long auction, Bid originalBid, Bid newBid)
            throws AuctionException;

    /**
     * <p>
     * Purpose: Attaches a listener to the events broadcast by this manager.
     * </p>
     * <p>
     * Args: listener - The AuctionListener to attach. Must not be null.
     * </p>
     * <p>
     * Returns: None.
     * </p>
     * <p>
     * Exceptions: IllegalArgumentexception - If the given argument is null.
     * </p>
     * @param listener
     */
    public void addAuctionListener(AuctionListener listener);

    /**
     * <p>
     * Purpose: Gets all the listeners attached to this manager.
     * </p>
     * <p>
     * Args: None.
     * </p>
     * <p>
     * Returns: All the AuctionListener objects attached to this manager.
     * </p>
     * <p>
     * Exceptions: None.
     * </p>
     * @return
     */
    public AuctionListener[] getAuctionListeners();

    /**
     * <p>
     * Purpose: Removes the given listener from the listeners attached to this manager.
     * </p>
     * <p>
     * Args: listener - The AuctionListener to remove. Must not be null.
     * </p>
     * <p>
     * Returns: None.
     * </p>
     * <p>
     * Exceptions: IllegalArgumentexception - If the given argument is null.
     * </p>
     * @param listener
     */
    public void removeAuctionListener(AuctionListener listener);

    /**
     * <p>
     * Purpose: Removes all listeners attached to this manager.
     * </p>
     * <p>
     * Args: None.
     * </p>
     * <p>
     * Returns: None.
     * </p>
     * <p>
     * Exceptions: None.
     * </p>
     */
    public void removeAllAuctionListeners();
}
