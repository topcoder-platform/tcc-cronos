/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.dependency.stresstests;

import junit.framework.TestCase;

import com.topcoder.util.dependency.extractor.JavaDependenciesEntryExtractor;
import com.topcoder.util.log.LogManager;


/**
 * Stress test for class <code>JavaDependenciesEntryExtractor</code>.
 *
 * @author PE
 * @version 1.0
 */
public class JavaDependenciesEntryExtractorStressTest extends TestCase {
    /**
     * <p>
     * Represents the build file to be used in test.
     * </p>
     */
    private static final String TEST_BUILD_FILE = "test_files/stress_test_files/build.xml";

    /**
     * <p>
     * Represents the persistence to be tested.
     * </p>
     */
    private JavaDependenciesEntryExtractor extractor;

    /**
     * Set up the environment.
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        extractor = new JavaDependenciesEntryExtractor(LogManager.getLog());
    }

    /**
     * Clean up the environment.
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
    }

    /**
     * Stress Test for the functionality of class <code>JavaDependenciesEntryExtractor</code>.
     *
     * @throws Exception to JUnit
     */
    public void testJavaDependenciesEntryExtractor_Extract()
        throws Exception {
        StressTestHelper.start();

        for (int i = 0; i < StressTestHelper.TIMES; i++) {
            extractor.extract(TEST_BUILD_FILE);
        }

        StressTestHelper.printResult("testJavaDependenciesEntryExtractor_Extract");
    }
}
