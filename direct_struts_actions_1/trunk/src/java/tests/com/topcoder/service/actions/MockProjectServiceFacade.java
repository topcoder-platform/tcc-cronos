/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.actions;

import java.util.ArrayList;
import java.util.List;

import com.topcoder.clients.dao.DAOException;
import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.Project;
import com.topcoder.clients.model.ProjectStatus;
import com.topcoder.security.TCSubject;
import com.topcoder.service.facade.project.DAOFault;
import com.topcoder.service.facade.project.EntityNotFoundFault;
import com.topcoder.service.facade.project.ProjectServiceFacade;
import com.topcoder.service.project.AuthorizationFailedFault;
import com.topcoder.service.project.IllegalArgumentFault;
import com.topcoder.service.project.PersistenceFault;
import com.topcoder.service.project.ProjectData;
import com.topcoder.service.project.ProjectNotFoundFault;
import com.topcoder.service.project.ProjectService;
import com.topcoder.service.project.UserNotFoundFault;

/**
 * <p>
 * This is an implementation of <code>Project Service Facade</code> web service in form of stateless session EJB. It
 * holds a reference to {@link ProjectService} which is delegated the fulfillment of requests.
 * </p>
 *
 * @author FireIce
 * @version 1.0
 */
public class MockProjectServiceFacade implements ProjectServiceFacade {

    /**
     * <p>
     * Creates a project with the given project data.
     * </p>
     * <p>
     * Note, any user can create project and the project will associate with him/her.
     * </p>
     *
     * @param tcSubject
     *            TCSubject instance contains the login security info for the current user
     * @param projectData
     *            The project data to be created. Must not be null. The <code>ProjectData.name</code> must not be
     *            null/empty. The <code>ProjectData.projectId</code>, if any, is ignored.
     * @return The project as it was created, with the <code>ProjectData.projectId</code> and <code>ProjectData.userId
     *         </code> set. Will
     *         never be null.
     * @throws IllegalArgumentFault
     *             if the given <code>ProjectData</code> is illegal.
     * @throws PersistenceFault
     *             if a generic persistence error occurs.
     */
    public ProjectData createProject(TCSubject tcSubject, ProjectData projectData) throws PersistenceFault,
        IllegalArgumentFault {
        projectData.setProjectId(1l);

        return projectData;
    }

    /**
     * <p>
     * Gets the project data for the project with the given Id.
     * </p>
     * <p>
     * Notes, only associated user can retrieve the specified project, administrator can retrieve any projects.
     * </p>
     *
     * @param tcSubject
     *            TCSubject instance contains the login security info for the current user
     * @param projectId
     *            the ID of the project to be retrieved.
     * @return The project data for the project with the given Id. Will never be null.
     * @throws PersistenceFault
     *             if a generic persistence error.
     * @throws ProjectNotFoundFault
     *             if no project with the given ID exists.
     * @throws AuthorizationFailedFault
     *             if the calling principal is not authorized to retrieve the project.
     * @see ProjectService#getProject(long)
     */
    public ProjectData getProject(TCSubject tcSubject, long projectId) throws PersistenceFault, ProjectNotFoundFault,
        AuthorizationFailedFault {
        throw new UnsupportedOperationException();
    }

    /**
     * <p>
     * Gets the project data for all projects of the given user.
     * </p>
     * <p>
     * Notes, only administrator can do this.
     * </p>
     *
     * @param tcSubject
     *            TCSubject instance contains the login security info for the current user
     * @param userId
     *            the ID of the user whose projects are to be retrieved.
     * @return The project data for all projects of the given user. The returned collection will not be null or contain
     *         nulls. Will never be empty.
     * @throws UserNotFoundFault
     *             if there are no projects linked to the given user.
     * @throws PersistenceFault
     *             if a generic persistence error occurs.
     * @throws AuthorizationFailedFault
     *             should not be thrown from version 1.1. It is here only to leave the web interface unchanged.
     * @see ProjectService#getProjectsForUser(long)
     */
    public List<ProjectData> getProjectsForUser(TCSubject tcSubject, long userId) throws PersistenceFault,
        UserNotFoundFault, AuthorizationFailedFault {
        throw new UnsupportedOperationException();
    }

