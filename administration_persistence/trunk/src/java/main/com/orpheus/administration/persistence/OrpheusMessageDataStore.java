/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.administration.persistence;

import com.topcoder.util.rssgenerator.DataStore;
import com.topcoder.util.rssgenerator.RSSFeed;
import com.topcoder.util.rssgenerator.RSSItem;
import com.topcoder.util.rssgenerator.RSSPersistenceException;
import com.topcoder.util.rssgenerator.SearchCriteria;

/**
 * <p>The <i>RSS Generator</i> persistence interface to the EJB layer. It implements a subset of the
 * <code>DataStore</code> interface -- only {@link RSSItem RSSItem} related operations are supported. This class is
 * responsible for converting between DTOs and VOs and it is left to concrete subclasses to actually interface with
 * the EJB.
 *
 * <p>This class has three configuration parameters, all of them required.
 *
 * <ul>
 *   <li><strong>specNamespace</strong>: the object factory configuration namespace to use to instantiate the
 *     message translator and search criteria translator</li>
 *   <li><strong>messageTranslatorKey</strong>: the object factory key to use to instantiate the message
 *     translator</li>
 *   <li><strong>searchCriteriaTranslatorKey</strong>: the object factory key to use to instantiate the search
 *     criteria translator</li>
 * </ul>
 *
 * <p><strong>Thread Safety</strong>: This class is immutable and thread safe.
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */

public abstract class OrpheusMessageDataStore implements DataStore {
    /**
     * Object responsible for translating between {@link RSSItem RSSItem} and {@link AdminMessage
     * AdminMessage}. This member is initialized in the constructor, is immutable, and will never be
     * <code>null</code>.
     */
    private final MessageTranslator messageTranslator;

    /**
     * Object responsible for translating between {@link SearchCriteria SearchCriteria} and {@link
     * SearchCriteriaDTO SearchCriteriaDTO}. This member is initialized in the constructor, is immutable, and will
     * never be <code>null</code>.
     */
    private final SearchCriteriaTranslator searchCriteriaTranslator;

    /**
     * Constructs a new <code>OrpheusMessengerPlugin</code> based on the specified configuration namespace. See the
     * class documentation for a description of the configuration parameters.
     *
     * @param namespace namespace to use to configure this instance
     * @throws IllegalArgumentException if <code>namespace</code> is <code>null</code> or an empty string
     * @throws InstantiationException if the search criteria or message translator cannot be instantiated
     */
    protected OrpheusMessageDataStore(String namespace) throws InstantiationException {
        ParameterHelpers.checkString(namespace, "message data store namespace");

        messageTranslator = (MessageTranslator) ObjectFactoryHelpers.
            instantiateObject(namespace, "specNamespace", "messageTranslatorKey", MessageTranslator.class);
        searchCriteriaTranslator = (SearchCriteriaTranslator) ObjectFactoryHelpers.
            instantiateObject(namespace, "specNamespace", "searchCriteriaTranslatorKey",
                              SearchCriteriaTranslator.class);
    }

    /**
     * This method is not supported by this implementation and always returns an empty array.
     *
     * @param criteria the criteria used to filter the feeds
     * @return an empty array
     */
    public RSSFeed[] findFeeds(SearchCriteria criteria) {
        return new RSSFeed[0];
    }

    /**
     * Returns items in the data store matching the specified criteria.
     *
     * @param criteria the criteria used to filter the items
     * @return a (possible empty) array of items matching the specified criteria
     * @throws IllegalArgumentException if <code>criteria</code> is <code>null</code>
     * @throws RSSPersistenceException if an error occurs while accessing the data store or any other checked
     *   exception prevents this method from completing
     */
    public RSSItem[] findItems(SearchCriteria criteria) throws RSSPersistenceException {
        if (criteria == null) {
            throw new IllegalArgumentException("item search criteria must not be null");
        }

        SearchCriteriaDTO dto;
        try {
            dto = searchCriteriaTranslator.assembleSearchCriteriaDTO(criteria);
        } catch (TranslationException ex) {
            throw new RSSPersistenceException("error creating search criteria DTO: " + ex.getMessage(), ex);
        }

        Message[] messages;
        try {
            messages = ejbFindMessages(dto);
        } catch (PersistenceException ex) {
            throw new RSSPersistenceException(ex.getMessage(), ex);
        }

        RSSItem[] items = new RSSItem[messages.length];
        for (int idx = 0; idx < items.length; ++idx) {
            try {
                items[idx] = (RSSItem) messageTranslator.assembleMessageVO(messages[idx]);
            } catch (TranslationException ex) {
                throw new RSSPersistenceException("error creating message VO: " + ex.getMessage(), ex);
            }
        }

        return items;
    }

