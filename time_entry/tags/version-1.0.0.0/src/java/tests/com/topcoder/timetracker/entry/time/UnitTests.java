/**
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved
 */
package com.topcoder.timetracker.entry.time;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * <p>
 * This test case aggregates all Unit test cases.
 * </p>
 *
 * @author TopCoder
 * @version 1.0
 */
public class UnitTests extends TestCase {
    /**
     * <p>
     * return the test suite.
     * </p>
     *
     * @return the test suite
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        // unit test
        suite.addTest(BaseDAOUnitTest.suite());
        suite.addTest(BaseDataObjectUnitTest.suite());
        suite.addTest(DataObjectUnitTest.suite());
        suite.addTest(DAOActionExceptionUnitTest.suite());
        suite.addTest(DAOFactoryExceptionUnitTest.suite());
        suite.addTest(DAOFactoryUnitTest.suite());
        suite.addTest(TaskTypeUnitTest.suite());
        suite.addTest(TimeStatusUnitTest.suite());
        suite.addTest(TimeEntryUnitTest.suite());
        suite.addTest(TaskTypeDAOUnitTest.suite());
        suite.addTest(TimeStatusDAOUnitTest.suite());
        suite.addTest(TimeEntryDAOUnitTest.suite());

        // demo test
        suite.addTest(DemoTest.suite());
        return suite;
    }
}
