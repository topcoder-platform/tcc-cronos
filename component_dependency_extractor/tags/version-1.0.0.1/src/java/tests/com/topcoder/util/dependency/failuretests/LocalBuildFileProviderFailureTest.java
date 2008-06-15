/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.dependency.failuretests;

import com.topcoder.util.dependency.extractor.BuildFileProvisionException;
import com.topcoder.util.dependency.extractor.fileprovider.LocalBuildFileProvider;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Failure test for LocalBuildFileProvider class.
 *
 * @author King_Bette
 * @version 1.0
 */
public class LocalBuildFileProviderFailureTest extends TestCase {
    /**
     * This instance is used in the test.
     */
    private LocalBuildFileProvider buildFileProvider = new LocalBuildFileProvider("test_files/failuretests/test_build.xml");
    /**
     * Aggregates all tests in this class.
     *
     * @return Test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(LocalBuildFileProviderFailureTest.class);
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
     * Failure test of <code>LocalBuildFileProvider(String currentFilePath)</code> constructor.
     *
     * <p>
     * currentFilePath is null.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testLocalBuildFileProvider_failure_null_currentFilePath() throws Exception {
        try {
            new LocalBuildFileProvider(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }
    /**
     * Failure test of <code>LocalBuildFileProvider(String currentFilePath)</code> constructor.
     *
     * <p>
     * currentFilePath is empty.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testLocalBuildFileProvider_failure_empty_currentFilePath() throws Exception {
        try {
            new LocalBuildFileProvider("  ");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>getFileStream(String relativeFilePath)</code> method.
     *
     * <p>
     * relativeFilePath is null.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testGetFileStream_failure_null_relativeFilePath() throws Exception {
        try {
            new LocalBuildFileProvider(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>getFileStream(String relativeFilePath)</code> method.
     *
     * <p>
     * relativeFilePath is empty.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testGetFileStream_failure_empty_relativeFilePath() throws Exception {
        try {
            new LocalBuildFileProvider("  ");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>getFileStream(String relativeFilePath)</code> method.
     *
     * <p>
     * relativeFilePath is invalid.
     * </p>
     *
     * <p>
     * Expect BuildFileProvisionException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testGetFileStream_failure_invalid_fileName() throws Exception {
        try {
            buildFileProvider.getFileStream("test.xml");
            fail("Expect BuildFileProvisionException.");
        } catch (BuildFileProvisionException e) {
            // expect
        }
    }
}
