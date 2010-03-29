/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.service;

import com.topcoder.service.project.ProjectData;
import com.topcoder.service.project.ProjectServiceRemote;

import junit.framework.Test;

/**
 * <p>
 * Demo of the Project Service.
 * </p>
 *
 * @author ernestobf
 * @version 1.1
 */
public class ProjectServiceDemo extends BaseUnitTestCase {

    /**
     * <p>
     * Aggregates all Unit tests in this class.
     * </p>
     *
     * @return Test suite aggregating all Unit tests in this class
     */
    public static Test suite() {
        return suite(ProjectServiceDemo.class);
    }

    /**
     * Demos the use of the <code>getProjectByName</code> method.
     *
     * @throws Exception pass any unhandled exception to JUunit
     */
    public void testDemo() throws Exception {
        ProjectServiceRemote projectService = lookupProjectServiceRemoteWithUserRole();

        ProjectData projectData = new ProjectData();
        projectData.setName("project2");
        projectData.setDescription("Foo");

        projectService.createProject(projectData);

        // 1 is the user id of the default user
        ProjectData pd = projectService.getProjectByName("project2", 1);

        assertEquals("Wrong project retrieved", "project2", pd.getName());
    }
}
