/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved
 */
package com.cronos.timetracker.entry.time.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * <p>
 * This test case aggregates all Accuracy test cases.
 * </p>
 *
 * @author TopCoder
 * @version 1.0
 */
public class AccuracyTests extends TestCase {
    /**
     * return the test suite.
     *
     * @return the test suite.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(RejectReasonDAOAccuracyTest.class);
        suite.addTestSuite(ResultDataAccuracyTest.class);
        suite.addTestSuite(SQLBasedTimeEntryCriteriaExpressionEvaluatorAccuracyTest.class);
        suite.addTestSuite(TimeEntryDAOAccuracyTest.class);

        return suite;
    }
}
