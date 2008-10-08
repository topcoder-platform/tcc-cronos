/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices.beans;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.jws.WebMethod;
import javax.jws.WebService;

import com.topcoder.clients.manager.ManagerException;
import com.topcoder.clients.manager.ProjectManager;
import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.Project;
import com.topcoder.clients.model.ProjectStatus;
import com.topcoder.clients.webservices.ClientProjectManagementServicesException;
import com.topcoder.clients.webservices.ProjectService;
import com.topcoder.clients.webservices.ProjectServiceException;
import com.topcoder.clients.webservices.ProjectServiceLocal;
import com.topcoder.clients.webservices.ProjectServiceRemote;
import com.topcoder.clients.webservices.usermapping.UserMappingRetriever;
import com.topcoder.util.errorhandling.ExceptionUtils;
import com.topcoder.util.log.Log;

/**
 * <p>
 *  This class is a Stateless Session Bean endpoint realization of ProjectService web service interface.
 *  This class has a default no-arg constructor. It uses ProjectManager from Client Project Management
 *  component by delegating to namesake methods to perform the operations. This class implements
 *  the methods available for the ProjectService web service: create, update and delete project,
 *  set project status for project.
 * </p>
 *
 * <p>
 *  <strong>Thread-safety:</strong>
 *  All the available web service operations performs logging using the Logging Wrapper component.
 * </p>
 *
 * <p>
 *  <strong>Authentication and Authorization</strong>
 *  All the available web service operations performs the authentication and authorization of
 *  the users that performs the operations using JBoss Login Module
 * </p>
 *
 * <p>
 *  Below is example configuration of ejb-jar.xml for ProjectServiceBean:
 *  <pre>
 *  &lt;?xml version="1.0" encoding="UTF-8"?&gt;
 *  &lt;ejb-jar version="3.0" xmlns="http://java.sun.com/xml/ns/javaee"
 *  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
 *  xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/ejb-jar_3_0.xsd"&gt;
 *  &lt;enterprise-beans&gt;
 *      &lt;session&gt;
 *          &lt;ejb-name&gt;ProjectServiceBean&lt;/ejb-name&gt;
 *          &lt;remote&gt;com.topcoder.clients.webservices.ProjectServiceRemote&lt;/remote&gt;
 *          &lt;local&gt;com.topcoder.clients.webservices.ProjectServiceLocal&lt;/local&gt;
 *          &lt;ejb-class&gt;com.topcoder.clients.webservices.beans.ProjectServiceBean&lt;/ejb-class&gt;
 *          &lt;session-type&gt;Stateless&lt;/session-type&gt;
 *          &lt;transaction-type&gt;Container&lt;/transaction-type&gt;
 *          &lt;env-entry&gt;
 *              &lt;env-entry-name&gt;projectManagerFile&lt;/env-entry-name&gt;
 *              &lt;env-entry-type&gt;java.lang.String&lt;/env-entry-type&gt;
 *              &lt;env-entry-value&gt;
 *                  /Applications/Developer_Suite/Service/jboss-4.2.3.GA/server/default/conf_tcs/
 *                  client_project_management_services/bean.properties
 *              &lt;/env-entry-value&gt;
 *          &lt;/env-entry&gt;
 *          &lt;env-entry&gt;
 *              &lt;env-entry-name&gt;projectManagerNamespace&lt;/env-entry-name&gt;
 *              &lt;env-entry-type&gt;java.lang.String&lt;/env-entry-type&gt;
 *              &lt;env-entry-value&gt;projectManagerNamespace&lt;/env-entry-value&gt;
 *          &lt;/env-entry&gt;
 *          &lt;env-entry&gt;
 *              &lt;env-entry-name&gt;userMappingRetrieverFile&lt;/env-entry-name&gt;
 *              &lt;env-entry-type&gt;java.lang.String&lt;/env-entry-type&gt;
 *              &lt;env-entry-value&gt;
 *                  /Applications/Developer_Suite/Service/jboss-4.2.3.GA/server/default/
 *                  conf_tcs/client_project_management_services/bean.properties
 *              &lt;/env-entry-value&gt;
 *          &lt;/env-entry&gt;
 *          &lt;env-entry&gt;
 *              &lt;env-entry-name&gt;userMappingRetrieverNamespace&lt;/env-entry-name&gt;
 *              &lt;env-entry-type&gt;java.lang.String&lt;/env-entry-type&gt;
 *              &lt;env-entry-value&gt;userMappingRetrieverNamespace&lt;/env-entry-value&gt;
 *          &lt;/env-entry&gt;
 *          &lt;env-entry&gt;
 *              &lt;env-entry-name&gt;logName&lt;/env-entry-name&gt;
 *              &lt;env-entry-type&gt;java.lang.String&lt;/env-entry-type&gt;
 *              &lt;env-entry-value&gt;System.out&lt;/env-entry-value&gt;
 *          &lt;/env-entry&gt;
 *          &lt;env-entry&gt;
 *              &lt;env-entry-name&gt;administratorRole&lt;/env-entry-name&gt;
 *              &lt;env-entry-type&gt;java.lang.String&lt;/env-entry-type&gt;
 *              &lt;env-entry-value&gt;Admin&lt;/env-entry-value&gt;
 *          &lt;/env-entry&gt;
 *          &lt;env-entry&gt;
 *              &lt;env-entry-name&gt;clientAndProjectUserRole&lt;/env-entry-name&gt;
 *              &lt;env-entry-type&gt;java.lang.String&lt;/env-entry-type&gt;
 *              &lt;env-entry-value&gt;developer&lt;/env-entry-value&gt;
 *          &lt;/env-entry&gt;
 *          &lt;persistence-context-ref&gt;
 *              &lt;persistence-context-ref-name&gt;persistenceUnit&lt;/persistence-context-ref-name&gt;
 *              &lt;persistence-unit-name&gt;persistenceUnit&lt;/persistence-unit-name&gt;
 *              &lt;persistence-context-type&gt;Transaction&lt;/persistence-context-type&gt;
 *          &lt;/persistence-context-ref&gt;
 *      &lt;/session&gt;
 *  &lt;/enterprise-beans&gt;
 *
 *  &lt;!-- define security roles permissions --&gt;
 *  &lt;assembly-descriptor&gt;
 *      &lt;security-role&gt;
 *          &lt;role-name&gt;Admin&lt;/role-name&gt;
 *      &lt;/security-role&gt;
 *      &lt;method-permission&gt;
 *          &lt;role-name&gt;Admin&lt;/role-name&gt;
 *          &lt;method&gt;
 *              &lt;ejb-name&gt;ProjectServiceBean&lt;/ejb-name&gt;
 *              &lt;method-name&gt;*&lt;/method-name&gt;
 *          &lt;/method&gt;
 *      &lt;/method-permission&gt;
 *  &lt;/assembly-descriptor&gt;
 *  &lt;/ejb-jar&gt;
 *  </pre>
 *  <pre>
 *      This service bean can be used like below example:
 *
 *      Properties env = ....;
 *      env.setProperty(Context.SECURITY_PRINCIPAL, username);
 *      env.setProperty(Context.SECURITY_CREDENTIALS, password);
 *      InitialContext ctx = new InitialContext(env);
 *
 *      // Assume, this bean is deployed in EAR package.
 *      ProjectService service = (ProjectService) ctx.lookup(EAR_NAME + "/" + ProjectServiceRemote.JNDI_NAME);
 *
 *      // Create Client
 *      service.createProject(project, client);
 *
 *      // Update Client
 *      service.updateProject(project);
 *
 *      // Delete Client
 *      service.deleteProject(project);
 *  </pre>
 * </p>
 *
 * <p>
 *  <strong>Thread-safety:</strong>
 *  This class is technically mutable since the configuration properties
 *  and manager are set after construction, but the container will not
 *  initialize the properties more than once for the session beans and the EJB3
 *  container ensure the thread safety in this case.
 * </p>
 *
 * @author  Mafy, CaDenza
 * @version 1.0
 */
