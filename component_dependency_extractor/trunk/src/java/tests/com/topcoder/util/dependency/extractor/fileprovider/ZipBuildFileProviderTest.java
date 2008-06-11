/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.dependency.extractor.fileprovider;

import java.io.File;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.util.dependency.extractor.BuildFileProvisionException;

/**
 * <p>
 * Unit tests for <code>ZipBuildFileProvider</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ZipBuildFileProviderTest extends TestCase {
    /**
     * <p>
     * zip file name to be used in test.
     * </p>
     */
    private static final String TEST_ZIP_FILE_NAME = "test_files" + File.separator + "test_ZipBuildFileProvider.zip";

    /**
     * <p>
     * jar file name to be used in test.
     * </p>
     */
    private static final String TEST_JAR_FILE_NAME = "test_files" + File.separator + "test_ZipBuildFileProvider.jar";

    /**
     * <p>
     * <code>ZipBuildFileProvider</code> instance to be tested.
     * </p>
     */
    private ZipBuildFileProvider provider;

    /**
     * <p>
     * Sets up the fixture. This method is called before a test is executed.
     * </p>
     *
     * @throws Exception if any error occurs.
     */
    protected void setUp() throws Exception {
        super.setUp();
        provider = new ZipBuildFileProvider(TEST_ZIP_FILE_NAME, "folder1/folder1_file1.txt");
    }

    /**
     * <p>
     * Tears down the fixture. This method is called after a test is executed.
     * </p>
     *
     * @throws Exception if any error occurs.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        provider = null;
    }

    /**
     * <p>
     * Gets the test suite for <code>ZipBuildFileProvider</code> class.
     * </p>
     *
     * @return a <code>TestSuite</code> providing the tests for <code>ZipBuildFileProvider</code> class.
     */
    public static Test suite() {
        return new TestSuite(ZipBuildFileProviderTest.class);
    }

    /**
     * <p>
     * Accuracy test for constructor <code>ZipBuildFileProvider(String,String)</code>.
     * </p>
     * <p>
     * Uses the constructor to create a non-null instance. No exception should be thrown.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testCtor1_Accuracy() throws Exception {
        assertNotNull("It should return non-null instance.", provider);
    }

    /**
     * <p>
     * Failure test for constructor <code>ZipBuildFileProvider(String,String)</code>.
     * </p>
     * <p>
     * Passes in null zip entry value. A <code>IllegalArgumentException</code> should be thrown.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testCtor1_Failure1() throws Exception {
        try {
            new ZipBuildFileProvider(TEST_ZIP_FILE_NAME, null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test for constructor <code>ZipBuildFileProvider(String,String)</code>.
     * </p>
     * <p>
     * Passes in null zip entry value. A <code>IllegalArgumentException</code> should be thrown.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testCtor1_Failure2() throws Exception {
        try {
            new ZipBuildFileProvider(" ", "folder1/folder1_file1.txt");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test for constructor <code>ZipBuildFileProvider(String,String)</code>.
     * </p>
     * <p>
     * Passes in not exist zip file name value. A <code>BuildFileProvisionException</code> should be thrown.
     * </p>
     */
    public void testCtor1_Failure3() {
        try {
            new ZipBuildFileProvider("not_exist.zip", "folder1/folder1_file1.txt");
            fail("BuildFileProvisionException should be thrown.");
        } catch (BuildFileProvisionException e) {
            // pass
        }
    }

    /**
     * <p>
     * Accuracy test for <code>getFileStream(String)</code>.
     * </p>
     * <p>
     * Passes in "" value and use current entry path. No exception should be thrown.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testGetFileStream_Accuracy1() throws Exception {
        assertNotNull("it should get non-null stream.", provider.getFileStream(""));
    }

    /**
     * <p>
     * Accuracy test for <code>getFileStream(String)</code>.
     * </p>
     * <p>
     * Passes in valid relative entry path. No exception should be thrown.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testGetFileStream_Accuracy2() throws Exception {
        assertNotNull("it should get non-null stream.", provider.getFileStream("folder1_1/folder1_1_file1.txt"));
    }

    /**
     * <p>
     * Accuracy test for <code>getFileStream(String)</code>.
     * </p>
     * <p>
     * Passes in valid relative entry path and it is in parent folder. No exception should be thrown.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testGetFileStream_Accuracy3() throws Exception {
        assertNotNull("it should get non-null stream.", provider.getFileStream("../file1.txt"));
    }

    /**
     * <p>
     * Accuracy test for <code>getFileStream(String)</code>.
     * </p>
     * <p>
     * Passes in valid relative path and it has "./" but they should be interpreted correctly. No exception should be
     * thrown.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testGetFileStream_Accuracy4() throws Exception {
        assertNotNull("it should get non-null stream.", provider.getFileStream("././../././file1.txt"));
    }

    /**
     * <p>
     * Accuracy test for <code>getFileStream(String)</code>.
     * </p>
     * <p>
     * Supports if the file path starts with "/".
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testGetFileStream_Accuracy5() throws Exception {
        assertNotNull("it should get non-null stream.", provider.getFileStream("/file1.txt"));
    }

    /**
     * <p>
     * Failure test for <code>getFileStream(String)</code>.
     * </p>
     * <p>
     * Passes in null value. A <code>IllegalArgumentException</code> should be thrown.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testGetFileStream_Failure1() throws Exception {
        try {
            provider.getFileStream(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test for <code>getFileStream(String)</code>.
     * </p>
     * <p>
     * Passes in invalid value. A <code>BuildFileProvisionException</code> should be thrown.
     * </p>
     */
    public void testGetFileStream_Failure2() {
        try {
            provider.getFileStream("../not_exist/notexist.txt");
            fail("BuildFileProvisionException should be thrown.");
        } catch (BuildFileProvisionException e) {
            // pass
        }
    }

    /**
     * <p>
     * Accuracy test for <code>close()</code>.
     * </p>
     * <p>
     * Passes in valid value. No exception should be thrown.
     * </p>
     */
    public void testClose_Accuracy() {
        provider.close();
        // ok
    }
}
