/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.dependency;

import java.util.HashSet;
import java.util.Set;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Unit tests for <code>BaseDependenciesEntryExtractor</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class BaseDependenciesEntryExtractorTest extends TestCase {
    /**
     * <p>
     * <code>BaseDependenciesEntryExtractor</code> instance to be tested.
     * </p>
     */
    private BaseDependenciesEntryExtractor extractor;

    /**
     * <p>
     * Sets up the fixture. This method is called before a test is executed.
     * </p>
     *
     * @throws Exception if any error occurs.
     */
    protected void setUp() throws Exception {
        super.setUp();
        // we implement a dummy sub class so that we can test extractor's basic functions
        extractor = new MockDependenciesEntryExtractor();
    }

    private static class MockDependenciesEntryExtractor extends BaseDependenciesEntryExtractor {
        /**
         * <p>
         * Mock ctor. It delegates to super().
         * </p>
         */
        public MockDependenciesEntryExtractor() {
            super();
        }

        /**
         * <p>
         * Mock implementation.
         * </p>
         *
         * @param filePath file path
         * @return always null
         * @throws DependenciesEntryExtractionException any error if possible
         */
        @Override
        public DependenciesEntry extract(String filePath) throws DependenciesEntryExtractionException {
            return null;
        }

        /**
         * <p>
         * Mock implementation.
         * </p>
         *
         * @param filePath file path
         * @return always false
         */
        @Override
        public boolean isSupportedFileType(String filePath) {
            return false;
        }
    };

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
     * Gets the test suite for <code>BaseDependenciesEntryExtractor</code> class.
     * </p>
     *
     * @return a <code>TestSuite</code> providing the tests for <code>BaseDependenciesEntryExtractor</code> class.
     */
    public static Test suite() {
        return new TestSuite(BaseDependenciesEntryExtractorTest.class);
    }

    /**
     * <p>
     * Accuracy test for current mock ctor which uses <code>BaseDependenciesEntryExtractor()</code> ctor.
     * </p>
     * <p>
     * The instance will be created successfully.
     * </p>
     */
    public void testCtor() {
        assertNotNull("", extractor);
        assertEquals("it should have 2 types by default.", 2, extractor.getExtractedTypes().size());
        assertEquals("it should have 2 categories by default.", 2, extractor.getExtractedCategories().size());
    }

    /**
     * <p>
     * Accuracy test for <code>setExtractedTypes(Set&lt;DependencyType&gt;)</code>.
     * </p>
     * <p>
     * Passes in valid types value. No exception should be thrown.
     * </p>
     */
    public void testSetExtractedTypes_Accuracy() {
        Set<DependencyType> types = new HashSet<DependencyType>();
        types.add(DependencyType.EXTERNAL);
        extractor.setExtractedTypes(types);
        assertEquals("types should be only 1.", 1, extractor.getExtractedTypes().size());
    }

    /**
     * <p>
     * Failure test for <code>setExtractedTypes(Set&lt;DependencyType&gt;)</code>.
     * </p>
     * <p>
     * Passes in empty types value. A <code>IllegalArgumentException</code> should be thrown.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testSetExtractedTypes_Failure1() throws Exception {
        try {
            Set<DependencyType> types = new HashSet<DependencyType>();
            extractor.setExtractedTypes(types);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Accuracy test for <code>setExtractedCategories(Set&lt;DependencyCategory&gt;)</code>.
     * </p>
     * <p>
     * Passes in valid value. No exception should be thrown.
     * </p>
     */
    public void testSetExtractedCategories_Accuracy() {
        Set<DependencyCategory> cats = new HashSet<DependencyCategory>();
        cats.add(DependencyCategory.COMPILE);
        extractor.setExtractedCategories(cats);
        assertEquals("categories should be only 1 now.", 1, extractor.getExtractedCategories().size());
    }

    /**
     * <p>
     * Failure test for <code>setExtractedCategories(Set&lt;DependencyCategory&gt;)</code>.
     * </p>
     * <p>
     * Passes in empty collection value. A <code>IllegalArgumentException</code> should be thrown.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testSetExtractedCategories_Failure1() throws Exception {
        try {
            Set<DependencyCategory> cats = new HashSet<DependencyCategory>();
            extractor.setExtractedCategories(cats);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Accuracy test for <code>getExtractedTypes()</code>.
     * </p>
     * <p>
     * Gets right number of types. No exception should be thrown.
     * </p>
     */
    public void testGetExtractedTypes_Accuracy() {
        assertEquals("types should be 2 now.", 2, extractor.getExtractedTypes().size());
    }

    /**
     * <p>
     * Accuracy test for <code>getExtractedCategories()</code>.
     * </p>
     * <p>
     * Gets right number of categories. No exception should be thrown.
     * </p>
     */
    public void testGetExtractedCategories_Accuracy() {
        assertEquals("categories should be 2 now.", 2, extractor.getExtractedCategories().size());
    }
}
