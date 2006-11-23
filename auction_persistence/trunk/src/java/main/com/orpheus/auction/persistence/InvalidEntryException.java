/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.auction.persistence;

/**
 * <p>
 * This is a specific persistence exception when inserting a record with a primary id that is not valid, which excludes
 * issues such as duplicate ids. The DAO and the associated EJB and client helper methods use it.
 * </p>
 *
 * <p>
 * <b>Thread Safety: </b>This class is thread safe by being immutable.
 * </p>
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public class InvalidEntryException extends PersistenceException {
    /**
     * <p>
     * Represents the invalid identifier. It is set in the constructor, may be null and can be generally any value, and
     * will not change.
     * </p>
     */
    private final Object identifier;

    /**
     * <p>
     * Creates a new instance of <code>InvalidEntryException</code> class with a detail error message and the duplicate
     * identifier.
     * </p>
     *
     * @param msg a detail error message describing the error.
     * @param identifier the duplicate identifier.
     */
    public InvalidEntryException(String msg, Object identifier) {
        super(msg);
        this.identifier = identifier;
    }

    /**
     * <p>
     * Gets the invalid identifier.
     * </p>
     *
     * @return the invalid identifier.
     */
    public Object getIdentifier() {
        return identifier;
    }
}
