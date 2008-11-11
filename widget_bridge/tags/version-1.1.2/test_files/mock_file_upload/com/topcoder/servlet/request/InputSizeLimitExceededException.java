/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.servlet.request;

import java.io.IOException;

/**
 * <p>
 * This exception is thrown by <code>UploadedFileInputStream</code> class to indicate that the size of the
 * file in the stream exceeded the predefined limit. This exception is also thrown by
 * <code>StreamFileUpload</code> class to indicate that the total size of the files in the ServletRequest
 * exceeded the limit.
 * </p>
 *
 * <p>
 * <b>Thread Safety: </b> This class is mutable and therefore not thread safe.
 * </p>
 *
 * @author Vovka, TCSDEVELOPER
 * @version 2.2
 */
public class InputSizeLimitExceededException extends IOException {
    /**
     * <p>
     * Size limit that was exceeded. Can be any value.
     * </p>
     */
    private long exceededSizeLimit = 0;

    /**
     * <p>
     * Constructs an exception with null as its error detail message.
     * </p>
     */
    public InputSizeLimitExceededException() {
        super();
    }

    /**
     * <p>
     * Creates an exception with specified message.
     * </p>
     *
     * @param message
     *            Explanation of the error. Can be empty string or null (useless, but allowed).
     */
    public InputSizeLimitExceededException(String message) {
        super(message);
    }

    /**
     * <p>
     * Setter for exceededSizeLimit field.
     * </p>
     *
     * @param exceededSizeLimit
     *            Size limit that was exceeded. Can be any value.
     */
    public void setExceededSizeLimit(long exceededSizeLimit) {
        this.exceededSizeLimit = exceededSizeLimit;
    }

    /**
     * <p>
     * Getter for exceededSizeLimit field.
     * </p>
     *
     * @return Size limit that was exceeded. Can be any value.
     */
    public long getExceededSizeLimit() {
        return exceededSizeLimit;
    }
}
