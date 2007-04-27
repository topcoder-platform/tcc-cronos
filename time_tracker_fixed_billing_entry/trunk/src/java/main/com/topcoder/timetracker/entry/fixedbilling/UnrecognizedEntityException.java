/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.fixedbilling;

/**
 * <p>
 * This exception is thrown when interacting with the data store and an entity cannot be recognized.  It may be thrown
 * when an entity with a specified identifier cannot be found.  It is thrown by all the Manager and DAO interfaces
 * (and their implementations).
 * </p>
 *
 * <p>
 * Thread Safety: This is an exception class, immutable, and therefore thread-safe.
 * </p>
 *
 * @author ShindouHikaru, flytoj2ee
 * @version 1.0
 */
public class UnrecognizedEntityException extends DataAccessException {
    /**
     * <p>
     * This is the id of the entity that could not be located in the persistent store.
     * </p>
     */
    private final long id;

    /**
     * <p>
     * Constructor that allows an exception to be defined with the id of the unrecognized entity.   A default message
     * is provided.
     * </p>
     *
     * @param id The id of the unrecognized entity.
     */
    public UnrecognizedEntityException(long id) {
        this(id, "Unrecognized the entity id in the persistent store.");
    }

    /**
     * <p>
     * Constructor that allows an exception to be defined with the id of the unrecognized entity.
     * </p>
     *
     * @param id The id of the unrecognized entity.
     * @param msg The message of the exception.
     */
    public UnrecognizedEntityException(long id, String msg) {
        super(msg);
        this.id = id;
    }

    /**
     * <p>
     * Retrieves the id of the entity that could not be located in the persistent store.
     * </p>
     *
     * @return the id of the entity that could not be located in the persistent store.
     */
    public long getId() {
        return id;
    }
}
