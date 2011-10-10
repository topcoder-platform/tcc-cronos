/*
 * Copyright (C) 2007 - 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project.persistence;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Unit test cases.
 * </p>
 * @author urtks, fuyun, pvmagacho, TCSDEVELOPER
 * @version 1.2.1
 * @since 1.0
 */
public class UnitTests extends TestCase {
    /**
     * Aggregates all tests in this class.
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTest(InformixProjectPersistenceTest.suite());
        suite.addTest(DemoTest.suite());
        suite.addTestSuite(InformixProjectPersistenceV11Test.class);
        suite.addTestSuite(UnmanagedTransactionInformixProjectPersistenceTest.class);
        suite.addTestSuite(DemoV11Test.class);
        suite.addTestSuite(HelperTest.class);

        // added in version 1.2 (Review Process Improvements Project)
        suite.addTestSuite(InformixReviewApplicationPersistenceUnitTests.class);
        suite.addTestSuite(UnmanagedTransactionInformixReviewApplicationPersistenceUnitTests.class);

        // added in version 1.2 (TC - Studio Replatforming Project)
        suite.addTestSuite(AbstractInformixProjectPersistenceUnitTests.class);
        suite.addTestSuite(DemoV12Test.class);

        return suite;
    }
}
