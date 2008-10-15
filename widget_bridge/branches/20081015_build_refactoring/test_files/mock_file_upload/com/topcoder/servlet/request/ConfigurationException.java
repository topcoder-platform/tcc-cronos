/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.servlet.request;

/**
 * <p>
 * This is an exception which indicates any errors related to configuration, such as missing required properties,
 * invalid property values (e.g. not a number) or accepting a ConfigurationObject.
 * </p>
 *
 * <p>
 * This exception is thrown from the constructors accepting a namespace.
 * </p>
 *
 * <p>
 * <b>Thread Safety: </b> This class is immutable and therefore thread safe.
 * </p>
 * <p>
 * <b>Change log:</b><br>
 * <ul>
 * <li><b>Version 2.1</b> added ConfigurationObject in java-docs. </li>
 * </ul>
 * </p>
 *
 * @author colau, PE
 * @author AleaActaEst, TCSDEVELOPER
 * @since 2.0
 * @version 2.1
 */
public class ConfigurationException extends FileUploadException {
    /**
     * <p>
     * Creates a new instance of <code>ConfigurationException</code> class with a detail error message.
     * </p>
     *
     * @param message a detail error message describing the error.
     */
    public ConfigurationException(String message) {
        super(message);
    }

    /**
     * <p>
     * Creates a new instance of <code>ConfigurationException</code> class with a detail error message and the original
     * exception causing the error.
     * </p>
     *
     * @param message a detail error message describing the error.
     * @param cause an exception representing the cause of the error.
     */
    public ConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
}
