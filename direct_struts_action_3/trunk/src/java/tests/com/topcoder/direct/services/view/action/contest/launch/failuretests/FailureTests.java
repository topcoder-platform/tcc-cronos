/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest.launch.failuretests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Failure tests suite.
 * 
 * @author Beijing2008
 * @version 1.0
 */
public class FailureTests extends TestCase {
    /**
     * <p>
     * Aggregates all failure test cases.
     * </p>
     * 
     * @return the test suite
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        
        suite.addTestSuite(ActiveContestPrizeIncreasingActionFailureTest.class);
        suite.addTestSuite(ActiveContestPrizeRetrievalActionFailureTest.class);
        suite.addTestSuite(ActiveContestScheduleExtensionActionFailureTest.class);
        suite.addTestSuite(ActiveContestScheduleRetrievalActionFailureTest.class);
        suite.addTestSuite(ContestReceiptRetrievalActionFailureTest.class);
        suite.addTestSuite(ContestReceiptSendingActionFailureTest.class);
        suite.addTestSuite(GamePlanRetrievalActionFailureTest.class);
        suite.addTestSuite(ProjectContestDataRetrievalActionFailureTest.class);
        suite.addTestSuite(WikiLinkRetrievalActionFailureTest.class);

        return suite;
    }
}
