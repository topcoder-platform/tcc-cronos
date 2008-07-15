/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.contest;

import com.topcoder.search.builder.filter.Filter;

import com.topcoder.service.digitalrun.entity.TrackContest;

import java.util.List;


/**
 * <p>
 * This is a DAO interface contract for CRUD operations on the <code>TrackContest</code> entities.
 * </p>
 *
 * <p>
 * The following injection setters should be provided if the DAO wants to log as well as to have
 * access to <code>SessionContext</code>, and the ability to fetch <code>EntityManager</code>:
 * <ol>
 *     <li>setLogger(logger:Log)</li>
 *     <li>setSessionContext(sessionContext:SessionContext)</li>
 *     <li>setUnitName(unitName:String)</li>
 * </ol>
 * Theses are not mandatory but they will be initialized if provided.
 * </p>
 *
 * <p>
 *     <strong>Thread Safety:</strong>
 *     Implementations should be thread safe and should not perform their own transactions.
 * </p>
 *
 * @author AleaActaEst, TCSDEVELOPER
 * @version 1.0
 */
public interface TrackContestDAO {

    /**
     * <p>
     * Creates a new <code>TrackContest</code> entity in persistence.
     * </p>
     *
     * <p>
     * It accepts an instance that has not yet been created in persistence, and persists it.
     * </p>
     *
     * @param trackContest The track contest we want to create.
     *
     * @return The created track contest (with possibly updated id).
     *
     * @throws IllegalArgumentException if the input is null.
     * @throws EntityExistsException if an entity with this id already exists.
     * @throws PersistenceException if there was a failure in creation.
     */
    TrackContest createTrackContest(TrackContest trackContest)
        throws PersistenceException, EntityExistsException;

    /**
     * <p>
     * Updates an existing <code>TrackContest</code> entity in persistence.
     * </p>
     *
     * <p>
     * It accepts an instance that should already be present in persistence, and persist the update.
     * </p>
     *
     * @param trackContest The track contest we want to update.
     *
     * @throws IllegalArgumentException if the input is null.
     * @throws EntityNotFoundException if the input entity doesn't exist in persistence.
     * @throws PersistenceException if there was a failure in the update.
     */
    void updateTrackContest(TrackContest trackContest)
        throws PersistenceException, EntityNotFoundException;

    /**
     * <p>
     * Removes an existing <code>TrackContest</code> entity from persistence.
     * </p>
     *
     * <p>
     * It accepts an id of an instance that should already be present in persistence, and
     * removes that entity from the persistence.
     * </p>
     *
     * @param trackContestId The track contest (by id) we want to remove.
     *
     * @throws EntityNotFoundException if the input entity id doesn't exist in persistence.
     * @throws PersistenceException if there was a failure in the removal.
     */
    void removeTrackContest(long trackContestId) throws PersistenceException, EntityNotFoundException;

    /**
     * <p>
     * Fetches a <code>TrackContest</code> entity from persistence based in the input id.
     * </p>
     *
     * @param trackContestId The track contest (by id) we want to fetch.
     *
     * @return The <code>TrackContest</code> entity if found or null of not found.
     *
     * @throws PersistenceException if there was a failure in the fetching.
     */
    TrackContest getTrackContest(long trackContestId) throws PersistenceException;

    /**
     * <p>
     * This will search the persistence for matching <code>TrackContest</code> entities based on the
     * specific search criteria provided.
     * </p>
     *
     * @param filter The search criteria to be used.
     *
     * @return List of <code>TrackContest</code> entities that are matched by the provided filter, empty
     *         list if none were found.
     *
     * @throws IllegalArgumentException if filter is null.
     * @throws PersistenceException exception if there was a failure in the searching.
     */
    List < TrackContest > searchTrackContests(Filter filter) throws PersistenceException;
}
