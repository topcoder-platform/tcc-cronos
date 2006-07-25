/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.scorecard;

import java.lang.reflect.InvocationTargetException;

import java.util.Iterator;
import java.util.List;


/**
 * <p>
 * Defines helper methods used in this component.
 * </p>
 *
 * @author tuenm, TCSDEVELOPER
 * @version 1.0
 */
public final class ManagerHelper {
    /**
     * <p>
     * Creates a new instance of <code>ManagerHelper</code> class.
     * </p>
     *
     * <p>
     * This private constructor prevents the creation of a new instance.
     * </p>
     */
    private ManagerHelper() {
    }

    /**
     * <p>
     * Validates the value of a variable. The value cannot be <code>null</code>.
     * </p>
     *
     * @param value the argument to validate
     * @param name the name of the argument
     *
     * @throws IllegalArgumentException if the argument is null
     */
    public static void validateNotNull(Object value, String name) {
        if (value == null) {
            throw new IllegalArgumentException(name + " should not be null.");
        }
    }

    /**
     * <p>
     * Validates the value of a variable. The value cannot be <code>null</code> or empty string.
     * </p>
     *
     * @param value the argument to validate
     * @param name the name of the argument
     *
     * @throws IllegalArgumentException if the argument is null or empty string
     */
    public static void validateNotNullOrEmpty(String value, String name) {
        validateNotNull(value, name);

        if (value.trim().length() == 0) {
            throw new IllegalArgumentException(name + " should not be empty string.");
        }
    }

    /**
     * <p>
     * Validates an object array. The array cannot be <code>null</code> or empty (if allowEmpty is false), any element
     * of it cannot be null.
     * </p>
     *
     * @param array the object array to validate
     * @param name the name of the object array
     * @param allowEmpty the flag indicating whether empty array is allowed
     *
     * @throws IllegalArgumentException if the argument is null or empty (if allowEmpty is false), or any element of it
     *         is null
     */
    public static void validateObjectArray(Object[] array, String name, boolean allowEmpty) {
        validateNotNull(array, name);

        if (!allowEmpty && (array.length == 0)) {
            throw new IllegalArgumentException(name + " should not be empty.");
        }

        for (int i = 0; i < array.length; i++) {
            validateNotNull(array[i], name + "[" + i + "]");
        }
    }

    /**
     * <p>
     * Validates an object array. The array cannot be <code>null</code> or empty (if allowEmpty is false), any element
     * of it cannot be null.
     * </p>
     *
     * @param array the object array to validate
     * @param name the name of the object array
     * @param allowEmpty the flag indicating whether empty array is allowed
     *
     * @throws IllegalArgumentException if the argument is null or empty (if allowEmpty is false), or any element of it
     *         is null
     */
    public static void validateLongArray(long[] array, String name, boolean allowEmpty) {
        validateNotNull(array, name);

        if (!allowEmpty && (array.length == 0)) {
            throw new IllegalArgumentException(name + " should not be empty.");
        }

        for (int i = 0; i < array.length; i++) {
            validatePositive(array[i], name + "[" + i + "]");
        }
    }

    /**
     * <p>
     * Validates an object array. The array cannot be <code>null</code> or empty (if allowEmpty is false), any element
     * of it cannot be null.
     * </p>
     *
     * @param array the object array to validate
     * @param name the name of the object array
     * @param allowEmpty the flag indicating whether empty array is allowed
     *
     * @throws IllegalArgumentException if the argument is null or empty (if allowEmpty is false), or any element of it
     *         is null
     */
    public static void validateLongArray(int[] array, String name, boolean allowEmpty) {
        validateNotNull(array, name);

        if (!allowEmpty && (array.length == 0)) {
            throw new IllegalArgumentException(name + " should not be empty.");
        }

        for (int i = 0; i < array.length; i++) {
            validatePositive(array[i], name + "[" + i + "]");
        }
    }

