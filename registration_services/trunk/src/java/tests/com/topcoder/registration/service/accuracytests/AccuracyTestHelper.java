/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.service.accuracytests;

import java.io.File;
import java.util.Iterator;

import com.topcoder.util.config.ConfigManager;

/**
 * <p>
 * Test Helper class for unit testing.
 * </p>
 *
 * @author mittu
 * @version 1.0
 */
class AccuracyTestHelper {
    /**
     * Represents the config file.
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