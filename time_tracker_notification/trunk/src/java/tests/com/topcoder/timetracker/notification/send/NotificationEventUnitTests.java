/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.notification.send;

import com.topcoder.timetracker.notification.UnitTestHelper;

import com.topcoder.util.scheduler.scheduling.ScheduledEnable;

import junit.framework.TestCase;

import java.io.File;


/**
 * Unit test for the <code>NotificationEvent</code> class.
 *
 * @author kzhu
 * @version 3.2
 */
public class NotificationEventUnitTests extends TestCase {
    /** Represents the private instance used for test. */
    private NotificationEvent event = null;

    /**
     * Set up the test.
     *
     * @throws Exception any exception to Junit
     */
    protected void setUp() throws Exception {
        UnitTestHelper.clearConfig();
        UnitTestHelper.loadXMLConfig("test_files" + File.separatorChar + "db_config.xml");
        UnitTestHelper.loadXMLConfig("test_files" + File.separatorChar + "object_factory_config.xml");
        UnitTestHelper.loadXMLConfig("test_files" + File.separatorChar + "log_config.xml");
        UnitTestHelper.loadXMLConfig("test_files" + File.separatorChar + "notification_manager_config.xml");
        UnitTestHelper.loadXMLConfig("test_files" + File.separatorChar + "db_search_strategy_config.xml");

        event = new NotificationEvent();
    }

    /**
     * <p>
     * Accuracy test for Constructor.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testConstructorAccuracy() throws Exception {
        assertNotNull("Instance should be created.", event);
    }

    /**
     * <p>
     * Accuracy test for inheritance.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testInheritance() throws Exception {
        assertTrue("Instance should inherit from ScheduledEnable.", event instanceof ScheduledEnable);
    }

    /**
     * <p>
     * Accuracy test for method <code>run()</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testrunAccuracy() throws Exception {
        assertFalse("Is done is false.", event.isDone());

        event.run();

        assertTrue("Is done is true.", event.isDone());
        assertEquals("Will be failed.", ScheduledEnable.FAILED, event.getRunningStatus());
    }

    /**
     * <p>
     * Accuracy test for method <code>isDone</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testisDoneAccuracy() throws Exception {
        assertFalse("Is done is false.", event.isDone());
    }

    /**
     * <p>
     * Accuracy test for method <code>close</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testcloseAccuracy() throws Exception {
        event.close();
    }

    /**
     * <p>
     * Accuracy test for method <code>getStatus()</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testgetStatusAccuracy() throws Exception {
        assertEquals("The status will be not started.", ScheduledEnable.NOT_STARTED, event.getStatus());
    }

    /**
     * <p>
     * Accuracy test for method <code>getStatus()</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testgetStatusAccuracy1() throws Exception {
        event.run();
        assertEquals("The status will be not started.", ScheduledEnable.FAILED, event.getStatus());
    }

    /**
     * <p>
     * Accuracy test for method <code>getRunningStatus()</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testgetRunningStatusAccuracy() throws Exception {
        assertEquals("The status will be not started.", ScheduledEnable.NOT_STARTED, event.getRunningStatus());
    }

    /**
     * <p>
     * Accuracy test for method <code>getRunningStatus()</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testgetRunningStatusAccuracy1() throws Exception {
        event.run();
        assertEquals("The status will be not started.", ScheduledEnable.FAILED, event.getRunningStatus());
    }

    /**
     * <p>
     * Accuracy test for method <code>getMessageData</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testgetMessageDataAccuracy() throws Exception {
        assertNull("Get message data.", event.getMessageData());
    }

    /**
     * <p>
     * Failure test for method <code>setJobName</code>. Set with null is illegal. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testsetJobNameWithNull() throws Exception {
        try {
            event.setJobName(null);
            fail("Set with null is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Failure test for method <code>setJobName</code>. Set with empty is illegal. IllegalArgumentException is
     * expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testsetJobNameWithEmpty() throws Exception {
        try {
            event.setJobName("     ");
            fail("Set with empty is illegal, IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // ok to be here
        }
    }

    /**
     * <p>
     * Accuracy test for method <code>getJobName</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testgetJobNameAccuracy() throws Exception {
        event.setJobName("TT_NOTIFICATION_100");

        assertEquals("The right job name should be get.", "TT_NOTIFICATION_100", event.getJobName());
    }
}
