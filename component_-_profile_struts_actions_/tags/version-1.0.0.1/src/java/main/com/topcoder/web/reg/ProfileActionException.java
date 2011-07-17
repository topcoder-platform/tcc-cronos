/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg;

import com.topcoder.web.common.TCWebException;

/**
 * <p>
 * This exception is thrown by the execute method and any helper methods in all actions if there is any error while
 * executing the operations.
 * </p>
 * <p>
 * Thread Safety: This class is not thread-safe because the base class is not thread-safe.
 * </p>
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
@SuppressWarnings("serial")
public class ProfileActionException extends TCWebException {

    /**
     * <p>
     * Default constructor.
     * </p>
     */
    public ProfileActionException() {
        super();
    }

    /**
     * <p>
     * Constructor taking a string message.
     * </p>
     * @param message - the message of the exception
     */
    public ProfileActionException(String message) {
        super(message);
    }

    /**
     * <p>
     * Constructor taking a nested exception.
     * </p>
     * @param cause the nested exception
     */
    public ProfileActionException(Throwable cause) {
        super(cause);
    }

    /**
     * <p>
     * Constructor taking a nested exception and a string.
     * </p>
     * @param message the message of this exception
     * @param cause the nested exception
     */
    public ProfileActionException(String message, Throwable cause) {
        super(message, cause);
    }
}
