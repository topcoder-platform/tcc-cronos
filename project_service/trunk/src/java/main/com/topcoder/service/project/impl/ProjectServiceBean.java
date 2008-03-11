/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.project.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.naming.InitialContext;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;

import com.topcoder.security.auth.module.UserProfilePrincipal;
import com.topcoder.service.project.AuthorizationFailedFault;
import com.topcoder.service.project.ConfigurationException;
import com.topcoder.service.project.IllegalArgumentFault;
import com.topcoder.service.project.PersistenceException;
import com.topcoder.service.project.PersistenceFault;
import com.topcoder.service.project.Project;
import com.topcoder.service.project.ProjectData;
import com.topcoder.service.project.ProjectHasCompetitionsFault;
import com.topcoder.service.project.ProjectNotFoundException;
import com.topcoder.service.project.ProjectNotFoundFault;
import com.topcoder.service.project.ProjectPersistence;
import com.topcoder.service.project.ProjectServiceLocal;
import com.topcoder.service.project.ProjectServiceRemote;
import com.topcoder.service.project.UserNotFoundFault;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;

/**
 * <p>
 * This class implements the <code>{@link ProjectServiceLocal}</code> as well as the
 * <code>{@link ProjectServiceRemote}</code> interfaces. Thus, by extension it also implements the
 * <code>{@link com.topcoder.service.project.ProjectService}</code> interface. While EJB 3.0 does not allow the same
 * business interface to be both local and remote, a single class can implement different local and remote business
 * interfaces. This class, when hosted from an EJB container, allows clients to access the project service (as a web
 * service/local bean/remote bean) It performs additional authorization tasks where necessary and delegates persistence
 * to a pluggable instance of ProjectPersistence.
 * </p>
 * <p>
 * We store a member persistence object as well as a member log. We also store certain string properties which allow
 * easy configuration of the usage of the <code>{@link UserProfilePrincipal}</code>. We use the injected resource
 * <code>{@link SessionContext}</code> to fetch the calling principal when required.
 * </p>
 * <p>
 * <b>ejb-jar.xml</b> configuration sample:<br>
 *
 * <pre>
 *  &lt;ejb-jar&gt;
 *  &lt;enterprise-beans&gt;
 *  &lt;session&gt;
 *  &lt;ejb-name&gt;ProjectServiceBean&lt;/ejb-name&gt;
 *  &lt;home&gt;com.topcoder.service.project.ProjectServiceLocal&lt;/home&gt;
 *  &lt;remote&gt;com.topcoder.service.project.ProjectServiceRemote&lt;/remote&gt;
 *  &lt;ejb-class&gt;com.topcoder.service.project.impl.ProjectServiceBean&lt;/ejb-class&gt;
 *  &lt;session-type&gt;Stateless&lt;/session-type&gt;
 *  &lt;transaction-type&gt;Bean&lt;/transaction-type&gt;
 *  &lt;env-entry&gt;
 *  &lt;description&gt;Class that implements ProjectPersistence&lt;/description&gt;
 *  &lt;env-entry-name&gt;project_persistence_class&lt;/env-entry-name&gt;
 *  &lt;env-entry-type&gt;java.lang.String&lt;/env-entry-type&gt;
 *  &lt;env-entry-value&gt;com.topcoder.service.project.persistence.JPAProjectPersistence&lt;/env-entry-value&gt;
 *  &lt;/env-entry&gt;
 *  &lt;env-entry&gt;
 *  &lt;description&gt;The name of the log to use&lt;/description&gt;
 *  &lt;env-entry-name&gt;log_name&lt;/env-entry-name&gt;
 *  &lt;env-entry-type&gt;java.lang.String&lt;/env-entry-type&gt;
 *  &lt;env-entry-value&gt;TopCoderLog&lt;/env-entry-value&gt;
 *  &lt;/env-entry&gt;
 *  &lt;env-entry&gt;
 *  &lt;description&gt;The key under which the roles will be found&lt;/description&gt;
 *  &lt;env-entry-name&gt;roles_key&lt;/env-entry-name&gt;
 *  &lt;env-entry-type&gt;java.lang.String&lt;/env-entry-type&gt;
 *  &lt;env-entry-value&gt;roles&lt;/env-entry-value&gt;
 *  &lt;/env-entry&gt;
 *  &lt;env-entry&gt;
 *  &lt;description&gt;The name of the administrator role&lt;/description&gt;
 *  &lt;env-entry-name&gt;administrator_role&lt;/env-entry-name&gt;
 *  &lt;env-entry-type&gt;java.lang.String&lt;/env-entry-type&gt;
 *  &lt;env-entry-value&gt;Administrator&lt;/env-entry-value&gt;
 *  &lt;/env-entry&gt;
 *  &lt;env-entry&gt;
 *  &lt;description&gt;The name of the user role&lt;/description&gt;
 *  &lt;env-entry-name&gt;user_role&lt;/env-entry-name&gt;
 *  &lt;env-entry-type&gt;java.lang.String&lt;/env-entry-type&gt;
 *  &lt;env-entry-value&gt;User&lt;/env-entry-value&gt;
 *  &lt;/env-entry&gt;
 *  &lt;!-- For the JPA persistence --&gt;
 *  &lt;env-entry&gt;
 *  &lt;description&gt;The persistence unit name&lt;/description&gt;
 *  &lt;env-entry-name&gt;persistence_unit_name&lt;/env-entry-name&gt;
 *  &lt;env-entry-type&gt;java.lang.String&lt;/env-entry-type&gt;
 *  &lt;env-entry-value&gt;HibernateProjectPersistence&lt;/env-entry-value&gt;
 *  &lt;/env-entry&gt;
 *  &lt;/session&gt;
 *  &lt;/enterprise-beans&gt;
 *  &lt;/ejb-jar&gt;
 * </pre>
 *
 * </p>
 * <p>
 * <b>Thread Safety</b>: This class is thread safe as it is immutable except for the session context. The
 * sessionContext resource, despite being mutable, does not compromise thread safety as it is injected by the EJB
 * container and will not change during calls to the bean's methods.
 * </p>
 *
 * @author humblefool, FireIce
 * @version 1.0
 */
