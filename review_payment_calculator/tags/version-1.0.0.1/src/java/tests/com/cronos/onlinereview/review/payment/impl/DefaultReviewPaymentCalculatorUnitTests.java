/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.review.payment.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import com.cronos.onlinereview.review.payment.InvalidReviewersStatisticsException;
import com.cronos.onlinereview.review.payment.ReviewPaymentCalculatorConfigurationException;
import com.cronos.onlinereview.review.payment.ReviewerPayment;
import com.cronos.onlinereview.review.payment.TestsHelper;
import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.management.resource.ReviewerStatistics;
import com.topcoder.management.resource.StatisticsType;

import junit.framework.JUnit4TestAdapter;

/**
 * <p>
 * Unit tests for <code>{@link DefaultReviewPaymentCalculator}</code> class.
 * </p>
 *
 * @author sparemax
 * @version 1.0
 */
public class DefaultReviewPaymentCalculatorUnitTests {
    /**
     * <p>
     * Represents the <code>DefaultReviewPaymentCalculator</code> instance used in tests.
     * </p>
     */
    private DefaultReviewPaymentCalculator instance;

    /**
     * <p>
     * Represents the configuration object used in tests.
     * </p>
     */
    private ConfigurationObject config;

    /**
     * <p>
     * Represents the project id used in tests.
     * </p>
     */
    private long projectId = 1;

    /**
     * <p>
     * Represents the primary payment used in tests.
     * </p>
     */
    private double primaryPayment = 120;

    /**
     * <p>
     * Represents the secondary payment pool used in tests.
     * </p>
     */
    private double secondaryPaymentPool = 200;

