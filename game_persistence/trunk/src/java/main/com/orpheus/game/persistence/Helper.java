/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.persistence;

import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.UnknownNamespaceException;

import java.util.Collection;
import java.util.Iterator;


/**
 * This class defines all the helper methods used in the base implementation classes of this component.
 *@author waits
 *@version 1.0
 */
public final class Helper {
    /** Represents the max length used to check String arguments. */
    private static final int MAX_LENGTH = 256;

    /**
     * <p>
     * The private constructor which prevents instantiation.
     * </p>
     */
    private Helper() {
        // empty
    }

    /**
     * <p>
     * Checks whether the given object is null.
     * </p>
     *
     * @param obj the object to check
     * @param name the name of the object
     *
     * @throws IllegalArgumentException if <code>obj</code> is null
     */
    public static void checkNotNull(Object obj, String name) {
        if (obj == null) {
            throw new IllegalArgumentException(name + " should not be null.");
        }
    }

    /**
     * <p>
     * Checks whether the given string is null or empty.
     * </p>
     *
     * @param str the string to check
     * @param name the name of the string
     *
     * @throws IllegalArgumentException if <code>str</code> is null or empty
     */
    public static void checkNotNullOrEmpty(String str, String name) {
        checkNotNull(str, name);

        if (str.trim().length() == 0) {
            throw new IllegalArgumentException(name + " should not be empty.");
        }
    }

    /**
     * <p>
     * Checks whether the given long value is not positive.
     * </p>
     *
     * @param value the long value to check
     * @param name the name of the value
     *
     * @throws IllegalArgumentException if <code>value</code> is not positive
     */
    public static void checkNotPositive(long value, String name) {
        if (value <= 0) {
            throw new IllegalArgumentException(name + " should be positive.");
        }
    }

    /**
     * <p>
     * Checks whether the given int value is negative.
     * </p>
     *
     * @param value the int value to check
     * @param name the name of the value
     *
     * @throws IllegalArgumentException if <code>value</code> is negative
     */
    public static void checkNegative(int value, String name) {
        if (value < 0) {
            throw new IllegalArgumentException(name + " should not be negative.");
        }
    }


    /**
     * <p>
     * Checks whether the object array is null or contains null element.
     * </p>
     *
     * @param array the object array to check
     * @param name the name of the object array
     *
     * @throws IllegalArgumentException if <code>array</code> is null or contains null element
     */
    public static void checkNotNullOrContainNullElement(Object[] array, String name) {
        checkNotNull(array, name);

        for (int i = 0; i < array.length; i++) {
            checkNotNull(array[i], name);
        }
    }


    /**
     * <p>
     * Load configuration value corresponding to the configuration key from the cofiguration file.
     * </p>
     * 
     * <p>
     * Serve as reusable code fragment.
     * </p>
     *
     * @param namespace the namespace
     * @param key configuration key in configuration file
     * @param required true if the property is required, false otherwise otherwise
     *
     * @return configuration value loaded from the configuration
     *
     * @throws InstantiationException if fail to load configuration values
     */
    public static String getString(String namespace, String key, boolean required)
        throws InstantiationException {
        ConfigManager m = ConfigManager.getInstance();

        try {
            String value = m.getString(namespace, key);

            if (value == null) {
                if (required) {
                    throw new InstantiationException("Property " + key + " is required.");
                }
            } else {
                if (value.trim().length() == 0) {
                    throw new InstantiationException("Property " + key + " should not be empty.");
                }
            }

            return value;
        } catch (UnknownNamespaceException une) {
            throw new InstantiationException("Namespace " + namespace + " is unknown.", une);
        }
    }

    /**
     * Return the int value of the property.
     *
     * @param namespace the namespace to get
     * @param propertyName the name of property
     * @param required whether this property is required
     *
     * @return the int value of the property
     *
     * @throws InstantiationException if fail to load the config values
     */
    public static int getInt(String namespace, String propertyName, boolean required)
        throws InstantiationException {
        try {
            return Integer.parseInt(getString(namespace, propertyName, required).trim());
        } catch (NumberFormatException e) {
            throw new InstantiationException("The property :" + propertyName + "'s value should be of integer.");
        }
    }
}
