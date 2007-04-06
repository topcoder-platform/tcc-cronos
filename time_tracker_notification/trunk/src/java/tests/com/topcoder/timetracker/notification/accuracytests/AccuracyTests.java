/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.notification.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all Accuracy test cases.</p>
 *
 * @author kzhu
 * @version 1.0
 */
public class AccuracyTests extends TestCase {
    /**
     * <p>
     * This test case aggregates all Unit test cases.
     * </p>
     *
     * @return all accuracy test cases.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTest(EmailNotificationSenderAccuracyTests.suite());
        suite.addTest(InformixNotificationPersistenceAccuracyTests.suite());
        suite.addTest(MessageBodyGeneratorExceptionAccuracyTests.suite());
        suite.addTest(NotificationAccuracyTests.suite());
        suite.addTest(NotificationConfigurationExceptionAccuracyTests.suite());
        suite.addTest(NotificationEventAccuracyTests.suite());
        suite.addTest(NotificationFilterFactoryAccuracyTests.suite());
        suite.addTest(NotificationManagerAccuracyTests.suite());

        suite.addTest(NotificationPersistenceExceptionAccuracyTests.suite());
        suite.addTest(NotificationPersistenceFactoryAccuracyTests.suite());
        suite.addTest(NotificationPersistenceSessionBeanAccuracyTests.suite());

        suite.addTest(NotificationSendingExceptionAccuracyTests.suite());
        suite.addTest(StringMatchTypeAccuracyTests.suite());

        return suite;
    }

}
