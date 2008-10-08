/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices;

import javax.jws.WebMethod;
import javax.jws.WebService;

import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.Project;
import com.topcoder.clients.model.ProjectStatus;
import com.topcoder.clients.webservices.beans.AuthorizationFailedException;

/**
 * <p>
 *  This interface represents the ProjectService web service endpoint interface.
 *  This interface defines the methods available for
 *  the ProjectService web service: create, update and delete project,
 *  set project status for project.
 * </p>
 *
 * <p>
 *  <strong>Thread-safety:</strong>
 *  Implementations of this interface should be thread safe.
 * </p>
 *
 * @author  Mafy, CaDenza
 * @version 1.0
 */
@WebService
public interface ProjectService {

    /**
     *  This static final String field represents the 'BEAN_NAME' property of
     *  the ProjectService web service endpoint interface.
     *  Represents the EJB session bean name.
     */
    public static final String BEAN_NAME = "ProjectServiceBean";

    /**
     * Defines the operation that performs the creation of a project
     * with the given client in the persistence.
     *
     * @param project
     *      the project that should be created with the given client. Should not be null.
     * @param client
     *      the client that should be set for the project. Should not be null.
     * @return the project for that was created with the given client in the persistence.
     *
     * @throws IllegalArgumentException
     *      if the project or client is null.
     * @throws AuthorizationFailedException
     *      if the user is not authorized to perform the operation.
     * @throws ProjectServiceException
     *      if any error occurs while performing this operation.
     */
    @WebMethod
    public Project createProject(Project project, Client client)
        throws AuthorizationFailedException, ProjectServiceException;

    /**
     * Defines the operation that performs the update of the given project in the persistence.
     *
     * @param project
     *      the project that should be updated. Should not be null.
     * @return the project updated in the persistence.
     *
     * @throws IllegalArgumentException
     *      if the project is null.
     * @throws AuthorizationFailedException
     *      if the user is not authorized to perform the operation.
     * @throws ProjectServiceException
     *      if any error occurs while performing this operation.
     */
    @WebMethod
    public Project updateProject(Project project)
        throws AuthorizationFailedException, ProjectServiceException;

    /**
     * Defines the operation that performs the deletion of the given project from the persistence.
     *
     * @param  project
     *      the project that should be deleted. Should not be null.
     * @return the project deleted from the persistence.
     *
     * @throws IllegalArgumentException
     *      if the project is null.
     * @throws AuthorizationFailedException
     *      if the user is not authorized to perform the operation.
     * @throws ProjectServiceException
     *      if any error occurs while performing this operation.
     */
    @WebMethod
    public Project deleteProject(Project project)
        throws AuthorizationFailedException, ProjectServiceException;

    /**
     *  Defines the operation that performs the set of the project status
     *  for the given project in the persistence.
     *
     *  @param project
     *          the project for that should be set the project status. Should not be null.
     *  @param status
     *          the project status that should be set for the project. Should not be null.
     *  @return the project for that the project status was set in the persistence.
     *
     *  @throws IllegalArgumentException
     *          if the project or project status is null.
     *  @throws AuthorizationFailedException
     *          if the user is not authorized to perform the operation.
     *  @throws ProjectServiceException
     *          if any error occurs while performing this operation.
     */
    @WebMethod
    public Project setProjectStatus(Project project, ProjectStatus status)
        throws AuthorizationFailedException, ProjectServiceException;
}

