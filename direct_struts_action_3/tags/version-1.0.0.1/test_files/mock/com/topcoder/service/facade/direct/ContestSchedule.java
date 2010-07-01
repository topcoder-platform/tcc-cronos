/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.facade.direct;

import java.io.Serializable;

import javax.xml.datatype.XMLGregorianCalendar;

/**
 * <p>
 * This class is a container for contest schedule data. It is a simple JavaBean (POJO) that provides getters and
 * setters for all private attributes and performs no validation in the setters.
 * </p>
 *
 * <p>
 * <strong>Thread Safety:</strong> This class is mutable and not thread safe.
 * </p>
 *
 * @author saarixx, TCSDEVELOPER
 * @version 1.0
 */
public class ContestSchedule implements Serializable {

    /**
     * <p>
     * Serial Version UID.
     * </p>
     */
    private static final long serialVersionUID = -8173138476326073664L;

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
     * The end of the registration phase for the contest. Can be any value. Has getter and setter.
     * </p>
     */
    private XMLGregorianCalendar registrationDeadline;

    /**
     * <p>
     * The duration of the registration phase in hours. Can be any value. Has getter and setter.
     * </p>
     */
    private int registrationDuration;

    /**
     * <p>
     * The end of the milestone phase for the contest. Is null when contest has only one round. Can be any value. Has
     * getter and setter.
     * </p>
     */
    private XMLGregorianCalendar milestoneDeadline;

    /**
     * <p>
     * The duration of the milestone phase in hours. Is null when contest has only one round. Can be any value. Has
     * getter and setter.
     * </p>
     */
    private Integer milestoneDuration;

    /**
     * <p>
     * The end of the submission phase for the contest. Can be any value. Has getter and setter.
     * </p>
     */
    private XMLGregorianCalendar submissionDeadline;

    /**
     * <p>
     * The duration of the submission phase in hours. Can be any value. Has getter and setter.
     * </p>
     */
    private int submissionDuration;

    /**
     * <p>
     * Creates an instance of ContestSchedule.
     * </p>
     */
    public ContestSchedule() {
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
     * Retrieves the end of the registration phase for the contest.
     * </p>
     *
     * @return the end of the registration phase for the contest
     */
    public XMLGregorianCalendar getRegistrationDeadline() {
        return registrationDeadline;
    }

    /**
     * <p>
     * Sets the end of the registration phase for the contest.
     * </p>
     *
     * @param registrationDeadline
     *            the end of the registration phase for the contest
     */
    public void setRegistrationDeadline(XMLGregorianCalendar registrationDeadline) {
        this.registrationDeadline = registrationDeadline;
    }

    /**
     * <p>
     * Retrieves the duration of the registration phase in hours.
     * </p>
     *
     * @return the duration of the registration phase in hours
     */
    public int getRegistrationDuration() {
        return registrationDuration;
    }

    /**
     * <p>
     * Sets the duration of the registration phase in hours.
     * </p>
     *
     * @param registrationDuration
     *            the duration of the registration phase in hours
     */
    public void setRegistrationDuration(int registrationDuration) {
        this.registrationDuration = registrationDuration;
    }

    /**
     * <p>
     * Retrieves the end of the milestone phase for the contest.
     * </p>
     *
     * @return the end of the milestone phase for the contest
     */
    public XMLGregorianCalendar getMilestoneDeadline() {
        return milestoneDeadline;
    }

    /**
     * <p>
     * Sets the end of the milestone phase for the contest.
     * </p>
     *
     * @param milestoneDeadline
     *            the end of the milestone phase for the contest
     */
    public void setMilestoneDeadline(XMLGregorianCalendar milestoneDeadline) {
        this.milestoneDeadline = milestoneDeadline;
    }

    /**
     * <p>
     * Retrieves the duration of the milestone phase in hours.
     * </p>
     *
     * @return the duration of the milestone phase in hours
     */
    public Integer getMilestoneDuration() {
        return milestoneDuration;
    }

    /**
     * <p>
     * Sets the duration of the milestone phase in hours.
     * </p>
     *
     * @param milestoneDuration
     *            the duration of the milestone phase in hours
     */
    public void setMilestoneDuration(Integer milestoneDuration) {
        this.milestoneDuration = milestoneDuration;
    }

    /**
     * <p>
     * Retrieves the end of the submission phase for the contest.
     * </p>
     *
     * @return the end of the submission phase for the contest
     */
    public XMLGregorianCalendar getSubmissionDeadline() {
        return submissionDeadline;
    }

    /**
     * <p>
     * Sets the end of the submission phase for the contest.
     * </p>
     *
     * @param submissionDeadline
     *            the end of the submission phase for the contest
     */
    public void setSubmissionDeadline(XMLGregorianCalendar submissionDeadline) {
        this.submissionDeadline = submissionDeadline;
    }

    /**
     * <p>
     * Retrieves the duration of the submission phase in hours.
     * </p>
     *
     * @return the duration of the submission phase in hours
     */
    public int getSubmissionDuration() {
        return submissionDuration;
    }

    /**
     * <p>
     * Sets the duration of the submission phase in hours.
     * </p>
     *
     * @param submissionDuration
     *            the duration of the submission phase in hours
     */
    public void setSubmissionDuration(int submissionDuration) {
        this.submissionDuration = submissionDuration;
    }
}
