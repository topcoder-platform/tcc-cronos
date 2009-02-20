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
    @WebMethod
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
    @WebMethod
    public Client retrieveClientById(Long id) throws EntityNotFoundFault, DAOFault {
    	try {
			return this.clientDAO.retrieveById(id);
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
    @WebMethod
    public List<Client> retrieveAllClients() throws DAOFault {
    	try {
			return this.clientDAO.retrieveAll();
		} catch (DAOException e) {
			throw new DAOFault(e.getMessage(), e.getCause());
		}
    }

    /**
     * <p>
     * Defines the operation that performs the retrieval of all entities that
     * have the given name in the persistence. If nothing is found, return an
     * empty list.
     * </p>
     *
     * @param name
     *                the name of the entity that should be retrieved. Should be
     *                not empty and not null.
     * @return the list with entities with the given name retrieved from the
     *         persistence. If nothing is found, return an empty list.
     * @throws DAOFault
     *                 if any error occurs while performing this operation.
     */
    @WebMethod
    public List<Client> searchClientsByName(String name) throws DAOFault {
    	try {
			return this.clientDAO.searchByName(name);
		} catch (DAOException e) {
			throw new DAOFault(e.getMessage(), e.getCause());
		}
    }
    
    /**
     * <p>
     * Defines the operation that performs the salvation of an entity in the
     * persistence.
     * </p>
     *
     * @param entity
     *                the entity that should be saved. Should not be null.
     * @return the entity saved in the persistence.
     * @throws IllegalArgumentException
     *                 if entity is null.
     * @throws DAOFault
     *                 if any error occurs while performing this operation.
     */
    @WebMethod
    public Client saveClient(Client entity) throws DAOFault {
    	try {
			return this.clientDAO.save(entity);
		} catch (DAOException e) {
			throw new DAOFault(e.getMessage(), e.getCause());
		}
    }

    /**
     * <p>
     * Defines the operation that performs the deletion of an entity from the
     * persistence.
     * </p>
     *
     * @param entity
     *                the entity that should be deleted from the persistence.
     *                Should not be null.
     * @throws IllegalArgumentException
     *                 if entity is null.
     * @throws EntityNotFoundFault
     *                 if entity is not found in the persistence.
     * @throws DAOFault
     *                 if any error occurs while performing this operation.
     */
    @WebMethod
    public void deleteClient(Client entity) throws EntityNotFoundFault, DAOFault {
    	try {
			this.clientDAO.delete(entity);
		} catch(EntityNotFoundException e) {
			throw new EntityNotFoundFault(e.getMessage(), e.getCause());
		} catch (DAOException e) {
			throw new DAOFault(e.getMessage(), e.getCause());
		}
    }
    
    /**
     * <p>
     * Defines the operation that performs the retrieval of the list with
     * clients with the given status from the persistence. If nothing is found,
     * return an empty list.
     * </p>
     *
     * @param status
     *                the given client status to retrieve it's clients. Should
     *                not be null.
     * @return the list of Clients for the given client status found in the
     *         persistence. If nothing is found, return an empty list.
     * @throws IllegalArgumentException
     *                 if status is null.
     * @throws EntityNotFoundFault
     *                 if status is not found in the persistence.
     * @throws DAOFault
     *                 if any error occurs while performing this operation.
     */
    @WebMethod
    public List<Client> getClientsWithStatus(ClientStatus status) throws EntityNotFoundFault, DAOFault {
    	try {
			return this.clientStatusDAO.getClientsWithStatus(status);
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
    @WebMethod
    public ClientStatus retrieveClientStatusById(Long id) throws EntityNotFoundFault, DAOFault {
    	try {
			return this.clientStatusDAO.retrieveById(id);
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
    @WebMethod
    public List<ClientStatus> retrieveAllClientStatus() throws DAOFault {
    	try {
			return this.clientStatusDAO.retrieveAll();
		} catch (DAOException e) {
			throw new DAOFault(e.getMessage(), e.getCause());
		}
    }

    /**
     * <p>
     * Defines the operation that performs the retrieval of all entities that
     * have the given name in the persistence. If nothing is found, return an
     * empty list.
     * </p>
     *
     * @param name
     *                the name of the entity that should be retrieved. Should be
     *                not empty and not null.
     * @return the list with entities with the given name retrieved from the
     *         persistence. If nothing is found, return an empty list.
     * @throws DAOFault
     *                 if any error occurs while performing this operation.
     */
    @WebMethod
    public List<ClientStatus> searchClientStatusByName(String name) throws DAOFault {
    	try {
			return this.clientStatusDAO.searchByName(name);
		} catch (DAOException e) {
			throw new DAOFault(e.getMessage(), e.getCause());
		}
    }
    

    /**
     * <p>
     * Defines the operation that performs the salvation of an entity in the
     * persistence.
     * </p>
     *
     * @param entity
     *                the entity that should be saved. Should not be null.
     * @return the entity saved in the persistence.
     * @throws IllegalArgumentException
     *                 if entity is null.
     * @throws DAOFault
     *                 if any error occurs while performing this operation.
     */
    @WebMethod
    public ClientStatus saveClientStatus(ClientStatus entity) throws DAOFault {
    	try {
			return this.clientStatusDAO.save(entity);
		} catch (DAOException e) {
			throw new DAOFault(e.getMessage(), e.getCause());
		}
    }

    /**
     * <p>
     * Defines the operation that performs the deletion of an entity from the
     * persistence.
     * </p>
     *
     * @param entity
     *                the entity that should be deleted from the persistence.
     *                Should not be null.
     * @throws IllegalArgumentException
     *                 if entity is null.
     * @throws EntityNotFoundFault
     *                 if entity is not found in the persistence.
     * @throws DAOFault
     *                 if any error occurs while performing this operation.
     */
    @WebMethod
    public void deleteClientStatus(ClientStatus entity) throws EntityNotFoundFault, DAOFault {
    	try {
			this.clientStatusDAO.delete(entity);
		} catch(EntityNotFoundException e) {
			throw new EntityNotFoundFault(e.getMessage(), e.getCause());
		} catch (DAOException e) {
			throw new DAOFault(e.getMessage(), e.getCause());
		}
    }
    /**
     * <p>
     * Defines the operation that performs the retrieval of the list with clients for the given company from the
     * persistence. If nothing is found, return an empty list.
     * </p>
     *
     * @param company
     *            the given company to retrieve it's clients. Should not be null.
     * @return the list of Clients for the given company found in the persistence. If nothing is found, return an empty
     *         list.
     * @throws IllegalArgumentException
     *             if company is null.
     * @throws EntityNotFoundFault
     *             if company is not found in the persistence.
     * @throws DAOFault
     *             if any error occurs while performing this operation.
     */
    @WebMethod
    public List<Client> getClientsForCompany(Company company) throws DAOFault {
    	try {
			return this.companyDAO.getClientsForCompany(company);
		} catch(EntityNotFoundException e) {
			throw new EntityNotFoundFault(e.getMessage(), e.getCause());
		} catch (DAOException e) {
			throw new DAOFault(e.getMessage(), e.getCause());
		}
    }

    /**
     * <p>
     * Defines the operation that performs the retrieval of the list with projects for the given company from the
     * persistence. If nothing is found, return an empty list.
     * </p>
     *
     * @param company
     *            the given company to retrieve it's projects. Should not be null.
     * @return the list of Projects for the given company found in the persistence. If nothing is found, return an empty
     *         list.
     * @throws IllegalArgumentException
     *             if company is null.
     * @throws EntityNotFoundFault
     *             if company is not found in the persistence.
     * @throws DAOFault
     *             if any error occurs while performing this operation.
     */
    @WebMethod
    public List<Project> getClientProjectsForCompany(Company company) throws DAOFault {
    	try {
			return this.companyDAO.getProjectsForCompany(company);
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
    @WebMethod
    public Company retrieveCompanyById(Long id) throws EntityNotFoundFault, DAOFault {
    	try {
			return this.companyDAO.retrieveById(id);
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
    @WebMethod
    public List<Company> retrieveAllCompanies() throws DAOFault {
    	try {
			return this.companyDAO.retrieveAll();
		} catch (DAOException e) {
			throw new DAOFault(e.getMessage(), e.getCause());
		}
    }

    /**
     * <p>
     * Defines the operation that performs the retrieval of all entities that
     * have the given name in the persistence. If nothing is found, return an
     * empty list.
     * </p>
     *
     * @param name
     *                the name of the entity that should be retrieved. Should be
     *                not empty and not null.
     * @return the list with entities with the given name retrieved from the
     *         persistence. If nothing is found, return an empty list.
     * @throws DAOFault
     *                 if any error occurs while performing this operation.
     */
    @WebMethod
    public List<Company> searchCompaniesByName(String name) throws DAOFault {
    	try {
			return this.companyDAO.searchByName(name);
		} catch (DAOException e) {
			throw new DAOFault(e.getMessage(), e.getCause());
		}
    }


    /**
     * <p>
     * Defines the operation that performs the salvation of an entity in the
     * persistence.
     * </p>
     *
     * @param entity
     *                the entity that should be saved. Should not be null.
     * @return the entity saved in the persistence.
     * @throws IllegalArgumentException
     *                 if entity is null.
     * @throws DAOFault
     *                 if any error occurs while performing this operation.
     */
    @WebMethod
    public Company saveCompany(Company entity) throws DAOFault {
    	try {
			return this.companyDAO.save(entity);
		} catch (DAOException e) {
			throw new DAOFault(e.getMessage(), e.getCause());
		}
    }

    /**
     * <p>
     * Defines the operation that performs the deletion of an entity from the
     * persistence.
     * </p>
     *
     * @param entity
     *                the entity that should be deleted from the persistence.
     *                Should not be null.
     * @throws IllegalArgumentException
     *                 if entity is null.
     * @throws EntityNotFoundFault
     *                 if entity is not found in the persistence.
     * @throws DAOFault
     *                 if any error occurs while performing this operation.
     */
    @WebMethod
    public void deleteCompany(Company entity) throws EntityNotFoundFault, DAOFault {
    	try {
			this.companyDAO.delete(entity);
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
    @WebMethod
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
    @WebMethod
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
    @WebMethod
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
    @WebMethod
    public List<Project> retrieveAllClientProjects() throws DAOFault {
    	try {
			return this.projectDAO.retrieveAll();
		} catch (DAOException e) {
			throw new DAOFault(e.getMessage(), e.getCause());
		}
    }

    /**
     * <p>
     * Defines the operation that performs the retrieval of all entities that
     * have the given name in the persistence. If nothing is found, return an
     * empty list.
     * </p>
     *
     * @param name
     *                the name of the entity that should be retrieved. Should be
     *                not empty and not null.
     * @return the list with entities with the given name retrieved from the
     *         persistence. If nothing is found, return an empty list.
     * @throws DAOFault
     *                 if any error occurs while performing this operation.
     */
    @WebMethod
    public List<Project> searchClientProjectsByName(String name) throws DAOFault {
    	try {
			return this.projectDAO.searchByName(name);
		} catch (DAOException e) {
			throw new DAOFault(e.getMessage(), e.getCause());
		}
    }

    

    /**
     * <p>
     * Defines the operation that performs the salvation of an entity in the
     * persistence.
     * </p>
     *
     * @param entity
     *                the entity that should be saved. Should not be null.
     * @return the entity saved in the persistence.
     * @throws IllegalArgumentException
     *                 if entity is null.
     * @throws DAOFault
     *                 if any error occurs while performing this operation.
     */
    @WebMethod
    public Project saveClientProject(Project entity) throws DAOFault {
    	try {
			return this.projectDAO.save(entity);
		} catch (DAOException e) {
			throw new DAOFault(e.getMessage(), e.getCause());
		}
    }

    /**
     * <p>
     * Defines the operation that performs the deletion of an entity from the
     * persistence.
     * </p>
     *
     * @param entity
     *                the entity that should be deleted from the persistence.
     *                Should not be null.
     * @throws IllegalArgumentException
     *                 if entity is null.
     * @throws EntityNotFoundFault
     *                 if entity is not found in the persistence.
     * @throws DAOFault
     *                 if any error occurs while performing this operation.
     */
    @WebMethod
    public void deleteClientProject(Project entity) throws EntityNotFoundFault, DAOFault {
    	try {
			this.projectDAO.delete(entity);
		} catch(EntityNotFoundException e) {
			throw new EntityNotFoundFault(e.getMessage(), e.getCause());
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
    @WebMethod
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
    @WebMethod
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
     * Defines the operation that performs the retrieval of all entities from
     * the persistence. If nothing is found, return an empty list.
     * </p>
     *
     * @return the list of entities found in the persistence. If nothing is
     *         found, return an empty list.
     * @throws DAOFault
     *                 if any error occurs while performing this operation.
     */
    @WebMethod
    public List<ProjectStatus> retrieveAllProjectStatus() throws DAOFault {
    	try {
			return this.projectStatusDAO.retrieveAll();
		} catch (DAOException e) {
			throw new DAOFault(e.getMessage(), e.getCause());
		}
    }

    /**
     * <p>
     * Defines the operation that performs the retrieval of all entities that
     * have the given name in the persistence. If nothing is found, return an
     * empty list.
     * </p>
     *
     * @param name
     *                the name of the entity that should be retrieved. Should be
     *                not empty and not null.
     * @return the list with entities with the given name retrieved from the
     *         persistence. If nothing is found, return an empty list.
     * @throws DAOFault
     *                 if any error occurs while performing this operation.
     */
    @WebMethod
    public List<ProjectStatus> searchProjectStatusByName(String name) throws DAOFault {
    	try {
			return this.projectStatusDAO.searchByName(name);
		} catch (DAOException e) {
			throw new DAOFault(e.getMessage(), e.getCause());
		}
    }


    /**
     * <p>
     * Defines the operation that performs the salvation of an entity in the
     * persistence.
     * </p>
     *
     * @param entity
     *                the entity that should be saved. Should not be null.
     * @return the entity saved in the persistence.
     * @throws IllegalArgumentException
     *                 if entity is null.
     * @throws DAOFault
     *                 if any error occurs while performing this operation.
     */
    @WebMethod
    public ProjectStatus saveProjectStatus(ProjectStatus entity) throws DAOFault {
    	try {
			return this.projectStatusDAO.save(entity);
		} catch (DAOException e) {
			throw new DAOFault(e.getMessage(), e.getCause());
		}
    }

    /**
     * <p>
     * Defines the operation that performs the deletion of an entity from the
     * persistence.
     * </p>
     *
     * @param entity
     *                the entity that should be deleted from the persistence.
     *                Should not be null.
     * @throws IllegalArgumentException
     *                 if entity is null.
     * @throws EntityNotFoundFault
     *                 if entity is not found in the persistence.
     * @throws DAOFault
     *                 if any error occurs while performing this operation.
     */
    @WebMethod
    public void deleteProjectStatus(ProjectStatus entity) throws EntityNotFoundFault, DAOFault {
    	try {
			this.projectStatusDAO.delete(entity);
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
    public List<Project> getClientProjectsByUser(long userId) throws DAOFault {
    	if ( sessionContext.getCallerPrincipal() == null || sessionContext.getCallerPrincipal().getName() == null) {
    		throw new DAOFault("Fail to get client project for user.");
    	}
    	try {
//    		if (sessionContext.isCallerInRole(ADMIN_ROLE)) {
//    			return this.projectDAO.retrieveAll();
//    		}    	
			return this.clientDAO.getProjectsByUser(userId);
		} catch(EntityNotFoundException e) {
			throw new EntityNotFoundFault(e.getMessage(), e.getCause());
		} catch (DAOException e) {
			throw new DAOFault(e.getMessage(), e.getCause());
		}
	}
}
