/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.utilities.cp.entities;

import java.io.Serializable;

/**
 * <p>
 * This class is a container for information about the contribution points associated with the given contest. It is a
 * simple JavaBean (POJO) that provides getters and setters for all private attributes and performs no argument
 * validation in the setters.
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> This class is mutable and not thread safe.
 * </p>
 *
 * @author vangavroche, sparemax
 * @version 1.0
 */
public class ProjectContestCPConfig implements Serializable {
    /**
     * The serial version ID.
     */
    private static final long serialVersionUID = -6209238658009383740L;

    /**
     * The ID of the contest. Can be any value. Has getter and setter.
     */
    private long contestId;

    /**
     * The original payment. Can be any value. Has getter and setter.
     */
    private OriginalPayment originalPayment;

    /**
     * The contribution points rate. Can be any value. Has getter and setter.
     */
    private double cpRate;

    /**
     * Creates an instance of ProjectContestCPConfig.
     */
    public ProjectContestCPConfig() {
        // Empty
    }

    /**
     * Gets the ID of the contest.
     *
     * @return the ID of the contest.
     */
    public long getContestId() {
        return contestId;
    }

    /**
     * Sets the ID of the contest.
     *
     * @param contestId
     *            the ID of the contest.
     */
    public void setContestId(long contestId) {
        this.contestId = contestId;
    }

    /**
     * Gets the original payment.
     *
     * @return the original payment.
     */
    public OriginalPayment getOriginalPayment() {
        return originalPayment;
    }

    /**
     * Sets the original payment.
     *
     * @param originalPayment
     *            the original payment.
     */
    public void setOriginalPayment(OriginalPayment originalPayment) {
        this.originalPayment = originalPayment;
    }

    /**
     * Gets contribution points rate.
     *
     * @return the contribution points rate.
     */
    public double getCpRate() {
        return cpRate;
    }

    /**
     * Sets contribution points rate.
     *
     * @param cpRate
     *            contribution points rate.
     */
    public void setCpRate(double cpRate) {
        this.cpRate = cpRate;
    }

    /**
     * Converts the object to a string.
     *
     * @return the string.
     */
    @Override
    public String toString() {
        return new StringBuilder().append(ProjectContestCPConfig.class.getName()).append("{")
            .append("contestId").append(":").append(contestId).append(", ")
            .append("originalPayment").append(":").append(originalPayment).append(", ")
            .append("cpRate").append(":").append(cpRate).append("}").toString();
    }
}
