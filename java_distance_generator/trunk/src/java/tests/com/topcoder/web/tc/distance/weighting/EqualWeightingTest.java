/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.tc.distance.weighting;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import com.topcoder.web.tc.distance.DistanceType;
import com.topcoder.web.tc.distance.DistanceWeighting;

/**
 * <p>
 * Unit test cases for <code>EqualWeighting</code> class.
 * </p>
 *
 * @author romanoTC
 * @version 1.0
 */
public class EqualWeightingTest extends TestCase {

    /**
     * The DistanceWeighting object used during testing.
     */
    private DistanceWeighting weighting;

    /**
     * Instantiates object for testing.
     */
    protected void setUp() {
        weighting = new EqualWeighting();
    }

    /**
     * Releases memory.
     */
    protected void tearDown() {
        weighting = null;
    }

    /**
     * Accuracy test for constructor <br>
     * Target: assert the created object is not null. Results will be asserted later.
     */
    public void testCtor() {
        assertNotNull("Should not be null", weighting);
    }

    /**
     * Accuracy test for method weightDistance <br>
     * Target: Let's have <code>weights = empty</code> and <code>distances = {{ Rating, .50}, { Geographical,
     * .25}}</code><br>
     * Our 'set' of weightings would be (prior to redistribution): <code>{{ Rating, 0}, { Geographical, 0}}</code>
     * <br>
     * The algorithm will redistribute the 50 evenly across the set:
     * <code>{{ Rating, 50 }, { Geographical, 50 }}</code><br>
     * And our final answer would be: <code>(.5 * 50 + .25 * 50) / 100 = .4125</code>.
     */
    public void testWeightDistance() {
        Map<DistanceType, Double> map = new HashMap<DistanceType, Double>();
        map.put(DistanceType.RATING, 0.5);
        map.put(DistanceType.GEOGRAPHICAL, 0.25);

        assertEquals("Invalid distance", 0.375, weighting.weightDistance(map), 1e-6);
    }
}
