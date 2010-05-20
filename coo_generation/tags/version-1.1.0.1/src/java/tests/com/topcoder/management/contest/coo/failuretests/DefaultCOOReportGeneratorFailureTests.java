/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.contest.coo.failuretests;

import org.junit.Assert;
import org.junit.Test;

import com.topcoder.configuration.defaults.DefaultConfigurationObject;
import com.topcoder.management.contest.coo.impl.ConfigurationException;
import com.topcoder.management.contest.coo.impl.DefaultCOOReportGenerator;

/**
 * <p>
 * Set of failure tests for DefaultCOOReportGenerator,
 * </p>
 * @author vilain
 * @version 1.0
 */
public class DefaultCOOReportGeneratorFailureTests {

    /**
     * Method under test
     * DefaultCOOReportGenerator.DefaultCOOReportGenerator(ConfigurationObject).
     * Failure testing of exception in case configuration is null.
     * @throws Exception wraps all exceptions
     */
    @Test
    public final void testDefaultCOOReportGeneratorFailureNull() throws Exception {
        try {
            new DefaultCOOReportGenerator(null);
            Assert.fail("IllegalArgumentException is expected since configuration is null");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Method under test
     * DefaultCOOReportGenerator.DefaultCOOReportGenerator(ConfigurationObject).
     * Failure testing of exception in case configuration is wrong.
     * @throws Exception wraps all exceptions
     */
    @Test
    public final void testDefaultCOOReportGeneratorFailureConfig() throws Exception {
        try {
            new DefaultCOOReportGenerator(new DefaultConfigurationObject("default"));
            Assert.fail("ConfigurationException is expected since configuration is wrong");
        } catch (ConfigurationException e) {
            // good
        }
    }
}