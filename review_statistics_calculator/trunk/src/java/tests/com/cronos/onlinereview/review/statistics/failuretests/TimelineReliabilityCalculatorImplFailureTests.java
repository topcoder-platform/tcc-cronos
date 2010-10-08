/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.review.statistics.failuretests;

import java.util.Date;

import junit.framework.TestCase;

import com.cronos.onlinereview.review.statistics.StatisticsCalculatorConfigurationException;
import com.cronos.onlinereview.review.statistics.impl.TimelineReliabilityCalculatorImpl;
import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.persistence.ConfigurationFileManager;


/**
 * Failure test cases <code>TimelineReliabilityCalculatorImpl</code>.
 *
 * @author gjw99
 * @version 1.0
 */
public class TimelineReliabilityCalculatorImplFailureTests extends TestCase {
    /** Represents the instance to be tested. */
    private TimelineReliabilityCalculatorImpl instance;

    /** Valid configuration object. */
    private ConfigurationObject config;

    /**
     * Setup the test environment.
     *
     * @throws Exception if any error
     */
    public void setUp() throws Exception {
        this.instance = new TimelineReliabilityCalculatorImpl();

        ConfigurationFileManager manager = new ConfigurationFileManager("test_files/failure/failure.properties");
        config = manager.getConfiguration("TimelineReliabilityCalculator").getChild("TimelineReliabilityCalculator");
    }

    /**
     * Tear down the environment
     */
    public void tearDown() {
        this.instance = null;
    }

    /**
     * Test method configure.
     *
     * @throws Exception if any error
     */
    public void test_configure1() throws Exception {
        try {
            instance.configure(null);
            fail("IAE is required");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Test method configure.
     *
     * @throws Exception if any error
     */
    public void test_configure2() throws Exception {
        try {
            config.setPropertyValue("defaultPenaltyPerHour", "-1");
            instance.configure(config);
            fail("StatisticsCalculatorConfigurationException is required");
        } catch (StatisticsCalculatorConfigurationException e) {
            // ok
        }
    }

    /**
     * Test method configure.
     *
     * @throws Exception if any error
     */
    public void test_configure3() throws Exception {
        try {
            config.setPropertyValue("penaltyPerHourForPhaseType3", "-1");
            instance.configure(config);
            fail("StatisticsCalculatorConfigurationException is required");
        } catch (StatisticsCalculatorConfigurationException e) {
            // ok
        }
    }

    /**
     * Test method configure.
     *
     * @throws Exception if any error
     */
    public void test_configure4() throws Exception {
        try {
            config.setPropertyValue("defaultPenaltyPerHour", "aaa");
            instance.configure(config);
            fail("StatisticsCalculatorConfigurationException is required");
        } catch (StatisticsCalculatorConfigurationException e) {
            // ok
        }
    }

    /**
     * Test method configure.
     *
     * @throws Exception if any error
     */
    public void test_configure5() throws Exception {
        try {
            config.setPropertyValue("penaltyPerHourForPhaseType3", "aaa");
            instance.configure(config);
            fail("StatisticsCalculatorConfigurationException is required");
        } catch (StatisticsCalculatorConfigurationException e) {
            // ok
        }
    }

    /**
     * Test method calculateReliability.
     *
     * @throws Exception if any error
     */
    public void test_calculateReliability1() throws Exception {
        try {
        	instance.configure(config);
        	Date[] deadlines = new Date[2];
        	deadlines[0] = new Date();
        	deadlines[1] = new Date();
        	Date[] committed = new Date[2];
        	committed[0] = new Date();
        	committed[1] = new Date();
        	long[] ids = new long[]{1, 2};
            instance.calculateReliability(null, committed, ids);
            fail("IAE is required");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Test method calculateReliability.
     *
     * @throws Exception if any error
     */
    public void test_calculateReliability2() throws Exception {
        try {
        	instance.configure(config);
        	Date[] deadlines = new Date[2];
        	deadlines[0] = new Date();
        	deadlines[1] = new Date();
        	Date[] committed = new Date[2];
        	committed[0] = new Date();
        	committed[1] = new Date();
        	long[] ids = new long[]{1, 2};
            instance.calculateReliability(deadlines, null, ids);
            fail("IAE is required");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Test method calculateReliability.
     *
     * @throws Exception if any error
     */
    public void test_calculateReliability3() throws Exception {
        try {
        	instance.configure(config);
        	Date[] deadlines = new Date[2];
        	deadlines[0] = new Date();
        	deadlines[1] = new Date();
        	Date[] committed = new Date[2];
        	committed[0] = new Date();
        	committed[1] = new Date();
        	long[] ids = new long[]{1, 2};
            instance.calculateReliability(deadlines, committed, null);
            fail("IAE is required");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Test method calculateReliability.
     *
     * @throws Exception if any error
     */
    public void test_calculateReliability4() throws Exception {
        try {
        	instance.configure(config);
        	Date[] deadlines = new Date[2];
        	deadlines[1] = new Date();
        	Date[] committed = new Date[2];
        	committed[0] = new Date();
        	committed[1] = new Date();
        	long[] ids = new long[]{1, 2};
            instance.calculateReliability(deadlines, committed, ids);
            fail("IAE is required");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Test method calculateReliability.
     *
     * @throws Exception if any error
     */
    public void test_calculateReliability5() throws Exception {
        try {
        	instance.configure(config);
        	Date[] deadlines = new Date[2];
        	deadlines[0] = new Date();
        	deadlines[1] = new Date();
        	Date[] committed = new Date[2];
        	committed[0] = new Date();
        	long[] ids = new long[]{1, 2};
            instance.calculateReliability(deadlines, committed, ids);
            fail("IAE is required");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Test method calculateReliability.
     *
     * @throws Exception if any error
     */
    public void test_calculateReliability6() throws Exception {
        try {
        	instance.configure(config);
        	Date[] deadlines = new Date[2];
        	deadlines[0] = new Date();
        	deadlines[1] = new Date();
        	Date[] committed = new Date[1];
        	committed[0] = new Date();
        	long[] ids = new long[]{1, 2};
            instance.calculateReliability(deadlines, committed, ids);
            fail("IAE is required");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Test method calculateReliability.
     *
     * @throws Exception if any error
     */
    public void test_calculateReliability7() throws Exception {
        try {
        	instance.configure(config);
        	Date[] deadlines = new Date[2];
        	deadlines[0] = new Date();
        	deadlines[1] = new Date();
        	Date[] committed = new Date[2];
        	committed[0] = new Date();
        	committed[1] = new Date();
        	long[] ids = new long[]{-1, 2};
            instance.calculateReliability(deadlines, committed, ids);
            fail("IAE is required");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }
}
