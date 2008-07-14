/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.contest;

import com.topcoder.search.builder.filter.Filter;

import com.topcoder.service.digitalrun.entity.TrackContest;
import com.topcoder.service.digitalrun.entity.TrackContestResultCalculator;
import com.topcoder.service.digitalrun.entity.TrackContestType;

import java.util.List;


/**
 * <p>
 * This is a contract for operations on digital run contests like add new contest,
 * get contest, update contest, search contests; CRUD (Create, Read, Update, and Delete)
 * operations on contest types; CRUD operations on contest calculators.
 * </p>
 *
 * <p>
 *     <strong>Thread Safety:</strong>
 *     Implementations must be thread-safe from the point of view of their use.
 *     Implementations can assume that passed objects will be operated on by just one thread.
 * </p>
 *
 * @author AleaActaEst, TCSDEVELOPER
 * @version 1.0
 */
public interface DigitalRunContestManager {

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
     * @throws DigitalRunContestManagerException if there was a failure in creation.
     */
    TrackContest createTrackContest(TrackContest trackContest) throws DigitalRunContestManagerException;

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
     * @throws DigitalRunContestManagerException if there was a failure in the update.
     */
    void updateTrackContest(TrackContest trackContest) throws DigitalRunContestManagerException;

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
     * @throws DigitalRunContestManagerException if there was a failure in the removal.
     */
    void removeTrackContest(long trackContestId) throws DigitalRunContestManagerException;

    /**
     * <p>
     * Fetches a <code>TrackContest</code> entity from persistence based in the input id.
     * </p>
     *
     * @param trackContestId The track contest (by id) we want to fetch.
     *
     * @return The <code>TrackContest</code> entity if found or null of not found.
     *
     * @throws DigitalRunContestManagerException if there was a failure in the fetching.
     */
    TrackContest getTrackContest(long trackContestId) throws DigitalRunContestManagerException;

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
     * @throws DigitalRunContestManagerException exception if there was a failure in the searching.
     */
    List < TrackContest > searchTrackContests(Filter filter) throws DigitalRunContestManagerException;

    /**
     * <p>
     * Creates a new <code>TrackContestType</code> entity in persistence.
     * </p>
     *
     * <p>
     * It accepts an instance that has not yet been created in persistence, and persists it.
     * </p>
     *
     * @param trackContestType The track contest type we want to create.
     *
     * @return The created track contest type (with possibly updated id).
     *
     * @throws IllegalArgumentException if the input is null.
     * @throws EntityExistsException if an entity with this id already exists.
     * @throws DigitalRunContestManagerException if there was a failure in creation.
     */
    TrackContestType createTrackContestType(TrackContestType trackContestType)
        throws DigitalRunContestManagerException;

    /**
     * <p>
     * Updates an existing <code>TrackContestType</code> entity in persistence.
     * </p>
     *
     * <p>
     * It accepts an instance that should already be present in persistence, and persist the update.
     * </p>
     *
     * @param trackContestType The track contest type we want to update.
     *
     * @throws IllegalArgumentException if the input is null.
     * @throws EntityNotFoundException if the input entity doesn't exist in persistence.
     * @throws DigitalRunContestManagerException if there was a failure in the update.
     */
    void updateTrackContestType(TrackContestType trackContestType)
        throws DigitalRunContestManagerException;

    /**
     * <p>
     * Removes an existing <code>TrackContestType</code> entity from persistence.
     * </p>
     *
     * <p>
     * It accepts an id of an instance that should already be present in persistence, and
     * removes that entity from the persistence.
     * </p>
     *
     * @param trackContestTypeId The track contest type (by id) we want to remove.
     *
     * @throws EntityNotFoundException if the input entity id doesn't exist in persistence.
     * @throws DigitalRunContestManagerException if there was a failure in the removal.
     */
    void removeTrackContestType(long trackContestTypeId) throws DigitalRunContestManagerException;

