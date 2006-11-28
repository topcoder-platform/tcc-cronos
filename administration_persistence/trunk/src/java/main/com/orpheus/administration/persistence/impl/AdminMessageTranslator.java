/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.administration.persistence.impl;

import com.orpheus.administration.persistence.Message;
import com.orpheus.administration.persistence.MessageTranslator;

import java.util.Date;


/**
 * <p>Implementation of the translator between message-related instances and their transport equivalents:
 * <code>Message</code>. The former are value objects used on the outside world and a data transfer object
 * (<i>DTO</i>) is an entity that this component uses to convey info between the clients and the DAOs.
 *
 * <p><strong>Thread Safety</strong>: This class is thread safe by virtue of being immutable.
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */

public class AdminMessageTranslator implements MessageTranslator {
    /**
     * Default constructor.
     */
    public AdminMessageTranslator() {
    }

    /**
     * <p>Assembles the value object (<i>VO</i>) from the Message data tranfer object (<i>DTO</i>). The VO
     * represents an object that is used outside this component. The DTO is used as the custom transfer wrapper for
     * messages inside this component to ensure serializability.</p>
     *
     * @param messageDataTransferObject the message DTO
     * @return <code>AdminMessage</code> object converted from <code>messageDataTransferObject</code>
     * @throws IllegalArgumentException if <code>messageDataTransferObject</code> is <code>null</code>
     */
    public Object assembleMessageVO(Message messageDataTransferObject) {
        if (messageDataTransferObject == null) {
            throw new IllegalArgumentException("message DTO must not be null");
        }

        AdminMessage msg = new AdminMessage();

        msg.setParameterValue(AdminMessage.GUID, messageDataTransferObject.getGuid());
        msg.setParameterValue(AdminMessage.CATEGORY, messageDataTransferObject.getCategory());
        msg.setParameterValue(AdminMessage.CONTENT_TYPE, messageDataTransferObject.getContentType());
        msg.setParameterValue(AdminMessage.CONTENT, messageDataTransferObject.getContent());
        msg.setParameterValue(AdminMessage.TIMESTAMP, messageDataTransferObject.getTimestamp());

        return msg;
    }

    /**
     * <p>Assembles the data transfer object (<i>DTO</i>) from the message value object (<i>VO</i>). The DTO is
     * used as the custom transfer object for messages inside this component to ensure serializability.
     *
     * @param valueObject the message VO
     * @return a <code>Message</code> converted from <code>valueObject</code>
     * @throws IllegalArgumentException if <code>valueObject</code> is <code>null</code> or is not an instance of
     *   {@link AdminMessage AdminMessage}
     */
    public Message assembleMessageDTO(Object valueObject) {
        if (valueObject == null) {
            throw new IllegalArgumentException("message VO must not be null");
        }

        AdminMessage admin;
        try {
            admin = (AdminMessage) valueObject;
        } catch (ClassCastException ex) {
            throw new IllegalArgumentException("message VO must be AdminMessage");
        }

        if (!admin.check()) {
            throw new IllegalArgumentException("invalid message passed to assembleMessageDTO");
        }

        return RSSItemTranslator.createMessageFromAdminMessage(admin);
    }
}
