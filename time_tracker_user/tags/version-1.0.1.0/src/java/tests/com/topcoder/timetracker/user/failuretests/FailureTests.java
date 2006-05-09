/**
 *
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved
 */
 package com.topcoder.timetracker.user.failuretests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.TestResult;

/**
 * <p>This test case aggregates all Failure test cases.</p>
 *
 * @author TopCoder
 * @version 1.0
 */
public class FailureTests extends TestCase {

    public static Test suite() {
        final TestSuite suite = new TestSuite();
        //suite.addTest(XXX.suite());
        suite.addTestSuite(UserFailureTests.class);
        suite.addTestSuite(UserPersistenceImplFailureTests.class);
        suite.addTestSuite(DbUserStoreFailureTests.class);
        suite.addTestSuite(UserStoreManagerImplFailureTests.class);
        suite.addTestSuite(UserManagerFailureTests.class);
        return suite;
    }

}
