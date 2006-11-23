/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.auction.persistence.failuretests;

import java.util.Date;

import com.topcoder.util.auction.Auction;
import com.topcoder.util.auction.Bid;

/**
 * <p>
 * Mock implementation of <code>{@link Auction}</code>.
 * </p>
 * @author FireIce
 * @version 1.0
 */
public class MockAuction implements Auction {
    /**
     * <p>
     * Represents the auction Id. It will not be null.
     * </p>
     */
    private Long id;

    /**
     * <p>
     * Represents the summary. It can be any value, but when retrieving from the DB, it will be empty.
     * </p>
     */
    private String summary;

    /**
     * <p>
     * Represents the description. It can be any value, but when retrieving from the DB, it will be empty.
     * </p>
     */
    private String description;

    /**
     * <p>
     * Represents the count of the individual items for sale.
     * </p>
     */
    private int itemCount;

    /**
     * <p>
     * Represents the minimum bid.
     * </p>
     */
    private int minimumBid;

    /**
     * <p>
     * Represents the date of start of this auction.
     * </p>
     */
    private Date startDate;

    /**
     * <p>
     * Represents the date of end of this auction.
     * </p>
     */
    private Date endDate;

    /**
     * <p>
     * Represents the bids associated with this action. It miht not actually represent all bids, especially when
     * updating a bid. If there are no bids, it will be an empty array, so it will never be null.
     * </p>
     */
    private Bid[] bids;

    /**
     * Creates an instance.
     * @param id
     *            id
     * @param summary
     *            summary
     * @param description
     *            description
     * @param itemCount
     *            itemCount
     * @param minimumBid
     *            minimumBid
     * @param startDate
     *            startDate
     * @param endDate
     *            endDate
     * @param bids
     *            bids
     */
    public MockAuction(Long id, String summary, String description, int itemCount, int minimumBid, Date startDate,
        Date endDate, Bid[] bids) {
        super();
        this.id = id;
        this.summary = summary;
        this.description = description;
        this.itemCount = itemCount;
        this.minimumBid = minimumBid;
        this.startDate = startDate;
        this.endDate = endDate;
        this.bids = bids;
    }

    /**
     * Get the id.
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Get the summary.
     * @return the summary
     */
    public String getSummary() {
        return summary;
    }

    /**
     * Get the description.
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Get the item count.
     * @return the item count
     */
    public int getItemCount() {
        return itemCount;
    }

    /**
     * Get the minimum bid.
     * @return the minimum bid
     */
    public int getMinimumBid() {
        return minimumBid;
    }

    /**
     * Get the start date.
     * @return the start date
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Get the end date.
     * @return the end date
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * Gets the bids.
     * @return bids
     */
    public Bid[] getBids() {
        return bids;
    }

}
