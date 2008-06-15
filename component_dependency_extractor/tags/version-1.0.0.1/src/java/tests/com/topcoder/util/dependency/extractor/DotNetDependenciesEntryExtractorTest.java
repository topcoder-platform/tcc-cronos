/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.dependency.extractor;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.util.dependency.ComponentLanguage;
import com.topcoder.util.dependency.DependenciesEntry;
import com.topcoder.util.dependency.DependenciesEntryExtractionException;
import com.topcoder.util.dependency.DependencyType;
import com.topcoder.util.log.LogManager;

/**
 * <p>
 * Unit tests for <code>DotNetDependenciesEntryExtractor</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DotNetDependenciesEntryExtractorTest extends TestCase {
    /**
     * <p>
     * default.build to be used in test.
     * </p>
     */
    private static final String TEST_BUILD_XML = "test_files" + File.separator + "scripts" + File.separator
        + "default.build";

    /**
     * <p>
     * a zip file to be used in test.
     * </p>
     */
    private static final String TEST_ZIP_FILE = "test_files" + File.separator + "scripts" + File.separator + "dotnet"
        + File.separator + "object_factory-1.2.1.zip";

    /**
     * <p>
     * <code>DotNetDependenciesEntryExtractor</code> instance to be tested.
     * </p>
     */
    private DotNetDependenciesEntryExtractor extractor;

    /**
     * <p>
     * Sets up the fixture. This method is called before a test is executed.
     * </p>
     *
     * @throws Exception if any error occurs.
     */
    protected void setUp() throws Exception {
        super.setUp();
        extractor = new DotNetDependenciesEntryExtractor(LogManager.getLog());
        Set<DependencyType> types = new HashSet<DependencyType>();
        types.add(DependencyType.INTERNAL);
        extractor.setExtractedTypes(types);
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
     * Gets the test suite for <code>DotNetDependenciesEntryExtractor</code> class.
     * </p>
     *
     * @return a <code>TestSuite</code> providing the tests for <code>DotNetDependenciesEntryExtractor</code> class.
     */
    public static Test suite() {
        return new TestSuite(DotNetDependenciesEntryExtractorTest.class);
    }

    /**
     * <p>
     * Accuracy test for constructor <code>DotNetDependenciesEntryExtractor(Log)</code>.
     * </p>
     * <p>
     * Uses the constructor to create a non-null instance. No exception should be thrown.
     * </p>
     */
    public void testCtor1_Accuracy1() {
        assertNotNull("It should return non-null instance.", extractor);
    }

    /**
     * <p>
     * Accuracy test for constructor <code>DotNetDependenciesEntryExtractor(Log)</code>.
     * </p>
     * <p>
     * Uses the constructor to create a non-null instance.logger could be null. No exception should be thrown.
     * </p>
     */
    public void testCtor1_Accuracy2() {
        assertNotNull("It should return non-null instance.", new DotNetDependenciesEntryExtractor(null));
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
    public void testExtract_Accuracy1() throws Exception {
        DependenciesEntry entry = extractor.extract(TEST_BUILD_XML);
        assertNotNull("it should return a not-null entry back.", entry);
        assertEquals("authentication", entry.getTargetComponent().getName());
        assertEquals("1.0", entry.getTargetComponent().getVersion());
        assertTrue(entry.getTargetComponent().getLanguage() == ComponentLanguage.DOT_NET);
        // 5 for compile and 6 for test minus 1 NUnit which is external
        assertEquals(10, entry.getDependencies().size());
    }

    /**
     * <p>
     * Accuracy test for <code>extract(String)</code>.
     * </p>
     * <p>
     * Passes in zip file value. No exception should be thrown.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testExtract_Accuracy2() throws Exception {
        DependenciesEntry entry = extractor.extract(TEST_ZIP_FILE);
        assertNotNull("it should return a not-null entry back.", entry);
        assertEquals("object_factory", entry.getTargetComponent().getName());
        assertEquals("1.2.1", entry.getTargetComponent().getVersion());
        assertTrue(entry.getTargetComponent().getLanguage() == ComponentLanguage.DOT_NET);
        // 1 for compile and 2 for test minus NUnit which is external
        assertEquals(2, entry.getDependencies().size());
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
     * Passes in wrong zip file. A <code>DependenciesEntryExtractionException</code> should be thrown.
     * </p>
     */
    public void testExtract_Failure2() {
        try {
            extractor.extract("test_files" + File.separator + "scripts" + File.separator + "dotnet" + File.separator
                + "wrongfile.zip");
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
        assertTrue("it should support it.", extractor.isSupportedFileType("./anypath/default.build"));
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
     * Passes in empty value. A <code>IllegalArgumentException</code> should be thrown.
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
     * Passes in null value. It means no logging. No exception should be thrown.
     * </p>
     */
    public void testSetLogger_Accuracy() {
        extractor.setLogger(null);
    }
}
