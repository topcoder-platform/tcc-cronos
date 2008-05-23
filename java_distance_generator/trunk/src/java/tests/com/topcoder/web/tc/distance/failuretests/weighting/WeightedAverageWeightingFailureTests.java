/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.tc.distance.failuretests.weighting;

import java.util.HashMap;
import java.util.Map;

import com.topcoder.web.tc.distance.DistanceType;
import com.topcoder.web.tc.distance.weighting.WeightedAverageWeighting;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

/**
 * <p>
 * Failure test cases for WeightedAverageWeighting.
 * </p>
 *
 * @author ivern, TheCois
 * @version 1.0
 */
public class WeightedAverageWeightingFailureTests extends TestCase {
    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(WeightedAverageWeightingFailureTests.class);
    }
    
    /**
     * <p>
     * Tests WeightedAverageWeighting#WeightedAverageWeighting(Map&lt;DistanceType,Integer&gt;)
     * for failure.  It tests the case that when the constructor is called with a null weight map, and expects an
     * IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testConstructorNullWeights() {
        try {
            WeightedAverageWeighting waw = new WeightedAverageWeighting(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // Good.
        }
    }
    
    /**
     * <p>
     * Tests WeightedAverageWeighting#WeightedAverageWeighting(Map&lt;DistanceType,Integer&gt;)
     * for failure.  It tests the case that when the constructor is called with a map containing negative weights,
     * and expects an IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testConstructorNegativeWeights() {
        try {
            Map<DistanceType, Integer> weights = new HashMap<DistanceType, Integer>();
            weights.put(DistanceType.RATING, new Integer(-1));
            WeightedAverageWeighting waw = new WeightedAverageWeighting(weights);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // Good.
        }
    }
    
    /**
     * <p>
     * Tests WeightedAverageWeighting#WeightedAverageWeighting(Map&lt;DistanceType,Integer&gt;)
     * for failure.  It tests the case that when the constructor is called with a map containing weights greater
     * than 100, and expects an IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testConstructorWeightsGreaterThan100() {
        try {
            Map<DistanceType, Integer> weights = new HashMap<DistanceType, Integer>();
            weights.put(DistanceType.RATING, new Integer(101));
            WeightedAverageWeighting waw = new WeightedAverageWeighting(weights);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // Good.
        }
    }
    
    /**
     * <p>
     * Tests WeightedAverageWeighting#WeightedAverageWeighting(Map&lt;DistanceType,Integer&gt;)
     * for failure.  It tests the case that when the constructor is called with a map containing weights with sum greater
     * than 100, and expects an IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testConstructorWeightsAddToMoreThan100() {
        try {
            Map<DistanceType, Integer> weights = new HashMap<DistanceType, Integer>();
            weights.put(DistanceType.RATING, new Integer(50));
            weights.put(DistanceType.OVERLAP, new Integer(51));
            WeightedAverageWeighting waw = new WeightedAverageWeighting(weights);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // Good.
        }
    }
    
    /**
     * <p>
     * Tests WeightedAverageWeighting#weightDistance(Map&lt;DistanceType,Double&gt;)
     * for failure.  It tests the case that when the method is called with a null distance map,
     * and expects an IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testWeightDistanceNullDistances() {
        try {
            Map<DistanceType, Integer> weights = new HashMap<DistanceType, Integer>();
            weights.put(DistanceType.RATING, new Integer(100));
            WeightedAverageWeighting waw = new WeightedAverageWeighting(weights);
            
            waw.weightDistance(null);
            
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // Good.
        }
    }
    
    /**
     * <p>
     * Tests WeightedAverageWeighting#weightDistance(Map&lt;DistanceType,Double&gt;)
     * for failure.  It tests the case that when the method is called with a distance map containing
     * null values, and expects an IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testWeightDistanceNullDistanceValues() {
        try {
            Map<DistanceType, Integer> weights = new HashMap<DistanceType, Integer>();
            weights.put(DistanceType.RATING, new Integer(100));
            WeightedAverageWeighting waw = new WeightedAverageWeighting(weights);
            
            Map<DistanceType, Double> distances = new HashMap<DistanceType, Double>();
            distances.put(DistanceType.RATING, null);
            
            waw.weightDistance(distances);
            
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // Good.
        }
    }
}