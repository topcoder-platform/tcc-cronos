/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.review.statistics;

import com.topcoder.management.review.data.Review;

/**
 * <p>
 * This interface represents a coverage calculator that can calculate coverage coefficients for reviewers by the given
 * review score cards. This interface extends Configurable interface to support configuration via Configuration API
 * component.
 * </p>
 *
 * <p>
 * <b>Thread Safety</b>: Implementations of this interface are not required to be thread safe when entities passed to
 * them are used by the caller in thread safe manner. It's assumed that configure() method will be called just once
 * right after instantiation, before calling any business methods.
 * </p>
 *
 * @author saarixx, TCSDEVELOPER
 * @version 1.0
 */
public interface CoverageCalculator extends Configurable {
    /**
     * Calculates the coverage coefficients for all secondary reviewers of a single contest.
     *
     * @param reviews the reviews with evaluation details (outer array - reviews for each reviewer, inner array -
     *            reviews of one reviewer for all submissions)
     *
     * @throws IllegalArgumentException if reviews is null or contains null, lengths of all inner arrays are not
     *             equal, inner array contains null
     * @throws IllegalStateException if calculator was not configured properly via configure() method
     * @throws CoverageCalculationException if some other error occurred
     *
     * @return the calculated coverage coefficients (not null; number of elements is equal to reviews.length; i-th
     *         element corresponds to i-th reviewer from the input; each coefficient is in the range [0 .. 1])
     */
    public double[] calculateCoverage(Review[][] reviews) throws CoverageCalculationException;
}
