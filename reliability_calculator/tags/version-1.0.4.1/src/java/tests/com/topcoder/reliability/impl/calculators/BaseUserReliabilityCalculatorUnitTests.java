/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reliability.impl.calculators;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.JUnit4TestAdapter;

import org.junit.Before;
import org.junit.Test;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.defaults.DefaultConfigurationObject;
import com.topcoder.reliability.MockUserReliabilityCalculator;
import com.topcoder.reliability.ReliabilityCalculatorConfigurationException;
import com.topcoder.reliability.TestsHelper;
import com.topcoder.reliability.impl.UserProjectParticipationData;
import com.topcoder.reliability.impl.UserProjectReliabilityData;
import com.topcoder.reliability.impl.UserReliabilityCalculationException;

/**
 * <p>
 * Unit tests for {@link BaseUserReliabilityCalculator} class.
 * </p>
 *
 * @author sparemax
 * @version 1.0
 */
public class BaseUserReliabilityCalculatorUnitTests {
    /**
     * <p>
     * Represents the <code>BaseUserReliabilityCalculator</code> instance used in tests.
     * </p>
     */
    private BaseUserReliabilityCalculator instance;

    /**
     * <p>
     * Represents the configuration object used in tests.
     * </p>
     */
    private ConfigurationObject config;

    /**
     * <p>
     * Represents the projects used in tests.
     * </p>
     */
    private List<UserProjectParticipationData> projects;

    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(BaseUserReliabilityCalculatorUnitTests.class);
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
        config.setPropertyValue("historyLength", "2");

        instance = new MockUserReliabilityCalculator();
        instance.configure(config);

        Date date1 = new Date(System.currentTimeMillis() + 100);
        Date date2 = new Date(System.currentTimeMillis() + 200);
        Date date3 = new Date(System.currentTimeMillis() + 300);
        Date date4 = new Date(System.currentTimeMillis() + 400);
        Date date5 = new Date(System.currentTimeMillis() + 500);
        Date date6 = new Date(System.currentTimeMillis() + 600);

