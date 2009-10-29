/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * Represents the entity class for db table <i>contest_type_lu</i>.
 * </p>
 *
 * <p>
 * Thread Safety: This entity is not thread safe since it is mutable.
 * </p>
 *
 * @author tushak, cyberjag
 * @version 1.0
 */
public class ContestType implements Serializable {
    /**
     * Generated serial version id.
     */
    private static final long serialVersionUID = -6625160651831591598L;

    /**
     * Represents the entity id.
     */
    private Long contestType;

    /**
     * Represents the description of contest type.
     */
    private String description;

    /**
     * Represents the flag for file preview.
     */
    private boolean requirePreviewFile;

    /**
     * Represents the flag for image preview.
     */
    private boolean requirePreviewImage;

    /**
     * Represents the list of configuration parameters.
     */
    private List<ContestTypeConfig> config;

    /**
     * Default constructor.
     */
    public ContestType() {
        // empty
    }

    /**
     * Returns the contestType.
     *
     * @return the contestType.
     */
    public Long getContestType() {
        return contestType;
    }

    /**
     * Updates the contestType with the specified value.
     *
     * @param contestType
     *            the contestType to set.
     */
    public void setContestType(Long contestType) {
        this.contestType = contestType;
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
     * Returns the requirePreviewFile.
     *
     * @return the requirePreviewFile.
     */
    public boolean isRequirePreviewFile() {
        return requirePreviewFile;
    }

    /**
     * Updates the requirePreviewFile with the specified value.
     *
     * @param requirePreviewFile
     *            the requirePreviewFile to set.
     */
    public void setRequirePreviewFile(boolean requirePreviewFile) {
        this.requirePreviewFile = requirePreviewFile;
    }

    /**
     * Returns the requirePreviewImage.
     *
     * @return the requirePreviewImage.
     */
    public boolean isRequirePreviewImage() {
        return requirePreviewImage;
    }

    /**
     * Updates the requirePreviewImage with the specified value.
     *
     * @param requirePreviewImage
     *            the requirePreviewImage to set.
     */
    public void setRequirePreviewImage(boolean requirePreviewImage) {
        this.requirePreviewImage = requirePreviewImage;
    }

    /**
     * Returns the config.
     *
     * @return the config.
     */
    public List<ContestTypeConfig> getConfig() {
        return config;
    }

    /**
     * Updates the config with the specified value.
     *
     * @param config
     *            the config to set.
     */
    public void setConfig(List<ContestTypeConfig> config) {
        this.config = config;
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
        if (obj instanceof ContestType) {
            return getContestType() == ((ContestType) obj).getContestType();
        }
        return false;
    }

    /**
     * Overrides {@code Object.hashCode()} to provide a hash code consistent with this class's
     * {@link #equals(Object)}} method.
     *
     * @return a hash code for this {@code ContestType}
     */
    @Override
    public int hashCode() {
        return Helper.calculateHash(contestType);
    }
}