    /**
     * <p>
     * Validates an object array. The array cannot be <code>null</code> or empty (if allowEmpty is false), any element
     * of it cannot be null.
     * </p>
     *
     * @param list the object array to validate
     * @param name the name of the object array
     * @param allowEmpty the flag indicating whether empty array is allowed
     *
     * @throws IllegalArgumentException if the argument is null or empty (if allowEmpty is false), or any element of it
     *         is null
     */
    public static void validateLongList(List list, String name, boolean allowEmpty) {
        validateNotNull(list, name);

        if (!allowEmpty && (list.size() == 0)) {
            throw new IllegalArgumentException(name + " should not be empty.");
        }

        Iterator iter = list.iterator();

        while (iter.hasNext()) {
            validatePositive(((Long) iter.next()).longValue(), "A member in " + name);
        }
    }

    /**
     * <p>
     * Validates the value of a variable. The value should be positive.
     * </p>
     *
     * @param value the argument to validate.
     * @param name the name of the argument.
     *
     * @throws IllegalArgumentException if the argument is not positive.
     */
    public static void validatePositive(long value, String name) {
        if (value <= 0) {
            throw new IllegalArgumentException(name + " should be positive.");
        }
    }

    /**
     * <p>
     * Validates a string array. The array cannot be <code>null</code> or empty (if allowEmpty is false), any element
     * of it cannot be null or empty string.
     * </p>
     *
     * @param array the string array to validate
     * @param name the name of the string array
     * @param allowEmpty the flag indicating whether empty array is allowed
     *
     * @throws IllegalArgumentException if the argument is null or empty (if allowEmpty is false), or any element of it
     *         is null or empty string
     */
    public static void validateStringArray(String[] array, String name, boolean allowEmpty) {
        validateNotNull(array, name);

        if (!allowEmpty && (array.length == 0)) {
            throw new IllegalArgumentException(name + " should not be empty.");
        }

        for (int i = 0; i < array.length; i++) {
            validateNotNullOrEmpty(array[i], name + "[" + i + "]");
        }
    }

    /**
     * <p>
     * Validates a string array. The array cannot be <code>null</code> or empty (if allowEmpty is false), any element
     * of it cannot be null or empty string.
     * </p>
     *
     * @param list the string array to validate
     * @param name the name of the string array
     * @param allowEmpty the flag indicating whether empty array is allowed
     *
     * @throws IllegalArgumentException if the argument is null or empty (if allowEmpty is false), or any element of it
     *         is null or empty string
     */
    public static void validateStringList(List list, String name, boolean allowEmpty) {
        validateNotNull(list, name);

        if (!allowEmpty && (list.size() == 0)) {
            throw new IllegalArgumentException(name + " should not be empty.");
        }

        Iterator iter = list.iterator();

        while (iter.hasNext()) {
            validateNotNullOrEmpty((String) iter.next(), "A member in " + name);
        }
    }

    /**
     * <p>
     * Creates an <code>Object</code> according the given class name and namespace.
     * </p>
     *
     * @param className the class name of the <code>Object</code>
     * @param namespace the namespace to create <code>Object</code> instance
     *
     * @return the created <code>DBConnectionFactory</code> instance
     *
     * @throws ConfigurationException if any error occurs when creating the <code>Object</code>
     */
    public static Object createInstance(String className, String namespace)
        throws ConfigurationException {
        try {
            return Class.forName(className).getConstructor(new Class[] {String.class}).newInstance(new Object[] {
                    namespace
                });
        } catch (IllegalArgumentException e) {
            throw new ConfigurationException("Illegal argument error occurs when create instance.", e);
        } catch (SecurityException e) {
            throw new ConfigurationException("Security error occurs when create instance.", e);
        } catch (InstantiationException e) {
            throw new ConfigurationException("Instantiation error occurs when create instance.", e);
        } catch (IllegalAccessException e) {
            throw new ConfigurationException("Illegal access error occurs when create instance.", e);
        } catch (InvocationTargetException e) {
            throw new ConfigurationException("Invocation target error occurs when create instance.", e);
        } catch (NoSuchMethodException e) {
            throw new ConfigurationException("No such method error occurs when create instance.", e);
        } catch (ClassNotFoundException e) {
            throw new ConfigurationException("Class not found error occurs when create instance.", e);
        }
    }
}
