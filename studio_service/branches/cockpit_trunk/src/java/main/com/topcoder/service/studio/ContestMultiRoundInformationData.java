/*
 * Copyright (C) 2009-2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * <p>
 * It is the DTO class which is used to transfer contest multiround information.
 * The information can be null or can be empty, therefore this check is not
 * present in the setters. It's the related to the equivalent
 * ContestMultiRoundInformation entity.
 * </p>
 *
 * <p>
 * Changes in v1.4 (Studio Multi-Rounds Assembly - Launch Contest): Added id attribute 
 * with corresponding getter and setter. Changed milestoneDate type to XMLGregorianCalendar.
 * 'XmlType' was also updated to include this new field.
 * Default serialVersionUID was also added.
 * </p>
 *
 * <p>
 * Changes in v1.4 (Studio Multi-Rounds Assembly - Launch Contest): Added id attribute 
 * with corresponding getter and setter. Changed milestoneDate type to XMLGregorianCalendar.
 * 'XmlType' was also updated to include this new field.
 * Default serialVersionUID was also added.
 * </p>
 * <p>
 * Version 1.5.1 (Direct Submission Viewer Release 4 Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Added {@link #generalFeedbackText} property with respective accessor/mutator methods.</li>
 *   </ol>
 * </p>
 *
 * <p>
 * This class is not thread safe because it's highly mutable.
 * </p>
 *
 * @author pulky, isv
 * @version 1.5.1
 * @since 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "contestMultiRoundInformationData", propOrder = { "id", "milestoneDate",
        "submittersLockedBetweenRounds", "roundOneIntroduction", "roundTwoIntroduction", "generalFeedbackText" })
public class ContestMultiRoundInformationData implements Serializable {
    /**
     * Default serial version id.
     *
     * @since 1.4
     */
    private static final long serialVersionUID = 3075278936505863276L;

    /**
     * <p>
     * Represents the contest multi round information data Id.
     * </p>
     *
     * @since 1.4
     */
    private long id = -1;

    /**
     * The milestone date. Can be any value. Has getter and setter.
     */
    private XMLGregorianCalendar milestoneDate;
    
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
     * <p>A <code>String</code> providing the text for general feedback on milestone round.</p>
     *
     * @since 1.5.1
     */
    private String generalFeedbackText;

    /**
     * Creates an instance of ContestMultiRoundInformationData.
     *
     */
    public ContestMultiRoundInformationData() {
    }
    
    /**
     * Gets the contest multi round information data id
     *
     * @return the value of the contest multi round information data id
     * @since 1.4
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the contest multi round information data id
     *
     * @param id the value of the contest multi round information data id to set
     * @since 1.4
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets the value of the milestone date attribute.
     *
     * @return the value of the milestone date attribute
     */
    public XMLGregorianCalendar getMilestoneDate() {
        return this.milestoneDate;
    }

    /**
     * Sets the value of the milestone date attribute.
     *
     * @param milestoneDate the new value for the milestone date attribute
     */
    public void setMilestoneDate(XMLGregorianCalendar milestoneDate) {
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

    /**
     * <p>Gets the text for general feedback on milestone round.</p>
     *
     * @return a <code>String</code> providing the text for general feedback on milestone round.
     *     <li>Added {@link #generalFeedbackText} property with respective accessor/mutator methods.</li>
     */
    public String getGeneralFeedbackText() {
        return this.generalFeedbackText;
    }

    /**
     * <p>Sets the text for general feedback on milestone round.</p>
     *
     * @param generalFeedbackText a <code>String</code> providing the text for general feedback on milestone round.
     *     <li>Added {@link #generalFeedbackText} property with respective accessor/mutator methods.</li>
     */
    public void setGeneralFeedbackText(String generalFeedbackText) {
        this.generalFeedbackText = generalFeedbackText;
    }
}
