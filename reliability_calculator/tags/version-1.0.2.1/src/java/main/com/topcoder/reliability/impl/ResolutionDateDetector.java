/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reliability.impl;

import com.topcoder.reliability.Configurable;

/**
 * <p>
 * This interface represents a resolution date detector that provides a method for detecting a resolution date for the
 * specified user project participation data instance. This interface extends Configurable interface to support
 * configuration via Configuration API component.
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> Implementations of this interface are not required to be thread safe.
 * </p>
 *
 * @author saarixx, sparemax
 * @version 1.0
 */
public interface ResolutionDateDetector extends Configurable {
    /**
     * <p>
     * Detects the reliability resolution date for the specified project. The result is stored in resolutionDate
     * property of the input UserProjectParticipationData instance. If the resolution date cannot be detected at this
     * point of time, null is set to resolutionDate property.
     * </p>
     *
     * @param data
     *            the user project participation data for which resolution date must be detected.
     *
     * @throws IllegalArgumentException
     *             if data is <code>null</code>.
     * @throws IllegalStateException
     *             if this detector was not properly configured (is not thrown by implementation that doesn't require
     *             any configuration parameters).
     * @throws ResolutionDateDetectionException
     *             if some error occurred when detecting a resolution date.
     */
    public void detect(UserProjectParticipationData data) throws ResolutionDateDetectionException;
}
