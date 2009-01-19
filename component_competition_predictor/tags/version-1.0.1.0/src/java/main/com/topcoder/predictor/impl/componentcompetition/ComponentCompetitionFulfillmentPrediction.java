/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.predictor.impl.componentcompetition;

import com.topcoder.predictor.Prediction;
import com.topcoder.predictor.Predictor;
import com.topcoder.predictor.Situation;

/**
 * <p>
 * A Prediction implementation that states the expected number of submissions that will pass review.
 * </p>
 *
 * <p>
 * Thread-Safety: This class has mutable fields expectedPassedReviewSubmissionCount and situation.
 * The user must synchronize access to their setters and other methods to use this class in thread safe manner.
 * </p>
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public class ComponentCompetitionFulfillmentPrediction implements Prediction {
    /**
     * Serialization version UID.
     */
    private static final long serialVersionUID = -7055922650338566322L;

    /**
     * Represents the expected number of submissions that pass review. It is set in the constructor. It is accessed in
     * the getter. It is used in the clone method. It will not be negative. It can be changed in the setter.
     */
    private double expectedPassedReviewSubmissionCount;

    /**
     * Represents the situation from which this prediction is made.
     *
     * It is set in the constructor. It is accessed in the getter. It is used in the clone method. It can be null.
     * It can be changed in the setter.
     */
    private ComponentCompetitionSituation situation;

    /**
     * Represents the predictor used to generate this prediction. It is set in the constructor. It is accessed in the
     * getter. It is used in the clone method. It can be null. Once set, it will not change.
     */
    private final Predictor<? extends Situation, ? extends Prediction> predictor;

    /**
     * Constructor with no args.
     */
    public ComponentCompetitionFulfillmentPrediction() {
        this.expectedPassedReviewSubmissionCount = 0;
        this.situation = null;
        this.predictor = null;
    }

    /**
     * Constructor accepting the variables.
     *
     * @param expectedSubmissionCount
     *            the expected number of submissions that pass review
     * @param situation
     *            the situation from which this prediction is made
     * @param predictor
     *            the predictor used to generate this prediction
     * @throws IllegalArgumentException
     *             If any object argument is null, or expectedSubmissionCount is negative
     */
    public ComponentCompetitionFulfillmentPrediction(double expectedSubmissionCount,
                    ComponentCompetitionSituation situation,
                    Predictor<? extends Situation, ? extends Prediction> predictor) {
        Helper.checkNotNegative(expectedSubmissionCount, "expectedSubmissionCount");
        Helper.checkNotNull(situation, "situation");
        Helper.checkNotNull(predictor, "predictor");
        this.expectedPassedReviewSubmissionCount = expectedSubmissionCount;
        this.situation = situation;
        this.predictor = predictor;
    }

    /**
     * Gets the expectedSubmissionCount field value.
     *
     * @return the expectedSubmissionCount field value
     */
    public double getExpectedPassedReviewSubmissionCount() {
        return this.expectedPassedReviewSubmissionCount;
    }

    /**
     * Sets the expectedPassedReviewSubmissionCount field value.
     *
     * @param expectedPassedReviewSubmissionCount the expectedSubmissionCount field value
     */
    public void setExpectedPassedReviewSubmissionCount(double expectedPassedReviewSubmissionCount) {
        this.expectedPassedReviewSubmissionCount = expectedPassedReviewSubmissionCount;
    }

    /**
     * Gets the situation field value.
     *
     * @return the situation field value
     */
    public ComponentCompetitionSituation getSituation() {
        return situation;
    }

    /**
     * Sets the situation field value.
     *
     * @param situation the situation field value
     */
    public void setSituation(ComponentCompetitionSituation situation) {
        this.situation = situation;
    }

    /**
     * Gets the predictor field value.
     *
     * @return the predictor field value
     */
    public Predictor<? extends Situation, ? extends Prediction> getPredictor() {
        return predictor;
    }

    /**
     * Makes a deep copy of this prediction.
     *
     * @return a deep copy of this prediction.
     */
    public Object clone() {
        if (situation == null || predictor == null) {
            return new ComponentCompetitionFulfillmentPrediction();
        }

        return new ComponentCompetitionFulfillmentPrediction(expectedPassedReviewSubmissionCount,
            (ComponentCompetitionSituation) situation.clone(),
            (Predictor<? extends Situation, ? extends Prediction>) predictor.clone());
    }
}
