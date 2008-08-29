/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.manager.dao;

import java.util.List;

import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.Project;
import com.topcoder.clients.model.ProjectStatus;
import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.defaults.DefaultConfigurationObject;
import com.topcoder.search.builder.filter.EqualToFilter;

import junit.framework.TestCase;

/**
 * Unit test cases for class <code>DAOProjectManager </code>.
 *
 * <p>
 * This class will test the accuracy performence of DAOProjectManager class.
 * </p>
 *
 * @author Chenhong
 * @version 1.0
 *
 */
public class TestDAOProjectManagerAccuracy extends TestCase {

    /**
     * Represents the DAOProjectManager instance for testing.
     */
    private DAOProjectManager manager = null;

    /**
     * Set up the environment.
     *
     * @throws Exception
     *             to junit
     */
    public void setUp() throws Exception {
        Util.clearConfigManager();
        Util.loadConfiguration();

        ConfigurationObject obj = new DefaultConfigurationObject("root");
        obj.setPropertyValue("id_generator_name", "test");
        obj.setPropertyValue("logger_name", "System.out");

        ConfigurationObject child = new DefaultConfigurationObject("object_factory_configuration");

        obj.addChild(child);
        obj.setPropertyValue("client_dao", "com.topcoder.clients.manager.dao.MockClientDAOAcc");
        obj.setPropertyValue("project_dao", "com.topcoder.clients.manager.dao.MockProjectDAOAccuracy");
        obj.setPropertyValue("project_status_dao", "com.topcoder.clients.manager.dao.MockProjectStatusDAOAcc");

        manager = new DAOProjectManager(obj);
    }

    /**
     * Clear the ConfigManager.
     *
     * @throws Exception
     *             to junit
     */
    public void tearDown() throws Exception {
        Util.clearConfigManager();
    }

    /**
     * Test method for 'DAOProjectManager()'.
     * <p>
     * The DAOProjectManager should be created.
     * </p>
     * @throws Exception to junit
     */
    public void testDAOProjectManager() throws Exception {
        manager = new DAOProjectManager();

        assertNotNull("The DAOProjectManager should be created.", manager);
    }

    /**
     * Test method for 'DAOProjectManager(ConfigurationObject)'.
     *
     * <p>
     * A DAOProjectManager should be created by the ConfigurationObject.
     * </p>
     * @throws Exception to junit
     */
    public void testDAOProjectManagerConfigurationObject() throws Exception {
        assertNotNull("The DAOProjectManager should be created.", manager);
    }

    /**
     * Test method for 'DAOProjectManager(String, String)'.
     *
     * <p>
     * The DAOProjectManager should be created.
     * </p>
     * @throws Exception to junit
     */
    public void testDAOProjectManagerStringString() throws Exception {
        manager = new DAOProjectManager("test_files/config.properties", "ProjectManager");
    }

