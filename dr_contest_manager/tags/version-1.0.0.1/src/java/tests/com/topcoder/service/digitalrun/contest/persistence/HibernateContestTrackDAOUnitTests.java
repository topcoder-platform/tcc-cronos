/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.contest.persistence;

import com.topcoder.configuration.ConfigurationException;
import com.topcoder.search.builder.SearchBundle;
import com.topcoder.service.digitalrun.contest.BaseTestCase;
import com.topcoder.service.digitalrun.contest.ContestFilterFactory;
import com.topcoder.service.digitalrun.contest.MockHibernateSearchStrategy;
import com.topcoder.service.digitalrun.contest.PersistenceException;


/**
 * <p>
 * Unit tests for <code>HibernateContestTrackDAO</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class HibernateContestTrackDAOUnitTests extends BaseTestCase {

    /**
     * <p>
     * Instance of <code>HibernateContestTrackDAO</code> should be created.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testConstructor_Acc() throws Exception {
        HibernateContestTrackDAO dao = new HibernateContestTrackDAO("DRSearchBundleManager", "TrackContestSearch");
        assertNotNull(dao);
        assertTrue(((SearchBundle) getField(HibernateContestTrackDAO.class, dao, "searchBundle")).getSearchStrategy()
            instanceof MockHibernateSearchStrategy);
    }

    /**
     * <p>
     * Given search bundle manager namespace is null.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testConstructor_NullNamespace() throws Exception {
        try {
            new HibernateContestTrackDAO(null, "TrackContestSearch");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Given search bundle manager namespace is empty.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testConstructor_EmptyNamespace() throws Exception {
        try {
            new HibernateContestTrackDAO(" ", "TrackContestSearch");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Given search bundle name is null.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testConstructor_NullBundleName() throws Exception {
        try {
            new HibernateContestTrackDAO("DRSearchBundleManager", null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Given search bundle name is empty.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testConstructor_EmptyBundleName() throws Exception {
        try {
            new HibernateContestTrackDAO("DRSearchBundleManager", " ");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Given search bundle manager namespace does not exist.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testConstructor_NoSuchBundleNamespace() throws Exception {
        try {
            new HibernateContestTrackDAO("NoSuchNamespace", "TrackContestSearch");
            fail("ConfigurationException expected");
        } catch (ConfigurationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Given search bundle name does not exist.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testConstructor_NoSuchBundleName() throws Exception {
        try {
            new HibernateContestTrackDAO("DRSearchBundleManager", "NoSuchBundle");
            fail("ConfigurationException expected");
        } catch (ConfigurationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Given entity is null.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCreateTrackContest_NullEntity() throws Exception {
        try {
            new HibernateContestTrackDAO("DRSearchBundleManager", "TrackContestSearch").createTrackContest(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Given entity is null.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateTrackContest_NullEntity() throws Exception {
        try {
            new HibernateContestTrackDAO("DRSearchBundleManager", "TrackContestSearch").updateTrackContest(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Given filter is null.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testSearchTrackContests_NullFilter() throws Exception {
        try {
            new HibernateContestTrackDAO("DRSearchBundleManager", "TrackContestSearch").searchTrackContests(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * An empty list is returned.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testSearchTrackContests() throws Exception {
        HibernateContestTrackDAO dao = new HibernateContestTrackDAO("DRSearchBundleManager", "TrackContestSearch");

        assertEquals(0,
            dao.searchTrackContests(ContestFilterFactory.createContestDescriptionEqualsFilter("description")).size());
    }

    /**
     * <p>
     * Error occurs while searching.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testSearchTrackContests_SearchException() throws Exception {
        try {
            HibernateContestTrackDAO dao = new HibernateContestTrackDAO("DRSearchBundleManager", "TrackContestSearch");
            MockHibernateSearchStrategy strategy = (MockHibernateSearchStrategy)
                ((SearchBundle) getField(HibernateContestTrackDAO.class, dao, "searchBundle")).getSearchStrategy();
            strategy.setThrowErr(true);
            dao.searchTrackContests(ContestFilterFactory.createContestDescriptionEqualsFilter("description"));
            fail("PersistenceException expected");
        } catch (PersistenceException e) {
            // pass
        }
    }
}
