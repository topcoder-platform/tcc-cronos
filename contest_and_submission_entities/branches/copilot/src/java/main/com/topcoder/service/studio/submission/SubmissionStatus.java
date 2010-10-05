/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.submission;

import java.io.Serializable;

import com.topcoder.service.studio.contest.Helper;

/**
 * <p>
 * Represents the entity class for db table <i>submission_status_lu</i>.
 * </p>
 * <p>
 * Currently two possible statuses are ACTIVE and DELETED.
 * </p>
 *
 * <p>
 * Thread Safety: This entity is not thread safe since it is mutable.
 * </p>
 *
 * @author tushak, cyberjag
 * @version 1.0
 */
public class SubmissionStatus implements Serializable {
    /**
     * Generated serial version id.
     */
    private static final long serialVersionUID = -7157147495107091757L;

    /**
     * Represents the entity id.
     */
    private Long submissionStatusId;

    /**
     * Represents the description of Status.
     */
    private String description;

    /**
     * Default constructor.
     */
    public SubmissionStatus() {
        // empty
    }

    /**
     * Returns the submissionStatusId.
     *
     * @return the submissionStatusId.
     */
    public Long getSubmissionStatusId() {
        return submissionStatusId;
    }

    /**
     * Updates the submissionStatusId with the specified value.
     *
     * @param submissionStatusId
     *            the submissionStatusId to set.
     */
    public void setSubmissionStatusId(Long submissionStatusId) {
        this.submissionStatusId = submissionStatusId;
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
        if (obj instanceof SubmissionStatus) {
            return getSubmissionStatusId() == ((SubmissionStatus) obj).getSubmissionStatusId();
        }
        return false;
    }

    /**
     * Overrides {@code Object.hashCode()} to provide a hash code consistent with this class's
     * {@link #equals(Object)}} method.
     *
     * @return a hash code for this {@code SubmissionStatus}
     */
    @Override
    public int hashCode() {
        return Helper.calculateHash(submissionStatusId);
    }
}
