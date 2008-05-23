/**
 *
 * Copyright (c) 2008, TopCoder, Inc. All rights reserved
 */
package com.topcoder.web.tc.distance.failuretests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.TestResult;

import com.topcoder.web.tc.distance.failuretests.calculators.GeographicalDistanceCalculatorFailureTests;
import com.topcoder.web.tc.distance.failuretests.calculators.MatchOverlapDistanceCalculatorFailureTests;
import com.topcoder.web.tc.distance.failuretests.calculators.RatingDistanceCalculatorFailureTests;
import com.topcoder.web.tc.distance.failuretests.weighting.WeightedAverageWeightingFailureTests;

/**
 * <p>This test case aggregates all Failure test cases.</p>
 *
 * @author TopCoder
 * @version 1.0
 */
public class FailureTests extends TestCase {

    public static Test suite() {
        final TestSuite suite = new TestSuite();
        
        suite.addTest(DefaultDistanceGeneratorFailureTests.suite());
        
        suite.addTest(GeographicalDistanceCalculatorFailureTests.suite());
        suite.addTest(MatchOverlapDistanceCalculatorFailureTests.suite());
        suite.addTest(RatingDistanceCalculatorFailureTests.suite());
        
        suite.addTest(WeightedAverageWeightingFailureTests.suite());
        
        return suite;
    }

}
