/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.dao;

import java.util.List;

import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.topcoder.clients.dao.ejb3.ProjectDAORemote;
import com.topcoder.clients.dao.ejb3.TestBase;
import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.Project;
import com.topcoder.clients.model.ProjectContestFee;

/**
 * The demo of this component.
 *
 * @author Mafy, nhzp339, TCSDEVELOPER
 * @version 1.2
 */
public class DemoTests extends TestBase {

    /**
     * <p>
     * Represents the remote interface of <code>ProjectDAOBean</code>.
     * </p>
     */
    private ProjectDAORemote bean;

    /**
     * <p>
     * Represents the Project instance.
     * </p>
     */
    private Project project;
    /**
     * <p>
     * Setup the testing environment.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();

        // prepare data
        // create dummy company
        createCompany(1);
        Client client = createClient(200);
        project = createProjectWithInitialBudget(100, client, 4000.0);
        createProjectWithClient(101, client);
        createProjectWithClient(102, client);
        createProjectWithClient(103, client);
        createProjectWithClient(123, client);
        getEntityManager().getTransaction().commit();

        // retrieve bean
        InitialContext ctx = new InitialContext();
        bean = (ProjectDAORemote) ctx.lookup("client_project_entities_dao/ProjectDAOBean/remote");
    }

    /**
     * This is demo for this component. For other entities and entity dao are almost the same as Project, the usage of
     * other entities is not showed.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testDemo() throws Exception {
        // get project for corresponding id
        Project tempProject = bean.retrieveById(100L);

        // get all projects
        List<Project> projects = bean.retrieveAll();

        // get all projects with the name "name"
        projects = bean.searchByName("name");

        // get all that match the given filter
        // Since the EqualToFilter class is not serializable, it can not be called remotely.
        // Filter filter = new EqualToFilter("projectStatus", project.getProjectStatus().getId());
        // projects = bean.search(filter);

        // save or update a project
        bean.save(project);

        // get project for corresponding id without projectChildren
        tempProject = bean.retrieveById(100L, false);

        // get project for corresponding id with projectChildren
        tempProject = bean.retrieveById(100L, true);

        // get all projects without projectChildren
        projects = bean.retrieveAll(false);

        // get all projects with projectChildren
        projects = bean.retrieveAll(true);

        // get projects by user
        projects = bean.getProjectsByUser("username");

        // get all projects only
        projects = bean.retrieveAllProjectsOnly();

        // search projects by project name
        projects = bean.searchProjectsByProjectName("projectname");

        // search projects by client name
        projects = bean.searchProjectsByClientName("clientname");

        // get contest fees by project
        List<ProjectContestFee> fees = bean.getContestFeesByProject(100L);

        // save contest fees
        bean.saveContestFees(fees, 100L);

        // check client project permission
        boolean clientProjectPermission = bean.checkClientProjectPermission("username", 100L);

        // check po number permission
        boolean poNumberPermission = bean.checkPoNumberPermission("username", "123456A");

        // add user to billing projects.
        bean.addUserToBillingProjects("username", new long[] {100, 101, 102});

        // remove user from billing projects.
        bean.removeUserFromBillingProjects("ivern", new long[] {100, 201});

        // get the projects by the given client id.
        projects = bean.getProjectsByClientId(200);

        // delete the project
        bean.delete(project);
    }

    /**
     * Demonstrates the new updateProjectBudget functionality.
     *
     * @throws Exception
     *             to JUnit
     * @since 1.2
     */
    public void testUpdateProjectBudget() throws Exception {
        // Increase the budget of project with id 100 with 500.
        double newBudget = bean.updateProjectBudget("ivern", 100, 500);

        // 4500 should be returned and the budget for id 100 will be updated to 4500, besides, a record will be added
        // into project_budget_audit table with project_id = 100, changed_amount = 500 and creation_user="ivern"
    }

    /**
     * Demonstrates the new getUsersByProject functionality.
     *
     * @throws Exception
     *             to JUnit
     *
     * @since 1.2
     */
    public void testGetUsersByProject() throws Exception {
        // prepare the data
        EntityManager entityManager = getEntityManager();
        Query insertUserQuery = entityManager.createNativeQuery(INSERT_DUMMY_USER_ACCOUNT);
        for (int i = 1; i <= 5; i++) {
            insertUserQuery.setParameter("userAccountId", 100 + i);
            insertUserQuery.setParameter("userName", "user" + i);
            insertUserQuery.executeUpdate();
        }

        Query insertProjectManager = entityManager.createNativeQuery(INSERT_PROJECT_MANAGER);
        insertProjectManager.setParameter("projectId", 123);
        insertProjectManager.setParameter("userAccountId", 101);
        insertProjectManager.setParameter("active", 1);
        insertProjectManager.executeUpdate();

        insertProjectManager.setParameter("projectId", 123);
        insertProjectManager.setParameter("userAccountId", 102);
        insertProjectManager.setParameter("active", 0);
        insertProjectManager.executeUpdate();

        Query insertProjectWorker = entityManager.createNativeQuery(INSERT_PROJECT_WORKER);
        insertProjectWorker.setParameter("projectId", 123);
        insertProjectWorker.setParameter("userAccountId", 104);
        insertProjectWorker.setParameter("active", 1);
        insertProjectWorker.executeUpdate();

        insertProjectWorker.setParameter("projectId", 123);
        insertProjectWorker.setParameter("userAccountId", 105);
        insertProjectWorker.setParameter("active", 0);
        insertProjectWorker.executeUpdate();
        entityManager.getTransaction().commit();

        // Now, get the user names who have access of the billing project with id 123.
        List<String> users = bean.getUsersByProject(123);
        // users list should contain 2 elements(“user1” and “user4”).
    }

}
