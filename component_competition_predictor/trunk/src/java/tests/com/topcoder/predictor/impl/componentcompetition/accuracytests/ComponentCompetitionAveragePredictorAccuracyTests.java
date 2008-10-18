/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.predictor.impl.componentcompetition.accuracytests;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.topcoder.predictor.Predictor;
import com.topcoder.predictor.impl.componentcompetition.ComponentCompetitionAveragePredictor;
import com.topcoder.predictor.impl.componentcompetition.ComponentCompetitionFulfillmentPrediction;
import com.topcoder.predictor.impl.componentcompetition.ComponentCompetitionPredictor;
import com.topcoder.predictor.impl.componentcompetition.ComponentCompetitionSituation;
import com.topcoder.predictor.impl.componentcompetition.Participant;

import junit.framework.TestCase;

/**
 * <p>
 * Unit tests for <code>{@link ComponentCompetitionAveragePredictor}</code> class.
 * </p>
 *
 * @author morehappiness
 * @version 1.0
 */
public class ComponentCompetitionAveragePredictorAccuracyTests extends TestCase {
    /**
     * <p>
     * Represents the <code>ComponentCompetitionAveragePredictor</code> used in tests.
     * </p>
     */
    private ComponentCompetitionAveragePredictor instance = null;

    /**
     * <p>
     * Represents the predictors that will be aggregated by this predictor.
     * </p>
     */
    private Predictor<ComponentCompetitionSituation, ComponentCompetitionFulfillmentPrediction>[] predictors = null;

    /**
     * <p>
     * Sets up the tests.
     * </p>
     */
    @SuppressWarnings("unchecked")
    public void setUp() {
        predictors = new Predictor[1];
        predictors[0] = new ComponentCompetitionPredictor();

        instance = new ComponentCompetitionAveragePredictor(predictors);
    }

    /**
     * <p>
     * Tests accuracy of <code>ComponentCompetitionAveragePredictor(Predictor&lt;ComponentCompetitionSituation,
     * ComponentCompetitionFulfillmentPrediction&gt;[])</code> constructor.<br>
     * Instance should be correctly created.
     * </p>
     */
    public void testCtor11() {
        assertEquals("Predictors should be correct.", 1, instance.getPredictors().length);
    }

    /**
     * <p>
     * Tests accuracy of <code>ComponentCompetitionAveragePredictor(Predictor&lt;ComponentCompetitionSituation,
     * ComponentCompetitionFulfillmentPrediction&gt;[])</code> constructor.<br>
     * Instance should be correctly created.
     * </p>
     */
    public void testCtor12() {
        instance = new ComponentCompetitionAveragePredictor(null);

        assertEquals("Predictors should be correct.", 0, instance.getPredictors().length);
    }

    /**
     * <p>
     * Tests accuracy of <code>ComponentCompetitionAveragePredictor(Predictor&lt;ComponentCompetitionSituation,
     * ComponentCompetitionFulfillmentPrediction&gt;[])</code> constructor.<br>
     * Instance should be correctly created.
     * </p>
     */
    public void testCtor13() {
        instance = new ComponentCompetitionAveragePredictor(new Predictor[0]);

        assertEquals("Predictors should be correct.", 0, instance.getPredictors().length);
    }

