/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.messaging;

import com.topcoder.util.errorhandling.ExceptionUtils;

import java.util.Date;


/**
 * <p>
 * This abstract class holds the common fields of the Response and Message classes. It extends from
 * BasicEntityData.The common fields of these two classes are the name, the date of posting and the
 * message.
 * </p>
 * <p>
 * Thread safe: This class it is not thread safe because it is mutable.
 * </p>
 *
 * @author DanLazar, yqw
 * @version 2.0
 */
public abstract class CommonEntityData extends BasicEntityData {
    /**
     * The name. It will be initialized in parameterized constructors. It has a setter and a getter.
     * It cannot be set to null or empty string.
     */
    private String name;

    /**
     * The date of posting. It will be initialized in parameterized constructors. It has a setter
     * and a getter. It cannot be set to null
     */
    private Date date;

    /**
     * The message. It will be initialized in parameterized constructors. It has a setter and a
     * getter. It cannot be set to null or empty string.
     */
    private String message;

    /**
     * Default constructor. Creates an instance of CommonEntityData.
     */
    protected CommonEntityData() {
        super();
    }

    /**
     * Creates a new instance whit name, date and message.
     *
     * @param message
     *            the message
     * @param name
     *            the name
     * @param date
     *            the date
     * @throws IllegalArgumentException
     *             if any arg is null, or if name or message are empty strings.
     */
    protected CommonEntityData(String name, Date date, String message) {
        ExceptionUtils.checkNullOrEmpty(name, null, null, "The name should not be null or empty.");
        ExceptionUtils.checkNull(date, null, null, "The date should not be null.");
        ExceptionUtils.checkNullOrEmpty(message, null, null, "The message should not be null or empty.");
        this.name = name;
        this.date = date;
        this.message = message;
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
     *             if any arg is null, or if name or message are empty strings or if id<0.
     */
    protected CommonEntityData(long id, String name, Date date, String message) {
        super(id);
        ExceptionUtils.checkNullOrEmpty(name, null, null, "The name should not be null or empty.");
        ExceptionUtils.checkNull(date, null, null, "The date should not be null.");
        ExceptionUtils.checkNullOrEmpty(message, null, null, "The message should not be null or empty.");
        this.name = name;
        this.date = date;
        this.message = message;
    }

    /**
     * Getter for the name of this common entity data.
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for the name.
     *
     * @param name
     *            the name
     * @throws IllegalArgumentException
     *             if name is null or empty string.
     */
    public void setName(String name) {
        ExceptionUtils.checkNullOrEmpty(name, null, null, "The name should not be null or empty.");
        this.name = name;
    }

    /**
     * Getter for the date.
     *
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Setter for the date.
     *
     * @param date
     *            the date
     * @throws IllegalArgumentException
     *             if date is null.
     */
    public void setDate(Date date) {
        ExceptionUtils.checkNull(date, null, null, "The date should not be null.");
        this.date = date;
    }

    /**
     * Getter for the message.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Setter for the message.
     *
     * @param message
     *            the message
     * @throws IllegalArgumentException
     *             if message is null or empty string
     */
    public void setMessage(String message) {
        ExceptionUtils.checkNullOrEmpty(message, null, null, "The message should not be null or empty.");
        this.message = message;
    }
}
