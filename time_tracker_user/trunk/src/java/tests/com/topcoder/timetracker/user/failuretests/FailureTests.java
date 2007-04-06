/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user.failuretests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Failure test cases.
 * </p>
 * @author TopCoder
 * @version 1.0
 */
public class FailureTests extends TestCase {

    /**
     * <p>
     * Aggregates all failure tests.
     * </p>
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTestSuite(UserFailureTests.class);
        suite.addTestSuite(UserAuthenticatorFailureTests.class);
        suite.addTestSuite(UserManagerFactoryFailureTests.class);
        suite.addTestSuite(UserManagerImplFailureTests.class);
        suite.addTestSuite(MappedBaseFilterFactoryFailureTests.class);
        suite.addTestSuite(MappedUserFilterFactoryFailureTests.class);
        suite.addTestSuite(DbUserDAOFailureTests.class);
        suite.addTestSuite(UserManagerDelegateFailureTests.class);
        return suite;
    }

}
