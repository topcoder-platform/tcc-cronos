/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.auction.persistence.impl;

import com.topcoder.util.auction.Bid;

import java.util.Date;


/**
 * <p>
 * An implementation of the Bid interface for testing.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockBid implements Bid {
    /**
     * Gets the bidder id.
     *
     * @return the bidder id.
     */
    public long getBidderId() {
        return 0;
    }

    /**
     * Gets the effective amount of this bid.
     *
     * @return the effective amount of this bid.
     */
    public Integer getEffectiveAmount() {
        return null;
    }

    /**
     * Gets the maximum amout of this bid.
     *
     * @return the maximum amout of this bid.
     */
    public int getMaxAmount() {
        return 0;
    }

    /**
     * Gets the date of this bid.
     *
     * @return the date of this bid.
     */
    public Date getTimestamp() {
        return null;
    }
}
