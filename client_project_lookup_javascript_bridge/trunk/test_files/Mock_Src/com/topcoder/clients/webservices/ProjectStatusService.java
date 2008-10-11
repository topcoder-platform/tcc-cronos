/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices;

import com.topcoder.clients.model.ProjectStatus;

/**
 * <p>
 * Mock class for testing.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ProjectStatusService {
    /**
     * <p>
     * Defines the operation that performs the creation of the given project status in the
     * persistence.
     * </p>
     *
     * @param status
     *            the project status that should be created. Should not be null.
     * @return the project status created in the persistence.
     * @throws IllegalArgumentException
     *             if the project status is null.
     * @throws ProjectStatusServiceException
     *             if any error occurs while performing this operation.
     */
    public ProjectStatus createProjectStatus(ProjectStatus status) throws ProjectStatusServiceException {
        return status;
    }

    /**
     * <p>
     * Defines the operation that performs the update of the given project status in the
     * persistence.
     * </p>
     *
     * @param status
     *            the project status that should be updated. Should not be null.
     * @return the project status updated in the persistence.
     * @throws IllegalArgumentException
     *             if the project status is null.
     * @throws ProjectStatusServiceException
     *             if any error occurs while performing this operation.
     */
    public ProjectStatus updateProjectStatus(ProjectStatus status) throws ProjectStatusServiceException {
        return status;
    }

    /**
     * <p>
     * Defines the operation that performs the deletion of the given project status from the
     * persistence.
     * </p>
     *
     * @param status
     *            the project status that should be deleted. Should not be null.
     * @return the project status deleted from the persistence.
     * @throws IllegalArgumentException
     *             if the project status is null.
     * @throws ProjectStatusServiceException
     *             if any error occurs while performing this operation.
     */
    public ProjectStatus deleteProjectStatus(ProjectStatus status) throws ProjectStatusServiceException {
        return status;
    }
}
