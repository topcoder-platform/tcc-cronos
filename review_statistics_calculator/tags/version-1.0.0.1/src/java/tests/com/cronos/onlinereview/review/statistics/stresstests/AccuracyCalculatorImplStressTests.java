/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.review.statistics.stresstests;

import junit.framework.TestCase;

import com.cronos.onlinereview.review.statistics.impl.AccuracyCalculatorImpl;
import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.management.review.data.Review;

/**
 * Test class AccuracyCalculatorImpl.
 *
 * @author extra
 * @version 1.0
 */
public class AccuracyCalculatorImplStressTests extends TestCase {

    /**
     * Test method calculateAccuracy for stress.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCalculateAccuracy_Stress() throws Exception {
        ConfigurationObject allConfig = StressTestHelper.load("config.xml");
        ConfigurationObject config = allConfig.getChild(
                "com.cronos.onlinereview.review.statistics.impl.ReviewerStatisticsCalculator").getChild(
                "accuracyCalculatorConfig");

        AccuracyCalculatorImpl calculator = new AccuracyCalculatorImpl();
        calculator.configure(config);

        int nReview = 3;
        int nSubmission = 3;
        Review[][] reviews = new Review[3][3];
        for (int i = 0; i < nReview; i++) {
            for (int j = 0; j < nSubmission; j++) {
                reviews[i][j] = StressTestHelper.createReview(i, j);
            }
        }

        long start = System.currentTimeMillis();

        for (int i = 0; i < 10; i++) {
            double[] result = calculator.calculateAccuracy(reviews);

            assertEquals("1 score", 32.0 / 62.0, result[0]);
            assertEquals("2 score", 126.0 / 216.0, result[1]);
            assertEquals("3 score", 19.0 / 29.0, result[2]);
        }

        System.out.println("run  calculateAccuracy 10 times cost:" + (System.currentTimeMillis() - start) + "ms");
    }

}
