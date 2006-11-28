/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.administration.persistence;

/**
 * <p>Concrete implementation of <code>OrpheusMessageDataStore</code> that delegates to a local message EJB using
 * the {@link MessageLocal MessageLocal} interface.
 *
 * <p>This class has four configuration parameters, all of them required.
 *
 * <ul>
 *   <li><strong>specNamespace</strong>: the object factory configuration namespace to use to instantiate the
 *     message translator and search criteria translator</li>
 *   <li><strong>messageTranslatorKey</strong>: the object factory key to use to instantiate the message
 *     translator</li>
 *   <li><strong>searchCriteriaTranslatorKey</strong>: the object factory key to use to instantiate the search
 *     criteria translator</li>
 *   <li><strong>jndiEjbReference</strong>: the JNDI reference to the message data bean
 * </ul>
 *
 * <p><strong>Thread Safety</strong>: This object is immutable and thread safe.
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */

public class LocalOrpheusMessageDataStore extends OrpheusMessageDataStore {
    /**
     * The EJB to which to delegate the message calls. This member is initialized in the constructor, is immutable,
     * and will never be <code>null</code>.
     */
    private final MessageLocal messageEJB;

    /**
     * Creates a new <code>LocalOrpheusMessageDataStore</code> configured based on the specified namespace. See
     * the class documentation for a description of the configuration parameters.
     *
     * @param namespace the namespace containing the configuration parameters
     * @throws IllegalArgumentException if <code>namespace</code> is <code>null</code> or an empty string
     * @throws InstantiationException if <code>namespace</code> does not exist or is missing a required property
     * @throws InstantiationException if the message translator or search criteria translator cannot be instantiated
     * @throws InstantiationException if the message EJB cannot be resolved
     */
    public LocalOrpheusMessageDataStore(String namespace) throws InstantiationException {
        super(namespace);

        messageEJB = ObjectFactoryHelpers.resolveLocalEjbReference(namespace);
    }

    /**
     * Retrieves the messages in the data store matching the specified criteria.
     *
     * @param criteria the criteria to use to filter the messages
     * @return a (possibly empty) array containing the messages that match the criteria
     * @throws IllegalArgumentException if <code>criteria</code> is <code>null</code>
     * @throws PersistenceException if an error occurs while accessing the data store or any other checked
     *   exception prevents this method from completing
     */
    protected Message[] ejbFindMessages(SearchCriteriaDTO criteria) throws PersistenceException {
        if (criteria == null) {
            throw new IllegalArgumentException("search criteria must not be null");
        }

        return messageEJB.findMessages(criteria);
    }

    /**
     * Records a new message in the data store.
     *
     * @param message the message to enter into the data store
     * @throws IllegalArgumentException if <code>message</code> is <code>null</code>
     * @throws DuplicateEntryException if a message with the same GUID already exists
     * @throws PersistenceException if an error occurs while accessing the data store or any other checked
     *   exception prevents this method from completing
     */
    protected void ejbCreateMessage(Message message) throws PersistenceException {
        if (message == null) {
            throw new IllegalArgumentException("message must not be null");
        }

        messageEJB.createMessage(message);
    }

    /**
     * Updates the specified message in the data store.
     *
     * @param message the message to update
     * @throws IllegalArgumentException if <code>message</code> is <code>null</code>
     * @throws EntryNotFoundException if no message with the specified GUID exists in the data store
     * @throws PersistenceException if an error occurs while accessing the data store or any other checked
     *   exception prevents this method from completing
     */
    protected void ejbUpdateMessage(Message message) throws PersistenceException {
        if (message == null) {
            throw new IllegalArgumentException("message must not be null");
        }

        messageEJB.updateMessage(message);
    }

    /**
     * Deletes the specified message from the data store.
     *
     * @param message the message to delete
     * @throws IllegalArgumentException if <code>message</code> is <code>null</code>
     * @throws PersistenceException if an error occurs while accessing the data store or any other checked
     *   exception prevents this method from completing
     */
    protected void ejbDeleteMessage(Message message) throws PersistenceException {
        if (message == null) {
            throw new IllegalArgumentException("message must not be null");
        }

        messageEJB.deleteMessage(message);
    }
}
