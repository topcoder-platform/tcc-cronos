/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.tc.distance.weighting;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import com.topcoder.web.tc.distance.DistanceType;

/**
 * <p>
 * Unit test cases for <code>WeightedAverageWeighting</code> class.
 * </p>
 *
 * @author romanoTC
 * @version 1.0
 */
public class WeightedAverageWeightingTest extends TestCase {
    /**
     * Failure test for constructor <br>
     * Cause: weights argument is null. <br>
     * Exception: IllegalArgumentException.
     */
    public void testCtor_NullWeights() {
        try {
            new WeightedAverageWeighting(null);

            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException ex) {
            assertEquals("Exception message is incorrect", "The [weights] argument cannot be null.", ex
                    .getMessage());
        }
    }

    /**
     * Failure test for constructor <br>
     * Cause: weights argument contains null keys. <br>
     * Exception: IllegalArgumentException.
     */
    public void testCtor_NullKeys() {
        Map<DistanceType, Integer> map = new HashMap<DistanceType, Integer>();
        map.put(null, 11);

        try {
            new WeightedAverageWeighting(map);

            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException ex) {
            assertEquals("Exception message is incorrect", "The [weights] argument cannot have null keys.", ex
                    .getMessage());
        }
    }

    /**
     * Failure test for constructor <br>
     * Cause: weights argument contains null values. <br>
     * Exception: IllegalArgumentException.
     */
    public void testCtor_NullValues() {
        Map<DistanceType, Integer> map = new HashMap<DistanceType, Integer>();
        map.put(DistanceType.GEOGRAPHICAL, null);

        try {
            new WeightedAverageWeighting(map);

            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException ex) {
            assertEquals("Exception message is incorrect", "The [weights] argument cannot have null values.", ex
                    .getMessage());
        }
    }

    /**
     * Failure test for constructor <br>
     * Cause: weights argument contains invalid value (< 0). <br>
     * Exception: IllegalArgumentException.
     */
    public void testCtor_NegativeValue() {
        Map<DistanceType, Integer> map = new HashMap<DistanceType, Integer>();
        map.put(DistanceType.GEOGRAPHICAL, -11);

        try {
            new WeightedAverageWeighting(map);

            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException ex) {
            assertEquals("Exception message is incorrect",
                    "The [weights] argument cannot have values < 0 or > 100.", ex.getMessage());
        }
    }

    /**
     * Failure test for constructor <br>
     * Cause: weights argument contains invalid value (> 100). <br>
     * Exception: IllegalArgumentException.
     */
    public void testCtor_LargeValue() {
        Map<DistanceType, Integer> map = new HashMap<DistanceType, Integer>();
        map.put(DistanceType.GEOGRAPHICAL, 101);

        try {
            new WeightedAverageWeighting(map);

            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException ex) {
            assertEquals("Exception message is incorrect",
                    "The [weights] argument cannot have values < 0 or > 100.", ex.getMessage());
        }
    }

    /**
     * Failure test for constructor <br>
     * Cause: weights argument contains value which sum is greater than 100. <br>
     * Exception: IllegalArgumentException.
     */
    public void testCtor_LargeValuesSum() {
        Map<DistanceType, Integer> map = new HashMap<DistanceType, Integer>();
        map.put(DistanceType.GEOGRAPHICAL, 10);
        map.put(DistanceType.OVERLAP, 51);
        map.put(DistanceType.RATING, 41);

        try {
            new WeightedAverageWeighting(map);

            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException ex) {
            assertEquals("Exception message is incorrect",
                    "The [weights] argument cannot have values summing greater than 100.", ex.getMessage());
        }
    }

    /**
     * Accuracy test for constructor <br>
     * Target: assert the created object is not null. Results will be asserted later.
     */
    public void testCtor() {
        assertNotNull("Should not be null", getWeightedAverageWeighting());
    }

