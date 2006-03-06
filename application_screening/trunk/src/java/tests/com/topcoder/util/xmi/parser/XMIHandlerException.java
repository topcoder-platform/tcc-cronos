/*
 * @(#)XMIHandlerException.java
 *
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved
 */
package com.topcoder.util.xmi.parser;

/**
 * <p>
 * Exception thrown by handleNode in XMIHandler during processing of the node
 * when XMIHandler cannot handle the node for any reason.
 * </p>
 *
 * @author TCSDESIGNER
 * @author smell
 * @version 1.0
 */
public class XMIHandlerException extends Exception {
    /**
     * <p>
     * Default constructor of XMIHandlerException.
     * </p>
     */
    public XMIHandlerException() {
    }

    /**
     * <p>
     * Constructs XMIHandlerException with a message.
     * </p>
     *
     * @param message - message of the exception
     */
    public XMIHandlerException(String message) {
        super(message);
    }

    /**
     * <p>
     * Constructs XMIHandlerException with message and cause.
     * </p>
     *
     * @param message - message of the exception
     * @param cause - cause of the exception
     */
    public XMIHandlerException(String message, Throwable cause) {
        super(message, cause);
    }
}
