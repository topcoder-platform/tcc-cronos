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
 * It is the DTO class which is used to transfer contest general info data. The
 * information can be null or can be empty, therefore this check is not present
 * in the setters. It's the related to the equivalent ContestGeneralInfo entity.
 * </p>
 *
 * <p>
 * This class is not thread safe because it's highly mutable.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.3
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "contestGeneralInfoData", propOrder = { "goals", "targetAudience", "brandingGuidelines",
        "dislikedDesignsWebsites", "otherInstructions", "winningCriteria" })
public class ContestGeneralInfoData implements Serializable {
    /**
     * The contest goals. Can be any value. Has getter and setter.
     */
    private String goals;
    /**
     * The target audience. Can be any value. Has getter and setter.
     */
    private String targetAudience;
    /**
     * The branding guidelines. Can be any value. Has getter and setter.
     */
    private String brandingGuidelines;
    /**
     * The disliked designs websites. Can be any value. Has getter and setter.
     */
    private String dislikedDesignsWebsites;
    /**
     * The other instructions. Can be any value. Has getter and setter.
     */
    private String otherInstructions;
    /**
     * The winning criteria. Can be any value. Has getter and setter.
     */
    private String winningCriteria;

    /**
     * Creates an instance of ContestGeneralInfoData.
     *
     */
    public ContestGeneralInfoData() {
    }

    /**
     * Gets the value of the contest goals attribute.
     *
     * @return the value of the contest goals attribute
     */
    public String getGoals() {
        return this.goals;
    }

    /**
     * Sets the value of the contest goals attribute.
     *
     * @param goals the new value for the contest goals attribute.
     */
    public void setGoals(String goals) {
        this.goals = goals;
    }

    /**
     * Gets the value of the target audience attribute.
     *
     * @return the value of the target audience attribute
     */
    public String getTargetAudience() {
        return this.targetAudience;
    }

    /**
     * Sets the value of the target audience attribute.
     *
     * @param targetAudience the new value for the target audience attribute
     */
    public void setTargetAudience(String targetAudience) {
        this.targetAudience = targetAudience;
    }

    /**
     * Gets the value of the branding guidelines attribute.
     *
     * @return the value of the branding guidelines attribute
     */
    public String getBrandingGuidelines() {
        return this.brandingGuidelines;
    }

    /**
     * Sets the value of the branding guidelines attribute.
     *
     * @param brandingGuidelines the new value for the branding guidelines
     *        attribute
     */
    public void setBrandingGuidelines(String brandingGuidelines) {
        this.brandingGuidelines = brandingGuidelines;
    }

    /**
     * Gets the value of the disliked designs websites attribute.
     *
     * @return the value of the disliked designs websites attribute
     */
    public String getDislikedDesignsWebsites() {
        return this.dislikedDesignsWebsites;
    }

    /**
     * Sets the value of the disliked designs websites attribute.
     *
     * @param dislikedDesignsWebsites the new value for the disliked designs
     *        websites attribute
     */
    public void setDislikedDesignsWebsites(String dislikedDesignsWebsites) {
        this.dislikedDesignsWebsites = dislikedDesignsWebsites;
    }

    /**
     * Gets the value of the other instructions attribute.
     *
     * @return the value of the other instructions attribute
     */
    public String getOtherInstructions() {
        return this.otherInstructions;
    }

    /**
     * Sets the value of the other instructions attribute.
     *
     * @param otherInstructions the new value for the other instructions
     *        attribute
     */
    public void setOtherInstructions(String otherInstructions) {
        this.otherInstructions = otherInstructions;
    }

    /**
     * Gets the value of the winning criteria attribute.
     *
     * @return the value of the winning criteria attribute
     */
    public String getWinningCriteria() {
        return this.winningCriteria;
    }

    /**
     * Sets the value of the winning criteria attribute.
     *
     * @param winningCriteria the new value for the winning criteria attribute
     */
    public void setWinningCriteria(String winningCriteria) {
        this.winningCriteria = winningCriteria;
    }
}
