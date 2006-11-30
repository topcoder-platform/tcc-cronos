/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.user.persistence;

import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;

import com.orpheus.user.persistence.ejb.ConfirmationMessageDTO;
import com.orpheus.user.persistence.impl.ConfirmationMessageTranslator;
import com.topcoder.util.cache.Cache;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogFactory;
import com.topcoder.validation.emailconfirmation.ConfirmationMessage;
import com.topcoder.validation.emailconfirmation.InvalidAddressException;
import com.topcoder.validation.emailconfirmation.PendingConfirmationStorageInterface;

/**
 * <p>
 * An implementation of the <code>EmailConfirmationStorageInterface</code>
 * which uses an EJB session bean to provide persistent storage for pending
 * confirmation messages. It supports all operations defined in the interface.
 * </p>
 * <p>
 * While this class performs the majority of the work required to persist the
 * confirmation messages, it does not actually interact with the EJB session
 * bean directly. Rather, the calls to the session bean are deferred by invoking
 * the corresponding <code>ejbXXX()</code> template methods from within the
 * persistence operations. These methods are abstract and protected, and are
 * meant to be implemented by subclasses. By doing so, the subclasses can decide
 * what EJB session bean to call and whether the local or remote interface to
 * the session bean should be used.
 * </p>
 * <p>
 * Two subclasses are provided in this component:
 * {@link LocalOrpheusPendingConfirmationStorage} and
 * {@link RemoteOrpheusPendingConfirmationStorage}. The
 * <code>LocalOrpheusPendingConfirmationStorage</code> uses the local
 * interface to the EJB session bean, and should be used when the application is
 * running in the same execution environment (JVM) as the session bean. The
 * <code>RemoteOrpheusPendingConfirmationStorage</code> class, on the other
 * hand, uses the remote interface to the EJB session, allowing the application
 * to interact with session bean remotely from a different execution environment
 * (JVM).
 * </p>
 * <p>
 * Since the <code>ConfirmationMessage</code> objects are not serializable,
 * this class first converts them internally to corresponding
 * {@link ConfirmationMessageDTO} instances in order to facilitate the transfer
 * of confirmation message information to and from the EJB session bean. An
 * {@link ObjectTranslator} is used to perform this conversion. The name of the
 * <code>ObjectTranslator</code> implementation class to use should be
 * specified in this class's configuration (see the <b>Configuration</b>
 * section below). Applications may use the
 * {@link ConfirmationMessageTranslator} class provided in this component.
 * </p>
 * <p>
 * Confirmation messages are cached within this class using a <code>Cache</code>
 * instance in order to reduce the number of requests made to the session bean
 * and to improve response times. The manner in which the messages are cached
 * (including the caching strategy, cache size, etc) can be configured in the
 * Simple Cache configuration namespace. Please consult the Simple Cache
 * component specification for information on how to configure the cache.
 * </p>
 * <p>
 * <b>Logging Activity:</b><br>
 * If any errors occur while performing the persistence operations, they will be
 * logged using the ERROR log level.
 * </p>
 * <p>
 * <b>Configuration:</b><br>
 * In order to use this class, it needs to be configured in the configuration
 * namespace provided to the {@link #OrpheusPendingConfirmationStorage(String)}
 * constructor. The configuration parameters are listed in the table below.
 * </p>
 * <table border="1"> <thead>
 * <tr>
 * <td><b>Property</b></td>
 * <td><b>Description</b></td>
 * <td><b>Required</b></td>
 * </tr>
 * </thead> <tbody>
 * <tr>
 * <td>specNamespace</td>
 * <td>The ObjectFactory configuration namespace specifying the
 * <code>ObjectTranslator</code> and <code>Cache</code> object creation.
 * This namespace is passed to the
 * <code>ConfigManagerSpecificationFactory</code> class</td>
 * <td>Yes</td>
 * </tr>
 * <tr>
 * <td>translatorKey</td>
 * <td>The key to pass to the <code>ObjectFactory</code> to create the
 * <code>ObjectTranslator</code> instance</td>
 * <td>Yes</td>
 * </tr>
 * <tr>
 * <td>cacheKey</td>
 * <td>The key to pass to the <code>ObjectFactory</code> to create the
 * <code>Cache</code> instance</td>
 * <td>Yes</td>
 * </tr>
 * <tr>
 * <td>log</td>
 * <td>The name of the logger to use when logging errors</td>
 * <td>Yes</td>
 * </tr>
 * </tbody> </table>
 * <p>
 * <b>Thread-safety:</b><br>
 * This class is immutable and thread-safe.
 * </p>
 *
 * @author argolite, mpaulse
 * @version 1.0
 */
