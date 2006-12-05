/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration;

/**
 * This exception is thrown from the ServiceLocator class to denote errors when
 * creating the InitialContext or when looking up ejb remote home references. It
 * is used to wrap NamingException when performing JNDI lookups.
 *
 * @author bose_java, KKD
 * @version 1.0
 */
public class ServiceLocatorException extends AdministrationException {

    /**
     * Constructs an exception with given error message.
     *
     *
     * @param message
     *            error message.
     */
    public ServiceLocatorException(String message) {
        super(message);
    }

    /**
     * Constructs an exception with given error message and cause.
     *
     *
     * @param message
     *            error message.
     * @param cause
     *            the cause.
     */
    public ServiceLocatorException(String message, Throwable cause) {
        super(message, cause);
    }
}
