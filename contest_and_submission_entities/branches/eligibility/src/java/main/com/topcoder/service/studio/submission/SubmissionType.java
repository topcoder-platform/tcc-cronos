/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.submission;

import java.io.Serializable;

import com.topcoder.service.studio.contest.Helper;

/**
 * <p>
 * Represents the entity class for db table <i>submission_type_lu</i>.
 * </p>
 * <p>
 * Currently two possible statuses are INITIAL_CONTEST_SUBMISSION_TYPE and FINAL_SUBMISSION_TYPE
 * </p>
 *
 * <p>
 * Thread Safety: This entity is not thread safe since it is mutable.
 * </p>
 *
 * @author tushak, cyberjag
 * @version 1.0
 */
public class SubmissionType implements Serializable {
    /**
     * Generated serial version id.
     */
    private static final long serialVersionUID = -3792912357236974677L;

    /**
     * Represents the entity id.
     */
    private Long submissionTypeId;

    /**
     * Represents the description of prize type.
     */
    private String description;

    /**
     * Default constructor.
     */
    public SubmissionType() {
        // empty
    }

    /**
     * Returns the submissionTypeId.
     *
     * @return the submissionTypeId.
     */
    public Long getSubmissionTypeId() {
        return submissionTypeId;
    }

    /**
     * Updates the submissionTypeId with the specified value.
     *
     * @param submissionTypeId
     *            the submissionTypeId to set.
     */
    public void setSubmissionTypeId(Long submissionTypeId) {
        this.submissionTypeId = submissionTypeId;
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
        if (obj instanceof SubmissionType) {
            return getSubmissionTypeId() == ((SubmissionType) obj).getSubmissionTypeId();
        }
        return false;
    }

    /**
     * Overrides {@code Object.hashCode()} to provide a hash code consistent with this class's
     * {@link #equals(Object)}} method.
     *
     * @return a hash code for this {@code SubmissionType}
     */
    @Override
    public int hashCode() {
        return Helper.calculateHash(submissionTypeId);
    }
}