public abstract class OrpheusPendingConfirmationStorage implements PendingConfirmationStorageInterface {

    /**
     * <p>
     * The object translator which is used to convert ConfirmationMessage
     * objects to ConfirmationMessageDTO instances, and vice-versa.
     * </p>
     * <p>
     * This field is set in the constructor, and can never be changed
     * afterwards. It cannot be null.
     * </p>
     */
    private final ObjectTranslator objectTranslator;

    /**
     * <p>
     * A cache of ConfirmationMessageDTO objects. The objects are indexed in the
     * by the corresponding confirmation message addresses. The cache size,
     * replacement policy and other caching options are set in the Simple Cache
     * configuration namespace.
     * </p>
     * <p>
     * This field is created in the constructor, and can never be changed
     * afterwards. It cannot be null.
     * </p>
     */
    private final Cache cache;

    /**
     * <p>
     * The logger which is used to report persistence errors.
     * </p>
     * <p>
     * This field is set in the constructor, and can never be changed
     * afterwards. It cannot be null.
     * </p>
     */
    private final Log log;

    /**
     * <p>
     * Creates a new <code>OrpheusPendingConfirmationStorage</code> instance
     * using the specified configuration namespace.
     * </p>
     * <p>
     * The configuration namespace is used to load the {@link ObjectTranslator},
     * which is used to convert <code>ConfirmationMessage</code> objects to
     * serializable {@link ConfirmationMessageDTO} instances that are
     * transported to the persistence layer, and vice-versa. It is also used to
     * load the <code>Cache</code>, which is used to cache confirmation
     * messages within this class, and the name of the logger which will be used
     * to log persistence errors. If an error occurs reading the configuration
     * information or while instantiating the <code>ObjectTranslator</code>
     * and <code>Cache</code> objects, an
     * <code>ObjectInstantiationException</code> is thrown. Please consult the
     * class documentation for more information on the configuration parameters.
     * </p>
     *
     * @param namespace the configuration namespace from which to read
     *        configuration information
     * @throws IllegalArgumentException if the configuration namespace is
     *         <code>null</code> or a blank string
     * @throws ObjectInstantiationException if an error occurs reading from the
     *         configuration namespace or while instantiating the
     *         <code>ObjectTranslator</code> and <code>Cache</code> objects
     */
    protected OrpheusPendingConfirmationStorage(String namespace) throws ObjectInstantiationException {
        UserLogicPersistenceHelper.assertArgumentNotNullOrBlank(namespace, "namespace");

        // Create the ObjectTranslator and Cache.
        objectTranslator = (ObjectTranslator) UserLogicPersistenceHelper.createObject(namespace, "translatorKey",
                                                                                      ObjectTranslator.class);
        cache = (Cache) UserLogicPersistenceHelper.createObject(namespace, "cacheKey", Cache.class);

        // Create the Log.
        String logName = UserLogicPersistenceHelper.getConfigProperty(namespace, "log", true);
        log = LogFactory.getLog(logName);
    }

