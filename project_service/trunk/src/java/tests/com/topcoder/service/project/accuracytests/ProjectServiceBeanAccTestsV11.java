/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.project.accuracytests;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

import junit.extensions.TestSetup;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.service.project.ProjectData;
import com.topcoder.service.project.ProjectService;


/**
 * <p>
 * Accuracy Test cases for the newly added method of <code>ProjectServiceBean</code> in version 1.1.
 * </p>
 * @author myxgyy
 * @version 1.1
 */
public class ProjectServiceBeanAccTestsV11 extends TestCase {
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

        suite.addTestSuite(ProjectServiceBeanAccTestsV11.class);

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
     * Lookup the projectService with admin role.
     *
     * @throws Exception into JUnit
     */
    private static void lookupProjectServiceRemoteWithAdminRole()
        throws Exception {
        Properties env = new Properties();
        env.setProperty(Context.SECURITY_PRINCIPAL, "admin");
        env.setProperty(Context.SECURITY_CREDENTIALS, "password");
        env.setProperty(Context.INITIAL_CONTEXT_FACTORY,
            "org.jboss.security.jndi.JndiLoginInitialContextFactory");

        InitialContext initCtx = new InitialContext(env);

        projectService = (ProjectService) initCtx.lookup(
                "remote/ProjectServiceBean");
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
        env.setProperty(Context.INITIAL_CONTEXT_FACTORY,
            "org.jboss.security.jndi.JndiLoginInitialContextFactory");
        ctx = new InitialContext(env);

        projectService = (ProjectService) ctx.lookup(
                "remote/ProjectServiceBean");
    }

    /**
     * Tests the <code>getProjectByName</code> method.
     *
     * @throws Exception to JUunit
     */
    public void testGetProjectByNameAccuracy() throws Exception {
        ProjectData projectData = new ProjectData();
        projectData.setName("project1");
        projectData.setDescription("des");

        projectService.createProject(projectData);

        ProjectData pd = projectService.getProjectByName("project1", 1);

        assertEquals(pd.getDescription(), projectData.getDescription());
        assertEquals(pd.getName(), projectData.getName());
    }
}
