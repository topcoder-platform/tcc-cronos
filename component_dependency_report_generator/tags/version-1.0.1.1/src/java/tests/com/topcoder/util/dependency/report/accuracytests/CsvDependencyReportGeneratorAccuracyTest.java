/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.dependency.report.accuracytests;

import junit.framework.TestCase;

import com.topcoder.util.dependency.persistence.DefaultXmlDependenciesEntryPersistence;

/**
 * <p>
 * This class is the accuracy tests for <code>CsvDependencyReportGenerator</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class CsvDependencyReportGeneratorAccuracyTest extends TestCase {

    /**
     * Represents the file name used for accuracy test.
     */
    private static final String ACC_FILE = "test_files/accuracytests/csvGeneratorTestAccuracy.dat";

    /**
     * Represents the <code>CsvDependencyReportGeneratorWrapper</code> instance used for accuracy test.
     */
    private CsvDependencyReportGeneratorWrapper generator;

    /**
     * Represents the persistence instance used for accuracy test.
     */
    private DefaultXmlDependenciesEntryPersistence persistence;

    /**
     * Sets up the testing environment.
     *
     * @throws Exception if any error occurs.
     */
    protected void setUp() throws Exception {
        AccuracyHelper.outputstream.reset();
        generator = new CsvDependencyReportGeneratorWrapper(AccuracyHelper.createTestEntries(), AccuracyHelper
            .getConfig("BaseConfig"));
        persistence = new DefaultXmlDependenciesEntryPersistence("test_files/accuracytests/dependenciesInput.xml");
    }

    /**
     * Accuracy test for constructor
     * <code>CsvDependencyReportGenerator(List&lt;DependenciesEntry&gt;,ConfigurationObject)</code>.
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testCtor1_Accuracy() throws Exception {
        assertNotNull("The created instance should not be null", generator);
    }

    /**
     * Accuracy test for constructor
     * <code>CsvDependencyReportGenerator(DependenciesEntryPersistence,ConfigurationObject)</code>.
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testCtor2_Accuracy() throws Exception {
        generator = new CsvDependencyReportGeneratorWrapper(persistence, AccuracyHelper.getConfig("BaseConfig"));
        assertNotNull(generator);
        AccuracyHelper.assertEquals(6, generator);
    }

    /**
     * Accuracy test for <code>writeReport(List&lt;DependenciesEntry&gt;,OutputStream)</code>.
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testWriteReport_Accuracy1() throws Exception {
        generator.writeReport(AccuracyHelper.createTestEntries(), AccuracyHelper.outputstream);
        String result = new String(AccuracyHelper.outputstream.toByteArray());
        assertTrue(result.contains("java-A-1.0,"));
    }

    /**
     * Accuracy test for <code>writeReport(List&lt;DependenciesEntry&gt;,OutputStream)</code>.
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testWriteReport_Accuracy2() throws Exception {
        try {
            generator.generate(AccuracyHelper.getAllComponentIds(), ACC_FILE, true);
        } catch (Exception e) {
            // no exception should occur here
            fail("Error occurs!");
        }
    }
}
