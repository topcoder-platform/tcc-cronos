/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Represents the completed contest data. A completed contest would have a few ranked (prized) submissions. It might
 * also have few additionally purchased submission too.
 * </p>
 *
 * @author shailendra_80
 * @version 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "completedContestData", propOrder = { "contestId", "submissions" })
public class CompletedContestData implements Serializable {

    /**
     * Default serial version id.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Contest Id of the completed contest.
     */
    private long contestId;

    /**
     * Submission Data of the completed contest.
     */
    private SubmissionPaymentData[] submissions;

    /**
     * <p>
     * Gets the contest identifier of this completed contest.
     * </p>
     *
     * @return the contest identifier of this completed contest.
     */
    public long getContestId() {
        return this.contestId;
    }

    /**
     * <p>
     * Sets the contest identifier of this completed contest.
     * </p>
     *
     * @param contestId
     *            the contest identifier of this completed contest.
     */
    public void setContestId(long contestId) {
        this.contestId = contestId;
    }

    /**
     * <p>
     * Gets the submissions data of this completed contest.
     * </p>
     *
     * @return submissions data of this completed contest.
     */
    public SubmissionPaymentData[] getSubmissions() {
        return this.submissions;
    }

    /**
     * <p>
     * Sets the submissions data of this completed contest.
     * </p>
     *
     * @param submissions
     *            submissions data of this completed contest.
     */
    public void setSubmissions(SubmissionPaymentData[] submissions) {
        this.submissions = submissions;
    }

}
