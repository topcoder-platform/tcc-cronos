/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.photo;

import com.topcoder.util.errorhandling.BaseRuntimeException;
import com.topcoder.util.errorhandling.ExceptionData;

/**
 * This exception indicates error with component configuration.
 * This class inherits from not thread-safe class and thus not thread-safe.
 *
 * @author gevak, mumujava
 * @version 1.0
 */
public class MemberPhotoActionConfigurationException extends BaseRuntimeException {
    /**
    * <p>
    * The serial version uid.
    * </p>
    */
    private static final long serialVersionUID = -7392703257727205526L;

    /**
     * Create instance with specified parameters. Simply delegates to base class constructor with same signature.
     *
     * @param message Error message.
     */
    public MemberPhotoActionConfigurationException(String message) {
        super(message);
    }

    /**
     * Create instance with specified parameters. Simply delegates to base class constructor with same signature.
     *
     * @param message Error message.
     * @param cause Error cause.
     */
    public MemberPhotoActionConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Create instance with specified parameters. Simply delegates to base class constructor with same signature.
     *
     * @param message Error message.
     * @param data Exception data.
     */
    public MemberPhotoActionConfigurationException(String message, ExceptionData data) {
        super(message, data);
    }

    /**
     * Create instance with specified parameters. Simply delegates to base class constructor with same signature.
     *
     * @param message Error message.
     * @param cause Error cause.
     * @param data Exception data.
     */
    public MemberPhotoActionConfigurationException(String message, Throwable cause, ExceptionData data) {
        super(message, cause, data);
    }
}
