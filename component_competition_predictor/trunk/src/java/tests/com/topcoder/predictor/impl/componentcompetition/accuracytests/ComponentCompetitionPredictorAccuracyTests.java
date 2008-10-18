/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.predictor.impl.componentcompetition.accuracytests;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.topcoder.predictor.impl.componentcompetition.ComponentCompetitionFulfillmentPrediction;
import com.topcoder.predictor.impl.componentcompetition.ComponentCompetitionPredictor;
import com.topcoder.predictor.impl.componentcompetition.ComponentCompetitionSituation;
import com.topcoder.predictor.impl.componentcompetition.Participant;

import junit.framework.TestCase;

/**
 * <p>
 * Unit tests for <code>{@link ComponentCompetitionPredictor}</code> class.
 * </p>
 *
 * @author morehappiness
 * @version 1.0
 */
public class ComponentCompetitionPredictorAccuracyTests extends TestCase {
    /**
     * <p>
     * Represents the <code>ComponentCompetitionPredictor</code> used in tests.
     * </p>
     */
    private ComponentCompetitionPredictor instance = null;

    /**
     * <p>
     * Sets up the tests.
     * </p>
     */
    public void setUp() {
        instance = new ComponentCompetitionPredictor();
    }

    /**
     * <p>
     * Tests accuracy of <code>ComponentCompetitionPredictor()</code> constructor.<br>
     * Instance should be correctly created.
     * </p>
     */
    public void testCtor() {
        assertNotNull("Instance should be created.", instance);
    }

    /**
     * <p>
     * Tests accuracy of <code>getSituationName()</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testGetSituationName() {
        String expected = "com.topcoder.predictor.impl.componentcompetition.ComponentCompetitionSituation";

        assertEquals("'getSituationName' should be correct.", expected, instance.getSituationName());
    }

    /**
     * <p>
     * Tests accuracy of <code>getPredictionName()</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testGetPredictionName() {
        String expected = "com.topcoder.predictor.impl.componentcompetition.ComponentCompetitionFulfillmentPrediction";

        assertEquals("'getPredictionName' should be correct.", expected, instance.getPredictionName());
    }

    /**
     * <p>
     * Tests accuracy of <code>isFullTrainingSupported()</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testIsFullTrainingSupported() {
        assertFalse("'isFullTrainingSupported' should be correct.", instance.isFullTrainingSupported());
    }

    /**
     * <p>
     * Tests accuracy of <code>isIncrementalTrainingSupported()</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testIsIncrementalTrainingSupported() {
        assertFalse("'isIncrementalTrainingSupported' should be correct.", instance.isIncrementalTrainingSupported());
    }

    /**
     * <p>
     * Tests accuracy of <code>isReadyToOperate()</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testIsReadyToOperate() {
        assertTrue("'isReadyToOperate' should be correct.", instance.isReadyToOperate());
    }

    /**
     * <p>
     * Tests accuracy of <code>getCapability(String)</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testGetapability() {
        assertNull("'getCapability' should be correct.", instance.getCapability("key"));
    }

    /**
     * <p>
     * Tests accuracy of <code>getCapabilityKeys()</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testGetapabilityKeys() {
        assertEquals("'getCapabilityKeys' should be correct.", 0, instance.getCapabilityKeys().size());
    }

    /**
     * <p>
     * Tests accuracy of <code>performFullTraining(List&lt;ComponentCompetitionSituation&gt;)</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testPerformFullTraining1() {
        List<ComponentCompetitionSituation> situations = new ArrayList<ComponentCompetitionSituation>();
        situations.add(new ComponentCompetitionSituation());

        instance.performFullTraining(situations);
    }

    /**
     * <p>
     * Tests accuracy of <code>performFullTraining(List&lt;ComponentCompetitionSituation&gt;,
     * List&lt;ComponentCompetitionFulfillmentPrediction&gt;)</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testPerformFullTraining2() {
        List<ComponentCompetitionSituation> situations = new ArrayList<ComponentCompetitionSituation>();
        situations.add(new ComponentCompetitionSituation());

        List<ComponentCompetitionFulfillmentPrediction> predictions =
            new ArrayList<ComponentCompetitionFulfillmentPrediction>();
        predictions.add(new ComponentCompetitionFulfillmentPrediction(1.0, new ComponentCompetitionSituation(),
            new ComponentCompetitionPredictor()));

        instance.performFullTraining(situations, predictions);
    }

    /**
     * <p>
     * Tests accuracy of <code>performIncrementalTraining(ComponentCompetitionSituation)</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testPerformIncrementalTraining1() {
        instance.performIncrementalTraining(new ComponentCompetitionSituation());
    }

    /**
     * <p>
     * Tests accuracy of
     * <code>performIncrementalTraining(ComponentCompetitionSituation, ComponentCompetitionFulfillmentPrediction)</code>
     * method.<br>
     * Result should be correct.
     * </p>
     */
    public void testPerformIncrementalTraining2() {
        instance.performIncrementalTraining(new ComponentCompetitionSituation(),
            new ComponentCompetitionFulfillmentPrediction(1.0, new ComponentCompetitionSituation(),
                new ComponentCompetitionPredictor()));
    }

    /**
     * <p>
     * Tests accuracy of <code>predict(ComponentCompetitionSituation)</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testPredict1() {
        ComponentCompetitionSituation situation = new ComponentCompetitionSituation();

        situation.setPostingDate(new Date(2008 - 1900, 9, 1, 9, 0));
        situation.setEndDate(new Date(2008 - 1900, 9, 5, 9, 0));
        situation.setPrize(new Double(800.00));

        List<Participant> participants = new ArrayList<Participant>();
        Participant participant1 = new Participant();
        participant1.setReliability(new Double(0.0));
        participants.add(participant1);
        Participant participant2 = new Participant();
        participant2.setReliability(new Double(1.0));
        participants.add(participant2);
        Participant participant3 = new Participant();
        participant3.setReliability(new Double(0.8));
        participants.add(participant3);
        Participant participant4 = new Participant();
        participant4.setReliability(new Double(0.6));
        participants.add(participant4);
        situation.setParticipants(participants);
        ComponentCompetitionFulfillmentPrediction prediction = instance.predict(situation);

        assertEquals("'predict' should be correct.", 2.4, prediction.getExpectedPassedReviewSubmissionCount());
        assertSame("'predict' should be correct.", situation, prediction.getSituation());
        assertSame("'predict' should be correct.", instance, prediction.getPredictor());
    }

    /**
     * <p>
     * Tests accuracy of <code>predict(ComponentCompetitionSituation)</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testPredict2() {
        ComponentCompetitionSituation situation = new ComponentCompetitionSituation();

        // No participant
        ComponentCompetitionFulfillmentPrediction prediction = instance.predict(situation);

        assertEquals("'predict' should be correct.", 0.0, prediction.getExpectedPassedReviewSubmissionCount());
        assertSame("'predict' should be correct.", situation, prediction.getSituation());
        assertSame("'predict' should be correct.", instance, prediction.getPredictor());
    }

    /**
     * <p>
     * Tests accuracy of <code>clone()</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testClone() {
        ComponentCompetitionPredictor clonedObj = (ComponentCompetitionPredictor) instance.clone();

        assertNotNull("'clone' should be correct.", clonedObj);
    }
}
