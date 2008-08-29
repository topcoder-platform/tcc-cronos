/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.manager.dao;

import java.util.List;

import com.topcoder.clients.dao.ClientDAO;
import com.topcoder.clients.dao.DAOConfigurationException;
import com.topcoder.clients.dao.DAOException;
import com.topcoder.clients.dao.EntityNotFoundException;
import com.topcoder.clients.dao.ProjectDAO;
import com.topcoder.clients.dao.ProjectStatusDAO;
import com.topcoder.clients.dao.ejb3.ClientDAOBean;
import com.topcoder.clients.dao.ejb3.ProjectDAOBean;
import com.topcoder.clients.dao.ejb3.ProjectStatusDAOBean;
import com.topcoder.clients.manager.ManagerConfigurationException;
import com.topcoder.clients.manager.ProjectEntityNotFoundException;
import com.topcoder.clients.manager.ProjectManager;
import com.topcoder.clients.manager.ProjectManagerException;
import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.Project;
import com.topcoder.clients.model.ProjectStatus;
import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.util.idgenerator.IDGenerationException;
import com.topcoder.util.objectfactory.ObjectFactory;

/**
 * This class is an implementation of ProjectManager interface.
 *
 * <p>
 * It provides convenient methods to manage Project and ProjectStatus entities. All operations will eventually delegate
 * to the underlying ProjectDAO, ClientDAO and ProjectStatusDAO appropriately.
 * </p>
 *
 * <p>
 * It uses IDGenerator to generate new IDs for entities, and has a logger used to log method entry/exit and all
 * exceptions.
 * </p>
 *
 * <p>
 * It can be configured via ConfigurationObject directly, as well as configuration file compatible with the format
 * defined in Configuration Persistence component.
 * </p>
 *
 * <p>
 * For checking whether the entity already exists in persistence, we call retrieveClient(id), if non-null entity is
 * returned, then the entity exists. This will affect the performance a bit, but we can not get the correct result by
 * just checking the ID.
 * </p>
 * <p>
 * <strong>Usage Sample:</strong>
 * <pre>
 *  ConfigurationObject obj = new DefaultConfigurationObject(&quot;root&quot;);
 *  obj.setPropertyValue(&quot;id_generator_name&quot;, &quot;test&quot;);
 *  obj.setPropertyValue(&quot;logger_name&quot;, &quot;System.out&quot;);
 *
 *  ConfigurationObject child = new DefaultConfigurationObject(&quot;object_factory_configuration&quot;);
 *
 *  obj.addChild(child);
 *  obj.setPropertyValue(&quot;client_dao&quot;, &quot;com.topcoder.clients.manager.dao.MockClientDAOAcc&quot;);
 *  obj.setPropertyValue(&quot;project_dao&quot;, &quot;com.topcoder.clients.manager.dao.MockProjectDAOAccuracy&quot;);
 *  obj.setPropertyValue(&quot;project_status_dao&quot;,
 *      &quot;com.topcoder.clients.manager.dao.MockProjectStatusDAOAcc&quot;);
 *
 *  // create an instance of DAOProjectManager by default
 *  DAOProjectManager manager = new DAOProjectManager();
 *  // create an instance of DAOProjectManager with ConfigurationObject
 *  manager = new DAOProjectManager(obj);
 *
 *  String configFile = &quot;test_files/config.properties&quot;;
 *  // create an instance of DAOProjectManager with configuration file
 * manager = new DAOProjectManager(configFile, &quot;ProjectManager&quot;);
 *
 *  // retrieve project , all children are retrieved too
 *  Project project = manager.retrieveProject(1);
 *
 *  // retrieve project children is not retrieved
 *  project = manager.retrieveProject(10, false);
 *
 *  Client client1 = new Client();
 *  client1.setStartDate(new Date(System.currentTimeMillis() - 1000000000L));
 *  client1.setEndDate(new Date());
 *  client1.setSalesTax(10.0);
 *  client1.setName(&quot;newClient&quot;);
 *
 *  // retrieve projects for client all children are retrieved
 *  List&lt;Project&gt; projects = manager.retrieveProjectsForClient(client1);
 *
 *  // retrieve projects for client1 ,children are not retrieved
 *  projects = manager.retrieveProjectsForClient(client1, false);
 *
 *  // retrieve all projects, children are retrieved too
 *  projects = manager.retrieveAllProjects();
 *
 *  // retrieve all projects, children are not retrieved
 *  projects = manager.retrieveAllProjects(false);
 *
 *  // search projects with name&quot;&quot;project-2&quot;
 *  projects = manager.searchProjectsByName(&quot;project-2&quot;);
 *
 *  EqualToFilter filter = new EqualToFilter(&quot;Company-1&quot;, new Boolean(false));
 *
 *  // search projects with filter.
 *  projects = manager.searchProjects(filter);
 *
 *  // retrieve project status wit id
 *  ProjectStatus status = manager.getProjectStatus(2);
 *  // get all project status
 *  List&lt;ProjectStatus&gt; statuses = manager.getAllProjectStatuses();
 *
 *  // get projects with status
 *  projects = manager.getProjectsForStatus(status);
 *
 *  // set the projectStatus
 *  project.setProjectStatus(status);
 *
 *  // update Project 's name
 *  project.setName(&quot;nasa&quot;);
 *  manager.updateProject(project);
 *
 *  // delete project
 *  manager.deleteProject(project);
 *
 *  Project newProject = new Project();
 *  newProject.setSalesTax(10.0);
 *  newProject.setDescription(&quot;des&quot;);
 *  newProject.setName(&quot;name&quot;);
 *
 *  // create a new project
 *  manager.createProject(newProject, new Client());
 *
 *  // update ProjectStatus name
 *  status.setName(&quot;new status&quot;);
 *  manager.updateProjectStatus(status);
 *
 *  // delete ProjectStatus
 *  manager.deleteProjectStatus(status);
 *
 *  ProjectStatus newStatus = new ProjectStatus();
 *  newStatus.setName(&quot;name&quot;);
 *  newStatus.setDescription(&quot;des&quot;);
 *  newStatus.setDeleted(false);
 *
 *  // create a new ProjectStatus
 *  manager.createProjectStatus(newStatus);
 * </pre>
 *
 * </p>
 *
 * <p>
 * Thread Safety: This class is thread-safe. All fields do not change after construction. Inner ProjectDAO,
 * ProjectStatusDAO, clientDAO, IDGenerator and Log are effectively thread-safe too.
 * </p>
 *
 * @author moonli, Chenhong
 * @version 1.0
 */
