/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.web.reg.actions.profile.AccountInfoTaskCheckerUnitTest;
import com.topcoder.web.reg.actions.profile.BaseDisplayProfileActionUnitTest;
import com.topcoder.web.reg.actions.profile.BaseProfileActionUnitTest;
import com.topcoder.web.reg.actions.profile.BaseProfileTaskCheckerUnitTest;
import com.topcoder.web.reg.actions.profile.BaseSaveProfileActionUnitTest;
import com.topcoder.web.reg.actions.profile.CompleteProfileActionUnitTest;
import com.topcoder.web.reg.actions.profile.ContactInfoTaskCheckerUnitTest;
import com.topcoder.web.reg.actions.profile.DefaultProfileCompletenessRetrieverUnitTest;
import com.topcoder.web.reg.actions.profile.DemographicInfoTaskCheckerUnitTest;
import com.topcoder.web.reg.actions.profile.EditAccountInfoActionUnitTest;
import com.topcoder.web.reg.actions.profile.EditDemographicInfoActionUnitTest;
import com.topcoder.web.reg.actions.profile.EditNameAndContactActionUnitTest;
import com.topcoder.web.reg.actions.profile.EmailSendingProfileActionUnitTest;
import com.topcoder.web.reg.actions.profile.HelperUnitTest;
import com.topcoder.web.reg.actions.profile.ProfileCompletenessReportTest;
import com.topcoder.web.reg.actions.profile.ProfileTaskReportUnitTest;
import com.topcoder.web.reg.actions.profile.SaveAccountInfoActionUnitTest;
import com.topcoder.web.reg.actions.profile.SaveDemographicInfoActionUnitTest;
import com.topcoder.web.reg.actions.profile.SaveNameAndContactActionUnitTest;
import com.topcoder.web.reg.actions.profile.TaskCompletionStatusTest;
import com.topcoder.web.reg.actions.profile.ViewAccountInfoActionUnitTest;
import com.topcoder.web.reg.actions.profile.ViewDemographicInfoActionActionUnitTest;
import com.topcoder.web.reg.actions.profile.ViewNameAndContactActionUnitTest;
import com.topcoder.web.reg.actions.profile.ViewProfileCompletenessActionUnitTest;

/**
 * <p>
 * This class aggregates all Unit tests for this component.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class UnitTests extends TestCase {

    /**
     * <p>
     * Creates TestSuite that aggregates all Unit tests for this component.
     * </p>
     * @return TestSuite that aggregates all Unit tests for this component
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        // base actions
        suite.addTestSuite(BaseProfileActionUnitTest.class);
        suite.addTestSuite(BaseDisplayProfileActionUnitTest.class);
        suite.addTestSuite(EmailSendingProfileActionUnitTest.class);
        suite.addTestSuite(BaseSaveProfileActionUnitTest.class);
        // generic actions
        suite.addTestSuite(SaveNameAndContactActionUnitTest.class);
        suite.addTestSuite(ViewNameAndContactActionUnitTest.class);
        suite.addTestSuite(EditNameAndContactActionUnitTest.class);
        suite.addTestSuite(SaveDemographicInfoActionUnitTest.class);
        suite.addTestSuite(ViewDemographicInfoActionActionUnitTest.class);
        suite.addTestSuite(EditDemographicInfoActionUnitTest.class);
        suite.addTestSuite(SaveAccountInfoActionUnitTest.class);
        suite.addTestSuite(ViewAccountInfoActionUnitTest.class);
        suite.addTestSuite(EditAccountInfoActionUnitTest.class);
        suite.addTestSuite(CompleteProfileActionUnitTest.class);
        suite.addTestSuite(ViewProfileCompletenessActionUnitTest.class);
        // retrievers and checkers
        suite.addTestSuite(DefaultProfileCompletenessRetrieverUnitTest.class);
        suite.addTestSuite(BaseProfileTaskCheckerUnitTest.class);
        suite.addTestSuite(AccountInfoTaskCheckerUnitTest.class);
        suite.addTestSuite(ContactInfoTaskCheckerUnitTest.class);
        suite.addTestSuite(DemographicInfoTaskCheckerUnitTest.class);
        // entities
        suite.addTestSuite(ProfileCompletenessReportTest.class);
        suite.addTestSuite(TaskCompletionStatusTest.class);
        suite.addTestSuite(ProfileTaskReportUnitTest.class);
        // exceptions
        suite.addTestSuite(ProfileActionConfigurationExceptionUnitTest.class);
        suite.addTestSuite(ProfileActionExceptionUnitTest.class);
        suite.addTestSuite(ProfileCompletenessRetrievalExceptionUnitTest.class);
        // demo
        suite.addTestSuite(Demo.class);
        // helper
        suite.addTestSuite(HelperUnitTest.class);
        return suite;
    }
}
