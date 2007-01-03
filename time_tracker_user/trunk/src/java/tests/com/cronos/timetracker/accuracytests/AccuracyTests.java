/**
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved
 */
package com.cronos.timetracker.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Accuracy test cases.
 * </p>
 *
 * @author Chenhong
 * @version 2.0
 */
public class AccuracyTests extends TestCase {

    /**
     * Aggregates all Accuracy test cases.
     *
     * @return Test
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        // package common.
        suite.addTestSuite(TestRejectReasonAccuracy.class);
        suite.addTestSuite(TestAddressAccuracy.class);
        suite.addTestSuite(TestTimeTrackerBeanAccuracy.class);
        suite.addTestSuite(TestEncryptionRepositoryAccuracy.class);
        suite.addTestSuite(TestRejectEmailAccuracy.class);
        suite.addTestSuite(TestRejectEmailSearchBuilderAccuracy.class);
        suite.addTestSuite(TestStateAccuracy.class);
        suite.addTestSuite(TestRejectReasonSearchBuilderAccuracy.class);
        suite.addTestSuite(TestContactAccuracy.class);
        suite.addTestSuite(TestRejectEmailDAOExceptionAccuracy.class);
        suite.addTestSuite(TestRejectEmailNotFoundExceptionAccuracy.class);
        suite.addTestSuite(TestRejectReasonDAOExceptionAccuracy.class);
        suite.addTestSuite(TestRejectReasonNotFoundExceptionAccuracy.class);
        suite.addTestSuite(TestDbRejectEmailDAOAccuracy.class);
        suite.addTestSuite(TestDbRejectReasonDAOAccuracy.class);

        // package company.
        suite.addTestSuite(TestCompanyAccuracy.class);
        suite.addTestSuite(TestCompanySearchBuilderAccuracy.class);
        suite.addTestSuite(TestCompanyDAOExceptionAccuracy.class);
        suite.addTestSuite(TestCompanyNotFoundExceptionAccuracy.class);
        suite.addTestSuite(TestBatchCompanyDAOExceptionAccuracy.class);
        suite.addTestSuite(TestDbCompanyDAOAccuracy.class);

        // package user.
        suite.addTestSuite(TestAccountStatusAccuracy.class);
        suite.addTestSuite(TestBatchUserDAOExceptionAccuracy.class);
        suite.addTestSuite(TestUserNotFoundExceptionAccuracy.class);
        suite.addTestSuite(TestUserDAOExceptionAccuracy.class);

        suite.addTestSuite(TestMyDbUserAuthenticatorAccuracy.class);
        suite.addTestSuite(TestUserAccuracy.class);
        suite.addTestSuite(TestUserSearchBuilderAccuracy.class);
        suite.addTestSuite(TestDbUserDAOAccuracy.class);
        suite.addTestSuite(TestUserManagerAccuracy.class);

        suite.addTestSuite(TestSearchRejectEmail.class);
        suite.addTestSuite(TestSearchRejectReason.class);
        suite.addTestSuite(TestSearchCompany.class);
        suite.addTestSuite(TestSearchUser.class);

        return suite;
    }
}
