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
 * @author extra
 * @version 1.0
 */
public class AccuracyTests extends TestCase {
    /**
     * <p>
     * Aggregates all test cases.
     * </p>
     *
     * @return All test cases.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTestSuite(LoginActionAccuracyTests.class);
        suite.addTestSuite(LogoutActionAccuracyTests.class);
        suite.addTestSuite(RecoverPasswordActionAccuracyTests.class);
        suite.addTestSuite(ResendPasswordRecoveryEmailActionAccuracyTests.class);
        suite.addTestSuite(ResetPasswordActionAccuracyTests.class);
        return suite;
    }

}
