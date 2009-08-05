/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.dao.failuretests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.clients.dao.DAOConfigurationException;
import com.topcoder.clients.dao.EntityNotFoundException;
import com.topcoder.clients.dao.ejb3.ProjectDAOBean;

/**
 * Failure test for ProjectDAOBean class.
 *
 * @author AK_47
 * @version 1.0
 */
public class ProjectDAOBeanFailureTest extends TestCase {
    /**
     * <p>
     * An instance of <code>ProjectDAOBean</code> which is tested.
     * </p>
     */
    private ProjectDAOBean bean = null;

    /**
     * Aggregates all tests in this class.
     *
     * @return Test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(ProjectDAOBeanFailureTest.class);
    }

    /**
     * <p>
     * setUp() routine.
     * </p>
     *
     * @throws Exception
     *                 to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();

        bean = new ProjectDAOBean();
        bean.setEntityManager(TestHelper.getEntityManager());
    }

    /**
     * Failure test of <code>retrieveAll(boolean)</code> method.
     *
     * <p>
     * entityManager is null.
     * </p>
     *
     * <p>
     * Expect DAOConfigurationException.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testRetrieveAll_Null_EntityManager() throws Exception {
        try {
            bean = new ProjectDAOBean();

            bean.retrieveAll(true);

            fail("Expect DAOConfigurationException.");
        } catch (DAOConfigurationException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>retrieveById(Long, boolean)</code> method.
     *
     * <p>
     * id is null.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testRetrieveById_Null_Id() throws Exception {
        try {
            bean.retrieveById(null, false);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>retrieveById(Long, boolean)</code> method.
     *
     * <p>
     * id is negative.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testRetrieveById_Negative_Id() throws Exception {
        try {
            bean.retrieveById(-1L, false);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * <p>
     * Tests the <code>retrieveById(Long, boolean)</code> for proper
     * behavior.
     * </p>
     *
     * <p>
     * Expect EntityNotFoundException.
     * </p>
     *
     * @throws Exception
     *                 to JUnit
     */
    public void testRetrieveById_Invalid_Client() throws Exception {
        try {
            // Note: this id doesn't exist in db
            bean.retrieveById(1L, false);
            fail("EntityNotFoundException expected.");
        } catch (EntityNotFoundException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>retrieveById(Long, boolean)</code> method.
     *
     * <p>
     * entityManager is null.
     * </p>
     *
     * <p>
     * Expect DAOConfigurationException.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testRetrieveById_Null_EntityManager() throws Exception {
        try {
            bean = new ProjectDAOBean();

            bean.retrieveById(1L, false);

            fail("Expect DAOConfigurationException.");
        } catch (DAOConfigurationException e) {
            // expect
        }
    }

}
