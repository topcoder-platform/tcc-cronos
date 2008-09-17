/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.dependency.report.accuracytests;

import junit.framework.TestCase;

import com.topcoder.util.dependency.persistence.DefaultXmlDependenciesEntryPersistence;

/**
 * <p>
 * This class is the accuracy test case for <code>XmlDependencyReportGenerator</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class XmlDependencyReportGeneratorAccuracyTest extends TestCase {

    /**
     * Represents the configuration file for accuracy test.
     */
    private static final String ACC_FILE = "test_files/accuracytests/xmlGeneratorTestAccuracy.xml";

    /**
     * Represents the <code>XmlDependencyReportGenerator</code> instance for accuracy test.
     */
    private XmlDependencyReportGeneratorWrapper generator;

    /**
     * Represents the <code>DefaultXmlDependenciesEntryPersistence</code> instance for accuracy test.
     */
    private DefaultXmlDependenciesEntryPersistence persistence;

    /**
     * Sets up the testing environment.
     *
     * @throws Exception if any error occurs.
     */
    protected void setUp() throws Exception {
        AccuracyHelper.outputstream.reset();
        generator = new XmlDependencyReportGeneratorWrapper(AccuracyHelper.createTestEntries(), AccuracyHelper
            .getConfig("BaseConfig"));
        persistence = new DefaultXmlDependenciesEntryPersistence("test_files/accuracytests/dependenciesInput.xml");
    }

    /**
     * Accuracy test for constructor
     * <code>XmlDependencyReportGenerator(List&lt;DependenciesEntry&gt;,ConfigurationObject)</code>.
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testCtor1_Accuracy() throws Exception {
        try {
            assertNotNull("It should return non-null instance.", generator);
        } catch (Exception e) {
            // no exception is allowed here
            fail("No error should occur!");
        }
    }

    /**
     * Accuracy test for constructor
     * <code>XmlDependencyReportGenerator(DependenciesEntryPersistence,ConfigurationObject)</code>.
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testCtor2_Accuracy() throws Exception {
        generator = new XmlDependencyReportGeneratorWrapper(persistence, AccuracyHelper.getConfig("BaseConfig"));
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
        assertTrue(result.contains("<components>"));
        assertTrue(result.contains("</components>"));
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
            // no exception is allowed here
            fail("No exception is allowed!");
        }
    }
}
