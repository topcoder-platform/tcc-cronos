/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.facade.project;

import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.ClientStatus;
import com.topcoder.clients.model.Company;
import com.topcoder.clients.model.Project;
import com.topcoder.clients.model.ProjectStatus;
import com.topcoder.service.project.IllegalArgumentFault;
import com.topcoder.service.project.PersistenceFault;
import com.topcoder.service.project.ProjectData;
import com.topcoder.service.project.ProjectNotFoundFault;
import com.topcoder.service.project.AuthorizationFailedFault;
import com.topcoder.service.project.UserNotFoundFault;
import com.topcoder.service.project.ProjectHasCompetitionsFault;
import com.topcoder.service.project.ProjectService;

import javax.jws.WebService;

import java.util.List;

/**
 * <p>An interface for the web service implementing unified <code>Facade</code> interface to various service components
 * provided by global <code>TopCoder</code> application.</p>
 *
 * <p>As of this version a facade to <code>Project Service</code> is provided only.</p>
 *
 * @author isv
 * @version 1.0
 */
@WebService(name = "ProjectServiceFacade")
public interface ProjectServiceFacade {

    /**
     * <p>Creates a project with the given project data.</p>
     *
     * <p>Note, any user can create project and the project will associate with him/her.</p>
     *
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
    ProjectData createProject(ProjectData projectData) throws PersistenceFault, IllegalArgumentFault;

    /**
     * <p>Gets the project data for the project with the given Id.</p>
     *
     * <p>Notes, only associated user can retrieve the specified project, administrator can retrieve any projects.</p>
     *
     * @param projectId the ID of the project to be retrieved.
     * @return The project data for the project with the given Id. Will never be null.
     * @throws PersistenceFault if a generic persistence error.
     * @throws ProjectNotFoundFault if no project with the given ID exists.
     * @throws AuthorizationFailedFault if the calling principal is not authorized to retrieve the project.
     * @see ProjectService#getProject(long)
     */
    ProjectData getProject(long projectId) throws PersistenceFault, ProjectNotFoundFault, AuthorizationFailedFault;

    /**
     * <p>Gets the project data for all projects of the given user.</p>
     *
     * <p>Notes, only administrator can do this.</p>
     *
     * @param userId the ID of the user whose projects are to be retrieved.
     * @return The project data for all projects of the given user. The returned collection will not be null or contain
     *         nulls. Will never be empty.
     * @throws UserNotFoundFault if there are no projects linked to the given user.
     * @throws PersistenceFault if a generic persistence error occurs.
     * @throws AuthorizationFailedFault should not be thrown from version 1.1. It is here only to leave the web
     *         interface unchanged.
     * @see ProjectService#getProjectsForUser(long)
     */
    List< ProjectData > getProjectsForUser(long userId) throws PersistenceFault, UserNotFoundFault,
                                                               AuthorizationFailedFault;

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
     *
     * @return The project data for all projects viewable from the calling principal. The returned collection will not
     *         be null or contain nulls. Possibly empty.
     * @throws PersistenceFault if a generic persistence error occurs.
     * @throws AuthorizationFailedFault if errors occurs during authorization of the caller user.
     * @throws UserNotFoundFault if errors occurs during authorization of the caller user.
     * 
     * @see ProjectService#getAllProjects()
     */
    List < ProjectData > getAllProjects() throws PersistenceFault, AuthorizationFailedFault, UserNotFoundFault;

    /**
     * <p>Updates the project data for the given project.</p>
     *
     * <p>Notes, only project-associated user can update that project, but administrator can update any project.</p>
     *
     * @param projectData
     *            The project data to be updated. Must not be null.
     *            The <code>ProjectData.name</code> must not be null/empty.
     *            The <code>ProjectData.projectId</code> must be non-null.
     * @throws IllegalArgumentFault if the given <code>ProjectData</code> is illegal.
     * @throws ProjectNotFoundFault if no project with the given ID exists.
     * @throws AuthorizationFailedFault if the calling principal is not authorized to update the project.
     * @throws PersistenceFault if a generic persistence error.
     * @see ProjectService#updateProject(ProjectData)
     */
    void updateProject(ProjectData projectData) throws PersistenceFault, ProjectNotFoundFault,
                                                       AuthorizationFailedFault, IllegalArgumentFault;

