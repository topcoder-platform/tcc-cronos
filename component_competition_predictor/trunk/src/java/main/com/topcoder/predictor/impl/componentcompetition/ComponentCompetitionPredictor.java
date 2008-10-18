/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.predictor.impl.componentcompetition;

import com.topcoder.predictor.Predictor;

import java.util.Set;
import java.util.HashSet;
import java.util.List;

/**
 * <p>
 * A simple predictor implementation that uses a ComponentCompetitionSituation to predict how many submissions will pass
 * review, which it will provide in the form of a ComponentCompetitionFulfillmentPrediction. It allows for no training,
 * and has no capabilities. Specifically, any attempt to add capabilities will be ignored. The capability methods are
 * basically disabled, where setting has no effect, and retrieving will result in no results.
 * </p>
 *
 * <p>
 * Usage:
 * <pre>
 * // Create a component competition situation (with just the relevant fields)
 * ComponentCompetitionSituation situation = new ComponentCompetitionSituation();
 * situation.setPostingDate(new Date(100000));
 * situation.setEndDate(new Date(200000));
 * situation.setPrize(800.0);
 * List&lt;Participant&gt; participants = new ArrayList&lt;Participant&gt;();
 * Participant participant1 = new Participant();
 * participant1.setReliability(0.0);
 * participants.add(participant1);
 * Participant participant2 = new Participant();
 * participant2.setReliability(1.0);
 * participants.add(participant2);
 * Participant participant3 = new Participant();
 * participant3.setReliability(0.8);
 * participants.add(participant3);
 * Participant participant4 = new Participant();
 * participant4.setReliability(0.6);
 * participants.add(participant4);
 * situation.setParticipants(participants);
 *
 * // Create a component competition predictor and predict
 * // the expected number of submissions to pass review
 * ComponentCompetitionPredictor predictor = new ComponentCompetitionPredictor();
 * ComponentCompetitionFulfillmentPrediction prediction = predictor.predict(situation);
 * // The prediction's expectedPassedReviewSubmissionCount will be 2.4,
 * // as it predicts that there will be 2.4 submissions for this competition,
 * // this is sum of the reliabilities
 * assertEquals(&quot;The prediction is incorrect.&quot;, 2.4, prediction.getExpectedPassedReviewSubmissionCount());
 * </pre>
 *
 * </p>
 *
 * <p>
 * Thread-Safety: This class is immutable and thread-safe.
 * </p>
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public class ComponentCompetitionPredictor implements
                Predictor<ComponentCompetitionSituation, ComponentCompetitionFulfillmentPrediction> {
    /**
     * Default empty constructor.
     */
    public ComponentCompetitionPredictor() {
    }

    /**
     * Retrieve the situation name (full class name), which can be processed by this Predictor. This data is needed to
     * find the predictors by their signatures (situationName and predictionName).
     *
     * @return the class name of the situation that can be used with this predictor
     */
    public String getSituationName() {
        return ComponentCompetitionSituation.class.getName();
    }

    /**
     * Retrieve the prediction name (full class name), which will be produced by this Predictor. This data is needed to
     * find the predictors by their signatures (situationName and predictionName).
     *
     * @return the name of the prediction that will be provided by this predictor
     */
    public String getPredictionName() {
        return ComponentCompetitionFulfillmentPrediction.class.getName();
    }

    /**
     * Retrieve the capability of the full training for this Predictor. It always returns false.
     *
     * @return true - if the full training is supported by this Predictor, false - otherwise. It currently always return
     *         false.
     */
    public boolean isFullTrainingSupported() {
        return false;
    }

    /**
     * Retrieve the capability of the incremental training for this Predictor. It always returns false.
     *
     * @return true - if the incremental training is supported by this Predictor, false - otherwise. It currently always
     *         returns false.
     */
    public boolean isIncrementalTrainingSupported() {
        return false;
    }

    /**
     * Retrieve the state of this Predictor - whether it is ready for performing predictions (true value is returned),
     * or not ready - like not trained yet (false value is returned). It always returns true.
     *
     * @return true - if the current Predictor instance is ready to perform "predict" method, false - otherwise. It
     *         always returns true.
     */
    public boolean isReadyToOperate() {
        return true;
    }

    /**
     * Add the custom capability value to the Predictor for the given key. If the capability with the same key is
     * present, then the old value will be replaced. At this time, this method will have no effect, as capabilties are
     * disabled.
     *
     * @param key
     *            the key of custom capability to be added. It can not be null or empty string.
     * @param value
     *            the value of the custom capability to be added. It can not be null or empty string.
     * @throws IllegalArgumentException
     *             if any argument is null or empty string.
     */
    public void putCapability(String key, String value) {
        Helper.checkNotNullNotEmpty(key, "key");
        Helper.checkNotNullNotEmpty(value, "value");
    }

    /**
     * Retrieve the custom capability value from the Predictor for the given key. At this time, this method will have no
     * effect, as capabilties are disabled.
     *
     * @param key
     *            the key of the custom capability to be retrieved. It can not be null or empty string.
     * @return null value
     * @throws IllegalArgumentException
     *             if argument is null or empty string.
     */
    public String getCapability(String key) {
        Helper.checkNotNullNotEmpty(key, "key");
        return null;
    }

    /**
     * Retrieve all the keys of the custom capabilities for the Predictor. At this time, this method will have no
     * effect, as capabilties are disabled.
     *
     * @return an empty set
     */
    public Set<String> getCapabilityKeys() {
        return new HashSet<String>();
    }

    /**
     * Delete the custom capability value from the Predictor for the given key. At this time, this method will have no
     * effect, as capabilties are disabled.
     *
     * @param key
     *            the key of the custom capability to be removed. It can not be null or empty string.
     * @throws IllegalArgumentException
     *             if argument is null or empty string.
     */
    public void removeCapability(String key) {
        Helper.checkNotNullNotEmpty(key, "key");
    }

    /**
     * Remove all the custom capabilities from the Predictor. At this time, this method will have no effect, as
     * capabilties are disabled.
     */
    public void clearCapabilities() {
        // empty
    }

    /**
     * Execute full training on the Predictor by providing the list of input situations.
     *
     * @param situations
     *            the situations used to train
     * @throws IllegalArgumentException
     *             if argument is null or empty list, or the list has null elements.
     */
    public void performFullTraining(List<ComponentCompetitionSituation> situations) {
        Helper.checkNotNullNotEmpty(situations, "situations");
        Helper.checkList(situations, "situations");
    }

    /**
     * Execute full training on the Predictor by providing the list of input situations and list of related output
     * predictions. This method can be used for some sophisticated algorithms - like Fuzzy logic.
     *
     * @param situations
     *            the situations used to train
     * @param predictions
     *            the predictions used to train
     * @throws IllegalArgumentException
     *             if any argument is null or empty list, or if any list argument has null elements; or if size of
     *             situations list is not equal to size of predictions list.
     */
    public void performFullTraining(List<ComponentCompetitionSituation> situations,
                    List<ComponentCompetitionFulfillmentPrediction> predictions) {
        Helper.checkNotNullNotEmpty(situations, "situations");
        Helper.checkList(situations, "situations");
        Helper.checkNotNullNotEmpty(predictions, "predictions");
        Helper.checkList(predictions, "predictions");
        if (situations.size() != predictions.size()) {
            throw new IllegalArgumentException("The situations anre predictions should have equal number of elements.");
        }
    }

    /**
     * Execute the incremental training on the Predictor by providing the input Situation instance.
     *
     * @param situation
     *            the situation used to train
     * @throws IllegalArgumentException
     *             if argument is null.
     */
    public void performIncrementalTraining(ComponentCompetitionSituation situation) {
        Helper.checkNotNull(situation, "situation");
    }

    /**
     * Execute the incremental training on the Predictor by providing the input Situation instance and the related
     * output Prediction. This method can be used for some sophisticated algorithms - like Fuzzy logic.
     *
     * @param prediction
     *            the prediction used to train
     * @param situation
     *            the situation used to train
     * @throws IllegalArgumentException
     *             if any argument is null.
     */
    public void performIncrementalTraining(ComponentCompetitionSituation situation,
                    ComponentCompetitionFulfillmentPrediction prediction) {
        Helper.checkNotNull(situation, "situation");
        Helper.checkNotNull(prediction, "prediction");
    }

    /**
     * Predict the outcome of the given situation. The result is returned as the Prediction instance.
     *
     * @param situation
     *            the situation to predict
     * @return the prediction
     * @throws IllegalArgumentException
     *             if argument is null.
     */
    public ComponentCompetitionFulfillmentPrediction predict(ComponentCompetitionSituation situation) {
        Helper.checkNotNull(situation, "situation");
        // Add up the reliability scores of all participants in the situation
        double sum = 0.0;
        for (Participant p : situation.getParticipants()) {
            Double r = p.getReliability();
            if (r != null) {
                sum += r;
            }
        }
        // Return a new ComponentCompetitionFulfillmentPrediction instance with the sum,
        // the situation, and this predictor instance
        return new ComponentCompetitionFulfillmentPrediction(sum, situation, this);
    }

    /**
     * Makes a deep copy of this predictor.
     *
     * @return the clone of this predictor.
     */
    public Object clone() {
        return new ComponentCompetitionPredictor();
    }
}