public class DAOProjectManager extends AbstractDAOManager implements ProjectManager {
    /**
     * Represents the name of this class. It will be used when log the method entrance or method exist only.
     */
    private static final String CLASSNAME = DAOProjectManager.class.getName();

    /**
     * Represents the object_factory_configuration property key when retrieving child ConfigurationObject to create
     * ObjectFactory instance.
     */
    private static final String OBJECT_FACTORY_CONFIGURATION = "object_factory_configuration";

    /**
     * Represents the client_dao property key.
     */
    private static final String CLIENT_DAO = "client_dao";

    /**
     * Represents the project_dao property key.
     */
    private static final String PROJECT_DAO = "project_dao";

    /**
     * Represents the projectStatus_dao property key.
     */
    private static final String PROJECT_STATUS_DAO = "project_status_dao";

    /**
     * Represents the a instance of ProjectDAO, it provides the persistence APIs for managing Project entities.
     *
     * It's set in the constructor, and do not change afterwards, can't be null. It's used to
     * create/update/delete/search Project entities.
     */
    private final ProjectDAO projectDAO;

    /**
     * Represents the a instance of ProjectStatusDAO, it provides the persistence APIs for managing ProjectStatus
     * entities.
     *
     * It's set in the constructor, and do not change afterwards, can't be null. It's used to
     * create/update/delete/search ProjectStatus entities.
     */
    private final ProjectStatusDAO projectStatusDAO;

    /**
     * Represents the a instance of ClientDAO, it provides the persistence APIs for managing Client entities.
     *
     * It's set in the constructor, and do not change afterwards, can't be null. It's used by retrieveProjectsForClient
     * method.
     */
    private final ClientDAO clientDAO;

    /**
     * Constructs an instance of this class by default.
     *
     * @throws ManagerConfigurationException
     *             if error occurred when creating IDGenerator instance.
     */
    public DAOProjectManager() throws ManagerConfigurationException {
        clientDAO = new ClientDAOBean();
        projectDAO = new ProjectDAOBean();
        projectStatusDAO = new ProjectStatusDAOBean();
    }

    /**
     * Constructs an instance of this class with given configuration object.
     *
     * @param configuration
     *            the ConfigurationObject used to configure this class, can't be null
     * @throws IllegalArgumentException
     *             if argument is null
     * @throws ManagerConfigurationException
     *             if error occurred when configuring this class, e.g. required configuration value is missing,
     *             or error occurred in object factory
     */
    public DAOProjectManager(ConfigurationObject configuration) throws ManagerConfigurationException {
        super(configuration);

        ConfigurationObject objectFactoryConfig = Helper.getChildConfigurationObject(configuration,
                OBJECT_FACTORY_CONFIGURATION);

        ObjectFactory factory = Helper.createObjectFactory(objectFactoryConfig);

        String clientDAOValue = Helper.getConfigurationParameter(configuration, CLIENT_DAO, true);

        String projectDAOValue = Helper.getConfigurationParameter(configuration, PROJECT_DAO, true);

        String projectStatusDAOValue = Helper.getConfigurationParameter(configuration, PROJECT_STATUS_DAO, true);

        // create clientDAO
        clientDAO = (ClientDAO) Helper.createObject(factory, clientDAOValue, ClientDAO.class);
        // create projectDAO
        projectDAO = (ProjectDAO) Helper.createObject(factory, projectDAOValue, ProjectDAO.class);
        // create projectStatusDAO
        projectStatusDAO = (ProjectStatusDAO) Helper.createObject(factory, projectStatusDAOValue,
                ProjectStatusDAO.class);
    }

    /**
     * Constructs an instance of this class with configuration in a specified file.
     *
     * @param namespace
     *            namespace of the configuration for this class, can't be null or empty
     * @param filename
     *            path of the configuration file, it can be null, but can't be empty
     * @throws IllegalArgumentException
     *             if file name is an empty string or namespace is null or empty string
     * @throws ManagerConfigurationException
     *             if error occurred when configuring this class, e.g. required configuration value is missing,
     *             or error occurred in object factory
     */
    public DAOProjectManager(String filename, String namespace) throws ManagerConfigurationException {
        this(getConfiguration(filename, namespace, DAOProjectManager.class.getName()));
    }

