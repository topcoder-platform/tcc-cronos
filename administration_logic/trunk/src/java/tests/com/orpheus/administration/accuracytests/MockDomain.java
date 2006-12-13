/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.accuracytests;

import com.orpheus.game.persistence.Domain;
import com.orpheus.game.persistence.ImageInfo;

/**
 * Dummy class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockDomain implements Domain {
    /**
     * The id of the Domain.
     */
    private Long id;

    /**
     * The name.
     */
    private String name;

    /**
     * The imageInfo array.
     */
    private ImageInfo[] images;

    /**
     * The id of the sponsor.
     */
    private long sponsorId;

    /**
     * Dummy
     */
    public Long getId() {
        return id;
    }

    /**
     * Dummy
     * @param id
     */
    public MockDomain(long id) {
        this.id = new Long(id);    
    }

    /**
     * Dummy
     */
    public long getSponsorId() {
        return this.sponsorId;
    }

    /**
     * Dummy
     * @param id the id
     */
    public void setSponsorId(long id) {
        this.sponsorId = id;    
    }

    /**
     * Dummy
     * @return the name
     */
    public String getDomainName() {
        return name;
    }

    /**
     * Set the name of the domain.
     * @param name the name.
     */
    public void setDomainName(String name) {
        this.name = name;
    }

    /**
     * Dummy
     */
    public Boolean isApproved() {
        return null;
    }

    /**
     * Dummy
     */
    public ImageInfo[] getImages() {
        return this.images;
    }

    /**
     * Dummy
     * @param images
     */
    public void setImages(ImageInfo[] images) {
        this.images = images;
    }
}
