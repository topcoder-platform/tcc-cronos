/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.service.accuracytests;

import java.util.Date;

import com.topcoder.date.workdays.DefaultWorkdays;
import com.topcoder.management.project.Project;
import com.topcoder.management.project.ProjectCategory;
import com.topcoder.management.project.ProjectStatus;
import com.topcoder.management.project.ProjectType;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.resource.ResourceRole;
import com.topcoder.management.team.TeamHeader;
import com.topcoder.project.service.FullProjectData;
import com.topcoder.project.service.ProjectServices;
import com.topcoder.search.builder.filter.Filter;

/**
 * <p>
 * A mock implementation of ProjectServices.
 * </p>
 * 
 * @author mittu
 * @version 1.0
 */
public class MockProjectServices implements ProjectServices {

    /**
     * @see com.topcoder.project.service.ProjectServices#findActiveProjects()
     */
    public FullProjectData[] findActiveProjects() {
        return new FullProjectData[] { buildFullProjectData(1) };
    }

    /**
     * @see com.topcoder.project.service.ProjectServices#findActiveProjectsHeaders()
     */
    public Project[] findActiveProjectsHeaders() {
        return null;
    }

    /**
     * @see com.topcoder.project.service.ProjectServices#findFullProjects(com.topcoder.search.builder.filter.Filter)
     */
    public FullProjectData[] findFullProjects(Filter filter) {
        return new FullProjectData[] { buildFullProjectData(1) };
    }

    /**
     * @see com.topcoder.project.service.ProjectServices#findProjectsHeaders(com.topcoder.search.builder.filter.Filter)
     */
    public Project[] findProjectsHeaders(Filter filter) {
        return null;
    }

    /**
     * @see com.topcoder.project.service.ProjectServices#getFullProjectData(long)
     */
    public FullProjectData getFullProjectData(long projectId) {
        return buildFullProjectData(1);
    }

    /**
     * <p>
     * Creates a test data of FullProjectData.
     * </p>
     * 
     * @param roleId
     *            the resource role id.
     * @return FullProjectData
     */
    private FullProjectData buildFullProjectData(long roleId) {
        FullProjectData data = new FullProjectData(new Date(), new DefaultWorkdays());

        Resource resource = new Resource();
        resource.setId(1);
        resource.setProperty("External reference ID", new Long(10));
        resource.setProperty("Handle", "handle");
        resource.setProperty("Email", "handle@topcoder.com");
        resource.setProperty("Registration Date", new Date(80800));

        ResourceRole role = new ResourceRole(roleId);
        resource.setResourceRole(role);

        data.setResources(new Resource[] { resource });

        Project project = new Project(101,
                new ProjectCategory(1, "component dev", new ProjectType(1, "java")), new ProjectStatus(1,
                        "reg"));
        project.setProperty("Project Name", "Registration Services");

        data.setProjectHeader(project);

        TeamHeader team = new TeamHeader();
        team.setName("Review");
        team.setDescription("Accuracy Review.");
        team.setCaptainResourceId(1);

        data.setTeams(new TeamHeader[] { team });

        return data;
    }
}
