/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.tool.accuracytests;

import com.cronos.onlinereview.autoscreening.tool.accuracytests.BaseTestCase;
import com.cronos.onlinereview.autoscreening.tool.ScreeningTool;

import com.topcoder.util.config.ConfigManager;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * <p>Accuracy test cases for <code>ScreeningTool</code> class.</p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ScreeningToolAccuracyTest extends BaseTestCase {
    /**
     * Represents the path of the screener config file.
     */
    protected static final String SCREENER_CONFIG_FILE = "accuracytests/screener.xml";

    /**
     * Represents the path of the object factory config file.
     */
    protected static final String OBJECT_FACTORY_CONFIG_FILE = "accuracytests/object_factory.xml";

    /**
     * Represents the config namespace of the screener.
     */
    protected static final String SCREENER_NAMESPACE = "com.cronos.autoscreening";

    /**
     * Aggregates all tests in this class.
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(ScreeningToolAccuracyTest.class);
    }

    /**
     * Sets up the test environment.
     * @throws Exception
     *             throw any exception to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();
        ConfigManager.getInstance().add(OBJECT_FACTORY_CONFIG_FILE);
        ConfigManager.getInstance().add(SCREENER_CONFIG_FILE);
    }

    /**
     * Clean up the test environment.
     * @throws Exception
     *             throw any exception to JUnit
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * <p>
     * Accuracy test of the method <code>static void main(String[] args)</code>.
     * </p>
     * <p>
     * usage should be printed.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyMain1() throws Exception {
        ScreeningTool.main(new String[] {"-screenerId 23"});
    }

    /**
     * <p>
     * Accuracy test of the method <code>static void main(String[] args)</code>.
     * </p>
     * <p>
     * usage should be printed.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyMain2() throws Exception {
        ScreeningTool.main(new String[] {"-screenerId 23",
                "- Interval 2000a",
                "-configNamespace com.topcoder"});
    }

    /**
     * <p>
     * Accuracy test of the method <code>static void main(String[] args)</code>.
     * </p>
     * <p>
     * perform screening just every 1 second, test the concurrency.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyMain3() throws Exception {

        ScreeningTool.main(new String[] {"-screenerId=2", "-configNamespace=" + SCREENER_NAMESPACE,
            "-interval=1000000"});

        // sleep 30 seconds for the screener to run.
        Thread.sleep(3000 * 10);
    }

    /**
     * <p>
     * Accuracy test of the method <code>static void main(String[] args)</code>.
     * </p>
     * <p>
     * perform screening
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyMain4() throws Exception {

        ScreeningTool.main(new String[] {"-screenerId=2", "-configNamespace=" + SCREENER_NAMESPACE,
            "-interval=1000000"});

        // sleep 30 seconds for the screener to run.
        Thread.sleep(3000 * 10);
    }
}
