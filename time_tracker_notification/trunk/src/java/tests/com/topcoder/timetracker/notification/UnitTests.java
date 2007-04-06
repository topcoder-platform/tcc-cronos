/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.notification;

import com.topcoder.timetracker.notification.ejb.NotificationPersistenceDelegateUnitTests;
import com.topcoder.timetracker.notification.ejb.NotificationPersistenceFactoryUnitTests;
import com.topcoder.timetracker.notification.ejb.NotificationPersistenceSessionBeanUnitTests;
import com.topcoder.timetracker.notification.persistence.InformixNotificationPersistenceUnitTests;
import com.topcoder.timetracker.notification.send.EmailNotificationSenderUnitTests;
import com.topcoder.timetracker.notification.send.MessageBodyGeneratorExceptionUnitTests;
import com.topcoder.timetracker.notification.send.NotificationEventUnitTests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * <p>
 * This test case aggregates all Unit test cases.
 * </p>
 *
 * @author kzhu
 * @version 3.2
 */
public class UnitTests extends TestCase {
    /**
     * Add the test suite.
     *
     * @return the Unit test suite
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        // add the package com.topcoder.timetracker.notification.persistence.
        suite.addTestSuite(InformixNotificationPersistenceUnitTests.class);

        // add the package com.topcoder.timetracker.notification
        suite.addTestSuite(NotificationConfigurationExceptionUnitTests.class);
        suite.addTestSuite(NotificationManagerUnitTests.class);
        suite.addTestSuite(HelperUnitTests.class);
        suite.addTestSuite(NotificationFilterFactoryUnitTests.class);
        suite.addTestSuite(NotificationPersistenceExceptionUnitTests.class);
        suite.addTestSuite(NotificationSendingExceptionUnitTests.class);
        suite.addTestSuite(NotificationUnitTests.class);
        suite.addTestSuite(StringMatchTypeUnitTests.class);

        // add the package com.topcoder.timetracker.notification.send.
        suite.addTestSuite(NotificationEventUnitTests.class);
        suite.addTestSuite(MessageBodyGeneratorExceptionUnitTests.class);
        suite.addTestSuite(EmailNotificationSenderUnitTests.class);

        // add the package com.topcoder.timetracker.notification.ejb.
        suite.addTestSuite(NotificationPersistenceDelegateUnitTests.class);
        suite.addTestSuite(NotificationPersistenceFactoryUnitTests.class);
        suite.addTestSuite(NotificationPersistenceSessionBeanUnitTests.class);

        // add the demo
        suite.addTestSuite(DemoTest.class);

        return suite;
    }
}
