/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.user.persistence;

/**
 * <p>
 * Thrown by {@link ObjectTranslator} implementations if an error occurs while
 * translating a value object to the corresponding data transfer object, or
 * vice-versa.
 * </p>
 * <p>
 * <b>Thread-safety:</b><br> This class is immutable and thread-safe.
 * </p>
 *
 * @author argolite, mpaulse
 * @version 1.0
 */
public class TranslationException extends UserPersistenceException {

    /**
     * <p>
     * Creates a new <code>TranslationException</code> with the specified
     * detail message.
     * </p>
     *
     * @param message the detail message describing the reason for the exception
     */
    public TranslationException(String message) {
        super(message);
    }

    /**
     * <p>
     * Creates a new <code>TranslationException</code> with the specified
     * detail message and cause.
     * </p>
     *
     * @param message the detail message describing the reason for the exception
     * @param cause the cause of this exception
     */
    public TranslationException(String message, Throwable cause) {
        super(message, cause);
    }

}
