/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.accuracytests;

import java.util.Date;

import com.orpheus.game.persistence.Domain;
import com.orpheus.game.persistence.DomainTarget;
import com.orpheus.game.persistence.HostingSlot;

/**
 * A stub class for HostingSlot.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AccuracyTestHostingSlot implements HostingSlot {
	/**
     * The id of the slot.
     */
    private Long id;

    /**
     * The domain of the slot.
     */
    private Domain domain;

    /**
     * The image id of the slot.
     */
    private long imageId;

    /**
     * The array of the brain teaser ids.
     */
    private long[] brainTeaserIds;

    /**
     * The puzzle id.
     */
    private Long puzzleId;

    /**
     * The sequence numner.
     */
    private int sequenceNumber;

    /**
     * The array of domain targets instance.
     */
    private DomainTarget[] domainTargets;

    /**
     * The winning bid.
     */
    private int winningBid;

    /**
     * The start date of the hosting.
     */
    private Date hostingStart;

    /**
     * The end date of the hosting.
     */
    private Date hostingEnd;

    /**
     * Empty constructor.
     *
     */
    public AccuracyTestHostingSlot() {
    }

    /**
     * Get the brainTeaserIds.
     *
     * @return an array of long which are the brain teaser ids of the slot.
     */
    public long[] getBrainTeaserIds() {
        return brainTeaserIds;
    }

    /**
     * Set the brainTeaserIds.
     *
     * @param brainTeaserIds the brain teaser ids.
     */
    public void setBrainTeaserIds(long[] brainTeaserIds) {
        this.brainTeaserIds = brainTeaserIds;
    }

    /**
     * Get the domain.
     *
     * @return the domian of the slot.
     */
    public Domain getDomain() {
        return domain;
    }

    /**
     * Get the domain of the slot.
     *
     * @param domain the Domain to be set
     */
    public void setDomain(Domain domain) {
        this.domain = domain;
    }

    /**
     * Get the domain targets.
     *
     * @return the domain targets of the slot
     */
    public DomainTarget[] getDomainTargets() {
        return domainTargets;
    }

    /**
     * Set the domain targets.
     *
     * @param domainTargets the domain targets of the slot to be set
     */
    public void setDomainTargets(DomainTarget[] domainTargets) {
        this.domainTargets = domainTargets;
    }

    /**
     * Get the start date of the hosting.
     *
     * @return the date of the hosting start date
     */
    public Date getHostingStart() {
        return hostingStart;
    }

    /**
     * Set the start date.
     *
     * @param hostingStart the hosting start date to be set
     */
    public void setHostingStart(Date hostingStart) {
        this.hostingStart = hostingStart;
    }

    /**
     * Get the end date of the hosting.
     *
     * @return the date of the hosting end date
     */
    public Date getHostingEnd() {
        return hostingEnd;
    }

    /**
     * Set the end date.
     *
     * @param hostingEnd the hosting end date to be set
     */
    public void setHostingEnd(Date hostingEnd) {
        this.hostingEnd = hostingEnd;
    }

    /**
     * Get the id of the slot.
     *
     * @return the id of the slot
     */
    public Long getId() {
        return id;
    }

    /**
     * Set the id of the slot.
     *
     * @param id the id to be set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get the image id of the slot.
     *
     * @return the image id
     */
    public long getImageId() {
        return imageId;
    }

    /**
     * Set the image id.
     *
     * @param imageId the image id to be set
     */
    public void setImageId(long imageId) {
        this.imageId = imageId;
    }

    /**
     * Get the puzzle id.
     *
     * @return the puzzle id
     */
    public Long getPuzzleId() {
        return puzzleId;
    }

    /**
     * Set the puzzle id.
     *
     * @param puzzleId the puzzle id to be set
     */
    public void setPuzzleId(Long puzzleId) {
        this.puzzleId = puzzleId;
    }

    /**
     * Get the sequence number.
     *
     * @return the sequence number
     */
    public int getSequenceNumber() {
        return sequenceNumber;
    }

    /**
     * Set the sequence number
     * @param sequenceNumber the sequence number to be set
     */
    public void setSequenceNumber(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    /**
     * Get the winning bid.
     *
     * @return the winning bid
     */
    public int getWinningBid() {
        return winningBid;
    }

    /**
     * Set the winning bid
     *
     * @param winningBid the winning bid to be set.
     */
    public void setWinningBid(int winningBid) {
        this.winningBid = winningBid;
    }
}