    /**
     * <p>
     * Stores the given confirmation message in the persistent store. If the
     * persistent store already contains a confirmation message with the same
     * address as that of the given message, an
     * <code>InvalidAddressException</code> is thrown.
     * </p>
     * <p>
     * Once the confirmation message has been successfully stored, it is cached
     * within this class in order to reduce the number of retrieval requests
     * made to the EJB session bean and to improve response times.
     * </p>
     * <p>
     * Any errors which may occur while storing the confirmation message will be
     * logged using the ERROR log level.
     * </p>
     *
     * @param message the confirmation message to store
     * @throws IllegalArgumentException if the confirmation message is
     *         <code>null</code>
     * @throws InvalidAddressException if the persistent store contains a
     *         confirmation message with the same address as that of the given
     *         message
     */
    public void store(ConfirmationMessage message) {
        UserLogicPersistenceHelper.assertArgumentNotNull(message, "pending confirmation message");

        // Check if the confirmation message is already in the cache.
        if (cache.get(message.getAddress().toLowerCase()) != null) {
            throw new InvalidAddressException("A pending confirmation message with address, " + message.getAddress()
                                              + ", already exists in the persistent store");
        }

        try {
            // Translate the confirmation message to its corresponding DTO.
            ConfirmationMessageDTO messageDTO = (ConfirmationMessageDTO) objectTranslator.assembleDTO(message);

            // Store the confirmation message and add it to the cache.
            ejbStore(messageDTO);
            cache.put(message.getAddress().toLowerCase(), messageDTO);
        } catch (TranslationException e) {
            log.log(Level.ERROR, "Error assembling ConfirmationMessageDTO from given ConfirmationMessage: " + e);
        } catch (DuplicateEntryException e) {
            log.log(Level.ERROR,
                    "Failed to store pending confirmation message with address, " + message.getAddress() + ": " + e);
            throw new InvalidAddressException("Failed to store pending confirmation message with address, "
                                              + message.getAddress(), e);
        } catch (PersistenceException e) {
            log.log(Level.ERROR,
                    "Failed to store pending confirmation message with address, " + message.getAddress() + ": " + e);
        }
    }

    /**
     * <p>
     * Returns whether a confirmation message with the specified address exists
     * in the persistent store.
     * </p>
     * <p>
     * If the confirmation message was found in the persistent store, it is
     * cached within this class in order to reduce the number of retrieval
     * requests made to the EJB session bean and to improve response times.
     * </p>
     * <p>
     * Any errors which may occur while checking if the confirmation message
     * exists in the persistent store will be logged using the ERROR log level.
     * </p>
     *
     * @param address the address of the confirmation message to check for
     * @return <code>true</code> if the confirmation message exists in the
     *         persistent store; <code>false</code> otherwise
     * @throws IllegalArgumentException if the specified address is
     *         <code>null</code> or a blank string
     */
    public boolean contains(String address) {
        UserLogicPersistenceHelper.assertArgumentNotNullOrBlank(address, "pending confirmation message address");

        // Check if the message is in the cache.
        if (cache.get(address.toLowerCase()) != null) {
            return true;
        }

        // If it is not in the cache, check if it is in persistent storage.
        // If it is in persistent storage, store it in the cache.
        try {
            ConfirmationMessageDTO messageDTO = ejbContains(address);
            if (messageDTO != null) {
                cache.put(address.toLowerCase(), messageDTO);
                return true;
            }
        } catch (PersistenceException e) {
            log.log(Level.ERROR, "Unable to check if a pending confirmation message with address, " + address
                    + ", is in the persistent store: " + e);
        }

        // The message is not in persistent storage (or an error has occurred).
        return false;
    }

