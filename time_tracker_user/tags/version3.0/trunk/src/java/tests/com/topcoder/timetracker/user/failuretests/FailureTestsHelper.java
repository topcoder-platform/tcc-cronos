/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user.failuretests;

import java.util.Iterator;

import com.topcoder.util.config.ConfigManager;

/**
 * <p>
 * Helper class that defines shared helper methods used during testing of this component.
 * </p>
 * @author kr00tki
 * @version 1.0
 */
final class FailureTestsHelper {
    /**
     * <p>
     * This class couldn't be instantiated.
     * </p>
     */
    private FailureTestsHelper() {
    }

    /**
     * <p>
     * Loads the specified config file.
     * </p>
     * @param configFile the config file to be loaded.
     * @throws Exception to JUnit.
     */
    static void loadConfig(String configFile) throws Exception {
        cleanConfig();

        ConfigManager configManager = ConfigManager.getInstance();
        configManager.add(configFile);
    }

    /**
     * <p>
     * Adds the specified config file.
     * </p>
     * @param configFile the config file to be loaded.
     * @throws Exception to JUnit.
     */
    static void addConfig(String configFile) throws Exception {
        ConfigManager.getInstance().add(configFile);
    }

    /**
     * <p>
     * Cleans all config namespaces needed by this conponent.
     * </p>
     * @throws Exception to JUnit.
     */
    static void cleanConfig() throws Exception {
        ConfigManager configManager = ConfigManager.getInstance();

        for (Iterator i = configManager.getAllNamespaces(); i.hasNext();) {
            String ns = (String) i.next();

            if (configManager.existsNamespace(ns)) {
                configManager.removeNamespace(ns);
            }
        }
    }
}
