/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.submission;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.topcoder.service.studio.contest.Contest;
import com.topcoder.service.studio.contest.Helper;

/**
 * <p>
 * Represents the entity class for db table <i>prize</i>.
 * </p>
 *
 * <p>
 * Thread Safety: This entity is not thread safe since it is mutable.
 * </p>
 *
 * @author tushak, cyberjag, TCSDEVELOPER
 * @version 1.2
 */
public class Prize implements Serializable {
    /**
     * Generated serial version id.
     */
    private static final long serialVersionUID = -2442478545786444211L;

    /**
     * Represents the entity id.
     */
    private Long prizeId;

    /**
     * Represents the place to pay.
     */
    private Integer place;

    /**
     * Represents the prize amount.
     */
    private Double amount;

    /**
     * Represents the connected contests.
     */
    private Set<Contest> contests = new HashSet<Contest>();

    /**
     * Represents the connected submissions.
     */
    private Set<Submission> submissions = new HashSet<Submission>();;

    /**
     * Represents the type of prize.
     */
    private PrizeType type;

    /**
     * Represents the create date.
     */
    private Date createDate;

    /**
     * Default constructor.
     */
    public Prize() {
        // empty
    }

    /**
     * Returns the prizeId.
     *
     * @return the prizeId.
     */
    public Long getPrizeId() {
        return prizeId;
    }

    /**
     * Updates the prizeId with the specified value.
     *
     * @param prizeId
     *            the prizeId to set.
     */
    public void setPrizeId(Long prizeId) {
        this.prizeId = prizeId;
    }

    /**
     * Returns the place.
     *
     * @return the place.
     */
    public Integer getPlace() {
        return place;
    }

    /**
     * Updates the place with the specified value.
     *
     * @param place
     *            the place to set.
     */
    public void setPlace(Integer place) {
        this.place = place;
    }

    /**
     * Returns the amount.
     *
     * @return the amount.
     */
    public Double getAmount() {
        return amount;
    }

    /**
     * Updates the amount with the specified value.
     *
     * @param amount
     *            the amount to set.
     */
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    /**
     * Returns the contests.
     *
     * @return the contests.
     */
    public Set<Contest> getContests() {
        return contests;
    }

    /**
     * Updates the contests with the specified value.
     *
     * @param contests
     *            the contests to set.
     */
    public void setContests(Set<Contest> contests) {
        this.contests = contests;
    }

    /**
     * Returns the submissions.
     *
     * @return the submissions.
     */
    public Set<Submission> getSubmissions() {
        return submissions;
    }

    /**
     * Updates the submissions with the specified value.
     *
     * @param submissions
     *            the submissions to set.
     */
    public void setSubmissions(Set<Submission> submissions) {
        this.submissions = submissions;
    }

    /**
     * Returns the type.
     *
     * @return the type.
     */
    public PrizeType getType() {
        return type;
    }

    /**
     * Updates the type with the specified value.
     *
     * @param type
     *            the type to set.
     */
    public void setType(PrizeType type) {
        this.type = type;
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
     * Compares this object with the passed object for equality. Only the id will be compared.
     *
     * @param obj
     *            the {@code Object} to compare to this one
     * @return true if this object is equal to the other, {@code false} if not
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Prize) {
            Prize prize2 = (Prize) obj;
            return getPlace() == prize2.getPlace() &&
                    getType() == prize2.getType() &&
                    getContests() == prize2.getContests() &&
                    getSubmissions() == prize2.getSubmissions();
        }
        return false;
    }

    /**
     * Overrides {@code Object.hashCode()} to provide a hash code consistent with this class's
     * {@link #equals(Object)}} method.
     *
     * @return a hash code for this {@code Prize}
     */
    @Override
    public int hashCode() {
        return (prizeId + "-" + getPlace() + getContests() + "-" + getSubmissions()).hashCode();
    }
}
