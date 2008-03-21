/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.prerequisite;

import javax.xml.ws.WebFault;

import com.topcoder.service.prerequisite.ejb.RoleNotFoundFault;
import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * This exception is thrown automatically by the web service interface when a role is not found. It is related with
 * "role_not_found_faultMsg" fault in WSDL.
 * </p>
 * <p>
 * The constructors with <code>{@link RoleNotFoundFault}</code> and the getter are necessary for the correct
 * webservices serialization/deserialization. The other constructors are added for the future implementations of the
 * service interface.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
@WebFault(name = "role_not_found_fault", faultBean = "com.topcoder.service.prerequisite.ejb.RoleNotFoundFault")
public class RoleNotFoundException extends PrerequisiteServiceException {

    /**
     * Represents the serial version unique id.
     */
    private static final long serialVersionUID = 8353694402003575464L;

    /**
     * <p>
     * Represents the fault info, Java type that goes as soapenv:Fault detail element.
     * </p>
     */
    private final RoleNotFoundFault faultInfo;

    /**
     * <p>
     * Represents the role id not found.
     * </p>
     */
    private final long roleIdNotFound;

    /**
     * <p>
     * Creates a <code>RoleNotFoundException</code> instance with error message and fault info.
     * </p>
     *
     * @param message
     *            the error message
     * @param faultInfo
     *            the fault info which contains the role id not found.
     */
    public RoleNotFoundException(String message, RoleNotFoundFault faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
        this.roleIdNotFound = -1;
    }

    /**
     * <p>
     * Creates a <code>RoleNotFoundException</code> instance with error message, cause exception and fault info.
     * </p>
     *
     * @param message
     *            the error message
     * @param faultInfo
     *            the fault info which contains the role id not found.
     * @param cause
     *            the cause exception
     */
    public RoleNotFoundException(String message, RoleNotFoundFault faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
        this.roleIdNotFound = -1;
    }

    /**
     * <p>
     * Creates a <code>RoleNotFoundException</code> instance with error message and the role id not found.
     * </p>
     *
     * @param message
     *            the error message
     * @param roleIdNotFound
     *            the role id not found
     */
    public RoleNotFoundException(String message, long roleIdNotFound) {
        super(message);
        this.faultInfo = null;
        this.roleIdNotFound = roleIdNotFound;
    }

    /**
     * <p>
     * Creates a <code>RoleNotFoundException</code> instance with error message, inner cause and the role id not
     * found.
     * </p>
     *
     * @param message
     *            the error message
     * @param cause
     *            the cause of this exception
     * @param roleIdNotFound
     *            the role id not found
     */
    public RoleNotFoundException(String message, Throwable cause, long roleIdNotFound) {
        super(message, cause);
        this.faultInfo = null;
        this.roleIdNotFound = roleIdNotFound;
    }

    /**
     * <p>
     * Creates a <code>RoleNotFoundException</code> instance with error message, exception data and the role id not
     * found.
     * </p>
     *
     * @param message
     *            the error message
     * @param data
     *            the exception data
     * @param roleIdNotFound
     *            the role id not found
     */
    public RoleNotFoundException(String message, ExceptionData data, long roleIdNotFound) {
        super(message, data);
        this.faultInfo = null;
        this.roleIdNotFound = roleIdNotFound;
    }

    /**
     * <p>
     * Creates a <code>RoleNotFoundException</code> instance with error message, exception data, inner cause and the
     * role id not found.
     * </p>
     *
     * @param message
     *            the error message
     * @param cause
     *            the cause of this exception
     * @param data
     *            the exception data
     * @param roleIdNotFound
     *            the role id not found.
     */
    public RoleNotFoundException(String message, Throwable cause, ExceptionData data, long roleIdNotFound) {
        super(message, cause, data);
        this.faultInfo = null;
        this.roleIdNotFound = roleIdNotFound;
    }

    /**
     * <p>
     * Gets the role id not found.
     * </p>
     *
     * @return the role id not found.
     */
    public long getRoleIdNotFound() {
        return roleIdNotFound;
    }

    /**
     * <p>
     * Gets the fault bean. This method is necessary for the serialization/deserialization.
     *
     * @return the fault bean, possibly null.
     */
    public RoleNotFoundFault getFaultInfo() {
        return faultInfo;
    }
}
