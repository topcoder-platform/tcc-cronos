/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clientcockpit.phases;

import com.topcoder.management.phase.PhaseHandlingException;


/**
 * <p>
 * This exception will thrown from the three protected sending email methods of <code>AbstractPhaseHandler</code>
 * if errors occur when preparing the message to be sent or when the message is sent.
 * </p>
 *
 * <p>
 *   <strong>Thread Safety:</strong>
 *   The class is mutable and not thread-safe. The application throwing the exception should handle it in a thread-safe
 *   manner.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
public class EmailSendingException extends PhaseHandlingException {

    /**
     * <p>
     * Serial Version UID.
     * </p>
     */
    private static final long serialVersionUID = -2159217875070698375L;

    /**
     * <p>
     * Creates a new instance with the given message.
     * </p>
     *
     * @param message the message
     */
    public EmailSendingException(String message) {
        super(message);
    }

    /**
     * <p>
     * Creates a new instance with the given message and cause.
     * </p>
     *
     * @param message the message
     * @param cause the cause
     */
    public EmailSendingException(String message, Throwable cause) {
        super(message, cause);
    }
}
