/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.ajax;

import com.topcoder.util.errorhandling.BaseException;

/**
 * <p>
 * This exception will be thrown by IMAjaxSupportUtility if any error occurred during loading the
 * configuration values.
 * </p>
 *
 * <p>
 * This class is not thread safe, it is not intended to be used in a multi-thread environment.
 * </p>
 *
 * @author woodjhon, TCSDEVELOPER
 * @version 1.0
 */
public class IMAjaxConfigurationException extends BaseException {

    /**
     * Create the exception with error message.
     *
     *
     * @param msg
     *            the error message
     */
    public IMAjaxConfigurationException(String msg) {
        super(msg);
    }

    /**
     * Create the exception with error message and cause exception.
     *
     *
     * @param msg
     *            the error message
     * @param cause
     *            the cause exception
     */
    public IMAjaxConfigurationException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
