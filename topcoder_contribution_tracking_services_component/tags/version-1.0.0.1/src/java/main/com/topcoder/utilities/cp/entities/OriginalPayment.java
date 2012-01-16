/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.utilities.cp.entities;

import java.io.Serializable;

/**
 * <p>
 * This class is a container for information about original payment. It is a simple JavaBean (POJO) that provides
 * getters and setters for all private attributes and performs no argument validation in the setters.
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> This class is mutable and not thread safe.
 * </p>
 *
 * @author vangavroche, sparemax
 * @version 1.0
 */
public class OriginalPayment implements Serializable {
    /**
     * The serial version ID.
     */
    private static final long serialVersionUID = -4526933870636290645L;

    /**
     * The ID of the contest. Can be any value. Has getter and setter.
     */
    private long contestId;

    /**
     * The 1st prize. Can be any value. Has getter and setter.
     */
    private double prize1st;

    /**
     * The 2nd prize. Can be any value. Has getter and setter.
     */
    private double prize2nd;

    /**
     * The 3rd prize. Can be any value. Has getter and setter.
     */
    private double prize3rd;

    /**
     * The 4th prize. Can be any value. Has getter and setter.
     */
    private double prize4th;

    /**
     * The 5th prize. Can be any value. Has getter and setter.
     */
    private double prize5th;

    /**
     * The copilot prize. Can be any value. Has getter and setter.
     */
    private double prizeCopilot;

    /**
     * The milestone prize. Can be any value. Has getter and setter.
     */
    private double prizeMilestone;

    /**
     * The specification review cost. Can be any value. Has getter and setter.
     */
    private double specReviewCost;

    /**
     * The review cost. Can be any value. Has getter and setter.
     */
    private double reviewCost;

    /**
     * Creates an instance of OriginalPayment.
     */
    public OriginalPayment() {
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
     * Gets the 1st prize.
     *
     * @return the 1st prize.
     */
    public double getPrize1st() {
        return prize1st;
    }

    /**
     * Sets the 1st prize.
     *
     * @param prize1st
     *            the 1st prize.
     */
    public void setPrize1st(double prize1st) {
        this.prize1st = prize1st;
    }

    /**
     * Gets the 2nd prize.
     *
     * @return the 2nd prize.
     */
    public double getPrize2nd() {
        return prize2nd;
    }

    /**
     * Sets the 2nd prize.
     *
     * @param prize2nd
     *            the 2nd prize.
     */
    public void setPrize2nd(double prize2nd) {
        this.prize2nd = prize2nd;
    }

    /**
     * Gets the 3rd prize.
     *
     * @return the 3rd prize.
     */
    public double getPrize3rd() {
        return prize3rd;
    }

    /**
     * Sets the 3rd prize.
     *
     * @param prize3rd
     *            the 3rd prize.
     */
    public void setPrize3rd(double prize3rd) {
        this.prize3rd = prize3rd;
    }

    /**
     * Gets the 4th prize.
     *
     * @return the 4th prize.
     */
    public double getPrize4th() {
        return prize4th;
    }

    /**
     * Sets the 4th prize.
     *
     * @param prize4th
     *            the 4th prize.
     */
    public void setPrize4th(double prize4th) {
        this.prize4th = prize4th;
    }

    /**
     * Gets the 5th prize.
     *
     * @return the 5th prize.
     */
    public double getPrize5th() {
        return prize5th;
    }

    /**
     * Sets the 5th prize.
     *
     * @param prize5th
     *            the 5th prize.
     */
    public void setPrize5th(double prize5th) {
        this.prize5th = prize5th;
    }

    /**
     * Gets the copilot prize.
     *
     * @return the copilot prize.
     */
    public double getPrizeCopilot() {
        return prizeCopilot;
    }

    /**
     * Sets the copilot prize.
     *
     * @param prizeCopilot
     *            the copilot prize.
     */
    public void setPrizeCopilot(double prizeCopilot) {
        this.prizeCopilot = prizeCopilot;
    }

    /**
     * Gets the copilot prize.
     *
     * @return the copilot prize.
     */
    public double getPrizeMilestone() {
        return prizeMilestone;
    }

    /**
     * Sets the milestone prize.
     *
     * @param prizeMilestone
     *            the milestone prize.
     */
    public void setPrizeMilestone(double prizeMilestone) {
        this.prizeMilestone = prizeMilestone;
    }

    /**
     * Gets the specification review cost.
     *
     * @return the specification review cost.
     */
    public double getSpecReviewCost() {
        return specReviewCost;
    }

    /**
     * Sets the specification review cost.
     *
     * @param specReviewCost
     *            the specification review cost.
     */
    public void setSpecReviewCost(double specReviewCost) {
        this.specReviewCost = specReviewCost;
    }

    /**
     * Gets the review cost.
     *
     * @return the review cost.
     */
    public double getReviewCost() {
        return reviewCost;
    }

    /**
     * Sets the review cost.
     *
     * @param reviewCost
     *            the review cost.
     */
    public void setReviewCost(double reviewCost) {
        this.reviewCost = reviewCost;
    }

    /**
     * Converts the object to a string.
     *
     * @return the string.
     */
    @Override
    public String toString() {
        return new StringBuilder().append(OriginalPayment.class.getName()).append("{").append("contestId")
            .append(":").append(contestId).append(", ").append("prize1st").append(":").append(prize1st).append(", ")
            .append("prize2nd").append(":").append(prize2nd).append(", ").append("prize3rd").append(":").append(
                prize3rd).append(", ").append("prize4th").append(":").append(prize4th).append(", ")
            .append("prize5th").append(":").append(prize5th).append(", ").append("prizeCopilot").append(":").append(
                prizeCopilot).append(", ").append("prizeMilestone").append(":").append(prizeMilestone).append(", ")
            .append("specReviewCost").append(":").append(specReviewCost).append(", ").append("reviewCost")
            .append(":").append(reviewCost).append("}").toString();
    }
}
