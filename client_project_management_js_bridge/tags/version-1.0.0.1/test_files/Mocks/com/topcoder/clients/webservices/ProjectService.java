package com.topcoder.clients.webservices;

import javax.jws.WebMethod;

import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.Project;
import com.topcoder.clients.model.ProjectStatus;
import com.topcoder.clients.webservices.beans.AuthorizationFailedException;

public class ProjectService {

    /**
     * Defines the operation that performs the creation of a project with the given client in the
     * persistence.
     *
     * @param project
     *            the project that should be created with the given client. Should not be null.
     * @param client
     *            the client that should be set for the project. Should not be null.
     * @return the project for that was created with the given client in the persistence.
     * @throws IllegalArgumentException
     *             if the project or client is null.
     * @throws AuthorizationFailedException
     *             if the user is not authorized to perform the operation.
     * @throws ProjectServiceException
     *             if any error occurs while performing this operation.
     */
    public Project createProject(Project project, Client client) throws AuthorizationFailedException,
        ProjectServiceException {
        return project;
    }

    /**
     * Defines the operation that performs the update of the given project in the persistence.
     *
     * @param project
     *            the project that should be updated. Should not be null.
     * @return the project updated in the persistence.
     * @throws IllegalArgumentException
     *             if the project is null.
     * @throws AuthorizationFailedException
     *             if the user is not authorized to perform the operation.
     * @throws ProjectServiceException
     *             if any error occurs while performing this operation.
     */
    public Project updateProject(Project project) throws AuthorizationFailedException,
        ProjectServiceException {
        return project;
    }

    /**
     * Defines the operation that performs the deletion of the given project from the persistence.
     *
     * @param project
     *            the project that should be deleted. Should not be null.
     * @return the project deleted from the persistence.
     * @throws IllegalArgumentException
     *             if the project is null.
     * @throws AuthorizationFailedException
     *             if the user is not authorized to perform the operation.
     * @throws ProjectServiceException
     *             if any error occurs while performing this operation.
     */
    public Project deleteProject(Project project) throws AuthorizationFailedException,
        ProjectServiceException {
        return project;
    }

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
    public Project setProjectStatus(Project project, ProjectStatus status)
        throws AuthorizationFailedException, ProjectServiceException{
        return project;
    }
}
