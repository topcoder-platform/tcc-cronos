/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.review.statistics.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import com.cronos.onlinereview.review.statistics.StatisticsCalculatorConfigurationException;
import com.topcoder.configuration.ConfigurationObject;

import junit.framework.TestCase;

/**
 * <p>
 * Unit test cases for <code>TimelineReliabilityCalculatorImpl</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class TimelineReliabilityCalculatorImplUnitTests extends TestCase {
    /**
     * <code>TimelineReliabilityCalculatorImpl</code> class instance for unit test.
     */
    private TimelineReliabilityCalculatorImpl instance;

    /**
     * <code>ConfigurationObject</code> class instance for unit test.
     */
    private ConfigurationObject cfgObject;

    /**
     * <p>
     * Set up.
     * </p>
     *
     * @throws Exception for JUnit.
     */
    public void setUp() throws Exception {
        instance = new TimelineReliabilityCalculatorImpl();
        cfgObject = TestsHelper.loadConfig("config.xml").getChild(TestsHelper.NAMESPACE).getChild(
            "timelineReliabilityCalculatorConfig");
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>TimelineReliabilityCalculatorImpl()</code>.<br>
     * Instance should be correctly created.
     * </p>
     */
    public void testCtor_TimelineReliabilityCalculatorImpl() {
        assertNotNull("Instance should be correctly created.", instance);
    }

    /**
     * <p>
     * Accuracy test for the method <code>configure(ConfigurationObject)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    @SuppressWarnings("unchecked")
    public void testTimelineReliabilityCalculatorImpl_configure() throws Exception {
        instance.configure(cfgObject);

        Map map = (Map) TestsHelper.getField(instance, "penaltyPerHourByPhaseType", instance.getClass());
        assertEquals(1, map.size());
    }

    /**
     * <p>
     * Tests failure of <code>configure(ConfigurationObject)()</code> method with invalid defaultPenaltyPerHour. <br>
     * <code>StatisticsCalculatorConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testTimelineReliabilityCalculatorImpl_configure_Invalid_defaultPenaltyPerHour() throws Exception {
        try {
            ConfigurationObject config = TestsHelper.loadConfig("configError.xml").getChild(
                "penaltyPerHourForPhaseTypeError").getChild("invalidDefault");
            instance.configure(config);

            fail("StatisticsCalculatorConfigurationException is expected.");
        } catch (StatisticsCalculatorConfigurationException e) {
            // Good
            assertEquals("Error during configuration of TimelineReliabilityCalculatorImpl class.", e.getMessage());
        }
    }

    /**
     * <p>
     * Tests failure of <code>configure(ConfigurationObject)()</code> method with invalid pointsForEvaluationType. <br>
     * <code>StatisticsCalculatorConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testTimelineReliabilityCalculatorImpl_configure_Invalid_pointsForEvaluationType() throws Exception {
        try {
            ConfigurationObject config = TestsHelper.loadConfig("configError.xml").getChild(
                "penaltyPerHourForPhaseTypeError").getChild("invalidPoints");
            instance.configure(config);

            fail("StatisticsCalculatorConfigurationException is expected.");
        } catch (StatisticsCalculatorConfigurationException e) {
            // Good
            assertEquals("Error during configuration of TimelineReliabilityCalculatorImpl class.", e.getMessage());
        }
    }

    /**
     * <p>
     * Accuracy test for the method <code>calculateReliability(Review[][])</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testTimelineReliabilityCalculatorImpl_calculateReliability_1() throws Exception {
        instance.configure(cfgObject);

        SimpleDateFormat sdf = new SimpleDateFormat("DD/MM/yyyy hh:mm:ss");
        Date base = sdf.parse("22/10/2010 00:00:00");
        Date diffOnePointTwo = sdf.parse("22/10/2010 01:12:00");
        Date diffTwoAndHalf = sdf.parse("22/10/2010 02:30:00");
        Date diffThreeAndHalf = sdf.parse("22/10/2010 03:30:00");

        Date[][] deadlines = new Date[][] { {base, base, base, base}, {base, base, base, base},
            {base, base, base, base}};
        Date[][] committed = new Date[][] { {base, diffTwoAndHalf, base, base}, {base, base, base, base},
            {diffTwoAndHalf, base, diffOnePointTwo, diffThreeAndHalf}};
        long[] phaseTypeIds = new long[] {3, 13, 14, 17};

        double value = 1.0 - (2.5 * 0.04 + 1.2 * 0.02 + 3.5 * 0.02);
        double[] result = new double[] {0.95, 1, value};
        for (int nReview = 0; nReview < result.length; nReview++) {
            assertEquals(result[nReview], instance.calculateReliability(deadlines[nReview], committed[nReview],
                phaseTypeIds));
        }
    }

    /**
     * <p>
     * Tests failure of <code>calculateReliability(Date[], Date[], Date[])</code> method with deadlines is
     * <code>null</code>.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception for JUnit.
     */
    public void testTimelineReliabilityCalculatorImpl_calculateReliability_deadlines_Null() throws Exception {
        try {
            instance.configure(cfgObject);

            instance.calculateReliability(null, new Date[0], new long[0]);

            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // Good
            assertTrue(e.getMessage().contains("deadlines"));
        }
    }

    /**
     * <p>
     * Tests failure of <code>calculateReliability(Date[], Date[], Date[])</code> method with committed is
     * <code>null</code>.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception for JUnit.
     */
    public void testTimelineReliabilityCalculatorImpl_calculateReliability_committed_Null() throws Exception {
        try {
            instance.configure(cfgObject);

            instance.calculateReliability(new Date[] {new Date()}, null, new long[0]);

            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // Good
            assertTrue(e.getMessage().contains("committed"));
        }
    }

    /**
     * <p>
     * Tests failure of <code>calculateReliability(Date[], Date[], Date[])</code> method with phaseTypeIds is
     * <code>null</code>.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception for JUnit.
     */
    public void testTimelineReliabilityCalculatorImpl_calculateReliability_phaseTypeIds_Null() throws Exception {
        try {
            instance.configure(cfgObject);

            instance.calculateReliability(new Date[] {new Date()}, new Date[] {new Date()}, null);

            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // Good
            assertTrue(e.getMessage().contains("phaseTypeIds"));
        }
    }

    /**
     * <p>
     * Tests failure of <code>calculateReliability(Review[][])</code> method with pointsForEvaluationType is
     * <code>null</code>.<br>
     * <code>IllegalStateException</code> is expected.
     * </p>
     *
     * @throws Exception for JUnit.
     */
    public void testTimelineReliabilityCalculatorImpl_pointsForAccurateEvaluationType_Null() throws Exception {
        try {
            instance.calculateReliability(new Date[] {new Date()}, new Date[] {new Date()}, new long[] {1L});

            fail("IllegalStateException is expected.");
        } catch (IllegalStateException e) {
            // Good
            assertTrue(e.getMessage().contains("penaltyPerHourByPhaseType"));
        }
    }

    /**
     * <p>
     * Tests failure of <code>calculateReliability(Review[][])</code> method with different size.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception for JUnit.
     */
    public void testTimelineReliabilityCalculatorImpl_pointsForAccurateEvaluationType_Different_1() throws Exception {
        try {
            instance.configure(cfgObject);

            instance.calculateReliability(new Date[] {new Date(), new Date()}, new Date[] {new Date()},
                new long[] {1L});

            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // Good
            assertTrue(e.getMessage().contains("Arguments size are different."));
        }
    }

    /**
     * <p>
     * Tests failure of <code>calculateReliability(Review[][])</code> method with different size.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception for JUnit.
     */
    public void testTimelineReliabilityCalculatorImpl_pointsForAccurateEvaluationType_Different_2() throws Exception {
        try {
            instance.configure(cfgObject);

            instance.calculateReliability(new Date[] {new Date()}, new Date[] {new Date()}, new long[] {1L, 1L});

            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // Good
            assertTrue(e.getMessage().contains("Arguments size are different."));
        }
    }

    /**
     * <p>
     * Tests failure of <code>calculateReliability(Review[][])</code> method with different size.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception for JUnit.
     */
    public void testTimelineReliabilityCalculatorImpl_pointsForAccurateEvaluationType_Different_3() throws Exception {
        try {
            instance.configure(cfgObject);

            instance.calculateReliability(new Date[] {new Date()}, new Date[] {new Date(), new Date()}, new long[] {
                1L, 1L});

            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // Good
            assertTrue(e.getMessage().contains("Arguments size are different."));
        }
    }

    /**
     * <p>
     * Tests failure of <code>calculateReliability(Review[][])</code> method with <code>null</code> in array.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception for JUnit.
     */
    public void testTimelineReliabilityCalculatorImpl_pointsForAccurateEvaluationType_Null_1() throws Exception {
        try {
            instance.configure(cfgObject);

            instance.calculateReliability(new Date[] {null}, new Date[] {new Date()}, new long[] {1L});

            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // Good
            assertTrue(e.getMessage().contains("element of deadline"));
        }
    }

    /**
     * <p>
     * Tests failure of <code>calculateReliability(Review[][])</code> method with <code>null</code> in array.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception for JUnit.
     */
    public void testTimelineReliabilityCalculatorImpl_pointsForAccurateEvaluationType_Null_2() throws Exception {
        try {
            instance.configure(cfgObject);

            instance.calculateReliability(new Date[] {new Date()}, new Date[] {null}, new long[] {1L});

            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // Good
            assertTrue(e.getMessage().contains("element of committed"));
        }
    }
}
