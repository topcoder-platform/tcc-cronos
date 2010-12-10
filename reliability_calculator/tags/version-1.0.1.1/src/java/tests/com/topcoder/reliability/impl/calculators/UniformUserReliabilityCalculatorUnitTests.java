/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reliability.impl.calculators;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import junit.framework.JUnit4TestAdapter;

import org.junit.Before;
import org.junit.Test;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.defaults.DefaultConfigurationObject;
import com.topcoder.reliability.ReliabilityCalculatorConfigurationException;
import com.topcoder.reliability.TestsHelper;
import com.topcoder.reliability.impl.UserProjectReliabilityData;

/**
 * <p>
 * Unit tests for {@link UniformUserReliabilityCalculator} class.
 * </p>
 *
 * @author sparemax
 * @version 1.0
 */
public class UniformUserReliabilityCalculatorUnitTests {
    /**
     * <p>
     * Represents the <code>UniformUserReliabilityCalculator</code> instance used in tests.
     * </p>
     */
    private UniformUserReliabilityCalculator instance;

    /**
     * <p>
     * Represents the configuration object used in tests.
     * </p>
     */
    private ConfigurationObject config;

    /**
     * <p>
     * Represents the reliability data used in tests.
     * </p>
     */
    private List<UserProjectReliabilityData> reliabilityData;

    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(UniformUserReliabilityCalculatorUnitTests.class);
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
        config = new DefaultConfigurationObject("root");
        config.setPropertyValue("loggerName", "myLogger");
        config.setPropertyValue("historyLength", "15");

        instance = new UniformUserReliabilityCalculator();
        instance.configure(config);

        reliabilityData = new ArrayList<UserProjectReliabilityData>();
        // 1
        reliabilityData.add(getData(true));
        reliabilityData.add(getData(false));
        reliabilityData.add(getData(true));
        reliabilityData.add(getData(true));
        // 5
        reliabilityData.add(getData(true));
        reliabilityData.add(getData(true));
        reliabilityData.add(getData(true));
        reliabilityData.add(getData(true));
        reliabilityData.add(getData(true));
        // 10
        reliabilityData.add(getData(true));
        reliabilityData.add(getData(true));
        reliabilityData.add(getData(true));
        reliabilityData.add(getData(true));
        reliabilityData.add(getData(true));
        // 15
        reliabilityData.add(getData(true));
        // 16
        reliabilityData.add(getData(true));
        // 17
        reliabilityData.add(getData(true));
        // 18
        reliabilityData.add(getData(false));
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>UniformUserReliabilityCalculator()</code>.<br>
     * Instance should be correctly created.
     * </p>
     */
    @Test
    public void testCtor() {
        instance = new UniformUserReliabilityCalculator();

        assertNull("'log' should be correct.", TestsHelper.getField(instance, "log"));
        assertEquals("'historyLength' should be correct.", 0, TestsHelper.getField(instance, "historyLength"));
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

        assertNotNull("'configure' should be correct.", TestsHelper.getField(instance, "log"));
        assertEquals("'historyLength' should be correct.", 15, TestsHelper.getField(instance, "historyLength"));
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

        assertNull("'configure' should be correct.", TestsHelper.getField(instance, "log"));
        assertEquals("'historyLength' should be correct.", 15, TestsHelper.getField(instance, "historyLength"));
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
     * Failure test for the method <code>configure(ConfigurationObject config)</code> with 'historyLength' is missing.
     * <br>
     * <code>ReliabilityCalculatorConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReliabilityCalculatorConfigurationException.class)
    public void test_configure_historyLengthMissing() throws Exception {
        config.removeProperty("historyLength");

        instance.configure(config);
    }

