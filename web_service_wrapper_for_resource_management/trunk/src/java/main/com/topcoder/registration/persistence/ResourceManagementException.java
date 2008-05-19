/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.persistence;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import javax.xml.ws.WebFault;

import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * This is a custom exception that will be thrown by the <code>RegistrationManagerService</code> implementations
 * and <code>ResourceManagerServiceClient</code> class (thrown in all methods) if an error occurs when managing
 * resources.
 * </p>
 *
 * <p>
 * The constructor with <code>ResourceManagementFault</code> and the getter are necessary for the correct WebServices
 * serialization/deserialization.
 * </p>
 *
 * <p>
 *   <strong>Thread Safety:</strong>
 *   The class is mutable and not thread-safe. The application throwing the exception should handle it in a thread-safe
 *   manner.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
@WebFault(
        name = "resourceManagementFault",
        faultBean = "com.topcoder.registration.persistence.ResourceManagementFault"
        )
@SuppressWarnings("serial")
public class ResourceManagementException extends WebServiceWrapperForResourceManagementException {

    /**
     * <p>
     * Represents the faultInfo, Java type that goes as &lt;soapenv:Fault&gt; detail element.
     * </p>
     * <p>
     * Defined in constructor.
     * </p>
     * <p>
     * Accessed In: getter method.
     * </p>
     * <p>
     * Modified In: none.
     * </p>
     * <p>
     * Utilized In: none.
     * </p>
     * <p>
     * Valid Values: Will never be null.
     * </p>
     */
    private final ResourceManagementFault faultInfo;

    /**
     * <p>
     * <strong>Usage:</strong> Constructor with error message.
     * </p>
     *
     * @param message The error message.
     */
    public ResourceManagementException(String message) {
        super(message);
        this.faultInfo = new ResourceManagementFault();
        this.faultInfo.setStackTrace(this.getStackTrace(this));
    }

    /**
     * <p>
     * <strong>Usage:</strong> Constructor with error message and fault bean.
     * </p>
     *
     * <p>
     * This constructor is necessary for the correct WebServices serialization/deserialization.
     * </p>
     *
     * @param message The error message.
     * @param faultInfo The fault bean.
     */
    public ResourceManagementException(String message, ResourceManagementFault faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * <p>
     * <strong>Usage:</strong> Constructor with error message and inner cause.
     * </p>
     *
     * @param message The error message.
     * @param cause The cause of this exception.
     */
    public ResourceManagementException(String message, Throwable cause) {
        super(message, cause);
        this.faultInfo = new ResourceManagementFault();
        this.faultInfo.setStackTrace(this.getStackTrace(cause == null ? this : cause));
    }

    /**
     * <p>
     * <strong>Usage:</strong> Constructor with error message and exception data.
     * </p>
     *
     * @param data The exception data.
     * @param message The error message.
     */
    public ResourceManagementException(String message, ExceptionData data) {
        super(message, data);
        this.faultInfo = new ResourceManagementFault();
        this.faultInfo.setStackTrace(this.getStackTrace(this));
    }

    /**
     * <p>
     * <strong>Usage:</strong> Constructor with error message and inner cause and exception data.
     * </p>
     *
     * @param message The error message.
     * @param cause The cause of this exception.
     * @param data The exception data.
     */
    public ResourceManagementException(String message, Throwable cause, ExceptionData data) {
        super(message, cause, data);
        this.faultInfo = new ResourceManagementFault();
        this.faultInfo.setStackTrace(this.getStackTrace(cause == null ? this : cause));
    }

    /**
     * <p>
     * Get exception stack trace.
     * </p>
     *
     * @param cause The error cause.
     *
     * @return The exception stack trace.
     */
    private String getStackTrace(Throwable cause) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        cause.printStackTrace(new PrintStream(baos));
        return baos.toString();
    }

    /**
     * <p>
     * Return the fault bean.
     * </p>
     *
     * <p>
     * This method is necessary for the serialization/deserialization.
     * </p>
     *
     * @return The fault bean. Will never be null.
     */
    public ResourceManagementFault getFaultInfo() {
        return faultInfo;
    }
}

