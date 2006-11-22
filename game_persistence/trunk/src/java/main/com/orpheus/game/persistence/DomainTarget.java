/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.persistence;

import java.io.Serializable;


/**
 * Represents an object to be sought by players on a host site.
 *
 * <p>
 * <strong>Thread Safety</strong>
 * </p>
 *
 * <p>
 * Implementations should strive to be thread-safe, but they can expect to be used in a thread-safe manner.
 * </p>
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public interface DomainTarget extends Serializable {
    /**
     * The unique identifier of this target, or null if none has yet been assigned.
     *
     * @return the id
     */
    Long getId();

    /**
     * Returns the sequence number of this target among those assigned to the same hosting slot.
     *
     * @return the sequence number
     */
    int getSequenceNumber();

    /**
     * Returns the path and file parts of the URI at which the target is located.
     *
     * @return the URI path
     */
    String getUriPath();

    /**
     * Returns the plain text identifier of the target.
     *
     * @return the identifier text
     */
    String getIdentifierText();

    /**
     * Returns a hash of the target's identifier.
     *
     * @return the identifier path
     */
    String getIdentifierHash();

    /**
     * Returns the unique identifier of a downloadable object constituting an image to be presented to users as the
     * clue for this target.
     *
     * @return the clue image id
     */
    long getClueImageId();
}
