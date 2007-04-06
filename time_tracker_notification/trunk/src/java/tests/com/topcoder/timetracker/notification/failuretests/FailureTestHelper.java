/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.notification.failuretests;

import java.io.File;
import java.util.Iterator;

import com.topcoder.util.config.ConfigManager;

/**
 * <p>
 * Failure test helper class.
 * </p>
 * @author FireIce
 * @version 1.0
 */
class FailureTestHelper {

    /**
     * Represents the the root of all failure config files.
     */
    public static final String FAILURE_CONFIG_ROOT = "failuretests" + File.separator;

    /**
     * <p>
     * private class preventing instantiation.
     * </p>
     */
    private FailureTestHelper() {
    }

    /**
     * <p>
     * Clear previously loaded configurations.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public static void clearNamespaces() throws Exception {
        ConfigManager configManager = ConfigManager.getInstance();

        for (Iterator iter = configManager.getAllNamespaces(); iter.hasNext();) {
            configManager.removeNamespace((String) iter.next());
        }
    }
}
