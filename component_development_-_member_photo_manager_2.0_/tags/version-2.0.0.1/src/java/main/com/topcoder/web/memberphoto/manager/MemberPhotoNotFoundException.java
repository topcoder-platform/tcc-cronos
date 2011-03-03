/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.web.memberphoto.manager;

import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * This exception signals an issue if the member photo does not exists for the given member id. Has a member id
 * argument in each constructor and a getter for this property.
 * </p>
 *
 * <p>
 * Thread Safety: This exception is not thread safe because parent exception is not thread safe. The application
 * should handle this exception in a thread-safe manner.
 * </p>
 *
 * @author Mafy, cyberjag
 * @version 1.0
 */
public class MemberPhotoNotFoundException extends MemberPhotoManagementException {

    /**
     * Generated serial version id.
     */
    private static final long serialVersionUID = 1694695551664109540L;

    /**
     * <p>
     * This field represents the member's id whose photo was not found. It is initialized during construction by
     * any of the constructors and accessed by the getMemberId() method. It will not be changed after
     * initialization. It can be any value.
     * </p>
     */
    private final long memberId;

    /**
     * Constructs a new <code>MemberPhotoNotFoundException</code> exception with the given message and memberId.
     *
     * @param message
     *            the exception message
     * @param memberId
     *            the memberId
     */
    public MemberPhotoNotFoundException(String message, long memberId) {
        super(message);
        this.memberId = memberId;
    }

    /**
     * Constructs a new <code>MemberPhotoNotFoundException</code> exception with the given message, cause and
     * memberId.
     *
     * @param message
     *            the exception message
     * @param cause
     *            the exception cause
     * @param memberId
     *            the memberId
     */
    public MemberPhotoNotFoundException(String message, Throwable cause, long memberId) {
        super(message, cause);
        this.memberId = memberId;
    }

    /**
     * Constructs a new <code>MemberPhotoNotFoundException</code> exception with the given message, data and
     * memberId.
     *
     * @param message
     *            the exception message
     * @param data
     *            the additional exception data
     * @param memberId
     *            the memberId
     */
    public MemberPhotoNotFoundException(String message, ExceptionData data, long memberId) {
        super(message, data);
        this.memberId = memberId;
    }

    /**
     * Constructs a new <code>MemberPhotoNotFoundException</code> exception with the given message, cause, data and
     * memberId.
     *
     * @param message
     *            the exception message
     * @param cause
     *            the exception cause
     * @param data
     *            the additional exception data
     * @param memberId
     *            the memberId
     */
    public MemberPhotoNotFoundException(String message, Throwable cause, ExceptionData data, long memberId) {
        super(message, cause, data);
        this.memberId = memberId;
    }

    /**
     * Gets the member id.
     *
     * @return the memberId field
     */
    public long getMemberId() {
        return memberId;
    }
}
