/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.tc.distance;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.web.tc.distance.calculators.GeographicalDistanceCalculatorTest;
import com.topcoder.web.tc.distance.calculators.MatchOverlapDistanceCalculatorTest;
import com.topcoder.web.tc.distance.calculators.RatingDistanceCalculatorTest;
import com.topcoder.web.tc.distance.weighting.EqualWeightingTest;
import com.topcoder.web.tc.distance.weighting.WeightedAverageWeightingTest;

/**
 * <p>
 * This test case aggregates all Unit test cases.
 * </p>
 *
 * @author romanoTC
 * @version 1.0
 */
public class UnitTests extends TestCase {
    /**
     * UnitTests for all public methods.
     *
     * @return Test suite
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(DistanceCalculatorExceptionTest.class);
        suite.addTestSuite(DistanceGeneratorExceptionTest.class);
        suite.addTestSuite(DistanceWeightingExceptionTest.class);

        suite.addTestSuite(RatingDistanceCalculatorTest.class);
        suite.addTestSuite(MatchOverlapDistanceCalculatorTest.class);
        suite.addTestSuite(GeographicalDistanceCalculatorTest.class);

        suite.addTestSuite(WeightedAverageWeightingTest.class);
        suite.addTestSuite(EqualWeightingTest.class);

        suite.addTestSuite(DefaultDistanceGeneratorTest.class);

        suite.addTestSuite(Demo.class);

        return suite;
    }

}
