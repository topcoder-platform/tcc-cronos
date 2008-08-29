/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.manager.stresstests;

import java.util.List;

import com.topcoder.clients.manager.dao.DAOProjectManager;
import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.Project;
import com.topcoder.clients.model.ProjectStatus;
import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.defaults.DefaultConfigurationObject;
import com.topcoder.search.builder.filter.EqualToFilter;

import junit.framework.TestCase;

/**
 * <p>
 * Stress tests for <code>DAOProjectManager</code>.
 * </p>
 * @author mayday
 * @version 1.0
 *
 */
public class DAOProjectManagerStressTest extends TestCase {

    /**
     * Number of repeat.
     */
    private static final int REPEAT_NUMBER = 10;

    /**
     * <p>
     * DAOProjectManager used in test.
     * </p>
     */
    private DAOProjectManager manager;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();
        StressTestHelper.loadConfig();

        ConfigurationObject obj = new DefaultConfigurationObject("root");
        obj.setPropertyValue("id_generator_name", "test");
        obj.setPropertyValue("logger_name", "logger");

        ConfigurationObject child = new DefaultConfigurationObject("object_factory_configuration");

        obj.addChild(child);
        obj.setPropertyValue("client_dao", "com.topcoder.clients.manager.stresstests.MockClientDAOStress");
        obj.setPropertyValue("project_dao", "com.topcoder.clients.manager.stresstests.MockProjectDAOStress");
        obj.setPropertyValue("project_status_dao", "com.topcoder.clients.manager.stresstests.MockProjectStatusDAOStress");

