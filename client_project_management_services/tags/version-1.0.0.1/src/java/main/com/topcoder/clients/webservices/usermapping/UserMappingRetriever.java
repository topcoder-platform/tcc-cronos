/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices.usermapping;

import java.util.List;

import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.Project;

/**
 * <p>
 *  This interface represents the UserMappingRetriever interface.
 *  This interface defines the specific methods available for the UserMappingRetriever
 *  interface: get valid users for client, get valid users for project,
 *  get clients for user id and get projects for user id.
 * </p>
 *
 * <p>
 *  <strong>Thread-safety:</strong>
 *  Implementations of this interface should be thread safe.
 * </p>
 *
 * @author  Mafy, CaDenza
 * @version 1.0
 */
public interface UserMappingRetriever {

    /**
     * Defines the operation that performs the retrieval of
     * the list with user identifiers that are mapped with
     * the given client from the persistence. If nothing is found, return an empty list.
     *
     * @param client
     *      the given client to retrieve it's mapped users. Should not be null.
     *
     * @return the list of user identifiers that are mapped
     * with the given client found in the persistence.
     * If nothing is found, return an empty list.
     *
     * @throws IllegalArgumentException
     *      if client is null.
     * @throws UserMappingRetrievalException
     *      if any error occurs while performing this operation.
     */
    public List<Long> getValidUsersForClient(Client client) throws UserMappingRetrievalException;

    /**
     * Defines the operation that performs the retrieval of
     * the list with user identifiers that are mapped with
     * the given project from the persistence.
     * If nothing is found, return an empty list.
     *
     * @param project
     *      the given project to retrieve it's mapped users. Should not be null.
     * @return the list of user identifiers that are mapped with
     *      the given project found in the persistence.
     *      If nothing is found, return an empty list.
     *
     * @throws IllegalArgumentException
     *      if project is null.
     * @throws UserMappingRetrievalException
     *      if any error occurs while performing this operation.
     */
    public List<Long> getValidUsersForProject(Project project) throws UserMappingRetrievalException;

    /**
     * Defines the operation that performs the retrieval of
     * the list with the clients that are mapped with
     * the user that has the given identifier from the persistence.
     * If nothing is found, return an empty list.
     *
     * @param userId
     *      the given user's id  to retrieve it's mapped clients. Should not be negative.
     * @return the list of clients that are mapped with
     *      the user that has the given identifier found in the persistence.
     *      If nothing is found, return an empty list.
     *
     * @throws IllegalArgumentException
     *      if userId &lt;=0.
     * @throws UserMappingRetrievalException if any error occurs while performing this operation.
     */
    public List<Client> getClientsForUserId(long userId) throws UserMappingRetrievalException;

    /**
     * Defines the operation that performs the retrieval of
     * the list with the projects that are mapped with
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
     * @throws UserMappingRetrievalException
     *      if any error occurs while performing this operation.
     */
    public List<Project> getProjectsForUserId(long userId) throws UserMappingRetrievalException;

}

