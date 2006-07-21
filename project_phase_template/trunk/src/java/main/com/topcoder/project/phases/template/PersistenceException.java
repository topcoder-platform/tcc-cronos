/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.phases.template;

/**
 * <p>
 * <code>PersistenceException</code> indicates that there're errors while accessing the
 * template persistence, e.g. persistence file doesn't exist for file-based
 * persistence layer.
 * </p>
 * <p>
 * It may be thrown from <code>{@link PhaseTemplatePersistence}</code> implementations.
 * </p>
 * @author albertwang, flying2hk
 * @version 1.0
 */
public class PersistenceException extends PhaseTemplateException {

    /**
     * <p>
     * Create <code>PersistenceException</code> instance with the error message.
     * </p>
     * @param message the message
     */
    public PersistenceException(String message) {
        super(message);
    }

    /**
     * <p>
     * Create <code>PersistenceException</code> instance with error message and the cause
     * exception.
     * </p>
     * @param message the message
     * @param cause the cause
     */
    public PersistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
