/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reliability.failuretests.impl.detectors;

import org.junit.Before;
import org.junit.Test;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.reliability.ReliabilityCalculatorConfigurationException;
import com.topcoder.reliability.failuretests.FailureTestHelper;
import com.topcoder.reliability.impl.detectors.PhaseEndTimeBasedResolutionDateDetector;

/**
 * Failure tests for PhaseEndTimeBasedResolutionDateDetector class.
 * 
 * @author Yeung
 * @version 1.0
 */
public class PhaseEndTimeBasedResolutionDateDetectorFailureTests {
    /**
     * The PhaseEndTimeBasedResolutionDateDetector instance to test against.
     */
    private PhaseEndTimeBasedResolutionDateDetector instance;

    /**
     * The configuration object for testing.
     */
    private ConfigurationObject config;

    /**
     * Sets up the test environment.
     * 
     * @throws Exception
     *             to JUnit.
     */
    @Before
    public void setUp() throws Exception {
        config = FailureTestHelper.getConfig("PhaseEndTimeBasedResolutionDateDetector");

        instance = new PhaseEndTimeBasedResolutionDateDetector();
        instance.configure(config);
    }

    /**
     * Tests the method configure with null config. Expect IllegalArgumentException.
     * 
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConfigureWithNullConfig() throws Exception {
        instance.configure(null);
    }

    /**
     * Tests the method configure with non-string 'loggerName'. Expect ReliabilityCalculatorConfigurationException.
     * 
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReliabilityCalculatorConfigurationException.class)
    public void testConfigureWithNonStringLoggerName() throws Exception {
        config.setPropertyValue("loggerName", 1);

        instance.configure(config);
    }

    /**
     * Tests the method configure with empty 'loggerName'. Expect ReliabilityCalculatorConfigurationException.
     * 
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReliabilityCalculatorConfigurationException.class)
    public void testConfigureWithEmptyLoggerName() throws Exception {
        config.setPropertyValue("loggerName", " \t \n ");

        instance.configure(config);
    }

    /**
     * Tests the method detect with null data. Expect IllegalArgumentException.
     * 
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testDetectWithNullData() throws Exception {
        instance.detect(null);
    }
}