    /**
     * <p>Deletes the project data for the project with the given Id.</p>
     *
     * <p>Notes, only project-associated user can delete that project, but administrator can delete any project.</p>
     *
     * @param projectId the ID of the project be deleted.
     * @return Whether the project was found, and thus deleted.
     * @throws AuthorizationFailedFault if the calling principal is not authorized to delete the project.
     * @throws ProjectHasCompetitionsFault if the project cannot be deleted since it has competitions associated with
     *         it.
     * @throws PersistenceFault if a generic persistence error.
     * @see ProjectService#deleteProject(long)
     */
    boolean deleteProject(long projectId) throws PersistenceFault, AuthorizationFailedFault,
                                                 ProjectHasCompetitionsFault;
    
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
    List<Project> getClientProjectsForClient(Client client) throws DAOFault;
    
    /**
     * <p>
     * Defines the operation that performs the retrieval of the list with
     * projects with the given user id. If nothing is found, return an empty list.
     * <p>
     * @return List of Project, if nothing is found, return an empty string
     * @throws DAOException if any error occurs while performing this operation.
     */
    public List<Project> getClientProjectsByUser() throws DAOFault;
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
    Client retrieveClientById(Long id) throws EntityNotFoundFault, DAOFault;

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
    List<Client> retrieveAllClients() throws DAOFault;

    /**
     * <p>
     * Defines the operation that performs the retrieval of all entities that
     * have the given name in the persistence. If nothing is found, return an
     * empty list.
     * </p>
     *
     * @param name
     *                the name of the entity that should be retrieved. Should be
     *                not empty and not null.
     * @return the list with entities with the given name retrieved from the
     *         persistence. If nothing is found, return an empty list.
     * @throws DAOFault
     *                 if any error occurs while performing this operation.
     */
    List<Client> searchClientsByName(String name) throws DAOFault;

    /**
     * <p>
     * Defines the operation that performs the salvation of an entity in the
     * persistence.
     * </p>
     *
     * @param entity
     *                the entity that should be saved. Should not be null.
     * @return the entity saved in the persistence.
     * @throws IllegalArgumentException
     *                 if entity is null.
     * @throws DAOFault
     *                 if any error occurs while performing this operation.
     */
    Client saveClient(Client entity) throws DAOFault;

    /**
     * <p>
     * Defines the operation that performs the deletion of an entity from the
     * persistence.
     * </p>
     *
     * @param entity
     *                the entity that should be deleted from the persistence.
     *                Should not be null.
     * @throws IllegalArgumentException
     *                 if entity is null.
     * @throws EntityNotFoundFault
     *                 if entity is not found in the persistence.
     * @throws DAOFault
     *                 if any error occurs while performing this operation.
     */
    void deleteClient(Client entity) throws EntityNotFoundFault, DAOFault;
    
    /**
     * <p>
     * Defines the operation that performs the retrieval of the list with
     * clients with the given status from the persistence. If nothing is found,
     * return an empty list.
     * </p>
     *
     * @param status
     *                the given client status to retrieve it's clients. Should
     *                not be null.
     * @return the list of Clients for the given client status found in the
     *         persistence. If nothing is found, return an empty list.
     * @throws IllegalArgumentException
     *                 if status is null.
     * @throws EntityNotFoundFault
     *                 if status is not found in the persistence.
     * @throws DAOFault
     *                 if any error occurs while performing this operation.
     */
    List<Client> getClientsWithStatus(ClientStatus status) throws EntityNotFoundFault, DAOFault;
    
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
    ClientStatus retrieveClientStatusById(Long id) throws EntityNotFoundFault, DAOFault;

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
    List<ClientStatus> retrieveAllClientStatus() throws DAOFault;

