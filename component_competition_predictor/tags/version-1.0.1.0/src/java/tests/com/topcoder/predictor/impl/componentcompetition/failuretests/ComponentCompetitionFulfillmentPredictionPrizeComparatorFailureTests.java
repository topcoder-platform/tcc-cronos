/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.predictor.impl.componentcompetition.failuretests;

import com.topcoder.predictor.impl.componentcompetition.ComponentCompetitionFulfillmentPrediction;
import com.topcoder.predictor.impl.componentcompetition.ComponentCompetitionPredictor;
import com.topcoder.predictor.impl.componentcompetition.ComponentCompetitionSituation;
import com.topcoder.predictor.impl.componentcompetition.analysis.ComponentCompetitionFulfillmentPredictionPrizeComparator;

import junit.framework.TestCase;
/**
 * The failure tests of the class <code>ComponentCompetitionFulfillmentPredictionPrizeComparator</code>.
 *
 * @author telly12
 * @version 1.0
 */
public class ComponentCompetitionFulfillmentPredictionPrizeComparatorFailureTests
        extends TestCase {
    /**
     * The ComponentCompetitionFulfillmentPredictionPrizeComparator instance for test.
     */
    private ComponentCompetitionFulfillmentPredictionPrizeComparator instance = null;
    /**
     * SetUp.
     */
    protected void setUp() {
        instance = new ComponentCompetitionFulfillmentPredictionPrizeComparator(1.0, 2.0, 1);
    }
    /**
     * The failure test of the constructor,
     * fail for the minPrediction is negative,
     * IllegalArgumentException should be thrown.
     *
     */
    public void test_ctor_failure1() {
        try {
            new ComponentCompetitionFulfillmentPredictionPrizeComparator(-1.0, 2.0, 1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * The failure test of the constructor,
     * fail for the maxPrediction is negative,
     * IllegalArgumentException should be thrown.
     *
     */
    public void test_ctor_failure2() {
        try {
            new ComponentCompetitionFulfillmentPredictionPrizeComparator(1.0, -2.0, 1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * The failure test of the constructor,
     * fail for the targetDuration is negative,
     * IllegalArgumentException should be thrown.
     *
     */
    public void test_ctor_failure3() {
        try {
            new ComponentCompetitionFulfillmentPredictionPrizeComparator(1.0, 2.0, -1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * The failure test of the constructor,
     * fail for the minPrediction is greater than maxPrediction,
     * IllegalArgumentException should be thrown.
     *
     */
    public void test_ctor_failure4() {
        try {
            new ComponentCompetitionFulfillmentPredictionPrizeComparator(3.0, 2.0, 1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * The failure test of the compare,
     * fail for the minPrediction is greater than prediction1,
     * IllegalArgumentException should be thrown.
     *
     */
    public void test_compare_failure1() {
        try {
            instance.compare(null, new ComponentCompetitionFulfillmentPrediction(0,
                    new ComponentCompetitionSituation(), new ComponentCompetitionPredictor()));
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * The failure test of the compare,
     * fail for the minPrediction is greater than prediction1,
     * IllegalArgumentException should be thrown.
     *
     */
    public void test_compare_failure2() {
        try {
            instance.compare(new ComponentCompetitionFulfillmentPrediction(0,
                    new ComponentCompetitionSituation(), new ComponentCompetitionPredictor()), null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
}
