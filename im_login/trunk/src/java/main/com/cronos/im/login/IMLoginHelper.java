/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.login;

import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.UnknownNamespaceException;

/**
 * <p>
 * The IMLogin Component helper class.
 * </p>
 * <p>
 * Thread safety: This class is thread safe since it is immutable.
 * </p>
 *
 * @author tyrian
 * @version 1.0
 */
class IMLoginHelper {
    /**
     * Represents the request key user.
     */
    static final String USER = "user";

    /**
     * Represents the request key password.
     */
    static final String PASSWORD = "pwd";

    /**
     * Represents the request key category.
     */
    static final String CATEGORY = "cat";

    /**
     * Represents the request key type.
     */
    static final String TYPE = "type";

    /**
     * Private constructor. No instances allowed.
     */
    private IMLoginHelper() {
    }

    /**
     * <p>
     * Gets the configuration value from the given namespace. If the value is not present or unknown namespace,
     * default value will be used.
     * </p>
     *
     * @param namespace
     *            the namespace to get the configuration
     * @param key
     *            the key to search
     * @param defaultVal
     *            the default value
     * @return the key value or default value
     */
    static String getConfig(String namespace, String key, String defaultVal) {
        ConfigManager manager = ConfigManager.getInstance();
        try {
            String value = manager.getString(namespace, key);
            if (value != null && value.trim().length() != 0) {
                return value;
            }
        } catch (UnknownNamespaceException e) {
            return defaultVal;
        }
        return defaultVal;
    }

    /**
     * <p>
     * Validates the value of object with type <code>Object</code>. The object cannot be <code>null</code>.
     * </p>
     *
     * @param object
     *            The object which needs validation.
     * @param name
     *            Name of the variable which is passed for validation.
     *
     * @throws IllegalArgumentException
     *             If <code>value</code> is <code>null</code>.
     */
    static void validateNotNull(Object object, String name) {
        if (object == null) {
            throw new IllegalArgumentException(name + " cannot be null.");
        }
    }

    /**
     * <p>
     * Validates the value of <code>String</code> variable. The value cannot be <code>null</code> or empty
     * <code>String</code>.
     * </p>
     *
     * @param string
     *            The string which is validated for empty.
     * @param name
     *            Name of the <code>String</code> passed for validation.
     *
     * @throws IllegalArgumentException
     *             If <code>String</code> is <code>null</code> or empty.
     */
    static void validateEmptyString(String string, String name) {
        validateNotNull(string, name);
        if (string.trim().length() == 0) {
            throw new IllegalArgumentException(name + " trimmed cannot be empty string.");
        }
    }
}
