/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.messaging;

import com.topcoder.util.errorhandling.ExceptionUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * <p>
 * This class represents a message that is posted on a thread. It extends from CommonEntityData. In
 * addition it has a map of attributes and a response.
 * <p>
 * <p>
 * Thread safe: This class is not thread safe because it is mutable.
 * </p>
 *
 * @author DanLazar, yqw
 * @version 2.0
 */
public class Message extends CommonEntityData {
    /**
     * The attributes of the message. It cannot be null after initialization but can be empty. The
     * keys cannot be null or empty string and values cannot be null. It will be initialized in
     * constructors. The reference is final but the contents can change in several methods.
     */
    private final Map<String, MessageAttribute> attributes;

    /**
     * Represents the response to the message. It will be initialized in two parameterized
     * constructors. It has a setter and a getter. It cannot be set to null.
     */
    private Response response;

    /**
     * Creates a new instance.
     */
    public Message() {
        attributes = new HashMap<String, MessageAttribute>();
    }

    /**
     * Creates a new instance with name data and message.
     *
     * @param message
     *            the message
     * @param name
     *            the name
     * @param date
     *            the date
     * @throws IllegalArgumentException
     *             if any arg is null, or if name or message are empty strings
     */
    public Message(String name, Date date, String message) {
        super(name, date, message);
        attributes = new HashMap<String, MessageAttribute>();
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
     *             if any arg is null, or if name or message are empty strings or id<0
     */
    public Message(long id, String name, Date date, String message) {
        super(id, name, date, message);
        attributes = new HashMap<String, MessageAttribute>();
    }

    /**
     * Creates a new instance with name, date message, attributes and response.
     *
     * @param response
     *            the response
     * @param message
     *            the message
     * @param name
     *            the name
     * @param attributes
     *            the attributes
     * @param date
     *            the date
     * @throws IllegalArgumentException
     *             if any arg is null, or if name or message are empty strings or if attributes
     *             contains null or empty keys, or if it contains null values or if it is empty.
     */
    public Message(String name, Date date, String message, Map<String, MessageAttribute> attributes, Response response) {
        super(name, date, message);
        checkMap(attributes);

        this.attributes = attributes;
        ExceptionUtils.checkNull(response, null, null, "The response should not be null.");
        this.response = response;
    }
    /**
     * check the map if it is null or empty, it has null or empty keys, or it has null values.
     * It will throw IllegalArgumentException.
     * @param attributes the map to check.
     * @throws IllegalArgumentException if the map is invalid.
     */
    private void checkMap(Map<String, MessageAttribute> attributes){
        ExceptionUtils.checkNull(attributes, null, null, "The attributes should not be null.");

        if (attributes.isEmpty()) {
            throw new IllegalArgumentException("The attributes map should not be empty.");
        }

        for (String key : attributes.keySet()) {
            if (key == null) {
                throw new IllegalArgumentException("The attributes map should not contains null key.");
            }

            if (key.trim().length() == 0) {
                throw new IllegalArgumentException("The attributes map should not contains empty key.");
            }

            if (attributes.get(key) == null) {
                throw new IllegalArgumentException("The attributes map should not contains null value.");
            }
        }
    }

    /**
     * Creates a new instance with id, name, date message, attributes and response.
     *
     * @param response
     *            the response
     * @param message
     *            the message
     * @param id
     *            the id
     * @param name
     *            the name
     * @param attributes
     *            the attributes
     * @param date
     *            the date
     * @throws IllegalArgumentException
     *             if any arg is null, or if name or message are empty strings or id < 0 or if
     *             attributes contains null or empty keys, or if it contains null values or if it is
     *             empty.
     *
     */
    public Message(long id, String name, Date date, String message, Map<String, MessageAttribute> attributes,
        Response response) {
        super(id, name, date, message);
        checkMap(attributes);

        this.attributes = attributes;
        ExceptionUtils.checkNull(response, null, null, "The response should not be null.");
        this.response = response;
    }

    /**
     * Adds an attribute. If an attribute with the given name exists then it will be replaced.
     *
     * @param attribute
     *            the attribute
     * @throws IllegalArgumentException
     *             if arg is null
     */
    public void addAttribute(MessageAttribute attribute) {
        ExceptionUtils.checkNull(attribute, null, null, "The attribute to add should not be null.");
        attributes.put(attribute.getName(), attribute);
    }

    /**
     * Set the attributes.
     *
     * @param attributes
     *            the attributes
     * @throws IllegalArgumentException
     *             if arg is null or if it contains null or empty keys or null values or if it is
     *             empty.
     */
    public void setAttributes(Map<String, MessageAttribute> attributes) {
        ExceptionUtils.checkNull(attributes, null, null, "The attributes map to set should not be null.");

        if (attributes.isEmpty()) {
            throw new IllegalArgumentException("The attributes map should not be empty.");
        }

        for (String name : attributes.keySet()) {
            ExceptionUtils.checkNullOrEmpty(name, null, null, "The attributes map contains null or empty key.");
            ExceptionUtils.checkNull(attributes.get(name), null, null, "The attributes map contains null value.");
        }

        this.attributes.clear();
        this.attributes.putAll(attributes);
    }

    /**
     * Removes the attribute with the given name.
     *
     * @param name
     *            the name
     * @return the removed MessageAttribute or null if there was not such an attribute in the map.
     * @throws IllegalArgumentException
     *             if if arg is null or empty string.
     */
    public MessageAttribute removeAttribute(String name) {
        ExceptionUtils.checkNullOrEmpty(name, null, null,
            "The name of the  MessageAttribute to remove is null or emtpy.");

        return attributes.remove(name);
    }

    /**
     * Clears the attributes map.
     */
    public void clearAttributes() {
        attributes.clear();
    }

    /**
     * Gets the attribute stored under the given name.
     *
     * @param name
     *            the name
     * @return a MessageAttribute instance or null if there is no attribute stored under the given
     *         name
     * @throws IllegalArgumentException
     *             if if arg is null or empty string.
     */
    public MessageAttribute getAttribute(String name) {
        ExceptionUtils.checkNullOrEmpty(name, null, null, "The name of the  MessageAttribute is null or emtpy.");

        return attributes.get(name);
    }

    /**
     * Gets all attributes.
     *
     * @return a shallow copy of the map
     */
    public Map<String, MessageAttribute> getAllAttributes() {
        return new HashMap<String, MessageAttribute>(attributes);
    }

    /**
     * Checks if an attribute is registered under the given name.
     *
     * @param name
     *            the name
     * @return true if an attribute is registered under name key
     * @throws IllegalArgumentException
     *             if if arg is null or empty string.
     */
    public boolean containsAttribute(String name) {
        ExceptionUtils.checkNullOrEmpty(name, null, null, "The name of the  MessageAttribute is null or emtpy.");

        return attributes.containsKey(name);
    }

    /**
     * Getter for the response.
     *
     * @return the response
     */
    public Response getResponse() {
        return response;
    }

    /**
     * Setter for the response.
     *
     * @param response
     *            the response
     * @throws IllegalArgumentException
     *             if if response is null.
     */
    public void setResponse(Response response) {
        ExceptionUtils.checkNull(response, null, null, "The response should not be null.");
        this.response = response;
    }
}
