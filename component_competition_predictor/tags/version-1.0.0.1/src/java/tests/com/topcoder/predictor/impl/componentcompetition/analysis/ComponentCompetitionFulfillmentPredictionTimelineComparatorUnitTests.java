/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.predictor.impl.componentcompetition.analysis;

import com.topcoder.predictor.impl.componentcompetition.ComponentCompetitionFulfillmentPrediction;
import com.topcoder.predictor.impl.componentcompetition.ComponentCompetitionSituation;
import com.topcoder.predictor.impl.componentcompetition.ComponentCompetitionPredictor;

import java.util.Date;

import junit.framework.TestCase;

/**
 * <p>
 * Unit tests for ComponentCompetitionFulfillmentPredictionTimelineComparator class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ComponentCompetitionFulfillmentPredictionTimelineComparatorUnitTests extends TestCase {

    /**
     * An instance of ComponentCompetitionFulfillmentPredictionTimelineComparator for the following tests.
     */
    private ComponentCompetitionFulfillmentPredictionTimelineComparator tester = null;

    /**
     * A situation for the following tests.
     */
    private ComponentCompetitionSituation situation = null;

    /**
     * A predictor for the following tests.
     */
    private ComponentCompetitionPredictor predictor = null;

    /**
     * Initialize the environment.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        tester = new ComponentCompetitionFulfillmentPredictionTimelineComparator(1.0, 3.0, 500.0);
        situation = new ComponentCompetitionSituation();
        predictor = new ComponentCompetitionPredictor();
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
        new ComponentCompetitionFulfillmentPredictionTimelineComparator(1.0, 3.0, 100);
    }

    /**
     * <p>
     * Failure test for the constructor. IllegalArgumentException should be thrown for negative minPrediction.
     * </p>
     */
    public void test_ctor_failure1() {
        try {
            new ComponentCompetitionFulfillmentPredictionTimelineComparator(-1.0, 3.0, 100);
            fail("IllegalArgumentException should be thrown for negative minPrediction.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test for the constructor. IllegalArgumentException should be thrown for negative maxPrediction.
     * </p>
     */
    public void test_ctor_failure2() {
        try {
            new ComponentCompetitionFulfillmentPredictionTimelineComparator(1.0, -3.0, 100);
            fail("IllegalArgumentException should be thrown for negative maxPrediction.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test for the constructor. IllegalArgumentException should be thrown for negative targetPrize.
     * </p>
     */
    public void test_ctor_failure3() {
        try {
            new ComponentCompetitionFulfillmentPredictionTimelineComparator(1.0, 3.0, -100);
            fail("IllegalArgumentException should be thrown for negative targetDuration.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test for the constructor. IllegalArgumentException should be thrown if minPrediction is larger than
     * maxPrediction.
     * </p>
     */
    public void test_ctor_failure4() {
        try {
            new ComponentCompetitionFulfillmentPredictionTimelineComparator(4.0, 3.0, 100);
            fail("IllegalArgumentException should be thrown for negative targetDuration.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Accuracy tests for the compare method. The result should be correct.
     * </p>
     */
    public void test_compare1() {
        ComponentCompetitionFulfillmentPrediction prediction1 = new ComponentCompetitionFulfillmentPrediction(2.5,
                        situation, predictor);
        ComponentCompetitionFulfillmentPrediction prediction2 = new ComponentCompetitionFulfillmentPrediction(3.5,
                        situation, predictor);
        // prediction1 is in the range, prediction2 is above the range, so result should be -1
        int result = tester.compare(prediction1, prediction2);
        assertEquals("The compare method returns incorrect result.", -1, result);
    }

    /**
     * <p>
     * Accuracy tests for the compare method. The result should be correct.
     * </p>
     */
    public void test_compare2() {
        situation.setPrize(300.0);
        ComponentCompetitionFulfillmentPrediction prediction1 = new ComponentCompetitionFulfillmentPrediction(1.1,
                        situation, predictor);
        situation = new ComponentCompetitionSituation();
        situation.setPrize(600.0);
        ComponentCompetitionFulfillmentPrediction prediction2 = new ComponentCompetitionFulfillmentPrediction(2.0,
                        situation, predictor);
        // both predictions are in the range;
        // prediction1's prize is less than target, but prediction2's prize is larger than target,
        // so result should be -1
        int result = tester.compare(prediction1, prediction2);
        assertEquals("The compare method returns incorrect result.", -1, result);
    }

    /**
     * <p>
     * Accuracy tests for the compare method. The result should be correct.
     * </p>
     */
    public void test_compare3() {
        situation.setEndDate(new Date(170000));
        situation.setPostingDate(new Date(100000));
        situation.setPrize(1000.0);
        ComponentCompetitionFulfillmentPrediction prediction1 = new ComponentCompetitionFulfillmentPrediction(1.1,
                        situation, predictor);
        situation = new ComponentCompetitionSituation();
        situation.setEndDate(new Date(160000));
        situation.setPostingDate(new Date(100000));
        situation.setPrize(900.0);
        ComponentCompetitionFulfillmentPrediction prediction2 = new ComponentCompetitionFulfillmentPrediction(2.0,
                        situation, predictor);
        // both predictions are in the range;
        // both prizes are more than target;
        // prediction1's situation has longer timeline,
        // so result should be 1
        int result = tester.compare(prediction1, prediction2);
        assertEquals("The compare method returns incorrect result.", 1, result);
    }

    /**
     * <p>
     * Accuracy tests for the compare method. The result should be correct.
     * </p>
     */
    public void test_compare4() {
        situation.setEndDate(new Date(150000));
        situation.setPostingDate(new Date(100000));
        situation.setPrize(900.0);
        ComponentCompetitionFulfillmentPrediction prediction1 = new ComponentCompetitionFulfillmentPrediction(1.1,
                        situation, predictor);
        situation = new ComponentCompetitionSituation();
        situation.setEndDate(new Date(150000));
        situation.setPostingDate(new Date(100000));
        situation.setPrize(10000.0);
        ComponentCompetitionFulfillmentPrediction prediction2 = new ComponentCompetitionFulfillmentPrediction(2.0,
                        situation, predictor);
        // both predictions are in the range;
        // both prizes are larger than target;
        // the two timelines are the same;
        // so result should be 0
        int result = tester.compare(prediction1, prediction2);
        assertEquals("The compare method returns incorrect result.", 0, result);
    }

    /**
     * <p>
     * Accuracy tests for the compare method. The result should be correct.
     * </p>
     */
    public void test_compare5() {
        ComponentCompetitionFulfillmentPrediction prediction1 = new ComponentCompetitionFulfillmentPrediction(1.1,
                        situation, predictor);
        ComponentCompetitionFulfillmentPrediction prediction2 = new ComponentCompetitionFulfillmentPrediction(2.0,
                        situation, predictor);
        // both predictions are in the range;
        // their situations' posting dates, end dates and prizes are not set,
        // so result should be 0
        int result = tester.compare(prediction1, prediction2);
        assertEquals("The compare method returns incorrect result.", 0, result);
    }

    /**
     * <p>
     * Failure test for the compare method. IllegalArgumentException should be thrown for null argument.
     * </p>
     */
    public void test_compare_failure1() {
        ComponentCompetitionFulfillmentPrediction prediction = new ComponentCompetitionFulfillmentPrediction(1.1,
                        situation, predictor);
        try {
            tester.compare(null, prediction);
            fail("IllegalArgumentException should be thrown for null argument.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test for the compare method. IllegalArgumentException should be thrown for null argument.
     * </p>
     */
    public void test_compare_failure2() {
        ComponentCompetitionFulfillmentPrediction prediction = new ComponentCompetitionFulfillmentPrediction(1.1,
                        situation, predictor);
        try {
            tester.compare(prediction, null);
            fail("IllegalArgumentException should be thrown for null argument.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Accuracy test for the equals method. Returned result should be correct.
     * </p>
     */
    public void test_equals_1() {
        ComponentCompetitionFulfillmentPredictionTimelineComparator c =
            new ComponentCompetitionFulfillmentPredictionTimelineComparator(1.0, 3.0, 500.0);
        boolean result = tester.equals(c);
        assertTrue("The equals method returns incorrect result.", result);
    }

    /**
     * <p>
     * Accuracy test for the equals method. Returned result should be correct.
     * </p>
     */
    public void test_equals_2() {
        ComponentCompetitionFulfillmentPredictionTimelineComparator c =
            new ComponentCompetitionFulfillmentPredictionTimelineComparator(1.0, 3.0, 600.0);
        boolean result = tester.equals(c);
        assertFalse("The equals method returns incorrect result.", result);
    }

    /**
     * <p>
     * Accuracy test for the equals method. Returned result should be correct.
     * </p>
     */
    public void test_equals_3() {
        boolean result = tester.equals(null);
        assertFalse("The equals method returns incorrect result.", result);
    }

    /**
     * <p>
     * Accuracy test for the equals method. Returned result should be correct.
     * </p>
     */
    public void test_equals_4() {
        boolean result = tester.equals(" ");
        assertFalse("The equals method returns incorrect result.", result);
    }

    /**
     * <p>
     * Accuracy test for the hashCode method. Returned result should be correct.
     * </p>
     */
    public void test_hashCode() {
        int expected = new Double(1.0).hashCode() ^ new Double(3.0).hashCode() ^ new Double(500.0).hashCode();
        assertEquals("The hashCode method returns incorrect result.", expected, tester.hashCode());
    }

    /**
     * <p>
     * Accuracy test for the getMinPrediction method. Returned result should be correct.
     * </p>
     */
    public void test_getMinPrediction() {
        assertEquals("The getMinPrediction method returns incorrect result.", 1.0, tester.getMinPrediction());
    }

    /**
     * <p>
     * Accuracy test for the getMaxPrediction method. Returned result should be correct.
     * </p>
     */
    public void test_getMaxPrediction() {
        assertEquals("The getMaxPrediction method returns incorrect result.", 3.0, tester.getMaxPrediction());
    }

    /**
     * <p>
     * Accuracy test for the getTargetDuration method. Returned result should be correct.
     * </p>
     */
    public void test_getTargetDuration() {
        assertEquals("The getTargetDuration method returns incorrect result.", 500.0, tester.getTargetPrize());
    }

}
