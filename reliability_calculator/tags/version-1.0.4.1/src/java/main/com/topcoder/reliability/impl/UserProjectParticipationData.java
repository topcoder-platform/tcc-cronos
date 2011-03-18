/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reliability.impl;

import java.util.Date;

/**
 * <p>
 * This class is a container for information about user's participation in one specific project. It is a simple
 * JavaBean (POJO) that provides getters and setters for all private attributes and performs no argument validation in
 * the setters.
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> This class is mutable and not thread safe.
 * </p>
 *
 * @author saarixx, sparemax
 * @version 1.0
 */
public class UserProjectParticipationData extends BaseUserProjectData {
    /**
     * <p>
     * The project start date. Corresponds to the start date of the Submission phase.
     * </p>
     *
     * <p>
     * Can be any value. Has getter and setter.
     * </p>
     */
    private Date projectStartDate;

    /**
     * <p>
     * The date when the user registered for the project.
     * </p>
     *
     * <p>
     * Can be any value. Has getter and setter.
     * </p>
     */
    private Date userRegistrationDate;

    /**
     * <p>
     * The final user score. Is null if user didn't provide a submission or the submission didn't pass screening.
     * </p>
     *
     * <p>
     * Can be any value. Has getter and setter.
     * </p>
     */
    private Double userScore;

    /**
     * <p>
     * The value indicating whether the user's submission passed review. Is false if the user didn't provide a
     * submission or the submission didn't pass screening.
     * </p>
     *
     * <p>
     * Can be any value. Has getter and setter.
     * </p>
     */
    private boolean passedReview;

    /**
     * <p>
     * The ID of the submission status. Is null if the user didn't provide a submission.
     * </p>
     *
     * <p>
     * Can be any value. Has getter and setter.
     * </p>
     */
    private Long submissionStatusId;

    /**
     * <p>
     * The ID of the submission phase status for this project.
     * </p>
     *
     * <p>
     * Can be any value. Has getter and setter.
     * </p>
     */
    private long submissionPhaseStatusId;

    /**
     * <p>
     * The submission phase end for this project.
     * </p>
     *
     * <p>
     * Can be any value. Has getter and setter.
     * </p>
     */
    private Date submissionPhaseEnd;

    /**
     * <p>
     * The screening phase end for this project.
     * </p>
     *
     * <p>
     * Can be any value. Has getter and setter.
     * </p>
     */
    private Date screeningPhaseEnd;

    /**
     * <p>
     * The appeals response phase end for this project.
     * </p>
     *
     * <p>
     * Can be any value. Has getter and setter.
     * </p>
     */
    private Date appealsResponsePhaseEnd;

    /**
     * <p>
     * Creates an instance of UserProjectParticipationData.
     * </p>
     */
    public UserProjectParticipationData() {
        // Empty
    }

    /**
     * <p>
     * Gets the project start date.
     * </p>
     *
     * @return the project start date.
     */
    public Date getProjectStartDate() {
        return projectStartDate;
    }

    /**
     * <p>
     * Sets the project start date.
     * </p>
     *
     * @param projectStartDate
     *            the project start date.
     */
    public void setProjectStartDate(Date projectStartDate) {
        this.projectStartDate = projectStartDate;
    }

    /**
     * <p>
     * Gets the date when the user registered for the project.
     * </p>
     *
     * @return the date when the user registered for the project.
     */
    public Date getUserRegistrationDate() {
        return userRegistrationDate;
    }

    /**
     * <p>
     * Sets the date when the user registered for the project.
     * </p>
     *
     * @param userRegistrationDate
     *            the date when the user registered for the project.
     */
    public void setUserRegistrationDate(Date userRegistrationDate) {
        this.userRegistrationDate = userRegistrationDate;
    }

    /**
     * <p>
     * Gets the final user score.
     * </p>
     *
     * @return the final user score.
     */
    public Double getUserScore() {
        return userScore;
    }

    /**
     * <p>
     * Sets the final user score.
     * </p>
     *
     * @param userScore
     *            the final user score.
     */
    public void setUserScore(Double userScore) {
        this.userScore = userScore;
    }

    /**
     * <p>
     * Gets the value indicating whether the user's submission passed review.
     * </p>
     *
     * @return the value indicating whether the user's submission passed review.
     */
    public boolean isPassedReview() {
        return passedReview;
    }

    /**
     * <p>
     * Sets the value indicating whether the user's submission passed review.
     * </p>
     *
     * @param passedReview
     *            the value indicating whether the user's submission passed review.
     */
    public void setPassedReview(boolean passedReview) {
        this.passedReview = passedReview;
    }

    /**
     * <p>
     * Gets the ID of the submission status.
     * </p>
     *
     * @return the ID of the submission status.
     */
    public Long getSubmissionStatusId() {
        return submissionStatusId;
    }

    /**
     * <p>
     * Sets the ID of the submission status.
     * </p>
     *
     * @param submissionStatusId
     *            the ID of the submission status.
     */
    public void setSubmissionStatusId(Long submissionStatusId) {
        this.submissionStatusId = submissionStatusId;
    }

    /**
     * <p>
     * Gets the ID of the submission phase status for this project.
     * </p>
     *
     * @return the ID of the submission phase status for this project.
     */
    public long getSubmissionPhaseStatusId() {
        return submissionPhaseStatusId;
    }

    /**
     * <p>
     * Sets the ID of the submission phase status for this project.
     * </p>
     *
     * @param submissionPhaseStatusId
     *            the ID of the submission phase status for this project.
     */
    public void setSubmissionPhaseStatusId(long submissionPhaseStatusId) {
        this.submissionPhaseStatusId = submissionPhaseStatusId;
    }

    /**
     * <p>
     * Gets the submission phase end for this project.
     * </p>
     *
     * @return the submission phase end for this project.
     */
    public Date getSubmissionPhaseEnd() {
        return submissionPhaseEnd;
    }

    /**
     * <p>
     * Sets the submission phase end for this project.
     * </p>
     *
     * @param submissionPhaseEnd
     *            the submission phase end for this project.
     */
    public void setSubmissionPhaseEnd(Date submissionPhaseEnd) {
        this.submissionPhaseEnd = submissionPhaseEnd;
    }

    /**
     * <p>
     * Gets the screening phase end for this project.
     * </p>
     *
     * @return the screening phase end for this project.
     */
    public Date getScreeningPhaseEnd() {
        return screeningPhaseEnd;
    }

    /**
     * <p>
     * Sets the screening phase end for this project.
     * </p>
     *
     * @param screeningPhaseEnd
     *            the screening phase end for this project.
     */
    public void setScreeningPhaseEnd(Date screeningPhaseEnd) {
        this.screeningPhaseEnd = screeningPhaseEnd;
    }

    /**
     * <p>
     * Gets the appeals response phase end for this project.
     * </p>
     *
     * @return the appeals response phase end for this project.
     */
    public Date getAppealsResponsePhaseEnd() {
        return appealsResponsePhaseEnd;
    }

    /**
     * <p>
     * Sets the appeals response phase end for this project.
     * </p>
     *
     * @param appealsResponsePhaseEnd
     *            the appeals response phase end for this project.
     */
    public void setAppealsResponsePhaseEnd(Date appealsResponsePhaseEnd) {
        this.appealsResponsePhaseEnd = appealsResponsePhaseEnd;
    }
}
