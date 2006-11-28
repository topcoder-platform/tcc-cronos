/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.administration.persistence;

/**
 * A specialization of <code>PersistenceException</code> used to indicate that an attempt was made to retrieve,
 * update, or delete a record with a primary ID that does not exist.
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */

public class EntryNotFoundException extends PersistenceException {
    /**
     * The unknown identifier. This member is initialized in the constructor, is immutable, and will never be
     * <code>null</code> or an empty string.
     */
    private final Object identifier;

    /**
     * Creates a new <code>EntryNotFoundException</code> with the specified message and identifier.
     *
     * @param message a description of the reason for the exception
     * @param identifier the unknown identifier that caused the exception
     */
    public EntryNotFoundException(String message, Object identifier) {
        super(message);
        this.identifier = identifier;
    }

    /**
     * Returns the unknown identifier.
     *
     * @return the unknown identifier
     */
    public Object getIdentifier() {
        return identifier;
    }
}
