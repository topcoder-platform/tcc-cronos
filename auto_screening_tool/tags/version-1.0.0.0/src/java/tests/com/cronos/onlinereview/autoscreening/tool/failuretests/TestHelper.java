/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.tool.failuretests;

import java.util.Iterator;

import com.topcoder.util.config.ConfigManager;

/**
 * The helper class for failure tests.
 *
 * @author assistant
 * @version 1.0
 */
final class TestHelper {

    /**
     * Load the configurations.
     *
     * @throws Exception if any error
     */
    public static void loadConfig() throws Exception {
        TestHelper.unloadConfig();
        ConfigManager cm = ConfigManager.getInstance();
        cm.add("failure/logging.xml");
        cm.add("failure/failure.xml");
        cm.add("failure/object_factory.xml");
        cm.add("failure/dbconfig.xml");
    }

    /**
     * Unload the configurations.
     *
     * @throws Exception if any error
     */
    public static void unloadConfig() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        Iterator it = cm.getAllNamespaces();
        while (it.hasNext()) {
            cm.removeNamespace(it.next().toString());
        }
    }

    /**
     * Remove the namespace.
     * @param namespace the ns to remove
     * @throws Exception if any error
     */
    public static void unloadNS(String namespace) throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        cm.removeNamespace(namespace);
    }

    /**
     * Load the xml file.
     * @param file the file to load
     * @throws Exception if any error
     */
    public static void loadFile(String file) throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        cm.add(file);
    }

}
