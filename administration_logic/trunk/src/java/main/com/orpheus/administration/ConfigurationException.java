/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration;

/**
 * This exception is thrown to indicate a configuration error such as missing properties
 * or missing configuration which prevent initialization of AdministrationManager.<br/>
 * @author bose_java, KKD
 * @version 1.0
 */
public class ConfigurationException extends AdministrationException {

    /**
     * <p>Constructs an exception with given error message.</p>
     *
     *
     * @param message error message.
     */
    public ConfigurationException(String message) {
        super(message);
    }

    /**
     * <p>Constructs an exception with given error message and cause. </p>
     *
     *
     * @param message error message.
     * @param cause the cause.
     */
    public ConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
}
