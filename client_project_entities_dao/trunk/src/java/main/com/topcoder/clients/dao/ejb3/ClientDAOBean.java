/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.dao.ejb3;

import java.util.ArrayList;
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

import com.topcoder.clients.dao.ClientDAO;
import com.topcoder.clients.dao.DAOConfigurationException;
import com.topcoder.clients.dao.DAOException;
import com.topcoder.clients.dao.EntityNotFoundException;
import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.Project;

/**
 * <p>
 * This class is a Stateless Session Bean realization of the ClientDAO business
 * interface.
 * </p>
 * <p>
 * This class has a default no-arg constructor.
 * </p>
 * <p>
 * This class implements the method available for the ClientDAO business
 * interface: get the projects for the corresponding client.
 * </p>
 * <p>
 * See base class for other available operations.
 * </p>
 * <p>
 * It uses the EntityManager configured in the base class to perform the needed
 * operations, retrieve the EntityManager using it's corresponding getter.
 * </p>
 * <p>
 * <strong>THREAD SAFETY:</strong> This class is technically mutable since the
 * inherited configuration properties (with {@link PersistenceContext} ) are set
 * after construction, but the container will not initialize the properties more
 * than once for the session beans and the EJB3 container ensure the thread
 * safety in this case.
 * </p>
 * 
 * @author Mafy, TCSDEVELOPER
 * @version 1.0
 */
@Local(ClientDAOLocal.class)
@Remote(ClientDAORemote.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ClientDAOBean extends GenericEJB3DAO<Client, Long> implements
        ClientDAO, ClientDAOLocal, ClientDAORemote {
	
	/**
     * The query string used to select projects.
     */
	private static final String SELECT_WORKER_PROJECT =
		"SELECT distinct project_id FROM project_worker WHERE user_account_id = ";
	/**
     * The query string used to select projects.
     */
	private static final String SELECT_MANAGER_PROJECT =
		"SELECT distinct project_id FROM project_manager WHERE user_account_id = ";


	private static final String SELECT_WORKER_PROJECT_USERNAME =
		"SELECT distinct project_id FROM project_worker p, user_account u WHERE p.user_account_id = u.user_account_id and u.user_name = ";
	/**
     * The query string used to select projects.
     */
	private static final String SELECT_MANAGER_PROJECT_USERNAME =
		"SELECT distinct project_id FROM project_manager p, user_account u WHERE p.user_account_id = u.user_account_id and  u.user_name = ";

	/**
     * The query string used to select projects.
     */
    private static final String QUERYSTRING = "select p from Project p "
            + "where p.client = :client and p.deleted = false";

    /**
     * Default no-arg constructor. Constructs a new 'ClientDAOBean' instance.
     */
    public ClientDAOBean() {
    }

    /**
     * <p>
     * Performs the retrieval of the list with projects with the given client
     * from the persistence. If nothing is found, return an empty list. Return
     * only the entities that are not marked as deleted.
     * </p>
     *
     * @param client
     *                the given clients to retrieve it's projects. Should not be
     *                null.
     * @return the list of Projects for the given client found in the
     *         persistence. If nothing is found, return an empty list.
     * @throws IllegalArgumentException
     *                 if client is null.
     * @throws EntityNotFoundException
     *                 if client is not found in the persistence.
     * @throws DAOConfigurationException
     *                 if the configured entityManager is invalid (invalid means
     *                 null here).
     * @throws DAOException
     *                 if any error occurs while performing this operation.
     */
    @SuppressWarnings("unchecked")
    public List<Project> getProjectsForClient(Client client)
        throws DAOException {
        Helper.checkNull(client, "client");

        EntityManager entityManager = Helper
                .checkEntityManager(getEntityManager());
        try {
            retrieveById(client.getId());
            return Helper.getEntities("client", client, entityManager, QUERYSTRING);
        } catch (Exception e) {
            throw Helper.WrapExceptionWithDAOException(e,
                    "Failed to get project for client.");
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
    @SuppressWarnings("unchecked")
	public List<Project> getProjectsByUser(String username) throws DAOException {
		EntityManager entityManager = Helper.checkEntityManager(getEntityManager());
		
		try {
			Query query = entityManager.createNativeQuery(SELECT_MANAGER_PROJECT_USERNAME + "'" + username + "'");
			List<Integer> managerProjects = query.getResultList();
			
			query = entityManager.createNativeQuery(SELECT_WORKER_PROJECT_USERNAME + "'" + username + "'");
			List<Integer> workerProjects = query.getResultList();
			
			//join all the distinct project id
			for(Integer projectId: workerProjects) {
				boolean existed = false;
				for(Integer existingId:managerProjects) {
					if ( existingId.longValue() == projectId.longValue()) {
						existed = true;
						break;
					}
				}
				if (!existed) {
					managerProjects.add(projectId);
				}
			}
			
			if ( managerProjects.isEmpty()) {
				return new ArrayList<Project>();
			}
		
			String queryString = "select project_id, name, po_box_number, description from project where active = 1 and project_id ";
			if ( managerProjects.size() == 1) {
				queryString += " = " + managerProjects.get(0);
			} else {
				queryString += " in (";
				for(Integer projectId:managerProjects) {
					queryString += projectId + ",";
				}
				queryString = queryString.substring(0, queryString.length()-1);
				queryString += ")";
			}

			queryString += " order by upper(name) ";

			Query query2 = entityManager.createNativeQuery(queryString);

            List list = query2.getResultList();

            List<Project> result = new ArrayList<Project>();
System.out.println("---------client project size ---" + list.size());

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

                result.add(c);

            }

			return result;

		} catch(Exception e) {
			throw Helper.WrapExceptionWithDAOException(e, "Failed to get project for user [" + username + "].");
		}
	}


}
