/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.project;

import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * This exception is thrown to indicate a configuration error in the component. It is thrown by constructors of the
 * <code>{@link com.topcoder.service.project.impl.ProjectServiceBean}</code> and
 * <code>{@link com.topcoder.service.project.persistence.JPAProjectPersistence}</code> classes.
 * </p>
 * <p>
 * It extends <code>{@link ProjectServiceException}</code> to get the functionality of error messages, cause wrapping
 * and exception data. We also declare four constructors that map onto super class constructors of the same signature.
 * </p>
 * <p>
 * <b>Thread Safety</b>: This class is thread safe as it has no state and the super class is thread safe.
 * </p>
 *
 * @author humblefool, FireIce
 * @version 1.0
 */
public class ConfigurationException extends ProjectServiceException {
    /**
     * <p>
     * Represents the serial version unique id.
     * </p>
     */
    private static final long serialVersionUID = 6553525534965498331L;

    /**
     * <p>
     * Constructs this exception without an error message or cause or exception data.
     * </p>
     */
    public ConfigurationException() {
        // super() is implicitly called.
    }

    /**
     * <p>
     * Constructs this exception with an error message.
     * </p>
     *
     * @param message
     *            The error message describing this exception. Possibly null/empty.
     */
    public ConfigurationException(String message) {
        super(message);
    }

    /**
     * <p>
     * Constructs this exception with an error message and a cause.
     * </p>
     *
     * @param message
     *            The error message describing this exception. Possibly null/empty.
     * @param cause
     *            The cause of this exception. Possibly null.
     */
    public ConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * <p>
     * Constructs this exception with an error message and a cause and exception data.
     * </p>
     *
     * @param message
     *            The error message describing this exception. Possibly null/empty.
     * @param cause
     *            The cause of this exception. Possibly null.
     * @param exceptionData
     *            The exception data. Possibly null.
     */
    public ConfigurationException(String message, Throwable cause, ExceptionData exceptionData) {
        super(message, cause, exceptionData);
    }
}
