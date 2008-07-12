/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.track;

import com.topcoder.service.digitalrun.entity.TrackType;

import java.util.List;


/**
 * <p>
 * This interface defines the contract for classes that will manage digital run <code>TrackType</code>entities into
 * persistence.
 * </p>
 *
 * <p>
 * The implementing classes will provide the following functionality:
 *
 * <ul>
 * <li>
 * -create a digital run track type entity into persistence
 * </li>
 * <li>
 * -update a digital run track type entity into persistence
 * </li>
 * <li>
 * -remove a digital run track type entity identified by its id from persistence
 * </li>
 * <li>
 * -get a digital run track type entity identified by its id from persistence
 * </li>
 * <li>
 * -get all digital run track type entities from persistence.
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
public interface DigitalRunTrackTypeDAO {
    /**
     * <p>
     * Creates a new TrackType entity into persistence. Returns the TrackType instance with id generated.
     * </p>
     *
     * @param trackType the type to be created
     *
     * @return the type with id set
     *
     * @throws IllegalArgumentException if argument is null or if its id is positive
     * @throws DigitalRunTrackManagerPersistenceException if any errors occur when accessing the persistent storage
     */
    public TrackType createTrackType(TrackType trackType)
        throws DigitalRunTrackManagerPersistenceException;

    /**
     * <p>
     * Updates the given TrackType instance into persistence.
     * </p>
     *
     * @param trackType the type to be updated
     *
     * @throws IllegalArgumentException if argument is null
     * @throws EntityNotFoundException if a TrackType entity with TrackType.id does not exist in the persistence
     * @throws DigitalRunTrackManagerPersistenceException if any errors occur when accessing the persistent storage
     */
    public void updateTrackType(TrackType trackType)
        throws DigitalRunTrackManagerPersistenceException, EntityNotFoundException;

    /**
     * <p>
     * Removes the TrackType entity identified by the given id from persistence.
     * </p>
     *
     * @param trackTypeId the id
     *
     * @throws IllegalArgumentException if argument less than 0
     * @throws EntityNotFoundException if there is no TrackType entity with the given id in persistence
     * @throws DigitalRunTrackManagerPersistenceException if any errors occur when accessing the persistent storage
     */
    public void removeTrackType(long trackTypeId)
        throws DigitalRunTrackManagerPersistenceException, EntityNotFoundException;

    /**
     * <p>
     * Gets the TrackType entity identified by the given id from persistence.
     * </p>
     *
     * @param trackTypeId the id
     *
     * @return the type identified by id
     *
     * @throws IllegalArgumentException if argument less than 0
     * @throws EntityNotFoundException if there is no TrackType entity  with the given id in persistence
     * @throws DigitalRunTrackManagerPersistenceException if any errors occur when accessing the persistent storage
     */
    public TrackType getTrackType(long trackTypeId)
        throws DigitalRunTrackManagerPersistenceException, EntityNotFoundException;

    /**
     * <p>
     * Gets all the TrackType entities from persistence. If there is no TrackType in persistence an empty list is
     * returned.
     * </p>
     *
     * @return a list containing all types or empty list if no type
     *
     * @throws DigitalRunTrackManagerPersistenceException if any errors occur when accessing the persistent storage
     */
    public List < TrackType > getAllTrackTypes() throws DigitalRunTrackManagerPersistenceException;
}
