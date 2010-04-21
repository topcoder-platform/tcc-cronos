/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.actions.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * <p>This test case aggregates all Accuracy test cases.</p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AccuracyTests extends TestCase {
    /**
     * Gets the suite of accuracy test.
     *
     * @return the suite of accuracy tests
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(XMLGregorianCalendarTypeConverterAccuracy.class);
        suite.addTestSuite(CreateProjectActionAccuracy.class);
        suite.addTestSuite(ValidationErrorsInterceptorAccuracy.class);
        suite.addTestSuite(GetAllBillingProjectsActionAccuracy.class);
        suite.addTestSuite(GetAllProjectsActionAccuracy.class);

        return suite;
    }
}
