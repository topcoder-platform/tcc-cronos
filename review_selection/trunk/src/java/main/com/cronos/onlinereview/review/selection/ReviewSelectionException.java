/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.review.selection;

import com.topcoder.util.errorhandling.BaseCriticalException;
import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * This exception is thrown if any error occurs while selecting reviewer. The ReviewSelection and
 * RatingBasedSelectionAlgorithm will all throw this exception.
 * </p>
 *
 * <p>
 * Thread Safety: This class is not thread-safe because the base class is not thread-safe.
 * </p>
 *
 * @author Wendell, xianwenchen
 * @version 1.0
 */
public class ReviewSelectionException extends BaseCriticalException {
    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = -3263214783859131550L;

    /**
     * Create a new instance of ReviewSelectionException with error message.
     *
     * @param message the error message
     */
    public ReviewSelectionException(String message) {
        super(message);
    }

    /**
     * Create a new instance of ReviewSelectionException with error message and cause.
     *
     * @param message the error message
     * @param cause the cause of the exception
     */
    public ReviewSelectionException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Create a new instance of ReviewSelectionException with error message and exception data.
     *
     * @param message the error message
     * @param data the exception data
     */
    public ReviewSelectionException(String message, ExceptionData data) {
        super(message, data);
    }

    /**
     * Create a new instance of ReviewSelectionException with error message, cause and exception data.
     *
     * @param message the error message
     * @param cause the cause of the exception
     * @param data the exception data
     */
    public ReviewSelectionException(String message, Throwable cause, ExceptionData data) {
        super(message, cause, data);
    }
}
