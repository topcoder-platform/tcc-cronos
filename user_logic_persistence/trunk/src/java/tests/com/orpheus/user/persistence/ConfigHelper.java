/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.user.persistence;

import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.ConfigManagerException;

/**
 * <p>
 * A helper class that is used by the unit test cases to load and unload test
 * configuration namespaces into the Configuration Manager.
 * </p>
 *
 * @author mpaulse
 * @version 1.0
 */
public class ConfigHelper {

    /**
     * <p>
     * The Simple Cache test configuration file.
     * </p>
     */
    private static final String CACHE_CONFIG = "test_conf/simplecache.xml";

    /**
     * <p>
     * The namespace containing the test Simple Cache configuration.
     * </p>
     */
    private static final String CACHE_NAMESPACE = "com.topcoder.util.cache";

    /**
     * <p>
     * The DB Connection Factory test configuration file.
     * </p>
     */
    private static final String DB_CONNECTION_FACTORY_CONFIG = "test_conf/dbconnectionfactory.xml";

    /**
     * <p>
     * The namespace containing the DB Connection Factory configuration.
     * </p>
     */
    private static final String DB_CONNECTION_FACTORY_NAMESPACE = "com.topcoder.db.connectionfactory";

    /**
     * <p>
     * The Logging Wrapper test configuration file.
     * </p>
     */
    private static final String LOGGER_CONFIG = "test_conf/logging.xml";

    /**
     * <p>
     * The namespace containing the Logging Wrapper configuration.
     * </p>
     */
    private static final String LOGGER_NAMESPACE = "com.topcoder.util.log";

    /**
     * <p>
     * The test configuration file for the User Profile types.
     * </p>
     */
    private static final String PROFILETYPES_CONFIG = "test_conf/profiletypes.xml";

    /**
     * <p>
     * The prefix of the namespace containing the User Profile types
     * configuration.
     * </p>
     */
    private static final String PROFILETYPES_NAMESPACE_PREFIX = "com.topcoder.user.profile.ConfigProfileType";

    /**
     * <p>
     * This constructor is private to prevent this class from being
     * instantiated.
     * </p>
     */
    private ConfigHelper() {
        // Empty constructor.
    }

    /**
     * <p>
     * Loads the specified configuration namespace in the specified
     * configuration file into the Configuration Manager.
     * </p>
     *
     * @param filename the configuration filename to load
     * @param namespace the configuration namespace
     * @throws ConfigManagerException if an error occurs while loading the
     *         configuration file
     */
    public static void loadConfig(String filename, String namespace) throws ConfigManagerException {
        ConfigManager configManager = ConfigManager.getInstance();
        unloadConfig(namespace);
        configManager.add(namespace, filename, ConfigManager.CONFIG_MULTIPLE_XML_FORMAT);
    }

    /**
     * <p>
     * Loads the test DB Connection Factory configuration into the Configuration
     * Manager.
     * </p>
     *
     * @throws ConfigManagerException if an error occurs while loading the
     *         configuration
     */
    public static void loadDBConnectionFactoryConfig() throws ConfigManagerException {
        loadConfig(DB_CONNECTION_FACTORY_CONFIG, DB_CONNECTION_FACTORY_NAMESPACE);
    }

    /**
     * <p>
     * Loads the test Logging Wrapper configuration into the Configuration
     * Manager.
     * </p>
     *
     * @throws ConfigManagerException if an error occurs while loading the
     *         configuration
     */
    public static void loadLoggerConfig() throws ConfigManagerException {
        loadConfig(LOGGER_CONFIG, LOGGER_NAMESPACE);
    }

    /**
     * <p>
     * Loads the test Simple Cache configuration into the Configuration
     * Manager.
     * </p>
     *
     * @throws ConfigManagerException if an error occurs while loading the
     *         configuration
     */
    public static void loadCacheConfig() throws ConfigManagerException {
        loadConfig(CACHE_CONFIG, CACHE_NAMESPACE);
    }

    /**
     * <p>
     * Loads the test User Profile types configuration into the Configuration
     * Manager.
     * </p>
     *
     * @throws ConfigManagerException if an error occurs while loading the
     *         configuration
     */
    public static void loadProfileTypesConfig() throws ConfigManagerException {
        loadConfig(PROFILETYPES_CONFIG, PROFILETYPES_NAMESPACE_PREFIX + ".base");
        loadConfig(PROFILETYPES_CONFIG, PROFILETYPES_NAMESPACE_PREFIX + ".player");
        loadConfig(PROFILETYPES_CONFIG, PROFILETYPES_NAMESPACE_PREFIX + ".admin");
        loadConfig(PROFILETYPES_CONFIG, PROFILETYPES_NAMESPACE_PREFIX + ".sponsor");
        loadConfig(PROFILETYPES_CONFIG, PROFILETYPES_NAMESPACE_PREFIX + ".address");
        loadConfig(PROFILETYPES_CONFIG, PROFILETYPES_NAMESPACE_PREFIX + ".credentials");
        loadConfig(PROFILETYPES_CONFIG, PROFILETYPES_NAMESPACE_PREFIX + ".preferences");
    }

    /**
     * <p>
     * Unloads the specified configuration namespace from the Configuration
     * Manager.
     * </p>
     *
     * @param namespace the configuration namespace to unload
     * @throws ConfigManagerException if unloading the configuration namespace
     *         fails
     */
    public static void unloadConfig(String namespace) throws ConfigManagerException {
        ConfigManager configManager = ConfigManager.getInstance();
        if (configManager.existsNamespace(namespace)) {
            configManager.removeNamespace(namespace);
        }
    }

    /**
     * <p>
     * Unloads the test DB Connection Factory configuration namespace from the
     * Configuration Manager.
     * </p>
     *
     * @throws ConfigManagerException if unloading the configuration namespace
     *         fails
     */
    public static void unloadDBConnectionFactoryConfig() throws ConfigManagerException {
        unloadConfig(DB_CONNECTION_FACTORY_NAMESPACE);
    }

    /**
     * <p>
     * Unloads the test Logging Wrapper configuration namespace from the
     * Configuration Manager.
     * </p>
     *
     * @throws ConfigManagerException if unloading the configuration namespace
     *         fails
     */
    public static void unloadLoggerConfig() throws ConfigManagerException {
        unloadConfig(LOGGER_NAMESPACE);
    }

    /**
     * <p>
     * Unloads the test Simple Cache configuration namespace from the
     * Configuration Manager.
     * </p>
     *
     * @throws ConfigManagerException if unloading the configuration namespace
     *         fails
     */
    public static void unloadCacheConfig() throws ConfigManagerException {
        unloadConfig(CACHE_NAMESPACE);
    }

    /**
     * <p>
     * Unloads the test User Profile types configuration namespaces from the
     * Configuration Manager.
     * </p>
     *
     * @throws ConfigManagerException if unloading the configuration namespace
     *         fails
     */
    public static void unloadProfileTypesConfig() throws ConfigManagerException {
        unloadConfig(PROFILETYPES_NAMESPACE_PREFIX + ".base");
        unloadConfig(PROFILETYPES_NAMESPACE_PREFIX + ".player");
        unloadConfig(PROFILETYPES_NAMESPACE_PREFIX + ".admin");
        unloadConfig(PROFILETYPES_NAMESPACE_PREFIX + ".sponsor");
        unloadConfig(PROFILETYPES_NAMESPACE_PREFIX + ".address");
        unloadConfig(PROFILETYPES_NAMESPACE_PREFIX + ".credentials");
    }

}
