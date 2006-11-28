/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.administration.persistence;

/**
 * <p>Implementation of <code>OrpheusMessengerPlugin</code> that implements the {@link #ejbSendMessage ejbSendMessage}
 * by delegating to the {@link MessageLocal MessageLocal} bean interface.
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

public class LocalOrpheusMessengerPlugin extends OrpheusMessengerPlugin {
    /**
     * The EJB that will service the messenger calls. This member is initialized in the constructor, is immutable,
     * and will never be <code>null</code>.
     */
    private final MessageLocal messageEJB;

    /**
     * Constructs a new <code>LocalOrpheusMessengerPlugin</code> based on the specified namespace. See the class
     * documentation for a description of the configuration parameters.
     *
     * @param namespace the namespace containing configuration information for this
     *   <code>LocalOrpheusMessengerPlugin</code>
     * @throws IllegalArgumentException if <code>namespace</code> is <code>null</code> or an empty string
     * @throws InstantiationException if the message translator cannot be instantiated via the object factory
     * @throws InstantiationException if the message EJB cannot be resolved
     */
    public LocalOrpheusMessengerPlugin(String namespace) throws InstantiationException {
        super(namespace);

        messageEJB = ObjectFactoryHelpers.resolveLocalEjbReference(namespace);
    }

    /**
     * Delegates to the EJB to record a new message in the data store.
     *
     * @param message the message
     * @throws PersistenceException if any checked exception prevents the successful completion of this method
     * @throws DuplicateEntryException if the message GUID already exists in persistence
     * @throws IllegalArgumentException if <code>message</code> is <code>null</code>
     */
    protected void ejbSendMessage(Message message) throws PersistenceException {
        if (message == null) {
            throw new IllegalArgumentException("message must not be null");
        }

        messageEJB.createMessage(message);
    }
}
