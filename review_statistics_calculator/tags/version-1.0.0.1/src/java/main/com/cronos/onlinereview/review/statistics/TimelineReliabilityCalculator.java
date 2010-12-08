/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.review.statistics;

import java.util.Date;

/**
 * <p>
 * This interface represents a timeline reliability calculator that can calculate timeline reliability coefficients
 * for reviewers. This interface extends Configurable interface to support configuration via Configuration API
 * component.
 * </p>
 *
 * <p>
 * <b>Thread Safety</b>: Implementations of this interface must be thread safe. It's assumed that configure() method
 * will be called just once right after instantiation, before calling any business methods.
 * </p>
 *
 * @author saarixx, TCSDEVELOPER
 * @version 1.0
 */
public interface TimelineReliabilityCalculator extends Configurable {
    /**
     * Calculates the timeline reliability coefficient for one reviewer in specific contest. Elements with the same
     * indices in the input arrays correspond to the same phase.
     *
     * @param deadlines the deadlines for phases in which reviewer had to submit a required deliverable
     * @param committed the committed dates for the corresponding reviewer deliverables
     * @param phaseTypeIds the IDs of the phase types
     *
     * @throws IllegalArgumentException if any argument is null, any two arguments have different number of elements,
     *             deadlines or committed contains null, phaseTypeIds contains not positive element
     * @throws IllegalStateException if calculator was not configured properly via configure() method
     * @throws TimelineReliabilityCalculationException if some other error occurred
     *
     * @return the calculated reliability coefficient (in the range [0 .. 1])
     */
    public double calculateReliability(Date[] deadlines, Date[] committed, long[] phaseTypeIds)
        throws TimelineReliabilityCalculationException;
}