    /**
     * <p>
     * Fetches a <code>TrackContestType</code> entity from persistence based in the input id.
     * </p>
     *
     * @param trackContestTypeId The track contest type (by id) we want to fetch.
     *
     * @return The <code>TrackContestType</code> entity if found or null of not found.
     *
     * @throws DigitalRunContestManagerException if there was a failure in the fetching.
     */
    TrackContestType getTrackContestType(long trackContestTypeId) throws DigitalRunContestManagerException;

    /**
     * <p>
     * Fetches all available <code>TrackContestType</code> entities from persistence.
     * </p>
     *
     * @return a list of all the <code>TrackContestType</code> entities found in persistence,
     *         or an empty list of none were found.
     *
     * @throws DigitalRunContestManagerException if there was a failure in the fetching.
     */
    List < TrackContestType > getAllTrackContestTypes() throws DigitalRunContestManagerException;

    /**
     * <p>
     * Creates a new <code>TrackContestResultCalculator</code> entity in persistence.
     * </p>
     *
     * <p>
     * It accepts an instance that has not yet been created in persistence, and persists it.
     * </p>
     *
     * @param trackContestResultCalculator The track contest result calculator we want to create.
     *
     * @return The created track contest result calculator (with possibly updated id).
     *
     * @throws IllegalArgumentException if the input is null.
     * @throws EntityExistsException if an entity with this id already exists.
     * @throws DigitalRunContestManagerException if there was a failure in creation.
     */
    TrackContestResultCalculator createTrackContestResultCalculator(
        TrackContestResultCalculator trackContestResultCalculator) throws DigitalRunContestManagerException;

    /**
     * <p>
     * Updates an existing <code>TrackContestResultCalculator</code> entity in persistence.
     * </p>
     *
     * <p>
     * It accepts an instance that should already be present in persistence, and persist the update.
     * </p>
     *
     * @param trackContestResultCalculator The track contest result calculator we want to update.
     *
     * @throws IllegalArgumentException if the input is null.
     * @throws EntityNotFoundException if the input entity doesn't exist in persistence.
     * @throws DigitalRunContestManagerException if there was a failure in the update.
     */
    void updateTrackContestResultCalculator(TrackContestResultCalculator trackContestResultCalculator)
        throws DigitalRunContestManagerException;

    /**
     * <p>
     * Removes an existing <code>TrackContestResultCalculator</code> entity from persistence.
     * </p>
     *
     * <p>
     * It accepts an id of an instance that should already be present in persistence, and
     * removes that entity from the persistence.
     * </p>
     *
     * @param trackContestResultCalculatorId The track contest result calculator (by id) we want to remove.
     *
     * @throws EntityNotFoundException if the input entity id doesn't exist in persistence.
     * @throws DigitalRunContestManagerException if there was a failure in the removal.
     */
    void removeTrackContestResultCalculator(long trackContestResultCalculatorId)
        throws DigitalRunContestManagerException;

    /**
     * <p>
     * Fetches a <code>TrackContestResultCalculator</code> entity from persistence based in the input id.
     * </p>
     *
     * @param trackContestResultCalculator The track contest result calculator (by id) we want to fetch.
     *
     * @return The <code>TrackContestResultCalculator</code> entity if found or null of not found.
     *
     * @throws DigitalRunContestManagerException if there was a failure in the fetching.
     */
    TrackContestResultCalculator getTrackContestResultCalculator(long trackContestResultCalculator)
        throws DigitalRunContestManagerException;

    /**
     * <p>
     * Fetches all available <code>TrackContestResultCalculator</code> entities from persistence.
     * </p>
     *
     * @return a list of all the <code>TrackContestResultCalculator</code> entities found in persistence,
     *         or an empty list of none were found.
     *
     * @throws DigitalRunContestManagerException if there was a failure in the fetching.
     */
    List < TrackContestResultCalculator > getAllTrackContestResultCalculators()
        throws DigitalRunContestManagerException;
}
