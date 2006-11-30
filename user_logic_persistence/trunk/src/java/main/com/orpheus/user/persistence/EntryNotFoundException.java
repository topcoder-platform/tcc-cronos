/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.user.persistence;

/**
 * <p>
 * Thrown whenever an attempt is made to update, retrieve or delete record which
 * could not be found in the persistent store. The {@link #getIdentifier()}
 * method can be used to obtain the identifier (or primary key) of the
 * non-existent record.
 * </p>
 * <p>
 * <b>Thread-safety:</b><br>
 * This class is immutable and thread-safe.
 * </p>
 *
 * @author argolite, mpaulse
 * @version 1.0
 */
public class EntryNotFoundException extends PersistenceException {

    /**
     * <p>
     * The identifier of the record which could not be found in the persistent
     * store.
     * </p>
     * <p>
     * This field is set in the constructor, and cannot be changed afterwards.
     * It can be any value, but usually will not be null nor an empty string.
     * </p>
     */
    private final Object identifier;

    /**
     * <p>
     * Creates a new <code>EntryNotFoundException</code> with the specified
     * detail message and the identifier of the record which could not be found
     * in the persistent store.
     * </p>
     *
     * @param message the detail message describing the reason for the exception
     * @param identifier the identifier (primary key) of the record which could
     *        be found in the persistent store
     */
    public EntryNotFoundException(String message, Object identifier) {
        super(message);
        this.identifier = identifier;
    }

    /**
     * <p>
     * Gets the identifier (or primary key) of the record which could not be
     * found in the persistent store. Since the value of the identifier is not
     * validated in the constructor, this method may return <code>null</code>
     * or an empty string.
     * </p>
     *
     * @return the identifier of the non-existent persistent store entry
     */
    public Object getIdentifier() {
        return identifier;
    }

}
