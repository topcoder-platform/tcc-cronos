/*
 * Copyright (C) 2006 - 2010 TopCoder Inc., All Rights Reserved.
 */



package com.cronos.onlinereview.login;

import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.UnknownNamespaceException;

import java.lang.reflect.InvocationTargetException;

/**
 * Utility class for Online Review Login Component.
 * <p>
 * It defines some common routines used by this component. It includes methods to get property value from
 * <code>ConfigManager</code>, as well as methods to validate parameters.
 * </p>
 * <p>
 * Changes in 1.1: Added a method creatObject for creating object using reflectivity.
 * </p>
 *
 * @author woodjohn, maone, TCSDEVELOPER
 * @version 1.1
 * @since 1.0
 */
public final class Util {

    /**
     * Represents the key of forwardUrl value.
     * <p>
     * It can be used to retrieve forward url value from <code>ActionForm</code> and <code>Principal</code>.
     * </p>
     */
    public static final String FOWARD_URL = "forwardUrl";

    /**
     * Represents the key of password value.
     * <p>
     * It can be used to retrieve password value from <code>ActionForm</code> and <code>Principal</code>.
     * </p>
     */
    public static final String PASSWORD = "password";

    /**
     * Represents the key of user name value.
     * <p>
     * It can be used to retrieve user name value from <code>ActionForm</code> and <code>Principal</code>.
     * </p>
     */
    public static final String USERNAME = "userName";

    /**
     * Private constructor.
     */
    private Util() {

        // emtpy
    }

    /**
     * Get optional property value for given <code>key</code> from the <code>namespace</code>.
     *
     * @param namespace
     *            the namespace to get configuration
     * @param key
     *            the key of desired value
     * @return the desired property value, or null if the value doesn't exist
     * @throws ConfigurationException
     *             if failed to get the value
     */
    public static String getOptionalPropertyString(String namespace, String key) throws ConfigurationException {
        try {
            return ConfigManager.getInstance().getString(namespace, key);
        } catch (UnknownNamespaceException e) {
            throw new ConfigurationException("Failed to get configuration value.", e);
        }
    }

    /**
     * Get required property value for given <code>key</code> from the <code>namespace</code>.
     *
     * @param namespace
     *            the namespace to get configuration
     * @param key
     *            the key of desired value
     * @return the desired property value
     * @throws ConfigurationException
     *             if failed to get the value
     */
    public static String getRequiredPropertyString(String namespace, String key) throws ConfigurationException {
        String val = getOptionalPropertyString(namespace, key);

        if ((val == null) || (val.trim().length() == 0)) {
            throw new ConfigurationException("Property " + key + " is required in " + namespace);
        }

        return val;
    }

    /**
     * Validate whether the given value is null.
     *
     * @param value
     *            the value to be validated
     * @param name
     *            the name associated with the value (should be non-null)
     * @return the validated value
     * @throws IllegalArgumentException
     *             is the given value is null
     */
    public static Object validateNotNull(Object value, String name) {
        if (value == null) {
            throw new IllegalArgumentException(name + " is null.");
        }

        return value;
    }

    /**
     * Validate whether the given String value is null or empty.
     *
     * @param value
     *            the value to be validated
     * @param name
     *            the name associated with the value (should be non-null)
     * @return the validated value
     * @throws IllegalArgumentException
     *             is the given value is null or empty
     */
    public static String validateNotNullOrEmpty(String value, String name) {
        validateNotNull(value, name);

        if (value.trim().length() == 0) {
            throw new IllegalArgumentException(name + " is empty.");
        }

        return value;
    }

    /**
     * This is a helper method that creates an object reflectively by calling its constructor with signature given by
     * ctorTypes and parameters given by params.
     *
     * @param className
     *            the full class name
     * @param ctorTypes
     *            the constructor signature
     * @param params
     *            the parameters to be passed into the constructor
     * @param expectedType
     *            the expected type of the object
     * @return the created object as an instance of MailSender
     * @throws ConfigurationException
     *             if any error occurs during creating the object
     * @since 1.1
     */
    @SuppressWarnings("unchecked")
    public static Object creatObject(String className, Class[] ctorTypes, Object[] params, Class expectedType)
            throws ConfigurationException {

        // Get the Class object
        try {
            Class clazz = Class.forName(className);

            // Instantiate the class reflectively
            Object obj = clazz.getDeclaredConstructor(ctorTypes).newInstance(params);

            if (!expectedType.isInstance(obj)) {
                throw new ConfigurationException("Can not cast the created instance into " + expectedType);
            }

            return obj;
        } catch (ClassNotFoundException e) {
            throw new ConfigurationException("Can not find the class named " + className, e);
        } catch (IllegalArgumentException e) {
            throw new ConfigurationException("The arguement is wrong to create an instance for " + className, e);
        } catch (SecurityException e) {
            throw new ConfigurationException("No right to create an instance for " + className, e);
        } catch (InstantiationException e) {
            throw new ConfigurationException("Can not create an instance for " + className, e);
        } catch (IllegalAccessException e) {
            throw new ConfigurationException("Can not access the class " + className, e);
        } catch (InvocationTargetException e) {
            throw new ConfigurationException("Can not call the constructor of " + className, e);
        } catch (NoSuchMethodException e) {
            throw new ConfigurationException("Can not find the constructor for " + className, e);
        }
    }
}
