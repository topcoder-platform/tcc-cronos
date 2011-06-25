/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.photo;

import com.topcoder.util.errorhandling.BaseCriticalException;
import com.topcoder.util.errorhandling.ExceptionData;

/**
 * This exception indicates error with member photo removal (I/O issue, DB issue, etc).
 * This class inherits from not thread-safe class and thus not thread-safe.
 *
 * @author gevak, mumujava
 * @version 1.0
 */
public class MemberPhotoRemovalException extends BaseCriticalException {
    /**
    * <p>
    * The serial version uid.
    * </p>
    */
    private static final long serialVersionUID = 4466307990977901297L;

    /**
     * Create instance with specified parameters. Simply delegates to base class constructor with same signature.
     *
     * @param message Error message.
     */
    public MemberPhotoRemovalException(String message) {
        super(message);
    }

    /**
     * Create instance with specified parameters. Simply delegates to base class constructor with same signature.
     *
     * @param message Error message.
     * @param cause Error cause.
     */
    public MemberPhotoRemovalException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Create instance with specified parameters. Simply delegates to base class constructor with same signature.
     *
     * @param message Error message.
     * @param data Exception data.
     */
    public MemberPhotoRemovalException(String message, ExceptionData data) {
        super(message, data);
    }

    /**
     * Create instance with specified parameters. Simply delegates to base class constructor with same signature.
     *
     * @param message Error message.
     * @param cause Error cause.
     * @param data Exception data.
     */
    public MemberPhotoRemovalException(String message, Throwable cause, ExceptionData data) {
        super(message, cause, data);
    }
}

