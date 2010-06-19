/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.clients.dao.accuracytests;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.topcoder.clients.dao.ejb3.ProjectDAOBean;
import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.Project;

/**
 * <p>
 * Tests the <code>{@link ProjectDAOBean}</code> for accuracy.
 * </p>
 *
 * <p>
 * Changes in 1.2: Added the testUpdateProjectBudget and testGetUsersByProject test cases.
 * </p>
 *
 * @author cyberjag, akinwale
 * @version 1.2
 * @since 1.0
 */
public class ProjectDAOAccuracyTests extends BaseTest<ProjectDAOBean, Project> {


    /**
     * Tests the <code>{@link ProjectDAOBean#ProjectDAOBean()}</code> for accuracy.
     */
    public void testProjectDAOBean() {
        assertNotNull("Failed to create the bean.", getTestBean());
    }

    /**
     * Tests the <code>{@link ProjectDAOBean#retrieveById(Long, boolean)}</code> for accuracy.
     *
     * @throws Exception
     *             to junit
     */
    public void testRetrieveByIdLongBoolean1() throws Exception {
        Client client = createClient(100);
        createProjectWithClient(10, client);
        createProjectWithClient(12, client);
        createProjectWithClient(13, client);

        setChildProject(10, 12);
        setChildProject(10, 13);

        Project res = getTestBean().retrieveById(10L, false);
        assertNotNull("Failed to RetrieveByIdLongBoolean.", res);
        assertNull("Failed to RetrieveByIdLongBoolean. Null child projects expected.", res.getChildProjects());
    }

    /**
     * Tests the <code>{@link ProjectDAOBean#retrieveById(Long, boolean)}</code> for accuracy.
     *
     * @throws Exception
     *             to junit
     */
    public void testRetrieveByIdLongBoolean2() throws Exception {
        Client client = createClient(100);
        createProjectWithClient(10, client);
        createProjectWithClient(12, client);
        createProjectWithClient(13, client);

        setChildProject(10, 12);
        setChildProject(10, 13);

        Project res = getTestBean().retrieveById(10L, true);
        assertEquals("Failed to RetrieveByIdLongBoolean. Child projects expected.", res.getChildProjects()
                .size(), 2);
    }

    /**
     * Set child project.
     *
     * @param parent
     *            the parent id
     * @param child
     *            the child id
     */
    protected void setChildProject(long parent, long child) {
        EntityManager em = getEntityManager();
        if (!em.getTransaction().isActive()) {
            em.getTransaction().begin();
        }
        Query query = em.createNativeQuery("update project set parent_project_id=? where project_id=?");

        query.setParameter(1, parent);
        query.setParameter(2, child);
        query.executeUpdate();

        em.getTransaction().commit();
    }

    /**
     * Tests the <code>{@link ProjectDAOBean#retrieveAll(boolean)}</code> for accuracy.
     *
     * @throws Exception
     *             to junit
     */
    public void testRetrieveAllBoolean1() throws Exception {
        Client client = createClient(100);
        createProjectWithClient(10, client);
        createProjectWithClient(12, client);
        createProjectWithClient(13, client);

        setChildProject(10, 12);
        setChildProject(10, 13);

        List<Project> list = getTestBean().retrieveAll(false);
        // including the one which is created during setup
        assertEquals("Failed to retrieveAll.", list.size(), 4);
        assertNull("Failed to retrieveAll. Null child projects expected.", list.get(0).getChildProjects());
        assertNull("Failed to retrieveAll. Null child projects expected.", list.get(1).getChildProjects());
        assertNull("Failed to retrieveAll. Null child projects expected.", list.get(2).getChildProjects());
    }