    /**
     * <p>
     * Tests accuracy of <code>ComponentCompetitionAveragePredictor()</code> constructor.<br>
     * Instance should be correctly created.
     * </p>
     */
    public void testCtor2() {
        instance = new ComponentCompetitionAveragePredictor();

        assertEquals("Predictors should be correct.", 0, instance.getPredictors().length);
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
    public void testIsFullTrainingSupported1() {
        assertFalse("'isFullTrainingSupported' should be correct.", instance.isFullTrainingSupported());
    }

    /**
     * <p>
     * Tests accuracy of <code>isFullTrainingSupported()</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testIsFullTrainingSupported2() {
        instance.setPredictors(null);

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
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testPerformFullTraining11() throws Exception {
        predictors = new Predictor[2];
        predictors[0] = new MockComponentCompetitionPredictor(3.0, true, true, true);
        predictors[1] = new MockComponentCompetitionPredictor(1.0, true, true, true);
        instance.setPredictors(predictors);

        List<ComponentCompetitionSituation> situations = new ArrayList<ComponentCompetitionSituation>();
        situations.add(new ComponentCompetitionSituation());

        instance.performFullTraining(situations);

        MockComponentCompetitionPredictor predictor = (MockComponentCompetitionPredictor) predictors[0];
        assertEquals("performFullTraining should be correct.", "performFullTraining1|3.0", predictor.getMessage());
        predictor = (MockComponentCompetitionPredictor) predictors[1];
        assertEquals("performFullTraining should be correct.", "performFullTraining1|1.0", predictor.getMessage());
    }

    /**
     * <p>
     * Tests accuracy of <code>performFullTraining(List&lt;ComponentCompetitionSituation&gt;)</code> method.<br>
     * Result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testPerformFullTraining12() throws Exception {
        predictors = new Predictor[2];
        predictors[0] = new MockComponentCompetitionPredictor(3.0, false, true, true);
        predictors[1] = new MockComponentCompetitionPredictor(1.0, false, true, true);
        instance.setPredictors(predictors);

        List<ComponentCompetitionSituation> situations = new ArrayList<ComponentCompetitionSituation>();
        situations.add(new ComponentCompetitionSituation());

        instance.performFullTraining(situations);

        MockComponentCompetitionPredictor predictor = (MockComponentCompetitionPredictor) predictors[0];
        assertNull("performFullTraining should be correct.", predictor.getMessage());
        predictor = (MockComponentCompetitionPredictor) predictors[1];
        assertNull("performFullTraining should be correct.", predictor.getMessage());
    }

    /**
     * <p>
     * Tests accuracy of <code>performFullTraining(List&lt;ComponentCompetitionSituation&gt;,
     * List&lt;ComponentCompetitionFulfillmentPrediction&gt;)</code> method.<br>
     * Result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testPerformFullTraining21() throws Exception {
        predictors = new Predictor[2];
        predictors[0] = new MockComponentCompetitionPredictor(3.0, true, true, true);
        predictors[1] = new MockComponentCompetitionPredictor(1.0, true, true, true);
        instance.setPredictors(predictors);

        List<ComponentCompetitionSituation> situations = new ArrayList<ComponentCompetitionSituation>();
        situations.add(new ComponentCompetitionSituation());

        List<ComponentCompetitionFulfillmentPrediction> predictions =
            new ArrayList<ComponentCompetitionFulfillmentPrediction>();
        predictions.add(new ComponentCompetitionFulfillmentPrediction(1.0, new ComponentCompetitionSituation(),
            new ComponentCompetitionPredictor()));

        instance.performFullTraining(situations, predictions);

        MockComponentCompetitionPredictor predictor = (MockComponentCompetitionPredictor) predictors[0];
        assertEquals("performFullTraining should be correct.", "performFullTraining2|3.0", predictor.getMessage());
        predictor = (MockComponentCompetitionPredictor) predictors[1];
        assertEquals("performFullTraining should be correct.", "performFullTraining2|1.0", predictor.getMessage());
    }

    /**
     * <p>
     * Tests accuracy of <code>performFullTraining(List&lt;ComponentCompetitionSituation&gt;,
     * List&lt;ComponentCompetitionSituation&gt;)</code> method.<br>
     * Result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testPerformFullTraining22() throws Exception {
        predictors = new Predictor[2];
        predictors[0] = new MockComponentCompetitionPredictor(3.0, true, true, true);
        predictors[1] = new MockComponentCompetitionPredictor(1.0, false, true, true);
        instance.setPredictors(predictors);

        List<ComponentCompetitionSituation> situations = new ArrayList<ComponentCompetitionSituation>();
        situations.add(new ComponentCompetitionSituation());

        List<ComponentCompetitionFulfillmentPrediction> predictions =
            new ArrayList<ComponentCompetitionFulfillmentPrediction>();
        predictions.add(new ComponentCompetitionFulfillmentPrediction(1.0, new ComponentCompetitionSituation(),
            new ComponentCompetitionPredictor()));

        instance.performFullTraining(situations, predictions);

        MockComponentCompetitionPredictor predictor = (MockComponentCompetitionPredictor) predictors[0];
        assertEquals("performFullTraining should be correct.", "performFullTraining2|3.0", predictor.getMessage());
        predictor = (MockComponentCompetitionPredictor) predictors[1];
        assertNull("performFullTraining should be correct.", predictor.getMessage());
    }

    /**
     * <p>
     * Tests accuracy of <code>performIncrementalTraining(ComponentCompetitionSituation)</code> method.<br>
     * Result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testPerformIncrementalTraining11() throws Exception {
        predictors = new Predictor[2];
        predictors[0] = new MockComponentCompetitionPredictor(3.0, true, true, true);
        predictors[1] = new MockComponentCompetitionPredictor(1.0, true, true, true);
        instance.setPredictors(predictors);

        instance.performIncrementalTraining(new ComponentCompetitionSituation());

        MockComponentCompetitionPredictor predictor = (MockComponentCompetitionPredictor) predictors[0];
        assertEquals("performIncrementalTraining should be correct.",
            "performIncrementalTraining1|3.0", predictor.getMessage());
        predictor = (MockComponentCompetitionPredictor) predictors[1];
        assertEquals("performIncrementalTraining should be correct.",
            "performIncrementalTraining1|1.0", predictor.getMessage());
    }

    /**
     * <p>
     * Tests accuracy of <code>performIncrementalTraining(ComponentCompetitionSituation)</code> method.<br>
     * Result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testPerformIncrementalTraining12() throws Exception {
        predictors = new Predictor[2];
        predictors[0] = new MockComponentCompetitionPredictor(3.0, true, false, true);
        predictors[1] = new MockComponentCompetitionPredictor(1.0, true, false, true);
        instance.setPredictors(predictors);

        instance.performIncrementalTraining(new ComponentCompetitionSituation());

        MockComponentCompetitionPredictor predictor = (MockComponentCompetitionPredictor) predictors[0];
        assertNull("performIncrementalTraining should be correct.", predictor.getMessage());
        predictor = (MockComponentCompetitionPredictor) predictors[1];
        assertNull("performIncrementalTraining should be correct.", predictor.getMessage());
    }

    /**
     * <p>
     * Tests accuracy of
     * <code>performIncrementalTraining(ComponentCompetitionSituation, ComponentCompetitionFulfillmentPrediction)</code>
     * method.<br>
     * Result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testPerformIncrementalTraining21() throws Exception {
        predictors = new Predictor[2];
        predictors[0] = new MockComponentCompetitionPredictor(3.0, true, true, true);
        predictors[1] = new MockComponentCompetitionPredictor(1.0, true, true, true);
        instance.setPredictors(predictors);

        instance.performIncrementalTraining(new ComponentCompetitionSituation(),
            new ComponentCompetitionFulfillmentPrediction(1.0, new ComponentCompetitionSituation(),
                new ComponentCompetitionPredictor()));

        MockComponentCompetitionPredictor predictor = (MockComponentCompetitionPredictor) predictors[0];
        assertEquals("performIncrementalTraining should be correct.",
            "performIncrementalTraining2|3.0", predictor.getMessage());
        predictor = (MockComponentCompetitionPredictor) predictors[1];
        assertEquals("performIncrementalTraining should be correct.",
            "performIncrementalTraining2|1.0", predictor.getMessage());
    }

    /**
     * <p>
     * Tests accuracy of
     * <code>performIncrementalTraining(ComponentCompetitionSituation, ComponentCompetitionFulfillmentPrediction)</code>
     * method.<br>
     * Result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testPerformIncrementalTraining22() throws Exception {
        predictors = new Predictor[0];
        instance.setPredictors(predictors);

        instance.performIncrementalTraining(new ComponentCompetitionSituation(),
            new ComponentCompetitionFulfillmentPrediction(1.0, new ComponentCompetitionSituation(),
                new ComponentCompetitionPredictor()));

        // Good
    }

    /**
     * <p>
     * Tests accuracy of <code>predict(ComponentCompetitionSituation)</code> method.<br>
     * Result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testPredict1() throws Exception {
        ComponentCompetitionSituation situation = createSituation();

        ComponentCompetitionFulfillmentPrediction prediction = instance.predict(situation);

        assertEquals("'predict' should be correct.", 2.4, prediction.getExpectedPassedReviewSubmissionCount(), 0.01);
        assertSame("'predict' should be correct.", situation, prediction.getSituation());
        assertSame("'predict' should be correct.", instance, prediction.getPredictor());
    }

    /**
     * <p>
     * Tests accuracy of <code>predict(ComponentCompetitionSituation)</code> method.<br>
     * Result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testPredict2() throws Exception {
        instance.setPredictors(new Predictor[]{predictors[0]});

        ComponentCompetitionSituation situation = createSituation();

        ComponentCompetitionFulfillmentPrediction prediction = instance.predict(situation);

        assertEquals("'predict' should be correct.", 2.4, prediction.getExpectedPassedReviewSubmissionCount());
        assertSame("'predict' should be correct.", situation, prediction.getSituation());
        assertSame("'predict' should be correct.", instance, prediction.getPredictor());
    }

    /**
     * <p>
     * Tests accuracy of <code>getPredictors()</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testGetPredictors1() {
        Predictor<ComponentCompetitionSituation, ComponentCompetitionFulfillmentPrediction>[] res =
            instance.getPredictors();

        assertEquals("'getPredictors' should be correct.", 1, res.length);

        // Copy
        assertTrue("'getPredictors' should be correct.", predictors != res);

        assertTrue("'getPredictors' should be correct.", predictors[0] == res[0]);
    }

    /**
     * <p>
     * Tests accuracy of <code>getPredictors()</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testGetPredictors2() {
        instance.setPredictors(null);

        Predictor<ComponentCompetitionSituation, ComponentCompetitionFulfillmentPrediction>[] res =
            instance.getPredictors();

        assertEquals("'getPredictors' should be correct.", 0, res.length);
    }

    /**
     * <p>
     * Tests accuracy of <code>setPredictors(Predictor&lt;ComponentCompetitionSituation,
     * ComponentCompetitionFulfillmentPrediction&gt;[])</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testSetPredictors1() {
        instance = new ComponentCompetitionAveragePredictor();
        instance.setPredictors(predictors);

        Predictor<ComponentCompetitionSituation, ComponentCompetitionFulfillmentPrediction>[] res =
            instance.getPredictors();

        assertEquals("'setPredictors' should be correct.", 1, res.length);
    }

    /**
     * <p>
     * Tests accuracy of <code>setPredictors(Predictor&lt;ComponentCompetitionSituation,
     * ComponentCompetitionFulfillmentPrediction&gt;[])</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testSetPredictors2() {
        instance.setPredictors(null);

        Predictor<ComponentCompetitionSituation, ComponentCompetitionFulfillmentPrediction>[] res =
            instance.getPredictors();

        assertEquals("'setPredictors' should be correct.", 0, res.length);
    }

    /**
     * <p>
     * Tests accuracy of <code>clone()</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testClone1() {
        ComponentCompetitionAveragePredictor clonedObj = (ComponentCompetitionAveragePredictor) instance.clone();

        assertNotNull("'clone' should be correct.", clonedObj);
        assertEquals("'clone' should be correct.", 1, clonedObj.getPredictors().length);
        // Clone
        assertTrue("'clone' should be correct.", predictors[0] != clonedObj.getPredictors()[0]);
    }

    /**
     * <p>
     * Tests accuracy of <code>clone()</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testClone2() {
        instance = new ComponentCompetitionAveragePredictor();
        ComponentCompetitionAveragePredictor clonedObj = (ComponentCompetitionAveragePredictor) instance.clone();

        assertNotNull("'clone' should be correct.", clonedObj);
        assertEquals("'clone' should be correct.", 0, clonedObj.getPredictors().length);
    }

    /**
     * <p>
     * Constructs the situation for tests..
     * </p>
     *
     * @return the created situation.
     */
    private ComponentCompetitionSituation createSituation() {
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

        return situation;
    }
}
