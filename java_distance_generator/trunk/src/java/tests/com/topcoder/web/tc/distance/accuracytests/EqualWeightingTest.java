/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.tc.distance.accuracytests;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import com.topcoder.web.tc.distance.DistanceType;
import com.topcoder.web.tc.distance.weighting.EqualWeighting;

/**
 * <p>
 * This is a unit tests for class <code>EqualWeighting</code>.
 * </p>
 *
 * @author aksonov
 * @version 1.0
 */
public class EqualWeightingTest extends TestCase {

    /** EqualWeighting instance that will be tested. */
    private EqualWeighting weighting = new EqualWeighting();

    /** Distances map. */
    private Map<DistanceType, Double> distances = new HashMap<DistanceType, Double>();

    /**
     * <p>
     * Tests method weightDistance.
     * </p>
     */
    public void testWeightDistance() {
        distances.put(DistanceType.RATING, 0.5);
        distances.put(DistanceType.GEOGRAPHICAL, 0.25);

        double expected = 0.375;
        assertEquals("failed to weight distance", expected, weighting
                .weightDistance(distances));
    }
}