    /**
     * Creates a new Project. After creation, a new ID will be generated for it.
     *
     *
     * @param project
     *            the new Project to create, can not be null , its salesTax should be greater than zero, description is
     *            Non-null string, name is Non-null and non-empty string , and isDeleted is false
     * @param client
     *            the client of the project, it can't be null
     *
     * @return the Project instance created, with new ID assigned.
     * @throws IllegalArgumentException
     *             if any argument is invalid (project is null, or its salesTax is not greater than zero,
     *             its description is null, or its name is null or empty, or its isDeleted is true, or
     *             client is null)
     * @throws ProjectManagerException
     *             if any error occurred during this operation, e.g. error in persistence layer, or in ID generator
     */
    public Project createProject(Project project, Client client) throws ProjectManagerException {
        String method = "createProject(Project project, Client client)";
        Helper.logEntranceInfo(getLog(), CLASSNAME, method, new String[] {"project", "client"}, new String[] {
                Helper.formatProject(project), Helper.formatClient(client) });

        try {
            Helper.validateProject(project);
            Helper.checkNull(client, "client");

            // generate a new id for this project
            project.setId(getIDGenerator().getNextID());

            // set the client
            project.setClient(client);

            // save the project
            Project newProject = (Project) projectDAO.save(project);

            Helper.logExitInfo(getLog(), CLASSNAME, method, Helper.formatProject(newProject));

            return newProject;
        } catch (IllegalArgumentException iae) {
            Helper.logException(getLog(), CLASSNAME, method, iae);

            throw iae;
        } catch (IDGenerationException e) {
            throw wrapInnerException(method, "Fail to generate id for project, cause by " + e.getMessage(), e, project,
                    null);
        } catch (DAOException e) {
            throw wrapInnerException(method, "Fail to create the given project, cause by " + e.getMessage(), e,
                    project, null);
        } catch (DAOConfigurationException e) {
            throw wrapInnerException(method, "Fail to create the given project, cause by " + e.getMessage(), e,
                    project, null);
        } catch (ClassCastException cce) {
            throw wrapInnerException(method, "Fail to create the given project, cause by " + cce.getMessage(), cce,
                    project, null);
        }
    }

    /**
     * Wrap the inner exceptions such as DAOException, DAOConfigurationException and IDGenerationException to
     * ProjectManagerException. Log the exception created and return.
     *
     * @param method
     *            the method name , needs when log
     * @param message
     *            the error message
     * @param e
     *            the exception to be wrapped
     * @param project
     *            the Project instance to create ProjectManagerException
     * @param status
     *            the ProjectStatus instance to create ProjectManagerException
     * @return ProjectManagerException instance created
     */
    private ProjectManagerException wrapInnerException(String method, String message, Exception e, Project project,
            ProjectStatus status) {
        ProjectManagerException pmException = new ProjectManagerException(message, e, project, status);

        Helper.logException(getLog(), CLASSNAME, method, pmException);

        return pmException;
    }

    /**
     * Updates a Project. Error will be thrown if the project to update does not exist in persistence.
     *
     *
     * @param project
     *            the new Project to create, can not be null , its salesTax should be greater than zero, description is
     *            Non-null string, name is Non-null and non-empty string , and isDeleted is false
     * @return the updated Project
     *
     * @throws IllegalArgumentException
     *             if the argument is invalid (project is null, or its salesTax is not greater than zero,
     *             its description is null, or its name is null or empty, or its isDeleted is true)
     * @throws ProjectEntityNotFoundException
     *             if the project to be updated does not exist in persistence
     * @throws ProjectManagerException
     *             if any error occurred during this operation, e.g. error in persistence layer
     */
    public Project updateProject(Project project) throws ProjectEntityNotFoundException, ProjectManagerException {
        String method = "updateProject(Project project)";
        Helper.logEntranceInfo(getLog(), CLASSNAME, method, new String[] {"project"}, new String[] {Helper
                .formatProject(project)});

        try {
            Helper.validateProject(project);

            // check project exist.
            checkProjectExist(project, method);

            Project updateProject = (Project) projectDAO.save(project);

            Helper.logExitInfo(getLog(), CLASSNAME, method, Helper.formatProject(updateProject));

            return updateProject;
        } catch (IllegalArgumentException iae) {
            Helper.logException(getLog(), CLASSNAME, method, iae);
            throw iae;
        } catch (DAOConfigurationException e) {
            throw wrapInnerException(method, "Fail to update the Project, cause by " + e.getMessage(),
                    e, project, null);
        } catch (DAOException e) {
            throw wrapInnerException(method, "Fail to update the Project, cause by " + e.getMessage(),
                    e, project, null);
        } catch (ClassCastException cce) {
            throw wrapInnerException(method, "Fail to update the Project, cause by " + cce.getMessage(),
                    cce, project, null);
        }
    }

    /**
     * Check if given Project exists in the persistence.
     *
     * @param project
     *            the project instance to be checked
     * @param method
     *            the method to log
     * @throws ProjectEntityNotFoundException
     *             if the given project does not exist in the persistence
     * @throws DAOException
     *             if any other exception except IllegalArgumentException and EntityNotFoundException occur while
     *             perform projectDAO.retrieveById(id) function
     */
    private void checkProjectExist(Project project, String method)
        throws ProjectEntityNotFoundException, DAOException {
        try {
            if (projectDAO.retrieveById(project.getId()) == null) {
                ProjectEntityNotFoundException pnf = new ProjectEntityNotFoundException(
                        "The project to update can not be found in the persistence with id =" + project.getId());

                Helper.logException(getLog(), CLASSNAME, method, pnf);

                // throw ProjectEntityNotFoundException if the project can not be found in the persistence.
                throw pnf;
            }
        } catch (IllegalArgumentException iae) {
            throw handleProjectEntityNotFoundException(method, " The project with id=" + project.getId()
                    + " to update does not exist.", iae);
        } catch (EntityNotFoundException e) {
            throw handleProjectEntityNotFoundException(method, " The project with id=" + project.getId()
                    + " to update does not exist.", e);
        }
    }

