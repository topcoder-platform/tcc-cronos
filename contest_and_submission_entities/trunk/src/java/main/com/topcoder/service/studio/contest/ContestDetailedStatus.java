/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest;

import java.io.Serializable;

/**
 * <p>
 * Represents the entity class for db table <i>contest_detail_status_lu</i>.
 * </p>
 *
 * <p>
 * Thread Safety: This entity is not thread safe since it is mutable.
 * </p>
 *
 * @author superZZ
 * @version 1.0
 */
public class ContestDetailedStatus implements Serializable {
    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = -3732665980743049769L;

    /**
     * Represents the entity id.
     */
    private Long contestDetailStatusId;

    /**
     * Represents the status description.
     */
    private String description;

    /**
     * Represents the ContestStatus.
     */
    private ContestStatus contestStatus;
    
    /**
     * Default constructor.
     */
    public ContestDetailedStatus() {
        // empty
    }

    /**
     * Returns the contestStatusDetailId.
     *
     * @return the contestStatusDetailId.
     */
    public Long getContestDetailedStatusId() {
        return contestDetailStatusId;
    }

    /**
     * Updates the contestStatusDetailId with the specified value.
     *
     * @param contestStatusId
     *            the contestStatusId to set.
     */
    public void setContestDetailedStatusId(Long contestStatusDetailId) {
        this.contestDetailStatusId = contestStatusDetailId;
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
     * Returns the contestStatus.
     *
     * @return the contestStatus.
     */
    public ContestStatus getContestStatus() {
        return contestStatus;
    }

    /**
     * Updates the contestStatus with the specified value.
     *
     * @param contestStatus
     *            the contestStatus to set.
     */
    public void setContestStatus(ContestStatus contestStatus) {
        this.contestStatus = contestStatus;
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
        if (obj instanceof ContestDetailedStatus) {
            return getContestDetailedStatusId() == ((ContestDetailedStatus) obj).getContestDetailedStatusId();
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
        return Helper.calculateHash(contestDetailStatusId);
    }

}
