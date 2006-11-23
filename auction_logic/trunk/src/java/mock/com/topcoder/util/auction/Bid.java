/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.auction;

import java.util.Date;

/**
 * <p>
 * This interface is referenced from the Auction Logic component but the Auction Framework component
 * where it is defined is still under development, so default implementation is used here. After the
 * Auction Framework will be released this interface can be removed. The documentation is left as it
 * was in the development distribution of the Auction Framework component.
 * </p>
 * Purpose: This interface provides the contract for the properties that a bid must satisfy.
 * <p>
 * Implementation: Implementations may use simple member variables to implement the properties.
 * </p>
 * <p>
 * Thread Safety: It is required that implementations be thread safe.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public interface Bid {
    /**
     * <p>
     * Purpose: Returns the bidder ID of the bid.
     * </p>
     * <p>
     * Args: None.
     * </p>
     * <p>
     * Implementation: Simply return the member variable.
     * </p>
     * <p>
     * Returns: The bidder ID of the bid.
     * </p>
     * <p>
     * Exceptions: None.
     * </p>
     * <p>
     * </p>
     * @return
     */
    public long getBidderId();

    /**
     * <p>
     * Purpose: Returns the effective amount of the bid.
     * </p>
     * <p>
     * Args: None.
     * </p>
     * <p>
     * Returns: The effective amount of the bid.
     * </p>
     * <p>
     * Exceptions: None.
     * </p>
     * <p>
     * </p>
     * @return
     */
    public Integer getEffectiveAmount();

    /**
     * <p>
     * Purpose: Returns the max amount of the bid.
     * </p>
     * <p>
     * Args: None.
     * </p>
     * <p>
     * Implementation: Simply return the member variable.
     * </p>
     * <p>
     * Returns: The max amount of the bid.
     * </p>
     * <p>
     * Exceptions: None.
     * </p>
     * <p>
     * </p>
     * @return
     */
    public int getMaxAmount();

    /**
     * <p>
     * Purpose: Returns the timestamp of the bid.
     * </p>
     * <p>
     * Args: None.
     * </p>
     * <p>
     * Implementation: Simply return the member variable.
     * </p>
     * <p>
     * Returns: The timestamp of the bid.
     * </p>
     * <p>
     * Exceptions: None.
     * </p>
     * <p>
     * </p>
     * @return
     */
    public Date getTimestamp();
}