    /**
     * Wrap the IllegalArgumentException or EntityNotFoundException while checking entity exist.
     *
     * @param method
     *            the method name to log
     * @param message
     *            the message
     * @param cause
     *            the inner cause
     * @return ProjectEntityNotFoundException created with error message and inner cause
     */
    private ProjectEntityNotFoundException handleProjectEntityNotFoundException(String method, String message,
            Exception cause) {
        ProjectEntityNotFoundException exception = new ProjectEntityNotFoundException(message, cause);

        Helper.logException(getLog(), CLASSNAME, method, exception);

        return exception;
    }

    /**
     * Deletes given Project.
     *
     * @param project
     *            the project to delete, can't be null, isDeleted should be false
     *
     * @return the deleted project, with isDeleted set to true
     * @throws IllegalArgumentException
     *             if project is null or it's isDeleted is true
     * @throws ProjectEntityNotFoundException
     *             if the ProjectStatus to be updated does not exist in persistence
     * @throws ProjectManagerException
     *             if any error occurred during this operation, e.g. error in persistence layer
     *
     */
    public Project deleteProject(Project project) throws ProjectManagerException, ProjectEntityNotFoundException {
        String method = "deleteProject(Project project) ";
        Helper.logEntranceInfo(getLog(), CLASSNAME, method, new String[] {"project"}, new String[] {Helper
                .formatProject(project)});

        try {
            Helper.checkNull(project, "project");
            if (project.isDeleted()) {
                throw new IllegalArgumentException("The project isDeleted value should be false, but was true.");
            }

            // delete the project
            projectDAO.delete(project);

            // set delete to true
            project.setDeleted(true);

            Helper.logExitInfo(getLog(), CLASSNAME, method, Helper.formatProject(project));

            return project;

        } catch (IllegalArgumentException iae) {
            Helper.logException(getLog(), CLASSNAME, method, iae);
            throw iae;
        } catch (DAOConfigurationException e) {
            throw wrapInnerException(method, "Fail to delete the project, cause by " + e.getMessage(),
                    e, project, null);
        } catch (EntityNotFoundException e) {
            throw handleProjectEntityNotFoundException(method, "The project with id=" + project.getId()
                    + " to delete does not exist.", e);
        } catch (DAOException e) {
            throw wrapInnerException(method, "Fail to delete the project, cause by " + e.getMessage(),
                    e, project, null);
        }
    }

    /**
     * Gets the Project by its ID. All child project will be retrieved too.
     *
     *
     * @param id
     *            ID of the Project to retrieve, can't be negative
     * @return the Project with given ID, or null if none exists with given ID
     * @throws IllegalArgumentException
     *             if id is negative
     * @throws ProjectManagerException
     *             if any error occurred during this operation, e.g. error in persistence layer
     */
    public Project retrieveProject(long id) throws ProjectManagerException {
        String method = "retrieveProject(long id)";
        Helper.logEntranceInfo(getLog(), CLASSNAME, method, new String[] {"id"}, new String[] {String.valueOf(id)});

        // get the project with id and includeChildren = true
        Project project = retrieveProject(id, true);

        Helper.logExitInfo(getLog(), CLASSNAME, method, Helper.formatProject(project));

        return project;
    }

    /**
     * Gets the Project by its ID, a boolean parameter is provided to indicate whether the child projects should be
     * retrieved. Error will occur if no entity exists for this ID.
     *
     * @param includeChildren
     *            true means child projects should also be retrieved, false mean no need to retrieve child projects
     * @param id
     *            id of the Project to retrieve, can't be negative
     * @return the Project with given ID, or null is none exists with given ID
     * @throws IllegalArgumentException if id is negative
     * @throws ProjectManagerException
     *             if any error occurred during this operation, e.g. error in persistence layer
     */
    public Project retrieveProject(long id, boolean includeChildren) throws ProjectManagerException {
        String method = "retrieveProject(long id, boolean includeChildren)";
        Helper.logEntranceInfo(getLog(), CLASSNAME, method, new String[] {"id", "includeChildren"}, new String[] {
                String.valueOf(id), String.valueOf(includeChildren)});

        try {
            // validate the id
            Helper.checkId(id);

            // get the Project by id
            Project project = (Project) projectDAO.retrieveById(id, includeChildren);

            Helper.logExitInfo(getLog(), CLASSNAME, method, Helper.formatProject(project));

            return project;
        } catch (IllegalArgumentException iae) {
            Helper.logException(getLog(), CLASSNAME, method, iae);
            throw iae;
        } catch (DAOConfigurationException e) {
            throw wrapInnerException(method, "Fail to retrieve project with id=" + id + " cause by " + e.getMessage(),
                    e, null, null);
        } catch (DAOException e) {
            throw wrapInnerException(method, "Fail to retrieve project with id=" + id + " cause by " + e.getMessage(),
                    e, null, null);
        }

    }

