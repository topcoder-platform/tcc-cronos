/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.dependency.stresstests;

import com.topcoder.util.dependency.extractor.DotNetDependenciesEntryExtractor;
import com.topcoder.util.log.LogManager;

import junit.framework.TestCase;


/**
 * Stress test for class <code>DotNetDependenciesEntryExtractor</code>.
 *
 * @author PE
 * @version 1.0
 */
public class DotNetDependenciesEntryExtractorStressTest extends TestCase {
    /**
     * <p>
     * Represents the build file to be used in test.
     * </p>
     */
    private static final String TEST_BUILD_FILE = "test_files/stress_test_files/default.build";

    /**
     * <p>
     * Represents the persistence to be tested.
     * </p>
     */
    private DotNetDependenciesEntryExtractor extractor;

    /**
     * Set up the environment.
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        extractor = new DotNetDependenciesEntryExtractor(LogManager.getLog());
    }

    /**
     * Clean up the environment.
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
    }

    /**
     * Stress Test for the functionality of class <code>DotNetDependenciesEntryExtractor</code>.
     *
     * @throws Exception to JUnit
     */
    public void testDotNetDependenciesEntryExtractor_Extract()
        throws Exception {
        StressTestHelper.start();

        for (int i = 0; i < StressTestHelper.TIMES; i++) {
            extractor.extract(TEST_BUILD_FILE);
        }

        StressTestHelper.printResult("testDotNetDependenciesEntryExtractor_Extract");
    }
}
