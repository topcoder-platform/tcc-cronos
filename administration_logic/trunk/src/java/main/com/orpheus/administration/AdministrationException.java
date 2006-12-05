/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration;

import com.topcoder.util.errorhandling.BaseException;

/**
 * This class acts as the base class for all custom exceptions in this component such as
 * ConfigurationException and ServiceLocatorException. In addition it is also thrown to
 * indicate errors such as remote exception.
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 *
 */
public class AdministrationException extends BaseException {

    /**
     * <p>Constructs an exception with given error message.</p>
     *
     *
     * @param message error message.
     */
    public AdministrationException(String message) {
        super(message);
    }

    /**
     * <p>Constructs an exception with given error message and cause.</p>
     *
     *
     * @param message error message.
     * @param cause the cause.
     */
    public AdministrationException(String message, Throwable cause) {
        super(message, cause);
    }
}