    /**
     * Gets the projects for specified client, children projects will be retrieved. Empty list will be returned if none
     * if found.
     *
     * @param client
     *            a Client whose projects will be retrieved, can't be null, id of client should not be negative
     *
     * @return a list of Project objects, can be empty
     * @throws IllegalArgumentException
     *             if client is null, or its id is negative
     * @throws ProjectManagerException
     *             if any error occurred during this operation, e.g. error in persistence layer
     */
    public List<Project> retrieveProjectsForClient(Client client) throws ProjectManagerException {
        String method = "retrieveProjectsForClient(Client client)";
        Helper.logEntranceInfo(getLog(), CLASSNAME, method, new String[] {"client"}, new String[] {Helper
                .formatClient(client)});

        List<Project> projects = retrieveProjectsForClient(client, true);

        Helper.logExitInfo(getLog(), CLASSNAME, method, Helper.formatProjectList(projects));

        return projects;
    }

    /**
     * Gets the projects for specified client, a boolean parameter is provided to indicate whether the child projects
     * should be retrieved. Empty list will be returned if none if found.
     *
     *
     * @param includeChildren
     *            true means the child projects should also be retrieved, false mean no need to retrieve child projects
     * @param client
     *            a Client whose projects will be retrieved, can't be null, id of client should not be negative
     *
     * @return a list of Project objects, can be empty
     * @throws IllegalArgumentException
     *             if argument is invalid (the client is null, id of client is negative)
     * @throws ProjectManagerException
     *             if any error occurred during this operation, e.g. error in persistence layer
     */
    public List<Project> retrieveProjectsForClient(Client client, boolean includeChildren)
        throws ProjectManagerException {
        String method = "retrieveProjectsForClient(Client client, boolean includeChildren)";
        Helper.logEntranceInfo(getLog(), CLASSNAME, method, new String[] {"client", "includeChildren"}, new String[] {
                Helper.formatClient(client), String.valueOf(includeChildren) });

        try {

            Helper.checkNull(client, "client");
            if (client.getId() < 0) {
                throw new IllegalArgumentException("The client id should not be negative.");
            }

            // get projects for given client
            List<Project> projects = clientDAO.getProjectsForClient(client);

            if (includeChildren) {
                // include the child projects

                for (int i = 0; i < projects.size(); i++) {
                    Project currentProject = projects.get(i);

                    // if no child projects, retrieve project with id and set it by the newly retrieved project
                    if (currentProject.getChildProjects() == null || currentProject.getChildProjects().isEmpty()) {

                        // retrieve the Project with child and set back
                        projects.set(i, retrieveProject(currentProject.getId()));
                    }
                }
            }

            Helper.logExitInfo(getLog(), CLASSNAME, method, Helper.formatProjectList(projects));

            return projects;
        } catch (IllegalArgumentException iae) {
            Helper.logException(getLog(), CLASSNAME, method, iae);
            throw iae;
        } catch (DAOConfigurationException e) {
            // wrap DAOConfigurationException to ProjectManagerException and throw
            throw wrapInnerException(method, "Fail to retrieve project with client, cause by " + e.getMessage(), e,
                    null, null);
        } catch (DAOException e) {
            throw wrapInnerException(method, "Fail to retrieve project with client, cause by " + e.getMessage(), e,
                    null, null);
        }
    }

    /**
     * Gets all projects, the child projects will be retrieved too. Empty list will be returned if none is found.
     *
     * @return a list of Project objects, can be empty
     * @throws ProjectManagerException
     *             if any error occurred during this operation, e.g. error in persistence layer
     */
    public List<Project> retrieveAllProjects() throws ProjectManagerException {
        String method = " retrieveAllProjects";
        Helper.logEntranceInfo(getLog(), CLASSNAME, method, new String[] {}, new String[] {});

        List<Project> projects = retrieveAllProjects(true);

        Helper.logExitInfo(getLog(), CLASSNAME, method, Helper.formatProjectList(projects));

        return projects;
    }

    /**
     * Gets all projects, a boolean parameter is provided to indicate whether the child projects should be retrieved.
     * Empty list will be returned if none if found.
     *
     * @param includeChildren
     *            true means child projects should also be retrieved, false mean no need to retrieve child projects
     * @return a list of Project objects, can be empty
     * @throws ProjectManagerException
     *             if any error occurred during this operation, e.g. error in persistence layer
     */
    public List<Project> retrieveAllProjects(boolean includeChildren) throws ProjectManagerException {
        String method = "retrieveAllProjects(boolean includeChildren)";
        Helper.logEntranceInfo(getLog(), CLASSNAME, method, new String[] {"includeChildren"}, new String[] {String
                .valueOf(includeChildren)});

        try {
            List<Project> projects = projectDAO.retrieveAll(includeChildren);

            Helper.logExitInfo(getLog(), CLASSNAME, method, Helper.formatProjectList(projects));

            return projects;

        } catch (DAOConfigurationException e) {
            // wrap DAOConfigurationException to ProjectManagerException and throw
            throw wrapInnerException(method, "Fail to retrieve all Projects, cause by " + e.getMessage(),
                    e, null, null);
        } catch (DAOException e) {
            throw wrapInnerException(method, "Fail to retrieve all Projects, cause by " + e.getMessage(),
                    e, null, null);
        }
    }

