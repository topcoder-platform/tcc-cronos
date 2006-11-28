/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.administration.persistence;

import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * The EJB that handles message-related client requests by delegating to the {@link MessageDAO MessageDAO} obtained
 * from the {@link DAOFactory DAOFactory}.
 *
 * <p><strong>Thread Safety</strong>: This class is immutable and therefore thread safe.
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */

public class MessageBean implements SessionBean {
    /**
     * <p>Represents the session context of this bean. It is used to indicate to the container if the bean wants to
     * rollback a transaction. This would usually occur if an application exception occurs. Set in the
     * {@link #setSessionContext setSessionContext()} method by the container.</p>
     */
    private SessionContext sessionContext;

    /**
     * <p>Empty default constructor.</p>
     */
    public MessageBean() {
    }

    /**
     * Retrieves the messages in the data store matching the specified criteria.
     *
     * @param criteria the criteria to use to filter the messages
     * @return a (possibly empty) array containing the messages that match the criteria
     * @throws IllegalArgumentException if <code>criteria</code> is <code>null</code>
     * @throws PersistenceException if a reference to the message DAO cannot be obtained from the factory
     * @throws PersistenceException if an error occurs while accessing the data store or any other checked
     *   exception prevents this method from completing
     */
    public Message[] findMessages(SearchCriteriaDTO criteria) throws PersistenceException {
        try {
            return getMessageDAO().findMessages(criteria);
        } catch (PersistenceException ex) {
            sessionContext.setRollbackOnly();
            throw ex;
        }
    }

    /**
     * Records a new message in the data store.
     *
     * @param message the message to enter into the data store
     * @throws IllegalArgumentException if <code>message</code> is <code>null</code>
     * @throws DuplicateEntryException if a message with the same GUID already exists
     * @throws PersistenceException if a reference to the message DAO cannot be obtained from the factory
     * @throws PersistenceException if an error occurs while accessing the data store or any other checked
     *   exception prevents this method from completing
     */
    public void createMessage(Message message) throws PersistenceException {
        try {
            getMessageDAO().createMessage(message);
        } catch (PersistenceException ex) {
            //sessionContext.setRollbackOnly();
            throw ex;
        }
    }

    /**
     * Updates the specified message in the data store.
     *
     * @param message the message to update
     * @throws IllegalArgumentException if <code>message</code> is <code>null</code>
     * @throws EntryNotFoundException if no message with the specified GUID exists in the data store
     * @throws PersistenceException if a reference to the message DAO cannot be obtained from the factory
     * @throws PersistenceException if an error occurs while accessing the data store or any other checked
     *   exception prevents this method from completing
     */
    public void updateMessage(Message message) throws PersistenceException {
        try {
            getMessageDAO().updateMessage(message);
        } catch (PersistenceException ex) {
            sessionContext.setRollbackOnly();
            throw ex;
        }
    }

    /**
     * Deletes the specified message from the data store.
     *
     * @param message the message to delete
     * @throws IllegalArgumentException if <code>message</code> is <code>null</code>
     * @throws PersistenceException if an error occurs while accessing the data store or any other checked
     *   exception prevents this method from completing
     */
    public void deleteMessage(Message message) throws PersistenceException {
        try {
            getMessageDAO().deleteMessage(message);
        } catch (PersistenceException ex) {
            sessionContext.setRollbackOnly();
            throw ex;
        }
    }

    /**
     * <p>Creates the bean. This is an empty implementation.</p>
     */
    public void ejbCreate() {
    }

    /**
     * <p>Removes the bean. This is an empty implementation.</p>
     */
    public void ejbRemove() {
    }

    /**
     * <p>Activates the bean. This is an empty implementation.</p>
     */
    public void ejbActivate() {
    }

    /**
     * <p>Passivates the bean. This is an empty implementation.</p>
     */
    public void ejbPassivate() {
    }

    /**
     * <p>Sets the session context.</p>
     *
     * @param ctx the session context
     */
    public void setSessionContext(SessionContext ctx) {
        this.sessionContext = ctx;
    }

    /**
     * Helper method to call <code>getMessageDAO</code> and rethrow <code>InstantiationException</code> as
     * <code>PersistenceException</code>.
     *
     * @return the message DAO instance
     * @throws PersistenceException if the message DAO cannot be instantiated
     */
    private static MessageDAO getMessageDAO() throws PersistenceException {
        try {
            return DAOFactory.getMessageDAO();
        } catch (InstantiationException ex) {
            throw new PersistenceException("failed to instantiate admin data DAO", ex);
        }
    }
}
