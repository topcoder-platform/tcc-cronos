/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource.persistence.sql;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * This test case aggregates all Unit test cases.
 *
 * @author Chenhong, mittu, TCSDEVELOPER
 * @version 1.4
 */
public class UnitTests extends TestCase {

    /**
     * Tests suites.
     *
     * @return returns the unit test suite
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(DemoTest.class);

        suite.addTestSuite(TestSqlResourcePersistence.class);
        suite.addTestSuite(TestUnManagedTransactionResourcePersistence.class);

        suite.addTestSuite(TestAbstractResourcePersistenceArgumentFailure.class);
        suite.addTestSuite(TestSqlResourcePersistenceConnectionFailure.class);
        suite.addTestSuite(TestUnmanagedTransactionResourcePersistenceConnectionFailure.class);

        suite.addTestSuite(BenchmarkTest.class);

        // since 1.3
        suite.addTestSuite(InformixReviewerStatisticsPersistenceUnitTests.class);
        suite.addTestSuite(UnmanagedTransactionInformixReviewerStatisticsPersistenceUnitTests.class);

        return suite;
    }

}
