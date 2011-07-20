/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous.failuretests;

import org.junit.runner.RunWith;

import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


/**
 * <p>This test case aggregates all Failure test cases.</p>
 *
 * @author TopCoder
 * @version 1.0
 */
@RunWith(Suite.class)
@SuiteClasses({DownloadBadgesActionTests.class,
    PreviewCardActionTests.class,
    RetrieveCardDataActionTests.class,
    ShowCardInstructionsActionTests.class,
    UnlockCardActionTests.class,
    ViewReferralsActionTests.class
})
public class FailureTests {
}
