/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.review.statistics.accuracytests;

import com.cronos.onlinereview.review.statistics.impl.TimelineReliabilityCalculatorImpl;

import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;


/**
 * Accuracy tests for TimelineReliabilityCalculatorImpl.
 *
 * @author onsky
 * @version 1.0
 */
public class TimelineReliabilityCalculatorImplAccTests extends AccuracyHelper {
    /** Represents the TimelineReliabilityCalculatorImpl instance to test. */
    private TimelineReliabilityCalculatorImpl instance;

    /**
     * <p>Sets up the unit tests.</p>
     *
     * @throws Exception to junit
     */
    @Before
    public void setUp() throws Exception {
        instance = new TimelineReliabilityCalculatorImpl();
    }

    /**
     * <p>Cleans up the unit tests.</p>
     *
     * @throws Exception to junit
     */
    @After
    public void tearDown() throws Exception {
        instance = null;
    }

    /**
     * Accuracy test for method TimelineReliabilityCalculatorImpl.
     */
    @Test
    public void test_TimelineReliabilityCalculatorImpl() {
        assertNotNull(instance);
    }

    /**
     * Accuracy test for method configure.
     *
     * @throws Exception to junit
     */
    @SuppressWarnings("unchecked")
    @Test
    public void test_configure() throws Exception {
        instance.configure(getConfigurationObject("accuracytests/TimelineReliabilityCalculatorImpl.xml",
                "com.cronos.onlinereview.review.statistics.impl.TimelineReliabilityCalculatorImpl"));
        assertEquals("field is not set", 3,
            ((Map<Long, Double>) getPrivateField(instance, "penaltyPerHourByPhaseType")).size());
    }

    /**
     * Accuracy test for method calculateReliability.
     *
     * @throws Exception to junit
     */
    @SuppressWarnings("unchecked")
    @Test
    public void test_calculateReliability() throws Exception {
        instance.configure(getConfigurationObject("accuracytests/TimelineReliabilityCalculatorImpl.xml",
                "com.cronos.onlinereview.review.statistics.impl.TimelineReliabilityCalculatorImpl"));
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        Calendar c3 = Calendar.getInstance();
        c1.set(2010, 9, 28);
        c2.set(2010, 9, 27);
        c3.set(2010, 9, 29);
        Date[] deadlines = new Date[2];
        deadlines[0] = c1.getTime();
        deadlines[1] = c1.getTime();
        Date[] committed = new Date[2];
        committed[0] = c2.getTime();
        committed[1] = c3.getTime();
        long phaseTypeIds[] = new long[]{1, 2};
        double result = instance.calculateReliability(deadlines, committed, phaseTypeIds);
        assertTrue("result is not correct", 0.1 == result);
    }
}
