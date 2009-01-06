/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.facade.project.ejb;

import com.topcoder.service.project.IllegalArgumentFault;
import com.topcoder.service.project.PersistenceFault;
import com.topcoder.service.project.ProjectService;
import com.topcoder.service.project.ProjectData;
import com.topcoder.service.project.ProjectNotFoundFault;
import com.topcoder.service.project.AuthorizationFailedFault;
import com.topcoder.service.project.UserNotFoundFault;
import com.topcoder.service.project.ProjectHasCompetitionsFault;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.EJB;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import java.util.List;

import org.jboss.ws.annotation.EndpointConfig;

/**
 * <p>This is an implementation of <code>Project Service Facade</code> web service in form of stateless session EJB. It
 * holds a reference to {@link ProjectService} which is delegated the fulfillment of requests.</p> 
 * </p>
 *
 * @author isv
 * @version 1.0
 */
@Stateless
@WebService
@EndpointConfig(configName = "Standard WSSecurity Endpoint")
@DeclareRoles({"Cockpit User", "Cockpit Administrator" })
@RolesAllowed({"Cockpit User", "Cockpit Administrator" })
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ProjectServiceFacadeBean implements ProjectServiceFacadeLocal, ProjectServiceFacadeRemote {

    /**
     * <p>A <code>ProjectService</code> providing access to available <code>Project Service EJB</code>. This bean is
     * delegated to process the calls to the methods inherited from <code>Project Service</code> component.</p>
     */
    @EJB(name = "ejb/ProjectService")
    private ProjectService projectService = null;

    /**
     * <p>Creates a project with the given project data.</p>
     *
     * <p>Note, any user can create project and the project will associate with him/her.</p>
     *
     * @param projectData The project data to be created. Must not be null. The <code>ProjectData.name</code> must not be
     * null/empty. The <code>ProjectData.projectId</code>, if any, is ignored.
     * @return The project as it was created, with the <code>ProjectData.projectId</code> and <code>ProjectData.userId
     *         </code> set. Will never be null.
     * @throws IllegalArgumentFault if the given <code>ProjectData</code> is illegal.
     * @throws PersistenceFault if a generic persistence error occurs.
     * @see ProjectService#createProject(ProjectData)
     */
    @WebMethod
    public @WebResult ProjectData createProject(@WebParam ProjectData projectData) throws PersistenceFault,
                                                                                          IllegalArgumentFault {
        return this.projectService.createProject(projectData);
    }

    /**
     * <p>Gets the project data for the project with the given Id.</p>
     *
     * <p>Notes, only associated user can retrieve the specified project, administrator can retrieve any projects.</p>
     *
     * @param projectId the ID of the project to be retrieved.
     * @return The project data for the project with the given Id. Will never be null.
     * @throws PersistenceFault if a generic persistence error.
     * @throws ProjectNotFoundFault if no project with the given ID exists.
     * @throws AuthorizationFailedFault if the calling principal is not authorized to retrieve the project.
     * @see ProjectService#getProject(long)
     */
    @WebMethod
    public @WebResult ProjectData getProject(@WebParam long projectId) throws PersistenceFault, ProjectNotFoundFault,
                                                                              AuthorizationFailedFault {
        return this.projectService.getProject(projectId);
    }

    /**
     * <p>Gets the project data for all projects of the given user.</p>
     *
     * <p>Notes, only administrator can do this.</p>
     *
     * @param userId the ID of the user whose projects are to be retrieved.
     * @return The project data for all projects of the given user. The returned collection will not be null or contain
     *         nulls. Will never be empty.
     * @throws UserNotFoundFault if there are no projects linked to the given user.
     * @throws PersistenceFault if a generic persistence error occurs.
     * @throws AuthorizationFailedFault should not be thrown from version 1.1. It is here only to leave the web interface
     * unchanged.
     * @see ProjectService#getProjectsForUser(long)
     */
    @WebMethod
    public @WebResult List<ProjectData> getProjectsForUser(@WebParam long userId) throws PersistenceFault,
                                                                                         UserNotFoundFault,
                                                                                         AuthorizationFailedFault {
        return this.projectService.getProjectsForUser(userId);
    }

    /**
     * <p>Gets the project data for all projects viewable from the calling principal.</p>
     *
     * <p>Notes, for user, it will retrieve only the projects associated with him; for administrators, it will retrieve all
     * the existing projects.</p>
     *
     * @return The project data for all projects viewable from the calling principal. The returned collection will not be
     *         null or contain nulls. Possibly empty.
     * @throws PersistenceFault if a generic persistence error occurs.
     * @see ProjectService#getAllProjects()
     */
    @WebMethod
    public @WebResult List<ProjectData> getAllProjects() throws PersistenceFault {
        return this.projectService.getAllProjects();
    }

    /**
     * <p>Updates the project data for the given project.</p>
     *
     * <p>Notes, only project-associated user can update that project, but administrator can update any project.</p>
     *
     * @param projectData The project data to be updated. Must not be null. The <code>ProjectData.name</code> must not be
     * null/empty. The <code>ProjectData.projectId</code> must be non-null.
     * @throws IllegalArgumentFault if the given <code>ProjectData</code> is illegal.
     * @throws ProjectNotFoundFault if no project with the given ID exists.
     * @throws AuthorizationFailedFault if the calling principal is not authorized to update the project.
     * @throws PersistenceFault if a generic persistence error.
     * @see ProjectService#updateProject(ProjectData)
     */
    @WebMethod
    public void updateProject(@WebParam ProjectData projectData) throws PersistenceFault, ProjectNotFoundFault,
                                                                        AuthorizationFailedFault, IllegalArgumentFault {
        this.projectService.updateProject(projectData);
    }

    /**
     * <p>Deletes the project data for the project with the given Id.</p>
     *
     * <p>Notes, only project-associated user can delete that project, but administrator can delete any project.</p>
     *
     * @param projectId the ID of the project be deleted.
     * @return Whether the project was found, and thus deleted.
     * @throws AuthorizationFailedFault if the calling principal is not authorized to delete the project.
     * @throws ProjectHasCompetitionsFault if the project cannot be deleted since it has competitions associated with it.
     * @throws PersistenceFault if a generic persistence error.
     * @see ProjectService#deleteProject(long)
     */
    @WebMethod
    public boolean deleteProject(@WebParam long projectId) throws PersistenceFault, AuthorizationFailedFault,
                                                                  ProjectHasCompetitionsFault {
        return this.projectService.deleteProject(projectId);
    }
}
