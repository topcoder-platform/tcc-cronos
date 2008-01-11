/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.forum.service.ejb;

import com.topcoder.util.errorhandling.BaseRuntimeException;
import com.topcoder.util.errorhandling.ExceptionData;


/**
 * <p>
 * This exception extends the <code>BaseRuntimeException</code>. It is thrown from
 * the <code>JiveForumServiceBean</code> if the bean is not configured properly to
 * be initialized.
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
public class ServiceConfigurationException extends BaseRuntimeException {
    /**
     * <p>Serial version uid for the Serializable class.</p>
     */
    private static final long serialVersionUID = 4233761244527629299L;

    /**
     * <p>Creates an instance with the error message.</p>
     *
     * @param message the error message
     */
    public ServiceConfigurationException(String message) {
        super(message);
    }

    /**
     * <p>Creates an instance with the error message and inner cause.</p>
     *
     * @param message the error message
     * @param cause the inner exception cause.
     */
    public ServiceConfigurationException(String message, Throwable cause) {
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
    public ServiceConfigurationException(String message, ExceptionData data) {
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
    public ServiceConfigurationException(String message, Throwable cause,
        ExceptionData data) {
        super(message, cause);
    }
}
