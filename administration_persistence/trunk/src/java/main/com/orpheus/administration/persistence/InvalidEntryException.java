/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.administration.persistence;

/**
 * Specialization of <code>PersistenceException</code> thrown when an attempt is made to approve or reject a player
 * that is not a pending winner for the game in question.
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 * @see AdminDataDAO
 * @see AdminData
 */

public class InvalidEntryException extends PersistenceException {
    /**
     * The invalid identiifer. This is set in the constructor, is immutable, and will never be <code>null</code>
     * or an empty string.
     */
    private final Object identifier;

    /**
     * Creates a new <code>InvalidEntryException</code> with the specified message and identifier.
     *
     * @param message a description of the reason for the exception
     * @param identifier the identifier that caused the exception
     */
    public InvalidEntryException(String message, Object identifier) {
        super(message);
        this.identifier = identifier;
    }

    /**
     * Returns the invliad identifier.
     *
     * @return the invalid identifier
     */
    public Object getIdentifier() {
        return identifier;
    }
}
