/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.administration.persistence;

/**
 * A specialization of <code>PersistenceException</code> indicating that an attempt was made to insert a record for
 * an existing primary ID.
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */

public class DuplicateEntryException extends PersistenceException {
    /**
     * The duplicate identiifer. This is set in the constructor, is immutable, and will never be <code>null</code>
     * or an empty string.
     */
    private final Object identifier;

    /**
     * Creates a new <code>DuplicateEntryException</code> with the specified message and identifier.
     *
     * @param message a description of the reason for the exception
     * @param identifier the identifier that caused the exception
     */
    public DuplicateEntryException(String message, Object identifier) {
        super(message);
        this.identifier = identifier;
    }

    /**
     * Returns the duplicate identifier.
     *
     * @return the duplicate identifier
     */
    public Object getIdentifier() {
        return identifier;
    }
}
