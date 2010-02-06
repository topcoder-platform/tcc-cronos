/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.contest.coo.failuretests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.configuration.defaults.DefaultConfigurationObject;
import com.topcoder.management.contest.coo.dependencyextractor.DotNetComponentDependencyExtractor;

public class DotNetComponentDependencyExtractorFailureTests {

    /**
     * Instance of DotNetComponentDependencyExtractor for testing.
     */
    private DotNetComponentDependencyExtractor dotNetComponentDependencyExtractor;

    /**
     * Setting up environment.
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        dotNetComponentDependencyExtractor =
            new DotNetComponentDependencyExtractor(new DefaultConfigurationObject("configuration"));
    }

    /**
     * Method under test
     * DotNetComponentDependencyExtractor.DotNetComponentDependencyExtractor
     * (ConfigurationObject). Failure testing of exception in case configuration
     * is null.
     * @throws Exception wraps all exceptions
     */
    @Test
    public final void testDotNetComponentDependencyExtractorFailureNull() throws Exception {
        try {
            new DotNetComponentDependencyExtractor(null);
            Assert.fail("IllegalArgumentException is expected since configuration is null");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Method under test
     * DotNetComponentDependencyExtractor.extractDependencies(InputStream).
     * Failure testing of exception in case input is null.
     * @throws Exception wraps all exceptions
     */
    @Test
    public final void testExtractDependenciesFailureNull() throws Exception {
        try {
            dotNetComponentDependencyExtractor.extractDependencies(null);
            Assert.fail("IllegalArgumentException is expected since input is null");
        } catch (IllegalArgumentException e) {
            // good
        }
    }
}