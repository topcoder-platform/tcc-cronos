/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg;

import com.topcoder.util.errorhandling.BaseCriticalException;
import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * This exception is thrown by the ProfileCompletenessRetriever if there is any error while executing the retrieval.
 * </p>
 * <p>
 * Thread Safety: This class is not thread-safe because the base class is not thread-safe.
 * </p>
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
@SuppressWarnings("serial")
public class ProfileCompletenessRetrievalException extends BaseCriticalException {

    /**
     * <p>
     * Creates an instance of the class with a message.
     * </p>
     * @param message the error message
     */
    public ProfileCompletenessRetrievalException(String message) {
        super(message);
    }

    /**
     * <p>
     * Creates an instance of the class with a message and cause.
     * </p>
     * @param message the error message
     * @param cause the error cause
     */
    public ProfileCompletenessRetrievalException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * <p>
     * Creates an instance of the class with a message and the exception data.
     * </p>
     * @param message the error message
     * @param exceptionData the exception data
     */
    public ProfileCompletenessRetrievalException(String message, ExceptionData exceptionData) {
        super(message, exceptionData);
    }

    /**
     * <p>
     * Creates an instance of the class with a message, cause and the exception data.
     * </p>
     * @param message the error message
     * @param cause the error cause
     * @param exceptionData the exception data
     */
    public ProfileCompletenessRetrievalException(String message, Throwable cause, ExceptionData exceptionData) {
        super(message, cause, exceptionData);
    }
}