    /**
     * <p>
     * Gets the project data for all projects viewable from the calling principal.
     * </p>
     * <p>
     * Notes, for user, it will retrieve only the projects associated with him; for administrators, it will retrieve all
     * the existing projects.
     * </p>
     *
     * @param tcSubject
     *            TCSubject instance contains the login security info for the current user
     * @return The project data for all projects viewable from the calling principal. The returned collection will not
     *         be null or contain nulls. Possibly empty.
     * @throws PersistenceFault
     *             if a generic persistence error occurs.
     */
    public List<ProjectData> getAllProjects(TCSubject tcSubject) throws PersistenceFault, AuthorizationFailedFault,
        UserNotFoundFault {
        List<ProjectData> projectDatas = new ArrayList<ProjectData>();

        for (int i = 1; i <= 5; i++) {
            ProjectData projectData = new ProjectData();
            projectData.setProjectId((long) i);
            projectData.setName("Test Project " + i);
            projectData.setDescription("Test " + i);

            projectDatas.add(projectData);
        }

        return projectDatas;
    }

    /**
     * <p>
     * Updates the project data for the given project.
     * </p>
     * <p>
     * Notes, only project-associated user can update that project, but administrator can update any project.
     * </p>
     *
     * @param tcSubject
     *            TCSubject instance contains the login security info for the current user
     * @param projectData
     *            The project data to be updated. Must not be null. The <code>ProjectData.name</code> must not be
     *            null/empty. The <code>ProjectData.projectId</code> must be non-null.
     * @throws IllegalArgumentFault
     *             if the given <code>ProjectData</code> is illegal.
     * @throws ProjectNotFoundFault
     *             if no project with the given ID exists.
     * @throws AuthorizationFailedFault
     *             if the calling principal is not authorized to update the project.
     * @throws PersistenceFault
     *             if a generic persistence error.
     */
    public void updateProject(TCSubject tcSubject, ProjectData projectData) throws PersistenceFault,
        ProjectNotFoundFault, AuthorizationFailedFault, IllegalArgumentFault {
        throw new UnsupportedOperationException();
    }

    /**
     * <p>
     * Defines the operation that performs the retrieval of the list with projects with the given client from the
     * persistence. If nothing is found, return an empty list.
     * </p>
     *
     * @param tcSubject
     *            TCSubject instance contains the login security info for the current user
     * @param client
     *            the given clients to retrieve it's projects. Should not be null.
     * @return the list of Projects for the given client found in the persistence. If nothing is found, return an empty
     *         list.
     * @throws IllegalArgumentException
     *             if client is null.
     * @throws EntityNotFoundFault
     *             if client is not found in the persistence.
     * @throws DAOFault
     *             if any error occurs while performing this operation.
     */
    public List<Project> getClientProjectsForClient(TCSubject tcSubject, Client client) throws DAOFault {
        throw new UnsupportedOperationException();
    }

    /**
     * <p>
     * Defines the operation that performs the retrieval of a project using the given id from the persistence. If
     * include children is true return the Project.childProjects list too; otherwise the list should not be returned.
     * </p>
     *
     * @param tcSubject
     *            TCSubject instance contains the login security info for the current user
     * @param id
     *            the identifier of the Project that should be retrieved. Should be positive and not null.
     * @param includeChildren
     *            the flag that mention if the Project.childrenProjects list should be returned or not.
     * @return the Project with the given id retrieved from the persistence.
     * @throws IllegalArgumentException
     *             if id <= 0 or id is null.
     * @throws EntityNotFoundFault
     *             if id is not found in the persistence.
     * @throws DAOFault
     *             if any error occurs while performing this operation.
     */
    public Project retrieveClientProjectById(TCSubject tcSubject, Long id, boolean includeChildren)
        throws EntityNotFoundFault, DAOFault {
        throw new UnsupportedOperationException();
    }

