/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.cronos.im.messenger;

import java.util.Iterator;
import java.util.Map;


/**
 * <p>
 * Defines helper methods used in this component.
 * </p>
 * <p>
 * <b>Thread safety</b>: This class is thread safe because it has no state (it contains only static methods).
 * </p>
 *
 * @author marius_neo
 * @version 1.0
 */
public class Helper {

    /**
     * <p>
     * Creates a new instance of <c>Helper</c> class.
     * </p>
     * <p>
     * This private constructor prevents the creation of a new instance of this class.
     * </p>
     */
    private Helper() {
    }

    /**
     * Validates the value of a variable. The value cannot be <c>null</c>.
     *
     * @param value The value of the variable to be validated.
     * @param name  The name of the variable to be validated.
     * @throws IllegalArgumentException If the value of the variable is <c>null</c>.
     */
    public static void validateNotNull(Object value, String name) {
        if (value == null) {
            throw new IllegalArgumentException(name + " cannot be null.");
        }
    }


    /**
     * Validates the value of a string variable. The value cannot be <c>null</c>
     * or empty/blank string.
     *
     * @param value The value of the variable to be validated.
     * @param name  The name of the variable to be validated.
     * @throws IllegalArgumentException If the value of the variable is <c>null</c>
     *                                  or he value of the variable is empty string.
     */
    public static void validateNotNullOrEmpty(String value, String name) {
        validateNotNull(value, name);

        if (value.trim().length() == 0) {
            throw new IllegalArgumentException(name + " cannot be empty string.");
        }
    }

    /**
     * Validates the value of the property specified to be non-null and non-empty.
     *
     * @param propName  The name of the property.
     * @param value     The value of the property.
     * @param namespace The namespace in which is found the property.
     * @throws FormatterConfigurationException
     *          If the value of the property is null or empty/blank string.
     */
    public static void validatePropertyValue(String propName, String value
        , String namespace) throws FormatterConfigurationException {
        if (value == null) {
            throw new FormatterConfigurationException(namespace
                + " namespace does not contain '" + propName + "' property");
        }
        if (value.trim().length() == 0) {
            throw new FormatterConfigurationException(namespace
                + " contains an empty string value for '" + propName + "' property");
        }
    }

    /**
     * Validates a map containing profile properties. The dictionary must not be empty.
     * The keys of the map must be non-null and non-empty strings.
     * Tje values of the map must be non-null and non-empty string arrays.
     * If any of these conditions are not respected an exception of type
     * <c>IllegalArgumentException</c> will be thrown.
     *
     * @param properties The profile properties map to be validated.
     * @param name       The name for the variable to be validated.
     * @throws IllegalArgumentException If the map is empty
     *                                  or it contains null/non-string keys or null/non String[] values
     *                                  or empty string keys or empty String[] values
     */
    public static void validateProfileProperties(Map properties, String name) {
        Helper.validateNotNull(properties, name);
        if (properties.isEmpty()) {
            throw new IllegalArgumentException(name + " map must not be empty");
        }
        for (Iterator i = properties.entrySet().iterator(); i.hasNext();) {
            Map.Entry propEntry = (Map.Entry) i.next();
            Object propKey = propEntry.getKey();
            Object propValues = propEntry.getValue();
            if (!(propKey instanceof String)) {
                throw new IllegalArgumentException(name + " map contains non-string or null keys");
            }
            if (((String) propKey).trim().length() == 0) {
                throw new IllegalArgumentException(name + "map contains empty string keys");
            }

            if (!(propValues instanceof String[])) {
                throw new IllegalArgumentException(name + "map contains non String[] values");
            }
            if (((String[]) propValues).length == 0) {
                throw new IllegalArgumentException(name + " map contains empty String[] values");
            }
        }
    }
}