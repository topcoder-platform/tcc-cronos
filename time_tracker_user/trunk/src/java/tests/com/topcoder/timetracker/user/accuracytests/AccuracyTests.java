/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * <p>This test case aggregates all Accuracy test cases.</p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class AccuracyTests extends TestCase {
    /**
     * <p>
     * This test case aggregates all Accuracy test cases.
     * </p>
     *
     * @return all Accuracy test cases.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTest(UserAuthenticatorAccuracyTests.suite());
        suite.addTest(UserManagerImplAccuracyTests.suite());
        suite.addTest(UnrecognizedEntityExceptionAccuracyTests.suite());
        suite.addTest(ConfigurationExceptionAccuracyTests.suite());
        suite.addTest(BatchOperationExceptionAccuracyTests.suite());
        suite.addTest(UserAccuracyTests.suite());
        suite.addTest(UserManagerFactoryAccuracyTests.suite());
        suite.addTest(StatusAccuracyTests.suite());
        suite.addTest(DataAccessExceptionAccuracyTests.suite());
        suite.addTest(StringMatchTypeAccuracyTests.suite());
        suite.addTest(DbUserDAOAccuracyTests.suite());
        suite.addTest(MappedUserFilterFactoryAccuracyTests.suite());
        suite.addTest(MappedBaseFilterFactoryAccuracyTests.suite());
        suite.addTest(UserManagerSessionBeanAccuracyTests.suite());
        
        //3.2.1 added.
        suite.addTestSuite(TestUserStatusAccuracy.class);
        suite.addTestSuite(TestUserTypeAccuracy.class);
        suite.addTestSuite(UserStatusFilterFactoryAccuracyTests.class);
        suite.addTestSuite(UserTypeFilterFactoryAccuracyTests.class);

        suite.addTestSuite(DbUserStatusDAOAccuracyTests.class);
        suite.addTestSuite(UserStatusManagerImplAccuracyTests.class);
        suite.addTestSuite(UserStatusManagerSessionBeanAccuracyTests.class);
        suite.addTestSuite(UserStatusManagerDelegateAccuracyTests.class);
        

        suite.addTestSuite(DbUserTypeDAOAccuracyTests.class);
        suite.addTestSuite(UserTypeManagerImplAccuracyTests.class);
        suite.addTestSuite(UserTypeManagerSessionBeanAccuracyTests.class);
        suite.addTestSuite(UserTypeManagerDelegateAccuracyTests.class);

        return suite;
    }

}
