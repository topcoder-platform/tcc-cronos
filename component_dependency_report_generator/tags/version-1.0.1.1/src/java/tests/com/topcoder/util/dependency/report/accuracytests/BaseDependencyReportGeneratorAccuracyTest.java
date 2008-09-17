/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.dependency.report.accuracytests;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import junit.framework.TestCase;

import com.topcoder.util.dependency.DependenciesEntry;
import com.topcoder.util.dependency.DependencyCategory;
import com.topcoder.util.dependency.DependencyType;
import com.topcoder.util.dependency.persistence.DefaultXmlDependenciesEntryPersistence;
import com.topcoder.util.dependency.report.BaseDependencyReportGenerator;

/**
 * <p>
 * This class is the accuracy test case for <code>BaseDependencyReportGenerator</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class BaseDependencyReportGeneratorAccuracyTest extends TestCase {
    /**
     * Represents the <code>BaseDependencyReportGenerator</code> instance to be tested.
     */
    private BaseDependencyReportGenerator generator;

    /**
     * Represents the persistence instance to be used in test.
     */
    private DefaultXmlDependenciesEntryPersistence persistence;

    /**
     * Represents the byte array output stream to be used.
     */
    private ByteArrayOutputStream os = new ByteArrayOutputStream();

    /**
     * Represents the file name to be used in tests.
     */
    private String fileName = "test_files/accuracytests/baseTest.xml";

    /**
     * Sets up the testing environment.
     *
     * @throws Exception if any error occurs.
     */
    protected void setUp() throws Exception {
        super.setUp();
        generator = new MockDependencyReportGenerator(AccuracyHelper.createTestEntries(), AccuracyHelper
            .getConfig("BaseConfig"));
        persistence = new DefaultXmlDependenciesEntryPersistence("test_files/accuracytests/dependenciesInput.xml");
    }

    /**
     * Clears up the testing environment.
     *
     * @throws Exception if any error occurs.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        generator = null;
        persistence = null;
    }

    /**
     * <p>
     * Accuracy test for constructor
     * <code>BaseDependencyReportGenerator(List&lt;DependenciesEntry&gt;,ConfigurationObject)</code>.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testCtor1_Accuracy() throws Exception {
        assertNotNull("It should return non-null instance.", generator);
        AccuracyHelper.assertEquals(4, generator);
    }

    /**
     * <p>
     * Accuracy test for constructor
     * <code>BaseDependencyReportGenerator(DependenciesEntryPersistence,ConfigurationObject)</code>.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testCtor2_Accuracy() throws Exception {
        generator = new MockDependencyReportGenerator(persistence, AccuracyHelper.getConfig("BaseConfig"));
        assertNotNull("It should return non-null instance.", generator);
        AccuracyHelper.assertEquals(6, generator);
    }

    /**
     * <p>
     * Accuracy test for <code>generate(List&lt;String&gt;,OutputStream,boolean)</code>.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testGenerateWithIdOSIndirect_Accuracy1() throws Exception {
        generator.generate(AccuracyHelper.getAllComponentIds(), os, true);
        assertEquals("The final result should be 4 components.", 4, getResultEntries(generator).size());
    }

    /**
     * <p>
     * Accuracy test for <code>generate(List&lt;String&gt;,OutputStream,boolean)</code>.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testGenerateWithIdOSIndirect_Accuracy2() throws Exception {
        generator.generate(Arrays.asList(new String[]{"java-A-1.0" }), os, true);
        List<DependenciesEntry> result = getResultEntries(generator);
        assertEquals("The final result should be 1 components.", 1, result.size());
        assertEquals("A should have 3 dependencies here.", 3, result.get(0).getDependencies().size());
    }

    /**
     * <p>
     * Accuracy test for <code>generate(List&lt;String&gt;,OutputStream,boolean)</code>.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testGenerateWithIdOSIndirect_Accuracy3() throws Exception {
        generator.generate(Arrays.asList(new String[]{"java-A-1.0" }), os, false);
        List<DependenciesEntry> result = getResultEntries(generator);
        assertEquals("The final result should be 1 components.", 1, result.size());
        assertEquals("A should have 2 dependencies here.", 2, result.get(0).getDependencies().size());
    }

    /**
     * <p>
     * Accuracy test for <code>generate(List&lt;String&gt;,String,boolean)</code>.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testGenerateWithIdFileNameIndirect_Accuracy() throws Exception {
        generator.generate(AccuracyHelper.getAllComponentIds(), "test_files" + File.separator
            + "baseTestAccuracy.xml", true);
        assertEquals("The final result should be 4 components.", 4, getResultEntries(generator).size());
    }

    /**
     * <p>
     * Accuracy test for <code>generate(List&lt;String&gt;,boolean)</code>.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testGenerateWithIdIndirect_Accuracy() throws Exception {
        generator.generate(AccuracyHelper.getAllComponentIds(), true);
        assertEquals("The final result should be 4 components.", 4, getResultEntries(generator).size());
    }

    /**
     * <p>
     * Accuracy test for <code>generateAll(OutputStream,boolean)</code>.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testGenerateWithOSIndirect_Accuracy() throws Exception {
        generator.generateAll(os, true);
        assertEquals("The final result should be 4 components.", 4, getResultEntries(generator).size());
    }

    /**
     * <p>
     * Accuracy test for <code>generateAll(String,boolean)</code>.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testGenerateWithStringIndirect_Accuracy() throws Exception {
        generator.generateAll(fileName, true);
        assertEquals("The final result should be 4 components.", 4, getResultEntries(generator).size());
    }

    /**
     * <p>
     * Accuracy test for <code>generateAll(boolean)</code>.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testGenerateAllWithIndirect_Accuracy() throws Exception {
        assertEquals("4 entries", generator.generateAll(true));
        assertEquals("The final result should be 4 components.", 4, getResultEntries(generator).size());
    }

    /**
     * <p>
     * Accuracy test for <code>setAllowedDependencyTypes(Set&lt;DependencyType&gt;)</code>.
     * </p>
     */
    public void testSetAllowedDependencyTypes_Accuracy() {
        Set<DependencyType> types = new HashSet<DependencyType>();
        types.add(DependencyType.EXTERNAL);
        generator.setAllowedDependencyTypes(types);
    }

    /**
     * <p>
     * Accuracy test for <code>setAllowedDependencyCategories(Set&lt;DependencyCategory&gt;)</code>.
     * </p>
     */
    public void testSetAllowedDependencyCategories_Accuracy() {
        Set<DependencyCategory> cats = new HashSet<DependencyCategory>();
        cats.add(DependencyCategory.COMPILE);
        generator.setAllowedDependencyCategories(cats);
    }

    /**
     * <p>
     * Accuracy test for <code>isDependencyTypeIncluded()</code>.
     * </p>
     */
    public void testIsDependencyTypeIncluded_Accuracy() {
        assertTrue("current value should be true.", generator.isDependencyTypeIncluded());
    }

    /**
     * <p>
     * Accuracy test for <code>isDependencyCategoryIncluded()</code>.
     * </p>
     */
    public void testIsDependencyCategoryIncluded_Accuracy() {
        assertTrue("current value should be true.", generator.isDependencyCategoryIncluded());
    }

    /**
     * <p>
     * Accuracy test for <code>isDependencyPathIncluded()</code>.
     * </p>
     */
    public void testIsDependencyPathIncluded_Accuracy() {
        assertTrue("current value should be true.", generator.isDependencyPathIncluded());
    }

    /**
     * Returns result entries being passed into writeReport.
     *
     * @param mock generator instance to be verified
     * @return result entries
     */
    private List<DependenciesEntry> getResultEntries(BaseDependencyReportGenerator mock) {
        return ((MockDependencyReportGenerator) mock).resultEntries;
    }

}
