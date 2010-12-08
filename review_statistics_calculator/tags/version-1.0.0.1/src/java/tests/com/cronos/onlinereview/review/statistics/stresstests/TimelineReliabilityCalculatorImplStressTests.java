/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.review.statistics.stresstests;

import java.text.SimpleDateFormat;
import java.util.Date;

import junit.framework.TestCase;

import com.cronos.onlinereview.review.statistics.impl.TimelineReliabilityCalculatorImpl;
import com.topcoder.configuration.ConfigurationObject;

/**
 * Stress test for class TimelineReliabilityCalculatorImpl.
 *
 * @author extra
 * @version 1.0
 */
public class TimelineReliabilityCalculatorImplStressTests extends TestCase {

    /**
     * Test method calculateReliability for stress.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCalculateReliability_Stress() throws Exception {
        TimelineReliabilityCalculatorImpl calculator = new TimelineReliabilityCalculatorImpl();
        ConfigurationObject config = StressTestHelper.load("config.xml").getChild(
                "com.cronos.onlinereview.review.statistics.impl.ReviewerStatisticsCalculator").getChild(
                "timelineReliabilityCalculatorConfig");

        calculator.configure(config);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date base = sdf.parse("2010-10-25 00:00:00");
        Date time = sdf.parse("2010-10-25 12:30:00");

        Date[] deadlines = new Date[] {base, base, base, base };
        Date[] committed = new Date[] {base, time, base, base };
        long[] phaseTypeIds = new long[] {3, 13, 14, 17 };

        for (int i = 0; i < 10; i++) {
            double result = calculator.calculateReliability(deadlines, committed, phaseTypeIds);
            assertEquals("reliablility", 0.99, result);
        }

    }
}
