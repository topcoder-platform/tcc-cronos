/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.review.statistics.accuracytests;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.persistence.ConfigurationFileManager;

import com.topcoder.util.config.ConfigManager;

import java.lang.reflect.Field;

import java.util.Iterator;


/**
 * Helper class for the accuracy test.<p><b>Thread safety:</b> This class is immutable and so thread safe.</p>
 *
 * @author onsky
 * @version 1.0
 */
public class AccuracyHelper {
    /** the dependency configurations. */
    private static final String[] ALLCONFIGS = new String[] {
            "accuracytests/db_connection_factory.xml", "accuracytests/LoggingConfig.xml",
            "accuracytests/review_manager_config.xml", "accuracytests/search_bundle_manager.xml",
            "accuracytests/manager.xml"
        };

    /**
     * Get the configuration object from the given file.
     *
     * @param file the configuration file.
     * @param namespace the namespace.
     *
     * @return the configuration object.
     */
    protected static ConfigurationObject getConfigurationObject(String file, String namespace)
        throws Exception {
        ConfigurationFileManager manager = new ConfigurationFileManager();

        manager.loadFile("root", file);

        ConfigurationObject config = manager.getConfiguration("root");

        return config.getChild(namespace);
    }

    /**
     * Build configuration.
     *
     * @throws Exception to JUnit.
     */
    protected static void buildConfig() throws Exception {
        clearConfig();

        ConfigManager configManager = ConfigManager.getInstance();

        for (String config : ALLCONFIGS) {
            configManager.add(config);
        }
    }

    /**
     * Clear configurations.
     *
     * @throws Exception to JUnit.
     */
    protected static void clearConfig() throws Exception {
        ConfigManager configManager = ConfigManager.getInstance();

        Iterator<?> iter = configManager.getAllNamespaces();

        while (iter.hasNext()) {
            configManager.removeNamespace((String) iter.next());
        }
    }

    /**
     * <p>Sets the value of a private field in the given class.</p>
     *
     * @param instance the instance which the private field belongs to
     * @param name the name of the private field to be set
     * @param value the value to set
     *
     * @throws RuntimeException if any error occurred when calling this method
     */
    protected static void setPrivateField(Object instance, String name, Object value) {
        Field field = null;

        try {
            // get the reflection of the field
            field = instance.getClass().getDeclaredField(name);

            // set the field accessible
            field.setAccessible(true);

            // set the value
            field.set(instance, value);
        } catch (Exception e) {
            throw new RuntimeException("Error occurred when calling setPrivateField method.", e);
        }
    }

    /**
     * <p>Gets the value of a private field in the given instance by given name.</p>
     *
     * @param instance the instance which the private field belongs to.
     * @param name the name of the private field to be retrieved.
     *
     * @return the value of the private field.
     */
    protected static Object getPrivateField(Object instance, String name) {
        Object obj = null;

        try {
            Field field = instance.getClass().getDeclaredField(name);

            // Set the field accessible.
            field.setAccessible(true);

            // Get the value
            obj = field.get(instance);
        } catch (NoSuchFieldException e) {
            // Ignore
        } catch (IllegalAccessException e) {
            // Ignore
        }

        return obj;
    }
}
