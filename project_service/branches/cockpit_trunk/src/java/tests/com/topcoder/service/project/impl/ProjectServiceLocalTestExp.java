/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.project.impl;

import javax.ejb.EJBAccessException;

import com.topcoder.service.BaseUnitTestCase;
import com.topcoder.service.project.AuthorizationFailedFault;
import com.topcoder.service.project.Competition;
import com.topcoder.service.project.IllegalArgumentFault;
import com.topcoder.service.project.MockUserGroupManager;
import com.topcoder.service.project.Project;
import com.topcoder.service.project.ProjectData;
import com.topcoder.service.project.ProjectHasCompetitionsFault;
import com.topcoder.service.project.ProjectNotFoundFault;
import com.topcoder.service.project.StudioCompetition;
import com.topcoder.service.project.UserNotFoundFault;

/**
 * <p>
 * Failure test for <code>{@link ProjectServiceBean}</code>'s Local interface.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.1
 */
public class ProjectServiceLocalTestExp extends BaseUnitTestCase {

    /**
     * <p>
     * Failure test for <code>{@link ProjectServiceBean#createProject(ProjectData)}</code> method.
     * </p>
     *
     * <p>
     * If the passed project data is null, should throw <code>IllegalArgumentFault</code>.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testCreateProject_null() throws Exception {
        try {
            this.lookupProjectServiceLocalWithUserRole().createProject(null);
            fail("Expect IllegalArgumentFault.");
        } catch (IllegalArgumentFault e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link ProjectServiceBean#createProject(ProjectData)}</code> method.
     * </p>
     *
     * <p>
     * If the name is null, should throw <code>IllegalArgumentFault</code>.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testCreateProject_nullName() throws Exception {
        try {
            this.lookupProjectServiceLocalWithUserRole().createProject(new ProjectData());
            fail("Expect IllegalArgumentFault.");
        } catch (IllegalArgumentFault e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link ProjectServiceBean#createProject(ProjectData)}</code> method.
     * </p>
     *
     * <p>
     * If the name is empty, should throw <code>IllegalArgumentFault</code>.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testCreateProject_emptyName() throws Exception {
        ProjectData projectData = new ProjectData();
        projectData.setName("");

        try {
            this.lookupProjectServiceLocalWithUserRole().createProject(projectData);
            fail("Expect IllegalArgumentFault.");
        } catch (IllegalArgumentFault e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link ProjectServiceBean#createProject(ProjectData)}</code> method.
     * </p>
     *
     * <p>
     * The name is trimmed empty, should throw <code>IllegalArgumentFault</code>.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testCreateProject_TrimmedEmptyName() throws Exception {
        ProjectData projectData = new ProjectData();
        projectData.setName(" ");

        try {
            this.lookupProjectServiceLocalWithUserRole().createProject(projectData);
            fail("Expect IllegalArgumentFault.");
        } catch (IllegalArgumentFault e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link ProjectServiceBean#getProject(long)}</code> method.
     * </p>
     *
     * <p>
     * If the project is not present in persistence, should throw <code>AuthorizationFailedFault</code>.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetProject_NotFound() throws Exception {
        try {
            // No such project with id as Long.MAX_VALUE
            this.lookupProjectServiceLocalWithUserRole().getProject(Long.MAX_VALUE);
            fail("Expect ProjectNotFoundFault.");
        } catch (ProjectNotFoundFault e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link ProjectServiceBean#getProject(long)}</code> method.
     * </p>
     *
     * <p>
     * If the project is not associated with the current user, should throw <code>AuthorizationFailedFault</code>.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetProject_NotOwned() throws Exception {
        ProjectData project = new ProjectData();
        project.setName("name");

        // Create a project by administrator
        project = this.lookupProjectServiceLocalWithAdminRole().createProject(project);

        try {
            // Get the previous created project by user. AuthorizationFailedFault should be thrown
            this.lookupProjectServiceLocalWithUserRole().getProject(project.getProjectId());
            fail("Expect AuthorizationFailedFault.");
        } catch (AuthorizationFailedFault e) {
            // expected
        }

        this.lookupProjectServiceLocalWithAdminRole().deleteProject(project.getProjectId());
    }

    /**
     * <p>
     * Failure test for <code>{@link ProjectServiceBean#getProjectsForUser(long)}</code> method.
     * </p>
     *
     * <p>
     * If the login user is not administrator, ejb container will do authorization check.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetProjectsForUser_NotAdministrator() throws Exception {
        // Only administrator can call getProjectsForUser()
        try {
            this.lookupProjectServiceLocalWithUserRole().getProjectsForUser(1);
            fail("Expect EJBAccessException");
        } catch (EJBAccessException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link ProjectServiceBean#getProjectsForUser(long)}</code> method.
     * </p>
     *
     * <p>
     * If no project found for the specified user, should throw <code>UserNotFoundFault</code>.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetProjectsForUser_UserNotFound() throws Exception {

        try {
            // No projects associated with user whose id is Long.MAX_VALUE
            this.lookupProjectServiceLocalWithAdminRole().getProjectsForUser(Long.MAX_VALUE);
            fail("Expect UserNotFoundFault");
        } catch (UserNotFoundFault e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link ProjectServiceBean#updateProject(ProjectData)}</code> method.
     * </p>
     *
     * <p>
     * If the passed project data is null, should throw <code>IllegalArgumentFault</code>.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateProject_null() throws Exception {
        try {
            this.lookupProjectServiceLocalWithAdminRole().updateProject(null);
            fail("Expect IllegalArgumentFault.");
        } catch (IllegalArgumentFault e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link ProjectServiceBean#updateProject(ProjectData)}</code> method.
     * </p>
     *
     * <p>
     * If the passed project id is null, should throw <code>IllegalArgumentFault</code>.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateProject_nullProjectId() throws Exception {
        ProjectData projectData = new ProjectData();
        projectData.setName("Hello");
        try {
            this.lookupProjectServiceLocalWithAdminRole().updateProject(projectData);
            fail("Expect IllegalArgumentFault.");
        } catch (IllegalArgumentFault e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link ProjectServiceBean#updateProject(ProjectData)}</code> method.
     * </p>
     *
     * <p>
     * If the name is null, should throw <code>IllegalArgumentFault</code>.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateProject_nullName() throws Exception {
        ProjectData projectData = new ProjectData();
        projectData.setProjectId(1L);

        try {
            this.lookupProjectServiceLocalWithAdminRole().updateProject(projectData);
            fail("Expect IllegalArgumentFault.");
        } catch (IllegalArgumentFault e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link ProjectServiceBean#updateProject(ProjectData)}</code> method.
     * </p>
     *
     * <p>
     * If the name is empty, should throw <code>IllegalArgumentFault</code>.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateProject_emptyName() throws Exception {
        ProjectData projectData = new ProjectData();
        projectData.setProjectId(1L);
        projectData.setName("");

        try {
            this.lookupProjectServiceLocalWithAdminRole().updateProject(projectData);
            fail("Expect IllegalArgumentFault.");
        } catch (IllegalArgumentFault e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link ProjectServiceBean#updateProject(ProjectData)}</code> method.
     * </p>
     *
     * <p>
     * If the name is trimmed empty, should throw <code>IllegalArgumentFault</code>.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateProject_TrimmedEmptyName() throws Exception {
        ProjectData projectData = new ProjectData();
        projectData.setProjectId(1L);
        projectData.setName("  ");

        try {
            this.lookupProjectServiceLocalWithAdminRole().updateProject(projectData);
            fail("Expect IllegalArgumentFault.");
        } catch (IllegalArgumentFault e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link ProjectServiceBean#updateProject(ProjectData)}</code> method.
     * </p>
     *
     * <p>
     * If the project is not found, should throw <code>ProjectNotFoundFault</code>.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateProject_NotFound() throws Exception {
        ProjectData projectData = new ProjectData();
        projectData.setName("Project Service");
        projectData.setDescription("Hello");

        // No such project with id as Long.MAX_VALUE
        projectData.setProjectId(Long.MAX_VALUE);

        try {
            this.lookupProjectServiceLocalWithAdminRole().updateProject(projectData);
            fail("Expect ProjectNotFoundFault.");
        } catch (ProjectNotFoundFault e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link ProjectServiceBean#updateProject(ProjectData)}</code> method.
     * </p>
     *
     * <p>
     * If the project is not owned by the user, should throw <code>AuthorizationFailedFault</code>.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateProject_NotOwned() throws Exception {
        ProjectData projectData = new ProjectData();
        projectData.setName("Project Service");
        projectData.setDescription("Hello");

        // Create a project by administrator
        projectData = this.lookupProjectServiceLocalWithAdminRole().createProject(projectData);

        try {
            // Update the previous created project by user. AuthorizationFailedFault should be thrown
            this.lookupProjectServiceLocalWithUserRole().updateProject(projectData);
            fail("Expect AuthorizationFailedFault.");
        } catch (AuthorizationFailedFault e) {
            // expected
        }

        this.lookupProjectServiceLocalWithAdminRole().deleteProject(projectData.getProjectId());
    }

    /**
     * <p>
     * Failure test for <code>{@link ProjectServiceBean#deleteProject(long)}</code> method.
     * </p>
     *
     * <p>
     * If user is not authorized to delete the project, should throw <code>AuthorizationFailedFault</code>.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDeleteProject_NotOwned() throws Exception {
        ProjectData projectData = new ProjectData();
        projectData.setName("Project Service");
        projectData.setDescription("Hello");

        // Create a project by administrator
        projectData = this.lookupProjectServiceLocalWithAdminRole().createProject(projectData);

        try {
            // Delete the previous created project by user. AuthorizationFailedFault should be thrown
            this.lookupProjectServiceLocalWithUserRole().deleteProject(projectData.getProjectId());
            fail("Expect AuthorizationFailedFault");
        } catch (AuthorizationFailedFault e) {
            // expected
        }

        this.lookupProjectServiceLocalWithAdminRole().deleteProject(projectData.getProjectId());
    }

    /**
     * <p>
     * Failure test for <code>{@link ProjectServiceBean#deleteProject(long)}</code> method.
     * </p>
     *
     * <p>
     * The project has competitions associated, should throw <code>ProjectHasCompetitionsFault</code>.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDeleteProject_HasCompetitions() throws Exception {
        ProjectData projectData = new ProjectData();
        projectData.setName("Project Service");
        projectData.setDescription("Hello");

        projectData = this.lookupProjectServiceLocalWithAdminRole().createProject(projectData);

        // Persist a competition within project
        Competition competition = new StudioCompetition();
        competition.setProject(getEntityManager().find(Project.class, projectData.getProjectId()));
        persist(competition);

        try {
            // The project can not be deleted since it has competition associated
            this.lookupProjectServiceLocalWithAdminRole().deleteProject(projectData.getProjectId());
            fail("Expect ProjectHasCompetitionsFault");
        } catch (ProjectHasCompetitionsFault e) {
            // expected
        }

        executeScript("/clean.sql");
    }

    /**
     * <p>
     * Failure test for <code>{@link ProjectServiceBean#createProject(ProjectData)}</code> method.
     * </p>
     *
     * <p>
     * The user has no role, should throw <code>EJBAccessException</code>.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testCreateProject_UserHasNoRole() throws Exception {
        ProjectData projectData = new ProjectData();
        try {
            ((ProjectServiceLocalBridge) getInitialContext(MockUserGroupManager.NO_ROLES_USER)
                    .lookup("project_service/ProjectServiceLocalBridgeBean/remote")).createProject(projectData);
            fail("Expect EJBAccessException.");
        } catch (EJBAccessException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link ProjectServiceBean#getProject(ProjectData)}</code> method.
     * </p>
     *
     * <p>
     * The user has no role, should throw <code>EJBAccessException</code>.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetProject_UserHasNoRole() throws Exception {
        try {
            ((ProjectServiceLocalBridge) getInitialContext(MockUserGroupManager.NO_ROLES_USER)
                    .lookup("project_service/ProjectServiceLocalBridgeBean/remote")).getProject(1L);
            fail("Expect EJBAccessException.");
        } catch (EJBAccessException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link ProjectServiceBean#getProjectsForUser(long)}</code> method.
     * </p>
     *
     * <p>
     * The user has no role, should throw <code>EJBAccessException</code>.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetProjectsForUser_UserHasNoRole() throws Exception {

        try {
            ((ProjectServiceLocalBridge) getInitialContext(MockUserGroupManager.NO_ROLES_USER)
                    .lookup("project_service/ProjectServiceLocalBridgeBean/remote")).getProjectsForUser(1L);
            fail("Expect EJBAccessException");
        } catch (EJBAccessException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link ProjectServiceBean#updateProject(ProjectData)}</code> method.
     * </p>
     *
     * <p>
     * The user has no role, should throw <code>EJBAccessException</code>.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateProject_UserHasNoRole() throws Exception {
        ProjectData projectData = new ProjectData();
        try {
            ((ProjectServiceLocalBridge) getInitialContext(MockUserGroupManager.NO_ROLES_USER)
                    .lookup("project_service/ProjectServiceLocalBridgeBean/remote")).updateProject(projectData);
            fail("Expect EJBAccessException.");
        } catch (EJBAccessException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link ProjectServiceBean#deleteProject(long)}</code> method.
     * </p>
     *
     * <p>
     * The user has no role, should throw <code>EJBAccessException</code>.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDeleteProject_UserHasNoRole() throws Exception {
        try {
            ((ProjectServiceLocalBridge) getInitialContext(MockUserGroupManager.NO_ROLES_USER)
                    .lookup("project_service/ProjectServiceLocalBridgeBean/remote")).deleteProject(1L);
            fail("Expect EJBAccessException");
        } catch (EJBAccessException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link ProjectServiceBean#getAllProjects()}</code> method.
     * </p>
     *
     * <p>
     * The user has no role, should throw <code>EJBAccessException</code>.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetAllProjects_UserHasNoRole() throws Exception {
        try {
            ((ProjectServiceLocalBridge) getInitialContext(MockUserGroupManager.NO_ROLES_USER)
                    .lookup("project_service/ProjectServiceLocalBridgeBean/remote")).getAllProjects();
            fail("Expect EJBAccessException");
        } catch (EJBAccessException e) {
            // expected
        }
    }
}
