/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.common;

/**
 * <p>
 *  This exception is thrown when a problem occurs when performing operations with the <code>PaymentTerm</code>
 *  in the persistence.
 *  </p>
 *
 * <p>
 *  <strong>Thread-safety:</strong>
 *  This class is immutable since its super class <code>CommonManagementException</code> is immutable,
 *  so this class is thread-safe.
 * </p>
 *
 * @author Mafy, liuliquan
 * @version 3.1
 */
public class PaymentTermDAOException extends CommonManagementException {

    /**
     * <p>
     * Serial Version UID.
     * </p>
     */
    private static final long serialVersionUID = -5001954074777924914L;

    /**
     * <p>
     * Constructor with error message.
     * </p>
     *
     * @param message the error message
     */
    public PaymentTermDAOException(String message) {
        super(message);
    }

    /**
     * <p>
     * Constructor with error cause.
     * </p>
     *
     * @param cause the cause of this exception
     */
    public PaymentTermDAOException(Throwable cause) {
        super(cause);
    }

    /**
     * <p>
     * Constructor with error message and inner cause.
     * </p>
     *
     * @param message the error message
     * @param cause the cause of this exception
     */
    public PaymentTermDAOException(String message, Throwable cause) {
        super(message, cause);
    }
}
