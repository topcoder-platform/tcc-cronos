/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable.latetracker;

import com.topcoder.configuration.ConfigurationObject;

import com.topcoder.management.deliverable.latetracker.retrievers.LateDeliverablesRetrieverImpl;
import com.topcoder.management.phase.PhaseManager;
import com.topcoder.management.project.ProjectManager;

import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogFactory;

import junit.framework.TestCase;

/**
 * Unit tests for <code>{@link Helper}</code> class.
 *
 * <p>
 * <em>Change in 1.1:</em>
 * <ol>
 * <li>Updated tests for parseLong.</li>
 * </ol>
 * </p>
 *
 * @author myxgyy, sparemax
 * @version 1.1
 */
public class HelperTests extends TestCase {
    /**
     * The <code>{@link Log}</code> instance used for testing.
     */
    private Log log;

    /**
     * The <code>ConfigurationObject</code> instance used for testing.
     */
    private ConfigurationObject config;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     *
     * @throws Exception
     *             throws exception if any.
     */
    protected void setUp() throws Exception {
        // Get logger
        log = LogFactory.getLog("my_logger");
        config = BaseTestCase.getConfigurationObject("config/LateDeliverablesRetrieverImpl.xml",
            LateDeliverablesRetrieverImpl.class.getName());

        BaseTestCase.addConfig();
    }

    /**
     * <p>
     * Tears down the test environment.
     * </p>
     *
     * @throws Exception
     *             throws exception if any.
     */
    protected void tearDown() throws Exception {
        BaseTestCase.clearNamespace();
    }

    /**
     * <p>
     * Tests failure of <code>checkState(Object, String, Log, String)</code> method with
     * <code>value</code> is <code>null</code>.<br>
     * <code>IllegalStateException</code> is expected.
     * </p>
     */
    public void test_checkState_Null() {
        try {
            Helper.checkState(null, "name", log, "name");

            fail("IllegalStateException is expected.");
        } catch (IllegalStateException e) {
            // Good
        }
    }

    /**
     * <p>
     * Tests accuracy of <code>checkState(Object, String, Log, String)</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void test_checkState() {
        Helper.checkState(new Object(), "name", log, "name");

        // Good
    }

    /**
     * <p>
     * Tests accuracy of <code>getPropertyValue(ConfigurationObject, String, boolean,
     * boolean)</code>
     * method.<br>
     * Result should be correct.
     * </p>
     */
    public void test_getPropertyValue_1() {
        assertEquals("wrong value", "myLogger", Helper.getPropertyValue(config, "loggerName", false,
            false));
    }

    /**
     * <p>
     * Tests accuracy of <code>getPropertyValue(ConfigurationObject, String, boolean,
     * boolean)</code>
     * method.
     * </p>
     * <p>
     * The specified key does not exist when <code>isRquired</code> is
     * <code>false</code>, <code>null</code> expected.
     * </p>
     */
    public void test_getPropertyValue_2() {
        assertNull("should be null", Helper.getPropertyValue(config, "not exist", false, false));
    }

    /**
     * <p>
     * Tests failure of <code>getPropertyValue(ConfigurationObject, String, boolean,
     * boolean)</code>
     * method.
     * </p>
     * <p>
     * The specified key does not exist when <code>isRquired</code> is <code>true</code>,
     * <code>LateDeliverablesTrackerConfigurationException</code> expected.
     * </p>
     */
    public void test_getPropertyValue_3() {
        try {
            Helper.getPropertyValue(config, "not exist", true, false);

            fail("LateDeliverablesTrackerConfigurationException is expected.");
        } catch (LateDeliverablesTrackerConfigurationException e) {
            // Good
        }
    }

