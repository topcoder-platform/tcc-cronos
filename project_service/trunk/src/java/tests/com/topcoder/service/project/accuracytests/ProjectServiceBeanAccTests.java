/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.project.accuracytests;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;

import junit.extensions.TestSetup;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.service.project.Competition;
import com.topcoder.service.project.Project;
import com.topcoder.service.project.ProjectData;
import com.topcoder.service.project.ProjectService;


/**
 * <p>
 * Accuracy Test cases for the ProjectServiceBean.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.1
 * @since 1.0
 */
public class ProjectServiceBeanAccTests extends TestCase {
    /**
     * <p>
     * Represents the <code>ProjectService</code> instance for testing.
     * </p>
     */
    private static ProjectService projectService;

    /**
     * <p>
     * Represents the <code>InitialContext</code> instance for looking up.
     * </p>
     */
    private static InitialContext ctx;

    /**
     * <p>
     * Return All the EJB test suite.
     * </p>
     *
     * @return all the EJB test suite
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(ProjectServiceBeanAccTests.class);

        /**
         * <p>
         * Setup the unit test.
         * </p>
         */
        TestSetup wrapper = new TestSetup(suite) {
                /**
                 * <p>
                 * Setup the EJB test.
                 * </p>
                 */
                @Override
                protected void setUp() throws Exception {
                    deleteAllProjects();

                    lookupProjectServiceRemoteWithUserRole();
                }

                /**
                 * <p>
                 * Tear down the EJB test.
                 * </p>
                 */
                @Override
                protected void tearDown() throws Exception {
                    ctx = null;
                    projectService = null;
                }
            };

        return wrapper;
    }

    /**
     * Delete all projects.
     *
     * @throws Exception into JUint
     */
    private static void deleteAllProjects() throws Exception {
        lookupProjectServiceRemoteWithAdminRole();

        //remove all the project data
        for (ProjectData data : projectService.getAllProjects()) {
            projectService.deleteProject(data.getProjectId());
        }
    }

    /**
     * Create project instance for testing.
     *
     * @return ProjectData instance
     */
    private ProjectData getProjectData() {
        ProjectData project = new Project();
        project.setDescription("project description");
        project.setName("project name");

        return project;
    }

    /**
     * <p>
     * Assert two projects.
     * </p>
     *
     * @param expected expected one
     * @param actual actual one
     */
    private static void assertProject(ProjectData expected, ProjectData actual) {
        assertEquals("The project name is invalid.", expected.getName(), actual.getName());
        assertEquals("The Description name is invalid.", expected.getDescription(), actual.getDescription());
    }

    /**
     * Test the createProject method.
     *
     * @throws Exception into JUnit
     */
    public void testCreateAndDeleteProject() throws Exception {
        //create project
        ProjectData project = getProjectData();
        ProjectData persisted = projectService.createProject(project);

        //verify
        assertProject(project, persisted);
        assertTrue("The id of persisted project is invalid", persisted.getProjectId().longValue() > 0);

        //retrieve it
        ProjectData retrieved = projectService.getProject(persisted.getProjectId());
        assertProject(persisted, retrieved);

        //delete it
        projectService.deleteProject(retrieved.getProjectId());

        //verify
        try {
            projectService.getProject(retrieved.getProjectId());
            fail("The project can not be found now.");
        } catch (Exception e) {
            //good
        }
    }

    /**
     * Lookup the projectService with admin role.
     *
     * @throws Exception into JUnit
     */
    private static void lookupProjectServiceRemoteWithAdminRole()
        throws Exception {
        Properties env = new Properties();
        env.setProperty(Context.SECURITY_PRINCIPAL, "admin");
        env.setProperty(Context.SECURITY_CREDENTIALS, "password");
        env.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.security.jndi.JndiLoginInitialContextFactory");

        InitialContext initCtx = new InitialContext(env);

        projectService = (ProjectService) initCtx.lookup("remote/ProjectServiceBean");
    }

    /**
     * Lookup the projectService with admin role.
     *
     * @throws Exception into JUnit
     */
    private static void lookupProjectServiceRemoteWithUserRole()
        throws Exception {
        Properties env = new Properties();
        env.setProperty(Context.SECURITY_PRINCIPAL, "username");
        env.setProperty(Context.SECURITY_CREDENTIALS, "password");
        env.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.security.jndi.JndiLoginInitialContextFactory");
        ctx = new InitialContext(env);

        projectService = (ProjectService) ctx.lookup("remote/ProjectServiceBean");
    }

    /**
     * Test the getAllProjects and getProjectForUser method.
     *
     * @throws Exception into JUnit
     */
    public void testRetrieveProjects() throws Exception {
        deleteAllProjects();
        lookupProjectServiceRemoteWithUserRole();

        //create project
        ProjectData projectOne = getProjectData();
        projectService.createProject(projectOne);

        //create another project
        ProjectData projectTwo = getProjectData();
        projectService.createProject(projectTwo);

        //retrieve all the projects for the user role
        assertEquals("The size of project should be 2.", 2, projectService.getAllProjects().size());

        lookupProjectServiceRemoteWithAdminRole();

        //create another projects
        ProjectData projectThree = getProjectData();
        projectService.createProject(projectThree);

        assertEquals("The size of project should be 3.", 3, projectService.getAllProjects().size());
        assertEquals("The size of project should be 2.", 2, projectService.getProjectsForUser(1).size());
        assertEquals("The size of project should be 1.", 1, projectService.getProjectsForUser(2).size());

        lookupProjectServiceRemoteWithUserRole();
        assertEquals("The size of project should be 2.", 2, projectService.getAllProjects().size());

        try {
            projectService.getProjectsForUser(0);
            fail("The user is not admin.");
        } catch (Exception e) {
            //good
        }

        //delete all projects
        lookupProjectServiceRemoteWithAdminRole();

        //remove all the project data
        for (ProjectData data : projectService.getAllProjects()) {
            projectService.deleteProject(data.getProjectId());
        }
    }

    /**
     * Test the update method.
     *
     * @throws Exception into JUnit
     */
    public void testUpdateProject() throws Exception {
        deleteAllProjects();
        lookupProjectServiceRemoteWithUserRole();

        //create project
        ProjectData projectOne = getProjectData();
        ProjectData persisted = projectService.createProject(projectOne);

        //update it
        persisted.setDescription("project description two");
        persisted.setName("project name two");

        projectService.updateProject(persisted);

        //retrieve
        ProjectData updated = projectService.getProject(persisted.getProjectId());
        //verify
        assertProject(persisted, updated);

        //update with other info
        ProjectData project = new Project();
        project.setDescription("p");
        project.setName("x");
        project.setProjectId(updated.getProjectId());
        ((Project) project).setUserId(0);
        ((Project) project).setCreateDate(new java.util.Date(1000000));

        Competition cp = new Competition();
        Set<Competition> cps = new HashSet<Competition>();
        cps.add(cp);
        ((Project) project).setCompetitions(cps);

        projectService.updateProject(project);

        //delete it
        assertTrue("It is true to delete it.", projectService.deleteProject(updated.getProjectId()));
        assertFalse("It is false to delete it.", projectService.deleteProject(updated.getProjectId()));
    }
}
