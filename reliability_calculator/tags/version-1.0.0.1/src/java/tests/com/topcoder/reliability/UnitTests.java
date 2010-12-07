/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reliability;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.reliability.impl.BaseUserProjectDataUnitTests;
import com.topcoder.reliability.impl.ReliabilityCalculatorImplUnitTests;
import com.topcoder.reliability.impl.ReliabilityDataPersistenceExceptionUnitTests;
import com.topcoder.reliability.impl.ResolutionDateDetectionExceptionUnitTests;
import com.topcoder.reliability.impl.UserProjectParticipationDataUnitTests;
import com.topcoder.reliability.impl.UserProjectReliabilityDataUnitTests;
import com.topcoder.reliability.impl.UserReliabilityCalculationExceptionUnitTests;
import com.topcoder.reliability.impl.calculators.BaseUserReliabilityCalculatorUnitTests;
import com.topcoder.reliability.impl.calculators.UniformUserReliabilityCalculatorUnitTests;
import com.topcoder.reliability.impl.calculators.WeightedUserReliabilityCalculatorUnitTests;
import com.topcoder.reliability.impl.comparators.UserProjectParticipationDataResolutionDateBasedComparatorUnitTests;
import com.topcoder.reliability.impl.detectors.PhaseEndTimeBasedResolutionDateDetectorUnitTests;
import com.topcoder.reliability.impl.persistence.DatabaseReliabilityDataPersistenceUnitTests;

/**
 * <p>
 * This test case aggregates all Unit test cases.
 * </p>
 *
 * @author sparemax
 * @version 1.0
 */
public class UnitTests extends TestCase {
    /**
     * <p>
     * All unit test cases.
     * </p>
     *
     * @return The test suite.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTest(Demo.suite());
        suite.addTest(HelperUnitTests.suite());
        suite.addTest(ReliabilityCalculationUtilityUnitTests.suite());

        suite.addTest(ReliabilityCalculatorImplUnitTests.suite());
        suite.addTest(BaseUserProjectDataUnitTests.suite());
        suite.addTest(UserProjectParticipationDataUnitTests.suite());
        suite.addTest(UserProjectReliabilityDataUnitTests.suite());

        suite.addTest(BaseUserReliabilityCalculatorUnitTests.suite());
        suite.addTest(UniformUserReliabilityCalculatorUnitTests.suite());
        suite.addTest(WeightedUserReliabilityCalculatorUnitTests.suite());

        suite.addTest(DatabaseReliabilityDataPersistenceUnitTests.suite());

        suite.addTest(PhaseEndTimeBasedResolutionDateDetectorUnitTests.suite());

        suite.addTest(UserProjectParticipationDataResolutionDateBasedComparatorUnitTests.suite());

        // Exceptions
        suite.addTest(ReliabilityCalculationExceptionUnitTests.suite());
        suite.addTest(ReliabilityCalculatorConfigurationExceptionUnitTests.suite());
        suite.addTest(ProjectCategoryNotSupportedExceptionUnitTests.suite());
        suite.addTest(ReliabilityDataPersistenceExceptionUnitTests.suite());
        suite.addTest(ResolutionDateDetectionExceptionUnitTests.suite());
        suite.addTest(UserReliabilityCalculationExceptionUnitTests.suite());

        return suite;
    }

}
