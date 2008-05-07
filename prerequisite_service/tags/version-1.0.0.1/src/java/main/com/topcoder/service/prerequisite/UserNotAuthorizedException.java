/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.prerequisite;

import javax.ejb.EJBAccessException;
import javax.xml.ws.WebFault;

import com.topcoder.service.prerequisite.ejb.UserNotAuthorizedFault;

/**
 * <p>
 * This exception is thrown automatically by the web service interface when the user is not authorized to perform the
 * operation. It is related with"user_not_authorized_faultMsg" fault in WSDL, but it contains only annotations
 * about"user_not_authorized_fault" because the messages are automatically constructed.
 * </p>
 * <p>
 * This exception extends <code>{@link EJBAccessException}</code>, because <code>{@link EJBAccessException}</code>
 * is thrown by the EJB when an authorization fails when the annotations are used. To maintain the same behavior,
 * the same exception is used.
 * </p>
 * <p>
 * The constructors with <code>{@link UserNotAuthorizedFault}</code> and the getter are necessary for the correct
 * webservices serialization/deserialization. The other constructors are added for the future implementations of the
 * service interface.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
@WebFault(name = "user_not_authorized_fault",
    faultBean = "com.topcoder.service.prerequisite.ejb.UserNotAuthorizedFault")
public class UserNotAuthorizedException extends EJBAccessException {

    /**
     * Represents the serial version unique id.
     */
    private static final long serialVersionUID = 8353694402003575464L;

    /**
     * <p>
     * Represents the fault info, Java type that goes as soapenv:Fault detail element.
     * </p>
     */
    private final UserNotAuthorizedFault faultInfo;

    /**
     * <p>
     * Represents the user id not authorized.
     * </p>
     */
    private final long userIdNotAuthorized;

    /**
     * <p>
     * Creates a <code>UserNotAuthorizedException</code> instance with error message and fault info.
     * </p>
     *
     * @param message
     *            the error message
     * @param faultInfo
     *            the fault info which contains the user id not authorized.
     */
    public UserNotAuthorizedException(String message, UserNotAuthorizedFault faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
        this.userIdNotAuthorized = -1;
    }

    /**
     * <p>
     * Creates a <code>UserNotAuthorizedException</code> instance with error message and the user id not authorized.
     * </p>
     *
     * @param message
     *            the error message
     * @param userIdNotAuthorized
     *            the user id not authorized
     */
    public UserNotAuthorizedException(String message, long userIdNotAuthorized) {
        super(message);
        this.faultInfo = null;
        this.userIdNotAuthorized = userIdNotAuthorized;
    }

    /**
     * <p>
     * Gets the user id not authorized.
     * </p>
     *
     * @return the user id not authorized.
     */
    public long getUserIdNotAuthorized() {
        return userIdNotAuthorized;
    }

    /**
     * <p>
     * Gets the fault bean. This method is necessary for the serialization/deserialization.
     *
     * @return the fault bean, possibly null.
     */
    public UserNotAuthorizedFault getFaultInfo() {
        return faultInfo;
    }
}
