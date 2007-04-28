/**
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved
 */
package com.topcoder.timetracker.project.failuretests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * <p>
 * This test case aggregates all Failure test cases.
 * </p>
 *
 * @author TopCoder
 * @version 1.0
 */
public class FailureTests extends TestCase {
    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(ClientFailureTest.class);
        suite.addTestSuite(ClientUtilityFailureTest.class);
        suite.addTestSuite(ProjectFailureTest.class);
        suite.addTestSuite(ProjectManagerFailureTest.class);
        suite.addTestSuite(ProjectPersistenceManagerFailureTest.class);
        suite.addTestSuite(ProjectUtilityFailureTest.class);
        suite.addTestSuite(ProjectWorkerFailureTest.class);
        suite.addTestSuite(InformixTimeTrackerProjectPersistenceFailureTest.class);

        // since 1.1
        suite.addTestSuite(ValueFilterFailureTest.class);
        suite.addTestSuite(NotFilterFailureTest.class);
        suite.addTestSuite(MultiValueFilterFailureTest.class);
        suite.addTestSuite(BinaryOperationFilterFailureTest.class);
        suite.addTestSuite(DatabaseSearchUtilityFailureTest.class);
        return suite;
    }
}
