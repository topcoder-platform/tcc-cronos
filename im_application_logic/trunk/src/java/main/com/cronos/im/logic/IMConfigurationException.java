/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.logic;

import com.topcoder.util.errorhandling.BaseException;

/**
 * <p>
 * This exception should be thrown when problems with component configuration appear. It encapsulates any
 * possible problems with configuration.
 * </p>
 * <p>
 * Thread safety: this class is thread safe because it is immutable.
 * </p>
 *
 *
 * @author justforplay, TCSDEVELOPER
 * @version 1.0
 */
public class IMConfigurationException extends BaseException {

    /**
     * <p>
     * Constructor which create exception with given text message.
     * </p>
     *
     *
     * @param message
     *            the message to associate with this exception
     */
    public IMConfigurationException(String message) {
        super(message);
    }

    /**
     * <p>
     * Constructor which create exception with given text message and underlying cause.
     * </p>
     *
     *
     * @param message
     *            the message to associate with this exception
     * @param cause
     *            the underlying exception.
     */
    public IMConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
}
