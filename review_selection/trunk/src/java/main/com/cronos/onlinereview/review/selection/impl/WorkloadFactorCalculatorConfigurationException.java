/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.review.selection.impl;

import com.topcoder.util.errorhandling.BaseRuntimeException;
import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * This exception is thrown if any error occurs while initializing instance of WorkloadFactorCalculator. The
 * WorkloadFactorCalculator and DefaultWorkloadFactorCalculator will all throw this exception.
 * </p>
 *
 * <p>
 * Thread Safety: This class is not thread-safe because the base class is not thread-safe.
 * </p>
 *
 * @author Wendell, xianwenchen
 * @version 1.0
 */
public class WorkloadFactorCalculatorConfigurationException extends BaseRuntimeException {
    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = -8772460824643216974L;

    /**
     * Create a new instance of WorkloadFactorCalculatorConfigurationException with error message.
     *
     * @param message the error message
     */
    public WorkloadFactorCalculatorConfigurationException(String message) {
        super(message);
    }

    /**
     * Create a new instance of WorkloadFactorCalculatorConfigurationException with error message and cause.
     *
     * @param message the error message
     * @param cause the cause of the exception
     */
    public WorkloadFactorCalculatorConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Create a new instance of WorkloadFactorCalculatorConfigurationException with error message and exception data.
     *
     * @param message the error message
     * @param data the exception data
     */
    public WorkloadFactorCalculatorConfigurationException(String message, ExceptionData data) {
        super(message, data);
    }

    /**
     * Create a new instance of WorkloadFactorCalculatorConfigurationException with error message, cause and exception
     * data.
     *
     * @param message the error message
     * @param cause the cause of the exception
     * @param data the exception data
     */
    public WorkloadFactorCalculatorConfigurationException(String message, Throwable cause, ExceptionData data) {
        super(message, cause, data);
    }
}
