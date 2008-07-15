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
import com.topcoder.service.digitalrun.contest.TrackContestResultCalculatorDAO;
import com.topcoder.service.digitalrun.entity.TrackContestResultCalculator;


/**
 * <p>
 * This is a DAO interface contract for CRUD operations on the <code>TrackContestResultCalculator</code> entities.
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
public class HibernateTrackContestResultCalculatorDAO extends BaseHibernateDAO
    implements TrackContestResultCalculatorDAO {

    /**
     * <p>
     * The class name.
     * </p>
     */
    private static final String CLASS_NAME = HibernateTrackContestResultCalculatorDAO.class.getSimpleName();

    /**
     * <p>
     * Used to query all <code>TrackContestResultCalculator</code> entities.
     * </p>
     */
    private static final String ALL_QUERY = "select entity from TrackContestResultCalculator entity";

    /**
     * <p>
     * Default constructor.
     * </p>
     */
    public HibernateTrackContestResultCalculatorDAO() {
        // empty
    }

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
     * @throws PersistenceException if there was a failure in creation.
     */
    public TrackContestResultCalculator createTrackContestResultCalculator(
        TrackContestResultCalculator trackContestResultCalculator)
        throws EntityExistsException, PersistenceException {

        final String method = "createTrackContestResultCalculator(TrackContestResultCalculator)";

        Helper.logEnter(getLogger(), CLASS_NAME, method);

        try {
            checkNullWithLogging(trackContestResultCalculator, "TrackContestResultCalculator to be created");

            EntityManager em = getEntityManager();

            errorIfEntityExists(em, trackContestResultCalculator);

            manageEntity(em, trackContestResultCalculator, Action.CREATE);

            return trackContestResultCalculator;
        } finally {
            Helper.logExit(getLogger(), CLASS_NAME, method);
        }
    }

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
     * @throws PersistenceException if there was a failure in the update.
     */
    public void updateTrackContestResultCalculator(
        TrackContestResultCalculator trackContestResultCalculator)
        throws EntityNotFoundException, PersistenceException  {

        final String method = "updateTrackContestResultCalculator(TrackContestResultCalculator)";

        Helper.logEnter(getLogger(), CLASS_NAME, method);

        try {
            checkNullWithLogging(trackContestResultCalculator, "TrackContestResultCalculator to be updated");

            EntityManager em = getEntityManager();

            errorIfEntityNotExists(em, trackContestResultCalculator);

            manageEntity(em, trackContestResultCalculator, Action.UPDATE);

        } finally {
            Helper.logExit(getLogger(), CLASS_NAME, method);
        }
    }

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
     * @throws PersistenceException if there was a failure in the removal.
     */
    public void removeTrackContestResultCalculator(long trackContestResultCalculatorId)
        throws EntityNotFoundException, PersistenceException  {

        final String method = "removeTrackContestResultCalculator(long)";

        Helper.logEnter(getLogger(), CLASS_NAME, method);

        try {

            EntityManager em = getEntityManager();

            TrackContestResultCalculator current =
                errorIfEntityNotExists(em, TrackContestResultCalculator.class, trackContestResultCalculatorId);

            manageEntity(em, current, Action.DELETE);

        } finally {
            Helper.logExit(getLogger(), CLASS_NAME, method);
        }
    }

    /**
     * <p>
     * Fetches a <code>TrackContestResultCalculator</code> entity from persistence based in the input id.
     * </p>
     *
     * @param trackContestResultCalculator The track contest result calculator (by id) we want to fetch.
     *
     * @return The <code>TrackContestResultCalculator</code> entity if found or null of not found.
     *
     * @throws PersistenceException if there was a failure in the fetching.
     */
    public TrackContestResultCalculator getTrackContestResultCalculator(long trackContestResultCalculator)
        throws PersistenceException {

        final String method = "getTrackContestResultCalculator(long)";

        Helper.logEnter(getLogger(), CLASS_NAME, method);

        try {

            EntityManager em = getEntityManager();

            return findEntity(em, TrackContestResultCalculator.class, trackContestResultCalculator);

        } finally {
            Helper.logExit(getLogger(), CLASS_NAME, method);
        }
    }

    /**
     * <p>
     * Fetches all available <code>TrackContestResultCalculator</code> entities from persistence.
     * </p>
     *
     * @return a list of all the <code>TrackContestResultCalculator</code> entities found in persistence,
     *         or an empty list of none were found.
     *
     * @throws PersistenceException if there was a failure in the fetching.
     */
    @SuppressWarnings("unchecked")
    public List < TrackContestResultCalculator > getAllTrackContestResultCalculators() throws PersistenceException {

        final String method = "getAllTrackContestResultCalculators()";

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
