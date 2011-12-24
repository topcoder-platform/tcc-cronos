/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.payments.amazonfps;

import com.topcoder.util.errorhandling.BaseRuntimeException;
import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * The <code>AmazonFlexiblePaymentSystemComponentConfigurationException</code> exception is thrown by classes and
 * implementations of interfaces defined in this component when some configuration specific error occurs while
 * initializing a class instance.
 * </p>
 *
 * <strong>Thread Safety:</strong> This class is not thread safe because its base class is not thread safe.
 *
 * @author saarixx, KennyAlive
 * @version 1.0
 */
@SuppressWarnings("serial")
public class AmazonFlexiblePaymentSystemComponentConfigurationException extends BaseRuntimeException {
    /**
     * Creates a new instance of <code>AmazonFlexiblePaymentSystemComponentConfigurationException</code> with the
     * specified detail message.
     *
     * @param message
     *          the detail message
     */
    public AmazonFlexiblePaymentSystemComponentConfigurationException(String message) {
        super(message);
    }

    /**
     * Creates a new instance of <code>AmazonFlexiblePaymentSystemComponentConfigurationException</code> with the
     * specified detail message and cause.
     *
     * @param message
     *          the detail message
     * @param cause
     *          the cause of this exception
     */
    public AmazonFlexiblePaymentSystemComponentConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates a new instance of <code>AmazonFlexiblePaymentSystemComponentConfigurationException</code> with the
     * specified detail message and exception data.
     *
     * @param message
     *          the detail message
     * @param data
     *          the exception data
     */
    public AmazonFlexiblePaymentSystemComponentConfigurationException(String message, ExceptionData data) {
        super(message, data);
    }

    /**
     * Creates a new instance of <code>AmazonFlexiblePaymentSystemComponentConfigurationException</code> with the
     * specified detail message, cause and exception data.
     *
     * @param message
     *          the detail message
     * @param cause
     *          the cause of this exception
     * @param data
     *          the exception data
     */
    public AmazonFlexiblePaymentSystemComponentConfigurationException(String message, Throwable cause,
        ExceptionData data) {
        super(message, cause, data);
    }
}
