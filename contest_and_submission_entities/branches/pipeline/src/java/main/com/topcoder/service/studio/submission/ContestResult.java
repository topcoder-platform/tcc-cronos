/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.submission;

import java.io.Serializable;
import java.util.Date;

import com.topcoder.service.studio.contest.Contest;
import com.topcoder.service.studio.contest.Helper;

/**
 * <p>
 * Represents the entity class for db table <i>contest_result</i>.
 * </p>
 *
 * <p>
 * Thread Safety: This entity is not thread safe since it is mutable.
 * </p>
 *
 * @author tushak, cyberjag
 * @version 1.0
 */
public class ContestResult implements Serializable {
    /**
     * Generated serial version id.
     */
    private static final long serialVersionUID = 3446907648346169300L;

    /**
     * Represents the contest for result.
     */
    private Contest contest;

    /**
     * Represents the submission.
     */
    private Submission submission;

    /**
     * Represents the score of submission.
     */
    private Float finalScore;

    /**
     * Represents the place of submission under contest.
     */
    private Integer placed;

    /**
     * Represents the create date.
     */
    private Date createDate;

    /**
     * Default constructor.
     */
    public ContestResult() {
        // empty
    }

    /**
     * Returns the contest.
     *
     * @return the contest.
     */
    public Contest getContest() {
        return contest;
    }

    /**
     * Updates the contest with the specified value.
     *
     * @param contest
     *            the contest to set.
     */
    public void setContest(Contest contest) {
        this.contest = contest;
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
     * Returns the finalScore.
     *
     * @return the finalScore.
     */
    public Float getFinalScore() {
        return finalScore;
    }

    /**
     * Updates the finalScore with the specified value.
     *
     * @param finalScore
     *            the finalScore to set.
     */
    public void setFinalScore(Float finalScore) {
        this.finalScore = finalScore;
    }

    /**
     * Returns the placed.
     *
     * @return the placed.
     */
    public Integer getPlaced() {
        return placed;
    }

    /**
     * Updates the placed with the specified value.
     *
     * @param placed
     *            the placed to set.
     */
    public void setPlaced(Integer placed) {
        this.placed = placed;
    }

    /**
     * Returns the createDate.
     *
     * @return the createDate.
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * Updates the createDate with the specified value.
     *
     * @param createDate
     *            the createDate to set.
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * Compares this object with the passed object for equality. Only the composite id will be compared.
     *
     * @param obj
     *            the {@code Object} to compare to this one
     * @return true if this object is equal to the other, {@code false} if not
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ContestResult) {
            ContestResult result = (ContestResult) obj;
            return (contest != null && getContest().equals(result.getContest()))
                    && (submission != null && getSubmission().equals(result.getSubmission()));
        }
        return false;
    }

    /**
     * Overrides {@code Object.hashCode()} to provide a hash code consistent with this class's
     * {@link #equals(Object)}} method.
     *
     * @return a hash code for this {@code ContestResult}
     */
    @Override
    public int hashCode() {
        return Helper.calculateHash(contest != null ? contest.getContestId() : null,
                submission != null ? submission.getSubmissionId() : null);
    }
}
