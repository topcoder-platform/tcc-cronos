/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.messaging;

import java.util.Date;


/**
 * <p>
 * This class represents a response to a message. It extends CommonEntityData abstract class without
 * adding any new fields.
 * </p>
 * <p>
 * Thread safe: This class is not thread safe because it is mutable (the abstract class is
 * mutable).
 * </p>
 *
 * @author DanLazar, yqw
 * @version 2.0
 */
public class Response extends CommonEntityData {
    /**
     * Default constructor. Creates an instance of Response.
     */
    public Response() {
    }

    /**
     * Creates a new instance with name, date and message.
     *
     * @param message
     *            the message
     * @param name
     *            the name
     * @param date
     *            the value
     * @throws IllegalArgumentException
     *             if any arg is null, or if name or message are empty strings.
     */
    public Response(String name, Date date, String message) {
        super(name, date, message);
    }

    /**
     * Creates a new instance with id, name, date and message.
     *
     * @param message
     *            the message
     * @param id
     *            the id
     * @param name
     *            the name
     * @param date
     *            the date
     * @throws IllegalArgumentException
     *             if any arg is null, or if name or message are empty strings or id < 0.
     */
    public Response(long id, String name, Date date, String message) {
        super(id, name, date, message);
    }
}
