/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices.usermapping.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.hibernate.HibernateException;

import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.Project;
import com.topcoder.clients.webservices.usermapping.UserMappingRetrievalException;
import com.topcoder.clients.webservices.usermapping.UserMappingRetriever;
import com.topcoder.util.errorhandling.ExceptionUtils;

/**
 * <p>
 *  This class is a JPA realization of the UserMappingRetriever interface.
 *  This class has a default no-arg constructor and a constructor that receive
 *  a entityManager to initialize the namesake field.
 * </p>
 *
 * <p>
 *  This class implements the methods available for the UserMappingRetriever interface:
 *  get valid users for client, get valid users for project, get clients for user id
 *  and get projects for user id.
 * </p>
 *
 * <p>
 *  It uses the configured EntityManager to perform the needed operations,
 *  retrieve the EntityManager using it's corresponding getter.
 * </p>
 *
 * <p>
 * THREAD SAFETY:
 *      This class is technically mutable since the configuration property
 *      (EntityManager) could be set after construction,
 *      but the container will not initialize the properties more than once and
 *      the EJB3 container ensure the thread safety in this case.
 * </p>
 *
 * @author  Mafy, CaDenza
 * @version 1.0
 */
public class JPAUserMappingRetriever implements UserMappingRetriever {

    /**
     * Query to retrieve user-client from selected client ID.
     */
    private static final String SQL_RETRIEVE_VALID_USER_CLIENT =
        "select u from " + UserClientMapping.class.getName() + " u where u.clientId = :clientId";

    /**
     * Query to retrieve user-project from selected project ID.
     */
    private static final String SQL_RETRIEVE_VALID_USER_PROJECT =
        "select u from " + UserProjectMapping.class.getName() + " u where u.projectId = :projectId";

    /**
     * Query to retrieve client user mapping by selected user ID.
     */
    private static final String SQL_RETRIEVE_CLIENT_USER_MAPPING_BY_USER_ID =
        "select u from " + UserClientMapping.class.getName() + " u where u.userId = :userId";

    /**
     * Query to retrieve Client by its ID that has status is not deleted.
     */
    private static final String SQL_RETRIEVE_NON_DELETED_CLIENT_BY_ID =
        "select c from " + Client.class.getName() + " c where c.id in (:clientId) and c.deleted = false";

    /**
     * Query to retrieve project user mapping by selected user ID.
     */
    private static final String SQL_RETRIEVE_PROJECT_USER_MAPPING_BY_USER_ID =
        "select u from " + UserProjectMapping.class.getName() + " u where u.userId = :userId";

    /**
     * Query to retrieve Project by its ID that has status is not deleted.
     */
    private static final String SQL_RETRIEVE_NON_DELETED_PROJECT_BY_ID =
        "select c from " + Project.class.getName() + " c where c.id in (:projectId) and c.deleted = false";

    /**
     * Represents the EntityManager injected by the EJB container automatically
     * or is set in appropriate constructor or setter method.
     *
     * Cannot be null after injected or set.
     * Container or client is responsible to provide a valid value.
     */
    @PersistenceContext(unitName = "persistenceUnit", type = PersistenceContextType.TRANSACTION)
    private EntityManager entityManager;

    /**
     * Default no-arg constructor.
     */
    public JPAUserMappingRetriever() {
        // do nothing.
    }

