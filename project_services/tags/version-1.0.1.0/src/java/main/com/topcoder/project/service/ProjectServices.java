/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.service;

import com.topcoder.management.project.Project;
import com.topcoder.search.builder.filter.Filter;

/**
 * <p>
 * This represents the interface that defines all business methods for project data retrieval
 * services. It will provide streamlined access to project information. It will allow for a simple
 * search for full or basic project information, or to use custom search criteria to locate
 * projects, either in its full or basic form. The basic form involves getting the
 * <code>Project</code> object (from <b>Project Management</b>), and the full form involves the
 * <code>FullProjectData</code> object, which not only provides information as the basic form, but
 * also provides project phase information, all resources participating in the project, and all
 * teams currently existing in it. Furthermore, it provides data about the technologies involved in
 * this project (such as Java, C#, etc&hellip;).
 * </p>
 * <p>
 * It has one implementation: <code>ProjectServicesImpl</code>.
 * </p>
 * <p>
 * Thread safety: Implementations must be thread-safe from the point of view of their use.
 * Implementations can assume that passed objects will be operated on by just one thread.
 * </p>
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public interface ProjectServices {

    /**
     * <p>
     * This method finds all active projects along with all known associated information. Returns
     * empty array if no projects found.
     * </p>
     * @return FullProjectData array with full projects info, or empty array if none found
     * @throws ProjectServicesException
     *             If there is a system error while performing the search
     */
    public FullProjectData[] findActiveProjects();

    /**
     * <p>
     * This method finds all active projects. Returns empty array if no projects found.
     * </p>
     * @return Project array with project info, or empty array if none found
     * @throws ProjectServicesException
     *             If there is a system error while performing the search
     */
    public Project[] findActiveProjectsHeaders();

    /**
     * <p>
     * This method finds all projects along with all known associated information that match the
     * search criteria. Returns empty array if no projects found.
     * </p>
     * @return FullProjectData array with full projects info, or empty array if none found
     * @param filter
     *            The search criteria to filter projects
     * @throws IllegalArgumentException
     *             If filter is null
     * @throws ProjectServicesException
     *             If there is a system error while performing the search
     */
    public FullProjectData[] findFullProjects(Filter filter);

    /**
     * <p>
     * This method finds all projects that match the search criteria. Returns empty array if no
     * projects found.
     * </p>
     * @return Project array with project info, or empty array if none found
     * @param filter
     *            The search criteria to filter projects
     * @throws IllegalArgumentException
     *             If filter is null
     * @throws ProjectServicesException
     *             If there is a system error while performing the search
     */
    public Project[] findProjectsHeaders(Filter filter);

    /**
     * <p>
     * This method retrieves the project along with all known associated information. Returns null
     * if not found.
     * </p>
     * @return the project along with all known associated information
     * @param projectId
     *            The ID of the project to retrieve
     * @throws IllegalArgumentException
     *             If projectId is negative
     * @throws ProjectServicesException
     *             If there is a system error while performing the search
     */
    public FullProjectData getFullProjectData(long projectId);
}
