/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.notification.failuretests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Failure test cases.
 * </p>
 * @author FireIce
 * @version 1.0
 */
public class FailureTests extends TestCase {

    /**
     * <p>
     * Aggregates all tests in this class.
     * </p>
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTestSuite(NotificationFailureTests.class);
        suite.addTestSuite(NotificationFilterFactoryFailureTests.class);
        suite.addTestSuite(NotificationManagerFailureTests.class);
        suite.addTestSuite(NotificationEventFailureTests.class);
        suite.addTestSuite(EmailNotificationSenderFailureTests.class);
        suite.addTestSuite(InformixNotificationPersistenceFailureTests.class);
        suite.addTestSuite(NotificationPersistenceFactoryFailureTests.class);
        suite.addTestSuite(NotificationPersistenceDelegateFailureTests.class);
        return suite;
    }

}
