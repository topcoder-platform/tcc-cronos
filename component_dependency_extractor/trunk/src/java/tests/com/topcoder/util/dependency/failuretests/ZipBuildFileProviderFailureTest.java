/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.dependency.failuretests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.util.dependency.extractor.BuildFileProvisionException;
import com.topcoder.util.dependency.extractor.fileprovider.ZipBuildFileProvider;

/**
 * Failure test for ZipBuildFileProvider class.
 *
 * @author King_Bette
 * @version 1.0
 */
public class ZipBuildFileProviderFailureTest extends TestCase {
    /**
     * Aggregates all tests in this class.
     *
     * @return Test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(ZipBuildFileProviderFailureTest.class);
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
     * Failure test of <code>ZipBuildFileProvider(String zipFilePath, String currentZipEntryPath)</code> constructor.
     *
     * <p>
     * zipFilePath is null.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testZipBuildFileProvider_failure_null_zipFilePath() throws Exception {
        try {
            new ZipBuildFileProvider(null, "build.xml");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>ZipBuildFileProvider(String zipFilePath, String currentZipEntryPath)</code> constructor.
     *
     * <p>
     * zipFilePath is empty.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testZipBuildFileProvider_failure_empty_zipFilePath() throws Exception {
        try {
            new ZipBuildFileProvider("  ", "build.xml");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>ZipBuildFileProvider(String zipFilePath, String currentZipEntryPath)</code> constructor.
     *
     * <p>
     * currentZipEntryPath is null.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testZipBuildFileProvider_failure_null_currentZipEntryPath() throws Exception {
        try {
            new ZipBuildFileProvider("test_files/failuretests/testZip.jar", null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }
    /**
     * Failure test of <code>ZipBuildFileProvider(String zipFilePath, String currentZipEntryPath)</code> constructor.
     *
     * <p>
     * currentZipEntryPath is empty.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testZipBuildFileProvider_failure_empty_currentZipEntryPath() throws Exception {
        try {
            new ZipBuildFileProvider("test_files/failuretests/testZip.jar", "  ");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>ZipBuildFileProvider(String zipFilePath, String currentZipEntryPath)</code> constructor.
     *
     * <p>
     * file not exist.
     * </p>
     *
     * <p>
     * Expect BuildFileProvisionException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testZipBuildFileProvider_failure_file_not_exist() throws Exception {
        try {
            new ZipBuildFileProvider("testxx.zip", "build.xml");
            fail("Expect BuildFileProvisionException.");
        } catch (BuildFileProvisionException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>ZipBuildFileProvider(String zipFilePath, String currentZipEntryPath)</code> constructor.
     *
     * <p>
     * file is invalid.
     * </p>
     *
     * <p>
     * Expect BuildFileProvisionException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testZipBuildFileProvider_failure_invalid_file1() throws Exception {
        try {
            new ZipBuildFileProvider("test_files", "build.xml");
            fail("Expect BuildFileProvisionException.");
        } catch (BuildFileProvisionException e) {
            // expect
        }
    }
    /**
     * Failure test of <code>ZipBuildFileProvider(String zipFilePath, String currentZipEntryPath)</code> constructor.
     *
     * <p>
     * file is invalid.
     * </p>
     *
     * <p>
     * Expect BuildFileProvisionException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testZipBuildFileProvider_failure_invalid_file2() throws Exception {
        try {
            new ZipBuildFileProvider("test_files/failuretests/invalid1.xml", "build.xml");
            fail("Expect BuildFileProvisionException.");
        } catch (BuildFileProvisionException e) {
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
        ZipBuildFileProvider buildFileProvider = new ZipBuildFileProvider("test_files/failuretests/testZip.jar", "build.xml");
        try {
            buildFileProvider.getFileStream(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>getFileStream(String relativeFilePath)</code> method.
     *
     * <p>
     * zipEntry is not found.
     * </p>
     *
     * <p>
     * Expect BuildFileProvisionException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testGetFileStream_failure() throws Exception {
        ZipBuildFileProvider buildFileProvider = new ZipBuildFileProvider("test_files/failuretests/testZip.jar", "build.xml");
        try {
            buildFileProvider.getFileStream("test.xml");
            fail("Expect BuildFileProvisionException.");
        } catch (BuildFileProvisionException e) {
            // expect
        }
    }
    /**
     * Failure test of <code>getFileStream(String relativeFilePath)</code> method.
     *
     * <p>
     * currentPath is invalid.
     * </p>
     *
     * <p>
     * Expect BuildFileProvisionException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testGetFileStream_failure_invalid_currentPath1() throws Exception {
        ZipBuildFileProvider buildFileProvider = new ZipBuildFileProvider("test_files/failuretests/testZip.jar", "./build.xml");
        try {
            buildFileProvider.getFileStream("build.xml");
            fail("Expect BuildFileProvisionException.");
        } catch (BuildFileProvisionException e) {
            // expect
        }
    }
    /**
     * Failure test of <code>getFileStream(String relativeFilePath)</code> method.
     *
     * <p>
     * currentPath is invalid.
     * </p>
     *
     * <p>
     * Expect BuildFileProvisionException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testGetFileStream_failure_invalid_currentPath2() throws Exception {
        ZipBuildFileProvider buildFileProvider = new ZipBuildFileProvider("test_files/failuretests/testZip.jar", "./build.xml");
        try {
            buildFileProvider.getFileStream("build.xml");
            fail("Expect BuildFileProvisionException.");
        } catch (BuildFileProvisionException e) {
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
    public void testGetFileStream_failure_invalid_path() throws Exception {
        ZipBuildFileProvider buildFileProvider = new ZipBuildFileProvider("test_files/failuretests/testZip.jar", "build.xml");
        try {
            buildFileProvider.getFileStream("../build.xml");
            fail("Expect BuildFileProvisionException.");
        } catch (BuildFileProvisionException e) {
            // expect
        }
    }
}
