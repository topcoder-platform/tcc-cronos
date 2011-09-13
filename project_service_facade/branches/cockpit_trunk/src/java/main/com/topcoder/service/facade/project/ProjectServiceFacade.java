/*
 * Copyright (C) 2008-2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.facade.project;

import java.util.List;

import com.topcoder.clients.dao.DAOException;
import com.topcoder.clients.model.Project;
import com.topcoder.security.TCSubject;
import com.topcoder.service.project.AuthorizationFailedFault;
import com.topcoder.service.project.IllegalArgumentFault;
import com.topcoder.service.project.PersistenceFault;
import com.topcoder.service.project.ProjectData;
import com.topcoder.service.project.ProjectNotFoundFault;
import com.topcoder.service.project.ProjectService;
import com.topcoder.service.project.UserNotFoundFault;

/**
 * <p>An interface for the web service implementing unified <code>Facade</code> interface to various service components
 * provided by global <code>TopCoder</code> application.</p>
 *
 * <p>As of this version a facade to <code>Project Service</code> is provided only.</p>
 *
 * <p>
 * Changes in v1.0.2(Cockpit Security Facade V1.0):
 *  - It is not a web-service facade any more.
 *  - All the methods accepts a parameter TCSubject which contains all the security info for current user.
 *    The implementation EJB should use TCSubject and now get these info from the sessionContext.
 *  - Please use the new ProjectServiceFacadeWebService as the facade now. That interface will delegates all the methods
 *    to this interface.
 * </p>
 *
 * <p>
 *     Version 1.0.3 changes:
 *     - add method {@link #updateProject(com.topcoder.security.TCSubject, ProjectData)}
 * </p>
 *
 * @author isv, GreatKevin
 * @version 1.0.3
 */
public interface ProjectServiceFacade {

    /**
     * <p>Creates a project with the given project data.</p>
     *
     * <p>Note, any user can create project and the project will associate with him/her.</p>
     * <p>
     * Update in v1.0.2: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param projectData
     *            The project data to be created. Must not be null.
     *            The <code>ProjectData.name</code> must not be null/empty.
     *            The <code>ProjectData.projectId</code>, if any, is ignored.
     * @return The project as it was created, with the <code>ProjectData.projectId</code> and <code>ProjectData.userId
     *         </code> set. Will never be null.
     * @throws IllegalArgumentFault if the given <code>ProjectData</code> is illegal.
     * @throws PersistenceFault if a generic persistence error occurs.
     * @see ProjectService#createProject(ProjectData)
     */
    ProjectData createProject(TCSubject tcSubject, ProjectData projectData) throws PersistenceFault, IllegalArgumentFault;
     /**
     * <p>Gets the project data for the project with the given Id.</p>
     *
     * <p>Notes, only associated user can retrieve the specified project, administrator can retrieve any projects.</p>
     * <p>
     * Update in v1.0.2: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param projectId the ID of the project to be retrieved.
     * @return The project data for the project with the given Id. Will never be null.
     * @throws PersistenceFault if a generic persistence error.
     * @throws ProjectNotFoundFault if no project with the given ID exists.
     * @throws AuthorizationFailedFault if the calling principal is not authorized to retrieve the project.
     * @see ProjectService#getProject(long)
     */
    ProjectData getProject(TCSubject tcSubject, long projectId) throws PersistenceFault, ProjectNotFoundFault, AuthorizationFailedFault;

    /**
     * <p>Gets the project data for all projects of the given user.</p>
     *
     * <p>Notes, only administrator can do this.</p>
     *<p>
     * Update in v1.0.2: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param userId the ID of the user whose projects are to be retrieved.
     * @return The project data for all projects of the given user. The returned collection will not be null or contain
     *         nulls. Will never be empty.
     * @throws UserNotFoundFault if there are no projects linked to the given user.
     * @throws PersistenceFault if a generic persistence error occurs.
     * @throws AuthorizationFailedFault should not be thrown from version 1.1. It is here only to leave the web
     *         interface unchanged.
     * @see ProjectService#getProjectsForUser(long)
     */
    List< ProjectData > getProjectsForUser(TCSubject tcSubject, long userId) throws PersistenceFault, UserNotFoundFault,
                                                               AuthorizationFailedFault;

    /**
     * <p>
     * Updates the project data.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param projectData The project data to be updated. Must not be null.
     * @throws PersistenceFault if a generic persistence error occurs.
     * @throws ProjectNotFoundFault if no project with the given ID exists.
     * @throws AuthorizationFailedFault if the calling principal is not authorized to retrieve the project.
     * @throws IllegalArgumentFault if the arguments are invalid.
     * @since 1.0.3
     */
    void updateProject(TCSubject tcSubject, ProjectData projectData) throws  PersistenceFault, ProjectNotFoundFault,
                                                                        AuthorizationFailedFault, IllegalArgumentFault;

    /**
     * <p>Gets the project data for all projects viewable from the calling principal.</p>
     *
     * <p>Notes, for user, it will retrieve only the projects associated with him; for administrators, it will retrieve
     * all the existing projects.</p>
     *
     * <p>
     * Updated for Cockpit Project Admin Release Assembly v1.0
     *      - Added check for admin user, if admin user then all projects are loaded else only for the user.
     * </p>
     * <p>
     * Update in v1.0.2: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @return The project data for all projects viewable from the calling principal. The returned collection will not
     *         be null or contain nulls. Possibly empty.
     * @throws PersistenceFault if a generic persistence error occurs.
     * @throws AuthorizationFailedFault if errors occurs during authorization of the caller user.
     * @throws UserNotFoundFault if errors occurs during authorization of the caller user.
     *
     * @see ProjectService#getAllProjects()
     */
    List < ProjectData > getAllProjects(TCSubject tcSubject) throws PersistenceFault, AuthorizationFailedFault, UserNotFoundFault;


