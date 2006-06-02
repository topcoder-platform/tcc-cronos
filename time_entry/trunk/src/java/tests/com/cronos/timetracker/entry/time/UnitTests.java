/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.entry.time;

import com.cronos.timetracker.entry.time.search.V1Dot1BooleanExpressionUnitTest;
import com.cronos.timetracker.entry.time.search.V1Dot1ComparisonExpressionUnitTest;
import com.cronos.timetracker.entry.time.search.V1Dot1RangeExpressionUnitTest;
import com.cronos.timetracker.entry.time.search.V1Dot1SQLBasedCriteriaExpressionEvaluatorUnitTest;
import com.cronos.timetracker.entry.time.search.V1Dot1SearchExceptionUnitTest;
import com.cronos.timetracker.entry.time.search.V1Dot1SubstringExpressionUnitTest;
import com.cronos.timetracker.entry.time.search.V1Dot1TimeEntryCriteriaUnitTest;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * <p>
 * This test case aggregates all Unit test cases.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 2.0
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

        // V1.1 unit test
        suite.addTestSuite(V1Dot1AsynchBatchDAOWrapperUnitTest.class);
        suite.addTestSuite(V1Dot1BaseDAOUnitTest.class);
        suite.addTestSuite(V1Dot1BatchOperationExceptionUnitTest.class);
        suite.addTestSuite(V1Dot1RejectReasonDAOUnitTest.class);
        suite.addTestSuite(V1Dot1RejectReasonUnitTest.class);
        suite.addTestSuite(V1Dot1ResultDataUnitTest.class);
        suite.addTestSuite(V1Dot1TimeEntryDAOUnitTest.class);
        suite.addTestSuite(V1Dot1TimeEntryUnitTest.class);

        suite.addTestSuite(V1Dot1ComparisonExpressionUnitTest.class);
        suite.addTestSuite(V1Dot1BooleanExpressionUnitTest.class);
        suite.addTestSuite(V1Dot1RangeExpressionUnitTest.class);
        suite.addTestSuite(V1Dot1SearchExceptionUnitTest.class);
        suite.addTestSuite(V1Dot1SubstringExpressionUnitTest.class);
        suite.addTestSuite(V1Dot1TimeEntryCriteriaUnitTest.class);
        suite.addTestSuite(V1Dot1SQLBasedCriteriaExpressionEvaluatorUnitTest.class);

        // V1.1 demo test
        suite.addTestSuite(V1Dot1DemoTest.class);

        suite.addTestSuite(V2DotTimeStatusDAOUnitTest.class);
        suite.addTestSuite(V2DotTaskTypeDAOUnitTest.class);
        return suite;
    }
}
