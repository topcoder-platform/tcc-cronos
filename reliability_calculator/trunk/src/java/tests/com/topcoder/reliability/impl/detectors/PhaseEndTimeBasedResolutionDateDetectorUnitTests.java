/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reliability.impl.detectors;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import java.util.Date;

import junit.framework.JUnit4TestAdapter;

import org.junit.Before;
import org.junit.Test;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.defaults.DefaultConfigurationObject;
import com.topcoder.reliability.ReliabilityCalculatorConfigurationException;
import com.topcoder.reliability.TestsHelper;
import com.topcoder.reliability.impl.UserProjectParticipationData;

/**
 * <p>
 * Unit tests for {@link PhaseEndTimeBasedResolutionDateDetector} class.
 * </p>
 *
 * @author sparemax
 * @version 1.0
 */
public class PhaseEndTimeBasedResolutionDateDetectorUnitTests {
    /**
     * <p>
     * Represents the <code>PhaseEndTimeBasedResolutionDateDetector</code> instance used in tests.
     * </p>
     */
    private PhaseEndTimeBasedResolutionDateDetector instance;

    /**
     * <p>
     * Represents the configuration object used in tests.
     * </p>
     */
    private ConfigurationObject config;

    /**
     * <p>
     * Represents the user project participation data used in tests.
     * </p>
     */
    private UserProjectParticipationData data;

    /**
     * <p>
     * Represents the date used in tests.
     * </p>
     */
    private Date date1;

    /**
     * <p>
     * Represents the date used in tests.
     * </p>
     */
    private Date date2;

    /**
     * <p>
     * Represents the date used in tests.
     * </p>
     */
    private Date date3;

    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(PhaseEndTimeBasedResolutionDateDetectorUnitTests.class);
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

        instance = new PhaseEndTimeBasedResolutionDateDetector();
        instance.configure(config);

        date1 = new Date(System.currentTimeMillis() + 10);
        date2 = new Date(System.currentTimeMillis() + 100);
        date3 = new Date(System.currentTimeMillis() + 1000);

        data = new UserProjectParticipationData();
        data.setSubmissionPhaseStatusId(3L);
        data.setResolutionDate(new Date());
        data.setSubmissionPhaseEnd(date1);
        data.setScreeningPhaseEnd(date2);
        data.setAppealsResponsePhaseEnd(date3);
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>PhaseEndTimeBasedResolutionDateDetector()</code>.<br>
     * Instance should be correctly created.
     * </p>
     */
    @Test
    public void testCtor() {
        instance = new PhaseEndTimeBasedResolutionDateDetector();

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
     * Accuracy test for the method <code>detect(UserProjectParticipationData data)</code>.<br>
     * The result should be correct.
     * </p>
     */
    @Test
    public void test_detect_1() {
        instance.detect(data);

        assertSame("'detect' should be correct.", date1, data.getResolutionDate());
    }

    /**
     * <p>
     * Accuracy test for the method <code>detect(UserProjectParticipationData data)</code>.<br>
     * The result should be correct.
     * </p>
     */
    @Test
    public void test_detect_2() {
        data.setSubmissionPhaseEnd(null);

        instance.detect(data);

        assertNull("'detect' should be correct.", data.getResolutionDate());
        assertNotSame("'detect' should be correct.", date1, data.getResolutionDate());
    }

    /**
     * <p>
     * Accuracy test for the method <code>detect(UserProjectParticipationData data)</code>.<br>
     * The result should be correct.
     * </p>
     */
    @Test
    public void test_detect_3() {
        data.setSubmissionStatusId(2L);

        instance.detect(data);

        assertSame("'detect' should be correct.", date2, data.getResolutionDate());
    }

    /**
     * <p>
     * Accuracy test for the method <code>detect(UserProjectParticipationData data)</code>.<br>
     * The result should be correct.
     * </p>
     */
    @Test
    public void test_detect_4() {
        data.setSubmissionStatusId(2L);
        data.setScreeningPhaseEnd(null);

        instance.detect(data);

        assertNull("'detect' should be correct.", data.getResolutionDate());
        assertNotSame("'detect' should be correct.", date2, data.getResolutionDate());
    }

    /**
     * <p>
     * Accuracy test for the method <code>detect(UserProjectParticipationData data)</code>.<br>
     * The result should be correct.
     * </p>
     */
    @Test
    public void test_detect_5() {
        data.setSubmissionStatusId(3L);

        instance.detect(data);

        assertSame("'detect' should be correct.", date3, data.getResolutionDate());
    }

    /**
     * <p>
     * Accuracy test for the method <code>detect(UserProjectParticipationData data)</code>.<br>
     * The result should be correct.
     * </p>
     */
    @Test
    public void test_detect_6() {
        data.setSubmissionStatusId(Long.MAX_VALUE);
        data.setPassedReview(true);

        instance.detect(data);

        assertSame("'detect' should be correct.", date3, data.getResolutionDate());
    }

    /**
     * <p>
     * Accuracy test for the method <code>detect(UserProjectParticipationData data)</code>.<br>
     * The result should be correct.
     * </p>
     */
    @Test
    public void test_detect_7() {
        data.setSubmissionStatusId(3L);
        data.setPassedReview(true);
        data.setAppealsResponsePhaseEnd(null);

        instance.detect(data);

        assertNull("'detect' should be correct.", data.getResolutionDate());
        assertNotSame("'detect' should be correct.", date3, data.getResolutionDate());
    }

    /**
     * <p>
     * Accuracy test for the method <code>detect(UserProjectParticipationData data)</code>.<br>
     * The result should be correct.
     * </p>
     */
    @Test
    public void test_detect_8() {
        data.setSubmissionStatusId(Long.MAX_VALUE);

        instance.detect(data);

        assertNull("'detect' should be correct.", data.getResolutionDate());
    }

    /**
     * <p>
     * Accuracy test for the method <code>detect(UserProjectParticipationData data)</code>.<br>
     * The result should be correct.
     * </p>
     */
    @Test
    public void test_detect_9() {
        data.setSubmissionPhaseStatusId(Long.MAX_VALUE);

        instance.detect(data);

        assertNull("'detect' should be correct.", data.getResolutionDate());
    }

    /**
     * <p>
     * Failure test for the method <code>detect(UserProjectParticipationData data)</code> with data is
     * <code>null</code>.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_detect_dataNull() {
        data = null;

        instance.detect(data);
    }
}