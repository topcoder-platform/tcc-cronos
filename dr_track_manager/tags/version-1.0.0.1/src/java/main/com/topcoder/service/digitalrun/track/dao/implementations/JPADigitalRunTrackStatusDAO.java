/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.track.dao.implementations;

import com.topcoder.service.digitalrun.entity.TrackStatus;
import com.topcoder.service.digitalrun.track.DigitalRunTrackManagerPersistenceException;
import com.topcoder.service.digitalrun.track.DigitalRunTrackStatusDAO;
import com.topcoder.service.digitalrun.track.EntityNotFoundException;
import com.topcoder.service.digitalrun.track.Helper;

import java.util.List;


/**
 * <p>
 * This class implements <code>DigitalRunTrackStatusDAO</code> interface. It also extends the <code>AbstractDAO</code>
 * class. This class manages <code>TrackStatus</code> entities in a JPA persistence (currently the JPA persistence
 * will use Hibernate as a provider but any provider can be used). Each public method performs logging.
 * </p>
 *
 * <p>
 * This class is not completely thread safe because it doesn't manage transactions and it is also mutable. Anyway, the
 * intent is to use this implementation in a stateless session bean so there will be no thread safety issues generated
 * by this class since the container will ensure thread safety.
 * </p>
 *
 * @see com.topcoder.service.digitalrun.track.DigitalRunTrackStatusDAO
 * @see com.topcoder.service.digitalrun.track.dao.implementations.AbstractDAO
 * @author DanLazar, waits
 * @version 1.0
 */
public class JPADigitalRunTrackStatusDAO extends AbstractDAO implements DigitalRunTrackStatusDAO {
    /**
     * <p>
     * Default constructor.
     * </p>
     */
    public JPADigitalRunTrackStatusDAO() {
    }

    /**
     * <p>
     * Creates a new TrackStatus entity into persistence.
     * </p>
     *
     * @param trackStatus the entity to be created
     *
     * @return entity with id set
     *
     * @throws IllegalArgumentException if argument is null or if its id is positive
     * @throws DigitalRunTrackManagerPersistenceException if any errors occur when accessing the persistent storage
     */
    public TrackStatus createTrackStatus(TrackStatus trackStatus)
        throws DigitalRunTrackManagerPersistenceException {
        Helper.logEntranceInfo("createTrackStatus(TrackStatus)", getLogger());

        try {
            return persist(trackStatus, "TrackStatus");
        } finally {
            Helper.logExitInfo("createTrackStatus(TrackStatus)", getLogger());
        }
    }

    /**
     * <p>
     * Updates the given TrackStatus instance into persistence.
     * </p>
     *
     * @param trackStatus the entity to be updated
     *
     * @throws IllegalArgumentException if argument is null
     * @throws EntityNotFoundException if a TrackStatus entity with DigitalRunPoints.id does not exist in the
     *         persistence
     * @throws DigitalRunTrackManagerPersistenceException if any errors occur when accessing the persistent storage
     */
    public void updateTrackStatus(TrackStatus trackStatus)
        throws DigitalRunTrackManagerPersistenceException, EntityNotFoundException {
        Helper.logEntranceInfo("updateTrackStatus(TrackStatus)", getLogger());

        try {
            update(trackStatus, "TrackStatus");
        } finally {
            Helper.logExitInfo("updateTrackStatus(TrackStatus)", getLogger());
        }
    }

    /**
     * <p>
     * Removes the TrackStatus entity identified by the given id from persistence.
     * </p>
     *
     * @param trackStatusId the id that identified the entity to be removed
     *
     * @throws IllegalArgumentException if argument is negative
     * @throws EntityNotFoundException if there is no TrackStatus entity with the given id in persistence
     * @throws DigitalRunTrackManagerPersistenceException if any errors occur when accessing the persistent storage
     */
    public void removeTrackStatus(long trackStatusId)
        throws DigitalRunTrackManagerPersistenceException, EntityNotFoundException {
        Helper.logEntranceInfo("removeTrackStatus(trackStatusId)", getLogger());

        try {
            remove(TrackStatus.class, trackStatusId);
        } finally {
            Helper.logExitInfo("removeTrackStatus(trackStatusId)", getLogger());
        }
    }

    /**
     * <p>
     * Gets the TrackStatus entity identified by the given id from persistence.
     * </p>
     *
     * @param trackStatusId the id that identifies the entity top be retrieved
     *
     * @return the entity identified by the id
     *
     * @throws IllegalArgumentException if argument is negative
     * @throws EntityNotFoundException if there is no TrackStatus entity  with the given id in persistence
     * @throws DigitalRunTrackManagerPersistenceException if any errors occur when accessing the persistent storage
     */
    public TrackStatus getTrackStatus(long trackStatusId)
        throws DigitalRunTrackManagerPersistenceException, EntityNotFoundException {
        Helper.logEntranceInfo("getTrackStatus(trackStatusId)", getLogger());

        try {
            return find(TrackStatus.class, trackStatusId);
        } finally {
            Helper.logExitInfo("getTrackStatus(trackStatusId)", getLogger());
        }
    }

    /**
     * <p>
     * Gets all TrackStatus entities from persistence. If there is no TrackStatus in persistence an empty list is
     * returned.
     * </p>
     *
     * @return a list containing all statuses or an empty list if there is no such entity
     *
     * @throws DigitalRunTrackManagerPersistenceException if any errors occur when accessing the persistent storage
     */
    public List < TrackStatus > getAllTrackStatuses() throws DigitalRunTrackManagerPersistenceException {
        Helper.logEntranceInfo("getAllTrackStatuses()", getLogger());

        try {
            return findAll(TrackStatus.class);
        } finally {
            Helper.logExitInfo("getAllTrackStatuses()", getLogger());
        }
    }
}
