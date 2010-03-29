/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.facade.admin;

import java.util.List;

import javax.jws.WebService;

import com.topcoder.clients.model.Project;
import com.topcoder.clients.model.ProjectContestFee;

/**
 * <p>
 * This interface defines the admin service facade.
 *
 * This service provides some admin related service such as manage contest fees for each project.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 * @since Configurable Contest Fees v1.0 Assembly
 */
@WebService(name = "AdminServiceFacade")
public interface AdminServiceFacade {
	/**
	 * Gets all contest fees by project id.
	 *
	 * @param projectId
	 *            the project id
	 * @return the list of project contest fees for the given project id
	 *
	 * @throws AdminServiceFacadeException
	 *             if any error occurs during the service call
	 */
	public List<ProjectContestFee> getContestFeesByProject(long projectId)
			throws AdminServiceFacadeException;

	/**
	 * Saves contest fees. It will refresh contest fees for the given project.
	 *
	 * @param contestFees
	 *            the contest fees
	 * @param projectId
	 *            the project id
	 *
	 * @throws AdminServiceFacadeException
	 *             if any error occurs during the service call
	 */
    public void saveContestFees(List<ProjectContestFee> contestFees, long projectId) throws AdminServiceFacadeException;

	/**
	 * Searches projects by project name. The name search is case insensitive
	 * and also allows for partial name search. The name doesn't allow wildcard
	 * characters: *, %. If it is null or empty, all projects will be returned.
	 *
	 * @param projectName
	 *            the project name
	 * @return projects matched with the project name
	 *
	 * @throws AdminServiceFacadeException
	 *             if any error occurs during the service call
	 */
    public List<Project> searchProjectsByProjectName(String projectName) throws AdminServiceFacadeException;

	/**
	 * Searches projects by client name. The name search is case insensitive and
	 * also allows for partial name search. The name doesn't allow wildcard
	 * characters: *, %. If it is null or empty, all projects will be returned.
	 *
	 * @param clientName
	 *            the client name
	 * @return projects matched with the client name
	 *
	 * @throws AdminServiceFacadeException
	 *             if any error occurs during the service call
	 */
    public List<Project> searchProjectsByClientName(String clientName) throws AdminServiceFacadeException;
}
