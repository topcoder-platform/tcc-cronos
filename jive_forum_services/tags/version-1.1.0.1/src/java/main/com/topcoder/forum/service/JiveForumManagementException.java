/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.forum.service;

import com.topcoder.util.errorhandling.BaseCriticalException;
import com.topcoder.util.errorhandling.ExceptionData;


/**
 * <p>
 * This exception extends the <code>BaseCriticalException</code>. It's the general exception
 * for the whole component, all the other custom exceptions should extend from it.
 * </p>
 *
 * <p>
 * <strong>Thread safety:</strong>
 * This class is not thread safe because its parent class is not thread safe. And exception
 * class is not expected to be used concurrently.
 * </p>
 *
 * @author Standlove, TCSDEVELOPER
 * @version 1.0
 */
public class JiveForumManagementException extends BaseCriticalException {
    /**
     * <p>Serial version uid for the Serializable class.</p>
     */
    private static final long serialVersionUID = -2128907276695026698L;

    /**
     * <p>Creates an instance with the error message.</p>
     *
     * @param message the error message
     */
    public JiveForumManagementException(String message) {
        super(message);
    }

    /**
     * <p>Creates an instance with the error message and inner cause.</p>
     *
     * @param message the error message
     * @param cause the inner exception cause.
     */
    public JiveForumManagementException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * <p>
     * Creates an instance with the error message and the additional data to attach to the
     * critical exception.
     * </p>
     *
     * @param message the error message.
     * @param data the additional data to attach to the exception
     */
    public JiveForumManagementException(String message, ExceptionData data) {
        super(message, data);
    }

    /**
     * <p>
     * Creates an instance with the error message, inner cause, and the additional data to
     * attach to the exception.
     * </p>
     *
     * @param message the error message.
     * @param cause the inner exception cause.
     * @param data The additional data to attach to the exception
     */
    public JiveForumManagementException(String message, Throwable cause,
        ExceptionData data) {
        super(message, cause, data);
    }
}
