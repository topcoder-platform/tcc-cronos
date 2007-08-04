/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.persistence.entities;

import com.orpheus.game.persistence.Domain;
import com.orpheus.game.persistence.DomainTarget;
import com.orpheus.game.persistence.Helper;
import com.orpheus.game.persistence.HostingSlot;

import java.util.Date;


/**
 * <p>
 * Simple implementation of the HostingSlot. Represents the persistent information about a particular hosting slot.
 * </p>
 *
 * <p>
 * <strong>Thread Safety</strong>:This class is immutable and thread-safe.
 * </p>
 * @author argolite, waits
 * @version 1.0
 */
public class HostingSlotImpl implements HostingSlot {
    /**
     * <p>
     * Represents the ID of this hosting slot. Set in the constructor, can be null, and will not change. If not null,
     * must be positive.
     * </p>
     */
    private final Long id;

    /**
     * <p>
     * Represents the ID of the image information associated with this hosting slot Set in the constructor, can be
     * any positive number, and will not change.
     * </p>
     */
    private final long imageId;

    /**
     * <p>
     * Represents the unique IDs of the brain teasers in this slot's brain teaser series. Set in the constructor,
     * cannot be null nor its elements non-positive, and will not change.
     * </p>
     */
    private final long[] brainTeaserIds;

    /**
     * <p>
     * Represents the ID of the puzzle assigned to this slot, or null if there is none. Set in the constructor, can
     * be null, and will not change. If not null, must be positive.
     * </p>
     */
    private final Long puzzleId;

    /**
     * <p>
     * Represents the sequence number of this slot within its block. Set in the constructor, can be any non-negative
     * value, and will not change.
     * </p>
     */
    private final int sequenceNumber;

    /**
     * <p>
     * Returns the amount of the winning bid in the auction for this slot. Set in the constructor, can be any
     * non-negative value, and will not change.
     * </p>
     */
    private final int winningBid;

    /**
     * <p>
     * Returns a Date representing the date and time at which this hosting slot began hosting, or null if it has not
     * yet started hosting. Set in the constructor and will not change.
     * </p>
     */
    private final Date hostingStart;

    /**
     * <p>
     * Returns a Date representing the date and time at which this hosting slot stopped hosting, or null if it has
     * not yet stopped (including if it hasn't begun). Set in the constructor and will not change.
     * </p>
     */
    private final Date hostingEnd;

    /**
     * <p>
     * Returns an array of DomainTarget objects representing the 'minihunt targets'; for this hosting slot.&nbsp; Set
     * in the constructor, cannot be null nor its elements null, and will not change.
     * </p>
     */
    private final DomainTarget[] domainTargets;

    /**
     * <p>
     * Represents a Domain object represented the domain assigned to this hosting slot. Set in the constructor,
     * cannot be null, and will not change.
     * </p>
     */
    private final Domain domain;

    /**
     * <p>
     * Represents the ID of a hosting block this slot belongs to. Set in the constructor, can be any
     * non-negative value, and will not change.
     * </p>
     */
    private final long hostingBlockId;

