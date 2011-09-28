package com.topcoder.accounting.service;

import com.topcoder.util.errorhandling.BaseCriticalException;
import com.topcoder.util.errorhandling.ExceptionData;

/**
 * This exception is the top-level application exception in this application. All other application exceptions in that
 * class will extend it. extends BaseCriticalException.
 */
public class BillingCostServiceException extends BaseCriticalException {
    /**
     * #### Purpose Creates a new exception instance with this error message. #### Parameters message - error message
     * @param message
     */
    public BillingCostServiceException(String message) {
        super(message);
    }

    /**
     * #### Purpose Creates a new exception instance with this error message and cause of error. #### Parameters message
     * - error message cause - cause of error
     * @param message
     * @param cause
     */
    public BillingCostServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * #### Purpose Creates a new exception instance with this error message and any additional data to attach to the
     * exception. #### Parameters message - error message data - additional data to attach to the exception
     * @param message
     * @param data
     */
    public BillingCostServiceException(String message, ExceptionData data) {
        super(message, data);
    }

    /**
     * #### Purpose Creates a new exception instance with this error message, cause of error, and any additional data to
     * attach to the exception. #### Parameters message - error message cause - cause of error data - additional data to
     * attach to the exception
     * @param message
     * @param cause
     * @param data
     */
    public BillingCostServiceException(String message, Throwable cause, ExceptionData data) {
        super(message, cause, data);
    }
}
