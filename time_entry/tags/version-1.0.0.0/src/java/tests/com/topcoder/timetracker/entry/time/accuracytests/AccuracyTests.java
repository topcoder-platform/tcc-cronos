/*
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved
 */
package com.topcoder.timetracker.entry.time.accuracytests;

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
     * @return the test suite.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTestSuite(DAOActionExceptionAccuracyTest.class);
        suite.addTestSuite(DAOFactoryExceptionAccuracyTest.class);
        suite.addTestSuite(DAOFactoryAccuracyTest.class);
        suite.addTestSuite(DataObjectAccuracyTest.class);
        suite.addTestSuite(TaskTypeAccuracyTest.class);
        suite.addTestSuite(TaskTypeDAOAccuracyTest.class);
        suite.addTestSuite(TimeEntryAccuracyTest.class);
        suite.addTestSuite(TimeEntryDAOAccuracyTest.class);
        suite.addTestSuite(TimeStatusAccuracyTest.class);
        suite.addTestSuite(TimeStatusDAOAccuracyTest.class);
        suite.addTestSuite(BaseDataObjectAccuracyTest.class);
        return suite;
    }

}
