/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.tc.distance.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all Accuracy test cases.</p>
 *
 * @author TopCoder
 * @version 1.0
 */
public class AccuracyTests extends TestCase {

    public static Test suite() {
        final TestSuite suite = new TestSuite();
       
        suite.addTestSuite(XmlDistanceGeneratorTests.class);
        suite.addTestSuite(WeightedAverageWeightingTest.class);
        suite.addTestSuite(OverlapDistanceCalculatorTests.class);
        suite.addTestSuite(RatingDistanceCalculatorTests.class);
        suite.addTestSuite(GeographicalDistanceCalculatorTests.class);
        suite.addTestSuite(FlatFileMemberDataAccessTests.class);
        suite.addTestSuite(EqualWeightingTest.class);
        
        return suite;
    }

}
