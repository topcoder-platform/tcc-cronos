/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.administration.persistence.impl;

import com.orpheus.administration.persistence.Message;
import com.orpheus.administration.persistence.MessageTranslator;

import com.topcoder.util.rssgenerator.RSSCategory;
import com.topcoder.util.rssgenerator.RSSItem;
import com.topcoder.util.rssgenerator.RSSText;

import com.topcoder.util.rssgenerator.impl.RSSCategoryImpl;
import com.topcoder.util.rssgenerator.impl.RSSObjectImpl;
import com.topcoder.util.rssgenerator.impl.RSSTextImpl;

import com.topcoder.util.rssgenerator.impl.atom10.Atom10Item;
import com.topcoder.util.rssgenerator.impl.atom10.Atom10Content;

import java.util.Date;

/**
 * <p>The concrete implementation of the <code>MessageTranslator</code> interface. This class translates between
 * {@link RSSItem RSSItem} objects, which are used by clients of this component, and {@link Message Message}
 * objects, which are used internally by the component to facilitate remote method invocations.
 *
 * <p><strong>Thread Safety</strong>: This object is immutable and therefore thread safe.
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */

public class RSSItemTranslator implements MessageTranslator {
    /**
     * <p>Empty default constructor.</p>
     */
    public RSSItemTranslator() {
    }

    /**
     * Creates an {@link RSSItem RSSItem} value object (<i>VO</i>) from a {@link Message Message} data transfer
     * object (<i>DTO</i>.
     *
     * @param messageDataTransferObject the DTO
     * @return a VO constructed from the specified DTO
     * @throws IllegalArgumentException if <code>messageDataTransferObject</code> is <code>null</code>
     */
    public Object assembleMessageVO(Message messageDataTransferObject) {
        if (messageDataTransferObject == null) {
            throw new IllegalArgumentException("message DTO must not be null");
        }

        Atom10Item item = new Atom10Item(new RSSObjectImpl());

        // GUID
        item.setId(messageDataTransferObject.getGuid());

	// Artificial title
	RSSText titleText = new RSSTextImpl(new RSSObjectImpl());
	titleText.setElementText("Server Message");
	item.setTitle(titleText);

        // category
        RSSCategory category = new RSSCategoryImpl(new RSSObjectImpl());
        category.setName(messageDataTransferObject.getCategory());
        item.addCategory(category);

        // content
        Atom10Content content = new Atom10Content(new RSSObjectImpl());
        content.setType(messageDataTransferObject.getContentType());
        content.setElementText(messageDataTransferObject.getContent());
        item.setContent(content);

        // timestamp
        item.setPublishedDate(messageDataTransferObject.getTimestamp());
        item.setUpdatedDate(messageDataTransferObject.getTimestamp());

        return item;
    }

    /**
     * Creates a <code>Message</code> data transfer object (<i>DTO</i>) from an {@link RSSItem RSSItem} or {@link
     * AdminMessage AdminMessage} value object (<i>VO</i>).
     *
     * @param valueObject the VO
     * @return a DTO constructed from the specified VO
     * @throws IllegalArgumentException if <code>valueObject</code> is <code>null</code>
     */
    public Message assembleMessageDTO(Object valueObject) {
        if (valueObject == null) {
            throw new IllegalArgumentException("the value object must not be null");
        }

        if (valueObject instanceof RSSItem) {
            Atom10Item item = new Atom10Item((RSSItem) valueObject);
            Atom10Content content = item.getContent();
            RSSText itemText = (content != null) ? content : item.getDescription();
            String contentType = itemText.getType();

            return new MessageImpl(item.getId(), item.getCategories()[0].getName(),
                    (contentType != null) ? contentType : "text",
                    itemText.getElementText(), item.getPublishedDate());
        } else if (valueObject instanceof AdminMessage) {
            AdminMessage message = (AdminMessage) valueObject;

            if (!message.check()) {
                throw new IllegalArgumentException("invalid message passed to assembleMessageDTO");
            }

            return createMessageFromAdminMessage(message);
        } else {
            throw new IllegalArgumentException("argument to assembleMessageDTO must be an AdminMessage or RSSItem");
        }
    }

    /**
     * Creates a new <code>MessageImpl</code> based on the specified <code>AdminMessage</code>.
     *
     * @param message the <code>AdminMessage</code> to convert
     * @return a new <code>MessageImpl</code> based on the specified <code>AdminMessage</code>
     */
    static Message createMessageFromAdminMessage(AdminMessage message) {
        return new MessageImpl((String) message.getParameterValue(AdminMessage.GUID),
                               (String) message.getParameterValue(AdminMessage.CATEGORY),
                               (String) message.getParameterValue(AdminMessage.CONTENT_TYPE),
                               (String) message.getParameterValue(AdminMessage.CONTENT),
                               (Date) message.getParameterValue(AdminMessage.TIMESTAMP));
    }
}
