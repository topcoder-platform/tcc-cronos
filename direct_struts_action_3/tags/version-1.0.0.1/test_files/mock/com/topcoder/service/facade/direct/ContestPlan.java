/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.facade.direct;

import java.io.Serializable;

import javax.xml.datatype.XMLGregorianCalendar;

/**
 * <p>
 * This class is a container for contest plan data. It is a simple JavaBean (POJO) that provides getters and setters
 * for all private attributes and performs no validation in the setters.
 * </p>
 *
 * <p>
 * <strong>Thread Safety:</strong> This class is mutable and not thread safe.
 * </p>
 *
 * @author saarixx, TCSDEVELOPER
 * @version 1.0
 */
public class ContestPlan implements Serializable {

    /**
     * <p>
     * Serial Version UID.
     * </p>
     */
    private static final long serialVersionUID = -7437440219118820560L;

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
     * The name of the contest. Can be any value. Has getter and setter.
     * </p>
     */
    private String name;

    /**
     * <p>
     * The start date of the contest. Can be any value. Has getter and setter.
     * </p>
     */
    private XMLGregorianCalendar startDate;

    /**
     * <p>
     * The end date of the contest. Can be any value. Has getter and setter.
     * </p>
     */
    private XMLGregorianCalendar endDate;

    /**
     * <p>
     * Creates an instance of ContestPlan.
     * </p>
     */
    public ContestPlan() {
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
     * Retrieves the name of the contest.
     * </p>
     *
     * @return the name of the contest
     */
    public String getName() {
        return name;
    }

    /**
     * <p>
     * Sets the name of the contest.
     * </p>
     *
     * @param name
     *            the name of the contest
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * <p>
     * Retrieves the start date of the contest.
     * </p>
     *
     * @return the start date of the contest
     */
    public XMLGregorianCalendar getStartDate() {
        return startDate;
    }

    /**
     * <p>
     * Sets the start date of the contest.
     * </p>
     *
     * @param startDate
     *            the start date of the contest
     */
    public void setStartDate(XMLGregorianCalendar startDate) {
        this.startDate = startDate;
    }

    /**
     * <p>
     * Retrieves the end date of the contest.
     * </p>
     *
     * @return the end date of the contest
     */
    public XMLGregorianCalendar getEndDate() {
        return endDate;
    }

    /**
     * <p>
     * Sets the end date of the contest.
     * </p>
     *
     * @param endDate
     *            the end date of the contest
     */
    public void setEndDate(XMLGregorianCalendar endDate) {
        this.endDate = endDate;
    }
}
