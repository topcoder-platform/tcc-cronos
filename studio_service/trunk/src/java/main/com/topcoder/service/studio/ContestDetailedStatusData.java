/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * /**
 * <p>
 * It is the DTO class which is used to transfer contest detailed status data. The
 * information can be null or can be empty, therefore this check is not present
 * in the setters. It's the related to the equivalent ContestDetailedStatus entity.
 * </p>
 * 
 * <p>
 * This class is not thread safe because it's highly mutable
 * </p>
 * 
 * @author superZZ
 * @version 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "contestDetailedStatusData", propOrder = { "contestDetailedStatusId", "contestStatus",
        "description", "contest" })
public class ContestDetailedStatusData implements Serializable {
    /**
     * <p>
     * Represents the contestDetailedStatus Id
     * </p>
     */
    private long contestDetailedStatusId = -1;

    /**
     * <p>
     * Represents the contest status
     * </p>
     */
    private ContestStatusData contestStatus;

    /**
     * <p>
     * Represents the contest.
     * </p>
     */
    private ContestData contest;

    /**
     * <p>
     * Represents the description
     * </p>
     */
    private String description;

    /**
     * <p>
     * This is the default constructor. It does nothing.
     * </p>
     */
    public ContestDetailedStatusData() {
    }

    /**
     * <p>
     * Return the contestDetailedStatusId
     * </p>
     * 
     * @return the contestDetailedStatusId
     */
    public long getContestDetailedStatusId() {
        return contestDetailedStatusId;
    }

    /**
     * <p>
     * Set the contestDetailedStatusId
     * </p>
     * 
     * @param contestDetailedStatusId
     *            the contestDetailedStatusId to set
     */
    public void setContestDetailedStatusId(long contestDetailedStatusId) {
        this.contestDetailedStatusId = contestDetailedStatusId;
    }

    /**
     * <p>
     * Return the contestStatus
     * </p>
     * 
     * @return the contestStatus
     */
    public ContestStatusData getContestStatusData() {
        return contestStatus;
    }

    /**
     * <p>
     * Set the contestStatus
     * </p>
     * 
     * @param contestStatus
     *            the contestStatus to set
     */
    public void setContestStatusData(ContestStatusData contestStatus) {
        this.contestStatus = contestStatus;
    }

    /**
     * <p>
     * Return the contest.
     * </p>
     * 
     * @return the contest.
     */
    public ContestData getContestData() {
        return contest;
    }

    /**
     * <p>
     * Set the contest.
     * </p>
     * 
     * @param contest
     *            the contest to set.
     */
    public void setContestData(ContestData contest) {
        this.contest = contest;
    }

    /**
     * <p>
     * Return the description
     * </p>
     * 
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * <p>
     * Set the description
     * </p>
     * 
     * @param description
     *            the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
