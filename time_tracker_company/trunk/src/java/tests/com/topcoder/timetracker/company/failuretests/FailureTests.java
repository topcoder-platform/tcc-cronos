/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.company.failuretests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Failure test cases.
 * </p>
 * @author FireIce
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
        suite.addTestSuite(CompanyFailureTests.class);
        suite.addTestSuite(CompanySearchBuilderFailureTests.class);
        suite.addTestSuite(CompanyDAOSynchronizedWrapperFailureTests.class);
        suite.addTestSuite(DbCompanyDAOFailureTests.class);
        suite.addTestSuite(LocalCompanyManagerDelegateFailureTests.class);
        return suite;
    }

}