    /**
     * Test method for 'createProject(Project, Client)'.
     *
     * @throws Exception to junit
     */
    public void testCreateProject() throws Exception {
        Project project = new Project();
        project.setSalesTax(10.0);
        project.setName("name");
        project.setDescription("description");
        project.setDeleted(false);

        System.out.println(project.getId());

        Client client = new Client();
        client.setName("client");

        Project ret = manager.createProject(project, client);

        // check if id is generated.
        assertTrue("A positive id must be set in.", ret.getId() > 0);

        // check if the save method is called, the modify name should be updated to 'projectDAO';
        assertEquals("Equal to 'projectDAO'", "projectDAO", ret.getModifyUsername());
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOProjectManager.updateProject(Project)'.
     * <p>
     * The project should be updated.
     * </p>
     * @throws Exception to junit
     */
    public void testUpdateProject() throws Exception {
        Project project = new Project();
        project.setSalesTax(10.0);
        project.setName("name");
        project.setDescription("description");
        project.setDeleted(false);
        project.setId(10L);

        Project ret = manager.updateProject(project);


        // check if the save method is called, the modify name should be updated to 'projectDAO';
        assertEquals("Equal to 'projectDAO'", "projectDAO", ret.getModifyUsername());
    }

    /**
     * Test method for 'deleteProject(Project)'.
     *
     * <p>
     * The project should be deleted. The isDeleted should be set to true.
     * </p>
     * @throws Exception to junit
     */
    public void testDeleteProject()  throws Exception {
        Project project = new Project();
        project.setSalesTax(10.0);
        project.setName("name");
        project.setDescription("description");
        project.setDeleted(false);
        project.setId(10L);

        Project ret = manager.deleteProject(project);

        assertTrue("The isDeleted should be true.", ret.isDeleted());

        // check if the save method is called, the modify name should be updated to 'projectDAO';
        assertEquals("Equal to 'projectDAO'", "deleted", ret.getModifyUsername());
    }

    /**
     * Test method for 'retrieveProject(long)'.
     * <p>
     * No Project with id = 1;
     * </p>
     * @throws Exception to junit
     */
    public void testRetrieveProjectLong_1() throws Exception {
        Project ret = manager.retrieveProject(1L);

        assertNull("No Project with id = 1", ret);
    }

    /**
     * Test method for 'retrieveProject(long)'.
     * <p>
     * Project existed with id = 10;
     * </p>
     * @throws Exception to junit
     */
    public void testRetrieveProjectLong_2() throws Exception {
        Project ret = manager.retrieveProject(10L);

        assertNotNull("A project with id = 10 should be returned.", ret);

        assertEquals("The name should be 'name'", "name", ret.getName());
    }

    /**
     * Test method for 'retrieveProject(long, boolean)'.
     * <p>
     * No project with id 1.
     * </p>
     * @throws Exception to junit
     */
    public void testRetrieveProjectLongBoolean() throws Exception {
        Project ret = manager.retrieveProject(1L, false);

        assertNull("No Project with id = 1", ret);

    }

    /**
     * Test method for 'retrieveProject(long, boolean)'.
     * <p>
     * A project existed with id = 10.
     * </p>
     * @throws Exception to junit
     */
    public void testRetrieveProjectLongBoolean_2() throws Exception {
        Project ret = manager.retrieveProject(10L, false);

        assertNotNull("A project with id = 10 should be returned.", ret);

        assertEquals("The name should be 'name'", "name", ret.getName());
    }

    /**
     * Test method for 'retrieveProjectsForClient(Client)'.
     *
     * <p>
     * In this test case, the clientId is 1, then an empty Project list will be returned from MockClientDAOAcc class.
     * </p>
     * @throws Exception to junit
     */
    public void testRetrieveProjectsForClientClient_1() throws Exception {
        Client client = new Client();
        client.setId(1L);


        List<Project> ret = manager.retrieveProjectsForClient(client);

        assertTrue("An empty list is expected.", ret.isEmpty());
    }


    /**
     * Test method for 'retrieveProjectsForClient(Client)'.
     *
     * <p>
     * In this test case, the clientId is 2, then 2 Project will be returned.
     * </p>
     * @throws Exception to junit
     */
    public void testRetrieveProjectsForClientClient_2() throws Exception {
        Client client = new Client();
        client.setId(2L);

        List<Project> ret = manager.retrieveProjectsForClient(client);

        assertEquals("A list with 2 project should be returned", 2, ret.size());

        assertEquals("Equal to 'name'", "name", ret.get(0).getName());
        assertEquals("Equal to 'name'", "name2", ret.get(1).getName());
    }

    /**
     * Test method for 'retrieveProjectsForClient(Client, boolean)'.
     * <p>
     * In this test case, the include is set to false. The project returned from projectDAO will be returned.
     * </p>
     * @throws Exception to junit
     */
    public void testRetrieveProjectsForClientClientBoolean() throws Exception {
        Client client = new Client();
        client.setId(2L);


        List<Project> ret = manager.retrieveProjectsForClient(client, false);

        assertEquals("A list with 2 project should be returned", 2, ret.size());

        assertEquals("Equal to 'name1'", "name1", ret.get(0).getName());
        assertEquals("Equal to 'name'", "name2", ret.get(1).getName());

    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOProjectManager.retrieveAllProjects()'.
     * @throws Exception to junit
     */
    public void testRetrieveAllProjects() throws Exception {
        List<Project> ret = manager.retrieveAllProjects();

        assertEquals("Equal to size 2.", 2, ret.size());
    }

    /**
     * Test method for 'retrieveAllProjects(boolean)'.
     *
     * <p>
     * In this test case, a false is passed as parameter, an empty list will be returned.
     * </p>
     * @throws Exception to junit
     */
    public void testRetrieveAllProjectsBoolean_1() throws Exception {
        List<Project> ret = manager.retrieveAllProjects(false);

        assertTrue("Should be empty.", ret.isEmpty());
    }


    /**
     * Test method for 'retrieveAllProjects(boolean)'.
     *
     * <p>
     * In this test case, true is passed as parameter, a list of 2 Project will be returned.
     * </p>
     * @throws Exception to junit
     */
    public void testRetrieveAllProjectsBoolean_2() throws Exception {
        List<Project> ret = manager.retrieveAllProjects(true);

        assertEquals("Equal to size 2.", 2, ret.size());
    }

    /**
     * Test method for 'setProjectStatus(Project, ProjectStatus)'.
     * @throws Exception to junit
     */
    public void testSetProjectStatus() throws Exception {
        Project project = new Project();
        project.setSalesTax(10.0);
        project.setName("name");
        project.setDescription("description");
        project.setDeleted(false);
        project.setId(10L);


        ProjectStatus status = new ProjectStatus();
        status.setDeleted(false);
        status.setName("status");
        status.setDescription("des");

        Project ret = manager.setProjectStatus(project, status);

        assertEquals("Equal to 'status'", "status", ret.getProjectStatus().getName());

        assertEquals("Equal to 'projectDAO'", "projectDAO", ret.getModifyUsername());
    }

    /**
     * Test method for 'searchProjectsByName(String)'.
     *
     * <p>
     * In this test case, the param is 'empty'. An empty list will be returned.
     * </p>
     * @throws Exception ot junit
     */
    public void testSearchProjectsByName_1() throws Exception  {
        List<Project> ret = manager.searchProjectsByName("empty");

        assertEquals("Equal to 0.", 0, ret.size());
    }

    /**
     * Test method for 'searchProjectsByName(String)'.
     *
     * <p>
     * In this test case, the param is '2'. one Project will be returned.
     * </p>
     * @throws Exception ot junit
     */
    public void testSearchProjectsByName_2() throws Exception  {
        List<Project> ret = manager.searchProjectsByName("2");

        assertEquals("Equal to 1.", 1, ret.size());

        assertEquals("Equal to 'name'", "name", ret.get(0).getName());
    }

    /**
     * Test method for 'searchProjects(Filter)'.
     * @throws Exception to junit
     */
    public void testSearchProjects() throws Exception {
        EqualToFilter equalToFilter = new EqualToFilter("DAOConfigurationException", new Boolean(false));
        List<Project> ret = manager.searchProjects(equalToFilter);

        assertEquals("Equal to 1.", 1, ret.size());

        assertEquals("Equal to 'name'", "name", ret.get(0).getName());
    }

    /**
     * Test method for 'getProjectStatus(long)'.
     * <p>
     * For id =1, a null will be returned.
     * <p>
     * @throws Exception to junit
     */
    public void testGetProjectStatus_1() throws Exception {
        ProjectStatus ret = manager.getProjectStatus(1L);

        assertNull("Should be null.", ret);
    }


    /**
     * Test method for 'getProjectStatus(long)'.
     * <p>
     * For id !=1, a ProjectStatus will be returned.
     * <p>
     * @throws Exception to junit
     */
    public void testGetProjectStatus_2() throws Exception {
        ProjectStatus ret = manager.getProjectStatus(2L);

        assertEquals("Equal to retrieve", "retrieve", ret.getName());
    }

    /**
     * Test method for 'getAllProjectStatuses()'.
     * <p>
     * An empty list will be returned.
     * </p>
     * @throws Exception to junit
     */
    public void testGetAllProjectStatuses() throws Exception  {
        List<ProjectStatus> ret = manager.getAllProjectStatuses();

        assertTrue("Empty.", ret.isEmpty());
    }

    /**
     * Test method for 'getProjectsForStatus(ProjectStatus)'.
     * <p>
     * An empty Project list will be returned.
     * </p>
     *
     * @throws Exception to junit
     */
    public void testGetProjectsForStatus()  throws Exception{
        ProjectStatus status = new ProjectStatus();
        status.setDeleted(false);
        status.setName("status");
        status.setDescription("des");


        List<Project> ret = manager.getProjectsForStatus(status);

        assertTrue("Empty.", ret.isEmpty());
    }

    /**
     * Test method for 'createProjectStatus(ProjectStatus)'.
     * <p>
     * A new id will be generated for this ProjectStatus instance.
     * </p>
     * @throws Exception to junit
     */
    public void testCreateProjectStatus()  throws Exception {
        ProjectStatus status = new ProjectStatus();
        status.setDeleted(false);
        status.setName("status");
        status.setDescription("des");

        ProjectStatus ret = manager.createProjectStatus(status);

        assertTrue("The id should be set.", ret.getId() > 0);

        assertEquals("create user name should be 'status'", "status", ret.getCreateUsername());
    }

    /**
     * Test method for 'updateProjectStatus(ProjectStatus)'.
     * <p>
     * The ProjecStatus should be updated.
     * </p>
     * @throws Exception to junit
     */
    public void testUpdateProjectStatus()  throws Exception{
        ProjectStatus status = new ProjectStatus();
        status.setDeleted(false);
        status.setName("status");
        status.setDescription("des");
        status.setId(10L);

        ProjectStatus ret = manager.updateProjectStatus(status);

        assertEquals("Equal to 'status'", "status", ret.getCreateUsername());
    }

    /**
     * Test method for 'deleteProjectStatus(ProjectStatus)'.
     * <p>
     * The ProjectStatus should be deleted. The isDeleted should be set to true.
     * </p>
     * @throws Exception to junit
     */
    public void testDeleteProjectStatus()  throws Exception{
        ProjectStatus status = new ProjectStatus();
        status.setDeleted(false);
        status.setName("status");
        status.setDescription("des");
        status.setId(10L);

        ProjectStatus ret = manager.deleteProjectStatus(status);

        assertTrue("The isDeleted should be true.", ret.isDeleted());
    }

}
