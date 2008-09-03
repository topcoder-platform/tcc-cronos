/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.manager.stresstests;

import java.io.File;
import java.util.Date;
import java.util.Iterator;

import com.topcoder.util.config.ConfigManager;


/**
 * The ConfigHelper is used to setup configuration and database for test.
 *
 * @author mayday
 * @version 1.0
 */
public class StressTestHelper {

    /**
     * <p>
     * Represents the start date constant.
     * </p>
     */
    public static final Date STARTDATE = new Date(123456789000L);

    /**
     * <p>
     * Represents the end date constant.
     * </p>
     */
    public static final Date ENDDATE = new Date(987654321000L);

    /**
     * <p>
     * The config file for the project.
     * </p>
     */
    private static final String CONFIG = "test_files/stress_test/DBConnectionFactory.xml";

    /**
     * <p>
     * The private constructor prevents the creation of a new instance.
     * </p>
     */
    private StressTestHelper() {
    }

    /**
     * <p>
     * Add the valid config files for testing.
     * </p>
     * @throws Exception - to JUnit.
     */
    public static void loadConfig() throws Exception {
        clearConfig();

        ConfigManager configManager = ConfigManager.getInstance();
        // configManager.add(new File(CONFIG).getCanonicalPath());
    }

    /**
     * <p>
     * Clear the config.
     * </p>
     * @throws Exception - to JUnit.
     */
    public static void clearConfig() throws Exception {
        ConfigManager configManager = ConfigManager.getInstance();

        for (Iterator iter = configManager.getAllNamespaces(); iter.hasNext();) {
            configManager.removeNamespace((String) iter.next());
        }
    }

}
