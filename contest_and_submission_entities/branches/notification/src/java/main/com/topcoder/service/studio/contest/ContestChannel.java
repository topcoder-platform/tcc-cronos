/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
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
 * <p>
 * Version 1.1: removed name, parentChannelId and fileType fields.
 * </p>
 *
 * @author tushak, cyberjag
 * @version 1.1
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
     * @return a hash code for this {@code ContestChannel}
     */
    @Override
    public int hashCode() {
        return Helper.calculateHash(contestChannelId);
    }
}
