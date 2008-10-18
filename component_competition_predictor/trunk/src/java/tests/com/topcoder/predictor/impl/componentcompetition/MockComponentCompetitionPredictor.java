/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.predictor.impl.componentcompetition;

/**
 * <p>
 * This is a mock implementation of predictor used to test the ComponentCompetitionAveragePredictor class. It supports
 * full training and incremental training. And whether it is ready can be specified.
 * </p>
 *
 * <p>
 * Thread-Safety: This class is immutable and thread-safe.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockComponentCompetitionPredictor extends ComponentCompetitionPredictor {
    /**
     * Indicates whether this predictor is ready.
     */
    private final boolean ready;

    /**
     * Default constructor.
     */
    public MockComponentCompetitionPredictor() {
        this(true);
    }

    /**
     * Constructor accepting a flag indicating whether this predictor is ready.
     *
     * @param ready whether this predictor is ready
     */
    public MockComponentCompetitionPredictor(boolean ready) {
        this.ready = ready;
    }

    /**
     * Retrieve the capability of the full training for this Predictor. It always returns true.
     *
     * @return true - if the full training is supported by this Predictor, false - otherwise. It currently always return
     *         true.
     */
    public boolean isFullTrainingSupported() {
        return true;
    }

    /**
     * Retrieve the capability of the incremental training for this Predictor. It always returns true.
     *
     * @return true - if the incremental training is supported by this Predictor, false - otherwise. It currently always
     *         returns true.
     */
    public boolean isIncrementalTrainingSupported() {
        return true;
    }

    /**
     * Retrieve the state of this Predictor - whether it is ready for performing predictions (true value is returned),
     * or not ready - like not trained yet (false value is returned). It returns the ready variable.
     *
     * @return true - if the current Predictor instance is ready to perform "predict" method, false - otherwise.
     */
    public boolean isReadyToOperate() {
        return ready;
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
        // Return a new ComponentCompetitionFulfillmentPrediction instance with 2.0,
        // the situation, and this predictor instance
        return new ComponentCompetitionFulfillmentPrediction(2.0, situation, this);
    }

    /**
     * Makes a deep copy of this predictor.
     *
     * @return the clone of this predictor.
     */
    public Object clone() {
        return new MockComponentCompetitionPredictor();
    }
}