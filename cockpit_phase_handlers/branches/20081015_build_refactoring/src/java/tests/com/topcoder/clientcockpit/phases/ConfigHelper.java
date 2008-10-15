/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clientcockpit.phases;

import java.util.Iterator;

import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.ConfigManagerException;

/**
 * <p>
 * Used to config <code>ConfigManger</code>. It provides methods to load configuration files and clear namespaces.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ConfigHelper {

    /**
     * <p>
     * Private empty constructor.
     * </p>
     */
    private ConfigHelper() {
        //empty
    }

    /**
     * <p>
     * Initialize the config manager with the config files located in the /test_files.
     * </p>
     *
     * @throws ConfigManagerException to JUnit.
     */
    public static void initialConfigManager() throws ConfigManagerException {
        clearAllNamespace();

        ConfigManager cm = ConfigManager.getInstance();

        cm.add(ConfigHelper.class.getResource("/TestSMTPServer.xml"));
        cm.add(ConfigHelper.class.getResource("/ObjectPool.xml"));
        cm.add(ConfigHelper.class.getResource("/EmailEngine.xml"));
        cm.add(ConfigHelper.class.getResource("/DocumentManager.xml"));
        cm.add(ConfigHelper.class.getResource("/PhaseHandler.xml"));
        cm.add(ConfigHelper.class.getResource("/ObjectFactory.xml"));
    }

    /**
     * <p>
     * Clear all the namespaces in the config manager.
     * </p>
     *
     * @throws ConfigManagerException to JUnit.
     *
     */
    public static void clearAllNamespace() throws ConfigManagerException {
        ConfigManager cm = ConfigManager.getInstance();

        Iterator namespaceIterator = cm.getAllNamespaces();
        while (namespaceIterator.hasNext()) {
            cm.removeNamespace((String) namespaceIterator.next());
        }
    }
}
