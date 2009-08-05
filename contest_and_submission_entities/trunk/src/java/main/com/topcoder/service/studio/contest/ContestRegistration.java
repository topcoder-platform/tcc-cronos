/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Date;

/**
 * <p>
 * Represents the entity class for db table <i>contest_registration</i>.
 * </p>
 *
 * <p>
 * Thread Safety: This entity is not thread safe since it is mutable.
 * </p>
 *
 * @author tushak, cyberjag
 * @version 1.0
 */
public class ContestRegistration implements Serializable {
    /**
     * Generated serial version id.
     */
    private static final long serialVersionUID = -7190430206777679577L;

    /**
     * Represents the Contest associated with this registration.
     */
    private Contest contest;

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
    public ContestRegistration() {
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

    /**
     * Compares this object with the passed object for equality. Only the
     * composite id will be compared.
     *
     * @param obj
     *            the {@code Object} to compare to this one
     * @return true if this object is equal to the other, {@code false} if not
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ContestRegistration) {
            ContestRegistration reg = (ContestRegistration) obj;
            return (contest != null && getContest().equals(reg.getContest())) && (userId == reg.getUserId());
        }
        return false;
    }

    /**
     * Overrides {@code Object.hashCode()} to provide a hash code consistent
     * with this class's {@link #equals(Object)} method.
     *
     * @return a hash code for this {@code ContestRegistration}
     */
    @Override
    public int hashCode() {
        return Helper.calculateHash(contest != null ? contest.getContestId() : null, userId);
    }


    /**
     * <p>
     * Returns the string representation.
     * </p>
     * @return the string representation
     */
    @Override
    public String toString() {
        return MessageFormat.format("ContestId: {0}, UserId: {1}, TermsOfUseId: {2}, createDate: {3}", contest
                .getContestId(), userId, termsOfUseId, createDate);
    }
}
