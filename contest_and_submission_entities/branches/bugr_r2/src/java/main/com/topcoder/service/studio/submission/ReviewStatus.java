/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.submission;

import java.io.Serializable;

import com.topcoder.service.studio.contest.Helper;

/**
 * <p>
 * Represents the entity class for db table <i>payments_status_lu</i>.
 * </p>
 *
 * <p>
 * Currently three possible statuses are PASSED, FAILED and CHEATED.
 * </p>
 *
 * <p>
 * Thread Safety: This entity is not thread safe since it is mutable.
 * </p>
 *
 * @author tushak, cyberjag
 * @version 1.0
 */
public class ReviewStatus implements Serializable {
    /**
     * Generated serial version id.
     */
    private static final long serialVersionUID = 2675730885682254434L;

    /**
     * Represents the entity id.
     */
    private Long reviewStatusId;

    /**
     * Represents the description of Status.
     */
    private String description;

    /**
     * Default constructor.
     */
    public ReviewStatus() {
        // empty
    }

    /**
     * Returns the reviewStatusId.
     *
     * @return the reviewStatusId.
     */
    public Long getReviewStatusId() {
        return reviewStatusId;
    }

    /**
     * Updates the reviewStatusId with the specified value.
     *
     * @param reviewStatusId
     *            the reviewStatusId to set.
     */
    public void setReviewStatusId(Long reviewStatusId) {
        this.reviewStatusId = reviewStatusId;
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
        if (obj instanceof ReviewStatus) {
            return getReviewStatusId() == ((ReviewStatus) obj).getReviewStatusId();
        }
        return false;
    }

    /**
     * Overrides {@code Object.hashCode()} to provide a hash code consistent with this class's
     * {@link #equals(Object)}} method.
     *
     * @return a hash code for this {@code ReviewStatus}
     */
    @Override
    public int hashCode() {
        return Helper.calculateHash(reviewStatusId);
    }
}
