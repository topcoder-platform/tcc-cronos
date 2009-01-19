/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.predictor.impl.componentcompetition;

import junit.framework.TestCase;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;

import com.topcoder.predictor.Predictor;
import com.topcoder.predictor.impl.componentcompetition.analysis.CompetitionSituationTimelineAndPrizeGenerator;
import com.topcoder.predictor.impl.componentcompetition.analysis.ComponentCompetitionFulfillmentPredictionPrizeComparator;
import com.topcoder.predictor.impl.componentcompetition.analysis.ComponentCompetitionFulfillmentPredictionTimelineComparator;
import com.topcoder.predictor.impl.componentcompetition.analysis.ComponentCompetitionSituationPrizeGenerator;
import com.topcoder.predictor.impl.componentcompetition.analysis.ComponentCompetitionSituationTimelineGenerator;

/**
 * <p>
 * Demo for this component. It shows usage of the predictors, situation generators and prediction comparators defined in
 * this component.
 * </p>
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public class Demo extends TestCase {

    /**
     * <p>
     * Demo for the ComponentCompetitionPredictor.
     * </p>
     */
    public void test_demo_ComponentCompetitionPredictor() {
        // Create a component competition situation (with just the relevant fields)
        ComponentCompetitionSituation situation = new ComponentCompetitionSituation();
        situation.setPostingDate(new Date(100000));
        situation.setEndDate(new Date(200000));
        situation.setPrize(800.0);
        List<Participant> participants = new ArrayList<Participant>();
        Participant participant1 = new Participant();
        participant1.setReliability(0.0);
        participants.add(participant1);
        Participant participant2 = new Participant();
        participant2.setReliability(1.0);
        participants.add(participant2);
        Participant participant3 = new Participant();
        participant3.setReliability(0.8);
        participants.add(participant3);
        Participant participant4 = new Participant();
        participant4.setReliability(0.6);
        participants.add(participant4);
        situation.setParticipants(participants);

        // Create a component competition predictor and predict
        // the expected number of submissions to pass review
        ComponentCompetitionPredictor predictor = new ComponentCompetitionPredictor();
        ComponentCompetitionFulfillmentPrediction prediction = predictor.predict(situation);
        // The prediction's expectedPassedReviewSubmissionCount will be 2.4,
        // as it predicts that there will be 2.4 submissions for this competition,
        // this is sum of the reliabilities
        assertEquals("The prediction is incorrect.", 2.4, prediction.getExpectedPassedReviewSubmissionCount());
    }

    /**
     * <p>
     * Demo for the ComponentCompetitionAveragePredictor.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_demo_ComponentCompetitionAveragePredictor() throws Exception {
        // prepare situation to predict
        ComponentCompetitionSituation situation = new ComponentCompetitionSituation();
        List<Participant> participants = new ArrayList<Participant>();
        Participant p1 = new Participant();
        p1.setReliability(0.1);
        participants.add(p1);
        Participant p2 = new Participant();
        p2.setReliability(0.5);
        participants.add(p2);
        situation.setParticipants(participants);
        // create average predictor
        Predictor<ComponentCompetitionSituation, ComponentCompetitionFulfillmentPrediction>[] predictors
            = new ComponentCompetitionPredictor[] {
                new ComponentCompetitionPredictor(),
                new MockComponentCompetitionPredictor() };
        ComponentCompetitionAveragePredictor predictor =
            new ComponentCompetitionAveragePredictor(predictors);
        // do prediction
        ComponentCompetitionFulfillmentPrediction prediction = predictor.predict(situation);
        // the first predictor predicts 0.6, the second predictor predicts 2.0, the average is 1.3
        assertEquals("The prediction is incorrect.",
            1.3, prediction.getExpectedPassedReviewSubmissionCount());
    }

    /**
     * <p>
     * Demo for the situation generators.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_demo_generators_and_comparators() throws Exception {
        ComponentCompetitionSituationPrizeGenerator prizeGen =
             new ComponentCompetitionSituationPrizeGenerator(
                             new ComponentCompetitionSituation(),
                             100.0, 300.0, 100.0);
        Iterator<ComponentCompetitionSituation> iterator1 = prizeGen.iterator();
        // iterator1 contains 3 situations, their prizes are
        // 100.0, 200.0, 300.0
        ComponentCompetitionSituation s = iterator1.next();
        assertEquals("The generated situation is incorrect", 100.0, s.getPrize().doubleValue());
        s = iterator1.next();
        assertEquals("The generated situation is incorrect", 200.0, s.getPrize().doubleValue());
        s = iterator1.next();
        assertEquals("The generated situation is incorrect", 300.0, s.getPrize().doubleValue());
        assertFalse("There should be no situation.", iterator1.hasNext());

        ComponentCompetitionSituationTimelineGenerator timeGen =
            new ComponentCompetitionSituationTimelineGenerator(
                            new ComponentCompetitionSituation(),
                            new Date(100000), new Date(300000), 100000);
        Iterator<ComponentCompetitionSituation> iterator2 = timeGen.iterator();
        // iterator2 contains 3 situations, their end dates are
        // new Date(100000), Date(200000), Date(300000)
        s = iterator2.next();
        assertEquals("The generated situation is incorrect", new Date(100000), s.getEndDate());
        s = iterator2.next();
        assertEquals("The generated situation is incorrect", new Date(200000), s.getEndDate());
        s = iterator2.next();
        assertEquals("The generated situation is incorrect", new Date(300000), s.getEndDate());
        assertFalse("There should be no situation.", iterator2.hasNext());

        CompetitionSituationTimelineAndPrizeGenerator prizeAndTimeGen =
            new CompetitionSituationTimelineAndPrizeGenerator(
                        new ComponentCompetitionSituation(),
                        100.0, 200.0, 100.0,
                        new Date(100000), new Date(200000), 100000);
        Iterator<ComponentCompetitionSituation> iterator3 = prizeAndTimeGen.iterator();
        // there should be 4 situations
        // end date of the first situation is new Date(100000)
        // prize of first situation is 100.0
        s = iterator3.next();
        assertEquals("The generated situation is incorrect", new Date(100000), s.getEndDate());
        assertEquals("The generated situation is incorrect", 100.0, s.getPrize().doubleValue());
        // end date of the second situation is new Date(100000)
        // prize of second situation is 200.0
        s = iterator3.next();
        assertEquals("The generated situation is incorrect", new Date(100000), s.getEndDate());
        assertEquals("The generated situation is incorrect", 200.0, s.getPrize().doubleValue());
        // end date of the third situation is new Date(200000)
        // prize of third situation is 100.0
        s = iterator3.next();
        assertEquals("The generated situation is incorrect", new Date(200000), s.getEndDate());
        assertEquals("The generated situation is incorrect", 100.0, s.getPrize().doubleValue());
        // end date of the fourth situation is new Date(200000)
        // prize of fourth situation is 200.0
        s = iterator3.next();
        assertEquals("The generated situation is incorrect", new Date(200000), s.getEndDate());
        assertEquals("The generated situation is incorrect", 200.0, s.getPrize().doubleValue());
        assertFalse("There should be no situation.", iterator3.hasNext());
    }

    /**
     * <p>
     * Demo for the prediction comparators.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_demo_comparators() throws Exception {
        // expected range is [1.0, 3.0], target timeline is 100000
        ComponentCompetitionFulfillmentPredictionPrizeComparator prizeComparator =
            new ComponentCompetitionFulfillmentPredictionPrizeComparator(1.0, 3.0, 100000);
        ComponentCompetitionSituation situation = new ComponentCompetitionSituation();
        ComponentCompetitionPredictor predictor = new ComponentCompetitionPredictor();
        ComponentCompetitionFulfillmentPrediction prediction1 =
            new ComponentCompetitionFulfillmentPrediction(0.5, situation, predictor);
        ComponentCompetitionFulfillmentPrediction prediction2 =
            new ComponentCompetitionFulfillmentPrediction(2.0, situation, predictor);
        // prediction1 is below the range, prediction2 is in the range, so result should be 1
        int result = prizeComparator.compare(prediction1, prediction2);
        assertEquals("The compare method returns incorrect result.", 1, result);

        situation.setEndDate(new Date(150000));
        situation.setPostingDate(new Date(100000));
        situation.setPrize(1000.0);
        prediction1 = new ComponentCompetitionFulfillmentPrediction(1.1,
                        situation, predictor);
        situation = new ComponentCompetitionSituation();
        situation.setEndDate(new Date(160000));
        situation.setPostingDate(new Date(100000));
        situation.setPrize(900.0);
        prediction2 = new ComponentCompetitionFulfillmentPrediction(2.0,
                        situation, predictor);
        // both predictions are in the range;
        // both timelines are shorter than target
        // prediction1's situation has larger prize
        // so result should be 1
        result = prizeComparator.compare(prediction1, prediction2);
        assertEquals("The compare method returns incorrect result.", 1, result);

        // expected range is [1.0, 3.0], target prize is 500.0
        ComponentCompetitionFulfillmentPredictionTimelineComparator timeComparator =
            new ComponentCompetitionFulfillmentPredictionTimelineComparator(1.0, 3.0, 500.0);
        situation.setPrize(300.0);
        prediction1 = new ComponentCompetitionFulfillmentPrediction(1.1,
                        situation, predictor);
        situation = new ComponentCompetitionSituation();
        situation.setPrize(600.0);
        prediction2 = new ComponentCompetitionFulfillmentPrediction(2.0,
                        situation, predictor);
        // both predictions are in the range;
        // prediction1's prize is less than target, but prediction2's prize is larger than target,
        // so result should be -1
        result = timeComparator.compare(prediction1, prediction2);
        assertEquals("The compare method returns incorrect result.", -1, result);

        situation = new ComponentCompetitionSituation();
        prediction1 = new ComponentCompetitionFulfillmentPrediction(1.1,
                        situation, predictor);
        prediction2 = new ComponentCompetitionFulfillmentPrediction(2.0,
                        situation, predictor);
        // both predictions are in the range;
        // their situations' posting dates, end dates and prizes are not set,
        // so result should be 0
        result = timeComparator.compare(prediction1, prediction2);
        assertEquals("The compare method returns incorrect result.", 0, result);
    }
}