    /**
     * Sets the project status, and forward this update to the persistence.
     *
     * @param project
     *            the new Project to create, can not be null , its salesTax should be greater than zero, description is
     *            Non-null string, name is Non-null and non-empty string , and isDeleted is false
     * @param status
     *            the project status to set, can't be null, its name should be non-null and non-empty string, its
     *            description should be non-null and non-empty string and its isDeleted should be false
     *
     * @return the updated Project, with new status set
     * @throws IllegalArgumentException
     *             if any argument is invalid (project is null, or its salesTax is not greater than zero,
     *             its description is null, or its name is null or empty, or its isDeleted is true;
     *             status is null, status's name is null or empty, status's description is null or empty,
     *             status's isDeleted is true)
     * @throws ProjectManagerException
     *             if any error occurred during this operation, e.g. error in persistence layer
     * @throws ProjectEntityNotFoundException
     *             if the project to be updated does not exist in persistence
     */
    public Project setProjectStatus(Project project, ProjectStatus status) throws ProjectEntityNotFoundException,
        ProjectManagerException {
        String method = "setProjectStatus(Project project, ProjectStatus status)";
        Helper.logEntranceInfo(getLog(), CLASSNAME, method, new String[] {"project", "status"}, new String[] {
                Helper.formatProject(project), Helper.formatProjectStatus(status)});

        try {
            Helper.validateProject(project);

            Helper.validateProjectStatus(status);
        } catch (IllegalArgumentException iae) {
            Helper.logException(getLog(), CLASSNAME, method, iae);
            throw iae;
        }

        // set the ProjectStatus
        project.setProjectStatus(status);
        // update the project
        Project updateProject = updateProject(project);

        Helper.logExitInfo(getLog(), CLASSNAME, method, Helper.formatProject(updateProject));

        return updateProject;
    }

    /**
     * Finds the Projects that match given name. Empty list will be returned if none is found.
     *
     * @param name
     *            name of the Projects to search for, can't be null or empty
     *
     * @return a list of Project objects that match given name, empty list if none is found
     * @throws IllegalArgumentException
     *             if name is null or empty
     * @throws ProjectManagerException
     *             if any error occurred during this operation, e.g. error in persistence layer
     */
    public List<Project> searchProjectsByName(String name) throws ProjectManagerException {
        String method = "searchProjectsByName(String name)";
        Helper.logEntranceInfo(getLog(), CLASSNAME, method, new String[] {"name"}, new String[] {name});

        try {
            Helper.checkString(name, "name");

            // search by name
            List<Project> projects = projectDAO.searchByName(name);

            Helper.logExitInfo(getLog(), CLASSNAME, method, Helper.formatProjectList(projects));

            return projects;
        } catch (IllegalArgumentException iae) {
            Helper.logException(getLog(), CLASSNAME, method, iae);
            throw iae;
        } catch (DAOConfigurationException e) {
            // wrap DAOConfigurationException to ProjectManagerException and throw
            throw wrapInnerException(method, "Fail to search projects by name=" + name + " cause by " + e.getMessage(),
                    e, null, null);
        } catch (DAOException e) {
            throw wrapInnerException(method, "Fail to search projects by name=" + name + " cause by " + e.getMessage(),
                    e, null, null);
        }

    }

    /**
     * Finds the projects with specified filter. Empty list will be returned if none is found
     *
     *
     * @param filter
     *            the filter used to search for Projects, can't be null
     *
     * @return a list of Project objects that match the filter, empty list if none is found
     * @throws IllegalArgumentException
     *             if filter is null
     * @throws ProjectManagerException
     *             if any error occurred during this operation, e.g. error in persistence layer
     */
    public List<Project> searchProjects(Filter filter) throws ProjectManagerException {
        String method = "searchProjects(Filter filter)";
        Helper.logEntranceInfo(getLog(), CLASSNAME, method, new String[] {"filter"}, new String[] {String
                .valueOf(filter)});

        try {
            Helper.checkNull(filter, "filter");

            List<Project> projects = projectDAO.search(filter);

            Helper.logExitInfo(getLog(), CLASSNAME, method, Helper.formatProjectList(projects));

            return projects;

        } catch (IllegalArgumentException iae) {
            Helper.logException(getLog(), CLASSNAME, method, iae);
            throw iae;
        } catch (DAOConfigurationException e) {
            // wrap DAOConfigurationException to ProjectManagerException and throw
            throw wrapInnerException(method, "Fail to search Project by filter, cause by " + e.getMessage(), e, null,
                    null);
        } catch (DAOException e) {
            throw wrapInnerException(method, "Fail to search Project by filter, cause by " + e.getMessage(), e, null,
                    null);
        }
    }

    /**
     * Gets the ProjectStatus with given ID.
     *
     * @param id
     *            id of the ProjectStatus to retrieve, can't be negative
     * @return a ProjectStatus matching given ID, or null if none exists with given id
     * @throws IllegalArgumentException
     *             if id is negative
     * @throws ProjectManagerException
     *             if any error occurred during this operation, e.g. error in persistence layer
     */
    public ProjectStatus getProjectStatus(long id) throws ProjectManagerException {
        String method = "getProjectStatus(long id)";
        Helper.logEntranceInfo(getLog(), CLASSNAME, method, new String[] {"id"}, new String[] {String.valueOf(id)});

        try {
            Helper.checkId(id);

            ProjectStatus projectStatus = (ProjectStatus) projectStatusDAO.retrieveById(id);

            Helper.logExitInfo(getLog(), CLASSNAME, method, Helper.formatProjectStatus(projectStatus));

            return projectStatus;
        } catch (IllegalArgumentException iae) {
            Helper.logException(getLog(), CLASSNAME, method, iae);

            throw iae;
        } catch (DAOConfigurationException e) {
            // wrap DAOConfigurationException to ProjectManagerException and throw
            throw wrapInnerException(method,
                    "Fail to getProjectStatus with id=" + id + " , cause by " + e.getMessage(), e, null, null);
        } catch (DAOException e) {
            throw wrapInnerException(method,
                    "Fail to getProjectStatus with id=" + id + " , cause by " + e.getMessage(), e, null, null);
        } catch (ClassCastException cce) {
            throw wrapInnerException(method, "Fail to getProjectStatus with id=" + id + " , cause by "
                    + cce.getMessage(), cce, null, null);
        }
    }

