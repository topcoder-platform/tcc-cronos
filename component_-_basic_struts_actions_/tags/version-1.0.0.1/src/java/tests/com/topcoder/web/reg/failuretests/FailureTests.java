/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.failuretests;


import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all Failure test cases.</p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class FailureTests extends TestCase {
    /**
     * Test suite for the failure tests.
     *
     * @return the test suite
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTest(BasePasswordActionFailureTests.suite());
        suite.addTest(ResendPasswordRecoveryEmailActionFailureTests.suite());
        suite.addTest(LoginActionFailureTests.suite());
        suite.addTest(ResetPasswordActionFailureTests.suite());
        suite.addTest(LogoutActionFailureTests.suite());
        suite.addTest(BaseActionFailureTests.suite());
        suite.addTest(BasePasswordRecoveryActionFailureTests.suite());
        suite.addTest(RecoverPasswordActionFailureTests.suite());
        suite.addTest(BaseUserDAOAwareActionFailureTests.suite());
        suite.addTest(AuthorizationInterceptorFailureTests.suite());
        suite.addTest(ThrottleInterceptorFailureTests.suite());


        return suite;
    }

}
