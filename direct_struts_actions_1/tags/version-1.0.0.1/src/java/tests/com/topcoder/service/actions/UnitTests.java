/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.actions;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Unit test cases.
 * </p>
 *
 * @author FireIce
 * @version 1.0
 */
public class UnitTests extends TestCase {

    /**
     * <p>
     * Aggregates all unit tests.
     * </p>
     *
     * @return test suite aggregating all unit tests.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTest(DirectStrutsActionsHelperUnitTests.suite());
        suite.addTest(BaseDirectStrutsActionUnitTests.suite());
        suite.addTest(ContestActionUnitTests.suite());
        suite.addTest(ProjectActionUnitTests.suite());
        suite.addTest(CreateProjectActionUnitTests.suite());
        suite.addTest(GetAllBillingProjectsActionUnitTests.suite());
        suite.addTest(GetAllProjectsActionUnitTests.suite());
        suite.addTest(GetContestActionUnitTests.suite());
        suite.addTest(GetCapacityFullDatesActionUnitTests.suite());
        suite.addTest(DateAfterCurrentDateValidatorUnitTests.suite());
        suite.addTest(ValidationErrorsInterceptorUnitTests.suite());
        suite.addTest(SaveDraftContestActionUnitTests.suite());
        suite.addTest(XMLGregorianCalendarTypeConverterUnitTests.suite());
        return suite;
    }

}