        manager = new DAOProjectManager(obj);
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        StressTestHelper.clearConfig();
        manager = null;
    }

    /**
     * Test method for {@link com.topcoder.clients.manager.dao.DAOProjectManager#
     * createProject(com.topcoder.clients.model.Project, com.topcoder.clients.model.Client)}.
     *
     * Test create project and check result and see time.
     *
     * @throws Exception to JUnit
     */
    public void testCreateProject() throws Exception {

        long time = System.currentTimeMillis();

        for (int i = 0; i < REPEAT_NUMBER; i++) {
            Project ret = manager.createProject(createProject(), createClient());
            assertEquals("should be project", "project", ret.getName());
            assertEquals("should be stress", "stress", ret.getModifyUsername());
        }

        time = System.currentTimeMillis() - time;

        System.out.println("retrieve took " + time + " ms");
    }

    /**
     * Test method for {@link com.topcoder.clients.manager.dao.DAOProjectManager#
     * updateProject(com.topcoder.clients.model.Project)}.
     *
     * Test update project and check result and see time.
     *
     * @throws Exception to JUnit
     */
    public void testUpdateProject() throws Exception {

        long time = System.currentTimeMillis();

        for (int i = 0; i < REPEAT_NUMBER; i++) {
            Project ret = manager.updateProject(createProject());
            assertEquals("should be stress", "stress", ret.getModifyUsername());
        }

        time = System.currentTimeMillis() - time;

        System.out.println("retrieve took " + time + " ms");
    }

    /**
     * Test method for {@link com.topcoder.clients.manager.dao.DAOProjectManager#
     * deleteProject(com.topcoder.clients.model.Project)}.
     *
     * Test delete project and check result and see time.
     *
     * @throws Exception to JUnit
     */
    public void testDeleteProject() throws Exception {

        long time = System.currentTimeMillis();

        for (int i = 0; i < REPEAT_NUMBER; i++) {
            Project ret = manager.deleteProject(createProject());
            assertEquals("should be stress", "stress", ret.getModifyUsername());
            assertTrue("should be true", ret.isDeleted());
        }

        time = System.currentTimeMillis() - time;

        System.out.println("retrieve took " + time + " ms");
    }

    /**
     * Test method for {@link com.topcoder.clients.manager.dao.DAOProjectManager#retrieveProject(long)}.
     *
     * Test retrieve project and check result and see time.
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveProjectLong() throws Exception {

        long time = System.currentTimeMillis();

        for (int i = 0; i < REPEAT_NUMBER; i++) {
            Project ret = manager.retrieveProject(10);
            assertEquals("should be project", "project", ret.getName());
        }

        time = System.currentTimeMillis() - time;

        System.out.println("retrieve took " + time + " ms");
    }

    /**
     * Test method for {@link com.topcoder.clients.manager.dao.DAOProjectManager#retrieveProject(long, boolean)}.
     *
     * Test retrieve project and check result and see time.
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveProjectLongBoolean() throws Exception {

        long time = System.currentTimeMillis();

        for (int i = 0; i < REPEAT_NUMBER; i++) {
            Project ret = manager.retrieveProject(10, false);
            assertEquals("should be project", "project", ret.getName());
        }

        time = System.currentTimeMillis() - time;

        System.out.println("retrieve took " + time + " ms");
    }

    /**
     * Test method for {@link com.topcoder.clients.manager.dao.DAOProjectManager#
     * retrieveProjectsForClient(com.topcoder.clients.model.Client)}.
     *
     * Test retrieve project for client and check result and see time.
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveProjectsForClientClient() throws Exception {

        long time = System.currentTimeMillis();

        for (int i = 0; i < REPEAT_NUMBER; i++) {
            List<Project> ret = manager.retrieveProjectsForClient(createClient());
            assertTrue("should be 2", ret.size() == 2);
            assertEquals("should be project", "project", ret.get(0).getName());
        }

        time = System.currentTimeMillis() - time;

        System.out.println("retrieve took " + time + " ms");
    }

    /**
     * Test method for {@link com.topcoder.clients.manager.dao.DAOProjectManager#
     * retrieveProjectsForClient(com.topcoder.clients.model.Client, boolean)}.
     *
     * Test retrieve project for client and check result and see time.
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveProjectsForClientClientBoolean() throws Exception {

        long time = System.currentTimeMillis();

        for (int i = 0; i < REPEAT_NUMBER; i++) {
            List<Project> ret = manager.retrieveProjectsForClient(createClient(), false);
            assertTrue("should be 2", ret.size() == 2);
            assertEquals("should be project1", "project1", ret.get(0).getName());
        }

        time = System.currentTimeMillis() - time;

        System.out.println("retrieve took " + time + " ms");
    }

    /**
     * Test method for {@link com.topcoder.clients.manager.dao.DAOProjectManager#retrieveAllProjects()}.
     *
     * Test retrieve all projects and check result and see time.
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveAllProjects() throws Exception {

        long time = System.currentTimeMillis();

        for (int i = 0; i < REPEAT_NUMBER; i++) {
            List<Project> ret = manager.retrieveAllProjects();
            assertTrue("should be 1", ret.size() == 1);
            assertEquals("should be project", "project", ret.get(0).getName());
        }

        time = System.currentTimeMillis() - time;

        System.out.println("retrieve took " + time + " ms");
    }

    /**
     * Test method for {@link com.topcoder.clients.manager.dao.DAOProjectManager#retrieveAllProjects(boolean)}.
     *
     * Test retrieve all projects and check result and see time.
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveAllProjectsBoolean() throws Exception {

        long time = System.currentTimeMillis();

        for (int i = 0; i < REPEAT_NUMBER; i++) {
            List<Project> ret = manager.retrieveAllProjects(false);
            assertTrue("should be 1", ret.size() == 1);
            assertEquals("should be project", "project", ret.get(0).getName());
        }

        time = System.currentTimeMillis() - time;

        System.out.println("retrieve took " + time + " ms");
    }

    /**
     * Test method for {@link com.topcoder.clients.manager.dao.DAOProjectManager#
     * setProjectStatus(com.topcoder.clients.model.Project, com.topcoder.clients.model.ProjectStatus)}.
     *
     * Test set project status and check result and see time.
     *
     * @throws Exception to JUnit
     */
    public void testSetProjectStatus() throws Exception {

        long time = System.currentTimeMillis();

        for (int i = 0; i < REPEAT_NUMBER; i++) {
            Project ret = manager.setProjectStatus(createProject(), createProjectStatus());
            assertEquals("should be project", "project", ret.getName());
            assertEquals("should be stress", "stress", ret.getModifyUsername());
        }

        time = System.currentTimeMillis() - time;

        System.out.println("retrieve took " + time + " ms");
    }

    /**
     * Test method for {@link com.topcoder.clients.manager.dao.DAOProjectManager#
     * searchProjectsByName(java.lang.String)}.
     *
     * Test search project by name and check result and see time.
     *
     * @throws Exception to JUnit
     */
    public void testSearchProjectsByName() throws Exception {

        long time = System.currentTimeMillis();

        for (int i = 0; i < REPEAT_NUMBER; i++) {
            List<Project> ret = manager.searchProjectsByName("project");
            assertTrue("should be 1", ret.size() == 1);
            assertEquals("should be project", "project", ret.get(0).getName());
        }

        time = System.currentTimeMillis() - time;

        System.out.println("retrieve took " + time + " ms");
    }

    /**
     * Test method for {@link com.topcoder.clients.manager.dao.DAOProjectManager#
     * searchProjects(com.topcoder.search.builder.filter.Filter)}.
     *
     * Test search projects and check result and see time.
     *
     * @throws Exception to JUnit
     */
    public void testSearchProjects() throws Exception {

        long time = System.currentTimeMillis();

        for (int i = 0; i < REPEAT_NUMBER; i++) {
            List<Project> ret = manager.searchProjects(new EqualToFilter("client", false));
            assertTrue("should be 1", ret.size() == 1);
            assertEquals("should be project", "project", ret.get(0).getName());
        }

        time = System.currentTimeMillis() - time;

        System.out.println("retrieve took " + time + " ms");
    }

    /**
     * Test method for {@link com.topcoder.clients.manager.dao.DAOProjectManager#getProjectStatus(long)}.
     *
     * Test get project status and check result and see time.
     *
     * @throws Exception to JUnit
     */
    public void testGetProjectStatus() throws Exception {

        long time = System.currentTimeMillis();

        for (int i = 0; i < REPEAT_NUMBER; i++) {
            ProjectStatus ret = manager.getProjectStatus(10);
            assertEquals("should be project status", "project status", ret.getName());
        }

        time = System.currentTimeMillis() - time;

        System.out.println("retrieve took " + time + " ms");
    }

    /**
     * Test method for {@link com.topcoder.clients.manager.dao.DAOProjectManager#getAllProjectStatuses()}.
     *
     * Test get all project statuses and check result and see time.
     *
     * @throws Exception to JUnit
     */
    public void testGetAllProjectStatuses() throws Exception {

        long time = System.currentTimeMillis();

        for (int i = 0; i < REPEAT_NUMBER; i++) {
            List<ProjectStatus> ret = manager.getAllProjectStatuses();
            assertTrue("should be 1", ret.size() == 1);
            assertEquals("should be project status", "project status", ret.get(0).getName());
        }

        time = System.currentTimeMillis() - time;

        System.out.println("retrieve took " + time + " ms");
    }

    /**
     * Test method for {@link com.topcoder.clients.manager.dao.DAOProjectManager#
     * getProjectsForStatus(com.topcoder.clients.model.ProjectStatus)}.
     *
     * Test get projects for status and check result and see time.
     *
     * @throws Exception to JUnit
     */
    public void testGetProjectsForStatus() throws Exception {

        long time = System.currentTimeMillis();

        for (int i = 0; i < REPEAT_NUMBER; i++) {
            List<Project> ret = manager.getProjectsForStatus(createProjectStatus());
            assertTrue("should be 1", ret.size() == 1);
            assertEquals("should be project", "project", ret.get(0).getName());
        }

        time = System.currentTimeMillis() - time;

        System.out.println("retrieve took " + time + " ms");
    }

    /**
     * Test method for {@link com.topcoder.clients.manager.dao.DAOProjectManager#
     * createProjectStatus(com.topcoder.clients.model.ProjectStatus)}.
     *
     * Test create project status and check result and see time.
     *
     * @throws Exception to JUnit
     */
    public void testCreateProjectStatus() throws Exception {

        long time = System.currentTimeMillis();

        for (int i = 0; i < REPEAT_NUMBER; i++) {
            ProjectStatus ret = manager.createProjectStatus(createProjectStatus());
            assertEquals("should be project status", "project status", ret.getName());
        }

        time = System.currentTimeMillis() - time;

        System.out.println("retrieve took " + time + " ms");
    }

    /**
     * Test method for {@link com.topcoder.clients.manager.dao.DAOProjectManager#
     * updateProjectStatus(com.topcoder.clients.model.ProjectStatus)}.
     *
     * Test update project status and check result and see time.
     *
     * @throws Exception to JUnit
     */
    public void testUpdateProjectStatus() throws Exception {

        long time = System.currentTimeMillis();

        for (int i = 0; i < REPEAT_NUMBER; i++) {
            ProjectStatus ret = manager.updateProjectStatus(createProjectStatus());
            assertEquals("should be stress", "stress", ret.getModifyUsername());
        }

        time = System.currentTimeMillis() - time;

        System.out.println("retrieve took " + time + " ms");
    }

    /**
     * Test method for {@link com.topcoder.clients.manager.dao.DAOProjectManager#
     * deleteProjectStatus(com.topcoder.clients.model.ProjectStatus)}.
     *
     * Test delete project status and check result and see time.
     *
     * @throws Exception to JUnit
     */
    public void testDeleteProjectStatus() throws Exception {

        long time = System.currentTimeMillis();

        for (int i = 0; i < REPEAT_NUMBER; i++) {
            ProjectStatus ret = manager.deleteProjectStatus(createProjectStatus());
            assertEquals("should be stress", "stress", ret.getModifyUsername());
        }

        time = System.currentTimeMillis() - time;

        System.out.println("retrieve took " + time + " ms");
    }

    /**
     * Create a client for test.
     * @return the created client.
     */
    private Client createClient() {
        Client client = new Client();
        client.setStartDate(StressTestHelper.STARTDATE);
        client.setEndDate(StressTestHelper.ENDDATE);
        client.setName("client");
        client.setDeleted(false);
        client.setId(1L);
        return client;
    }

    /**
     * Create a project for test.
     * @return the created project.
     */
    private Project createProject() {
        Project project = new Project();
        project.setSalesTax(10.0);
        project.setName("project");
        project.setDescription("description");
        project.setDeleted(false);
        project.setId(10L);
        return project;
    }

    /**
     * Create a ProjectStatus for test.
     * @return the created ProjectStatus.
     */
    private ProjectStatus createProjectStatus() {
        ProjectStatus status = new ProjectStatus();
        status.setName("project status");
        status.setDescription("des");
        status.setId(10L);
        return status;
    }

}
