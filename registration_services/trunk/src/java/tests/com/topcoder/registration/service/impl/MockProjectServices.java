/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.service.impl;

import com.topcoder.management.project.Project;
import com.topcoder.project.service.FullProjectData;
import com.topcoder.project.service.ProjectServices;
import com.topcoder.search.builder.filter.Filter;

/**
 * <p>
 * This is a mock implementation of <code>ProjectServices</code>.
 * </p>
 * @author moonli
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
     * @return FullProjectData array with full projects info, or empty array if none found
     */
    public FullProjectData[] findActiveProjects() {
        return new FullProjectData[] {TestHelper.createFullProjectData(1)};
    }

    /**
     * This method finds all active projects. Returns empty array if no projects found.
     * @return Project array with project info, or empty array if none found
     */
    public Project[] findActiveProjectsHeaders() {
        return null;
    }

    /**
     * This method finds all projects along with all known associated information that match the
     * search criteria. Returns empty array if no projects found.
     * @return FullProjectData array with full projects info, or empty array if none found
     * @param filter
     *            The search criteria to filter projects
     * @throws IllegalArgumentException
     *             If filter is null
     */
    public FullProjectData[] findFullProjects(Filter filter) {
        return new FullProjectData[] {TestHelper.createFullProjectData(1)};
    }

    /**
     * This method finds all projects that match the search criteria. Returns empty array if no
     * projects found.
     * @return Project array with project info, or empty array if none found
     * @param filter
     *            The search criteria to filter projects
     * @throws IllegalArgumentException
     *             If filter is null
     */
    public Project[] findProjectsHeaders(Filter filter) {
        return null;
    }

    /**
     * This method retrieves the project along with all known associated information. Returns null
     * if not found.
     * @return the project along with all known associated information
     * @param projectId
     *            The ID of the project to retrieve
     * @throws IllegalArgumentException
     *             If projectId is negative
     */
    public FullProjectData getFullProjectData(long projectId) {
        if (projectId == 2) {
            return TestHelper.createFullProjectData(2);
        }
        return TestHelper.createFullProjectData(1);
    }

}
