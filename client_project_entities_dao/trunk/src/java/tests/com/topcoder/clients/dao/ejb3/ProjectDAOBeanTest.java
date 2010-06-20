/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.dao.ejb3;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.topcoder.clients.dao.DAOConfigurationException;
import com.topcoder.clients.dao.EntityNotFoundException;
import com.topcoder.clients.dao.ProjectDAO;
import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.Project;
import com.topcoder.clients.model.ProjectContestFee;
import com.topcoder.util.idgenerator.IDGenerator;
import com.topcoder.util.idgenerator.IDGeneratorFactory;

/**
 * <p>
 * Test class: <code>ProjectDAOBean</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.2
 */
public class ProjectDAOBeanTest extends TestBase {
    /**
     * An EntityManager instance used in tests.
     */
    private EntityManager em;

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

        em = getEntityManager();

        target = new ProjectDAOBean();
        target.setEntityManager(em);
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
        assertTrue("ProjectDAOBean does not subclasses GenericEJB3DAO.", target instanceof GenericEJB3DAO<?, ?>);
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
     * </p>
     *
     * @throws Exception
     *                 to JUnit
     * @since 1.2
     */
    public void test_retrieveById_Long_boolean_4() throws Exception {
        // prepare data
        Client client = createClient(100);
        createProjectWithInitialBudget(10, client, 100.0);
        createProjectWithClient(12, client);
        createProjectWithClient(13, client);
        createProjectWithClient(14, client);

        setChildProject(10, 12);
        setChildProject(10, 13);
        setChildProject(12, 14);

        Project res = target.retrieveById(10L, true);
        assertNotNull("should not return null.", res);
        assertEquals("The budget is not retrieved from database.", 100.0, res.getBudget(), 1e-5);
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

    /**
     * <p>
     * Tests the <code>retrieveAllProjectsOnly()</code> method.
     * </p>
     * @throws Exception  pass any unexpected exception to JUnit.
     */
    public void test_retrieveAllProjectsOnly1() throws Exception {
        List<Project> projects = target.retrieveAllProjectsOnly();

        assertNotNull("the projects list should not be null", projects);
        assertEquals("The list should be empty.", 0, projects.size());
    }

    /**
     * <p>
     * Tests the <code>retrieveAllProjectsOnly()</code> method.
     * </p>
     * @throws Exception  pass any unexpected exception to JUnit.
     */
    public void test_retrieveAllProjectsOnly2() throws Exception {
        // prepare data
        Client client1 = createClient(100);
        createProjectWithClient(10, client1);
        createProjectWithClient(12, client1);
        createProjectWithClient(13, client1);
        createProjectWithClient(14, client1);


        List<Project> projects = target.retrieveAllProjectsOnly();

        assertNotNull("the projects list should not be null", projects);
        assertEquals("The list should be 4 elements.", 4, projects.size());
    }

    /**
     * <p>
     * Tests the <code>retrieveAllProjectsOnly()</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     * @since 1.2
     */
    public void test_retrieveAllProjectsOnly3() throws Exception {
        // prepare data
        Client client1 = createClient(100);
        createProjectWithInitialBudget(10, client1, 100.0);

        List<Project> projects = target.retrieveAllProjectsOnly();

        assertNotNull("the projects list should not be null", projects);
        assertEquals("The list should be 1 elements.", 1, projects.size());
        Project project = projects.get(0);
        assertNotNull("The project should not be null", project);
        assertEquals("The budget is not retrieved.", 100.0, project.getBudget(), 1e-5);
    }

    /**
     * <p>
     * Tests the <code>getContestFeesByProject()</code> method.
     * </p>
     * @throws Exception  pass any unexpected exception to JUnit.
     */
    public void test_getContestFeesByProject() throws Exception {
        List<ProjectContestFee> fees = target.getContestFeesByProject(1);

        assertNotNull("the projects list should not be null", fees);
        assertEquals("The list should be empty.", 0, fees.size());
    }


    /**
     * <p>
     * Tests the <code>saveContestFees(List, long)</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void test_saveContestFees_contains_null() throws Exception {
        List<ProjectContestFee> fees = new ArrayList<ProjectContestFee>();
        fees.add(null);
        try {
            target.saveContestFees(fees, 1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the <code>saveContestFees(List, long)</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void test_saveContestFees_inconsistent_projectId() throws Exception {
        ProjectContestFee projectContestFee = new ProjectContestFee();
        projectContestFee.setContestFee(100);
        projectContestFee.setContestType(111);
        projectContestFee.setCreateDate(new Date());
        projectContestFee.setCreateUsername("ivern");
        projectContestFee.setDeleted(false);
        projectContestFee.setModifyDate(new Date());
        projectContestFee.setModifyUsername("ivern");
        projectContestFee.setName("Test");
        projectContestFee.setProjectId(1);
        projectContestFee.setStudio(true);

        List<ProjectContestFee> fees = new ArrayList<ProjectContestFee>();
        fees.add(projectContestFee);
        try {
            target.saveContestFees(fees, 10);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }


    /**
     * <p>
     * Tests the <code>saveContestFees(List, long)</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void test_saveContestFees() throws Exception {
        ProjectContestFee projectContestFee = new ProjectContestFee();
        projectContestFee.setContestFee(100);
        projectContestFee.setContestType(111);
        projectContestFee.setCreateDate(new Date());
        projectContestFee.setCreateUsername("ivern");
        projectContestFee.setDeleted(false);
        projectContestFee.setModifyDate(new Date());
        projectContestFee.setModifyUsername("ivern");
        projectContestFee.setName("Test");
        projectContestFee.setProjectId(1);
        projectContestFee.setStudio(true);

        List<ProjectContestFee> fees = new ArrayList<ProjectContestFee>();
        fees.add(projectContestFee);
        target.saveContestFees(fees, 1);
    }

    /**
     * <p>
     * Tests the <code>saveContestFees(List, long)</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void test_saveContestFees1() throws Exception {
        // just delete the related entities.
        target.saveContestFees(null, 1);
    }


    /**
     * <p>
     * Tests the <code>searchProjectsByClientName(String)</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void test_searchProjectsByClientName() throws Exception {

        // prepare data
        Client client = createClient("tc", 100);
        createProjectWithClient(10, client);
        createProjectWithClient(12, client);
        createProjectWithClient(13, client);

        Client client2 = createClient("microsoft", 101);
        createProjectWithClient(14, client2);

        List<Project> projs = target.searchProjectsByClientName(client.getName());
        assertEquals("The list size is incorrect.",  3, projs.size());
    }


    /**
     * <p>
     * Tests the <code>getProjectsByUser(String)</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void test_getProjectsByUser() throws Exception {
        List<Project> projs = target.getProjectsByUser("ivern");
        assertEquals("The list size is incorrect.", 0, projs.size());
    }


    /**
     * <p>
     * Tests the <code>searchProjectsByProjectName(String)</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void test_searchProjectsByProjectName_invalidName1() throws Exception {
        try {
            target.searchProjectsByProjectName("TopCoder*");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the <code>searchProjectsByProjectName(String)</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void test_searchProjectsByProjectName_invalidName2() throws Exception {
        try {
            target.searchProjectsByProjectName("TopCoder%");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }


    /**
     * <p>
     * Tests the <code>searchProjectsByProjectName(String)</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void test_searchProjectsByProjectName() throws Exception {
        List<Project> projs = target.searchProjectsByProjectName("Project");
        assertEquals("The list size is incorrect.", 0, projs.size());
    }

    /**
     * <p>
     * Tests the <code>checkClientProjectPermission(String, long)</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void test_checkClientProjectPermission() throws Exception {
        assertFalse(target.checkClientProjectPermission("ivern", 1000));
    }

    /**
     * <p>
     * Tests the <code>checkPoNumberPermission(String, long)</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void test_checkPoNumberPermission() throws Exception {
        assertFalse(target.checkPoNumberPermission("ivern", "1000"));
    }

    /**
     * <p>
     * Tests the <code>addUserToBillingProjects(String, long[])</code> method.
     * </p>
     * <p>
     * If the userName is null, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void test_addUserToBillingProjects_userName_null() throws Exception {
        try {
            target.addUserToBillingProjects(null, new long[] {1});

            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the <code>addUserToBillingProjects(String, long[])</code> method.
     * </p>
     * <p>
     * If the userName is empty, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void test_addUserToBillingProjects_userName_empty() throws Exception {
        try {
            target.addUserToBillingProjects("", new long[] {1});

            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the <code>addUserToBillingProjects(String, long[])</code> method.
     * </p>
     * <p>
     * If the userName is trimmed empty, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void test_addUserToBillingProjects_userName_trimmedEmpty() throws Exception {
        try {
            target.addUserToBillingProjects("  ", new long[] {1});

            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the <code>addUserToBillingProjects(String, long[])</code> method.
     * </p>
     * <p>
     * If the billingProjectIds is null, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void test_addUserToBillingProjects_billingProjectIds_null() throws Exception {
        try {
            target.addUserToBillingProjects("ivern", null);

            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the <code>addUserToBillingProjects(String, long[])</code> method.
     * </p>
     * <p>
     * If the billingProjectIds is empty, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void test_addUserToBillingProjects_billingProjectIds_empty() throws Exception {
        try {
            target.addUserToBillingProjects("ivern", new long[0]);

            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the <code>addUserToBillingProjects(String, long[])</code> method.
     * </p>
     * <p>
     * If the billingProjectIds contains 0, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void test_addUserToBillingProjects_billingProjectIds_containsZero() throws Exception {
        try {
            target.addUserToBillingProjects("ivern", new long[] {0, 1});

            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the <code>addUserToBillingProjects(String, long[])</code> method.
     * </p>
     * <p>
     * If the billingProjectIds contains negative, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void test_addUserToBillingProjects_billingProjectIds_containsNegative() throws Exception {
        try {
            target.addUserToBillingProjects("ivern", new long[] {1, -1});

            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }


    /**
     * <p>
     * Tests the <code>addUserToBillingProjects(String, long[])</code> method.
     * </p>
     * <p>
     * If the user is not found, a new user_account is added, and project management relations are added.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    @SuppressWarnings("unchecked")
    public void test_addUserToBillingProjects_accuracy() throws Exception {
        TestHelper.clearConfig();
        TestHelper.addConfig("test_files/com/topcoder/db/connectionfactory/DBConnectionFactoryImpl.xml");

        // prepare data
        // dummy project
        createCompany(1);
        Client client1 = createClient(100);
        createProjectWithClient(10, client1);
        createProjectWithClient(12, client1);
        createProjectWithClient(13, client1);
        createProjectWithClient(14, client1);

        target.addUserToBillingProjects("ivern", new long[] {10, 12});

        EntityManager entityManager = getEntityManager();

        Query query = entityManager.createNativeQuery("SELECT user_account_id FROM user_account"
                + " WHERE user_name = :userName");
        query.setParameter("userName", "ivern");

        List res = query.getResultList();

        assertEquals("The result list should contain one element.", 1, res.size());

        Long userAccountId = Long.parseLong(res.get(0).toString());

        query = entityManager.createNativeQuery("SELECT project_id FROM project_manager"
                + " WHERE user_account_id = :userAccountId");
        query.setParameter("userAccountId", userAccountId);

        res = query.getResultList();

        assertEquals("The result list should contain 2 elements.", 2, res.size());
    }

    /**
     * <p>
     * Tests the <code>addUserToBillingProjects(String, long[])</code> method.
     * </p>
     * <p>
     * If project management relations are already exists, nothing should happen.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    @SuppressWarnings("unchecked")
    public void test_addUserToBillingProjects_accuracy2() throws Exception {
        TestHelper.clearConfig();
        TestHelper.addConfig("test_files/com/topcoder/db/connectionfactory/DBConnectionFactoryImpl.xml");

        // prepare data
        // dummy project
        createCompany(1);
        Client client1 = createClient(100);
        createProjectWithClient(10, client1);
        createProjectWithClient(12, client1);
        createProjectWithClient(13, client1);
        createProjectWithClient(14, client1);

        // two relationship should be added.
        target.addUserToBillingProjects("ivern", new long[] {10, 12});

        // for project 10, the relationship is present, simply ignore it.
        target.addUserToBillingProjects("ivern", new long[] {10, 13});

        EntityManager entityManager = getEntityManager();

        Query query = entityManager.createNativeQuery("SELECT user_account_id FROM user_account"
                + " WHERE user_name = :userName");
        query.setParameter("userName", "ivern");

        List res = query.getResultList();

        assertEquals("The result list should contain one element.", 1, res.size());

        Long userAccountId = Long.parseLong(res.get(0).toString());

        query = entityManager.createNativeQuery("SELECT project_id FROM project_manager"
                + " WHERE user_account_id = :userAccountId");
        query.setParameter("userAccountId", userAccountId);

        res = query.getResultList();

        assertEquals("The result list should contain 3 elements.", 3, res.size());
    }
    /**
     * <p>
     * Tests the <code>removeUserFromBillingProjects(String, long[])</code> method.
     * </p>
     * <p>
     * If the userName is null, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void test_removeUserFromBillingProjects_userName_null() throws Exception {
        try {
            target.removeUserFromBillingProjects(null, new long[] {1});

            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the <code>removeUserFromBillingProjects(String, long[])</code> method.
     * </p>
     * <p>
     * If the userName is empty, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void test_removeUserFromBillingProjects_userName_empty() throws Exception {
        try {
            target.removeUserFromBillingProjects("", new long[] {1});

            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the <code>removeUserFromBillingProjects(String, long[])</code> method.
     * </p>
     * <p>
     * If the userName is trimmed empty, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void test_removeUserFromBillingProjects_userName_trimmedEmpty() throws Exception {
        try {
            target.removeUserFromBillingProjects("  ", new long[] {1});

            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the <code>removeUserFromBillingProjects(String, long[])</code> method.
     * </p>
     * <p>
     * If the billingProjectIds is null, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void test_removeUserFromBillingProjects_billingProjectIds_null() throws Exception {
        try {
            target.removeUserFromBillingProjects("ivern", null);

            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the <code>removeUserFromBillingProjects(String, long[])</code> method.
     * </p>
     * <p>
     * If the billingProjectIds is empty, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void test_removeUserFromBillingProjects_billingProjectIds_empty() throws Exception {
        try {
            target.removeUserFromBillingProjects("ivern", new long[0]);

            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the <code>removeUserFromBillingProjects(String, long[])</code> method.
     * </p>
     * <p>
     * If the billingProjectIds contains 0, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void test_removeUserFromBillingProjects_billingProjectIds_containsZero() throws Exception {
        try {
            target.removeUserFromBillingProjects("ivern", new long[] {0, 1});

            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the <code>removeUserFromBillingProjects(String, long[])</code> method.
     * </p>
     * <p>
     * If the billingProjectIds contains negative, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void test_removeUserFromBillingProjects_billingProjectIds_containsNegative() throws Exception {
        try {
            target.removeUserFromBillingProjects("ivern", new long[] {1, -1});

            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the <code>removeUserFromBillingProjects(String, long[])</code> method.
     * </p>
     * <p>
     * If user name does not exist, no thing happens.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void test_removeUserFromBillingProjects_accuracy1() throws Exception {
        TestHelper.clearConfig();
        TestHelper.addConfig("test_files/com/topcoder/db/connectionfactory/DBConnectionFactoryImpl.xml");

        // prepare data
        Client client1 = createClient(100);
        createProjectWithClient(10, client1);
        createProjectWithClient(12, client1);
        createProjectWithClient(13, client1);
        createProjectWithClient(14, client1);


        target.removeUserFromBillingProjects("ivern", new long[] {10, 12});

        // nothing happens.
    }

    /**
     * <p>
     * Tests the <code>removeUserFromBillingProjects(String, long[])</code> method.
     * </p>
     * <p>
     * If the user is found, project management relations are removed.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    @SuppressWarnings("unchecked")
    public void test_removeUserFromBillingProjects_accuracy2() throws Exception {
        TestHelper.clearConfig();
        TestHelper.addConfig("test_files/com/topcoder/db/connectionfactory/DBConnectionFactoryImpl.xml");

        // prepare data
        Client client1 = createClient(100);
        createProjectWithClient(10, client1);
        createProjectWithClient(12, client1);
        createProjectWithClient(13, client1);
        createProjectWithClient(14, client1);

        EntityManager entityManager = getEntityManager();

        IDGenerator idGen = IDGeneratorFactory.getIDGenerator("com.topcoder.clients.model.User");

        Long userAccountId = idGen.getNextID();
        // and get the userAccountId
        String insertUserQueryString = "insert into user_account (user_account_id, account_status_id,"
                + " user_name, password, creation_date, creation_user, modification_date, modification_user)"
                + " values(:userAccountId, 1, :userName, '', CURRENT, '', CURRENT, '')";
        Query insertUserQuery = entityManager.createNativeQuery(insertUserQueryString);
        insertUserQuery.setParameter("userAccountId", userAccountId);
        insertUserQuery.setParameter("userName", "ivern");
        insertUserQuery.executeUpdate();

        String insertProjectManagerString = "insert into project_manager (project_id, user_account_id, cost,"
                + " active, creation_date, creation_user, modification_date, modification_user) values"
                + " (:projectId, :userAccountId, 0, 1, CURRENT, '', CURRENT, '')";
        Query insertProjectManagerQuery = entityManager.createNativeQuery(insertProjectManagerString);

        for (long projectId : new long[] {10, 12, 13}) {
            insertProjectManagerQuery.setParameter("userAccountId", userAccountId);
            insertProjectManagerQuery.setParameter("projectId", projectId);
            insertProjectManagerQuery.executeUpdate();
        }

        target.removeUserFromBillingProjects("ivern", new long[] {10, 12, 13});

        Query query = entityManager.createNativeQuery("SELECT user_account_id FROM user_account"
                + " WHERE user_name = :userName");
        query.setParameter("userName", "ivern");

        List res = query.getResultList();

        assertEquals("The result list should be one element.", 1, res.size());

        query = entityManager.createNativeQuery("SELECT project_id FROM project_manager"
                + " WHERE user_account_id = :userAccountId");
        query.setParameter("userAccountId", userAccountId);

        res = query.getResultList();

        assertEquals("The result list should be empty.", 0, res.size());
    }

    /**
     * <p>
     * Tests the <code>getProjectsByClientId(long)</code> method.
     * </p>
     * <p>
     * If the clientId is zero, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void test_getProjectsByClientId_clientId_zero() throws Exception {
        try {
            target.getProjectsByClientId(0);

            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }


    /**
     * <p>
     * Tests the <code>getProjectsByClientId(long)</code> method.
     * </p>
     * <p>
     * If the clientId is negative, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void test_getProjectsByClientId_clientId_negative() throws Exception {
        try {
            target.getProjectsByClientId(-1);

            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the <code>getProjectsByClientId(long)</code> method.
     * </p>
     * <p>
     * If the client id does not exist, should return empty list.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void test_getProjectsByClientId_clientNotExist() throws Exception {
        List<Project> res = target.getProjectsByClientId(123);
        assertEquals("The number of returned projects is incorrect.", 0, res.size());
    }


    /**
     * <p>
     * Tests the <code>getProjectsByClientId(long)</code> method.
     * </p>
     * <p>
     * Should return the projects whose client have id
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void test_getProjectsByClientId() throws Exception {

        // prepare data
        Client client1 = createClient(100);
        createProjectWithClient(10, client1);
        createProjectWithClient(12, client1);
        createProjectWithClient(13, client1);
        createProjectWithClient(14, client1);

        setChildProject(10, 12);
        setChildProject(10, 13);

        Client client2 = createClient(101);
        createProjectWithClient(15, client2);
        createProjectWithClient(16, client2);
        getEntityManager().getTransaction().commit();

        List<Project> res = target.getProjectsByClientId(client1.getId());
        assertEquals("The number of returned projects is incorrect.", 4, res.size());

        res = target.getProjectsByClientId(client2.getId());
        assertEquals("The number of returned projects is incorrect.", 2, res.size());
    }

    /**
     * <p>
     * Tests the <code>updateProjectBudget(String, long, double)</code> method.
     * </p>
     * <p>
     * If the username is null, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     * @since 1.2
     */
    public void test_updateProjectBudget_username_null() throws Exception {
        try {
            target.updateProjectBudget(null, 1, 0);

            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the <code>updateProjectBudget(String, long, double)</code> method.
     * </p>
     * <p>
     * If the username is empty, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     * @since 1.2
     */
    public void test_updateProjectBudget_username_empty() throws Exception {
        try {
            target.updateProjectBudget("", 1, 0);

            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the <code>updateProjectBudget(String, long, double)</code> method.
     * </p>
     * <p>
     * If the username is trimmed empty, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     * @since 1.2
     */
    public void test_updateProjectBudget_username_trimmedEmpty() throws Exception {
        try {
            target.updateProjectBudget(" \t", 1, 0);

            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the <code>updateProjectBudget(String, long, double)</code> method.
     * </p>
     * <p>
     * If the new budget is negative, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     * @since 1.2
     */
    public void test_updateProjectBudget_budget_negative() throws Exception {
        // prepare data
        Client client1 = createClient(100);
        createProjectWithClient(10, client1);

        try {
            target.updateProjectBudget("ivern", 10, -1000000);

            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the <code>updateProjectBudget(String, long, double)</code> method.
     * </p>
     * <p>
     * If the billing project does not exist, should throw EntityNotFoundException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     * @since 1.2
     */
    public void test_updateProjectBudget_EntityNotFoundException() throws Exception {
        try {
            target.updateProjectBudget("ivern", 100, 1);

            fail("EntityNotFoundException expected");
        } catch (EntityNotFoundException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the <code>updateProjectBudget(String, long, double)</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     * @since 1.2
     */
    public void test_updateProjectBudget_accuracy1() throws Exception {
        // prepare data
        Client client = createClient(100);
        createProjectWithClient(10, client);

        assertEquals("The new budget is incorrect.", 100.0, target.updateProjectBudget("ivern", 10, 100.0), 1e-9);
        assertEquals("one audit record should be inserted", 1, checkProjectBudgetAudit("ivern", 10, 100.0));
    }

    /**
     * <p>
     * Tests the <code>updateProjectBudget(String, long, double)</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     * @since 1.2
     */
    public void test_updateProjectBudget_accuracy2() throws Exception {
        // prepare data
        Client client = createClient(100);
        createProjectWithInitialBudget(10, client, 20.0);

        assertEquals("The new budget is incorrect.", 120.0, target.updateProjectBudget("ivern", 10, 100.0), 1e-9);
        assertEquals("one audit record should be inserted", 1, checkProjectBudgetAudit("ivern", 10, 100.0));
    }


    /**
     * <p>
     * Tests the <code>getUsersByProject(long)</code> method.
     * </p>
     * <p>
     * If the billingProjectId is negative, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     * @since 1.2
     */
    public void test_getUsersByProject_billingProjectId_negative() throws Exception {
        try {
            target.getUsersByProject(-1);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the <code>getUsersByProject(long)</code> method.
     * </p>
     * <p>
     * If the billingProjectId is zero, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     * @since 1.2
     */
    public void test_getUsersByProject_billingProjectId_zero() throws Exception {
        try {
            target.getUsersByProject(0);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the <code>getUsersByProject(long)</code> method.
     * </p>
     * <p>
     * If the billingProjectId doesn't exist, should throw EntityNotFoundException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     * @since 1.2
     */
    public void test_getUsersByProject_billingProjectId_notFound() throws Exception {
        try {
            target.getUsersByProject(10000);
            fail("EntityNotFoundException expected");
        } catch (EntityNotFoundException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the <code>getUsersByProject(long)</code> method.
     * </p>
     * <p>
     * If the billingProjectId is present, should return the users for this project.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     * @since 1.2
     */
    public void test_getUsersByProject_billingProjectId_accuracy() throws Exception {
        // prepare data
        Client client1 = createClient(100);
        createProjectWithClient(10, client1);

        List<String> result = target.getUsersByProject(10);
        assertNotNull("The returned list should not be null.", result);
        assertTrue("The returned list should be empty", result.isEmpty());
    }

    /**
     * <p>
     * Tests the <code>getUsersByProject(long)</code> method.
     * </p>
     * <p>
     * If the billingProjectId is present, should return the users for this project.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     * @since 1.2
     */
    public void test_getUsersByProject_billingProjectId_accuracy2() throws Exception {
        TestHelper.clearConfig();
        TestHelper.addConfig("test_files/com/topcoder/db/connectionfactory/DBConnectionFactoryImpl.xml");

        // prepare data
        // dummy company for dummy user account
        createCompany(1);
        Client client = createClient(100);
        createProjectWithClient(10, client);
        target.addUserToBillingProjects("ivern", new long[] {10});

        List<String> result = target.getUsersByProject(10);
        assertNotNull("The returned list should not be null.", result);
        assertEquals("The returned list should have one element.", 1, result.size());
        assertEquals("The username retrieved is incorrect.", "ivern", result.get(0));
    }

    /**
     * <p>
     * Tests the <code>getUsersByProject(long)</code> method.
     * </p>
     * <p>
     * If the billingProjectId is present, should return the users for this project.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     * @since 1.2
     */
    public void test_getUsersByProject_billingProjectId_accuracy3() throws Exception {
        TestHelper.clearConfig();
        TestHelper.addConfig("test_files/com/topcoder/db/connectionfactory/DBConnectionFactoryImpl.xml");

        // prepare data
        // dummy company for dummy user account
        createCompany(1);
        Client client = createClient(100);
        createProjectWithClient(10, client);
        addProjectWorker("ivern", 10);

        List<String> result = target.getUsersByProject(10);
        assertNotNull("The returned list should not be null.", result);
        assertEquals("The returned list should have one element.", 1, result.size());
        assertEquals("The username retrieved is incorrect.", "ivern", result.get(0));
    }
}
