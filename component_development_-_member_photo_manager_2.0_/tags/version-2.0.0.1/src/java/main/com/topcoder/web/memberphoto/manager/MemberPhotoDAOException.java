/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.web.memberphoto.manager;

import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * This exception is the exception raised from operations performed over the persistence. This exception wraps
 * exceptions raised from persistence and from usage of the JPA Hibernate utilities.
 * </p>
 * <p>
 * Thread Safety: This exception is not thread safe because parent exception is not thread safe. The application
 * should handle this exception in a thread-safe manner.
 * </p>
 *
 * @author Mafy, cyberjag
 * @version 1.0
 */
public class MemberPhotoDAOException extends MemberPhotoManagementException {

    /**
     * Generated serial version id.
     */
    private static final long serialVersionUID = -6257788819469510371L;

    /**
     * Constructs a new <code>MemberPhotoDAOException</code> instance with the given message.
     *
     * @param message
     *            the exception message
     */
    public MemberPhotoDAOException(String message) {
        super(message);
    }

    /**
     * Constructs a new <code>MemberPhotoDAOException</code> exception with the given message and cause.
     *
     * @param message
     *            the exception message
     * @param cause
     *            the exception cause
     */
    public MemberPhotoDAOException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new <code>MemberPhotoDAOException</code> exception with the given message and data.
     *
     * @param message
     *            the exception message
     * @param data
     *            the additional exception data
     */
    public MemberPhotoDAOException(String message, ExceptionData data) {
        super(message, data);
    }

    /**
     * Constructs a new <code>MemberPhotoDAOException</code> exception with the given message, cause, and data.
     *
     * @param message
     *            the exception message
     * @param cause
     *            the exception cause
     * @param data
     *            the additional exception data
     */
    public MemberPhotoDAOException(String message, Throwable cause, ExceptionData data) {
        super(message, cause, data);
    }
}
