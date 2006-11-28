/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.administration.persistence;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

/**
 * <p>The remote interface used to communicate with the {@link MessageBean MessageBean}.
 *
 * <p><strong>Thread Safety</strong>: The container assumes all responsibility for thread-safety of these objects.
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */

public interface MessageRemote extends EJBObject {
    /**
     * Retrieves messages in the data store matching the specified search criteria.
     *
     * @param criteria the criteria used to filter the results
     * @return a (possibly empty) array of messages matching the search criteria
     * @throws IllegalArgumentException if <code>criteria</code> is <code>null</code>
     * @throws PersistenceException if a data store error or any other checked exception prevents this method from
     *   completing successfully
     * @throws RemoteException if a network error occurs while communicating with the bean
     */
    public Message[] findMessages(SearchCriteriaDTO criteria) throws PersistenceException, RemoteException;

    /**
     * Stores a new message in the data store.
     *
     * @param message the message to store
     * @throws IllegalArgumentException if <code>message</code> is <code>null</code>
     * @throws DuplicateEntryException if a message with the same GUID already exists
     * @throws PersistenceException if a data store error or any other checked exception prevents this method from
     *   completing successfully
     * @throws RemoteException if a network error occurs while communicating with the bean
     */
    public void createMessage(Message message) throws PersistenceException, RemoteException;

    /**
     * Updates an existing message record in the data store.
     *
     * @param message the message to update
     * @throws IllegalArgumentException if <code>message</code> is <code>null</code>
     * @throws EntryNotFoundException if no message with a matching GUID exists
     * @throws PersistenceException if a data store error or any other checked exception prevents this method from
     *   completing successfully
     * @throws RemoteException if a network error occurs while communicating with the bean
     */
    public void updateMessage(Message message) throws PersistenceException, RemoteException;

    /**
     * Deletes a message from the data store.
     *
     * @param message the message to delete
     *
     * @throws IllegalArgumentException if <code>message</code> is <code>null</code>
     * @throws PersistenceException if a data store error or any other checked exception prevents this method from
     *   completing successfully
     * @throws RemoteException if a network error occurs while communicating with the bean
     */
    public void deleteMessage(Message message) throws PersistenceException, RemoteException;
}
