/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.predictor.impl.componentcompetition;

import junit.framework.TestCase;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;

import com.topcoder.predictor.Predictor;

/**
 * <p>
 * Unit tests for ComponentCompetitionAveragePredictor class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ComponentCompetitionAveragePredictorUnitTests extends TestCase {

    /**
     * An instance of ComponentCompetitionAveragePredictor for the following tests.
     */
    private ComponentCompetitionAveragePredictor tester = null;

    /**
     * Another instance of ComponentCompetitionAveragePredictor for the following tests.
     */
    private ComponentCompetitionAveragePredictor tester2 = null;

    /**
     * Initialize the environment.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        Predictor<ComponentCompetitionSituation, ComponentCompetitionFulfillmentPrediction>[] predictors =
            new ComponentCompetitionPredictor[] {
                new ComponentCompetitionPredictor(), new MockComponentCompetitionPredictor()};
        tester = new ComponentCompetitionAveragePredictor(predictors);
        Predictor<ComponentCompetitionSituation, ComponentCompetitionFulfillmentPrediction>[] predictors2 =
            new ComponentCompetitionPredictor[] {new ComponentCompetitionPredictor()};
        tester2 = new ComponentCompetitionAveragePredictor(predictors2);
    }

    /**
     * Clear the test environment.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void tearDown() throws Exception {
        tester = null;
        tester2 = null;
    }

    /**
     * <p>
     * Accuracy test for the first constructor. No exception is thrown.
     * </p>
     */
    public void test_ctor1() {
        // no exception
        tester = new ComponentCompetitionAveragePredictor();
        assertEquals("There should be no predictors.", 0, tester.getPredictors().length);
    }

    /**
     * <p>
     * Accuracy test for the second constructor. No exception is thrown for valid predictors.
     * And the predictors should be set properly.
     * </p>
     */
    public void test_ctor2_1() {
        // tester and tester2 are already created with this constructor,
        // we can simply verify the result
        assertEquals("There should be two predictors.", 2, tester.getPredictors().length);
        assertEquals("There should be one predictor.", 1, tester2.getPredictors().length);
    }

    /**
     * <p>
     * Accuracy test for the second constructor. No exception is thrown for null argument.
     * </p>
     */
    public void test_ctor2_2() {
        // no exception
        tester = new ComponentCompetitionAveragePredictor(null);
        assertEquals("There should be no predictors.", 0, tester.getPredictors().length);
    }

    /**
     * <p>
     * Failure test for the second constructor. IllegalArgumentException should be thrown if there is null predictor.
     * </p>
     */
    public void test_ctor2_failure() {
        try {
            new ComponentCompetitionAveragePredictor(new ComponentCompetitionPredictor[]{null});
            fail("IllegalArgumentException should be thrown if there is null predictor.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Accuracy test for the setPredictors method. Predictors should be properly set.
     * </p>
     */
    public void test_setPredictors_1() {
        ComponentCompetitionPredictor[] predictors = new ComponentCompetitionPredictor[]{
            new ComponentCompetitionPredictor()};
        tester.setPredictors(predictors);
        assertEquals("There should be one predictor.", 1, tester.getPredictors().length);
    }

    /**
     * <p>
     * Failure test for the setPredictors method.
     * IllegalArgumentException should be thrown if there is null predictor.
     * </p>
     */
    public void test_setPredictors_failure() {
        try {
            tester.setPredictors(new ComponentCompetitionPredictor[]{null});
            fail("IllegalArgumentException should be thrown if there is null predictor.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Accuracy test for the getSituationName method. Returned value should be as expected.
     * </p>
     */
    public void test_getSituationName() {
        assertEquals("The getSituationName returns incorrect value.", ComponentCompetitionSituation.class.getName(),
                        tester.getSituationName());
    }

    /**
     * <p>
     * Accuracy test for the getPredictionName method. Returned value should be as expected.
     * </p>
     */
    public void test_getPredictionName() {
        assertEquals("The getPredictionName returns incorrect value.", ComponentCompetitionFulfillmentPrediction.class
                        .getName(), tester.getPredictionName());
    }

    /**
     * <p>
     * Accuracy test for the isFullTrainingSupported method. Returned value should be true because there is a predictor
     * that supports full training.
     * </p>
     */
    public void test_isFullTrainingSupported1() {
        assertTrue("The isFullTrainingSupported returns incorrect value.", tester.isFullTrainingSupported());
    }

    /**
     * <p>
     * Accuracy test for the isFullTrainingSupported method. Returned value should be false because there is no
     * predictor that supports full training.
     * </p>
     */
    public void test_isFullTrainingSupported2() {
        assertFalse("The isFullTrainingSupported returns incorrect value.", tester2.isFullTrainingSupported());
    }

    /**
     * <p>
     * Accuracy test for the isIncrementalTrainingSupported method. Returned value should be false because there is a
     * predictor that supports full training.
     * </p>
     */
    public void test_isIncrementalTrainingSupported1() {
        assertTrue("The isIncrementalTrainingSupported returns incorrect value.", tester
                        .isIncrementalTrainingSupported());
    }

    /**
     * <p>
     * Accuracy test for the isIncrementalTrainingSupported method. Returned value should be false because there is no
     * predictor that supports full training.
     * </p>
     */
    public void test_isIncrementalTrainingSupported2() {
        assertFalse("The isIncrementalTrainingSupported returns incorrect value.", tester2
                        .isIncrementalTrainingSupported());
    }

    /**
     * <p>
     * Accuracy test for the isReadyToOperate method. Returned value should be false because there is a predictor that
     * is not ready.
     * </p>
     */
    public void test_isReadyToOperate1() {
        Predictor<ComponentCompetitionSituation, ComponentCompetitionFulfillmentPrediction>[] predictors =
            new ComponentCompetitionPredictor[] {
                new ComponentCompetitionPredictor(), new MockComponentCompetitionPredictor(false)};
        tester = new ComponentCompetitionAveragePredictor(predictors);
        assertFalse("The isReadyToOperate returns incorrect value.", tester.isReadyToOperate());
    }

    /**
     * <p>
     * Accuracy test for the isReadyToOperate method. Returned value should be true because all predictors are ready.
     * </p>
     */
    public void test_isReadyToOperate2() {
        assertTrue("The isReadyToOperate returns incorrect value.", tester.isReadyToOperate());
    }

    /**
     * <p>
     * Accuracy test for the putCapability method. No exception is thrown for valid arguments.
     * </p>
     */
    public void test_putCapability() {
        // no exception
        tester.putCapability("key", "value");
    }

    /**
     * <p>
     * Accuracy test for the getCapability method. No exception is thrown for valid argument, and null should be
     * returned.
     * </p>
     */
    public void test_getCapability() {
        // no exception
        String value = tester.getCapability("key");
        assertNull("Result should be null.", value);
    }

    /**
     * <p>
     * Accuracy test for the getCapabilityKeys method. Empty set should be returned.
     * </p>
     */
    public void test_getCapabilityKeys() {
        // no exception
        Set set = tester.getCapabilityKeys();
        assertEquals("Result should be empty set.", 0, set.size());
    }

    /**
     * <p>
     * Accuracy test for the removeCapability method. No exception is thrown for valid argument.
     * </p>
     */
    public void test_removeCapability() {
        // no exception
        tester.removeCapability("key");
    }

    /**
     * <p>
     * Accuracy test for the clearCapabilities method. No exception is thrown for valid argument.
     * </p>
     */
    public void test_clearCapabilities() {
        // no exception
        tester.clearCapabilities();
    }

    /**
     * <p>
     * Accuracy test for the first performFullTraining method. No exception is thrown for valid arguments.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_performFullTraining1() throws Exception {
        List<ComponentCompetitionSituation> situations = new ArrayList<ComponentCompetitionSituation>();
        situations.add(new ComponentCompetitionSituation());
        // no exception
        tester.performFullTraining(situations);
    }

    /**
     * <p>
     * Accuracy test for the second performFullTraining method. No exception is thrown for valid arguments.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_performFullTraining2() throws Exception {
        List<ComponentCompetitionSituation> situations = new ArrayList<ComponentCompetitionSituation>();
        situations.add(new ComponentCompetitionSituation());
        List<ComponentCompetitionFulfillmentPrediction> predictions =
            new ArrayList<ComponentCompetitionFulfillmentPrediction>();
        predictions.add(new ComponentCompetitionFulfillmentPrediction(1.1, new ComponentCompetitionSituation(),
                        new ComponentCompetitionAveragePredictor()));
        // no exception
        tester.performFullTraining(situations, predictions);
    }

    /**
     * <p>
     * Accuracy test for the first performIncrementalTraining method. No exception is thrown for valid arguments.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_performIncrementalTraining1() throws Exception {
        // no exception
        tester.performIncrementalTraining(new ComponentCompetitionSituation());
    }

    /**
     * <p>
     * Accuracy test for the second performIncrementalTraining method. No exception is thrown for valid arguments.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_performIncrementalTraining2() throws Exception {
        // no exception
        tester.performIncrementalTraining(new ComponentCompetitionSituation(),
                        new ComponentCompetitionFulfillmentPrediction(1.1, new ComponentCompetitionSituation(),
                                        new ComponentCompetitionAveragePredictor()));
    }

    /**
     * <p>
     * Accuracy test for the predict method. Result should be as expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_predict1() throws Exception {
        // prepare situation to predict
        ComponentCompetitionSituation situation = new ComponentCompetitionSituation();
        List<Participant> participants = new ArrayList<Participant>();
        Participant p = new Participant();
        p.setReliability(0.1);
        participants.add(p);
        p = new Participant();
        p.setReliability(0.5);
        participants.add(p);
        situation.setParticipants(participants);
        // do prediction
        ComponentCompetitionFulfillmentPrediction prediction = tester.predict(situation);
        // verify result
        // the first predictor predicts 0.6, the second predictor predicts 2.0, the average is 1.3
        assertEquals("The expected passed review submission count is incorrect.", 1.3, prediction
                        .getExpectedPassedReviewSubmissionCount());
        assertEquals("The situation is incorrect.", situation, prediction.getSituation());
        assertEquals("The predictor is incorrect.", tester, prediction.getPredictor());
    }

    /**
     * <p>
     * Accuracy test for the predict method. Result should be as expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_predict2() throws Exception {
        // prepare situation to predict
        ComponentCompetitionSituation situation = new ComponentCompetitionSituation();
        List<Participant> participants = new ArrayList<Participant>();
        Participant p = new Participant();
        p.setReliability(0.1);
        participants.add(p);
        p = new Participant();
        p.setReliability(0.5);
        participants.add(p);
        situation.setParticipants(participants);
        // create predictor
        Predictor<ComponentCompetitionSituation, ComponentCompetitionFulfillmentPrediction>[] predictors =
            new ComponentCompetitionPredictor[] {
                new ComponentCompetitionPredictor(), new MockComponentCompetitionPredictor(),
                new MockComponentCompetitionPredictor()};
        tester = new ComponentCompetitionAveragePredictor(predictors);
        // do prediction
        ComponentCompetitionFulfillmentPrediction prediction = tester.predict(situation);
        // verify result
        // the first predictor predicts 0.6, the second predictor predicts 2.0,
        // the third predictor predicts 2.0, the min and max values are removed,
        // then only 2.0 is left, so result is 2.0
        assertEquals("The expected passed review submission count is incorrect.", 2.0, prediction
                        .getExpectedPassedReviewSubmissionCount());
        assertEquals("The situation is incorrect.", situation, prediction.getSituation());
        assertEquals("The predictor is incorrect.", tester, prediction.getPredictor());
    }

    /**
     * <p>
     * Accuracy test for the clone method, the cloned object should be type of ComponentCompetitionAveragePredictor.
     * </p>
     */
    public void test_clone() {
        Object obj = tester.clone();
        assertTrue("Cloned object should be type of ComponentCompetitionAveragePredictor.",
                        obj instanceof ComponentCompetitionAveragePredictor);
        assertEquals("It should contain two predictors.", 2, ((ComponentCompetitionAveragePredictor) obj)
                        .getPredictors().length);
    }

    /**
     * <p>
     * Failure test for the putCategory method. IllegalArgumentException is thrown for null argument.
     * </p>
     */
    public void test_putCategory_failure1() {
        try {
            tester.putCapability(null, "value");
            fail("IllegalArgumentException is thrown for null argument.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test for the putCategory method. IllegalArgumentException is thrown for null argument.
     * </p>
     */
    public void test_putCategory_failure2() {
        try {
            tester.putCapability("key", null);
            fail("IllegalArgumentException is thrown for null argument.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test for the putCategory method. IllegalArgumentException is thrown for empty string argument.
     * </p>
     */
    public void test_putCategory_failure3() {
        try {
            tester.putCapability(" ", "value");
            fail("IllegalArgumentException is thrown for empty string argument.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test for the putCategory method. IllegalArgumentException is thrown for empty string argument.
     * </p>
     */
    public void test_putCategory_failure4() {
        try {
            tester.putCapability("key", "  ");
            fail("IllegalArgumentException is thrown for empty string argument.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test for the getCategory method. IllegalArgumentException is thrown for null argument.
     * </p>
     */
    public void test_getCategory_failure1() {
        try {
            tester.getCapability(null);
            fail("IllegalArgumentException is thrown for null argument.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test for the getCategory method. IllegalArgumentException is thrown for empty string argument.
     * </p>
     */
    public void test_getCategory_failure2() {
        try {
            tester.getCapability("");
            fail("IllegalArgumentException is thrown for empty string argument.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test for the removeCategory method. IllegalArgumentException is thrown for null argument.
     * </p>
     */
    public void test_removeCategory_failure1() {
        try {
            tester.removeCapability(null);
            fail("IllegalArgumentException is thrown for null argument.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test for the removeCategory method. IllegalArgumentException is thrown for empty string argument.
     * </p>
     */
    public void test_removeCategory_failure2() {
        try {
            tester.removeCapability("");
            fail("IllegalArgumentException is thrown for empty string argument.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test for the first performFullTraining method. IllegalArgumentException is thrown for null argument.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_performFullTraining1_failure1() throws Exception {
        try {
            tester.performFullTraining(null);
            fail("IllegalArgumentException is thrown for null argument.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test for the first performFullTraining method. IllegalArgumentException is thrown for empty list.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_performFullTraining1_failure2() throws Exception {
        try {
            tester.performFullTraining(new ArrayList<ComponentCompetitionSituation>());
            fail("IllegalArgumentException is thrown for empty list argument.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test for the first performFullTraining method. IllegalArgumentException is thrown for null element.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_performFullTraining1_failure3() throws Exception {
        List<ComponentCompetitionSituation> situations = new ArrayList<ComponentCompetitionSituation>();
        situations.add(null);
        try {
            tester.performFullTraining(situations);
            fail("IllegalArgumentException is thrown for null element.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test for the second performFullTraining method. IllegalArgumentException is thrown for null argument.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_performFullTraining2_failure1() throws Exception {
        List<ComponentCompetitionFulfillmentPrediction> predictions =
            new ArrayList<ComponentCompetitionFulfillmentPrediction>();
        predictions.add(new ComponentCompetitionFulfillmentPrediction(1.1, new ComponentCompetitionSituation(),
            new ComponentCompetitionAveragePredictor()));
        try {
            tester.performFullTraining(null, predictions);
            fail("IllegalArgumentException is thrown for null argument.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test for the second performFullTraining method. IllegalArgumentException is thrown for empty list
     * argument.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_performFullTraining2_failure2() throws Exception {
        List<ComponentCompetitionFulfillmentPrediction> predictions =
            new ArrayList<ComponentCompetitionFulfillmentPrediction>();
        predictions.add(new ComponentCompetitionFulfillmentPrediction(1.1, new ComponentCompetitionSituation(),
            new ComponentCompetitionAveragePredictor()));
        try {
            tester.performFullTraining(new ArrayList<ComponentCompetitionSituation>(), predictions);
            fail("IllegalArgumentException is thrown for empty list argument.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test for the second performFullTraining method. IllegalArgumentException is thrown for list contains null
     * element.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_performFullTraining2_failure3() throws Exception {
        List<ComponentCompetitionSituation> situations = new ArrayList<ComponentCompetitionSituation>();
        situations.add(null);
        List<ComponentCompetitionFulfillmentPrediction> predictions =
            new ArrayList<ComponentCompetitionFulfillmentPrediction>();
        predictions.add(new ComponentCompetitionFulfillmentPrediction(1.1, new ComponentCompetitionSituation(),
            new ComponentCompetitionAveragePredictor()));
        try {
            tester.performFullTraining(situations, predictions);
            fail("IllegalArgumentException is thrown for list containing null element.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test for the second performFullTraining method. IllegalArgumentException is thrown for null argument.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_performFullTraining2_failure4() throws Exception {
        List<ComponentCompetitionSituation> situations = new ArrayList<ComponentCompetitionSituation>();
        situations.add(new ComponentCompetitionSituation());
        try {
            tester.performFullTraining(situations, null);
            fail("IllegalArgumentException is thrown for null argument.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test for the second performFullTraining method. IllegalArgumentException is thrown for empty list
     * argument.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_performFullTraining2_failure5() throws Exception {
        List<ComponentCompetitionSituation> situations = new ArrayList<ComponentCompetitionSituation>();
        situations.add(new ComponentCompetitionSituation());
        List<ComponentCompetitionFulfillmentPrediction> predictions =
            new ArrayList<ComponentCompetitionFulfillmentPrediction>();
        try {
            tester.performFullTraining(situations, predictions);
            fail("IllegalArgumentException is thrown for empty list argument.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test for the second performFullTraining method. IllegalArgumentException is thrown for list contains null
     * element.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_performFullTraining2_failure6() throws Exception {
        List<ComponentCompetitionSituation> situations = new ArrayList<ComponentCompetitionSituation>();
        situations.add(new ComponentCompetitionSituation());
        List<ComponentCompetitionFulfillmentPrediction> predictions =
            new ArrayList<ComponentCompetitionFulfillmentPrediction>();
        predictions.add(null);
        try {
            tester.performFullTraining(situations, predictions);
            fail("IllegalArgumentException is thrown for list containing null element.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test for the second performFullTraining method. IllegalArgumentException is thrown if the two lists do
     * not have same sizes.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_performFullTraining2_failure7() throws Exception {
        List<ComponentCompetitionSituation> situations = new ArrayList<ComponentCompetitionSituation>();
        situations.add(new ComponentCompetitionSituation());
        situations.add(new ComponentCompetitionSituation());
        List<ComponentCompetitionFulfillmentPrediction> predictions =
            new ArrayList<ComponentCompetitionFulfillmentPrediction>();
        predictions.add(new ComponentCompetitionFulfillmentPrediction(1.1, new ComponentCompetitionSituation(),
            new ComponentCompetitionAveragePredictor()));
        try {
            tester.performFullTraining(situations, predictions);
            fail("IllegalArgumentException is thrown if the two lists do not have same sizes.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test for the first performIncrementalTraining method. IllegalArgumentException is thrown for null
     * argument.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_performIncrementalTraining1_failure() throws Exception {
        try {
            tester.performIncrementalTraining(null);
            fail("IllegalArgumentException is thrown for null argument.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test for the second performIncrementalTraining method. IllegalArgumentException is thrown for null
     * argument.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_performIncrementalTraining2_failure1() throws Exception {
        try {
            tester.performIncrementalTraining(null, new ComponentCompetitionFulfillmentPrediction(1.1,
                            new ComponentCompetitionSituation(), new ComponentCompetitionAveragePredictor()));
            fail("IllegalArgumentException is thrown for null argument.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test for the second performIncrementalTraining method. IllegalArgumentException is thrown for null
     * argument.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_performIncrementalTraining2_failure2() throws Exception {
        try {
            tester.performIncrementalTraining(new ComponentCompetitionSituation(), null);
            fail("IllegalArgumentException is thrown for null argument.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test for the predict method. IllegalArgumentException is thrown for null argument.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_predict_failure() throws Exception {
        try {
            tester.predict(null);
            fail("IllegalArgumentException is thrown for null argument.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

}
