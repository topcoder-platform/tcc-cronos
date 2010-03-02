/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest;

import java.io.Serializable;

/**
 * <p>
 * Represents the entity class for db table <i>mime_type_lu</i>.
 * </p>
 *
 * <p>
 * Thread Safety: This entity is not thread safe since it is mutable.
 * </p>
 *
 * @author tushak, cyberjag
 * @version 1.0
 */
public class MimeType implements Serializable {
    /**
     * Generated serial version id.
     */
    private static final long serialVersionUID = -6143772018938261855L;

    /**
     * Represents the entity id.
     */
    private Long mimeTypeId;

    /**
     * Represents the description of mime type.
     */
    private String description;

    /**
     * Represents the studio file type.
     */
    private StudioFileType studioFileType;

    /**
     * Default constructor.
     */
    public MimeType() {
        // empty
    }

    /**
     * Returns the mimeTypeId.
     *
     * @return the mimeTypeId.
     */
    public Long getMimeTypeId() {
        return mimeTypeId;
    }

    /**
     * Updates the mimeTypeId with the specified value.
     *
     * @param mimeTypeId
     *            the mimeTypeId to set.
     */
    public void setMimeTypeId(Long mimeTypeId) {
        this.mimeTypeId = mimeTypeId;
    }

    /**
     * Returns the description.
     *
     * @return the description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Updates the description with the specified value.
     *
     * @param description
     *            the description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the studioFileType.
     *
     * @return the studioFileType.
     */
    public StudioFileType getStudioFileType() {
        return studioFileType;
    }

    /**
     * Updates the studioFileType with the specified value.
     *
     * @param studioFileType
     *            the studioFileType to set.
     */
    public void setStudioFileType(StudioFileType studioFileType) {
        this.studioFileType = studioFileType;
    }

    /**
     * Compares this object with the passed object for equality. Only the id will be compared.
     *
     * @param obj
     *            the {@code Object} to compare to this one
     * @return true if this object is equal to the other, {@code false} if not
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof MimeType) {
            return getMimeTypeId() == ((MimeType) obj).getMimeTypeId();
        }
        return false;
    }

    /**
     * Overrides {@code Object.hashCode()} to provide a hash code consistent with this class's
     * {@link #equals(Object)}} method.
     *
     * @return a hash code for this {@code MimeType}
     */
    @Override
    public int hashCode() {
        return Helper.calculateHash(mimeTypeId);
    }
}
