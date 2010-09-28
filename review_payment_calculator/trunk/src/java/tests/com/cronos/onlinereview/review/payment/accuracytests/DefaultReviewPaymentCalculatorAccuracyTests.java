/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.review.payment.accuracytests;

import java.lang.reflect.Field;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.cronos.onlinereview.review.payment.ReviewerPayment;
import com.cronos.onlinereview.review.payment.impl.DefaultReviewPaymentCalculator;
import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.defaults.DefaultConfigurationObject;
import com.topcoder.management.resource.ReviewerStatistics;
import com.topcoder.management.resource.StatisticsType;

/**
 * Set of tests for testing DefaultReviewPaymentCalculator class.
 * @author vilain
 * @version 1.0
 */
public class DefaultReviewPaymentCalculatorAccuracyTests {

    /**
     * Instance of DefaultReviewPaymentCalculator for testing.
     */
    private DefaultReviewPaymentCalculator defaultReviewPaymentCalculator;

    /**
     * Setting up environment.
     * @throws Exception wraps all exceptions
     */
    @Before
    public void setUp() throws Exception {
        defaultReviewPaymentCalculator = new DefaultReviewPaymentCalculator();
        ConfigurationObject configurationObject = new DefaultConfigurationObject("config");
        configurationObject.setPropertyValue("loggerName", DefaultConfigurationObject.class.getName());
        defaultReviewPaymentCalculator.configure(configurationObject);
    }

    /**
     * Method under test
     * {@link DefaultReviewPaymentCalculator#configure(com.topcoder.configuration.ConfigurationObject)}
     * .Accuracy testing of properly initialized logger.
     * @throws Exception wraps all exceptions
     */
    @Test
    public final void testConfigureLoggerName() throws Exception {
        DefaultReviewPaymentCalculator currentDefaultReviewPaymentCalculator =
            new DefaultReviewPaymentCalculator();
        ConfigurationObject configurationObject = new DefaultConfigurationObject("config");
        configurationObject.setPropertyValue("loggerName", DefaultConfigurationObject.class.getName());
        currentDefaultReviewPaymentCalculator.configure(configurationObject);
        Assert.assertNotNull("log must be initialized", getFieldValue(currentDefaultReviewPaymentCalculator,
            "log"));
    }

    /**
     * Method under test
     * {@link DefaultReviewPaymentCalculator#configure(com.topcoder.configuration.ConfigurationObject)}
     * .Accuracy testing of properly initialized bonus percentage.
     * @throws Exception wraps all exceptions
     */
    @Test
    public final void testConfigureBonusPercentage() throws Exception {
        DefaultReviewPaymentCalculator currentDefaultReviewPaymentCalculator =
            new DefaultReviewPaymentCalculator();
        ConfigurationObject configurationObject = new DefaultConfigurationObject("config");
        configurationObject.setPropertyValue("bonusPercentage", "0.56");
        currentDefaultReviewPaymentCalculator.configure(configurationObject);
        Assert.assertEquals("bonus percentage must be initialized", 0.56, getFieldValue(
            currentDefaultReviewPaymentCalculator, "bonusPercentage"));
    }

    /**
     * Method under test
     * {@link DefaultReviewPaymentCalculator#computeReviewPayment(long, double, double, ReviewerStatistics[])}
     * .Accuracy testing of properly computed review payment.
     * @throws Exception wraps all exceptions
     */
    @Test
    public final void testComputePrimaryReviewerPayment() throws Exception {
        ReviewerPayment[] reviewerPayments =
            defaultReviewPaymentCalculator.computeReviewPayment(1, 120, 200, generateStatistics());
        ReviewerPayment primaryReviewerPayment = reviewerPayments[0];
        Assert.assertEquals("1 is expected as project id", 1, primaryReviewerPayment.getProjectId());
        Assert.assertEquals("1001 is expected as reviwer id", 1001, primaryReviewerPayment.getReviewerId());
        Assert.assertEquals("102 is expected as payment amount", 102, primaryReviewerPayment
            .getPaymentAmount(), 0.0001);
    }

