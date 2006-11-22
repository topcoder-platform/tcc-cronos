/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.persistence;

/**
 * <p>
 * An interface representing the stored information about an image associated with a specific domain.
 * </p>
 *
 * <p>
 * <b>Thread Safety:</b> Implementations should strive to be thread-safe, but they can expect to be used in a
 * thread-safe manner.
 * </p>
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public interface ImageInfo extends java.io.Serializable {
    /**
     * Returns the unique ID associated with this image information, or null if none has yet been assigned.
     *
     * @return the id
     */
    public Long getId();

    /**
     * Returns the unique ID of the downloadable image data associated with this image information.
     *
     * @return the download id
     */
    public long getDownloadId();

    /**
     * Returns a String description of the image
     *
     * @return the description
     */
    public String getDescription();

    /**
     * Returns the value of the approval flag for this image, or null if no approval decision has yet been made.
     *
     * @return true if approved, false otherwise, or null if not yet decided
     */
    public Boolean isApproved();
}
