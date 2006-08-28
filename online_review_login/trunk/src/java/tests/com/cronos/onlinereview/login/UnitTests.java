/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.login;

import com.cronos.onlinereview.login.authenticator.SecurityManagerAuthResponseParserTest;
import com.cronos.onlinereview.login.authenticator.SecurityManagerAuthenticatorTest;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all Unit test cases.</p>
 *
 * @author maone
 * @version 1.0
 */
public class UnitTests extends TestCase {

    /**
     * Suite all the unit tests.
     *
     * @return a suite of unit tests
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(AuthResponseParsingExceptionTest.class);
        suite.addTestSuite(ConfigurationExceptionTest.class);
        suite.addTestSuite(LoginActionsTest.class);
        suite.addTestSuite(UtilTest.class);

        suite.addTestSuite(SecurityManagerAuthenticatorTest.class);
        suite.addTestSuite(SecurityManagerAuthResponseParserTest.class);

        return suite;
    }

}
