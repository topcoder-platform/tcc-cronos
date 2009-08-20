/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * It is the DTO class which is used to transfer contest multiround information.
 * The information can be null or can be empty, therefore this check is not
 * present in the setters. It's the related to the equivalent
 * ContestMultiRoundInformation entity.
 * </p>
 * <p>
 * This class is not thread safe because it's highly mutable.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.3
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "contestMultiRoundInformationData", propOrder = { "milestoneDate",
        "submittersLockedBetweenRounds", "roundOneIntroduction", "roundTwoIntroduction" })
public class ContestMultiRoundInformationData implements Serializable {
    /**
     * The milestone date. Can be any value. Has getter and setter.
     */
    private Date milestoneDate;
    /**
     * The flag indicating whether submitters are locked between rounds. Can be
     * any value. Has getter and setter.
     */
    private Boolean submittersLockedBetweenRounds;
    /**
     * The first round introduction. Can be any value. Has getter and setter.
     */
    private String roundOneIntroduction;
    /**
     * The second round introduction. Can be any value. Has getter and setter.
     */
    private String roundTwoIntroduction;

    /**
     * Creates an instance of ContestMultiRoundInformationData.
     *
     */
    public ContestMultiRoundInformationData() {
    }

    /**
     * Gets the value of the milestone date attribute.
     *
     * @return the value of the milestone date attribute
     */
    public Date getMilestoneDate() {
        return this.milestoneDate;
    }

    /**
     * Sets the value of the milestone date attribute.
     *
     * @param milestoneDate the new value for the milestone date attribute
     */
    public void setMilestoneDate(Date milestoneDate) {
        this.milestoneDate = milestoneDate;
    }

    /**
     * Gets the value of the flag indicating whether submitters are locked
     * between rounds.
     *
     * @return the value of the flag indicating whether submitters are locked
     *         between rounds.
     */
    public Boolean getSubmittersLockedBetweenRounds() {
        return this.submittersLockedBetweenRounds;
    }

    /**
     * Sets the value of the flag indicating whether submitters are locked
     * between rounds.
     *
     * @param submittersLockedBetweenRounds the new value for the flag
     *        indicating whether submitters are locked between rounds.
     */
    public void setSubmittersLockedBetweenRounds(Boolean submittersLockedBetweenRounds) {
        this.submittersLockedBetweenRounds = submittersLockedBetweenRounds;
    }

    /**
     * Gets the value of the first round introduction attribute.
     *
     * @return the value of the first round introduction attribute
     */
    public String getRoundOneIntroduction() {
        return this.roundOneIntroduction;
    }

    /**
     * Sets the value of the first round introduction attribute.
     *
     * @param roundOneIntroduction the new value for the first round
     *        introduction attribute
     */
    public void setRoundOneIntroduction(String roundOneIntroduction) {
        this.roundOneIntroduction = roundOneIntroduction;
    }

    /**
     * Gets the value of the second round introduction attribute.
     *
     * @return the value of the second round introduction attribute
     */
    public String getRoundTwoIntroduction() {
        return this.roundTwoIntroduction;
    }

    /**
     * Sets the value of the second round introduction attribute.
     *
     * @param roundTwoIntroduction the new value for the second round
     *        introduction attribute
     */
    public void setRoundTwoIntroduction(String roundTwoIntroduction) {
        this.roundTwoIntroduction = roundTwoIntroduction;
    }
}