    /**
     * <p>
     * Constructor. It will make shallow copies of the brainTeaserIds and domainTargets arrays.
     * </p>
     *
     * @param id the id
     * @param domain the domain
     * @param imageId the image id
     * @param brainTeaserIds the brain teaser ids
     * @param puzzleId puzzle id
     * @param sequenceNumber the sequence number
     * @param domainTargets the array of DomainTarget objects
     * @param winningBid the amount of the winning bid
     * @param hostingStart the start date of the hosting
     * @param hostingEnd the end date of the hosting
     * @param hostingBlockId the ID of a hosting block this slot belongs to
     *
     * @throws IllegalArgumentException If id, if given, is not positive, or if domain is null, or if imageId is not
     *         positive, OR if brainTeaserIds is null or any of its elements are not positive, OR if puzzleId, if
     *         given, is not positive,OR if sequenceNumber is negative, OR if domainTargets is null or its elements
     *         is null, OR if winningBid is negative, or if hostingStart &gt hostingEnd if both not null or
     *         hostingBlockId is negative.
     */
    public HostingSlotImpl(Long id, Domain domain, long imageId, long[] brainTeaserIds, Long puzzleId,
        int sequenceNumber, DomainTarget[] domainTargets, int winningBid, Date hostingStart, Date hostingEnd,
        long hostingBlockId) {
        if (id != null) {
            Helper.checkNotPositive(id.longValue(), "Id");
        }

        if (puzzleId != null) {
            Helper.checkNotPositive(puzzleId.longValue(), "puzzleId");
        }

        Helper.checkNotNull(domain, "Domain");
        Helper.checkNegative(winningBid, "winningBid");
        Helper.checkNotPositive(imageId, "imageId");
        Helper.checkNotPositive(hostingBlockId, "hostingBlockId");
        Helper.checkNegative(sequenceNumber, "sequenceNumber");

        //brainTeaserIds should not be null and its elements are not positive
        Helper.checkNotNull(brainTeaserIds, "brainTeaserIds");

        for (int i = 0; i < brainTeaserIds.length; i++) {
            Helper.checkNotPositive(brainTeaserIds[i], "brainTeaserId");
        }

        //domainTargets should not be null and does not contain null element
        Helper.checkNotNullOrContainNullElement(domainTargets, "DomainTargets");

        //endDate should be later than startDate
        if ((hostingStart != null) && (hostingEnd != null) && hostingEnd.before(hostingStart)) {
            throw new IllegalArgumentException("The HostingEnd should be later than HostingStart.");
        }

        this.id = id;
        this.sequenceNumber = sequenceNumber;
        this.domain = domain;
        this.imageId = imageId;
        this.winningBid = winningBid;
        this.hostingStart = hostingStart;
        this.hostingEnd = hostingEnd;
        this.puzzleId = puzzleId;
        this.hostingBlockId = hostingBlockId;

        //shallow copy  brainTeaserIds
        this.brainTeaserIds = (long[]) brainTeaserIds.clone();

        //shallow copy domainTargets
        this.domainTargets = (DomainTarget[]) domainTargets.clone();
    }

    /**
     * <p>
     * Gets the ID of this hosting slot, or null if none has yet been assigned.
     * </p>
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * <p>
     * Returns a Domain object represented the domain assigned to this hosting slot.
     * </p>
     *
     * @return the domain
     */
    public Domain getDomain() {
        return this.domain;
    }

    /**
     * <p>
     * Returns the ID of the image information associated with this hosting slot.
     * </p>
     *
     * @return the image id
     */
    public long getImageId() {
        return this.imageId;
    }

    /**
     * <p>
     * Returns the unique IDs of the brain teasers in this slot's brain teaser series. Returns a shallow copy.
     * </p>
     *
     * @return the brain teaser ids
     */
    public long[] getBrainTeaserIds() {
        return (long[]) this.brainTeaserIds.clone();
    }

    /**
     * <p>
     * Returns the ID of the puzzle assigned to this slot, or null if there is none.
     * </p>
     *
     * @return puzzle id
     */
    public Long getPuzzleId() {
        return this.puzzleId;
    }

    /**
     * <p>
     * Returns the sequence number of this slot within its block.
     * </p>
     *
     * @return sequence number
     */
    public int getSequenceNumber() {
        return this.sequenceNumber;
    }

    /**
     * <p>
     * Returns an array of DomainTarget objects representing the 'minihunt targets' for this hosting slot. Returns a
     * shallow copy.
     * </p>
     *
     * @return the array of DomainTarget objects
     */
    public DomainTarget[] getDomainTargets() {
        return (DomainTarget[]) this.domainTargets.clone();
    }

    /**
     * <p>
     * Returns the amount of the winning bid in the auction for this slot.
     * </p>
     *
     * @return the amount of the winning bid
     */
    public int getWinningBid() {
        return this.winningBid;
    }

    /**
     * <p>
     * Returns a Date representing the date and time at which this hosting slot began hosting, or null if it has not.
     * </p>
     * yet started hosting
     *
     * @return the start date of the hosting
     */
    public Date getHostingStart() {
        return this.hostingStart;
    }

    /**
     * <p>
     * Returns a Date representing the date and time at which this hosting slot stopped hosting, or null if it has
     * not yet stopped (including if it hasn't begun).
     * </p>
     *
     * @return the end date of the hosting
     */
    public Date getHostingEnd() {
        return this.hostingEnd;
    }

    /**
     * <p>
     * Returns the ID of a hosting block which this slot belongs to.
     * </p>
     *
     * @return the ID of hosting block which this slot belongs to.
     */
    public long getHostingBlockId() {
        return this.hostingBlockId;
    }
}
