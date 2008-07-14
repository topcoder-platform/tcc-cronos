/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.contest.failuretests;

import com.topcoder.service.digitalrun.contest.BaseTestCase;
import com.topcoder.service.digitalrun.contest.persistence.BaseHibernateDAO;

/**
 * <p>
 * Failure tests for <code>BaseHibernateDAO</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class BaseHibernateDAOFailureTests extends BaseTestCase {

    /**
     * <p>
     * Failure test for the method <code>BaseHibernateDAO#setUnitName(String)</code> with the unit name is null,
     * IllegalArgumentException.
     * </p>
     */
    public void testSetUnitNameWithNullUnitName() {
        BaseHibernateDAO dao = new MockBaseHibernateDAO();
        try {
            dao.setUnitName(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the method <code>BaseHibernateDAO#setUnitName(String)</code> with the unit name is empty,
     * IllegalArgumentException.
     * </p>
     */
    public void testSetUnitNameWithEmptyUnitName() {
        BaseHibernateDAO dao = new MockBaseHibernateDAO();
        try {
            dao.setUnitName(" ");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * This mocks class extends <code>BaseHibernateDAO</code> for test.
     * </p>
     *
     * @author TCSDEVELOPER
     * @version 1.0
     */
    private static class MockBaseHibernateDAO extends BaseHibernateDAO {
    }
}
