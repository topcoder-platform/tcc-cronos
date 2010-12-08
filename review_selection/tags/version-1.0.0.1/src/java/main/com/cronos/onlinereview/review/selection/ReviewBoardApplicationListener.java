/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.review.selection;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.management.project.ReviewApplication;

/**
 * <p>
 * This interface is used for the cases when the review positions are not filled at the completion of the review
 * registration phase. This listener listens for review registration events and tests if the reviewer applications are
 * sufficient in such an event, in which case it closes the review registration phase. This component provides the
 * DefaultReviewBoardApplicationListener as an implementation of this interface.
 * </p>
 *
 * <p>
 * Thread Safety: Implementation of this interface should be thread-safe.
 * </p>
 *
 * @author Wendell, xianwenchen
 * @version 1.0
 */
public interface ReviewBoardApplicationListener {
    /**
     * Handle the review registration event.
     *
     * @param application the review application for the review registration event.
     * @throws IllegalArgumentException if given application is null.
     * @throws IllegalStateException if any property not configured properly.
     * @throws ReviewBoardApplicationListenerException if any error occurs.
     */
    public void applicationReceived(ReviewApplication application) throws ReviewBoardApplicationListenerException;

    /**
     * Configures this instance with use of the given configuration object.
     *
     * @param config the configuration object
     * @throws IllegalArgumentException if config is null
     * @throws ReviewBoardApplicationListenerConfigurationException if some error occurred when initializing an
     *             instance using the given configuration
     */
    public void configure(ConfigurationObject config);
}
