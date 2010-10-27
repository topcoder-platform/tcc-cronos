/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.review.selection;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.management.project.ReviewApplication;

/**
 * <p>
 * This interface defines the contract for different review selection algorithms. It provides method to choose one
 * primary reviewer and several secondary reviewers from a list of review applications. This component provides the
 * RatingBasedSelectionAlgorithm class as an implementation of this interface.
 * </p>
 *
 * <p>
 * Thread Safety: Implementation of this interface should be thread-safe.
 * </p>
 *
 * @author Wendell, xianwenchen
 * @version 1.0
 */
public interface ReviewSelection {
    /**
     * Select primary and secondary reviewers from the given review application list. The reviewer selection result
     * will be returned using the ReviewerSelectionResult object.
     *
     * @param applications an array of ReviewApplication object, from which the reviewer will be selected
     * @return The ReviewerSelectionResult object containing the selection result.
     * @throws IllegalArgumentException if given applications parameter is null/empty or contains null element or if
     *             all the application have the acceptPrimary field set to false, or if the applications array length
     *             is too short (normally there should be at least 3 applications, but this number may be configurable
     *             in the implementation class), or if applications contains different projectId or if applications
     *             contains duplicate reviewerId.
     * @throws IllegalStateException if any property not configured properly.
     * @throws ReviewSelectionException if any error occurs.
     */
    public ReviewSelectionResult selectReviewers(ReviewApplication[] applications) throws ReviewSelectionException;

    /**
     * Configures this instance with use of the given configuration object.
     *
     * @param config the configuration object
     * @throws IllegalArgumentException if config is null
     * @throws ReviewSelectionConfigurationException if some error occurred when initializing an instance using the
     *             given configuration
     */
    public void configure(ConfigurationObject config);
}
