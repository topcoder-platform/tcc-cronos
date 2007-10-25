/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.validation.failuretests;

import java.io.File;
import java.util.Iterator;

import com.topcoder.util.config.ConfigManager;

/**
 * A helper to test.
 *
 * @author slion
 * @version 1.0
  */
public class TestHelper {
    /**
     * Represents the ConfigManager instance for loading configuration.
     */
    private static ConfigManager cm = ConfigManager.getInstance();

    /**
     * Private constructor to avoid initiating.
     */
    private TestHelper() {
    }

    /**
     * Loads the configuration from the file.
     * @param file the configuration file.
     * @throws Exception to JUnit.
     */
    public static void loadConfiguration(String file) throws Exception {
        cm.add(new File(file).getCanonicalPath());
    }

    /**
     * Clears the configurations.
     * @throws Exception to JUnit.
     */
    public static void clearConfiguration() throws Exception {
        for (Iterator it = cm.getAllNamespaces(); it.hasNext();) {
            cm.removeNamespace((String) it.next());
        }
    }
}
