/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.contest.persistence;

import com.topcoder.service.digitalrun.contest.BaseTestCase;

/**
 * <p>
 * Unit tests for <code>HibernateTrackContestResultCalculatorDAO</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class HibernateTrackContestResultCalculatorDAOUnitTests extends BaseTestCase {

    /**
     * <p>
     * Instance of <code>HibernateTrackContestResultCalculatorDAO</code> should be created.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testConstructor_Acc() throws Exception {
        assertNotNull(new HibernateTrackContestResultCalculatorDAO());
    }

    /**
     * <p>
     * Given entity is null.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCreateTrackContestResultCalculator_NullEntity() throws Exception {
        try {
            new HibernateTrackContestResultCalculatorDAO().createTrackContestResultCalculator(null);
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
    public void testUpdateTrackContestResultCalculator_NullEntity() throws Exception {
        try {
            new HibernateTrackContestResultCalculatorDAO().updateTrackContestResultCalculator(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

}
