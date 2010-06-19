/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.dao.ejb3;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.topcoder.clients.dao.DAOConfigurationException;
import com.topcoder.clients.dao.DAOException;
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
     * @throws Exception to JUnit
     */
    @Override
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
     * Verifies <code>ProjectDAOBean</code> subclasses <code>GenericEJB3DAO</code>.
     * </p>
     */
    public void testInheritance1() {
        assertTrue("ProjectDAOBean does not subclasses GenericEJB3DAO.",
            target instanceof GenericEJB3DAO < ?, ? >);
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
        assertTrue("ProjectDAOBean does not implements ProjectDAO.", target instanceof ProjectDAO);
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     * <p>
     * Verifies <code>ProjectDAOBean</code> implements <code>ProjectDAOLocal</code>.
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
     * Verifies <code>ProjectDAOBean</code> implements <code>ProjectDAORemote</code>.
     * </p>
     */
    public void testInheritance4() {
        assertTrue("ProjectDAOBean does not implements ProjectDAORemote.",
            target instanceof ProjectDAORemote);
    }

    /**
     * <p>
     * Tests the <code>com.topcoder.clients.dao.ejb3.ProjectDAOBean()</code> for proper behavior.
     * </p>
     * @throws Exception to JUnit
     */
    public void testConstructor() throws Exception {
        assertNotNull("ProjectDAOBean() failed.", target);
    }

    /**
     * <p>
     * Tests the <code>retrieveById(Long, boolean)</code> for proper behavior.
     * </p>
     * @throws Exception to JUnit
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
     * @throws Exception to JUnit
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
        List < Project > children = res.getChildProjects();
        assertEquals("should include child.", 2, children.size());
    }

    /**
     * <p>
     * Tests the <code>retrieveById(Long, boolean)</code> for proper behavior.
     * </p>
     * @throws Exception to JUnit
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
        List < Project > children = res.getChildProjects();
        assertEquals("should include child, and exclude child-child.", 2, children.size());
    }

    /**
     * <p>
     * Tests the <code>retrieveById(Long, boolean)</code> for proper behavior.
     * IllegalArgumentException if id <= 0 or id is null.
     * </p>
     * @throws Exception to JUnit
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
     * @throws Exception to JUnit
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
     * @throws Exception to JUnit
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
     * DAOConfigurationException if the configured entityManager is invalid (invalid means null
     * here).
     * </p>
     * @throws Exception to JUnit
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
     * @throws Exception to JUnit
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
     * @throws Exception to JUnit
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

        List < Project > res = target.retrieveAll(false);
        assertEquals("The number of returned projects.", 4, res.size());
    }

    /**
     * <p>
     * Tests the <code>retrieveAll(boolean)</code> for proper behavior.
     * </p>
     * @throws Exception to JUnit
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

        List < Project > res = target.retrieveAll(true);
        assertEquals("The number of returned projects.", 4, res.size());
    }

    /**
     * <p>
     * Tests the <code>retrieveAll(boolean)</code> for proper behavior. DAOConfigurationException if
     * the configured entityManager is invalid (invalid means null here).
     * </p>
     * @throws Exception to JUnit
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
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void test_retrieveAllProjectsOnly1() throws Exception {
        List < Project > projects = target.retrieveAllProjectsOnly();

        assertNotNull("the projects list should not be null", projects);
        assertEquals("The list should be empty.", 0, projects.size());
    }

    /**
     * <p>
     * Tests the <code>retrieveAllProjectsOnly()</code> method.
     * </p>
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void test_retrieveAllProjectsOnly2() throws Exception {
        // prepare data
        Client client1 = createClient(100);
        createProjectWithClient(10, client1);
        createProjectWithClient(12, client1);
        createProjectWithClient(13, client1);
        createProjectWithClient(14, client1);

        List < Project > projects = target.retrieveAllProjectsOnly();

        assertNotNull("the projects list should not be null", projects);
        assertEquals("The list should be 4 elements.", 4, projects.size());
    }

    /**
     * <p>
     * Tests the <code>getContestFeesByProject()</code> method.
     * </p>
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void test_getContestFeesByProject() throws Exception {
        List < ProjectContestFee > fees = target.getContestFeesByProject(1);

        assertNotNull("the projects list should not be null", fees);
        assertEquals("The list should be empty.", 0, fees.size());
    }

    /**
     * <p>
     * Tests the <code>saveContestFees(List, long)</code> method.
     * </p>
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void test_saveContestFees_contains_null() throws Exception {
        List < ProjectContestFee > fees = new ArrayList < ProjectContestFee >();
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
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void test_saveContestFees_inconsistent_projectId() throws Exception {
        ProjectContestFee projectContestFee = new ProjectContestFee();
        projectContestFee.setContestFee(100);
        // projectContestFee.setContestType("test");
        projectContestFee.setCreateDate(new Date());
        projectContestFee.setCreateUsername("ivern");
        projectContestFee.setDeleted(false);
        projectContestFee.setModifyDate(new Date());
        projectContestFee.setModifyUsername("ivern");
        projectContestFee.setName("Test");
        projectContestFee.setProjectId(1);
        // projectContestFee.setSubType("type");

        List < ProjectContestFee > fees = new ArrayList < ProjectContestFee >();
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
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void test_saveContestFees() throws Exception {
        ProjectContestFee projectContestFee = new ProjectContestFee();
        projectContestFee.setContestFee(100);
        // projectContestFee.setContestType("test");
        projectContestFee.setCreateDate(new Date());
        projectContestFee.setCreateUsername("ivern");
        projectContestFee.setDeleted(false);
        projectContestFee.setModifyDate(new Date());
        projectContestFee.setModifyUsername("ivern");
        projectContestFee.setName("Test");
        projectContestFee.setProjectId(1);
        // projectContestFee.setSubType("type");

        List < ProjectContestFee > fees = new ArrayList < ProjectContestFee >();
        fees.add(projectContestFee);
        target.saveContestFees(fees, 1);
    }

    /**
     * <p>
     * Tests the <code>saveContestFees(List, long)</code> method.
     * </p>
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void test_saveContestFees1() throws Exception {
        // just delete the related entities.
        target.saveContestFees(null, 1);
    }

    /**
     * <p>
     * Tests the <code>searchProjectsByClientName(String)</code> method.
     * </p>
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void test_searchProjectsByClientName() throws Exception {

        // prepare data
        Client client = createClient("tc", 100);
        createProjectWithClient(10, client);
        createProjectWithClient(12, client);
        createProjectWithClient(13, client);

        Client client2 = createClient("microsoft", 101);
        createProjectWithClient(14, client2);

        List < Project > projs = target.searchProjectsByClientName(client.getName());
        assertEquals("The list size is incorrect.", 3, projs.size());
    }

    /**
     * <p>
     * Tests the <code>getProjectsByUser(String)</code> method.
     * </p>
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void test_getProjectsByUser() throws Exception {
        List < Project > projs = target.getProjectsByUser("ivern");
        assertEquals("The list size is incorrect.", 0, projs.size());
    }

    /**
     * <p>
     * Tests the <code>searchProjectsByProjectName(String)</code> method.
     * </p>
     * @throws Exception pass any unexpected exception to JUnit.
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
     * @throws Exception pass any unexpected exception to JUnit.
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
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void test_searchProjectsByProjectName() throws Exception {
        List < Project > projs = target.searchProjectsByProjectName("Project");
        assertEquals("The list size is incorrect.", 0, projs.size());
    }

    /**
     * <p>
     * Tests the <code>checkClientProjectPermission(String, long)</code> method.
     * </p>
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void test_checkClientProjectPermission() throws Exception {
        assertFalse(target.checkClientProjectPermission("ivern", 1000));
    }

    /**
     * <p>
     * Tests the <code>checkPoNumberPermission(String, long)</code> method.
     * </p>
     * @throws Exception pass any unexpected exception to JUnit.
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
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void test_addUserToBillingProjects_userName_null() throws Exception {
        try {
            target.addUserToBillingProjects(null, new long[] {1 });

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
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void test_addUserToBillingProjects_userName_empty() throws Exception {
        try {
            target.addUserToBillingProjects("", new long[] {1 });

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
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void test_addUserToBillingProjects_userName_trimmedEmpty() throws Exception {
        try {
            target.addUserToBillingProjects("  ", new long[] {1 });

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
     * @throws Exception pass any unexpected exception to JUnit.
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
     * @throws Exception pass any unexpected exception to JUnit.
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
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void test_addUserToBillingProjects_billingProjectIds_containsZero() throws Exception {
        try {
            target.addUserToBillingProjects("ivern", new long[] {0, 1 });

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
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void test_addUserToBillingProjects_billingProjectIds_containsNegative() throws Exception {
        try {
            target.addUserToBillingProjects("ivern", new long[] {1, -1 });

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
     * If the user is not found, a new user_account is added, and project management relations are
     * added.
     * </p>
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void test_addUserToBillingProjects_accuracy() throws Exception {
        TestHelper.clearConfig();
        TestHelper.addConfig("com/topcoder/db/connectionfactory/DBConnectionFactoryImpl.xml");

        // prepare data
        createCompany(1);
        Client client1 = createClient(100);
        createProjectWithClient(10, client1);
        createProjectWithClient(12, client1);
        createProjectWithClient(13, client1);
        createProjectWithClient(14, client1);

        target.addUserToBillingProjects("ivern", new long[] {10, 12 });

        EntityManager entityManager = getEntityManager();

        Query query =
            entityManager.createNativeQuery("SELECT user_account_id FROM user_account"
                + " WHERE user_name = :userName");
        query.setParameter("userName", "ivern");

        List res = query.getResultList();

        assertEquals("The result list should contain one element.", 1, res.size());

        Long userAccountId = Long.parseLong(res.get(0).toString());

        query =
            entityManager.createNativeQuery("SELECT project_id FROM project_manager"
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
     * If no default company exist, new user account can not be created.
     * </p>
     * @throws Exception pass any unexpected exception to JUnit.
     * @since 1.2
     */
    public void test_addUserToBillingProjects_exception() throws Exception {
        TestHelper.clearConfig();
        TestHelper.addConfig("com/topcoder/db/connectionfactory/DBConnectionFactoryImpl.xml");

        // prepare data, without a default company.
        // createCompany(1);
        Client client1 = createClient(100);
        createProjectWithClient(10, client1);
        createProjectWithClient(12, client1);
        createProjectWithClient(13, client1);
        createProjectWithClient(14, client1);

        try {
            target.addUserToBillingProjects("ivern", new long[] {10, 12 });
            fail("should throw DAOException");
        } catch (DAOException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the <code>removeUserFromBillingProjects(String, long[])</code> method.
     * </p>
     * <p>
     * If the userName is null, should throw IllegalArgumentException.
     * </p>
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void test_removeUserFromBillingProjects_userName_null() throws Exception {
        try {
            target.removeUserFromBillingProjects(null, new long[] {1 });

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
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void test_removeUserFromBillingProjects_userName_empty() throws Exception {
        try {
            target.removeUserFromBillingProjects("", new long[] {1 });

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
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void test_removeUserFromBillingProjects_userName_trimmedEmpty() throws Exception {
        try {
            target.removeUserFromBillingProjects("  ", new long[] {1 });

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
     * @throws Exception pass any unexpected exception to JUnit.
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
     * @throws Exception pass any unexpected exception to JUnit.
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
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void test_removeUserFromBillingProjects_billingProjectIds_containsZero()
        throws Exception {
        try {
            target.removeUserFromBillingProjects("ivern", new long[] {0, 1 });

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
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void test_removeUserFromBillingProjects_billingProjectIds_containsNegative()
        throws Exception {
        try {
            target.removeUserFromBillingProjects("ivern", new long[] {1, -1 });

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
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void test_removeUserFromBillingProjects_accuracy1() throws Exception {
        TestHelper.clearConfig();
        TestHelper.addConfig("com/topcoder/db/connectionfactory/DBConnectionFactoryImpl.xml");

        // prepare data
        Client client1 = createClient(100);
        createProjectWithClient(10, client1);
        createProjectWithClient(12, client1);
        createProjectWithClient(13, client1);
        createProjectWithClient(14, client1);

        target.removeUserFromBillingProjects("ivern", new long[] {10, 12 });

        // nothing happens.
    }

    /**
     * <p>
     * Tests the <code>removeUserFromBillingProjects(String, long[])</code> method.
     * </p>
     * <p>
     * If the user is found, project management relations are removed.
     * </p>
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void test_removeUserFromBillingProjects_accuracy2() throws Exception {
        TestHelper.clearConfig();
        TestHelper.addConfig("com/topcoder/db/connectionfactory/DBConnectionFactoryImpl.xml");

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
        String insertUserQueryString =
            "insert into user_account (user_account_id, account_status_id,"
                + " user_name, password, creation_date, creation_user, modification_date, modification_user)"
                + " values(:userAccountId, 1, :userName, '', CURRENT, '', CURRENT, '')";
        Query insertUserQuery = entityManager.createNativeQuery(insertUserQueryString);
        insertUserQuery.setParameter("userAccountId", userAccountId);
        insertUserQuery.setParameter("userName", "ivern");
        insertUserQuery.executeUpdate();

        String insertProjectManagerString =
            "insert into project_manager (project_id, user_account_id, cost,"
                + " active, creation_date, creation_user, modification_date, modification_user) values"
                + " (:projectId, :userAccountId, 0, 1, CURRENT, '', CURRENT, '')";
        Query insertProjectManagerQuery =
            entityManager.createNativeQuery(insertProjectManagerString);

        for (long projectId : new long[] {10, 12, 13 }) {
            insertProjectManagerQuery.setParameter("userAccountId", userAccountId);
            insertProjectManagerQuery.setParameter("projectId", projectId);
            insertProjectManagerQuery.executeUpdate();
        }

        target.removeUserFromBillingProjects("ivern", new long[] {10, 12, 13 });

        Query query =
            entityManager.createNativeQuery("SELECT user_account_id FROM user_account"
                + " WHERE user_name = :userName");
        query.setParameter("userName", "ivern");

        List res = query.getResultList();

        assertEquals("The result list should be one element.", 1, res.size());

        query =
            entityManager.createNativeQuery("SELECT project_id FROM project_manager"
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
     * @throws Exception pass any unexpected exception to JUnit.
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
     * @throws Exception pass any unexpected exception to JUnit.
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
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void test_getProjectsByClientId_clientNotExiss() throws Exception {
        List < Project > res = target.getProjectsByClientId(123);
        assertEquals("The number of returned projects is incorrect.", 0, res.size());
    }

    /**
     * <p>
     * Tests the <code>getProjectsByClientId(long)</code> method.
     * </p>
     * <p>
     * Should return the projects whose client have id
     * </p>
     * @throws Exception pass any unexpected exception to JUnit.
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

        List < Project > res = target.getProjectsByClientId(client1.getId());
        assertEquals("The number of returned projects is incorrect.", 4, res.size());

        res = target.getProjectsByClientId(client2.getId());
        assertEquals("The number of returned projects is incorrect.", 2, res.size());
    }

    /**
     * <p>
     * Tests <code>updateProjectBudget(String, long, double)</code> method.
     * </p>
     * <p>
     * If the username is null, <code>IllegalArgumentException</code> is expected.
     * </p>
     * @throws Exception to JUnit
     */
    public void testUpdateProjectBudget_Username_Null() throws Exception {
        try {
            target.updateProjectBudget(null, 1, 0);

            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // Good
        }
    }

    /**
     * <p>
     * Tests <code>updateProjectBudget(String, long, double)</code> method.
     * </p>
     * <p>
     * If the username is empty, <code>IllegalArgumentException</code> is expected.
     * </p>
     * @throws Exception to JUnit
     */
    public void testUpdateProjectBudget_Username_Empty_Fail() throws Exception {
        try {
            target.updateProjectBudget(" ", 1, 0);

            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // Good
        }
    }

    /**
     * <p>
     * Tests <code>updateProjectBudget(String, long, double)</code> method.
     * </p>
     * <p>
     * if updatedAmount makes the new budget negative(budget + updatedAmount < 0),
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     * @throws Exception to JUnit
     */
    public void testUpdateProjectBudget_UpdateNegative_Fail() throws Exception {
        // create a client
        Client client = createClient(1);

        // create a project with id=2. Note initial budget is 1.
        createProjectWithClient(2, client);

        try {
            // updatedAmount is -2, budget + updatedAmount < 0
            target.updateProjectBudget("user", 2, -2);

            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // Good
        }
    }

    /**
     * <p>
     * Tests <code>updateProjectBudget(String, long, double)</code> method.
     * </p>
     * <p>
     * If the configured entityManager is invalid (invalid means null here),
     * <code>DAOConfigurationException</code> is expected.
     * </p>
     * @throws Exception to JUnit
     */
    public void testUpdateProjectBudget_EntityManager_Null_Fail() throws Exception {
        try {
            // create a new Bean without entityManager.
            target = new ProjectDAOBean();

            target.updateProjectBudget("user", 1, 0);

            fail("DAOConfigurationException is expected.");
        } catch (DAOConfigurationException e) {
            // Good
        }
    }

    /**
     * <p>
     * Tests <code>updateProjectBudget(String, long, double)</code> method.
     * </p>
     * <p>
     * if project with given id is not found in the persistence.<code>EntityNotFoundException</code>
     * is expected.
     * </p>
     * @throws Exception to JUnit
     */
    public void testUpdateProjectBudget_EntityNotFound_Fail() throws Exception {
        // create a client
        Client client = createClient(1);

        // create a project with id=2. Note initial budget is 1.
        createProjectWithClient(2, client);
        try {
            // find project with id=1, no found
            target.updateProjectBudget("user", 1, 0);

            fail("EntityNotFoundException is expected.");
        } catch (EntityNotFoundException e) {
            // Good
        }
    }

    /**
     * <p>
     * Tests <code>updateProjectBudget(String, long, double)</code> method.
     * </p>
     * <p>
     * It should succeed.
     * </p>
     * @throws Exception to JUnit
     */
    public void testUpdateProjectBudget_Accuracy_zero() throws Exception {
        // create a client
        Client client = createClient(1);

        // create a project with id=2. Note initial budget is 1.
        int projectId = 2;
        createProjectWithClient(projectId, client);

        // update project budget, just make it to zero.
        double ret = target.updateProjectBudget("user", projectId, -1.0);
        assertEquals("Should return zero", new Double(0), ret);

        // retrieve the budget from the database to verify
        EntityManager entityManager = getEntityManager();
        Query query =
            entityManager
                .createNativeQuery("select budget from project where project_id = :projectId");
        query.setParameter("projectId", projectId);
        BigDecimal updateBudget = (BigDecimal) query.getSingleResult();

        assertEquals("Should update the database", new Double(0), updateBudget.doubleValue());

        // retrieve project budget audit from the database to verify
        query =
            entityManager.createNativeQuery("select changed_amount, creation_user from "
                + "project_budget_audit where project_id = :projectId");
        query.setParameter("projectId", projectId);
        Object result[] = (Object[]) query.getSingleResult();

        assertEquals("updatedAmount should be set", new Double(-1), ((BigDecimal) result[0])
            .doubleValue());
        assertEquals("username should be set", "user", result[1]);
    }

    /**
     * <p>
     * Tests <code>updateProjectBudget(String, long, double)</code> method.
     * </p>
     * <p>
     * It should succeed even with a null Budget.
     * </p>
     * @throws Exception to JUnit
     */
    public void testUpdateProjectBudget_Accuracy_NullBudget() throws Exception {
        // create a client
        Client client = createClient(1);

        // create a project.
        int projectId = 2;
        setInitialBudget(null);
        createProjectWithClient(projectId, client);

        // update project budget
        double ret = target.updateProjectBudget("user", projectId, 2.0);
        assertEquals("Should return 2", new Double(2), ret);

        // retrieve the budget from the database to verify
        EntityManager entityManager = getEntityManager();
        entityManager.flush();

        Query query =
            entityManager
                .createNativeQuery("select budget from project where project_id = :projectId");
        query.setParameter("projectId", projectId);
        BigDecimal updateBudget = (BigDecimal) query.getSingleResult();

        assertEquals("Should update the database", new Double(2), updateBudget.doubleValue());

        // retrieve project budget audit from the database to verify
        query =
            entityManager.createNativeQuery("select changed_amount, creation_user from "
                + "project_budget_audit where project_id = :projectId");
        query.setParameter("projectId", projectId);
        Object result[] = (Object[]) query.getSingleResult();

        assertEquals("updatedAmount should be set", new Double(2), ((BigDecimal) result[0])
            .doubleValue());
        assertEquals("username should be set", "user", result[1]);
    }

    /**
     * <p>
     * Tests <code>updateProjectBudget(String, long, double)</code> method.
     * </p>
     * <p>
     * It should succeed.
     * </p>
     * @throws Exception to JUnit
     */
    public void testUpdateProjectBudget_Accuracy_demo() throws Exception {
        // prepare data
        // create a client
        Client client = createClient(1);

        // create project with budget 4000.
        int projectId = 100;
        setInitialBudget(new Double(4000));
        createProjectWithClient(projectId, client);

        // Increase the budget of project with id 100 with 500.
        double ret = target.updateProjectBudget("ivern", 100, 500);
        assertEquals("Should return 4500", new Double(4500), ret);

        // retrieve the budget from the database to verify
        EntityManager entityManager = getEntityManager();
        Query query =
            entityManager
                .createNativeQuery("select budget from project where project_id = :projectId");
        query.setParameter("projectId", projectId);
        BigDecimal updateBudget = (BigDecimal) query.getSingleResult();

        assertEquals("Should update the database", new Double(4500), updateBudget.doubleValue());

        // retrieve project budget audit from the database to verify
        query =
            entityManager.createNativeQuery("select changed_amount, creation_user from "
                + "project_budget_audit where project_id = :projectId");
        query.setParameter("projectId", projectId);
        Object result[] = (Object[]) query.getSingleResult();

        assertEquals("updatedAmount should be set", new Double(500), ((BigDecimal) result[0])
            .doubleValue());
        assertEquals("username should be set", "ivern", result[1]);
    }

    /**
     * <p>
     * Tests <code>getUsersByProject(long billingProjectId)</code> method.
     * </p>
     * <p>
     * If the billingProjectId <= 0, <code>IllegalArgumentException</code> is expected.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetUsersByProject_NegativeId_Fail() throws Exception {
        try {
            target.getUsersByProject(-1);

            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // Good
        }
    }

    /**
     * <p>
     * Tests <code>getUsersByProject(long billingProjectId)</code> method.
     * </p>
     * <p>
     * If the billingProjectId <= 0, <code>IllegalArgumentException</code> is expected.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetUsersByProject_ZeroId_Fail() throws Exception {
        try {
            target.getUsersByProject(0);

            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // Good
        }
    }

    /**
     * <p>
     * Tests <code>getUsersByProject(long billingProjectId)</code> method.
     * </p>
     * <p>
     * If project with given id is not found in the persistence,
     * <code>EntityNotFoundException</code> is expected.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetUsersByProject_EntityNotFound_Fail() throws Exception {
        try {
            // it's an empty database
            target.getUsersByProject(1);

            fail("EntityNotFoundException is expected.");
        } catch (EntityNotFoundException e) {
            // Good
        }
    }

    /**
     * <p>
     * Tests <code>getUsersByProject(long billingProjectId)</code> method.
     * </p>
     * <p>
     * if the configured entityManager is invalid (invalid means null here),
     * <code>DAOConfigurationException</code> is expected.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetUsersByProject_EntityManagerNull_Fail() throws Exception {
        try {
            // no EntityManager
            target = new ProjectDAOBean();

            target.getUsersByProject(1);

            fail("DAOConfigurationException is expected.");
        } catch (DAOConfigurationException e) {
            // Good
        }
    }

    /**
     * <p>
     * Tests <code>getUsersByProject(long billingProjectId)</code> method.
     * </p>
     * <p>
     * It should succeed.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetUsersByProject_Accuracy() throws Exception {
        // create a client
        Client client = createClient(1);

        // create a project with id=2. Note initial budget is 1.
        int projectId = 2;
        createProjectWithClient(projectId, client);

        List < String > ret = target.getUsersByProject(projectId);

        // since no user is specified
        assertEquals("Should return empty list", 0, ret.size());
    }

    /**
     * <p>
     * Tests <code>getUsersByProject(long billingProjectId)</code> method.
     * </p>
     * <p>
     * It should succeed.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetUsersByProject_Accuracy2() throws Exception {
        // create a client
        Client client = createClient(1);

        // create a project. Note initial budget is 1.
        int projectId = 123;
        createProjectWithClient(projectId, client);

        // create a company for User foreign key
        int companyId = 1;
        createCompany(companyId);

        Query query;
        // create User accounts, user1-user5
        EntityManager entityManager = getEntityManager();
        for (int i = 1; i <= 5; i++) {
            String insertUser =
                "insert into user_account (user_account_id, company_id, account_status_id, "
                    + "user_name, password) values(:userAccountId, :companyId,"
                    + " :accountStatusId, :userName, :userName)";
            query = entityManager.createNativeQuery(insertUser);
            query.setParameter("userAccountId", 100 + i);
            query.setParameter("companyId", companyId);
            query.setParameter("accountStatusId", 1);
            query.setParameter("userName", "user" + i);
            query.executeUpdate();
        }

        // create project manager table
        String insertProjectManager =
            "insert into project_manager( project_id, user_account_id, cost, active)"
                + "values(:projectId, :userAccountId, 0, :active)";
        query = entityManager.createNativeQuery(insertProjectManager);
        query.setParameter("projectId", projectId);
        query.setParameter("userAccountId", 101);
        query.setParameter("active", 1);
        query.executeUpdate();

        query.setParameter("active", 0);
        query.setParameter("userAccountId", 102);
        query.executeUpdate();

        // create project worker table
        String insertProjectWorker =
            "insert into project_worker(project_id,user_account_id,"
                + "start_date,end_date,cost,active,creation_date,creation_user,"
                + "modification_date,modification_user) values(:projectId,:userAccountId,"
                + ":date1, :date2, 0, :active, :date1, 'x', :date1, 'x')";
        query = entityManager.createNativeQuery(insertProjectWorker);
        query.setParameter("projectId", projectId);
        query.setParameter("date1", "2000-01-01 01:01:01");
        query.setParameter("date2", "2100-01-01 01:01:01");

        query.setParameter("userAccountId", 104);
        query.setParameter("active", 1);
        query.executeUpdate();

        query.setParameter("userAccountId", 105);
        query.setParameter("active", 0);
        query.executeUpdate();

        // Now, get the user names who have access of the billing project with id 123.
        List < String > users = target.getUsersByProject(projectId);

        // users list should contain 2 elements("user1" and "user4").
        assertEquals("Should contain 2 elements", 2, users.size());
        assertTrue("Should contain user1 and user4", users.get(0).equals("user1")
            && users.get(1).equals("user4") || users.get(1).equals("user1")
            && users.get(0).equals("user4"));
    }

}
