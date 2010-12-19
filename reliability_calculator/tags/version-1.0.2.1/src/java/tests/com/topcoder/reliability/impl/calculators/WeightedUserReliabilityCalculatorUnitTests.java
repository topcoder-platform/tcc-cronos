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
 * Unit tests for {@link WeightedUserReliabilityCalculator} class.
 * </p>
 *
 * @author sparemax
 * @version 1.0
 */
public class WeightedUserReliabilityCalculatorUnitTests {
    /**
     * <p>
     * Represents the <code>WeightedUserReliabilityCalculator</code> instance used in tests.
     * </p>
     */
    private WeightedUserReliabilityCalculator instance;

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
        return new JUnit4TestAdapter(WeightedUserReliabilityCalculatorUnitTests.class);
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
        config.setPropertyValues("weights",
            new String[] {"0.82", "0.84", "0.86", "0.88", "0.9", "0.92", "0.94", "0.96", "0.98", "1.0"});

        instance = new WeightedUserReliabilityCalculator();
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
        // 11
        reliabilityData.add(getData(true));
        // 12
        reliabilityData.add(getData(true));
        // 13
        reliabilityData.add(getData(false));
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>WeightedUserReliabilityCalculator()</code>.<br>
     * Instance should be correctly created.
     * </p>
     */
    @Test
    public void testCtor() {
        instance = new WeightedUserReliabilityCalculator();

        assertNull("'log' should be correct.", TestsHelper.getField(instance, "log"));
        assertNull("'weights' should be correct.", TestsHelper.getField(instance, "weights"));
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

        double[] weights = (double[]) TestsHelper.getField(instance, "weights");
        assertEquals("'weights' should be correct.", 10, weights.length);
        assertEquals("'weights' should be correct.", 0.82, weights[0], 0.001);
        assertEquals("'weights' should be correct.", 0.84, weights[1], 0.001);
        assertEquals("'weights' should be correct.", 0.86, weights[2], 0.001);
        assertEquals("'weights' should be correct.", 0.88, weights[3], 0.001);
        assertEquals("'weights' should be correct.", 0.9, weights[4], 0.001);
        assertEquals("'weights' should be correct.", 0.92, weights[5], 0.001);
        assertEquals("'weights' should be correct.", 0.94, weights[6], 0.001);
        assertEquals("'weights' should be correct.", 0.96, weights[7], 0.001);
        assertEquals("'weights' should be correct.", 0.98, weights[8], 0.001);
        assertEquals("'weights' should be correct.", 1.0, weights[9], 0.001);
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

        double[] weights = (double[]) TestsHelper.getField(instance, "weights");
        assertEquals("'weights' should be correct.", 10, weights.length);
        assertEquals("'weights' should be correct.", 0.82, weights[0], 0.001);
        assertEquals("'weights' should be correct.", 0.84, weights[1], 0.001);
        assertEquals("'weights' should be correct.", 0.86, weights[2], 0.001);
        assertEquals("'weights' should be correct.", 0.88, weights[3], 0.001);
        assertEquals("'weights' should be correct.", 0.9, weights[4], 0.001);
        assertEquals("'weights' should be correct.", 0.92, weights[5], 0.001);
        assertEquals("'weights' should be correct.", 0.94, weights[6], 0.001);
        assertEquals("'weights' should be correct.", 0.96, weights[7], 0.001);
        assertEquals("'weights' should be correct.", 0.98, weights[8], 0.001);
        assertEquals("'weights' should be correct.", 1.0, weights[9], 0.001);
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
     * Failure test for the method <code>configure(ConfigurationObject config)</code> with 'weights' is missing.
     * <br>
     * <code>ReliabilityCalculatorConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReliabilityCalculatorConfigurationException.class)
    public void test_configure_weightsMissing() throws Exception {
        config.removeProperty("weights");

        instance.configure(config);
    }

    /**
     * <p>
     * Failure test for the method <code>configure(ConfigurationObject config)</code> with 'weights' is null.
     * <br>
     * <code>ReliabilityCalculatorConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReliabilityCalculatorConfigurationException.class)
    public void test_configure_weightsNull() throws Exception {
        config.setPropertyValues("weights", null);

        instance.configure(config);
    }

    /**
     * <p>
     * Failure test for the method <code>configure(ConfigurationObject config)</code> with 'weights' is empty.
     * <br>
     * <code>ReliabilityCalculatorConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReliabilityCalculatorConfigurationException.class)
    public void test_configure_weightsEmpty() throws Exception {
        config.setPropertyValues("weights", new String[] {});

        instance.configure(config);
    }

    /**
     * <p>
     * Failure test for the method <code>configure(ConfigurationObject config)</code> with 'weights' is not a
     * String[].<br>
     * <code>ReliabilityCalculatorConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReliabilityCalculatorConfigurationException.class)
    public void test_configure_weightsNotStringArray() throws Exception {
        config.setPropertyValues("weights", new Object[] {"0.82", 1.0});

        instance.configure(config);
    }

    /**
     * <p>
     * Failure test for the method <code>configure(ConfigurationObject config)</code> with 'weights' contains
     * negative.<br>
     * <code>ReliabilityCalculatorConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReliabilityCalculatorConfigurationException.class)
    public void test_configure_weightsContainsNegative() throws Exception {
        config.setPropertyValues("weights", new String[] {"0.82", "-1.0"});

        instance.configure(config);
    }

    /**
     * <p>
     * Failure test for the method <code>configure(ConfigurationObject config)</code> with 'weights' contains
     * zero.<br>
     * <code>ReliabilityCalculatorConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReliabilityCalculatorConfigurationException.class)
    public void test_configure_weightsContainsZero() throws Exception {
        config.setPropertyValues("weights", new String[] {"0.82", "0.0"});

        instance.configure(config);
    }

    /**
     * <p>
     * Failure test for the method <code>configure(ConfigurationObject config)</code> with 'weights' contains empty.<br>
     * <code>ReliabilityCalculatorConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReliabilityCalculatorConfigurationException.class)
    public void test_configure_weightsContainsEmpty() throws Exception {
        config.setPropertyValues("weights", new String[] {"0.82", TestsHelper.EMPTY_STRING});

        instance.configure(config);
    }

    /**
     * <p>
     * Failure test for the method <code>configure(ConfigurationObject config)</code> with 'weights' contains invalid.
     * <br>
     * <code>ReliabilityCalculatorConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReliabilityCalculatorConfigurationException.class)
    public void test_configure_weightsContainsInvalid() throws Exception {
        config.setPropertyValues("weights", new String[] {"0.82", "invalid_num"});

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
            0.82 / 0.82,
            (1.66 - 0.84) / 1.66,
            (2.52 - 0.84) / 2.52,
            (3.40 - 0.84) / 3.40,
            (4.30 - 0.84) / 4.30,
            (5.22 - 0.84) / 5.22,
            (6.16 - 0.84) / 6.16,
            (7.12 - 0.84) / 7.12,
            (8.10 - 0.84) / 8.10,
            (9.10 - 0.84) / 9.10,
            (9.10 - 0.82) / 9.10,
            9.10 / 9.10,
            (9.10 - 1.0) / 9.10
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
        instance = new WeightedUserReliabilityCalculator();

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