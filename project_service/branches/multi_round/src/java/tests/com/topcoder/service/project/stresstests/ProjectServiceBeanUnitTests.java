/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.project.stresstests;

import java.util.List;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

import com.topcoder.service.project.BaseTestCase;
import com.topcoder.service.project.ProjectData;
import com.topcoder.service.project.ProjectService;

/**
 * <p>
 * Stress test for ProjectServiceBean class.
 * </p>
 *
 * @author 80x86
 * @version 1.0
 */
public class ProjectServiceBeanUnitTests extends BaseTestCase {

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
     * Sets up the testing environment.
     *
     * @throws Exception
     *             to JUnit
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();

        executeScriptFile("/clean.sql");
        executeScriptFile("/stress/insert.sql");
        // has to deploy it! because mockejb doesn't support ejb3
        Properties env = new Properties();
        env.setProperty(Context.SECURITY_PRINCIPAL, "username");
        env.setProperty(Context.SECURITY_CREDENTIALS, "password");
        env.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.security.jndi.JndiLoginInitialContextFactory");
        ctx = new InitialContext(env);

        projectService = (ProjectService) ctx.lookup("remote/ProjectServiceBean");
    }

    /**
     * Clears up the testing environment.
     *
     * @throws Exception
     *             to JUnit
     */
    @Override
    protected void tearDown() throws Exception {
        ctx.close();

        executeScriptFile("/clean.sql");
    }

    /**
     * Stress test for createProject(ProjectData) and deleteProject.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCreateDeleteProject() throws Exception {
        long start = System.currentTimeMillis();
        for (int i = 0; i < StressTests.TIMES; i++) {
            ProjectData projectData = new ProjectData();
            projectData.setName("Project Service");
            projectData.setDescription("Hello");
            ProjectData newPrjData = projectService.createProject(projectData);

            assertNotNull("Never return null.", newPrjData);
            assertNotNull("The project id should be updated.", newPrjData.getProjectId());

            // delete it
            assertTrue("the project should exit and be deleted", projectService
                    .deleteProject(newPrjData.getProjectId()));
        }
        long end = System.currentTimeMillis();
        System.out.println("Running createProject and deleteProject for " + StressTests.TIMES + " times costs "
                + (end - start) / 1000.0 + " seconds.");
    }

    /**
     * Stress test for getProject(long) with not an admin.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetProject_NotAdmin() throws Exception {
        long start = System.currentTimeMillis();
        for (int i = 0; i < StressTests.TIMES; i++) {
            // 3 is pre-inserted
            ProjectData projectData = projectService.getProject(3);
            assertNotNull("Should not return null.", projectData);
        }
        long end = System.currentTimeMillis();
        System.out.println("Running getProject with not an admin for " + StressTests.TIMES + " times costs "
                + (end - start) / 1000.0 + " seconds.");
    }

    /**
     * Stress test for getProject(long) with an admin.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetProject_Admin() throws Exception {
        Properties env = new Properties();
        env.setProperty(Context.SECURITY_PRINCIPAL, "admin");
        env.setProperty(Context.SECURITY_CREDENTIALS, "password");
        env.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.security.jndi.JndiLoginInitialContextFactory");
        InitialContext initCtx = new InitialContext(env);
        projectService = (ProjectService) initCtx.lookup("remote/ProjectServiceBean");
        long start = System.currentTimeMillis();
        for (int i = 0; i < StressTests.TIMES; i++) {
            // 1 is pre-inserted
            ProjectData projectData = projectService.getProject(1);
            assertNotNull("Should not return null.", projectData);
        }
        long end = System.currentTimeMillis();
        System.out.println("Running getProject with an admin for " + StressTests.TIMES + " times costs "
                + (end - start) / 1000.0 + " seconds.");
    }

    /**
     * Stress test for getAllProjects with not an admin.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetAllProjects_NotAdmin() throws Exception {
        long start = System.currentTimeMillis();
        for (int i = 0; i < StressTests.TIMES; i++) {
            List<ProjectData> projectDatas = projectService.getAllProjects();
            assertNotNull("Null is not expected.", projectDatas);
            assertTrue("should contain 1 result", projectDatas.size() == 1);
        }
        long end = System.currentTimeMillis();
        System.out.println("Running getAllProjects with not an admin for " + StressTests.TIMES + " times costs "
                + (end - start) / 1000.0 + " seconds.");
    }

    /**
     * Stress test for getAllProjects with an admin.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetAllProjects_Admin() throws Exception {
        Properties env = new Properties();
        env.setProperty(Context.SECURITY_PRINCIPAL, "admin");
        env.setProperty(Context.SECURITY_CREDENTIALS, "password");
        env.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.security.jndi.JndiLoginInitialContextFactory");
        InitialContext initCtx = new InitialContext(env);

        projectService = (ProjectService) initCtx.lookup("remote/ProjectServiceBean");
        long start = System.currentTimeMillis();
        for (int i = 0; i < StressTests.TIMES; i++) {
            List<ProjectData> projectDatas = projectService.getAllProjects();
            assertNotNull("Null is not expected.", projectDatas);
            assertTrue("should contain more than 1 results", projectDatas.size() > 1);
        }
        long end = System.currentTimeMillis();
        System.out.println("Running getAllProjects with an admin for " + StressTests.TIMES + " times costs "
                + (end - start) / 1000.0 + " seconds.");
    }

    /**
     * Stress test for getProjectsForUser.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetProjectsForUser_accuracy() throws Exception {
        Properties env = new Properties();
        env.setProperty(Context.SECURITY_PRINCIPAL, "admin");
        env.setProperty(Context.SECURITY_CREDENTIALS, "password");
        env.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.security.jndi.JndiLoginInitialContextFactory");
        InitialContext initCtx = new InitialContext(env);
        projectService = (ProjectService) initCtx.lookup("remote/ProjectServiceBean");
        long start = System.currentTimeMillis();
        for (int i = 0; i < StressTests.TIMES; i++) {
            List<ProjectData> projectDatas = projectService.getProjectsForUser(2);
            assertNotNull("Null is not expected.", projectDatas);
            assertEquals("The return list size is incorrect.", 1, projectDatas.size());
        }
        long end = System.currentTimeMillis();
        System.out.println("Running getProjectsForUser with an admin for " + StressTests.TIMES + " times costs "
                + (end - start) / 1000.0 + " seconds.");
    }

    /**
     * Stress test for updateProject with not an admin.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateProject_NotAdmin() throws Exception {
        // insert for testing
        ProjectData projectData = new ProjectData();
        projectData.setName("Project Service");
        projectData.setDescription("test");
        ProjectData newPrjData = projectService.createProject(projectData);
        assertNotNull("should create successfully", newPrjData);
        long start = System.currentTimeMillis();
        for (int i = 0; i < StressTests.TIMES; i++) {
            String newname = "new" + i;
            String newdesc = "desc" + i;
            newPrjData.setName(newname);
            newPrjData.setDescription(newdesc);
            projectService.updateProject(newPrjData);
            // test data
            newPrjData = projectService.getProject(newPrjData.getProjectId());
            assertNotNull("should exist in DB", newPrjData);
            assertEquals("should be the same value", newname, newPrjData.getName());
        }
        long end = System.currentTimeMillis();
        System.out.println("Running updateProject(and getProject) with not an admin for " + StressTests.TIMES
                + " times costs " + (end - start) / 1000.0 + " seconds.");
        // delete it
        projectService.deleteProject(newPrjData.getProjectId());
    }

    /**
     * Stress test for deleteProject with the project doesn't exist in DB.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testDeleteProject_NotExist() throws Exception {
        long start = System.currentTimeMillis();
        for (int i = 0; i < StressTests.TIMES; i++) {
            assertFalse("Should return false.", projectService.deleteProject(99999999L));
        }
        long end = System.currentTimeMillis();
        System.out.println("Running deleteProject with a project not existed for " + StressTests.TIMES
                + " times costs " + (end - start) / 1000.0 + " seconds.");
    }
}
