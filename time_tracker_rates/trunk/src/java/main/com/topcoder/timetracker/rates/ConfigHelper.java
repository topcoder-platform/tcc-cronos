/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.rates;

import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.UnknownNamespaceException;

/**
 * <p>Helper class used to process component configuration.</p>
 * <p>Thread-Safety: This class is thread safe since it's not mutable.</p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public final class ConfigHelper {

    /**
     * Private constructor preventing this class from being instantiated.
     */
    private ConfigHelper() {
        //does nothing
    }

    /**
     * Obtains the string property from given namespace. If the property is missing but required, or the value
     * is trimmed empty string, ConfigurationException will be thrown.
     *
     * @param namespace namespace the property is read
     * @param propertyName name of the property
     * @param required flag indicating that the property is required or not
     *
     * @return the String value of the property, non-empty string or null
     *
     * @throws RateConfigurationException if the property is invalid
     */
    public static String getStringProperty(String namespace, String propertyName, boolean required)
        throws RateConfigurationException {
        String value;

        try {
            value = ConfigManager.getInstance().getString(namespace, propertyName);
        } catch (UnknownNamespaceException e) {
            throw new RateConfigurationException("namespace:" + namespace + " is not found", e);
        }

        if (value != null) {
            //property value should not be empty string
            if (value.trim().length() == 0) {
                throw new RateConfigurationException("property:" + propertyName + " is empty string");
            }
        } else if (required) {
            //property is missing
            throw new RateConfigurationException("property:" + propertyName + " is required but missing");
        }

        return value;
    }
}