    /**
     * <p>
     * Defines the operation that performs the retrieval of all entities that
     * have the given name in the persistence. If nothing is found, return an
     * empty list.
     * </p>
     *
     * @param name
     *                the name of the entity that should be retrieved. Should be
     *                not empty and not null.
     * @return the list with entities with the given name retrieved from the
     *         persistence. If nothing is found, return an empty list.
     * @throws DAOFault
     *                 if any error occurs while performing this operation.
     */
    List<ClientStatus> searchClientStatusByName(String name) throws DAOFault;

    /**
     * <p>
     * Defines the operation that performs the salvation of an entity in the
     * persistence.
     * </p>
     *
     * @param entity
     *                the entity that should be saved. Should not be null.
     * @return the entity saved in the persistence.
     * @throws IllegalArgumentException
     *                 if entity is null.
     * @throws DAOFault
     *                 if any error occurs while performing this operation.
     */
    ClientStatus saveClientStatus(ClientStatus entity) throws DAOFault;

    /**
     * <p>
     * Defines the operation that performs the deletion of an entity from the
     * persistence.
     * </p>
     *
     * @param entity
     *                the entity that should be deleted from the persistence.
     *                Should not be null.
     * @throws IllegalArgumentException
     *                 if entity is null.
     * @throws EntityNotFoundFault
     *                 if entity is not found in the persistence.
     * @throws DAOFault
     *                 if any error occurs while performing this operation.
     */
    void deleteClientStatus(ClientStatus entity) throws EntityNotFoundFault, DAOFault;
    /**
     * <p>
     * Defines the operation that performs the retrieval of the list with clients for the given company from the
     * persistence. If nothing is found, return an empty list.
     * </p>
     *
     * @param company
     *            the given company to retrieve it's clients. Should not be null.
     * @return the list of Clients for the given company found in the persistence. If nothing is found, return an empty
     *         list.
     * @throws IllegalArgumentException
     *             if company is null.
     * @throws EntityNotFoundFault
     *             if company is not found in the persistence.
     * @throws DAOFault
     *             if any error occurs while performing this operation.
     */
    List<Client> getClientsForCompany(Company company) throws DAOFault;

    /**
     * <p>
     * Defines the operation that performs the retrieval of the list with projects for the given company from the
     * persistence. If nothing is found, return an empty list.
     * </p>
     *
     * @param company
     *            the given company to retrieve it's projects. Should not be null.
     * @return the list of Projects for the given company found in the persistence. If nothing is found, return an empty
     *         list.
     * @throws IllegalArgumentException
     *             if company is null.
     * @throws EntityNotFoundFault
     *             if company is not found in the persistence.
     * @throws DAOFault
     *             if any error occurs while performing this operation.
     */
    List<Project> getClientProjectsForCompany(Company company) throws DAOFault;
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
    Company retrieveCompanyById(Long id) throws EntityNotFoundFault, DAOFault;

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
    List<Company> retrieveAllCompanies() throws DAOFault;

    /**
     * <p>
     * Defines the operation that performs the retrieval of all entities that
     * have the given name in the persistence. If nothing is found, return an
     * empty list.
     * </p>
     *
     * @param name
     *                the name of the entity that should be retrieved. Should be
     *                not empty and not null.
     * @return the list with entities with the given name retrieved from the
     *         persistence. If nothing is found, return an empty list.
     * @throws DAOFault
     *                 if any error occurs while performing this operation.
     */
    List<Company> searchCompaniesByName(String name) throws DAOFault;


    /**
     * <p>
     * Defines the operation that performs the salvation of an entity in the
     * persistence.
     * </p>
     *
     * @param entity
     *                the entity that should be saved. Should not be null.
     * @return the entity saved in the persistence.
     * @throws IllegalArgumentException
     *                 if entity is null.
     * @throws DAOFault
     *                 if any error occurs while performing this operation.
     */
    Company saveCompany(Company entity) throws DAOFault;

