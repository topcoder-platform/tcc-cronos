/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.dependency.failuretests;

import com.topcoder.util.dependency.DependenciesEntryExtractionException;
import com.topcoder.util.dependency.extractor.DotNetDependenciesEntryExtractor;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Failure test for DotNetDependenciesEntryExtractor class.
 *
 * @author King_Bette
 * @version 1.0
 */
public class DotNetDependenciesEntryExtractorFailureTest extends TestCase {
    /**
     * This instance is used in the test.
     */
    private DotNetDependenciesEntryExtractor extractor = new DotNetDependenciesEntryExtractor(null);
    /**
     * Aggregates all tests in this class.
     *
     * @return Test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(DotNetDependenciesEntryExtractorFailureTest.class);
    }

    /**
     * Sets up the environment for the TestCase.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    protected void setUp() throws Exception {
        // do nothing
    }

    /**
     * Tears down the environment for the TestCase.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    protected void tearDown() throws Exception {
        // do nothing
    }

    /**
     * Failure test of <code>extract(String filePath)</code> method.
     *
     * <p>
     * filePath is null.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testExtract_failure_null_filePath() throws Exception {
        try {
            extractor.extract(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>extract(String filePath)</code> method.
     *
     * <p>
     * filePath is null.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testExtract_failure_empty_filePath() throws Exception {
        try {
            extractor.extract("  ");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>extract(String filePath)</code> method.
     *
     * <p>
     * filePath is not supported.
     * </p>
     *
     * <p>
     * Expect DependenciesEntryExtractionException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testExtract_failure() throws Exception {
        try {
            extractor.extract("test_files/failuretests/build.xml");
            fail("Expect DependenciesEntryExtractionException.");
        } catch (DependenciesEntryExtractionException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>isSupportedFileType(String filePath)</code> method.
     *
     * <p>
     * filePath is null.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testIsSupportedFileType_failure_null_filePath() throws Exception {
        try {
            extractor.isSupportedFileType(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>isSupportedFileType(String filePath)</code> method.
     *
     * <p>
     * filePath is empty.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testIsSupportedFileType_failure_empty_filePath() throws Exception {
        try {
            extractor.isSupportedFileType("   ");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }
}
