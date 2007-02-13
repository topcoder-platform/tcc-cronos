/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.project;

import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.ConfigManagerException;
import com.topcoder.util.config.Property;


/**
 * <p>
 * A utility class providing some common operations related to configuration. This class is declared as public for the
 * sub-packages to access. It is not intended for public use however.
 * </p>
 *
 * @author real_vg, TCSDEVELOPER
 * @version 1.1
 */
public class ConfigUtil {
    /**
     * <p>
     * Private constructor to prevent outside instantiation.
     * </p>
     */
    private ConfigUtil() {
    }

    /**
     * <p>
     * Obtains the root property of the configuration file in the specified namespace. This method conveniently wraps
     * the exceptions from the Configuration Manager into ConfigurationException.
     * </p>
     *
     * @param namespace the configuration namespace to use.
     *
     * @return the root property of the configuration file.
     *
     * @throws ConfigurationException if a problem occurs while configuring.
     */
    public static Property getRootProperty(String namespace)
        throws ConfigurationException {
        try {
            return ConfigManager.getInstance().getPropertyObject(namespace, "");
        } catch (ConfigManagerException e) {
            throw new ConfigurationException("Fails to load from configuration namespace " + namespace, e);
        }
    }

    /**
     * <p>
     * Gets the property object of the specified name. If the property does not exist, a ConfigurationException will be
     * thrown.
     * </p>
     *
     * @param property the parent property to get the child property from.
     * @param name the property name.
     *
     * @return the property object of the specified name.
     *
     * @throws ConfigurationException if the property does not exist.
     */
    public static Property getProperty(Property property, String name)
        throws ConfigurationException {
        Property result = property.getProperty(name);

        if (result == null) {
            throw new ConfigurationException("Missing property " + name);
        }
        return result;
    }

    /**
     * <p>
     * Gets the property value of the specified name. If the property value does not exist or is empty, a
     * ConfigurationException will be thrown.
     * </p>
     *
     * @param property the property to get the value from.
     * @param name the property name.
     *
     * @return the property value of the specified name.
     *
     * @throws ConfigurationException if the property value does not exist or is empty.
     */
    public static String getPropertyValue(Property property, String name)
        throws ConfigurationException {
        String value = getOptionalPropertyValue(property, name);

        if (value == null) {
            throw new ConfigurationException("Missing value for property " + name);
        }
        return value;
    }

    /**
     * <p>
     * Gets the optional property value of the specified name. If the property value exists but is empty, a
     * ConfigurationException will be thrown.
     * </p>
     *
     * @param property the property to get the value from.
     * @param name the property name.
     *
     * @return the property value of the specified name, or null if there is none.
     *
     * @throws ConfigurationException if the property value exists but is empty.
     */
    public static String getOptionalPropertyValue(Property property, String name)
        throws ConfigurationException {
        String value = property.getValue(name);

        if ((value != null) && (value.trim().length() == 0)) {
            throw new ConfigurationException("Empty value for property " + name);
        }
        return value;
    }
}
