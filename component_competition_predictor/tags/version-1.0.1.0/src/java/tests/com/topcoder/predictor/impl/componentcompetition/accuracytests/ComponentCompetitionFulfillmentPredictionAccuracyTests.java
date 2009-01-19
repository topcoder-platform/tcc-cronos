/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.predictor.impl.componentcompetition.accuracytests;

import com.topcoder.predictor.Prediction;
import com.topcoder.predictor.Predictor;
import com.topcoder.predictor.Situation;
import com.topcoder.predictor.impl.componentcompetition.ComponentCompetitionFulfillmentPrediction;
import com.topcoder.predictor.impl.componentcompetition.ComponentCompetitionPredictor;
import com.topcoder.predictor.impl.componentcompetition.ComponentCompetitionSituation;

import junit.framework.TestCase;

/**
 * <p>
 * Unit tests for <code>{@link ComponentCompetitionFulfillmentPrediction}</code> class.
 * </p>
 *
 * @author morehappiness
 * @version 1.0
 */
public class ComponentCompetitionFulfillmentPredictionAccuracyTests extends TestCase {
    /**
     * <p>
     * Represents the <code>ComponentCompetitionFulfillmentPrediction</code> used in tests.
     * </p>
     */
    private ComponentCompetitionFulfillmentPrediction instance = null;

    /**
     * <p>
     * Represents the situation used in tests.
     * </p>
     */
    private ComponentCompetitionSituation situation = null;

    /**
     * <p>
     * Represents the predictor used in tests.
     * </p>
     */
    private Predictor<? extends Situation, ? extends Prediction> predictor = null;

    /**
     * <p>
     * Sets up the tests.
     * </p>
     */
    public void setUp() {
        situation = new ComponentCompetitionSituation();
        predictor = new ComponentCompetitionPredictor();

        instance = new ComponentCompetitionFulfillmentPrediction(1.0, situation, predictor);
    }

    /**
     * <p>
     * Tests accuracy of <code>ComponentCompetitionFulfillmentPrediction(double, ComponentCompetitionSituation,
     * Predictor&lt;? extends Situation, ? extends Prediction&gt;)</code> constructor.<br>
     * Instance should be correctly created.
     * </p>
     */
    public void testCtor() {
        assertEquals("ExpectedPassedReviewSubmissionCount should be correct.",
            1.0, instance.getExpectedPassedReviewSubmissionCount());
        assertSame("Situation should be correct.", situation, instance.getSituation());
        assertSame("Predictor should be correct.", predictor, instance.getPredictor());
    }

    /**
     * <p>
     * Tests accuracy of <code>getExpectedPassedReviewSubmissionCount()</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testGetExpectedPassedReviewSubmissionCount() {
        assertEquals("'getExpectedPassedReviewSubmissionCount' should be correct.",
            1.0, instance.getExpectedPassedReviewSubmissionCount());
    }

    /**
     * <p>
     * Tests accuracy of <code>getSituation()</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testGetSituation() {
        assertSame("'getSituation' should be correct.", situation, instance.getSituation());
    }

    /**
     * <p>
     * Tests accuracy of <code>getPredictor()</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testGetPredictor() {
        assertSame("'getPredictor' should be correct.", predictor, instance.getPredictor());
    }

    /**
     * <p>
     * Tests accuracy of <code>clone()</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testClone() {
        ComponentCompetitionFulfillmentPrediction cloneObj =
            (ComponentCompetitionFulfillmentPrediction) instance.clone();

        assertEquals("'clone' should be correct.", 1.0, cloneObj.getExpectedPassedReviewSubmissionCount());
        assertNotNull("'clone' should be correct.", cloneObj.getSituation());
        assertTrue("'clone' should be correct.", situation != cloneObj.getSituation());
        assertNotNull("'clone' should be correct.", cloneObj.getPredictor());
        assertTrue("'clone' should be correct.", predictor != cloneObj.getPredictor());
    }
}
