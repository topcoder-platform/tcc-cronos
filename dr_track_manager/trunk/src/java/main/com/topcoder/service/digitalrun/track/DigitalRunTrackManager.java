/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.track;

import com.topcoder.search.builder.filter.Filter;

import com.topcoder.service.digitalrun.entity.PointsCalculator;
import com.topcoder.service.digitalrun.entity.ProjectType;
import com.topcoder.service.digitalrun.entity.Track;
import com.topcoder.service.digitalrun.entity.TrackStatus;
import com.topcoder.service.digitalrun.entity.TrackType;

import java.util.List;


/**
 * <p>
 * This interface defines the contract for classes that will manage digital run track entities into persistence.
 * </p>
 *
 * <p>
 * The implementing classes will provide the following functionality:
 *
 * <ul>
 * <li>
 * -create a track/ track type /track status/points calculator into persistence
 * </li>
 * <li>
 * -update a track/ track type /track status/points calculator into persistence
 * </li>
 * <li>
 * -remove a track/ track type /track status/points calculator from persistence
 * </li>
 * <li>
 * -get a track/ track type /track status/points calculator/project type identified by its id from persistence
 * </li>
 * <li>
 * -search the track entities in persistence that match a given filter
 * </li>
 * <li>
 * -get all track type /track status/points calculator/project type from persistence
 * </li>
 * <li>
 * -get active tracks
 * </li>
 * <li>
 * -add/remove project type from track .
 * </li>
 * </ul>
 * </p>
 *
 * <p>
 * Implementations are required to be thread safe.
 * </p>
 * @author DanLazar, waits
 * @version 1.0
 */
public interface DigitalRunTrackManager {
    /**
     * <p>
     * Creates a new Track entity into persistence. Returns the Track instance with id generated.
     * </p>
     *
     * @param track the Track instance to be created into persistence
     *
     * @return the Track instance with id generated
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
     * @param track theTrack to be updated into persistence
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
     * @param trackId the id if the Track entity to be removed
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
     * @param trackId the id of the entity
     *
     * @return the Track entity identified by id
     *
     * @throws IllegalArgumentException if argument less than 0
     * @throws EntityNotFoundException if there is no Track entity  with the given id in persistence
     * @throws DigitalRunTrackManagerPersistenceException if any errors occur when accessing the persistent storage
     */
    public Track getTrack(long trackId) throws DigitalRunTrackManagerPersistenceException, EntityNotFoundException;

    /**
     * <p>
     * Gets the active tracks from persistence. An empty list will be returned if there are no active tracks.
     * </p>
     *
     * @return a list containing the active tracks
     *
     * @throws DigitalRunTrackManagerPersistenceException if any errors occur when accessing the persistent storage
     */
    public List < Track > getActiveTracks() throws DigitalRunTrackManagerPersistenceException;

    /**
     * <p>
     * Searches the Track entities that match the given filter. If there is no such entity that matches the given
     * filter an empty list is returned.
     * </p>
     *
     * @param filter the filter used for searching
     *
     * @return a list containing the matching Track entities or empty  list if no such entity
     *
     * @throws IllegalArgumentException if argument is null
     * @throws DigitalRunTrackManagerPersistenceException if any errors occur when accessing the persistent storage
     */
    public List < Track > searchTracks(Filter filter) throws DigitalRunTrackManagerPersistenceException;

    /**
     * <p>
     * Adds a project type to the given track.
     * </p>
     *
     * @param projectType the ProjectType entity
     * @param track the Track entity
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
     * @param projectType the ProjectType entity
     * @param track the Track entity
     *
     * @throws IllegalArgumentException if any argument is null
     * @throws EntityNotFoundException if the track or project type does not exist in persistence
     * @throws DigitalRunTrackManagerPersistenceException if any errors occur when accessing the persistent storage
     */
    public void removeTrackProjectType(Track track, ProjectType projectType)
        throws DigitalRunTrackManagerPersistenceException, EntityNotFoundException;

    /**
     * <p>
     * Creates a new TrackType entity into persistence. Returns the TrackType instance with id generated.
     * </p>
     *
     * @param trackType the TrackType instance to be created into persistence
     *
     * @return the TrackType instance with id generated
     *
     * @throws IllegalArgumentException if argument is null or if its id large than 0
     * @throws DigitalRunTrackManagerPersistenceException if any errors occur when accessing the persistent storage
     */
    public TrackType createTrackType(TrackType trackType)
        throws DigitalRunTrackManagerPersistenceException;

    /**
     * <p>
     * Updates the given TrackType instance into persistence.
     * </p>
     *
     * @param trackType the TrackType entity to be updated into persistence
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
     * @param trackTypeId the TrackType id to be removed
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
     * @return the TrackType identified by id
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
     * @return a list containing all track types or empty list if no track type
     *
     * @throws DigitalRunTrackManagerPersistenceException if any errors occur when accessing the persistent storage
     */
    public List < TrackType > getAllTrackTypes() throws DigitalRunTrackManagerPersistenceException;

