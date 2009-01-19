/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.predictor.impl.componentcompetition.failuretests;

import com.topcoder.predictor.impl.componentcompetition.ComponentCompetitionFulfillmentPrediction;
import com.topcoder.predictor.impl.componentcompetition.ComponentCompetitionPredictor;
import com.topcoder.predictor.impl.componentcompetition.ComponentCompetitionSituation;

import junit.framework.TestCase;
/**
 * The failure tests of the class <code>ComponentCompetitionFulfillmentPrediction</code>.
 *
 * @author telly12
 * @version 1.0
 */
public class ComponentCompetitionFulfillmentPredictionFailureTests extends
        TestCase {
    /**
     * The failure test of the cosntructor,
     * fail for the expectedSubmissionCount is negative,
     * IllegalArgumentException should be thrown.
     *
     */
    public void test_ctor_failure1() {
        try {
            new ComponentCompetitionFulfillmentPrediction(-1,
                    new ComponentCompetitionSituation(), new ComponentCompetitionPredictor());
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * The failure test of the cosntructor,
     * fail for the situation is negative,
     * IllegalArgumentException should be thrown.
     *
     */
    public void test_ctor_failure2() {
        try {
            new ComponentCompetitionFulfillmentPrediction(1,
                    null, new ComponentCompetitionPredictor());
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * The failure test of the cosntructor,
     * fail for the predictor is negative,
     * IllegalArgumentException should be thrown.
     *
     */
    public void test_ctor_failure3() {
        try {
            new ComponentCompetitionFulfillmentPrediction(1,
                    new ComponentCompetitionSituation(), null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
}
