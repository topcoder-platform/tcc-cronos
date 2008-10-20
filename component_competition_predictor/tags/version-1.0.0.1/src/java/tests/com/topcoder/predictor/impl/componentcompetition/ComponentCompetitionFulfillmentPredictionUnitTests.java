/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.predictor.impl.componentcompetition;

import com.topcoder.predictor.Prediction;
import com.topcoder.predictor.Predictor;
import com.topcoder.predictor.Situation;

import junit.framework.TestCase;

/**
 * <p>
 * Unit tests for ComponentCompetitionFulfillmentPrediction class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ComponentCompetitionFulfillmentPredictionUnitTests extends TestCase {

    /**
     * An instance of ComponentCompetitionFulfillmentPrediction for the following tests.
     */
    private ComponentCompetitionFulfillmentPrediction tester = null;

    /**
     * An instance of ComponentCompetitionSituation for the following tests.
     */
    private ComponentCompetitionSituation situation = null;

    /**
     * A predictor for the following tests.
     */
    private Predictor<? extends Situation, ? extends Prediction> predictor = null;

    /**
     * Initialize the environment.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        situation = new ComponentCompetitionSituation();
        predictor = new ComponentCompetitionPredictor();
        tester = new ComponentCompetitionFulfillmentPrediction(3, situation, predictor);
    }

    /**
     * Clear the test environment.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void tearDown() throws Exception {
        tester = null;
        situation = null;
        predictor = null;
    }

    /**
     * <p>
     * Accuracy test for the constructor. No exception is thrown.
     * </p>
     */
    public void test_ctor() {
        // no exception
        new ComponentCompetitionFulfillmentPrediction(4.5, situation, predictor);
    }

    /**
     * <p>
     * Failure test for the constructor. IllegalArgumentException is thrown if expectedSubmissionCount is negative.
     * </p>
     */
    public void test_ctor_failure1() {
        try {
            new ComponentCompetitionFulfillmentPrediction(-4.5, situation, predictor);
            fail("IllegalArgumentException is thrown if expectedSubmissionCount is negative.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test for the constructor. IllegalArgumentException is thrown if situation is null.
     * </p>
     */
    public void test_ctor_failure2() {
        try {
            new ComponentCompetitionFulfillmentPrediction(4.5, null, predictor);
            fail("IllegalArgumentException is thrown if situation is null.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test for the constructor. IllegalArgumentException is thrown if predictor is null.
     * </p>
     */
    public void test_ctor_failure3() {
        try {
            new ComponentCompetitionFulfillmentPrediction(4.5, situation, null);
            fail("IllegalArgumentException is thrown if predictor is null.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Accuracy test for the getExpectedPassedReviewSubmissionCount property. Returned value should be as expected.
     * </p>
     */
    public void test_getExpectedPassedReviewSubmissionCount() {
        assertEquals("The getExpectedPassedReviewSubmissionCount returns incorrect result.", 3.0, tester
                        .getExpectedPassedReviewSubmissionCount());
    }

    /**
     * <p>
     * Accuracy test for the getSituation property. Returned value should be as expected.
     * </p>
     */
    public void test_getSituation() {
        assertEquals("The getSituation returns incorrect result.", situation, tester.getSituation());
    }

    /**
     * <p>
     * Accuracy test for the getPredictor property. Returned value should be as expected.
     * </p>
     */
    public void test_getPredictor() {
        assertEquals("The getPredictor returns incorrect result.", predictor, tester.getPredictor());
    }

    /**
     * <p>
     * Accuracy test for the clone method, the cloned object should be same as the original object.
     * </p>
     */
    public void test_clone() {
        Object obj = tester.clone();
        assertTrue("Cloned object should be type of ComponentCompetitionFulfillmentPrediction.",
                        obj instanceof ComponentCompetitionFulfillmentPrediction);
        ComponentCompetitionFulfillmentPrediction p = (ComponentCompetitionFulfillmentPrediction) obj;
        assertEquals("The getExpectedPassedReviewSubmissionCount returns incorrect result.", 3.0, p
                        .getExpectedPassedReviewSubmissionCount());
        assertTrue("The clone is not deep copy.", situation != p.getSituation());
        assertTrue("The clone is not deep copy.", predictor != p.getPredictor());
    }

}
