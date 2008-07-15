/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.track;

import com.topcoder.search.builder.filter.Filter;

import com.topcoder.service.digitalrun.entity.ProjectType;
import com.topcoder.service.digitalrun.entity.Track;

import java.util.List;


/**
 * <p>
 * This interface defines the contract for classes that will manage <code>Track</code> entities into persistence.
 * </p>
 *
 * <p>
 * The implementing classes will provide the following functionality:
 *
 * <ul>
 * <li>
 * -create a Track entity into persistence
 * </li>
 * <li>
 * -update a Track entity into persistence
 * </li>
 * <li>
 * -remove a Track entity identified by its id from persistence
 * </li>
 * <li>
 * -get a Track entity identified by its id from persistence
 * </li>
 * <li>
 * -search the Track entities in persistence that match a given filter
 * </li>
 * <li>
 * -get active Track entities
 * </li>
 * <li>
 * -add/remove ProjectType to/from Track.
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
public interface DigitalRunTrackDAO {
    /**
     * <p>
     * Creates a new Track entity into persistence. Returns the Track instance with id generated.
     * </p>
     *
     * @param track the entity to be created
     *
     * @return the entity with id set
     *
     * @throws IllegalArgumentException if argument is null or if its id large than 0
     * @throws DigitalRunTrackManagerPersistenceException if any errors occur when accessing the persistent storage
     */
    public Track createTrack(Track track) throws DigitalRunTrackManagerPersistenceException;

    /**
     * <p>
     * Updates the given Track instance into persistence.
     * </p>
     *
     * @param track the entity to be updated
     *
     * @throws IllegalArgumentException if argument is null
     * @throws EntityNotFoundException if a digital run points entity with Track.id does not exist in the persistence
     * @throws DigitalRunTrackManagerPersistenceException if any errors occur when accessing the persistent storage
     */
    public void updateTrack(Track track) throws DigitalRunTrackManagerPersistenceException, EntityNotFoundException;

    /**
     * <p>
     * Removes the Track entity identified by the given id from persistence.
     * </p>
     *
     * @param trackId the id
     *
     * @throws IllegalArgumentException if argument less than 0
     * @throws EntityNotFoundException if there is no Track entity with the given id in persistence
     * @throws DigitalRunTrackManagerPersistenceException if any errors occur when accessing the persistent storage
     */
    public void removeTrack(long trackId) throws DigitalRunTrackManagerPersistenceException, EntityNotFoundException;

    /**
     * <p>
     * Gets the Track entity identified by the given id from persistence.
     * </p>
     *
     * @param trackId the id
     *
     * @return the entity identified by the id
     *
     * @throws IllegalArgumentException if argument less than 0
     * @throws EntityNotFoundException if there is no Track entity  with the given id in persistence
     * @throws DigitalRunTrackManagerPersistenceException if any errors occur when accessing the persistent storage
     */
    public Track getTrack(long trackId) throws DigitalRunTrackManagerPersistenceException, EntityNotFoundException;

    /**
     * <p>
     * Searches the Track entities that match the given filter. If there is no such entity that matches the given
     * filter an empty list is returned.
     * </p>
     *
     * @param filter the filter used for searching
     *
     * @return the matching digital run points entities
     *
     * @throws IllegalArgumentException if argument is null
     * @throws DigitalRunTrackManagerPersistenceException if any errors occur when accessing the persistent storage
     */
    public List < Track > searchTracks(Filter filter) throws DigitalRunTrackManagerPersistenceException;

    /**
     * <p>
     * Gets the active tracks from persistence. An empty list will be returned if there are no active tracks.
     * </p>
     *
     * @return a list with active tracks or empty list if there are no active tracks
     *
     * @throws DigitalRunTrackManagerPersistenceException if any errors occur when accessing the persistent storage
     */
    public List < Track > getActiveTracks() throws DigitalRunTrackManagerPersistenceException;

    /**
     * <p>
     * Adds a project type to the given track.
     * </p>
     *
     * @param projectType the project type to be added to track
     * @param track the track
     *
     * @throws IllegalArgumentException if any argument is null
     * @throws EntityNotFoundException if the track or project type does not exist in persistence
     * @throws DigitalRunTrackManagerPersistenceException if any errors occur when accessing the persistent storage
     */
    public void addTrackProjectType(Track track, ProjectType projectType)
        throws DigitalRunTrackManagerPersistenceException, EntityNotFoundException;

    /**
     * <p>
     * Removes a project type from the given track.
     * </p>
     *
     * @param projectType the project type to be removed from track
     * @param track the track
     *
     * @throws IllegalArgumentException if any argument is null
     * @throws EntityNotFoundException if the track or project type does not exist in persistence
     * @throws DigitalRunTrackManagerPersistenceException if any errors occur when accessing the persistent storage
     */
    public void removeTrackProjectType(Track track, ProjectType projectType)
        throws DigitalRunTrackManagerPersistenceException, EntityNotFoundException;
}
