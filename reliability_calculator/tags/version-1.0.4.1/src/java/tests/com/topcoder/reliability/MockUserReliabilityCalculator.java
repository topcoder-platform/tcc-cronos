/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reliability;

import java.util.List;

import com.topcoder.reliability.impl.UserProjectReliabilityData;
import com.topcoder.reliability.impl.UserReliabilityCalculationException;
import com.topcoder.reliability.impl.calculators.BaseUserReliabilityCalculator;

/**
 * <p>
 * A mock implementation of BaseUserReliabilityCalculator. Used for testing.
 * </p>
 *
 * @author sparemax
 * @version 1.0
 */
public class MockUserReliabilityCalculator extends BaseUserReliabilityCalculator {
    /**
     * <p>
     * Represents the flag indicates whether an exception should be thrown in getConnection().
     * </p>
     */
    private boolean throwException;

    /**
     * <p>
     * Constructs a new instance of <code>MockUserReliabilityCalculator</code>.
     * </p>
     */
    public MockUserReliabilityCalculator() {
        // Empty
    }

    /**
     * <p>
     * Calculates the reliability after projects are resolved. This method should update
     * UserProjectParticipationData#reliabilityAfterResolution property for all elements in the input list.
     * </p>
     *
     * @param reliabilityData
     *            the list with user project reliability data.
     *
     * @throws UserReliabilityCalculationException
     *             if some error occurred when calculating the reliability rating.
     */
    @Override
    protected void calculateReliabilityAfterProjects(List<UserProjectReliabilityData> reliabilityData)
        throws UserReliabilityCalculationException {

        if (throwException) {
            throw new UserReliabilityCalculationException("UserReliabilityCalculationException for testing.");
        }

    }

    /**
     * <p>
     * Sets the flag indicates whether an exception should be thrown.
     * </p>
     */
    public void throwException() {
        this.throwException = true;
    }
}
