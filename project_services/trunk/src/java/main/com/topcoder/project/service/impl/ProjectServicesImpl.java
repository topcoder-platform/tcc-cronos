/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import sun.font.EAttribute;

import com.cronos.onlinereview.external.ExternalProject;
import com.cronos.onlinereview.external.ProjectRetrieval;
import com.cronos.onlinereview.external.RetrievalException;
import com.topcoder.management.phase.PhaseManagementException;
import com.topcoder.management.phase.PhaseManager;
import com.topcoder.management.project.PersistenceException;
import com.topcoder.management.project.Project;
import com.topcoder.management.project.ProjectFilterUtility;
import com.topcoder.management.project.ProjectManager;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.resource.ResourceManager;
import com.topcoder.management.resource.persistence.ResourcePersistenceException;
import com.topcoder.management.resource.search.ResourceFilterBuilder;
import com.topcoder.management.team.TeamHeader;
import com.topcoder.management.team.TeamManager;
import com.topcoder.management.team.TeamPersistenceException;
import com.topcoder.project.phases.Phase;
import com.topcoder.project.service.ConfigurationException;
import com.topcoder.project.service.FullProjectData;
import com.topcoder.project.service.ProjectServices;
import com.topcoder.project.service.ProjectServicesException;
import com.topcoder.project.service.Util;
import com.topcoder.search.builder.SearchBuilderException;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.UnknownNamespaceException;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;
import com.topcoder.util.objectfactory.InvalidClassSpecificationException;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.impl.ConfigManagerSpecificationFactory;
import com.topcoder.util.objectfactory.impl.IllegalReferenceException;
import com.topcoder.util.objectfactory.impl.SpecificationConfigurationException;

