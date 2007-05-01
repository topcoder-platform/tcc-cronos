/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.chat.session;

import java.util.Collection;
import java.util.Iterator;

import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.UnknownNamespaceException;

/**
 * <p>
 * Helper class for the chat contact manager component.
 * </p>
 *
 * <p>
 * Thread Safety : This class is immutable and so thread safe.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public final class Util {
    /**
     * <p>
     * This private constructor prevents to create a new instance.
     * </p>
     */
    private Util() {
        // empty
    }

    /**
     * <p>
     * Checks whether the given Object is null.
     * </p>
     *
     * @param arg the argument to check
     * @param name the name of the argument to check
     * @return the original object to check
     *
     * @throws IllegalArgumentException if the given Object is null
     */
    public static Object checkNull(Object arg, String name) {
        if (arg == null) {
            throw new IllegalArgumentException(name + "should not be null.");
        }

        return arg;
    }

    /**
     * <p>
     * Checks whether the given String is null or empty.
     * </p>
     *
     * @param arg the String to check
     * @param name the name of the String argument to check
     *
     * @throws IllegalArgumentException if the given string is null or empty
     */
    public static void checkString(String arg, String name) {
        checkNull(arg, name);

        if (arg.trim().length() == 0) {
            throw new IllegalArgumentException(name + " should not be empty.");
        }
    }

    /**
     * <p>
     * Returns the value of the property in the given namespace.
     * </p>
     *
     * <p>
     * If the given property is missing, then it will cause an exception.
     * </p>
     *
     * @param namespace the namespace to get the property value from.
     * @param propertyName the name of property
     *
     * @return the value of the property
     *
     * @throws ChatSessionConfigurationException if fail to load the config values.
     * @throws IllegalArgumentException if the given parameter is null or empty
     */
    public static String getString(String namespace, String propertyName) throws ChatSessionConfigurationException {
        checkString(namespace, "namespace");
        checkString(propertyName, "propertyName");
        try {
            String property = ConfigManager.getInstance().getString(namespace, propertyName);

            // Property is missed
            if (property == null) {
                throw new ChatSessionConfigurationException("Missed property: " + propertyName);
            }

            // Empty property value is not allowed
            if ((property.trim().length() == 0)) {
                throw new ChatSessionConfigurationException("The value for property: " + propertyName + " is empty.");
            }

            return property;
        } catch (UnknownNamespaceException e) {
            throw new ChatSessionConfigurationException(
                "UnknownNamespaceException occurs when accessing ConfigManager.", e);
        }
    }

    /**
     * <p>
     * Converts the collection to a long array.
     * </p>
     *
     * <p>
     * Note, the given collection contains only Long element.
     * </p>
     *
     * @param ids the <code>Collection</code> instance containing a collection of id in Long type
     *
     * @return the long array containing all the ids
     *
     * @throws IllegalArgumentException if ids is null
     */
    public static long[] toLongArray(Collection ids) {
        Util.checkNull(ids, "ids");

        long[] idsArray = new long[ids.size()];

        // for each element in the collection, converts to the corresponding element in the array
        Iterator it = ids.iterator();
        for (int i = 0; i < idsArray.length; i++) {
            idsArray[i] = ((Long) it.next()).longValue();
        }

        return idsArray;
    }
}