    /**
     * <p>
     * Defines the operation that performs the retrieval of the list with
     * projects with the given client from the persistence. If nothing is found,
     * return an empty list.
     * </p>
     *
     * @param client
     *                the given clients to retrieve it's projects. Should not be
     *                null.
     * @return the list of Projects for the given client found in the
     *         persistence. If nothing is found, return an empty list.
     * @throws IllegalArgumentException
     *                 if client is null.
     * @throws EntityNotFoundFault
     *                 if client is not found in the persistence.
     * @throws DAOFault
     *                 if any error occurs while performing this operation.
     */
    //List<Project> getClientProjectsForClient(Client client) throws DAOFault;

    /**
     * <p>
     * Defines the operation that performs the retrieval of the list with
     * projects with the given user id. If nothing is found, return an empty list.
     * <p>
     * <p>
     * Update in v1.0.2: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @return List of Project, if nothing is found, return an empty string
     * @throws DAOException if any error occurs while performing this operation.
     */
    public List<Project> getClientProjectsByUser(TCSubject tcSubject) throws DAOFault;




    /**
     * <p>
     * Defines the operation that performs the retrieval of a project using the
     * given id from the persistence. If include children is true return the
     * Project.childProjects list too; otherwise the list should not be
     * returned.
     * </p>
     *
     * @param id
     *                the identifier of the Project that should be retrieved.
     *                Should be positive and not null.
     * @param includeChildren
     *                the flag that mention if the Project.childrenProjects list
     *                should be returned or not.
     * @return the Project with the given id retrieved from the persistence.
     * @throws IllegalArgumentException
     *                 if id <= 0 or id is null.
     * @throws EntityNotFoundFault
     *                 if id is not found in the persistence.
     * @throws DAOFault
     *                 if any error occurs while performing this operation.
     */
    //Project retrieveClientProjectById(Long id, boolean includeChildren) throws EntityNotFoundFault, DAOFault;

    /**
     * <p>
     * Defines the operation that performs the retrieval of all projects from
     * the persistence. If include children is true return the
     * Project.childProjects list too; otherwise the list should not be
     * returned. If nothing is found, return an empty list.
     * </p>
     *
     * @param includeChildren
     *                the flag that mention if the Project.childrenProjects list
     *                should be returned or not.
     * @return the list of Projects found in the persistence. If nothing is
     *         found, return an empty list.
     * @throws DAOFault
     *                 if any error occurs while performing this operation.
     */
    //List<Project> retrieveClientProjects(boolean includeChildren) throws DAOFault;
    /**
     * <p>
     * Defines the operation that performs the retrieval of an entity using the
     * given id from the persistence.
     * </p>
     *
     * @param id
     *                the identifier of the entity that should be retrieved.
     *                Should be positive and not null.
     * @return the entity with the given id retrieved from the persistence.
     * @throws IllegalArgumentException
     *                 if id <= 0 or id is null.
     * @throws EntityNotFoundFault
     *                 if an entity for the given id is not found in the
     *                 persistence.
     * @throws DAOFault
     *                 if any error occurs while performing this operation.
     */
    //Project retrieveClientProjectByProjectId(Long id) throws EntityNotFoundFault, DAOFault;

    /**
     * <p>
     * Defines the operation that performs the retrieval of all entities from
     * the persistence. If nothing is found, return an empty list.
     * </p>
     *
     * @return the list of entities found in the persistence. If nothing is
     *         found, return an empty list.
     * @throws DAOFault
     *                 if any error occurs while performing this operation.
     */
    //List<Project> retrieveAllClientProjects() throws DAOFault;

    /**
     * <p>
     * Defines the operation that performs the retrieval of the list with
     * projects with the given status from the persistence. If nothing is found,
     * return an empty list.
     * </p>
     *
     * @param status
     *                the given project status to retrieve it's projects. Should
     *                not be null.
     * @return the list of Projects for the given project status found in the
     *         persistence. If nothing is found, return an empty list.
     * @throws IllegalArgumentException
     *                 if status is null.
     * @throws EntityNotFoundFault
     *                 if status is not found in the persistence.
     * @throws DAOFault
     *                 if any error occurs while performing this operation.
     */
    //List<Project> getClientProjectsWithStatus(ProjectStatus status) throws DAOFault;
    /**
     * <p>
     * Defines the operation that performs the retrieval of an entity using the
     * given id from the persistence.
     * </p>
     *
     * @param id
     *                the identifier of the entity that should be retrieved.
     *                Should be positive and not null.
     * @return the entity with the given id retrieved from the persistence.
     * @throws IllegalArgumentException
     *                 if id <= 0 or id is null.
     * @throws EntityNotFoundFault
     *                 if an entity for the given id is not found in the
     *                 persistence.
     * @throws DAOFault
     *                 if any error occurs while performing this operation.
     */
    //ProjectStatus retrieveProjectStatusById(Long id) throws EntityNotFoundFault, DAOFault;




}
