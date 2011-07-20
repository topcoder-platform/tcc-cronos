/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous.accuracytests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.topcoder.web.reg.actions.miscellaneous.PreviewCardActionAccuracyTests;
import com.topcoder.web.reg.actions.miscellaneous.RetrieveCardDataActionAccuracyTests;
import com.topcoder.web.reg.actions.miscellaneous.ShowCardInstructionsActionAccuracyTests;

/**
 * <p>
 * This test case aggregates all accuracy test cases.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */

@RunWith(Suite.class)
@SuiteClasses({
    BaseDataAccessUserCommunityManagementActionAccuracyTests.class,
    BaseRatedUserCommunityManagementActionAccuracyTests.class,
    BaseUserCommunityManagementActionAccuracyTests.class,

    DownloadBadgesActionAccuracyTests.class,
    ReferralDataAccuracyTests.class,
    ShowCardDescriptionActionAccuracyTests.class,
    UnlockCardActionAccuracyTests.class,
    ViewReferralsActionAccuracyTests.class,

    PreviewCardActionAccuracyTests.class,

    RetrieveCardDataActionAccuracyTests.class,

    ShowCardInstructionsActionAccuracyTests.class
})
public class AccuracyTests {
}