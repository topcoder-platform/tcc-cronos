/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.dependency.failuretests;

import java.util.ArrayList;
import java.util.List;

import com.topcoder.util.dependency.BaseDependenciesEntryExtractor;
import com.topcoder.util.dependency.DependenciesEntryExtractionException;
import com.topcoder.util.dependency.extractor.JavaDependenciesEntryExtractor;
import com.topcoder.util.dependency.extractor.MultipleFormatDependenciesEntryExtractor;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Failure test for MultipleFormatDependenciesEntryExtractor class.
 * @author King_Bette
 * @version 1.0
 */
public class MultipleFormatDependenciesEntryExtractorFailureTest extends TestCase {
    /**
     * This instance is used in the test.
     */
    private MultipleFormatDependenciesEntryExtractor extractor = new MultipleFormatDependenciesEntryExtractor(null);

    /**
     * Aggregates all tests in this class.
     * @return Test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(MultipleFormatDependenciesEntryExtractorFailureTest.class);
    }

    /**
     * Sets up the environment for the TestCase.
     * @throws Exception throw exception to JUnit.
     */
    protected void setUp() throws Exception {
        // do nothing
    }

    /**
     * Tears down the environment for the TestCase.
     * @throws Exception throw exception to JUnit.
     */
    protected void tearDown() throws Exception {
        // do nothing
    }

    /**
     * Failure test of
     * <code>MultipleFormatDependenciesEntryExtractor(Log logger, List<BaseDependenciesEntryExtractor> extractors)</code>
     * constructor.
     * <p>
     * extractors is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testMultipleFormatDependenciesEntryExtractor_failure_null_extractors() throws Exception {
        try {
            new MultipleFormatDependenciesEntryExtractor(null, null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }
    /**
     * Failure test of
     * <code>MultipleFormatDependenciesEntryExtractor(Log logger, List<BaseDependenciesEntryExtractor> extractors)</code>
     * constructor.
     * <p>
     * extractors is empty.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testMultipleFormatDependenciesEntryExtractor_failure_empty_extractors() throws Exception {
        try {
            new MultipleFormatDependenciesEntryExtractor(null, new ArrayList<BaseDependenciesEntryExtractor>());
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }
    /**
     * Failure test of
     * <code>MultipleFormatDependenciesEntryExtractor(Log logger, List<BaseDependenciesEntryExtractor> extractors)</code>
     * constructor.
     * <p>
     * extractors contains null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testMultipleFormatDependenciesEntryExtractor_failure_null_extractor() throws Exception {
        List<BaseDependenciesEntryExtractor> extractors = new ArrayList<BaseDependenciesEntryExtractor>();
        extractors.add(new JavaDependenciesEntryExtractor(null));
        extractors.add(null);
        try {
            new MultipleFormatDependenciesEntryExtractor(null, extractors);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>extract(String filePath)</code> method.
     * <p>
     * filePath is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit.
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
     * <p>
     * filePath is empty.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit.
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
     * <p>
     * filePath is not support
     * </p>
     * <p>
     * Expect DependenciesEntryExtractionException.
     * </p>
     * @throws Exception to JUnit.
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
     * Failure test of <code>isSupportedFileType(String filePath)</code> method.
     * <p>
     * filePath is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit.
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
     * <p>
     * filePath is empty.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit.
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
