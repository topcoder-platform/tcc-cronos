/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.dependency.accuracytests;

import java.io.InputStream;
import java.util.List;

import junit.framework.TestCase;

import com.topcoder.util.dependency.extractor.BuildFileProvisionException;
import com.topcoder.util.dependency.extractor.fileprovider.ZipBuildFileProvider;

/**
 * Accuracy tests for class ZipBuildFileProvider.
 *
 * @author telly12
 * @version 1.0
 */
public class ZipBuildFileProviderAccuracyTest extends TestCase {

    /**
     * <p>
     * Represents the zip file path.
     * </p>
     */
    private static String zipFilePath;

    /**
     * <p>
     * All the entries in the given zipFile.
     * </p>
     */
    private static List<String> entries;

    static {
        zipFilePath = "test_files/accuracytests/BuildScript.zip";
        try {
            entries = AccuracyTestHelper.getEntries(zipFilePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * <p>
     * Represents the provider.
     * </p>
     */
    private ZipBuildFileProvider provider;

    /**
     * <p>
     * The expected file.
     * </p>
     */
    private String expectedFile;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     * @throws Exception
     *             to test case.
     */
    protected void setUp() throws Exception {
        expectedFile = "test_files/accuracytests/BuildScript/java/logging_wrapper/2.0.0/build.version";
        super.setUp();
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     * @throws Exception
     *             to test case.
     */
    protected void tearDown() throws Exception {
        if (provider != null) {
            provider.close();
        }
        super.tearDown();
    }

    /**
     * <p>
     * Test method getFileStream(String relativeFilePath).
     * </p>
     * <p>
     * With correct given relativeFilePath, the result is should not be null.
     * </p>
     *
     * @throws Exception
     *             to test case
     */
    public void testGetFileStream_All() throws Exception {
        provider = new ZipBuildFileProvider(zipFilePath, "java/logging_wrapper/2.0.0/build.xml");

        for (String entryPath : entries) {
            String relativeFilePath = "../../../" + entryPath;

            InputStream is = null;
            try {
                is = provider.getFileStream(relativeFilePath);
                assertNotNull(relativeFilePath + " entry not found", is);
            } finally {
                if (is != null) {
                    is.close();
                }
            }
        }
    }

    /**
     * <p>
     * Test method getFileStream(String relativeFilePath).
     * </p>
     * <p>
     * With correct given relativeFilePath, the result is should not be null.
     * </p>
     *
     * @throws Exception
     *             to test case
     */
    public void testGetFileStream_1() throws Exception {
        provider = new ZipBuildFileProvider(zipFilePath, "java/logging_wrapper/2.0.0/");

        InputStream actualIS = provider.getFileStream("build.version");

        AccuracyTestHelper.validateIS(expectedFile, actualIS);
    }

    /**
     * <p>
     * Test method getFileStream(String relativeFilePath).
     * </p>
     * <p>
     * With correct given relativeFilePath, the result is should not be null.
     * </p>
     *
     * @throws Exception
     *             to test case
     */
    public void testGetFileStream_2() throws Exception {
        provider = new ZipBuildFileProvider(zipFilePath, "java/logging_wrapper/2.0.0/");

        InputStream actualIS = provider.getFileStream("./build.version");
        AccuracyTestHelper.validateIS(expectedFile, actualIS);
    }

    /**
     * <p>
     * Test method getFileStream(String relativeFilePath).
     * </p>
     * <p>
     * With correct given relativeFilePath, the result is should not be null.
     * </p>
     *
     * @throws Exception
     *             to test case
     */
    public void testGetFileStream_3() throws Exception {
        provider = new ZipBuildFileProvider(zipFilePath, "java/logging_wrapper/2.0.0/");

        InputStream actualIS = provider.getFileStream("../2.0.0/build.version");
        AccuracyTestHelper.validateIS(expectedFile, actualIS);
    }

    /**
     * <p>
     * Test method getFileStream(String relativeFilePath).
     * </p>
     * <p>
     * With correct given relativeFilePath, the result is should not be null.
     * </p>
     *
     * @throws Exception
     *             to test case
     */
    public void testGetFileStream_4() throws Exception {
        provider = new ZipBuildFileProvider(zipFilePath, "java/logging_wrapper/2.0.0/build.version");

        InputStream actualIS = provider.getFileStream("");
        AccuracyTestHelper.validateIS(expectedFile, actualIS);
    }

    /**
     * <p>
     * Test method close().
     * </p>
     * <p>
     * The zipFile should be closed.
     * </p>
     *
     * @throws Exception
     *             to test case
     */
    public void testClose() throws Exception {
        provider = new ZipBuildFileProvider(zipFilePath, "java/");
        provider.close();

        try {
            // already closed
            provider.getFileStream("logging_wrapper/2.0.0/build.xml");
            fail("IllegalStateException expected.");
        } catch (IllegalStateException e) {
            // expected
        } catch (BuildFileProvisionException e) {
            fail("BuildFileProvisionException not expected:" + e.getMessage());
        }
    }
}
