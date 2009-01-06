/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.facade.project;

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
     * @return The project data for all projects viewable from the calling principal. The returned collection will not
     *         be null or contain nulls. Possibly empty.
     * @throws PersistenceFault if a generic persistence error occurs.
     * @see ProjectService#getAllProjects()
     */
    List < ProjectData > getAllProjects() throws PersistenceFault;

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
}
