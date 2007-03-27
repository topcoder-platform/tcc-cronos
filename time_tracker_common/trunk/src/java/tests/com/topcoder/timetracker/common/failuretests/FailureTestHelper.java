/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.common.failuretests;

import java.util.Iterator;

import com.topcoder.util.config.ConfigManager;

/**
 * <p>
 * Failure testing helper class.
 * </p>
 * @author FireIce
 * @version 1.0
 */
class FailureTestHelper {
    public static final String LONG_STRING = "this is a very long string, that it exceeds the length limitation of the payment term.";

    /**
     * <p>
     * private constructor preventing instantiation.
     * </p>
     */
    private FailureTestHelper() {
    }

    /**
     * <p>
     * clear the testing namespaces.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    static void clearNamespaces() throws Exception {
        ConfigManager configManager = ConfigManager.getInstance();

        for (Iterator iter = configManager.getAllNamespaces(); iter.hasNext();) {
            configManager.removeNamespace((String) iter.next());
        }
    }

    /**
     * <p>
     * Loads the testing namespaces.
     * </p>
     * @param filename
     *            the file name
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    static void loadNamesapces(String filename) throws Exception {
        ConfigManager.getInstance().add(filename);
    }
}
