/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.dao.ejb3;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.topcoder.clients.dao.DAOConfigurationException;
import com.topcoder.clients.dao.DAOException;
import com.topcoder.clients.dao.EntityNotFoundException;
import com.topcoder.clients.dao.ProjectDAO;
import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.Project;

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
 * Updated for Cockpit Release Assembly for Receipts
 *      - now fetching Client value too for getProjectsByUser()
 * </p>
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
 * @version 1.0
 */
@Local(ProjectDAOLocal.class)
@Remote(ProjectDAORemote.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ProjectDAOBean extends GenericEJB3DAO<Project, Long> implements
        ProjectDAO, ProjectDAOLocal, ProjectDAORemote {



	private static final String SELECT_WORKER_PROJECT = "SELECT distinct project_id FROM project_worker p, user_account u "
        + "WHERE p.start_date <= current and current <= p.end_date and p.active =1 and "
        + "p.user_account_id = u.user_account_id and u.user_name = ";
	/**
     * The query string used to select projects.
     */
	private static final String SELECT_MANAGER_PROJECT =
		"SELECT distinct project_id FROM project_manager p, user_account u WHERE p.user_account_id = u.user_account_id and p.active = 1 and  u.user_name = ";

	/**
	 * The query string used to select projects.
	 * 
	 * Updated for Cockpit Release Assembly for Receipts
	 *     - now fetching client name too.
	 */
	private static final String SELECT_PROJECT 	= "select p.project_id, p.name, p.po_box_number, p.description, "
			  + " p.active, p.sales_tax, p.payment_terms_id, p.modification_user, p.modification_date, "
			  + " p.creation_date, p.creation_user, p.is_deleted, "
			  + " cp.client_id, c.name as client_name "
			  + " from project as p left join client_project as cp on p.project_id = cp.project_id left join client c "
              + "            on c.client_id = cp.client_id and (c.is_deleted = 0 or c.is_deleted is null) "
			  + " where p.start_date <= current and current <= p.end_date ";


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

			String queryString = SELECT_PROJECT + " and active = 1 and project_id in " + "("
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

			result.add(c);

		}

		return result;
	}
}