    /**
     * <p>
     * Defines the operation that performs the retrieval of all projects from the persistence. If include children is
     * true return the Project.childProjects list too; otherwise the list should not be returned. If nothing is found,
     * return an empty list.
     * </p>
     *
     * @param tcSubject
     *            TCSubject instance contains the login security info for the current user
     * @param includeChildren
     *            the flag that mention if the Project.childrenProjects list should be returned or not.
     * @return the list of Projects found in the persistence. If nothing is found, return an empty list.
     * @throws DAOFault
     *             if any error occurs while performing this operation.
     */
    public List<Project> retrieveClientProjects(TCSubject tcSubject, boolean includeChildren) throws DAOFault {
        throw new UnsupportedOperationException();
    }

    /**
     * <p>
     * Defines the operation that performs the retrieval of an entity using the given id from the persistence.
     * </p>
     *
     * @param tcSubject
     *            TCSubject instance contains the login security info for the current user
     * @param id
     *            the identifier of the entity that should be retrieved. Should be positive and not null.
     * @return the entity with the given id retrieved from the persistence.
     * @throws IllegalArgumentException
     *             if id <= 0 or id is null.
     * @throws EntityNotFoundFault
     *             if an entity for the given id is not found in the persistence.
     * @throws DAOFault
     *             if any error occurs while performing this operation.
     */
    public Project retrieveClientProjectByProjectId(TCSubject tcSubject, Long id) throws EntityNotFoundFault, DAOFault {
        throw new UnsupportedOperationException();
    }

    /**
     * <p>
     * Defines the operation that performs the retrieval of all entities from the persistence. If nothing is found,
     * return an empty list.
     * </p>
     *
     * @param tcSubject
     *            TCSubject instance contains the login security info for the current user
     * @return the list of entities found in the persistence. If nothing is found, return an empty list.
     * @throws DAOFault
     *             if any error occurs while performing this operation.
     */
    public List<Project> retrieveAllClientProjects(TCSubject tcSubject) throws DAOFault {
        throw new UnsupportedOperationException();
    }

    /**
     * <p>
     * Defines the operation that performs the retrieval of the list with projects with the given status from the
     * persistence. If nothing is found, return an empty list.
     * </p>
     *
     * @param tcSubject
     *            TCSubject instance contains the login security info for the current user
     * @param status
     *            the given project status to retrieve it's projects. Should not be null.
     * @return the list of Projects for the given project status found in the persistence. If nothing is found, return
     *         an empty list.
     * @throws IllegalArgumentException
     *             if status is null.
     * @throws EntityNotFoundFault
     *             if status is not found in the persistence.
     * @throws DAOFault
     *             if any error occurs while performing this operation.
     */
    public List<Project> getClientProjectsWithStatus(TCSubject tcSubject, ProjectStatus status) throws DAOFault {
        throw new UnsupportedOperationException();
    }

    /**
     * <p>
     * Defines the operation that performs the retrieval of an entity using the given id from the persistence.
     * </p>
     *
     * @param tcSubject
     *            TCSubject instance contains the login security info for the current user
     * @param id
     *            the identifier of the entity that should be retrieved. Should be positive and not null.
     * @return the entity with the given id retrieved from the persistence.
     * @throws IllegalArgumentException
     *             if id <= 0 or id is null.
     * @throws EntityNotFoundFault
     *             if an entity for the given id is not found in the persistence.
     * @throws DAOFault
     *             if any error occurs while performing this operation.
     */
    public ProjectStatus retrieveProjectStatusById(TCSubject tcSubject, Long id) throws EntityNotFoundFault, DAOFault {
        throw new UnsupportedOperationException();
    }

    /**
     * <p>
     * Defines the operation that performs the retrieval of the list with projects with the given user id. If nothing is
     * found, return an empty list.
     * <p>
     *
     * @param tcSubject
     *            TCSubject instance contains the login security info for the current user
     * @param userId
     *            the user id
     * @return List of Project, if nothing is found, return an empty string
     * @throws DAOException
     *             if any error occurs while performing this operation.
     */
    public List<Project> getClientProjectsByUser(TCSubject tcSubject) throws DAOFault {
        List<Project> projects = new ArrayList<Project>();

        for (int i = 0; i < 5; i++) {
            Project project = new Project();
            project.setName("Test Project " + i);
            project.setDescription("Test " + i);
            project.setId(i + 1);

            projects.add(project);
        }

        return projects;
    }
}
