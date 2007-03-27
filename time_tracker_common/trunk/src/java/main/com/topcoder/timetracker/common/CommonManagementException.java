/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.common;

import com.topcoder.util.errorhandling.BaseException;


/**
 * <p>
 *  This exception extends the <code>BaseException</code> and is thrown from the <code>CommonManager</code> interface.
 *  It is also the parent exception class for all the other custom exceptions in this design.
 * </p>
 *
 * <p>
 *  <strong>Thread-safety:</strong>
 *  This class is immutable since its super class <code>BaseException</code> is immutable,
 *  so this class is thread-safe.
 * </p>
 *
 * @author Mafy, liuliquan
 * @version 3.1
 */
public class CommonManagementException extends BaseException {

    /**
     * <p>
     * Serial Version UID.
     * </p>
     */
    private static final long serialVersionUID = 2875964748756230148L;

    /**
     * <p>
     * Constructor with error message.
     * </p>
     *
     * @param message the error message
     */
    public CommonManagementException(String message) {
        super(message);
    }

    /**
     * <p>
     * Constructor with error cause.
     * </p>
     *
     * @param cause the cause of this exception
     */
    public CommonManagementException(Throwable cause) {
        super(cause);
    }

    /**
     * <p>
     * Constructor with error message and inner cause.
     * </p>
     *
     * @param message the error message.
     * @param cause the cause of this exception.
     */
    public CommonManagementException(String message, Throwable cause) {
        super(message, cause);
    }
}
