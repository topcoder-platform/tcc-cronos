/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.contest.failuretests;

import com.topcoder.configuration.ConfigurationException;
import com.topcoder.search.builder.SearchBundle;
import com.topcoder.service.digitalrun.contest.BaseTestCase;
import com.topcoder.service.digitalrun.contest.ContestFilterFactory;
import com.topcoder.service.digitalrun.contest.MockHibernateSearchStrategy;
import com.topcoder.service.digitalrun.contest.PersistenceException;
import com.topcoder.service.digitalrun.contest.persistence.HibernateContestTrackDAO;

/**
 * <p>
 * Failure tests for <code>HibernateContestTrackDAO</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class HibernateContestTrackDAOFailureTests extends BaseTestCase {

    /**
     * <p>
     * Failure test for the constructor with the search bundle manager namespace is null.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testCtorWithNullNamespace() throws Exception {
        try {
            new HibernateContestTrackDAO(null, "TrackContestSearch");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the constructor with the search bundle manager namespace is empty.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testCtorWithEmptyNamespace() throws Exception {
        try {
            new HibernateContestTrackDAO(" ", "TrackContestSearch");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the constructor with the search bundle name is null.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testConstructorWithNullBundleName() throws Exception {
        try {
            new HibernateContestTrackDAO("DRSearchBundleManager", null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the constructor with the search bundle name is empty.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testConstructorWithEmptyBundleName() throws Exception {
        try {
            new HibernateContestTrackDAO("DRSearchBundleManager", " ");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the constructor with the search bundle manager namespace does not exist.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testConstructorWithNoSuchBundleNamespace() throws Exception {
        try {
            new HibernateContestTrackDAO("NoSuchNamespace", "TrackContestSearch");
            fail("ConfigurationException expected");
        } catch (ConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the constructor with the search bundle name does not exist.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testConstructorWithNoSuchBundleName() throws Exception {
        try {
            new HibernateContestTrackDAO("DRSearchBundleManager", "NoSuchBundle");
            fail("ConfigurationException expected");
        } catch (ConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the method <code>createTrackContest(TrackContest trackContest)</code> with the entity is
     * null.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testCreateTrackContestWithNullEntity() throws Exception {
        try {
            new HibernateContestTrackDAO("DRSearchBundleManager", "TrackContestSearch").createTrackContest(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the method <code>updateTrackContest(TrackContest trackContest)</code> with the entity is
     * null.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testUpdateTrackContestWithNullEntity() throws Exception {
        try {
            new HibernateContestTrackDAO("DRSearchBundleManager", "TrackContestSearch").updateTrackContest(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the method <code>searchTrackContests(Filter filter)</code> with the filter is null.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testSearchTrackContestsWithNullFilter() throws Exception {
        try {
            new HibernateContestTrackDAO("DRSearchBundleManager", "TrackContestSearch").searchTrackContests(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the method <code>searchTrackContests(Filter filter)</code> with PersistenceException occurs
     * while searching.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testSearchTrackContests_SearchException() throws Exception {
        try {
            HibernateContestTrackDAO dao = new HibernateContestTrackDAO("DRSearchBundleManager", "TrackContestSearch");
            MockHibernateSearchStrategy strategy = (MockHibernateSearchStrategy) ((SearchBundle) getField(
                    HibernateContestTrackDAO.class, dao, "searchBundle")).getSearchStrategy();
            strategy.setThrowErr(true);
            dao.searchTrackContests(ContestFilterFactory.createContestDescriptionEqualsFilter("description"));
            fail("PersistenceException expected");
        } catch (PersistenceException e) {
            // expected
        }
    }
}
