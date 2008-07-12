/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.track;

import com.topcoder.service.digitalrun.entity.TrackStatus;

import java.util.List;


/**
 * <p>
 * This interface defines the contract for classes that will manage digital run <code>TrackStatus</code> entities into
 * persistence.
 * </p>
 *
 * <p>
 * The implementing classes will provide the following functionality:
 *
 * <ul>
 * <li>
 * -create a digital run TrackStatus entity into persistence
 * </li>
 * <li>
 * -update a digital run TrackStatus entity into persistence
 * </li>
 * <li>
 * -remove a digital run TrackStatus entity identified by its id from persistence
 * </li>
 * <li>
 * -get a digital run TrackStatus entity identified by its id from persistence
 * </li>
 * <li>
 * -get all digital run TrackStatus entities from persistence.
 * </li>
 * </ul>
 * </p>
 *
 * <p>
 * Implementations are not required to be thread safe.
 * </p>
 * @author DanLazar, waits
 * @version 1.0
 */
public interface DigitalRunTrackStatusDAO {
    /**
     * <p>
     * Creates a new TrackStatus entity into persistence. Returns the TrackStatus instance with id generated.
     * </p>
     *
     * @param trackStatus the TrackStatus to be created
     *
     * @return the TrackStatus with id set
     *
     * @throws IllegalArgumentException if argument is null or if its id large than 0
     * @throws DigitalRunTrackManagerPersistenceException if any errors occur when accessing the persistent storage
     */
    public TrackStatus createTrackStatus(TrackStatus trackStatus)
        throws DigitalRunTrackManagerPersistenceException;

    /**
     * <p>
     * Updates the given TrackStatus instance into persistence.
     * </p>
     *
     * @param trackStatus the TrackStatus to be updated
     *
     * @throws IllegalArgumentException if argument is null
     * @throws EntityNotFoundException if a TrackStatus entity with DigitalRunPoints.id does not exist in the
     *         persistence
     * @throws DigitalRunTrackManagerPersistenceException if any errors occur when accessing the persistent storage
     */
    public void updateTrackStatus(TrackStatus trackStatus)
        throws DigitalRunTrackManagerPersistenceException, EntityNotFoundException;

    /**
     * <p>
     * Removes the TrackStatus entity identified by the given id from persistence.
     * </p>
     *
     * @param trackStatusId the id of TrackStatus to be removed
     *
     * @throws IllegalArgumentException if argument less than 0
     * @throws EntityNotFoundException if there is no TrackStatus entity with the given id in persistence
     * @throws DigitalRunTrackManagerPersistenceException if any errors occur when accessing the persistent storage
     */
    public void removeTrackStatus(long trackStatusId)
        throws DigitalRunTrackManagerPersistenceException, EntityNotFoundException;

    /**
     * <p>
     * Gets the TrackStatus entity identified by the given id from persistence.
     * </p>
     *
     * @param trackStatusId the id
     *
     * @return the TrackStatus identified by id
     *
     * @throws IllegalArgumentException if argument less than 0
     * @throws EntityNotFoundException if there is no TrackStatus entity  with the given id in persistence
     * @throws DigitalRunTrackManagerPersistenceException if any errors occur when accessing the persistent storage
     */
    public TrackStatus getTrackStatus(long trackStatusId)
        throws DigitalRunTrackManagerPersistenceException, EntityNotFoundException;

    /**
     * <p>
     * Gets all TrackStatus entities from persistence. If there is no TrackStatus in persistence an empty list is
     * returned.
     * </p>
     *
     * @return all track statuses or empty list if no track statuses
     *
     * @throws DigitalRunTrackManagerPersistenceException if any errors occur when accessing the persistent storage
     */
    public List < TrackStatus > getAllTrackStatuses() throws DigitalRunTrackManagerPersistenceException;
}
