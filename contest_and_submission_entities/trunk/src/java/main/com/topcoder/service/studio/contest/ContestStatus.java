/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * Represents the entity class for db table <i>contest_detailed_status_lu</i>.
 * </p>
 *
 * <p>
 * Thread Safety: This entity is not thread safe since it is mutable.
 * </p>
 *
 * @author tushak, cyberjag
 * @version 1.0
 */
public class ContestStatus implements Serializable {
    /**
     * Generated serial version id.
     */
    private static final long serialVersionUID = -47120448345509357L;

    /**
     * Represents the entity id.
     */
    private Long contestStatusId;

    /**
     * Represents the status id.
     */
    private Long statusId;

    /**
     * Represents the status description.
     */
    private String description;

    /**
     * Represents the name.
     */
    private String name;

    /**
     * Represents the list of the next possible statuses.
     */
    private List<ContestStatus> statuses;

    /**
     * Default constructor.
     */
    public ContestStatus() {
        // empty
    }

    /**
     * Returns the contestStatusId.
     *
     * @return the contestStatusId.
     */
    public Long getContestStatusId() {
        return contestStatusId;
    }

    /**
     * Updates the contestStatusId with the specified value.
     *
     * @param contestStatusId
     *            the contestStatusId to set.
     */
    public void setContestStatusId(Long contestStatusId) {
        this.contestStatusId = contestStatusId;
    }

    /**
     * Returns the statusId.
     *
     * @return the statusId.
     */
    public Long getStatusId() {
        return statusId;
    }

    /**
     * Updates the statusId with the specified value.
     *
     * @param statusId
     *            the statusId to set.
     */
    public void setStatusId(Long statusId) {
        this.statusId = statusId;
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
     * Returns the statuses.
     *
     * @return the statuses.
     */
    public List<ContestStatus> getStatuses() {
        return statuses;
    }

    /**
     * Updates the statuses with the specified value.
     *
     * @param statuses
     *            the statuses to set.
     */
    public void setStatuses(List<ContestStatus> statuses) {
        this.statuses = statuses;
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
     * Compares this object with the passed object for equality. Only the id will be compared.
     *
     * @param obj
     *            the {@code Object} to compare to this one
     * @return true if this object is equal to the other, {@code false} if not
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ContestStatus) {
            return getContestStatusId() == ((ContestStatus) obj).getContestStatusId();
        }
        return false;
    }

    /**
     * Overrides {@code Object.hashCode()} to provide a hash code consistent with this class's
     * {@link #equals(Object)}} method.
     *
     * @return a hash code for this {@code ContestStatus}
     */
    @Override
    public int hashCode() {
        return Helper.calculateHash(contestStatusId);
    }

}
