/**
 *
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved
 */
package com.topcoder.apps.screening.applications.specification.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Accuracy test cases.
 * </p>
 * 
 * @author TopCoder
 * @version 1.0
 */
public class AccuracyTests extends TestCase {

    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTest(ActivityDiagramPathReportGeneratorTest.suite());
        suite.addTest(ConfigurationExceptionTest.suite());
        suite.addTest(DefaultActivityDiagramNamingValidatorTest.suite());
        suite.addTest(DefaultActivityDiagramValidatorTest.suite());
        suite.addTest(DefaultSubmissionValidatorFactoryTest.suite());
        suite.addTest(DefaultUseCaseDiagramNamingValidatorTest.suite());
        suite.addTest(DefaultUseCaseDiagramValidatorTest.suite());
        suite.addTest(FormatterExceptionTest.suite());
        suite.addTest(SubmissionTest.suite());
        suite.addTest(SubmissionValidatorTest.suite());
        suite.addTest(TextValidationOutputFormatterTest.suite());
        suite.addTest(ValidationOutputTest.suite());
        suite.addTest(ValidationOutputTypeTest.suite());
        suite.addTest(XMLValidationOutputFormatterTest.suite());
        return suite;
    }
}