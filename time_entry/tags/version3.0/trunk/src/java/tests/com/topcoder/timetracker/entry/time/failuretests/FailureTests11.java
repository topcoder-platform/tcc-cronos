/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
 package com.topcoder.timetracker.entry.time.failuretests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all Failure test cases.</p>
 *
 * @author GavinWang
 * @version 1.1
 */
public class FailureTests11 extends TestCase {

    public static Test suite() {
        final TestSuite suite = new TestSuite();
        //suite.addTest(XXX.suite());
        suite.addTestSuite(AsynchBatchDAOWrapperFailureTests.class);
        suite.addTestSuite(BaseDAO11FailureTests.class);
        suite.addTestSuite(ExpressionsFailureTests.class);
        suite.addTestSuite(RejectReasonDAOFailureTests.class);
        suite.addTestSuite(ResultDataFailureTests.class);
        suite.addTestSuite(SQLBasedTimeEntryCriteriaExpressionEvaluatorFailureTests.class);
        suite.addTestSuite(TimeEntry11FailureTests.class);
        suite.addTestSuite(TimeEntryDAO11FailureTests.class);
        
        return suite;
    }
    

}
