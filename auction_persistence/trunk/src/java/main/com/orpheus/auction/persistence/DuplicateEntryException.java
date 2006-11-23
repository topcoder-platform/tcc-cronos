/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.auction.persistence;

/**
 * <p>
 * This is a specific persistence exception when inserting a record with a primary id that already exists. The DAO and
 * the associated EJB and client helper methods use it.
 * </p>
 *
 * <p>
 * <b>Thread Safety: </b>This class is thread safe by being immutable.
 * </p>
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public class DuplicateEntryException extends PersistenceException {
    /**
     * <p>
     * Represents the duplicate identifier. It is set in the constructor, will not be null (or empty if a String), and
     * will not change.
     * </p>
     */
    private final Object identifier;

    /**
     * <p>
     * Creates a new instance of <code>DuplicateEntryException</code> class with a detail error message and the
     * duplicate identifier.
     * </p>
     *
     * @param msg a detail error message describing the error.
     * @param identifier the duplicate identifier.
     */
    public DuplicateEntryException(String msg, Object identifier) {
        super(msg);
        this.identifier = identifier;
    }

    /**
     * <p>
     * Gets the duplicate identifier.
     * </p>
     *
     * @return the duplicate identifier.
     */
    public Object getIdentifier() {
        return identifier;
    }
}
