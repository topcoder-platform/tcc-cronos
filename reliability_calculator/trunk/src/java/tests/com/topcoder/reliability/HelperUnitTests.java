/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reliability;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.JUnit4TestAdapter;

import org.junit.Before;
import org.junit.Test;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;
import com.topcoder.util.objectfactory.ObjectFactory;

/**
 * <p>
 * Unit tests for {@link Helper} class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class HelperUnitTests {
    /**
     * <p>
     * Represents the configuration object used in tests.
     * </p>
     */
    private ConfigurationObject config;

    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(HelperUnitTests.class);
    }

    /**
     * <p>
     * Sets up the unit tests.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Before
    public void setUp() throws Exception {
        config = TestsHelper.getConfig();
    }

    /**
     * <p>
     * Tests failure of <code>checkNull(Object value, String name)</code> method with <code>value</code> is
     * <code>null</code>.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_checkNull_Null() {
        Object value = null;

        Helper.checkNull(value, "name");
    }

    /**
     * <p>
     * Tests accuracy of <code>checkNull(Object value, String name)</code> method.<br>
     * Result should be correct.
     * </p>
     */
    @Test
    public void test_checkNull() {
        Object value = new Object();

        Helper.checkNull(value, "name");

        // Good
    }

    /**
     * <p>
     * Tests failure of <code>checkPositive(long value, String name)</code> method with <code>value</code> is
     * negative.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_checkPositive_Negative() {
        long value = -1;

        Helper.checkPositive(value, "name");
    }

    /**
     * <p>
     * Tests accuracy of <code>checkPositive(long value, String name)</code> method.<br>
     * Result should be correct.
     * </p>
     */
    @Test
    public void test_checkPositive() {
        long value = 1;

        Helper.checkPositive(value, "name");

        // Good
    }

    /**
     * <p>
     * Tests failure of <code>checkList(List&lt;T&gt; value, String name)</code> method with <code>value</code> is
     * <code>null</code>.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_checkList_Null() {
        List<Object> value = null;

        Helper.checkList(value, "name");
    }

    /**
     * <p>
     * Tests accuracy of <code>checkList(List&lt;T&gt; value, String name)</code> method.<br>
     * Result should be correct.
     * </p>
     */
    @Test
    public void test_checkList() {
        List<Object> value = new ArrayList<Object>();
        value.add(new Object());

        Helper.checkList(value, "name");

        // Good
    }

    /**
     * <p>
     * Tests failure of <code>checkState(boolean isInvalid, String message)</code> method with <code>isInvalid</code> is
     * <code>true</code>.<br>
     * <code>IllegalStateException</code> is expected.
     * </p>
     */
    @Test(expected = IllegalStateException.class)
    public void test_checkState_Invalid() {
        boolean isInvalid = true;

        Helper.checkState(isInvalid, "name");
    }

    /**
     * <p>
     * Tests accuracy of <code>checkState(boolean isInvalid, String message)</code> method.<br>
     * Result should be correct.
     * </p>
     */
    @Test
    public void test_checkState() {
        boolean isInvalid = false;

        Helper.checkState(isInvalid, "name");

        // Good
    }

    /**
     * <p>
     * Tests accuracy of <code>getLog(ConfigurationObject config)</code> method.<br>
     * Result should be correct.
     * </p>
     */
    @Test
    public void test_getLog() {
        assertNotNull("'getLog' should be correct.", Helper.getLog(config));
    }

    /**
     * <p>
     * Tests accuracy of <code>getChildConfig(ConfigurationObject config, String key)</code> method.<br>
     * Result should be correct.
     * </p>
     */
    @Test
    public void test_getChildConfig() {
        assertNotNull("'getChildConfig' should be correct.", Helper.getChildConfig(config, "objectFactoryConfig"));
    }

    /**
     * <p>
     * Tests accuracy of <code>getProperty(ConfigurationObject config, String key,
     * boolean required)</code> method.<br>
     * Result should be correct.
     * </p>
     */
    @Test
    public void test_getProperty() {
        assertEquals("'getProperty' should be correct.",
            "myLogger", Helper.getProperty(config, "loggerName", false));
    }

    /**
     * <p>
     * Tests accuracy of <code>getPropertyValues(ConfigurationObject config, String key)</code> method.<br>
     * Result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_getPropertyValues() throws Exception {
        assertEquals("'getPropertyValues' should be correct.",
            10, Helper.getPropertyValues(config.getChild("reliabilityCalculator1"), "projectCategoryIds").length);
    }

    /**
     * <p>
     * Tests failure of <code>getPropertyPositiveValues(ConfigurationObject config, String key)</code> method with
     * <code>config</code> is invalid.<br>
     * <code>ReliabilityCalculatorConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReliabilityCalculatorConfigurationException.class)
    public void test_getPropertyPositiveValues_Invalid1() throws Exception {
        config.getChild("reliabilityCalculator1").setPropertyValues("projectCategoryIds", new Object[]{1});

        Helper.getPropertyPositiveValues(config.getChild("reliabilityCalculator1"), "projectCategoryIds");
    }

    /**
     * <p>
     * Tests failure of <code>getPropertyPositiveValues(ConfigurationObject config, String key)</code> method with
     * <code>config</code> is invalid.<br>
     * <code>ReliabilityCalculatorConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReliabilityCalculatorConfigurationException.class)
    public void test_getPropertyPositiveValues_Invalid2() throws Exception {
        config.getChild("reliabilityCalculator1").setPropertyValues("projectCategoryIds", new Object[]{"invalid_num"});

        Helper.getPropertyPositiveValues(config.getChild("reliabilityCalculator1"), "projectCategoryIds");
    }

    /**
     * <p>
     * Tests failure of <code>getPropertyPositiveValues(ConfigurationObject config, String key)</code> method with
     * <code>config</code> is invalid.<br>
     * <code>ReliabilityCalculatorConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReliabilityCalculatorConfigurationException.class)
    public void test_getPropertyPositiveValues_Invalid3() throws Exception {
        config.getChild("reliabilityCalculator1").setPropertyValues("projectCategoryIds", new Object[]{"-1"});

        Helper.getPropertyPositiveValues(config.getChild("reliabilityCalculator1"), "projectCategoryIds");
    }

    /**
     * <p>
     * Tests accuracy of <code>getPropertyPositiveValues(ConfigurationObject config, String key)</code> method.<br>
     * Result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_getPropertyPositiveValues() throws Exception {
        assertEquals("'getPropertyPositiveValues' should be correct.", 10,
            Helper.getPropertyPositiveValues(config.getChild("reliabilityCalculator1"), "projectCategoryIds").length);
    }

    /**
     * <p>
     * Tests accuracy of <code>getObjectFactory(ConfigurationObject config)</code> method.<br>
     * Result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_getObjectFactory() throws Exception {
        assertNotNull("'getObjectFactory' should be correct.", Helper.getObjectFactory(config));
    }

    /**
     * <p>
     * Tests accuracy of <code>createObject(ObjectFactory objectFactory, ConfigurationObject config,
     * String objKey, String configKey)</code> method.<br>
     * Result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_createObject1() throws Exception {
        ObjectFactory objectFactory = Helper.getObjectFactory(config);

        assertNotNull("'createObject' should be correct.",
            Helper.createObject(ReliabilityCalculator.class, objectFactory,
                config.getChild("reliabilityCalculator1"), "key", "config"));
    }

    /**
     * <p>
     * Tests failure of <code>createObject(ObjectFactory objectFactory, ConfigurationObject config,
     * String objKey)</code> method with an error occurred.<br>
     * <code>ReliabilityCalculatorConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReliabilityCalculatorConfigurationException.class)
    public void test_createObject_Error1() throws Exception {
        ObjectFactory objectFactory = Helper.getObjectFactory(config);

        Helper.createObject(String.class, objectFactory, config.getChild("reliabilityCalculator1"), "key");
    }

    /**
     * <p>
     * Tests accuracy of <code>createObject(ObjectFactory objectFactory, ConfigurationObject config,
     * String objKey)</code> method.<br>
     * Result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_createObject2() throws Exception {
        ObjectFactory objectFactory = Helper.getObjectFactory(config);

        assertNotNull("'createObject' should be correct.", Helper.createObject(ReliabilityCalculator.class,
            objectFactory, config.getChild("reliabilityCalculator1"), "key"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>logEntrance(Log logger, String signature,
     * String[] paramNames, Object[] paramValues)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_logEntrance_1() throws Exception {
        Log logger = LogManager.getLog("loggerName");

        Helper.logEntrance(logger, "signature", null, null);
    }

    /**
     * <p>
     * Accuracy test for the method <code>logEntrance(Log logger, String signature,
     * String[] paramNames, Object[] paramValues)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_logEntrance_2() throws Exception {
        Log logger = LogManager.getLog("loggerName");

        Helper.logEntrance(logger, "signature", new String[] {"p1", "p2"}, new String[] {"v1", "v2"});
    }

    /**
     * <p>
     * Accuracy test for the method <code>logEntrance(Log logger, String signature,
     * String[] paramNames, Object[] paramValues)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_logEntrance_3() throws Exception {
        Log logger = null;

        Helper.logEntrance(logger, "signature", new String[] {"p1", "p2"}, new String[] {"v1", "v2"});
    }

    /**
     * <p>
     * Accuracy test for the method <code>logExit(Log logger, String signature,
     * Object[] value, Date enterTimestamp)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_logExit_1() throws Exception {
        Log logger = LogManager.getLog("loggerName");

        Helper.logExit(logger, "signature", null, new Date());
    }

    /**
     * <p>
     * Accuracy test for the method <code>logExit(Log logger, String signature,
     * Object[] value, Date enterTimestamp)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_logExit_2() throws Exception {
        Log logger = LogManager.getLog("loggerName");

        Helper.logExit(logger, "signature", new String[] {"v1", "v2"}, new Date());
    }

    /**
     * <p>
     * Accuracy test for the method <code>logExit(Log logger, String signature,
     * Object[] value, Date enterTimestamp)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_logExit_3() throws Exception {
        Log logger = null;

        Helper.logExit(logger, "signature", new String[] {"v1", "v2"}, new Date());
    }

    /**
     * <p>
     * Accuracy test for the method <code>logException(Log log, String signature, T e, String message)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_logException_1() throws Exception {
        Log logger = LogManager.getLog("loggerName");

        Throwable e = new Exception("Test");
        Helper.logException(logger, "signature", e, "Log message.");
    }

    /**
     * <p>
     * Accuracy test for the method <code>logException(Log log, String signature, T e, String message)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_logException_2() throws Exception {
        Log logger = null;

        Throwable e = new Exception("Test");
        Helper.logException(logger, "signature", e, "Log message.");
    }
}
