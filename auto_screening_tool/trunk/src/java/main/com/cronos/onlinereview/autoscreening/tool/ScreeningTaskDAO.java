/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.tool;

/**
 * <p>
 * This is a DAO that performs some data access operations on the backing
 * persistent store.
 * </p>
 * <p>
 * The interface that is provided is minimalistic, and doesn't encapsulate a
 * full management API. The interface is used simply to satisfy all the needs of
 * the screening tool (for actual DAO Screening Task management, the Screening
 * Management component is suggested)
 * </p>
 * <p>
 * Thread Safety: Implementations are not required to be thread-safe.
 * </p>
 * @author ShindouHikaru, urtks
 * @version 1.0
 */
public interface ScreeningTaskDAO {
    /**
     * <p>
     * Loads all screening tasks in the datastore that have the provided
     * screenerId as their screener. If a status is provided, then the tasks
     * will also be constrained to the provided status.
     * </p>
     * <p>
     * If the screenerId is null, then only the status will be used to constrain
     * the search. If the status is null, then only the screenerId will be
     * utilized. If both are null, it means all the tasks should be returned.
     * </p>
     * @return The screening tasks satisfying the specified criteria.
     * @param screenerId
     *            The screenerId of the tasks to retrieve (may be null to
     *            indicate unconstrained by screener id)
     * @param status
     *            The status of the tasks to retrieve (may be null to indicate
     *            unconstrained by status)
     * @throws IllegalArgumentException
     *             if screenerId is not null and its long value is <= 0.
     * @throws DAOException
     *             if a problem occurs while accessing the datastore.
     */
    public ScreeningTask[] loadScreeningTasks(Long screenerId, ScreeningStatus status)
        throws DAOException;

    /**
     * <p>
     * Updates the provided Screening Task in the datastore.
     * </p>
     * @param screeningTask
     *            The screening task to update.
     * @throws IllegalArgumentException
     *             if screeningTask is null.
     * @throws UpdateFailedException
     *             if a problem occurs due to a simultaneous update by a
     *             different screener.
     * @throws DAOException
     *             if a problem occurs while accessing the datastore.
     */
    public void updateScreeningTask(ScreeningTask screeningTask) throws DAOException;
}
