/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.messenger;

/**
 * This exception will be used in <c>FormatterLoader</c> class in order to wrap any exceptions
 * that might appear during the operations that retrieve informations from the configuration.
 *
 * @author woodjhon, marius_neo
 * @version 1.0
 */
public class FormatterConfigurationException
    extends ChatMessageFormattingException {

    /**
     * Create the exception with the specified error message.
     *
     * @param msg The error message of the exception.
     */
    public FormatterConfigurationException(String msg) {
        super(msg, "");
    }

    /**
     * Create the exception with error message and cause.
     *
     * @param msg   The error message of the exception.
     * @param cause The cause exception of the exception.
     */
    public FormatterConfigurationException(String msg, Throwable cause) {
        super(msg, cause, "");
    }
}
