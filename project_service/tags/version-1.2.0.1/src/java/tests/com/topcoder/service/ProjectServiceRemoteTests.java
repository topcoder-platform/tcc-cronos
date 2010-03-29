/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.service;

import junit.framework.Test;

import com.topcoder.service.project.IllegalArgumentFault;
import com.topcoder.service.project.PersistenceFault;
import com.topcoder.service.project.ProjectData;
import com.topcoder.service.project.ProjectNotFoundFault;
import com.topcoder.service.project.ProjectService;
import com.topcoder.service.project.ProjectServiceRemote;

/**
 * <p>
 * Unit test for the <code>{@link ProjectServiceBean#getProjectByName(String, long)}</code> method
 * which was added in the 1.1 version.
 * </p>
 *
 * @author ernestobf
 * @version 1.1
 */
public class ProjectServiceRemoteTests extends BaseUnitTestCase {

    /**
     * <p>
     * Aggregates all Unit tests in this class.
     * </p>
     *
     * @return Test suite aggregating all Unit tests in this class
     */
    public static Test suite() {
        return suite(ProjectServiceRemoteTests.class);
    }

    /**
     * Tests the <code>getProjectByName</code> method creating first a project
     * and then retrieving it with user role.
     *
     * @throws Exception pass any unhandled exception to JUunit
     */
    public void testGetProjectByNameUserRole() throws Exception {
        ProjectServiceRemote projectService = lookupProjectServiceRemoteWithUserRole();

        ProjectData projectData = new ProjectData();
        projectData.setName("project1");
        projectData.setDescription("Hello");

        projectService.createProject(projectData);

        // 1 is the user id
        ProjectData pd = projectService.getProjectByName("project1", 1);

        assertNotNull("null project data received", pd);
    }

    /**
     * Tests accessing the <code>getProjectByName</code> method as a web
     * service.
     *
     * @throws Exception pass any unhandled exception to JUunit
     */
    public void testGetProjectByNameWebService() throws Exception {
        ProjectService projectService = lookupProjectServiceWSClientWithUserRole();

        ProjectData projectData = new ProjectData();
        projectData.setName("project3");
        projectData.setDescription("Hello");

        projectService.createProject(projectData);

        // 1 is the user id
        ProjectData pd = projectService.getProjectByName("project3", 1);

        assertNotNull("null project data received", pd);
    }

    /**
     * Tests the <code>getProjectByName</code> method with a project
     * that does not exist.
     *
     * @throws Exception pass any unhandled exception to JUunit
     */
    public void testGetProjectByNameDoesNotExist() throws Exception {
        ProjectServiceRemote projectService = lookupProjectServiceRemoteWithUserRole();

        // 1 is the user id
        try {
            projectService.getProjectByName("xxxxx", 1);
            fail("ProjectNotFoundFault expected.");
        } catch (ProjectNotFoundFault e) {
            // expected
        }
    }

    /**
     * Tests the <code>getProjectByName</code> method with a duplicated project
     * in the database that should cause this method to throw <code>PersistenceFault</code>.
     *
     * @throws Exception pass any unhandled exception to JUunit
     */
    public void testGetProjectByNameRepeated() throws Exception {
        ProjectServiceRemote projectService = lookupProjectServiceRemoteWithUserRole();

        // null name
        try {
            projectService.getProjectByName("repeated", 1);
            fail("PersistenceException expected.");
        } catch (PersistenceFault e) {
            // expected
        }
    }

    /**
     * Tests the <code>getProjectByName</code> method with invalid
     * parameters that should cause the method to throw IllegalArgumentException.
     *
     * @throws Exception pass any unhandled exception to JUunit
     */
    public void testGetProjectByNameIllegalArguments() throws Exception {
        ProjectServiceRemote projectService = lookupProjectServiceRemoteWithUserRole();

        // null name
        try {
            projectService.getProjectByName(null, 1);
            fail("ProjectNotFoundFault expected.");
        } catch (IllegalArgumentFault e) {
            // expected
        }

        // empty name
        try {
            projectService.getProjectByName(" ", 1);
            fail("ProjectNotFoundFault expected.");
        } catch (IllegalArgumentFault e) {
            // expected
        }

        // non-positive user id
        try {
            projectService.getProjectByName("abc", 0);
            fail("ProjectNotFoundFault expected.");
        } catch (IllegalArgumentFault e) {
            // expected
        }

        // non-positive user id
        try {
            projectService.getProjectByName("abc", -1);
            fail("ProjectNotFoundFault expected.");
        } catch (IllegalArgumentFault e) {
            // expected
        }
    }

    /**
     * Tests the <code>getProjectByName</code> method creating first a project
     * and then retrieving it with administrator role.
     *
     * @throws Exception pass any unhandled exception to JUnit
     */
    public void testGetProjectByNameAdminRole() throws Exception {

        ProjectServiceRemote projectService = lookupProjectServiceRemoteWithAdminRole();

        ProjectData projectData = new ProjectData();
        projectData.setName("project2");
        projectData.setDescription("Hello");

        projectService.createProject(projectData);

        // 2 is the administrator user id
        ProjectData pd = projectService.getProjectByName("project2", 2);

        assertNotNull("null project data received", pd);
    }
}
