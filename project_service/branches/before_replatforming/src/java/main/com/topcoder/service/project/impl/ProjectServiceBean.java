/*
 * Copyright (C) 2008-2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.project.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import com.topcoder.security.RolePrincipal;
import com.topcoder.security.TCSubject;
import com.topcoder.security.auth.module.UserProfilePrincipal;
import com.topcoder.service.project.AuthorizationFailedFault;
import com.topcoder.service.project.ConfigurationException;
import com.topcoder.service.project.IllegalArgumentFault;
import com.topcoder.service.project.PersistenceFault;
import com.topcoder.service.project.Project;
import com.topcoder.service.project.ProjectData;
import com.topcoder.service.project.ProjectHasCompetitionsFault;
import com.topcoder.service.project.ProjectNotFoundFault;
import com.topcoder.service.project.ProjectServiceLocal;
import com.topcoder.service.project.ProjectServiceRemote;
import com.topcoder.service.project.UserNotFoundFault;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;

/**
 * <p>
 * This class implements the <code>{@link ProjectServiceLocal}</code> as well as the <code>{@link ProjectServiceRemote}
 * </code> interfaces. Thus by extension it also implements the <code>{@link com.topcoder.service.project.ProjectService
 * }</code> interface. While EJB 3.0 does not allow the same business interface to be both local and remote, a single
 * class can implement different local and remote business interfaces.
 * </p>
 *
 * <p>
 * This class, when hosted from an EJB container, allows clients to access the project service (as a web service/local
 * bean/remote bean).
 * </p>
 *
 * <p>
 * It performs additional authorization tasks where necessary and delegates persistence to a container managed <code>
 * EntityManager</code>.
 * </p>
 *
 * <p>
 * We store a member <code>log</code> to perform logging. The log name is specified by <b>log_name</b> environment
 * entry.
 * </p>
 *
 * <p>
 * We also store a string property which represents the administrator role. It is specified by <b>administrator_role
 * </b> environment entry. It is used to check whether the caller <code>{@link UserProfilePrincipal}</code> is having
 * administrator role.
 * </p>
 *
 * <p>
 * We use the injected resource <code>{@link SessionContext}</code> to fetch the calling principal when required.
 * </p>
 *
 * <p>
 *     <strong>Version History:</strong>
 *     <ul>
 *         <li>Introduced since version 1.0.</li>
 *         <li>Modified in version 1.1:</li>
 *             <ul>
 *               <li>Removed the <code>projectPersistence</code> variable. This class is now responsible for performing
 *               persistence operations directly;</li>
 *               <li>Removed <code>getProjectPersistence()</code> method;</li>
 *               <li>Removed the <code>rolesKey</code> variable. This class now relies on standard J2EE APIs for
 *               determining which roles a user has;</li>
 *               <li>Removed <code>getRolesKey()</code> method;</li>
 *               <li>Removed the <code>userRole</code> variable, which had no particular use;</li>
 *               <li>Removed <code>getRolesKey()</code> method, which had no particular use;</li>
 *               <li>Change <code>getAdministratorRole()</code> method to be protected;</li>
 *               <li>Added <code>entityManager</code> variable, which is a container injected/managed, transaction
 *               scoped <code>EntityManager</code>;</li>
 *               <li>Annotated with "@DeclareRoles({"Cockpit User", "Cockpit Administrator" })" to
 *               declare security roles in making authorization decisions. Note this can be overridden in deployment
 *               descriptor;</li>
 *               <li>Annotated with "@RolesAllowed({"Cockpit User", "Cockpit Administrator" })" to
 *               allow security roles in making authorization decisions. Note this can be overridden in deployment
 *               descriptor;</li>
 *               <li>Annotated with "@TransactionManagement(TransactionManagementType.CONTAINER)" to rely on container
 *               -managed transactions;</li>
 *               <li>Annotated with "@TransactionAttribute(TransactionAttributeType.REQUIRED)" to indicate each business
 *               method expects to have EJB transaction attribute "REQUIRED".</li>
 *             </ul>
 *     </ul>
 * </p>
 *
 * <p>
 * <b>ejb-jar.xml</b> configuration sample:<br>
 * <pre>
 *  &lt;ejb-jar&gt;
 *    &lt;enterprise-beans&gt;
 *      &lt;session&gt;
 *        &lt;ejb-name&gt;ProjectServiceBean&lt;/ejb-name&gt;
 *        &lt;remote&gt;com.topcoder.service.project.ProjectServiceRemote&lt;/remote&gt;
 *        &lt;local&gt;com.topcoder.service.project.ProjectServiceLocal&lt;/local&gt;
 *        &lt;ejb-class&gt;com.topcoder.service.project.impl.ProjectServiceBean&lt;/ejb-class&gt;
 *        &lt;session-type&gt;Stateless&lt;/session-type&gt;
 *        &lt;transaction-type&gt;Container&lt;/transaction-type&gt;
 *        &lt;env-entry&gt;
 *          &lt;description&gt;The name of the log to use&lt;/description&gt;
 *          &lt;env-entry-name&gt;log_name&lt;/env-entry-name&gt;
 *          &lt;env-entry-type&gt;java.lang.String&lt;/env-entry-type&gt;
 *          &lt;env-entry-value&gt;TopCoderLog&lt;/env-entry-value&gt;
 *        &lt;/env-entry&gt;
 *        &lt;env-entry&gt;
 *          &lt;description&gt;The name of the administrator role&lt;/description&gt;
 *          &lt;env-entry-name&gt;administrator_role&lt;/env-entry-name&gt;
 *          &lt;env-entry-type&gt;java.lang.String&lt;/env-entry-type&gt;
 *          &lt;env-entry-value&gt;Cockpit Administrator&lt;/env-entry-value&gt;
 *        &lt;/env-entry&gt;
 *        &lt;!-- Tells the container for which persistence unit to inject an EntityManager --&gt;
 *        &lt;persistence-context-ref&gt;
 *              &lt;persistence-context-ref-name&gt;project_service_persistence&lt;/persistence-context-ref-name&gt;
 *              &lt;persistence-unit-name&gt;HibernateProjectPersistence&lt;/persistence-unit-name&gt;
 *        &lt;/persistence-context-ref&gt;
 *      &lt;/session&gt;
 *    &lt;/enterprise-beans&gt;
 *  &lt;/ejb-jar&gt;
 * </pre>
 * </p>
 *
 * <p>
 *     <strong>To use Remote EJB:</strong>
 *     <pre>
 *        Properties env = new Properties();
 *        // Specify principal
 *        env.setProperty(Context.SECURITY_PRINCIPAL, "username");
 *        // Specify credential
 *        env.setProperty(Context.SECURITY_CREDENTIALS, "password");
 *
 *        // The initial factory and provider url are specified in jndi.properties
 *        InitialContext ctx = new InitialContext(env);
 *
 *        // Lookup remote EJB
 *        ProjectServiceRemote service = (ProjectServiceRemote) ctx.lookup("remote/ProjectServiceBean");
 *
 *        // Instantiate a ProjectData
 *        ProjectData projectData = ...
 *
 *        // Create project
 *        projectData = service.createProject(projectData);
 *
 *        System.out.println("Project created with id - " + projectData.getProjectId());
 *
 *        // Get project by id
 *        service.getProject(projectData.getProjectId());
 *
 *        // Get all projects own by user
 *        service.getAllProjects();
 *
 *        projectData.setName("new name");
 *        projectData.setDescription("new description");
 *
 *        // Update project
 *        service.updateProject(projectData);
 *
 *        // Only administrator can get projects for user
 *        try {
 *            service.getProjectsForUser(1L);
 *        } catch (EJBAccessException e) {
 *            System.err.println("Only administrator can get projects for user");
 *        }
 *
 *        // Delete project
 *        service.deleteProject(projectData.getProjectId());
 *     </pre>
 * </p>
 *
 * <p>
 *     <strong>To inject Local EJB:</strong>
 *     "@EJB(beanName = "ProjectServiceBean") private ProjectServiceLocal projectService;"
 * </p>
 *
 * <p>
 *     <strong>To get Web Service client:</strong>
 *     <pre>
 *        ProjectService service = Service.create(
 *            new URL("http://127.0.0.1:8080/project_service-project_service/ProjectServiceBean?wsdl"),
 *                new QName("http://impl.project.service.topcoder.com/", "ProjectServiceBeanService"))
 *                .getPort(ProjectService.class);
 *
 *        // Provide the username/password security info
 *        URL securityURL = Demo.class.getResource("/jboss-wsse-client.xml");
 *        ((StubExt) service).setSecurityConfig(securityURL.toURI().toString());
 *        ((StubExt) service).setConfigName("Standard WSSecurity Client");
 *        ((BindingProvider) service).getRequestContext().put(BindingProvider.USERNAME_PROPERTY, "username");
 *        ((BindingProvider) service).getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, "password");
 *     </pre>
 * </p>
 *<p>
 *Modified in version 1.1.1:
 *             <ul>
 *               <li>It is not web-service any more.</li>
 *               <li>createProject, getProject, updateProject, deleteProject accepts a new parameter TCSubject
 *                   which is used to get the security info for current user.
 *               </li>
 *             </ul>
 *  <li>Modified in version 1.1.2</li>
 *             <ul>
 *               <li>Added <code>getProjectByName(String,long)</code> method.</li>
 *               <li>Added <code>checkAuthorization(Project)</code> method.</li>
 *               <li>Modified <code>getProjectById(long)</code> method. It now uses the <code>checkAuthorization</code>
 *               method instead of verifying the authorization itself</li>
 *            </ul>
 * </p>
 * <p>
 * <b>Thread Safety</b>: This class is thread safe as it is immutable except for the session context. The
 * sessionContext resource, despite being mutable, does not compromise thread safety as it is injected by the EJB
 * container and will not change during calls to the bean's methods.
 * </p>
 *
 * @author humblefool, FireIce
 * @author ThinMan, TCSDEVELOPER
 * @author woodjhon, ernestobf
 * @version 1.1.2
 * @since 1.0
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ProjectServiceBean implements ProjectServiceLocal, ProjectServiceRemote {

    /**
     * <p>
     * The query used to retrieve <code>Project</code> entities.
     * </p>
     *
     * @since 1.1
     */
    private static final String QUERY_ALL_PROJECTS = "SELECT project_id, name, description, project_status_id FROM tc_direct_project p where  p.project_status_id = 1";

    /**
     * <p>
     * The "where" clause used to retrieve <code>Project</code> entities by user id.
     * </p>
     *
     * <p>
     * Updated for Cockpit Project Admin Release Assembly v1.0
     *      - also need to fetch users who has read, write, full permissions on the project other than the creator.
     * </p>
     *
     * @since 1.1
     */
    private static final String QUERY_PROJECTS_BY_USER = "SELECT project_id, name, description, project_status_id FROM tc_direct_project p, user_permission_grant per "
                                    + " where p.project_id = per.resource_id and p.project_status_id = 1 and per.user_id = ";

    /**
     * <p>
     * JPQL query used to retrieve the project with the specified name and user id. It contains the named parameters
     * <code>projectName</code> and <code>userId</code>.
     * </p>
     *
     * @since 1.1
     */
    private static final String QUERY_PROJECT_BY_NAME =
        "SELECT p FROM Project p WHERE p.name = :projectName and p.userId = :userId";

    /**
     * <p>
     * JPQL query used to retrieve the project with the specified name and user id. It contains the named parameters
     * <code>projectName</code> and <code>userId</code>.
     * </p>
     *
     * @since 1.1
     */
    private static final String QUERY_PROJECT_BY_NAME_ONLY =
        "SELECT p FROM Project p WHERE p.name = :projectName";
    
    /**
     * constant for active project id
     */
    private static final long PROJECT_STATUS_ACTIVE = 1;

    /**
     * <p>
     * Represents the log used to log useful information.
     * </p>
     *
     * <p>
     * It is not frozen because it is set in {@link #initialize()} (not in the constructor),
     * but once set it will not be modified.
     * </p>
     *
     * <p>
     * It may be null, indicating that no logging is to be performed.
     * </p>
     *
     * <p>
     * It is retrieved by the {@link #getLog()} method.
     * </p>
     *
     * <p>
     * It is used by all the methods of this class to perform logging.
     * The details of the logging may be seen in section 1.3.4 of the component specification.
     * </p>
     *
     * <p>
     *     <strong>Version History:</strong>
     *     <ul>
     *         <li>Introduced since version 1.0.</li>
     *         <li>Modified in version 1.1:</li>
     *             <ul>
     *               <li>Made to be not frozen.</li>
     *             </ul>
     *     </ul>
     * </p>
     *
     * @since 1.0
     */
    private Log log;

    /**
     * <p>
     * Represents the name of the administrator role in the roles attribute of the profile of the user profile
     * persistence.
     * </p>
     *
     * <p>
     * It is not frozen because it is set in {@link #initialize()} (not in the constructor),
     * but once set it will not be modified.
     * </p>
     *
     * <p>
     * It will not be null/empty.
     * </p>
     *
     * <p>
     * It is retrieved by the {@link #getAdministratorRole()} method.
     * </p>
     *
     * <p>
     * It is used by those methods of this class which need to check the roles satisfied by a user as part of
     * their logic.
     * </p>
     *
     * <p>
     *     <strong>Version History:</strong>
     *     <ul>
     *         <li>Introduced since version 1.0.</li>
     *         <li>Modified in version 1.1:</li>
     *             <ul>
     *               <li>Made to be not frozen.</li>
     *             </ul>
     *     </ul>
     * </p>
     *
     * @since 1.0
     */
    private String administratorRole;

    /**
     * <p>
     * Represents the session context of this bean.
     * </p>
     *
     * <p>
     * It is a resource injected by the EJB container and will not be null while client calls are being executed.
     * </p>
     *
     * <p>
     * It is not frozen since it will be set by the EJB container when required. However, it does not compromise
     * thread safety since the context will not be set while the bean is executing client calls.
     * </p>
     *
     * @since 1.0
     */
    @Resource
    private SessionContext sessionContext;

    /**
     * <p>
     * A container-managed JTA <code>EntityManager</code> for the persistence context this service manages,
     * injected into instances of this classes by the EJB container.
     * </p>
     *
     * <p>
     * It is the responsibility of the container to ensure that this object is injected and used in a thread-safe
     * manner.
     * </p>
     *
     * @since 1.1
     */
    //@PersistenceContext
   // private EntityManager entityManager;

   /**
     * <p>
     * This field represents the persistence unit name to lookup the <code>EntityManager</code> from the
     * <code>SessionContext</code>. It is initialized in the <code>initialize</code> method, and never changed
     * afterwards. It must be non-null, non-empty string.
     * </p>
     */
    @Resource(name = "unitName")
    private String unitName;

    /**
     * <p>
     * Constructs a <code>ProjectServiceBean</code> instance.
     * </p>
     *
     * <p>
     *     <strong>Version History:</strong>
     *     <ul>
     *         <li>Introduced since version 1.0.</li>
     *         <li>Modified in version 1.1:</li>
     *             <ul>
     *               <li>Now this is a completely empty constructor. The logic to retrieve/validate environment entries
     *               are moved to <code>initialize()</code> method.</li>
     *             </ul>
     *     </ul>
     * </p>
     *
     * @since 1.0
     */
    public ProjectServiceBean() {
        //empty constructor
    }

    /**
     * <p>
     * All instance initialization depends on this bean's EJB environment, and is therefore moved to here from the
     * constructor.
     * </p>
     *
     * @throws ConfigurationException To indicate configuration errors(e.g. if required <code>administrator_role</code>
     *         property is missing or not type of <code>String</code>, or if <code>log_name</code> property is present
     *         but not type of <code>String</code>).
     *
     * @since 1.1
     */
    @PostConstruct
    protected void initialize() {

        String logName = getConfigString("log_name", false);

        // Note JBoss will treat the empty env-entry value as null
        if (logName != null) {
            log = LogManager.getLog(logName);
        } else {
            log = LogManager.getLog(ProjectServiceBean.class.getName());
        }

        administratorRole = getConfigString("administrator_role", true);
    }

    /**
     * <p>
     * Retrieve the configuration string from context.
     * </p>
     *
     * @param name
     *            The name of the object to look up.
     * @param required
     *            Whether this property is required.
     *
     * @return The configured object.
     *
     * @throws ConfigurationException
     *             If the required property is missing or if any property is not desired type.
     *
     * @since 1.1
     */
    private String getConfigString(String name, boolean required) {
        try {
            String value = (String) sessionContext.lookup(name);

            if (required && (value == null || 0 == value.trim().length())) {
                throw new ConfigurationException(
                    MessageFormat.format("The {0} property is required to be non-null and non-empty.", name));
            }

            return value;
        } catch (ClassCastException e) {
            throw new ConfigurationException(MessageFormat.format("The {0} property is not String type.", name), e);
        } catch (IllegalArgumentException e) {
            if (!required) {
                return null;
            }
            throw new ConfigurationException(MessageFormat.format("The {0} property is missing.", name), e);
        }
    }

    /**
     * <p>
     * Creates a project with the given project data.
     * </p>
     *
     * <p>
     * Note, any user can create project and the project will associate with him/her.
     * </p>
     *
     * <p>
     *     <strong>Only interest for <code>ProjectData.name</code> and <code>ProjectData.description</code>:</strong>
     *     We are only interested for the <code>name</code> and <code>description</code> properties of given <code>
     *     ProjectData</code>. The <code>ProjectData.projectId</code>, if any, is ignored. And also any property
     *     of <code>Project</code>, if the given <code>ProjectData</code> is instance of <code>Project</code>, is
     *     ignored.
     * </p>
     *
     * <p>
     *     <strong>Passed in <code>ProjectData</code> is not changed:</strong>
     *     We treat the given <code>ProjectData</code> as a DTO, and will not update its properties(e.g. the project id,
     *     the associated user id). The application should rely on the instance of <code>ProjectData</code> returned by
     *     this method.
     * </p>
     *
     * <p>
     *     <strong>Version History:</strong>
     *     <ul>
     *         <li>Introduced since version 1.0.</li>
     *         <li>Modified in version 1.1:</li>
     *             <ul>
     *               <li>Uses a container injected/managed <code>EntityManager</code> to perform persistence tasks
     *               directly;</li>
     *               <li>The allowed roles are pre-defined as "<b>Cockpit User</b>" and "<b>Cockpit Administrator
     *               </b>". Note this can be overridden in deployment descriptor.</li>
     *             </ul>
     *     </ul>
     * </p>
     * <p>
     * Update in v1.1.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param projectData
     *            The project data to be created. Must not be null.
     *            The <code>ProjectData.name</code> must not be null/empty.
     *            The <code>ProjectData.projectId</code>, if any, is ignored.
     *
     * @return The project as it was created, with the <code>ProjectData.projectId</code> and <code>ProjectData.userId
     *         </code> set. Will never be null.
     *
     * @throws IllegalArgumentFault
     *             If the given <code>ProjectData</code> is illegal.
     * @throws PersistenceFault
     *             If a generic persistence error occurs.
     * @throws NullPointerException
     *             If there is no caller principal.
     * @throws ClassCastException
     *             If the caller principal is not type of <code>UserProfilePrincipal</code>.
     *
     * @since 1.0
     */
    public ProjectData createProject(TCSubject tcSubject, ProjectData projectData) throws IllegalArgumentFault, PersistenceFault {

        logEnter("createProject(TCSubject, ProjectData)");
        logParameters("project data: {0}", tcSubject);
        logParameters("project data: {1}", formatProjectData(projectData));

        try {
            // Validate
            checkProjectData(projectData, true);

            // Create a new Project, copy name and description
            Project project = new Project();
            project.setName(projectData.getName());
            project.setDescription(projectData.getDescription());
            project.setProjectStatusId(PROJECT_STATUS_ACTIVE);

            // Obtain the user id of caller
            long callerUserId = tcSubject.getUserId();

            // Set the user ID
            project.setUserId(callerUserId);

            // Set the creation date
            project.setCreateDate(new Date());
            project.setModifyDate(new Date());

            // Persist and flush
            this.manageEntity(project, Action.CREATE);

            // Copy to a new ProjectData entity.
            ProjectData resultProjectData = copyProjectData(project);

            logReturn(formatProjectData(resultProjectData));
            return resultProjectData;
        } finally {
            logExit("createProject(ProjectData)");
        }
    }

    /**
     * <p>
     * Gets the project data for the project with the given Id.
     * </p>
     *
     * <p>
     * Notes, only associated user can retrieve the specified project, administrator can retrieve any projects.
     * </p>
     *
     * <p>
     *     <strong>Version History:</strong>
     *     <ul>
     *         <li>Introduced since version 1.0.</li>
     *         <li>Modified in version 1.1:</li>
     *             <ul>
     *               <li>Uses a container injected/managed <code>EntityManager</code> to perform persistence tasks
     *               directly;</li>
     *               <li>The allowed roles are pre-defined as "<b>Cockpit User</b>" and "<b>Cockpit Administrator
     *               </b>". Note this can be overridden in deployment descriptor.</li>
     *             </ul>
     *     </ul>
     * </p>
     * <p>
     * Update in v1.1.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param projectId
     *            The ID of the project to be retrieved.
     *
     * @return The project data for the project with the given Id. Will never be null.
     *
     * @throws PersistenceFault
     *             If a generic persistence error.
     * @throws ProjectNotFoundFault
     *             If no project with the given ID exists.
     * @throws AuthorizationFailedFault
     *             If the calling principal is not authorized to retrieve the project.
     * @throws NullPointerException
     *             If there is no caller principal.
     * @throws ClassCastException
     *             If the caller principal is not type of <code>UserProfilePrincipal</code>.
     *
     * @since 1.0
     */
    public ProjectData getProject(TCSubject tcSubject,long projectId)
        throws ProjectNotFoundFault, AuthorizationFailedFault, PersistenceFault {

        logEnter("getProject(tcSubject, long)");
        logParameters("project id: {0}", tcSubject);
        logParameters("project id: {1}", projectId);

        try {
            // Authorization is already done in getProjectById.
            Project project = getProjectById(tcSubject, projectId);

            ProjectData projectData = copyProjectData(project);

            logReturn(formatProjectData(projectData));
            return projectData;
        } finally {
            logExit("getProject(long)");
        }
    }

    /**
     * <p>
     * Gets the project data for the project with the project name.
     * </p>
     *
     * @param projectName
     *            the name of the project to be retrieved.
     * @param userId
     *            The ID of the user whose projects are to be retrieved.
     * @return
     *            The project data for the project with the given Id. Will never be null.
     * @throws PersistenceFault
     *            If a generic persistence error occurs.
     * @throws ProjectNotFoundFault
     *            If no project with the given name and user id exists.
     * @throws AuthorizationFailedFault
     *            If the calling principal is not authorized to retrieve the project.
     * @throws IllegalArgumentFault
     *            If the given <code>projectName</code> is null/empty, or <code>userId</code>
     *            is non-positive.
     * @since 1.1
     */
    public ProjectData getProjectByName(String projectName, long userId) throws PersistenceFault,
        ProjectNotFoundFault, AuthorizationFailedFault, IllegalArgumentFault {

        logEnter("getProjectByName(String,long)");
        logParameters("project name: {0}, user id: {1}", projectName, userId);

        try {

            if ((projectName == null) || (projectName.trim().length() == 0)) {
                throw logException(new IllegalArgumentFault("projectName cannot be null or empty"));
            } else if (userId <= 0) {
                throw logException(new IllegalArgumentFault("userId must be a positive number"));
            }

            Query q = getEntityManager().createQuery(QUERY_PROJECT_BY_NAME);
            q.setParameter("projectName", projectName);
            q.setParameter("userId", userId);
            Project project = (Project) q.getSingleResult();

            //checkAuthorization(project);

            ProjectData projectData = copyProjectData(project);

            logReturn(formatProjectData(projectData));

            return projectData;
        } catch (NoResultException e) {
            throw logException(new ProjectNotFoundFault(
                    MessageFormat.format("Project with name {0} and userId {1} does not exist", projectName,
                            userId)));
        } catch (NonUniqueResultException e) {
            logException(e);
            throw new PersistenceFault(e.getMessage());
        } catch (PersistenceException e) {
            logException(e);
            throw new PersistenceFault(e.getMessage());
        } finally {
            logExit("getProjectByName(String,long)");
        }
    }

    /**
     * <p>
     * Gets the project data for all projects of the given user.
     * </p>
     *
     * <p>
     * Updated for Cockpit Project Admin Release Assembly v1.0 - now all users can do this.
     * </p>
     *
     * <p>
     *     <strong>Version History:</strong>
     *     <ul>
     *         <li>Introduced since version 1.0.</li>
     *         <li>Modified in version 1.1:</li>
     *             <ul>
     *               <li>Uses a container injected/managed <code>EntityManager</code> to perform persistence tasks
     *               directly;</li>
     *               <li>The allowed role is pre-defined as "<b>Cockpit Administrator</b>". Note this can be overridden
     *               in deployment descriptor;</li>
     *               <li>This method no longer performs role-based authorization in code. It relies instead (as always
     *               did) on EJB declarative security.</li>
     *             </ul>
     *     </ul>
     * </p>
     *
     * @param userId
     *            The ID of the user whose projects are to be retrieved.
     *
     * @return The project data for all projects of the given user. The returned collection will not
     *         be null or contain nulls. Will never be empty.
     *
     * @throws UserNotFoundFault
     *             If there are no projects linked to the given user.
     * @throws PersistenceFault
     *             If a generic persistence error occurs.
     *
     * @since 1.0
     */
    public List < ProjectData > getProjectsForUser(long userId) throws PersistenceFault {
        logEnter("getProjectsForUser(long)");
        logParameters("user id: {0}", userId);

        try {
            List < ProjectData > projectDatas = doGetProjects(userId);

            // If there is no project linked to user, throw UserNotFoundFault
            //if (projectDatas.isEmpty()) {
            //    throw logException(new UserNotFoundFault("No projects linked with the given user " + userId));
           // }

            logReturn(projectDatas.size() + " projects found. ");// formatProjectDatas(projectDatas));
            return projectDatas;
        } finally {
            logExit("getProjectsForUser(long)");
        }
    }

    /**
     * <p>
     * Get projects by given user id. If given user id is null, then all projects will be retrieved.
     * </p>
     *
     * <p>
     * Called by <code>getProjectsForUser()</code> and <code>getAllProjects()</code>.
     * </p>
     *
     * <p>
     * Introduced in version 1.1 to retrieve projects by using <code>Query</code>.
     * </p>
     *
     * @param userId
     *            The ID of the user whose projects are to be retrieved. May be null if want to retrieve all projects.
     *
     * @return The projects retrieved. The returned collection will not be null or contain nulls. Possibly empty.
     *
     * @throws PersistenceFault
     *             If a generic persistence error occurs.
     *
     * @since 1.1
     */
    @SuppressWarnings("unchecked")
    private List < ProjectData > doGetProjects(Long userId) throws PersistenceFault {

        Query query = null;

        try {
            if (userId != null) { 
                query = getEntityManager().createNativeQuery(QUERY_PROJECTS_BY_USER + userId, "GetProjectsResult");

            } else {
                query = getEntityManager().createNativeQuery(QUERY_ALL_PROJECTS, "GetProjectsResult");
            }

            List list = query.getResultList();

            // Copy each Project
            List < ProjectData > projectDatas = new ArrayList();

            for (int i = 0; i < list.size(); i++) {
                ProjectData data = (ProjectData) list.get(i);

                projectDatas.add(data);
            }

            return projectDatas;
        } catch (PersistenceException e) {
            logException(e);
            throw new PersistenceFault(e.getMessage());
        }
    }

    /**
     * <p>
     * Gets the project data for all projects viewable from the calling principal.
     * </p>
     *
     * <p>
     * Notes, for user, it will retrieve only the projects associated with him;
     * For administrator, it will retrieve all the projects.
     * </p>
     *
     * <p>
     *     <strong>Version History:</strong>
     *     <ul>
     *         <li>Introduced since version 1.0.</li>
     *         <li>Modified in version 1.1:</li>
     *             <ul>
     *               <li>Uses a container injected/managed <code>EntityManager</code> to perform persistence tasks
     *               directly;</li>
     *               <li>The allowed roles are pre-defined as "<b>Cockpit User</b>" and "<b>Cockpit Administrator
     *               </b>". Note this can be overridden in deployment descriptor.</li>
     *             </ul>
     *     </ul>
     * </p>
     *
     * @return The project data for all projects viewable from the calling principal. The returned collection will not
     *         be null or contain nulls. Possibly empty.
     *
     * <p>
     * Updated for Cockpit Project Admin Release Assembly v1.0
     *      - Removed check for admin.
     * </p>
     *
     * @throws PersistenceFault
     *             If a generic persistence error occurs.
     * @throws NullPointerException
     *             If there is no caller principal.
     * @throws ClassCastException
     *             If the caller principal is not type of <code>UserProfilePrincipal</code>.
     *
     * @since 1.0
     */
    public List < ProjectData > getAllProjects() throws PersistenceFault {

        logEnter("getAllProjects()");

        try {
            List < ProjectData > projectDatas = doGetProjects(null);
            logReturn(projectDatas.size() + " projects found");
            return projectDatas;
        } finally {
            logExit("getAllProjects()");
        }
    }

    /**
     * <p>
     * Updates the project data for the given project.
     * </p>
     *
     * <p>
     * Notes, only project-associated user can update that project, but administrator can update any project.
     * </p>
     *
     * <p>
     *     <strong>Version History:</strong>
     *     <ul>
     *         <li>Introduced since version 1.0.</li>
     *         <li>Modified in version 1.1:</li>
     *             <ul>
     *               <li>Uses a container injected/managed <code>EntityManager</code> to perform persistence tasks
     *               directly;</li>
     *               <li>The allowed roles are pre-defined as "<b>Cockpit User</b>" and "<b>Cockpit Administrator
     *               </b>". Note this can be overridden in deployment descriptor.</li>
     *             </ul>
     *     </ul>
     * </p>
     * <p>
     * Update in v1.1.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param projectData
     *            The project data to be updated. Must not be null.
     *            The <code>ProjectData.name</code> must not be null/empty.
     *            The <code>ProjectData.projectId</code> must be non-null.
     *
     * @throws IllegalArgumentFault
     *             If the given <code>ProjectData</code> is illegal.
     * @throws ProjectNotFoundFault
     *             If no project with the given ID exists.
     * @throws AuthorizationFailedFault
     *             If the calling principal is not authorized to update the project.
     * @throws PersistenceFault
     *             If a generic persistence error.
     * @throws NullPointerException
     *             If there is no caller principal.
     * @throws ClassCastException
     *             If the caller principal is not type of <code>UserProfilePrincipal</code>.
     *
     * @since 1.0
     */
    public void updateProject(TCSubject tcSubject, ProjectData projectData)
        throws IllegalArgumentFault, ProjectNotFoundFault, AuthorizationFailedFault, PersistenceFault {

        logEnter("updateProject(TCSubject, ProjectData)");
        logParameters("project data: {0}", tcSubject);
        logParameters("project data: {1}", formatProjectData(projectData));

        try {
            checkProjectData(projectData, false);

            // Authorization is already done in getProjectById.
            Project managedProject = getProjectById(tcSubject, projectData.getProjectId());

            // Update the data into managed project entity.
            managedProject.setName(projectData.getName());
            managedProject.setDescription(projectData.getDescription());

            // Update the modify date.
            managedProject.setModifyDate(new Date());

            this.manageEntity(managedProject, Action.UPDATE);

        } finally {
            logExit("updateProject(ProjectData)");
        }
    }

    /**
     * <p>
     * Deletes the project data for the project with the given Id.
     * </p>
     *
     * <p>
     * Notes, only project-associated user can delete that project, but administrator can delete any project.
     * </p>
     *
     * <p>
     *     <strong>Version History:</strong>
     *     <ul>
     *         <li>Introduced since version 1.0.</li>
     *         <li>Modified in version 1.1:</li>
     *             <ul>
     *               <li>Uses a container injected/managed <code>EntityManager</code> to perform persistence tasks
     *               directly;</li>
     *               <li>The allowed roles are pre-defined as "<b>Cockpit User</b>" and "<b>Cockpit Administrator
     *               </b>". Note this can be overridden in deployment descriptor.</li>
     *             </ul>
     *     </ul>
     * </p>
     * <p>
     * Update in v1.1.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param projectId
     *            The ID of the project to be deleted.
     * @return Whether the project was found, and thus deleted.
     *
     * @throws AuthorizationFailedFault
     *             If the calling principal is not authorized to delete the project.
     * @throws ProjectHasCompetitionsFault
     *             If the project cannot be deleted since it has competitions associated with it.
     * @throws PersistenceFault
     *             If a generic persistence error.
     * @throws NullPointerException
     *             If there is no caller principal.
     * @throws ClassCastException
     *             If the caller principal is not type of <code>UserProfilePrincipal</code>.
     *
     * @since 1.0
     */
    public boolean deleteProject(TCSubject tcSubject, long projectId)
        throws AuthorizationFailedFault, ProjectHasCompetitionsFault, PersistenceFault {

        logEnter("deleteProject(tcSubject, long)");
        logParameters("project id: {0}", tcSubject);
        logParameters("project id: {1}", projectId);

        try {

            // Authorization is already done in getProjectById.
            Project project = getProjectById(tcSubject, projectId);

            // If project has competitions associated, then it can not be deleted.
            if (!project.getCompetitions().isEmpty()) {
                throw logException(new ProjectHasCompetitionsFault("There are related competitions for this project."));
            }

            this.manageEntity(project, Action.DELETE);

            logReturn("true");
            return true;
        } catch (ProjectNotFoundFault e) {
            // If project is not found, return false
            logReturn("false");
            return false;
        } finally {
            logExit("deleteProject(long)");
        }
    }


    /**
     * <p>
     * Gets the project data for the project with the project name.
     * </p>
     *
     * @param projectName
     *            the name of the project to be retrieved.
     * @return
     *            The project data for the project with the given Id. Will never be null.
     * @throws PersistenceFault
     *            If a generic persistence error occurs.
     * @throws ProjectNotFoundFault
     *            If no project with the given name and user id exists.
     * @throws AuthorizationFailedFault
     *            If the calling principal is not authorized to retrieve the project.
     * @throws IllegalArgumentFault
     *            If the given <code>projectName</code> is null/empty, or <code>userId</code>
     *            is non-positive.
     * @since 1.1
     */
    public List <ProjectData> getProjectsByName(String projectName) throws PersistenceFault,
        ProjectNotFoundFault, IllegalArgumentFault {

        logEnter("getProjectByName(String,long)");
        logParameters("project name: {0} ", projectName);

        try {

            if ((projectName == null) || (projectName.trim().length() == 0)) {
                throw logException(new IllegalArgumentFault("projectName cannot be null or empty"));
            } 

            Query query = getEntityManager().createQuery(QUERY_PROJECT_BY_NAME_ONLY);
            query.setParameter("projectName", projectName);

            List list = query.getResultList();

            // Copy each Project
            List < ProjectData > projectDatas = new ArrayList();

            for (int i = 0; i < list.size(); i++) {
                ProjectData data = (ProjectData) list.get(i);
                projectDatas.add(data);
            }

            return projectDatas;
        } catch (NoResultException e) {
            throw logException(new ProjectNotFoundFault(
                    MessageFormat.format("Project with name {0} does not exist", projectName)));
        } catch (NonUniqueResultException e) {
            logException(e);
            throw new PersistenceFault(e.getMessage());
        } catch (PersistenceException e) {
            logException(e);
            throw new PersistenceFault(e.getMessage());
        } finally {
            logExit("getProjectByName(String,long)");
        }
    }

    /**
     * <p>
     * Create/update/delete project entity.
     * </p>
     *
     * @param project The <code>Project</code> entity to be created/updated/deleted.
     * @param action The enum indicates the desired action.
     *
     * @throws PersistenceFault
     *             If a generic persistence error.
     *
     * @since 1.1
     */
    private void manageEntity(Project project, Action action) throws PersistenceFault {

        try {
            EntityManager entityManager = getEntityManager();
            if (action == Action.CREATE) {
                // Persist entity
                entityManager.persist(project);
            } else if (action == Action.DELETE) {
                // Remove entity
                entityManager.remove(project);
            }

            // For update, no need call EntityManager.merge() explicitly since we pushed the new properties
            // into the managed entity directly(see updateProject()), only flush() is sufficient.

            // Flush EntityManager
            entityManager.flush();
        } catch (PersistenceException e) {
            logException(e);
            throw new PersistenceFault(e.getMessage());
        }
    }

    /**
     * <p>
     * Gets the log used by this bean to log information. This method is protected so that sub-classes may access the
     * log if required.
     * </p>
     *
     * @return The log used by this bean to log information.
     *
     * @since 1.0
     */
    protected Log getLog() {
        return log;
    }

    /**
     * <p>
     * Gets the name of the administrator role used by this bean when checking for administrator role.
     * </p>
     *
     * <p>
     *     <strong>Version History:</strong>
     *     <ul>
     *         <li>Introduced since version 1.0.</li>
     *         <li>Modified in version 1.1:</li>
     *             <ul>
     *               <li>This method is made protected.</li>
     *             </ul>
     *     </ul>
     * </p>
     *
     * @return The name of the administrator role used by this bean when checking for administrator role.
     *
     * @since 1.0
     */
    protected String getAdministratorRole() {
        return administratorRole;
    }

    /**
     * <p>
     * Checks the <code>ProjectData</code> object, validate it contains all the required information.
     * </p>
     *
     * @param projectData
     *            The project data to validate.
     * @param isCreate
     *            Whether is creation operation.
     *
     * @throws IllegalArgumentFault
     *             If the projectData is illegal
     *
     * @since 1.0
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
     * Gets the project with the given Id.
     * </p>
     *
     * <p>
     * This will check that the project is owned by the user or the user is an administrator.
     * </p>
     *
     * <p>
     *     <strong>Version History:</strong>
     *     <ul>
     *         <li>Introduced since version 1.0.</li>
     *         <li>Modified in version 1.1:</li>
     *             <ul>
     *               <li>Use container managed <code>EntityManager</code> to find project.</li>
     *             </ul>
     *         <li>Modified in version 1.1:</li>
     *             <ul>
     *               <li>Calls <code>checkAuthorization</code> to check if the
     *               user is authorized to retrieve the project.</li>
     *             </ul>
     *     </ul>
     * </p>
     *
     * @param projectId
     *            The ID of the project.
     *
     * @return The project with the given Id.
     *
     * @throws PersistenceFault
     *             If a generic persistence error.
     * @throws ProjectNotFoundFault
     *             If no project with the given ID exists.
     * @throws AuthorizationFailedFault
     *             If the calling principal is not authorized to retrieve the project.
     * @throws NullPointerException
     *             If there is no caller principal.
     * @throws ClassCastException
     *             If the caller principal is not type of <code>UserProfilePrincipal</code>.
     *
     * @since 1.0
     */
    private Project getProjectById(TCSubject tcSubject, long projectId) throws PersistenceFault, ProjectNotFoundFault,
        AuthorizationFailedFault {
        try {
            Project project = getEntityManager().find(Project.class, projectId);

            if (project == null) {
                throw logException(new ProjectNotFoundFault(
                        MessageFormat.format("Project with id {0} does not exist", projectId + "")));
            }

            // Obtain the user id of caller
            long callerUserId = tcSubject.getUserId();

            return project;
        } catch (PersistenceException e) {
            logException(e);
            throw new PersistenceFault(e.getMessage());
        }
    }

    /**
     * <p>
     * Checks if the login user is admin or not.
     * </p>
     *
     * @param tcSubject TCSubject instance for login user
     * @return true if it is admin
     */
    private boolean isAdminRole(TCSubject tcSubject) {
        Set<RolePrincipal> roles = tcSubject.getPrincipals();
        if (roles != null) {
            for (RolePrincipal role : roles) {
                if (role.getName().equalsIgnoreCase(getAdministratorRole())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * <p>
     * Creates a new <code>ProjectData</code> instance by copying data from the passed <code>ProjectData</code>.
     * </p>
     *
     * <p>
     * Version 1.1 renames the <code>createProjectData()</code> method to <code>copyProjectData()</code>.
     * </p>
     *
     * @param project The <code>ProjectData</code> instance to be copied.
     *
     * @return The newly created <code>ProjectData</code> instance with data copied.
     *
     * @since 1.1
     */
    private ProjectData copyProjectData(ProjectData project) {
        ProjectData projectData = new ProjectData();

        projectData.setName(project.getName());
        projectData.setDescription(project.getDescription());
        projectData.setProjectId(project.getProjectId());

        return projectData;
    }

    /**
     * <p>
     * Logs the entrance of a method.
     * </p>
     *
     * <p>
     *     <strong>Version History:</strong>
     *     <ul>
     *         <li>Introduced since version 1.0.</li>
     *         <li>Modified in version 1.1:</li>
     *             <ul>
     *               <li>Check whether the <code>Level.INFO</code> is enabled before logging.</li>
     *             </ul>
     *     </ul>
     * </p>
     *
     * @param methodName
     *            The method to enter.
     *
     * @since 1.0
     */
    private void logEnter(String methodName) {
        doLog(this.getLog(), Level.INFO, null, "Enter into method [ProjectServiceBean#{0}]", methodName);
    }

    /**
     * <p>
     * Logs the exit of a method.
     * </p>
     *
     * <p>
     *     <strong>Version History:</strong>
     *     <ul>
     *         <li>Introduced since version 1.0.</li>
     *         <li>Modified in version 1.1:</li>
     *             <ul>
     *               <li>Check whether the <code>Level.INFO</code> is enabled before logging.</li>
     *             </ul>
     *     </ul>
     * </p>
     *
     * @param methodName
     *            The method to exit.
     *
     * @since 1.0
     */
    private void logExit(String methodName) {
        doLog(this.getLog(), Level.INFO, null, "Exit into method [ProjectServiceBean#{0}]", methodName);
    }

    /**
     * <p>
     * Logs the parameters that passed into the specified method.
     * </p>
     *
     * <p>
     *     <strong>Version History:</strong>
     *     <ul>
     *         <li>Introduced since version 1.0.</li>
     *         <li>Modified in version 1.1:</li>
     *             <ul>
     *               <li>Check whether the <code>Level.INFO</code> is enabled before logging.</li>
     *             </ul>
     *     </ul>
     * </p>
     *
     * @param pattern
     *            The pattern to fill the parameter values.
     * @param parameters
     *            An array of objects to be formatted and substituted.
     *
     * @since 1.0
     */
    private void logParameters(String pattern, Object... parameters) {
        doLog(this.getLog(), Level.INFO, null, "Parameters: " + pattern, parameters);
    }

    /**
     * <p>
     * Logs the returned value by a method call.
     * </p>
     *
     * <p>
     *     <strong>Version History:</strong>
     *     <ul>
     *         <li>Introduced since version 1.0.</li>
     *         <li>Modified in version 1.1:</li>
     *             <ul>
     *               <li>Check whether the <code>Level.INFO</code> is enabled before logging.</li>
     *             </ul>
     *     </ul>
     * </p>
     *
     * @param message
     *            The message to log.
     *
     * @since 1.0
     */
    private void logReturn(String message) {
        doLog(this.getLog(), Level.INFO, null, "Returns: {0}", message);
    }

    /**
     * <p>
     * Logs the exception thrown.
     * </p>
     *
     * <p>
     *     <strong>Version History:</strong>
     *     <ul>
     *         <li>Introduced since version 1.0.</li>
     *         <li>Modified in version 1.1:</li>
     *             <ul>
     *               <li>Check whether the <code>Level.ERROR</code> is enabled before logging.</li>
     *             </ul>
     *     </ul>
     * </p>
     *
     * @param <T> The exception type.
     * @param exception
     *            The exception thrown.
     *
     * @return The logged exception.
     *
     * @since 1.0
     */
    private < T extends Exception > T logException(T exception) {
        doLog(this.getLog(), Level.ERROR, exception, "{0}", exception.getMessage());

        return exception;
    }

    /**
     * <p>
     * Logs the given exception and message with given level and given format.
     * </p>
     *
     * <p>
     * If <code>logger</code> is null, this method does nothing.
     * </p>
     *
     * @param logger The <code>Log</code> to perform logging.
     * @param level The log <code>Level</code>.
     * @param exception The <code>Exception</code> to be logged.
     * @param format The format pattern.
     * @param message The message to be logged.
     *
     * @since 1.1
     */
    private static void doLog(Log logger, Level level, Exception exception, String format, Object... message) {
        //This minimizes the overhead by allowing the formatting to happen as late as possible
        //(certainly after a check to see if logging is enabled).
        //See Logging Wrapper 2.0 CS 4.3.4 for details
        if (canPerformLogging(logger, level)) {
            logger.log(level, exception, format, message);
        }
    }

    /**
     * <p>
     * Check whether can perform logging with given <code>Log</code> at given <code>Level</code>.
     * </p>
     *
     * @param logger The <code>Log</code> to perform logging.
     * @param level The logging <code>Level</code>.
     *
     * @return True if can perform logging with given <code>Log</code> at given <code>Level</code>.
     *         False otherwise.
     *
     * @since 1.1
     */
    private static boolean canPerformLogging(Log logger, Level level) {
        return logger != null && logger.isEnabled(level);
    }

    /**
     * <p>
     * Formats the <code>ProjectData</code> instance into string representation.
     * </p>
     *
     * <p>
     *     <strong>Version History:</strong>
     *     <ul>
     *         <li>Introduced since version 1.0.</li>
     *         <li>Modified in version 1.1:</li>
     *             <ul>
     *               <li>Return fast if can not perform logging.</li>
     *             </ul>
     *     </ul>
     * </p>
     *
     * @param projectData
     *            The <code>ProjectData</code> instance to format.
     * @return The string representation of the <code>ProjectData</code> instance.
     *
     * @since 1.0
     */
    private String formatProjectData(ProjectData projectData) {

        // Return fast
        if (null == projectData || !canPerformLogging(getLog(), Level.INFO)) {
            return null;
        }

        StringBuilder builder = new StringBuilder();
        builder.append("<");
        builder.append("project id: ").append(projectData.getProjectId());
        builder.append(", name: ").append(projectData.getName());
        builder.append(", description: ").append(projectData.getDescription());
        builder.append(">");

        return builder.toString();
    }

    /**
     * <p>
     * Formats the <code>ProjectData</code> instances list into string representation.
     * </p>
     *
     * <p>
     *     <strong>Version History:</strong>
     *     <ul>
     *         <li>Introduced since version 1.0.</li>
     *         <li>Modified in version 1.1:</li>
     *             <ul>
     *               <li>Return fast if can not perform logging.</li>
     *             </ul>
     *     </ul>
     * </p>
     *
     * @param projectDatas
     *            The <code>ProjectData</code> instances list to format.
     * @return The string representation of the <code>ProjectData</code> instances list.
     *
     * @since 1.0
     */
    private String formatProjectDatas(List < ProjectData > projectDatas) {
        // The list of projectDatas never null.

        // Return fast
        if (0 == projectDatas.size() || !canPerformLogging(getLog(), Level.INFO)) {
            return "[]";
        }

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

    /**
     * <p>
     * The enum represents the action to create/update/delete entity. It is only used within this class and
     * is not exposed to external.
     * </p>
     *
     * @author TCSDEVELOPER
     * @version 1.1
     * @since 1.1
     */
    private static enum Action {

        /**
         * <p>
         * Enum represents the action to create entity.
         * </p>
         */
        CREATE,

        /**
         * <p>
         * Enum represents the action to update entity.
         * </p>
         */
        UPDATE,

        /**
         * <p>
         * Enum represents the action to delete entity.
         * </p>
         */
        DELETE
    }


        /**
     * <p>
     * Returns the <code>EntityManager</code> looked up from the session context.
     * </p>
     *
     * @return the EntityManager looked up from the session context
     * @throws ContestManagementException
     *             if fail to get the EntityManager from the sessionContext.
     */
    private EntityManager getEntityManager() throws PersistenceFault {
        try {
            Object obj = sessionContext.lookup(unitName);

            if (obj == null) {
                throw new PersistenceFault("The object for jndi name '" + unitName + "' doesn't exist.");
            }

            return (EntityManager) obj;
        } catch (ClassCastException e) {
            throw new PersistenceFault( "The jndi name for '" + unitName
                    + "' should be EntityManager instance." + e.getMessage());
        }
        //return entityManager;
    }
}
