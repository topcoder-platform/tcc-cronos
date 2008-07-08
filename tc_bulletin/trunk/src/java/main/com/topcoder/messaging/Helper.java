/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.messaging;

import com.topcoder.configuration.ConfigurationAccessException;
import com.topcoder.configuration.ConfigurationObject;


/**
 * The helper class for this component.
 *
 * @author yqw
 * @version 2.0
 */
public class Helper {
    /**
     * make the default constructor private.
     */
    private Helper() {
    }

    /**
     * Get the string property from the configuration.
     *
     * @param propertyName
     *            the property name.
     * @param configuration
     *            the configuration object.
     * @param required
     *            if the property is required.
     * @return the string value
     * @throws ConfigurationAccessException
     *             if any error while reading the property.
     * @throws MessageBoardConfigurationException
     *             if required property is missing.
     *
     */
    public static String getStringProperty(String propertyName, ConfigurationObject configuration, boolean required)
        throws ConfigurationAccessException, MessageBoardConfigurationException {
        String value = (String) configuration.getPropertyValue(propertyName);

        if ((value != null) && (value.trim().length() == 0)) {
            throw new MessageBoardConfigurationException(getErrorMessage(ErrorMessagesCache.CONFIGURATION,
                    "The property for " + propertyName + " is empty."));
        }

        if (required && (value == null)) {
            throw new MessageBoardConfigurationException(getErrorMessage(ErrorMessagesCache.CONFIGURATION,
                    "The property for " + propertyName + " is required."));
        }

        return value;
    }

    /**
     * the error message in the ErrorMessagesCache, or default message if error message is not
     * stored in ErrorMessagesCache
     *
     * @param key
     *            the error message key.
     * @param defaultMessage
     *            the default message
     * @return the error message in the ErrorMessagesCache
     */
    public static String getErrorMessage(String key, String defaultMessage) {
        String message = ErrorMessagesCache.getErrorMessage(key);

        if (message == null) {
            message = defaultMessage;
        }

        return message;
    }

    /**
     * check the object if it is null.
     *
     * @param obj
     *            the object to check.
     * @param paramterName
     *            the parameter name.
     * @throws IllegalArgumentException
     *             if the object is null.
     */
    public static void checkNull(Object obj, String paramterName) {
        if (obj == null) {
            throw new IllegalArgumentException(Helper.getErrorMessage(ErrorMessagesCache.INVALID_ARGUMENT,
                    "The parameter [" + paramterName + "] is null."));
        }
    }

    /**
     * check the string if it is null or empty.
     *
     * @param str
     *            the object to check.
     * @param paramterName
     *            the parameter name.
     * @throws IllegalArgumentException
     *             if the object is null or empty.
     */
    public static void checkNullOrEmpty(String str, String paramterName) {
        checkNull(str, paramterName);

        if (str.trim().length() == 0) {
            throw new IllegalArgumentException(Helper.getErrorMessage(ErrorMessagesCache.INVALID_ARGUMENT,
                    "The parameter [" + paramterName + "] is empty."));
        }
    }
}
