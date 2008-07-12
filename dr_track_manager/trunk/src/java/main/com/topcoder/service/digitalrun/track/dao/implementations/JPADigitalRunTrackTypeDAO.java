/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.track.dao.implementations;

import com.topcoder.service.digitalrun.entity.TrackType;
import com.topcoder.service.digitalrun.track.DigitalRunTrackManagerPersistenceException;
import com.topcoder.service.digitalrun.track.DigitalRunTrackTypeDAO;
import com.topcoder.service.digitalrun.track.EntityNotFoundException;
import com.topcoder.service.digitalrun.track.Helper;

import java.util.List;


/**
 * <p>
 * This class implements <code>DigitalRunTrackTypeDAO</code> interface. It also extends the<code>AbstractDAO</code>
 * class. This class manages <code>TrackType </code>entities in a JPA persistence (currently the JPA persistence will
 * use Hibernate as a provider but any provider can be used). Each public method performs logging.
 * </p>
 *
 * <p>
 * This class is not completely thread safe because it doesn't manage transactions and it is also mutable. Anyway, the
 * intent is to use this implementation in a stateless session bean so there will be no thread safety issues generated
 * by this class since the container will ensure thread safety.
 * </p>
 *
 * @see com.topcoder.service.digitalrun.track.DigitalRunTrackTypeDAO
 * @see com.topcoder.service.digitalrun.track.dao.implementations.AbstractDAO
 * @author DanLazar, waits
 * @version 1.0
 */
public class JPADigitalRunTrackTypeDAO extends AbstractDAO implements DigitalRunTrackTypeDAO {
    /**
     * <p>
     * Default constructor.
     * </p>
     */
    public JPADigitalRunTrackTypeDAO() {
    }

    /**
     * <p>
     * Creates a new TrackType entity into persistence. Returns the TrackType instance with id generated.
     * </p>
     *
     * @param trackType the entity to be created
     *
     * @return persisted TrackType entity with id set
     *
     * @throws IllegalArgumentException if argument is null or if its id is positive
     * @throws DigitalRunTrackManagerPersistenceException if any errors occur when accessing the persistent storage
     */
    public TrackType createTrackType(TrackType trackType)
        throws DigitalRunTrackManagerPersistenceException {
        Helper.logEntranceInfo("createTrackType(TrackType)", getLogger());

        try {
            return persist(trackType, "TrackType");
        } finally {
            Helper.logExitInfo("createTrackType(TrackType)", getLogger());
        }
    }

    /**
     * <p>
     * Updates the given TrackType instance into persistence.
     * </p>
     *
     * @param trackType the entity to be updated
     *
     * @throws IllegalArgumentException if argument is null
     * @throws EntityNotFoundException if a TrackType entity with TrackType.id does not exist in the persistence
     * @throws DigitalRunTrackManagerPersistenceException if any errors occur when accessing the persistent storage
     */
    public void updateTrackType(TrackType trackType)
        throws EntityNotFoundException, DigitalRunTrackManagerPersistenceException {
        Helper.logEntranceInfo("updateTrackType(trackType)", getLogger());

        try {
            update(trackType, "TrackStatus");
        } finally {
            Helper.logExitInfo("updateTrackType(trackType)", getLogger());
        }
    }

    /**
     * <p>
     * Removes the TrackType entity identified by the given id from persistence.
     * </p>
     *
     * @param trackTypeId the id that identified the entity to be removed
     *
     * @throws IllegalArgumentException if argument is negative
     * @throws EntityNotFoundException if there is no TrackType entity with the given id in persistence
     * @throws DigitalRunTrackManagerPersistenceException if any errors occur when accessing the persistent storage
     */
    public void removeTrackType(long trackTypeId)
        throws EntityNotFoundException, DigitalRunTrackManagerPersistenceException {
        Helper.logEntranceInfo("removeTrackType(trackTypeId)", getLogger());

        try {
            remove(TrackType.class, trackTypeId);
        } finally {
            Helper.logExitInfo("removeTrackType(trackTypeId)", getLogger());
        }
    }

    /**
     * <p>
     * Gets the TrackType entity identified by the given id from persistence.
     * </p>
     *
     * @param trackTypeId the id that identifies the entity top be retrieved
     *
     * @return the entity identified by the id
     *
     * @throws IllegalArgumentException if argument is negative
     * @throws EntityNotFoundException if there is no TrackType entity  with the given id in persistence
     * @throws DigitalRunTrackManagerPersistenceException if any errors occur when accessing the persistent storage
     */
    public TrackType getTrackType(long trackTypeId)
        throws EntityNotFoundException, DigitalRunTrackManagerPersistenceException {
        Helper.logEntranceInfo("getTrackType(trackTypeId)", getLogger());

        try {
            return find(TrackType.class, trackTypeId);
        } finally {
            Helper.logExitInfo("getTrackType(trackTypeId)", getLogger());
        }
    }

    /**
     * <p>
     * Gets all the TrackType entities from persistence. If there is no TrackType in persistence an empty list is
     * returned.
     * </p>
     *
     * @return a list containing all types or an empty list if there is no such entity
     *
     * @throws DigitalRunTrackManagerPersistenceException if any errors occur when accessing the persistent storage
     */
    public List < TrackType > getAllTrackTypes() throws DigitalRunTrackManagerPersistenceException {
        Helper.logEntranceInfo("getAllTrackTypes()", getLogger());

        try {
            return findAll(TrackType.class);
        } finally {
            Helper.logExitInfo("getAllTrackTypes()", getLogger());
        }
    }
}
