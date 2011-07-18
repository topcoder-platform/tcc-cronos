/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg;

import com.topcoder.web.reg.actions.registration.ActivateAccountActionTest;
import com.topcoder.web.reg.actions.registration.BaseRegistrationActionTest;
import com.topcoder.web.reg.actions.registration.CreateAccountActionTest;
import com.topcoder.web.reg.actions.registration.EmailSendingActionTest;
import com.topcoder.web.reg.actions.registration.HelperTest;
import com.topcoder.web.reg.actions.registration.PreCreateAccountActionTest;
import com.topcoder.web.reg.actions.registration.ResendAccountActivationEmailActionTest;
import com.topcoder.web.reg.actions.registration.SelectRegistrationPreferenceActionTest;
import com.topcoder.web.reg.actions.registration.ViewRegistrationPreferenceActionTest;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Unit test cases.
 * </p>
 *
 * @author stevenfrog
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

        suite.addTest(RegistrationActionConfigurationExceptionTest.suite());

        suite.addTest(ActivateAccountActionTest.suite());
        suite.addTest(BaseRegistrationActionTest.suite());
        suite.addTest(CreateAccountActionTest.suite());
        suite.addTest(EmailSendingActionTest.suite());
        suite.addTest(PreCreateAccountActionTest.suite());
        suite.addTest(ResendAccountActivationEmailActionTest.suite());
        suite.addTest(SelectRegistrationPreferenceActionTest.suite());
        suite.addTest(ViewRegistrationPreferenceActionTest.suite());

        suite.addTest(DemoTest.suite());

        suite.addTest(HelperTest.suite());

        return suite;
    }

}
