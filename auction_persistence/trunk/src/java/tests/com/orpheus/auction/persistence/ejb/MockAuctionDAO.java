/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.auction.persistence.ejb;

import java.util.Date;

import com.orpheus.auction.persistence.AuctionDAO;


/**
 * <p>
 * A mock class which extends AuctionDAO for testing. In this mock class, the implemented methods will do nothing and
 * just set the flag to indicate these methods have been invoked.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockAuctionDAO implements AuctionDAO {
    /** Represents whether the method of createAuction has been invoked. */
    private boolean isCreateAuctionCalled = false;

    /** Represents whether the method of getAuction has been invoked. */
    private boolean isGetAuctionCalled = false;

    /** Represents whether the method of updateAuction has been invoked. */
    private boolean isUpdateAuctionCalled = false;

    /** Represents whether the method of updateBids has been invoked. */
    private boolean isUpdateBidsCalled = false;

    /** Represents whether the method of deleteAuction has been invoked. */
    private boolean isDeleteAuctionCalled = false;

    /** Represents whether the method of findAuctionsByDate has been invoked. */
    private boolean isFindAuctionsByDateCalled = false;

    /** Represents whether the method of findAuctionsByBidder has been invoked. */
    private boolean isFindAuctionsByBidderCalled = false;

    /**
     * Gets whether the method of createAuction has been invoked.
     *
     * @return Returns the isCreateAuctionCalled.
     */
    public boolean isCreateAuctionCalled() {
        return isCreateAuctionCalled;
    }

    /**
     * Gets whether the method of deleteAuction has been invoked.
     *
     * @return Returns the isDeleteAuctionCalled.
     */
    public boolean isDeleteAuctionCalled() {
        return isDeleteAuctionCalled;
    }

    /**
     * Gets whether the method of findAuctionsByBidder has been invoked.
     *
     * @return Returns the isFindAuctionsByBidderCalled.
     */
    public boolean isFindAuctionsByBidderCalled() {
        return isFindAuctionsByBidderCalled;
    }

    /**
     * Gets whether the method of findAuctionsByDate has been invoked.
     *
     * @return Returns the isFindAuctionsByDateCalled.
     */
    public boolean isFindAuctionsByDateCalled() {
        return isFindAuctionsByDateCalled;
    }

    /**
     * Gets whether the method of getAuction has been invoked.
     *
     * @return Returns the isGetAuctionCalled.
     */
    public boolean isGetAuctionCalled() {
        return isGetAuctionCalled;
    }

    /**
     * Gets whether the method of updateAuction has been invoked.
     *
     * @return Returns the isUpdateAuctionCalled.
     */
    public boolean isUpdateAuctionCalled() {
        return isUpdateAuctionCalled;
    }

    /**
     * Gets whether the method of updateBids has been invoked.
     *
     * @return Returns the isUpdateBidsCalled.
     */
    public boolean isUpdateBidsCalled() {
        return isUpdateBidsCalled;
    }

    /**
     * Set the flag to indicate this methods has been invoked.
     *
     * @param auction ignored.
     *
     * @return always null.
     */
    public AuctionDTO createAuction(AuctionDTO auction) {
        isCreateAuctionCalled = true;

        return null;
    }

    /**
     * Set the flag to indicate this methods has been invoked.
     *
     * @param auctionId ignored.
     *
     * @return always null.
     */
    public AuctionDTO getAuction(long auctionId) {
        isGetAuctionCalled = true;

        return null;
    }

    /**
     * Set the flag to indicate this methods has been invoked.
     *
     * @param auction ignored.
     *
     * @return always null.
     */
    public AuctionDTO updateAuction(AuctionDTO auction) {
        isUpdateAuctionCalled = true;

        return null;
    }

    /**
     * Set the flag to indicate this methods has been invoked.
     *
     * @param auctionId ignored.
     * @param bids ignored.
     *
     * @return always null.
     */
    public AuctionDTO updateBids(long auctionId, BidDTO[] bids) {
        isUpdateBidsCalled = true;

        return null;
    }

    /**
     * Set the flag to indicate this methods has been invoked.
     *
     * @param auctionId ignored.
     */
    public void deleteAuction(long auctionId) {
        this.isDeleteAuctionCalled = true;
    }

    /**
     * Set the flag to indicate this methods has been invoked.
     *
     * @param startingBy ignored.
     * @param endingAfter ignored.
     *
     * @return always null.
     */
    public AuctionDTO[] findAuctionsByDate(Date startingBy, Date endingAfter) {
        this.isFindAuctionsByDateCalled = true;

        return null;
    }

    /**
     * Set the flag to indicate this methods has been invoked.
     *
     * @param bidderId ignored.
     * @param endingAfter ignored.
     *
     * @return always null.
     */
    public AuctionDTO[] findAuctionsByBidder(long bidderId, Date endingAfter) {
        this.isFindAuctionsByBidderCalled = true;

        return null;
    }
}