    /**
     * Tests the <code>{@link ProjectDAOBean#retrieveAll(boolean)}</code> for accuracy.
     *
     * @throws Exception
     *             to junit
     */
    public void testRetrieveAllBoolean2() throws Exception {
        Client client = createClient(100);
        createProjectWithClient(10, client);
        createProjectWithClient(12, client);
        createProjectWithClient(13, client);

        setChildProject(10, 12);
        setChildProject(10, 13);

        List<Project> list = getTestBean().retrieveAll(true);
        assertEquals("Failed to retrieveAll.", list.size(), 4);
        assertNotNull("Failed to retrieveAll.", list.get(0).getChildProjects());
    }

    /**
     * <p>
     * Tests that the updateProjectBudget(String, long, double) method works properly.
     * </p>
     *
     * @throws Exception
     *             exception to pass to JUnit
     *
     * @since 1.2
     */
    public void testUpdateProjectBudget() throws Exception {
        Client client = createClient(42);
        createProjectWithClientAndBudget(101, client, 50);
        createProjectWithClientAndBudget(102, client, 25);
        createProjectWithClientAndBudget(103, client, 45);

        // Check the updated tables
        EntityManager em = getEntityManager();

        ProjectDAOBean bean = getTestBean();
        if (!em.getTransaction().isActive()) {
            em.getTransaction().begin();
        }
        double budget1 = bean.updateProjectBudget("topcoder1", 101, 300);
        double budget2 = bean.updateProjectBudget("topcoder2", 102, 400);
        double budget3 = bean.updateProjectBudget("topcoder3", 103, 500);
        em.getTransaction().commit();

        // Check the returned budget
        String error = "updateProjectBudget does not work properly";
        assertEquals(error, 350.0, budget1);
        assertEquals(error, 425.0, budget2);
        assertEquals(error, 545.0, budget3);

        Query query = em.createNativeQuery(
            "SELECT COUNT(*) FROM project WHERE project_id IN (101, 102, 103) AND budget IN (350.0, 425.0, 545.0)");
        BigDecimal result = (BigDecimal) query.getSingleResult();
        assertEquals(error, 3, result.intValue());

        query = em.createNativeQuery(
              "SELECT COUNT(*) FROM project_budget_audit WHERE project_id IN (101, 102, 103) "
            + "AND changed_amount IN (300, 400, 500) "
            + "AND creation_user IN ('topcoder1', 'topcoder2', 'topcoder3')");
        result = (BigDecimal) query.getSingleResult();
        assertEquals(error, 3, result.intValue());
    }

    /**
     * <p>
     * Tests that the getUsersByProject(long) method works properly.
     * </p>
     *
     * @throws Exception
     *             exception to pass to JUnit
     *
     * @since 1.2
     */
    public void testGetUsersByProject() throws Exception {
        Client client = createClient(42);
        createProjectWithClient(101, client);

        EntityManager em = getEntityManager();
        createCompany(1);
        ProjectDAOBean bean = getTestBean();
        getUserAccountId("topcoder", true);
        getUserAccountId("user", true);

        if (!em.getTransaction().isActive()) {
            em.getTransaction().begin();
        }
        bean.addUserToBillingProjects("topcoder", new long[] {101});
        bean.addUserToBillingProjects("user", new long[] {101});
        em.getTransaction().commit();

        addProjectWorker("worker", 101);
        addProjectWorker("builder", 101);
        addProjectWorker("topcoder", 101);


        List<String> users = bean.getUsersByProject(101);
        List<String> expected = Arrays.asList(new String[] {"topcoder", "user", "worker", "builder"});

        String error = "getUsersByProject does not work properly";
        assertEquals(error, 4, users.size());
        assertTrue(error, expected.containsAll(users));
    }

    /**
     * Creates the entity specific to this test.
     */
    @Override
    protected void createEntity() {
        Client client = createClient(51);
        Project project = createProjectWithClient(1001, client);
        setEntity(project);
    }

    /**
     * Creates the EJB specific to this test.
     */
    @Override
    protected void createTestBean() {
        ProjectDAOBean bean = new ProjectDAOBean();
        bean.setEntityManager(getEntityManager());
        setTestBean(bean);
    }
}
