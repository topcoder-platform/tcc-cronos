/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.logic;

import com.topcoder.util.config.ConfigManager;

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
        "ChatUserStatusTracker.xml",
        "ChatStatusTracker.xml", "ChatSessionStatusTracker.xml",
        "IMServiceHandler.xml", "Logging.xml", "ObjectFactory.xml",
        "UserSessionEventListener.xml", "MessagePoolDetector.xml",
        "Scheduler.xml"};

    /**
     * The namespaces in the above configuration files.
     */
    private static final String[] NAMESPACES = new String[] {
        "com.cronos.im.logic.objectfactory",
        "com.topcoder.util.log", "com.cronos.im.logic.MessagePoolDetector",
        "com.cronos.im.logic.MessagePoolDetector.Invalid1",
        "com.cronos.im.logic.MessagePoolDetector.Invalid2",
        "com.cronos.im.logic.MessagePoolDetector.Invalid3",
        "com.cronos.im.logic.MessagePoolDetector.Invalid4",
        "com.cronos.im.logic.MessagePoolDetector.Invalid5",
        "com.cronos.im.logic.MessagePoolDetector.Invalid6",
        "com.cronos.im.logic.MessagePoolDetector.Invalid7",
        "com.cronos.im.logic.MessagePoolDetector.Invalid8",
        "com.cronos.im.logic.MessagePoolDetector.Invalid9",
        "com.cronos.im.logic.MessagePoolDetector.Invalid10", "com.cronos.im.logic.IMServiceHandler",
        "com.cronos.im.logic.IMServiceHandler.Invalid1", "com.cronos.im.logic.IMServiceHandler.Invalid2",
        "com.cronos.im.logic.IMServiceHandler.Invalid3", "com.cronos.im.logic.IMServiceHandler.Invalid4",
        "com.topcoder.chat.status", "com.topcoder.chat.status.session", "com.topcoder.chat.status.user",
        "com.cronos.im.logic.UserSessionEventListener",
        "com.cronos.im.logic.UserSessionEventListener.Invalid1",
        "com.cronos.im.logic.UserSessionEventListener.Invalid2",
        "com.cronos.im.logic.UserSessionEventListener.Invalid3", "com.topcoder.util.scheduler"};

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
        for (int loop = 0; loop < NAMESPACES.length; loop++) {
            if (cm.existsNamespace(NAMESPACES[loop])) {
                cm.removeNamespace(NAMESPACES[loop]);
            }
        }
    }

}