    /**
     * Gets all the project status. If none exists, empty list will be returned.
     *
     * @return a list of ProjectStatus objects, empty list if none exists
     * @throws ProjectManagerException
     *             if any error occurred during this operation, e.g. error in persistence layer
     */
    public List<ProjectStatus> getAllProjectStatuses() throws ProjectManagerException {
        String method = "getAllProjectStatuses()";
        Helper.logEntranceInfo(getLog(), CLASSNAME, method, new String[] {}, new String[] {});

        try {
            List<ProjectStatus> projectStatuses = projectStatusDAO.retrieveAll();

            Helper.logExitInfo(getLog(), CLASSNAME, method, Helper.formatProjectStatusList(projectStatuses));

            return projectStatuses;
        } catch (DAOConfigurationException e) {
            throw wrapInnerException(method, "Fail to get all ProjectStatus , cause by " + e.getMessage(), e, null,
                    null);
        } catch (DAOException e) {
            throw wrapInnerException(method, "Fail to get all ProjectStatus , cause by " + e.getMessage(), e, null,
                    null);
        }
    }

    /**
     * Gets all Projects that match given project status. Empty list will be returned if none if found.
     *
     *
     * @param status
     *            a ProjectStatus, can't be null, id should not be negative
     *
     * @return a list Project objects, empty list if none is found.
     * @throws IllegalArgumentException
     *             if status is null, or its id is negative
     * @throws ProjectManagerException
     *             if any error occurred during this operation, e.g. error in persistence layer
     */
    public List<Project> getProjectsForStatus(ProjectStatus status) throws ProjectManagerException {
        String method = "getProjectsForStatus(ProjectStatus status)";
        Helper.logEntranceInfo(getLog(), CLASSNAME, method, new String[] {"status"}, new String[] {Helper
                .formatProjectStatus(status) });

        try {
            Helper.checkNull(status, "status");

            Helper.checkId(status.getId());

            List<Project> projects = projectStatusDAO.getProjectsWithStatus(status);

            Helper.logExitInfo(getLog(), CLASSNAME, method, Helper.formatProjectList(projects));

            return projects;
        } catch (IllegalArgumentException iae) {
            Helper.logException(getLog(), CLASSNAME, method, iae);
            throw iae;
        } catch (DAOConfigurationException e) {
            throw wrapInnerException(method, "Fail to get Projects for ProjectStatus, cause by " + e.getMessage(), e,
                    null, status);
        } catch (DAOException e) {
            throw wrapInnerException(method, "Fail to get Projects for ProjectStatus, cause by " + e.getMessage(), e,
                    null, status);
        }
    }

    /**
     * Create a new ProjectStatus, a new id will be generated for it.
     *
     * @param status
     *            a new ProjectStatus to create, can't be null (its name should be non-null and non-empty, description
     *            non-null and non-empty, isDeleted false)
     *
     * @return the created ProjectStatus with new ID assigned
     * @throws IllegalArgumentException
     *             if the ProjectStatus is not valid to be created (null or its name is null or empty,
     *             its description is null or empty, or isDeleted is true)
     * @throws ProjectManagerException
     *             if any error occurred during this operation, e.g. error in persistence layer, or in ID generator
     */
    public ProjectStatus createProjectStatus(ProjectStatus status) throws ProjectManagerException {
        String method = "createProjectStatus(ProjectStatus status)";
        Helper.logEntranceInfo(getLog(), CLASSNAME, method, new String[] {"status"}, new String[] {Helper
                .formatProjectStatus(status)});

        try {
            Helper.validateProjectStatus(status);

            // generate a new id for ProjectStatus
            status.setId(getIDGenerator().getNextID());

            // save the ProjectStatus
            ProjectStatus newStatus = (ProjectStatus) projectStatusDAO.save(status);

            Helper.logExitInfo(getLog(), CLASSNAME, method, Helper.formatProjectStatus(newStatus));

            return newStatus;
        } catch (IllegalArgumentException iae) {
            Helper.logException(getLog(), CLASSNAME, method, iae);
            throw iae;
        } catch (IDGenerationException e) {
            throw wrapInnerException(method, "Fail to generate id for ProjectStatus, cause by " + e.getMessage(), e,
                    null, status);
        } catch (DAOConfigurationException e) {
            throw wrapInnerException(method, "Fail to create ProjectStatus, cause by " + e.getMessage(), e, null,
                    status);
        } catch (DAOException e) {
            throw wrapInnerException(method, "Fail to create ProjectStatus, cause by " + e.getMessage(), e, null,
                    status);
        } catch (ClassCastException cce) {
            throw wrapInnerException(method, "Fail to create ProjectStatus, cause by " + cce.getMessage(), cce, null,
                    null);
        }
    }

