/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.contest.failuretests;

import com.topcoder.service.digitalrun.contest.BaseTestCase;
import com.topcoder.service.digitalrun.contest.persistence.HibernateTrackContestResultCalculatorDAO;

/**
 * <p>
 * Failure tests for <code>HibernateTrackContestResultCalculatorDAO</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class HibernateTrackContestResultCalculatorDAOFailureTests extends BaseTestCase {

    /**
     * <p>
     * Failure test for the method
     * <code>createTrackContestResultCalculator(TrackContestResultCalculator trackContestResultCalculator)</code>
     * with the entity is null, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testCreateTrackContestResultCalculatorWithNullEntity() throws Exception {
        try {
            new HibernateTrackContestResultCalculatorDAO().createTrackContestResultCalculator(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the method
     * <code>updateTrackContestResultCalculator(TrackContestResultCalculator trackContestResultCalculator)</code>
     * with the entity is null, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testUpdateTrackContestResultCalculator_NullEntity() throws Exception {
        try {
            new HibernateTrackContestResultCalculatorDAO().updateTrackContestResultCalculator(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

}
