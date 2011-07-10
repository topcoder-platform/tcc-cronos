/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.web.reg.actions.basic.BaseActionTest;
import com.topcoder.web.reg.actions.basic.BasePasswordActionTest;
import com.topcoder.web.reg.actions.basic.BasePasswordRecoveryActionTest;
import com.topcoder.web.reg.actions.basic.BaseUserDAOAwareActionTest;
import com.topcoder.web.reg.actions.basic.BasicActionExceptionTest;
import com.topcoder.web.reg.actions.basic.LoginActionTest;
import com.topcoder.web.reg.actions.basic.LogoutActionTest;
import com.topcoder.web.reg.actions.basic.RecoverPasswordActionTest;
import com.topcoder.web.reg.actions.basic.ResendPasswordRecoveryEmailActionTest;
import com.topcoder.web.reg.actions.basic.ResetPasswordActionTest;
import com.topcoder.web.reg.interceptors.AuthorizationInterceptorExceptionTest;
import com.topcoder.web.reg.interceptors.AuthorizationInterceptorTest;
import com.topcoder.web.reg.interceptors.BaseInterceptorTest;
import com.topcoder.web.reg.interceptors.ThrottleInterceptorTest;


/**
 * <p>
 * This test case aggregates all Unit test cases.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class UnitTests extends TestCase {

    /**
     * <p>
     * All unit test suite.
     * </p>
     *
     * @return the test suite
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTest(BasicStrutsActionsConfigurationExceptionTest.suite());

        suite.addTest(BaseActionTest.suite());
        suite.addTest(BasePasswordActionTest.suite());
        suite.addTest(BasePasswordRecoveryActionTest.suite());
        suite.addTest(BaseUserDAOAwareActionTest.suite());
        suite.addTest(BasicActionExceptionTest.suite());
        suite.addTest(LoginActionTest.suite());
        suite.addTest(LogoutActionTest.suite());
        suite.addTest(RecoverPasswordActionTest.suite());
        suite.addTest(ResendPasswordRecoveryEmailActionTest.suite());
        suite.addTest(ResetPasswordActionTest.suite());

        suite.addTest(AuthorizationInterceptorExceptionTest.suite());
        suite.addTest(AuthorizationInterceptorTest.suite());
        suite.addTest(BaseInterceptorTest.suite());
        suite.addTest(ThrottleInterceptorTest.suite());

        suite.addTest(DemoTest.suite());

        return suite;
    }

}
