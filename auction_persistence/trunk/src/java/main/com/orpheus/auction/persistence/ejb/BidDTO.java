/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.auction.persistence.ejb;

import java.io.Serializable;

import java.util.Date;


/**
 * <p>
 * Simple transfer bean that exists parallel to the Bid interface but is serializable. Specifically, it works in
 * parallel with the CustomBid class. It transports the data between the client and the DAO layers. It is assembled at
 * both those ends, and is also cached in the DAO layer. The EJB layer does not operate on it.
 * </p>
 *
 * <p>
 * <b>Thread Safety: </b>This object is mutable and not thread-safe
 * </p>
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public class BidDTO implements Serializable {
    /**
     * <p>
     * Represents the bid Id. It can be null to indicate that it has not be assigned yet.
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
     * Represents the id of an image associated with sponsored domains.
     * </p>
     *
     * <p>
     * Use the setter/getter for access. Any value is acceptable since this is a DTO, to be used for transporting data,
     * not making judgements on its content.
     * </p>
     */
    private long imageId;

    /**
     * <p>
     * Represents the id of a bidding sponsor.
     * </p>
     *
     * <p>
     * Use the setter/getter for access. Any value is acceptable since this is a DTO, to be used for transporting data,
     * not making judgements on its content.
     * </p>
     */
    private long bidderId;

    /**
     * <p>
     * Represents the effective amount of the bid. It can be null to indicate that it has not be assigned yet.
     * </p>
     *
     * <p>
     * Use the setter/getter for access. Any value is acceptable since this is a DTO, to be used for transporting data,
     * not making judgements on its content.
     * </p>
     */
    private Integer effectiveAmount;

    /**
     * <p>
     * Represents the maximum amount a sponsor is willing to pay..
     * </p>
     *
     * <p>
     * Use the setter/getter for access. Any value is acceptable since this is a DTO, to be used for transporting data,
     * not making judgements on its content.
     * </p>
     */
    private int maxAmount;

    /**
     * <p>
     * Represents the date and time of this bid.
     * </p>
     *
     * <p>
     * Use the setter/getter for access. Any value is acceptable since this is a DTO, to be used for transporting data,
     * not making judgements on its content.
     * </p>
     */
    private Date timestamp;

    /**
     * <p>
     * Empty constructor.
     * </p>
     */
    public BidDTO() {
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
     * Get the image id.
     *
     * @return the image id.
     */
    public long getImageId() {
        return imageId;
    }

    /**
     * Set the image id.
     *
     * @param imageId the image id.
     */
    public void setImageId(long imageId) {
        this.imageId = imageId;
    }

    /**
     * Get the bidder id.
     *
     * @return the bidder id.
     */
    public long getBidderId() {
        return bidderId;
    }

    /**
     * Set the bidder id.
     *
     * @param bidderId the bidder id.
     */
    public void setBidderId(long bidderId) {
        this.bidderId = bidderId;
    }

    /**
     * Get the effective amount.
     *
     * @return the effective amount.
     */
    public Integer getEffectiveAmount() {
        return effectiveAmount;
    }

    /**
     * Set the effective amount.
     *
     * @param effectiveAmount the effective amount.
     */
    public void setEffectiveAmount(Integer effectiveAmount) {
        this.effectiveAmount = effectiveAmount;
    }

    /**
     * Get the max amount.
     *
     * @return the max amount.
     */
    public int getMaxAmount() {
        return maxAmount;
    }

    /**
     * Set the max amount.
     *
     * @param maxAmount the max amount.
     */
    public void setMaxAmount(int maxAmount) {
        this.maxAmount = maxAmount;
    }

    /**
     * Get the date of this bid.
     *
     * @return the date of this bid.
     */
    public Date getTimestamp() {
        return timestamp;
    }

    /**
     * Set the date of this bid.
     *
     * @param timestamp the date of this bid.
     */
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
