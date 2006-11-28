/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.administration.persistence;

/**
 * <p>Interface specifying the contract for translating between message-related instances and their transport
 * equivalents: <code>Message</code>. The former are value objects used on the outside world and a data transfer
 * object <i>(DTO)</i> is an entity that this component uses to convey info between the clients and the
 * DAOs. Implementations will constrain the data types they support.
 *
 * <p><strong>Thread Safety</strong>: Implementations are expected to be thread-safe.
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */

public interface MessageTranslator {
    /**
     * <p>Assembles the value object (<i>VO</i>) from the Message data tranfer object (<i>DTO</i>). The VO
     * represents an object that is used outside this component. The DTO is used as the custom transfer wrapper for
     * messages inside this component to ensure serializability.</p>
     *
     * @param messageDataTransferObject the message DTO
     * @return value object converted from <code>messageDataTransferObject</code>
     * @throws IllegalArgumentException if <code>messageDataTransferObject</code> is <code>null</code>
     * @throws TranslationException if a problem occurs during translation
     */
    public Object assembleMessageVO(Message messageDataTransferObject) throws TranslationException;

    /**
     * <p>Assembles the data transfer object (<i>DTO</i>) from the value object (<i>VO</i>). The VO represents a
     * object that is used outside this component. The DTO is used as the custom transfer wrapper for messages
     * inside this component to ensure serializability.</p>
     *
     * @param valueObject the message VO
     * @return the message data transfer object
     * @throws IllegalArgumentException if <code>valueObject</code> is <code>null</code>
     * @throws TranslationException if a problem occurs during translation
     */
    public Message assembleMessageDTO(Object valueObject) throws TranslationException;
}
