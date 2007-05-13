/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.project;

/**
 * <p>
 * This exception is thrown when there is an attempt to associate an entity
 * with a <code>Project</code>, and the Company IDs of the <code>Project</code> and the Entity
 * do not match.
 * </p>
 *
 * <p>
 * An example would be adding an Entry (Time /Expense/ Fixed Billing) to a Project, where the
 * Company of the Entry and the Company of the Project do not match.
 * </p>
 *
 * <p>
 * Usually, the expectedCompanyId would be the Project's company id, and the foundCompany id
 * would be the id of the entity being associated with the Project.
 * </p>
 *
 * <p>
 * Thread-Safety: This class is thread safe as it has no state and its super class is also thread safe.
 * </p>
 *
 * @author ShindouHikaru, bendlund, TCSDEVELOPER
 * @version 3.2
 */
public class InvalidCompanyException extends DataAccessException {

	/**
	 * Automatically generated unique ID for use with serialization.
	 */
	private static final long serialVersionUID = 7041791341235924139L;

	/**
     * <p>
     * The company id of the entity being associated with the project.
     * </p>
     */
    private final long foundCompanyId;

    /**
     * <p>
     * The company id of the project.
     * </p>
     */
    private final long expectedCompanyId;

    /**
     * <p>
     * Creates an <code>InvalidCompanyException</code> with the specified found and
     * expectedIds.
     * </p>
     *
     * @param foundId The foundId of the exception.
     * @param expectedId The expectedId of the exception.
     */
    public InvalidCompanyException(long foundId, long expectedId) {
        super("The found company id is [" + foundId + "] but the expected id is [" + expectedId + "].");

        this.foundCompanyId = foundId;
        this.expectedCompanyId = expectedId;
    }

    /**
     * <p>
     * Creates an <code>InvalidCompanyException</code> with the specified found and
     * expectedIds.
     * </p>
     *
     * @param foundId The foundId of the exception.
     * @param expectedId The expectedId of the exception.
     * @param msg The message of the exception.
     */
    public InvalidCompanyException(long foundId, long expectedId, String msg) {
        super(msg);

        this.foundCompanyId = foundId;
        this.expectedCompanyId = expectedId;
    }

    /**
     * <p>
     * Retrieves the company id of the entity being associated with the project.
     * </p>
     *
     * @return The company id of the entity being associated with the project.
     */
    public long getFoundCompanyId() {
        return foundCompanyId;
    }

    /**
     * <p>
     * Retrieves he company id of the project.
     * </p>
     *
     * @return The company id of the project.
     */
    public long getExpectedCompanyId() {
        return expectedCompanyId;
    }
}
