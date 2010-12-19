/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reliability.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.sql.Connection;

import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.reliability.ProjectCategoryNotSupportedException;
import com.topcoder.reliability.ReliabilityCalculatorConfigurationException;
import com.topcoder.reliability.TestsHelper;

/**
 * <p>
 * Unit tests for {@link ReliabilityCalculatorImpl} class.
 * </p>
 *
 * @author sparemax
 * @version 1.0
 */
public class ReliabilityCalculatorImplUnitTests {
    /**
     * <p>
     * Represents the <code>ReliabilityCalculatorImpl</code> instance used in tests.
     * </p>
     */
    private ReliabilityCalculatorImpl instance;

    /**
     * <p>
     * Represents the configuration object used in tests.
     * </p>
     */
    private ConfigurationObject config;

    /**
     * <p>
     * Represents the connection used in tests.
     * </p>
     */
    private Connection connection;

    /**
     * <p>
     * Represents the project category id used in tests.
     * </p>
     */
    private long projectCategoryId = 2;


    /**
     * <p>
     * Represents the update current reliability flag used in tests.
     * </p>
     */
    private boolean updateCurrentReliability = true;

    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(ReliabilityCalculatorImplUnitTests.class);
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
        connection = TestsHelper.getConnection();
        TestsHelper.clearDB(connection);
        TestsHelper.loadDB(connection);

        config = TestsHelper.getConfig().getChild("reliabilityCalculator1").getChild("config");

