/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reliability.accuracytests;

import java.util.List;

import com.topcoder.reliability.impl.UserProjectReliabilityData;
import com.topcoder.reliability.impl.UserReliabilityCalculationException;
import com.topcoder.reliability.impl.calculators.BaseUserReliabilityCalculator;
import com.topcoder.util.log.Log;


/**
 * Mock class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockBaseUserReliabilityCalculator
    extends BaseUserReliabilityCalculator {
    /**
     * <p>
     * Calculates the reliability after projects are resolved. This method should update
     * <code>UserProjectParticipationData#reliabilityAfterResolution</code> property for all elements in the input
     * list.
     * </p>
     *
     * @param reliabilityData the list with user project reliability data.
     * @throws IllegalArgumentException if <code>reliabilityData</code> is <code>null</code> or contains
     *         <code>null</code>.
     * @throws IllegalStateException if this calculator was not properly configured.
     * @throws UserReliabilityCalculationException if some error occurred when calculating the reliability rating.
     */
    protected void calculateReliabilityAfterProjects(
        List<UserProjectReliabilityData> reliabilityData)
        throws UserReliabilityCalculationException {
        if (reliabilityData.size() == 5) {
            reliabilityData.get(0).setReliabilityAfterResolution(1.0);
            reliabilityData.get(1).setReliabilityAfterResolution(1.0);
            reliabilityData.get(2).setReliabilityAfterResolution(0.6);
            reliabilityData.get(3).setReliabilityAfterResolution(0.75);
            reliabilityData.get(4).setReliabilityAfterResolution(0.8);
        }
    }

    public Log getLog() {
        return super.getLog();
    }
}
