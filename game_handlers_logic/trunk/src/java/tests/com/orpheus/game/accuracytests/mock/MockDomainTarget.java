/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.accuracytests.mock;

import com.orpheus.game.persistence.DomainTarget;

/**
 * Mock implementation of DomainTarget.
 * 
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockDomainTarget implements DomainTarget {
    int seq;

    private Long id;

    /**
     * No implementation.
     * 
     * @return always null
     */
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets seq.
     * 
     * @return seq number
     */
    public int getSequenceNumber() {
        return seq;
    }

    /**
     * Set seq;
     * 
     * @return seq number
     */
    public void setSequenceNumber(int seq) {
        this.seq = seq;
    }

    /**
     * No implementation.
     * 
     * @return always null
     */
    public String getUriPath() {
        return null;
    }

    /**
     * Always return string "test".
     * 
     * @return "test"
     */
    public String getIdentifierText() {
        return "text";
    }

    /**
     * No implementation.
     * 
     * @return always null
     */
    public String getIdentifierHash() {
        return null;
    }

    /**
     * No implementation.
     * 
     * @return always 0
     */
    public long getClueImageId() {
        return 0;
    }

}
