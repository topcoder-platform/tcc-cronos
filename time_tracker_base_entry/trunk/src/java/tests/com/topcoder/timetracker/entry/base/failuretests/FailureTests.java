/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.base.failuretests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all failure cases for Time Tracker Base Entry 3.2.
 * </p>
 *
 * @author Hacker_QC
 * @version 3.2
 */
public class FailureTests extends TestCase {

    /**
     * <p>
     * Creates a test suite of the failure tests.
     * </p>
     *
     * @return a test suite of the tests contained in this class.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(BaseDaoFailureTest.class);
        suite.addTestSuite(BaseEntryFailureTest.class);
        suite.addTestSuite(CutoffTimeBeanFailureTest.class);
        suite.addTestSuite(EntryDelegateFailureTest.class);
        suite.addTestSuite(EntrySessionBeanFailureTest.class);
        suite.addTestSuite(InformixCutoffTimeDaoFailureTest.class);

        return suite;
    }
}
