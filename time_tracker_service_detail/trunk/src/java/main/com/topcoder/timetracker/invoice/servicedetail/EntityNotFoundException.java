/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice.servicedetail;

/**
 * <p>
 * This exception should be thrown if detail is not found. Id of this exception may be or id of detail or id of
 * invoice.
 * </p>
 * <p>
 * Class is thread safe because it is immutable
 * </p>
 *
 * @author tushak, enefem21
 * @version 1.0
 */
public class EntityNotFoundException extends DataAccessException {

    /** Serial version UID. */
    private static final long serialVersionUID = 821404090654091915L;

    /**
     * This attribute represents id of detail or invoice. This is immutable, sets by constructor. It is accessible
     * by corresponding getter method.
     */
    private final long id;

    /**
     * <p>
     * Constructor which create exception with given id of entity.
     * </p>
     *
     * @param id
     *            the id that can't be found
     */
    public EntityNotFoundException(long id) {
        super("Entity with id [" + id + "] can't be found in the database");
        this.id = id;
    }

    /**
     * <p>
     * Constructor which create exception with given id of entity.
     * </p>
     *
     * @param id
     *            the id that can't be found
     * @param message
     *            A string representing the message for this exception.
     */
    public EntityNotFoundException(long id, String message) {
        super(message);
        this.id = id;
    }

    /**
     * <p>
     * Gets entity id.
     * </p>
     *
     * @return the id related to this error
     */
    public long getId() {
        return this.id;
    }
}
