/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.manager.accuracytests;

import com.topcoder.clients.manager.ProjectManager;
import com.topcoder.clients.manager.dao.DAOProjectManager;
import com.topcoder.clients.manager.dao.Util;
import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.Project;
import com.topcoder.clients.model.ProjectStatus;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.defaults.DefaultConfigurationObject;

import com.topcoder.search.builder.filter.EqualToFilter;

import junit.framework.TestCase;

import java.util.Calendar;
import java.util.List;


/**
 * Accuracy test for ProjectManager implementation.
 *
 * @author onsky
 * @version 1.0
 */
public class ProjectManagerTests extends TestCase {
    /** Represents the ClientManager instance for testing. */
    private ProjectManager manager = null;

    /**
     * Set up the environment.
     *
     * @throws Exception to junit
     */
    public void setUp() throws Exception {
        Util.clearConfigManager();
        Util.loadConfiguration();

        ConfigurationObject obj = new DefaultConfigurationObject("root");
        obj.setPropertyValue("id_generator_name", "test");
        obj.setPropertyValue("logger_name", "testlog");

        ConfigurationObject child = new DefaultConfigurationObject("object_factory_configuration");

        obj.addChild(child);
        obj.setPropertyValue("client_dao", "com.topcoder.clients.manager.accuracytests.MockClientDAO");
        obj.setPropertyValue("project_dao", "com.topcoder.clients.manager.accuracytests.MockProjectDAO");
        obj.setPropertyValue("project_status_dao", "com.topcoder.clients.manager.accuracytests.MockProjectStatusDAO");

        manager = new DAOProjectManager(obj);
    }

    /**
     * Clear the ConfigManager.
     *
     * @throws Exception to junit
     */
    public void tearDown() throws Exception {
        Util.clearConfigManager();
    }

    /**
     * <p>Accuracy test for the constructor <code>DAOProjectManager()</code>, expects the instance is created
     * properly.</p>
     *
     * @throws Exception to JUnit
     */
    public void testCtor1Accuracy() throws Exception {
        manager = new DAOProjectManager();
        assertNotNull("Failed to create the DAOProjectManager instance.", manager);
        assertNotNull("Failed to create the DAOProjectManager instance.",
                TestHelper.getPropertyByReflection(manager, "projectDAO"));
        assertNotNull("Failed to create the DAOProjectManager instance.",
                TestHelper.getPropertyByReflection(manager, "clientDAO"));
        assertNotNull("Failed to create the DAOProjectManager instance.",
                TestHelper.getPropertyByReflection(manager, "projectStatusDAO"));
    }

    /**
     * <p>Accuracy test for the constructor <code>DAOProjectManager(ConfigurationObject)</code>, expects the
     * instance is created properly.</p>
     *
     * @throws Exception to JUnit
     */
    public void testCtor2Accuracy() throws Exception {
        assertNotNull("Failed to create the DAOProjectManager instance.", manager);
        assertNotNull("Failed to create the DAOProjectManager instance.",
                TestHelper.getPropertyByReflection(manager, "projectDAO"));
        assertNotNull("Failed to create the DAOProjectManager instance.",
                TestHelper.getPropertyByReflection(manager, "clientDAO"));
        assertNotNull("Failed to create the DAOProjectManager instance.",
                TestHelper.getPropertyByReflection(manager, "projectStatusDAO"));
    }

    /**
     * <p>Accuracy test for method createProject.</p>
     *
     * @throws Exception to junit
     */
    public void testCreateProject() throws Exception {
        // create project
        Project project = new Project();
        project.setSalesTax(0.23d);
        project.setDescription("desc");
        project.setName("test");
        project.setDeleted(false);

        Project created = manager.createProject(project, getSampleClient());
        assertTrue("project must be create with an generated id", created.getId() > 0);
        assertEquals("the name should not be changed", "test", created.getName());
    }

    /**
     * <p>Accuracy test for method retrieveProject and updateProject.</p>
     *
     * @throws Exception to junit
     */
    public void testFindAndUpdateProject() throws Exception {
        // find project by id
        Project found = manager.retrieveProject(1L);
        assertNotNull("project with given id must exist", found);
        // update project
        found.setName("update project name");

        Project updated = manager.updateProject(found);
        assertEquals("project must be updated", "update project name", updated.getName());
    }

