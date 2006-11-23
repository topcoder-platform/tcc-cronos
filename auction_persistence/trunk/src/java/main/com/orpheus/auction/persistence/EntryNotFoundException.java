/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.auction.persistence;

/**
 * <p>
 * This is a specific persistence exception when retrieving, updating, or deleting a record with a primary id that does
 * not exist. The DAO and the associated EJB and client helper methods use it.
 * </p>
 *
 * <p>
 * <b>Thread Safety: </b>This class is thread safe by being immutable.
 * </p>
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public class EntryNotFoundException extends PersistenceException {
    /**
     * <p>
     * Represents the unknown identifier. It is set in the constructor, will not be null (or empty if a String), and
     * will not change.
     * </p>
     */
    private final Object identifier;

    /**
     * <p>
     * Creates a new instance of <code>EntryNotFoundException</code> class with a detail error message and the
     * duplicate identifier.
     * </p>
     *
     * @param msg a detail error message describing the error.
     * @param identifier the duplicate identifier.
     */
    public EntryNotFoundException(String msg, Object identifier) {
        super(msg);
        this.identifier = identifier;
    }

    /**
     * <p>
     * Gets the unknown identifier.
     * </p>
     *
     * @return the unknown identifier.
     */
    public Object getIdentifier() {
        return identifier;
    }
}
