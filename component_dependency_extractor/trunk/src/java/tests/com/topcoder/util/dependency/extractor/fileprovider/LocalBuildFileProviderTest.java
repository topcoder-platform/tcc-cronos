/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.dependency.extractor.fileprovider;

import java.io.File;
import java.io.InputStream;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.util.dependency.Utils;
import com.topcoder.util.dependency.extractor.BuildFileProvisionException;

/**
 * <p>
 * Unit tests for <code>LocalBuildFileProvider</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class LocalBuildFileProviderTest extends TestCase {
    /**
     * <p>
     * A file name to be used in tests.
     * </p>
     */
    private static final String TEST_FILE_NAME = "test_files" + File.separator + "UtilsUnitTest.xml";

    /**
     * <p>
     * <code>LocalBuildFileProvider</code> instance to be tested.
     * </p>
     */
    private LocalBuildFileProvider provider;

    /**
     * <p>
     * Sets up the fixture. This method is called before a test is executed.
     * </p>
     *
     * @throws Exception if any error occurs.
     */
    protected void setUp() throws Exception {
        super.setUp();
        provider = new LocalBuildFileProvider(TEST_FILE_NAME);
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
     * Gets the test suite for <code>LocalBuildFileProvider</code> class.
     * </p>
     *
     * @return a <code>TestSuite</code> providing the tests for <code>LocalBuildFileProvider</code> class.
     */
    public static Test suite() {
        return new TestSuite(LocalBuildFileProviderTest.class);
    }

    /**
     * <p>
     * Accuracy test for constructor <code>LocalBuildFileProvider(String)</code>.
     * </p>
     * <p>
     * Uses the constructor to create a non-null instance. No exception should be thrown.
     * </p>
     */
    public void testCtor1_Accuracy() {
        assertNotNull("It should return non-null instance.", provider);
    }

    /**
     * <p>
     * Failure test for constructor <code>LocalBuildFileProvider(String)</code>.
     * </p>
     * <p>
     * Passes in null value. A <code>IllegalArgumentException</code> should be thrown.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testCtor1_Failure1() throws Exception {
        try {
            new LocalBuildFileProvider(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test for constructor <code>LocalBuildFileProvider(String)</code>.
     * </p>
     * <p>
     * Passes in empty value. A <code>IllegalArgumentException</code> should be thrown.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testCtor1_Failure2() throws Exception {
        try {
            new LocalBuildFileProvider(" ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Accuracy test for <code>getFileStream(String)</code>.
     * </p>
     * <p>
     * Passes in a valid relative path. No exception should be thrown.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testGetFileStream_Accuracy1() throws Exception {
        InputStream is = provider.getFileStream("./config.xml");
        assertNotNull("It should get not null stream.", is);
        Utils.closeInputStreamSafely(is);
    }

    /**
     * <p>
     * Accuracy test for <code>getFileStream(String)</code>.
     * </p>
     * <p>
     * Passes in a valid relative path. No exception should be thrown.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testGetFileStream_Accuracy2() throws Exception {
        InputStream is = provider.getFileStream("scripts/java/file_upload-2.2.0.jar");
        assertNotNull("It should get not null stream.", is);
        Utils.closeInputStreamSafely(is);
    }

    /**
     * <p>
     * Accuracy test for <code>getFileStream(String)</code>.
     * </p>
     * <p>
     * Passes in a valid relative path. The local file system will handle it correctly. No exception should be thrown.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testGetFileStream_Accuracy3() throws Exception {
        InputStream is = provider.getFileStream("./com/../UtilsUnitTest.xml");
        assertNotNull("It should get not null stream.", is);
        Utils.closeInputStreamSafely(is);
    }

    /**
     * <p>
     * Failure test for <code>getFileStream(String)</code>.
     * </p>
     * <p>
     * Passes in null path. A <code>IllegalArgumentException</code> should be thrown.
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
     * Passes in not exist file path. A <code>BuildFileProvisionException</code> should be thrown.
     * </p>
     */
    public void testGetFileStream_Failure2() {
        try {
            provider.getFileStream("notexist/notexist.xml");
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
        // pass
    }
}
