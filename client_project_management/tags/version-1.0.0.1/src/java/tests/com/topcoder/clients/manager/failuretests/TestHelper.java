/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.manager.failuretests;

import java.io.File;
import java.util.Iterator;

import com.topcoder.util.config.ConfigManager;

/**
 * A utility class to help unit testing.
 *
 * <p>
 * This class has only 2 apis. One is to clear all namespace of ConfigManager and the other is to load configuration
 * file for DBConnectionFactory.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 *
 */
public final class TestHelper {

    /**
     * Private ctor.
     *
     */
    private TestHelper() {
        // empty
    }

    /**
     * Load the DBConnectionFactory configuration file.
     *
     * @throws Exception
     *             to junit
     */
    public static void loadConfiguration() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();

        cm.add(new File("test_files/DBConnectionFactory.xml").getCanonicalPath());
    }

    /**
     * Clear all the namespace of ConfigManager.
     *
     * @throws Exception
     *             to junit
     */
    public static void clearConfigManager() throws Exception {

        ConfigManager cm = ConfigManager.getInstance();

        for (Iterator iter = cm.getAllNamespaces(); iter.hasNext();) {
            cm.removeNamespace((String) iter.next());
        }
    }
}
