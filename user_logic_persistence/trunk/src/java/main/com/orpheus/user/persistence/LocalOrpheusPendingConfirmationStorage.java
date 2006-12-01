/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.user.persistence;

import javax.ejb.CreateException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

import com.orpheus.user.persistence.ejb.ConfirmationMessageDTO;
import com.orpheus.user.persistence.ejb.PendingConfirmationHomeLocal;
import com.orpheus.user.persistence.ejb.PendingConfirmationLocal;

/**
 * <p>
 * An implementation of the <code>EmailConfirmationStorageInterface</code>
 * which provides persistent storage for pending confirmation messages. It
 * implements the abstract <code>ejbXXX()</code> methods defined in the
 * {@link OrpheusPendingConfirmationStorage} base class in order to interact
 * with the local {@link PendingConfirmationLocal} EJB session bean.
 * </p>
 * <p>
 * This class should be used when the application is running in the same
 * execution environment (JVM) as the session bean.
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
 * {@link #LocalOrpheusPendingConfirmationStorage(String)} constructor. Besides
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
 * <td>The JNDI reference to the local {@link PendingConfirmationLocal} EJB
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
public class LocalOrpheusPendingConfirmationStorage extends OrpheusPendingConfirmationStorage {

    /**
     * <p>
     * The local EJB session bean that to which all persistent operations will
     * be delegated.
     * </p>
     * <p>
     * This field is set in the constructor, and will never be changed
     * afterwards. It cannot be null.
     * </p>
     */
    private final PendingConfirmationLocal pendingConfirmationEJB;

    /**
     * <p>
     * Creates a new <code>LocalOrpheusPendingConfirmationStorage</code>
     * instance using the default configuration namespace (the fully qualified
     * name of this class).
     * </p>
     * <p>
     * The configuration namespace is used to load the {@link ObjectTranslator},
     * which is used to convert <code>ConfirmationMessage</code> objects to
     * serializable {@link ConfirmationMessageDTO} instances that are
     * transported to the {@link PendingConfirmationLocal} EJB session bean, and
     * vice-versa. It is also used to load the <code>Cache</code>, which is
     * used to cache confirmation messages within this class, the name of the
     * logger which will be used to log persistence errors, and the JNDI
     * reference to the EJB session bean. If an error occurs reading the
     * configuration information, while instantiating the
     * <code>ObjectTranslator</code> or <code>Cache</code> objects or while
     * looking up the <code>PendingConfirmationLocal</code> EJB session bean,
     * an <code>ObjectInstantiationException</code> is thrown. Please consult
     * the class documentation for more information on the configuration
     * parameters.
     * </p>
     *
     * @throws ObjectInstantiationException if an error occurs reading from the
     *         configuration namespace, while instantiating the
     *         <code>ObjectTranslator</code> or <code>Cache</code> objects,
     *         or while looking up the <code>PendingConfirmationLocal</code>
     *         EJB session bean
     */
    public LocalOrpheusPendingConfirmationStorage() throws ObjectInstantiationException {
        this(LocalOrpheusPendingConfirmationStorage.class.getName());
    }

    /**
     * <p>
     * Creates a new <code>LocalOrpheusPendingConfirmationStorage</code>
     * instance using the specified configuration namespace.
     * </p>
     * <p>
     * The configuration namespace is used to load the {@link ObjectTranslator},
     * which is used to convert <code>ConfirmationMessage</code> objects to
     * serializable {@link ConfirmationMessageDTO} instances that are
     * transported to the {@link PendingConfirmationLocal} EJB session bean, and
     * vice-versa. It is also used to load the <code>Cache</code>, which is
     * used to cache confirmation messages within this class, the name of the
     * logger which will be used to log persistence errors, and the JNDI
     * reference to the EJB session bean. If an error occurs reading the
     * configuration information, while instantiating the
     * <code>ObjectTranslator</code> or <code>Cache</code> objects or while
     * looking up the <code>PendingConfirmationLocal</code> EJB session bean,
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
     *         or while looking up the <code>PendingConfirmationLocal</code>
     *         EJB session bean
     */
    public LocalOrpheusPendingConfirmationStorage(String namespace) throws ObjectInstantiationException {
        super(namespace);

        // Lookup the PendingConfirmationHomeLocal EJB interface.
        PendingConfirmationHomeLocal home = null;
        String jndiRef = UserLogicPersistenceHelper.getConfigProperty(namespace, "jndiEjbReference", true);
        try {
            Context context = new InitialContext();
            home = (PendingConfirmationHomeLocal) PortableRemoteObject.narrow(context.lookup(jndiRef),
                                                                              PendingConfirmationHomeLocal.class);
        } catch (NamingException e) {
            throw new ObjectInstantiationException("Unable to lookup with PendingConfirmationHomeLocal session bean "
                                                   + "with JNDI name, " + jndiRef, e);
        }

        // Create the PendingConfirmationLocal EJB interface.
        try {
            pendingConfirmationEJB = home.create();
        } catch (CreateException e) {
            throw new ObjectInstantiationException("Could not create PendingConfirmationLocal object", e);
        }
    }

    /**
     * <p>
     * Stores the given confirmation message in the persistent store using the
     * {@link PendingConfirmationLocal} EJB session bean. If the persistent
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
        pendingConfirmationEJB.store(message);
    }

    /**
     * <p>
     * Checks if a confirmation message with the specified address exists in the
     * persistent store using the {@link PendingConfirmationLocal} EJB session
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
        return pendingConfirmationEJB.contains(address);
    }

    /**
     * <p>
     * Retrieves the confirmation message with the specified address from the
     * {@link PendingConfirmationLocal} EJB session bean. If no such
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
        return pendingConfirmationEJB.retrieve(address);
    }

    /**
     * <p>
     * Deletes the confirmation message with the specified address from the
     * persistent store using the {@link PendingConfirmationLocal} EJB session
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
        pendingConfirmationEJB.delete(address);
    }

    /**
     * <p>
     * Retrieves all the confirmation messages from the
     * {@link PendingConfirmationLocal} EJB session bean as an array. If no
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
        return pendingConfirmationEJB.getMessages();
    }

}
