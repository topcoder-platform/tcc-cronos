/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.controlpanel.clientassociations.dao.hibernate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import com.topcoder.controlpanel.clientassociations.dao.ClientAssociationDAO;
import com.topcoder.controlpanel.clientassociations.dao.ClientAssociationDAOException;

/**
 * <p>
 * This class implements the <code>{@link ClientAssociationDAO}</code> interface and uses a database as storage media.
 * This implementation completely bases on the <b>Hibernate</b> framework and can work with most popular databases by
 * providing proper configuration. This class uses some classes generated from the given database schema to perform all
 * necessary database operations. This class itself doesn't use transaction directly, but the EJB container will handle
 * all the transaction logic. So all business methods will not involve any transaction handling logic.
 * </p>
 * <p>
 * <b>Thread Safety</b>: Strictly speaking this class is not thread safe since although it is immutable, the mutating
 * operations are not self transaction safe, but it's expected the session bean using this dao will use CMT
 * (Container-Managed Transaction), which means all transaction management will be done by the EJB container so when
 * used there will be no thread safety issues.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
public class ClientAssociationHibernateDAO implements ClientAssociationDAO {
    /**
     * <p>
     * Creates a <code>ClientAssociationHibernateDAO</code> instance.
     * </p>
     */
    public ClientAssociationHibernateDAO() {
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
     * @throws ClientAssociationDAOException
     *             if any error occurs when trying to assign the component.
     */
    public void assignComponent(long componentId, int clientId) throws ClientAssociationDAOException {
        // create new entity
        CompClient cc = new CompClient(new CompClientPK(componentId, clientId));

        Session session = null;
        // persist it by using Hibernate. no transaction involved.
        try {
            session = HibernateHelper.getSessionFactory().openSession();

            session.save(cc);

            session.flush();
        } catch (HibernateException e) {
            throw new ClientAssociationDAOException("Fail to assign the component [" + componentId + "] to client ["
                    + clientId + "].", e);
        } finally {
            closeSession(session);
        }
    }

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
    public void assignUser(long userId, int clientId, boolean isAdmin) throws ClientAssociationDAOException {
        // create new entity
        UserClient uc = new UserClient(new UserClientPK(userId, clientId));
        uc.setAdminInd(isAdmin ? 1 : 0);

        Session session = null;
        // persist it by using Hibernate. no transaction involved.
        try {
            session = HibernateHelper.getSessionFactory().openSession();

            session.save(uc);

            session.flush();
        } catch (HibernateException e) {
            throw new ClientAssociationDAOException("Fail to assign the user [" + userId + "] to client [" + clientId
                    + "].", e);
        } finally {
            closeSession(session);
        }
    }

    /**
     * <p>
     * Removes the component with the given component id from the client with the given client id.
     * </p>
     * <p>
     * If the association does not exist, nothing special occurs.
     * </p>
     *
     * @param componentId
     *            the id of the component to remove.
     * @param clientId
     *            the id of the client from which the component will be removed.
     * @throws ClientAssociationDAOException
     *             if any error occurs when trying to remove the component.
     */
    public void removeComponent(long componentId, int clientId) throws ClientAssociationDAOException {
        // delete the association between component and client
        // if the association does not exist, it simply return.
        Session session = null;
        try {
            session = HibernateHelper.getSessionFactory().openSession();
            session.delete(new CompClient(new CompClientPK(componentId, clientId)));
            session.flush();
        } catch (HibernateException e) {
            throw new ClientAssociationDAOException("Fail to remove component [" + componentId + "] from client ["
                    + clientId + "].", e);
        } finally {
            closeSession(session);
        }
    }

    /**
     * <p>
     * Removes the user with the given user id from the client with the given client id.
     * </p>
     * <p>
     * If the association does not exist, nothing special occurs.
     * </p>
     *
     * @param userId
     *            the id of the user to remove.
     * @param clientId
     *            the if of the client from which the user will be removed.
     * @throws ClientAssociationDAOException
     *             if any errors occur when trying to remove the user.
     */
    public void removeUser(long userId, int clientId) throws ClientAssociationDAOException {
        // delete the association between user and client
        // if the association does not exist, it simply return.
        Session session = null;
        try {
            session = HibernateHelper.getSessionFactory().openSession();
            session.delete(new UserClient(new UserClientPK(userId, clientId)));
            session.flush();
        } catch (HibernateException e) {
            throw new ClientAssociationDAOException("Fail to remove user [" + userId + "] from client [" + clientId
                    + "].", e);
        } finally {
            closeSession(session);
        }
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
     * @throws ClientAssociationDAOException
     *             if any error occurs when trying to get component ids.
     */
    @SuppressWarnings("unchecked")
    public List<Long> getComponentsByClient(int clientId) throws ClientAssociationDAOException {
        Session session = null;
        try {
            session = HibernateHelper.getSessionFactory().openSession();

            Query query = session.createQuery("from CompClient cc where cc.client.clientId=:id");
            query.setInteger("id", clientId);
            List<CompClient> res = query.list();

            // iterate and retrieve the component ids
            List<Long> componentIds = new ArrayList<Long>();
            for (Iterator<CompClient> iter = res.iterator(); iter.hasNext();) {
                componentIds.add(iter.next().getComp_id().getComponentId());
            }

            return componentIds;
        } catch (HibernateException e) {
            throw new ClientAssociationDAOException("Fail to get component ids with client id [" + clientId + "].", e);
        } finally {
            closeSession(session);
        }
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
     * @throws ClientAssociationDAOException
     *             if any error occurs when trying to get component ids.
     */
    @SuppressWarnings("unchecked")
    public List<Long> getComponentsByUser(long userId) throws ClientAssociationDAOException {
        Session session = null;
        try {
            session = HibernateHelper.getSessionFactory().openSession();
            Query query = session.createQuery("select cc from CompClient cc, UserClient uc"
                    + " where cc.client.clientId = uc.client.clientId and uc.comp_id.userId=:id");
            query.setLong("id", userId);
            List<CompClient> res = query.list();

            // iterate and retrieve the component ids
            List<Long> componentIds = new ArrayList<Long>();
            for (Iterator<CompClient> iter = res.iterator(); iter.hasNext();) {
                componentIds.add(iter.next().getComp_id().getComponentId());
            }

            return componentIds;
        } catch (HibernateException e) {
            throw new ClientAssociationDAOException("Fail to get component ids with user id [" + userId + "].", e);
        } finally {
            closeSession(session);
        }
    }

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
    @SuppressWarnings("unchecked")
    public List<Long> getUsers(int clientId) throws ClientAssociationDAOException {
        Session session = null;
        try {
            session = HibernateHelper.getSessionFactory().openSession();
            Query query = session.createQuery("from UserClient uc where uc.client.clientId=:id");
            query.setInteger("id", clientId);
            List<UserClient> res = query.list();

            // iterate and retrieve the user ids.
            List<Long> userIds = new ArrayList<Long>();
            for (Iterator<UserClient> iter = res.iterator(); iter.hasNext();) {
                userIds.add(iter.next().getComp_id().getUserId());
            }

            return userIds;
        } catch (HibernateException e) {
            throw new ClientAssociationDAOException("Fail to get user ids with client id [" + clientId + "].", e);
        } finally {
            closeSession(session);
        }
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
     * @throws ClientAssociationDAOException
     *             if the user is not assigned to the client or any other error occurs when trying to check this.
     */
    public boolean isAdminUser(long userId, int clientId) throws ClientAssociationDAOException {
        Session session = null;
        // query algorithm is replaced by simple Session.get(...) method.
        // As userId and clientId can uniquely identify a user-client association.
        try {
            session = HibernateHelper.getSessionFactory().openSession();

            // try to retrieve the persistent entity, if not found, null will return.
            UserClient userClient = (UserClient) session.get(UserClient.class, new UserClientPK(userId, clientId));

            if (null == userClient) {
                throw new ClientAssociationDAOException("The user [" + userId + "] is not assigned to the client ["
                        + clientId + "].");
            } else {
                return userClient.getAdminInd() == 1;
            }
        } catch (HibernateException e) {
            throw new ClientAssociationDAOException("Problem to check user admin.", e);
        } finally {
            closeSession(session);
        }
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
     * @throws ClientAssociationDAOException
     *             if any error occurs when trying to get client ids.
     */
    @SuppressWarnings("unchecked")
    public List<Integer> getClientsByComponent(long componentId) throws ClientAssociationDAOException {
        Session session = null;
        try {
            session = HibernateHelper.getSessionFactory().openSession();
            Query query = session.createQuery("from CompClient cc where cc.comp_id.componentId=:id");
            query.setLong("id", componentId);
            List<CompClient> res = query.list();

            // iterate and retrieve the client ids.
            List<Integer> clientIds = new ArrayList<Integer>();
            for (Iterator<CompClient> iter = res.iterator(); iter.hasNext();) {
                clientIds.add(iter.next().getClient().getClientId());
            }

            return clientIds;
        } catch (HibernateException e) {
            throw new ClientAssociationDAOException("Fail to get client ids the component [" + componentId
                    + "] assigned to.", e);
        } finally {
            closeSession(session);
        }
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
     * @throws ClientAssociationDAOException
     *             if any error occurs when trying to get client ids.
     */
    @SuppressWarnings("unchecked")
    public List<Integer> getClientsByUser(long userId) throws ClientAssociationDAOException {
        Session session = null;
        try {
            session = HibernateHelper.getSessionFactory().openSession();
            Query query = session.createQuery("from UserClient cc where cc.comp_id.userId=:id");
            query.setLong("id", userId);
            List<UserClient> res = query.list();

            // iterate and retrieve the client ids.
            List<Integer> clientIds = new ArrayList<Integer>();
            for (Iterator<UserClient> iter = res.iterator(); iter.hasNext();) {
                clientIds.add(iter.next().getClient().getClientId());
            }

            return clientIds;
        } catch (HibernateException e) {
            throw new ClientAssociationDAOException("Fail to get client ids by user [" + userId + "] assigned to.", e);
        } finally {
            closeSession(session);
        }
    }

    /**
     * <p>
     * Closes the session. This method should be used in finally block.
     * <p>
     *
     * @param session
     *            the hibernate session to close.
     */
    private static void closeSession(Session session) {
        try {
            if (session != null && session.isOpen()) {
                session.close();
            }
        } catch (HibernateException e) {
            // ignored here.
            // as the transaction is commit, throw exception to rollback is useless.
        }
    }
}
