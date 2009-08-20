/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio;

import com.topcoder.util.errorhandling.BaseCriticalException;
import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * This exception extends the BaseCriticalException. It is also the parent
 * exception class for all the other custom exceptions in the Studio package.
 * </p>
 *
 * @author fabrizyo, TCSDEVELOPER
 * @version 1.0
 */
public class StudioServiceException extends BaseCriticalException {
    /**
     * <p>
     * Constructor with error message.
     * </p>
     *
     * @param message the error message
     */
    public StudioServiceException(String message) {
        super(message);
    }

    /**
     * <p>
     * Constructor with error message and inner cause.
     * </p>
     *
     * @param message the error message
     * @param cause the cause of this exception
     */
    public StudioServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * <p>
     * Constructor with error message and exception data.
     * </p>
     *
     * @param message the error message
     * @param data the exception data
     */
    public StudioServiceException(String message, ExceptionData data) {
        super(message, data);
    }

    /**
     * <p>
     * Constructor with error message and inner cause and exception data.
     * </p>
     *
     * @param message the error message
     * @param cause the cause of this exception
     * @param data the exception data
     */
    public StudioServiceException(String message, Throwable cause, ExceptionData data) {
        super(message, cause, data);
    }
}