        projects = new ArrayList<UserProjectParticipationData>();
        projects.add(getData(false, new Date(), new Date()));
        projects.add(getData(true, date1, date2));
        projects.add(getData(false, date3, date4));
        projects.add(getData(true, date3, date4));
        projects.add(getData(true, date5, date6));
        projects.add(getData(false, date5, date6));
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>BaseUserReliabilityCalculator()</code>.<br>
     * Instance should be correctly created.
     * </p>
     */
    @Test
    public void testCtor() {
        instance = new MockUserReliabilityCalculator();

        assertNull("'log' should be correct.", TestsHelper.getField(instance, "log"));
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
     * Accuracy test for the method <code>calculate(List&lt;UserProjectParticipationData&gt; projects)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_calculate_1() throws Exception {
        instance = new UniformUserReliabilityCalculator();
        instance.configure(config);

        List<UserProjectReliabilityData> res = instance.calculate(projects);

        UserProjectReliabilityData data = res.get(0);

        assertEquals("'calculate' should be correct.",
            1.0, data.getReliabilityAfterResolution(), 0.001);
        assertNull("'calculate' should be correct.", data.getReliabilityBeforeResolution());
        assertNull("'calculate' should be correct.", data.getReliabilityOnRegistration());

        // After resolution
        double[] expected1 = new double[] {0.5, 0.5, 1.0, 0.5};
        // Before resolution
        double[] expected2 = new double[] {1.0, 0.5, 0.5, 1.0};
        // On registration
        double[] expected3 = new double[] {1.0, 1.0, 0.5, 0.5};

        for (int i = 1; i < res.size(); i++) {
            data = res.get(i);

            assertEquals("'calculate' should be correct.",
                expected1[i - 1], data.getReliabilityAfterResolution(), 0.001);

            assertEquals("'calculate' should be correct.",
                expected2[i - 1], data.getReliabilityBeforeResolution(), 0.001);

            assertEquals("'calculate' should be correct.",
                expected3[i - 1], data.getReliabilityOnRegistration(), 0.001);
        }
    }

    /**
     * <p>
     * Accuracy test for the method <code>calculate(List&lt;UserProjectParticipationData&gt; projects)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_calculate_2() throws Exception {
        projects.clear();
        projects.add(getData(false, new Date(), new Date()));

        instance = new UniformUserReliabilityCalculator();
        instance.configure(config);

        List<UserProjectReliabilityData> res = instance.calculate(projects);

        assertEquals("'calculate' should be correct.", 0, res.size());
    }

    /**
     * <p>
     * Failure test for the method <code>calculate(List&lt;UserProjectParticipationData&gt; projects)</code> with
     * projects is <code>null</code>.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_calculate_projectsNull() throws Exception {
        projects = null;

        instance.calculate(projects);
    }

    /**
     * <p>
     * Failure test for the method <code>calculate(List&lt;UserProjectParticipationData&gt; projects)</code> with
     * projects is empty.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_calculate_projectsEmpty() throws Exception {
        projects.clear();

        instance.calculate(projects);
    }

    /**
     * <p>
     * Failure test for the method <code>calculate(List&lt;UserProjectParticipationData&gt; projects)</code> with
     * projects contains <code>null</code>.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_calculate_projectsContainsNull() throws Exception {
        projects.add(null);

        instance.calculate(projects);
    }

    /**
     * <p>
     * Failure test for the method <code>calculate(List&lt;UserProjectParticipationData&gt; projects)</code> with
     * projects contains an element with resolutionDate is <code>null</code>.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_calculate_resolutionDateNull() throws Exception {
        projects.get(0).setResolutionDate(null);

        instance.calculate(projects);
    }

    /**
     * <p>
     * Failure test for the method <code>calculate(List&lt;UserProjectParticipationData&gt; projects)</code> with
     * projects contains an element with userRegistrationDate is <code>null</code>.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_calculate_userRegistrationDateNull() throws Exception {
        projects.get(0).setUserRegistrationDate(null);

        instance.calculate(projects);
    }

    /**
     * <p>
     * Failure test for the method <code>calculate(List&lt;UserProjectParticipationData&gt; projects)</code> with
     * this calculator was not properly configured.<br>
     * <code>IllegalStateException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalStateException.class)
    public void test_calculate_NotConfigured() throws Exception {
        instance = new UniformUserReliabilityCalculator();

        instance.calculate(projects);
    }

    /**
     * <p>
     * Failure test for the method <code>calculate(List&lt;UserProjectParticipationData&gt; projects)</code> with an
     * error occurred when calculating the reliability rating.<br>
     * <code>UserReliabilityCalculationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = UserReliabilityCalculationException.class)
    public void test_calculate_Error() throws Exception {
        ((MockUserReliabilityCalculator) instance).throwException();

        instance.calculate(projects);
    }

    /**
     * <p>
     * Accuracy test for the method <code>getLog()</code>.<br>
     * The result should be correct.
     * </p>
     */
    @Test
    public void test_getLog() {
        assertNotNull("'getLog' should be correct.", instance.getLog());
    }

    /**
     * <p>
     * Creates an instance of UserProjectParticipationData with given values.
     * </p>
     *
     * @param passedReview
     *            the value indicating whether the user's submission passed review.
     * @param userRegistrationDate
     *            the user registration date.
     * @param resolutionDate
     *            the resolution date.
     *
     * @return the created UserProjectParticipationData instance.
     */
    private static UserProjectParticipationData getData(boolean passedReview, Date userRegistrationDate,
        Date resolutionDate) {
        UserProjectParticipationData data = new UserProjectParticipationData();

        data.setPassedReview(passedReview);
        data.setResolutionDate(resolutionDate);
        data.setUserRegistrationDate(userRegistrationDate);

        return data;
    }
}