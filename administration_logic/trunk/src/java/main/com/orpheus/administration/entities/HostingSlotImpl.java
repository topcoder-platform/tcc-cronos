/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.entities;

import java.util.Date;

import com.orpheus.game.persistence.Domain;
import com.orpheus.game.persistence.DomainTarget;
import com.orpheus.game.persistence.HostingSlot;

/**
 * The implementation of HostingSlot interface representing the persistent
 * information about a particular hosting slot <br/> No validation is done for
 * any of the fields as this is a plain POJO class meant to be used as a data
 * transfer object between remote calls.<br/> Thread-Safety: this class is not
 * thread safe as it is mutable.
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
public class HostingSlotImpl implements HostingSlot {

    /**
     * will hold the unique identifier of this slot.<br/> It can be
     * retrieved/set using corresponding getter/setter.<br/> It may be null.
     *
     */
    private Long id;

    /**
     * will hold a Domain object represented the domain assigned to this hosting
     * slot.<br/> It can be retrieved/set using corresponding getter/setter.<br/>
     * It may be null.
     *
     */
    private Domain domain;

    /**
     * will hold the ID of the image information associated with this hosting
     * slot.<br/> It can be retrieved/set using corresponding getter/setter.<br/>
     * It may be any value.
     *
     */
    private long imageId;

    /**
     * will hold the unique IDs of the brain teasers in this slot?s brain teaser
     * series.<br/> It can be retrieved/set using corresponding getter/setter.<br/>
     * It may be null.
     *
     */
    private long[] brainTeaserIds;

    /**
     * will hold the ID of the puzzle assigned to this slot.<br/> It can be
     * retrieved/set using corresponding getter/setter.<br/> It may be null.
     *
     */
    private Long puzzleId;

    /**
     * will hold the sequence number of this slot within its block.<br/> It can
     * be retrieved/set using corresponding getter/setter.<br/> It may be any
     * value.
     *
     */
    private int sequenceNumber;

    /**
     * will hold an array of DomainTarget objects representing the "minihunt
     * targets" for this hosting slot.<br/> It can be retrieved/set using
     * corresponding getter/setter.<br/> It may be null.
     *
     */
    private DomainTarget[] domainTargets;

    /**
     * will hold the amount of the winning bid in the auction for this slot.<br/>
     * It can be retrieved/set using corresponding getter/setter.<br/> It may
     * be any value.
     *
     */
    private int winningBid;

    /**
     * will hold a Date representing the date and time at which this hosting
     * slot began hosting.<br/> It can be retrieved/set using corresponding
     * getter/setter.<br/> It may be null.
     *
     */
    private Date hostingStart;

    /**
     * will hold Date representing the date and time at which this hosting slot
     * stopped hosting.<br/> It can be retrieved/set using corresponding
     * getter/setter.<br/> It may be null.
     *
     */
    private Date hostingEnd;

    /**
     * Empty constructor.
     *
     */
    public HostingSlotImpl() {

    }

    /**
     * Retrieves the unique IDs of the brain teasers in this slot?s brain teaser
     * series.
     *
     * @return Returns the brainTeaserIds.
     */
    public long[] getBrainTeaserIds() {
        return brainTeaserIds;
    }

    /**
     * Sets the unique IDs of the brain teasers in this slot's brain teaser
     * series.
     *
     *
     * @param brainTeaserIds
     *            The brainTeaserIds to set.
     */
    public void setBrainTeaserIds(long[] brainTeaserIds) {
        this.brainTeaserIds = brainTeaserIds;
    }

    /**
     * Retrieves a Domain object represented the domain assigned to this hosting
     * slot.
     *
     * @return Returns the domain.
     */
    public Domain getDomain() {
        return domain;
    }

    /**
     * Sets a Domain object represented the domain assigned to this hosting
     * slot.
     *
     *
     * @param domain
     *            The domain to set.
     */
    public void setDomain(Domain domain) {
        this.domain = domain;
    }

    /**
     * Retrieves an array of DomainTarget objects representing the "minihunt
     * targets" for this hosting slot.
     *
     *
     * @return Returns the domainTargets.
     */
    public DomainTarget[] getDomainTargets() {
        return domainTargets;
    }

    /**
     * Sets an array of DomainTarget objects representing the "minihunt targets"
     * for this hosting slot.
     *
     *
     * @param domainTargets
     *            The domainTargets to set.
     */
    public void setDomainTargets(DomainTarget[] domainTargets) {
        this.domainTargets = domainTargets;
    }

    /**
     * Retrieves Date representing the date and time at which this hosting slot
     * stopped hosting.
     *
     * @return Returns the hostingEnd.
     */
    public Date getHostingEnd() {
        return hostingEnd;
    }

    /**
     * Sets Date representing the date and time at which this hosting slot
     * stopped hosting.
     *
     *
     * @param hostingEnd
     *            The hostingEnd to set.
     */
    public void setHostingEnd(Date hostingEnd) {
        this.hostingEnd = hostingEnd;
    }

    /**
     * Retrieves a Date representing the date and time at which this hosting
     * slot began hosting.
     *
     * @return Returns the hostingStart.
     */
    public Date getHostingStart() {
        return hostingStart;
    }

    /**
     * Sets hostingStart.
     *
     *
     * @param hostingStart
     *            The hostingStart to set.
     */
    public void setHostingStart(Date hostingStart) {
        this.hostingStart = hostingStart;
    }

    /**
     * Retrieves the unique identifier of this slot.
     *
     *
     * @return Returns the id.
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique identifier of this slot.
     *
     *
     * @param id
     *            The id to set.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Retrieves the ID of the image information associated with this hosting
     * slot.
     *
     * @return Returns the imageId.
     */
    public long getImageId() {
        return imageId;
    }

    /**
     * Sets the ID of the image information associated with this hosting slot.
     *
     *
     * @param imageId
     *            The imageId to set.
     */
    public void setImageId(long imageId) {
        this.imageId = imageId;
    }

    /**
     * Retrieves the ID of the puzzle assigned to this slot.
     *
     *
     * @return Returns the puzzleId.
     */
    public Long getPuzzleId() {
        return puzzleId;
    }

    /**
     * Sets the ID of the puzzle assigned to this slot.
     *
     *
     * @param puzzleId
     *            The puzzleId to set.
     */
    public void setPuzzleId(Long puzzleId) {
        this.puzzleId = puzzleId;
    }

    /**
     * Retrieves the sequence number of this slot within its block.
     *
     *
     * @return Returns the sequenceNumber.
     */
    public int getSequenceNumber() {
        return sequenceNumber;
    }

    /**
     * Sets the sequence number of this slot within its block.
     *
     *
     * @param sequenceNumber
     *            The sequenceNumber to set.
     */
    public void setSequenceNumber(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    /**
     * Retrieves the amount of the winning bid in the auction for this slot.
     *
     *
     * @return Returns the winningBid.
     */
    public int getWinningBid() {
        return winningBid;
    }

    /**
     * Sets the amount of the winning bid in the auction for this slot.
     *
     *
     * @param winningBid
     *            The winningBid to set.
     */
    public void setWinningBid(int winningBid) {
        this.winningBid = winningBid;
    }
}
