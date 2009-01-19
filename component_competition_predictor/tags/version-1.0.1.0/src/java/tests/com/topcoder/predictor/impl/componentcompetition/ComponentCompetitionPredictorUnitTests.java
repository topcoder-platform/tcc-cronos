/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.predictor.impl.componentcompetition;

import junit.framework.TestCase;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;

/**
 * <p>
 * Unit tests for ComponentCompetitionPredictor class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ComponentCompetitionPredictorUnitTests extends TestCase {

    /**
     * An instance of ComponentCompetitionPredictor for the following tests.
     */
    private ComponentCompetitionPredictor tester = null;

    /**
     * Initialize the environment.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        tester = new ComponentCompetitionPredictor();
    }

    /**
     * Clear the test environment.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void tearDown() throws Exception {
        tester = null;
    }

    /**
     * <p>
     * Accuracy test for the constructor. No exception is thrown.
     * </p>
     */
    public void test_ctor() {
        // no exception
        new ComponentCompetitionPredictor();
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
     * Accuracy test for the isFullTrainingSupported method. Returned value should be false.
     * </p>
     */
    public void test_isFullTrainingSupported() {
        assertFalse("The isFullTrainingSupported returns incorrect value.", tester.isFullTrainingSupported());
    }

    /**
     * <p>
     * Accuracy test for the isIncrementalTrainingSupported method. Returned value should be false.
     * </p>
     */
    public void test_isIncrementalTrainingSupported() {
        assertFalse("The isIncrementalTrainingSupported returns incorrect value.", tester
                        .isIncrementalTrainingSupported());
    }

    /**
     * <p>
     * Accuracy test for the isReadyToOperate method. Returned value should be true.
     * </p>
     */
    public void test_isReadyToOperate() {
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
     */
    public void test_performFullTraining1() {
        List<ComponentCompetitionSituation> situations = new ArrayList<ComponentCompetitionSituation>();
        situations.add(new ComponentCompetitionSituation());
        // no exception
        tester.performFullTraining(situations);
    }

    /**
     * <p>
     * Accuracy test for the second performFullTraining method. No exception is thrown for valid arguments.
     * </p>
     */
    public void test_performFullTraining2() {
        List<ComponentCompetitionSituation> situations = new ArrayList<ComponentCompetitionSituation>();
        situations.add(new ComponentCompetitionSituation());
        List<ComponentCompetitionFulfillmentPrediction> predictions =
            new ArrayList<ComponentCompetitionFulfillmentPrediction>();
        predictions.add(new ComponentCompetitionFulfillmentPrediction(1.1, new ComponentCompetitionSituation(),
            new ComponentCompetitionPredictor()));
        // no exception
        tester.performFullTraining(situations, predictions);
    }

    /**
     * <p>
     * Accuracy test for the first performIncrementalTraining method. No exception is thrown for valid arguments.
     * </p>
     */
    public void test_performIncrementalTraining1() {
        // no exception
        tester.performIncrementalTraining(new ComponentCompetitionSituation());
    }

    /**
     * <p>
     * Accuracy test for the second performIncrementalTraining method. No exception is thrown for valid arguments.
     * </p>
     */
    public void test_performIncrementalTraining2() {
        // no exception
        tester.performIncrementalTraining(new ComponentCompetitionSituation(),
                        new ComponentCompetitionFulfillmentPrediction(1.1, new ComponentCompetitionSituation(),
                                        new ComponentCompetitionPredictor()));
    }

    /**
     * <p>
     * Accuracy test for the predict method. Result should be as expected.
     * </p>
     */
    public void test_predict() {
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
        assertEquals("The expected passed review submission count is incorrect.", 0.6, prediction
                        .getExpectedPassedReviewSubmissionCount());
        assertEquals("The situation is incorrect.", situation, prediction.getSituation());
        assertEquals("The predictor is incorrect.", tester, prediction.getPredictor());
    }

    /**
     * <p>
     * Accuracy test for the clone method, the cloned object should be type of ComponentCompetitionPredictor.
     * </p>
     */
    public void test_clone() {
        Object obj = tester.clone();
        assertTrue("Cloned object should be type of ComponentCompetitionPredictor.",
                        obj instanceof ComponentCompetitionPredictor);
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
     */
    public void test_performFullTraining1_failure1() {
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
     */
    public void test_performFullTraining1_failure2() {
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
     */
    public void test_performFullTraining1_failure3() {
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
     */
    public void test_performFullTraining2_failure1() {
        List<ComponentCompetitionFulfillmentPrediction> predictions =
            new ArrayList<ComponentCompetitionFulfillmentPrediction>();
        predictions.add(new ComponentCompetitionFulfillmentPrediction(1.1, new ComponentCompetitionSituation(),
            new ComponentCompetitionPredictor()));
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
     */
    public void test_performFullTraining2_failure2() {
        List<ComponentCompetitionFulfillmentPrediction> predictions =
            new ArrayList<ComponentCompetitionFulfillmentPrediction>();
        predictions.add(new ComponentCompetitionFulfillmentPrediction(1.1, new ComponentCompetitionSituation(),
            new ComponentCompetitionPredictor()));
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
     */
    public void test_performFullTraining2_failure3() {
        List<ComponentCompetitionSituation> situations = new ArrayList<ComponentCompetitionSituation>();
        situations.add(null);
        List<ComponentCompetitionFulfillmentPrediction> predictions =
            new ArrayList<ComponentCompetitionFulfillmentPrediction>();
        predictions.add(new ComponentCompetitionFulfillmentPrediction(1.1, new ComponentCompetitionSituation(),
            new ComponentCompetitionPredictor()));
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
     */
    public void test_performFullTraining2_failure4() {
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
     */
    public void test_performFullTraining2_failure5() {
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
     */
    public void test_performFullTraining2_failure6() {
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
     */
    public void test_performFullTraining2_failure7() {
        List<ComponentCompetitionSituation> situations = new ArrayList<ComponentCompetitionSituation>();
        situations.add(new ComponentCompetitionSituation());
        situations.add(new ComponentCompetitionSituation());
        List<ComponentCompetitionFulfillmentPrediction> predictions =
            new ArrayList<ComponentCompetitionFulfillmentPrediction>();
        predictions.add(new ComponentCompetitionFulfillmentPrediction(1.1, new ComponentCompetitionSituation(),
            new ComponentCompetitionPredictor()));
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
     */
    public void test_performIncrementalTraining1_failure() {
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
     */
    public void test_performIncrementalTraining2_failure1() {
        try {
            tester.performIncrementalTraining(null, new ComponentCompetitionFulfillmentPrediction(1.1,
                            new ComponentCompetitionSituation(), new ComponentCompetitionPredictor()));
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
     */
    public void test_performIncrementalTraining2_failure2() {
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
     */
    public void test_predict_failure() {
        try {
            tester.predict(null);
            fail("IllegalArgumentException is thrown for null argument.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

}
