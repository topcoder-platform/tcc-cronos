/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.service.failuretests.impl;

import java.util.Date;

import com.topcoder.date.workdays.DefaultWorkdays;
import com.topcoder.management.project.Project;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.resource.ResourceRole;
import com.topcoder.management.team.TeamHeader;
import com.topcoder.project.service.FullProjectData;
import com.topcoder.project.service.ProjectServices;
import com.topcoder.search.builder.filter.Filter;

/**
 * The mock class for <code>ProjectServices</code>.
 *
 * @author liulike
 * @version 1.0
 */
public class MockProjectServices implements ProjectServices {

    /**
     * The default ctor.
     */
    public MockProjectServices() {

    }

    /**
     * This method finds all active projects along with all known associated information. Returns empty array
     * if no projects found.
     *
     * @return FullProjectData array with full projects info, or empty array if none found
     */
    public FullProjectData[] findActiveProjects() {
        return new FullProjectData[] {createFullProjectData(1)};
    }

    /**
     * This method finds all active projects. Returns empty array if no projects found.
     *
     * @return Project array with project info, or empty array if none found
     */
    public Project[] findActiveProjectsHeaders() {
        return null;
    }

    /**
     * This method finds all projects along with all known associated information that match the search
     * criteria. Returns empty array if no projects found.
     *
     * @return FullProjectData array with full projects info, or empty array if none found
     * @param filter The search criteria to filter projects
     * @throws IllegalArgumentException If filter is null
     */
    public FullProjectData[] findFullProjects(Filter filter) {
        return new FullProjectData[] {createFullProjectData(1)};
    }

    /**
     * This method finds all projects that match the search criteria. Returns empty array if no projects
     * found.
     *
     * @return Project array with project info, or empty array if none found
     * @param filter The search criteria to filter projects
     * @throws IllegalArgumentException If filter is null
     */
    public Project[] findProjectsHeaders(Filter filter) {
        return null;
    }

    /**
     * This method retrieves the project along with all known associated information. Returns null if not
     * found.
     *
     * @return the project along with all known associated information
     * @param projectId The ID of the project to retrieve
     * @throws IllegalArgumentException If projectId is negative
     */
    public FullProjectData getFullProjectData(long projectId) {
        return createFullProjectData(1);
    }

    /**
     * The helper method to create FullProjectData instance.
     *
     * @param roleId The role id for FullProjectData instance
     * @return The created FullProjectData instance
     */
    private FullProjectData createFullProjectData(long roleId) {
        FullProjectData data = new FullProjectData(new Date(1), new DefaultWorkdays());

        Resource resource = new Resource();
        resource.setId(1);

        ResourceRole role = new ResourceRole(roleId);
        resource.setResourceRole(role);

        data.setResources(new Resource[] {resource});

        TeamHeader team = new TeamHeader();
        team.setName("9");
        team.setCaptainResourceId(1);
        data.setTeams(new TeamHeader[] {team});

        return data;
    }

}
