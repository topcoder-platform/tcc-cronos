/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reliability.failuretests;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.reliability.failuretests.impl.ReliabilityCalculatorImplFailureTests;
import com.topcoder.reliability.failuretests.impl.calculators.BaseUserReliabilityCalculatorFailureTests;
import com.topcoder.reliability.failuretests.impl.calculators.UniformUserReliabilityCalculatorFailureTests;
import com.topcoder.reliability.failuretests.impl.calculators.WeightedUserReliabilityCalculatorFailureTests;
import com.topcoder.reliability.failuretests.impl.detectors.PhaseEndTimeBasedResolutionDateDetectorFailureTests;
import com.topcoder.reliability.failuretests.impl.persistence.DatabaseReliabilityDataPersistenceFailureTests;

/**
 * This test case aggregates all failure test cases.
 * 
 * @author Yeung
 * @version 1.0
 */
public class FailureTests extends TestCase {
    /**
     * All unit test cases.
     * 
     * @return The test suite.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTest(new JUnit4TestAdapter(ReliabilityCalculatorImplFailureTests.class));
        
        suite.addTest(new JUnit4TestAdapter(PhaseEndTimeBasedResolutionDateDetectorFailureTests.class));

        suite.addTest(new JUnit4TestAdapter(BaseUserReliabilityCalculatorFailureTests.class));
        suite.addTest(new JUnit4TestAdapter(UniformUserReliabilityCalculatorFailureTests.class));
        suite.addTest(new JUnit4TestAdapter(WeightedUserReliabilityCalculatorFailureTests.class));
        
        suite.addTest(new JUnit4TestAdapter(DatabaseReliabilityDataPersistenceFailureTests.class));
        return suite;
    }

}
