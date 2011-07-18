/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all accuracy test cases.
 * </p>
 *
 * @author Beijing2008
 * @version 1.0
 */
public class AccuracyTests extends TestCase {

    /**
     * The test suite.
     *
     * @return the test suite
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTest(ActivateAccountActionAccTests.suite());
        suite.addTest(CreateAccountActionAccTests.suite());
        suite.addTest(PreCreateAccountActionAccTests.suite());
        suite.addTest(ResendAccountActivationEmailActionAccTests.suite());
        suite.addTest(SelectRegistrationPreferenceActionAccTests.suite());
        suite.addTest(ViewRegistrationPreferenceActionAccTests.suite());
        return suite;
    }

}
