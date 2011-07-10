/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.accuracytests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.topcoder.web.reg.actions.profile.ProfileCompletenessReportTest;
import com.topcoder.web.reg.actions.profile.TaskCompletionStatusTest;

/**
 * <p>
 * This test case aggregates all Accuracy test cases.
 * </p>
 *
 * @author TopCoder
 * @version 1.0
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ AccountInfoTaskCheckerTest.class, BaseDisplayProfileActionTest.class,
        BaseProfileActionTest.class, BaseProfileTaskCheckerTest.class, BaseSaveProfileActionTest.class,
        CompleteProfileActionTest.class, ContactInfoTaskCheckerTest.class,
        DefaultProfileCompletenessRetrieverTest.class, DemographicInfoTaskCheckerTest.class,
        EditAccountInfoActionTest.class, EditDemographicInfoActionTest.class, EditNameAndContactActionTest.class,
        EmailSendingProfileActionTest.class, ProfileCompletenessReportTest.class, ProfileTaskReportTest.class,
        SaveAccountInfoActionTest.class, SaveDemographicInfoActionTest.class, SaveNameAndContactActionTest.class,
        TaskCompletionStatusTest.class, ViewAccountInfoActionTest.class, ViewDemographicInfoActionTest.class,
        ViewNameAndContactActionTest.class, ViewProfileCompletenessActionTest.class })
public class AccuracyTests {

}
