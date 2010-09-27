/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable.latetracker.failuretests;

import java.lang.reflect.Field;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.persistence.ConfigurationFileManager;

/**
 * Helper class of getting configuration object from the file.
 *
 * @author gjw99
 * @version 1.0
 */
final class TestHelper {


    /**
     * Gets the configuration object from the given file with the given namespace.
     *
     * @param file
     *            the configuration file.
     * @param namespace
     *            the namespace.
     * @return the configuration object.
     * @throws Exception
     *             to JUnit.
     */
    static ConfigurationObject getConfigurationObject(String file, String namespace)
        throws Exception {
        ConfigurationFileManager manager = new ConfigurationFileManager();

        manager.loadFile("root", file);

        ConfigurationObject config = manager.getConfiguration("root");

        return config.getChild(namespace);
    }

    /**
     * <p>
     * Sets the value of a private field in the given class.
     * </p>
     *
     * @param instance the instance which the private field belongs to
     * @param name the name of the private field to be set
     * @param value the value to set
     * @param classType the class to get field
     * @throws RuntimeException if any error occurred when calling this method
     */
    public static void setPrivateField(Class<?> classType, Object instance, String name, Object value) {
        Field field = null;
        try {
            // get the reflection of the field
            field = classType.getDeclaredField(name);

            // set the field accessible
            field.setAccessible(true);
            // set the value
            field.set(instance, value);
        } catch (Exception e) {
            throw new RuntimeException("Error occurred when calling setPrivateField method.", e);
        }
    }
}
