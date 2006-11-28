/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.administration.persistence;

import com.orpheus.administration.AdministrationException;

/**
 * A specialization of <code>AdministrationException</code> used by {@link MessageTranslator MessageTranslator} and
 * {@link SearchCriteriaTranslator SearchCriteriaTranslator} to indicate errors performing translations. This
 * exception is not currently thrown but is provided for future expansion.
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */

public class TranslationException extends AdministrationException {
    /**
     * Constructs a new <code>SlidingTilePuzzleRenderingException</code> with the specified message.
     *
     * @param message a description of the reason for the exception
     */
    public TranslationException(String message) {
        super(message);
    }

    /**
     * Constructs a new <code>TranslationException</code> with the specified message and cause.
     *
     * @param message a description of the reason for the exception
     * @param cause the lower-level exception that caused this exception to be thrown
     */
    public TranslationException(String message, Throwable cause) {
        super(message, cause);
    }
}
