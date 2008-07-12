/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.contest.persistence;

import java.util.List;

import javax.persistence.EntityManager;

import com.topcoder.service.digitalrun.contest.EntityExistsException;
import com.topcoder.service.digitalrun.contest.EntityNotFoundException;
import com.topcoder.service.digitalrun.contest.Helper;
import com.topcoder.service.digitalrun.contest.PersistenceException;
import com.topcoder.service.digitalrun.contest.TrackContestTypeDAO;
import com.topcoder.service.digitalrun.entity.TrackContestType;


/**
 * <p>
 * This is a DAO interface contract for CRUD operations on the <code>TrackContestType</code> entities.
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
public class HibernateTrackContestTypeDAO extends BaseHibernateDAO implements TrackContestTypeDAO {

    /**
     * <p>
     * The class name.
     * </p>
     */
    private static final String CLASS_NAME = HibernateTrackContestTypeDAO.class.getSimpleName();

    /**
     * <p>
     * Used to query all <code>TrackContestType</code> entities.
     * </p>
     */
    private static final String ALL_QUERY = "select entity from TrackContestType entity";

    /**
     * <p>
     * Default constructor.
     * </p>
     */
    public HibernateTrackContestTypeDAO() {
        // empty
    }

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
     * @throws PersistenceException if there was a failure in creation.
     */
    public TrackContestType createTrackContestType(TrackContestType trackContestType)
        throws EntityExistsException, PersistenceException {

        final String method = "createTrackContestType(TrackContestType)";

        Helper.logEnter(getLogger(), CLASS_NAME, method);

        try {
            checkNullWithLogging(trackContestType, "TrackContestType to be created");

            EntityManager em = getEntityManager();

            errorIfEntityExists(em, trackContestType);

            manageEntity(em, trackContestType, Action.CREATE);

            return trackContestType;
        } finally {
            Helper.logExit(getLogger(), CLASS_NAME, method);
        }
    }

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
     * @throws PersistenceException if there was a failure in the update.
     */
    public void updateTrackContestType(TrackContestType trackContestType)
        throws EntityNotFoundException, PersistenceException  {

        final String method = "updateTrackContestType(TrackContestType)";

        Helper.logEnter(getLogger(), CLASS_NAME, method);

        try {
            checkNullWithLogging(trackContestType, "TrackContestType to be updated");

            EntityManager em = getEntityManager();

            errorIfEntityNotExists(em, trackContestType);

            manageEntity(em, trackContestType, Action.UPDATE);

        } finally {
            Helper.logExit(getLogger(), CLASS_NAME, method);
        }
    }

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
     * @throws PersistenceException if there was a failure in the removal.
     */
    public void removeTrackContestType(long trackContestTypeId)
        throws EntityNotFoundException, PersistenceException  {

        final String method = "removeTrackContestType(long)";

        Helper.logEnter(getLogger(), CLASS_NAME, method);

        try {

            EntityManager em = getEntityManager();

            TrackContestType current = errorIfEntityNotExists(em, TrackContestType.class, trackContestTypeId);

            manageEntity(em, current, Action.DELETE);

        } finally {
            Helper.logExit(getLogger(), CLASS_NAME, method);
        }
    }

    /**
     * <p>
     * Fetches a <code>TrackContestType</code> entity from persistence based in the input id.
     * </p>
     *
     * @param trackContestTypeId The track contest type (by id) we want to fetch.
     *
     * @return The <code>TrackContestType</code> entity if found or null of not found.
     *
     * @throws PersistenceException if there was a failure in the fetching.
     */
    public TrackContestType getTrackContestType(long trackContestTypeId) throws PersistenceException {

        final String method = "getTrackContestType(long)";

        Helper.logEnter(getLogger(), CLASS_NAME, method);

        try {

            EntityManager em = getEntityManager();

            return findEntity(em, TrackContestType.class, trackContestTypeId);

        } finally {
            Helper.logExit(getLogger(), CLASS_NAME, method);
        }
    }

    /**
     * <p>
     * Fetches all available <code>TrackContestType</code> entities from persistence.
     * </p>
     *
     * @return a list of all the <code>TrackContestType</code> entities found in persistence,
     *         or an empty list of none were found.
     *
     * @throws PersistenceException if there was a failure in the fetching.
     */
    @SuppressWarnings("unchecked")
    public List < TrackContestType > getAllTrackContestTypes() throws PersistenceException {

        final String method = "getAllTrackContestTypes()";

        Helper.logEnter(getLogger(), CLASS_NAME, method);

        try {

            EntityManager em = getEntityManager();

            // Invoke unchecked conversion
            return queryEntities(em, ALL_QUERY);

        } finally {
            Helper.logExit(getLogger(), CLASS_NAME, method);
        }
    }
}