    /**
     * Method under test
     * {@link DefaultReviewPaymentCalculator#computeReviewPayment(long, double, double, ReviewerStatistics[])}
     * .Accuracy testing of properly computed review payment.
     * @throws Exception wraps all exceptions
     */
    @Test
    public final void testComputeAccuracyReviewerPayment() throws Exception {
        ReviewerPayment[] reviewerPayments =
            defaultReviewPaymentCalculator.computeReviewPayment(1, 120, 200, generateStatistics());
        ReviewerPayment accuracyReviewerPayment = reviewerPayments[1];
        Assert.assertEquals("1 is expected as project id", 1, accuracyReviewerPayment.getProjectId());
        Assert.assertEquals("1002 is expected as reviwer id", 1002, accuracyReviewerPayment.getReviewerId());
        Assert.assertEquals("142.7877 is expected as payment amount", 142.78775, accuracyReviewerPayment
            .getPaymentAmount(), 0.0001);
    }

    /**
     * Method under test
     * {@link DefaultReviewPaymentCalculator#computeReviewPayment(long, double, double, ReviewerStatistics[])}
     * .Accuracy testing of properly computed review payment.
     * @throws Exception wraps all exceptions
     */
    @Test
    public final void testComputeStressReviewerPayment() throws Exception {
        ReviewerPayment[] reviewerPayments =
            defaultReviewPaymentCalculator.computeReviewPayment(1, 120, 200, generateStatistics());
        ReviewerPayment stressReviewerPayment = reviewerPayments[2];
        Assert.assertEquals("1 is expected as project id", 1, stressReviewerPayment.getProjectId());
        Assert.assertEquals("1003 is expected as reviwer id", 1003, stressReviewerPayment.getReviewerId());
        Assert.assertEquals("57.2122 is expected as payment amount", 57.2122, stressReviewerPayment
            .getPaymentAmount(), 0.0001);
    }

    /**
     * Generates reviewers' statistics.
     * @return statistics
     */
    private static ReviewerStatistics[] generateStatistics() {
        ReviewerStatistics[] statistics = new ReviewerStatistics[3];
        // primary reviewer statistic
        ReviewerStatistics primaryReviewerStats = new ReviewerStatistics();
        primaryReviewerStats.setProjectId(1);
        primaryReviewerStats.setReviewerId(1001);
        primaryReviewerStats.setTimelineReliability(0.85);
        primaryReviewerStats.setStatisticsType(StatisticsType.HISTORY);
        primaryReviewerStats.setCoverage(-1);
        primaryReviewerStats.setAccuracy(-1);
        statistics[0] = primaryReviewerStats;
        // secondary reviewer statistic
        ReviewerStatistics firstSecondaryReviewerStats = new ReviewerStatistics();
        firstSecondaryReviewerStats.setProjectId(1);
        firstSecondaryReviewerStats.setReviewerId(1002);
        firstSecondaryReviewerStats.setTimelineReliability(1);
        firstSecondaryReviewerStats.setCoverage(0.61);
        firstSecondaryReviewerStats.setAccuracy(0.85);
        firstSecondaryReviewerStats.setStatisticsType(StatisticsType.HISTORY);
        firstSecondaryReviewerStats.setTotalEvaluationCoefficient(-1);
        statistics[1] = firstSecondaryReviewerStats;
        // secondary reviewer statistic
        ReviewerStatistics secondSecondaryReviewerStats = new ReviewerStatistics();
        secondSecondaryReviewerStats.setProjectId(1);
        secondSecondaryReviewerStats.setReviewerId(1003);
        secondSecondaryReviewerStats.setTimelineReliability(0.74);
        secondSecondaryReviewerStats.setCoverage(0.39);
        secondSecondaryReviewerStats.setAccuracy(1);
        secondSecondaryReviewerStats.setStatisticsType(StatisticsType.HISTORY);
        secondSecondaryReviewerStats.setTotalEvaluationCoefficient(-1);
        statistics[2] = secondSecondaryReviewerStats;
        return statistics;
    }

    /**
     * Getting the value of the object's field.
     * @param object provided object
     * @param fieldName the name of the field
     * @return the valued of the field
     * @throws Exception wraps all exceptions
     */
    private static Object getFieldValue(Object object, String fieldName) throws Exception {
        Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(object);
    }
}