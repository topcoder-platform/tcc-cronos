/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.contest.persistence;

import com.topcoder.service.digitalrun.contest.BaseTestCase;

/**
 * <p>
 * Unit tests for <code>HibernateTrackContestTypeDAO</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class HibernateTrackContestTypeDAOUnitTests extends BaseTestCase {

    /**
     * <p>
     * Instance of <code>HibernateTrackContestTypeDAO</code> should be created.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testConstructor_Acc() throws Exception {
        assertNotNull(new HibernateTrackContestTypeDAO());
    }

    /**
     * <p>
     * Given entity is null.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCreateTrackContestType_NullEntity() throws Exception {
        try {
            new HibernateTrackContestTypeDAO().createTrackContestType(null);
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
    public void testUpdateTrackContestType_NullEntity() throws Exception {
        try {
            new HibernateTrackContestTypeDAO().updateTrackContestType(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }
}
