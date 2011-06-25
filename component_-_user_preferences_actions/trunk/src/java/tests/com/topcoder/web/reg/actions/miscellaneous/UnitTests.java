/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

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
        suite.addTestSuite(BasePreferenceActionParametersUnitTest.class);
        suite.addTestSuite(BasePreferenceActionUnitTest.class);
        suite.addTestSuite(BaseForumActionParametersUnitTest.class);
        // actions
        suite.addTestSuite(ConfigurePrivacyPreferencesActionParametersUnitTest.class);
        suite.addTestSuite(ConfigurePrivacyPreferencesActionUnitTest.class);
        suite.addTestSuite(BlackListActionParametersUnitTest.class);
        suite.addTestSuite(BlackListActionUnitTest.class);
        suite.addTestSuite(EmailNotificationActionParametersUnitTest.class);
        suite.addTestSuite(EmailNotificationActionUnitTest.class);
        suite.addTestSuite(PaymentPreferencesParametersUnitTest.class);
        suite.addTestSuite(PaymentPreferencesActionUnitTest.class);
        // forum actions
        suite.addTestSuite(ForumRatingPreferencesActionParametersUnitTest.class);
        suite.addTestSuite(ForumRatingPreferencesActionUnitTest.class);
        suite.addTestSuite(ForumWatchPreferencesActionParametersUnitTest.class);
        suite.addTestSuite(ForumWatchPreferencesActionUnitTest.class);
        suite.addTestSuite(ForumGeneralPreferencesActionParametersUnitTest.class);
        suite.addTestSuite(ForumGeneralPreferencesActionUnitTest.class);
        suite.addTestSuite(MyForumPostHistoryActionParametersUnitTest.class);
        suite.addTestSuite(MyForumPostHistoryActionUnitTest.class);
        // exceptions
        suite.addTestSuite(UserPreferencesActionConfigurationExceptionUnitTest.class);
        suite.addTestSuite(PreferencesActionDiscardExceptionUnitTest.class);
        suite.addTestSuite(UserPreferencesActionExecutionExceptionUnitTest.class);
        // helper
        suite.addTestSuite(HelperTest.class);
        // demo
        suite.addTestSuite(Demo.class);
        return suite;
    }
}
