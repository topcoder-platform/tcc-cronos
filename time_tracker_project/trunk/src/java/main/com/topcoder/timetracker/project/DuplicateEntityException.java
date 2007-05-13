/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.project;

/**
 * <p>
 * This exception is thrown when adding an entry to a <code>Project</code>, and the entry has already
 * been found to be associated with the project.
 * </p>
 *
 * <p>
 * Thread-Safety: This class is thread safe as it has no state and its super class is also thread safe.
 * </p>
 *
 * @author ShindouHikaru, bendlund, TCSDEVELOPER
 * @version 3.2
 */
public class DuplicateEntityException extends DataAccessException {

	/**
	 * Automatically generated unique ID for use with serialization.
	 */
	private static final long serialVersionUID = -4380955509198903343L;

	/**
     * <p>
     * This is the id of the entry that has been found to have a duplicate.
     * </p>
     */
    private long id;

    /**
     * <p>
     * Constructor with an entry id.
     * </p>
     *
     * @param id the id of the entry that has been found to have a duplicate.
     */
    public DuplicateEntityException(long id) {
        super("There is duplicate entity id [" + id + "].");

        this.id = id;
    }

    /**
     * <p>
     * Constructor with the entry id and custom message.
     * </p>
     *
     * @param id the id of the entry that has been found to have a duplicate.
     * @param msg A custom message to use.
     */
    public DuplicateEntityException(long id, String msg) {
        super(msg);

        this.id = id;
    }

    /**
     * <p>
     * Retrieves the id of the entry that has been found to have a duplicate.
     * </p>
     *
     * @return the id of the entry that has been found to have a duplicate.
     */
    public long getId() {
        return id;
    }
}