/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.memberphoto.servlet.stresstests;

import com.topcoder.util.config.ConfigManager;

import java.util.Iterator;

/**
 * Simple helper with some standard functions.
 * @author orange_cloud
 * @version 1.0
 */
public class TestHelper {
    /**
     * Private constructor to prevent direct instantiation.
     */
    private TestHelper() {
    }

    /**
     * Load default config files in ConfigManager.
     * @throws Exception when it occurs deeper
     */
    public static void loadConfiguration() throws Exception {
        ConfigManager manager = ConfigManager.getInstance();
        manager.add("stresstests/file_upload.xml");
        manager.add("stresstests/MimeTypeAndFileExtensionValidator.xml");
    }

    /**
     * Remove all namespaces from ConfigManager.
     * @throws Exception when it occurs deeper
     */
    public static void clearConfiguration() throws Exception {
        ConfigManager manager = ConfigManager.getInstance();
        for (Iterator<?> it = manager.getAllNamespaces(); it.hasNext();) {
            manager.removeNamespace((String) it.next());
        }
    }
}
