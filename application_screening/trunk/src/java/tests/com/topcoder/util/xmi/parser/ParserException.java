/*
 * @(#)ParserException.java
 *
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.util.xmi.parser;

/**
 * <p>
 * Exception thrown by <code>Parser</code> interface, it wraps any Exception
 * caught by the parser.
 * </p>
 *
 * @author TCSDESIGNER
 * @author smell
 * @version 1.0
 */
public class ParserException extends Exception {
    /**
     * <p>
     * Default constructor of XMIParserException.
     * </p>
     */
    public ParserException() {
    }

    /**
     * <p>
     * Constructs XMIParserException with a message.
     * </p>
     *
     * @param message - message of the exception
     */
    public ParserException(String message) {
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
    public ParserException(String message, Throwable cause) {
        super(message, cause);
    }
}