@Stateless
@WebService
public class ProjectServiceBean implements ProjectServiceLocal, ProjectServiceRemote {
    /**
     * <p>
     * Represents the persistence object used to persist <code>Project</code> entities.
     * </p>
     * <p>
     * It is frozen as it will not be changed after being set by the constructor. It will not be null. It is retrieved
     * by the {@link #getProjectPersistence()} method. It is used by all the methods of this class to perform
     * persistence tasks.
     * </p>
     */
    private final ProjectPersistence projectPersistence;

    /**
     * <p>
     * Represents the log used to log useful information.
     * </p>
     * <p>
     * It is frozen as it will not be changed after being set by the constructor. It may be null, indicating that no
     * logging is to be performed. It is retrieved by the {@link #getLog()} method. It is used by all the methods of
     * this class to perform logging. The details of the logging may be seen in section 1.3.4 of the component
     * specification.
     * </p>
     */
    private final Log log;

    /**
     * <p>
     * Represents the key used to fetch roles from the profile attributes of user profile principal.
     * </p>
     * <p>
     * It is frozen as it will not be changed after being set by the constructor. It will not be null/empty. It is
     * retrieved by the {@link #getRolesKey()} method. It is used by the {@link #getAllProjects()} method, whose logic
     * depends on what roles the current user satisfies.
     * </p>
     */
    private final String rolesKey;

    /**
     * <p>
     * Represents the name of the administrator role in the roles attribute of the profile of the user profile
     * persistence.
     * </p>
     * <p>
     * It is frozen as it will not be changed after being set by the constructor. It will not be null/empty. It is
     * retrieved by the {@link #getAdministratorRole()} method. It is used by those methods of this class which need to
     * check the roles satisfied by a user as part of their logic.
     * </p>
     */
    private final String administratorRole;

