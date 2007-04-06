/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.notification;

import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.UnknownNamespaceException;
import com.topcoder.util.objectfactory.InvalidClassSpecificationException;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.impl.ConfigManagerSpecificationFactory;
import com.topcoder.util.objectfactory.impl.IllegalReferenceException;
import com.topcoder.util.objectfactory.impl.SpecificationConfigurationException;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


/**
 * <p>
 * A Helper class provides the argument validation and reading config.
 * </p>
 *
 * @author kzhu
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
     * Check whether the given long array is valid, which means it's not null, does not contain non-positive element
     * and each element is unique.
     *
     * @param numbers the number array to be checked.
     * @param name the name of the array
     *
     * @throws IllegalArgumentException if the given array is not valid
     */
    public static void checkUniqueArray(long[] numbers, String name) {
        checkNull(numbers, "numbers");

        // map used to check unique
        Set set = new HashSet();

        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i] <= 0) {
                throw new IllegalArgumentException("The " + i + " element of the array should be positive.");
            }

            // and current element to the map
            Long currentLong = new Long(numbers[i]);

            // if some number the same as current one is already in the list.
            if (set.contains(currentLong)) {
                throw new IllegalArgumentException("The array " + name + " contains duplicate elements.");
            }

            // add current number to the list.
            set.add(currentLong);
        }
    }

    /**
     * Check whether the map is valid. Valid map should contain non-null and non-empty String for key and value.
     *
     * @param map the map to check
     * @param name the name of the map
     *
     * @throws IllegalArgumentException if the map is not valid.
     */
    public static void checkMap(Map map, String name) {
        // make sure the map is not null.
        checkNull(map, name);

        // check each element of the map
        for (Iterator it = map.entrySet().iterator(); it.hasNext();) {
            Map.Entry entry = (Map.Entry) it.next();

            Object key = entry.getKey();
            Object value = entry.getValue();

            if (!(key instanceof String)) {
                throw new IllegalArgumentException(name + " contains non-string key.");
            }

            checkString((String) key, "The key of Map " + name + " is invalid.");

            if (!(value instanceof String)) {
                throw new IllegalArgumentException(name + " contains non-string value.");
            }

            checkString((String) value, "The value of Map " + name + " is invalid.");
        }
    }

    /**
     * Check whether the map contain specified keys, the passed in map should be not null and all key value pairs are
     * from non-empty string to non-empty string.
     *
     * @param map the map to check
     * @param keys the keys to check
     * @param name the name of the map
     *
     * @throws IllegalArgumentException if the map does not contain all the keys in the array
     */
    public static void checkMapContainKeys(Map map, String[] keys, String name) {
        for (int i = 0; i < keys.length; i++) {
            if (!map.containsKey(keys[i])) {
                throw new IllegalArgumentException("The map " + name + "does not contain " + keys[i]);
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
        throws NotificationConfigurationException {
        try {
            return new ObjectFactory(new ConfigManagerSpecificationFactory(namespace));
        } catch (IllegalReferenceException ire) {
            throw new NotificationConfigurationException("Configuration is invalid somewhere.", ire);
        } catch (SpecificationConfigurationException sce) {
            throw new NotificationConfigurationException("the error occurs when getting from ConfigManager.", sce);
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
        throws NotificationConfigurationException {
        try {
            return factory.createObject(key);
        } catch (InvalidClassSpecificationException e) {
            throw new NotificationConfigurationException("Error occurred when creating DBConnectionFactory.", e);
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
     * @throws NotificationConfigurationException if can not get the config string and the required is true
     */
    public static String getConfigString(String namespace, String key, boolean required)
        throws NotificationConfigurationException {
        String value = null;

        try {
            value = ConfigManager.getInstance().getString(namespace, key);
        } catch (UnknownNamespaceException une) {
            throw new NotificationConfigurationException("The namespace '" + namespace + "' is unknown.", une);
        }

        if (value == null) {
            if (required) {
                throw new NotificationConfigurationException("The '" + key + "' property does not exist in '"
                    + namespace + "' namespace.");
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
        throws NotificationConfigurationException {
        if (value.trim().length() == 0) {
            throw new NotificationConfigurationException("The '" + key + "' property is empty.");
        }
    }
}