        instance = new ReliabilityCalculatorImpl();
        instance.configure(config);
    }

    /**
     * <p>
     * Cleans up the unit tests.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @After
    public void tearDown() throws Exception {
        TestsHelper.clearDB(connection);
        TestsHelper.closeConnection(connection);

        connection = null;
        instance = null;
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>ReliabilityCalculatorImpl()</code>.<br>
     * Instance should be correctly created.
     * </p>
     */
    @Test
    public void testCtor() {
        instance = new ReliabilityCalculatorImpl();

        assertNull("'log' should be correct.", TestsHelper.getField(instance, "log"));
        assertNull("'projectCategoryParamsById' should be correct.",
            TestsHelper.getField(instance, "projectCategoryParamsById"));
        assertNull("'reliabilityDataPersistence' should be correct.",
            TestsHelper.getField(instance, "reliabilityDataPersistence"));
        assertNull("'participationDataComparator' should be correct.",
            TestsHelper.getField(instance, "participationDataComparator"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>configure(ConfigurationObject config)</code>.<br>
     * The result should be correct.
     * </p>
     */
    @Test
    public void test_configure_1() {
        instance.configure(config);

        assertNotNull("'log' should be correct.", TestsHelper.getField(instance, "log"));
        assertNotNull("'projectCategoryParamsById' should be correct.",
            TestsHelper.getField(instance, "projectCategoryParamsById"));
        assertNotNull("'reliabilityDataPersistence' should be correct.",
            TestsHelper.getField(instance, "reliabilityDataPersistence"));
        assertNotNull("'participationDataComparator' should be correct.",
            TestsHelper.getField(instance, "participationDataComparator"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>configure(ConfigurationObject config)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_configure_2() throws Exception {
        config.removeProperty("loggerName");

        instance.configure(config);

        assertNull("'log' should be correct.", TestsHelper.getField(instance, "log"));
        assertNotNull("'projectCategoryParamsById' should be correct.",
            TestsHelper.getField(instance, "projectCategoryParamsById"));
        assertNotNull("'reliabilityDataPersistence' should be correct.",
            TestsHelper.getField(instance, "reliabilityDataPersistence"));
        assertNotNull("'participationDataComparator' should be correct.",
            TestsHelper.getField(instance, "participationDataComparator"));
    }

    /**
     * <p>
     * Failure test for the method <code>configure(ConfigurationObject config)</code> with config is <code>null</code>
     * .<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_configure_configNull() {
        config = null;

        instance.configure(config);
    }

    /**
     * <p>
     * Failure test for the method <code>configure(ConfigurationObject config)</code> with 'loggerName' is not a
     * string.<br>
     * <code>ReliabilityCalculatorConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReliabilityCalculatorConfigurationException.class)
    public void test_configure_loggerNameNotString() throws Exception {
        config.setPropertyValue("loggerName", 1);

        instance.configure(config);
    }

    /**
     * <p>
     * Failure test for the method <code>configure(ConfigurationObject config)</code> with 'loggerName' is empty.<br>
     * <code>ReliabilityCalculatorConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReliabilityCalculatorConfigurationException.class)
    public void test_configure_loggerNameEmpty() throws Exception {
        config.setPropertyValue("loggerName", TestsHelper.EMPTY_STRING);

        instance.configure(config);
    }

    /**
     * <p>
     * Failure test for the method <code>configure(ConfigurationObject config)</code> with 'reliabilityStartDate' is
     * missing.<br>
     * <code>ReliabilityCalculatorConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReliabilityCalculatorConfigurationException.class)
    public void test_configure_reliabilityStartDateMissing() throws Exception {
        config.getChild("projectCategoryConfig1")
            .removeProperty("reliabilityStartDate");

        instance.configure(config);
    }

    /**
     * <p>
     * Failure test for the method <code>configure(ConfigurationObject config)</code> with 'reliabilityStartDate' is
     * empty.<br>
     * <code>ReliabilityCalculatorConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReliabilityCalculatorConfigurationException.class)
    public void test_configure_reliabilityStartDateEmpty() throws Exception {
        config.getChild("projectCategoryConfig1")
            .setPropertyValue("reliabilityStartDate", TestsHelper.EMPTY_STRING);

        instance.configure(config);
    }

    /**
     * <p>
     * Failure test for the method <code>configure(ConfigurationObject config)</code> with 'reliabilityStartDate' is
     * invalid.<br>
     * <code>ReliabilityCalculatorConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReliabilityCalculatorConfigurationException.class)
    public void test_configure_reliabilityStartDateInvalid() throws Exception {
        config.getChild("projectCategoryConfig1")
            .setPropertyValue("reliabilityStartDate", "invalid_date");

        instance.configure(config);
    }

    /**
     * <p>
     * Failure test for the method <code>configure(ConfigurationObject config)</code> with 'projectCategoryConfigXXX'
     * is missing.<br>
     * <code>ReliabilityCalculatorConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReliabilityCalculatorConfigurationException.class)
    public void test_configure_projectCategoryConfigMissing() throws Exception {
        config.removeChild("projectCategoryConfig1");
        config.removeChild("projectCategoryConfig2");

        instance = new ReliabilityCalculatorImpl();
        instance.configure(config);
    }

    /**
     * <p>
     * Accuracy test for the method <code>calculate(long projectCategoryId, boolean updateCurrentReliability)</code>.
     * <br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_calculate_1() throws Exception {
        instance.calculate(projectCategoryId, updateCurrentReliability);

        assertEquals("'calculate' should be correct.",
            4, TestsHelper.getRowsCount(connection, "project_reliability"));
        assertEquals("'updateCurrentUserReliability' should be correct.",
            1, TestsHelper.getRowsCount(connection, "user_reliability"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>calculate(long projectCategoryId, boolean updateCurrentReliability)</code>.
     * <br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_calculate_2() throws Exception {
        TestsHelper.clearDB(connection);

        instance.calculate(projectCategoryId, updateCurrentReliability);

        assertEquals("'calculate' should be correct.",
            0, TestsHelper.getRowsCount(connection, "project_reliability"));
        assertEquals("'updateCurrentUserReliability' should be correct.",
            0, TestsHelper.getRowsCount(connection, "user_reliability"));
    }

    /**
     * <p>
     * Failure test for the method <code>calculate(long projectCategoryId, boolean updateCurrentReliability)</code>
     * with projectCategoryId is negative.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_calculate_projectCategoryIdNegative() throws Exception {
        projectCategoryId = -1;

        instance.calculate(projectCategoryId, updateCurrentReliability);
    }

    /**
     * <p>
     * Failure test for the method <code>calculate(long projectCategoryId, boolean updateCurrentReliability)</code>
     * with projectCategoryId is zero.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_calculate_projectCategoryIdZero() throws Exception {
        projectCategoryId = 0;

        instance.calculate(projectCategoryId, updateCurrentReliability);
    }

    /**
     * <p>
     * Failure test for the method <code>calculate(long projectCategoryId, boolean updateCurrentReliability)</code>
     * with this reliability calculator was not properly configured.<br>
     * <code>IllegalStateException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalStateException.class)
    public void test_calculate_NotConfigured() throws Exception {
        instance = new ReliabilityCalculatorImpl();

        instance.calculate(projectCategoryId, updateCurrentReliability);
    }

    /**
     * <p>
     * Failure test for the method <code>calculate(long projectCategoryId, boolean updateCurrentReliability)</code>
     * with an error occurred.<br>
     * <code>ProjectCategoryNotSupportedException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ProjectCategoryNotSupportedException.class)
    public void test_calculate_Error1() throws Exception {
        projectCategoryId = Long.MAX_VALUE;

        instance.calculate(projectCategoryId, updateCurrentReliability);
    }

    /**
     * <p>
     * Failure test for the method <code>calculate(long projectCategoryId, boolean updateCurrentReliability)</code>
     * with an error occurred.<br>
     * <code>ReliabilityDataPersistenceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReliabilityDataPersistenceException.class)
    public void test_calculate_Error2() throws Exception {
        config.getChild("reliabilityDataPersistenceConfig").setPropertyValue("connectionName", "unknown_name");
        instance = new ReliabilityCalculatorImpl();
        instance.configure(config);

        instance.calculate(projectCategoryId, updateCurrentReliability);
    }
}