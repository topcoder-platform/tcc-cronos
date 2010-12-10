/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reliability;

/**
 * <p>
 * This interface represents a reliability calculator that provides a method for calculating all users' reliability
 * ratings for the specified project category and optionally updating current reliability for all users. Where to take
 * the information for calculating the reliability from and where to save the calculated reliability ratings is up to
 * implementations. This interface extends Configurable interface to support configuration via Configuration API
 * component.
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> Implementations of this interface are not required to be thread safe.
 * </p>
 *
 * @author saarixx, sparemax
 * @version 1.0
 */
public interface ReliabilityCalculator extends Configurable {
    /**
     * <p>
     * Calculates the reliability ratings for project category with the specified ID. Optionally updates the current
     * reliability ratings for all users. Where to take the information for calculating the reliability from and where
     * to save the calculated reliability ratings is up to implementations.
     * </p>
     *
     * @param updateCurrentReliability
     *            <code>true</code> if current reliability must be updated for all users; <code>false</code>
     *            otherwise.
     * @param projectCategoryId
     *            the ID of the project category.
     *
     * @throws IllegalArgumentException
     *             if projectCategoryId &lt;= 0.
     * @throws IllegalStateException
     *             if this reliability calculator was not properly configured (is not thrown by implementation that
     *             doesn't require any configuration parameters).
     * @throws ProjectCategoryNotSupportedException
     *             if project category with the given ID is not supported by this reliability calculator.
     * @throws ReliabilityCalculationException
     *             if some other error occurred when calculating or updating the reliability.
     */
    public void calculate(long projectCategoryId, boolean updateCurrentReliability)
        throws ReliabilityCalculationException;
}