    /**
     *  Constructor. Constructs a new 'JPAUserMappingRetriever' instance.
     *  Initialize the entityManager with the given argument.
     *
     *  @param entityManager
     *          the new entityManager to be used for 'entityManager' property.
     *          It can be any value.
     */
    public JPAUserMappingRetriever(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Setter used by container injection mechanism for 'entityManager' property.
     *
     * @param entityManager
     *      the new entityManager to be used for 'entityManager' property.
     *      It can be any value.
     */
    @PersistenceContext(unitName = "persistenceUnit", type = PersistenceContextType.TRANSACTION)
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Getter for 'entityManager' property.
     *
     * @return the value of the 'entityManager' property.
     *          It can be any value.
     */
    protected EntityManager getEntityManager() {
        return entityManager;
    }

    /**
     * <p>
     *  Performs the retrieval of the list with user identifiers
     *  that are mapped with the given client from the persistence.
     *  If nothing is found, return an empty list.
     * </p>
     *
     * @param client
     *      the given client to retrieve it's mapped users.
     *      Should not be null.
     * @return the list of user identifiers that are mapped with the given
     *      client found in the persistence.
     *      If nothing is found, return an empty list.
     *
     * @throws IllegalArgumentException
     *      if client is null.
     * @throws IllegalStateException
     *      if the entityManager is not set (or it is set to a null value).
     * @throws UserMappingRetrievalException
     *      if any error occurs while performing this operation.
     */
    @SuppressWarnings("unchecked")
    public List<Long> getValidUsersForClient(Client client) throws UserMappingRetrievalException {
        checkState();
        ExceptionUtils.checkNull(client, null, null, "Argument [client] cannot be null.");

        List<UserClientMapping> userClientMappings =
            retrieveInstanceFromDB(SQL_RETRIEVE_VALID_USER_CLIENT,
                    new String[]{"clientId"}, new Object[]{client.getId()});

        // Reduce redundant userId.
        Set<Long> userIds = new HashSet<Long>();
        for (UserClientMapping elmt : userClientMappings) {
            userIds.add(elmt.getUserId());
        }
        return Arrays.asList(userIds.toArray(new Long[userIds.size()]));
    }

    /**
     * <p>
     *  Performs the retrieval of the list with user identifiers that
     *  are mapped with the given project from the persistence.
     *  If nothing is found, return an empty list.
     * </p>
     *
     * @param project
     *      the given project to retrieve it's mapped users.
     *      Should not be null.
     * @return the list of user identifiers that are mapped with
     *      the given project found in the persistence.
     *      If nothing is found, return an empty list.
     *
     * @throws IllegalArgumentException
     *      if project is null.
     * @throws IllegalStateException
     *      if the entityManager is not set (or it is set to a null value).
     * @throws UserMappingRetrievalException
     *      if any error occurs while performing this operation.
     */
    @SuppressWarnings("unchecked")
    public List<Long> getValidUsersForProject(Project project) throws UserMappingRetrievalException {
        checkState();
        ExceptionUtils.checkNull(project, null, null, "Argument [project] cannot be null.");

        List<UserProjectMapping> userProjectMappings =
            retrieveInstanceFromDB(SQL_RETRIEVE_VALID_USER_PROJECT,
                    new String[]{"projectId"}, new Object[]{project.getId()});

        // Reduce redundant user ID.
        Set<Long> userIds = new HashSet<Long>();
        for (UserProjectMapping elmt : userProjectMappings) {
            userIds.add(elmt.getUserId());
        }
        return Arrays.asList(userIds.toArray(new Long[userIds.size()]));
    }

    /**
     * Performs the retrieval of the list with the clients that are mapped with
     * the user that has the given identifier from the persistence.
     * If nothing is found, return an empty list.
     *
     * @param userId
     *      the given user's id  to retrieve it's mapped clients.
     *      Should not be negative.
     * @return the list of clients that are mapped with
     *      the user that has the given identifier found in the persistence.
     *      If nothing is found, return an empty list.
     *
     * @throws IllegalArgumentException
     *      if userId &lt;=0.
     * @throws IllegalStateException
     *      if the entityManager is not set (or it is set to a null value).
     * @throws UserMappingRetrievalException
     *      if any error occurs while performing this operation.
     */
    @SuppressWarnings("unchecked")
    public List<Client> getClientsForUserId(long userId) throws UserMappingRetrievalException {
        checkState();
        checkNumberRange("User-ID", userId);

        List<UserClientMapping> userClientMappings =
            retrieveInstanceFromDB(SQL_RETRIEVE_CLIENT_USER_MAPPING_BY_USER_ID,
                    new String[]{"userId"}, new Object[]{userId});

        boolean first = true;
        StringBuilder ids = new StringBuilder();
        for (UserClientMapping elmt : userClientMappings) {
            if (elmt != null) {
                if (first) {
                    first = false;
                } else {
                    ids.append(",");
                }
                ids.append(elmt.getClientId());
            }
        }

        // Retrieve Client instance by its ID.
        List<Client> validClients = new ArrayList<Client>();
        if (ids.length() > 0) {
            String sql = SQL_RETRIEVE_NON_DELETED_CLIENT_BY_ID.replace(":clientId", ids.toString());
            validClients = retrieveInstanceFromDB(sql, null, null);
        }
        return validClients;
    }

    /**
     * Performs the retrieval of the list with the projects that are mapped with
     * the user that has the given identifier from the persistence.
     * If nothing is found, return an empty list.
     *
     * @param userId
     *      the given user's id  to retrieve it's mapped projects.
     *      Should not be negative.
     * @return the list of projects that are mapped with
     *      the user that has the given identifier found in the persistence.
     *      If nothing is found, return an empty list.
     *
     * @throws IllegalArgumentException
     *      if userId &lt;= 0.
     * @throws IllegalStateException
     *      if the entityManager is not set (or it is set to a null value).
     * @throws UserMappingRetrievalException
     *      if any error occurs while performing this operation.
     */
    @SuppressWarnings("unchecked")
    public List<Project> getProjectsForUserId(long userId) throws UserMappingRetrievalException {
        checkState();
        checkNumberRange("User-ID", userId);

        List<UserProjectMapping> userProjectMappings =
            retrieveInstanceFromDB(SQL_RETRIEVE_PROJECT_USER_MAPPING_BY_USER_ID,
                    new String[]{"userId"}, new Object[]{userId});

        boolean first = true;
        StringBuilder ids = new StringBuilder();
        for (UserProjectMapping elmt : userProjectMappings) {
            if (elmt != null) {
                if (first) {
                    first = false;
                } else {
                    ids.append(",");
                }
                ids.append(elmt.getProjectId());
            }
        }

        List<Project> validProjects = new ArrayList<Project>();
        if (ids.length() > 0) {
            String sql = SQL_RETRIEVE_NON_DELETED_PROJECT_BY_ID.replace(":projectId", ids.toString());
            validProjects = retrieveInstanceFromDB(sql, null, null);
        }
        return validProjects;
    }

    /**
     * Retrieve entity instance from persistence layer.
     *
     * @param queryString
     *      The query to retrieve instance.
     * @param params
     *      The params name.
     * @param values
     *      The value of param.
     *
     * @return collection of instance. Can be empty.
     *
     * @throws UserMappingRetrievalException
     *      if any failure occurs.
     */
    @SuppressWarnings("unchecked")
    private List retrieveInstanceFromDB(String queryString, String[] params, Object[] values)
        throws UserMappingRetrievalException {
        try {
            Query query = entityManager.createQuery(queryString);
            if (params != null) {
                for (int i = 0; i < params.length; i++) {
                    query.setParameter(params[i], values[i]);
                }
            }
            return query.getResultList();
        } catch (IllegalArgumentException e) {
            throw new UserMappingRetrievalException("Found invalid argument.", e);
        } catch (IllegalStateException e) {
            throw new UserMappingRetrievalException("Required property has invalid value.", e);
        } catch (HibernateException e) {
            throw new UserMappingRetrievalException("Any failure occurs in hibernate.", e);
        } catch (PersistenceException e) {
            throw new UserMappingRetrievalException("Any unexpected failure occurs in persistence.", e);
        }
    }

    /**
     * Check state of EntityManager.
     *
     * @throws IllegalStateException
     *      if EntityManager is null.
     */
    private void checkState() {
        if (entityManager == null) {
            throw new IllegalStateException("EntityManager cannot be null. Initialize it first !!!");
        }
    }

    /**
     * Checking whether given number &lt;= 0.
     *
     * @param propName
     *      The name of property to be checked.
     * @param value
     *      The value to be checked.
     *
     * @throws IllegalArgumentException
     *      if value is less than or equal to '0'.
     */
    private void checkNumberRange(String propName, long value) {
        if (value <= 0) {
            throw new IllegalArgumentException(propName + " cannot has value less than or equal to 0.");
        }
    }
}

