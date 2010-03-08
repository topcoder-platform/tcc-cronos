/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.service;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.service.actions.AbstractActionUnitTest;
import com.topcoder.service.actions.AggregateDataModelUnitTest;
import com.topcoder.service.actions.HelperUnitTest;
import com.topcoder.service.actions.ValidationErrorRecordUnitTest;
import com.topcoder.service.actions.ValidationErrorsUnitTest;
import com.topcoder.service.interceptors.AuthenticationInterceptorUnitTest;
import com.topcoder.service.interceptors.InterceptorWebUnitTest;
import com.topcoder.service.interceptors.LoggingInterceptorUnitTest;

/**
 * <p>This test case aggregates all unit test cases.</p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class UnitTests extends TestCase {

    /**
     * The test suite.
     *
     * @return the test suite
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(AbstractActionUnitTest.class);
        suite.addTestSuite(AggregateDataModelUnitTest.class);
        suite.addTestSuite(ValidationErrorRecordUnitTest.class);
        suite.addTestSuite(ValidationErrorsUnitTest.class);

        suite.addTestSuite(AuthenticationInterceptorUnitTest.class);
        suite.addTestSuite(LoggingInterceptorUnitTest.class);
        suite.addTestSuite(InterceptorWebUnitTest.class);
        suite.addTestSuite(HelperUnitTest.class);

        suite.addTestSuite(Demo.class);

        return suite;
    }

}
