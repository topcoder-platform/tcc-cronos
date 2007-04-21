/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice;

/**
 * <p>
 * This exception is thrown when interacting with the data store and an entity cannot be recognized. It may be
 * thrown when an entity with a specified identifier cannot be found. It is thrown by all the Manager and DAO
 * interfaces (and their implementations).
 * </p>
 * <p>
 * Thread Safety: This is an exception class, immutable, and therefore thread-safe.
 * </p>
 *
 * @author fabrizyo, enefem21
 * @version 1.0
 */
public class InvoiceUnrecognizedEntityException extends InvoiceDataAccessException {

    /** Serial version UID. */
    private static final long serialVersionUID = 5417436792504890433L;

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
     * @param id
     *            The id of the unrecognized entity.
     * @param msg
     *            The message of the exception.
     */
    public InvoiceUnrecognizedEntityException(long id, String msg) {
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
