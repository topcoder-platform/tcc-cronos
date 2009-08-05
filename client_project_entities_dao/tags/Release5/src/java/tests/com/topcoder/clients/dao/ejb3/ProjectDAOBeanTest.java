/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.dao.ejb3;

import java.util.List;

import javax.persistence.EntityManager;

import com.topcoder.clients.dao.DAOConfigurationException;
import com.topcoder.clients.dao.EntityNotFoundException;
import com.topcoder.clients.dao.ProjectDAO;
import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.Project;

/**
 * <p>
 * Test class: <code>ProjectDAOBean</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ProjectDAOBeanTest extends TestBase {
    /**
     * An EntityManager instance used in tests.
     */
    private EntityManager entityManager;

    /**
     * <p>
     * An instance of <code>ProjectDAOBean</code> which is tested.
     * </p>
     */
    private ProjectDAOBean target = null;

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

        entityManager = getEntityManager();

        target = new ProjectDAOBean();
        target.setEntityManager(entityManager);
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     * <p>
     * Verifies <code>ProjectDAOBean</code> subclasses
     * <code>GenericEJB3DAO</code>.
     * </p>
     */
    public void testInheritance1() {
        assertTrue("ProjectDAOBean does not subclasses GenericEJB3DAO.",
                target instanceof GenericEJB3DAO);
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     * <p>
     * Verifies <code>ProjectDAOBean</code> implements <code>ProjectDAO</code>.
     * </p>
     */
    public void testInheritance2() {
        assertTrue("ProjectDAOBean does not implements ProjectDAO.",
                target instanceof ProjectDAO);
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     * <p>
     * Verifies <code>ProjectDAOBean</code> implements
     * <code>ProjectDAOLocal</code>.
     * </p>
     */
    public void testInheritance3() {
        assertTrue("ProjectDAOBean does not implements ProjectDAOLocal.",
                target instanceof ProjectDAOLocal);
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     * <p>
     * Verifies <code>ProjectDAOBean</code> implements
     * <code>ProjectDAORemote</code>.
     * </p>
     */
    public void testInheritance4() {
        assertTrue("ProjectDAOBean does not implements ProjectDAORemote.",
                target instanceof ProjectDAORemote);
    }

    /**
     * <p>
     * Tests the <code>com.topcoder.clients.dao.ejb3.ProjectDAOBean()</code>
     * for proper behavior.
     * </p>
     *
     * @throws Exception
     *                 to JUnit
     */
    public void testConstructor() throws Exception {
        assertNotNull("ProjectDAOBean() failed.", target);
    }

    /**
     * <p>
     * Tests the <code>retrieveById(Long, boolean)</code> for proper behavior.
     * </p>
     *
     * @throws Exception
     *                 to JUnit
     */
    public void test_retrieveById_Long_boolean_1() throws Exception {
        // prepare data
        Client client = createClient(100);
        createProjectWithClient(10, client);
        createProjectWithClient(12, client);
        createProjectWithClient(13, client);
        createProjectWithClient(14, client);

        setChildProject(10, 12);
        setChildProject(10, 13);

        Project res = target.retrieveById(10L, false);
        assertNotNull("should not return null.", res);
        assertNull("should return null child.", res.getChildProjects());
    }

    /**
     * <p>
     * Tests the <code>retrieveById(Long, boolean)</code> for proper behavior.
     * </p>
     *
     * @throws Exception
     *                 to JUnit
     */
    public void test_retrieveById_Long_boolean_2() throws Exception {
        // prepare data
        Client client = createClient(100);
        createProjectWithClient(10, client);
        createProjectWithClient(12, client);
        createProjectWithClient(13, client);
        createProjectWithClient(14, client);

        setChildProject(10, 12);
        setChildProject(10, 13);

        Project res = target.retrieveById(10L, true);
        // verify result
        assertNotNull("should not return null.", res);
        List<Project> children = res.getChildProjects();
        assertEquals("should include child.", 2, children.size());
    }

    /**
     * <p>
     * Tests the <code>retrieveById(Long, boolean)</code> for proper behavior.
     * </p>
     *
     * @throws Exception
     *                 to JUnit
     */
    public void test_retrieveById_Long_boolean_3() throws Exception {
        // prepare data
        Client client = createClient(100);
        createProjectWithClient(10, client);
        createProjectWithClient(12, client);
        createProjectWithClient(13, client);
        createProjectWithClient(14, client);

        setChildProject(10, 12);
        setChildProject(10, 13);
        setChildProject(12, 14);

        Project res = target.retrieveById(10L, true);
        assertNotNull("should not return null.", res);
        List<Project> children = res.getChildProjects();
        assertEquals("should include child, and exclude child-child.", 2, children.size());
    }

    /**
     * <p>
     * Tests the <code>retrieveById(Long, boolean)</code> for proper behavior.
     * IllegalArgumentException if id <= 0 or id is null.
     * </p>
     *
     * @throws Exception
     *                 to JUnit
     */
    public void test_retrieveById_Long_boolean_failure_1() throws Exception {
        try {
            target.retrieveById(0L, false);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Tests the <code>retrieveById(Long, boolean)</code> for proper behavior.
     * IllegalArgumentException if id <= 0 or id is null.
     * </p>
     *
     * @throws Exception
     *                 to JUnit
     */
    public void test_retrieveById_Long_boolean_failure_2() throws Exception {
        try {
            target.retrieveById(null, false);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Tests the <code>retrieveById(Long, boolean)</code> for proper behavior.
     * EntityNotFoundException if id is not found in the persistence.
     * </p>
     *
     * @throws Exception
     *                 to JUnit
     */
    public void test_retrieveById_Long_boolean_failure_3() throws Exception {
        try {
            target.retrieveById(1L, false);
            fail("EntityNotFoundException expected.");
        } catch (EntityNotFoundException e) {
            // ok
        }
    }

    /**
     * <p>
     * Tests the <code>retrieveById(Long, boolean)</code> for proper behavior.
     * DAOConfigurationException if the configured entityManager is invalid
     * (invalid means null here).
     * </p>
     *
     * @throws Exception
     *                 to JUnit
     */
    public void test_retrieveById_Long_boolean_failure_4() throws Exception {
        try {
            target = new ProjectDAOBean();
            target.retrieveById(10L, false);
            fail("DAOConfigurationException expected.");
        } catch (DAOConfigurationException e) {
            // ok
        }
    }

    /**
     * <p>
     * Tests the <code>retrieveById(Long, boolean)</code> for proper behavior.
     * IllegalArgumentException if id <= 0 or id is null.
     * </p>
     *
     * @throws Exception
     *                 to JUnit
     */
    public void test_retrieveById_Long_boolean_failure_5() throws Exception {
        try {
            target.retrieveById(-1L, false);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Tests the <code>retrieveAll(boolean)</code> for proper behavior.
     * </p>
     *
     * @throws Exception
     *                 to JUnit
     */
    public void test_retrieveAll_boolean_1() throws Exception {
        // prepare data
        Client client = createClient(100);
        createProjectWithClient(10, client);
        createProjectWithClient(12, client);
        createProjectWithClient(13, client);
        createProjectWithClient(14, client);

        setChildProject(10, 12);
        setChildProject(10, 13);

        List<Project> res = target.retrieveAll(false);
        assertEquals("The number of returned projects.", 4, res.size());
    }

    /**
     * <p>
     * Tests the <code>retrieveAll(boolean)</code> for proper behavior.
     * </p>
     *
     * @throws Exception
     *                 to JUnit
     */
    public void test_retrieveAll_boolean_2() throws Exception {
        // prepare data
        Client client = createClient(100);
        createProjectWithClient(10, client);
        createProjectWithClient(12, client);
        createProjectWithClient(13, client);
        createProjectWithClient(14, client);

        setChildProject(10, 12);
        setChildProject(10, 13);

        List<Project> res = target.retrieveAll(true);
        assertEquals("The number of returned projects.", 4, res.size());
    }

    /**
     * <p>
     * Tests the <code>retrieveAll(boolean)</code> for proper behavior.
     * DAOConfigurationException if the configured entityManager is invalid
     * (invalid means null here).
     * </p>
     *
     * @throws Exception
     *                 to JUnit
     */
    public void test_retrieveAll_boolean_failure_1() throws Exception {
        try {
            target = new ProjectDAOBean();
            target.retrieveAll(true);
            fail("DAOConfigurationException expected.");
        } catch (DAOConfigurationException e) {
            // ok
        }
    }
}
