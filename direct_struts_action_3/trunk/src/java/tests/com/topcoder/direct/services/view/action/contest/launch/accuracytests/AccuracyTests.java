/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest.launch.accuracytests;

import junit.framework.JUnit4TestAdapter;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * The accuracy tests.
 *
 * @author KLW
 * @version 1.0
  */
@RunWith(Suite.class)
@SuiteClasses({
	ActiveContestPrizeIncreasingActionAccTest.class,
	ActiveContestPrizeRetrievalActionAccTest.class,
	ActiveContestScheduleExtensionActionAccTest.class,
	ActiveContestScheduleRetrievalActionAccTest.class,
	ContestReceiptRetrievalActionAccTest.class,
	ContestReceiptSendingActionAccTest.class,
	GamePlanRetrievalActionAccTest.class,
	ProjectContestDataRetrievalActionAccTest.class,
	WikiLinkRetrievalActionAccTest.class
})
public class AccuracyTests {
    public static junit.framework.Test suite() {    
        return new JUnit4TestAdapter(AccuracyTests.class);    
    }
}