    /**
     * Updates a ProjectStatus. Error will be thrown if the project status to update does not exist in persistence.
     *
     * @param status
     *            a new ProjectStatus to create, can't be null (its name should be non-null and non-empty, description
     *            non-null and non-empty, isDeleted false)
     *
     * @return the updated ProjectStatus
     * @throws IllegalArgumentException
     *             if the argument is invalid (null or its name is null or empty,
     *             its description is null or empty, or isDeleted is true)
     * @throws ProjectEntityNotFoundException
     *             if the ProjectStatus to be updated does not exist in persistence
     * @throws ProjectManagerException
     *             if any error occurred during this operation, e.g. error in persistence layer
     */
    public ProjectStatus updateProjectStatus(ProjectStatus status) throws ProjectEntityNotFoundException,
        ProjectManagerException {
        String method = "updateProjectStatus(ProjectStatus status)";
        Helper.logEntranceInfo(getLog(), CLASSNAME, method, new String[] {"status"}, new String[] {Helper
                .formatProjectStatus(status)});

        try {
            // validate parameter
            Helper.validateProjectStatus(status);

            // check if ProjectStatus existed in the persistence
            checkProjectStatusExist(status, method);

            // save the ProjectStatus.
            ProjectStatus newStatus = (ProjectStatus) projectStatusDAO.save(status);

            Helper.logExitInfo(getLog(), CLASSNAME, method, Helper.formatProjectStatus(newStatus));

            return newStatus;
        } catch (IllegalArgumentException iae) {
            Helper.logException(getLog(), CLASSNAME, method, iae);

            throw iae;
        } catch (DAOConfigurationException e) {
            throw wrapInnerException(method, "Fail to update ProjectStatus, cause by " + e.getMessage(), e, null,
                    status);
        } catch (DAOException e) {
            throw wrapInnerException(method, "Fail to update ProjectStatus, cause by " + e.getMessage(), e, null,
                    status);
        } catch (ClassCastException cce) {
            throw wrapInnerException(method, "Fail to update ProjectStatus, cause by " + cce.getMessage(), cce, null,
                    null);
        }
    }

    /**
     * Check if the ProjectStatus exist in the persistence.
     *
     * @param status
     *            the ProjectStatus to be checked
     * @param method
     *            the method for log
     * @throws ProjectEntityNotFoundException
     *             if the given ProjectStatus does not exist
     * @throws DAOException
     *             if any other exception except IllegalArgumentException and EntityNotFoundException occur while
     *             performing projectStatusDAO.retrieveById(id) function
     */
    private void checkProjectStatusExist(ProjectStatus status, String method) throws ProjectEntityNotFoundException,
        DAOException {
        try {
            if (projectStatusDAO.retrieveById(status.getId()) == null) {
                ProjectEntityNotFoundException projectNF = new ProjectEntityNotFoundException(
                        "The ProjectStatus with id=" + status.getId() + " does not exist.");

                Helper.logException(getLog(), CLASSNAME, method, projectNF);

                throw projectNF;
            }
        } catch (IllegalArgumentException iae) {
            throw handleProjectEntityNotFoundException(method, "The ProjectStatus with id=" + status.getId()
                    + " does not exist, cause by " + iae.getMessage(), iae);
        } catch (EntityNotFoundException e) {
            throw handleProjectEntityNotFoundException(method, "The ProjectStatus with id=" + status.getId()
                    + " does not exist, cause by " + e.getMessage(), e);
        }
    }

    /**
     * Deletes given ProjectStatus.
     *
     *
     * @param status
     *            the project status to delete, can't be null, isDeleted should be false
     *
     * @return the deleted project status, with isDeleted set to true
     *
     * @throws IllegalArgumentException
     *             if project status is null or it's isDeleted is true
     * @throws ProjectEntityNotFoundException
     *             if the ProjectStatus to be updated does not exist in persistence
     * @throws ProjectManagerException
     *             if any error occurred during this operation, e.g. error in persistence layer
     */
    public ProjectStatus deleteProjectStatus(ProjectStatus status) throws ProjectManagerException,
        ProjectEntityNotFoundException {
        String method = "deleteProjectStatus(ProjectStatus status)";
        Helper.logEntranceInfo(getLog(), CLASSNAME, method, new String[] {"status"}, new String[] {Helper
                .formatProjectStatus(status)});

        try {
            Helper.checkNull(status, "status");
            if (status.isDeleted()) {
                throw new IllegalArgumentException("The para status should have isDeleted  false, but was true.");
            }

            // delete the status
            projectStatusDAO.delete(status);

            status.setDeleted(true);
            Helper.logExitInfo(getLog(), CLASSNAME, method, Helper.formatProjectStatus(status));

            return status;
        } catch (IllegalArgumentException iae) {
            Helper.logException(getLog(), CLASSNAME, method, iae);
            throw iae;
        } catch (EntityNotFoundException e) {
            throw handleProjectEntityNotFoundException(method, "The ProjectStatus with id=" + status.getId()
                    + " does not exist, cause by " + e.getMessage(), e);
        } catch (DAOException e) {
            throw wrapInnerException(method, "Fail to delete the ProjectStatus, cause by " + e.getMessage(), e, null,
                    status);
        } catch (DAOConfigurationException e) {
            throw wrapInnerException(method, "Fail to delete the ProjectStatus, cause by " + e.getMessage(), e, null,
                    status);
        }
    }
}
