/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.logic;

import com.topcoder.util.config.ConfigManager;
import java.util.Iterator;

/**
 * Helper class for the unit tests.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
final class TestHelper {

    /**
     * The path of configuration files for this test case.
     */
    private static final String[] CONFIG_FILES = new String[] {
        "test_files/ChatUserStatusTracker.xml",
        "test_files/ChatStatusTracker.xml", "test_files/ChatSessionStatusTracker.xml",
        "test_files/IMServiceHandler.xml", "test_files/Logging.xml", "test_files/ObjectFactory.xml",
        "test_files/UserSessionEventListener.xml", "test_files/MessagePoolDetector.xml",
        "test_files/Scheduler.xml", "test_files/ServiceEngine.xml"};


    /**
     * Private constructor to prevent creation.
     */
    private TestHelper() {
    }

    /**
     * Initialize the environment.
     *
     * @throws Exception
     *             to JUnit
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
     * @throws Exception
     *             to JUnit
     */
    static void clearConfig() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        for (Iterator it = cm.getAllNamespaces(); it.hasNext();) {
            String ns = (String) it.next();
            cm.removeNamespace(ns);
        }
    }

}
