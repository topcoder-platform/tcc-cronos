/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reliability.impl;

import java.util.List;

import com.topcoder.reliability.Configurable;

/**
 * <p>
 * This interface represents a user reliability calculator that provides a method for calculating reliability ratings
 * for specific user based on his full project participation data. This interface extends Configurable interface to
 * support configuration via Configuration API component.
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> Implementations of this interface are not required to be thread safe.
 * </p>
 *
 * @author saarixx, sparemax
 * @version 1.0
 */
public interface UserReliabilityCalculator extends Configurable {
    /**
     * <p>
     * Calculates reliability for all projects of specific user and project category.
     * </p>
     *
     * @param projects
     *            the project participation data for the user (must be in chronological order).
     *
     * @return the calculated project reliability data (not <code>null</code>, doesn't contain <code>null</code>).
     *
     * @throws IllegalArgumentException
     *             if projects is <code>null</code>/empty, contains <code>null</code> or contains an element with
     *             resolutionDate or userRegistrationDate is <code>null</code>.
     * @throws IllegalStateException
     *             if this calculator was not properly configured (is not thrown by implementation that doesn't
     *             require any configuration parameters).
     * @throws UserReliabilityCalculationException
     *             if some error occurred when calculating the reliability rating.
     */
    public List<UserProjectReliabilityData> calculate(List<UserProjectParticipationData> projects)
        throws UserReliabilityCalculationException;
}
