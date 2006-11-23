/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.auction.failuretests;

import java.util.Date;

import com.topcoder.util.auction.Auction;
import com.topcoder.util.auction.Bid;

/**
 * <p>
 * A mock implementation of the Auction interface. Allows to set the return values for some
 * methods.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockAuction implements Auction {

    /**
     * <p>
     * Auction's id.
     * </p>
     */
    private Long id = null;

    /**
     * <p>
     * Auction's start date.
     * </p>
     */
    private Date startDate = null;

    /**
     * <p>
     * Auction's end date.
     * </p>
     */
    private Date endDate = null;

    /**
     * <p>
     * Auction's bids.
     * </p>
     */
    private Bid[] bids = null;

    /**
     * <p>
     * Auction's minimum bid.
     * </p>
     */
    private int minimumBid = 0;

    /**
     * <p>
     * Method stub. Returns id.
     * </p>
     * @return ignore
     */
    public Long getId() {
        return id;
    }

    /**
     * <p>
     * Sets auction id.
     * </p>
     * @param id
     *            auction id.
     */
    public void setId(long id) {
        this.id = new Long(id);
    }

    /**
     * <p>
     * Method stub. Returns null.
     * </p>
     * @return ignore
     */
    public String getSummary() {
        return null;
    }

    /**
     * <p>
     * Method stub. Returns null.
     * </p>
     * @return ignore
     */
    public String getDescription() {
        return null;
    }

    /**
     * <p>
     * Method stub. Returns 0.
     * </p>
     * @return ignore
     */
    public int getItemCount() {
        return 0;
    }

    /**
     * <p>
     * Method stub. Returns minimumBid.
     * </p>
     * @return ignore
     */
    public int getMinimumBid() {
        return minimumBid;
    }

    /**
     * <p>
     * Sets minimum bid.
     * </p>
     * @param minimumBid
     *            auction's minimum bid.
     */
    public void setMinimumBid(int minimumBid) {
        this.minimumBid = minimumBid;
    }

    /**
     * <p>
     * Method stub. Returns startDate.
     * </p>
     * @return ignore
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * <p>
     * Sets start date.
     * </p>
     * @param startDate
     *            auction's start date.
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * <p>
     * Method stub. Returns endDate.
     * </p>
     * @return ignore
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * <p>
     * Sets end date.
     * </p>
     * @param endDate
     *            auction's end date.
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * <p>
     * Method stub. Returns bids.
     * </p>
     * @return ignore
     */
    public Bid[] getBids() {
        return bids;
    }

    /**
     * <p>
     * Set bids.
     * </p>
     * @param bids
     *            auctions' bids.
     */
    public void setBids(Bid[] bids) {
        this.bids = bids;
    }
}
