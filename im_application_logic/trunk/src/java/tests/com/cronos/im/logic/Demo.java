/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.logic;

import com.cronos.im.messenger.Messenger;
import com.topcoder.chat.message.pool.MessagePool;
import com.topcoder.chat.session.ChatSessionManager;
import com.topcoder.chat.status.ChatSessionStatusTracker;
import com.topcoder.chat.status.ChatUserStatusTracker;
import com.topcoder.chat.status.ChatStatusEventListener;
import com.topcoder.service.ServiceEngine;
import com.topcoder.util.scheduler.Scheduler;
import com.topcoder.util.scheduler.Job;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Calendar;
import junit.framework.TestCase;

/**
 * <p>
 * Demo for this component.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class Demo extends TestCase {

    /**
     * Initialize the environment.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        TestHelper.loadConfig();
    }

    /**
     * Clear the test environment.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void tearDown() throws Exception {
        TestHelper.clearConfig();
    }

    /**
     * Demo for usage of UserStatusEventListener and SessionStatusEventListener.
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_demo_status_event_listeners() throws Exception {
        // Demo the usage of UserStatusEventListener.
        // The UserStatusEventListener is plugged into the ChatUserStatusTracker component.
        ChatUserStatusTracker userStatusTracker = new ChatUserStatusTracker();

        MessagePool msgPool = new MockMessagePool();

        // Use default logger.
        IMLogger logger = new IMLogger(new SimpleDateFormat());

        // Initialize the user status event listener.
        ChatStatusEventListener userStatusEventListener = new UserStatusEventListener(userStatusTracker,
                msgPool, logger);

        userStatusTracker.addChatStatusEventListener(userStatusEventListener);

        // When the user status is change, the userStatusEventListener.statusChanged will be invoked.

        // Demo the usage of SessionStatusEventListener
        ChatSessionStatusTracker sessionStatusTracker = new ChatSessionStatusTracker();

        ChatSessionManager sessionManager = new MockChatSessionManager();
        Messenger messenger = new MockMessenger();

        // Initialize the session status event listener.
        ChatStatusEventListener sessionStatusEventListener = new SessionStatusEventListener(
                sessionStatusTracker, sessionManager, messenger, logger);

        sessionStatusTracker.addChatStatusEventListener(sessionStatusEventListener);

        // When the session status is changed, the sessionStatusEventListener.statusChanged
        // will be invoked.
    }

    /**
     * Demo for usage of UserSessionEventListener.
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_demo_user_session_event_listener() throws Exception {
        ChatSessionManager sessionManager = new MockChatSessionManager();

        UserSessionEventListener sessionEventListener = new UserSessionEventListener(sessionManager,
                new MockMessenger(), new ChatSessionStatusTracker(), null);

        sessionManager.addChatSessionEventListener(sessionEventListener);
    }

    /**
     * Demo for usage of IMServiceHandler.
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_demo_service_handler() throws Exception {
        ServiceEngine serviceEngine = new ServiceEngine();

        IMServiceHandler serviceHandler = new IMServiceHandler(new MockChatSessionManager(),
                new MockMessenger(), new ChatSessionStatusTracker(), new ChatUserStatusTracker(),
                serviceEngine, null);
        // manipulate service handler
        serviceHandler.getChatSessionManager();
        serviceHandler.clearAllResponders();

        // plug serviceHandler into service engine
    }

    /**
     * Demo for usage of MessagePoolDetector.
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_demo_message_pool_detector() throws Exception {
        // Instantiate the Scheduler, passing it the name of the config file
        // containing job data.
        Scheduler myScheduler = new Scheduler("com.topcoder.util.scheduler");

        // For first time runs, add new jobs.
        // This job will start at 1 am on the 10th of March (GregorianCalendar
        // months
        // run from 0 to 11), and will run once a day, at 1 am, everyday until
        // the 10th of March 2004 (inclusive).
        Job job = new Job("Message Pool Detector", new GregorianCalendar(2003, 04, 10, 01, 00, 00),
                new GregorianCalendar(2004, 04, 10, 01, 00, 00), 1, Calendar.DATE,
                Scheduler.JOB_TYPE_EXTERNAL, "java com.cronos.im.logic.MessagePoolDetector");
        myScheduler.addJob(job);

        // Start the scheduler.
        myScheduler.start();
    }

}
