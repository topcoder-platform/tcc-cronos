/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.phases.template;

import com.topcoder.util.errorhandling.BaseException;

/**
 * <p>
 * <code>PhaseTemplateException</code> is the base class for custom exceptions
 * thrown from <b>Project Phase Template</b> component.
 * </p>
 * @author albertwang, flying2hk
 * @version 1.0
 */
public class PhaseTemplateException extends BaseException {
    /**
     * <p>
     * Create <code>PhaseTemplateException</code> instance with the error
     * message.
     * </p>
     * @param message the error message
     */
    public PhaseTemplateException(String message) {
        super(message);
    }

    /**
     * <p>
     * Create <code>PhaseTemplateException</code> instance with error message
     * and the cause exception.
     * </p>
     * @param message the error message
     * @param cause the cause
     */
    public PhaseTemplateException(String message, Throwable cause) {
        super(message, cause);
    }
}
