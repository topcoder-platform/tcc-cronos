/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.web.memberphoto.manager;

import com.topcoder.util.errorhandling.BaseCriticalException;
import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * This exception is the base exception for all exceptions raised from operations performed in this design. This
 * exception wraps exceptions raised from persistence, from usage of the JPA Hibernate utilities or used TopCoder
 * components.
 * </p>
 * <p>
 * This exception is not thread safe because parent exception is not thread safe. The application should handle
 * this exception in a thread-safe manner.
 * </p>
 *
 * @author Mafy, cyberjag
 * @version 1.0
 */
public class MemberPhotoManagementException extends BaseCriticalException {

    /**
     * Generated serial version id.
     */
    private static final long serialVersionUID = -1495478015510822850L;

    /**
     * <p>
     * Constructs a new <code>MemberPhotoManagementException</code> instance with the given message.
     * </p>
     *
     * @param message
     *            the exception message
     */
    public MemberPhotoManagementException(String message) {
        super(message);
    }

    /**
     * <p>
     * Constructs a new <code>MemberPhotoManagementException</code> exception with the given message and cause.
     * </p>
     *
     * @param message
     *            the exception message
     * @param cause
     *            the exception cause
     */
    public MemberPhotoManagementException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * <p>
     * Constructs a new <code>MemberPhotoManagementException</code> exception with the given message and data.
     * </p>
     *
     * @param message
     *            the exception message
     * @param data
     *            the additional exception data
     */
    public MemberPhotoManagementException(String message, ExceptionData data) {
        super(message, data);
    }

    /**
     * <p>
     * Constructs a new <code>MemberPhotoManagementException</code> exception with the given message, cause, and
     * data.
     * </p>
     *
     * @param message
     *            the exception message
     * @param cause
     *            the exception cause
     * @param data
     *            the additional exception data
     */
    public MemberPhotoManagementException(String message, Throwable cause, ExceptionData data) {
        super(message, cause, data);
    }
}
