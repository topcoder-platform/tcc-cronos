/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.logic.failure;

import java.util.Iterator;

import com.topcoder.util.config.ConfigManager;

/**
 * Helper class for unit tests.
 *
 * @author singlewood
 * @version 1.0
 */

final class FailureTestHelper {
    /**
     * The path of configuration files for this test case.
     */
    private static final String[] CONFIG_FILES = new String[] {"failure/ChatUserStatusTracker.xml",
            "failure/ChatStatusTracker.xml", "failure/ChatSessionStatusTracker.xml",
            "failure/IMServiceHandler.xml", "failure/Logging.xml",
            "failure/ObjectFactory.xml", "failure/UserSessionEventListener.xml",
            "failure/MessagePoolDetector.xml", "failure/Scheduler.xml",
            "failure/MessagePoolDetectorInvalid.xml", "failure/IMServiceHandlerInvaild.xml"};

    /**
     * Private constructor.
     */
    private FailureTestHelper() {
    }

    /**
     * Initialize the environment.
     *
     * @throws Exception throw to JUnit
     */
    static void loadConfig() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        for (int loop = 0; loop < CONFIG_FILES.length; loop++) {
            cm.add(CONFIG_FILES[loop]);
        }
    }

    /**
     * Clear the test environment.
     *
     * @throws Exception throw to JUnit
     */
    static void clearConfig() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        for (Iterator iter = cm.getAllNamespaces(); iter.hasNext();) {
            String ns = (String) iter.next();
            cm.removeNamespace(ns);
        }
    }
}
