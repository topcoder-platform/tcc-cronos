/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>
 * Represents the entity class for db table <i>file_type_lu</i>.
 * </p>
 *
 * <p>
 * Thread Safety: This entity is not thread safe since it is mutable.
 * </p>
 *
 * @author tushak, cyberjag
 * @version 1.0
 */
public class StudioFileType implements Serializable {
    /**
     * Generated serial version id.
     */
    private static final long serialVersionUID = -8787477894026160631L;

    /**
     * Represents the id of file type.
     */
    private long studioFileType;

    /**
     * Represents the map with supported mime types.
     */
    private Set<MimeType> mimeTypes = new HashSet<MimeType>();

    /**
     * Represents the description.
     */
    private String description;

    /**
     * Represents the sort.
     */
    private Integer sort;

    /**
     * Represents the flag for image file.
     */
    private Boolean imageFile;

    /**
     * Represents the extension of file.
     */
    private String extension;

    /**
     * Default constructor.
     */
    public StudioFileType() {
        // empty
    }

    /**
     * Returns the studioFileType.
     *
     * @return the studioFileType.
     */
    public long getStudioFileType() {
        return studioFileType;
    }

    /**
     * Updates the studioFileType with the specified value.
     *
     * @param studioFileType
     *            the studioFileType to set.
     */
    public void setStudioFileType(long studioFileType) {
        this.studioFileType = studioFileType;
    }

    /**
     * Returns the mimeTypes.
     *
     * @return the mimeTypes.
     */
    public Set<MimeType> getMimeTypes() {
        return mimeTypes;
    }

    /**
     * Updates the mimeTypes with the specified value.
     *
     * @param mimeTypes
     *            the mimeTypes to set.
     */
    public void setMimeTypes(Set<MimeType> mimeTypes) {
        this.mimeTypes = mimeTypes;
    }

    /**
     * Returns the sort.
     *
     * @return the sort.
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * Updates the sort with the specified value.
     *
     * @param sort
     *            the sort to set.
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /**
     * Returns the imageFile.
     *
     * @return the imageFile.
     */
    public Boolean isImageFile() {
        return imageFile;
    }

    /**
     * Updates the imageFile with the specified value.
     *
     * @param imageFile
     *            the imageFile to set.
     */
    public void setImageFile(Boolean imageFile) {
        this.imageFile = imageFile;
    }

    /**
     * Returns the extension.
     *
     * @return the extension.
     */
    public String getExtension() {
        return extension;
    }

    /**
     * Updates the extension with the specified value.
     *
     * @param extension
     *            the extension to set.
     */
    public void setExtension(String extension) {
        this.extension = extension;
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
     * Compares this object with the passed object for equality. Only the id will be compared.
     *
     * @param obj
     *            the {@code Object} to compare to this one
     * @return true if this object is equal to the other, {@code false} if not
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof StudioFileType) {
            return getStudioFileType() == ((StudioFileType) obj).getStudioFileType();
        }
        return false;
    }

    /**
     * Overrides {@code Object.hashCode()} to provide a hash code consistent with this class's
     * {@link #equals(Object)}} method.
     *
     * @return a hash code for this {@code StudioFileType}
     */
    @Override
    public int hashCode() {
        return Helper.calculateHash(studioFileType);
    }
}
