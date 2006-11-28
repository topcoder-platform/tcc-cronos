/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.administration.persistence;

import java.io.Serializable;

import java.util.Date;

/**
 * <p>Interface specifying the message data transfer object (<i>DTO</i>). DTOs are serializable wrappers that are
 * used to communicate between the client and DAO layers. The end user of the EJB layer will not manipulate these
 * objects directly.
 *
 * <p><strong>Thread Safety</strong>: Implementations are required to be thread safe.
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */

public interface Message extends Serializable {
    /**
     * Returns the GUID uniquely identifying this message.
     *
     * @return the GUID uniquely identifying this message
     */
    public String getGuid();

    /**
     * Returns the category of this message.
     *
     * @return the category of this message
     */
    public String getCategory();

    /**
     * Returns the content type.
     *
     * @return the content type
     */
    public String getContentType();

    /**
     * Returns the message content.
     *
     * @return the message content
     */
    public String getContent();

    /**
     * Returns the message timestamp.
     *
     * @return the message timestamp
     */
    public Date getTimestamp();
}
