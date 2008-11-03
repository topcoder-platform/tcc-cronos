/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clientcockpit.phases;

import com.topcoder.management.phase.PhaseHandlingException;


/**
 * <p>
 * This exception will be thrown by the <code>EmailMessageGenerator</code> implementations
 * and the <code>AbstractPhaseHandler</code> if errors occur while generating the email message.
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
public class EmailMessageGenerationException extends PhaseHandlingException {

    /**
     * <p>
     * Serial Version UID.
     * </p>
     */
    private static final long serialVersionUID = -6107839268125873243L;

    /**
     * <p>
     * Creates a new instance with the given message.
     * </p>
     *
     * @param message the message
     */
    public EmailMessageGenerationException(String message) {
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
    public EmailMessageGenerationException(String message, Throwable cause) {
        super(message, cause);
    }
}
