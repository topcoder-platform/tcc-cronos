package com.topcoder.project.service;

import com.topcoder.management.project.Project;
import com.topcoder.search.builder.filter.Filter;

public interface ProjectServices {
	/**
	 * This method finds all active projects along with all known associated information. Returns empty array if no projects found.
	 * 
	 * @poseidon-object-id [Im1e310c32m11222206dc3mm7ab0]
	 * @return FullProjectData array with full projects info, or empty array if none found
	 * @throws ProjectServicesException If there is a system error while performing the search
	 */
	    public FullProjectData[] findActiveProjects();
	/**
	 * This method finds all active projects. Returns empty array if no projects found.
	 * 
	 * @poseidon-object-id [Im1e310c32m11222206dc3mm7aa7]
	 * @return Project array with project info, or empty array if none found
	 */
	    public Project[] findActiveProjectsHeaders();
	/**
	 * This method finds all projects along with all known associated information that match the search criteria. Returns empty array if no projects found.
	 * 
	 * @poseidon-object-id [Im1e310c32m11222206dc3mm7a9e]
	 * @return FullProjectData array with full projects info, or empty array if none found
	 * @param filter The search criteria to filter projects
	 * @throws IllegalArgumentException If filter is null
	 * @throws ProjectServicesException If there is a system error while performing the search
	 */
	    public FullProjectData[] findFullProjects(Filter filter);
	/**
	 * This method finds all projects that match the search criteria. Returns empty array if no projects found.
	 * 
	 * @poseidon-object-id [Im1e310c32m11222206dc3mm7a93]
	 * @return Project array with project info, or empty array if none found
	 * @param filter The search criteria to filter projects
	 * @throws IllegalArgumentException If filter is null
	 * @throws ProjectServicesException If there is a system error while performing the search
	 */
	    public Project[] findProjectsHeaders(Filter filter);
	/**
	 * This method retrieves the project along with all known associated information. Returns null if not found.
	 * 
	 * @poseidon-object-id [Im1e310c32m11222206dc3mm7a87]
	 * @return the project along with all known associated information
	 * @param projectId The ID of the project to retrieve
	 * @throws IllegalArgumentException If projectId is negative
	 * @throws ProjectServicesException If there is a system error while performing the search
	 */
	    public FullProjectData getFullProjectData(long projectId);

}
