/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.manager.dao;

import java.lang.reflect.Field;

import com.topcoder.clients.manager.ManagerConfigurationException;
import com.topcoder.clients.manager.MockProjectDAO;
import com.topcoder.clients.manager.MockProjectStatusDAO;
import com.topcoder.clients.manager.ProjectEntityNotFoundException;
import com.topcoder.clients.manager.ProjectManagerException;
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
 * This class will test the failure cases of DAOProjectManager class.
 * </p>
 *
 * @author Chenhong
 * @version 1.0
 *
 */
public class TestDAOProjectManagerFailure extends TestCase {
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
        obj.setPropertyValue("client_dao", "com.topcoder.clients.manager.MockClientDAO");
        obj.setPropertyValue("project_dao", "com.topcoder.clients.manager.MockProjectDAO");
        obj.setPropertyValue("project_status_dao", "com.topcoder.clients.manager.MockProjectStatusDAO");

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
     * Test method for 'DAOProjectManager(ConfigurationObject)'.
     * <p>
     * No object_factory_configuration child configuration, ManagerConfigurationException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testDAOProjectManagerConfigurationObject_1() throws Exception {
        try {

            ConfigurationObject obj = new DefaultConfigurationObject("root");
            obj.setPropertyValue("id_generator_name", "test");
            obj.setPropertyValue("logger_name", "System.out");

            obj.setPropertyValue("client_dao", "com.topcoder.clients.manager.dao.MockClientDAOAcc");
            obj.setPropertyValue("project_dao", "com.topcoder.clients.manager.dao.MockProjectDAOAccuracy");
            obj.setPropertyValue("projectStatus_dao", "com.topcoder.clients.manager.MockProjectStatusDAO");

            new DAOProjectManager(obj);
            fail("ManagerConfigurationException is expected.");
        } catch (ManagerConfigurationException e) {
            // ok
        }
    }

