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
    private static final String[] CONFIG_FILES = new String[] {"test_files/failure/ChatUserStatusTracker.xml",
            "test_files/failure/ChatStatusTracker.xml", "test_files/failure/ChatSessionStatusTracker.xml",
            "test_files/failure/IMServiceHandler.xml", "test_files/failure/Logging.xml",
            "test_files/failure/ObjectFactory.xml", "test_files/failure/UserSessionEventListener.xml",
            "test_files/failure/MessagePoolDetector.xml", "test_files/failure/Scheduler.xml",
            "test_files/failure/MessagePoolDetectorInvalid.xml", "test_files/failure/IMServiceHandlerInvaild.xml"};

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
