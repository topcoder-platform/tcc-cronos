/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.predictor.impl.componentcompetition.stresstests;

import java.util.Date;

import com.topcoder.predictor.impl.componentcompetition.ComponentCompetitionFulfillmentPrediction;
import com.topcoder.predictor.impl.componentcompetition.ComponentCompetitionPredictor;
import com.topcoder.predictor.impl.componentcompetition.ComponentCompetitionSituation;
import com.topcoder.predictor.impl.componentcompetition.analysis.ComponentCompetitionFulfillmentPredictionTimelineComparator;

/**
 * Stress tests for class ComponentCompetitionFulfillmentPredictionTimelineComparator.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 *
 */
public class ComponentCompetitionFulfillmentPredictionTimelineComparatorStressTests extends BaseStressTests {

    /**
     * The instance of ComponentCompetitionFulfillmentPredictionTimelineComparator for test.
     */
    private ComponentCompetitionFulfillmentPredictionTimelineComparator comparator;

    /**
     * Sets up test environment.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        comparator = new ComponentCompetitionFulfillmentPredictionTimelineComparator(0.8D, 1.2D, 800D);
        super.setUp();
    }

    /**
     * Stress tests for method compare(ComponentCompetitionFulfillmentPrediction,
     * ComponentCompetitionFulfillmentPrediction).
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCompare1() throws Exception {
        ComponentCompetitionSituation situation = new ComponentCompetitionSituation();
        ComponentCompetitionPredictor predictor = new ComponentCompetitionPredictor();
        ComponentCompetitionFulfillmentPrediction prediction1 = new ComponentCompetitionFulfillmentPrediction(1.0D,
                situation, predictor);
        ComponentCompetitionFulfillmentPrediction prediction2 = new ComponentCompetitionFulfillmentPrediction(1.5D,
                situation, predictor);

        begin();

        for (int i = 0; i < TIMES; i++) {
            // predictions in range < predictions above the range
            int result = comparator.compare(prediction1, prediction2);
            // result should be < 0
            assertTrue("result of compare", result < 0);
        }

        print("ComponentCompetitionFulfillmentPredictionTimelineComparator#compare");
    }

    /**
     * Stress tests for method compare(ComponentCompetitionFulfillmentPrediction,
     * ComponentCompetitionFulfillmentPrediction).
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCompare2() throws Exception {
        ComponentCompetitionSituation situation = new ComponentCompetitionSituation();
        ComponentCompetitionPredictor predictor = new ComponentCompetitionPredictor();
        ComponentCompetitionFulfillmentPrediction prediction1 = new ComponentCompetitionFulfillmentPrediction(1.0D,
                situation, predictor);
        ComponentCompetitionFulfillmentPrediction prediction2 = new ComponentCompetitionFulfillmentPrediction(0.6D,
                situation, predictor);

        begin();

        for (int i = 0; i < TIMES; i++) {
            // predictions in range < predictions below the range
            int result = comparator.compare(prediction1, prediction2);
            // result should be < 0
            assertTrue("result of compare", result < 0);
        }

        print("ComponentCompetitionFulfillmentPredictionTimelineComparator#compare");
    }

    /**
     * Stress tests for method compare(ComponentCompetitionFulfillmentPrediction,
     * ComponentCompetitionFulfillmentPrediction).
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCompare3() throws Exception {
        ComponentCompetitionSituation situation = new ComponentCompetitionSituation();
        ComponentCompetitionPredictor predictor = new ComponentCompetitionPredictor();
        ComponentCompetitionFulfillmentPrediction prediction1 = new ComponentCompetitionFulfillmentPrediction(1.5D,
                situation, predictor);
        ComponentCompetitionFulfillmentPrediction prediction2 = new ComponentCompetitionFulfillmentPrediction(0.6D,
                situation, predictor);

        begin();

        for (int i = 0; i < TIMES; i++) {
            // predictions above the range < predictions below the range
            int result = comparator.compare(prediction1, prediction2);
            // result should be < 0
            assertTrue("result of compare", result < 0);
        }

        print("ComponentCompetitionFulfillmentPredictionTimelineComparator#compare");
    }

    /**
     * Stress tests for method compare(ComponentCompetitionFulfillmentPrediction,
     * ComponentCompetitionFulfillmentPrediction).
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCompare4() throws Exception {
        ComponentCompetitionSituation situation1 = new ComponentCompetitionSituation();
        situation1.setPostingDate(new Date());
        situation1.setEndDate(new Date(situation1.getPostingDate().getTime() + 100000));
        situation1.setPrize(200D);

        ComponentCompetitionSituation situation2 = new ComponentCompetitionSituation();
        situation2.setPostingDate(new Date());
        situation2.setEndDate(new Date(situation2.getPostingDate().getTime() + 10000));
        situation2.setPrize(1000D);

        ComponentCompetitionPredictor predictor = new ComponentCompetitionPredictor();
        ComponentCompetitionFulfillmentPrediction prediction1 = new ComponentCompetitionFulfillmentPrediction(1.0D,
                situation1, predictor);
        ComponentCompetitionFulfillmentPrediction prediction2 = new ComponentCompetitionFulfillmentPrediction(1.0D,
                situation2, predictor);

        begin();

        for (int i = 0; i < TIMES; i++) {
            // prizes lower than or equal to the target < prizes higher than the target
            int result = comparator.compare(prediction1, prediction2);
            // result should be < 0
            assertTrue("result of compare", result < 0);
        }

        print("ComponentCompetitionFulfillmentPredictionTimelineComparator#compare");
    }

    /**
     * Stress tests for method compare(ComponentCompetitionFulfillmentPrediction,
     * ComponentCompetitionFulfillmentPrediction).
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCompare5() throws Exception {
        ComponentCompetitionSituation situation1 = new ComponentCompetitionSituation();
        situation1.setPostingDate(new Date());
        situation1.setEndDate(new Date(situation1.getPostingDate().getTime() + 1000));
        situation1.setPrize(1000D);

        ComponentCompetitionSituation situation2 = new ComponentCompetitionSituation();
        situation2.setPostingDate(new Date());
        situation2.setEndDate(new Date(situation2.getPostingDate().getTime() + 10000));
        situation2.setPrize(1000D);

        ComponentCompetitionPredictor predictor = new ComponentCompetitionPredictor();
        ComponentCompetitionFulfillmentPrediction prediction1 = new ComponentCompetitionFulfillmentPrediction(1.0D,
                situation1, predictor);
        ComponentCompetitionFulfillmentPrediction prediction2 = new ComponentCompetitionFulfillmentPrediction(1.0D,
                situation2, predictor);

        begin();

        for (int i = 0; i < TIMES; i++) {
            // shorter timelines < longer timelines
            int result = comparator.compare(prediction1, prediction2);
            // result should be < 0
            assertTrue("result of compare", result < 0);
        }

        print("ComponentCompetitionFulfillmentPredictionTimelineComparator#compare");
    }
}
