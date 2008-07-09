/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.messaging;

import com.topcoder.util.errorhandling.ExceptionUtils;

import java.util.HashMap;
import java.util.Map;


/**
 * <p>
 * This class holds the error messages that will be used in MessageBoard class and in the
 * persistence implementation class. It defines five constants under which generic messages can be
 * registered in the map. Generic messages can also be registered in the map under different keys,
 * not defined as constants.
 * </p>
 * <p>
 * Thread safe: It is not thread safe because access to the errorMessages map is not
 * synchronized.
 * </p>
 *
 * @author DanLazar, yqw
 * @version 2.0
 */
public class ErrorMessagesCache {
    /**
     * The key under which the generic message used for invalid arguments is stored.
     */
    public static final String INVALID_ARGUMENT = "invalid_argument";

    /**
     * The key under which the generic message used to signal that a message is to big is stored.
     */
    public static final String MESSAGE_SIZE_EXCEEDED = "message_size_exceeded";

    /**
     * The key under which the generic message used for configuration errors is stored.
     */
    public static final String CONFIGURATION = "configuration";

    /**
     * The key under which the generic message used for persistence errors is stored.
     */
    public static final String PERSISTENCE = "persistence";

    /**
     * The key under which the generic message, used for the cases when an entity is not found, is
     * stored.
     */
    public static final String ENTITY_NOT_FOUND = "entity_not_found";

    /**
     * This map holds the error message. It cannot contain null or empty keys/values. It can be
     * empty. It will be initialized to an empty map at the point of definition. The reference
     * cannot be modified but the contents can be modified.
     */
    private static final Map<String, String> errorMessages = new HashMap<String, String>();

    /**
     * Empty constructor.
     */
    private ErrorMessagesCache() {
    }

    /**
     * Gets the error message registered under the given name.
     * @param name
     *            the name
     * @return the message registered under the given name or null
     * @throws IllegalArgumentException if name is null or empty string
     */
    public static String getErrorMessage(String name) {
        ExceptionUtils.checkNullOrEmpty(name, null, null, "The name should not be null or empty.");

        return errorMessages.get(name);
    }

    /**
     * Adds the given error message to the map. If a message is registered under the given name it
     * will be replaced.
     *
     * @param name
     *            the name
     * @param value
     *            the value
     * @throws IllegalArgumentException if args are null or empty string
     */
    public static void addErrorMessage(String name, String value) {
        ExceptionUtils.checkNullOrEmpty(name, null, null, "The name should not be null or empty.");
        ExceptionUtils.checkNullOrEmpty(value, null, null, "The value should not be null or empty.");
        errorMessages.put(name, value);
    }

    /**
     * Removes the error message stored under the given name.
     * @param name
     *            the name
     * @return the removed error message or null
     * @throws IllegalArgumentException if name is null or empty string
     */
    public static String removeErrorMessage(String name) {
        ExceptionUtils.checkNullOrEmpty(name, null, null, "The name should not be null or empty.");

        return errorMessages.remove(name);
    }

    /**
     * Clears the errorMessages map.
     */
    public static void clearErrorMessages() {
        errorMessages.clear();
    }
}
