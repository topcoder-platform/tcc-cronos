/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
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
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.topcoder.clients.dao.DAOConfigurationException;
import com.topcoder.clients.dao.DAOException;
import com.topcoder.clients.dao.EntityNotFoundException;
import com.topcoder.clients.dao.ProjectDAO;
import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.Project;
import com.topcoder.clients.model.ProjectContestFee;
import com.topcoder.util.idgenerator.IDGenerator;
import com.topcoder.util.idgenerator.IDGeneratorFactory;

/**
 * <p>
 * This class is a Stateless Session Bean realization of the ProjectDAO business interface.
 * </p>
 * <p>
 * This class has a default no-arg constructor.
 * </p>
 * <p>
 * This class implements the method available for the ProjectDAO business interface: retrieve project by id and retrieve
 * all projects.
 * </p>
 * <p>
 * See base class for other available operations.
 * </p>
 * <p>
 * It uses the EntityManager configured in the base class to perform the needed operations, retrieve the EntityManager
 * using it's corresponding getter.
 * </p>
 * <p>
 * Updated for Cockpit Release Assembly for Receipts - now fetching Client value too for getProjectsByUser()
 * </p>
 * <p>
 * Change note for Configurable Contest Fees v1.0 Assembly: Adds contest fee related functions:
 * <ul>
 * <li>searchProjectsByClientName</li>
 * <li>searchProjectsByProjectName</li>
 * <li>saveContestFees</li>
 * <li>getContestFeesByProject</li>
 * </ul>
 * </p>
 * <p>
 * Change note for Cockpit Release Assembly 7 v1.0:
 * <ul>
 * <li>Added fetch for is_manual_prize_setting value for project.</li>
 * </ul>
 * </p>
 * <p>
 * Change note for 1.1 version, three new methods are added:
 * <ul>
 * <li>addUserToBillingProjects</li>
 * <li>removeUserFromBillingProjects</li>
 * <li>getProjectsByClientId</li>
 * </ul>
 * </p>
 * <p>
 * Change note for 1.2 version, two new methods are added:
 * <ul>
 * <li>Add updateProjectBudget method to update the project’s budget.</li>
 * <li>Add getUsersByProject method to get all users' name who have access of the specified billing project.</li>
 * </ul>
 * </p>
 * <p>
 * <strong>THREAD SAFETY:</strong> This class is technically mutable since the inherited configuration properties (with
 * {@link PersistenceContext}) are set after construction, but the container will not initialize the properties more
 * than once for the session beans and the EJB3 container ensure the thread safety in this case.
 * </p>
 *
 * @author Mafy, snow01, flying2hk, nhzp339, TCSDEVELOPER
 * @version 1.2
 */
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@Stateless
public class ProjectDAOBean extends GenericEJB3DAO<Project, Long> implements
        ProjectDAO, ProjectDAOLocal, ProjectDAORemote {

    /**
     * The query to select worker project.
     */
    private static final String SELECT_WORKER_PROJECT = "SELECT distinct project_id FROM project_worker p,"
            + " user_account u WHERE p.start_date <= current and current <= p.end_date and p.active =1 and "
            + "p.user_account_id = u.user_account_id and u.user_name = ";
    /**
     * The query string used to select projects.
     */
    private static final String SELECT_MANAGER_PROJECT = "SELECT distinct project_id FROM project_manager p,"
            + " user_account u WHERE p.user_account_id = u.user_account_id and p.active = 1 and  u.user_name = ";

    /**
     * The query string used to select projects.
     *
     * Updated for Cockpit Release Assembly for Receipts
     *     - now fetching client name too.
     *
     * Updated for Version 1.1.1 - added fetch for is_manual_prize_setting property too.
     */
    private static final String SELECT_PROJECT = "select p.project_id, p.name, p.po_box_number, p.description, "
              + " p.active, p.sales_tax, p.payment_terms_id, p.modification_user, p.modification_date, "
              + " p.creation_date, p.creation_user, p.is_deleted,"
              + " cp.client_id, c.name as client_name, p.is_manual_prize_setting, p.budget "
              + " from project as p left join client_project as cp on p.project_id = cp.project_id left join client c "
              + "            on c.client_id = cp.client_id and (c.is_deleted = 0 or c.is_deleted is null) "
              + " where p.start_date <= current and current <= p.end_date ";

    /**
     * The query string used to select projects.
     */
    private static final String SELECT_PROJECT_BY_CLIENT_ID = "select p.project_id, p.name, p.po_box_number,"
        + " p.description,  p.active, p.sales_tax, p.payment_terms_id, p.modification_user, p.modification_date, "
        + " p.creation_date, p.creation_user, p.is_deleted, "
        + " cp.client_id, c.name as client_name, p.is_manual_prize_setting, p.budget "
        + " from project as p, client_project as cp, client as c "
        + " where p.start_date <= current and current <= p.end_date "
        + " and c.client_id = cp.client_id and (p.is_deleted = 0 or p.is_deleted is null) "
        + " and p.project_id = cp.project_id and cp.client_id = ";

    /**
     * The JPA query string to select project contest fees.
     *
     * @since Configurable Contest Fees v1.0 Assembly
     */
    private static final String SELECT_PROJECT_CONTEST_FEES_JPA = "select fee from"
            + " com.topcoder.clients.model.ProjectContestFee fee where fee.projectId = :projectId";

    /**
     * The native query to delete project contest fees.
     *
     * @since Configurable Contest Fees v1.0 Assembly
     */
    private static final String DELETE_PROJECT_CONTEST_FEES = "delete from project_contest_fee"
            + " where project_id = :projectId";

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
    private static final String SEARCH_PROJECTS_BY_PROJECT_NAME = "select p from com.topcoder.clients.model.Project p"
            + " where upper(p.name) like :keyword";

    /**
     * The query to get projects by client name.
     *
     * @since Configurable Contest Fees v1.0 Assembly
     */
    private static final String SEARCH_PROJECTS_BY_CLIENT_NAME = "select p from com.topcoder.clients.model.Project p"
            + " where upper(p.client.name) like :keyword";

    /**
     * <p>
     * The query to get the user account id by user name.
     * </p>
     *
     * @since 1.1
     */
    private static final String SEARCH_USER_ACCOUNT_ID_BY_USRENAME = "select user_account_id from user_account"
            + " where user_name = :userName";


    /**
     * <p>
     * Represents the default company id for inserting dummy user account.
     * </p>
     */
    private static final long DEFAULT_COMPANY_ID = 1;

    /**
     * <p>
     * The query to insert dummy user account.
     * </p>
     *
     * @since 1.1
     */
    private static final String INSERT_DUMMY_USER_ACCOUNT = "insert into user_account"
            + " (user_account_id, company_id, account_status_id, user_name, password, creation_date, creation_user,"
            + " modification_date, modification_user) values "
            + "(:userAccountId, " + DEFAULT_COMPANY_ID + ", 1, :userName, '', CURRENT, '', CURRENT, '')";

    /**
     * <p>
     * The query to insert project manager relationship.
     * </p>
     *
     * @since 1.1
     */
    private static final String INSERT_PROJECT_MANAGER = "insert into project_manager (project_id, user_account_id,"
            + " pay_rate, cost, active, creation_date, creation_user, modification_date, modification_user) "
            + "values (:projectId, :userAccountId, 0, 0, 1, CURRENT, 'System', CURRENT, 'System')";

    /**
     * <p>
     * The query to delete project manager relationship.
     * </p>
     *
     * @since 1.1
     */
    private static final String DELETE_PROJECT_MANAGER = "delete from project_manager"
            + " where project_id = :projectId and user_account_id = :userAccountId";

    /**
     * <p>
     * The key to get IDGenerator instance.
     * </p>
     *
     * @since 1.1
     */
    private static final String ID_KEY = "com.topcoder.clients.model.User";

    /**
     * <p>
     * The query string used to select project's budget.
     * </p>
     *
     * @since 1.2
     */
    private static final String SELECT_PROJECT_BUDGET = "select budget from project where project_id = :projectId";

    /**
     * <p>
     * The string used to update project's budget.
     * </p>
     *
     * @since 1.2
     */
    private static final String UPDATE_PROJECT_BUDGET =
        "update project set budget = :budget where project_id = :projectId";

    /**
     * <p>
     * The string used to insert project's budget audit record.
     * </p>
     *
     * @since 1.2
     */
    private static final String INSERT_PROJECT_BUDGET_AUDIT = "insert into project_budget_audit (project_id,"
            + " changed_amount, creation_date, creation_user) values (:projectId, :updatedAmount, CURRENT, :username)";

    /**
     * <p>
     * The string used to select users who have access to a specific project.
     * </p>
     *
     * @since 1.2
     */
    private static final String SELECT_USERS_BY_PROJECT = "SELECT distinct user_name FROM project_manager p, "
            + "user_account u WHERE p.user_account_id = u.user_account_id and p.active = 1 and"
            + " p.project_id = :projectId union SELECT distinct user_name FROM project_worker p,"
            + "user_account u WHERE p.start_date <= current and current <= p.end_date and p.active =1 and "
            + "p.user_account_id = u.user_account_id and p.project_id = :projectId";

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
                    + " where (p.deleted is null or p.deleted = false) and p.active = true order by upper(p.name) ";
            Query query = entityManager.createQuery(queryString);

            // Involves an unchecked conversion
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
     * @throws DAOConfigurationException
     *             if the configured entityManager is invalid (invalid means null here).
     */
    public List<Project> getProjectsByUser(String username) throws DAOException {
        EntityManager entityManager = Helper
                .checkEntityManager(getEntityManager());

        try {

            String queryString = SELECT_PROJECT + " and p.active = 1 and p.project_id in " + "("
                    + SELECT_MANAGER_PROJECT + "'" + username + "' " + "union "
                    + SELECT_WORKER_PROJECT + "'" + username + "')";
            queryString += " order by upper(name) ";

            Query query = entityManager.createNativeQuery(queryString);

            return convertQueryToListProjects(query);

        } catch (Exception e) {
            throw Helper.wrapWithDAOException(e,
                    "Failed to get projects for user [" + username + "].");
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
     * @throws DAOConfigurationException
     *             if the configured entityManager is invalid (invalid means null here).
     */
    public List<Project> retrieveAllProjectsOnly() throws DAOException {
        EntityManager entityManager = Helper
                .checkEntityManager(getEntityManager());

        try {
            Query query = entityManager.createNativeQuery(SELECT_PROJECT);

            return convertQueryToListProjects(query);

        } catch (Exception e) {
            throw Helper.wrapWithDAOException(e,
                    "Failed to get all projects.");
        }
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
     * @throws DAOConfigurationException
     *             if the configured entityManager is invalid (invalid means null here).
     * @since Configurable Contest Fees v1.0 Assembly
     */
    @SuppressWarnings("unchecked")
    public List<ProjectContestFee> getContestFeesByProject(long projectId) throws DAOException {
        EntityManager entityManager = Helper.checkEntityManager(getEntityManager());
        try {

            Query query = entityManager.createQuery(SELECT_PROJECT_CONTEST_FEES_JPA);
            query.setParameter("projectId", projectId);

            return query.getResultList();
        } catch (Exception e) {
            throw Helper.wrapWithDAOException(e, "Failed to retrieve contest fees for project id of " + projectId
                    + ". Error is " + e.getMessage());
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
     * @throws DAOConfigurationException
     *             if the configured entityManager is invalid (invalid means null here).
     * @since Configurable Contest Fees v1.0 Assembly
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void saveContestFees(List<ProjectContestFee> contestFees, long projectId) throws DAOException {
        // we could allow this as deletion process for all contest fees
        if (contestFees == null) {
            contestFees = new ArrayList<ProjectContestFee>();
        } else {
            for (ProjectContestFee fee : contestFees) {
                if (fee == null) {
                    throw new IllegalArgumentException("contestFees should not contain null element.");
                }

                if (fee.getProjectId() != projectId) {
                    throw new IllegalArgumentException("contestFee in the list should belong to project of id "
                            + projectId);
                }
            }
        }

        EntityManager entityManager = Helper.checkEntityManager(getEntityManager());

        try {
            // wipe out old data
            Query query = entityManager.createNativeQuery(DELETE_PROJECT_CONTEST_FEES);
            query.setParameter("projectId", projectId);
            query.executeUpdate();

            // save them
            for (ProjectContestFee fee : contestFees) {
                entityManager.persist(fee);
            }

            entityManager.flush();
        } catch (Exception e) {
            // DAOException is marked as rollbackable exception so we don't need
            // to explicitly catch it
            throw Helper.wrapWithDAOException(e, "Failed to save contest fees for project id of  " + projectId
                    + ". Error is " + e.getMessage());
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
     * @throws DAOConfigurationException
     *             if the configured entityManager is invalid (invalid means null here).
     * @since Configurable Contest Fees v1.0 Assembly
     */
    public List<Project> searchProjectsByClientName(String clientName)
            throws DAOException {
        checkSearchName(clientName);
        try {
            return internalSearchProjectsByName(clientName, true);
        } catch (Exception e) {
            throw Helper.wrapWithDAOException(e,
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
     * @throws DAOConfigurationException
     *             if the configured entityManager is invalid (invalid means null here).
     *
     * @since Configurable Contest Fees v1.0 Assembly
     */
    public List<Project> searchProjectsByProjectName(String projectName)
            throws DAOException {
        checkSearchName(projectName);
        try {
            return internalSearchProjectsByName(projectName, false);
        } catch (Exception e) {
            throw Helper.wrapWithDAOException(e,
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
     * @param projectId client project id
     *
     * @return true/false
     * @throws DAOException
     *             if any error occurs while performing this operation.
     * @throws DAOConfigurationException
     *             if the configured entityManager is invalid (invalid means null here).
     */
    @SuppressWarnings("unchecked")
    public boolean checkClientProjectPermission(String username, long projectId) throws DAOException {
        EntityManager entityManager = Helper
                .checkEntityManager(getEntityManager());

        try {

            String queryString = SELECT_PROJECT + " and p.active = 1 and p.project_id in " + "("
                    + SELECT_MANAGER_PROJECT + "'" + username + "' " + "union "
                    + SELECT_WORKER_PROJECT + "'" + username + "')";
            queryString += " and p.project_id = " + projectId;

            Query query2 = entityManager.createNativeQuery(queryString);

            List result = query2.getResultList();

            return result != null && result.size() > 0;
        } catch (Exception e) {
            throw Helper.wrapWithDAOException(e,
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
     * @throws DAOConfigurationException
     *             if the configured entityManager is invalid (invalid means null here).
     */
    @SuppressWarnings("unchecked")
    public boolean checkPoNumberPermission(String username, String poNumber) throws DAOException {
        EntityManager entityManager = Helper
                .checkEntityManager(getEntityManager());

        try {

            String queryString = SELECT_PROJECT + " and p.active = 1 and p.project_id in " + "("
                    + SELECT_MANAGER_PROJECT + "'" + username + "' " + "union "
                    + SELECT_WORKER_PROJECT + "'" + username + "')";
            queryString += " and p.po_box_number = '" + poNumber + "'";

            Query query2 = entityManager.createNativeQuery(queryString);

            List result = query2.getResultList();

            return result != null && result.size() > 0;
        } catch (Exception e) {
            throw Helper.wrapWithDAOException(e,
                    "Failed in check PO number permission for [" + username + ", " + poNumber +"].");
        }
    }

    /**
     * <p>
     * Adds the user with given user name to the billing projects that are specified with project IDs.
     * </p>
     * <p>
     * Note that if the user doesn't exist, a new record will be created.
     * </p>
     *
     * @param userName
     *            the user name.
     * @param billingProjectIds
     *            the IDs of the projects to which the user is added
     * @throws IllegalArgumentException
     *             if userName is null/empty, or addUserToBillingProjects is null/empty, or any project ID in
     *             addUserToBillingProjects array is &lt;= 0.
     * @throws DAOConfigurationException
     *             if the configured entityManager is invalid (invalid means null here).
     * @throws DAOException
     *             if any error occurs while performing this operation.
     * @since 1.1
     */
    @SuppressWarnings("unchecked")
    public void addUserToBillingProjects(String userName, long[] billingProjectIds) throws DAOException {
        checkUserNameAndBillingProjectIds(userName, billingProjectIds);

        EntityManager entityManager = Helper.checkEntityManager(getEntityManager());

        Long userAccountId = getUserAccountId(entityManager, userName, true);
        String queryStr = "select project_id from project_manager "
                + "where project_id =:projectId and user_account_id=:userAccountId";
        Query searchQuery = entityManager.createNativeQuery(queryStr);
        try {
            // Insert a record to project_manager table for each specified project id
            Query projectManagerInsertQuery = entityManager.createNativeQuery(INSERT_PROJECT_MANAGER);

            for (long projectId : billingProjectIds) {
                // add checking, if user/project is already in the table, we DO NOT insert.
                searchQuery.setParameter("projectId", projectId);
                searchQuery.setParameter("userAccountId", userAccountId);
                List result = searchQuery.getResultList();
                if(result != null && result.size() > 0){
                    continue;
                }

                projectManagerInsertQuery.setParameter("projectId", projectId);
                projectManagerInsertQuery.setParameter("userAccountId", userAccountId);
                projectManagerInsertQuery.executeUpdate();
            }

        } catch (Exception e) {
            throw Helper.wrapWithDAOException(e, "Fail to insert records to project_manager table.");
        }
    }

    /**
     * <p>
     * Removes the user with given user name from the billing projects that are specified with project IDs.
     * </p>
     * <p>
     * Note that if the user doesn't exist, nothing will happen.
     * </p>
     *
     * @param userName
     *            the user name.
     * @param billingProjectIds
     *            the IDs of the projects from which the user is removed
     * @throws IllegalArgumentException
     *             if userName is null/empty, or addUserToBillingProjects is null/empty, or any project ID in
     *             addUserToBillingProjects array is &lt;= 0.
     * @throws DAOConfigurationException
     *             if the configured entityManager is invalid (invalid means null here).
     * @throws DAOException
     *             if any error occurs while performing this operation.
     * @since 1.1
     */
    public void removeUserFromBillingProjects(String userName, long[] billingProjectIds) throws DAOException {
        checkUserNameAndBillingProjectIds(userName, billingProjectIds);

        EntityManager entityManager = Helper.checkEntityManager(getEntityManager());

        Long userAccountId = getUserAccountId(entityManager, userName, false);

        if (userAccountId != null) {
            try {
            // Delete the records from project_manager table
            Query projectManagerDeleteQuery = entityManager.createNativeQuery(DELETE_PROJECT_MANAGER);
            for (long projectId : billingProjectIds) {
                projectManagerDeleteQuery.setParameter("projectId", projectId);
                projectManagerDeleteQuery.setParameter("userAccountId", userAccountId);
                projectManagerDeleteQuery.executeUpdate();
            }
            } catch (Exception e) {
                throw Helper.wrapWithDAOException(e, "Fail to remove user from billing projects.");
            }
        }
    }


    /**
     * <p>
     * Get all projects associated with the given client id.
     * </p>
     *
     * @param clientId
     *            the client ID.
     * @return a list of projects which are associated with the given client id, if no project is found empty list will
     *         be returned.
     * @throws IllegalArgumentException
     *             if clientId is &lt;= 0.
     * @throws DAOConfigurationException
     *             if the configured entityManager is invalid (invalid means null here).
     * @throws DAOException
     *             if any error occurs while performing this operation.
     * @since 1.1
     */
    public List<Project> getProjectsByClientId(long clientId) throws DAOException {
        if (clientId <= 0) {
            throw new IllegalArgumentException("The clientId should be positive.");
        }

        EntityManager entityManager = Helper.checkEntityManager(getEntityManager());
        try {
            String queryString = SELECT_PROJECT_BY_CLIENT_ID + clientId;

            Query query = entityManager.createNativeQuery(queryString);

            return convertQueryToListProjects(query);
        } catch (Exception e) {
            throw Helper.wrapWithDAOException(e, "Fail to get projects by client id.");
        }
    }

    /**
     * <p>
     * updates the project’s budget to current budget + updatedAmount.
     * </p>
     * <p>
     * The audit record is also inserted into project_budget_audit table.
     * </p>
     *
     * @param username
     *            the user name
     * @param billingProjectId
     *            the id for the project which is to be updated.
     * @param updatedAmount
     *            the delta amount of budget to add(or subtract if it's negative).
     * @return The budget after modified.
     * @throws DAOException
     *             if any error occurs while performing this operation.
     * @throws IllegalArgumentException
     *             if username is null or empty, or if updatedAmount makes the new budget negative(budget +
     *             updatedAmount &lt; 0)
     * @throws DAOConfigurationException
     *             if the configured entityManager is invalid (invalid means null here).
     * @throws EntityNotFoundException
     *             if project with given id is not found in the persistence.
     * @since 1.2
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public double updateProjectBudget(String username, long billingProjectId, double updatedAmount)
        throws DAOException {
        Helper.checkNullAndEmpty(username, "username");

        EntityManager entityManager = Helper.checkEntityManager(getEntityManager());
        try {

            // retrieve the current project budget
            Query selectQuery = entityManager.createNativeQuery(SELECT_PROJECT_BUDGET);
            selectQuery.setParameter("projectId", billingProjectId);
            Object result = selectQuery.getSingleResult();

            // null budget is deemed as zero.
            double currentBudget = 0.0;

            if (null != result) {
                currentBudget = Double.parseDouble(result.toString());
            }

            if (currentBudget + updatedAmount < 0) {
                throw new IllegalArgumentException("The new budget cann't be negative.");
            }

            // update the the project budget
            Query updateQuery = entityManager.createNativeQuery(UPDATE_PROJECT_BUDGET);
            updateQuery.setParameter("budget", updatedAmount);
            updateQuery.setParameter("projectId", billingProjectId);
            updateQuery.executeUpdate();

            // insert a project budget audit record.
            Query insertAuditQuery = entityManager.createNativeQuery(INSERT_PROJECT_BUDGET_AUDIT);
            insertAuditQuery.setParameter("projectId", billingProjectId);
            insertAuditQuery.setParameter("updatedAmount", updatedAmount);
            insertAuditQuery.setParameter("username", username);
            insertAuditQuery.executeUpdate();
            entityManager.flush();

            return currentBudget + updatedAmount;
        } catch (IllegalArgumentException e) {
            // re-throw it.
            throw e;
        } catch (NoResultException e) {
            throw new EntityNotFoundException("no project with the given id in the persistence.", e);
        } catch (Exception e) {
            throw Helper.wrapWithDAOException(e, "Fail to update project budget.");
        }
    }


    /**
     * <p>
     * Gets all users' name who have access of the billing project.
     * </p>
     *
     * @param billingProjectId
     *            the id for the project which is to be updated.
     * @return All the users's name who have access of the billing project. If none have access, empty list is returned
     *         but if project doesn't exist, EntityNotFoundException is thrown.
     * @throws DAOException
     *             if any error occurs while performing this operation.
     * @throws IllegalArgumentException
     *             if id &lt;= 0
     * @throws EntityNotFoundException
     *             if project with given id is not found in the persistence.
     * @throws DAOConfigurationException
     *             if the configured entityManager is invalid (invalid means null here).
     * @since 1.2
     */
    @SuppressWarnings("unchecked")
    public List<String> getUsersByProject(long billingProjectId) throws DAOException {
        // try to retrieve the project with the given id, making sure it is present
        retrieveById(billingProjectId);

        EntityManager entityManager = Helper.checkEntityManager(getEntityManager());
        try {
            Query query = entityManager.createNativeQuery(SELECT_USERS_BY_PROJECT);
            query.setParameter("projectId", billingProjectId);
            return query.getResultList();
        } catch (Exception e) {
            throw Helper.wrapWithDAOException(e, "Fail to get users by project.");
        }
    }


    /**
     * <p>
     * Checks the search name, make sure it does not contain not-allowed wildcard characters: *, %.
     * </p>
     *
     * @param name
     *            the search name.
     * @throws IllegalArgumentException
     *             if the project name contains wildcard character: *, %
     */
    private static void checkSearchName(String name) {
        // name can not contains wildcard characters % or *
        if (name != null && (name.contains("%") || name.contains("*"))) {
            throw new IllegalArgumentException("the name should not contain wildcard % or *");
        }
    }


    /**
     * <p>
     * Checks the <code>userName</code> and <code>billingProjectIds</code> arguments.
     * </p>
     *
     * @param userName
     *            the user name.
     * @param billingProjectIds
     *            the IDs of the projects to which the user is added
     * @throws IllegalArgumentException
     *             if userName is null/empty, or addUserToBillingProjects is null/empty, or any project ID in
     *             addUserToBillingProjects array is &lt;= 0.
     */
    private static void checkUserNameAndBillingProjectIds(String userName, long[] billingProjectIds) {
        Helper.checkNullAndEmpty(userName, "userName");
        Helper.checkNull(billingProjectIds, "billingProjectIds");
        if (billingProjectIds.length == 0) {
            throw new IllegalArgumentException("The 'billingProjectIds' array can not be empty.");
        }
        for (long projectId : billingProjectIds) {
            if (projectId <= 0) {
                throw new IllegalArgumentException("Any billing project id should be positive");
            }
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
     * @param isClientName whether the name is client's name
     * @return projects matched with the given name
     *
     * @throws IllegalArgumentException
     *             if the project name contains wildcard character: *, %
     * @throws DAOConfigurationException
     *             if the configured entityManager is invalid (invalid means null here).
     * @throws IllegalStateException
     *             if the entity manager is closed.
     * @since Configurable Contest Fees v1.0 Assembly
     */
    @SuppressWarnings("unchecked")
    private List<Project> internalSearchProjectsByName(String name,
            boolean isClientName) {
        EntityManager entityManager = Helper
                .checkEntityManager(getEntityManager());

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

            if (os[15] != null) {
                double budget = Double.parseDouble(os[15].toString());
                c.setBudget(budget);
            }

            result.add(c);

        }

        return result;
    }

    /**
     * <p>
     * Gets the user account id according to given user name.
     * </p>
     *
     * @param entityManager
     *            the entity manager for database operation.
     * @param userName
     *            the username to find the user account id.
     * @param isAdd
     *            whether to add a new user account if not found.
     * @return the matched user account id.
     * @throws DAOException
     *             if any error occurs.
     */
    private Long getUserAccountId(EntityManager entityManager, String userName, boolean isAdd) throws DAOException {
        try {
            // Query the user account id.
            Query userQuery = entityManager.createNativeQuery(SEARCH_USER_ACCOUNT_ID_BY_USRENAME);
            userQuery.setParameter("userName", userName);
            Long userAccountId = null;
            try {
                userAccountId = Long.parseLong(userQuery.getSingleResult().toString());
            } catch (NoResultException nre) {
                // No matching record in user_account table, should insert new records
            }

            if (null == userAccountId && isAdd) {
                // generate the user account id.
                IDGenerator idGen = IDGeneratorFactory.getIDGenerator(ID_KEY);

                userAccountId = idGen.getNextID();

                // and get the userAccountId
                Query insertUserQuery = entityManager.createNativeQuery(INSERT_DUMMY_USER_ACCOUNT);
                insertUserQuery.setParameter("userAccountId", userAccountId);
                insertUserQuery.setParameter("userName", userName);
                int counts = insertUserQuery.executeUpdate();
                if (counts != 1) {
                    // cannot insert into user_account table, throw DAOException
                    throw new DAOException("Cannot insert user data into user_account!");
                }
            }

            return userAccountId;
        } catch (Exception e) {
            throw Helper.wrapWithDAOException(e, "Fail to get the User account id.");
        }
    }

}
