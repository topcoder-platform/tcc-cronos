/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.user.persistence;

/**
 * <p>
 * Thrown whenever an attempt is made to insert a record into a persistent store
 * which already contains a record with the same identifier (primary key). The
 * {@link #getIdentifier()} method can be used to obtain the identifier of the
 * duplicate record.
 * </p>
 * <p>
 * <b>Thread-safety:</b><br>
 * This class is immutable and thread-safe.
 * </p>
 *
 * @author argolite, mpaulse
 * @version 1.0
 */
public class DuplicateEntryException extends PersistenceException {

    /**
     * <p>
     * The identifier of the duplicate record in the persistent store
     * </p>
     * <p>
     * This field is set in the constructor, and cannot be changed afterwards.
     * It can be any value, but usually will not be null nor an empty string.
     * </p>
     */
    private final Object identifier;

    /**
     * <p>
     * Creates a new <code>DuplicateEntryException</code> with the specified
     * detail message and the identifier of the duplicate record in the
     * persistent store.
     * </p>
     *
     * @param message the detail message describing the reason for the exception
     * @param identifier the identifier (primary key) of the duplicate record in
     *        the persistent store
     */
    public DuplicateEntryException(String message, Object identifier) {
        super(message);
        this.identifier = identifier;
    }

    /**
     * <p>
     * Gets the identifier (or primary key) of the duplicate record in the
     * persistent store. Since the value of the identifier is not validated in
     * the constructor, this method may return <code>null</code> or an empty
     * string.
     * </p>
     *
     * @return the identifier of the duplicate persistent store entry
     */
    public Object getIdentifier() {
        return identifier;
    }

}
