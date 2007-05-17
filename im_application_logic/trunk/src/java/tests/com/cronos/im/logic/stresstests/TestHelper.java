/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.logic.stresstests;

import java.util.Iterator;

import com.topcoder.util.config.ConfigManager;

/**
 * Helper class for the Stress tests.
 * 
 * @author kaqi072821
 * @version 1.0
 */
final class TestHelper {

    /**
     * Id of the online status of user.
     */
    static final long USER_STATUS_ONLINE = 101;

    /**
     * Id of the offline status of user.
     */
    static final long USER_STATUS_OFFLINE = 103;

    /**
     * Id of the open status of session.
     */
    static final long SESSION_STATUS_OPEN = 203;

    /**
     * Id of the close status of session.
     */
    static final long SESSION_STATUS_CLOSE = 204;

    /**
     * The maximum time allowed in a stress test.
     */
    static final long MAX_TIME = 1500;

    /**
     * Id of the user entity.
     */
    static final long ENTITY_USER = 1;

    /**
     * Id of the session entity.
     */
    static final long ENTITY_SESSION = 2;

    /**
     * Category id used in the stress test.
     */
    static final int CATEGORY_ID = 123;

    /**
     * User id used in the stress tests.
     */
    static final int USER_ID_TWO = 2;

    /**
     * Session id used in the stress tests.
     */
    static final int SESSION_ID_ONE = 1;

    /**
     * User id used in the stress tests.
     */
    static final int USER_ID_ONE = 1;


    /**
     * The path of configuration files for this test case.
     */
    private static final String[] CONFIG_FILES = new String[] {
            "test_files/stress_test_files/ChatSessionStatusTracker.xml",
            "test_files/stress_test_files/ChatUserStatusTracker.xml",
            "test_files/stress_test_files/ChatStatusTracker.xml", "test_files/stress_test_files/Logging.xml",
            "test_files/stress_test_files/ObjectFactory.xml", "test_files/stress_test_files/MessagePoolDetector.xml",
            "test_files/stress_test_files/Scheduler.xml", "test_files/stress_test_files/ServiceEngine.xml" };

    /**
     * Private constructor to prevent creation.
     */
    private TestHelper() {
    }

    /**
     * Initializes the environment. Loads configure files.
     * 
     * @throws Exception to JUnit
     */
    static void loadConfig() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        for (int loop = 0; loop < CONFIG_FILES.length; loop++) {
            cm.add(CONFIG_FILES[loop]);
        }
    }

    /**
     * Clears the test environment.
     * 
     * @throws Exception to JUnit
     */
    static void clearConfig() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        for (Iterator it = cm.getAllNamespaces(); it.hasNext();) {
            String ns = (String) it.next();
            cm.removeNamespace(ns);
        }
    }

}
