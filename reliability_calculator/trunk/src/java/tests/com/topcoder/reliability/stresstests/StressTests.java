/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reliability.stresstests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This class contains all Stress tests for this component.
 * </p>
 * @author sokol
 * @version 1.0
 */
public class StressTests extends TestCase {

    /**
     * <p>
     * Creates suite that aggregates all Stress tests for this component.
     * </p>
     * @return Test suite that aggregates all Stress tests for this component
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTestSuite(BaseUserReliabilityCalculatorStressTest.class);
        suite.addTestSuite(DatabaseReliabilityDataPersistenceStressTest.class);
        suite.addTestSuite(ReliabilityCalculatorImplStressTest.class);
        suite.addTestSuite(UniformUserReliabilityCalculatorStressTest.class);
        suite.addTestSuite(UserProjectParticipationDataResolutionDateBasedComparatorStressTest.class);
        suite.addTestSuite(WeightedUserReliabilityCalculatorStressTest.class);
        return suite;
    }
}
