/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.notification.ejb;

import com.topcoder.timetracker.notification.NotificationConfigurationException;
import com.topcoder.timetracker.notification.UnitTestHelper;

import junit.framework.TestCase;

import java.io.File;


/**
 * Unit test for the <code>NotificationPersistenceFactory</code> class.
 *
 * @author kzhu
 * @version 3.2
 */
public class NotificationPersistenceFactoryUnitTests extends TestCase {
    /**
     * Set tup the test.
     *
     * @throws Exception to Junit
     */
    protected void setUp() throws Exception {
        UnitTestHelper.clearConfig();
        UnitTestHelper.loadXMLConfig("test_files" + File.separatorChar + "db_config.xml");
        UnitTestHelper.loadXMLConfig("test_files" + File.separatorChar + "object_factory_config.xml");
        UnitTestHelper.loadXMLConfig("test_files" + File.separatorChar + "log_config.xml");
        UnitTestHelper.loadXMLConfig("test_files" + File.separatorChar + "notification_manager_config.xml");
        UnitTestHelper.loadXMLConfig("test_files" + File.separatorChar + "db_search_strategy_config.xml");
        UnitTestHelper.loadXMLConfig("test_files" + File.separatorChar + "np_factory_config.xml");
    }

    /**
     * <p>
     * Accuracy test for method <code>getNotificationPersistence</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testgetNotificationPersistenceAccuracy()
        throws Exception {
        assertNotNull("The persistence should be get.", NotificationPersistenceFactory.getNotificationPersistence());
    }
}
