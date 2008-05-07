/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.prerequisite;

import javax.xml.ws.WebFault;

import com.topcoder.service.prerequisite.ejb.UserAlreadyAnsweredDocumentFault;
import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * This exception is thrown automatically by the web service interface when the user has already answered to the
 * document.. It is related with "user_already_answered_document_faultMsg" fault in WSDL, but it contains only
 * annotations about "user_already_answered_document_fault" because the messages are automatically constructed.
 * </p>
 * <p>
 * The constructors with <code>{@link UserAlreadyAnsweredDocumentFault}</code> and the getter are necessary for the
 * correct webservices serialization/deserialization. The other constructors are added for the future implementations
 * of the service interface.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
@WebFault(name = "user_already_answered_document_fault",
    faultBean = "com.topcoder.service.prerequisite.ejb.UserAlreadyAnsweredDocumentFault")
public class UserAlreadyAnsweredDocumentException extends PrerequisiteServiceException {

    /**
     * Represents the serial version unique id.
     */
    private static final long serialVersionUID = 8353694402003575464L;

    /**
     * <p>
     * Represents the fault info, Java type that goes as soapenv:Fault detail element.
     * </p>
     */
    private final UserAlreadyAnsweredDocumentFault faultInfo;

    /**
     * <p>
     * Represents the user id who has already answered.
     * </p>
     */
    private final long userIdAlreadyAnsweredDocument;

    /**
     * <p>
     * Creates a <code>UserAlreadyAnsweredDocumentException</code> instance with error message and fault info.
     * </p>
     *
     * @param message
     *            the error message
     * @param faultInfo
     *            the fault info which contains the user id who has already answered.
     */
    public UserAlreadyAnsweredDocumentException(String message, UserAlreadyAnsweredDocumentFault faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
        this.userIdAlreadyAnsweredDocument = -1;
    }

    /**
     * <p>
     * Creates a <code>UserAlreadyAnsweredDocumentException</code> instance with error message, cause exception and
     * fault info.
     * </p>
     *
     * @param message
     *            the error message
     * @param faultInfo
     *            the fault info which contains the user id who has already answered.
     * @param cause
     *            the cause exception
     */
    public UserAlreadyAnsweredDocumentException(String message, UserAlreadyAnsweredDocumentFault faultInfo,
        Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
        this.userIdAlreadyAnsweredDocument = -1;
    }

    /**
     * <p>
     * Creates a <code>UserAlreadyAnsweredDocumentException</code> instance with error message and the user id who has
     * already answered.
     * </p>
     *
     * @param message
     *            the error message
     * @param userIdAlreadyAnsweredDocument
     *            the user id who has already answered.
     */
    public UserAlreadyAnsweredDocumentException(String message, long userIdAlreadyAnsweredDocument) {
        super(message);
        this.faultInfo = null;
        this.userIdAlreadyAnsweredDocument = userIdAlreadyAnsweredDocument;
    }

    /**
     * <p>
     * Creates a <code>UserAlreadyAnsweredDocumentException</code> instance with error message, inner cause and the
     * user id who has already answered.
     * </p>
     *
     * @param message
     *            the error message
     * @param cause
     *            the cause of this exception
     * @param userIdAlreadyAnsweredDocument
     *            the user id who has already answered.
     */
    public UserAlreadyAnsweredDocumentException(String message, Throwable cause, long userIdAlreadyAnsweredDocument) {
        super(message, cause);
        this.faultInfo = null;
        this.userIdAlreadyAnsweredDocument = userIdAlreadyAnsweredDocument;
    }

    /**
     * <p>
     * Creates a <code>UserAlreadyAnsweredDocumentException</code> instance with error message, exception data and the
     * user id who has already answered.
     * </p>
     *
     * @param message
     *            the error message
     * @param data
     *            the exception data
     * @param userIdAlreadyAnsweredDocument
     *            the user id who has already answered.
     */
    public UserAlreadyAnsweredDocumentException(String message, ExceptionData data,
        long userIdAlreadyAnsweredDocument) {
        super(message, data);
        this.faultInfo = null;
        this.userIdAlreadyAnsweredDocument = userIdAlreadyAnsweredDocument;
    }

    /**
     * <p>
     * Creates a <code>UserAlreadyAnsweredDocumentException</code> instance with error message, exception data, inner
     * cause and the user id who has already answered.
     * </p>
     *
     * @param message
     *            the error message
     * @param cause
     *            the cause of this exception
     * @param data
     *            the exception data
     * @param userIdAlreadyAnsweredDocument
     *            the user id who has already answered.
     */
    public UserAlreadyAnsweredDocumentException(String message, Throwable cause, ExceptionData data,
        long userIdAlreadyAnsweredDocument) {
        super(message, cause, data);
        this.faultInfo = null;
        this.userIdAlreadyAnsweredDocument = userIdAlreadyAnsweredDocument;
    }

    /**
     * <p>
     * Gets the user id who has already answered.
     * </p>
     *
     * @return the user id who has already answered.
     */
    public long getUserIdAlreadyAnsweredDocument() {
        return userIdAlreadyAnsweredDocument;
    }

    /**
     * <p>
     * Gets the fault bean. This method is necessary for the serialization/deserialization.
     *
     * @return the fault bean, possibly null.
     */
    public UserAlreadyAnsweredDocumentFault getFaultInfo() {
        return faultInfo;
    }
}
