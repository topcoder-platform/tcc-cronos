/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.team.service.stresstests;

import com.topcoder.management.project.Project;
import com.topcoder.management.project.ProjectCategory;
import com.topcoder.management.project.ProjectStatus;
import com.topcoder.management.project.ProjectType;
import com.topcoder.management.resource.Resource;
import com.topcoder.project.service.FullProjectData;
import com.topcoder.project.service.ProjectServices;
import com.topcoder.search.builder.filter.Filter;

/**
 * <p>
 * This is a mock implementation of <code>ProjectServices</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockProjectServices implements ProjectServices {

    /**
     * <p>
     * Constructs an instance.
     * </p>
     */
    public MockProjectServices() {
    }

    /**
     * This method finds all active projects along with all known associated information. Returns
     * empty array if no projects found.
     *
     * @return FullProjectData array with full projects info, or empty array if none found
     */
    public FullProjectData[] findActiveProjects() {
        return new FullProjectData[]{StressTestsHelper.createFullProjectData()};
    }

    /**
     * This method finds all active projects. Returns empty array if no projects found.
     *
     * @return Project array with project info, or empty array if none found
     */
    public Project[] findActiveProjectsHeaders() {
        ProjectCategory category = new ProjectCategory(1, "Java", new ProjectType(1, "type1"));
        Project project = new Project(5, category, new ProjectStatus(1, "active"));
        project.setProperty("Project Name", "Team Services");
        Project project1 = new Project(6, category, new ProjectStatus(1, "active"));
        project.setProperty("Project Name", "Team Services");
        return new Project[]{project, project1};
    }

    /**
     * This method finds all projects along with all known associated information that match the
     * search criteria. Returns empty array if no projects found.
     *
     * @param filter The search criteria to filter projects
     * @return FullProjectData array with full projects info, or empty array if none found
     * @throws IllegalArgumentException If filter is null
     */
    public FullProjectData[] findFullProjects(Filter filter) {
        return null;
    }

    /**
     * This method finds all projects that match the search criteria. Returns empty array if no
     * projects found.
     *
     * @param filter The search criteria to filter projects
     * @return Project array with project info, or empty array if none found
     * @throws IllegalArgumentException If filter is null
     */
    public Project[] findProjectsHeaders(Filter filter) {
        return null;
    }

    /**
     * This method retrieves the project along with all known associated information. Returns null
     * if not found.
     *
     * @param projectId The ID of the project to retrieve
     * @return the project along with all known associated information
     * @throws IllegalArgumentException If projectId is negative
     */
    public FullProjectData getFullProjectData(long projectId) {
        if (projectId == 10) {
            return null;
        }
        FullProjectData data = StressTestsHelper.createFullProjectData();
        if (projectId == 20) {
            data.setResources(new Resource[]{new Resource(100)});
        }
        return data;
    }

}
