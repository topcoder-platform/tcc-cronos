/*
 * Copyright (C) 2009-2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * Represents the entity class for db table <i>contest_multi_round_information</i>.
 * </p>
 * <p>
 * It holds the attributes contest multi round information id,milestone date,submitters locked between rounds,round one
 * introduction,etc.
 * </p>
 * <p>
 * Thread Safety: This entity is not thread safe since it is mutable.
 * </p>
 *
 * <p>
 *   Version 1.2.1 (Direct Submission Viewer Release 4) change notes:
 *   <ul>
 *     <li>Added {@link #generalFeedbackText} property with respective accessor/mutator methods.</li>
 *   </ul>
 * </p>
 *
 * @author TCSDESIGNER, isv
 * @version 1.2.1
 * @since 1.2
 */
public class ContestMultiRoundInformation implements Serializable {

    /**
     * <p>
     * Generated serial version id.
     * </p>
     */
    private static final long serialVersionUID = -876465857690561247L;

    /**
     * <p>
     * Represents the contest multi round information id.
     * </p>
     */
    private Long contestMultiRoundInformationId;

    /**
     * <p>
     * Represents the milestone date.
     * </p>
     */
    private Date milestoneDate;

    /**
     * <p>
     * Represents the submitters locked between rounds.
     * </p>
     */
    private Boolean submittersLockedBetweenRounds;

    /**
     * <p>
     * Represents the round one introduction.
     * </p>
     */
    private String roundOneIntroduction;

    /**
     * <p>
     * Represents the round two introduction.
     * </p>
     */
    private String roundTwoIntroduction;

    /**
     * <p>A <code>String</code> providing the text for general feedback on milestone round.</p>
     * 
     * @since 1.2.1
     */
    private String generalFeedbackText;

    /**
     * <p>
     * Default no-arg constructor.
     * </p>
     */
    public ContestMultiRoundInformation() {
    }

    /**
     * <p>
     * Gets the contest multi round information id.
     * </p>
     *
     * @return the contest multi round information id.
     */
    public Long getContestMultiRoundInformationId() {
        return contestMultiRoundInformationId;
    }

    /**
     * <p>
     * Sets the contest multi round information id.
     * </p>
     *
     * @param contestMultiRoundInformationId
     *            the contest multi round information id.
     */
    public void setContestMultiRoundInformationId(Long contestMultiRoundInformationId) {
        this.contestMultiRoundInformationId = contestMultiRoundInformationId;
    }

    /**
     * <p>
     * Gets the milestone date.
     * </p>
     *
     * @return the milestone date.
     */
    public Date getMilestoneDate() {
        return milestoneDate;
    }

    /**
     * <p>
     * Sets the milestone date.
     * </p>
     *
     * @param milestoneDate
     *            the milestone date.
     */
    public void setMilestoneDate(Date milestoneDate) {
        this.milestoneDate = milestoneDate;
    }

    /**
     * <p>
     * Gets the submitters locked between rounds.
     * </p>
     *
     * @return the submitters locked between rounds.
     */
    public Boolean isSubmittersLockedBetweenRounds() {
        return submittersLockedBetweenRounds;
    }

    /**
     * <p>
     * Sets the submitters locked between rounds.
     * </p>
     *
     * @param submittersLockedBetweenRounds
     *            the submitters locked between rounds.
     */
    public void setSubmittersLockedBetweenRounds(Boolean submittersLockedBetweenRounds) {
        this.submittersLockedBetweenRounds = submittersLockedBetweenRounds;
    }

    /**
     * <p>
     * Gets the round one introduction.
     * </p>
     *
     * @return the round one introduction.
     */
    public String getRoundOneIntroduction() {
        return roundOneIntroduction;
    }

    /**
     * <p>
     * Sets the round one introduction.
     * </p>
     *
     * @param roundOneIntroduction
     *            the round one introduction.
     */
    public void setRoundOneIntroduction(String roundOneIntroduction) {
        this.roundOneIntroduction = roundOneIntroduction;
    }

    /**
     * <p>
     * Gets the round two introduction.
     * </p>
     *
     * @return the round two introduction.
     */
    public String getRoundTwoIntroduction() {
        return roundTwoIntroduction;
    }

    /**
     * <p>
     * Sets the round two introduction.
     * </p>
     *
     * @param roundTwoIntroduction
     *            the round two introduction.
     */
    public void setRoundTwoIntroduction(String roundTwoIntroduction) {
        this.roundTwoIntroduction = roundTwoIntroduction;
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
        if (obj instanceof ContestMultiRoundInformation) {
            return getContestMultiRoundInformationId() == ((ContestMultiRoundInformation) obj)
                    .getContestMultiRoundInformationId();
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
        return Helper.calculateHash(contestMultiRoundInformationId);
    }

    /**
     * <p>Gets the text for general feedback on milestone round.</p>
     *
     * @return a <code>String</code> providing the text for general feedback on milestone round.
     * @since 1.2.1
     */
    public String getGeneralFeedbackText() {
        return this.generalFeedbackText;
    }

    /**
     * <p>Sets the text for general feedback on milestone round.</p>
     *
     * @param generalFeedbackText a <code>String</code> providing the text for general feedback on milestone round.
     * @since 1.2.1
     */
    public void setGeneralFeedbackText(String generalFeedbackText) {
        this.generalFeedbackText = generalFeedbackText;
    }
}
