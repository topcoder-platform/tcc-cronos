/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.user.persistence;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

import com.orpheus.user.persistence.ejb.ConfirmationMessageDTO;
import com.orpheus.user.persistence.ejb.PendingConfirmationHomeRemote;
import com.orpheus.user.persistence.ejb.PendingConfirmationLocal;
import com.orpheus.user.persistence.ejb.PendingConfirmationRemote;

/**
 * <p>
 * An implementation of the <code>EmailConfirmationStorageInterface</code>
 * which provides persistent storage for pending confirmation messages. It
 * implements the abstract <code>ejbXXX()</code> methods defined in the
 * {@link OrpheusPendingConfirmationStorage} base class in order to interact
 * with the remote {@link PendingConfirmationRemote} EJB session bean.
 * </p>
 * <p>
 * This class should be used when the application and session bean are running
 * in different execution environments (JVM's), possibly on different host
 * machines.
 * </p>
 * <p>
 * <b>Logging Activity:</b><br>
 * If any errors occur while performing the persistence operations, they will be
 * logged using the ERROR log level.
 * </p>
 * <p>
 * <b>Configuration:</b><br>
 * In order to use this class, it needs to be configured in the configuration
 * namespace provided to the
 * {@link #RemoteOrpheusPendingConfirmationStorage(String)} constructor. Besides
 * the configuration properties described in the
 * {@link OrpheusPendingConfirmationStorage} class documentation, the
 * "jndiEjbRefence" configuration property also needs to be specified. This
 * property is described in the table below.
 * </p>
 * <table border="1"> <thead>
 * <tr>
 * <td><b>Property</b></td>
 * <td><b>Description</b></td>
 * <td><b>Required</b></td>
 * </tr>
 * </thead> <tbody>
 * <tr>
 * <td>jndiEjbReference</td>
 * <td>The JNDI reference to the remote {@link PendingConfirmationRemote} EJB
 * session bean</td>
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
 * @see PendingConfirmationLocal
 */
public class RemoteOrpheusPendingConfirmationStorage extends OrpheusPendingConfirmationStorage {

    /**
     * <p>
     * The remote EJB session bean that to which all persistent operations will
     * be delegated.
     * </p>
     * <p>
     * This field is set in the constructor, and will never be changed
     * afterwards. It cannot be null.
     * </p>
     */
    private final PendingConfirmationRemote pendingConfirmationEJB;

    /**
     * <p>
     * Creates a new <code>RemoteOrpheusPendingConfirmationStorage</code>
     * instance using the specified configuration namespace.
     * </p>
     * <p>
     * The configuration namespace is used to load the {@link ObjectTranslator},
     * which is used to convert <code>ConfirmationMessage</code> objects to
     * serializable {@link ConfirmationMessageDTO} instances that are
     * transported to the {@link PendingConfirmationRemote} EJB session bean,
     * and vice-versa. It is also used to load the <code>Cache</code>, which
     * is used to cache confirmation messages within this class, the name of the
     * logger which will be used to log persistence errors, and the JNDI
     * reference to the EJB session bean. If an error occurs reading the
     * configuration information, while instantiating the
     * <code>ObjectTranslator</code> or <code>Cache</code> objects or while
     * looking up the <code>PendingConfirmationRemote</code> EJB session bean,
     * an <code>ObjectInstantiationException</code> is thrown. Please consult
     * the class documentation for more information on the configuration
     * parameters.
     * </p>
     *
     * @param namespace the configuration namespace from which to read
     *        configuration information
     * @throws IllegalArgumentException if the configuration namespace is
     *         <code>null</code> or a blank string
     * @throws ObjectInstantiationException if an error occurs reading from the
     *         configuration namespace, while instantiating the
     *         <code>ObjectTranslator</code> or <code>Cache</code> objects,
     *         or while looking up the <code>PendingConfirmationRemote</code>
     *         EJB session bean
     */
    public RemoteOrpheusPendingConfirmationStorage(String namespace) throws ObjectInstantiationException {
        super(namespace);

        // Lookup the PendingConfirmationHomeRemote EJB interface.
        PendingConfirmationHomeRemote home = null;
        String jndiRef = UserLogicPersistenceHelper.getConfigProperty(namespace, "jndiEjbReference", true);
        try {
            Context context = new InitialContext();
            home = (PendingConfirmationHomeRemote) PortableRemoteObject.narrow(context.lookup(jndiRef),
                                                                               PendingConfirmationHomeRemote.class);
        } catch (NamingException e) {
            throw new ObjectInstantiationException("Unable to lookup PendingConfirmationHomeRemote session bean "
                                                   + "with JNDI name, " + jndiRef, e);
        }

        // Create the PendingConfirmationRemote EJB interface.
        try {
            pendingConfirmationEJB = home.create();
        } catch (CreateException e) {
            throw new ObjectInstantiationException("Could not create PendingConfirmationRemote object", e);
        } catch (RemoteException e) {
            throw new ObjectInstantiationException("Could not create PendingConfirmationRemote object", e);
        }
    }

