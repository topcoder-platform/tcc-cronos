/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.review.selection;

import com.topcoder.util.errorhandling.BaseCriticalException;
import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * This exception is thrown if any error occurs while processing reviewer selection result. The
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
public class ReviewApplicationProcessorException extends BaseCriticalException {
    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = -7170209991645910014L;

    /**
     * Create a new instance of ReviewApplicationProcessorException with error message.
     *
     * @param message the error message
     */
    public ReviewApplicationProcessorException(String message) {
        super(message);
    }

    /**
     * Create a new instance of ReviewApplicationProcessorException with error message and cause.
     *
     * @param message the error message
     * @param cause the cause of the exception
     */
    public ReviewApplicationProcessorException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Create a new instance of ReviewApplicationProcessorException with error message and exception data.
     *
     * @param message the error message
     * @param data the exception data
     */
    public ReviewApplicationProcessorException(String message, ExceptionData data) {
        super(message, data);
    }

    /**
     * Create a new instance of ReviewApplicationProcessorException with error message, cause and exception data.
     *
     * @param message the error message
     * @param cause the cause of the exception
     * @param data the exception data
     */
    public ReviewApplicationProcessorException(String message, Throwable cause, ExceptionData data) {
        super(message, cause, data);
    }
}
