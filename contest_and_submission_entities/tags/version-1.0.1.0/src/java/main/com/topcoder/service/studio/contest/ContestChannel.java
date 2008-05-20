/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest;

import java.io.Serializable;

/**
 * <p>
 * Represents the entity class for db table <i>contest_channel_lu</i>.
 * </p>
 *
 * <p>
 * Thread Safety: This entity is not thread safe since it is mutable.
 * </p>
 *
 * @author tushak, cyberjag
 * @version 1.0
 */
public class ContestChannel implements Serializable {
    /**
     * Generated serial version id.
     */
    private static final long serialVersionUID = 4713053485807111093L;

    /**
     * Represents the entity id.
     */
    private Long contestChannelId;

    /**
     * Represents the description of category.
     */
    private String description;

    /**
     * Represents the name of category.
     */
    private String name;

    /**
     * Represents the parent channel.
     */
    private Long parentChannelId;

    /**
     * Represents the file type, default for category.
     */
    private StudioFileType fileType;

    /**
     * Default constructor.
     */
    public ContestChannel() {
        // empty
    }

    /**
     * Returns the contestChannelId.
     *
     * @return the contestChannelId.
     */
    public Long getContestChannelId() {
        return contestChannelId;
    }

    /**
     * Updates the contestChannelId with the specified value.
     *
     * @param contestChannelId
     *            the contestChannelId to set.
     */
    public void setContestChannelId(Long contestChannelId) {
        this.contestChannelId = contestChannelId;
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
     * Returns the name.
     *
     * @return the name.
     */
    public String getName() {
        return name;
    }

    /**
     * Updates the name with the specified value.
     *
     * @param name
     *            the name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the parentChannelId.
     *
     * @return the parentChannelId.
     */
    public Long getParentChannelId() {
        return parentChannelId;
    }

    /**
     * Updates the parentChannelId with the specified value.
     *
     * @param parentChannelId
     *            the parentChannelId to set.
     */
    public void setParentChannelId(Long parentChannelId) {
        this.parentChannelId = parentChannelId;
    }

    /**
     * Returns the fileType.
     *
     * @return the fileType.
     */
    public StudioFileType getFileType() {
        return fileType;
    }

    /**
     * Updates the fileType with the specified value.
     *
     * @param fileType
     *            the fileType to set.
     */
    public void setFileType(StudioFileType fileType) {
        this.fileType = fileType;
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
        if (obj instanceof ContestChannel) {
            return getContestChannelId() == ((ContestChannel) obj).getContestChannelId();
        }
        return false;
    }

    /**
     * Overrides {@code Object.hashCode()} to provide a hash code consistent with this class's
     * {@link #equals(Object)}} method.
     *
     * @return a hash code for this {@code ContestCategory}
     */
    @Override
    public int hashCode() {
        return Helper.calculateHash(contestChannelId);
    }
}
