/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.messenger;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXParseException;

/**
 * Error handler class used in parsing the results of implementations of
 * the method {@link XMLMessage#toXMLString(DateFormatContext)}.
 *
 * @author marius_neo
 * @version 1.0
 */
public class SimpleErrorHandler implements ErrorHandler {

    /**
     * Receive notification of a recoverable error.
     *
     * @param exception The errror information encapsulated in a SAX parse exception.
     */
    public void error(SAXParseException exception) {
        throw new RuntimeException(exception);
    }

    /**
     * Receive notification of a non-recoverable error.
     *
     * @param exception The fatal error information encapsulated in a SAX parse exception.
     */
    public void fatalError(SAXParseException exception) {
        throw new RuntimeException(exception);
    }

    /**
     * Receive notification of a recoverable error.
     *
     * @param exception The warning information encapsulated in a SAX parse exception.
     */
    public void warning(SAXParseException exception) {
        throw new RuntimeException(exception);
    }
}
