/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Date;

/**
 * <p>
 * Represents DTO for contest registration data.
 * </p>
 *
 * <p>
 * Thread Safety: This entity is not thread safe since it is mutable.
 * </p>
 *
 * @version 1.0
 */
public class ContestRegistrationData implements Serializable {
    /**
     * Generated serial version id.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Represents the Contest associated with this registration.
     */
    private long contestId;

    /**
     * Represents the user id.
     */
    private Long userId;

    /**
     * Represents the terms of use id.
     */
    private Long termsOfUseId;

    /**
     * Represents the create date.
     */
    private Date createDate;

    /**
     * Default constructor.
     */
    public ContestRegistrationData() {
        // empty
    }

    /**
     * Returns the contest.
     *
     * @return the contest.
     */
    public long getContestId() {
        return contestId;
    }

    /**
     * Updates the contest with the specified value.
     *
     * @param contest
     *            the contest to set.
     */
    public void setContest(long contestId) {
        this.contestId = contestId;
    }

    /**
     * Returns the userId.
     *
     * @return the userId.
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * Updates the userId with the specified value.
     *
     * @param userId
     *            the userId to set.
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * Returns the termsOfUseId.
     *
     * @return the termsOfUseId.
     */
    public Long getTermsOfUseId() {
        return termsOfUseId;
    }

    /**
     * Updates the termsOfUseId with the specified value.
     *
     * @param termsOfUseId
     *            the termsOfUseId to set.
     */
    public void setTermsOfUseId(Long termsOfUseId) {
        this.termsOfUseId = termsOfUseId;
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

  
}
