/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.accuracytests.mock;

import com.orpheus.game.persistence.Domain;
import com.orpheus.game.persistence.ImageInfo;

/**
 * A mock implementation of Domain.
 * 
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockDomain implements Domain {

    /**
     * Represents the domain id.
     */
    private Long id;

    /**
     * Creates a mock domain.
     * 
     * @param id
     *            the domain id.
     */
    MockDomain(long id) {
        this.id = new Long(id);
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
     * No implementation.
     * 
     * @return always 0.
     */
    public long getSponsorId() {
        return 0;
    }

    /**
     * No implementation.
     * 
     * @return always null.
     */
    public String getDomainName() {
        return "domain " + id;
    }

    /**
     * No implementation.
     * 
     * @return always null.
     */
    public Boolean isApproved() {
        return id.longValue() % 2 == 0 ? Boolean.TRUE : Boolean.FALSE;
    }

    /**
     * No implementation.
     * 
     * @return always null.
     */
    public ImageInfo[] getImages() {
        return null;
    }
}