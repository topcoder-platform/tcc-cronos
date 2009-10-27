/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.submission;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.topcoder.service.studio.contest.Helper;

/**
 * <p>
 * Represents the entity class for db table <i>contest_milestone_prize</i>.
 * </p>
 * <p>
 * It holds the attributes milestone prize id,create date,amount,number of submissions,etc.
 * </p>
 * <p>
 * Thread Safety: This entity is not thread safe since it is mutable.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 * @since 1.2
 */
public class MilestonePrize implements Serializable {

    /**
     * <p>
     * Generated serial version id.
     * </p>
     */
    private static final long serialVersionUID = 42154558470144366L;

    /**
     * <p>
     * Represents the milestone prize id.
     * </p>
     */
    private Long milestonePrizeId;

    /**
     * <p>
     * Represents the create date.
     * </p>
     */
    private Date createDate;

    /**
     * <p>
     * Represents the amount.
     * </p>
     */
    private Double amount = 0.0;

    /**
     * <p>
     * Represents the number of submissions.
     * </p>
     */
    private Integer numberOfSubmissions;

    /**
     * <p>
     * Represents the submissions.
     * </p>
     */
    private Set<Submission> submissions = new HashSet<Submission>();

    /**
     * <p>
     * Represents the type.
     * </p>
     */
    private PrizeType type;

    /**
     * <p>
     * Default no-arg constructor.
     * </p>
     */
    public MilestonePrize() {
    }

    /**
     * <p>
     * Gets the milestone prize id.
     * </p>
     *
     * @return the milestone prize id.
     */
    public Long getMilestonePrizeId() {
        return milestonePrizeId;
    }

    /**
     * <p>
     * Sets the milestone prize id.
     * </p>
     *
     * @param milestonePrizeId
     *            the milestone prize id.
     */
    public void setMilestonePrizeId(Long milestonePrizeId) {
        this.milestonePrizeId = milestonePrizeId;
    }

    /**
     * <p>
     * Gets the create date.
     * </p>
     *
     * @return the create date.
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * <p>
     * Sets the create date.
     * </p>
     *
     * @param createDate
     *            the create date
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * <p>
     * Gets the amount.
     * </p>
     *
     * @return the amount.
     */
    public Double getAmount() {
        return amount;
    }

    /**
     * <p>
     * Sets the amount.
     * </p>
     *
     * @param amount
     *            the amount.
     */
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    /**
     * <p>
     * Gets the number of submissions.
     * </p>
     *
     * @return the number of submissions.
     */
    public Integer getNumberOfSubmissions() {
        return numberOfSubmissions;
    }

    /**
     * <p>
     * Sets the number of submissions.
     * </p>
     *
     * @param numberOfSubmissions
     *            the number of submissions.
     */
    public void setNumberOfSubmissions(Integer numberOfSubmissions) {
        this.numberOfSubmissions = numberOfSubmissions;
    }

    /**
     * <p>
     * Gets the submissions.
     * </p>
     *
     * @return the submissions.
     */
    public Set<Submission> getSubmissions() {
        return submissions;
    }

    /**
     * <p>
     * Sets the submissions.
     * </p>
     *
     * @param submissions
     *            the submissions.
     */
    public void setSubmissions(Set<Submission> submissions) {
        this.submissions = submissions;
    }

    /**
     * <p>
     * Gets the type.
     * </p>
     *
     * @return the type.
     */
    public PrizeType getType() {
        return type;
    }

    /**
     * <p>
     * Sets the type.
     * </p>
     *
     * @param type
     *            the type.
     */
    public void setType(PrizeType type) {
        this.type = type;
    }

    /**
     * <p>
     * Compares this object with the passed object for equality. Only the entity id will be compared.
     * </p>
     *
     * @param obj
     *            the obj to compare to this one
     * @return true if this object is equal to the other, false otherwise
     */
    @Override
    public synchronized boolean equals(Object obj) {
        if (obj instanceof MilestonePrize) {
            return getMilestonePrizeId() == ((MilestonePrize) obj).getMilestonePrizeId();
        }
        return false;
    }

    /**
     * <p>
     * Overrides {@code Object.hashCode()} to provide a hash code consistent with this class's {@link #equals(Object)}}
     * method.
     * </p>
     *
     * @return a hash code for this entity
     */
    @Override
    public int hashCode() {
        return Helper.calculateHash(milestonePrizeId);
    }
}
