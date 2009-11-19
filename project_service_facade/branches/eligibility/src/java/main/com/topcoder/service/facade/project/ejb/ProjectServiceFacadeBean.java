/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.facade.project.ejb;

import com.topcoder.clients.dao.ClientDAO;
import com.topcoder.clients.dao.ClientStatusDAO;
import com.topcoder.clients.dao.CompanyDAO;
import com.topcoder.clients.dao.DAOException;
import com.topcoder.clients.dao.EntityNotFoundException;
import com.topcoder.clients.dao.ProjectDAO;
import com.topcoder.clients.dao.ProjectStatusDAO;
import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.ClientStatus;
import com.topcoder.clients.model.Company;
import com.topcoder.clients.model.Project;
import com.topcoder.clients.model.ProjectStatus;
import com.topcoder.service.facade.project.DAOFault;
import com.topcoder.service.facade.project.EntityNotFoundFault;
import com.topcoder.service.project.IllegalArgumentFault;
import com.topcoder.service.project.PersistenceFault;
import com.topcoder.service.project.ProjectService;
import com.topcoder.service.project.ProjectData;
import com.topcoder.service.project.ProjectNotFoundFault;
import com.topcoder.service.project.AuthorizationFailedFault;
import com.topcoder.service.project.UserNotFoundFault;
import com.topcoder.service.project.ProjectHasCompetitionsFault;
import com.topcoder.security.auth.module.UserProfilePrincipal;

