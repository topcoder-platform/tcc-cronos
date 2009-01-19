/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.predictor.impl.componentcompetition.accuracytests;

import java.util.List;

import com.topcoder.predictor.impl.componentcompetition.ComponentCompetitionFulfillmentPrediction;
import com.topcoder.predictor.impl.componentcompetition.ComponentCompetitionPredictor;
import com.topcoder.predictor.impl.componentcompetition.ComponentCompetitionSituation;

/**
 * <p>
 * A mock predictor that simply extends <code>ComponentCompetitionPredictor</code>. Used for testing.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockComponentCompetitionPredictor extends ComponentCompetitionPredictor {
    /**
     * <p>
     * Represents the reliability scores.
     * </p>
     */
    private Double reliabilityScores = 0.0;

    /**
     * <p>
     * Represents if the full training is supported by this Predictor.
     * </p>
     */
    private boolean isFullTrainingSupported = false;

    /**
     * <p>
     * Represents if the incremental training is supported by this Predictor.
     * </p>
     */
    private boolean isIncrementalTrainingSupported = false;

    /**
     * <p>
     * Represents if the current Predictor instance is ready to perform "predict" method.
     * </p>
     */
    private boolean isReadyToOperate = true;

    /**
     * <p>
     * Represents the message for the <code>performFullTraining</code> and <code>performIncrementalTraining</code>
     * operations.
     * </p>
     */
    private String message = null;

    /**
     * <p>
     * Constructs a default <code>MockComponentCompetitionPredictor</code> instance.
     * </p>
     */
    public MockComponentCompetitionPredictor() {
        // empty
    }

    /**
     * <p>
     * Constructs a default <code>MockComponentCompetitionPredictor</code> instance.
     * </p>
     *
     * @param reliabilityScores
     *            the reliability scores.
     */
    public MockComponentCompetitionPredictor(Double reliabilityScores) {
        this.reliabilityScores = reliabilityScores;
    }

    /**
     * <p>
     * Constructs a default <code>MockComponentCompetitionPredictor</code> instance.
     * </p>
     *
     * @param reliabilityScores
     *            the reliability scores.
     * @param isFullTrainingSupported
     *            if the full training is supported by this Predictor.
     * @param isIncrementalTrainingSupported
     *            if the incremental training is supported by this Predictor.
     * @param isReadyToOperate
     *            if the current Predictor instance is ready to perform "predict" method.
     */
    public MockComponentCompetitionPredictor(Double reliabilityScores, boolean isFullTrainingSupported,
        boolean isIncrementalTrainingSupported, boolean isReadyToOperate) {
        this.reliabilityScores = reliabilityScores;
        this.isFullTrainingSupported = isFullTrainingSupported;
        this.isIncrementalTrainingSupported = isIncrementalTrainingSupported;
        this.isReadyToOperate = isReadyToOperate;
    }

    /**
     * <p>
     * Gets the message for the expected operation.
     * </p>
     *
     * @return the operation message. <code>null</code> if no such operation.
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * <p>
     * Retrieves the capability of the full training for this Predictor.
     * </p>
     *
     * @return <code>true</code> if the full training is supported by this Predictor; <code>false</code>
     *         otherwise.
     */
    public boolean isFullTrainingSupported() {
        return this.isFullTrainingSupported;
    }

    /**
     * <p>
     * Retrieves the capability of the incremental training for this Predictor.
     * </p>
     *
     * @return <code>true</code> if the incremental training is supported by this Predictor; <code>false</code>
     *         otherwise.
     */
    public boolean isIncrementalTrainingSupported() {
        return this.isIncrementalTrainingSupported;
    }

    /**
     * <p>
     * Retrieves the state of this Predictor - whether it is ready for performing predictions (true value is
     * returned), or not ready - like not trained yet (false value is returned).
     * </p>
     *
     * @return <code>true</code> if the current Predictor instance is ready to perform "predict" method;
     *         <code>false</code> otherwise.
     */
    public boolean isReadyToOperate() {
        return this.isReadyToOperate;
    }

    /**
     * <p>
     * Executes full training on the Predictor by providing the list of input situations.
     * </p>
     *
     * @param situations
     *            the situations used to train.
     */
    public void performFullTraining(List<ComponentCompetitionSituation> situations) {
        message = "performFullTraining1|" + this.reliabilityScores;
    }

    /**
     * <p>
     * Executes full training on the Predictor by providing the list of input situations and list of related output
     * predictions. This method can be used for some sophisticated algorithms - like Fuzzy logic.
     * </p>
     *
     * @param situations
     *            the situations used to train.
     * @param predictions
     *            the predictions used to train.
     */
    public void performFullTraining(List<ComponentCompetitionSituation> situations,
        List<ComponentCompetitionFulfillmentPrediction> predictions) {
        message = "performFullTraining2|" + this.reliabilityScores;
    }

    /**
     * <p>
     * Executes the incremental training on the Predictor by providing the input Situation instance.
     * </p>
     *
     * predictor.performIncrementalTraining(situations)
     *
     * @param situation
     *            the predictions used to train.
     */
    public void performIncrementalTraining(ComponentCompetitionSituation situation) {
        message = "performIncrementalTraining1|" + this.reliabilityScores;
    }

    /**
     * <p>
     * Executes the incremental training on the Predictor by providing the input Situation instance and the related
     * output Prediction. This method can be used for some sophisticated algorithms - like Fuzzy logic.
     * </p>
     *
     * @param prediction
     *            the prediction used to train.
     * @param situation
     *            the situation used to train.
     */
    public void performIncrementalTraining(ComponentCompetitionSituation situation,
        ComponentCompetitionFulfillmentPrediction prediction) {
        message = "performIncrementalTraining2|" + this.reliabilityScores;
    }

    /**
     * <p>
     * Predicts the outcome of the given situation. The result is returned as the Prediction instance.
     * </p>
     *
     * @param situation
     *            the situation to predict.
     *
     * @return the prediction.
     *
     * @throws IllegalArgumentException
     *             if the argument is null.
     */
    public ComponentCompetitionFulfillmentPrediction predict(ComponentCompetitionSituation situation) {
        if (situation == null) {
            throw new IllegalArgumentException("situation should not be null!");
        }

        return new ComponentCompetitionFulfillmentPrediction(reliabilityScores, situation, this);
    }

    /**
     * <p>
     * Clones this object.
     * </p>
     *
     * @return the cloned object
     */
    public Object clone() {
        return new MockComponentCompetitionPredictor();
    }
}
