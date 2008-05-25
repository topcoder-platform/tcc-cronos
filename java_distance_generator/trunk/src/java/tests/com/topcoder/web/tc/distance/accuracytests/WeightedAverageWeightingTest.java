/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.tc.distance.accuracytests;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import com.topcoder.web.tc.distance.DistanceType;
import com.topcoder.web.tc.distance.weighting.WeightedAverageWeighting;

/**
 * <p>
 * Accuracy tests for <code>WeightedAverageWeighting</code>.
 * </p>
 *
 * @author aksonov
 * @version 1.0
 */
public class WeightedAverageWeightingTest extends TestCase {

    /** WeightedAverageWeighting instance that will be tested. */
    private WeightedAverageWeighting weighting;

    /** Weights map. */
    private Map<DistanceType, Integer> weights;

    /** Distances map. */
    private Map<DistanceType, Double> distances;

    /**
     * <p>
     * Set up environment.
     * </p>
     *
     * @throws Exception
     *             exception
     */
    protected void setUp() throws Exception {
        weights = new HashMap<DistanceType, Integer>();
        distances = new HashMap<DistanceType, Double>();

        weighting = new WeightedAverageWeighting(weights);
    }

    /**
     * <p>
     * Tests method weightDistance with sample data from forum
     * </p>
     */
    public void testWeightDistance() {
        weights.put(DistanceType.RATING, 30);
        weights.put(DistanceType.OVERLAP, 20);

        distances.put(DistanceType.RATING, 0.5);
        distances.put(DistanceType.GEOGRAPHICAL, 0.25);

        double expected = 0.4125;

        weighting = new WeightedAverageWeighting(weights);
        assertEquals("failed to weight distance", expected, weighting
                .weightDistance(distances));
    }
}