    /**
     * <p>
     * Retrieves the confirmation message with the specified address from the
     * persistent store. If no such confirmation message could be found, an
     * <code>InvalidAddressException</code> is thrown. If an error occurs
     * while retrieving the confirmation message, <code>null</code> is
     * returned.
     * </p>
     * <p>
     * Once the confirmation message has been retrieved, it is cached within
     * this class in order to reduce the number of retrieval requests made to
     * the EJB session bean and to improve response times.
     * </p>
     * <p>
     * Any errors which may occur while retrieving the confirmation message will
     * be logged using the ERROR log level.
     * </p>
     *
     * @param address the address of the confirmation message to retrieve
     * @return the confirmation message with the specified address, or
     *         <code>null</code> if an error occurs while retrieving the
     *         confirmation message
     * @throws IllegalArgumentException if the specified address is
     *         <code>null</code> or a blank string
     * @throws InvalidAddressException if no confirmation message with the
     *         specified address exists in the persistent store
     */
    public ConfirmationMessage retrieve(String address) {
        UserLogicPersistenceHelper.assertArgumentNotNullOrBlank(address, "pending confirmation message address");

        // If the message is in the cache, return it.
        // Otherwise, retrieve it from the persistent storage,
        // add it to the cache, and then return it.
        ConfirmationMessageDTO messageDTO = (ConfirmationMessageDTO) cache.get(address.toLowerCase());
        if (messageDTO == null) {
            try {
                messageDTO = ejbRetrieve(address);
                cache.put(address.toLowerCase(), messageDTO);
            } catch (EntryNotFoundException e) {
                log.log(Level.ERROR,
                        "Failed to retrieve pending confirmation message with address, " + address + ": " + e);
                throw new InvalidAddressException("Failed to retrieve pending confirmation message with address, "
                                                  + address, e);
            } catch (PersistenceException e) {
                log.log(Level.ERROR,
                        "Failed to retrieve pending confirmation message with address, " + address + ": " + e);
            }
        }

        // Translate the DTO to a VO, and return it.
        try {
            return (ConfirmationMessage) objectTranslator.assembleVO(messageDTO);
        } catch (TranslationException e) {
            log.log(Level.ERROR,
                    "Error assembling ConfirmationMessage object from retrieved ConfirmationMessageDTO: " + e);
        }

        // Error
        return null;
    }

    /**
     * <p>
     * Deletes the confirmation message with the specified address from the
     * persistent store. If no such confirmation message could be found, an
     * <code>InvalidAddressException</code> is thrown.
     * </p>
     * <p>
     * If the confirmation message is cached within this class, it is deleted
     * from the cache as well.
     * </p>
     * <p>
     * Any errors which may occur while deleting the confirmation message will
     * be logged using the ERROR log level.
     * </p>
     *
     * @param address the address of the confirmation message to delete
     * @throws IllegalArgumentException if the specified address is
     *         <code>null</code> or a blank string
     * @throws InvalidAddressException if no confirmation message with the
     *         specified address exists in the persistent store
     */
    public void delete(String address) {
        UserLogicPersistenceHelper.assertArgumentNotNullOrBlank(address, "pending confirmation message address");

        // Delete the confirmation message from the cache and the persistent
        // storage.
        cache.remove(address.toLowerCase());
        try {
            ejbDelete(address);
        } catch (EntryNotFoundException e) {
            log.log(Level.ERROR, "Failed to delete pending configuration message with address, " + address + ": " + e);
            throw new InvalidAddressException("Failed to delete pending configuration message with address, "
                                              + address, e);
        } catch (PersistenceException e) {
            log.log(Level.ERROR, "Failed to delete pending confirmation message with address, " + address + ": " + e);
        }
    }

    /**
     * <p>
     * Retrieves an enumeration of the addresses of all the confirmation
     * messages currently in the persistent store. If no confirmation messages
     * exist in the persistent store, an empty enumeration is returned.
     * </p>
     * <p>
     * Once all the confirmation message have been retrieved, they are cached
     * within this class in order to reduce the number of retrieval requests
     * made to the EJB session bean and to improve response times.
     * </p>
     * <p>
     * Any errors which may occur while retrieving the addresses will be logged
     * using the ERROR log level.
     * </p>
     *
     * @return an enumeration of the addresses of all the confirmation messages
     *         in the persistent store, or an empty enumeration if there are no
     *         confirmation messages
     */
    public Enumeration getAddresses() {
        try {
            // Get the confirmation messages.
            ConfirmationMessageDTO[] messageDTOs = ejbGetMessages();

            // Put the confirmation message DTO's in the cache,
            // and create an array storing their addresses.
            String[] addresses = new String[messageDTOs.length];
            for (int i = 0; i < messageDTOs.length; i++) {
                addresses[i] = messageDTOs[i].getAddress();
                cache.put(addresses[i].toLowerCase(), messageDTOs[i]);
            }

            // Return an enumeration of the message addresses.
            return Collections.enumeration(Arrays.asList(addresses));
        } catch (PersistenceException e) {
            log.log(Level.ERROR, "Failed to retrieve all the pending confirmation message addresses: " + e);
        }

        // Return an empty enumeration if an error occurred.
        return Collections.enumeration(Arrays.asList(new String[0]));
    }

