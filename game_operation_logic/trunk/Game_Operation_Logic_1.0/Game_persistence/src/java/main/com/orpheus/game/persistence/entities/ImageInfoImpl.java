/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.persistence.entities;

import com.orpheus.game.persistence.Helper;
import com.orpheus.game.persistence.ImageInfo;


/**
 * Simple implementation of the ImageInfo. Represents the stored information about an image associated with a
 * specific domain.
 *
 * <p>
 * Thread Safety:This class is immutable and thread-safe.
 * </p>
 * @author argolite, waits
 * @version 1.0
 */
public class ImageInfoImpl implements ImageInfo {
    /**
     * <p>
     * Represents the unique ID associated with this image information. Set in the constructor, can be null, and will
     * not change. If not null, must be positive.
     * </p>
     */
    private final Long id;

    /**
     * <p>
     * Represents the unique ID of the downloadable image data associated with this image information. Set in the
     * constructor, can be any positive number, and will not change.
     * </p>
     */
    private final long downloadId;

    /**
     * <p>
     * Represents a String description of the image. Set in the constructor, cannot be null/empty, and will not
     * change.
     * </p>
     */
    private final String description;

    /**
     * <p>
     * Represents the value of the approval flag for this image, or null if no approval decision has yet been made.
     * Set in the constructor and will not change.
     * </p>
     */
    private final Boolean approved;

    /**
     * <p>
     * Constructor.
     * </p>
     *
     * @param id the id
     * @param downloadId the download id
     * @param description the description
     * @param approved true if approved, false otherwise, or null if not yet decided
     *
     * @throws IllegalArgumentException If id, if given, is not positive, or if downloadId is not positive, or
     *         description is null/empty
     */
    public ImageInfoImpl(Long id, long downloadId, String description, Boolean approved) {
        if (id != null) {
            Helper.checkNotPositive(id.longValue(), "id");
        }

        Helper.checkNotPositive(downloadId, "downloadId");
        Helper.checkNotNullOrEmpty(description, "description");

        this.id = id;
        this.downloadId = downloadId;
        this.description = description;
        this.approved = approved;
    }

    /**
     * <p>
     * Returns the unique ID associated with this image information, or null if none has yet been assigned.
     * </p>
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * <p>
     * Returns the unique ID of the downloadable image data associated with this image information.
     * </p>
     *
     * @return the download id
     */
    public long getDownloadId() {
        return this.downloadId;
    }

    /**
     * <p>
     * Returns a String description of the image.
     * </p>
     *
     * @return the description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * <p>
     * Returns the value of the approval flag for this image, or null if no approval decision has yet been made.
     * </p>
     *
     * @return true if approved, false otherwise, or null if not yet decided
     */
    public Boolean isApproved() {
        return this.approved;
    }
}
