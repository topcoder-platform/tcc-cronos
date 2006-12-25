/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.persistence.entities;

import com.orpheus.game.persistence.DomainTarget;
import com.orpheus.game.persistence.Helper;


/**
 * <p>
 * Simple implementation of the DomainTarget. Represents an object to be sought by players on a host site.
 * </p>
 *
 * <p>
 * Thread Safety:This class is immutable and thread-safe.
 * </p>
 * @author argolite, waits
 * @version 1.0
 */
public class DomainTargetImpl implements DomainTarget {
    /**
     * <p>
     * Represents the unique identifier of this target. Set in the constructor, can be null, and will not change. If
     * not null, must be positive.
     * </p>
     */
    private final Long id;

    /**
     * <p>
     * Represents the sequence number of this target among those assigned to the same hosting slot. Set in the
     * constructor, can be any non-negative value, and will not change.
     * </p>
     */
    private final int sequenceNumber;

    /**
     * <p>
     * Represents the path and file parts of the URI at which the target is located. Set in the constructor, cannot
     * be null/empty, and will not change.
     * </p>
     */
    private final String uriPath;

    /**
     * <p>
     * Represents the plain text identifier of the target. Set in the constructor, can be null/empty, and will not
     * change. Empty is set to null.
     * </p>
     */
    private final String identifierText;

    /**
     * <p>
     * Represents a hash of the target's identifier. Set in the constructor, cannot be null/empty, and will not
     * change.
     * </p>
     */
    private final String identifierHash;

    /**
     * <p>
     * Represents the unique identifier of a downloadable object constituting an image to be presented to users as
     * the clue for this target. Set in the constructor, can be any positive number, and will not change.
     * </p>
     */
    private final long clueImageId;

    /**
     * <p>
     * Constructor.
     * </p>
     *
     * @param id the id
     * @param sequenceNumber the sequence number
     * @param uriPath the URI path
     * @param identifierText the identifier text
     * @param identifierHash the identifier hash
     * @param clueImageId the clue image id
     *
     * @throws IllegalArgumentException If id, if given, is not positive, or if sequenceNumber is negative, or if
     *         uriPath is null or empty, or identifierHash is null or empty, or clueImageId is not positive
     */
    public DomainTargetImpl(Long id, int sequenceNumber, String uriPath, String identifierText,
        String identifierHash, long clueImageId) {
        if (id != null) {
            Helper.checkNotPositive(id.longValue(), "Id");
        }

        Helper.checkNotPositive(clueImageId, "clueImageId");
        Helper.checkNegative(sequenceNumber, "sequenceNumber");
        Helper.checkNotNullOrEmpty(uriPath, "uriPath");
        Helper.checkNotNullOrEmpty(identifierHash, "identifierHash");

        this.id = id;
        this.sequenceNumber = sequenceNumber;
        this.uriPath = uriPath;
        this.identifierText = identifierText;
        this.identifierHash = identifierHash;
        this.clueImageId = clueImageId;
    }

    /**
     * <p>
     * The unique identifier of this target, or null if none has yet been assigned.
     * </p>
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * <p>
     * Returns the sequence number of this target among those assigned to the same hosting slot.
     * </p>
     *
     * @return the sequence number
     */
    public int getSequenceNumber() {
        return this.sequenceNumber;
    }

    /**
     * <p>
     * Returns the path and file parts of the URI at which the target is located.
     * </p>
     *
     * @return the URI path
     */
    public String getUriPath() {
        return this.uriPath;
    }

    /**
     * <p>
     * Returns the plain text identifier of the target.
     * </p>
     *
     * @return the identifier text
     */
    public String getIdentifierText() {
        return this.identifierText;
    }

    /**
     * <p>
     * Returns a hash of the target's identifier.
     * </p>
     *
     * @return the identifier hash
     */
    public String getIdentifierHash() {
        return this.identifierHash;
    }

    /**
     * <p>
     * Returns the unique identifier of a downloadable object constituting an image to be presented to users as the
     * clue for this target.
     * </p>
     *
     * @return the clue image id
     */
    public long getClueImageId() {
        return this.clueImageId;
    }
}
