/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.project;

import java.util.List;

import javax.jws.WebService;

/**
 * <p>
 * This interface is annotated as a web service and defines the contract for the service methods which must be
 * implemented. The service methods provide CRUD functionality for the <code>ProjectData</code> entity.
 * </p>
 *
 * <p>
 * The contract lays down 6 methods - 4 simple CRUD methods and 2 additional retrieve methods that allow retrieval
 * by user, and retrieval of all projects.
 * </p>
 *
 * <p>
 * Implementations will provide the logic to implement the service methods. This may be done through a container
 * managed <code>EntityManager</code>.
 * </p>
 *
 * <p>
 *     <strong>Version History:</strong>
 *     <ul>
 *         <li>Introduced since version 1.0.</li>
 *         <li>Modified in version 1.1:</li>
 *             <ul>
 *               <li>The implementation of <code>getProjectsForUser()</code> method should no longer perform
 *               role-based authorization in code. It should rely instead (as always did) on EJB declarative
 *               security.</li>
 *             </ul>
 *     </ul>
 * </p>
 *
 * <p>
 * <b>Thread Safety</b>: Implementations must be thread safe.
 * </p>
 *
 * @author humblefool, FireIce
 * @author ThinMan, TCSDEVELOPER
 * @version 1.1
 * @since 1.0
 */
@WebService
public interface ProjectService {

    /**
     * <p>
     * Creates a project with the given project data.
     * </p>
     *
     * <p>
     * Note, any user can create project and the project will associate with him/her.
     * </p>
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
     *
     * @since 1.0
     */
    ProjectData createProject(ProjectData projectData) throws PersistenceFault, IllegalArgumentFault;

    /**
     * <p>
     * Gets the project data for the project with the given Id.
     * </p>
     *
     * <p>
     * Notes, only associated user can retrieve the specified project, administrator can retrieve any projects.
     * </p>
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
     *
     * @since 1.0
     */
    ProjectData getProject(long projectId) throws PersistenceFault, ProjectNotFoundFault,
        AuthorizationFailedFault;

    /**
     * <p>
     * Gets the project data for all projects of the given user.
     * </p>
     *
     * <p>
     * Notes, only administrator can do this.
     * </p>
     *
     * <p>
     *     <strong>Version History:</strong>
     *     <ul>
     *         <li>Introduced since version 1.0.</li>
     *         <li>Modified in version 1.1:</li>
     *             <ul>
     *               <li>The implementation should no longer perform role-based authorization in code.
     *               It should rely instead (as always did) on EJB declarative security.</li>
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
     * @throws AuthorizationFailedFault
     *             Should not be thrown from version1.1. It is here only to leave the web interface unchanged.
     *
     * @since 1.0
     */
    List < ProjectData > getProjectsForUser(long userId) throws PersistenceFault, UserNotFoundFault,
        AuthorizationFailedFault;

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
     * @return The project data for all projects viewable from the calling principal. The returned collection will not
     *         be null or contain nulls. Possibly empty.
     *
     * @throws PersistenceFault
     *             If a generic persistence error occurs.
     *
     * @since 1.0
     */
    List < ProjectData > getAllProjects() throws PersistenceFault;

    /**
     * <p>
     * Updates the project data for the given project.
     * </p>
     *
     * <p>
     * Notes, only project-associated user can update that project, but administrator can update any project.
     * </p>
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
     *
     * @since 1.0
     */
    void updateProject(ProjectData projectData) throws PersistenceFault, ProjectNotFoundFault,
        AuthorizationFailedFault, IllegalArgumentFault;

    /**
     * <p>
     * Deletes the project data for the project with the given Id.
     * </p>
     *
     * <p>
     * Notes, only project-associated user can delete that project, but administrator can delete any project.
     * </p>
     *
     * @param projectId
     *            The ID of the project be deleted.
     * @return Whether the project was found, and thus deleted.
     *
     * @throws AuthorizationFailedFault
     *             If the calling principal is not authorized to delete the project.
     * @throws ProjectHasCompetitionsFault
     *             If the project cannot be deleted since it has competitions associated with it.
     * @throws PersistenceFault
     *             If a generic persistence error.
     *
     * @since 1.0
     */
    boolean deleteProject(long projectId) throws PersistenceFault, AuthorizationFailedFault,
        ProjectHasCompetitionsFault;
}
