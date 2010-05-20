/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.contest.coo.stresstests;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * This is the helper class used by stress tests.
 *
 * @author yuanyeyuanye, DixonD
 * @version 1.1
 */
public class StressTestHelper {
    /**
     * <p>
     * Project id used for stress test cases.
     * </p>
     */
    public static final int TEST_PROJECT_ID;

    /**
     * This private constructor does nothing. It only exists to prevent direct instantiation.
     */
    private StressTestHelper() {
        // do nothing
    }

    static {
        Properties config = new Properties();
        InputStream configInput = null;
        try {
            configInput = new FileInputStream("test_files/stress/stress_configuration.properties");
            config.load(configInput);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (configInput != null) {
                try {
                    configInput.close();
                } catch (IOException e) {
                    // Ignore.
                }
            }
        }

        TEST_PROJECT_ID = Integer.parseInt(config.getProperty("TEST_PROJECT_ID"));
    }
}