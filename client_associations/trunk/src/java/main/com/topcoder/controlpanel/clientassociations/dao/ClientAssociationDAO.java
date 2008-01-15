/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.controlpanel.clientassociations.dao;

import java.util.List;

/**
 * <p>
 * This interface defines the persistence contract for the association operations. It enables the creation, deletion and
 * query of those associations. Implementations of this interface may or may not handle transactions directly depending
 * on whether the session bean using it uses CMT or BMT.
 * </p>
 * <p>
 * <b>Thread Safety</b>: Implementations of this interface may not be thread safe if intended to be used with session
 * bean using CMT(since they don't handle transactions themselves), but they are used in a thread safe way. If the
 * implementations are not intended to be used with session bean using CMT, then the implementations must be thread
 * safe.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
public interface ClientAssociationDAO {
    /**
     * <p>
     * Assigns the component with the given component id to the client with the given client id.
     * </p>
     *
     * @param componentId
     *            the id of the component to assign.
     * @param clientId
     *            the id of the client to which the component will be assigned.
     * @throws ClientAssociationDAOException
     *             if any error occurs when trying to assign the component.
     */
    public void assignComponent(long componentId, int clientId) throws ClientAssociationDAOException;

    /**
     * <p>
     * Assigns the user with the given user id to the client with the given client id. The user to assign could be an
     * admin user or a non-admin user.
     * </p>
     *
     * @param userId
     *            the id of the user to assign to the client.
     * @param clientId
     *            the id of the client to which the user will be assigned.
     * @param isAdmin
     *            true if the user is an admin user, false otherwise.
     * @throws ClientAssociationDAOException
     *             if any error occurs when trying to assign the user.
     */
    public void assignUser(long userId, int clientId, boolean isAdmin) throws ClientAssociationDAOException;

    /**
     * <p>
     * Removes the component with the given component id from the client with the given client id.
     * </p>
     *
     * @param componentId
     *            the id of the component to remove.
     * @param clientId
     *            the id of the client from which the component will be removed.
     * @throws ClientAssociationDAOException
     *             if any error occurs when trying to remove the component.
     */
    public void removeComponent(long componentId, int clientId) throws ClientAssociationDAOException;

    /**
     * <p>
     * Removes the user with the given user id from the client with the given client id.
     * </p>
     *
     * @param userId
     *            the id of the user to remove.
     * @param clientId
     *            the if of the client from which the user will be removed.
     * @throws ClientAssociationDAOException
     *             if any errors occur when trying to remove the user.
     */
    public void removeUser(long userId, int clientId) throws ClientAssociationDAOException;

    /**
     * <p>
     * Gets ids of all components assigned to the client with the given client id. This might return an empty list if no
     * component is assigned to the client.
     * </p>
     *
     * @param clientId
     *            the id of the client from which all assigned component ids will be retrieved.
     * @return ids of all components assigned to the client with the given id. Might be an empty list.
     * @throws ClientAssociationDAOException
     *             if any error occurs when trying to get component ids.
     */
    public List<Long> getComponentsByClient(int clientId) throws ClientAssociationDAOException;

    /**
     * <p>
     * Gets ids of all components assigned to the clients to which the user with the given user id is assigned. This
     * might return an empty list if no component is assigned to any of the clients.
     * </p>
     *
     * @param userId
     *            the id of the user through which to get the clients to which all desired component ids are assigned
     *            to.
     * @return ids of all components that's assigned to the clients to which the user with the given user id is assigned
     *         to. Might be an empty list.
     * @throws ClientAssociationDAOException
     *             if any error occurs when trying to get component ids.
     */
    public List<Long> getComponentsByUser(long userId) throws ClientAssociationDAOException;

    /**
     * <p>
     * Gets ids of all users assigned to the client with the given client id. This might return an empty list if no user
     * is assigned to the given client.
     * </p>
     *
     * @param clientId
     *            id of the client from which ids of all assigned users will be retrieved.
     * @return ids of all users assigned to the given client.
     * @throws ClientAssociationDAOException
     *             if any error occurs when trying to get user ids.
     */
    public List<Long> getUsers(int clientId) throws ClientAssociationDAOException;

    /**
     * <p>
     * Checks if the user with the given id is an admin user of the client with the given client id.
     * </p>
     *
     * @param userId
     *            the id of the user that will be checked whether is an admin user or not.
     * @param clientId
     *            the id of the client that we will check if the given user is an admin user of it or not.
     * @return true if the user with the given user id is an admin user of the client, false otherwise.
     * @throws ClientAssociationDAOException
     *             if if the user is not assigned to the client or any other error occurs when trying to check this.
     */
    public boolean isAdminUser(long userId, int clientId) throws ClientAssociationDAOException;

    /**
     * <p>
     * Gets ids of all clients to which the component with the given id is assigned. This might return an empty list if
     * no client is assigned with the given component.
     * </p>
     *
     * @param componentId
     *            the id of the component used to get all desired client ids.
     * @return ids of all clients to which the component with the given id is assigned. Might be an empty list.
     * @throws ClientAssociationDAOException
     *             if any error occurs when trying to get client ids.
     */
    public List<Integer> getClientsByComponent(long componentId) throws ClientAssociationDAOException;

    /**
     * <p>
     * Gets ids of all clients to which the user with the given id is assigned. This might return an empty list if no
     * client is assigned with the given user.
     * </p>
     *
     * @param userId
     *            the id of the user used to get all desired client ids.
     * @return ids of all clients to which the user with the given id is assigned. Might be an empty list.
     * @throws ClientAssociationDAOException
     *             if any error occurs when trying to get client ids.
     */
    public List<Integer> getClientsByUser(long userId) throws ClientAssociationDAOException;

}