    /**
     * <p>
     * Tests accuracy of <code>getPropertyValue(ConfigurationObject, String, boolean,
     * boolean)</code>
     * method.
     * </p>
     * <p>
     * The value is empty when <code>canBeEmpty</code> is <code>true</code>, empty
     * value expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void test_getPropertyValue_4() throws Exception {
        config.setPropertyValue("loggerName", "");
        assertEquals("empty value expected", "", Helper.getPropertyValue(config, "loggerName", true,
            true));
    }

    /**
     * <p>
     * Tests failure of <code>getPropertyValue(ConfigurationObject, String, boolean,
     * boolean)</code>
     * method.
     * </p>
     * <p>
     * The value is empty when <code>canBeEmpty</code> is <code>false</code>,
     * <code>LateDeliverablesTrackerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void test_getPropertyValue_5() throws Exception {
        config.setPropertyValue("loggerName", "");

        try {
            Helper.getPropertyValue(config, "loggerName", true, false);

            fail("LateDeliverablesTrackerConfigurationException is expected.");
        } catch (LateDeliverablesTrackerConfigurationException e) {
            // Good
        }
    }

    /**
     * <p>
     * Tests failure of <code>getPropertyValue(ConfigurationObject, String, boolean,
     * boolean)</code>
     * method.
     * </p>
     * <p>
     * The value not type of <code>String</code>,
     * <code>LateDeliverablesTrackerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void test_getPropertyValue_6() throws Exception {
        config.setPropertyValue("loggerName", new Exception());

        try {
            Helper.getPropertyValue(config, "loggerName", true, false);

            fail("LateDeliverablesTrackerConfigurationException is expected.");
        } catch (LateDeliverablesTrackerConfigurationException e) {
            // Good
        }
    }

    /**
     * <p>
     * Tests accuracy of <code>getChildConfig(ConfigurationObject, String)</code>
     * method.<br>
     * Result should be correct.
     * </p>
     */
    public void test_getChildConfig_1() {
        assertNotNull("should not be null", Helper.getChildConfig(config, "objectFactoryConfig"));
    }

    /**
     * <p>
     * Tests failure of <code>getChildConfig(ConfigurationObject, String)</code> method.
     * </p>
     * <p>
     * The child config does not exist,
     * <code>LateDeliverablesTrackerConfigurationException</code> expected.
     * </p>
     */
    public void test_getChildConfig_2() {
        try {
            Helper.getChildConfig(config, "not exist");

            fail("LateDeliverablesTrackerConfigurationException is expected.");
        } catch (LateDeliverablesTrackerConfigurationException e) {
            // Good
        }
    }

    /**
     * <p>
     * Tests accuracy of <code>createObjectFactory(ConfigurationObject)</code> method.
     * </p>
     */
    public void test_createObjectFactory() {
        assertNotNull("should not be null", Helper.createObjectFactory(config));
    }

    /**
     * <p>
     * Tests accuracy of
     * <code>createObject(ConfigurationObject, ObjectFactory, String, Class)</code>
     * method.<br>
     * Object should be created correctly.
     * </p>
     */
    public void test_createObject_1() {
        assertNotNull("should not be null", Helper.createObject(config, Helper
            .createObjectFactory(config), "projectManagerKey", ProjectManager.class));
    }

    /**
     * <p>
     * Tests failure of
     * <code>createObject(ConfigurationObject, ObjectFactory, String, Class)</code>
     * method.
     * </p>
     * <p>
     * The object created is not desired type,
     * <code>LateDeliverablesTrackerConfigurationException</code> expected.
     * </p>
     */
    public void test_createObject_2() {
        try {
            Helper.createObject(config, Helper.createObjectFactory(config), "projectManagerKey",
                PhaseManager.class);

            fail("LateDeliverablesTrackerConfigurationException is expected.");
        } catch (LateDeliverablesTrackerConfigurationException e) {
            // Good
        }
    }

    /**
     * <p>
     * Tests accuracy of <code>parseLong(String, String, long)</code> method.
     * </p>
     *
     * <p>
     * <em>Change in 1.1:</em>
     * <ol>
     * <li>Updated.</li>
     * </ol>
     * </p>
     */
    public void test_parseLong_1() {
        assertEquals("should be correct", 1, Helper.parseLong("1", "name", 1));
    }

