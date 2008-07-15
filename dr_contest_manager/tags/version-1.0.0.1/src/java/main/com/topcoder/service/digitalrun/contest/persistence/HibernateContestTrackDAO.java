/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.contest.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import com.topcoder.configuration.ConfigurationException;
import com.topcoder.search.builder.SearchBuilderException;
import com.topcoder.search.builder.SearchBundle;
import com.topcoder.search.builder.SearchBundleManager;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.service.digitalrun.contest.EntityExistsException;
import com.topcoder.service.digitalrun.contest.EntityNotFoundException;
import com.topcoder.service.digitalrun.contest.Helper;
import com.topcoder.service.digitalrun.contest.PersistenceException;
import com.topcoder.service.digitalrun.contest.TrackContestDAO;
import com.topcoder.service.digitalrun.entity.TrackContest;
import com.topcoder.util.errorhandling.BaseException;
import com.topcoder.util.errorhandling.ExceptionUtils;


/**
 * <p>
 * This is a DAO implementation of the contract for CRUD operations on the <code>TrackContest</code> entities.
 * </p>
 *
 * <p>
 * This is a JPA based and Hibernate backed implementation.
 * </p>
 *
 * <p>
 *     <strong>Logging:</strong>
 *     All defined operations will be logged. Logging strategy:
 *     <ol>
 *         <li>Entrance and exit of methods will be logged at the INFO level</li>
 *         <li>Exception will be logged at the ERROR level</li>
 *     </ol>
 * </p>
 *
 * <p>
 *     <strong>Thread Safety:</strong>
 *     Implementation is conditionally thread safe. Injection setters MUST be only called once and after construction.
 * </p>
 *
 * @author AleaActaEst, TCSDEVELOPER
 * @version 1.0
 */
public class HibernateContestTrackDAO extends BaseHibernateDAO implements TrackContestDAO {

    /**
     * <p>
     * The class name.
     * </p>
     */
    private static final String CLASS_NAME = HibernateContestTrackDAO.class.getSimpleName();

    /**
     * <p>
     * Represents the <code>SearchBundle</code> that will be used to perform searches with filter.
     * </p>
     *
     * <p>
     * This is initialized through the constructor, and never changed afterwards.
     * </p>
     *
     * <p>
     * Cannot be null.
     * </p>
     *
     * <p>
     * It is used in the <code>searchTrackContests(Filter)</code> method.
     * </p>
     */
    private final SearchBundle searchBundle;

    /**
     * <p>
     * The only initialization constructor. Will initialize the search bundle for this DAO.
     * </p>
     *
     * @param bundleNamespace Search Bundle namespace, not null, not empty.
     * @param bundle Bundle name, not null, not empty.
     *
     * @throws IllegalArgumentException if any input is null or an empty string.
     * @throws ConfigurationException if there were issues with configuring this DAO (i.e. we could not initialize
     *         the search bundle).
     */
    public HibernateContestTrackDAO(String bundleNamespace, String bundle) throws ConfigurationException {
        ExceptionUtils.checkNullOrEmpty(bundleNamespace, null, null, "Bundle namespace should not be null or empty.");
        ExceptionUtils.checkNullOrEmpty(bundle, null, null, "Bundle name should not be null or empty.");

        try {
            searchBundle = new SearchBundleManager(bundleNamespace).getSearchBundle(bundle);
        } catch (BaseException e) {
            throw new ConfigurationException("Error occurs while initializing search bundle.", e);
        }

        // SearchBundleManager.getSearchBundle() may return null
        if (searchBundle == null) {
            throw new ConfigurationException("There is no search bundle with name: " + bundle);
        }
    }

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
    public TrackContest createTrackContest(TrackContest trackContest)
        throws PersistenceException, EntityExistsException {

        final String method = "createTrackContest(TrackContest)";

        Helper.logEnter(getLogger(), CLASS_NAME, method);

        try {
            checkNullWithLogging(trackContest, "TrackContest to be created");

            EntityManager em = getEntityManager();

            errorIfEntityExists(em, trackContest);

            manageEntity(em, trackContest, Action.CREATE);

            return trackContest;
        } finally {
            Helper.logExit(getLogger(), CLASS_NAME, method);
        }
    }

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
    public void updateTrackContest(TrackContest trackContest)
        throws PersistenceException, EntityNotFoundException {

        final String method = "updateTrackContest(TrackContest)";

        Helper.logEnter(getLogger(), CLASS_NAME, method);

        try {
            checkNullWithLogging(trackContest, "TrackContest to be updated");

            EntityManager em = getEntityManager();

            errorIfEntityNotExists(em, trackContest);

            manageEntity(em, trackContest, Action.UPDATE);

        } finally {
            Helper.logExit(getLogger(), CLASS_NAME, method);
        }
    }

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
    public void removeTrackContest(long trackContestId)
        throws PersistenceException, EntityNotFoundException {

        final String method = "removeTrackContest(long)";

        Helper.logEnter(getLogger(), CLASS_NAME, method);

        try {

            EntityManager em = getEntityManager();

            TrackContest current = errorIfEntityNotExists(em, TrackContest.class, trackContestId);

            manageEntity(em, current, Action.DELETE);

        } finally {
            Helper.logExit(getLogger(), CLASS_NAME, method);
        }
    }

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
    public TrackContest getTrackContest(long trackContestId) throws PersistenceException {

        final String method = "getTrackContest(long)";

        Helper.logEnter(getLogger(), CLASS_NAME, method);

        try {

            EntityManager em = getEntityManager();

            return findEntity(em, TrackContest.class, trackContestId);

        } finally {
            Helper.logExit(getLogger(), CLASS_NAME, method);
        }
    }

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
    @SuppressWarnings("unchecked")
    public List < TrackContest > searchTrackContests(Filter filter) throws PersistenceException {

        final String method = "searchTrackContests(Filter)";

        Helper.logEnter(getLogger(), CLASS_NAME, method);

        try {

            checkNullWithLogging(filter, "Filter");

            List < TrackContest > result = (List) searchBundle.search(filter);

            return result == null ? new ArrayList() : result;

        } catch (SearchBuilderException e) {
            throw new PersistenceException(
                "Error occurs while searching track contests.", Helper.logException(getLogger(), e));
        } finally {
            Helper.logExit(getLogger(), CLASS_NAME, method);
        }
    }
}
