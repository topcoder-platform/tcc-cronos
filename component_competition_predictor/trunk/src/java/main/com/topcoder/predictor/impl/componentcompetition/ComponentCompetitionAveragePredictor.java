/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.predictor.impl.componentcompetition;

import com.topcoder.predictor.Predictor;
import com.topcoder.predictor.PredictorException;

import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/**
 * <p>
 * A simple predictor that aggregates other predictors that use ComponentCompetitionSituation and
 * ComponentCompetitionFulfillmentPrediction. It delegates all training to the constituent predictors, and has no
 * capabilities. Its predict method will obtain the predictions from each constituent predictor, and average them in a
 * new prediction. Please note that when there are three or more predictors, then the average will ignore the highest
 * and lowest prediction. It has no capabilities. Specifically, any attempt to add capabilities will be ignored. The
 * capability methods are basically disabled, where setting has no effect, and retrieving will result in no results.
 * </p>
 *
 * <p>
 * Usage:
 *
 * <pre>
 * // prepare situation to predict
 * ComponentCompetitionSituation situation = new ComponentCompetitionSituation();
 * List&lt;Participant&gt; participants = new ArrayList&lt;Participant&gt;();
 * Participant p1 = new Participant();
 * p1.setReliability(0.1);
 * participants.add(p1);
 * Participant p2 = new Participant();
 * p2.setReliability(0.5);
 * participants.add(p2);
 * situation.setParticipants(participants);
 * // create average predictor
 * Predictor&lt;ComponentCompetitionSituation, ComponentCompetitionFulfillmentPrediction&gt;[] predictors =
 *     new ComponentCompetitionPredictor[] {
 *        new ComponentCompetitionPredictor(), new MockComponentCompetitionPredictor() };
 * ComponentCompetitionAveragePredictor predictor = new ComponentCompetitionAveragePredictor(predictors);
 * // do prediction
 * ComponentCompetitionFulfillmentPrediction prediction = predictor.predict(situation);
 * // the first predictor predicts 0.6, the second predictor predicts 2.0, the average is 1.3
 * assertEquals(&quot;The prediction is incorrect.&quot;, 1.3, prediction.getExpectedPassedReviewSubmissionCount());
 * </pre>
 *
 * </p>
 *
 * <p>
 * Thread-Safety: This class is mutable and not thread-safe.
 * </p>
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public class ComponentCompetitionAveragePredictor implements
                Predictor<ComponentCompetitionSituation, ComponentCompetitionFulfillmentPrediction> {
    /**
     * Represents the collection of predictors aggregated by this predictor. It is set in the constructor and setter. It
     * is accessed with the getter. It is used in all training methods and the predict method. It will contain 0 to many
     * predictors. There will be no null elements.
     */
    private Predictor<ComponentCompetitionSituation, ComponentCompetitionFulfillmentPrediction>[] predictors;

    /**
     * Constructor accepting an array of predictors. A null input will be interpreted as a empty input.
     *
     * @param predictors
     *            the predictors that will be aggregated by this predictor
     * @throws IllegalArgumentException
     *             If predictors contains null elements
     */
    public ComponentCompetitionAveragePredictor(
                    Predictor<ComponentCompetitionSituation, ComponentCompetitionFulfillmentPrediction>[] predictors) {
        setPredictors(predictors);
    }

    /**
     * Default constructor. This constructor is primarily to preserve full compatibility for the manager, but its use is
     * not anticipated, and in fact, not desired.
     */
    public ComponentCompetitionAveragePredictor() {
        // since it is empty list, we just simply use any implementation of
        // Predictor<ComponentCompetitionSituation,ComponentCompetitionFulfillmentPrediction>
        this.predictors = new ComponentCompetitionPredictor[0];
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
     * Retrieve the capability of the full training for this Predictor.
     *
     * @return true - if the full training is supported by this Predictor, false - otherwise.
     */
    public boolean isFullTrainingSupported() {
        for (Predictor<?, ?> p : predictors) {
            if (p.isFullTrainingSupported()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retrieve the capability of the incremental training for this Predictor. Returns true if any of the contained
     * predictors returns true for this method. If all return false, then this method returns false.
     *
     * @return true - if the incremental training is supported by this Predictor, false - otherwise.
     */
    public boolean isIncrementalTrainingSupported() {
        for (Predictor<?, ?> p : predictors) {
            if (p.isIncrementalTrainingSupported()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retrieve the state of this Predictor - whether it is ready for performing predictions (true value is returned),
     * or not ready - like not trained yet (false value is returned). Returns true if all of the contained predictors
     * returns true for this method. If any return false, then this method returns false.
     *
     * @return true - if the current Predictor instance is ready to perform "predict" method, false - otherwise.
     */
    public boolean isReadyToOperate() {
        for (Predictor<?, ?> p : predictors) {
            if (!p.isReadyToOperate()) {
                return false;
            }
        }
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
     * @return it always returns null
     * @throws IllegalArgumentException
     *             if argument is null or empty string.
     */
    public String getCapability(String key) {
        Helper.checkNotNullNotEmpty(key, "key");
        return null;
    }

    /**
     * Retrieve all the keys of the custom capabilities for the Predictor.
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
     * @throws IllegalOperationException
     *             if the current operation is not supported (propagated from an inner prediction only)
     * @throws IllegalStateException
     *             if the current operation can not be executed right now. (propagated from an inner prediction only)
     * @throws PredictorException
     *             in any general problems case (like external exception to be wrapped by this one). (propagated from an
     *             inner prediction only)
     * @throws TrainingException
     *             if some error has occurred during training of the predictor. (propagated from an inner prediction
     *             only)
     */
    public void performFullTraining(List<ComponentCompetitionSituation> situations) throws PredictorException {
        Helper.checkNotNullNotEmpty(situations, "situations");
        Helper.checkList(situations, "situations");
        // For each predictor in predictors:
        for (Predictor<ComponentCompetitionSituation, ?> p : predictors) {
            // Check if it is fully trainable
            if (p.isFullTrainingSupported()) {
                // if true, then train it
                p.performFullTraining(situations);
            }
        }
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
     * @throws IllegalOperationException
     *             if the current operation is not supported. (propagated from an inner prediction only)
     * @throws IllegalStateException
     *             if the current operation can not be executed right now. (propagated from an inner prediction only)
     * @throws PredictorException
     *             in any general problems case (like external exception to be wrapped by this one). (propagated from an
     *             inner prediction only)
     * @throws TrainingException
     *             if some error has occurred during training of the predictor. (propagated from an inner prediction
     *             only)
     */
    public void performFullTraining(List<ComponentCompetitionSituation> situations,
                    List<ComponentCompetitionFulfillmentPrediction> predictions) throws PredictorException {
        Helper.checkNotNullNotEmpty(situations, "situations");
        Helper.checkList(situations, "situations");
        Helper.checkNotNullNotEmpty(predictions, "predictions");
        Helper.checkList(predictions, "predictions");
        if (situations.size() != predictions.size()) {
            throw new IllegalArgumentException("The situations anre predictions should have equal number of elements.");
        }
        // For each predictor in predictors:
        for (Predictor<ComponentCompetitionSituation, ComponentCompetitionFulfillmentPrediction> p : predictors) {
            // Check if it is fully trainable
            if (p.isFullTrainingSupported()) {
                // If true, then train it
                p.performFullTraining(situations, predictions);
            }
        }
    }

    /**
     * Execute the incremental training on the Predictor by providing the input Situation instance.
     *
     * @param situation
     *            the situation used to train
     * @throws IllegalArgumentException
     *             if argument is null.
     * @throws IllegalOperationException
     *             if the current operation is not supported. (propagated from an inner prediction only)
     * @throws IllegalStateException
     *             if the current operation can not be executed right now. (propagated from an inner prediction only)
     * @throws PredictorException
     *             in any general problems case (like external exception to be wrapped by this one). (propagated from an
     *             inner prediction only)
     * @throws TrainingException
     *             if some error has occurred during training of the predictor. (propagated from an inner prediction
     *             only)
     */
    public void performIncrementalTraining(ComponentCompetitionSituation situation) throws PredictorException {
        Helper.checkNotNull(situation, "situation");
        // For each predictor in predictors:
        for (Predictor<ComponentCompetitionSituation, ?> p : predictors) {
            // Check if it is incrementally trainable
            if (p.isIncrementalTrainingSupported()) {
                // If it is true, then train it incrementally
                p.performIncrementalTraining(situation);
            }
        }
    }

    /**
     * Execute the incremental training on the Predictor by providing the input Situation instance and the related
     * output Prediction. This method can be used for some sophisticated algorithms - like Fuzzy logic.
     *
     * @param prediction
     *            the predictions used to train
     * @param situation
     *            the situations used to train
     * @throws IllegalArgumentException
     *             if any argument is null.
     * @throws IllegalOperationException
     *             if the current operation is not supported. (propagated from an inner prediction only)
     * @throws IllegalStateException
     *             if the current operation can not be executed right now. (propagated from an inner prediction only)
     * @throws PredictorException
     *             in any general problems case (like external exception to be wrapped by this one). (propagated from an
     *             inner prediction only)
     * @throws TrainingException
     *             if some error has occurred during training of the predictor. (propagated from an inner prediction
     *             only)
     */
    public void performIncrementalTraining(ComponentCompetitionSituation situation,
                    ComponentCompetitionFulfillmentPrediction prediction) throws PredictorException {
        Helper.checkNotNull(situation, "situation");
        Helper.checkNotNull(prediction, "prediction");
        // For each predictor in predictors:
        for (Predictor<ComponentCompetitionSituation, ComponentCompetitionFulfillmentPrediction> p : predictors) {
            // Check if it is incrementally trainable
            if (p.isIncrementalTrainingSupported()) {
                // If it is true, then train it incrementally:
                p.performIncrementalTraining(situation, prediction);
            }
        }
    }

    /**
     * Predict the outcome of the given situation. The result is returned as the Prediction instance.
     *
     * @param situation
     *            the situation to predict
     * @return the prediction
     * @throws IllegalArgumentException
     *             if argument is null.
     * @throws IllegalOperationException
     *             if the current operation is not supported. (propagated from an inner prediction only)
     * @throws IllegalStateException
     *             if the current operation can not be executed right now. (propagated from an inner prediction only)
     * @throws PredictorException
     *             in any general problems case (like external exception to be wrapped by this one). (propagated from an
     *             inner prediction only)
     */
    public ComponentCompetitionFulfillmentPrediction predict(ComponentCompetitionSituation situation)
                    throws PredictorException {
        Helper.checkNotNull(situation, "situation");
        List<Double> predictionValues = new ArrayList<Double>();
        // For each predictor in predictors collection
        for (Predictor<ComponentCompetitionSituation, ComponentCompetitionFulfillmentPrediction> p : predictors) {
            // Get prediction:
            ComponentCompetitionFulfillmentPrediction prediction = p.predict(situation);
            // Put the prediction's expectedSubmissionCount value as a Double into the predictionValues list
            predictionValues.add(prediction.getExpectedPassedReviewSubmissionCount());
        }
        // Sort the list
        Collections.sort(predictionValues);
        // If list is of size more than 2, then remove the first and last elements in the list.
        if (predictionValues.size() > 2) {
            predictionValues.remove(predictionValues.size() - 1);
            predictionValues.remove(0);
        }
        // Calculate the mean of the remaining values
        double sum = 0.0;
        for (Double value : predictionValues) {
            sum += value;
        }
        double avg = predictionValues.size() == 0 ? 0.0 : sum / predictionValues.size();
        // return a new ComponentCompetitionFulfillmentPrediction with this mean value,
        // the passed situation, and this predictor.
        return new ComponentCompetitionFulfillmentPrediction(avg, situation, this);
    }

    /**
     * Gets a shallow copy of the predictors.
     *
     * @return a shallow copy of the predictors
     */
    public Predictor<ComponentCompetitionSituation, ComponentCompetitionFulfillmentPrediction>[] getPredictors() {
        return this.predictors.clone();
    }

    /**
     * Makes a shallow copy of the array and sets it to the namesake field. A null input will be interpreted as a empty
     * input.
     *
     * @param predictors
     *            the predictors that will be aggregated by this predictor
     * @throws IllegalArgumentException
     *             If predictors contains null elements
     */
    public void setPredictors(
                    Predictor<ComponentCompetitionSituation, ComponentCompetitionFulfillmentPrediction>[] predictors) {
        if (predictors != null) {
            Helper.checkArray(predictors, "predictors");
            this.predictors = predictors.clone();
        } else {
            // since it is empty list, we just simply use any implementation of
            // Predictor<ComponentCompetitionSituation,ComponentCompetitionFulfillmentPrediction>
            this.predictors = new ComponentCompetitionPredictor[0];
        }
    }

    /**
     * Makes a deep copy of this predictor.
     *
     * @return a deep copy of this predictor.
     */
    public Object clone() {
        Predictor<ComponentCompetitionSituation, ComponentCompetitionFulfillmentPrediction>[] predictors =
            getPredictors();
        // make deep copy of each contained predictor
        for (int i = 0; i < predictors.length; i++) {
            predictors[i] =
                (Predictor<ComponentCompetitionSituation, ComponentCompetitionFulfillmentPrediction>)
                predictors[i].clone();
        }
        return new ComponentCompetitionAveragePredictor(predictors);
    }
}
