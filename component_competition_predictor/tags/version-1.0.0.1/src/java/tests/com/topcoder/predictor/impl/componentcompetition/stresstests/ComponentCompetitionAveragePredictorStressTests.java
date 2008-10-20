/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.predictor.impl.componentcompetition.stresstests;

import java.util.ArrayList;
import java.util.List;

import com.topcoder.predictor.Predictor;
import com.topcoder.predictor.impl.componentcompetition.ComponentCompetitionAveragePredictor;
import com.topcoder.predictor.impl.componentcompetition.ComponentCompetitionFulfillmentPrediction;
import com.topcoder.predictor.impl.componentcompetition.ComponentCompetitionPredictor;
import com.topcoder.predictor.impl.componentcompetition.ComponentCompetitionSituation;
import com.topcoder.predictor.impl.componentcompetition.Participant;

/**
 * Stress tests for class ComponentCompetitionAveragePredictor.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 *
 */
public class ComponentCompetitionAveragePredictorStressTests extends BaseStressTests {

    /**
     * The instance of ComponentCompetitionAveragePredictor for test.
     */
    private ComponentCompetitionAveragePredictor predictor;

    /**
     * The Predictor for test.
     */
    private Predictor<ComponentCompetitionSituation, ComponentCompetitionFulfillmentPrediction>[] predictors;

    /**
     * The situations for test.
     */
    private List<ComponentCompetitionSituation> situations;

    /**
     * The predictions for test.
     */
    private List<ComponentCompetitionFulfillmentPrediction> predictions;

    /**
     * Set up test environment.
     * @throws Exception to JUnit
     */
    @SuppressWarnings("unchecked")
    protected void setUp() throws Exception {
        predictors = new Predictor[10];
        for (int i = 0; i < predictors.length; i++) {
            if (i % 2 == 0) {
                predictors[i] = new ComponentCompetitionAveragePredictor();
            } else {
                predictors[i] = new ComponentCompetitionPredictor();
            }
        }
        predictor = new ComponentCompetitionAveragePredictor();

        List<Participant> participants = new ArrayList<Participant>();
        for (int i = 0; i < 5; i++) {
            Participant participant = new Participant();
            if (i != 3) {
                participant.setReliability(0.1 * (i + 1));
            }
            participants.add(participant);
        }

        situations = new ArrayList<ComponentCompetitionSituation>();

        for (int i = 0; i < 5; i++) {
            ComponentCompetitionSituation situation = new ComponentCompetitionSituation();
            situation.setParticipants(participants);
            situations.add(situation);
        }

        predictions = new ArrayList<ComponentCompetitionFulfillmentPrediction>();
        for (int i = 0; i < 5; i++) {
            ComponentCompetitionFulfillmentPrediction prediction = new ComponentCompetitionFulfillmentPrediction(
                    1d + i * 0.1, situations.get(i), predictors[i]);
            predictions.add(prediction);
        }
        super.setUp();
    }

    /**
     * Stress tests for method ComponentCompetitionAveragePredictor(Predictor[]).
     */
    public void testCtorStress() {

        begin();

        for (int i = 0; i < TIMES; i++) {
            new ComponentCompetitionAveragePredictor(predictors);
        }

        print("ComponentCompetitionAveragePredictor(Predictor[])");
    }

    /**
     * Stress tests for method performFullTraining(List).
     *
     * @throws Exception
     *             to JUnit
     */
    public void testPerformFullTrainingStress1() throws Exception {
        predictor = new ComponentCompetitionAveragePredictor(predictors);

        begin();

        for (int i = 0; i < TIMES; i++) {
            predictor.performFullTraining(situations);
        }

        print("ComponentCompetitionAveragePredictor#performFullTraining(List)");
    }

    /**
     * Stress tests for method performFullTraining(List, List).
     *
     * @throws Exception
     *             to JUnit
     */
    public void testPerformFullTrainingStress2() throws Exception {
        predictor = new ComponentCompetitionAveragePredictor(predictors);

        begin();

        for (int i = 0; i < TIMES; i++) {
            predictor.performFullTraining(situations, predictions);
        }

        print("ComponentCompetitionAveragePredictor#performFullTraining(List, List)");
    }

    /**
     * Stress tests for method performIncrementalTraining(ComponentCompetitionSituation).
     *
     * @throws Exception
     *             to JUnit
     */
    public void testPerformIncrementalTrainingStress1() throws Exception {
        predictor = new ComponentCompetitionAveragePredictor(predictors);

        begin();

        for (int i = 0; i < TIMES; i++) {
            predictor.performIncrementalTraining(situations.get(0));
        }

        print("ComponentCompetitionAveragePredictor#performIncrementalTraining(ComponentCompetitionSituation)");
    }

    /**
     * Stress tests for method performIncrementalTraining(ComponentCompetitionSituation,
     * ComponentCompetitionFulfillmentPrediction).
     *
     * @throws Exception
     *             to JUnit
     */
    public void testPerformIncrementalTrainingStress2() throws Exception {
        predictor = new ComponentCompetitionAveragePredictor(predictors);

        begin();

        for (int i = 0; i < TIMES; i++) {
            predictor.performIncrementalTraining(situations.get(i % 5), predictions.get(0));
        }

        print("ComponentCompetitionAveragePredictor#performIncrementalTraining"
                + "(ComponentCompetitionSituation, ComponentCompetitionFulfillmentPrediction)");
    }

    /**
     * Stress tests for method predict(ComponentCompetitionSituation).
     *
     * @throws Exception
     *             to JUnit
     */
    public void testPredictStress1() throws Exception {
        predictor = new ComponentCompetitionAveragePredictor(predictors);

        begin();

        for (int i = 0; i < TIMES; i++) {
            ComponentCompetitionFulfillmentPrediction prediction = predictor.predict(situations.get(0));
        }

        print("ComponentCompetitionAveragePredictor#predict(ComponentCompetitionSituation)");
    }

}
