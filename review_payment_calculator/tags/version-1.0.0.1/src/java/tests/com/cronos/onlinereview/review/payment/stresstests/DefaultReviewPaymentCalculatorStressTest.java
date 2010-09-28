/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.review.payment.stresstests;

import java.util.Date;

import junit.framework.Test;
import junit.framework.TestSuite;

import com.cronos.onlinereview.review.payment.ReviewerPayment;
import com.cronos.onlinereview.review.payment.impl.DefaultReviewPaymentCalculator;
import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.defaults.DefaultConfigurationObject;
import com.topcoder.management.resource.ReviewerStatistics;
import com.topcoder.management.resource.StatisticsType;

/**
 * <p>
 * Stress test case of {@link DefaultReviewPaymentCalculator}.
 * </p>
 * 
 * @author mumujava
 * @version 1.0
 */
public class DefaultReviewPaymentCalculatorStressTest extends BaseStressTest {
    /**
     * <p>
     * The DefaultReviewPaymentCalculator instance to test.
     * </p>
     * */
    private DefaultReviewPaymentCalculator instance;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     * 
     * @throws Exception
     *             to jUnit.
     */
    @Override
    public void setUp() throws Exception {
        super.setUp();
        instance = new DefaultReviewPaymentCalculator();

    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     * 
     * @throws Exception
     *             to jUnit.
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        instance = null;
    }

    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     * 
     * @return a TestSuite for this test case.
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(
                DefaultReviewPaymentCalculatorStressTest.class);
        return suite;
    }

    /**
     * <p>
     * Stress test for method DefaultReviewPaymentCalculator#computeReviewPayment().
     * 
     * </p>
     * @throws Throwable to junit
     */
    public void test_test() throws Throwable {
        ConfigurationObject config = new DefaultConfigurationObject("stress");
        config.setPropertyValue("bonus+ercentage", "0.2");
        instance.configure(config);
        final ReviewerStatistics[] statistics = new ReviewerStatistics[]{createReviewerStatistics(2, 0.85, -1, -1), 
                createReviewerStatistics(2, 1, 0.61, 0.95), createReviewerStatistics(2, 0.74, 0.39, 1)};
        
        
        Thread thread[] = new Thread[testCount];
        for (int i = 0; i < testCount; i++) {
            thread[i] = new Thread() {
                public void run() {
                    try {
                        ReviewerPayment[] res = instance.computeReviewPayment(1, 120, 200, statistics);
                        assertEquals(res[0].getPaymentAmount(), 102, 0.01);
                        assertEquals(res[1].getPaymentAmount(), 146.80, 0.01);
                        assertEquals(res[2].getPaymentAmount(), 53.19, 0.01);
                    } catch (Throwable e) {
                        lastError = e;
                    }

                }
            };
            thread[i].start();
        }
        for (int i = 0; i < testCount; i++) {
            // wait to end
            thread[i].join();
        }
        if (lastError != null) {
            throw lastError;
        }
        
        System.out.println("Run computeReviewPayment for " + testCount + " times takes "
                + (new Date().getTime() - start) + "ms");
    }
    public static ReviewerStatistics createReviewerStatistics(long reviewerId,
            double timelineReliability, double coverage, double accuracy) {
            ReviewerStatistics reviewerStatistics = new ReviewerStatistics();
            reviewerStatistics.setProjectId(1);
            reviewerStatistics.setReviewerId(reviewerId);
            reviewerStatistics.setTimelineReliability(timelineReliability);
            reviewerStatistics.setCoverage(coverage);
            reviewerStatistics.setAccuracy(accuracy);
            reviewerStatistics.setTotalEvaluationCoefficient(-1);
            reviewerStatistics.setStatisticsType(StatisticsType.HISTORY);

            return reviewerStatistics;
        }
}