    /**
     * <p>
     * Failure test for the method <code>configure(ConfigurationObject config)</code> with 'historyLength' is not a
     * string.<br>
     * <code>ReliabilityCalculatorConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReliabilityCalculatorConfigurationException.class)
    public void test_configure_historyLengthNotString() throws Exception {
        config.setPropertyValue("historyLength", 1);

        instance.configure(config);
    }

    /**
     * <p>
     * Failure test for the method <code>configure(ConfigurationObject config)</code> with 'historyLength' is
     * negative.<br>
     * <code>ReliabilityCalculatorConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReliabilityCalculatorConfigurationException.class)
    public void test_configure_historyLengthNegative() throws Exception {
        config.setPropertyValue("historyLength", "-1");

        instance.configure(config);
    }

    /**
     * <p>
     * Failure test for the method <code>configure(ConfigurationObject config)</code> with 'historyLength' is
     * zero.<br>
     * <code>ReliabilityCalculatorConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReliabilityCalculatorConfigurationException.class)
    public void test_configure_historyLengthZero() throws Exception {
        config.setPropertyValue("historyLength", "0");

        instance.configure(config);
    }

    /**
     * <p>
     * Failure test for the method <code>configure(ConfigurationObject config)</code> with 'historyLength' is empty.<br>
     * <code>ReliabilityCalculatorConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReliabilityCalculatorConfigurationException.class)
    public void test_configure_historyLengthEmpty() throws Exception {
        config.setPropertyValue("historyLength", TestsHelper.EMPTY_STRING);

        instance.configure(config);
    }

    /**
     * <p>
     * Accuracy test for the method <code>calculateReliabilityAfterProjects(List&lt;UserProjectReliabilityData&gt;
     * reliabilityData)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_calculateReliabilityAfterProjects_1() throws Exception {
        instance.calculateReliabilityAfterProjects(reliabilityData);

        double[] expected = new double[] {
            1 / 1.0, 1 / 2.0, 2 / 3.0, 3 / 4.0, 4 / 5.0, 5 / 6.0, 6 / 7.0, 7 / 8.0, 8 / 9.0, 9 / 10.0,
            10 / 11.0, 11 / 12.0, 12 / 13.0, 13 / 14.0, 14 / 15.0, 14 / 15.0, 15 / 15.0, 14 / 15.0
        };

        for (int i = 0; i < reliabilityData.size(); i++) {
            assertEquals("'calculateReliabilityAfterProjects' should be correct.", expected[i],
                reliabilityData.get(i).getReliabilityAfterResolution(), 0.001);
        }
    }

    /**
     * <p>
     * Accuracy test for the method <code>calculateReliabilityAfterProjects(List&lt;UserProjectReliabilityData&gt;
     * reliabilityData)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_calculateReliabilityAfterProjects_2() throws Exception {
        reliabilityData.clear();

        instance.calculateReliabilityAfterProjects(reliabilityData);

        assertEquals("'calculateReliabilityAfterProjects' should be correct.", 0, reliabilityData.size());
    }

    /**
     * <p>
     * Failure test for the method <code>calculateReliabilityAfterProjects(List&lt;UserProjectReliabilityData&gt;
     * reliabilityData)</code> with reliabilityData is <code>null</code>.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_calculateReliabilityAfterProjects_reliabilityDataNull() throws Exception {
        reliabilityData = null;

        instance.calculateReliabilityAfterProjects(reliabilityData);
    }

    /**
     * <p>
     * Failure test for the method <code>calculateReliabilityAfterProjects(List&lt;UserProjectReliabilityData&gt;
     * reliabilityData)</code> with reliabilityData contains <code>null</code>.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_calculateReliabilityAfterProjects_reliabilityDataContainsNull() throws Exception {
        reliabilityData.add(null);

        instance.calculateReliabilityAfterProjects(reliabilityData);
    }

    /**
     * <p>
     * Failure test for the method <code>calculateReliabilityAfterProjects(List&lt;UserProjectReliabilityData&gt;
     * reliabilityData)</code> with this calculator was not properly configured.<br>
     * <code>IllegalStateException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalStateException.class)
    public void test_calculateReliabilityAfterProjects_NotConfigured() throws Exception {
        instance = new UniformUserReliabilityCalculator();

        instance.calculateReliabilityAfterProjects(reliabilityData);
    }

    /**
     * <p>
     * Creates an instance of UserProjectReliabilityData with given values.
     * </p>
     *
     * @param reliable
     *            the value indicating whether the user was reliable in this project.
     *
     * @return the created UserProjectReliabilityData instance.
     */
    private static UserProjectReliabilityData getData(boolean reliable) {
        UserProjectReliabilityData data = new UserProjectReliabilityData();

        data.setReliable(reliable);

        return data;
    }
}