    /**
     * <p>
     * Defines the operation that performs the deletion of an entity from the
     * persistence.
     * </p>
     *
     * @param entity
     *                the entity that should be deleted from the persistence.
     *                Should not be null.
     * @throws IllegalArgumentException
     *                 if entity is null.
     * @throws EntityNotFoundFault
     *                 if entity is not found in the persistence.
     * @throws DAOFault
     *                 if any error occurs while performing this operation.
     */
    void deleteCompany(Company entity) throws EntityNotFoundFault, DAOFault;
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
    Project retrieveClientProjectById(Long id, boolean includeChildren) throws EntityNotFoundFault, DAOFault;

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
    List<Project> retrieveClientProjects(boolean includeChildren) throws DAOFault;
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
    Project retrieveClientProjectByProjectId(Long id) throws EntityNotFoundFault, DAOFault;

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
    List<Project> retrieveAllClientProjects() throws DAOFault;

    /**
     * <p>
     * Defines the operation that performs the retrieval of all entities that
     * have the given name in the persistence. If nothing is found, return an
     * empty list.
     * </p>
     *
     * @param name
     *                the name of the entity that should be retrieved. Should be
     *                not empty and not null.
     * @return the list with entities with the given name retrieved from the
     *         persistence. If nothing is found, return an empty list.
     * @throws DAOFault
     *                 if any error occurs while performing this operation.
     */
    List<Project> searchClientProjectsByName(String name) throws DAOFault;

    /**
     * <p>
     * Defines the operation that performs the salvation of an entity in the
     * persistence.
     * </p>
     *
     * @param entity
     *                the entity that should be saved. Should not be null.
     * @return the entity saved in the persistence.
     * @throws IllegalArgumentException
     *                 if entity is null.
     * @throws DAOFault
     *                 if any error occurs while performing this operation.
     */
    Project saveClientProject(Project entity) throws DAOFault;

    /**
     * <p>
     * Defines the operation that performs the deletion of an entity from the
     * persistence.
     * </p>
     *
     * @param entity
     *                the entity that should be deleted from the persistence.
     *                Should not be null.
     * @throws IllegalArgumentException
     *                 if entity is null.
     * @throws EntityNotFoundFault
     *                 if entity is not found in the persistence.
     * @throws DAOFault
     *                 if any error occurs while performing this operation.
     */
    void deleteClientProject(Project entity) throws EntityNotFoundFault, DAOFault;
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
    List<Project> getClientProjectsWithStatus(ProjectStatus status) throws DAOFault;
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
    ProjectStatus retrieveProjectStatusById(Long id) throws EntityNotFoundFault, DAOFault;

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
    List<ProjectStatus> retrieveAllProjectStatus() throws DAOFault;

    /**
     * <p>
     * Defines the operation that performs the retrieval of all entities that
     * have the given name in the persistence. If nothing is found, return an
     * empty list.
     * </p>
     *
     * @param name
     *                the name of the entity that should be retrieved. Should be
     *                not empty and not null.
     * @return the list with entities with the given name retrieved from the
     *         persistence. If nothing is found, return an empty list.
     * @throws DAOFault
     *                 if any error occurs while performing this operation.
     */
    List<ProjectStatus> searchProjectStatusByName(String name) throws DAOFault;

    /**
     * <p>
     * Defines the operation that performs the salvation of an entity in the
     * persistence.
     * </p>
     *
     * @param entity
     *                the entity that should be saved. Should not be null.
     * @return the entity saved in the persistence.
     * @throws IllegalArgumentException
     *                 if entity is null.
     * @throws DAOFault
     *                 if any error occurs while performing this operation.
     */
    ProjectStatus saveProjectStatus(ProjectStatus entity) throws DAOFault;

    /**
     * <p>
     * Defines the operation that performs the deletion of an entity from the
     * persistence.
     * </p>
     *
     * @param entity
     *                the entity that should be deleted from the persistence.
     *                Should not be null.
     * @throws IllegalArgumentException
     *                 if entity is null.
     * @throws EntityNotFoundFault
     *                 if entity is not found in the persistence.
     * @throws DAOFault
     *                 if any error occurs while performing this operation.
     */
    void deleteProjectStatus(ProjectStatus entity) throws EntityNotFoundFault, DAOFault;
}