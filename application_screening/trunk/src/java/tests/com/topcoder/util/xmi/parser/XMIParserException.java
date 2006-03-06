/*
 * @(#)XMIParserException.java
 *
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved
 */
package com.topcoder.util.xmi.parser;

/**
 * <p>
 * Exception thrown by XMIParser during parsing the input. It wraps any
 * exceptions that occur internally like XMIHandlerException or other parsing
 * exceptions.
 * </p>
 *
 * @author TCSDESIGNER
 * @author smell
 * @version 1.0
 */
public class XMIParserException extends ParserException {
    /**
     * <p>
     * Default constructor of XMIParserException.
     * </p>
     */
    public XMIParserException() {
    }

    /**
     * <p>
     * Constructs XMIParserException with a message.
     * </p>
     *
     * @param message - message of the exception
     */
    public XMIParserException(String message) {
        super(message);
    }

    /**
     * <p>
     * Constructs XMIParserException with message and cause.
     * </p>
     *
     * @param message - message of the exception
     * @param cause - cause of the exception
     */
    public XMIParserException(String message, Throwable cause) {
        super(message, cause);
    }
}
