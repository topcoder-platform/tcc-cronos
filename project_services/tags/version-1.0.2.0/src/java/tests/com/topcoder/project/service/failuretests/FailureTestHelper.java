/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.service.failuretests;

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
class FailureTestHelper {
    /**
     * Represents the config file folder.
     */
    public static final String CONFIG = System.getProperty("user.dir") + File.separator + "test_files"
            + File.separator + "failure" + File.separator;

    /**
     * <p>
     * Private constructor. No instances allowed.
     * </p>
     */
    private FailureTestHelper() {
    }

    /**
     * <p>
     * Loads the configuration from the given configuration file.
     * </p>
     * 
     * @param file
     *            the xml file to be loaded
     * @throws Exception
     *             exception to junit.
     */
    public static void loadConfigs(String file) throws Exception {
        releaseConfigs();
        ConfigManager cm = ConfigManager.getInstance();
        cm.add(CONFIG + file);
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