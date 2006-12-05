/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.accuracytests.entities;

import com.orpheus.game.persistence.Domain;
import com.orpheus.game.persistence.ImageInfo;

/**
 * Dummy class.
 *
 * @author KKD
 * @version 1.0
 */
public class MockDomain implements Domain {
    /**
     * get domainName.
     * @return DomainName
     */
    public String getDomainName() {

        return null;
    }
    /**
     * get d.
     * @return d
     */
    public Long getId() {

        return null;
    }
    /**
     * get Images.
     * @return Images
     */
    public ImageInfo[] getImages() {

        return null;
    }
    /**
     * get SponsorId.
     * @return SponsorId
     */
    public long getSponsorId() {

        return 0;
    }
    /**
     * get isApproved.
     * @return isApproved
     */
    public Boolean isApproved() {
        return null;
    }
}
