/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous;

import com.topcoder.util.errorhandling.BaseRuntimeException;
import com.topcoder.util.errorhandling.ExceptionData;

/**
 * This exception is thrown by BaseUserCommunityManagementAction and its subclasses when the class is not initialized
 * properly with Spring dependency injection.
 * <p>
 * Thread Safety:
 * This class is not thread safe because its base class is not thread safe.
 *
 * @author saarixx, mumujava
 * @version 1.0
 */
public class UserCommunityManagementInitializationException extends BaseRuntimeException {
    /**
    * <p>
    * The serial version uid.
    * </p>
    */
    private static final long serialVersionUID = -3854329640107117195L;

    /**
     * Creates a new instance of this exception with the given message.
     *
     * @param message the detailed error message of this exception
     */
    public UserCommunityManagementInitializationException(String message) {
        super(message);

    }

    /**
     * Creates a new instance of this exception with the given message and cause.
     *
     * @param message the detailed error message of this exception
     * @param cause the inner cause of this exception
     */
    public UserCommunityManagementInitializationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates a new instance of this exception with the given message and exception data.
     *
     * @param message the detailed error message of this exception
     * @param data the exception data
     */
    public UserCommunityManagementInitializationException(String message, ExceptionData data) {
        super(message, data);
    }

    /**
     * Creates a new instance of this exception with the given message, cause and exception data.
     *
     * @param message the detailed error message of this exception
     * @param cause the inner cause of this exception
     * @param data the exception data
     */
    public UserCommunityManagementInitializationException(String message, Throwable cause, ExceptionData data) {
        super(message, cause, data);
    }
}