    /**
     * Represents the name of the user role in the roles attribute of the profile of the user profile persistence. It is
     * frozen as it will not be changed after being set by the constructor. It will not be null/empty. It is retrieved
     * by the {@link #getUserRole()} method. It is used by the {@link #getAllProjects()} method of this class whose
     * logic depends on whether the User role is satisfied.
     */
    private final String userRole;

    /**
     * <p>
     * Represents the session context of this bean.
     * </p>
     * <p>
     * It is a resource injected by the EJB container and will not be null while client calls are being executed. It is
     * not frozen since it will be set by the EJB container when required. However, it does not compromise thread safety
     * since the context will not be set while the bean is executing client calls.
     * </p>
     */
    @Resource
    private SessionContext sessionContext;

    /**
     * Constructs a <code>ProjectServiceBean</code> instance.
     *
     * @throws ConfigurationException
     *             if required properties are missing or there is an exception during reflection.
     */
    public ProjectServiceBean() throws ConfigurationException {
        InitialContext ctx;
        try {
            ctx = new InitialContext();
        } catch (NamingException e) {
            throw new ConfigurationException("A naming problem occurs.", e);
        }

        String projectPersistenceClass = getConfigString(ctx, "project_persistence_class", true);

        try {
            projectPersistence = (ProjectPersistence) Class.forName(projectPersistenceClass).newInstance();
        } catch (InstantiationException e) {
            throw new ConfigurationException("Fail to create persistence instance.", e);
        } catch (IllegalAccessException e) {
            throw new ConfigurationException("Illegal access problem.", e);
        } catch (ClassNotFoundException e) {
            throw new ConfigurationException("Unable to find the class.", e);
        } catch (ClassCastException e) {
            throw new ConfigurationException("The instance is not ProjectPersistence type.", e);
        }

        String logName = getConfigString(ctx, "log_name", false);

        if (logName != null) {
            log = LogManager.getLog(logName);
        } else {
            log = null;
        }

        rolesKey = getConfigString(ctx, "roles_key", true);
        administratorRole = getConfigString(ctx, "administrator_role", true);
        userRole = getConfigString(ctx, "user_role", true);
    }

    /**
     * <p>
     * Creates a project with the given project data.
     * </p>
     * <p>
     * Notes, any user can create project, the project will associate with him/her.
     * </p>
     *
     * @param projectData
     *            The project data. Must not be null. The name must also not be null/empty. The ProjectId , if any, is
     *            ignored.
     * @return The project as it was created, with the projectId set.
     * @throws PersistenceFault
     *             If a generic persistence error.
     * @throws IllegalArgumentFault
     *             If the arg given was illegal.
     */
    @RolesAllowed("User")
    public ProjectData createProject(ProjectData projectData) throws PersistenceFault, IllegalArgumentFault {
        logEnter("createProject(ProjectData)");
        logParameters("project data: {0}", formatProjectData(projectData));

        try {
            checkProjectData(projectData, true);

            Project project = new Project();
            project.setName(projectData.getName());
            project.setDescription(projectData.getDescription());

            // Obtain the user profile principal
            UserProfilePrincipal principal = (UserProfilePrincipal) sessionContext.getCallerPrincipal();

            // Obtain the user ID
            long userId = principal.getUserId();
            project.setUserId(userId);

            project.setCreateDate(new Date());

            try {
                project = projectPersistence.createProject(project);
            } catch (PersistenceException e) {
                logException(e);
                throw new PersistenceFault(e.getMessage());
            }

            ProjectData newProjectData = createProjectData(project);
            logReturn(formatProjectData(newProjectData));
            return newProjectData;
        } finally {
            logExit("createProject(ProjectData)");
        }
    }

