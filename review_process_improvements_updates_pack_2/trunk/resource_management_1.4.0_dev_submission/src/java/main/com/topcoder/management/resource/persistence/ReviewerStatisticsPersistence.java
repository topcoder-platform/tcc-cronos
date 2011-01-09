/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource.persistence;

import com.topcoder.management.resource.ReviewerStatistics;
import com.topcoder.management.resource.ReviewerStatisticsPersistenceException;
import com.topcoder.management.resource.SideBySideStatistics;

/**
 * <p>
 * This interface defines the contract for managing ReviewerStatistics entities in persistence. It provides CRUD
 * operations for ReviewerStatistics entity.
 * </p>
 *
 * <p>
 * Implementation class is expected to have a constructor with exactly one parameter (namespace) which represents the
 * configuration namespace for that class.
 * </p>
 *
 * <p>
 * <b>Thread Safety</b>: implementations are not required to be thread safe.
 * </p>
 *
 * @author moonli, pvmagacho
 * @version 1.3
 * @since 1.3
 */
public interface ReviewerStatisticsPersistence {
    /**
     * Creates a new instance in persistence. Returns the persisted object or null.
     *
     * <p>
     * It handles both HISTORY and AVERAGE statistics.
     * </p>
     *
     * @param reviewerStatistics the reviewer statistics to create
     * @return the created ReviewerStatistics object, with id assigned
     * @throws IllegalArgumentException if argument is null
     * @throws ReviewerStatisticsPersistenceException if any error occurred during operation
     */
    public ReviewerStatistics create(ReviewerStatistics reviewerStatistics)
        throws ReviewerStatisticsPersistenceException;

    /**
     * Updates an existing ReviewerStatistic object.
     *
     * <p>
     * It handles both HISTORY and AVERAGE statistics.
     * </p>
     *
     * @param reviewerStatistics the reviewer statistics entity to update
     * @return the updated reviewer statistics entity
     * @throws IllegalArgumentException if argument is null
     * @throws ReviewerStatisticsPersistenceException if any error occurred during operation
     */
    public ReviewerStatistics update(ReviewerStatistics reviewerStatistics)
        throws ReviewerStatisticsPersistenceException;

    /**
     * Retrieve an ReviewerStatistics entity by ID.
     *
     * <p>
     * It handles both HISTORY and AVERAGE statistics.
     * </p>
     *
     * @param id id of the ReviewerStatistics entity to retrieve
     * @return the retrieved ReviewerStatistics
     * @throws IllegalArgumentException if argument is negative
     * @throws ReviewerStatisticsPersistenceException if any error occurred during operation
     */
    public ReviewerStatistics retrieve(long id) throws ReviewerStatisticsPersistenceException;

    /**
     * Deletes an existing ReviewerStatistics entity.
     *
     * <p>
     * It handles both HISTORY and AVERAGE statistics.
     * </p>
     *
     * @param id id of the ReviewerStatistics entity to delete
     * @return true if entity is deleted, false is entity is missing
     * @throws IllegalArgumentException if argument is negative
     * @throws ReviewerStatisticsPersistenceException if any error occurred during operation
     */
    public boolean delete(long id) throws ReviewerStatisticsPersistenceException;

    /**
     * Retrieves a specific ReviewStatistics for a reviewer and competition type.
     *
     * <p>
     * It returns only AVERAGE statistics.
     * </p>
     *
     * @param reviewerId id of the reviewer
     * @param competitionTypeId id of the competition type
     * @return the ReviewerStatistics entity
     * @throws IllegalArgumentException if any of the arguments is negative
     * @throws ReviewerStatisticsPersistenceException if any error occurred during operation
     */
    public ReviewerStatistics getReviewerStatisticsByCompetitionType(long reviewerId, int competitionTypeId)
        throws ReviewerStatisticsPersistenceException;

    /**
     * Gets all statistics for projects in which the two reviewers competed against one another (had secondary
     * reviewer role).
     *
     * <p>
     * It returns only HISTORY statistics.
     * </p>
     *
     * @param firstReviewerId id of the first reviewer
     * @param secondReviewerId id of the second reviewer
     * @param competitionTypeId id of the competition type
     * @return an array of ReviewerStatistics entities
     * @throws IllegalArgumentException if any of the arguments is negative
     * @throws ReviewerStatisticsPersistenceException if any error occurred during operation
     */
    public SideBySideStatistics getSideBySideStatistics(long firstReviewerId, long secondReviewerId,
        int competitionTypeId) throws ReviewerStatisticsPersistenceException;

    /**
     * Gets the AVERGES statistics for specified reviewer.
     *
     * @param reviewerId id of the reviewer
     * @return the ReviewerStatistics for specified reviewer, or empty array if not exist
     * @throws IllegalArgumentException if argument is negative
     * @throws ReviewerStatisticsPersistenceException if any error occurred during operation
     */
    public ReviewerStatistics[] getReviewerAverageStatistics(long reviewerId)
        throws ReviewerStatisticsPersistenceException;

    /**
     * Retrieves an array of ReviewStatistics object; the size of the array will be equal to the number of review
     * boards that the reviewer issuer is member of.
     *
     * <p>
     * It returns only HISTORY statistics.
     * </p>
     *
     * @param reviewerId id of the reviewer
     * @return an array of ReviewerStatistics entities, may be empty
     * @throws IllegalArgumentException if argument is negative
     * @throws ReviewerStatisticsPersistenceException if any error occurred during operation
     */
    public ReviewerStatistics[] getReviewerStatistics(long reviewerId) throws ReviewerStatisticsPersistenceException;
}
