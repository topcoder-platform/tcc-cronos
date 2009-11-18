/*
 * Copyright (C) 2008-2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.dao.ejb3;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TransactionRequiredException;

import com.topcoder.clients.Utils;
import com.topcoder.clients.dao.DAOConfigurationException;
import com.topcoder.clients.dao.DAOException;
import com.topcoder.clients.dao.EntityNotFoundException;
import com.topcoder.clients.dao.ProjectDAO;
import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.Project;
import com.topcoder.clients.model.ProjectContestFee;

/**
 * <p>
 * This class is a Stateless Session Bean realization of the ProjectDAO business
 * interface.
 * </p>
 * <p>
 * This class has a default no-arg constructor.
 * </p>
 * <p>
 * This class implements the method available for the ProjectDAO business
 * interface: retrieve project by id and retrieve all projects.
 * </p>
 * <p>
 * See base class for other available operations.
 * </p>
 * <p>
 * It uses the EntityManager configured in the base class to perform the needed
 * operations, retrieve the EntityManager using it's corresponding getter.
 * </p>
 * 
 * <p>
 * Updated for Cockpit Release Assembly for Receipts - now fetching Client value
 * too for getProjectsByUser()
 * </p>
 *
 * <p>
 * Change note for Configurable Contest Fees v1.0 Assembly: Adds contest fee
 * related functions:
 * <ul>
 * <li>searchProjectsByClientName</li>
 * <li>searchProjectsByProjectName</li>
 * <li>saveContestFees</li>
 * <li>getContestFeesByProject</li>
 * </ul>
 * </p>
 * 
 * <p>
 * Version 1.1.1 (Cockpit Release Assembly 7 v1.0) Change Notes:
 *  - Added fetch for is_manual_prize_setting value for project.
 * </p> 
 * 
 * 
 * <p>
 * <strong>THREAD SAFETY:</strong> This class is technically mutable since the
 * inherited configuration properties (with {@link PersistenceContext}) are set
 * after construction, but the container will not initialize the properties more
 * than once for the session beans and the EJB3 container ensure the thread
 * safety in this case.
 * </p>
 *
 * @author Mafy, snow01, TCSDEVELOPER
 * @version 1.1.1
 * @since 1.0
 */
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@Stateless
public class ProjectDAOBean extends GenericEJB3DAO<Project, Long> implements
        ProjectDAO, ProjectDAOLocal, ProjectDAORemote {

	/**
	 * The query to select worker project.
	 */
	private static final String SELECT_WORKER_PROJECT = "SELECT distinct project_id FROM project_worker p, user_account u "
			+ "WHERE p.start_date <= current and current <= p.end_date and p.active =1 and "
			+ "p.user_account_id = u.user_account_id and u.user_name = ";
	/**
	 * The query string used to select projects.
	 */
	private static final String SELECT_MANAGER_PROJECT = "SELECT distinct project_id FROM project_manager p, user_account u "
            + " WHERE p.user_account_id = u.user_account_id and p.active = 1 and  u.user_name = ";

	/**
	 * The query string used to select projects.
	 * 
	 * Updated for Cockpit Release Assembly for Receipts
	 *     - now fetching client name too.
	 *     
	 * Updated for Version 1.1.1 - added fetch for is_manual_prize_setting property too.
	 */
	private static final String SELECT_PROJECT 	= "select p.project_id, p.name, p.po_box_number, p.description, "
			  + " p.active, p.sales_tax, p.payment_terms_id, p.modification_user, p.modification_date, "
			  + " p.creation_date, p.creation_user, p.is_deleted, "
			  + " cp.client_id, c.name as client_name, p.is_manual_prize_setting "
			  + " from project as p left join client_project as cp on p.project_id = cp.project_id left join client c "
              + "            on c.client_id = cp.client_id and (c.is_deleted = 0 or c.is_deleted is null) "
			  + " where p.start_date <= current and current <= p.end_date ";

	/**
	 * The JPA query string to select project contest fees.
	 *
	 * @since Configurable Contest Fees v1.0 Assembly
	 */
	private static final String SELECT_PROJECT_CONTEST_FEES_JPA = "select fee from com.topcoder.clients.model.ProjectContestFee fee"
			+ " where fee.projectId = :projectId";

	/**
	 * The native query to delete project contest fees.
	 *
	 * @since Configurable Contest Fees v1.0 Assembly
	 */
	private static final String DELETE_PROJECT_CONTEST_FEES = "delete from project_contest_fee where project_id = :projectId";

	/**
	 * The query to get all projects.
	 *
	 * @since Configurable Contest Fees v1.0 Assembly
	 */
	private static final String GET_ALL_RROJECTS = "select p from com.topcoder.clients.model.Project p";

	/**
	 * The query to get projects by project name.
	 *
	 * @since Configurable Contest Fees v1.0 Assembly
	 */
	private static final String SEARCH_PROJECTS_BY_PROJECT_NAME = "select p from com.topcoder.clients.model.Project p where upper(p.name) like :keyword";

	/**
	 * The query to get projects by client name.
	 *
	 * @since Configurable Contest Fees v1.0 Assembly
	 */
	private static final String SEARCH_PROJECTS_BY_CLIENT_NAME = "select p from com.topcoder.clients.model.Project p where upper(p.client.name) like :keyword";

    /**
     * Default no-arg constructor. Constructs a new 'ProjectDAOBean' instance.
     */
    public ProjectDAOBean() {
    }

    /**
     * Performs the retrieval of a project using the given id from the
     * persistence. If include children is true return the Project.childProjects
     * list too, otherwise the list should not be returned.
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
     * @throws EntityNotFoundException
     *                 if id is not found in the persistence.
     * @throws DAOConfigurationException
     *                 if the configured entityManager is invalid (invalid means
     *                 null here).
     * @throws DAOException
     *                 if any error occurs while performing this operation.
     */
    public Project retrieveById(Long id, boolean includeChildren)
        throws DAOException {
        Project project = retrieveById(id);

        // if includeChildren is true we return the childProjects list too
        if (includeChildren) {
            return project;
        } else {
            project.setChildProjects(null);
            return project;
        }
    }

    /**
     * Performs the retrieval of all projects from the persistence. If include
     * children is true return the Project.childProjects list too; otherwise the
     * list should not be returned. If nothing is found, return an empty list.
     * Return only the entities that are not marked as deleted.
     *
     *
     * @param includeChildren
     *                the flag that mention if the Project.childrenProjects list
     *                should be returned or not.
     * @return the list of Projects found in the persistence. If nothing is
     *         found, return an empty list.
     * @throws DAOConfigurationException
     *                 if the configured entityManager is invalid (invalid means
     *                 null here).
     * @throws DAOException
     *                 if any error occurs while performing this operation.
     */
    @SuppressWarnings("unchecked")
    public List<Project> retrieveAll(boolean includeChildren)
        throws DAOException {
        EntityManager entityManager = Helper
                .checkEntityManager(getEntityManager());

        try {
            String queryString = "select p from com.topcoder.clients.model.Project p"
                    + " where p.deleted = false and p.active = true order by upper(p.name) ";
            Query query = entityManager.createQuery(queryString);

            // Involves an unchecked conversion:
            List<Project> projects = query.getResultList();

            // if includeChildren is true we return the childProjects list too
            if (includeChildren) {
                return projects;
            } else {
                for (Project proj : projects) {
                    proj.setChildProjects(null);
                }
                return projects;
            }
        } catch (Exception e) {
            throw new DAOException("Failed to retrieve all project.", e);
        }
    }


	/**
	 * <p>
	 * Defines the operation that performs the retrieval of the list with
	 * projects with the given user id. If nothing is found, return an empty
	 * list.
	 * <p>
	 *
	 * @param username
	 *            the user name
	 * @return List of Project, if nothing is found, return an empty string
	 * @throws DAOException
	 *             if any error occurs while performing this operation.
	 */
	public List<Project> getProjectsByUser(String username) throws DAOException {
		EntityManager entityManager = Helper
				.checkEntityManager(getEntityManager());

		try {

			String queryString = SELECT_PROJECT + " and active = 1 and p.project_id in " + "("
					+ SELECT_MANAGER_PROJECT + "'" + username + "' " + "union "
					+ SELECT_WORKER_PROJECT + "'" + username + "')";
			queryString += " order by upper(name) ";

			Query query2 = entityManager.createNativeQuery(queryString);

			return convertQueryToListProjects(query2);

		} catch (Exception e) {
			throw Helper.WrapExceptionWithDAOException(e,
					"Failed to get project for user [" + username + "].");
		}
	}

	/**
	 * <p>
	 * Defines the operation that performs the retrieval of the list of all
	 * projects. If nothing is found, return an empty list.
	 * <p>
	 *
	 * @return List of Project, if nothing is found, return an empty string
	 * @throws DAOException
	 *             if any error occurs while performing this operation.
	 */
	public List<Project> retrieveAllProjectsOnly() throws DAOException {
		EntityManager entityManager = Helper
				.checkEntityManager(getEntityManager());

		try {
			String queryString = SELECT_PROJECT;

			Query query2 = entityManager.createNativeQuery(queryString);

			return convertQueryToListProjects(query2);

		} catch (Exception e) {
			throw Helper.WrapExceptionWithDAOException(e,
					"Failed to get all projects.");
		}
	}

	/**
	 * Converts the given query results into list of project.
	 * 
	 * <p>
	 * Updated for Cockpit Release Assembly for Receipts
	 *     - now setting client name too.
	 * </p>
	 * 
	 * <p>
	 * Updated for Version 1.1.1
	 *     - Added fetch for is_manual_prize_setting.
	 * </p>
	 * 
	 * @param query the specified query.
	 * @return list of project.
	 */
	@SuppressWarnings("unchecked")
	private List<Project> convertQueryToListProjects(Query query) {
		List list = query.getResultList();


		List<Project> result = new ArrayList<Project>();

		for (int i = 0; i < list.size(); i++) {

			Project c = new Project();
			Object[] os = (Object[]) list.get(i);
			if (os[0] != null)
				c.setId(Integer.parseInt(os[0].toString()));

			if (os[1] != null)
				c.setName(os[1].toString());

			if (os[2] != null)
				c.setPOBoxNumber(os[2].toString());

			if (os[3] != null)
				c.setDescription(os[3].toString());

			if (os[4] != null)
				c.setActive(((Short) os[4]).intValue() == 1 ? true : false);

			if (os[5] != null)
				c.setSalesTax(((BigDecimal) os[5]).doubleValue());

			if (os[6] != null)
				c.setPaymentTermsId(((Integer) os[6]).intValue());

			if (os[7] != null)
				c.setModifyUsername(os[7].toString());

			if (os[8] != null)
				c.setModifyDate(new Date(((Timestamp) os[8]).getTime()));

			if (os[9] != null)
				c.setCreateDate(new Date(((Timestamp) os[9]).getTime()));

			if (os[10] != null)
				c.setCreateUsername(os[10].toString());

			if (os[11] != null)
				c.setDeleted(((Short) os[11]).intValue() == 1 ? true : false);
			
			Client client = new Client();
			c.setClient(client);
			
			if (os[12] != null) {
			    client.setId(Integer.parseInt(os[12].toString()));
			}
			
			if (os[13] != null) {
                           client.setName(os[13].toString());
			}
			
			if (os[14] != null) {
			    int manualPrizeSetting = Integer.parseInt(os[14].toString());
			    c.setManualPrizeSetting(manualPrizeSetting == 0 ? false : true);
			}

			result.add(c);

		}

		return result;
	}

	/**
	 * Gets all contest fees by project id.
	 *
	 * @param projectId
	 *            the project id
	 * @return the list of project contest fees for the given project id
	 *
	 * @throws DAOException
	 *             if any persistence or other error occurs
	 *
	 * @since Configurable Contest Fees v1.0 Assembly
	 */
	@SuppressWarnings("unchecked")
	public List<ProjectContestFee> getContestFeesByProject(long projectId)
			throws DAOException {
		try {
			EntityManager entityManager = Helper
					.checkEntityManager(getEntityManager());

			Query query = entityManager
					.createQuery(SELECT_PROJECT_CONTEST_FEES_JPA);
			query.setParameter("projectId", projectId);

			return query.getResultList();
		} catch (IllegalStateException e) {
			throw Helper.WrapExceptionWithDAOException(e,
					"The EntityManager is closed.");
		} catch (Exception e) {
			throw Helper.WrapExceptionWithDAOException(e,
					"Failed to retrieve contest fees for project id of "
					+ projectId + ". Error is " + e.getMessage());
		}
	}

	/**
	 * Saves contest fees. It will refresh contest fees for the given project.
	 *
	 * @param contestFees
	 *            the contest fees
	 * @param projectId
	 *            the project id
	 *
	 * @throws IllegalArgumentException
	 *             if the associated project id is not equal to the one given or
	 *             the <code>contestFees</code> contains null element
	 * @throws DAOException
	 *             if any persistence or other error occurs
	 *
	 * @since Configurable Contest Fees v1.0 Assembly
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void saveContestFees(List<ProjectContestFee> contestFees,
			long projectId) throws DAOException {
		// we could allow this as deletion process for all contest fees
		if (contestFees == null) {
			contestFees = new ArrayList<ProjectContestFee>();
		}
		Utils.checkContestFees(contestFees, projectId);

		try {
			EntityManager entityManager = Helper
					.checkEntityManager(getEntityManager());

			// wipe out old data
			Query query = entityManager
					.createNativeQuery(DELETE_PROJECT_CONTEST_FEES);
			query.setParameter("projectId", projectId);
			query.executeUpdate();

			// save them
			for (ProjectContestFee fee : contestFees) {
				entityManager.persist(fee);
			}

			entityManager.flush();
		} catch (IllegalStateException e) {
			throw Helper.WrapExceptionWithDAOException(e,
					"The EntityManager is closed.");
		} catch (TransactionRequiredException e) {
			throw Helper.WrapExceptionWithDAOException(e,
					"This method is required to run in transaction.");
		} catch (PersistenceException e) {
			throw Helper.WrapExceptionWithDAOException(e,
					"There are errors while persisting the entity.");
		} catch (Exception e) {
			// DAOException is marked as rollbackable exception so we don't need
			// to explicitly catch it
			throw Helper.WrapExceptionWithDAOException(e,
					"Failed to save contest fees for project id of  "
							+ projectId + ". Error is " + e.getMessage());
		}
	}

	/**
	 * Searches projects by client name. The name search is case insensitive and
	 * also allows for partial name search. The name doesn't allow wildcard
	 * characters: *, %. If it is null or empty, all projects will be returned.
	 *
	 * @param clientName
	 *            the client name
	 * @return projects matched with the client name
	 *
	 * @throws IllegalArgumentException
	 *             if the client name contains wildcard character: *, %
	 * @throws DAOException
	 *             if any persistence or other error occurs
	 *
	 * @since Configurable Contest Fees v1.0 Assembly
	 */
	public List<Project> searchProjectsByClientName(String clientName)
			throws DAOException {
		try {
			return internalSearchProjectsByName(clientName, false);
		} catch (Exception e) {
			throw Helper.WrapExceptionWithDAOException(e,
					"Failed to search projects by client name of " + clientName
							+ ". Error is " + e.getMessage());
		}
	}

	/**
	 * Searches projects by project name. The name search is case insensitive
	 * and also allows for partial name search. The name doesn't allow wildcard
	 * characters: *, %. If it is null or empty, all projects will be returned.
	 *
	 * @param projectName
	 *            the project name
	 * @return projects matched with the project name
	 *
	 * @throws IllegalArgumentException
	 *             if the project name contains wildcard character: *, %
	 * @throws DAOException
	 *             if any persistence or other error occurs
	 *
	 * @since Configurable Contest Fees v1.0 Assembly
	 */
	public List<Project> searchProjectsByProjectName(String projectName)
			throws DAOException {
		try {
			return internalSearchProjectsByName(projectName, false);
		} catch (IllegalStateException e) {
			throw Helper.WrapExceptionWithDAOException(e,
					"The EntityManager is closed.");
		} catch (Exception e) {
			throw Helper.WrapExceptionWithDAOException(e,
					"Failed to search projects by project name of "
							+ projectName + ". Error is " + e.getMessage());
		}
	}

    /**
	 * <p>
	 * Check if user has permission on the client project.
	 * <p>
	 *
	 * @param username  the user name
     * @projectId client project id
     *
	 * @return true/false
	 * @throws DAOException
	 *             if any error occurs while performing this operation.
	 */
	public boolean checkClientProjectPermission(String username, long projectId) throws DAOException {
		EntityManager entityManager = Helper
				.checkEntityManager(getEntityManager());

		try {

			String queryString = SELECT_PROJECT + " and active = 1 and p.project_id in " + "("
					+ SELECT_MANAGER_PROJECT + "'" + username + "' " + "union "
					+ SELECT_WORKER_PROJECT + "'" + username + "')";
			queryString += " and p.project_id = " + projectId;

			Query query2 = entityManager.createNativeQuery(queryString);

			List result = query2.getResultList();

            if (result != null && result.size() > 0)
            {
                return true;
            }

            return false;
        

		} catch (Exception e) {
			throw Helper.WrapExceptionWithDAOException(e,
					"Failed in check client project permission for [" + username + ", " + projectId +"].");
		}
	}

    /**
	 * <p>
	 * Check if user has permission on the po number.
	 * <p>
	 *
	 * @param username  the user name
     * @param poNumber po number
     *
	 * @return true/false
	 * @throws DAOException
	 *             if any error occurs while performing this operation.
	 */
	public boolean checkPoNumberPermission(String username, String poNumber) throws DAOException {
		EntityManager entityManager = Helper
				.checkEntityManager(getEntityManager());

		try {

			String queryString = SELECT_PROJECT + " and active = 1 and p.project_id in " + "("
					+ SELECT_MANAGER_PROJECT + "'" + username + "' " + "union "
					+ SELECT_WORKER_PROJECT + "'" + username + "')";
			queryString += " and p.po_box_number = '" + poNumber + "'";

			Query query2 = entityManager.createNativeQuery(queryString);

			List result = query2.getResultList();

            if (result != null && result.size() > 0)
            {
                return true;
            }

            return false;
        

		} catch (Exception e) {
			throw Helper.WrapExceptionWithDAOException(e,
					"Failed in check client project permission for [" + username + ", " + poNumber +"].");
		}
	}

	/**
	 * Searches projects by the name which is either client name or project
	 * name. The name search is case insensitive and also allows for partial
	 * name search. The name doesn't allow wildcard characters: *, %. If it is
	 * null or empty, all projects will be returned.
	 *
	 * @param name
	 *            the project name or the client name
	 * @param
	 * @return projects matched with the given name
	 *
	 * @throws IllegalArgumentException
	 *             if the project name contains wildcard character: *, %
	 * @throws DAOException
	 *             if any persistence or other error occurs
	 *
	 * @since Configurable Contest Fees v1.0 Assembly
	 */
	@SuppressWarnings("unchecked")
	private List<Project> internalSearchProjectsByName(String name,
			boolean isClientName) throws Exception {
		EntityManager entityManager = Helper
				.checkEntityManager(getEntityManager());

		// name can not contains wildcard characters % or *
		Utils.checkSearchName(name);

		// if name is null or empty, returns all
		if (name == null || name.trim().length() == 0) {
			return entityManager.createQuery(GET_ALL_RROJECTS).getResultList();
		}

		Query query = entityManager
				.createQuery(isClientName ? SEARCH_PROJECTS_BY_CLIENT_NAME
						: SEARCH_PROJECTS_BY_PROJECT_NAME);
		query.setParameter("keyword", "%" + name.toUpperCase() + "%");

		return query.getResultList();

	}
}
