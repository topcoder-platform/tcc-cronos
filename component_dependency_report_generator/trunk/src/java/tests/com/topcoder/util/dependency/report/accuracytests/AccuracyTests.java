/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.dependency.report.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Accuracy test cases.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AccuracyTests extends TestCase {

    /**
     * <p>
     * Aggregates all test cases.
     * </p>
     *
     * @return A test suite
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(BaseDependencyReportGeneratorAccuracyTest.class);
        suite.addTestSuite(CircularComponentDependencyExceptionAccuracyTest.class);
        suite.addTestSuite(ComponentIdNotFoundExceptionAccuracyTest.class);
        suite.addTestSuite(CsvDependencyReportGeneratorAccuracyTest.class);
        suite.addTestSuite(DependencyReportConfigurationExceptionAccuracyTest.class);

        suite.addTestSuite(DependencyReportGenerationExceptionAccuracyTest.class);
        suite.addTestSuite(HtmlDependencyReportGeneratorAccuracyTest.class);
        suite.addTestSuite(XmlDependencyReportGeneratorAccuracyTest.class);

        suite.addTestSuite(DependencyReportGeneratorUtilityAccuracyTest.class);

        return suite;
    }
}