    /**
     * <p>
     * Implemented by subclasses to store the given confirmation message in the
     * persistent store using the EJB session bean. If the persistent store
     * already contains a confirmation message with the same address as that of
     * the given message, a <code>DuplicateEntryException</code> is thrown.
     * </p>
     *
     * @param message the data transfer object (DTO) representing the
     *        confirmation message to store
     * @throws DuplicateEntryException if the persistent store contains a
     *         confirmation message with the same address as that of the given
     *         message
     * @throws PersistenceException if storing the confirmation message in the
     *         persistent store fails
     */
    protected abstract void ejbStore(ConfirmationMessageDTO message) throws PersistenceException;

    /**
     * <p>
     * Implemented by subclasses to check if a confirmation message with the
     * specified address exists in the persistent store using the EJB session
     * bean. It it does, a data transfer object (DTO) representing the
     * confirmation message is returned. Otherwise, <code>null</code> is
     * returned.
     * </p>
     *
     * @param address the address of the confirmation message to check for
     * @return a <code>ConfirmationMessageDTO</code> representing the
     *         confirmation message with the specified address, or
     *         <code>null</code> if no such confirmation message exists in the
     *         persistent store
     * @throws PersistenceException if checking if the confirmation message is
     *         in the persistent store fails
     */
    protected abstract ConfirmationMessageDTO ejbContains(String address) throws PersistenceException;

    /**
     * <p>
     * Implemented by subclasses to retrieve the confirmation message with the
     * specified address from the EJB session bean. If no such confirmation
     * message could be found, an <code>EntryNotFoundException</code> is
     * thrown.
     * </p>
     *
     * @param address the address of the confirmation message to retrieve
     * @return a <code>ConfirmationMessageDTO</code> representing the
     *         confirmation message with the specified address
     * @throws EntryNotFoundException if no confirmation message with the
     *         specified address exists in the persistent store
     * @throws PersistenceException if retrieving the confirmation message from
     *         the persistent store fails
     */
    protected abstract ConfirmationMessageDTO ejbRetrieve(String address) throws PersistenceException;

    /**
     * <p>
     * Implemented by subclasses to delete the confirmation message with the
     * specified address from the persistent store using the EJB session bean.
     * If no such confirmation message could be found, an
     * <code>EntryNotFoundException</code> is thrown.
     * </p>
     *
     * @param address the address of the confirmation message to delete
     * @throws EntryNotFoundException if no confirmation message with the
     *         specified address exists in the persistent store
     * @throws PersistenceException if deleting the confirmation message from
     *         the persistent store fails
     */
    protected abstract void ejbDelete(String address) throws PersistenceException;

    /**
     * <p>
     * Implemented by subclasses to retrieve all the confirmation messages from
     * the EJB session bean as an array. If no confirmation messages exist in
     * the persistent store, an empty array is returned.
     * </p>
     *
     * @return a <code>ConfirmationMessageDTO[]</code> array containing all
     *         the confirmation messages in the persistent store, or an empty
     *         array if there are no confirmation messages
     * @throws PersistenceException if retrieving the all confirmation messages
     *         from the persistent store fails
     */
    protected abstract ConfirmationMessageDTO[] ejbGetMessages() throws PersistenceException;

}
