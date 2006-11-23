/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.auction.persistence;

import com.topcoder.util.auction.Bid;

import java.util.Date;


/**
 * <p>
 * An implementation of the Bid interface to support the addition of a mutable id and immutable imageId. Also, the
 * effectiveAmount is given a setter. This is the Bid implementation that is excepted to be used by the persistence
 * clients.
 * </p>
 *
 * <p>
 * <b>Thread Safety: </b>This class is mutable and not thread-safe.
 * </p>
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public class CustomBid implements Bid {
    /**
     * <p>
     * Represents the bid Id. It can be null to indicate that it has not be assigned yet. Use the setter/getter for
     * access.
     * </p>
     *
     * <p>
     * This number will be positive once set, but this is up to the persistence to decide what is allowed.
     * </p>
     */
    private Long id;

    /**
     * <p>
     * Represents the id of an image associated with sponsored domains. It will be set in the constructor, and will not
     * change. Accessed via the getter.
     * </p>
     *
     * <p>
     * This number will be positive once set, but this is up to the persistence to decide what is allowed.
     * </p>
     */
    private final long imageId;

    /**
     * <p>
     * Represents the id of a bidding sponsor. It will be set in the constructor, and will not change. Accessed via the
     * getter.
     * </p>
     *
     * <p>
     * This number will be positive once set, but this is up to the persistence to decide what is allowed.
     * </p>
     */
    private final long bidderId;

    /**
     * <p>
     * Represents the effective amount of the bid. It can be null to indicate that it has not be assigned yet. Use the
     * setter/getter for access.
     * </p>
     *
     * <p>
     * This number will be non-negative once set, but this is up to the persistence to decide what is allowed.
     * </p>
     */
    private Integer effectiveAmount;

    /**
     * <p>
     * Represents the maximum amount a sponsor is willing to pay. It will be set in the constructor, and will not
     * change. Accessed via the getter.
     * </p>
     *
     * <p>
     * This number will be non-negative once set, but this is up to the persistence to decide what is allowed.
     * </p>
     */
    private final int maxAmount;

    /**
     * <p>
     * Represents the date and time of this bid. It will be set in the constructor, will not be null, and will not
     * change. Accessed via the getter.
     * </p>
     */
    private final Date timestamp;

    /**
     * <p>
     * Constructor: Creates a new CustomBid with these parameters. timestamp can't be null.
     * </p>
     *
     * @param bidderId the bidder id.
     * @param imageId the image id.
     * @param maxAmount the maximum amout of this bid.
     * @param timestamp the date of this bid.
     *
     * @throws IllegalArgumentException If timestamp is null.
     */
    public CustomBid(long bidderId, long imageId, int maxAmount, Date timestamp) {
        AuctionPersistenceHelper.validateNotNull(timestamp, "timestamp");
        this.bidderId = bidderId;
        this.imageId = imageId;
        this.maxAmount = maxAmount;
        this.timestamp = timestamp;
    }

    /**
     * <p>
     * Gets the id of this bid. It can be null to indicate the id has not been set yet.
     * </p>
     *
     * @return the id of this bid.
     */
    public Long getId() {
        return id;
    }

    /**
     * <p>
     * Sets the id of this bid.
     * </p>
     *
     * @param id the id of this bid.
     */
    public void setId(long id) {
        this.id = new Long(id);
    }

    /**
     * Gets the image id.
     *
     * @return the image id.
     */
    public long getImageId() {
        return imageId;
    }

    /**
     * Gets the bidder id.
     *
     * @return the bidder id.
     */
    public long getBidderId() {
        return bidderId;
    }

    /**
     * Gets the effective amount of this bid.
     *
     * @return the effective amount of this bid.
     */
    public Integer getEffectiveAmount() {
        return effectiveAmount;
    }

    /**
     * Sets the effective amount of this bid.
     *
     * @param effectiveAmount the effective amount of this bid.
     */
    public void setEffectiveAmount(int effectiveAmount) {
        this.effectiveAmount = new Integer(effectiveAmount);
    }

    /**
     * Gets the maximum amout of this bid.
     *
     * @return the maximum amout of this bid.
     */
    public int getMaxAmount() {
        return maxAmount;
    }

    /**
     * Gets the date of this bid.
     *
     * @return the date of this bid.
     */
    public Date getTimestamp() {
        return timestamp;
    }
}
