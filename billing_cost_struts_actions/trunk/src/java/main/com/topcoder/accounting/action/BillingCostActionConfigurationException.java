/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.action;

import com.topcoder.accounting.service.BillingCostConfigurationException;

/**
 * <p>
 * This is thrown if any configuration error occurs for the actions of this
 * component.
 * </p>
 * <p>
 * <strong> Thread Safety:</strong> This class is not thread-safe because the
 * base class is not thread-safe.
 * </p>
 *
 * @author mekanizumu, TCSDEVELOPER
 * @version 1.0
 */
@SuppressWarnings("serial")
public class BillingCostActionConfigurationException extends
        BillingCostConfigurationException {
    /**
     * Create a new exception with error message.
     *
     * @param message the error message
     */
    public BillingCostActionConfigurationException(String message) {
        super(message);
    }

    /**
     * Create a new exception with error message and inner cause.
     *
     * @param message the error message
     * @param cause inner cause
     */
    public BillingCostActionConfigurationException(String message,
            Throwable cause) {
        super(message, cause);
    }
}
