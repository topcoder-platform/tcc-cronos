/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.user.persistence.stresstests;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.topcoder.util.config.ConfigManager;

/**
 * <p>
 * A helper class that is used for stress test cases.
 * </p>
 *
 * @author Thinfox
 * @version 1.0
 */
class StressTestHelper {

    /**
     * <p>
     * This constructor is private to prevent this class from being instantiated.
     * </p>
     */
    private StressTestHelper() {
        // Empty constructor.
    }

    /**
     * Cleans up the ConfigManager.
     *
     * @throws Exception if fail to clean up
     */
    static void cleanConfiguration() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();

        List namespaces = new ArrayList();

        // iterate through all the namespaces and delete them.
        for (Iterator it = cm.getAllNamespaces(); it.hasNext();) {
            namespaces.add(it.next());
        }

        for (Iterator it = namespaces.iterator(); it.hasNext();) {
            cm.removeNamespace((String) it.next());
        }
    }

    /**
     * Loads the configuration files used for stress tests.
     *
     * @throws Exception if fail to clean up
     */
    static void loadConfiguration() throws Exception {
        cleanConfiguration();

        ConfigManager cm = ConfigManager.getInstance();
        cm.add("test_conf/stress/logging.xml");
        cm.add("test_conf/stress/dbconnectionfactory.xml");
        cm.add("test_conf/stress/simplecache.xml");
        cm.add("test_conf/stress/profiletypes.xml");
        cm.add("test_conf/stress/daofactory.xml");
        cm.add("test_conf/stress/dao.xml");
    }
}
