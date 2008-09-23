/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.controlpanel.clientassociations;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.naming.NamingException;

import com.topcoder.controlpanel.clientassociations.dao.ClientAssociationDAO;
import com.topcoder.controlpanel.clientassociations.dao.hibernate.ClientAssociationHibernateDAO;
import com.topcoder.naming.jndiutility.JNDIUtils;

/**
 * <p>
 * This class is an implementation of the remote and local interfaces and is implemented as a stateless session bean. It
 * uses a <code>{@link ClientAssociationDAO}</code> instance to perform all necessary persistence, basically this
 * class simply delegates all tasks to the DAO instance.
 * </p>
 * <p>
 * <b>Usage examples</b><br>
 * How to retrieve the session bean:<br>
 *
 * <pre>
 * // create a new context
 * InitialContext ctx = new InitialContext();
 * // look up the session bean
 * cas = (ClientAssociationServiceRemote) ctx.lookup(&quot;ClientAssociationServiceStatelessSessionBean&quot;);
 * </pre>
 *
 * Business method usage:<br>
 *
 * <pre>
 * // assign a component
 * cas.assignComponent(4, 1);
 *
 * // calling the above method twice would cause exception though since the record
 * // already exists, this also applies to the assignUser method
 *
 * // assign a user
 * cas.assignUser(2l, 1, true);
 *
 * // however if you do this, you'll get an exception since the client doesn't exist, this also applies to the
 * // other mutating methods
 * try {
 *     cas.assignUser(2, 4, false);
 * } catch (ClientAssociationServiceException e) {
 *     // exception should be handled here.
 * }
 *
 * // get components by client
 * List&lt;Long&gt; compIds = cas.getComponentsByClient(1);
 *
 * // or we could get components by user (get components from all clients to which
 * // the user is assigned)
 * compIds = cas.getComponentsByUser(2);
 *
 * // check if a user is an admin user
 * boolean isAdmin = cas.isAdminUser(1, 2);
 *
 * // however this causes exception since there is no association between user 3 and
 * // client 1
 * try {
 *     isAdmin = cas.isAdminUser(3, 1);
 * } catch (ClientAssociationServiceException e) {
 *     // exception should be handled.
 * }
 *
 * // remove a component
 * cas.removeComponent(1, 1);
 *
 * // usage of the other operations are just similar so no duplication is provided
 * // here
 * ...
 * </pre>
 *
 * </p>
 * <p>
 * <b>Thread Safety</b>:This class is thread safe since it's immutable and all operations are transaction safe.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ClientAssociationServiceStatelessSessionBean implements ClientAssociationServiceRemote,
        ClientAssociationServiceLocal {
    /**
     * <p>
     * Represents the <code>ClientAssociationDAO</code> instance to which this stateless session bean will delegate
     * all tasks.
     * </p>
     * <p>
     * This instance is initialized in the initialize() method by either retrieving from JNDI tree or setting to a
     * ClientAssociationHibernateDAO instance. This field can only be accessed by the methods defined inside this
     * session bean. Once initialized it will not be modified until this session bean is destroyed. It will not be null
     * in the lifetime of the session bean, and if not found in the JNDI tree the default hibernate dao implementation
     * will be used.
     * </p>
     */
    private ClientAssociationDAO dao;

    /**
     * An empty do-nothing constructor.
     */
    public ClientAssociationServiceStatelessSessionBean() {
    }

    /**
     * <p>
     * Performs initialization after constructor is called and before any business methods are called.
     * </p>
     */
    @PostConstruct
    protected void initialize() {
        try {
            dao = (ClientAssociationDAO) JNDIUtils.getObject("dao/clientAssociationDAO");
        } catch (NamingException e) {
            // ignore the exception
        } catch (ClassCastException e) {
            // ignore the exception
        }

        if (null == dao) {
            dao = new ClientAssociationHibernateDAO();
        }
    }

    /**
     * <p>
     * Performs cleanup before this stateless session bean is destroyed.
     * </p>
     */
    @PreDestroy
    protected void cleanUp() {
        dao = null;
    }

    /**
     * <p>
     * Assigns the component with the given component id to the client with the given client id.
     * </p>
     *
     * @param componentId
     *            the id of the component to assign.
     * @param clientId
     *            the id of the client to which the component will be assigned.
     * @throws ClientAssociationServiceException
     *             if any error occurs when trying to assign the component.
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void assignComponent(long componentId, int clientId) throws ClientAssociationServiceException {
        dao.assignComponent(componentId, clientId);
    }

    /**
     * Assigns the user with the given user id to the client with the given client id. The user to assign could be an
     * admin user or a non-admin user.
     *
     * @param userId
     *            the id of the user to assign to the client.
     * @param clientId
     *            the id of the client to which the user will be assigned.
     * @param isAdmin
     *            true if the user is an admin user, false otherwise.
     * @throws ClientAssociationServiceException
     *             if any error occurs when trying to assign the user.
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void assignUser(long userId, int clientId, boolean isAdmin) throws ClientAssociationServiceException {
        dao.assignUser(userId, clientId, isAdmin);
    }

    /**
     * <p>
     * Removes the component with the given component id from the client with the given client id.
     * </p>
     *
     * @param componentId
     *            the id of the component to remove.
     * @param clientId
     *            the id of the client from which the component will be removed.
     * @throws ClientAssociationServiceException
     *             if any error occurs when trying to remove the component.
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void removeComponent(long componentId, int clientId) throws ClientAssociationServiceException {
        dao.removeComponent(componentId, clientId);
    }

    /**
     * <p>
     * Removes the user with the given user id from the client with the given client id.
     * </p>
     *
     * @param userId
     *            the id of the user to remove.
     * @param clientId
     *            the id of the client from which the user will be removed.
     * @throws ClientAssociationServiceException
     *             if any errors occur when trying to remove the user.
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void removeUser(long userId, int clientId) throws ClientAssociationServiceException {
        dao.removeUser(userId, clientId);
    }

    /**
     * <p>
     * Gets ids of all components assigned to the client with the given client id. This might return an empty list if no
     * component is assigned to the client.
     * </p>
     *
     * @param clientId
     *            the id of the client from which all assigned component ids will be retrieved.
     * @return ids of all components assigned to the client with the given id. Might be an empty list.
     * @throws ClientAssociationServiceException
     *             if any error occurs when trying to get component ids.
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Long> getComponentsByClient(int clientId) throws ClientAssociationServiceException {
        return dao.getComponentsByClient(clientId);
    }

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
     * @throws ClientAssociationServiceException
     *             if any error occurs when trying to get component ids.
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Long> getComponentsByUser(long userId) throws ClientAssociationServiceException {
        return dao.getComponentsByUser(userId);
    }

    /**
     * <p>
     * Gets ids of all users assigned to the client with the given client id. This might return an empty list if no user
     * is assigned to the given client. The "" annotation must be applied to this method.
     * </p>
     *
     * @param clientId
     *            id of the client from which ids of all assigned users will be retrieved.
     * @return ids of all users assigned to the given client.
     * @throws ClientAssociationServiceException
     *             if any error occurs when trying to get user ids.
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Long> getUsers(int clientId) throws ClientAssociationServiceException {
        return dao.getUsers(clientId);
    }

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
     * @throws ClientAssociationServiceException
     *             if the user is not assigned to the client or any other error occurs when trying to check this.
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public boolean isAdminUser(long userId, int clientId) throws ClientAssociationServiceException {
        return dao.isAdminUser(userId, clientId);
    }

    /**
     * <p>
     * Gets ids of all clients to which the component with the given id is assigned. This might return an empty list if
     * no client is assigned with the given component.
     * </p>
     *
     * @param componentId
     *            the id of the component used to get all desired client ids.
     * @return ids of all clients to which the component with the given id is assigned. Might be an empty list.
     * @throws ClientAssociationServiceException
     *             if any error occurs when trying to get client ids.
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Integer> getClientsByComponent(long componentId) throws ClientAssociationServiceException {
        return dao.getClientsByComponent(componentId);
    }

    /**
     * <p>
     * Gets ids of all clients to which the user with the given id is assigned. This might return an empty list if no
     * client is assigned with the given user.
     * </p>
     *
     * @param userId
     *            the id of the user used to get all desired client ids.
     * @return ids of all clients to which the user with the given id is assigned. Might be an empty list.
     * @throws ClientAssociationServiceException
     *             if any error occurs when trying to get client ids.
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Integer> getClientsByUser(long userId) throws ClientAssociationServiceException {
        return dao.getClientsByUser(userId);
    }
}
