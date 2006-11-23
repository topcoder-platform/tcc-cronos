/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.auction.persistence.ejb;

import java.io.Serializable;

import java.util.Date;


/**
 * <p>
 * Simple transfer bean that exists parallel to the Auction interface but is serializable. It transports the data
 * between the client and the DAO layers. It is assembled at both those ends, and is also cached in the DAO layer. The
 * EJB layer does not operate on it.
 * </p>
 *
 * <p>
 * <b>Thread Safety: </b>This object is mutable and not thread-safe.
 * </p>
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public class AuctionDTO implements Serializable {
    /**
     * <p>
     * Represents the auction Id. It will not be null.
     * </p>
     *
     * <p>
     * Use the setter/getter for access. Any value is acceptable since this is a DTO, to be used for transporting data,
     * not making judgements on its content.
     * </p>
     */
    private Long id;

    /**
     * <p>
     * Represents the summary. It can be any value, but when retrieving from the DB, it will be empty.
     * </p>
     *
     * <p>
     * Use the setter/getter for access. Any value is acceptable since this is a DTO, to be used for transporting data,
     * not making judgements on its content.
     * </p>
     */
    private String summary;

    /**
     * <p>
     * Represents the description. It can be any value, but when retrieving from the DB, it will be empty.
     * </p>
     *
     * <p>
     * Use the setter/getter for access. Any value is acceptable since this is a DTO, to be used for transporting data,
     * not making judgements on its content.
     * </p>
     */
    private String description;

    /**
     * <p>
     * Represents the count of the individual items for sale.
     * </p>
     *
     * <p>
     * Use the setter/getter for access. Any value is acceptable since this is a DTO, to be used for transporting data,
     * not making judgements on its content.
     * </p>
     */
    private int itemCount;

    /**
     * <p>
     * Represents the minimum bid.
     * </p>
     *
     * <p>
     * Use the setter/getter for access. Any value is acceptable since this is a DTO, to be used for transporting data,
     * not making judgements on its content.
     * </p>
     */
    private int minimumBid;

    /**
     * <p>
     * Represents the date of start of this auction.
     * </p>
     *
     * <p>
     * Use the setter/getter for access. Any value is acceptable since this is a DTO, to be used for transporting data,
     * not making judgements on its content.
     * </p>
     */
    private Date startDate;

    /**
     * <p>
     * Represents the date of end of this auction.
     * </p>
     *
     * <p>
     * Use the setter/getter for access. Any value is acceptable since this is a DTO, to be used for transporting data,
     * not making judgements on its content.
     * </p>
     */
    private Date endDate;

    /**
     * <p>
     * Represents the bids associated with this action. It miht not actually represent all bids, especially when
     * updating a bid. If there are no bids, it will be an empty array, so it will never be null.
     * </p>
     *
     * <p>
     * Use the setter/getter for access. Any value is acceptable since this is a DTO, to be used for transporting data,
     * not making judgements on its content.
     * </p>
     */
    private BidDTO[] bids;

    /**
     * <p>
     * Empty constructor.
     * </p>
     */
    public AuctionDTO() {
    }

    /**
     * Get the id.
     *
     * @return the id.
     */
    public Long getId() {
        return id;
    }

    /**
     * Set the id.
     *
     * @param id the id.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get the summary.
     *
     * @return the summary.
     */
    public String getSummary() {
        return summary;
    }

    /**
     * Set the summary.
     *
     * @param summary the summary.
     */
    public void setSummary(String summary) {
        this.summary = summary;
    }

    /**
     * Get the description.
     *
     * @return the description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the description.
     *
     * @param description the description.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get the item count.
     *
     * @return the item count..
     */
    public int getItemCount() {
        return itemCount;
    }

    /**
     * Set the item count.
     *
     * @param itemCount the item count.
     */
    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    /**
     * Get the minimum bid.
     *
     * @return the minimum bid.
     */
    public int getMinimumBid() {
        return minimumBid;
    }

    /**
     * Set the minimum bid.
     *
     * @param minimumBid the minimum bid.
     */
    public void setMinimumBid(int minimumBid) {
        this.minimumBid = minimumBid;
    }

    /**
     * Get the start date.
     *
     * @return the start date.
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Set the start date.
     *
     * @param startDate the start date.
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * Get the end date.
     *
     * @return the end date.
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * Set the end date.
     *
     * @param endDate the end date.
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * Get the bids.
     *
     * @return the bids.
     */
    public BidDTO[] getBids() {
        return bids;
    }

    /**
     * Set the bids.
     *
     * @param bids the bids.
     */
    public void setBids(BidDTO[] bids) {
        this.bids = bids;
    }
}
