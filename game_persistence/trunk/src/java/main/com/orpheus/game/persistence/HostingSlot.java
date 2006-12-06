/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.persistence;

import java.io.Serializable;

import java.util.Date;


/**
 * <p>
 * An interface representing the persistent information about a particular hosting slot.
 * </p>
 *
 * <p>
 * <b>Thread Safety:</b>  Implementations should strive to be thread-safe, but they can expect to be used in a
 * thread-safe manner.
 * </p>
 *
 * @author argolite, waits
 * @version 1.0
 */
public interface HostingSlot extends Serializable {
    /**
     * Gets the ID of this hosting slot, or null if none has yet been assigned.
     *
     * @return the id
     */
    Long getId();

    /**
     * Returns a Domain object represented the domain assigned to this hosting slot.
     *
     * @return the domain
     */
    Domain getDomain();

    /**
     * Returns the ID of the image information associated with this hosting slot.
     *
     * @return the image id
     */
    long getImageId();

    /**
     * Returns the unique IDs of the brain teasers in this slot's brain teaser series.
     *
     * @return the brain teaser id
     */
    long[] getBrainTeaserIds();

    /**
     * Returns the ID of the puzzle assigned to this slot, or null if there is none.
     *
     * @return puzzle id
     */
    Long getPuzzleId();

    /**
     * Returns the sequence number of this slot within its block.
     *
     * @return sequence number
     */
    int getSequenceNumber();

    /**
     * Returns an array of DomainTarget objects representing the 'minihunt targets'; for this hosting slot.
     *
     * @return the array of DomainTarget objects
     */
    DomainTarget[] getDomainTargets();

    /**
     * Returns the amount of the winning bid in the auction for this slot.
     *
     * @return the amount of the winning bid
     */
    int getWinningBid();

    /**
     * Returns a Date representing the date and time at which this hosting slot began hosting, or null if it has not.
     * yet started hosting
     *
     * @return the start date of the hosting
     */
    Date getHostingStart();

    /**
     * Returns a Date representing the date and time at which this hosting slot stopped hosting, or null if it has
     * not yet stopped (including if it hasn't begun).
     *
     * @return the end date of the hosting
     */
    Date getHostingEnd();
}
