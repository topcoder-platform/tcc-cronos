/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.auction.stresstests;

import com.topcoder.util.auction.Auction;
import com.topcoder.util.auction.Bid;

import java.util.Date;


/**
 * <p>
 * A mock implementation of the Auction interface.
 * </p>
 * @author waits
 * @version 1.0
 */
public class MockAuction implements Auction {
    /** id. */
    private Long id = null;

    /** start date. */
    private Date startDate = null;

    /** end date. */
    private Date endDate = null;

    /** bids. */
    private Bid[] bids = null;

    /** minimum bid. */
    private int minimumBid = 0;

    /**
     * Method stub. Returns id.
     *
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets auction id.
     *
     * @param id auction id.
     */
    public void setId(long id) {
        this.id = new Long(id);
    }

    /**
     * Method stub. Returns null.
     *
     * @return null
     */
    public String getSummary() {
        return null;
    }

    /**
     * Method stub. Returns null.
     *
     * @return null
     */
    public String getDescription() {
        return null;
    }

    /**
     * Method stub. Returns 0.
     *
     * @return 0
     */
    public int getItemCount() {
        return 0;
    }

    /**
     * Method stub. Returns minimumBid.
     *
     * @return minimumBid
     */
    public int getMinimumBid() {
        return minimumBid;
    }

    /**
     * Sets minimum bid.
     *
     * @param minimumBid minimum bid.
     */
    public void setMinimumBid(int minimumBid) {
        this.minimumBid = minimumBid;
    }

    /**
     * Method stub. Returns startDate.
     *
     * @return startdate
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Sets start date.
     *
     * @param startDate start date.
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * Method stub. Returns endDate.
     *
     * @return date
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * Sets end date.
     *
     * @param endDate end date.
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * Method stub. Returns bids.
     *
     * @return bids
     */
    public Bid[] getBids() {
        return bids;
    }

    /**
     * Set bids.
     *
     * @param bids auctions' bids.
     */
    public void setBids(Bid[] bids) {
        this.bids = bids;
    }
}
