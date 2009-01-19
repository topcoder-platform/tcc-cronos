/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.predictor.impl.componentcompetition.failuretests;

import java.util.ArrayList;
import java.util.List;

import com.topcoder.predictor.impl.componentcompetition.ComponentCompetitionFulfillmentPrediction;
import com.topcoder.predictor.impl.componentcompetition.ComponentCompetitionPredictor;
import com.topcoder.predictor.impl.componentcompetition.ComponentCompetitionSituation;

import junit.framework.TestCase;
/**
 * The failure tests of the class <code>ComponentCompetitionPredictor</code>.
 *
 * @author telly12
 * @version 1.0
 */
public class ComponentCompetitionPredictorFailureTests extends TestCase {
    /**
     * The ComponentCompetitionAveragePredictor instance for failure test.
     */
    private ComponentCompetitionPredictor instance
        = new ComponentCompetitionPredictor();
    /**
     * The failure test of the method <code>putCapability</code>,
     * fail for the key is null,
     * IllegalArgumentException should be thrown.
     *
     */
    public void test_putCapability_failure1() {
        try {
            instance.putCapability(null, "value");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * The failure test of the method <code>putCapability</code>,
     * fail for the key is empty,
     * IllegalArgumentException should be thrown.
     *
     */
    public void test_putCapability_failure2() {
        try {
            instance.putCapability("", "value");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * The failure test of the method <code>putCapability</code>,
     * fail for the key is trimmed empty,
     * IllegalArgumentException should be thrown.
     *
     */
    public void test_putCapability_failure3() {
        try {
            instance.putCapability("  ", "value");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * The failure test of the method <code>putCapability</code>,
     * fail for the value is null,
     * IllegalArgumentException should be thrown.
     *
     */
    public void test_putCapability_failure4() {
        try {
            instance.putCapability("key", null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * The failure test of the method <code>putCapability</code>,
     * fail for the value is empty,
     * IllegalArgumentException should be thrown.
     *
     */
    public void test_putCapability_failure5() {
        try {
            instance.putCapability("key", "");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * The failure test of the method <code>putCapability</code>,
     * fail for the value is trimmed empty,
     * IllegalArgumentException should be thrown.
     *
     */
    public void test_putCapability_failure6() {
        try {
            instance.putCapability("value", "  ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * The failure test of the method <code>getCapability</code>,
     * fail for the key is null,
     * IllegalArgumentException should be thrown.
     *
     */
    public void test_getCapability_failure1() {
        try {
            instance.getCapability(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * The failure test of the method <code>getCapability</code>,
     * fail for the key is empty,
     * IllegalArgumentException should be thrown.
     *
     */
    public void test_getCapability_failure2() {
        try {
            instance.getCapability("");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * The failure test of the method <code>getCapability</code>,
     * fail for the key is  trimmed empty,
     * IllegalArgumentException should be thrown.
     *
     */
    public void test_getCapability_failure3() {
        try {
            instance.getCapability("  ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * The failure test of the method <code>removeCapability</code>,
     * fail for the key is null,
     * IllegalArgumentException should be thrown.
     *
     */
    public void test_removeCapability_failure1() {
        try {
            instance.removeCapability(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * The failure test of the method <code>removeCapability</code>,
     * fail for the key is empty,
     * IllegalArgumentException should be thrown.
     *
     */
    public void test_removeCapability_failure2() {
        try {
            instance.removeCapability("");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * The failure test of the method <code>removeCapability</code>,
     * fail for the key is  trimmed empty,
     * IllegalArgumentException should be thrown.
     *
     */
    public void test_removeCapability_failure3() {
        try {
            instance.removeCapability("  ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * The failure test of the method <code>performFullTraining</code>,
     * fail for the situations is null,
     * IllegalArgumentException should be thrown.
     *
     * @throws Exception to JUnit
     */
    public void test_performFullTraining_failure1() throws Exception {
        try {
            instance.performFullTraining(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * The failure test of the method <code>performFullTraining</code>,
     * fail for the situations is empty,
     * IllegalArgumentException should be thrown.
     *
     * @throws Exception to JUnit
     */
    public void test_performFullTraining_failure2() throws Exception {
        List<ComponentCompetitionSituation> situations
            = new ArrayList<ComponentCompetitionSituation>();
        try {
            instance.performFullTraining(situations);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * The failure test of the method <code>performFullTraining</code>,
     * fail for the situations contains null,
     * IllegalArgumentException should be thrown.
     *
     * @throws Exception to JUnit
     */
    public void test_performFullTraining_failure3() throws Exception {
        List<ComponentCompetitionSituation> situations
            = new ArrayList<ComponentCompetitionSituation>();
        try {
            instance.performFullTraining(situations);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * The failure test of the method <code>performFullTraining</code> with predictions,
     * fail for the predictions is null,
     * IllegalArgumentException should be thrown.
     *
     * @throws Exception to JUnit
     */
    public void test_performFullTraining_WithPrediction_failure1() throws Exception {
        List<ComponentCompetitionSituation> situations
            = new ArrayList<ComponentCompetitionSituation>();
        situations.add(new ComponentCompetitionSituation());
        try {
            instance.performFullTraining(situations, null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * The failure test of the method <code>performFullTraining</code> with predictions,
     * fail for the predictions is empty,
     * IllegalArgumentException should be thrown.
     *
     * @throws Exception to JUnit
     */
    public void test_performFullTraining_WithPrediction_failure2() throws Exception {
        List<ComponentCompetitionSituation> situations
            = new ArrayList<ComponentCompetitionSituation>();
        situations.add(new ComponentCompetitionSituation());
        List<ComponentCompetitionFulfillmentPrediction> predictions
            = new ArrayList<ComponentCompetitionFulfillmentPrediction>();
        try {
            instance.performFullTraining(situations, predictions);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * The failure test of the method <code>performFullTraining</code> with predictions,
     * fail for the predictions contains null,
     * IllegalArgumentException should be thrown.
     *
     * @throws Exception to JUnit
     */
    public void test_performFullTraining_WithPrediction_failure3() throws Exception {
        List<ComponentCompetitionSituation> situations
            = new ArrayList<ComponentCompetitionSituation>();
        situations.add(new ComponentCompetitionSituation());
        List<ComponentCompetitionFulfillmentPrediction> predictions
            = new ArrayList<ComponentCompetitionFulfillmentPrediction>();
        predictions.add(null);
        try {
            instance.performFullTraining(situations, predictions);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * The failure test of the method <code>performFullTraining</code> with predictions,
     * fail for the situations is null,
     * IllegalArgumentException should be thrown.
     *
     * @throws Exception to JUnit
     */
    public void test_performFullTraining_WithPrediction_failure4() throws Exception {
        List<ComponentCompetitionFulfillmentPrediction> predictions
            = new ArrayList<ComponentCompetitionFulfillmentPrediction>();
        predictions.add(new ComponentCompetitionFulfillmentPrediction(0,
                new ComponentCompetitionSituation(), new ComponentCompetitionPredictor()));
        try {
            instance.performFullTraining(null, predictions);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * The failure test of the method <code>performFullTraining</code> with predictions,
     * fail for the situations is empty,
     * IllegalArgumentException should be thrown.
     *
     * @throws Exception to JUnit
     */
    public void test_performFullTraining_WithPrediction_failure5() throws Exception {
        List<ComponentCompetitionSituation> situations
            = new ArrayList<ComponentCompetitionSituation>();
        List<ComponentCompetitionFulfillmentPrediction> predictions
            = new ArrayList<ComponentCompetitionFulfillmentPrediction>();
        predictions.add(new ComponentCompetitionFulfillmentPrediction(0,
            new ComponentCompetitionSituation(), new ComponentCompetitionPredictor()));
        try {
            instance.performFullTraining(situations, predictions);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * The failure test of the method <code>performFullTraining</code> with predictions,
     * fail for the situations contains null,
     * IllegalArgumentException should be thrown.
     *
     * @throws Exception to JUnit
     */
    public void test_performFullTraining_WithPrediction_failure6() throws Exception {
        List<ComponentCompetitionSituation> situations
            = new ArrayList<ComponentCompetitionSituation>();
        situations.add(null);
        List<ComponentCompetitionFulfillmentPrediction> predictions
            = new ArrayList<ComponentCompetitionFulfillmentPrediction>();
        predictions.add(new ComponentCompetitionFulfillmentPrediction(0,
                new ComponentCompetitionSituation(), new ComponentCompetitionPredictor()));
        try {
            instance.performFullTraining(situations, predictions);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * The failure test of the method <code>performIncrementalTraining</code>,
     * fail for the situation is null,
     * IllegalArgumentException should be thrown.
     *
     * @throws Exception to JUnit
     */
    public void test_performIncrementalTraining_failure1() throws Exception {
        try {
            instance.performIncrementalTraining(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * The failure test of the method <code>performIncrementalTraining</code>,
     * fail for the situation is null,
     * IllegalArgumentException should be thrown.
     *
     * @throws Exception to JUnit
     */
    public void test_performIncrementalTraining_failure2() throws Exception {
        try {
            instance.performIncrementalTraining(null, new ComponentCompetitionFulfillmentPrediction(0,
                    new ComponentCompetitionSituation(), new ComponentCompetitionPredictor()));
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * The failure test of the method <code>performIncrementalTraining</code>,
     * fail for the prediction is null,
     * IllegalArgumentException should be thrown.
     *
     * @throws Exception to JUnit
     */
    public void test_performIncrementalTraining_failure3() throws Exception {
        try {
            instance.performIncrementalTraining(new ComponentCompetitionSituation(), null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * The failure test of the method <code>predict</code>,
     * fail for the situation is null,
     * IllegalArgumentException should be thrown.
     *
     * @throws Exception to JUnit
     */
    public void test_predict_failure1() throws Exception {
        try {
            instance.predict(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
}
