/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.manager;

import java.util.Date;
import java.util.List;

import com.topcoder.clients.manager.dao.DAOClientManager;
import com.topcoder.clients.manager.dao.DAOCompanyManager;
import com.topcoder.clients.manager.dao.DAOProjectManager;
import com.topcoder.clients.manager.dao.Util;
import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.ClientStatus;
import com.topcoder.clients.model.Company;
import com.topcoder.clients.model.Project;
import com.topcoder.clients.model.ProjectStatus;
import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.defaults.DefaultConfigurationObject;
import com.topcoder.search.builder.filter.EqualToFilter;

import junit.framework.TestCase;

/**
 * Demo the usage of this component.
 *
 *
 * @author Chenhong
 * @version 1.0
 *
 */
public class Demo extends TestCase {

    /**
     * Set up the environment.
     *
     * @throws Exception
     *             to junit
     */
    public void setUp() throws Exception {
        Util.clearConfigManager();
        Util.loadConfiguration();

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
     * Show the usage of DAOClientManager class.
     *
     * @throws Exception
     *             to junit
     */
    public void testDemoDAOClientManager() throws Exception {
        // the configuration for DAOClientManager
        ConfigurationObject obj = new DefaultConfigurationObject("root");
        obj.setPropertyValue("id_generator_name", "client_id");
        obj.setPropertyValue("logger_name", "System.out");

        ConfigurationObject child = new DefaultConfigurationObject("object_factory_configuration");

        obj.addChild(child);
        obj.setPropertyValue("client_dao", "com.topcoder.clients.manager.MockClientDAO");
        obj.setPropertyValue("client_status_dao", "com.topcoder.clients.manager.MockClientStatusDAO");

        // create an instance of DAOClientManager by default
        DAOClientManager manager = new DAOClientManager();

        // create an instance of DAOClientManager with ConfigurationObject
        manager = new DAOClientManager(obj);

        // create an instance of DAOClientManager with configuration file
        String configFile = "test_files/daoclient.properties";
        manager = new DAOClientManager(configFile, "daoClient");

        // retrieve a client with id
        Client client = manager.retrieveClient(1);

        // retrieve all clients
        List<Client> clients = manager.retrieveAllClients();

        // search client for specified name
        clients = manager.searchClientsByName("clent-1");

        // search clients with Company-1, create a filter for this
        EqualToFilter filter = new EqualToFilter("Company-1", new Boolean(false));

        clients = manager.searchClients(filter);

        // set new code name to Client
        manager.setClientCodeName(client, "new code name");

        // get client status with id
        ClientStatus status = manager.getClientStatus(3);

        // get clients with status
        clients = manager.getClientsForStatus(status);

        // set new status to client
        manager.setClientStatus(client, status);

        // update the client
        client.setSalesTax(100.121);
        manager.updateClient(client);

        // deletes the client
        manager.deleteClient(client);

        // create new client to persist.
        Client newClient = new Client();
        newClient.setStartDate(new Date(System.currentTimeMillis() - 1000000000L));
        newClient.setEndDate(new Date());
        newClient.setSalesTax(10.0);
        newClient.setName("newClient");

        // create a new client
        manager.createClient(newClient);

        // updates the status
        status.setName("pending");

        // deletes the status
        manager.deleteClientStatus(status);

        ClientStatus newStatus = new ClientStatus();
        newStatus.setName("newStatus");
        newStatus.setDescription("des");
        newStatus.setDeleted(false);

        // create a new Client status
        manager.createClientStatus(newStatus);
    }

    /**
     * Demo the usage of DAOProjectManager class.
     *
     * @throws Exception
     *             to junit
     */
    public void testDemoDAOProjectManager() throws Exception {
        ConfigurationObject obj = new DefaultConfigurationObject("root");
        obj.setPropertyValue("id_generator_name", "test");
        obj.setPropertyValue("logger_name", "System.out");

        ConfigurationObject child = new DefaultConfigurationObject("object_factory_configuration");

        obj.addChild(child);
        obj.setPropertyValue("client_dao", "com.topcoder.clients.manager.dao.MockClientDAOAcc");
        obj.setPropertyValue("project_dao", "com.topcoder.clients.manager.dao.MockProjectDAOAccuracy");
        obj.setPropertyValue("project_status_dao", "com.topcoder.clients.manager.dao.MockProjectStatusDAOAcc");

        // create an instance of DAOProjectManager by default
        DAOProjectManager manager = new DAOProjectManager();
        // create an instance of DAOProjectManager with ConfigurationObject
        manager = new DAOProjectManager(obj);

        String configFile = "test_files/config.properties";
        // create an instance of DAOProjectManager with configuration file
        manager = new DAOProjectManager(configFile, "ProjectManager");

        // retrieve project , all children are retrieved too
        Project project = manager.retrieveProject(1);

        // retrieve project children is not retrieved
        project = manager.retrieveProject(10, false);

        Client client1 = new Client();
        client1.setStartDate(new Date(System.currentTimeMillis() - 1000000000L));
        client1.setEndDate(new Date());
        client1.setSalesTax(10.0);
        client1.setName("newClient");

        // retrieve projects for client all children are retrieved
        List<Project> projects = manager.retrieveProjectsForClient(client1);

        // retrieve projects for client1 ,children are not retrieved
        projects = manager.retrieveProjectsForClient(client1, false);

        // retrieve all projects, children are retrieved too
        projects = manager.retrieveAllProjects();

        // retrieve all projects, children are not retrieved
        projects = manager.retrieveAllProjects(false);

        // search projects with name="project-2"
        projects = manager.searchProjectsByName("project-2");

        EqualToFilter filter = new EqualToFilter("Company-1", new Boolean(false));

        // search projects with filter.
        projects = manager.searchProjects(filter);

        // retrieve project status wit id
        ProjectStatus status = manager.getProjectStatus(2);
        // get all project status
        List<ProjectStatus> statuses = manager.getAllProjectStatuses();

        // get projects with status
        projects = manager.getProjectsForStatus(status);

        // set the projectStatus
        project.setProjectStatus(status);

        // update Project 's name
        project.setName("nasa");
        manager.updateProject(project);

        // delete project
        manager.deleteProject(project);

        Project newProject = new Project();
        newProject.setSalesTax(10.0);
        newProject.setDescription("des");
        newProject.setName("name");

        // create a new project
        manager.createProject(newProject, new Client());

        // update ProjectStatus name
        status.setName("new status");
        manager.updateProjectStatus(status);

        // delete ProjectStatus
        manager.deleteProjectStatus(status);

        ProjectStatus newStatus = new ProjectStatus();
        newStatus.setName("name");
        newStatus.setDescription("des");
        newStatus.setDeleted(false);

        // create a new ProjectStatus
        manager.createProjectStatus(newStatus);
    }

    /**
     * Demo the usage of DAOCompanyManager.
     *
     * @throws Exception
     *             to junit
     */
    public void testDemoDAOCompanyManager() throws Exception {
        ConfigurationObject obj = new DefaultConfigurationObject("root");
        obj.setPropertyValue("id_generator_name", "test");
        obj.setPropertyValue("logger_name", "System.out");

        ConfigurationObject child = new DefaultConfigurationObject("object_factory_configuration");

        obj.addChild(child);
        obj.setPropertyValue("company_dao", "com.topcoder.clients.manager.MockCompanyDAO");

        // create an instance of DAOCompanyManager by default
        DAOCompanyManager manager = new DAOCompanyManager();
        // create an instance of DAOCompanyManager with ConfigurationObject
        manager = new DAOCompanyManager(obj);
        // create an instance of DAOCompanyManager with configuration file
        manager = new DAOCompanyManager("test_files/daocompany.properties", "daoCompany");

        // get Company with id
        Company company = manager.retrieveCompany(99);

        // get all companies
        List<Company> companies = manager.retrieveAllCompanies();

        // search companies with name="company-1"
        companies = manager.searchCompaniesByName("company-1");

        EqualToFilter filter = new EqualToFilter("Company-2", new Boolean(false));

        // search companies with filter
        companies = manager.searchCompanies(filter);

        // get clients for company
        List<Client> clients = manager.getClientsForCompany(company);

        // get projects for company
        List<Project> projects = manager.getProjectsForCompany(company);

        // update company's name
        company.setName("dsa");
        manager.updateCompany(company);

        // deletes Company
        manager.deleteCompany(company);

        Company newCompany = new Company();
        newCompany.setName("name");
        newCompany.setDeleted(false);
        newCompany.setPasscode("code");
        // create a new Company
        manager.createCompany(newCompany);

    }

}
