/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time;

/**
 * <p>
 * This exception is thrown when interacting with the data store and an entity cannot be recognized.
 * </p>
 *
 * <p>
 * It may be thrown when an entity with a specified identifier cannot be found.
 * </p>
 *
 * <p>
 * It is thrown by all the Manager and DAO interfaces (and their implementations).
 * </p>
 *
 * <p>
 * Thread-Safety: This class is thread safe as it has no state and its super class is also thread safe.
 * </p>
 *
 * @author ShindouHikaru, TCSDEVELOPER
 * @version 3.2
 */
public class UnrecognizedEntityException extends DataAccessException {

    /**
     * Automatically generated unique ID for use with serialization.
     */
    private static final long serialVersionUID = -4068567941311816185L;

    /**
     * <p>
     * This is the id of the entity that could not be located in the persistent store.
     * </p>
     */
    private final long id;

    /**
     * <p>
     * Constructor that allows an exception to be defined with the id of the unrecognized entity.
     * </p>
     *
     * <p>
     * A default message is provided.
     * </p>
     *
     * @param id The id of the unrecognized entity.
     */
    public UnrecognizedEntityException(long id) {
        super("The entity id [" + id + "] is unrecognized.");

        this.id = id;
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
        return this.id;
    }
}
