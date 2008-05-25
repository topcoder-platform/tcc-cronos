/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.web.tc.distance.stresstests;

import java.util.ArrayList;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.topcoder.web.tc.distance.data.FlatFileMemberDataAccess;
import com.topcoder.web.tc.distance.CompetitionType;
import com.topcoder.web.tc.distance.DefaultDistanceGenerator;
import com.topcoder.web.tc.distance.DistanceGenerator;
import com.topcoder.web.tc.distance.calculators.RatingDistanceCalculator;
import com.topcoder.web.tc.distance.calculators.GeographicalDistanceCalculator;
import com.topcoder.web.tc.distance.calculators.MatchOverlapDistanceCalculator;
import com.topcoder.web.tc.distance.DistanceCalculator;
import com.topcoder.web.tc.distance.DistanceType;
import com.topcoder.web.tc.distance.weighting.EqualWeighting;

import junit.framework.TestCase;

/**
 * <p>
 * Stress tests.
 * </p>
 *
 * @author cnettel
 * @version 1.0
 */
public class StressTestCase extends TestCase {
    /**
     * <p>
     * A test instance used in each test.
     * </p>
     */
    private DistanceGenerator instance;

    /**
     * <p>
     * Sets up the test object.
     * </p>
     */
    public void setUp()
    {
        List<DistanceCalculator> calcs =
            new ArrayList<DistanceCalculator>();
        calcs.add(new MatchOverlapDistanceCalculator());
        calcs.add(new GeographicalDistanceCalculator());
        calcs.add(new RatingDistanceCalculator());

        instance = new DefaultDistanceGenerator(new FlatFileMemberDataAccess("test_files/"),
            new EqualWeighting(),
            calcs);
    }

    /**
     * <p>
     * A dictionary of correct strings.
     * </p>
     */
    Map<Long, String> correctStrings = new HashMap<Long, String>();

    /**
     * Helper that makes a XML generation call.
     *
     * @param id The id.
     *
     * @return The XML.
     */
    private String makeCall(long id)
    {
        return instance.generateDistance(id,
            EnumSet.allOf(DistanceType.class),
            EnumSet.allOf(CompetitionType.class));
    }

    /**
     * <p>
     * The number of threads to spawn.
     * </p>
     */
    private static final int ThreadCount = 15;

    /// <summary>
    /// The number of generation runs within each thread.
    /// </summary>
    private static final int InEach = 50;

    /**
     * <p>
     * Actual test runner.
     * </p>
     *
     * @author cnettel
     *
     */
    private class ThreadRunner extends Thread {
        /**
         * <p>
         * Launches the thread.
         * </p>
         */
        public void run() {
            for (int i = 0; i < InEach; i++)
            {
                for (Long id : correctStrings.keySet())
                {
                    assertEquals("Result mismatch for " + id, correctStrings.get(id), makeCall(id));
                }
            }
        }
    }

    /**
     * <p>
     * Test
     * </p>
     */
    public void testStress() throws InterruptedException
    {
        long old = System.currentTimeMillis();

        correctStrings.put((long) 144400, makeCall(144400));
        correctStrings.put((long) 156859, makeCall(156859));
        correctStrings.put((long) 277356, makeCall(277356));
        correctStrings.put((long) 297731, makeCall(297731));
        correctStrings.put((long) 7210680, makeCall(7210680));

        Thread[] threads = new Thread[ThreadCount];
        for (int i = 0; i < ThreadCount; i++)
        {
            threads[i] = new ThreadRunner();
            threads[i].start();
        }

        for (int i = 0; i < ThreadCount; i++)
        {
            threads[i].join();
        }

        System.out.println("Time used: " + (System.currentTimeMillis() - old));
    }
}

