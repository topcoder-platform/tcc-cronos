/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices;

import javax.jws.WebMethod;
import javax.jws.WebService;

import com.topcoder.clients.model.ProjectStatus;

/**
 * <p>
 * This interface represents the <code>ProjectStatusService</code> web service endpoint interface. This
 * interface defines the methods available for the <code>ProjectStatusService</code> web service: create,
 * update and delete project status.
 * </p>
 * <p>
 * Thread safety: Implementations of this interface should be thread safe. Thread safety should be provided
 * technically or by EJB container.
 * </p>
 *
 * @author Mafy, TCSDEVELOPER
 * @version 1.0
 */
@WebService(name = "ProjectStatusService")
public interface ProjectStatusService {
    /**
     * <p>
     * This static final String field represents the 'BEAN_NAME' property of the ProjectStatusService web
     * service endpoint interface. Represents the EJB session bean name. It is initialized to a default value:
     * "ProjectStatusServiceBean" String during runtime. Accessed directly, it is public. Can not be changed.
     * It is constant.
     * </p>
     */
    public static final String BEAN_NAME = "ProjectStatusServiceBean";

    /**
     * <p>
     * Defines the operation that performs the creation of the given project status in the persistence.
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
    @WebMethod
    public ProjectStatus createProjectStatus(ProjectStatus status) throws ProjectStatusServiceException;

    /**
     * <p>
     * Defines the operation that performs the update of the given project status in the persistence.
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
    @WebMethod
    public ProjectStatus updateProjectStatus(ProjectStatus status) throws ProjectStatusServiceException;

    /**
     * <p>
     * Defines the operation that performs the deletion of the given project status from the persistence.
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
    @WebMethod
    public ProjectStatus deleteProjectStatus(ProjectStatus status) throws ProjectStatusServiceException;
}
