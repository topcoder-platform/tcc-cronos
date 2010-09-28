/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.review.payment.failuretests;

import junit.framework.TestCase;

import com.cronos.onlinereview.review.payment.InvalidReviewersStatisticsException;
import com.cronos.onlinereview.review.payment.ReviewPaymentCalculatorConfigurationException;
import com.cronos.onlinereview.review.payment.impl.DefaultReviewPaymentCalculator;
import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.defaults.DefaultConfigurationObject;
import com.topcoder.management.resource.ReviewerStatistics;
import com.topcoder.management.resource.StatisticsType;

/**
 * <p>
 * Failure tests for DefaultReviewPaymentCalculator.
 * </p>
 * 
 * @author Beijing2008
 * @version 1.0
 */
public class DefaultReviewPaymentCalculatorFailureTest extends TestCase {

    /** Represents DefaultReviewPaymentCalculator instance for test. */
    private DefaultReviewPaymentCalculator instance;

    /**
     * <p>
     * Setup the environment.
     * </p>
     * 
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        ConfigurationObject config = new DefaultConfigurationObject("name");
        config.setPropertyValue("loggerName", "mylogger");
        config.setPropertyValue("bonus+ercentage", "0.2");
        instance = new DefaultReviewPaymentCalculator();
        instance.configure(config);
    }

    /**
     * <p>
     * Clean up the environment.
     * </p>
     * 
     * @throws Exception
     *             to JUnit.
     */
    protected void tearDown() throws Exception {
        instance = null;
    }
    /**
     * Tests for configure() with null input.
     * 
     * 
     * @throws Exception
     *             to junit
     */
    public void test_configure() throws Exception {
        try {
            instance.configure(null);
            fail("Expects IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }
    /**
     * Tests for configure() with illegal input.
     * 
     * 
     * @throws Exception
     *             to junit
     */
    public void test_configure2() throws Exception {
        ConfigurationObject config = new DefaultConfigurationObject("name");
        config.setPropertyValue("loggerName", "   \n\t");
        config.setPropertyValue("bonus+ercentage", "0.2");
        try {
            instance.configure(config);
            fail("Expects ReviewPaymentCalculatorConfigurationException");
        } catch (ReviewPaymentCalculatorConfigurationException e) {
            // pass
        }
    }
    /**
     * Tests for configure() with illegal input.
     * 
     * 
     * @throws Exception
     *             to junit
     */
    public void test_configure3() throws Exception {
        ConfigurationObject config = new DefaultConfigurationObject("name");
        config.setPropertyValue("loggerName", "mylogger");
        config.setPropertyValue("bonusPercentage", "xxx");
        try {
            instance.configure(config);
            fail("Expects ReviewPaymentCalculatorConfigurationException");
        } catch (ReviewPaymentCalculatorConfigurationException e) {
            // pass
        }
    }
    /**
     * Tests for configure() with illegal input.
     * 
     * 
     * @throws Exception
     *             to junit
     */
    public void test_configure5() throws Exception {
        ConfigurationObject config = new DefaultConfigurationObject("name");
        config.setPropertyValue("loggerName", "mylogger");
        config.setPropertyValue("bonusPercentage", "   ");
        try {
            instance.configure(config);
            fail("Expects ReviewPaymentCalculatorConfigurationException");
        } catch (ReviewPaymentCalculatorConfigurationException e) {
            // pass
        }
        config.setPropertyValue("bonusPercentage", "1.01");
        try {
            instance.configure(config);
            fail("Expects ReviewPaymentCalculatorConfigurationException");
        } catch (ReviewPaymentCalculatorConfigurationException e) {
            // pass
        }
        config.setPropertyValue("bonusPercentage", "-0.01");
        try {
            instance.configure(config);
            fail("Expects ReviewPaymentCalculatorConfigurationException");
        } catch (ReviewPaymentCalculatorConfigurationException e) {
            // pass
        }
    }

    /**
     * Tests for computeReviewPayment() with illegal input.
     * 
     * 
     * @throws Exception
     *             to junit
     */
    public void test_computeReviewPayment1() throws Exception {
        ReviewerStatistics[] array = getReviewerStatistics();
        try {
            instance.computeReviewPayment(0, 300, 400, array);
            fail("Expects IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // pass
        }
        try {
            instance.computeReviewPayment(100, 0, 400, array);
            fail("Expects IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // pass
        }
        try {
            instance.computeReviewPayment(100, 300, 0, array);
            fail("Expects IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // pass
        }
        try {
            instance.computeReviewPayment(-1, 300, 400, array);
            fail("Expects IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // pass
        }
        try {
            instance.computeReviewPayment(100, -1, 400, array);
            fail("Expects IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // pass
        }
        try {
            instance.computeReviewPayment(100, 300, -1, array);
            fail("Expects IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }
    /**
     * Tests for computeReviewPayment() with illegal input.
     * 
     * 
     * @throws Exception
     *             to junit
     */
    public void test_computeReviewPayment2() throws Exception {
        try {
            instance.computeReviewPayment(100, 300, 400, new ReviewerStatistics[]{});
            fail("Expects IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // pass
        }
        try {
            instance.computeReviewPayment(100, 300, 400, null);
            fail("Expects IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }
    /**
     * Tests for computeReviewPayment() with illegal input.
     * 
     * 
     * @throws Exception
     *             to junit
     */
    public void test_computeReviewPayment3() throws Exception {
        ReviewerStatistics[] array = getReviewerStatistics();
        try {
            //length is less than 3
            instance.computeReviewPayment(100, 300, 400, new ReviewerStatistics[]{array[0], array[1]});
            fail("Expects IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // pass
        }
        try {
            //length is less than 3
            instance.computeReviewPayment(100, 300, 400, new ReviewerStatistics[]{array[0], array[1], array[2], null});
            fail("Expects IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }
    /**
     * Tests for computeReviewPayment() with illegal input.
     * 
     * 
     * @throws Exception
     *             to junit
     */
    public void test_computeReviewPayment4() throws Exception {
        ReviewerStatistics[] array = getReviewerStatistics();
        array[0].setStatisticsType(StatisticsType.AVERGAE);
        try {
            instance.computeReviewPayment(100, 300, 400, array);
            fail("Expects InvalidReviewersStatisticsException");
        } catch (InvalidReviewersStatisticsException e) {
            // pass
        }
    }
    /**
     * Tests for computeReviewPayment() with illegal input.
     * 
     * 
     * @throws Exception
     *             to junit
     */
    public void test_computeReviewPayment5() throws Exception {
        ReviewerStatistics[] array = getReviewerStatistics();
        //not primary
        array[0].setCoverage(0.8);
        try {
            instance.computeReviewPayment(100, 300, 400, array);
            fail("Expects InvalidReviewersStatisticsException");
        } catch (InvalidReviewersStatisticsException e) {
            // pass
        }
    }
    /**
     * Tests for computeReviewPayment() with illegal input.
     * 
     * 
     * @throws Exception
     *             to junit
     */
    public void test_computeReviewPayment6() throws Exception {
        ReviewerStatistics[] array = getReviewerStatistics();
        array[1].setCoverage(1.1);
        try {
            instance.computeReviewPayment(100, 300, 400, array);
            fail("Expects InvalidReviewersStatisticsException");
        } catch (InvalidReviewersStatisticsException e) {
            // pass
        }
    }
    /**
     * Tests for computeReviewPayment() with illegal input.
     * 
     * 
     * @throws Exception
     *             to junit
     */
    public void test_computeReviewPayment7() throws Exception {
        ReviewerStatistics[] array = getReviewerStatistics();
        array[1].setCoverage(-0.1);
        try {
            instance.computeReviewPayment(100, 300, 400, array);
            fail("Expects InvalidReviewersStatisticsException");
        } catch (InvalidReviewersStatisticsException e) {
            // pass
        }
    }
    /**
     * Tests for computeReviewPayment() with illegal input.
     * 
     * 
     * @throws Exception
     *             to junit
     */
    public void test_computeReviewPayment8() throws Exception {
        ReviewerStatistics[] array = getReviewerStatistics();
        array[0].setTimelineReliability(-0.1);
        try {
            instance.computeReviewPayment(100, 300, 400, array);
            fail("Expects InvalidReviewersStatisticsException");
        } catch (InvalidReviewersStatisticsException e) {
            // pass
        }
    }
    /**
     * Tests for computeReviewPayment() with illegal input.
     * 
     * 
     * @throws Exception
     *             to junit
     */
    public void test_computeReviewPayment9() throws Exception {
        ReviewerStatistics[] array = getReviewerStatistics();
        array[1].setTimelineReliability(1.1);
        try {
            instance.computeReviewPayment(100, 300, 400, array);
            fail("Expects InvalidReviewersStatisticsException");
        } catch (InvalidReviewersStatisticsException e) {
            // pass
        }
    }
    private static ReviewerStatistics[] getReviewerStatistics() {
        ReviewerStatistics statistics1 = new ReviewerStatistics();
        statistics1.setStatisticsType(StatisticsType.HISTORY);
        statistics1.setAccuracy(-1);
        statistics1.setCoverage(-1);
        statistics1.setProjectId(100);
        statistics1.setReviewerId(3001);
        statistics1.setTimelineReliability(0.9);
        statistics1.setTotalEvaluationCoefficient(-1);
        ReviewerStatistics statistics2 = new ReviewerStatistics();
        statistics2.setAccuracy(0.8);
        statistics2.setCoverage(0.9);
        statistics2.setProjectId(100);
        statistics2.setReviewerId(3002);
        statistics2.setTimelineReliability(0.91);
        statistics2.setTotalEvaluationCoefficient(-1);
        statistics2.setStatisticsType(StatisticsType.HISTORY);
        ReviewerStatistics statistics3 = new ReviewerStatistics();
        statistics3.setAccuracy(0.7);
        statistics3.setCoverage(0.6);
        statistics3.setProjectId(100);
        statistics3.setReviewerId(3003);
        statistics3.setTimelineReliability(0.92);
        statistics3.setTotalEvaluationCoefficient(-1);
        statistics3.setStatisticsType(StatisticsType.HISTORY);
        ReviewerStatistics[] array = new ReviewerStatistics[]{statistics1, statistics2, statistics3};
        return array;
    }
}
