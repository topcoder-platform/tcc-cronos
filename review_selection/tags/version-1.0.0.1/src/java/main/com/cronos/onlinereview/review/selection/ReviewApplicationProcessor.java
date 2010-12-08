/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.review.selection;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.management.project.ReviewApplication;

/**
 * <p>
 * This interface is used by RatingBasedSelectionAlgorithm class to process the reviewer selection result. This
 * component provides the DefaultReviewApplicationProcessor as an implementation of this interface.
 * </p>
 *
 * <p>
 * Thread Safety: Implementation of this interface should be thread-safe.
 * </p>
 *
 * @author Wendell, xianwenchen
 * @version 1.0
 */
public interface ReviewApplicationProcessor {
    /**
     * Process the assigned application to update reviewer statistics.
     *
     * @param applications an array of application to be processed.
     * @throws IllegalArgumentException if application is null/empty, or application contains null element, or if
     *             applications contains different projectId or if applications contains duplicate reviewerId.
     * @throws IllegalStateException if any property not configured properly.
     * @throws ReviewApplicationProcessorException if any error occurs.
     */
    public void updateReviewAssignmentStatistics(ReviewApplication[] applications)
        throws ReviewApplicationProcessorException;

    /**
     * Process the unassigned application to update reviewer statistics.
     *
     * @param applications an array of application to be processed.
     * @throws IllegalArgumentException if application is null/empty, or application contains null element, or if
     *             applications contains different projectId or if applications contains duplicate reviewerId.
     * @throws IllegalStateException if any property not configured properly.
     * @throws ReviewApplicationProcessorException if any error occurs.
     */
    public void updateUnassignedReviewersStatistics(ReviewApplication[] applications)
        throws ReviewApplicationProcessorException;

    /**
     * Configures this instance with use of the given configuration object.
     *
     * @param config the configuration object
     * @throws IllegalArgumentException if config is null
     * @throws ReviewApplicationProcessorConfigurationException if some error occurred when initializing an instance
     *             using the given configuration
     */
    public void configure(ConfigurationObject config);
}
