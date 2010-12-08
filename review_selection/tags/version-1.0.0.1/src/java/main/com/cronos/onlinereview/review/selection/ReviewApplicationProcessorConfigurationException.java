/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.review.selection;

import com.topcoder.util.errorhandling.BaseRuntimeException;
import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * This exception is thrown if any error occurs while initializing instance of ReviewApplicationProcessor. The
 * ReviewApplicationProcessor and DefaultReviewApplicationProcessor will all throw this exception.
 * </p>
 *
 * <p>
 * Thread Safety: This class is not thread-safe because the base class is not thread-safe.
 * </p>
 *
 * @author Wendell, xianwenchen
 * @version 1.0
 */
public class ReviewApplicationProcessorConfigurationException extends BaseRuntimeException {
    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = -429113645105401638L;

    /**
     * Create a new instance of ReviewApplicationProcessorConfigurationException with error message.
     *
     * @param message the error message
     */
    public ReviewApplicationProcessorConfigurationException(String message) {
        super(message);
    }

    /**
     * Create a new instance of ReviewApplicationProcessorConfigurationException with error message and cause.
     *
     * @param message the error message
     * @param cause the cause of the exception
     */
    public ReviewApplicationProcessorConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Create a new instance of ReviewApplicationProcessorConfigurationException with error message and exception
     * data.
     *
     * @param message the error message
     * @param data the exception data
     */
    public ReviewApplicationProcessorConfigurationException(String message, ExceptionData data) {
        super(message, data);
    }

    /**
     * Create a new instance of ReviewApplicationProcessorConfigurationException with error message, cause and
     * exception data.
     *
     * @param message the error message
     * @param cause the cause of the exception
     * @param data the exception data
     */
    public ReviewApplicationProcessorConfigurationException(String message, Throwable cause, ExceptionData data) {
        super(message, cause, data);
    }
}
