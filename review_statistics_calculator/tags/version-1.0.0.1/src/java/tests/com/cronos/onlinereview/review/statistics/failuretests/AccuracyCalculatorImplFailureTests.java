/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.review.statistics.failuretests;

import junit.framework.TestCase;

import com.cronos.onlinereview.review.statistics.StatisticsCalculatorConfigurationException;
import com.cronos.onlinereview.review.statistics.impl.AccuracyCalculatorImpl;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.persistence.ConfigurationFileManager;
import com.topcoder.management.review.data.Review;


/**
 * Failure test cases <code>AccuracyCalculatorImpl</code>.
 *
 * @author gjw99
 * @version 1.0
 */
public class AccuracyCalculatorImplFailureTests extends TestCase {
    /** Represents the instance to be tested. */
    private AccuracyCalculatorImpl instance;

    /** Valid configuration object. */
    private ConfigurationObject config;

    /**
     * Setup the test environment.
     *
     * @throws Exception if any error
     */
    public void setUp() throws Exception {
        this.instance = new AccuracyCalculatorImpl();

        ConfigurationFileManager manager = new ConfigurationFileManager("test_files/failure/failure.properties");
        config = manager.getConfiguration("AccuracyCalculator").getChild("AccuracyCalculator");
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
            config.setPropertyValue("pointsForAccurateEvaluationType1", "-1");
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
            config.setPropertyValue("pointsForInaccurateEvaluationType1", "-1");
            instance.configure(config);
            fail("StatisticsCalculatorConfigurationException is required");
        } catch (StatisticsCalculatorConfigurationException e) {
            // ok
        }
    }

    /**
     * Test method calculateAccuracy.
     *
     * @throws Exception if any error
     */
    public void test_calculateAccuracy1() throws Exception {
        try {
        	instance.configure(config);
            instance.calculateAccuracy(null);
            fail("IAE is required");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Test method calculateAccuracy.
     *
     * @throws Exception if any error
     */
    public void test_calculateAccuracy2() throws Exception {
        try {
        	instance.configure(config);
        	Review [][] reviews = new Review[2][2];
        	reviews[0][0] = new Review();
        	reviews[0][1] = new Review();
        	reviews[1][0] = new Review();
            instance.calculateAccuracy(reviews);
            fail("IAE is required");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Test method calculateAccuracy.
     *
     * @throws Exception if any error
     */
    public void test_calculateAccuracy3() throws Exception {
        try {
        	instance.configure(config);
        	Review [][] reviews = new Review[2][2];
        	reviews[0][0] = new Review();
        	reviews[0][1] = new Review();
        	reviews[1][0] = new Review();
        	reviews[1][1] = null;
            instance.calculateAccuracy(reviews);
            fail("IAE is required");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Test method calculateAccuracy.
     *
     * @throws Exception if any error
     */
    public void test_calculateAccuracy4() throws Exception {
        try {
        	Review [][] reviews = new Review[2][2];
        	reviews[0][0] = new Review();
        	reviews[0][1] = new Review();
        	reviews[1][0] = new Review();
        	reviews[1][1] = new Review();
            instance.calculateAccuracy(reviews);
            fail("ISE is required");
        } catch (IllegalStateException e) {
            // ok
        }
    }
}