    /**
     * Test method for 'DAOProjectManager(ConfigurationObject)'.
     * <p>
     * Missing client_dao properties, ManagerConfigurationException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testDAOProjectManagerConfigurationObject_2() throws Exception {
        ConfigurationObject obj = new DefaultConfigurationObject("root");
        obj.setPropertyValue("id_generator_name", "test");
        obj.setPropertyValue("logger_name", "System.out");

        ConfigurationObject child = new DefaultConfigurationObject("object_factory_configuration");

        obj.addChild(child);

        obj.setPropertyValue("client_dao", "com.topcoder.clients.manager.dao.MockClientDAOAcc");
        obj.setPropertyValue("project_dao", "com.topcoder.clients.manager.dao.MockProjectDAOAccuracy");
        obj.setPropertyValue("projectStatus_dao", "com.topcoder.clients.manager.dao.MockProjectStatusDAOAcc");

        try {
            obj.removeProperty("client_dao");

            new DAOProjectManager(obj);
            fail("ManagerConfigurationException is expected.");
        } catch (ManagerConfigurationException e) {
            // ok
        }
    }

    /**
     * Test method for 'DAOProjectManager(ConfigurationObject)'.
     * <p>
     * client_dao properties is not invalid, ManagerConfigurationException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testDAOProjectManagerConfigurationObject_3() throws Exception {
        ConfigurationObject obj = new DefaultConfigurationObject("root");
        obj.setPropertyValue("id_generator_name", "test");
        obj.setPropertyValue("logger_name", "System.out");

        ConfigurationObject child = new DefaultConfigurationObject("object_factory_configuration");

        obj.addChild(child);

        obj.setPropertyValue("client_dao", "com.topcoder.clients.manager.dao.MockClientDAOAcc");
        obj.setPropertyValue("project_dao", "com.topcoder.clients.manager.dao.MockProjectDAOAccuracy");
        obj.setPropertyValue("projectStatus_dao", "com.topcoder.clients.manager.dao.MockProjectStatusDAOAcc");

        try {
            obj.setPropertyValue("client_dao", "com.topcoder.clients.manager.dao.MockClientDAOOk");

            new DAOProjectManager(obj);
            fail("ManagerConfigurationException is expected.");
        } catch (ManagerConfigurationException e) {
            // ok
        }
    }

    /**
     * Test method for 'DAOProjectManager(ConfigurationObject)'.
     * <p>
     * client_dao properties is not invalid, ManagerConfigurationException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testDAOProjectManagerConfigurationObject_4() throws Exception {
        ConfigurationObject obj = new DefaultConfigurationObject("root");
        obj.setPropertyValue("id_generator_name", "test");
        obj.setPropertyValue("logger_name", "System.out");

        ConfigurationObject child = new DefaultConfigurationObject("object_factory_configuration");

        obj.addChild(child);

        obj.setPropertyValue("client_dao", "com.topcoder.clients.manager.dao.MockClientDAOAcc");
        obj.setPropertyValue("project_dao", "com.topcoder.clients.manager.dao.MockProjectDAOAccuracy");
        obj.setPropertyValue("projectStatus_dao", "com.topcoder.clients.manager.dao.MockProjectStatusDAOAcc");

        try {
            obj.setPropertyValue("client_dao", "com.topcoder.clients.manager.dao.MockProjectStatusDAOAcc");

            new DAOProjectManager(obj);
            fail("ManagerConfigurationException is expected.");
        } catch (ManagerConfigurationException e) {
            // ok
        }
    }

    /**
     * Test method for 'DAOProjectManager(ConfigurationObject)'.
     * <p>
     * missing projectStatus_dao, ManagerConfigurationException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testDAOProjectManagerConfigurationObject_5() throws Exception {
        ConfigurationObject obj = new DefaultConfigurationObject("root");
        obj.setPropertyValue("id_generator_name", "test");
        obj.setPropertyValue("logger_name", "System.out");

        ConfigurationObject child = new DefaultConfigurationObject("object_factory_configuration");

        obj.addChild(child);

        obj.setPropertyValue("client_dao", "com.topcoder.clients.manager.dao.MockClientDAOAcc");
        obj.setPropertyValue("project_dao", "com.topcoder.clients.manager.dao.MockProjectDAOAccuracy");
        obj.setPropertyValue("projectStatus_dao", "com.topcoder.clients.manager.dao.MockProjectStatusDAOAcc");

        try {

            obj.removeProperty("projectStatus_dao");

            new DAOProjectManager(obj);
            fail("ManagerConfigurationException is expected.");
        } catch (ManagerConfigurationException e) {
            // ok
        }
    }

    /**
     * Test method for 'DAOProjectManager(ConfigurationObject)'.
     * <p>
     * missing project_dao, ManagerConfigurationException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testDAOProjectManagerConfigurationObject_6() throws Exception {
        ConfigurationObject obj = new DefaultConfigurationObject("root");
        obj.setPropertyValue("id_generator_name", "test");
        obj.setPropertyValue("logger_name", "System.out");

        ConfigurationObject child = new DefaultConfigurationObject("object_factory_configuration");

        obj.addChild(child);

        obj.setPropertyValue("client_dao", "com.topcoder.clients.manager.dao.MockClientDAOAcc");
        obj.setPropertyValue("project_dao", "com.topcoder.clients.manager.dao.MockProjectDAOAccuracy");
        obj.setPropertyValue("projectStatus_dao", "com.topcoder.clients.manager.dao.MockProjectStatusDAOAcc");

        try {

            obj.removeProperty("project_dao");

            new DAOProjectManager(obj);
            fail("ManagerConfigurationException is expected.");
        } catch (ManagerConfigurationException e) {
            // ok
        }
    }

    /**
     * Test method for 'createProject(Project, Client)'.
     * <p>
     * The project is not valid(null), IAE is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testCreateProject_1() throws Exception {
        try {
            Client c = new Client();
            manager.createProject(null, c);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Test method for 'createProject(Project, Client)'.
     * <p>
     * The project is not valid(salesTax < 0), IAE is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testCreateProject_2() throws Exception {
        try {
            Client c = new Client();

            Project p = new Project();
            p.setSalesTax(-1.0);
            p.setName("name");
            p.setDescription("d");
            p.setDeleted(false);

            manager.createProject(p, c);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Test method for 'createProject(Project, Client)'.
     * <p>
     * The project is not valid(name is null), IAE is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testCreateProject_3() throws Exception {
        try {
            Client c = new Client();

            Project p = new Project();
            p.setSalesTax(10.0);
            p.setName(null);
            p.setDescription("d");
            p.setDeleted(false);

            manager.createProject(p, c);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Test method for 'createProject(Project, Client)'.
     * <p>
     * The project is not valid(name is empty), IAE is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testCreateProject_4() throws Exception {
        try {
            Client c = new Client();

            Project p = new Project();
            p.setSalesTax(10.0);
            p.setName("  ");
            p.setDescription("d");
            p.setDeleted(false);

            manager.createProject(p, c);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Test method for 'createProject(Project, Client)'.
     * <p>
     * The project is not valid(description is null), IAE is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testCreateProject_6() throws Exception {
        try {
            Client c = new Client();

            Project p = new Project();
            p.setSalesTax(10.0);
            p.setName("n");
            p.setDescription(null);
            p.setDeleted(false);

            manager.createProject(p, c);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Test method for 'createProject(Project, Client)'.
     * <p>
     * The project is not valid(isDeleted is true), IAE is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testCreateProject_7() throws Exception {
        try {
            Client c = new Client();

            Project p = new Project();
            p.setSalesTax(10.0);
            p.setName("n");
            p.setDescription("d");
            p.setDeleted(true);

            manager.createProject(p, c);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Test method for 'createProject(Project, Client)'.
     * <p>
     * Client is null, IAE is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testCreateProject_8() throws Exception {
        try {
            Client c = null;

            Project p = new Project();
            p.setSalesTax(10.0);
            p.setName("n");
            p.setDescription("d");
            p.setDeleted(false);

            manager.createProject(p, c);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Test method for 'createProject(Project, Client)'.
     * <p>
     * Inner DAOException thrown, ProjectManagerException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testCreateProject_9() throws Exception {
        try {
            Client c = new Client();

            Project p = new Project();
            p.setSalesTax(10.0);
            p.setName("DAOException");
            p.setDescription("d");
            p.setDeleted(false);

            manager.createProject(p, c);
            fail("ProjectManagerException is expected.");
        } catch (ProjectManagerException e) {
            // ok
        }
    }

    /**
     * Test method for 'createProject(Project, Client)'.
     * <p>
     * Inner DAOConfigurationException thrown, ProjectManagerException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testCreateProject_10() throws Exception {
        try {
            Client c = new Client();

            Project p = new Project();
            p.setSalesTax(10.0);
            p.setName("DAOConfigurationException");
            p.setDescription("d");
            p.setDeleted(false);

            manager.createProject(p, c);
            fail("DAOConfigurationException is expected.");
        } catch (ProjectManagerException e) {
            // ok
        }
    }

    /**
     * Test method for 'createProject(Project, Client)'.
     * <p>
     * Inner ClassCastException  thrown, ProjectManagerException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testCreateProject_11() throws Exception {
        try {
            Client c = new Client();

            Project p = new Project();
            p.setSalesTax(10.0);
            p.setName("ClassCastException");
            p.setDescription("d");
            p.setDeleted(false);

            manager.createProject(p, c);
            fail("DAOConfigurationException is expected.");
        } catch (ProjectManagerException e) {
            // ok
        }
    }

    /**
     * Test method for 'updateProject(Project)'.
     *
     * <p>
     * The project is null, IAE is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testUpdateProject_F_1() throws Exception {
        try {
            manager.updateProject(null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Test method for 'updateProject(Project)'.
     *
     * <p>
     * The project is invalid (salesTax less than 0), IAE is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testUpdateProject_F_2() throws Exception {
        try {
            Project p = new Project();
            p.setSalesTax(-10.0);
            p.setDescription("d");
            p.setName("name");
            p.setDeleted(false);

            manager.updateProject(p);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Test method for 'updateProject(Project)'.
     *
     * <p>
     * The project is invalid (description is null), IAE is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testUpdateProject_F_3() throws Exception {
        try {
            Project p = new Project();
            p.setSalesTax(10.0);
            p.setDescription(null);
            p.setName("name");
            p.setDeleted(false);

            manager.updateProject(p);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Test method for 'updateProject(Project)'.
     *
     * <p>
     * The project is invalid (name is null), IAE is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testUpdateProject_F_4() throws Exception {
        try {
            Project p = new Project();
            p.setSalesTax(10.0);
            p.setDescription("d");
            p.setName(null);
            p.setDeleted(false);

            manager.updateProject(p);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Test method for 'updateProject(Project)'.
     *
     * <p>
     * The project is invalid (name is empty), IAE is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testUpdateProject_F_5() throws Exception {
        try {
            Project p = new Project();
            p.setSalesTax(10.0);
            p.setDescription("d");
            p.setName(" ");
            p.setDeleted(false);

            manager.updateProject(p);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Test method for 'updateProject(Project)'.
     *
     * <p>
     * The project is invalid (isDeleted is true), IAE is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testUpdateProject_F_6() throws Exception {
        try {
            Project p = new Project();
            p.setSalesTax(10.0);
            p.setDescription("d");
            p.setName("name");
            p.setDeleted(true);

            manager.updateProject(p);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Test method for 'updateProject(Project)'.
     *
     * <p>
     * The project can not be found, ProjectEntityNotFoundException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testUpdateProject_F_7() throws Exception {
        try {
            Project p = new Project();
            p.setSalesTax(10.0);
            p.setDescription("d");
            p.setName("name");
            p.setDeleted(false);
            p.setId(-10);

            manager.updateProject(p);
            fail("ProjectEntityNotFoundException is expected.");
        } catch (ProjectEntityNotFoundException e) {
            // ok
        }
    }

    /**
     * Test method for 'updateProject(Project)'.
     *
     * <p>
     * The project can not be found, ProjectEntityNotFoundException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testUpdateProject_F_8() throws Exception {
        try {
            Project p = new Project();
            p.setSalesTax(10.0);
            p.setDescription("d");
            p.setName("name");
            p.setDeleted(false);
            p.setId(1L);

            manager.updateProject(p);
            fail("ProjectEntityNotFoundException is expected.");
        } catch (ProjectEntityNotFoundException e) {
            // ok
        }
    }

    /**
     * Test method for 'updateProject(Project)'.
     *
     * <p>
     * The project can not be found, ProjectEntityNotFoundException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testUpdateProject_F_9() throws Exception {
        try {
            Project p = new Project();
            p.setSalesTax(10.0);
            p.setDescription("d");
            p.setName("name");
            p.setDeleted(false);
            p.setId(4L);

            manager.updateProject(p);
            fail("ProjectEntityNotFoundException is expected.");
        } catch (ProjectEntityNotFoundException e) {
            // ok
        }
    }

    /**
     * Test method for 'updateProject(Project)'.
     *
     * <p>
     * Inner DAOException is thrown, ProjectManagerException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testUpdateProject_F_10() throws Exception {
        try {
            Project p = new Project();
            p.setSalesTax(10.0);
            p.setDescription("d");
            p.setName("name");
            p.setDeleted(false);
            p.setId(2L);

            manager.updateProject(p);
            fail("ProjectManagerException is expected.");
        } catch (ProjectManagerException e) {
            // ok
        }
    }

    /**
     * Test method for 'updateProject(Project)'.
     *
     * <p>
     * Inner DAOConfigurationException is thrown, ProjectManagerException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testUpdateProject_F_11() throws Exception {
        try {
            Project p = new Project();
            p.setSalesTax(10.0);
            p.setDescription("d");
            p.setName("name");
            p.setDeleted(false);
            p.setId(3L);

            manager.updateProject(p);
            fail("ProjectManagerException is expected.");
        } catch (ProjectManagerException e) {
            // ok
        }
    }

    /**
     * Test method for 'updateProject(Project)'.
     *
     * <p>
     * Inner ClassCastException is thrown, ProjectManagerException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testUpdateProject_F_12() throws Exception {
        try {
            Project p = new Project();
            p.setSalesTax(10.0);
            p.setDescription("d");
            p.setName("ClassCastException");
            p.setDeleted(false);
            p.setId(3L);

            manager.updateProject(p);
            fail("ProjectManagerException is expected.");
        } catch (ProjectManagerException e) {
            // ok
        }
    }

    /**
     * Test method for 'deleteProject(Project)'.
     * <p>
     * The project is null, IAE is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testDeleteProject_1() throws Exception {
        try {
            manager.deleteProject(null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Test method for 'deleteProject(Project)'.
     * <p>
     * The project 's isDeleted is true, IAE is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testDeleteProject_2() throws Exception {
        try {
            Project project = new Project();
            project.setDeleted(true);
            manager.deleteProject(project);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Test method for 'deleteProject(Project)'.
     * <p>
     * The project does not exist, ProjectEntityNotFoundException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testDeleteProject_3() throws Exception {
        try {
            Project project = new Project();
            project.setDeleted(false);

            project.setName("entityNotFound");

            manager.deleteProject(project);
            fail("ProjectEntityNotFoundException is expected.");
        } catch (ProjectEntityNotFoundException e) {
            // ok
        }
    }

    /**
     * Test method for 'deleteProject(Project)'.
     * <p>
     * Inner DAOException is thrown, ProjectManagerException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testDeleteProject_4() throws Exception {
        try {
            Project project = new Project();
            project.setDeleted(false);

            project.setName("DAOException");

            manager.deleteProject(project);
            fail("ProjectManagerException is expected.");
        } catch (ProjectManagerException e) {
            // ok
        }
    }

    /**
     * Test method for 'deleteProject(Project)'.
     * <p>
     * Inner DAOConfigurationException is thrown, ProjectManagerException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testDeleteProject_5() throws Exception {
        try {
            Project project = new Project();
            project.setDeleted(false);

            project.setName("DAOConfigurationException");

            manager.deleteProject(project);
            fail("ProjectManagerException is expected.");
        } catch (ProjectManagerException e) {
            // ok
        }
    }

    /**
     * Test method for 'retrieveProject(long)'.
     *
     * <p>
     * the id is negative, IAE is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testRetrieveProjectLong_1() throws Exception {
        try {
            manager.retrieveProject(-1);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Test method for 'retrieveProject(long)'.
     *
     * <p>
     * Inner DAOException thrown, ProjectManagerException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testRetrieveProjectLong_2() throws Exception {
        try {
            manager.retrieveProject(101L);
            fail("ProjectManagerException is expected.");
        } catch (ProjectManagerException e) {
            // ok
        }
    }

    /**
     * Test method for 'retrieveProject(long)'.
     *
     * <p>
     * Inner DAOConfigurationException thrown, ProjectManagerException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testRetrieveProjectLong_3() throws Exception {
        try {
            manager.retrieveProject(102L);
            fail("ProjectManagerException is expected.");
        } catch (ProjectManagerException e) {
            // ok
        }
    }

    /**
     * Test method for 'retrieveProject(long, boolean)'.
     * <p>
     * Id is negative, IAE is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testRetrieveProjectLongBoolean_1() throws Exception {
        try {
            manager.retrieveProject(-1, false);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Test method for 'retrieveProject(long, boolean)'.
     * <p>
     * Inner DAOException will be thrown, ProjectManagerException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testRetrieveProjectLongBoolean_2() throws Exception {
        try {
            manager.retrieveProject(101L, false);
            fail("ProjectManagerException is expected.");
        } catch (ProjectManagerException e) {
            // ok
        }
    }

    /**
     * Test method for 'retrieveProject(long, boolean)'.
     * <p>
     * Inner DAOConfigurationException will be thrown, ProjectManagerException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testRetrieveProjectLongBoolean_3() throws Exception {
        try {
            manager.retrieveProject(101L, false);
            fail("ProjectManagerException is expected.");
        } catch (ProjectManagerException e) {
            // ok
        }
    }

    /**
     * Test method for 'retrieveProjectsForClient(Client)'.
     * <p>
     * The client is null, IAE is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testRetrieveProjectsForClientClient_1() throws Exception {
        try {
            manager.retrieveProjectsForClient(null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Test method for 'retrieveProjectsForClient(Client)'.
     * <p>
     * The client's id is negative, IAE is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testRetrieveProjectsForClientClient_2() throws Exception {
        try {
            Client client = new Client();
            client.setId(-1);
            manager.retrieveProjectsForClient(client);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Test method for 'retrieveProjectsForClient(Client)'.
     * <p>
     * Inner DAOException is thrown, ProjectManagerException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testRetrieveProjectsForClientClient_3() throws Exception {
        try {
            Client client = new Client();
            client.setId(1L);
            client.setName("DAOException");
            manager.retrieveProjectsForClient(client);
            fail("ProjectManagerException is expected.");
        } catch (ProjectManagerException e) {
            // ok
        }
    }

    /**
     * Test method for 'retrieveProjectsForClient(Client)'.
     * <p>
     * Inner DAOConfigurationException is thrown, ProjectManagerException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testRetrieveProjectsForClientClient_4() throws Exception {
        try {
            Client client = new Client();
            client.setId(1L);
            client.setName("DAOConfigurationException");
            manager.retrieveProjectsForClient(client);
            fail("ProjectManagerException is expected.");
        } catch (ProjectManagerException e) {
            // ok
        }
    }

    /**
     * Test method for 'retrieveProjectsForClient(Client, boolean)'.
     * <p>
     * Inner DAOConfigurationException is thrown, ProjectManagerException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testRetrieveProjectsForClientClientBoolean_1() throws Exception {
        try {
            Client client = new Client();
            client.setId(1L);
            client.setName("DAOConfigurationException");
            manager.retrieveProjectsForClient(client, false);
            fail("ProjectManagerException is expected.");
        } catch (ProjectManagerException e) {
            // ok
        }
    }

    /**
     * Test method for 'retrieveProjectsForClient(Client, boolean)'.
     * <p>
     * Inner DAOException is thrown, ProjectManagerException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testRetrieveProjectsForClientClientBoolean_2() throws Exception {
        try {
            Client client = new Client();
            client.setId(1L);
            client.setName("DAOException");
            manager.retrieveProjectsForClient(client, false);
            fail("ProjectManagerException is expected.");
        } catch (ProjectManagerException e) {
            // ok
        }
    }

    /**
     * Test method for 'retrieveAllProjects(boolean)'.
     *
     * <p>
     * Inner DAOException will be thrown, ProjectManagerException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testRetrieveAllProjectsBoolean_1() throws Exception {
        MockProjectDAO dao = new MockProjectDAO();
        dao.setFlag(1);
        injectField("projectDAO", dao);

        try {
            manager.retrieveAllProjects(false);
            fail("ProjectManagerException is expected.");
        } catch (ProjectManagerException e) {
            // ok
        }
    }

    /**
     * Test method for 'retrieveAllProjects(boolean)'.
     *
     * <p>
     * Inner DAOConfigurationException will be thrown, ProjectManagerException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testRetrieveAllProjectsBoolean_2() throws Exception {
        MockProjectDAO dao = new MockProjectDAO();
        dao.setFlag(2);
        injectField("projectDAO", dao);

        try {
            manager.retrieveAllProjects(false);
            fail("ProjectManagerException is expected.");
        } catch (ProjectManagerException e) {
            // ok
        }
    }

    /**
     * Test method for 'setProjectStatus(Project, ProjectStatus)'.
     *
     * <p>
     * The project is null, IAE is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testSetProjectStatus_1() throws Exception {
        try {
            ProjectStatus s = new ProjectStatus();
            s.setName("name");
            s.setDeleted(false);
            s.setDescription("des");

            manager.setProjectStatus(null, s);

            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Test method for 'setProjectStatus(Project, ProjectStatus)'.
     *
     * <p>
     * The project is invalid, IAE is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testSetProjectStatus_2() throws Exception {
        try {
            ProjectStatus s = new ProjectStatus();
            s.setName("name");
            s.setDeleted(false);
            s.setDescription("des");

            Project project = new Project();
            project.setSalesTax(-10.0);
            project.setDescription("des");
            project.setName("name");
            project.setDeleted(false);

            manager.setProjectStatus(project, s);

            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Test method for 'setProjectStatus(Project, ProjectStatus)'.
     *
     * <p>
     * The project is invalid (name is empty), IAE is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testSetProjectStatus_4() throws Exception {
        try {
            ProjectStatus s = new ProjectStatus();
            s.setName("name");
            s.setDeleted(false);
            s.setDescription("des");

            Project project = new Project();
            project.setSalesTax(10.0);
            project.setDescription("des");
            project.setName(" ");
            project.setDeleted(false);

            manager.setProjectStatus(project, s);

            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Test method for 'setProjectStatus(Project, ProjectStatus)'.
     *
     * <p>
     * The projectStatus is not valid, IAE is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testSetProjectStatus_5() throws Exception {
        try {
            ProjectStatus s = new ProjectStatus();
            s.setName("name");
            s.setDeleted(false);
            s.setDescription("des");

            Project project = new Project();
            project.setSalesTax(10.0);
            project.setDescription("des");
            project.setName("name");
            project.setDeleted(false);

            manager.setProjectStatus(project, null);

            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Test method for 'setProjectStatus(Project, ProjectStatus)'.
     *
     * <p>
     * The projectStatus is not valid, IAE is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testSetProjectStatus_6() throws Exception {
        try {
            ProjectStatus s = new ProjectStatus();
            s.setName(null);
            s.setDeleted(false);
            s.setDescription("des");

            Project project = new Project();
            project.setSalesTax(10.0);
            project.setDescription("des");
            project.setName("name");
            project.setDeleted(false);

            manager.setProjectStatus(project, s);

            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Test method for 'setProjectStatus(Project, ProjectStatus)'.
     *
     * <p>
     * The projectStatus is not valid, IAE is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testSetProjectStatus_7() throws Exception {
        try {
            ProjectStatus s = new ProjectStatus();
            s.setName(" ");
            s.setDeleted(false);
            s.setDescription("des");

            Project project = new Project();
            project.setSalesTax(10.0);
            project.setDescription("des");
            project.setName("name");
            project.setDeleted(false);

            manager.setProjectStatus(project, s);

            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Test method for 'setProjectStatus(Project, ProjectStatus)'.
     *
     * <p>
     * The projectStatus is not valid, IAE is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testSetProjectStatus_8() throws Exception {
        try {
            ProjectStatus s = new ProjectStatus();
            s.setName("name");
            s.setDeleted(false);
            s.setDescription("");

            Project project = new Project();
            project.setSalesTax(10.0);
            project.setDescription("des");
            project.setName("name");
            project.setDeleted(false);

            manager.setProjectStatus(project, s);

            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Test method for 'setProjectStatus(Project, ProjectStatus)'.
     *
     * <p>
     * The projectStatus is not valid, IAE is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testSetProjectStatus_9() throws Exception {
        try {
            ProjectStatus s = new ProjectStatus();
            s.setName("name");
            s.setDeleted(false);
            s.setDescription(null);

            Project project = new Project();
            project.setSalesTax(10.0);
            project.setDescription("des");
            project.setName("name");
            project.setDeleted(false);

            manager.setProjectStatus(project, s);

            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Test method for 'setProjectStatus(Project, ProjectStatus)'.
     *
     * <p>
     * The projectStatus is not valid, IAE is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testSetProjectStatus_10() throws Exception {
        try {
            ProjectStatus s = new ProjectStatus();
            s.setName("name");
            s.setDeleted(true);
            s.setDescription("des");

            Project project = new Project();
            project.setSalesTax(10.0);
            project.setDescription("des");
            project.setName("name");
            project.setDeleted(false);

            manager.setProjectStatus(project, s);

            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Test method for 'searchProjectsByName(String)'.
     *
     * <p>
     * the name is null, IAE is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testSearchProjectsByName_1() throws Exception {
        try {
            manager.searchProjectsByName(null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Test method for 'searchProjectsByName(String)'.
     *
     * <p>
     * the name is empty, IAE is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testSearchProjectsByName_1_2() throws Exception {
        try {
            manager.searchProjectsByName("  ");
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Test method for 'searchProjectsByName(String)'.
     *
     * <p>
     * Inner DAOException is thrown, ProjectManagerException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testSearchProjectsByName_2() throws Exception {
        try {
            manager.searchProjectsByName("DAOException");
            fail("ProjectManagerException is expected.");
        } catch (ProjectManagerException e) {
            // ok
        }
    }

    /**
     * Test method for 'searchProjectsByName(String)'.
     *
     * <p>
     * Inner DAOConfigurationException is thrown, ProjectManagerException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testSearchProjectsByName_3() throws Exception {
        try {
            manager.searchProjectsByName("DAOConfigurationException");
            fail("ProjectManagerException is expected.");
        } catch (ProjectManagerException e) {
            // ok
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOProjectManager.searchProjects(Filter)'.
     * <p>
     * The filter is null, IAE is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testSearchProjects_1() throws Exception {
        try {
            manager.searchProjects(null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOProjectManager.searchProjects(Filter)'.
     * <p>
     * Inner DAOException is thrown, ProjectManagerException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testSearchProjects_2() throws Exception {
        try {
            EqualToFilter f = new EqualToFilter("DAOException", new Boolean(false));

            manager.searchProjects(f);
            fail("ProjectManagerException is expected.");
        } catch (ProjectManagerException e) {
            // ok
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOProjectManager.searchProjects(Filter)'.
     * <p>
     * Inner DAOConfigurationException is thrown, ProjectManagerException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testSearchProjects_3() throws Exception {
        try {
            EqualToFilter f = new EqualToFilter("DAOConfigurationException", new Boolean(false));

            manager.searchProjects(f);
            fail("ProjectManagerException is expected.");
        } catch (ProjectManagerException e) {
            // ok
        }
    }

    /**
     * Test method for 'getProjectStatus(long)'.
     * <p>
     * The param is negative, IAE is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testGetProjectStatus_1() throws Exception {
        try {
            manager.getProjectStatus(-1);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Test method for 'getProjectStatus(long)'.
     * <p>
     * Inner exception is thrown, ProjectManagerException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testGetProjectStatus_2() throws Exception {
        try {
            manager.getProjectStatus(1001L);
            fail("ProjectManagerException is expected.");
        } catch (ProjectManagerException e) {
            // ok
        }
    }

    /**
     * Test method for 'getProjectStatus(long)'.
     * <p>
     * Inner exception is thrown, ProjectManagerException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testGetProjectStatus_3() throws Exception {
        try {
            manager.getProjectStatus(1002L);
            fail("ProjectManagerException is expected.");
        } catch (ProjectManagerException e) {
            // ok
        }
    }

    /**
     * Test method for 'getProjectStatus(long)'.
     * <p>
     * Inner exception is thrown, ProjectManagerException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testGetProjectStatus_4() throws Exception {
        try {
            manager.getProjectStatus(4L);
            fail("ProjectManagerException is expected.");
        } catch (ProjectManagerException e) {
            // ok
        }
    }

    /**
     * Test method for 'getAllProjectStatuses()'.
     *
     * <p>
     * Inner DAOException is thrown, ProjectManagerException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testGetAllProjectStatuses_1() throws Exception {
        MockProjectStatusDAO dao = new MockProjectStatusDAO();
        dao.setFlag(1);

        injectField("projectStatusDAO", dao);

        try {
            manager.getAllProjectStatuses();
            fail("ProjectManagerException is expected.");
        } catch (ProjectManagerException e) {
            // ok
        }
    }

    /**
     * Test method for 'getAllProjectStatuses()'.
     *
     * <p>
     * Inner DAOConfigurationException is thrown, ProjectManagerException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testGetAllProjectStatuses_2() throws Exception {
        MockProjectStatusDAO dao = new MockProjectStatusDAO();
        dao.setFlag(2);

        injectField("projectStatusDAO", dao);

        try {
            manager.getAllProjectStatuses();
            fail("ProjectManagerException is expected.");
        } catch (ProjectManagerException e) {
            // ok
        }
    }

    /**
     * Test method for 'getProjectsForStatus(ProjectStatus)'.
     * <p>
     * The ProjectStatus is null, IAE is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testGetProjectsForStatus_1() throws Exception {
        try {
            manager.getProjectsForStatus(null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Test method for 'getProjectsForStatus(ProjectStatus)'.
     * <p>
     * The ProjectStatus 'id is negative, IAE is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testGetProjectsForStatus_2() throws Exception {
        try {
            ProjectStatus s = new ProjectStatus();
            s.setId(-1);

            manager.getProjectsForStatus(s);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Test method for 'getProjectsForStatus(ProjectStatus)'.
     * <p>
     * Inner DAOException is thrown , ProjectManagerException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testGetProjectsForStatus_3() throws Exception {
        try {
            ProjectStatus s = new ProjectStatus();
            s.setId(1);
            s.setName("DAOException");

            manager.getProjectsForStatus(s);
            fail("ProjectManagerException is expected.");
        } catch (ProjectManagerException e) {
            // ok
        }
    }

    /**
     * Test method for 'getProjectsForStatus(ProjectStatus)'.
     * <p>
     * Inner DAOConfigurationException is thrown , ProjectManagerException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testGetProjectsForStatus_4() throws Exception {
        try {
            ProjectStatus s = new ProjectStatus();
            s.setId(1);
            s.setName("DAOConfigurationException");

            manager.getProjectsForStatus(s);
            fail("ProjectManagerException is expected.");
        } catch (ProjectManagerException e) {
            // ok
        }
    }

    /**
     * Test method for 'createProjectStatus(ProjectStatus)'.
     *
     * <p>
     * The status is null, IAE is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testCreateProjectStatus_F_1() throws Exception {
        try {
            manager.createProjectStatus(null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Test method for 'createProjectStatus(ProjectStatus)'.
     *
     * <p>
     * The status is not valid(name is null), IAE is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testCreateProjectStatus_F_2() throws Exception {
        try {
            ProjectStatus s = new ProjectStatus();
            s.setDescription("a");

            manager.createProjectStatus(s);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Test method for 'createProjectStatus(ProjectStatus)'.
     *
     * <p>
     * The status is not valid(description is null), IAE is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testCreateProjectStatus_F_4() throws Exception {
        try {
            ProjectStatus s = new ProjectStatus();
            s.setDescription(null);
            s.setName("name");

            manager.createProjectStatus(s);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Test method for 'createProjectStatus(ProjectStatus)'.
     *
     * <p>
     * The status is not valid(name is empty), IAE is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testCreateProjectStatus_F_3() throws Exception {
        try {
            ProjectStatus s = new ProjectStatus();
            s.setDescription("a");
            s.setName("  ");

            manager.createProjectStatus(s);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Test method for 'createProjectStatus(ProjectStatus)'.
     *
     * <p>
     * The status is not valid(description is empty), IAE is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testCreateProjectStatus_F_5() throws Exception {
        try {
            ProjectStatus s = new ProjectStatus();
            s.setDescription("  ");
            s.setName("name");

            manager.createProjectStatus(s);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Test method for 'createProjectStatus(ProjectStatus)'.
     *
     * <p>
     * The status is not valid(isDeleted is true), IAE is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testCreateProjectStatus_F_6() throws Exception {
        try {
            ProjectStatus s = new ProjectStatus();
            s.setDescription("d");
            s.setName("name");
            s.setDeleted(true);

            manager.createProjectStatus(s);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Test method for 'createProjectStatus(ProjectStatus)'.
     *
     * <p>
     * Inner DAOException is thrown, ProjectManagerException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testCreateProjectStatus_F_7() throws Exception {
        try {
            ProjectStatus s = new ProjectStatus();
            s.setDescription("d");
            s.setName("DAOException");
            s.setDeleted(false);

            manager.createProjectStatus(s);
            fail("ProjectManagerException is expected.");
        } catch (ProjectManagerException e) {
            // ok
        }
    }

    /**
     * Test method for 'createProjectStatus(ProjectStatus)'.
     *
     * <p>
     * Inner DAOConfigurationException is thrown, ProjectManagerException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testCreateProjectStatus_F_8() throws Exception {
        try {
            ProjectStatus s = new ProjectStatus();
            s.setDescription("d");
            s.setName("DAOConfigurationException");
            s.setDeleted(false);

            manager.createProjectStatus(s);
            fail("ProjectManagerException is expected.");
        } catch (ProjectManagerException e) {
            // ok
        }
    }

    /**
     * Test method for 'createProjectStatus(ProjectStatus)'.
     *
     * <p>
     * Inner DAOConfigurationException is thrown, ProjectManagerException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testCreateProjectStatus_F_9() throws Exception {
        try {
            ProjectStatus s = new ProjectStatus();
            s.setDescription("d");
            s.setName("ClassCastException");
            s.setDeleted(false);

            manager.createProjectStatus(s);
            fail("ProjectManagerException is expected.");
        } catch (ProjectManagerException e) {
            // ok
        }
    }

    /**
     * Test method for 'updateProjectStatus(ProjectStatus)'.
     * <p>
     * The ProjectStatus is null, IAE is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testUpdateProjectStatus_F_1() throws Exception {
        try {
            manager.updateProjectStatus(null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Test method for 'updateProjectStatus(ProjectStatus)'.
     * <p>
     * The ProjectStatus is not invalid, IAE is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testUpdateProjectStatus_F_2() throws Exception {
        try {
            ProjectStatus s = new ProjectStatus();
            s.setDeleted(true);
            s.setDescription("d");
            s.setName("d");

            manager.updateProjectStatus(s);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Test method for 'updateProjectStatus(ProjectStatus)'.
     * <p>
     * The ProjectStatus can not be found, ProjectEntityNotFoundException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testUpdateProjectStatus_F_3() throws Exception {
        try {
            ProjectStatus s = new ProjectStatus();
            s.setDeleted(false);
            s.setDescription("d");
            s.setName("d");
            s.setId(1L);

            manager.updateProjectStatus(s);
            fail("ProjectEntityNotFoundException is expected.");
        } catch (ProjectEntityNotFoundException e) {
            // ok
        }
    }

    /**
     * Test method for 'updateProjectStatus(ProjectStatus)'.
     * <p>
     * The ProjectStatus can not be found, ProjectEntityNotFoundException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testUpdateProjectStatus_F_4() throws Exception {
        try {
            ProjectStatus s = new ProjectStatus();
            s.setDeleted(false);
            s.setDescription("d");
            s.setName("d");
            s.setId(-1L);

            manager.updateProjectStatus(s);
            fail("ProjectEntityNotFoundException is expected.");
        } catch (ProjectEntityNotFoundException e) {
            // ok
        }
    }

    /**
     * Test method for 'updateProjectStatus(ProjectStatus)'.
     * <p>
     * The ProjectStatus can not be found, ProjectEntityNotFoundException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testUpdateProjectStatus_F_5() throws Exception {
        try {
            ProjectStatus s = new ProjectStatus();
            s.setDeleted(false);
            s.setDescription("d");
            s.setName("d");
            s.setId(2L);

            manager.updateProjectStatus(s);
            fail("ProjectEntityNotFoundException is expected.");
        } catch (ProjectEntityNotFoundException e) {
            // ok
        }
    }

    /**
     * Test method for 'updateProjectStatus(ProjectStatus)'.
     * <p>
     * ProjectManagerException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testUpdateProjectStatus_F_6() throws Exception {
        try {
            ProjectStatus s = new ProjectStatus();
            s.setDeleted(false);
            s.setDescription("d");
            s.setName("d");
            s.setId(1001L);

            manager.updateProjectStatus(s);
            fail("ProjectManagerException is expected.");
        } catch (ProjectManagerException e) {
            // ok
        }
    }

    /**
     * Test method for 'updateProjectStatus(ProjectStatus)'.
     * <p>
     * ProjectManagerException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testUpdateProjectStatus_F_7() throws Exception {
        try {
            ProjectStatus s = new ProjectStatus();
            s.setDeleted(false);
            s.setDescription("d");
            s.setName("d");
            s.setId(1002L);

            manager.updateProjectStatus(s);
            fail("ProjectManagerException is expected.");
        } catch (ProjectManagerException e) {
            // ok
        }
    }

    /**
     * Test method for 'updateProjectStatus(ProjectStatus)'.
     * <p>
     * ProjectManagerException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testUpdateProjectStatus_F_8() throws Exception {
        try {
            ProjectStatus s = new ProjectStatus();
            s.setDeleted(false);
            s.setDescription("d");
            s.setName("ClassCastException");
            s.setId(3L);

            manager.updateProjectStatus(s);
            fail("ProjectManagerException is expected.");
        } catch (ProjectManagerException e) {
            // ok
        }
    }

    /**
     * Test method for 'deleteProjectStatus(ProjectStatus)'.
     * <p>
     * the projectstatus is null, IAE is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testDeleteProjectStatus_F_1() throws Exception {
        try {
            manager.deleteProjectStatus(null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Test method for 'deleteProjectStatus(ProjectStatus)'.
     * <p>
     * the projectstatus 's isDeleted is true, IAE is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testDeleteProjectStatus_F_2() throws Exception {
        try {
            ProjectStatus s = new ProjectStatus();
            s.setDeleted(true);

            manager.deleteProjectStatus(s);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Test method for 'deleteProjectStatus(ProjectStatus)'.
     * <p>
     * the projectstatus is not found, ProjectEntityNotFoundException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testDeleteProjectStatus_F_3() throws Exception {
        try {
            ProjectStatus s = new ProjectStatus();
            s.setDeleted(false);
            s.setName("entityNotFound");

            manager.deleteProjectStatus(s);
            fail("ProjectEntityNotFoundException is expected.");
        } catch (ProjectEntityNotFoundException e) {
            // ok
        }
    }

    /**
     * Test method for 'deleteProjectStatus(ProjectStatus)'.
     * <p>
     * Inner DAOException is thrown, ProjectManagerException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testDeleteProjectStatus_F_4() throws Exception {
        try {
            ProjectStatus s = new ProjectStatus();
            s.setDeleted(false);
            s.setName("DAOException");

            manager.deleteProjectStatus(s);
            fail("ProjectManagerException is expected.");
        } catch (ProjectManagerException e) {
            // ok
        }
    }

    /**
     * Test method for 'deleteProjectStatus(ProjectStatus)'.
     * <p>
     * Inner DAOConfigurationException is thrown, ProjectManagerException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testDeleteProjectStatus_F_5() throws Exception {
        try {
            ProjectStatus s = new ProjectStatus();
            s.setDeleted(false);
            s.setName("DAOConfigurationException");

            manager.deleteProjectStatus(s);
            fail("ProjectManagerException is expected.");
        } catch (ProjectManagerException e) {
            // ok
        }
    }

    /**
     * Inject the field with value.
     *
     * @param field
     *            the field to inject
     * @param value
     *            the value to inject
     * @throws Exception
     *             to junit
     */
    private void injectField(String field, Object value) throws Exception {
        Field f = manager.getClass().getDeclaredField(field);
        f.setAccessible(true);

        f.set(manager, value);
    }

}
