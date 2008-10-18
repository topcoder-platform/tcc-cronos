/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.predictor.impl.componentcompetition.stresstests;

import java.util.ArrayList;
import java.util.List;

import com.topcoder.predictor.impl.componentcompetition.ComponentCompetitionFulfillmentPrediction;
import com.topcoder.predictor.impl.componentcompetition.ComponentCompetitionPredictor;
import com.topcoder.predictor.impl.componentcompetition.ComponentCompetitionSituation;
import com.topcoder.predictor.impl.componentcompetition.Participant;

/**
 * Stress tests for class ComponentCompetitionPredictor.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 *
 */
public class ComponentCompetitionPredictorStressTests extends BaseStressTests {

    /**
     * Stress tests for method predict(ComponentCompetitionSituation situation).
     *
     * @throws Exception
     *             to JUnit
     */
    public void testPredictStress1() throws Exception {
        // prepare the test data
        ComponentCompetitionPredictor predictor = new ComponentCompetitionPredictor();
        List<Participant> participants = new ArrayList<Participant>();
        for (int i = 0; i < 5; i++) {
            Participant participant = new Participant();
            participant.setReliability(0.1 * (i + 1));
            participants.add(participant);
        }
        ComponentCompetitionSituation situation = new ComponentCompetitionSituation();
        situation.setParticipants(participants);

        begin();

        for (int i = 0; i < TIMES; i++) {
            ComponentCompetitionFulfillmentPrediction prediction = predictor.predict(situation);

            assertEquals("prediction", 1.5, prediction.getExpectedPassedReviewSubmissionCount());
        }

        print("ComponentCompetitionPredictor#predict(ComponentCompetitionSituation situation)");
    }

    /**
     * Stress tests for method predict(ComponentCompetitionSituation situation).
     *
     * @throws Exception
     *             to JUnit
     */
    public void testPredictStress2() throws Exception {
        // prepare the test data
        ComponentCompetitionPredictor predictor = new ComponentCompetitionPredictor();
        List<Participant> participants = new ArrayList<Participant>();
        for (int i = 0; i < 5; i++) {
            Participant participant = new Participant();
            if (i != 3) {
                // some have no reliability
                participant.setReliability(0.1 * (i + 1));
            }
            participants.add(participant);
        }
        ComponentCompetitionSituation situation = new ComponentCompetitionSituation();
        situation.setParticipants(participants);

        begin();

        for (int i = 0; i < TIMES; i++) {
            ComponentCompetitionFulfillmentPrediction prediction = predictor.predict(situation);

            assertEquals("prediction", 1.1, prediction.getExpectedPassedReviewSubmissionCount());
        }

        print("ComponentCompetitionPredictor#predict(ComponentCompetitionSituation situation)");
    }

}