@WebService(name = "ProjectService",
            serviceName = "ProjectService",
            targetNamespace = "com.topcoder.clients.webservices.ProjectService",
            endpointInterface = "com.topcoder.clients.webservices.ProjectService")
@Stateless(name = ProjectService.BEAN_NAME)
@Local(ProjectServiceLocal.class)
@Remote(ProjectServiceRemote.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@DeclareRoles({ Roles.USER, Roles.ADMIN })
@RolesAllowed({ Roles.USER, Roles.ADMIN })
public class ProjectServiceBean implements ProjectService, ProjectServiceLocal, ProjectServiceRemote {

    /**
     * Represents token name for user mapping retriever.
     */
    private static final String USER_MAPPING_TOKEN = "user_mapping_retriever_token";

    /**
     * Represents token name for project manager.
     */
    private static final String PROJECT_MANAGER_TOKEN = "project_manager_token";

    /**
     *  Represents the project manager instance.
     *  Cannot be null after this bean is constructed.
     *  REQUIRED.
     */
    private ProjectManager projectManager;

    /**
     * Represents the user mapping retriever instance.
     * Cannot be null after this bean is constructed.
     * REQUIRED.
     */
    private UserMappingRetriever userMappingRetriever;

    /**
     *  Represents the project manager file.
     *  Cannot be null or empty String after injected when this bean is instantiated.
     *  REQUIRED.
     */
    @Resource(name = "projectManagerFile")
    private String projectManagerFile;

    /**
     *  Represents the project manager namespace.
     *  Cannot be null or empty String after injected when this bean is instantiated.
     *  REQUIRED.
     */
    @Resource(name = "projectManagerNamespace")
    private String projectManagerNamespace;

    /**
     *  Represents the user mapping retriever file.
     *  Cannot be null or empty String after injected when this bean is instantiated.
     *  REQUIRED.
     */
    @Resource(name = "userMappingRetrieverFile")
    private String userMappingRetrieverFile;

    /**
     *  Represents the user mapping retriever namespace.
     *  Cannot be null or empty String after injected when this bean is instantiated.
     *  REQUIRED.
     */
    @Resource(name = "userMappingRetrieverNamespace")
    private String userMappingRetrieverNamespace;

    /**
     *  Represents the SessionContext injected by the EJB container automatically
     *  and it is used to retrieve the Principal needed for user authentication.
     *  Cannot be null after injected when this bean is instantiated.
     */
    @Resource
    private SessionContext sessionContext;

    /**
     *  Represents the administrator role used to check if the user is administrator.
     *  Can be null or empty String.
     *  OPTIONAL.
     */
    @Resource(name = "administratorRole")
    private String administratorRole;

    /**
     * Represents the client and project user role used
     * to check if the user is a  client and project user.
     * Can be null or empty String.
     * OPTIONAL.
     */
    @Resource(name = "clientAndProjectUserRole")
    private String clientAndProjectUserRole;

    /**
     *  Represents the log name used to initialize the log.
     *  Can be null or empty String. OPTIONAL.
     */
    @Resource(name = "logName")
    private String logName;

    /**
     * This field represents the 'log' instance of the ProjectServiceBean.
     * It will be used to perform the logging operations.
     * Contains an instance of Log interface.
     * It can be enabled (not null) or disabled (null).
     * Can be any value. OPTIONAL.
     */
    private Log log;

    /**
     *  This field represents the 'verboseLogging' property of the ProjectServiceBean.
     *  The control flag defining whether the detailed logging actions should
     *  be performed (true value), or standard logging (only exceptions)
     *  should be supported (false value).
     *  There are no restrictions at this moment.
     *  It can take any value.
     *  OPTIONAL.
     */
    private boolean verboseLogging = false;

    /**
     * Default no-arg constructor.
     */
    public ProjectServiceBean() {
        // do nothing.
    }

    /**
     *  Initialize the projectManager, mapping retreiver and log and makes
     *  sure the initialization of this session bean is ok.
     *  This method is called after this bean is constructed by the EJB container.
     *
     * @throws ProjectServiceBeanConfigurationException
     *      if the required String fields are null or empty,
     *      if needed configurations are missing or invalid (invalid means null here),
     *      if Object Factory cannot initialize the needed dependencies.
     *      Also this exception is thrown if both ad if both administratorRole
     *      and clientAndProjectUserRole are null or empty strings.
     */
    @PostConstruct
    protected void initialize() {
        // Create logger.
        log = BeanUtils.createLogger(logName);
        try {
            // Create ProjectManager instance.
            projectManager = BeanUtils.createManager(ProjectManager.class,
                    projectManagerFile, projectManagerNamespace, PROJECT_MANAGER_TOKEN);
            // Create UserMappingRetriever instance.
            userMappingRetriever = BeanUtils.createManager(UserMappingRetriever.class,
                    userMappingRetrieverFile, userMappingRetrieverNamespace, USER_MAPPING_TOKEN);
        } catch (ServiceBeanConfigurationException e) {
            throw new ProjectServiceBeanConfigurationException("Fail to "
                    + "initialize 'projectManager' or 'userMappingRetriever'.", e.getCause());
        }
    }

    /**
     * Performs the creation of a project with the given client in the persistence.
     *
     * @param project
     *      the project that should be created with the given client.
     *      Should not be null.
     * @param client
     *      the client that should be set for the project.
     *      Should not be null.
     * @return the project for that was created with the given client in the persistence.
     *
     * @throws IllegalArgumentException
     *      if the project or client is null.
     * @throws IllegalStateException
     *      if the projectManager or userMappingRetriever are not set (or it is set to a null value).
     * @throws AuthorizationFailedException
     *      if the user is not authorized to perform the operation.
     * @throws ProjectServiceException
     *      if any error occurs while performing this operation.
     */
    @WebMethod
    public Project createProject(Project project, Client client)
        throws AuthorizationFailedException, ProjectServiceException {
        String method = this.getClass().getName() + "#createProject(Project project, Client client)";
        Project retProject = null;
        Date enterTime = null;

        try {
            // Log entrance.
            enterTime = BeanUtils.logEnter(log, verboseLogging, method, new Object[]{project, client});

            // Perform authentication for current user.
            BeanUtils.validateCaller(method, sessionContext, administratorRole, clientAndProjectUserRole,
                    project, log, false, userMappingRetriever);

            // Perform create operation for Project.
            ExceptionUtils.checkNull(client, null, null, "Argument [ClientProject] cannot be null.");
            retProject = BeanUtils.createOperation(BeanUtils.getCurrentUser(sessionContext),
                    project, client, null, projectManager, null);
        } catch (ClientProjectManagementServicesException e) {
            throw BeanUtils.logException(log, new ProjectServiceException("Fail create project.", e.getCause()));
        } catch (AuthorizationFailedException e) {
            throw BeanUtils.logException(log, e);
        } catch (IllegalArgumentException e) {
            throw BeanUtils.logException(log, e);
        } catch (IllegalStateException e) {
            throw BeanUtils.logException(log, e);
        } finally {
            // Log exit method.
            BeanUtils.logExit(log, verboseLogging, method, enterTime, retProject);
        }

        return retProject;
    }

    /**
     *  Performs the update of the given project in the persistence.
     *
     * @param  project
     *          the project that should be updated. Should not be null.
     * @return the project updated in the persistence.
     *
     * @throws IllegalArgumentException
     *          if the project is null.
     * @throws IllegalStateException
     *          if the projectManager or userMappingRetriever are
     *          not set (or it is set to a null value).
     * @throws AuthorizationFailedException
     *          if the user is not authorized to perform the operation.
     * @throws ProjectServiceException
     *          if any error occurs while performing this operation.
     */
    @WebMethod
    public Project updateProject(Project project)
        throws AuthorizationFailedException, ProjectServiceException {
        String method = this.getClass().getName() + "#updateProject(Project project)";
        Project retProject = null;
        Date enterTime = null;

        try {
            // Log entrance.
            enterTime = BeanUtils.logEnter(log, verboseLogging, method, new Object[]{project});

            // Perform authentication for current user.
            BeanUtils.validateCaller(method, sessionContext, administratorRole, clientAndProjectUserRole,
                    project, log, true, userMappingRetriever);

            // Perform update operation for Project.
            retProject = BeanUtils.updateOperation(BeanUtils.getCurrentUser(sessionContext),
                    project, null, projectManager, null);
        } catch (ClientProjectManagementServicesException e) {
            throw BeanUtils.logException(log, new ProjectServiceException("Fail update project.", e.getCause()));
        } catch (AuthorizationFailedException e) {
            throw BeanUtils.logException(log, e);
        } catch (IllegalArgumentException e) {
            throw BeanUtils.logException(log, e);
        } catch (IllegalStateException e) {
            throw BeanUtils.logException(log, e);
        } finally {
            // Log exit method.
            BeanUtils.logExit(log, verboseLogging, method, enterTime, retProject);
        }

        return retProject;
    }

    /**
     * Performs the deletion of the given project from the persistence.
     *
     * @param  project
     *      the project that should be deleted. Should not be null.
     * @return the project deleted from the persistence.
     *
     * @throws IllegalArgumentException
     *      if the project is null.
     * @throws IllegalStateException
     *      if the projectManager or userMappingRetriever
     *      are not set (or it is set to a null value).
     * @throws AuthorizationFailedException
     *      if the user is not authorized to perform the operation.
     * @throws ProjectServiceException
     *      if any error occurs while performing this operation.
     */
    @WebMethod
    public Project deleteProject(Project project)
        throws AuthorizationFailedException, ProjectServiceException {
        String method = this.getClass().getName() + "#deleteProject(Project project)";
        Project retProject = null;
        Date enterTime = null;

        try {
            // Log entrance.
            enterTime = BeanUtils.logEnter(log, verboseLogging, method, new Object[]{project});

            // Perform authentication for current user.
            BeanUtils.validateCaller(method, sessionContext, administratorRole, clientAndProjectUserRole,
                    project, log, true, userMappingRetriever);

            // Perform delete operation for Project.
            retProject = BeanUtils.deleteOperation(BeanUtils.getCurrentUser(sessionContext),
                    project, null, projectManager, null);
        } catch (ClientProjectManagementServicesException e) {
            throw BeanUtils.logException(log, new ProjectServiceException("Fail delete project.", e.getCause()));
        } catch (AuthorizationFailedException e) {
            throw BeanUtils.logException(log, e);
        } catch (IllegalArgumentException e) {
            throw BeanUtils.logException(log, e);
        } catch (IllegalStateException e) {
            throw BeanUtils.logException(log, e);
        } finally {
            // Log exit method.
            BeanUtils.logExit(log, verboseLogging, method, enterTime, retProject);
        }

        return retProject;
    }

    /**
     * Performs the set of the project status for the given project in the persistence.
     *
     * @param project
     *      the project for that should be set the project status.
     *      Should not be null.
     * @param status
     *      the project status that should be set for the project.
     *      Should not be null.
     * @return the project for that the project status was set in the persistence.
     *
     * @throws IllegalArgumentException
     *      if the project or project status is null.
     * @throws IllegalStateException
     *      if the projectManager or userMappingRetriever are not set (or it is set to a null value).
     * @throws AuthorizationFailedException
     *      if the user is not authorized to perform the operation.
     * @throws ProjectServiceException
     *      if any error occurs while performing this operation.
     */
    @WebMethod
    public Project setProjectStatus(Project project, ProjectStatus status)
        throws AuthorizationFailedException, ProjectServiceException {

        String method = this.getClass().getName() + "#deleteProject(Project project)";
        Project retProject = null;
        Date enterTime = null;

        try {
            // Log entrance.
            enterTime = BeanUtils.logEnter(log, verboseLogging, method, new Object[]{project});

            // Check required property.
            BeanUtils.checkState("ProjectManager", projectManager);
            ExceptionUtils.checkNull(project, null, null, "Argument [project] cannot be null.");
            ExceptionUtils.checkNull(status, null, null, "Argument [status] cannot be null");

            // Perform authentication for current user.
            BeanUtils.validateCaller(method, sessionContext, administratorRole, clientAndProjectUserRole,
                    project, log, true, userMappingRetriever);

            try {
                // set status for project.
                retProject = projectManager.setProjectStatus(project, status);
            } catch (IllegalArgumentException e) {
                // any illegal argument from manager must be caught here.
                throw BeanUtils.logException(log,
                        new ProjectServiceException("Illegal argument from Project Manager.", e));
            }
        } catch (ManagerException e) {
            throw BeanUtils.logException(log, new ProjectServiceException("Fail setup project status", e.getCause()));
        } catch (AuthorizationFailedException e) {
            throw BeanUtils.logException(log, e);
        } catch (IllegalArgumentException e) {
            throw BeanUtils.logException(log, e);
        } catch (IllegalStateException e) {
            throw BeanUtils.logException(log, e);
        } finally {
            // Log exit method.
            BeanUtils.logExit(log, verboseLogging, method, enterTime, retProject);
        }

        return retProject;
    }

    /**
     * Getter for 'verboseLogging' property.
     *
     * @return the value of the 'verboseLogging' property.
     *      It can be any value.
     */
    public boolean isVerboseLogging() {
        return verboseLogging;
    }

    /**
     * Setter for 'verboseLogging' property.
     *
     * @param verboseLogging
     *      the new value of the control flag defining whether
     *      the detailed logging actions should be performed (true value),
     *      or standard logging (only exceptions) should be supported (false value).
     */
    public void setVerboseLogging(boolean verboseLogging) {
        this.verboseLogging = verboseLogging;
    }

    /**
     * Getter for name of 'log' property.
     *
     * @return the value of the 'log' property.
     * It can be any value.
     */
    public Log getLog() {
        return log;
    }

    /**
     * Setter for 'log' property.
     *
     * @param log the new log to set for log property.
     *      It can be any value.
     */
    public void setLog(Log log) {
        this.log = log;
    }
}