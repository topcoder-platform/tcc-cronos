/*
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved
 */
package com.topcoder.timetracker.user.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all Accuracy test cases.</p>
 *
 * @author TopCoder
 * @version 1.0
 */
public class AccuracyTests extends TestCase {

    /**
     * return the test suite.
     * @return the test suite
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTestSuite(ConfigurationExceptionAccuracyTest.class);
        suite.addTestSuite(DbUserStoreAccuracyTest.class);
        suite.addTestSuite(PersistenceExceptionAccuracyTest.class);
        suite.addTestSuite(UnknownUserExceptionAccuracyTest.class);
        suite.addTestSuite(UnknownUserStoreExceptionAccuracyTest.class);
        suite.addTestSuite(UserAccuracyTest.class);
        suite.addTestSuite(UserPersistenceImplAccuracyTest.class);
        suite.addTestSuite(UserStoreManagerImplAccuracyTest.class);
        suite.addTestSuite(UserManagerAccuracyTest.class);
        //suite.addTest(XXX.suite());
        return suite;
    }

}
