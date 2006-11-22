/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.accuracytests.mock;

import java.util.Date;

import com.orpheus.game.persistence.Domain;
import com.orpheus.game.persistence.DomainTarget;
import com.orpheus.game.persistence.HostingSlot;

/**
 * A mock implementation of HostingSlot.
 * 
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockHostingSlot implements HostingSlot {
    /**
     * Represents a flag.
     */
    private static boolean flag;

    /**
     * Represents the domain.
     */
    private Domain domain;

    /**
     * Represents the id.
     */
    private Long id;

    /**
     * Represents the sequence number.
     */
    private int seq;

    /**
     * Default constructor.
     */
    MockHostingSlot() {
        // empty
    }

    /**
     * Creates the MockHostingSlot.
     * 
     * @param domainId
     *            the domain id
     * @param id
     *            slot id
     * @param seq
     *            sequence number
     */
    MockHostingSlot(long domainId, long id, int seq) {
        domain = new MockDomain(domainId);
        this.id = new Long(id);
        this.seq = seq;
    }

    /**
     * Returns the id.
     * 
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * Returns the domain.
     * 
     * @return domain
     */
    public Domain getDomain() {
        return domain;
    }

    /**
     * No implementation.
     * 
     * @return always 0
     */
    public long getImageId() {
        return 0;
    }

    /**
     * No implementation.
     * 
     * @return always null
     */
    public long[] getBrainTeaserIds() {
        return null;
    }

    /**
     * No implementation.
     * 
     * @return always null
     */
    public Long getPuzzleId() {
        return null;
    }

    /**
     * Returns the sequence number.
     * 
     * @return sequence number
     */
    public int getSequenceNumber() {
        return seq;
    }

    /**
     * Return a pre-defined DomainTargets.
     * 
     * @return DomainTargets array
     */
    public DomainTarget[] getDomainTargets() {
        MockDomainTarget[] targets = new MockDomainTarget[1];
        targets[0] = new MockDomainTarget();
        targets[0].setId(new Long(1));
        targets[0].setSequenceNumber(seq);
        
        return targets;
    }

    /**
     * No implementation.
     * 
     * @return always 0
     */
    public int getWinningBid() {
        return 0;
    }

    /**
     * Returns the current date.
     * 
     * @return current date
     */
    public Date getHostingStart() {
        return new Date();
    }

    /**
     * No implementation.
     * 
     * @return always null
     */
    public Date getHostingEnd() {
        return id.longValue() % 2 == 0 ? new Date() : null;
    }

    /**
     * Sets the flag.
     * 
     * @param flag
     *            flag for setting
     */
    public static void setFlag(boolean flag) {
        MockHostingSlot.flag = flag;
    }
}