    /**
     * <p>
     * Creates a new TrackStatus entity into persistence. Returns the TrackStatus instance with id generated.
     * </p>
     *
     * @param trackStatus the TrackStatus instance to be created into persistence
     *
     * @return the TrackStatus instance with id generated
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
     * @param trackStatus the TrackStatus entity to be updated into persistence
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
     * @param trackStatusId the id of the TrackStatus to be removed
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
     * @return a list containing all the track statuses or empty list if no track status
     *
     * @throws DigitalRunTrackManagerPersistenceException if any errors occur when accessing the persistent storage
     */
    public List < TrackStatus > getAllTrackStatuses() throws DigitalRunTrackManagerPersistenceException;

    /**
     * <p>
     * Creates a new PointsCalculator entity into persistence. Returns the PointsCalculator instance with id generated.
     * </p>
     *
     * @param pointsCalculator the PointsCalculator instance to be created into persistence
     *
     * @return the PointsCalculator instance with id generated
     *
     * @throws IllegalArgumentException if argument is null or if its id large than 0
     * @throws DigitalRunTrackManagerPersistenceException if any errors occur when accessing the persistent storage
     */
    public PointsCalculator createPointsCalculator(PointsCalculator pointsCalculator)
        throws DigitalRunTrackManagerPersistenceException;

    /**
     * <p>
     * Updates the given PointsCalculator instance into persistence.
     * </p>
     *
     * @param pointsCalculator the PointsCalculator to be updated into persistence
     *
     * @throws IllegalArgumentException if argument is null
     * @throws EntityNotFoundException if a PointsCalculator entity with DigitalRunPoints.id does not exist in the
     *         persistence
     * @throws DigitalRunTrackManagerPersistenceException if any errors occur when accessing the persistent storage
     */
    public void updatePointsCalculator(PointsCalculator pointsCalculator)
        throws DigitalRunTrackManagerPersistenceException, EntityNotFoundException;

    /**
     * <p>
     * Removes the PointsCalculator type entity identified by the given id from persistence.
     * </p>
     *
     * @param pointsCalculatorId the id of the PointsCalculator to be removed
     *
     * @throws IllegalArgumentException if argument less than 0
     * @throws EntityNotFoundException if there is no PointsCalculator entity with the given id in persistence
     * @throws DigitalRunTrackManagerPersistenceException if any errors occur when accessing the persistent storage
     */
    public void removePointsCalculator(long pointsCalculatorId)
        throws DigitalRunTrackManagerPersistenceException, EntityNotFoundException;

    /**
     * <p>
     * Gets the PointsCalculator entity identified by the given id from persistence.
     * </p>
     *
     * @param pointsCalculatorId the id
     *
     * @return the PointsCalculator identified by id
     *
     * @throws IllegalArgumentException if argument less than 0
     * @throws EntityNotFoundException if there is no PointsCalculator entity  with the given id in persistence
     * @throws DigitalRunTrackManagerPersistenceException if any errors occur when accessing the persistent storage
     */
    public PointsCalculator getPointsCalculator(long pointsCalculatorId)
        throws DigitalRunTrackManagerPersistenceException, EntityNotFoundException;

    /**
     * <p>
     * Gets all the PointsCalculator entities from persistence. If there is no PointsCalculator in persistence an empty
     * list is returned.
     * </p>
     *
     * @return a list containing all PointsCalculator entities or empty list if no PointsCalculator
     *
     * @throws DigitalRunTrackManagerPersistenceException if any errors occur when accessing the persistent storage
     */
    public List < PointsCalculator > getAllPointsCalculators()
        throws DigitalRunTrackManagerPersistenceException;

    /**
     * <p>
     * Gets the ProjectType entity identified by the given id from the persistence.
     * </p>
     *
     * @param projectTypeId the id
     *
     * @return the ProjectType identified by id
     *
     * @throws IllegalArgumentException id argument less than 0
     * @throws EntityNotFoundException if there is no ProjectType with the given id in persistence
     * @throws DigitalRunTrackManagerPersistenceException if any errors occur when accessing the persistent storage
     */
    public ProjectType getProjectType(long projectTypeId)
        throws DigitalRunTrackManagerPersistenceException, EntityNotFoundException;

    /**
     * <p>
     * Gets all ProjectTypes entities from persistence. If there is no ProjectType in persistence an empty list is
     * returned.
     * </p>
     *
     * @return a list containing all the project types or empty list if no project type
     *
     * @throws DigitalRunTrackManagerPersistenceException if any errors occur when accessing the persistent storage
     */
    public List < ProjectType > getAllProjectTypes() throws DigitalRunTrackManagerPersistenceException;
}
