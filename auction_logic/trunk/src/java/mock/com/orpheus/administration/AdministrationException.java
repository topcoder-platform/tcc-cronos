/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration;

import com.topcoder.util.errorhandling.BaseException;

/**
 * <p>
 * This class is referenced from the Auction Logic component but the Administration Logic component
 * where it is defined is still under development, so default implementation is used here. After the
 * Administration Logic will be released this class can be removed.
 * </p>
 * <p>
 * <b>The documentation is left as it was in the development distribution of the Administration
 * Logic component.</b>
 * </p>
 * <p>
 * This class acts as the base class for all custom exceptions in this component such as
 * ConfigurationException and ServiceLocatorException. In addition it is also thrown to indicate
 * errors such as remote exception.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AdministrationException extends BaseException {

    /**
     * Constructs an exception with given error message. <br/> Impl Notes: call super(message).
     * @param message error message.
     */
    public AdministrationException(String message) {
        super(message);
    }

    /**
     * Constructs an exception with given error message and cause. <br/> Impl Notes: call
     * super(message,cause).
     * @param message error message.
     * @param cause the cause.
     */
    public AdministrationException(String message, Throwable cause) {
        super(message, cause);
    }
}
