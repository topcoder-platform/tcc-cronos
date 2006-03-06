/**
 *
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved
 */
package com.topcoder.apps.screening.applications.specification.failuretests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * <p>
 * This test case aggregates all Failure test cases.
 * </p>
 *
 * @author TopCoder
 * @version 1.0
 */
public class FailureTests extends TestCase {
    
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTestSuite(SubmissionFailureTests.class);
        suite.addTestSuite(SubmissionValidatorFailureTests.class);
        suite.addTestSuite(ActivityDiagramPathReportGeneratorFailureTests.class);
        suite.addTestSuite(ActivityDiagramValidatorFailureTests.class);
        suite.addTestSuite(DefaultActivityDiagramNamingValidatorFailureTests.class);
        suite.addTestSuite(DefaultActivityDiagramValidatorFailureTests.class);
        suite.addTestSuite(DefaultSubmissionValidatorFactoryFailureTests.class);
        suite.addTestSuite(DefaultUseCaseDiagramNamingValidatorFailureTests.class);
        suite.addTestSuite(DefaultUseCaseDiagramValidatorFailureTests.class);
        suite.addTestSuite(TextValidationOutputFormatterFailureTests.class);
        suite.addTestSuite(UseCaseDiagramValidatorFailureTests.class);
        suite.addTestSuite(ValidationManagerFailureTests.class);
        suite.addTestSuite(ValidationOutputFailureTests.class);
        suite.addTestSuite(XMLValidationOutputFormatterFailureTests.class);

        return suite;
    }
}
