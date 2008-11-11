/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.widgets.bridge.mocks;

import java.util.ArrayList;
import java.util.List;

import com.topcoder.service.project.AuthorizationFailedFault;
import com.topcoder.service.project.IllegalArgumentFault;
import com.topcoder.service.project.PersistenceFault;
import com.topcoder.service.project.ProjectData;
import com.topcoder.service.project.ProjectHasCompetitionsFault;
import com.topcoder.service.project.ProjectNotFoundFault;
import com.topcoder.service.project.ProjectService;
import com.topcoder.service.project.UserNotFoundFault;

/**
 * <p>
 * Mock implementation for Project Services.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockProjectService implements ProjectService {
    /**
     * <p>
     * Mock implementation.
     * </p>
     *
     * @param projectData The project data. Must not be null. The name must also not be null/empty. The ProjectId , if
     *            any, is ignored.
     * @return The project as it was created, with the projectId set.
     * @throws PersistenceFault If a generic persistence error.
     * @throws IllegalArgumentFault If the arg given was illegal.
     */
    public ProjectData createProject(ProjectData projectData) throws PersistenceFault, IllegalArgumentFault {
        if (projectData.getProjectId() == 1) {
            return projectData;
        } else {
            throw new PersistenceFault("Problem creating the project");
        }
    }

    /**
     * <p>
     * Mock implementation.
     * </p>
     *
     * @param projectId The ID of the project.
     * @return Whether the project was found, and thus deleted.
     * @throws PersistenceFault If a generic persistence error.
     * @throws AuthorizationFailedFault If the calling principal is not authorized to delete the project.
     * @throws ProjectHasCompetitionsFault If the project cannot be deleted since it has competitions associated with
     *             it.
     */
    public boolean deleteProject(long projectId) throws PersistenceFault, AuthorizationFailedFault,
        ProjectHasCompetitionsFault {
        if (projectId == 1) {
            return true; // do nothing
        } else {
            throw new PersistenceFault("Problem deleting the project");
        }
    }

    /**
     * <p>
     * Mock implementation.
     * </p>
     *
     * @return The project data for all projects viewable from the calling principal.
     * @throws PersistenceFault If a generic persistence error.
     */
    public List<ProjectData> getAllProjects() throws PersistenceFault {
        List<ProjectData> projects = new ArrayList<ProjectData>();
        ProjectData proj = new ProjectData();
        proj.setProjectId(1L);
        proj.setName("Get Project for User");
        proj.setDescription("Get Project for User Desc");
        projects.add(proj);

        ProjectData proj2 = new ProjectData();
        proj2.setProjectId(2L);
        proj2.setName("Get Project2 for User 2");
        proj2.setDescription("Get Project2 for User Desc2");
        projects.add(proj2);

        return projects;
    }

    /**
     * <p>
     * Mock implementation.
     * </p>
     *
     * @param projectId The ID of the project.
     * @return The project data for the project with the given Id.
     * @throws PersistenceFault If a generic persistence error.
     * @throws ProjectNotFoundFault If no project with the given ID exists.
     * @throws AuthorizationFailedFault If the calling principal is not authorized to retrieve the project.
     */
    public ProjectData getProject(long projectId) throws PersistenceFault, ProjectNotFoundFault,
        AuthorizationFailedFault {
        if (projectId == 1) {
            ProjectData proj = new ProjectData();
            proj.setProjectId(1L);
            proj.setName("Get ProjectData");
            proj.setDescription("Get ProjectData");
            return proj;
        } else {
            throw new ProjectNotFoundFault("Problem retrieving the project for project id " + projectId);
        }
    }

    /**
     * <p>
     * Mock implementation.
     * </p>
     *
     * @param userId The ID of the user whose projects are to be retrieved.
     * @return The project data for all projects of the given user.
     * @throws PersistenceFault If a generic persistence error.
     * @throws AuthorizationFailedFault If the calling principal is not authorized to retrieve the projects.
     * @throws UserNotFoundFault If there are no projects linked to the given user.
     */
    public List<ProjectData> getProjectsForUser(long userId) throws PersistenceFault, AuthorizationFailedFault,
        UserNotFoundFault {
        if (userId == 1) {
            List<ProjectData> projects = new ArrayList<ProjectData>();
            ProjectData proj = new ProjectData();
            proj.setProjectId(1L);
            proj.setName("Get ProjectData for User");
            proj.setDescription("Get ProjectData for User Desc");
            projects.add(proj);

            ProjectData proj2 = new ProjectData();
            proj2.setProjectId(2L);
            proj2.setName("Get Project2 for User 2");
            proj2.setDescription("Get Project2 for User Desc2");
            projects.add(proj2);

            return projects;
        } else if (userId == 50) {
            throw new UserNotFoundFault("User not found for id of 50");
        } else if (userId == 51) {
            throw new AuthorizationFailedFault("User not authorized for this function with id of 51");
        } else if (userId == 52) {
            throw new PersistenceFault("Project not found for user");
        } else {
            return new ArrayList<ProjectData>();
        }
    }

    /**
     * <p>
     * Mock implementation.
     * </p>
     *
     * @param projectData The project data. Must not be null. The name,description must also not be null/empty. The
     *            ProjectId must be non-null.
     * @throws PersistenceFault If a generic persistence error.
     * @throws ProjectNotFoundFault If no project with the given ID exists.
     * @throws AuthorizationFailedFault If the calling principal is not authorized to update the project.
     * @throws IllegalArgumentFault If the arg given was illegal.
     */
    public void updateProject(ProjectData projectData) throws PersistenceFault, ProjectNotFoundFault,
        AuthorizationFailedFault, IllegalArgumentFault {
        if (projectData.getProjectId() == 1) {
            return;
        } else {
            throw new PersistenceFault("Problem updating the project data");
        }
    }
}
