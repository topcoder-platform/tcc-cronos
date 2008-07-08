/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.messaging;

import com.topcoder.util.errorhandling.ExceptionUtils;


/**
 * <p>
 * This class represents a message attribute. A Message instance can have many such attributes. A
 * message attribute has a name and a value.
 * </p>
 * <p>
 * Thread safe: This class is not thread safe because it is mutable.
 * </p>
 *
 * @author DanLazar, yqw
 * @version 2.0
 */
public class MessageAttribute {
    /**
     * The name of the attribute. It will be initialized in parameterized constructor. It has a
     * setter and a getter. It cannot be set to null or empty string.
     */
    private String name;

    /**
     * The value of the attribute. It will be initialized in parameterized constructor. It has a
     * setter and a getter. It cannot be set to null or empty string.
     */
    private String value;

    /**
     * Empty constructor, creates an instance of MessageAttribute.
     */
    public MessageAttribute() {
    }

    /**
     * Creates a new instance with name and value.
     *
     * @param name
     *            the name
     * @param value
     *            the value
     * @throws IllegalArgumentException
     *             if args are null or empty strings.
     */
    public MessageAttribute(String name, String value) {
        ExceptionUtils.checkNullOrEmpty(name, null, null, "The name should not be null or empty.");
        ExceptionUtils.checkNullOrEmpty(value, null, null, "The value should not be null or empty.");
        this.name = name;
        this.value = value;
    }

    /**
     * Getter for the name.
     *
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
     *             if name are null or empty strings.
     */
    public void setName(String name) {
        ExceptionUtils.checkNullOrEmpty(name, null, null, "The name should not be null or empty.");
        this.name = name;
    }

    /**
     * Getter for the value.
     *
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * Setter for the value.
     * @param value
     *            the value
     * @throws IllegalArgumentException
     *             if value are null or empty strings.
     */
    public void setValue(String value) {
        ExceptionUtils.checkNullOrEmpty(value, null, null, "The value should not be null or empty.");
        this.value = value;
    }
}
