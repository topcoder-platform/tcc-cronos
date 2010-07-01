/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.facade.direct;

/**
 * <p>
 * This class is a container for contest schedule extension data. It is a simple JavaBean (POJO) that provides getters
 * and setters for all private attributes and performs no validation in the setters.
 * </p>
 *
 * <p>
 * <strong>Thread Safety:</strong> This class is mutable and not thread safe.
 * </p>
 *
 * @author saarixx, TCSDEVELOPER
 * @version 1.0
 */
public class ContestScheduleExtension {

    /**
     * <p>
     * The ID of the contest. Can be any value. Has getter and setter.
     * </p>
     */
    private long contestId;

    /**
     * <p>
     * The flag indicating whether the contest is studio contest or software competition. Can be any value. Has getter
     * and setter.
     * </p>
     */
    private boolean studio;

    /**
     * <p>
     * The extension duration for the registration phase in hours. Can be any value. Has getter and setter.
     * </p>
     */
    private Integer extendRegistrationBy;

    /**
     * <p>
     * The extension duration for the milestone phase in hours. Can be any value. Has getter and setter.
     * </p>
     */
    private Integer extendMilestoneBy;

    /**
     * <p>
     * The extension duration for the submission phase in hours. Can be any value. Has getter and setter.
     * </p>
     */
    private Integer extendSubmissionBy;

    /**
     * <p>
     * Creates an instance of ContestScheduleExtension.
     * </p>
     */
    public ContestScheduleExtension() {
        // Do nothing.
    }

    /**
     * <p>
     * Retrieves the ID of the contest.
     * </p>
     *
     * @return the ID of the contest
     */
    public long getContestId() {
        return contestId;
    }

    /**
     * <p>
     * Sets the ID of the contest.
     * </p>
     *
     * @param contestId
     *            the ID of the contest
     */
    public void setContestId(long contestId) {
        this.contestId = contestId;
    }

    /**
     * <p>
     * Retrieves the flag indicating whether the contest is studio contest or software competition.
     * </p>
     *
     * @return the flag indicating whether the contest is studio contest or software competition
     */
    public boolean isStudio() {
        return studio;
    }

    /**
     * <p>
     * Sets the flag indicating whether the contest is studio contest or software competition.
     * </p>
     *
     * @param studio
     *            the flag indicating whether the contest is studio contest or software competition
     */
    public void setStudio(boolean studio) {
        this.studio = studio;
    }

    /**
     * <p>
     * Retrieves the extension duration for the registration phase in hours.
     * </p>
     *
     * @return the extension duration for the registration phase in hours
     */
    public Integer getExtendRegistrationBy() {
        return extendRegistrationBy;
    }

    /**
     * <p>
     * Sets the extension duration for the registration phase in hours.
     * </p>
     *
     * @param extendRegistrationBy
     *            the extension duration for the registration phase in hours
     */
    public void setExtendRegistrationBy(Integer extendRegistrationBy) {
        this.extendRegistrationBy = extendRegistrationBy;
    }

    /**
     * <p>
     * Retrieves the extension duration for the milestone phase in hours.
     * </p>
     *
     * @return the extension duration for the milestone phase in hours
     */
    public Integer getExtendMilestoneBy() {
        return extendMilestoneBy;
    }

    /**
     * <p>
     * Sets the extension duration for the milestone phase in hours.
     * </p>
     *
     * @param extendMilestoneBy
     *            the extension duration for the milestone phase in hours
     */
    public void setExtendMilestoneBy(Integer extendMilestoneBy) {
        this.extendMilestoneBy = extendMilestoneBy;
    }

    /**
     * <p>
     * Retrieves the extension duration for the submission phase in hours.
     * </p>
     *
     * @return the extension duration for the submission phase in hours
     */
    public Integer getExtendSubmissionBy() {
        return extendSubmissionBy;
    }

    /**
     * <p>
     * Sets the extension duration for the submission phase in hours.
     * </p>
     *
     * @param extendSubmissionBy
     *            the extension duration for the submission phase in hours
     */
    public void setExtendSubmissionBy(Integer extendSubmissionBy) {
        this.extendSubmissionBy = extendSubmissionBy;
    }
}
