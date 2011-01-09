/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project;

/**
 * <p>
 * This interface defines the contract for managing ReviewApplication entities in persistence.
 * </p>
 *
 * <p>
 * <b>Thread Safety</b>: implementations are not required to be thread safe.
 * </p>
 *
 * @author moonli, pvmagacho
 * @version 1.1
 * @since 1.1
 */
public interface ReviewApplicationPersistence {
    /**
     * Create a new review application in persistence.
     *
     * @param reviewApplication the new review application to create, cannot be null. reviewId and projectId should be
     *            positive return
     *
     * @return the created review application
     * @throws IllegalArgumentException if any argument is invalid
     * @throws ReviewApplicationPersistenceException if any other error occurred during operation
     */
    public ReviewApplication create(ReviewApplication reviewApplication) throws ReviewApplicationPersistenceException;

    /**
     * Updates existing review application.
     *
     * @param reviewApplication the review application to update, cannot be null. reviewId and projectId should be
     *            positive return
     *
     * @return the updated review application
     * @throws IllegalArgumentException if any argument is invalid
     * @throws ReviewApplicationPersistenceException if any other error occurred during operation
     */
    public ReviewApplication update(ReviewApplication reviewApplication) throws ReviewApplicationPersistenceException;

    /**
     * Retrieve the review application by id.
     *
     * @param id id of the review application to retrieve, should be positive
     * @return the retrieved review application, or null if not found
     * @throws IllegalArgumentException if any argument is invalid
     * @throws ReviewApplicationPersistenceException if any other error occurred during operation
     */
    public ReviewApplication retrieve(long id) throws ReviewApplicationPersistenceException;

    /**
     * Deletes the review application by ID.
     *
     * @param id id of the review application to delete, should be positive
     * @return true if review application is deleted, otherwise false (id doesn't exist in persistence)
     * @throws IllegalArgumentException if any argument is invalid
     * @throws ReviewApplicationPersistenceException if any other error occurred during operation
     */
    public boolean delete(long id) throws ReviewApplicationPersistenceException;

    /**
     * Gets primary review applications for specified project.
     *
     * @param projectId id of the project, should be positive
     * @return a ReviewApplication array, won't be null, may be empty
     * @throws IllegalArgumentException if any argument is invalid
     * @throws ReviewApplicationPersistenceException if any other error occurred during operation
     */
    public ReviewApplication[] getPrimaryApplications(long projectId) throws ReviewApplicationPersistenceException;

    /**
     * Gets secondary review applications for specified project.
     *
     * @param projectId id of the project, should be positive
     * @return a ReviewApplication array, won't be null, may be empty
     * @throws IllegalArgumentException if any argument is invalid
     * @throws ReviewApplicationPersistenceException if any other error occurred during operation
     */
    public ReviewApplication[] getSecondaryApplications(long projectId) throws ReviewApplicationPersistenceException;

    /**
     * Gets all review applications for specified project.
     *
     * @param projectId id of the project, should be positive
     * @return a ReviewApplication array, won't be null, may be empty
     * @throws IllegalArgumentException if any argument is invalid
     * @throws ReviewApplicationPersistenceException if any other error occurred during operation
     */
    public ReviewApplication[] getAllApplications(long projectId) throws ReviewApplicationPersistenceException;
}
