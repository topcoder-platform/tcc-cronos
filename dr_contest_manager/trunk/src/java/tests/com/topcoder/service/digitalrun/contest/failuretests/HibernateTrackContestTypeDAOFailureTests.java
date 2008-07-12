/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.contest.failuretests;

import com.topcoder.service.digitalrun.contest.BaseTestCase;
import com.topcoder.service.digitalrun.contest.persistence.HibernateTrackContestTypeDAO;

/**
 * <p>
 * Failure tests for <code>HibernateTrackContestTypeDAO</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class HibernateTrackContestTypeDAOFailureTests extends BaseTestCase {

    /**
     * <p>
     * Failure test for the method <code>createTrackContestType(TrackContestType trackContestType)</code> with the
     * entity is null, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testCreateTrackContestTypeWithNullEntity() throws Exception {
        try {
            new HibernateTrackContestTypeDAO().createTrackContestType(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the method <code>updateTrackContestType(TrackContestType trackContestType)</code> with the
     * entity is null, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testUpdateTrackContestTypeWithNullEntity() throws Exception {
        try {
            new HibernateTrackContestTypeDAO().updateTrackContestType(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
