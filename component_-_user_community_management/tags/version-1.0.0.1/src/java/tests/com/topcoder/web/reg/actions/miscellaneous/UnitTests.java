/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * <p>
 * This test case aggregates all test cases.
 * </p>
 *
 * @author mumujava
 * @version 1.0
 */
@RunWith(Suite.class)
@SuiteClasses({
UserCommunityManagementInitializationExceptionTest.class,
ReferralDataTest.class,
BaseDataAccessUserCommunityManagementActionTest.class,
BaseRatedUserCommunityManagementActionTest.class,
BaseUserCommunityManagementActionTest.class,
ViewReferralsActionTest.class,
ShowCardDescriptionActionTest.class,
ShowCardInstructionsActionTest.class,
CardHelperTest.class,
DownloadBadgesActionTest.class,
PreviewCardActionTest.class,
UnlockCardActionTest.class,
RetrieveCardDataActionTest.class
})
public class UnitTests {
}
