/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.dependency.failuretests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.util.dependency.DependenciesEntryExtractionException;
import com.topcoder.util.dependency.extractor.JavaDependenciesEntryExtractor;

/**
 * Failure test for JavaDependenciesEntryExtractor class.
 *
 * @author King_Bette
 * @version 1.0
 */
public class JavaDependenciesEntryExtractorFailureTest extends TestCase {
    /**
     * This instance is used in the test.
     */
    private JavaDependenciesEntryExtractor extractor = new JavaDependenciesEntryExtractor(null);
    /**
     * Aggregates all tests in this class.
     *
     * @return Test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(JavaDependenciesEntryExtractorFailureTest.class);
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
    public void testExtract_failure_empty_filePath() throws Exception {
        try {
            extractor.extract("   ");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>extract(String filePath)</code> method.
     *
     * <p>
     * filePath is not support
     * </p>
     *
     * <p>
     * Expect DependenciesEntryExtractionException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testExtract_failure_unsupport_file() throws Exception {
        try {
            extractor.extract("test_files");
            fail("Expect DependenciesEntryExtractionException.");
        } catch (DependenciesEntryExtractionException e) {
            // expect
        }
    }
    /**
     * Failure test of <code>extract(String filePath)</code> method.
     *
     * <p>
     * build file is invalid, component name is missed.
     * </p>
     *
     * <p>
     * Expect DependenciesEntryExtractionException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testExtract_failure_invalid_file1() throws Exception {
        try {
            extractor.extract("test_files/failuretests/invalid1/build.xml");
            fail("Expect DependenciesEntryExtractionException.");
        } catch (DependenciesEntryExtractionException e) {
            // expect
        }
    }
    /**
     * Failure test of <code>extract(String filePath)</code> method.
     *
     * <p>
     * build file is invalid.
     * </p>
     *
     * <p>
     * Expect DependenciesEntryExtractionException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testExtract_failure_invalid_file2() throws Exception {
        try {
            extractor.extract("test_files/failuretests/invalid2/build.xml");
            fail("Expect DependenciesEntryExtractionException.");
        } catch (DependenciesEntryExtractionException e) {
            // expect
        }
    }
    /**
     * Failure test of <code>extract(String filePath)</code> method.
     *
     * <p>
     * build file is invalid.
     * </p>
     *
     * <p>
     * Expect DependenciesEntryExtractionException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testExtract_failure_invalid_file3() throws Exception {
        try {
            extractor.extract("test_files/failuretests/invalid3/build.xml");
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
            extractor.isSupportedFileType("  ");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }
}
