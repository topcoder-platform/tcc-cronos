/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.client;

import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.UnknownNamespaceException;
import com.topcoder.util.objectfactory.InvalidClassSpecificationException;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.impl.ConfigManagerSpecificationFactory;
import com.topcoder.util.objectfactory.impl.IllegalReferenceException;
import com.topcoder.util.objectfactory.impl.SpecificationConfigurationException;


/**
 * <p>
 * A Helper class provides the argument validation.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public final class Helper {
    /**
     * Private constructor to prevent this class to be instantiated.
     */
    private Helper() {
        // empty constructor
    }

    /**
     * Checks whether the given Object is null.
     *
     * @param arg the argument to be checked
     * @param name the name of the argument
     *
     * @throws IllegalArgumentException if the given Object is null
     */
    public static void checkNull(Object arg, String name) {
        if (arg == null) {
            throw new IllegalArgumentException(name + " should not be null.");
        }
    }

    /**
     * Checks whether the given string is null or empty.
     *
     * @param str the string to be checked
     * @param name the name of the string
     *
     * @throws IllegalArgumentException if the given string is null or empty
     */
    public static void checkString(String str, String name) {
        checkNull(str, name);

        if (str.trim().length() == 0) {
            throw new IllegalArgumentException(name + "should not be empty");
        }
    }

    /**
     * Checks whether the given number is positive.
     *
     * @param number the number to be checked
     * @param name the name of the number
     *
     * @throws IllegalArgumentException if the given number is not positive
     */
    public static void checkPositive(long number, String name) {
        if (number <= 0) {
            throw new IllegalArgumentException(name + " should be positive.");
        }
    }

    /**
     * Check whether the given array is null or contain null element.
     *
     * @param array the array to check
     * @param name the name of the array
     *
     * @throws IllegalArgumentException if the array is null or contain null element
     */
    public static void checkArray(Object[] array, String name) {
        checkNull(array, "name");

        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) {
                throw new IllegalArgumentException("The " + i + " item of the array " + name + " is null.");
            }
        }
    }

    /**
     * Check whether the given array is null or contain null or empty element.
     *
     * @param array the array to check
     * @param name the name of the array
     *
     * @throws IllegalArgumentException if the array is null or contain null element
     */
    public static void checkArrayString(String[] array, String name) {
        checkNull(array, "name");

        for (int i = 0; i < array.length; i++) {
            if ((array[i] == null) || (array[i].trim().length() == 0)) {
                throw new IllegalArgumentException("The " + i + " item of the array " + name + " is null.");
            }
        }
    }
    /**
     * Check if the given array is a positive array.
     *
     * @param ids the array to check
     * @param name the name of the array
     *
     * @throws IllegalArgumentException if the array is null or contain non-positive element
     */
    public static void checkPositiveArray(long[] ids, String name) {
        checkNull(ids, name);

        for (int i = 0; i < ids.length; i++) {
            if (ids[i] <= 0) {
                throw new IllegalArgumentException("The [" + i + "] of the array is not positive");
            }
        }
    }

    /**
     * Get the ObjectFactory use the specified namespace.
     *
     * @param namespace the namespace used to get the ObjectFactory
     *
     * @return a ObjectFactory constructed from the given namespace.
     *
     * @throws NotificationConfigurationException if can not create the ObjectFactory from the namespace.
     */
    public static ObjectFactory getObjectFactory(String namespace)
        throws ConfigurationException {
        try {
            return new ObjectFactory(new ConfigManagerSpecificationFactory(namespace));
        } catch (IllegalReferenceException ire) {
            throw new ConfigurationException("Configuration is invalid somewhere.", ire);
        } catch (SpecificationConfigurationException sce) {
            throw new ConfigurationException("the error occurs when getting from ConfigManager.", sce);
        }
    }

    /**
     * Get the Object through ObjectFactory.
     *
     * @param factory the ObjectFactory to get the object
     * @param key the key for the Object
     *
     * @return the Object
     *
     * @throws NotificationConfigurationException if any error occurred when get the object from the object factory
     */
    public static Object createObjectViaObjectFactory(ObjectFactory factory, String key)
        throws ConfigurationException {
        try {
            return factory.createObject(key);
        } catch (InvalidClassSpecificationException e) {
            throw new ConfigurationException("Error occurred when creating DBConnectionFactory.", e);
        }
    }

    /**
     * Get the property specified from the specified namespace.
     *
     * @param namespace namespace to retrieve from
     * @param key to retrieve
     * @param required indicate if the property is required
     *
     * @return a String value of the property specified by namespace and key
     *
     * @throws ConfigurationException if can not get the config string and the required is true
     */
    public static String getConfigString(String namespace, String key, boolean required)
        throws ConfigurationException {
        String value = null;

        try {
            value = ConfigManager.getInstance().getString(namespace, key);
        } catch (UnknownNamespaceException une) {
            throw new ConfigurationException("The namespace '" + namespace + "' is unknown.", une);
        }

        if (value == null) {
            if (required) {
                throw new ConfigurationException("The '" + key + "' property does not exist in '" + namespace
                    + "' namespace.");
            }
        } else {
            checkConfigString(key, value);
        }

        return value;
    }

    /**
     * Check whether the value is empty.
     *
     * @param key the key of value
     * @param value the value to be checked
     *
     * @throws NotificationConfigurationException if the value is empty
     */
    private static void checkConfigString(String key, String value)
        throws ConfigurationException {
        if (value.trim().length() == 0) {
            throw new ConfigurationException("The '" + key + "' property is empty.");
        }
    }
}
