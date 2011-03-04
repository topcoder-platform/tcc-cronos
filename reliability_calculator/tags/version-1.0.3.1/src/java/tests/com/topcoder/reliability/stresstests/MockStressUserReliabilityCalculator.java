/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reliability.stresstests;

import java.util.ArrayList;
import java.util.List;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.reliability.impl.UserProjectParticipationData;
import com.topcoder.reliability.impl.UserProjectReliabilityData;
import com.topcoder.reliability.impl.UserReliabilityCalculationException;
import com.topcoder.reliability.impl.calculators.BaseUserReliabilityCalculator;

/**
 * <p>
 * This is mock stress implementation of BaseUserReliabilityCalculator.
 * </p>
 * @author sokol
 * @version 1.0
 */
public class MockStressUserReliabilityCalculator extends BaseUserReliabilityCalculator {

    /**
     * <p>
     * Represents quantity of all user's project reliability data in result of {@link #calculate(List)}.
     * </p>
     */
    private static final int USER_PROJECT_RELIABILITY_DATA_COUNT = 20;

    /**
     * <p>
     * Represents reliability after resolution constant.
     * </p>
     */
    private static final double RELIABILITY_AFTER_RESOLUTION = 0.7;

    /**
     * <p>
     * Creates an instance of MockStressUserReliabilityCalculator.
     * </p>
     */
    public MockStressUserReliabilityCalculator() {
    }

    /**
     * <p>
     * Do nothing.
     * </p>
     * @param config the configuration
     */
    public void configure(ConfigurationObject config) {
    }

    /**
     * <p>
     * Do nothing.
     * </p>
     * @param reliabilityData the reliability data
     * @throws UserReliabilityCalculationException never
     */
    protected void calculateReliabilityAfterProjects(List < UserProjectReliabilityData > reliabilityData)
        throws UserReliabilityCalculationException {
    }

    /**
     * Returns list of UserProjectReliabilityData with element quantity equals USER_PROJECT_RELIABILITY_DATA_COUNT.
     * @param projects the projects
     * @return List of UserProjectReliabilityData with element quantity equals USER_PROJECT_RELIABILITY_DATA_COUNT
     * @throws UserReliabilityCalculationException never
     */
    public List < UserProjectReliabilityData > calculate(List < UserProjectParticipationData > projects)
        throws UserReliabilityCalculationException {
        List < UserProjectReliabilityData > result = new ArrayList < UserProjectReliabilityData >();
        for (int i = 0; i < USER_PROJECT_RELIABILITY_DATA_COUNT; i++) {
            UserProjectReliabilityData data = new UserProjectReliabilityData();
            data.setReliabilityAfterResolution(RELIABILITY_AFTER_RESOLUTION);
            result.add(data);
        }
        return result;
    }
}