    /**
     * <p>Accuracy test for method deleteProject.</p>
     *
     * @throws Exception to junit
     */
    public void testDeleteProject() throws Exception {
        // find project by id
        Project found = manager.retrieveProject(1L);

        // delete project
        Project deleted = manager.deleteProject(found);
        assertTrue("the project must be deleted", deleted.isDeleted());
    }

    /**
     * <p>Accuracy test for method retrieveAllProjects.</p>
     *
     * @throws Exception to junit
     */
    public void testRetrieveAllProjects() throws Exception {
        // get all projects
        List<Project> projects = manager.retrieveAllProjects();
        assertEquals("there must be 3 projects in all", 3, projects.size());
    }

    /**
     * <p>Accuracy test for method retrieveProjectsForClient.</p>
     *
     * @throws Exception to junit
     */
    public void testRetrieveProjectsForClient() throws Exception {
        // get all projects
        List<Project> projects = manager.retrieveProjectsForClient(getSampleClient());
        assertEquals("there must be 2 projects in all for this client", 2, projects.size());
    }

    /**
     * <p>Accuracy test for method searchProjectsByName.</p>
     *
     * @throws Exception to junit
     */
    public void testSearchProjectsByName() throws Exception {
        // search project by name
        List<Project> projects = manager.searchProjectsByName("test");
        assertEquals("one record must return", 1, projects.size());
        assertEquals("project name must be correct", "test", projects.get(0).getName());
    }

    /**
     * <p>Accuracy test for method searchProjectsByName.</p>
     *
     * @throws Exception to junit
     */
    public void testSearchProjects() throws Exception {
        // search projects by filter
        List<Project> projects = manager.searchProjects(new EqualToFilter("test", new Boolean(false)));
        assertEquals("two record must return", 2, projects.size());
    }

    /**
     * <p>Accuracy test for method getProjectsForStatus.</p>
     *
     * @throws Exception to junit
     */
    public void testGetProjectsForStatus() throws Exception {
        ProjectStatus status = new ProjectStatus();
        status.setName("test");
        status.setDeleted(false);
        status.setDescription("desc");

        List<Project> projects = manager.getProjectsForStatus(status);
        assertEquals("two record must return", 2, projects.size());
    }

    /**
     * <p>Accuracy test for method createProjectStatus.</p>
     *
     * @throws Exception to junit
     */
    public void testCreateProjectStatus() throws Exception {
        // create project status
        ProjectStatus status = new ProjectStatus();
        status.setName("test");
        status.setDeleted(false);
        status.setDescription("desc");

        ProjectStatus created = manager.createProjectStatus(status);
        assertTrue("ProjectStatus must be created", created.getId() > 0);
    }

    /**
     * <p>Accuracy test for method updateProjectStatus.</p>
     *
     * @throws Exception to junit
     */
    public void testUpdateProjectStatus() throws Exception {
        Project found = manager.retrieveProject(1L);
        ProjectStatus status = found.getProjectStatus();
        status.setName("update project status name");

        ProjectStatus updated = manager.updateProjectStatus(status);
        assertEquals("project status must be updated", "update project status name", updated.getName());
    }

    /**
     * <p>Accuracy test for method deleteProjectStatus.</p>
     *
     * @throws Exception to junit
     */
    public void testDeleteProjectStatus() throws Exception {
        Project found = manager.retrieveProject(1L);
        ProjectStatus status = found.getProjectStatus();

        // delete project
        ProjectStatus deleted = manager.deleteProjectStatus(status);
        assertTrue("the project must be deleted", deleted.isDeleted());
    }

    /**
     * Get Sample Client instance.
     * @return sample client instance
     */
    private Client getSampleClient() {
        Client client = new Client();
        Calendar calendar = Calendar.getInstance();
        client.setEndDate(calendar.getTime());
        calendar.set(2008, 1, 1);
        client.setStartDate(calendar.getTime());
        client.setName("client name");
        client.setDeleted(false);

        return client;
    }
}