    /**
     * <p>
     * Gets the project data for the project with the given Id.
     * </p>
     * <p>
     * Notes, only associated user can retrieve the specified project, administrator can retrieve any projects.
     * </p>
     *
     * @param projectId
     *            The ID of the project.
     * @return The project data for the project with the given Id.
     * @throws PersistenceFault
     *             If a generic persistence error.
     * @throws ProjectNotFoundFault
     *             If no project with the given ID exists.
     * @throws AuthorizationFailedFault
     *             If the calling principal is not authorized to retrieve the project.
     */
    @RolesAllowed("User")
    public ProjectData getProject(long projectId) throws PersistenceFault, ProjectNotFoundFault,
        AuthorizationFailedFault {
        logEnter("getProject(long)");
        logParameters("project id: {0}", projectId);
        try {
            ProjectData projectData = createProjectData(getProjectById(projectId));
            logReturn(formatProjectData(projectData));
            return projectData;
        } finally {
            logExit("getProject(long)");
        }
    }

    /**
     * <p>
     * Gets the project data for all projects of the given user.
     * </p>
     * <p>
     * Notes, only administrator can do this.
     * </p>
     *
     * @param userId
     *            The ID of the user whose projects are to be retrieved.
     * @return The project data for all projects of the given user.
     * @throws PersistenceFault
     *             If a generic persistence error.
     * @throws AuthorizationFailedFault
     *             If the calling principal is not authorized to retrieve the projects.
     * @throws UserNotFoundFault
     *             If there are no projects linked to the given user.
     */
    @SuppressWarnings("unchecked")
    @RolesAllowed("Administrator")
    public List<ProjectData> getProjectsForUser(long userId) throws PersistenceFault, AuthorizationFailedFault,
        UserNotFoundFault {
        logEnter("getProjectsForUser(long)");
        logParameters("user id: {0}", userId);

        try {
            // Obtain the user profile principal
            UserProfilePrincipal principal = (UserProfilePrincipal) sessionContext.getCallerPrincipal();

            // Obtain the roles from the profile
            Map<Object, String> roles = (Map<Object, String>) principal.getProfile().getAttribute(rolesKey).getValue();

            // Check if the administrator role is satisfied
            if (!roles.containsValue(administratorRole)) {
                throw logException(new AuthorizationFailedFault("The user is not administrator."));
            }

            List<Project> projects = projectPersistence.getProjectsForUser(userId);

            if (projects.isEmpty()) {
                throw logException(new UserNotFoundFault("No projects present with the given user " + userId));
            }

            List<ProjectData> projectDatas = new ArrayList<ProjectData>();

            for (Project project : projects) {
                projectDatas.add(createProjectData(project));
            }

            logReturn(formatProjectDatas(projectDatas));
            return projectDatas;
        } catch (PersistenceException e) {
            logException(e);
            throw new PersistenceFault(e.getMessage());
        } finally {
            logExit("getProjectsForUser(long)");
        }
    }

    /**
     * <p>
     * Gets the project data for all projects viewable from the calling principal.
     * </p>
     * <p>
     * Notes, for user, it will return all the projects associated with him,
     * administrator will return all the projects.
     * </p>
     *
     * @return The project data for all projects viewable from the calling principal. The returned collection will not
     *         be null or contain nulls. Possibly empty.
     * @throws PersistenceFault
     *             If a generic persistence error.
     */
    @SuppressWarnings("unchecked")
    @RolesAllowed("User")
    public List<ProjectData> getAllProjects() throws PersistenceFault {
        logEnter("getAllProjects()");

        try {
            // Obtain the user profile principal
            UserProfilePrincipal principal = (UserProfilePrincipal) sessionContext.getCallerPrincipal();

            // Obtain the roles from the profile
            Map<Object, String> roles = (Map<Object, String>) principal.getProfile().getAttribute(rolesKey).getValue();

            // Check if the administrator role is satisfied

            List<Project> projects = null;
            if (!roles.containsValue(administratorRole)) {
                // not administrator
                projects = projectPersistence.getProjectsForUser(principal.getUserId());
            } else {
                projects = projectPersistence.getAllProjects();
            }

            List<ProjectData> projectDatas = new ArrayList<ProjectData>();

            for (Project project : projects) {
                projectDatas.add(createProjectData(project));
            }

            logReturn(formatProjectDatas(projectDatas));
            return projectDatas;
        } catch (PersistenceException e) {
            logException(e);
            throw new PersistenceFault(e.getMessage());
        } finally {
            logExit("getAllProjects()");
        }
    }

