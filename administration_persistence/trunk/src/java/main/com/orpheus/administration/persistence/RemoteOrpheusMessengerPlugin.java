/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.administration.persistence;

import java.rmi.RemoteException;

/**
 * <p>Implementation of <code>OrpheusMessengerPlugin</code> that implements the {@link #ejbSendMessage ejbSendMessage}
 * by delegating to the {@link MessageRemote MessageRemote} bean interface.
 *
 * <p>This class has three configuration parameters, all of them required.
 *
 * <ul>
 *   <li><strong>specNamespace</strong>: the namespace containing the object factory configuration for the message
 *     translator</li>
 *   <li><strong>messageTranslatorKey</strong>: the namespace containing the object factory key for the message
 *     translator</li>
 *   <li><strong>jndiEjbReference</strong>: the JNDI location of the EJB</li>
 * </ul>
 *
 * <p><strong>Thread Safety</strong>: This object is immutable and thread safe.
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */

public class RemoteOrpheusMessengerPlugin extends OrpheusMessengerPlugin {
    /**
     * The EJB that will service the messenger calls. This member is initialized in the constructor, is immutable,
     * and will never be <code>null</code>.
     */
    private final MessageRemote messageEJB;

    /**
     * Constructs a new <code>RemoteOrpheusMessengerPlugin</code> based on the specified namespace. See the class
     * documentation for a description of the configuration parameters.
     *
     * @param namespace the namespace containing configuration information for this
     *   <code>RemoteOrpheusMessengerPlugin</code>
     * @throws IllegalArgumentException if <code>namespace</code> is <code>null</code> or an empty string
     * @throws InstantiationException if the message translator cannot be instantiated via the object factory
     * @throws InstantiationException if the message EJB cannot be resolved
     */
    public RemoteOrpheusMessengerPlugin(String namespace) throws InstantiationException {
        super(namespace);

        messageEJB = ObjectFactoryHelpers.resolveRemoteEjbReference(namespace);
    }

    /**
     * Passes the message DTO to the EJB's {@link MessageRemote#createMessage createMessage} method.
     *
     * @param message the message
     * @throws IllegalArgumentException if <code>message</code> is <code>null</code>
     * @throws DuplicateEntryException if a message with the same GUID already exists in the data store
     * @throws PersistenceException if a problem occurs accessing persistent storage
     * @throws PersistenceException if any other unchecked exception causes this method to fail
     */
    protected void ejbSendMessage(Message message) throws PersistenceException {
        if (message == null) {
            throw new IllegalArgumentException("message must not be null");
        }

        try {
            messageEJB.createMessage(message);
        } catch (RemoteException ex) {
            throw new PersistenceException("error communicating with message bean: " + ex.getMessage(), ex);
        }
    }
}
