/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.logic.accuracytests;

import java.io.File;
import java.util.Iterator;

import com.topcoder.util.config.ConfigManager;

/**
 * <p>
 * Test Helper class for unit testing.
 * </p>
 *
 * @author mittu
 * @version 3.2
 */
class AccuracyTestHelper {
    /**
     * <p>
     * Represents the millisecs for applying the time in testing.
     * </p>
     */
    public static final int ONE_SEC = 1000;

    /**
     * <p>
     * Represents the Requester UserId for testing.
     * </p>
     */
    public static final long REQ_USER_ID = 1;

    /**
     * <p>
     * Represents the Responder UserId for testing.
     * </p>
     */
    public static final long RES_USER_ID = 11;

    /**
     * <p>
     * Represents the SessionId for testing.
     * </p>
     */
    public static final long SESSION_ID = 222;

    /**
     * <p>
     * Represents the Category id for testing.
     * </p>
     */
    public static final long CATEGORY_ID = 10;

    /**
     * Represents the config.xml used for accuracy testing.
     */
    public static final String CONFIG = System.getProperty("user.dir") + File.separator + "test_files"
        + File.separator + "accuracy" + File.separator + "config.xml";

    /**
     * <p>
     * Private constructor. No instances allowed.
     * </p>
     */
    private AccuracyTestHelper() {
    }

    /**
     * <p>
     * Loads the configuration from the given configuration file.
     * </p>
     *
     * @throws Exception
     *             exception to junit.
     */
    public static void loadConfigs() throws Exception {
        releaseConfigs();
        ConfigManager cm = ConfigManager.getInstance();
        cm.add(CONFIG);
    }

    /**
     * <p>
     * Releases the configurations.
     * </p>
     *
     * @throws Exception
     *             exception to junit.
     */
    public static void releaseConfigs() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        for (Iterator iterator = cm.getAllNamespaces(); iterator.hasNext();) {
            cm.removeNamespace((String) iterator.next());
        }
    }
}