    /**
     * <p>
     * Updates the project data for the given project.
     * </p>
     * <p>
     * Notes, only project-associated user can update that project, but administrator can update any project.
     * </p>
     *
     * @param projectData
     *            The project data. Must not be null. The name,description must also not be null/empty. The ProjectId
     *            must be non-null.
     * @throws PersistenceFault
     *             If a generic persistence error.
     * @throws ProjectNotFoundFault
     *             If no project with the given ID exists.
     * @throws AuthorizationFailedFault
     *             If the calling principal is not authorized to update the project.
     * @throws IllegalArgumentFault
     *             If the arg given was illegal.
     */
    @RolesAllowed("User")
    public void updateProject(ProjectData projectData) throws PersistenceFault, ProjectNotFoundFault,
        AuthorizationFailedFault, IllegalArgumentFault {
        logEnter("updateProject(ProjectData)");
        logParameters("project data: ", formatProjectData(projectData));
        try {
            checkProjectData(projectData, false);

            Project project = projectPersistence.getProject(projectData.getProjectId());

            // Obtain the user profile principal
            UserProfilePrincipal principal = (UserProfilePrincipal) sessionContext.getCallerPrincipal();

            long userId = principal.getUserId();

            if (userId != project.getUserId()) {
                throw logException(new AuthorizationFailedFault("User is not authorized to update project."));
            }

            // update the data.
            project.setName(projectData.getName());
            project.setDescription(projectData.getDescription());
            // update the modify date.
            project.setModifyDate(new Date());

            projectPersistence.updateProject(project);
        } catch (ProjectNotFoundException e) {
            logException(e);
            throw new ProjectNotFoundFault(e.getMessage());
        } catch (PersistenceException e) {
            logException(e);
            throw new PersistenceFault(e.getMessage());
        } finally {
            logExit("updateProject(ProjectData)");
        }
    }

    /**
     * <p>
     * Deletes the project data for the project with the given Id.
     * </p>
     * <p>
     * Notes, only project-associated user can delete that project, but administrator can delete any project.
     * </p>
     *
     * @param projectId
     *            The ID of the project.
     * @return Whether the project was found, and thus deleted.
     * @throws PersistenceFault
     *             If a generic persistence error.
     * @throws AuthorizationFailedFault
     *             If the calling principal is not authorized to delete the project.
     * @throws ProjectHasCompetitionsFault
     *             If the project cannot be deleted since it has competitions associated with it.
     */
    @SuppressWarnings("unchecked")
    @RolesAllowed("User")
    public boolean deleteProject(long projectId) throws PersistenceFault, ProjectHasCompetitionsFault,
        AuthorizationFailedFault {
        logEnter("deleteProject(long)");
        logParameters("project id: {0}", projectId);

        try {

            // authorization is already done in getProjectById.
            Project project = getProjectById(projectId);

            // project never null here.
            if (!project.getCompetitions().isEmpty()) {
                throw logException(new ProjectHasCompetitionsFault("There are related competitions for this project."));
            }

            boolean deleted = projectPersistence.deleteProject(projectId);
            logReturn(String.valueOf(deleted));
            return deleted;
        } catch (ProjectNotFoundFault e) {
            logReturn(String.valueOf(false));
            return false;
        } catch (PersistenceException e) {
            logException(e);
            throw new PersistenceFault(e.getMessage());
        } finally {
            logExit("deleteProject(long)");
        }
    }

    /**
     * <p>
     * Gets the persistence object used by this bean to persist Project entities. This method is protected so that
     * sub-classes may access the persistence if required.
     * </p>
     *
     * @return the persistence object used by this bean to persist Project entities.
     */
    protected ProjectPersistence getProjectPersistence() {
        return projectPersistence;
    }