/**
 * <p>
 * Full implementation of the <code>ProjectServices</code> interface. This implementation makes
 * use of a large array of components to accomplish its task of retrieving project data: <b>User
 * Data Store Persistence</b> component to retrieve external project information, the <b>Project
 * Phases</b> and <b>Phase and Resource Management</b> to get project header and phase
 * information, <b>Resource Manager</b> to get resource information, and <b>Team Manager</b> to
 * get team information.
 * </p>
 * <p>
 * To provide a good view as the steps are progressing in each method, the <b>Logging Wrapper</b>
 * component is used in each method. To configure this component, the <b>ConfigManager</b> and
 * <b>ObjectFactory</b> components are used.
 * </p>
 * <p>
 * Also provided are configuration-backed and programmatic constructors. This allows the user to
 * either create all internal supporting objects from configuration, or to simply pass the instances
 * directly.
 * </p>
 * <p>
 * Here is the sample configuration for this class
 *
 * <pre>
 *  &lt;Config name=&quot;com.topcoder.project.service.impl.ProjectServicesImpl&quot;&gt;
 *      &lt;Property name=&quot;specNamespace&quot;&gt;
 *          &lt;Value&gt;com.topcoder.util.objectfactory&lt;/Value&gt;
 *      &lt;/Property&gt;
 *      &lt;Property name=&quot;projectRetrievalKey&quot;&gt;
 *          &lt;Value&gt;projectRetrievalKey&lt;/Value&gt;
 *      &lt;/Property&gt;
 *      &lt;Property name=&quot;resourceManagerKey&quot;&gt;
 *          &lt;Value&gt;resourceManagerKey&lt;/Value&gt;
 *      &lt;/Property&gt;
 *      &lt;Property name=&quot;phaseManagerKey&quot;&gt;
 *          &lt;Value&gt;phaseManagerKey&lt;/Value&gt;
 *      &lt;/Property&gt;
 *      &lt;Property name=&quot;projectManagerKey&quot;&gt;
 *          &lt;Value&gt;projectManagerKey&lt;/Value&gt;
 *      &lt;/Property&gt;
 *      &lt;Property name=&quot;teamManagerKey&quot;&gt;
 *          &lt;Value&gt;teamManagerKey&lt;/Value&gt;
 *      &lt;/Property&gt;
 *      &lt;Property name=&quot;loggerName&quot;&gt;
 *          &lt;Value&gt;SystemLog&lt;/Value&gt;
 *      &lt;/Property&gt;
 *      &lt;Property name=&quot;activeProjectStatusId&quot;&gt;
 *          &lt;Value&gt;1&lt;/Value&gt;
 *      &lt;/Property&gt;
 *  &lt;/Config&gt;
 * </pre>
 *
 * </p>
 * <p>
 * Thread Safety: This class is immutable but operates on non thread safe objects, thus making it
 * potentially non thread safe.
 * </p>
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public class ProjectServicesImpl implements ProjectServices {

    /**
     * <p>
     * Represents the default namespace used by the default constructor to access configuration info
     * in the construction.
     * </p>
     */
    public static final String DEFAULT_NAMESPACE = "com.topcoder.project.service.impl.ProjectServicesImpl";

    /**
     * <p>
     * Represents the <b>specNamespace</b> property key.
     * </p>
     */
    private static final String SPEC_NAMESPACE = "specNamespace";

    /**
     * <p>
     * Represents the <b>userRetrieval</b> property key.
     * </p>
     */
    private static final String PROJECT_RETRIEVAL_KEY = "projectRetrievalKey";

    /**
     * <p>
     * Represents the <b>ResourceManager</b> property key.
     * </p>
     */
    private static final String RESOURCE_MANAGER_KEY = "resourceManagerKey";

    /**
     * <p>
     * Represents the <b>phaseManagerKey</b> property key.
     * </p>
     */
    private static final String PHASE_MANAGER_KEY = "phaseManagerKey";

    /**
     * <p>
     * Represents the <b>projectManagerKey</b> property key.
     * </p>
     */
    private static final String PROJECT_MANAGER_KEY = "projectManagerKey";

    /**
     * <p>
     * Represents the <b>teamManagerKey</b> property key.
     * </p>
     */
    private static final String TEAM_MANAGER_KEY = "teamManagerKey";

    /**
     * <p>
     * Represents the <b>loggerName</b> property key.
     * </p>
     */
    private static final String LOGGER_NAME = "loggerName";

    /**
     * <p>
     * Represents the <b>activeProjectStatusId</b> property key.
     * </p>
     */
    private static final String ACTIVE_PROJECT_STATUS_ID = "activeProjectStatusId";

    /**
     * <p>
     * Represents the <b>activeProjectStatusId</b> property key.
     * </p>
     */
    private static final String ACTIVE_PROJECT_CATEGORY_ID = "activeProjectCategoryId";

    /**
     * <p>
     * Represents the <b>externalReferenceID</b> property key.
     * </p>
     */
    private static final String EXTERNAL_REFERENCE_ID = "External Reference ID";

    /**
     * <p>
     * Represents the <code>ProjectRetrieval</code> instance that is used to retrieve the project
     * technologies information. It is set in the constructor to a non-null value, and will never
     * change.
     * </p>
     */
    private final ProjectRetrieval projectRetrieval;

    /**
     * <p>
     * Represents the <code>ResourceManager</code> instance that is used to retrieve resources. It
     * is set in the constructor to a non-null value, and will never change.
     * </p>
     */
    private final ResourceManager resourceManager;

    /**
     * <p>
     * Represents the <code>PhaseManager</code> instance that is used to obtain phase information
     * about a project. It is set in the constructor to a non-null value, and will never change.
     * </p>
     */
    private final PhaseManager phaseManager;

    /**
     * <p>
     * Represents the <code>TeamManager</code> instance that is used to retrieve teams. It is set
     * in the constructor to a non-null value, and will never change.
     * </p>
     */
    private final TeamManager teamManager;

    /**
     * <p>
     * Represents the <code>ProjectManager</code> instance that is used to retrieve projects. It
     * is set in the constructor to a non-null value, and will never change.
     * </p>
     */
    private final ProjectManager projectManager;

    /**
     * <p>
     * Used extensively in this class to log information. This will include logging method entry and
     * exit, errors, debug information for calls to other components, etc.
     * </p>
     * <p>
     * Note that logging is optional and can be null, in which case, no logging will take place. It
     * will be set in the constructor and will not change.
     * </p>
     */
    private final Log logger;

    /**
     * <p>
     * Represents the id of the active project status.
     * </p>
     * <p>
     * It is set in the constructor to a non-negative value, and will never change.
     * </p>
     */
    private final long activeProjectStatusId;

    /**
     * <p>
     * Represents the ids of the active project categories.
     * </p>
     * <p>
     * It is set in the constructor to non-negative values, and will never change.
     * </p>
     */
    List<Long> activeCategoriesList=new ArrayList<Long>();

    /**
     * <p>
     * Default constructor.
     * </p>
     * @throws ConfigurationException
     *             If any configuration error occurs, such as unknown namespace, or missing required
     *             values, or errors while constructing the managers and services
     */
    public ProjectServicesImpl() {
        this(DEFAULT_NAMESPACE);
    }

    /**
     * <p>
     * Namespace constructor. Initializes this instance from configuration info from the <b>Config
     * Manager</b>. It will use the <b>Object Factory</b> to create instances of required objects.
     * </p>
     * @param namespace
     *            The configuration namespace
     * @throws IllegalArgumentException
     *             If namespace is null/empty
     * @throws ConfigurationException
     *             If any configuration error occurs, such as unknown namespace, or missing required
     *             values, or errors while constructing the managers and services.
     */
    public ProjectServicesImpl(String namespace) {
        Util.checkStrNotNullOrEmpty(namespace, "namespace");

        ConfigManager cm = ConfigManager.getInstance();
        try {
            // gets namespace for ConfigManagerSpecificationFactory
            String specNamespace = cm.getString(namespace, SPEC_NAMESPACE);
            // creates an instance of ObjectFactory
            ObjectFactory objectFactory = new ObjectFactory(new ConfigManagerSpecificationFactory(
                specNamespace));

            // gets the value of userRetrievalKey and creates an instance by ObjectFactory
            this.projectRetrieval = (ProjectRetrieval) createObject(cm, objectFactory, namespace,
                PROJECT_RETRIEVAL_KEY);
            // gets the value of resourceManagerKey and creates an instance by ObjectFactory
            this.resourceManager = (ResourceManager) createObject(cm, objectFactory, namespace,
                RESOURCE_MANAGER_KEY);
            // gets the value of phaseManagerKey and creates an instance by ObjectFactory
            this.phaseManager = (PhaseManager) createObject(cm, objectFactory, namespace,
                PHASE_MANAGER_KEY);
            // gets the value of projectManagerKey and creates an instance by ObjectFactory
            this.projectManager = (ProjectManager) createObject(cm, objectFactory, namespace,
                PROJECT_MANAGER_KEY);
            // gets the value of teamManagerKey and creates an instance by ObjectFactory
            this.teamManager = (TeamManager) createObject(cm, objectFactory, namespace,
                TEAM_MANAGER_KEY);

            // gets name of the log and gets the logger instance from LogManager if necessary
            String logName = cm.getString(namespace, LOGGER_NAME);
            this.logger = ((logName == null) ? null : LogManager.getLog(logName));

            // gets the value of activeProjectStatusId
            String activeStatusId = cm.getString(namespace, ACTIVE_PROJECT_STATUS_ID);
            // parses it to 'long' value, if error occurs or negative value returned, throw an
            // exception
            long theActiveProjectStatusId = Long.parseLong(activeStatusId);
            if (theActiveProjectStatusId < 0) {
                throw new ConfigurationException(
                    "Value of [activeProjectStatusId] should not be negative.");
            }
            this.activeProjectStatusId = theActiveProjectStatusId;


            // Nuevo parametro
            log(Level.DEBUG, "Looking for activeCategoryIds");
            int categoryEntryNumber = 0;            
            while (true) {
                // increase the innerValidatorNumber to get the key for the next innerValidator.
            	categoryEntryNumber++;
                String keyPropertyName = ACTIVE_PROJECT_CATEGORY_ID + categoryEntryNumber;
                // Creates innerValidator using the ObjectFactory

                String categoryIdStr = cm.getString(namespace, keyPropertyName);
                if (categoryIdStr == null) {
                    break;
                } else {
                    long categoryId = Long.parseLong(categoryIdStr);
                	if (categoryId < 0) {
	                    throw new ConfigurationException(
	                        "Value of [" + ACTIVE_PROJECT_CATEGORY_ID + categoryEntryNumber + "] should not be negative.");
	                }
                    log(Level.DEBUG, "Adding activeCategoryId: " + categoryId);					
                	this.activeCategoriesList.add(categoryId);
                }
            }
            // Hasta aca 
            
        } catch (UnknownNamespaceException ex) {
            throw new ConfigurationException(
                "Given namespace can't be recognized by ConfigManager.", ex);
        } catch (IllegalReferenceException ex) {
            throw new ConfigurationException(
                "IllegalReferenceException occurred when initializing ObjectFactory.", ex);
        } catch (SpecificationConfigurationException ex) {
            throw new ConfigurationException(
                "SpecificationConfigurationException occurred when initializing ObjectFactory.", ex);
        } catch (InvalidClassSpecificationException ex) {
            throw new ConfigurationException("The configuration for ObjectFactory is invalid.", ex);
        } catch (NumberFormatException ex) {
            throw new ConfigurationException(
                "Long value in configuration can not be converted to 'long' type.", ex);
        } catch (IllegalArgumentException ex) {
            throw new ConfigurationException(
                "Some configuration value for ObjectFactory is null or empty.", ex);
        } catch (ClassCastException ex) {
            throw new ConfigurationException(
                "Object created by ObjectFactory can not be casted to specific type.", ex);
        }
    }

    /**
     * <p>
     * Parameter constructor.
     * </p>
     * @param projectRetrieval
     *            the ProjectRetrieval instance that is used to retrieve the project technologies
     *            information
     * @param resourceManager
     *            the ResourceManager instance that is used to retrieve resources
     * @param phaseManager
     *            the PhaseManager instance that is used to obtain phase information about a project
     * @param teamManager
     *            the TeamManager instance that is used to retrieve teams
     * @param projectManager
     *            the ProjectManager instance that is used to retrieve projects
     * @param logger
     *            used to log information
     * @param activeProjectStatusId
     *            the id of the active project status
     * @throws IllegalArgumentException
     *             if projectRetrieval or resourceManager or phaseManager or teamManager or
     *             projectManager is null, or activeProjectStatusId is negative
     */
    public ProjectServicesImpl(ProjectRetrieval projectRetrieval, ResourceManager resourceManager,
        PhaseManager phaseManager, TeamManager teamManager, ProjectManager projectManager,
        Log logger, long activeProjectStatusId) {
        Util.checkObjNotNull(projectRetrieval, "projectRetrieval", null);
        Util.checkObjNotNull(resourceManager, "resourceManager", null);
        Util.checkObjNotNull(phaseManager, "phaseManager", null);
        Util.checkObjNotNull(teamManager, "teamManager", null);
        Util.checkObjNotNull(projectManager, "projectManager", null);
        Util.checkIDNotNegative(activeProjectStatusId, "activeProjectStatusId", null);

        this.projectRetrieval = projectRetrieval;
        this.resourceManager = resourceManager;
        this.phaseManager = phaseManager;
        this.teamManager = teamManager;
        this.projectManager = projectManager;
        this.logger = logger;
        this.activeProjectStatusId = activeProjectStatusId;

    }

    /**
     * <p>
     * Creates new object by ObjectFactory.
     * </p>
     * @param cm
     *            ConfigManager instance
     * @param objFactory
     *            ObjectFactory instance
     * @param namespace
     *            the namespace of configuration
     * @param propertyName
     *            the property name in configuration
     * @return the new created object
     * @throws IllegalArgumentException
     *             if any property value in configuration is null or empty
     * @throws InvalidClassSpecificationException
     *             if configuration for ObjectFactory is invalid
     * @throws UnknownNamespaceException
     *             if given namespace is unknown by ConfigManager
     */
    private Object createObject(ConfigManager cm, ObjectFactory objFactory, String namespace,
        String propertyName) throws InvalidClassSpecificationException, UnknownNamespaceException {
        // gets the property value by ConfigManager
        String propertyValue = cm.getString(namespace, propertyName);
        // creates a new object using ObjectFactory
        return objFactory.createObject(propertyValue);
    }

    /**
     * <p>
     * Logs messages if necessary.
     * </p>
     * @param level
     *            the log level
     * @param msg
     *            the log message
     */
    private void log(Level level, String msg) {
        if (logger != null) {
            logger.log(level, msg);
        }
    }

    /**
     * <p>
     * Logs debug level message while calling external Topcoder classes.
     * </p>
     * @param msg
     *            the logging message
     */
    private void logDebug(String msg) {
        log(Level.DEBUG, msg);
    }

    /**
     * <p>
     * This method finds all active projects along with all known associated information. Returns
     * empty array if no projects found.
     * </p>
     * @return FullProjectData array with full projects info, or empty array if none found
     * @throws ProjectServicesException
     *             If there is a system error while performing the search
     */
    public FullProjectData[] findActiveProjects() {
        log(Level.INFO, "Enters ProjectServicesImpl#findActiveProjects method.");
        // finds the active projects
        Project[] projects = findActiveProjectsHeaders();
        // assembles the full project datas
        FullProjectData[] fullDatas = assembleFullProjectDatas(projects);

        log(Level.INFO, "Exits ProjectServicesImpl#findActiveProjects method.");
        return fullDatas;
    }

    /**
     * <p>
     * This method finds all active projects. Returns empty array if no projects found.
     * </p>
     * @return Project array with project info, or empty array if none found
     * @throws ProjectServicesException
     *             If there is a system error while performing the search
     */
    public Project[] findActiveProjectsHeaders() {
        log(Level.INFO, "Enters ProjectServicesImpl#findActiveProjectsHeaders method.");

        // represents the active projects
        Project[] projects = null;
        try {
            // find all projects
            logDebug("Starts calling ProjectManager#searchProjects method.");

    	    Filter filter = ProjectFilterUtility
	        .buildStatusIdEqualFilter(activeProjectStatusId);

            for (long categoryId : activeCategoriesList) {
                Filter filterForCategory = ProjectFilterUtility
                .buildCategoryIdEqualFilter(categoryId);
        	    filter = ProjectFilterUtility.buildAndFilter(filterForCategory, filter);
            }
    	
            projects = projectManager.searchProjects(filter);

            logDebug("Finished calling ProjectManager#searchProjects method.");

        } catch (PersistenceException ex) {
            log(Level.ERROR,
                "ProjectServicesException occurred in ProjectServicesImpl#findActiveProjectsHeaders method.");
            throw new ProjectServicesException(
                "PersisteceException occurred when operating ProjectManager.", ex);
        }

        log(Level.INFO, "Exits ProjectServicesImpl#findActiveProjectsHeaders method.");
        return projects;
    }

    /**
     * <p>
     * This method finds all projects along with all known associated information that match the
     * search criteria. Returns empty array if no projects found.
     * </p>
     * @return FullProjectData array with full projects info, or empty array if none found
     * @param filter
     *            The search criteria to filter projects
     * @throws IllegalArgumentException
     *             If filter is null
     * @throws ProjectServicesException
     *             If there is a system error while performing the search
     */
    public FullProjectData[] findFullProjects(Filter filter) {
        log(Level.INFO, "Enters ProjectServicesImpl#findFullProjects method.");
        Util.checkObjNotNull(filter, "filter", logger);

        // filter ProjectHeaders
        Project[] projects = findProjectsHeaders(filter);
        // assembles FullProjectDatas
        FullProjectData[] fullProjects = assembleFullProjectDatas(projects);

        log(Level.INFO, "Exits ProjectServicesImpl#findFullProjects method.");
        return fullProjects;
    }

    /**
     * <p>
     * This method finds all projects that match the search criteria. Returns empty array if no
     * projects found.
     * </p>
     * @return Project array with project info, or empty array if none found
     * @param filter
     *            The search criteria to filter projects
     * @throws IllegalArgumentException
     *             If filter is null
     * @throws ProjectServicesException
     *             If there is a system error while performing the search
     */
    public Project[] findProjectsHeaders(Filter filter) {
        log(Level.INFO, "Enters ProjectServicesImpl#findProjectsHeaders method.");
        Util.checkObjNotNull(filter, "filter", logger);

        Project[] projects = null;
        try {
            // searches projects using given filter
            logDebug("Starts calling ProjectManager#searchProjects method.");
            projects = projectManager.searchProjects(filter);
            logDebug("Finished calling ProjectManager#searchProjects method.");

        } catch (PersistenceException ex) {
            log(Level.ERROR,
                "ProjectServicesException occurred in ProjectServicesImpl#findProjectsHeaders method.");
            throw new ProjectServicesException(
                "PersistenceException occurred when searching projects.", ex);
        }

        log(Level.INFO, "Exits ProjectServicesImpl#findProjectsHeaders method.");
        return projects;
    }

    /**
     * <p>
     * This method retrieves the project along with all known associated information. Returns null
     * if not found.
     * </p>
     * @return the project along with all known associated information
     * @param projectId
     *            the ID of the project to retrieve
     * @throws IllegalArgumentException
     *             if projectId is negative
     * @throws ProjectServicesException
     *             if there is a system error while performing the search
     * @throws TeamPersistenceException
     *             if error occurred when searching teams
     */
    public FullProjectData getFullProjectData(long projectId) {
        log(Level.INFO, "Enters ProjectServicesImpl#getFullProjectData method.");
        Util.checkIDNotNegative(projectId, "projectId", logger);

        FullProjectData fullProjectData = null;
        try {
            // gets the phase project
            logDebug("Starts calling PhaseManager#getPhases method.");
            com.topcoder.project.phases.Project phaseProject = phaseManager.getPhases(projectId);
            logDebug("Finished calling PhaseManager#getPhases method.");
            if (phaseProject == null) {
                log(Level.INFO, "Exits ProjectServicesImpl#getFullProjectData method.");
                return null;
            }

            // creates an instance of FullProjectData with phaseProject
            fullProjectData = new FullProjectData(phaseProject.getStartDate(), phaseProject
                .getWorkdays());
            Phase[] allPhases = phaseProject.getAllPhases();
            for (int i = 0; i < allPhases.length; i++) {
                fullProjectData.addPhase(allPhases[i]);
            }

            // gets the project header
            logDebug("Starts calling ProjectManager#getProject method.");
            Project projectHeader = projectManager.getProject(projectId);
            logDebug("Finished calling ProjectManager#getProject method.");
            // if not found, return null
            if (projectHeader == null) {
                log(Level.INFO, "Exits ProjectServicesImpl#getFullProjectData method.");
                return null;
            }
            // sets the project header
            fullProjectData.setProjectHeader(projectHeader);

            // searches the resources associated with give project
            logDebug("Starts calling ResourceManager#searchResources method.");
            Resource[] resources = resourceManager.searchResources(ResourceFilterBuilder
                .createProjectIdFilter(projectId));
            logDebug("Finished calling ResourceManager#searchResources method.");
            // sets the resources to fullProjectData
            fullProjectData.setResources(resources);

            // searches the teams associated with given project
            logDebug("Starts calling TeamManager#findTeams method.");
            TeamHeader[] teams = teamManager.findTeams(projectId);
            logDebug("Finished calling TeamManager#findTeams method.");
            // sets the teams to fullProjectData
            fullProjectData.setTeams(teams);
            
            String externalProjectIdStr = (String) projectHeader.getProperty(EXTERNAL_REFERENCE_ID);
            if (externalProjectIdStr!=null) {
                long externalProjectId =
                    Long.parseLong(externalProjectIdStr);
                // retrieves the external project with given project id
                logDebug("Starts calling ProjectRetrieval#retrieveProject method.");
                ExternalProject externalProject = projectRetrieval.retrieveProject(externalProjectId);            	
                logDebug("Finished calling ProjectRetrieval#retrieveProject method.");
                // gets the technologies associated with give project and sets them to fullProjectData
                fullProjectData.setTechnologies(externalProject.getTechnologies());
            } else {
            	logDebug("Project " + projectId + " is missing external reference");
            }
        } catch (NumberFormatException ex) {
            log(Level.ERROR,
            "ProjectServicesException occurred in ProjectServicesImpl#getFullProjectData method.");
            throw new ProjectServicesException(
            "PhaseManagementException occurred when retrieving project phases.", ex);
        } catch (PhaseManagementException ex) {
            log(Level.ERROR,
                "ProjectServicesException occurred in ProjectServicesImpl#getFullProjectData method.");
            throw new ProjectServicesException(
                "PhaseManagementException occurred when retrieving project phases.", ex);
        } catch (PersistenceException ex) {
            log(Level.ERROR,
                "ProjectServicesException occurred in ProjectServicesImpl#getFullProjectData method.");
            throw new ProjectServicesException(
                "PersistenceException occurred when retrieving project.", ex);
        } catch (SearchBuilderException ex) {
            log(Level.ERROR,
                "ProjectServicesException occurred in ProjectServicesImpl#getFullProjectData method.");
            throw new ProjectServicesException(
                "SearchBuilderException occurred when searching resources.", ex);
        } catch (ResourcePersistenceException ex) {
            log(Level.ERROR,
                "ProjectServicesException occurred in ProjectServicesImpl#getFullProjectData method.");
            throw new ProjectServicesException(
                "ResourcePersistenceException occurred when searching resources.", ex);
        } catch (RetrievalException ex) {
            log(Level.ERROR,
                "ProjectServicesException occurred in ProjectServicesImpl#getFullProjectData method.");
            throw new ProjectServicesException(
                "RetrievalException occurred when retrieving external project.", ex);
        }

        log(Level.INFO, "Exits ProjectServicesImpl#getFullProjectData method.");
        return fullProjectData;
    }

    /**
     * <p>
     * Helper method to obtains full project information for each of the passed projects. The
     * returned array will be of the same dimension as the passed array, and each
     * <code>Project</code> in projects will correspond to its <code>FullProjectData</code> at
     * the same index in the returned array. Since this is a helper method, no parameter checking is
     * done.
     * </p>
     * @return FullProjectData array with the full project info.
     * @param projects
     *            The projects whose full associated information needs to be retrieved
     * @throws ProjectServicesException
     *             If there is a system error while performing the search
     */
    private FullProjectData[] assembleFullProjectDatas(Project[] projects) {
        log(Level.INFO, "Enters ProjectServicesImpl#assembleFullProjectDatas method.");

        FullProjectData[] fullProjects = new FullProjectData[projects.length];
        for (int i = 0; i < projects.length; i++) {
            fullProjects[i] = getFullProjectData(projects[i].getId());
        }

        log(Level.INFO, "Exits ProjectServicesImpl#assembleFullProjectDatas method.");
        return fullProjects;
    }
}
