/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.contest.coo.failuretests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.configuration.defaults.DefaultConfigurationObject;
import com.topcoder.management.contest.coo.dependencyextractor.JavaComponentDependencyExtractor;

/**
 * <p>
 * Set of failure tests for JavaComponentDependencyExtractor,
 * </p>
 * @author vilain
 * @version 1.0
 */
public class JavaComponentDependencyExtractorFailureTests {

    /**
     * Instance of DBComponentManager for testing.
     */
    private JavaComponentDependencyExtractor javaComponentDependencyExtractor;

    /**
     * Setting up environment.
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        javaComponentDependencyExtractor =
            new JavaComponentDependencyExtractor(new DefaultConfigurationObject("configuration"));
    }

    /**
     * Method under test
     * JavaComponentDependencyExtractor.JavaComponentDependencyExtractor
     * (ConfigurationObject). Failure testing of exception in case configuration
     * is null.
     * @throws Exception wraps all exceptions
     */
    @Test
    public final void testJavaComponentDependencyExtractorFailureNull() throws Exception {
        try {
            new JavaComponentDependencyExtractor(null);
            Assert.fail("IllegalArgumentException is expected since configuration is null");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Method under test
     * JavaComponentDependencyExtractor.extractDependencies(InputStream).
     * Failure testing of exception in case input is null.
     * @throws Exception wraps all exceptions
     */
    @Test
    public final void testExtractDependenciesFailureNull() throws Exception {
        try {
            javaComponentDependencyExtractor.extractDependencies(null);
            Assert.fail("IllegalArgumentException is expected since input is null");
        } catch (IllegalArgumentException e) {
            // good
        }
    }
}