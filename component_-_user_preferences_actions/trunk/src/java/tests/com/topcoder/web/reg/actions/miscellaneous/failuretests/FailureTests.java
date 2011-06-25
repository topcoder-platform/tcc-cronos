/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous.failuretests;

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
public class FailureTests extends TestCase {

    /**
     * <p>
     * Creates TestSuite that aggregates all Unit tests for this component.
     * </p>
     * @return TestSuite that aggregates all Unit tests for this component
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTestSuite(BasePreferencesActionTest.class);
        suite.addTestSuite(ConfigurePrivacyPreferencesActionTest.class);
        suite.addTestSuite(BlackListActionTest.class);
        suite.addTestSuite(EmailNotificationActionTest.class);
        suite.addTestSuite(PaymentPreferencesActionTest.class);
        suite.addTestSuite(ForumWatchPreferencesActionTest.class);
        suite.addTestSuite(ForumGeneralPreferencesActionTest.class);
        suite.addTestSuite(MyForumPostHistoryActionTest.class);
        return suite;
    }
}
