/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reliability.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * <p>This test case aggregates all Accuracy test cases.</p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AccuracyTests extends TestCase {
    /**
     * Gets all accuracy test cases.
     * @return all accuracy test cases
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTestSuite(ReliabilityCalculationExceptionAccuracy.class);
        suite.addTestSuite(ReliabilityCalculatorConfigurationExceptionAccuracy.class);
        suite.addTestSuite(ProjectCategoryNotSupportedExceptionAccuracy.class);
        suite.addTestSuite(ReliabilityDataPersistenceExceptionAccuracy.class);
        suite.addTestSuite(UserReliabilityCalculationExceptionAccuracy.class);
        suite.addTestSuite(ResolutionDateDetectionExceptionAccuracy.class);
        suite.addTestSuite(BaseUserProjectDataAccuracy.class);
        suite.addTestSuite(UserProjectReliabilityDataAccuracy.class);
        suite.addTestSuite(UserProjectParticipationDataAccuracy.class);
        suite.addTestSuite(PhaseEndTimeBasedResolutionDateDetectorAccuracy.class);
        suite.addTestSuite(UserProjectParticipationDataResolutionDateBasedComparatorAccuracy.class);
        suite.addTestSuite(BaseUserReliabilityCalculatorAccuracy.class);
        suite.addTestSuite(UniformUserReliabilityCalculatorAccuracy.class);
        suite.addTestSuite(WeightedUserReliabilityCalculatorAccuracy.class);
        suite.addTestSuite(WeightedUserReliabilityCalculatorAccuracy.class);

        suite.addTestSuite(DatabaseReliabilityDataPersistenceAccuracy.class);
        suite.addTestSuite(ReliabilityCalculatorImplAccuracy.class);
        suite.addTestSuite(ReliabilityCalculatorUtilityAccuracy.class);

        return suite;
    }
}
