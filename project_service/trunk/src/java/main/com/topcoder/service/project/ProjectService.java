/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.project;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.jws.WebService;

/**
 * <p>
 * This interface is annotated as a web service and defines the contract for the service methods which must be
 * implemented. The service methods provide CRUD functionality for the ProjectData entity. The contract lays down 6
 * methods - 4 simple CRUD methods and 2 additional retrieve methods that allow retrieval by user, and retrieval of all
 * projects. All methods also have the RolesAllowed annotation on them to take advantage of EJB container security.
 * </p>
 * <p>
 * Implementations will provide the logic to implement the service methods. This may be done through a pluggable
 * persistence mechanism (using the <code>{@link ProjectPersistence}</code> interface) but is not necessary.
 * </p>
 * <p>
 * <b>Thread Safety</b>: Implementations must be thread safe.
 * </p>
 *
 * @author humblefool, FireIce
 * @version 1.0
 */
@WebService
public interface ProjectService {
    /**
     * <p>
     * Creates a project with the given project data.
     * </p>
     * <p>
     * Notes, any user can create project, the project will associate with him/her.
     * </p>
     *
     * @param projectData
     *            The project data. Must not be null. The name must also not be null/empty. The ProjectId ,
     *            if any, is ignored.
     * @return The project as it was created, with the projectId set.
     * @throws PersistenceFault
     *             If a generic persistence error.
     * @throws IllegalArgumentFault
     *             If the arg given was illegal.
     */
    ProjectData createProject(ProjectData projectData) throws PersistenceFault, IllegalArgumentFault;

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
    ProjectData getProject(long projectId) throws PersistenceFault, ProjectNotFoundFault,
        AuthorizationFailedFault;

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
    List<ProjectData> getProjectsForUser(long userId) throws PersistenceFault, AuthorizationFailedFault,
        UserNotFoundFault;

    /**
     * <p>
     * Gets the project data for all projects viewable from the calling principal.
     * </p>
     * <p>
     * Notes, for user, it will return all the projects associated with him,
     * administrator will return all the projects.
     * </p>
     *
     * @return The project data for all projects viewable from the calling principal.
     * @throws PersistenceFault
     *             If a generic persistence error.
     */
    List<ProjectData> getAllProjects() throws PersistenceFault;

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
    void updateProject(ProjectData projectData) throws PersistenceFault, ProjectNotFoundFault,
        AuthorizationFailedFault, IllegalArgumentFault;

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
    boolean deleteProject(long projectId) throws PersistenceFault, AuthorizationFailedFault,
        ProjectHasCompetitionsFault;
}