    /**
     * This methid is not supported by this implementation.
     *
     * @param feed the feed to create
     * @throws UnsupportedOperationException always thrown by this implementation
     */
    public void createFeed(RSSFeed feed) {
        throw new UnsupportedOperationException("DataStore.createFeed is not supported");
    }

    /**
     * Records the specified item in the data store.
     *
     * @param item the item to enter into the data store
     * @throws IllegalArgumentException if <code>item</code> is <code>null</code>
     * @throws RSSPersistenceException if an error occurs while accessing the data store or any other checked
     *   exception prevents this method from completing
     */
    public void createItem(RSSItem item) throws RSSPersistenceException {
        try {
            ejbCreateMessage(createMessageDTO(item));
        } catch (PersistenceException ex) {
            throw new RSSPersistenceException(ex.getMessage(), ex);
        }
    }

    /**
     * This methid is not supported by this implementation.
     *
     * @param feed the feed to update
     * @throws UnsupportedOperationException always thrown by this implementation
     */
    public void updateFeed(RSSFeed feed) {
        throw new UnsupportedOperationException("DataStore.updateFeed is not supported");
    }

    /**
     * Updates the specified item in the data store.
     *
     * @param item the item to update in the data store
     * @throws IllegalArgumentException if <code>item</code> is <code>null</code>
     * @throws RSSPersistenceException if an error occurs while accessing the data store, the item GUID does not
     *   exist, or any other checked exception prevents this method from completing
     */
    public void updateItem(RSSItem item) throws RSSPersistenceException {
        try {
            ejbUpdateMessage(createMessageDTO(item));
        } catch (PersistenceException ex) {
            throw new RSSPersistenceException(ex.getMessage(), ex);
        }
    }

    /**
     * This methid is not supported by this implementation.
     *
     * @param feed the feed to delete
     * @throws UnsupportedOperationException always thrown by this implementation
     */
    public void deleteFeed(RSSFeed feed) {
        throw new UnsupportedOperationException("DataStore.deleteFeed is not supported");
    }

    /**
     * Deletes the specified item from the data store.
     *
     * @param item the item to delete from the data store
     * @throws IllegalArgumentException if <code>item</code> is <code>null</code>
     * @throws RSSPersistenceException if an error occurs while accessing the data store, the item GUID does not
     *   exist, or any other checked exception prevents this method from completing
     */
    public void deleteItem(RSSItem item) throws RSSPersistenceException {
        try {
            ejbDeleteMessage(createMessageDTO(item));
        } catch (PersistenceException ex) {
            throw new RSSPersistenceException(ex.getMessage(), ex);
        }
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
    protected abstract Message[] ejbFindMessages(SearchCriteriaDTO criteria) throws PersistenceException;

    /**
     * Records a new message in the data store.
     *
     * @param message the message to enter into the data store
     * @throws IllegalArgumentException if <code>message</code> is <code>null</code>
     * @throws DuplicateEntryException if a message with the same GUID already exists
     * @throws PersistenceException if an error occurs while accessing the data store or any other checked
     *   exception prevents this method from completing
     */
    protected abstract void ejbCreateMessage(Message message) throws PersistenceException;

    /**
     * Updates the specified message in the data store.
     *
     * @param message the message to update
     * @throws IllegalArgumentException if <code>message</code> is <code>null</code>
     * @throws EntryNotFoundException if no message with the specified GUID exists in the data store
     * @throws PersistenceException if an error occurs while accessing the data store or any other checked
     *   exception prevents this method from completing
     */
    protected abstract void ejbUpdateMessage(Message message) throws PersistenceException;

    /**
     * Deletes the specified message from the data store.
     *
     * @param message the message to delete
     * @throws IllegalArgumentException if <code>message</code> is <code>null</code>
     * @throws PersistenceException if an error occurs while accessing the data store or any other checked
     *   exception prevents this method from completing
     */
    protected abstract void ejbDeleteMessage(Message message) throws PersistenceException;

    /**
     * Helper method to convert from <code>RSSItem</code> to <code>Message</code>.
     *
     * @param item the item to convert
     * @return the message DTO
     * @throws IllegalArgumentException if <code>item</code> is <code>null</code>
     * @throws RSSPersistenceException if the item cannot be translated
     */
    private Message createMessageDTO(RSSItem item) throws RSSPersistenceException {
        if (item == null) {
            throw new IllegalArgumentException("RSS item must not be null");
        }

        try {
            return messageTranslator.assembleMessageDTO(item);
        } catch (TranslationException ex) {
            throw new RSSPersistenceException("error creating message DTO: " + ex.getMessage());
        }
    }
}
