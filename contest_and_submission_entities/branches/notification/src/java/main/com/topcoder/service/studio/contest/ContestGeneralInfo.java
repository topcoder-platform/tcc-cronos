/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest;

import java.io.Serializable;

/**
 * <p>
 * Represents the entity class for db table <i>contest_general_info</i>.
 * </p>
 * <p>
 * It holds the attributes contest general info id,goals,target audience,branding guidelines,etc.
 * </p>
 * <p>
 * Thread Safety: This entity is not thread safe since it is mutable.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 * @since 1.2
 */
public class ContestGeneralInfo implements Serializable {

    /**
     * Generated serial version id.
     */
    private static final long serialVersionUID = 7631925707092970455L;

    /**
     * <p>
     * Represents the contest general info id.
     * </p>
     */
    private Long contestGeneralInfoId;

    /**
     * <p>
     * Represents the goals.
     * </p>
     */
    private String goals;

    /**
     * <p>
     * Represents the target audience.
     * </p>
     */
    private String targetAudience;

    /**
     * <p>
     * Represents the branding guidelines.
     * </p>
     */
    private String brandingGuidelines;

    /**
     * <p>
     * Represents the disliked designs websites.
     * </p>
     */
    private String dislikedDesignsWebsites;

    /**
     * <p>
     * Represents the other instructions.
     * </p>
     */
    private String otherInstructions;

    /**
     * <p>
     * Represents the winning criteria.
     * </p>
     */
    private String winningCriteria;

    /**
     * Default constructor.
     */
    public ContestGeneralInfo() {
    }

    /**
     * <p>
     * Returns the contest general info id.
     * </p>
     *
     * @return the contest general info id
     */
    public Long getContestGeneralInfoId() {
        return contestGeneralInfoId;
    }

    /**
     * <p>
     * Updates the contest general info id.
     * </p>
     *
     * @param contestGeneralInfoId
     *            the contest general info id
     */
    public void setContestGeneralInfoId(Long contestGeneralInfoId) {
        this.contestGeneralInfoId = contestGeneralInfoId;
    }

    /**
     * <p>
     * Returns the goals.
     * </p>
     *
     * @return the goals
     */
    public String getGoals() {
        return goals;
    }

    /**
     * <p>
     * Updates the goals.
     * </p>
     *
     * @param goals
     *            the goals
     */
    public void setGoals(String goals) {
        this.goals = goals;
    }

    /**
     * <p>
     * Returns the target audience.
     * </p>
     *
     * @return the target audience
     */
    public String getTargetAudience() {
        return targetAudience;
    }

    /**
     * <p>
     * Updates the target audience.
     * </p>
     *
     * @param targetAudience
     *            the target audience
     */
    public void setTargetAudience(String targetAudience) {
        this.targetAudience = targetAudience;
    }

    /**
     * <p>
     * Returns the branding guidelines.
     * </p>
     *
     * @return the branding guidelines
     */
    public String getBrandingGuidelines() {
        return brandingGuidelines;
    }

    /**
     * <p>
     * Updates the branding guidelines.
     * </p>
     *
     * @param brandingGuidelines
     *            the branding guidelines
     */
    public void setBrandingGuidelines(String brandingGuidelines) {
        this.brandingGuidelines = brandingGuidelines;
    }

    /**
     * <p>
     * Returns the disliked designs websites.
     * </p>
     *
     * @return the disliked designs websites
     */
    public String getDislikedDesignsWebsites() {
        return dislikedDesignsWebsites;
    }

    /**
     * <p>
     * Updates the disliked designs websites.
     * </p>
     *
     * @param dislikedDesignsWebsites
     *            the disliked designs websites
     */
    public void setDislikedDesignsWebsites(String dislikedDesignsWebsites) {
        this.dislikedDesignsWebsites = dislikedDesignsWebsites;
    }

    /**
     * <p>
     * Returns the other instructions.
     * </p>
     *
     * @return the other instructions.
     */
    public String getOtherInstructions() {
        return otherInstructions;
    }

    /**
     * <p>
     * Updates the other instructions.
     * </p>
     *
     * @param otherInstructions
     *            the other instructions.
     */
    public void setOtherInstructions(String otherInstructions) {
        this.otherInstructions = otherInstructions;
    }

    /**
     * <p>
     * Returns the winning criteria.
     * </p>
     *
     * @return the winning criteria.
     */
    public String getWinningCriteria() {
        return winningCriteria;
    }

    /**
     * <p>
     * Updates the winning criteria.
     * </p>
     *
     * @param winningCriteria
     *            the winning criteria.
     */
    public void setWinningCriteria(String winningCriteria) {
        this.winningCriteria = winningCriteria;
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
        if (obj instanceof ContestGeneralInfo) {
            return getContestGeneralInfoId() == ((ContestGeneralInfo) obj).getContestGeneralInfoId();
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
        return Helper.calculateHash(contestGeneralInfoId);
    }
}