    /**
     * <p>
     * Represents the statistics used in tests.
     * </p>
     */
    private ReviewerStatistics[] statistics;

    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(DefaultReviewPaymentCalculatorUnitTests.class);
    }

    /**
     * <p>
     * Sets up the unit tests.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Before
    public void setUp() throws Exception {
        config = TestsHelper.getConfig();

        instance = new DefaultReviewPaymentCalculator();
        instance.configure(config);

        statistics = new ReviewerStatistics[] {
            TestsHelper.getReviewerStatistics(1, 1001, 0.85, -1.0, -1.0, -1.0, StatisticsType.HISTORY),
            TestsHelper.getReviewerStatistics(1, 1002, 1, 0.61, 0.85, -1.0, StatisticsType.HISTORY),
            TestsHelper.getReviewerStatistics(1, 1003, 0.74, 0.39, 1, -1.0, StatisticsType.HISTORY)
        };
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>DefaultReviewPaymentCalculator()</code>.<br>
     * Instance should be correctly created.
     * </p>
     */
    @Test
    public void testCtor() {
        instance = new DefaultReviewPaymentCalculator();

        assertNull("'log' should be correct.", TestsHelper.getField(instance, "log"));
        assertEquals("'bonusPercentage' should be correct.",
            0.0, (Double) TestsHelper.getField(instance, "bonusPercentage"), 0.001);
    }

    /**
     * <p>
     * Accuracy test for the method <code>configure(ConfigurationObject config)</code>.<br>
     * The result should be correct.
     * </p>
     */
    @Test
    public void test_configure_1() {
        instance.configure(config);

        assertNotNull("'configure' should be correct.", TestsHelper.getField(instance, "log"));
        assertEquals("'configure' should be correct.",
            0.2, (Double) TestsHelper.getField(instance, "bonusPercentage"), 0.001);
    }

    /**
     * <p>
     * Accuracy test for the method <code>configure(ConfigurationObject config)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_configure_2() throws Exception {
        config.removeProperty("loggerName");
        config.removeProperty("bonusPercentage");
        instance.configure(config);

        assertNull("'configure' should be correct.", TestsHelper.getField(instance, "log"));
        assertEquals("'configure' should be correct.",
            0.2, (Double) TestsHelper.getField(instance, "bonusPercentage"), 0.001);
    }

    /**
     * <p>
     * Failure test for the method <code>configure(ConfigurationObject config)</code> with config is
     * <code>null</code>.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_configure_configNull() throws Exception {
        config = null;

        instance.configure(config);
    }

    /**
     * <p>
     * Failure test for the method <code>configure(ConfigurationObject config)</code> with 'loggerName' is empty.<br>
     * <code>ReviewPaymentCalculatorConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReviewPaymentCalculatorConfigurationException.class)
    public void test_configure_loggerNameEmpty() throws Exception {
        config.setPropertyValue("loggerName", TestsHelper.EMPTY_STRING);

        instance.configure(config);
    }

    /**
     * <p>
     * Failure test for the method <code>configure(ConfigurationObject config)</code> with 'loggerName' is not a
     * string.<br>
     * <code>ReviewPaymentCalculatorConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReviewPaymentCalculatorConfigurationException.class)
    public void test_configure_loggerNameNotString() throws Exception {
        config.setPropertyValue("loggerName", new Integer(1));

        instance.configure(config);
    }

    /**
     * <p>
     * Failure test for the method <code>configure(ConfigurationObject config)</code> with 'bonusPercentage' is
     * empty.<br>
     * <code>ReviewPaymentCalculatorConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReviewPaymentCalculatorConfigurationException.class)
    public void test_configure_bonusPercentageEmpty() throws Exception {
        config.setPropertyValue("bonusPercentage", TestsHelper.EMPTY_STRING);

        instance.configure(config);
    }

    /**
     * <p>
     * Failure test for the method <code>configure(ConfigurationObject config)</code> with 'bonusPercentage' is not a
     * string.<br>
     * <code>ReviewPaymentCalculatorConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReviewPaymentCalculatorConfigurationException.class)
    public void test_configure_bonusPercentageNotString() throws Exception {
        config.setPropertyValue("bonusPercentage", new Integer(1));

        instance.configure(config);
    }

    /**
     * <p>
     * Failure test for the method <code>configure(ConfigurationObject config)</code> with 'bonusPercentage' cannot
     * be parsed.<br>
     * <code>ReviewPaymentCalculatorConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReviewPaymentCalculatorConfigurationException.class)
    public void test_configure_bonusPercentageInvalid1() throws Exception {
        config.setPropertyValue("bonusPercentage", "invalid_number");

        instance.configure(config);
    }

    /**
     * <p>
     * Failure test for the method <code>configure(ConfigurationObject config)</code> with 'bonusPercentage' is less
     * than <code>0</code>.<br>
     * <code>ReviewPaymentCalculatorConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReviewPaymentCalculatorConfigurationException.class)
    public void test_configure_bonusPercentageInvalid2() throws Exception {
        config.setPropertyValue("bonusPercentage", "-0.001");

        instance.configure(config);
    }

    /**
     * <p>
     * Failure test for the method <code>configure(ConfigurationObject config)</code> with 'bonusPercentage' is greater
     * than <code>1</code>.<br>
     * <code>ReviewPaymentCalculatorConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReviewPaymentCalculatorConfigurationException.class)
    public void test_configure_bonusPercentageInvalid3() throws Exception {
        config.setPropertyValue("bonusPercentage", "1.001");

        instance.configure(config);
    }

    /**
     * <p>
     * Accuracy test for the method <code>computeReviewPayment(long projectId, double primaryPayment,
     * double secondaryPaymentPool, ReviewerStatistics[] statistics)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_computeReviewPayment_1() throws Exception {
        ReviewerPayment[] res =
            instance.computeReviewPayment(projectId, primaryPayment, secondaryPaymentPool, statistics);

        assertEquals("'computeReviewPayment' should be correct.", 3, res.length);

        ReviewerPayment reviewerPayment = res[0];
        assertEquals("'computeReviewPayment' should be correct.", 1, reviewerPayment.getProjectId());
        assertEquals("'computeReviewPayment' should be correct.", 1001, reviewerPayment.getReviewerId());
        assertEquals("'computeReviewPayment' should be correct.", 102, reviewerPayment.getPaymentAmount(), 0.0001);
        reviewerPayment = res[1];
        assertEquals("'computeReviewPayment' should be correct.", 1, reviewerPayment.getProjectId());
        assertEquals("'computeReviewPayment' should be correct.", 1002, reviewerPayment.getReviewerId());
        assertEquals("'computeReviewPayment' should be correct.", 142.7877, reviewerPayment.getPaymentAmount(), 0.0001);
        reviewerPayment = res[2];
        assertEquals("'computeReviewPayment' should be correct.", 1, reviewerPayment.getProjectId());
        assertEquals("'computeReviewPayment' should be correct.", 1003, reviewerPayment.getReviewerId());
        assertEquals("'computeReviewPayment' should be correct.", 57.2122, reviewerPayment.getPaymentAmount(), 0.0001);
    }

    /**
     * <p>
     * Accuracy test for the method <code>computeReviewPayment(long projectId, double primaryPayment,
     * double secondaryPaymentPool, ReviewerStatistics[] statistics)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_computeReviewPayment_2() throws Exception {
        statistics[1].setTotalEvaluationCoefficient(0.5185);

        ReviewerPayment[] res =
            instance.computeReviewPayment(projectId, primaryPayment, secondaryPaymentPool, statistics);

        assertEquals("'computeReviewPayment' should be correct.", 3, res.length);

        ReviewerPayment reviewerPayment = res[0];
        assertEquals("'computeReviewPayment' should be correct.", 1, reviewerPayment.getProjectId());
        assertEquals("'computeReviewPayment' should be correct.", 1001, reviewerPayment.getReviewerId());
        assertEquals("'computeReviewPayment' should be correct.", 102, reviewerPayment.getPaymentAmount(), 0.0001);
        reviewerPayment = res[1];
        assertEquals("'computeReviewPayment' should be correct.", 1, reviewerPayment.getProjectId());
        assertEquals("'computeReviewPayment' should be correct.", 1002, reviewerPayment.getReviewerId());
        assertEquals("'computeReviewPayment' should be correct.", 142.7877, reviewerPayment.getPaymentAmount(), 0.0001);
        reviewerPayment = res[2];
        assertEquals("'computeReviewPayment' should be correct.", 1, reviewerPayment.getProjectId());
        assertEquals("'computeReviewPayment' should be correct.", 1003, reviewerPayment.getReviewerId());
        assertEquals("'computeReviewPayment' should be correct.", 57.2122, reviewerPayment.getPaymentAmount(), 0.0001);
    }

    /**
     * <p>
     * Accuracy test for the method <code>computeReviewPayment(long projectId, double primaryPayment,
     * double secondaryPaymentPool, ReviewerStatistics[] statistics)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_computeReviewPayment_3() throws Exception {
        statistics = new ReviewerStatistics[] {
            TestsHelper.getReviewerStatistics(1, 1001, 0.85, -1.0, -1.0, -1.0, StatisticsType.HISTORY),
            TestsHelper.getReviewerStatistics(1, 1002, 1, 0.61, 0.85, -1.0, StatisticsType.HISTORY),
            TestsHelper.getReviewerStatistics(1, 1003, 0.74, 0.39, 1, -1.0, StatisticsType.HISTORY),
            TestsHelper.getReviewerStatistics(1, 1004, 0.74, 0.39, 1, -1.0, StatisticsType.HISTORY)
        };

        ReviewerPayment[] res =
            instance.computeReviewPayment(projectId, primaryPayment, secondaryPaymentPool, statistics);

        assertEquals("'computeReviewPayment' should be correct.", 4, res.length);

        ReviewerPayment reviewerPayment = res[0];
        assertEquals("'computeReviewPayment' should be correct.", 1, reviewerPayment.getProjectId());
        assertEquals("'computeReviewPayment' should be correct.", 1001, reviewerPayment.getReviewerId());
        assertEquals("'computeReviewPayment' should be correct.", 102, reviewerPayment.getPaymentAmount(), 0.0001);
        reviewerPayment = res[1];
        assertEquals("'computeReviewPayment' should be correct.", 1, reviewerPayment.getProjectId());
        assertEquals("'computeReviewPayment' should be correct.", 1002, reviewerPayment.getReviewerId());
        assertEquals("'computeReviewPayment' should be correct.", 115.7141, reviewerPayment.getPaymentAmount(), 0.0001);
        reviewerPayment = res[2];
        assertEquals("'computeReviewPayment' should be correct.", 1, reviewerPayment.getProjectId());
        assertEquals("'computeReviewPayment' should be correct.", 1003, reviewerPayment.getReviewerId());
        assertEquals("'computeReviewPayment' should be correct.", 42.1429, reviewerPayment.getPaymentAmount(), 0.0001);
        reviewerPayment = res[3];
        assertEquals("'computeReviewPayment' should be correct.", 1, reviewerPayment.getProjectId());
        assertEquals("'computeReviewPayment' should be correct.", 1004, reviewerPayment.getReviewerId());
        assertEquals("'computeReviewPayment' should be correct.", 42.1429, reviewerPayment.getPaymentAmount(), 0.0001);
    }

    /**
     * <p>
     * Accuracy test for the method <code>computeReviewPayment(long projectId, double primaryPayment,
     * double secondaryPaymentPool, ReviewerStatistics[] statistics)</code> (without logging).<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_computeReviewPayment_NoLogging() throws Exception {
        config.removeProperty("loggerName");
        instance.configure(config);

        ReviewerPayment[] res =
            instance.computeReviewPayment(projectId, primaryPayment, secondaryPaymentPool, statistics);

        assertEquals("'computeReviewPayment' should be correct.", 3, res.length);

        ReviewerPayment reviewerPayment = res[0];
        assertEquals("'computeReviewPayment' should be correct.", 1, reviewerPayment.getProjectId());
        assertEquals("'computeReviewPayment' should be correct.", 1001, reviewerPayment.getReviewerId());
        assertEquals("'computeReviewPayment' should be correct.", 102, reviewerPayment.getPaymentAmount(), 0.0001);
        reviewerPayment = res[1];
        assertEquals("'computeReviewPayment' should be correct.", 1, reviewerPayment.getProjectId());
        assertEquals("'computeReviewPayment' should be correct.", 1002, reviewerPayment.getReviewerId());
        assertEquals("'computeReviewPayment' should be correct.", 142.7877, reviewerPayment.getPaymentAmount(), 0.0001);
        reviewerPayment = res[2];
        assertEquals("'computeReviewPayment' should be correct.", 1, reviewerPayment.getProjectId());
        assertEquals("'computeReviewPayment' should be correct.", 1003, reviewerPayment.getReviewerId());
        assertEquals("'computeReviewPayment' should be correct.", 57.2122, reviewerPayment.getPaymentAmount(), 0.0001);
    }

    /**
     * <p>
     * Failure test for the method <code>computeReviewPayment(long projectId, double primaryPayment,
     * double secondaryPaymentPool, ReviewerStatistics[] statistics)</code> with primaryPayment is 0.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_computeReviewPayment_primaryPaymentZero() throws Exception {
        primaryPayment = 0;

        instance.computeReviewPayment(projectId, primaryPayment, secondaryPaymentPool, statistics);
    }

    /**
     * <p>
     * Failure test for the method <code>computeReviewPayment(long projectId, double primaryPayment,
     * double secondaryPaymentPool, ReviewerStatistics[] statistics)</code> with primaryPayment is 0
     * (without logging).<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_computeReviewPayment_primaryPaymentZero_NoLogging() throws Exception {
        config.removeProperty("loggerName");
        instance.configure(config);

        primaryPayment = 0;

        instance.computeReviewPayment(projectId, primaryPayment, secondaryPaymentPool, statistics);
    }

    /**
     * <p>
     * Failure test for the method <code>computeReviewPayment(long projectId, double primaryPayment,
     * double secondaryPaymentPool, ReviewerStatistics[] statistics)</code> with secondaryPaymentPool is 0.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_computeReviewPayment_secondaryPaymentPoolZero() throws Exception {
        secondaryPaymentPool = 0;

        instance.computeReviewPayment(projectId, primaryPayment, secondaryPaymentPool, statistics);
    }

    /**
     * <p>
     * Failure test for the method <code>computeReviewPayment(long projectId, double primaryPayment,
     * double secondaryPaymentPool, ReviewerStatistics[] statistics)</code> with projectId is 0.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_computeReviewPayment_projectIdZero() throws Exception {
        projectId = 0;

        instance.computeReviewPayment(projectId, primaryPayment, secondaryPaymentPool, statistics);
    }

    /**
     * <p>
     * Failure test for the method <code>computeReviewPayment(long projectId, double primaryPayment,
     * double secondaryPaymentPool, ReviewerStatistics[] statistics)</code> with statistics is <code>null</code>.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_computeReviewPayment_statisticsNull() throws Exception {
        statistics = null;

        instance.computeReviewPayment(projectId, primaryPayment, secondaryPaymentPool, statistics);
    }

    /**
     * <p>
     * Failure test for the method <code>computeReviewPayment(long projectId, double primaryPayment,
     * double secondaryPaymentPool, ReviewerStatistics[] statistics)</code> with statistics contains
     * <code>null</code>.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_computeReviewPayment_statisticsContainsNull() throws Exception {
        statistics[0] = null;

        instance.computeReviewPayment(projectId, primaryPayment, secondaryPaymentPool, statistics);
    }

    /**
     * <p>
     * Failure test for the method <code>computeReviewPayment(long projectId, double primaryPayment,
     * double secondaryPaymentPool, ReviewerStatistics[] statistics)</code> with statistics.length
     * &lt;= <code>2</code>.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_computeReviewPayment_statisticsLengthInvalid() throws Exception {
        statistics = new ReviewerStatistics[] {statistics[0], statistics[1]};

        instance.computeReviewPayment(projectId, primaryPayment, secondaryPaymentPool, statistics);
    }

    /**
     * <p>
     * Failure test for the method <code>computeReviewPayment(long projectId, double primaryPayment,
     * double secondaryPaymentPool, ReviewerStatistics[] statistics)</code> with statistics type is not 'HISTORY'.<br>
     * <code>InvalidReviewersStatisticsException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = InvalidReviewersStatisticsException.class)
    public void test_computeReviewPayment_StatisticsTypeInvalid1() throws Exception {
        statistics[0].setStatisticsType(StatisticsType.AVERGAE);

        instance.computeReviewPayment(projectId, primaryPayment, secondaryPaymentPool, statistics);
    }

    /**
     * <p>
     * Failure test for the method <code>computeReviewPayment(long projectId, double primaryPayment,
     * double secondaryPaymentPool, ReviewerStatistics[] statistics)</code> with statistics type is invalid.<br>
     * <code>InvalidReviewersStatisticsException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = InvalidReviewersStatisticsException.class)
    public void test_computeReviewPayment_StatisticsTypeInvalid2() throws Exception {
        statistics[0].setStatisticsType(null);

        instance.computeReviewPayment(projectId, primaryPayment, secondaryPaymentPool, statistics);
    }

    /**
     * <p>
     * Failure test for the method <code>computeReviewPayment(long projectId, double primaryPayment,
     * double secondaryPaymentPool, ReviewerStatistics[] statistics)</code> with no primary reviewer statistics
     * found.<br>
     * <code>InvalidReviewersStatisticsException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = InvalidReviewersStatisticsException.class)
    public void test_computeReviewPayment_NoPrimaryReviewer() throws Exception {
        statistics[0].setCoverage(0.0);

        instance.computeReviewPayment(projectId, primaryPayment, secondaryPaymentPool, statistics);
    }

    /**
     * <p>
     * Failure test for the method <code>computeReviewPayment(long projectId, double primaryPayment,
     * double secondaryPaymentPool, ReviewerStatistics[] statistics)</code> with more than one primary reviewer
     * statistics found.<br>
     * <code>InvalidReviewersStatisticsException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = InvalidReviewersStatisticsException.class)
    public void test_computeReviewPayment_MultiplePrimaryReviewers() throws Exception {
        statistics[1].setCoverage(-1.0);
        statistics[1].setAccuracy(-1.0);

        instance.computeReviewPayment(projectId, primaryPayment, secondaryPaymentPool, statistics);
    }

    /**
     * <p>
     * Failure test for the method <code>computeReviewPayment(long projectId, double primaryPayment,
     * double secondaryPaymentPool, ReviewerStatistics[] statistics)</code> with timeline reliability is not
     * in range [0, 1].<br>
     * <code>InvalidReviewersStatisticsException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = InvalidReviewersStatisticsException.class)
    public void test_computeReviewPayment_TimelineReliabilityInvalid1() throws Exception {
        statistics[0].setTimelineReliability(-0.001);

        instance.computeReviewPayment(projectId, primaryPayment, secondaryPaymentPool, statistics);
    }

    /**
     * <p>
     * Failure test for the method <code>computeReviewPayment(long projectId, double primaryPayment,
     * double secondaryPaymentPool, ReviewerStatistics[] statistics)</code> with timeline reliability is not
     * in range [0, 1].<br>
     * <code>InvalidReviewersStatisticsException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = InvalidReviewersStatisticsException.class)
    public void test_computeReviewPayment_TimelineReliabilityInvalid2() throws Exception {
        statistics[0].setTimelineReliability(1.001);

        instance.computeReviewPayment(projectId, primaryPayment, secondaryPaymentPool, statistics);
    }

    /**
     * <p>
     * Failure test for the method <code>computeReviewPayment(long projectId, double primaryPayment,
     * double secondaryPaymentPool, ReviewerStatistics[] statistics)</code> with timeline reliability is not
     * in range [0, 1].<br>
     * <code>InvalidReviewersStatisticsException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = InvalidReviewersStatisticsException.class)
    public void test_computeReviewPayment_TimelineReliabilityInvalid3() throws Exception {
        statistics[1].setTimelineReliability(1.001);

        instance.computeReviewPayment(projectId, primaryPayment, secondaryPaymentPool, statistics);
    }

    /**
     * <p>
     * Failure test for the method <code>computeReviewPayment(long projectId, double primaryPayment,
     * double secondaryPaymentPool, ReviewerStatistics[] statistics)</code> with coverage is not
     * in range [0, 1].<br>
     * <code>InvalidReviewersStatisticsException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = InvalidReviewersStatisticsException.class)
    public void test_computeReviewPayment_CoverageInvalid1() throws Exception {
        statistics[1].setCoverage(-0.001);

        instance.computeReviewPayment(projectId, primaryPayment, secondaryPaymentPool, statistics);
    }

    /**
     * <p>
     * Failure test for the method <code>computeReviewPayment(long projectId, double primaryPayment,
     * double secondaryPaymentPool, ReviewerStatistics[] statistics)</code> with coverage is not
     * in range [0, 1].<br>
     * <code>InvalidReviewersStatisticsException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = InvalidReviewersStatisticsException.class)
    public void test_computeReviewPayment_CoverageInvalid2() throws Exception {
        statistics[1].setCoverage(1.001);

        instance.computeReviewPayment(projectId, primaryPayment, secondaryPaymentPool, statistics);
    }

    /**
     * <p>
     * Failure test for the method <code>computeReviewPayment(long projectId, double primaryPayment,
     * double secondaryPaymentPool, ReviewerStatistics[] statistics)</code> with accuracy is not
     * in range [0, 1].<br>
     * <code>InvalidReviewersStatisticsException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = InvalidReviewersStatisticsException.class)
    public void test_computeReviewPayment_AccuracyInvalid1() throws Exception {
        statistics[1].setAccuracy(-0.001);

        instance.computeReviewPayment(projectId, primaryPayment, secondaryPaymentPool, statistics);
    }

    /**
     * <p>
     * Failure test for the method <code>computeReviewPayment(long projectId, double primaryPayment,
     * double secondaryPaymentPool, ReviewerStatistics[] statistics)</code> with accuracy is not
     * in range [0, 1].<br>
     * <code>InvalidReviewersStatisticsException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = InvalidReviewersStatisticsException.class)
    public void test_computeReviewPayment_AccuracyInvalid2() throws Exception {
        statistics[1].setAccuracy(1.001);

        instance.computeReviewPayment(projectId, primaryPayment, secondaryPaymentPool, statistics);
    }

    /**
     * <p>
     * Failure test for the method <code>computeReviewPayment(long projectId, double primaryPayment,
     * double secondaryPaymentPool, ReviewerStatistics[] statistics)</code> with calculated total evaluation coefficient
     * is not in range [0, 1].<br>
     * <code>InvalidReviewersStatisticsException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = InvalidReviewersStatisticsException.class)
    public void test_computeReviewPayment_TotalEvaluationCoefficientInvalid1() throws Exception {
        statistics[1].setTotalEvaluationCoefficient(-0.001);

        instance.computeReviewPayment(projectId, primaryPayment, secondaryPaymentPool, statistics);
    }

    /**
     * <p>
     * Failure test for the method <code>computeReviewPayment(long projectId, double primaryPayment,
     * double secondaryPaymentPool, ReviewerStatistics[] statistics)</code> with calculated total evaluation coefficient
     * is not in range [0, 1].<br>
     * <code>InvalidReviewersStatisticsException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = InvalidReviewersStatisticsException.class)
    public void test_computeReviewPayment_TotalEvaluationCoefficientInvalid2() throws Exception {
        statistics[1].setTotalEvaluationCoefficient(1.001);

        instance.computeReviewPayment(projectId, primaryPayment, secondaryPaymentPool, statistics);
    }

    /**
     * <p>
     * Failure test for the method <code>computeReviewPayment(long projectId, double primaryPayment,
     * double secondaryPaymentPool, ReviewerStatistics[] statistics)</code> with sum of total evaluation coefficient
     * is 0 (without logging).<br>
     * <code>InvalidReviewersStatisticsException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = InvalidReviewersStatisticsException.class)
    public void test_computeReviewPayment_totalEvaluationCoefficientSumZero_NoLogging() throws Exception {
        config.removeProperty("loggerName");
        instance.configure(config);

        statistics[1].setTimelineReliability(0);
        statistics[2].setTimelineReliability(0);

        instance.computeReviewPayment(projectId, primaryPayment, secondaryPaymentPool, statistics);
    }
}
