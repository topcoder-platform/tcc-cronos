/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.service.impl;

import com.topcoder.project.service.ProjectServices;
import com.topcoder.project.service.FullProjectData;

import java.util.Date;

import com.topcoder.date.workdays.DefaultWorkdaysFactory;
import com.topcoder.date.workdays.Workdays;
import com.topcoder.date.workdays.WorkdaysFactory;
import com.topcoder.management.project.Project;
import com.topcoder.management.project.ProjectCategory;
import com.topcoder.management.project.ProjectStatus;
import com.topcoder.management.project.ProjectType;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.resource.ResourceRole;
import com.topcoder.management.team.TeamHeader;
import com.topcoder.search.builder.filter.Filter;

/**
 * <p>
 * This class is a full implementation of the ProjectServices interface.
 * </p>
 * <p>
 * This class is immutable and thread safe.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
*/
public class MockProjectServicesImpl implements ProjectServices {
    /**
     * <p>
     * Empty constructor.
     * </p>
     *
     */
    public MockProjectServicesImpl() {
    }

    /**
     * <p>
     * Creates a new instance of this class from the given namesapce.
     * </p>
     *
     * @param namespace
     *            the namespace to create this instance from.
     */
    public MockProjectServicesImpl(String namespace) {
    }

    /**
     * This method finds all active projects along with all known associated
     * information. Returns empty array if no projects found.
     *
     * @return FullProjectData array with full projects info, or empty array if
     *         none found
     * @throws ProjectServicesException
     *             If there is a system error while performing the search
     */
    public FullProjectData[] findActiveProjects() {

        FullProjectData project1 = createFullProjectData();
        FullProjectData project2 = createFullProjectData();
        FullProjectData project3 = createFullProjectData();

        return new FullProjectData[] { project1, project2, project3 };

    };

    /**
     * Creates an FullProjectData instance for mock
     *
     * @return the created FullProjectData instance
     */
    private static FullProjectData createFullProjectData() {

        Date date = new Date();
        WorkdaysFactory factory = new DefaultWorkdaysFactory();
        Workdays workdays = factory.createWorkdaysInstance();

        FullProjectData project = new FullProjectData(date, workdays);

        long resourceId1 = 11;
        ResourceRole resourceRole1 = new ResourceRole();
        Resource resource1 = new Resource(resourceId1, resourceRole1);
        resource1.setProperty("External Reference ID", new Long(1));

        long resourceId2 = 66;
        ResourceRole resourceRole2 = new ResourceRole();
        Resource resource2 = new Resource(resourceId2, resourceRole2);
        resourceRole2.setCreationUser("resourceRole2CreationUser");
        resource2.setProperty("External Reference ID", new Long(5));
        Resource[] Resources = new Resource[] { resource1, resource2 };

        project.setResources(Resources);
        ProjectType projectType = new ProjectType(1, "ProjectType");
        ProjectCategory projectCategory = new ProjectCategory(1,
                "projectCategoryName", projectType);
        ProjectStatus projectStatus = new ProjectStatus(1, "projectStatusName");

        Project projectHeader = new Project(projectCategory, projectStatus);
        projectHeader.setCreationUser("projectHeaderCreationUser");
        project.setProjectHeader(projectHeader);

        TeamHeader team1 = new TeamHeader();
        TeamHeader team2 = new TeamHeader();
        TeamHeader[] teams = new TeamHeader[] {team1, team2 };
        project.setTeams(teams);

        String[] technologies = new String[] {"Java", "SQL" };
        project.setTechnologies(technologies);

        return project;
    }

    /**
     * This method finds all active projects. Returns empty array if no projects
     * found.
     *
     * @return Project array with project info, or empty array if none found
     */
    public Project[] findActiveProjectsHeaders() {
        return null;
    };

    /**
     * This method finds all projects along with all known associated
     * information that match the search criteria. Returns empty array if no
     * projects found.
     *
     * @return FullProjectData array with full projects info, or empty array if
     *         none found
     * @param filter
     *            The search criteria to filter projects
     * @throws IllegalArgumentException
     *             If filter is null
     * @throws ProjectServicesException
     *             If there is a system error while performing the search
     */
    public FullProjectData[] findFullProjects(Filter filter) {
        return null;
    };

    /**
     * This method finds all projects that match the search criteria. Returns
     * empty array if no projects found.
     *
     * @return Project array with project info, or empty array if none found
     * @param filter
     *            The search criteria to filter projects
     * @throws IllegalArgumentException
     *             If filter is null
     * @throws ProjectServicesException
     *             If there is a system error while performing the search
     */
    public Project[] findProjectsHeaders(Filter filter) {
        return null;
    };

    /**
     * This method retrieves the project along with all known associated
     * information. Returns null if not found.
     *
     * @return the project along with all known associated information
     * @param projectId
     *            The ID of the project to retrieve
     * @throws IllegalArgumentException
     *             If projectId is negative
     * @throws ProjectServicesException
     *             If there is a system error while performing the search
     */
    public FullProjectData getFullProjectData(long projectId) {
        return null;
    }

}
