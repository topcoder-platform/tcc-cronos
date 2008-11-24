/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.servlet.request;

/**
 * <p>
 * This exception is thrown by subclasses of <code>FileUpload</code> when an invalid file is encountered
 * in the <code>ServletRequest</code> (and if the FileUpload is configured to throw an exception instead
 * of simply skipping the file).
 * </p>
 *
 * <p>
 * <b>Thread Safety: </b> This class is immutable and therefore thread safe.
 * </p>
 *
 * @author Vovka, TCSDEVELOPER
 * @version 2.2
 */
public class InvalidFileException extends FileUploadException {
    /**
     * <p>
     * Creates an exception with specified message.
     * </p>
     *
     * @param message
     *            Explanation of the error. Can be empty string or null (useless, but allowed).
     */
    public InvalidFileException(String message) {
        super(message);
    }

    /**
     * <p>
     * Creates an exception with specified message and cause. Simply call super(message,cause).
     * </p>
     *
     * @param message
     *            Explanation of the error. Can be empty string or null (useless, but allowed).
     * @param cause
     *            Underlying cause of the error. Can be null, which means that initial exception is
     *            nonexistent or unknown.
     */
    public InvalidFileException(String message, Throwable cause) {
        super(message, cause);
    }
}
