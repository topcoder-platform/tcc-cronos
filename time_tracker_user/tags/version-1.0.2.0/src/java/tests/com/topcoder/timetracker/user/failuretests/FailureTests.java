/**
 *
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved
 */
package com.cronos.timetracker.user.failuretests;

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

    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(AccountStatusFailureTests.class);
        suite.addTestSuite(AddressFailureTests.class);
        suite.addTestSuite(BatchCompanyDAOExceptionFailureTests.class);
        suite.addTestSuite(BatchUserDAOExceptionFailureTests.class);
        suite.addTestSuite(CompanyDAOExceptionFailureTests.class);
        suite.addTestSuite(CompanyFailureTests.class);
        suite.addTestSuite(CompanyNotFoundExceptionFailureTests.class);
        suite.addTestSuite(CompanySearchBuilderFailureTests.class);
        suite.addTestSuite(ContactFailureTests.class);
        suite.addTestSuite(DbCompanyDAOFailureTests.class);
        suite.addTestSuite(DbRejectEmailDAOFailureTests.class);
        suite.addTestSuite(DbRejectReasonDAOFailureTests.class);
        suite.addTestSuite(DbUserAuthenticatorFailureTests.class);
        suite.addTestSuite(DbUserDAOFailureTests.class);
        suite.addTestSuite(EncryptionRepositoryFailureTests.class);
        suite.addTestSuite(RejectEmailDAOExceptionFailureTests.class);
        suite.addTestSuite(RejectEmailFailureTests.class);
        suite.addTestSuite(RejectEmailNotFoundExceptionFailureTests.class);
        suite.addTestSuite(RejectEmailSearchBuilderFailureTests.class);
        suite.addTestSuite(RejectReasonDAOExceptionFailureTests.class);
        suite.addTestSuite(RejectReasonFailureTests.class);
        suite.addTestSuite(RejectReasonNotFoundExceptionFailureTests.class);
        suite.addTestSuite(StateFailureTests.class);
        suite.addTestSuite(TimeTrackerBeanFailureTests.class);
        suite.addTestSuite(UserDAOExceptionFailureTests.class);
        suite.addTestSuite(UserFailureTests.class);
        suite.addTestSuite(UserManagerFailureTests.class);
        suite.addTestSuite(UserNotFoundExceptionFailureTests.class);
        suite.addTestSuite(UserSearchBuilderFailureTests.class);

        return suite;
    }

}
