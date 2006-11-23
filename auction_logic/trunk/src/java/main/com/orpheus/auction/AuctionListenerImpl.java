/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.auction;

import javax.servlet.ServletContext;

import com.orpheus.administration.AdministrationException;
import com.orpheus.administration.AdministrationManager;
import com.orpheus.auction.persistence.CustomBid;
import com.orpheus.game.GameDataException;
import com.orpheus.game.GameDataManager;
import com.topcoder.util.auction.Auction;
import com.topcoder.util.auction.AuctionListener;
import com.topcoder.util.auction.Bid;
import com.topcoder.web.frontcontroller.HandlerExecutionException;

/**
 * <p>
 * An AuctionListener implementation that responds to the auction completion events by notifying
 * the game data manager service of the winning bids and then requesting the administration manager
 * service to initialize the slots for the corresponding block.
 * </p>
 * <p>
 * Thread safety: This class is thread-safe since it's immutable.
 * </p>
 * @author woodjhon, TCSDEVELOPER
 * @version 1.0
 */
public class AuctionListenerImpl implements AuctionListener {

    /**
     * <p>
     * The servlet context used to get the GameDataManager and AdminstratorManager stored as the
     * context attributes. The attribute keys are defined in the KeyConstants. It's set in the
     * constructor, immutable and cannot be null.
     * </p>
     */
    private final ServletContext context;

    /**
     * <p>
     * Creates a new AuctionListenerImpl instance with the given context.
     * </p>
     * @param context
     *            servlet context to get GameDataManager and AdminstratorManager from.
     * @throws IllegalArgumentException
     *             if context is null.
     */
    public AuctionListenerImpl(ServletContext context) {
        Helper.checkNotNull(context, "context");
        this.context = context;
    }

    /**
     * <p>
     * Alerts this listener that an auction has been completed.
     * </p>
     * <p>
     * Notifies the game data manager service of the winning bids and then requests the
     * administration manager service to initialize the slots for the corresponding block. For this
     * purposes, auction id of the completed auction is used as the block id required by the game
     * data manager.
     * </p>
     * @param auction
     *            the auction that has completed.
     * @throws IllegalArgumentException
     *             if argument is null.
     * @throws AuctionListenerImplException
     *             if error occurs during processing.
     */
    public void auctionComplete(Auction auction) {
        Helper.checkNotNull(auction, "auction");

        // get GameDataManager
        GameDataManager gameDataManager;
        try {
            gameDataManager =
                    (GameDataManager) Helper.getServletContextAttribute(
                            context, KeyConstants.GAME_DATA_MANAGER_KEY, GameDataManager.class);
        } catch (HandlerExecutionException e) {
            throw new AuctionListenerImplException("Unable to retrieve GameDataManager from the context");
        }

        // get AdministrationManager
        AdministrationManager administrationManager;
        try {
            administrationManager =
                    (AdministrationManager) Helper.getServletContextAttribute(
                            context, KeyConstants.ADMINISTRATION_MANAGER_KEY, AdministrationManager.class);
        } catch (HandlerExecutionException e) {
            throw new AuctionListenerImplException("Unable to retrieve AdministrationManager from the context");
        }

        // get array of ids of winning bids
        Bid[] bids = auction.getBids();
        // if bids is null throw Exception
        if (bids == null) {
            throw new AuctionListenerImplException("Bids array couldn't be null");
        }

        long[] ids = new long[bids.length];
        for (int i = 0; i < ids.length; i++) {
            // all bids must be CustomBid instances
            if (!(bids[i] instanceof CustomBid)) {
                throw new AuctionListenerImplException("The auction bids should be CustomBid instances");
            }

            CustomBid custom = (CustomBid) bids[i];
            if (custom.getId() == null) {
                throw new AuctionListenerImplException("The bid is is not specified");
            }
            ids[i] = custom.getId().longValue();
        }

        // if auction id is null then throw Exception
        if (auction.getId() == null) {
            throw new AuctionListenerImplException("Auction id couldn't be null");
        }

        // notify GameDataManager
        try {
            gameDataManager.recordWinningBids(auction.getId().longValue(), ids);
        } catch (GameDataException e) {
            throw new AuctionListenerImplException("Unable to notify GameDataManager", e);
        }
        // request AdministrationManager
        try {
            administrationManager.initializeSlotsForBlock(auction.getId().longValue());
        } catch (AdministrationException e) {
            throw new AuctionListenerImplException("Unable to request AdministrationManager", e);
        }
    }

    /**
     * <p>
     * Alerts this listener that a bid has been outbid.
     * </p>
     * <p>
     * Empty method. Does nothing.
     * </p>
     * @param auction
     *            the auction where the outbidding took place.
     * @param bid
     *            the bid that was out bid.
     * @throws IllegalArgumentException
     *             if one of the arguments is null.
     */
    public void outBid(Auction auction, Bid bid) {
        Helper.checkNotNull(auction, "auction");
        Helper.checkNotNull(bid, "bid");
    }

    /**
     * <p>
     * Alerts this listener that a bid has been submitted.
     * </p>
     * <p>
     * Empty method. Does nothing.
     * </p>
     * @param auction
     *            the auction where the submission took place.
     * @param bid
     *            the bid that was submitted.
     * @throws IllegalArgumentException
     *             if one of the arguments is null.
     */
    public void bidSubmitted(Auction auction, Bid bid) {
        Helper.checkNotNull(auction, "auction");
        Helper.checkNotNull(bid, "bid");
    }

    /**
     * <p>
     * Alerts this listener that a bid has been updated.
     * </p>
     * <p>
     * Empty method. Does nothing.
     * </p>
     * @param auction
     *            the auction where the update took place.
     * @param bid
     *            the updated bid.
     * @throws IllegalArgumentException
     *             if one of the arguments is null.
     */
    public void bidUpdated(Auction auction, Bid bid) {
        Helper.checkNotNull(auction, "auction");
        Helper.checkNotNull(bid, "bid");
    }

    /**
     * <p>
     * Alerts this listener that an auction has started.
     * </p>
     * <p>
     * Empty method. Does nothing.
     * </p>
     * @param auction
     *            the auction that has started.
     * @throws IllegalArgumentException
     *             if argument is null.
     */
    public void auctionStarted(Auction auction) {
        Helper.checkNotNull(auction, "auction");
    }
}