    /**
     * <p>
     * Gets the log used by this bean to log information. This method is protected so that sub-classes may access the
     * log if required.
     * </p>
     *
     * @return the log used by this bean to log information.
     */
    protected Log getLog() {
        return log;
    }

    /**
     * <p>
     * Gets the roles key used by this bean to get the roles attribute from the profile of the user profile principal.
     * </p>
     *
     * @return the roles key used by this bean to get the roles attribute from the profile of the user profile
     *         principal.
     */
    public String getRolesKey() {
        return rolesKey;
    }

    /**
     * <p>
     * Gets the name of the administrator role used by this bean to when checking for role fulfillment.
     * </p>
     *
     * @return the name of the administrator role used by this bean to when checking for role fulfillment.
     */
    public String getAdministratorRole() {
        return administratorRole;
    }

    /**
     * <p>
     * Gets the name of the user role used by this bean to when checking for role fulfillment.
     * </p>
     *
     * @return the name of the user role used by this bean to when checking for role fulfillment.
     */
    public String getUserRole() {
        return userRole;
    }

    /**
     * <p>
     * Retrieve the required configuration string from context.
     * </p>
     *
     * @param ctx
     *            the context to retrieve.
     * @param name
     *            the name of the object to look up
     * @param required
     *            whether this property is required.
     * @return the configured object.
     * @throws ConfigurationException
     *             If the required property is missing or is not desired type, or any naming exception occurs.
     */
    private static String getConfigString(InitialContext ctx, String name, boolean required)
        throws ConfigurationException {
        try {
            String value = (String) ctx.lookup("java:comp/env/" + name);

            if (value == null || 0 == value.trim().length()) {
                throw new ConfigurationException(MessageFormat.format("The '{0}' property is empty.", name));
            }

            return value;
        } catch (NameNotFoundException e) {
            if (required) {
                throw new ConfigurationException("A naming problem occurs.", e);
            }

            return null;
        } catch (NamingException e) {
            throw new ConfigurationException("A naming problem occurs.", e);
        } catch (ClassCastException e) {
            throw new ConfigurationException(MessageFormat.format("The '{0}' property is not String type.", name), e);
        }
    }

    /**
     * <p>
     * Checks the ProjectData object, validate it does all the required information.
     * </p>
     *
     * @param projectData
     *            the project data to validate.
     * @param isCreate
     *            whether is creation operation.
     * @throws IllegalArgumentFault
     *             If the projectData is illegal
     */
    private void checkProjectData(ProjectData projectData, boolean isCreate) throws IllegalArgumentFault {
        if (projectData == null) {
            throw logException(new IllegalArgumentFault("The [projectData] is null."));
        }

        if (!isCreate && projectData.getProjectId() == null) {
            throw logException(new IllegalArgumentFault("The project id can not be null."));
        }

        String name = projectData.getName();
        if (null == name) {
            throw logException(new IllegalArgumentFault("The name attribute of the project data can not be null."));
        } else if (name.trim().length() == 0) {
            throw logException(new IllegalArgumentFault("The name attribute of the project data can not be empty."));
        }
    }

    /**
     * <p>
     * Logs the entrance of a method.
     * </p>
     *
     * @param methodName
     *            the method to enter.
     */
    private void logEnter(String methodName) {
        if (log != null) {
            log.log(Level.INFO, "Enter into method [ProjectServiceBean#{0}]", methodName);
        }
    }

    /**
     * <p>
     * Logs the exit of a method.
     * </p>
     *
     * @param methodName
     *            the method name
     */
    private void logExit(String methodName) {
        if (log != null) {
            log.log(Level.INFO, "Exit method [ProjectServiceBean#{0}].", methodName);
        }
    }

