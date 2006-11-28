/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.administration.persistence;

import com.topcoder.message.messenger.MessageException;
import com.topcoder.message.messenger.MessageAPI;
import com.topcoder.message.messenger.MessengerPlugin;

import com.orpheus.administration.persistence.impl.AdminMessage;

/**
 * This is the <i>Messenger Framework</i> <code>MessengerPlugin</code> persistence client to the EJB layer. It
 * implements <code>MessengerPlugin</code> and supports its lone operation: <code>send</code>. It uses the
 * <i>Configuration Manager</i> and <i>Object Factory</i> to initialize itself. It is built to work with EJBs, and
 * this class leaves it to implementations to specify the EJBs; hence the abstract {@link #ejbSendMessage
 * ejbSendMessage} method. The public methods defer to this for actual persistence calls. This class delegates to a
 * <code>MessageTranslator</code> instance to perform translations between the {@link MessageAPI MessageAPI} class,
 * {@link AdminMessage AdminMessage}, and its equivalent transport entity -- <code>Message</code>.
 *
 * <p><strong>Thread Safety</strong>: This class is immutable and thread-safe.</p>
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */

public abstract class OrpheusMessengerPlugin extends MessengerPlugin {
    /**
     * The message translator for converting between value objects and data transfer objects. This member is
     * initialized in the constructor, is immutable, and will never be <code>null</code>.
     */
    private final MessageTranslator translator;

    /**
     * Constructs a new <code>OrpheusMessengerPlugin</code> based on the specified configuration namespace. See the
     * class documentation for a description of the configuration parameters.
     *
     * @param namespace the configuration namespace to use
     * @throws IllegalArgumentException if <code>namespace</code> is <code>null</code> or an empty string
     * @throws InstantiationException if an error occurs while instantiating the message translator
     */
    protected OrpheusMessengerPlugin(String namespace) throws InstantiationException {
        ParameterHelpers.checkString(namespace, "orpheus messenger plugin namespace");

        translator = (MessageTranslator) ObjectFactoryHelpers.
            instantiateObject(namespace, "specNamespace", "messageTranslatorKey", MessageTranslator.class);
    }

    /**
     * Sends a message using this message translator.
     *
     * @param message the value object to send
     * @throws MessageException if the message is invalid or not of the correct type
     * @throws PersistenceException if the message cannot be translated or some other checked exception occurs
     *   during the execution of this method
     * @throws DuplicateEntryException if the message GUID already exists in persistence
     * @throws IllegalArgumentException if <code>message</code> is <code>null</code>
     */
    public void sendMessage(MessageAPI message) throws MessageException, PersistenceException {
        if (message == null) {
            throw new IllegalArgumentException("message must not be null");
        }

        if (!message.check()) {
            throw new MessageException("invalid message");
        }

        if (!getMessageType().isInstance(message)) {
            throw new MessageException("message '" + message.getClass().getName() + "' is not the required type '"
                                       + getMessageType().getName());
        }

        Message dto;

        try {
            dto = translator.assembleMessageDTO(message);
        } catch (TranslationException ex) {
            throw new PersistenceException("failed to translate message: " + ex.getMessage(), ex);
        }

        ejbSendMessage(dto);
    }

    /**
     * Returns the specific subclass of {@link MessageAPI MessageAPI} expected by {@link #sendMessage
     * sendMessage}. In this case, the subclass is {@link AdminMessage AdminMessage}.
     *
     * @return the specific subclass of {@link MessageAPI MessageAPI} expected by {@link #sendMessage sendMessage}
     */
    protected Class getMessageType() {
        return AdminMessage.class;
    }

    /**
     * Routes the DTO to the applicable EJB.
     *
     * @param message the message
     * @throws PersistenceException if any checked exception prevents the successful completion of this method
     * @throws DuplicateEntryException if the message GUID already exists in persistence
     * @throws IllegalArgumentException if <code>message</code> is <code>null</code>
     */
    protected abstract void ejbSendMessage(Message message) throws PersistenceException;
}