    /**
     * Failure test for method weightDistance<br>
     * Cause: distances argument contains null keys. <br>
     * Exception: IllegalArgumentException.
     */
    public void testWeightDistance_NullArgument() {
        WeightedAverageWeighting weighting = getWeightedAverageWeighting();

        try {
            weighting.weightDistance(null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException ex) {
            assertEquals("Exception message is incorrect", "The [distances] argument cannot be null.", ex
                    .getMessage());
        }
    }

    /**
     * Failure test for method weightDistance<br>
     * Cause: distances argument contains null keys. <br>
     * Exception: IllegalArgumentException.
     */
    public void testWeightDistance_NullKeys() {
        WeightedAverageWeighting weighting = getWeightedAverageWeighting();

        Map<DistanceType, Double> map = new HashMap<DistanceType, Double>();
        map.put(null, 1.0);

        try {
            weighting.weightDistance(map);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException ex) {
            assertEquals("Exception message is incorrect", "The [distances] argument cannot have null keys.", ex
                    .getMessage());
        }
    }

    /**
     * Failure test for method weightDistance<br>
     * Cause: distances argument contains null keys. <br>
     * Exception: IllegalArgumentException.
     */
    public void testWeightDistance_NullValues() {
        WeightedAverageWeighting weighting = getWeightedAverageWeighting();

        Map<DistanceType, Double> map = new HashMap<DistanceType, Double>();
        map.put(DistanceType.GEOGRAPHICAL, null);

        try {
            weighting.weightDistance(map);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException ex) {
            assertEquals("Exception message is incorrect", "The [distances] argument cannot have null values.", ex
                    .getMessage());
        }
    }

    /**
     * Accuracy test for method weightDistance <br>
     * Target: assert the result is -1 when map is empty.
     */
    public void testWeightDistance_EmptyMap() {
        WeightedAverageWeighting weighting = getWeightedAverageWeighting();
        Map<DistanceType, Double> map = new HashMap<DistanceType, Double>();

        assertEquals("Invalid distance", -1.0, weighting.weightDistance(map), 1e-6);
    }

    /**
     * Accuracy test for method weightDistance <br>
     * Target: If distance is 0.5, the result will be 0.5.
     */
    public void testWeightDistance_SingleDistance() {
        Map<DistanceType, Integer> weightMap = new HashMap<DistanceType, Integer>();
        weightMap.put(DistanceType.OVERLAP, 40);

        WeightedAverageWeighting weighting = new WeightedAverageWeighting(weightMap);

        Map<DistanceType, Double> map = new HashMap<DistanceType, Double>();
        map.put(DistanceType.RATING, 0.5);

        assertEquals("Invalid distance", 0.5, weighting.weightDistance(map), 1e-6);
    }

    /**
     * Accuracy test for method weightDistance <br>
     * Target: Let's have <code>weights = {{ Rating, 30 }, { Overlap, 20 }}</code> and
     * <code>distances = {{ Rating, .50}, { Geographical,
     * .25}}</code><br>
     * Our 'set' of weightings would be (prior to redistribution): <code>{{ Rating, 30}, { Geographical, 0}}</code>
     * <br>
     * The algorithm will redistribute the 70 evenly across the set:
     * <code>{{ Rating, 65 }, { Geographical, 35 }}</code><br>
     * And our final answer would be: <code>(.5 * 65 + .25 * 35) / 100 = .4125</code>.
     */
    public void testWeightDistance() {
        Map<DistanceType, Integer> weightMap = new HashMap<DistanceType, Integer>();
        weightMap.put(DistanceType.OVERLAP, 20);
        weightMap.put(DistanceType.RATING, 30);

        WeightedAverageWeighting weighting = new WeightedAverageWeighting(weightMap);

        Map<DistanceType, Double> map = new HashMap<DistanceType, Double>();
        map.put(DistanceType.RATING, 0.5);
        map.put(DistanceType.GEOGRAPHICAL, 0.25);

        assertEquals("Invalid distance", 0.4125, weighting.weightDistance(map), 1e-6);
    }

    /**
     * Return a constant WeightedAverageWeighting object.
     *
     * @return a constant WeightedAverageWeighting object.
     */
    private static WeightedAverageWeighting getWeightedAverageWeighting() {
        Map<DistanceType, Integer> map = new HashMap<DistanceType, Integer>();
        map.put(DistanceType.GEOGRAPHICAL, 10);
        map.put(DistanceType.OVERLAP, 11);
        map.put(DistanceType.RATING, 21);

        return new WeightedAverageWeighting(map);
    }
}
