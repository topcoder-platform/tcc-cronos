/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.dependency.extractor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.util.dependency.BaseDependenciesEntryExtractor;
import com.topcoder.util.dependency.ComponentLanguage;
import com.topcoder.util.dependency.DependenciesEntry;
import com.topcoder.util.dependency.DependenciesEntryExtractionException;
import com.topcoder.util.log.LogManager;

/**
 * <p>
 * Unit tests for <code>MultipleFormatDependenciesEntryExtractor</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MultipleFormatDependenciesEntryExtractorTest extends TestCase {
    /**
     * <p>
     * build.xml to be used in test.
     * </p>
     */
    private static final String TEST_JAVA_BUILD_XML = "test_files" + File.separator + "scripts" + File.separator
        + "build.xml";

    /**
     * <p>
     * build.xml to be used in test.
     * </p>
     */
    private static final String TEST_DOT_NET_BUILD_XML = "test_files" + File.separator + "scripts" + File.separator
        + "default.build";

    /**
     * <p>
     * <code>MultipleFormatDependenciesEntryExtractor</code> instance to be tested.
     * </p>
     */
    private MultipleFormatDependenciesEntryExtractor extractor;

    /**
     * <p>
     * Sets up the fixture. This method is called before a test is executed.
     * </p>
     *
     * @throws Exception if any error occurs.
     */
    protected void setUp() throws Exception {
        super.setUp();
        extractor = new MultipleFormatDependenciesEntryExtractor(LogManager.getLog());
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
        extractor = null;
    }

    /**
     * <p>
     * Gets the test suite for <code>MultipleFormatDependenciesEntryExtractor</code> class.
     * </p>
     *
     * @return a <code>TestSuite</code> providing the tests for <code>MultipleFormatDependenciesEntryExtractor</code>
     *         class.
     */
    public static Test suite() {
        return new TestSuite(MultipleFormatDependenciesEntryExtractorTest.class);
    }

    /**
     * <p>
     * Accuracy test for constructor <code>MultipleFormatDependenciesEntryExtractor(Log)</code>.
     * </p>
     * <p>
     * Uses the constructor to create a non-null instance. No exception should be thrown.
     * </p>
     */
    public void testCtor1_Accuracy() {
        assertNotNull("It should return non-null instance.", extractor);
    }

    /**
     * <p>
     * Accuracy test for constructor
     * <code>MultipleFormatDependenciesEntryExtractor(Log,List&lt;BaseDependenciesEntryExtractor&gt;)</code>.
     * </p>
     * <p>
     * Uses the constructor to create a non-null instance. No exception should be thrown.
     * </p>
     */
    public void testCtor2_Accuracy() {
        List<BaseDependenciesEntryExtractor> extractors = new ArrayList<BaseDependenciesEntryExtractor>();
        extractors.add(new JavaDependenciesEntryExtractor(null));
        assertNotNull("It should return non-null instance.", new MultipleFormatDependenciesEntryExtractor(null,
            extractors));
    }

    /**
     * <p>
     * Failure test for constructor
     * <code>MultipleFormatDependenciesEntryExtractor(Log,List&lt;BaseDependenciesEntryExtractor&gt;)</code>.
     * </p>
     * <p>
     * Passes in empty extractor list. A <code>IllegalArgumentException</code> should be thrown.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testCtor2_Failure1() throws Exception {
        try {
            List<BaseDependenciesEntryExtractor> extractors = new ArrayList<BaseDependenciesEntryExtractor>();
            new MultipleFormatDependenciesEntryExtractor(null, extractors);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Accuracy test for <code>extract(String)</code>.
     * </p>
     * <p>
     * Passes in valid build.xml path. No exception should be thrown.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testExtract_Accuracy1() throws Exception {
        DependenciesEntry entry = extractor.extract(TEST_JAVA_BUILD_XML);
        assertNotNull("it should return a not-null entry back.", entry);
        assertEquals("authentication", entry.getTargetComponent().getName());
        assertEquals("1.0.1", entry.getTargetComponent().getVersion());
        assertTrue(entry.getTargetComponent().getLanguage() == ComponentLanguage.JAVA);
        // 10 for both compile and test
        assertEquals(20, entry.getDependencies().size());
    }

    /**
     * <p>
     * Accuracy test for <code>extract(String)</code>.
     * </p>
     * <p>
     * Passes in default.build value. No exception should be thrown.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testExtract_Accuracy2() throws Exception {
        DependenciesEntry entry = extractor.extract(TEST_DOT_NET_BUILD_XML);
        assertNotNull("it should return a not-null entry back.", entry);
        assertEquals("authentication", entry.getTargetComponent().getName());
        assertEquals("1.0", entry.getTargetComponent().getVersion());
        assertTrue(entry.getTargetComponent().getLanguage() == ComponentLanguage.DOT_NET);
        // 5 for compile and 6 for test
        assertEquals(11, entry.getDependencies().size());
    }

    /**
     * <p>
     * Failure test for <code>extract(String)</code>.
     * </p>
     * <p>
     * Passes in null value. A <code>IllegalArgumentException</code> should be thrown.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testExtract_Failure1() throws Exception {
        try {
            extractor.extract(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test for <code>extract(String)</code>.
     * </p>
     * <p>
     * Passes in invalid value. A <code>DependenciesEntryExtractionException</code> should be thrown.
     * </p>
     */
    public void testExtract_Failure2() {
        try {
            extractor.extract("./test_files/not_exists.txt");
            fail("DependenciesEntryExtractionException should be thrown.");
        } catch (DependenciesEntryExtractionException e) {
            // pass
        }
    }

    /**
     * <p>
     * Accuracy test for <code>isSupportedFileType(String)</code>.
     * </p>
     * <p>
     * Passes in supported value. No exception should be thrown.
     * </p>
     */
    public void testIsSupportedFileType1_Accuracy() {
        assertTrue("it should support it.", extractor.isSupportedFileType("./anypath/component-1.0.jar"));
    }

    /**
     * <p>
     * Accuracy test for <code>isSupportedFileType(String)</code>.
     * </p>
     * <p>
     * Passes in supported value. No exception should be thrown.
     * </p>
     */
    public void testIsSupportedFileType2_Accuracy() {
        assertTrue("it should support it.", extractor.isSupportedFileType("./anypath/component-1.0.zip"));
    }

    /**
     * <p>
     * Accuracy test for <code>isSupportedFileType(String)</code>.
     * </p>
     * <p>
     * Passes in not supported value. No exception should be thrown.
     * </p>
     */
    public void testIsSupportedFileType3_Accuracy() {
        assertFalse("it should not support it.", extractor.isSupportedFileType("./anypath/a.txt"));
    }

    /**
     * <p>
     * Failure test for <code>isSupportedFileType(String)</code>.
     * </p>
     * <p>
     * Passes in empty file path value. A <code>IllegalArgumentException</code> should be thrown.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testIsSupportedFileType_Failure1() throws Exception {
        try {
            extractor.isSupportedFileType(" ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Accuracy test for <code>setLogger(Log)</code>.
     * </p>
     * <p>
     * Passes in null logger. No exception should be thrown.
     * </p>
     */
    public void testSetLogger_Accuracy() {
        extractor.setLogger(LogManager.getLog());
    }
}
