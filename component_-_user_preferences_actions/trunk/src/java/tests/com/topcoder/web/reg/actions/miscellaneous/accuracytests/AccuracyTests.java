/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous.accuracytests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * <p>
 * This class aggregates all Accuracy tests for this component.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AccuracyTests {

    /**
     * <p>
     * Creates TestSuite that aggregates all test Accuracy cases for this component.
     * </p>
     * @return TestSuite that aggregates all test Accuracy cases for this component
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTest(ConfigurePrivacyPreferencesActionAccuracyTests.suite());
        suite.addTest(ForumRatingPreferencesActionAccuracyTests.suite());
        suite.addTest(UserPreferencesActionConfigurationExceptionAccuracyTests.suite());
        suite.addTest(BlackListActionAccuracyTests.suite());
        suite.addTest(UserPreferencesActionExecutionExceptionAccuracyTests.suite());
        suite.addTest(MyForumPostHistoryActionAccuracyTests.suite());
        suite.addTest(ForumWatchPreferencesActionAccuracyTests.suite());
        suite.addTest(PaymentPreferencesActionAccuracyTests.suite());
        suite.addTest(PreferencesActionDiscardExceptionAccuracyTests.suite());
        suite.addTest(EmailNotificationActionAccuracyTests.suite());
        suite.addTest(ForumGeneralPreferencesActionAccuracyTests.suite());

        return suite;
    }
}
