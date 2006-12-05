/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.accuracytests.handlers;

import com.orpheus.game.persistence.ImageInfo;

/**
 * Dummy class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockImageInfo implements ImageInfo {
    /**
     * The id.
     */
    private Long id;

    /**
     * Dummy
     */
    public Long getId() {
        return id;
    }

    /**
     * Dummy
     * @param id the id
     */
    public MockImageInfo(Long id) {
        this.id = id;
    }

    /**
     * Dummy
     */
    public long getDownloadId() {
        return 0;
    }

    /**
     * Dummy
     */
    public String getDescription() {
        return null;
    }

    /**
     * Dummy
     */
    public Boolean isApproved() {
        return null;
    }
}
