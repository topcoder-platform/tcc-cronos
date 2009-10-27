/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.submission;

import java.io.Serializable;
import java.util.Date;

import com.topcoder.service.studio.contest.Helper;

/**
 * <p>
 * Represents the entity class for db table <i>submission_review</i>.
 * </p>
 *
 * <p>
 * Thread Safety: This entity is not thread safe since it is mutable.
 * </p>
 *
 * @author tushak, cyberjag
 * @version 1.0
 */
public class SubmissionReview implements Serializable {
    /**
     * Generated serial version id.
     */
    private static final long serialVersionUID = -5751923508893860381L;

    /**
     * Represents the id of reviewer.
     */
    private Long reviewerId;

    /**
     * Represents the review text.
     */
    private String text;

    /**
     * Represents the review status.
     */
    private ReviewStatus status;

    /**
     * Represents the submission for review.
     */
    private Submission submission;

    /**
     * Represents the modify date.
     */
    private Date modifyDate;

    /**
     * Default constructor.
     */
    public SubmissionReview() {
        // empty
    }

    /**
     * Returns the reviewerId.
     *
     * @return the reviewerId.
     */
    public Long getReviewerId() {
        return reviewerId;
    }

    /**
     * Updates the reviewerId with the specified value.
     *
     * @param reviewerId
     *            the reviewerId to set.
     */
    public void setReviewerId(Long reviewerId) {
        this.reviewerId = reviewerId;
    }

    /**
     * Returns the text.
     *
     * @return the text.
     */
    public String getText() {
        return text;
    }

    /**
     * Updates the text with the specified value.
     *
     * @param text
     *            the text to set.
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Returns the status.
     *
     * @return the status.
     */
    public ReviewStatus getStatus() {
        return status;
    }

    /**
     * Updates the status with the specified value.
     *
     * @param status
     *            the status to set.
     */
    public void setStatus(ReviewStatus status) {
        this.status = status;
    }

    /**
     * Returns the submission.
     *
     * @return the submission.
     */
    public Submission getSubmission() {
        return submission;
    }

    /**
     * Updates the submission with the specified value.
     *
     * @param submission
     *            the submission to set.
     */
    public void setSubmission(Submission submission) {
        this.submission = submission;
    }

    /**
     * Returns the modifyDate.
     *
     * @return the modifyDate.
     */
    public Date getModifyDate() {
        return modifyDate;
    }

    /**
     * Updates the modifyDate with the specified value.
     *
     * @param modifyDate
     *            the modifyDate to set.
     */
    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
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
        if (obj instanceof SubmissionReview) {
            SubmissionReview review = (SubmissionReview) obj;
            return getReviewerId() == review.getReviewerId()
                    && (submission != null && getSubmission().equals(review.getSubmission()));
        }
        return false;
    }

    /**
     * Overrides {@code Object.hashCode()} to provide a hash code consistent with this class's
     * {@link #equals(Object)}} method.
     *
     * @return a hash code for this {@code SubmissionReview}
     */
    @Override
    public int hashCode() {
        return Helper.calculateHash(submission != null ? submission.getSubmissionId() : null, reviewerId);
    }
}