    /**
     * <p>
     * Logs the parameters that when calling the specified method.
     * </p>
     *
     * @param pattern
     *            the pattern to fill the parameter values.
     * @param parameters
     *            an array of objects to be formatted and substituted.
     */
    private void logParameters(String pattern, Object... parameters) {
        if (log != null) {
            log.log(Level.INFO, "Parameters: {0}", MessageFormat.format(pattern, parameters));
        }
    }

    /**
     * <p>
     * Logs the returned value by a method call.
     * </p>
     *
     * @param message
     *            the message to log.
     */
    private void logReturn(String message) {
        if (log != null) {
            log.log(Level.INFO, "Returns: {0}", message);
        }
    }

    /**
     * <p>
     * Logs the exception thrown.
     * </p>
     *
     * @param exception
     *            the exception thrown.
     * @param <T> the exception type
     * @return the logged exception
     */
    private <T extends Exception> T logException(T exception) {
        if (log != null) {
            log.log(Level.ERROR, exception, exception.getMessage());
        }

        return exception;
    }

    /**
     * <p>
     * Gets the project with the given Id.
     * </p>
     *
     * @param projectId
     *            The ID of the project.
     * @return The project with the given Id.
     * @throws PersistenceFault
     *             If a generic persistence error.
     * @throws ProjectNotFoundFault
     *             If no project with the given ID exists.
     * @throws AuthorizationFailedFault
     *             If the calling principal is not authorized to retrieve the project.
     */
    @SuppressWarnings("unchecked")
    private Project getProjectById(long projectId) throws PersistenceFault, ProjectNotFoundFault,
        AuthorizationFailedFault {
        Project project = null;
        try {
            project = projectPersistence.getProject(projectId);

            // Obtain the user profile principal
            UserProfilePrincipal principal = (UserProfilePrincipal) sessionContext.getCallerPrincipal();

            // Obtain the roles from the profile
            Map<Object, String> roles = (Map<Object, String>) principal.getProfile().getAttribute(rolesKey).getValue();

            if (principal.getUserId() != project.getUserId() &&  !roles.containsValue(administratorRole)) {
                throw logException(new AuthorizationFailedFault("User is is not administrator or own this probject."));
            }

            return project;
        } catch (ProjectNotFoundException e) {
            logException(e);
            throw new ProjectNotFoundFault(e.getMessage());
        } catch (PersistenceException e) {
            logException(e);
            throw new PersistenceFault(e.getMessage());
        }
    }

    /**
     * <p>
     * Creates a new ProjectData instance by copying data from the passed project.
     * </p>
     *
     * @param project
     *            the project instance to copy data.
     * @return the newly created ProjectData instance.
     */
    private ProjectData createProjectData(Project project) {
        ProjectData projectData = new ProjectData();
        projectData.setDescription(project.getDescription());
        projectData.setName(project.getName());
        projectData.setProjectId(project.getProjectId());
        return projectData;
    }


    /**
     * <p>
     * Formats the ProjectData instance into string representation.
     * </p>
     *
     * @param projectData
     *            the ProjectData instance to format.
     * @return the string representation of the ProjectData instance.
     */
    private static String formatProjectData(ProjectData projectData) {
        if (null == projectData) {
            return null;
        }

        StringBuilder builder = new StringBuilder();
        builder.append("<");
        builder.append("project id: ").append(projectData.getName());
        builder.append(", name: ").append(projectData.getName());
        builder.append(", description: ").append(projectData.getDescription());
        builder.append(">");

        return builder.toString();
    }

    /**
     * <p>
     * Formats the ProjectData instance list into string representation.
     * </p>
     *
     * @param projectDatas
     *            the ProjectData instance list to format.
     * @return the string representation of the ProjectData instance list.
     */
    private static String formatProjectDatas(List<ProjectData> projectDatas) {
        // projectDatas never null.

        StringBuilder builder = new StringBuilder();
        builder.append("[");

        boolean first = true;
        for (ProjectData projectData : projectDatas) {
            if (first) {
                first = false;
            } else {
                builder.append(", ");
            }

            builder.append(formatProjectData(projectData));
        }

        builder.append("]");

        return builder.toString();
    }
}
