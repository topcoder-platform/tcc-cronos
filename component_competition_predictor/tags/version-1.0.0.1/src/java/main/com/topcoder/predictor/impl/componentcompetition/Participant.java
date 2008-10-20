/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.predictor.impl.componentcompetition;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicReference;

/**
 * <p>
 * A bean that describes a participant in a component competition situation, including the participant's scores given as
 * the component goes through the review stages and various other pieces of information.
 * </p>
 *
 * <p>
 * It is Serializable and Cloneable.
 * </p>
 *
 * <p>
 * Thread-Safety: This class is mutable but thread-safe.
 * </p>
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public class Participant implements Serializable, Cloneable {
    /**
     * The max value for reliability.
     */
    private static final double MAX_RELIABILITY = 1.0;

    /**
     * The max value for score.
     */
    private static final double MAX_SCORE = 100.0;

    /**
     * Serialization version UID.
     */
    private static final long serialVersionUID = 1453148693517997404L;

    /**
     * Represents the ID of this participant, wrapped in an AtomicReference, so it can be accessed in an atomic,
     * thread-safe manner. The underlying value will be set in the setter, and accessed with the getter. The
     * AtomicReference instance will never be null. The underlying value can be any value. The AtomicReference instance
     * will never change. The underlying value accessed as stated in the Usage section.
     */
    private final AtomicReference<Integer> userId = new AtomicReference<Integer>();

    /**
     * Represents the rating of this participant, wrapped in an AtomicReference, so it can be accessed in an atomic,
     * thread-safe manner. The underlying value will be set in the setter, and accessed with the getter. The
     * AtomicReference instance will never be null. The underlying value can be either null or not negative. The
     * AtomicReference instance will never change. The underlying value accessed as stated in the Usage section.
     */
    private final AtomicReference<Integer> rating = new AtomicReference<Integer>();

    /**
     * Represents the reliability of this participant, wrapped in an AtomicReference, so it can be accessed in an
     * atomic, thread-safe manner. The underlying value will be set in the setter, and accessed with the getter. The
     * AtomicReference instance will never be null. The underlying value can be either null or 0.0 to 1.0. The
     * AtomicReference instance will never change. The underlying value accessed as stated in the Usage section.
     */
    private final AtomicReference<Double> reliability = new AtomicReference<Double>();

    /**
     * Represents the result of the screening of the component by this participant, wrapped in an AtomicReference, so it
     * can be accessed in an atomic, thread-safe manner. The underlying value will be set in the setter, and accessed
     * with the getter. The AtomicReference instance will never be null. The underlying value can be any value. The
     * AtomicReference instance will never change. The underlying value accessed as stated in the Usage section.
     */
    private final AtomicReference<String> autoScreeningResult = new AtomicReference<String>();

    /**
     * Represents the screening score of the component by this participant, wrapped in an AtomicReference, so it can be
     * accessed in an atomic, thread-safe manner. The underlying value will be set in the setter, and accessed with the
     * getter. The AtomicReference instance will never be null. The underlying value can be either null or 0.0 to
     * 100.00. The AtomicReference instance will never change. The underlying value accessed as stated in the Usage
     * section.
     */
    private final AtomicReference<Double> screeningScore = new AtomicReference<Double>();

    /**
     * Represents the flag indicating whether the component done by this participant passed screening, wrapped in an
     * AtomicReference, so it can be accessed in an atomic, thread-safe manner. The underlying value will be set in the
     * setter, and accessed with the getter. The AtomicReference instance will never be null. The underlying value can
     * be any value. The AtomicReference instance will never change. The underlying value accessed as stated in the
     * Usage section.
     */
    private final AtomicReference<Boolean> passedScreening = new AtomicReference<Boolean>();

    /**
     * Represents the score for this component by this participant before the appeals phase, wrapped in an
     * AtomicReference, so it can be accessed in an atomic, thread-safe manner. The underlying value will be set in the
     * setter, and accessed with the getter. The AtomicReference instance will never be null. The underlying value can
     * be either null or 0.0 to 100.00. The AtomicReference instance will never change. The underlying value accessed as
     * stated in the Usage section.
     */
    private final AtomicReference<Double> scoreBeforeAppeals = new AtomicReference<Double>();

    /**
     * Represents the score for this component by this participant after the appeals phase, wrapped in an
     * AtomicReference, so it can be accessed in an atomic, thread-safe manner. The underlying value will be set in the
     * setter, and accessed with the getter. The AtomicReference instance will never be null. The underlying value can
     * be either null or 0.0 to 100.00. The AtomicReference instance will never change. The underlying value accessed as
     * stated in the Usage section.
     */
    private final AtomicReference<Double> scoreAfterAppeals = new AtomicReference<Double>();

    /**
     * Represents the flag indicating whether the component done by this participant passed review, wrapped in an
     * AtomicReference, so it can be accessed in an atomic, thread-safe manner. The underlying value will be set in the
     * setter, and accessed with the getter. The AtomicReference instance will never be null. The underlying value can
     * be any value. The AtomicReference instance will never change. The underlying value accessed as stated in the
     * Usage section.
     */
    private final AtomicReference<Boolean> passedReview = new AtomicReference<Boolean>();

    /**
     * Represents the number of appeals instigated by this participant for the component, wrapped in an AtomicReference,
     * so it can be accessed in an atomic, thread-safe manner. The underlying value will be set in the setter, and
     * accessed with the getter. The AtomicReference instance will never be null. The underlying value can be either
     * null or not negative. If both it and successfulAppealCount have non-null values, then this value must be not less
     * than the successfulAppealCount's value. The AtomicReference instance will never change. The underlying value
     * accessed as stated in the Usage section.
     */
    private final AtomicReference<Integer> appealCount = new AtomicReference<Integer>();

    /**
     * Represents the number of successful appeals instigated by this participant for the component, wrapped in an
     * AtomicReference, so it can be accessed in an atomic, thread-safe manner. The underlying value will be set in the
     * setter, and accessed with the getter. The AtomicReference instance will never be null. The underlying value can
     * be either null or not negative. If both it and appealCount have non-null values, then this value must be not
     * greater than the appealCount's value. The AtomicReference instance will never change. The underlying value
     * accessed as stated in the Usage section.
     */
    private final AtomicReference<Integer> successfulAppealCount = new AtomicReference<Integer>();

    /**
     * Default empty constructor.
     */
    public Participant() {
    }

    /**
     * Gets the userId field value.
     *
     * @return the userId field value
     */
    public Integer getUserId() {
        return this.userId.get();
    }

    /**
     * Sets the userId field value.
     *
     * @param userId
     *            the userId field value
     */
    public void setUserId(Integer userId) {
        this.userId.set(userId);
    }

    /**
     * Gets the rating field value.
     *
     * @return the rating field value
     */
    public Integer getRating() {
        return this.rating.get();
    }

    /**
     * Sets the rating field value.
     *
     * @param rating
     *            the rating field value
     * @throws IllegalArgumentException -
     *             If rating is not null and rating.intValue < 0
     */
    public void setRating(Integer rating) {
        if (rating != null) {
            Helper.checkNotNegative(rating, "rating");
        }
        this.rating.set(rating);
    }

    /**
     * Gets the reliability field value.
     *
     * @return the reliability field value
     */
    public Double getReliability() {
        return this.reliability.get();
    }

    /**
     * Sets the reliability field value.
     *
     * @param reliability
     *            the reliability field value
     * @throws IllegalArgumentException -
     *             If reliability is not null and (reliability < 0.0 or reliability > 1.0)
     */
    public void setReliability(Double reliability) {
        if (reliability != null) {
            Helper.checkNotNegative(reliability, "reliability");
            if (reliability > MAX_RELIABILITY) {
                throw new IllegalArgumentException("The reliability should not be larger than "
                    + MAX_RELIABILITY + ".");
            }
        }
        this.reliability.set(reliability);
    }

    /**
     * Gets the autoScreeningResult field value.
     *
     * @return the autoScreeningResult field value
     */
    public String getAutoScreeningResult() {
        return this.autoScreeningResult.get();
    }

    /**
     * Sets the autoScreeningResult field value.
     *
     * @param autoScreeningResult
     *            the autoScreeningResult field value
     */
    public void setAutoScreeningResult(String autoScreeningResult) {
        this.autoScreeningResult.set(autoScreeningResult);
    }

    /**
     * Gets the screeningScore field value.
     *
     * @return the screeningScore field value
     */
    public Double getScreeningScore() {
        return this.screeningScore.get();
    }

    /**
     * Sets the screeningScore field value.
     *
     * @param screeningScore
     *            the screeningScore field value
     * @throws IllegalArgumentException -
     *             If screeningScore is not null and (screeningScore < 0.0 or screeningScore > 100.0)
     */
    public void setScreeningScore(Double screeningScore) {
        if (screeningScore != null) {
            Helper.checkNotNegative(screeningScore, "screeningScore");
            if (screeningScore > MAX_SCORE) {
                throw new IllegalArgumentException("The screeningScore should not be larger than " + MAX_SCORE + ".");
            }
        }
        this.screeningScore.set(screeningScore);
    }

    /**
     * Gets the passedScreening field value.
     *
     * @return the passedScreening field value
     */
    public Boolean getPassedScreening() {
        return this.passedScreening.get();
    }

    /**
     * Sets the passedScreening field value.
     *
     * @param passedScreening
     *            the passedScreening field value
     */
    public void setPassedScreening(Boolean passedScreening) {
        this.passedScreening.set(passedScreening);
    }

    /**
     * Gets the scoreBeforeAppeals field value.
     *
     * @return the scoreBeforeAppeals field value
     */
    public Double getScoreBeforeAppeals() {
        return this.scoreBeforeAppeals.get();
    }

    /**
     * Sets the scoreBeforeAppeals field value.
     *
     * @param scoreBeforeAppeals
     *            the scoreBeforeAppeals field value
     * @throws IllegalArgumentException -
     *             If scoreBeforeAppeals is not null and (scoreBeforeAppeals < 0.0 or scoreBeforeAppeals > 100.0)
     */
    public void setScoreBeforeAppeals(Double scoreBeforeAppeals) {
        if (scoreBeforeAppeals != null) {
            Helper.checkNotNegative(scoreBeforeAppeals, "scoreBeforeAppeals");
            if (scoreBeforeAppeals > MAX_SCORE) {
                throw new IllegalArgumentException("The scoreBeforeAppeals should not be larger than " + MAX_SCORE
                                + ".");
            }
        }
        this.scoreBeforeAppeals.set(scoreBeforeAppeals);
    }

    /**
     * Gets the scoreAfterAppeals field value.
     *
     * @return the scoreAfterAppeals field value
     */
    public Double getScoreAfterAppeals() {
        return this.scoreAfterAppeals.get();
    }

    /**
     * Sets the scoreAfterAppeals field value.
     *
     * @param scoreAfterAppeals
     *            the scoreAfterAppeals field value
     * @throws IllegalArgumentException -
     *             If scoreAfterAppeals is not null and (scoreAfterAppeals < 0.0 or scoreAfterAppeals > 100.0)
     */
    public void setScoreAfterAppeals(Double scoreAfterAppeals) {
        if (scoreAfterAppeals != null) {
            Helper.checkNotNegative(scoreAfterAppeals, "scoreAfterAppeals");
            if (scoreAfterAppeals > MAX_SCORE) {
                throw new IllegalArgumentException("The scoreAfterAppeals should not be larger than "
                    + MAX_SCORE + ".");
            }
        }
        this.scoreAfterAppeals.set(scoreAfterAppeals);
    }

    /**
     * Gets the passedReview field value.
     *
     * @return the passedReview field value
     */
    public Boolean getPassedReview() {
        return this.passedReview.get();
    }

    /**
     * Sets the passedReview field value.
     *
     * @param passedReview
     *            the passedReview field value
     */
    public void setPassedReview(Boolean passedReview) {
        this.passedReview.set(passedReview);
    }

    /**
     * Gets the appealCount field value.
     *
     * @return the appealCount field value
     */
    public Integer getAppealCount() {
        return this.appealCount.get();
    }

    /**
     * Sets the appealCount field value.
     *
     * @param appealCount
     *            the appealCount field value
     * @throws IllegalArgumentException
     *             If appealCount is not null and negative; Or if appealCount is not null, and the value of
     *             this.successfulAppealCount is not null, and appealCount < this.successfulAppealCount.get()
     */
    public void setAppealCount(Integer appealCount) {
        if (appealCount != null) {
            Helper.checkNotNegative(appealCount, "appealCount");
            if (this.successfulAppealCount.get() != null && appealCount < this.successfulAppealCount.get()) {
                throw new IllegalArgumentException(
                                "The successful appeal count should not be larger than appeal count.");
            }
        }
        this.appealCount.set(appealCount);
    }

    /**
     * Gets the successfulAppealCount field value.
     *
     * @return the successfulAppealCount field value
     */
    public Integer getSuccessfulAppealCount() {
        return this.successfulAppealCount.get();
    }

    /**
     * Sets the successfulAppealCount field value.
     *
     * @param successfulAppealCount
     *            the successfulAppealCount field value
     * @throws IllegalArgumentException
     *             If successfulAppealCount is not null and negative; Or if successfulAppealCount is not null, and the
     *             value of this.appealCount is not null, and successfulAppealCount > this.appealCount.get()
     */
    public void setSuccessfulAppealCount(Integer successfulAppealCount) {
        if (successfulAppealCount != null) {
            Helper.checkNotNegative(successfulAppealCount, "successfulAppealCount");
            if (appealCount.get() != null && appealCount.get() < successfulAppealCount) {
                throw new IllegalArgumentException(
                                "The successful appeal count should not be larger than appeal count.");
            }
        }
        this.successfulAppealCount.set(successfulAppealCount);
    }

    /**
     * Makes a deep copy of this participant.
     *
     * @return the clone of this participant.
     */
    public Object clone() {
        Participant p = new Participant();
        p.setAppealCount(this.getAppealCount());
        p.setAutoScreeningResult(this.getAutoScreeningResult());
        p.setPassedReview(this.getPassedReview());
        p.setPassedScreening(this.getPassedScreening());
        p.setRating(this.getRating());
        p.setReliability(this.getReliability());
        p.setScoreAfterAppeals(this.getScoreAfterAppeals());
        p.setScoreBeforeAppeals(this.getScoreBeforeAppeals());
        p.setScreeningScore(this.getScreeningScore());
        p.setSuccessfulAppealCount(this.getSuccessfulAppealCount());
        p.setUserId(this.getUserId());
        return p;
    }
}
