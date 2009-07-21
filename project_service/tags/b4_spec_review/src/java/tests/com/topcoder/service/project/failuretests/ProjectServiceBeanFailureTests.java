/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.project.failuretests;

import java.util.Properties;

import javax.ejb.EJBAccessException;
import javax.naming.Context;
import javax.naming.InitialContext;

import com.topcoder.service.project.AuthorizationFailedFault;
import com.topcoder.service.project.BaseTestCase;
import com.topcoder.service.project.IllegalArgumentFault;
import com.topcoder.service.project.ProjectData;
import com.topcoder.service.project.ProjectHasCompetitionsFault;
import com.topcoder.service.project.ProjectNotFoundFault;
import com.topcoder.service.project.ProjectService;
import com.topcoder.service.project.UserNotFoundFault;

/**
 * <p>
 * Failure test for <code>{@link ProjectServiceBean}</code> class.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.1
 * @since 1.0
 */
public class ProjectServiceBeanFailureTests extends BaseTestCase {

    /**
     * <p>
     * Represents the <code>ProjectService</code> instance for testing.
     * </p>
     */
    private ProjectService projectService;

    /**
     * <p>
     * Represents the <code>InitialContext</code> instance for looking up.
     * </p>
     */
    private InitialContext ctx;

    /**
     * <p>
     * Setup the testing environment.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();

        executeScriptFile("/clean.sql");

        Properties env = new Properties();
        env.setProperty(Context.SECURITY_PRINCIPAL, "username");
        env.setProperty(Context.SECURITY_CREDENTIALS, "password");
        env.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.security.jndi.JndiLoginInitialContextFactory");
        ctx = new InitialContext(env);

        projectService = (ProjectService) ctx.lookup("remote/ProjectServiceBean");
    }

    /**
     * <p>
     * Tear down the testing environment.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    @Override
    protected void tearDown() throws Exception {
        ctx.close();

        executeScriptFile("/clean.sql");

        super.tearDown();
    }

    /**
     * <p>
     * Failure test for <code>{@link ProjectServiceBean#createProject(ProjectData)}</code> method.
     * </p>
     * <p>
     * If the passed project data is null, should throw IllegalArgumentFault.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testCreateProject_null() throws Exception {
        try {
            projectService.createProject(null);
            fail("Expect IllegalArgumentFault.");
        } catch (IllegalArgumentFault e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link ProjectServiceBean#createProject(ProjectData)}</code> method.
     * </p>
     * <p>
     * If the name is null, should throw IllegalArgumentFault.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testCreateProject_nullName() throws Exception {
        try {
            projectService.createProject(new ProjectData());
            fail("Expect IllegalArgumentFault.");
        } catch (IllegalArgumentFault e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link ProjectServiceBean#createProject(ProjectData)}</code> method.
     * </p>
     * <p>
     * If the name is empty, should throw IllegalArgumentFault.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testCreateProject_emptyName() throws Exception {
        ProjectData projectData = new ProjectData();
        projectData.setName("");

        try {
            projectService.createProject(projectData);
            fail("Expect IllegalArgumentFault.");
        } catch (IllegalArgumentFault e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link ProjectServiceBean#createProject(ProjectData)}</code> method.
     * </p>
     * <p>
     * If the name is trimmed empty, should throw IllegalArgumentFault.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testCreateProject_TrimmedEmptyName() throws Exception {
        ProjectData projectData = new ProjectData();
        projectData.setName("  ");

        try {
            projectService.createProject(projectData);
            fail("Expect IllegalArgumentFault.");
        } catch (IllegalArgumentFault e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link ProjectServiceBean#getProject(long)}</code> method.
     * </p>
     * <p>
     * If the project is not present in persistence, should throw AuthorizationFailedFault.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetProject_NotFound() throws Exception {
        try {
            projectService.getProject(1);
            fail("Expect ProjectNotFoundFault.");
        } catch (ProjectNotFoundFault e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link ProjectServiceBean#getProject(long)}</code> method.
     * </p>
     * <p>
     * If the project is not belong to the current login user and the login user is not administrator,
     * should throw AuthorizationFailedFault.
     * </p>
     *
     * <p>
     * The login user has user id as 0 and is not administrator. But the desire project to get
     * has user id as 2.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetProject_AuthorizationFailed() throws Exception {
        executeSQL(new String[] {"INSERT INTO tc_direct_project (project_id, name, user_id, create_date)"
                + " values (1, 'Project Service', 2, '2008-02-20 12:53:45')"});

        try {
            projectService.getProject(1);
            fail("Expect AuthorizationFailedFault.");
        } catch (AuthorizationFailedFault e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link ProjectServiceBean#getProjectsForUser(long)}</code> method.
     * </p>
     * <p>
     * If the login user is not administrator, ejb container will do authorization check.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetProjectsForUser_NotAdministrator() throws Exception {
        try {
            projectService.getProjectsForUser(1);
            fail("Expect EJBAccessException");
        } catch (EJBAccessException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link ProjectServiceBean#getProjectsForUser(long)}</code> method.
     * </p>
     * <p>
     * If there are no projects linked to the login user., should throw UserNotFoundFault.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetProjectsForUser_UserNotFound() throws Exception {
        //Login as administrator
        Properties env = new Properties();
        env.setProperty(Context.SECURITY_PRINCIPAL, "admin");
        env.setProperty(Context.SECURITY_CREDENTIALS, "password");
        env.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.security.jndi.JndiLoginInitialContextFactory");
        InitialContext initCtx = new InitialContext(env);

        projectService = (ProjectService) initCtx.lookup("remote/ProjectServiceBean");

        try {
            projectService.getProjectsForUser(1);
            fail("Expect UserNotFoundFault");
        } catch (UserNotFoundFault e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link ProjectServiceBean#updateProject(ProjectData)}</code> method.
     * </p>
     * <p>
     * If the passed project data is null, should throw IllegalArgumentFault.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateProject_null() throws Exception {
        try {
            projectService.updateProject(null);
            fail("Expect IllegalArgumentFault.");
        } catch (IllegalArgumentFault e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link ProjectServiceBean#updateProject(ProjectData)}</code> method.
     * </p>
     * <p>
     * If the passed Project#id is null, should throw IllegalArgumentFault.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateProject_nullProjectId() throws Exception {
        ProjectData projectData = new ProjectData();
        projectData.setName("Hello");
        try {
            projectService.updateProject(projectData);
            fail("Expect IllegalArgumentFault.");
        } catch (IllegalArgumentFault e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link ProjectServiceBean#updateProject(ProjectData)}</code> method.
     * </p>
     * <p>
     * If the passed Project#name is null, should throw IllegalArgumentFault.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateProject_nullName() throws Exception {
        ProjectData projectData = new ProjectData();
        projectData.setProjectId(1L);

        try {
            projectService.updateProject(projectData);
            fail("Expect IllegalArgumentFault.");
        } catch (IllegalArgumentFault e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link ProjectServiceBean#updateProject(ProjectData)}</code> method.
     * </p>
     * <p>
     * If the passed Project#name is empty, should throw IllegalArgumentFault.
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
            projectService.updateProject(projectData);
            fail("Expect IllegalArgumentFault.");
        } catch (IllegalArgumentFault e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link ProjectServiceBean#updateProject(ProjectData)}</code> method.
     * </p>
     * <p>
     * If the passed Project#name is trimmed empty, should throw IllegalArgumentFault.
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
            projectService.updateProject(projectData);
            fail("Expect IllegalArgumentFault.");
        } catch (IllegalArgumentFault e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link ProjectServiceBean#updateProject(ProjectData)}</code> method.
     * </p>
     * <p>
     * If the project is not found, should throw ProjectNotFoundFault.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateProject_NotFound() throws Exception {
        ProjectData projectData = new ProjectData();
        projectData.setName("Project Service");
        projectData.setDescription("Hello");
        projectData.setProjectId(1L);

        try {
            projectService.updateProject(projectData);
            fail("Expect ProjectNotFoundFault.");
        } catch (ProjectNotFoundFault e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link ProjectServiceBean#updateProject(ProjectData)}</code> method.
     * </p>
     * <p>
     * If the project is not owned by the user, should throw AuthorizationFailedFault.
     * </p>
     *
     * <p>
     * The login user has user id as 0 and is not administrator. But the desire project to be updated
     * has user id as 2.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateProject_NotOwned() throws Exception {
        executeSQL(new String[] {"INSERT INTO tc_direct_project (project_id, name, user_id, create_date)"
                + " values (1, 'Project Service', 2, '2008-02-20 12:53:45')"});

        ProjectData projectData = new ProjectData();
        projectData.setName("Project Service");
        projectData.setDescription("Hello");
        projectData.setProjectId(1L);

        try {
            projectService.updateProject(projectData);
            fail("Expect AuthorizationFailedFault.");
        } catch (AuthorizationFailedFault e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link ProjectServiceBean#deleteProject(long)}</code> method.
     * </p>
     * <p>
     * If the project is not owned by the user and the user is not an administrator, should throw
     * AuthorizationFailedFault.
     * </p>
     *
     * <p>
     * The login user has user id as 0 and is not administrator. But the desire project to be deleted
     * has user id as 2.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDeleteProject_NotOwned() throws Exception {
        executeSQL(new String[] {"INSERT INTO tc_direct_project (project_id, name, user_id, create_date)"
                + " values (1, 'Project Service', 2, '2008-02-20 12:53:45')"});

        try {
            projectService.deleteProject(1);
            fail("Expect AuthorizationFailedFault.");
        } catch (AuthorizationFailedFault e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link ProjectServiceBean#deleteProject(long)}</code> method.
     * </p>
     *
     * <p>
     * The desire project to be deleted has competitions associated, it cannot be deleted.
     * ProjectHasCompetitionsFault expected.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDeleteProject_HasCompetitions() throws Exception {
        executeSQL(new String[] {"INSERT INTO tc_direct_project (project_id, name, user_id, create_date)"
                + " values (1, 'Project Service', 1, '2008-02-20 12:53:45')"
                , "INSERT INTO competition (competition_id, project_id) values (1, 1)"});

        try {
            projectService.deleteProject(1);
            fail("Expect ProjectHasCompetitionsFault.");
        } catch (ProjectHasCompetitionsFault e) {
            // expected
        }
    }
}