import javax.ejb.SessionContext;
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
import javax.annotation.Resource;
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
     * <p>A <code>ClientDAO</code> providing access to available <code>Client DAO EJB</code>. This bean is
     * delegated to process the calls to the methods inherited from <code>Client Project Entities and DAO</code>
     * component.</p>
     */
    @EJB(name = "ejb/ClientDAOBean")
    private ClientDAO clientDAO = null;
    /**
     * <p>A <code>ClientStatusDAO</code> providing access to available <code>ClientStatusDAOBean EJB</code>. It is
     * delegated to process the calls to the methods inherited from <code>Client Project Entities and DAO</code>
     * component.</p>
     */
    @EJB(name = "ejb/ClientStatusDAOBean")
    private ClientStatusDAO clientStatusDAO = null;
    /**
     * <p>A <code>CompanyDAO</code> providing access to available <code>ClientStatusDAOBean EJB</code>. This bean is
     * delegated to process the calls to the methods inherited from <code>Client Project Entities and DAO</code>
     * component.</p>
     */
    @EJB(name = "ejb/CompanyDAOBean")
    private CompanyDAO companyDAO = null;
    /**
     * <p>A <code>ProjectDAO</code> providing access to available <code>ClientStatusDAOBean EJB</code>. This bean is
     * delegated to process the calls to the methods inherited from <code>Client Project Entities and DAO</code>
     * component.</p>
     */
    @EJB(name = "ejb/ProjectDAOBean")
    private ProjectDAO projectDAO = null;
    /**
     * <p>A <code>ProjectStatusDAO</code> providing access to available <code>ClientStatusDAOBean EJB</code>. It is
     * delegated to process the calls to the methods inherited from <code>Client Project Entities and DAO</code>
     * component.</p>
     */
    @EJB(name = "ejb/ProjectStatusDAOBean")
    private ProjectStatusDAO projectStatusDAO = null;
    
    /**
     * <p>
     * The instance of SessionContext that was injected by the EJB container.
     * </p>
     * <p>
     * Valid Values: sessionContext objects (possibly null).
     * </p>
     */
    @Resource
    private SessionContext sessionContext;

	/**
     * Private constant specifying administrator role.
     */
    private static final String ADMIN_ROLE = "Cockpit Administrator";
    

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
     * <p>
     * Updated for Cockpit Project Admin Release Assembly v1.0
     *      - Added check for admin user, if admin user then all projects are loaded else only for the user.
     * </p>
     *
     * @return The project data for all projects viewable from the calling principal. The returned collection will not be
     *         null or contain nulls. Possibly empty.
     * @throws PersistenceFault if a generic persistence error occurs.
     * @see ProjectService#getAllProjects()
     */
    @WebMethod
    public @WebResult List<ProjectData> getAllProjects() throws PersistenceFault, AuthorizationFailedFault, UserNotFoundFault {
        if (sessionContext.isCallerInRole(ADMIN_ROLE)) {
            return this.projectService.getAllProjects();
        } else {
            UserProfilePrincipal p = (UserProfilePrincipal) sessionContext.getCallerPrincipal();
            return this.projectService.getProjectsForUser(p.getUserId());
        }
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
     * <p>
     * Defines the operation that performs the retrieval of the list with
     * projects with the given client from the persistence. If nothing is found,
     * return an empty list.
     * </p>
     *
     * @param client
     *                the given clients to retrieve it's projects. Should not be
     *                null.
     * @return the list of Projects for the given client found in the
     *         persistence. If nothing is found, return an empty list.
     * @throws IllegalArgumentException
     *                 if client is null.
     * @throws EntityNotFoundFault
     *                 if client is not found in the persistence.
     * @throws DAOFault
     *                 if any error occurs while performing this operation.
     */
    //@WebMethod
    public List<Project> getClientProjectsForClient(Client client) throws DAOFault {
    	try {
			return this.clientDAO.getProjectsForClient(client);
		} catch(EntityNotFoundException e) {
			throw new EntityNotFoundFault(e.getMessage(), e.getCause());
		} catch (DAOException e) {
			throw new DAOFault(e.getMessage(), e.getCause());
		}
    }
 


    /**
     * <p>
     * Defines the operation that performs the retrieval of a project using the
     * given id from the persistence. If include children is true return the
     * Project.childProjects list too; otherwise the list should not be
     * returned.
     * </p>
     *
     * @param id
     *                the identifier of the Project that should be retrieved.
     *                Should be positive and not null.
     * @param includeChildren
     *                the flag that mention if the Project.childrenProjects list
     *                should be returned or not.
     * @return the Project with the given id retrieved from the persistence.
     * @throws IllegalArgumentException
     *                 if id <= 0 or id is null.
     * @throws EntityNotFoundFault
     *                 if id is not found in the persistence.
     * @throws DAOFault
     *                 if any error occurs while performing this operation.
     */
    //@WebMethod
    public Project retrieveClientProjectById(Long id, boolean includeChildren) throws EntityNotFoundFault, DAOFault {
    	try {
			return this.projectDAO.retrieveById(id, includeChildren);
		} catch(EntityNotFoundException e) {
			throw new EntityNotFoundFault(e.getMessage(), e.getCause());
		} catch (DAOException e) {
			throw new DAOFault(e.getMessage(), e.getCause());
		}
    }

    /**
     * <p>
     * Defines the operation that performs the retrieval of all projects from
     * the persistence. If include children is true return the
     * Project.childProjects list too; otherwise the list should not be
     * returned. If nothing is found, return an empty list.
     * </p>
     *
     * @param includeChildren
     *                the flag that mention if the Project.childrenProjects list
     *                should be returned or not.
     * @return the list of Projects found in the persistence. If nothing is
     *         found, return an empty list.
     * @throws DAOFault
     *                 if any error occurs while performing this operation.
     */
 //   @WebMethod
    public List<Project> retrieveClientProjects(boolean includeChildren) throws DAOFault {
    	try {
			return this.projectDAO.retrieveAll(includeChildren);
		} catch (DAOException e) {
			throw new DAOFault(e.getMessage(), e.getCause());
		}
    }
    /**
     * <p>
     * Defines the operation that performs the retrieval of an entity using the
     * given id from the persistence.
     * </p>
     *
     * @param id
     *                the identifier of the entity that should be retrieved.
     *                Should be positive and not null.
     * @return the entity with the given id retrieved from the persistence.
     * @throws IllegalArgumentException
     *                 if id <= 0 or id is null.
     * @throws EntityNotFoundFault
     *                 if an entity for the given id is not found in the
     *                 persistence.
     * @throws DAOFault
     *                 if any error occurs while performing this operation.
     */
   // @WebMethod
    public Project retrieveClientProjectByProjectId(Long id) throws EntityNotFoundFault, DAOFault {
    	try {
			return this.projectDAO.retrieveById(id);
		} catch(EntityNotFoundException e) {
			throw new EntityNotFoundFault(e.getMessage(), e.getCause());
		} catch (DAOException e) {
			throw new DAOFault(e.getMessage(), e.getCause());
		}
    }

    /**
     * <p>
     * Defines the operation that performs the retrieval of all entities from
     * the persistence. If nothing is found, return an empty list.
     * </p>
     *
     * @return the list of entities found in the persistence. If nothing is
     *         found, return an empty list.
     * @throws DAOFault
     *                 if any error occurs while performing this operation.
     */
    //@WebMethod
    public List<Project> retrieveAllClientProjects() throws DAOFault {
    	try {
			return this.projectDAO.retrieveAll();
		} catch (DAOException e) {
			throw new DAOFault(e.getMessage(), e.getCause());
		}
    }

   

    /**
     * <p>
     * Defines the operation that performs the retrieval of the list with
     * projects with the given status from the persistence. If nothing is found,
     * return an empty list.
     * </p>
     *
     * @param status
     *                the given project status to retrieve it's projects. Should
     *                not be null.
     * @return the list of Projects for the given project status found in the
     *         persistence. If nothing is found, return an empty list.
     * @throws IllegalArgumentException
     *                 if status is null.
     * @throws EntityNotFoundFault
     *                 if status is not found in the persistence.
     * @throws DAOFault
     *                 if any error occurs while performing this operation.
     */
    //@WebMethod
    public List<Project> getClientProjectsWithStatus(ProjectStatus status) throws DAOFault {
    	try {
			return this.projectStatusDAO.getProjectsWithStatus(status);
		} catch(EntityNotFoundException e) {
			throw new EntityNotFoundFault(e.getMessage(), e.getCause());
		} catch (DAOException e) {
			throw new DAOFault(e.getMessage(), e.getCause());
		}
    }
    /**
     * <p>
     * Defines the operation that performs the retrieval of an entity using the
     * given id from the persistence.
     * </p>
     *
     * @param id
     *                the identifier of the entity that should be retrieved.
     *                Should be positive and not null.
     * @return the entity with the given id retrieved from the persistence.
     * @throws IllegalArgumentException
     *                 if id <= 0 or id is null.
     * @throws EntityNotFoundFault
     *                 if an entity for the given id is not found in the
     *                 persistence.
     * @throws DAOFault
     *                 if any error occurs while performing this operation.
     */
    //@WebMethod
    public ProjectStatus retrieveProjectStatusById(Long id) throws EntityNotFoundFault, DAOFault {
    	try {
			return this.projectStatusDAO.retrieveById(id);
		} catch(EntityNotFoundException e) {
			throw new EntityNotFoundFault(e.getMessage(), e.getCause());
		} catch (DAOException e) {
			throw new DAOFault(e.getMessage(), e.getCause());
		}
    }



    /**
     * <p>
     * Defines the operation that performs the retrieval of the list with
     * projects with the given user id. If nothing is found, return an empty list.
     * <p>
     * @param userId the user id
     * @return List of Project, if nothing is found, return an empty string
     * @throws DAOException if any error occurs while performing this operation.
     */
    @WebMethod
    public List<Project> getClientProjectsByUser() throws DAOFault {
    	if ( sessionContext.getCallerPrincipal() == null || sessionContext.getCallerPrincipal().getName() == null) {
    		throw new DAOFault("Fail to get client project for user.");
    	}
    	try {
    		if (sessionContext.isCallerInRole(ADMIN_ROLE)) {
    			return this.projectDAO.retrieveAllProjectsOnly();
    		}    

			//TODO, until we fix retrieveAll, 
			 UserProfilePrincipal p = (UserProfilePrincipal) sessionContext.getCallerPrincipal();
			 //return this.clientDAO.getProjectsByUser(p.getName());
             		 return this.projectDAO.getProjectsByUser(p.getName());

		} catch(EntityNotFoundException e) {
			throw new EntityNotFoundFault(e.getMessage(), e.getCause());
		} catch (DAOException e) {
			throw new DAOFault(e.getMessage(), e.getCause());
		}
	}
}
