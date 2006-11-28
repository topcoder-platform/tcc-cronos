/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.administration.persistence;

/**
 * <p>Interface to the data access object (<i>DAO</i>) that is used by the {@link MessageBean MessageBean} to
 * perform the actual persistence operations.
 *
 * <p><strong>Thead Safety</strong>: Implementations are required to be thread safe.
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */

public interface MessageDAO {
    /**
     * Retrieves the messages in the data store matching the specified criteria.
     *
     * @param criteria the criteria to use to filter the messages
     * @return a (possibly empty) array containing the messages that match the criteria
     * @throws IllegalArgumentException if <code>criteria</code> is <code>null</code>
     * @throws PersistenceException if an error occurs while accessing the data store or any other checked
     *   exception prevents this method from completing
     */
    public Message[] findMessages(SearchCriteriaDTO criteria) throws PersistenceException;

    /**
     * Records a new message in the data store.
     *
     * @param message the message to enter into the data store
     * @throws IllegalArgumentException if <code>message</code> is <code>null</code>
     * @throws DuplicateEntryException if a message with the same GUID already exists
     * @throws PersistenceException if an error occurs while accessing the data store or any other checked
     *   exception prevents this method from completing
     */
    public void createMessage(Message message) throws PersistenceException;

    /**
     * Updates the specified message in the data store.
     *
     * @param message the message to update
     * @throws IllegalArgumentException if <code>message</code> is <code>null</code>
     * @throws EntryNotFoundException if no message with the specified GUID exists in the data store
     * @throws PersistenceException if an error occurs while accessing the data store or any other checked
     *   exception prevents this method from completing
     */
    public void updateMessage(Message message) throws PersistenceException;

    /**
     * Deletes the specified message from the data store.
     *
     * @param message the message to delete
     * @throws IllegalArgumentException if <code>message</code> is <code>null</code>
     * @throws PersistenceException if an error occurs while accessing the data store or any other checked
     *   exception prevents this method from completing
     */
    public void deleteMessage(Message message) throws PersistenceException;
}