    /**
     * <p>
     * Stores the given confirmation message in the persistent store using the
     * {@link PendingConfirmationRemote} EJB session bean. If the persistent
     * store already contains a confirmation message with the same address as
     * that of the given message, a <code>DuplicateEntryException</code> is
     * thrown.
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
    protected void ejbStore(ConfirmationMessageDTO message) throws PersistenceException {
        try {
            pendingConfirmationEJB.store(message);
        } catch (RemoteException e) {
            throw new PersistenceException("Failed to store pending confirmation message with address, "
                                           + message.getAddress(), e);
        }
    }

    /**
     * <p>
     * Checks if a confirmation message with the specified address exists in the
     * persistent store using the {@link PendingConfirmationRemote} EJB session
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
    protected ConfirmationMessageDTO ejbContains(String address) throws PersistenceException {
        try {
            return pendingConfirmationEJB.contains(address);
        } catch (RemoteException e) {
            throw new PersistenceException("Failed to check if pending confirmation message with address, "
                                           + address + ", is in the persistent store", e);
        }
    }

    /**
     * <p>
     * Retrieves the confirmation message with the specified address from the
     * {@link PendingConfirmationRemote} EJB session bean. If no such
     * confirmation message could be found, an
     * <code>EntryNotFoundException</code> is thrown.
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
    protected ConfirmationMessageDTO ejbRetrieve(String address) throws PersistenceException {
        try {
            return pendingConfirmationEJB.retrieve(address);
        } catch (RemoteException e) {
            throw new PersistenceException("Failed to retrieve pending confirmation message with address, " + address,
                                           e);
        }
    }

    /**
     * <p>
     * Deletes the confirmation message with the specified address from the
     * persistent store using the {@link PendingConfirmationRemote} EJB session
     * bean. If no such confirmation message could be found, an
     * <code>EntryNotFoundException</code> is thrown.
     * </p>
     *
     * @param address the address of the confirmation message to delete
     * @throws EntryNotFoundException if no confirmation message with the
     *         specified address exists in the persistent store
     * @throws PersistenceException if deleting the confirmation message from
     *         the persistent store fails
     */
    protected void ejbDelete(String address) throws PersistenceException {
        try {
            pendingConfirmationEJB.delete(address);
        } catch (RemoteException e) {
            throw new PersistenceException("Failed to delete pending confirmation message with address, " + address,
                                           e);
        }
    }

    /**
     * <p>
     * Retrieves all the confirmation messages from the
     * {@link PendingConfirmationRemote} EJB session bean as an array. If no
     * confirmation messages exist in the persistent store, an empty array is
     * returned.
     * </p>
     *
     * @return a <code>ConfirmationMessageDTO[]</code> array containing all
     *         the confirmation messages in the persistent store, or an empty
     *         array if there are no confirmation messages
     * @throws PersistenceException if retrieving the all confirmation messages
     *         from the persistent store fails
     */
    protected ConfirmationMessageDTO[] ejbGetMessages() throws PersistenceException {
        try {
            return pendingConfirmationEJB.getMessages();
        } catch (RemoteException e) {
            throw new PersistenceException("Failed to retrieve all pending confirmation messages", e);
        }
    }

}
