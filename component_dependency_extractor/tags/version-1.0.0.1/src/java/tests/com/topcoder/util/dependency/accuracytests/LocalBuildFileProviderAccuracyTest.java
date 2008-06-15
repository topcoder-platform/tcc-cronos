/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.dependency.accuracytests;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import junit.framework.TestCase;

import com.topcoder.util.dependency.extractor.fileprovider.LocalBuildFileProvider;

/**
 * <p>
 * Accuracy tests for class LocalBuildFileProvider.
 * </p>
 *
 * @author telly12
 * @version 1.0
 */
public class LocalBuildFileProviderAccuracyTest extends TestCase {

    /**
     * <p>
     * The current file path.
     * </p>
     */
    private static String currentFilePath;

    /**
     * <p>
     * Represents the provider.
     * </p>
     */
    private LocalBuildFileProvider provider;

    /**
     * <p>
     * The expected file.
     * </p>
     */
    private String expectedFile;

    /**
     * <p>
     * All the files.
     * </p>
     */
    private static List<String> entries;

    static {
        currentFilePath = "test_files/accuracytests/BuildScript/java/logging_wrapper/2.0.0/build.xml";
        String zipFilePath = "test_files/accuracytests/BuildScript.zip";
        try {
            entries = AccuracyTestHelper.getEntries(zipFilePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
        provider = new LocalBuildFileProvider(currentFilePath);

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
        provider = new LocalBuildFileProvider("test_files/accuracytests/BuildScript/java/logging_wrapper/2.0.0/");

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
        provider = new LocalBuildFileProvider("test_files/accuracytests/BuildScript/java/logging_wrapper/2.0.0/");

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
        provider = new LocalBuildFileProvider("test_files/accuracytests/BuildScript/java/logging_wrapper/2.0.0/");

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
        provider = new LocalBuildFileProvider(
                "test_files/accuracytests/BuildScript/java/logging_wrapper/2.0.0/build.version");

        InputStream actualIS = provider.getFileStream("");

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
    public void testGetFileStream_5() throws Exception {
        provider = new LocalBuildFileProvider(
                "test_files/accuracytests/BuildScript/java/logging_wrapper/2.0.0/build.xml");

        InputStream actualIS = provider.getFileStream(new File(
                "test_files/accuracytests/BuildScript/java/base_exception/2.0.0/build.version").getCanonicalPath());

        AccuracyTestHelper.validateIS("test_files/accuracytests/BuildScript/java/base_exception/2.0.0/build.version",
                actualIS);
    }

}
