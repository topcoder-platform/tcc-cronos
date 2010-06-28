/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest.launch;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all unit test cases.</p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class UnitTests extends TestCase {

    /**
     * The test suite.
     *
     * @return the test suite
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(ActiveContestPrizeIncreasingActionUnitTest.class);
        suite.addTestSuite(ActiveContestPrizeRetrievalActionUnitTest.class);
        suite.addTestSuite(ActiveContestScheduleExtensionActionUnitTest.class);
        suite.addTestSuite(ActiveContestScheduleRetrievalActionUnitTest.class);
        suite.addTestSuite(ContestReceiptRetrievalActionUnitTest.class);
        suite.addTestSuite(ContestReceiptSendingActionUnitTest.class);
        suite.addTestSuite(GamePlanRetrievalActionUnitTest.class);
        suite.addTestSuite(ProjectContestDataRetrievalActionUnitTest.class);
        suite.addTestSuite(WikiLinkRetrievalActionUnitTest.class);

        return suite;
    }
}
