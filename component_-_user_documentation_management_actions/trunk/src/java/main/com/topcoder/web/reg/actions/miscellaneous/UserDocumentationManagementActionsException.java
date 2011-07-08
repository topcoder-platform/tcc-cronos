/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous;

import com.topcoder.util.errorhandling.BaseException;
import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * This is thrown if any error occurs in the actions of this component. It's thrown by AssignmentDocumentHistoryAction,
 * BaseAction, VisaLetterStatusAction, PaymentHistoryAction, AffirmAffidavitAction, AffidavitHistoryAction.
 * </p>
 * <p>
 * <strong>Thread Safety:</strong> This class is not thread-safe because the base class is not thread-safe.
 * </p>
 * @author mekanizumu, TCSDEVELOPER
 * @version 1.0
 */
public class UserDocumentationManagementActionsException extends BaseException {
    /**
     * Generated Serial Version id.
     */
    private static final long serialVersionUID = -4505774449282484597L;

    /**
     * <p>
     * Create an instance of the class with a message.
     * </p>
     * @param message
     *            the error message
     */
    public UserDocumentationManagementActionsException(String message) {
        super(message);
    }

    /**
     * <p>
     * Create an instance of the class with a message and the cause.
     * </p>
     * @param message
     *            the error message
     * @param cause
     *            the cause for this exception
     */
    public UserDocumentationManagementActionsException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * <p>
     * Create an instance of the class with a message and the exception data.
     * </p>
     * @param message
     *            the error message
     * @param exceptionData
     *            the exception data
     */
    public UserDocumentationManagementActionsException(String message, ExceptionData exceptionData) {
        super(message, exceptionData);
    }

    /**
     * <p>
     * Create an instance of the class with a message, cause and the exception data.
     * </p>
     * @param message
     *            the error message
     * @param cause
     *            the cause for this exception
     * @param exceptionData
     *            the exception data
     */
    public UserDocumentationManagementActionsException(String message, Throwable cause, ExceptionData exceptionData) {
        super(message, cause, exceptionData);
    }
}
