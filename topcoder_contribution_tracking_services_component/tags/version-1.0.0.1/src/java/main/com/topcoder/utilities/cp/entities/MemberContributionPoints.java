/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.utilities.cp.entities;

import java.io.Serializable;


/**
 * <p>
 * This class is a container for information about member contribution points. It is a simple JavaBean (POJO) that
 * provides getters and setters for all private attributes and performs no argument validation in the setters.
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> This class is mutable and not thread safe.
 * </p>
 *
 * @author vangavroche, sparemax
 * @version 1.0
 */
public class MemberContributionPoints implements Serializable {
    /**
     * The serial version ID.
     */
    private static final long serialVersionUID = 6585818936963975140L;

    /**
     * The ID of the member contribution points. Can be any value. Has getter and setter.
     */
    private long id;

    /**
     * The ID of the user. Can be any value. Has getter and setter.
     */
    private long userId;

    /**
     * The ID of the contest id. Can be any value. Has getter and setter.
     */
    private long contestId;

    /**
     * The contribution points. Can be any value. Has getter and setter.
     */
    private double contributionPoints;

    /**
     * The contribution type. Can be any value. Has getter and setter.
     */
    private String contributionType;

    /**
     * Creates an instance of MemberContributionPoints.
     */
    public MemberContributionPoints() {
        // Empty
    }

    /**
     * Gets the ID of the member contribution points.
     *
     * @return the ID of the member contribution points.
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the ID of the member contribution points.
     *
     * @param id
     *            the ID of the member contribution points.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets the ID of the user.
     *
     * @return the ID of the user.
     */
    public long getUserId() {
        return userId;
    }

    /**
     * Sets the ID of the user.
     *
     * @param userId
     *            the ID of the user.
     */
    public void setUserId(long userId) {
        this.userId = userId;
    }

    /**
     * Gets the ID of the contest id.
     *
     * @return the ID of the contest id.
     */
    public long getContestId() {
        return contestId;
    }

    /**
     * Sets the ID of the contest id.
     *
     * @param contestId
     *            the ID of the contest id.
     */
    public void setContestId(long contestId) {
        this.contestId = contestId;
    }

    /**
     * Gets the contribution points.
     *
     * @return the contribution points.
     */
    public double getContributionPoints() {
        return contributionPoints;
    }

    /**
     * Sets the contribution points.
     *
     * @param contributionPoints
     *            the contribution points.
     */
    public void setContributionPoints(double contributionPoints) {
        this.contributionPoints = contributionPoints;
    }

    /**
     * Gets the contribution type.
     *
     * @return the contribution type.
     */
    public String getContributionType() {
        return contributionType;
    }

    /**
     * Sets the contribution type.
     *
     * @param contributionType
     *            the contribution type.
     */
    public void setContributionType(String contributionType) {
        this.contributionType = contributionType;
    }

    /**
     * Converts the object to a string.
     *
     * @return the string.
     */
    @Override
    public String toString() {
        return new StringBuilder().append(MemberContributionPoints.class.getName()).append("{")
            .append("id").append(":").append(id).append(", ")
            .append("userId").append(":").append(userId).append(", ")
            .append("contestId").append(":").append(contestId).append(", ")
            .append("contributionPoints").append(":").append(contributionPoints).append(", ")
            .append("contributionType").append(":").append(contributionType).append("}").toString();
    }
}