    /**
     * <p>
     * Tests accuracy of <code>parseLong(String, String, long)</code> method.
     * </p>
     *
     * <p>
     * <em>Change in 1.1:</em>
     * <ol>
     * <li>Updated.</li>
     * </ol>
     * </p>
     */
    public void test_parseLong_2() {
        assertEquals("should be correct", 0, Helper.parseLong("0", "name", 0));
    }

    /**
     * <p>
     * Tests failure of <code>parseLong(String, String, long)</code> method.
     * </p>
     * <p>
     * The value can not be parsed to long,
     * <code>LateDeliverablesTrackerConfigurationException</code> expected.
     * </p>
     *
     * <p>
     * <em>Change in 1.1:</em>
     * <ol>
     * <li>Updated.</li>
     * </ol>
     * </p>
     */
    public void test_parseLong_Invalid1() {
        try {
            Helper.parseLong("1xxx", "name", 1);

            fail("LateDeliverablesTrackerConfigurationException is expected.");
        } catch (LateDeliverablesTrackerConfigurationException e) {
            // Good
        }
    }

    /**
     * <p>
     * Tests failure of <code>parseLong(String, String, long)</code> method.
     * </p>
     * <p>
     * The value is less than minValue,
     * <code>LateDeliverablesTrackerConfigurationException</code> expected.
     * </p>
     *
     * <p>
     * <em>Change in 1.1:</em>
     * <ol>
     * <li>Updated.</li>
     * </ol>
     * </p>
     */
    public void test_parseLong_Invalid2() {
        try {
            Helper.parseLong("0", "name", 1);

            fail("LateDeliverablesTrackerConfigurationException is expected.");
        } catch (LateDeliverablesTrackerConfigurationException e) {
            // Good
        }
    }

    /**
     * <p>
     * Tests accuracy of <code>logEntrance(Log, String, String[], Object[])</code>
     * method.
     * </p>
     * <p>
     * Log should be done.
     * </p>
     */
    public void test_logEntrance_1() {
        Helper.logEntrance(log, "name", new String[] {"name1"}, new Object[] {"value1"});
        // log to console
    }

    /**
     * <p>
     * Tests accuracy of <code>logEntrance(Log, String, String[], Object[])</code>
     * method.
     * </p>
     * <p>
     * The log is null, no log done.
     * </p>
     */
    public void test_logEntrance_2() {
        Helper.logEntrance(null, "name", new String[] {"name1"}, new Object[] {"value1"});
    }

    /**
     * <p>
     * Tests accuracy of <code>logExit(Log, String, Object, long)</code> method.
     * </p>
     * <p>
     * Log should be done.
     * </p>
     */
    public void test_logExit_1() {
        Helper.logExit(log, "name", "value", System.currentTimeMillis());
        // log to console
    }

    /**
     * <p>
     * Tests accuracy of <code>logExit(Log, String, Object, long)</code> method.
     * </p>
     * <p>
     * The log is null, no log done.
     * </p>
     */
    public void test_logExit_2() {
        Helper.logExit(null, "name", "value", System.currentTimeMillis());
    }

    /**
     * <p>
     * Tests accuracy of <code>logException(Log, String, T)</code> method.
     * </p>
     * <p>
     * Log should be done.
     * </p>
     */
    public void test_logException_1() {
        Helper.logException(log, "name", new Exception("message"));
        // log to console
    }

    /**
     * <p>
     * Tests accuracy of <code>logException(Log, String, T)</code> method.
     * </p>
     * <p>
     * The log is null, no log done.
     * </p>
     */
    public void test_logException_2() {
        Helper.logException(null, "name", new Exception("message"));
    }

    /**
     * <p>
     * Tests accuracy of <code>logInfo(Log, String)</code> method.
     * </p>
     * <p>
     * Log should be done.
     * </p>
     */
    public void test_logInfo_1() {
        Helper.logInfo(log, "name");
        // log to console
    }

    /**
     * <p>
     * Tests accuracy of <code>logInfo(Log, String)</code> method.
     * </p>
     * <p>
     * The log is null, no log done.
     * </p>
     */
    public void test_logInfo_2() {
        Helper.logInfo(null, "name");
    }
}
