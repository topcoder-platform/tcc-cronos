/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.manager.dao;

import java.io.IOException;

import com.topcoder.clients.manager.ManagerConfigurationException;
import com.topcoder.configuration.ConfigurationAccessException;
import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.persistence.ConfigurationFileManager;
import com.topcoder.configuration.persistence.ConfigurationParserException;
import com.topcoder.configuration.persistence.NamespaceConflictException;
import com.topcoder.configuration.persistence.UnrecognizedFileTypeException;
import com.topcoder.configuration.persistence.UnrecognizedNamespaceException;
import com.topcoder.util.idgenerator.IDGenerationException;
import com.topcoder.util.idgenerator.IDGenerator;
import com.topcoder.util.idgenerator.IDGeneratorFactory;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;

/**
 * This is the base class for all manager classes in this component, it provides a IDGenerator used by subclasses to
 * generate ID for new entities, and a logger that can be used to log activities and error in children.
 *
 * <p>
 * It stores a ConfigurationObject parsed by Configuration Persistence Manager if configuration file is used, so
 * subclasses can use it to do further configuration.
 * </p>
 *
 * <p>
 * An ObjectFactory is created and can be used by subclasses to create DAO instances.
 * </p>
 *
 * <p>
 * Thread Safety: this class is thread safe, as all fields in it do change after construction.
 * </p>
 *
 * @author moonli, Chenhong
 * @version 1.0
 */
public abstract class AbstractDAOManager {
    /**
     * Represents a ID generator can be used to generate ID for new entities.
     *
     * It's set in the constructor, and don't change afterwards, can't be null. It can be retrieved by getter.
     */
    private final IDGenerator idGenerator;

    /**
     * Represents a logger used to log activities and errors in subclasses.
     *
     * It's set in the constructor, and don't change afterwards, can't be null. It can be retrieved by getter.
     */
    private final Log log;

    /**
     * Constructs an instance of this class by default.
     *
     * @throws ManagerConfigurationException
     *             if failed to create IDGenerator instance
     */
    protected AbstractDAOManager() throws ManagerConfigurationException {
        // create the IDGenerator instance with full class name
        this.idGenerator = createIDGenerator(getClass().getName());
        // create the Log instance with full class name
        log = LogManager.getLog(getClass().getName());
    }

    /**
     * Constructs an instance of this class with given configuration object.
     *
     * @param configuration
     *            the ConfigurationObject used to configure this class, can't be null
     * @throws IllegalArgumentException
     *             if configuration is null
     * @throws ManagerConfigurationException
     *             if error occurred when configuring this class, e.g. required configuration value is missing,
     *             or error occurred in object factory
     */
    protected AbstractDAOManager(ConfigurationObject configuration) throws ManagerConfigurationException {
        Helper.checkNull(configuration, "configuration");

        String idGeneratorName = Helper.getConfigurationParameter(configuration, "id_generator_name", true);

        this.idGenerator = createIDGenerator(idGeneratorName);

        // get the optional logName.
        String logName = Helper.getConfigurationParameter(configuration, "logger_name", false);

        if (logName == null) {
            logName = getClass().getName();
        }

        // Get the Log from LogManager
        log = LogManager.getLog(logName);
    }

    /**
     * Create the IDGenerator instance with given name.
     *
     * @param name
     *            the name to obtain IDGenerator instance
     * @return IDGenerator instance created
     * @throws ManagerConfigurationException
     *             if failed to create the IDGenerator instance with given name
     */
    private IDGenerator createIDGenerator(String name) throws ManagerConfigurationException {
        try {
            return IDGeneratorFactory.getIDGenerator(name);
        } catch (IDGenerationException e) {
            throw new ManagerConfigurationException("Fail to get the IDGenerator with name=" + name + " , cause by : "
                    + e.getMessage(), e);
        }
    }

    /**
     * Gets the ID generator.
     *
     * @return an instance of IDGenerator, will not be null
     */
    protected IDGenerator getIDGenerator() {
        return idGenerator;
    }

    /**
     * Gets the log.
     *
     * @return an instance of Log, will not be null
     */
    protected Log getLog() {
        return log;
    }

    /**
     * Gets the configuration for this class from a file.
     *
     *
     * @param namespace
     *            the namespace to retrieve configuration object, can't be null or empty
     * @param filename
     *            path of the configuration file, it can be null, but can't be empty
     * @param className
     *            the className used as a name to get the child ConfigurationObject, can not be null or empty
     * @return the ConfigurationObject retrieve from a file
     * @throws IllegalArgumentException
     *             if the parameter filename is empty, namespace is null or empty, or className is null or empty
     * @throws ManagerConfigurationException
     *             if error occurred when loading configuration, or the configuration does not exist
     */
    protected static ConfigurationObject getConfiguration(String filename, String namespace, String className)
        throws ManagerConfigurationException {
        Helper.checkEmpty(filename, "filename");
        Helper.checkString(namespace, "namespace");
        Helper.checkString(className, "className");

        try {
            ConfigurationFileManager manager = (filename == null) ? new ConfigurationFileManager()
                    : new ConfigurationFileManager(filename);

            return manager.getConfiguration(namespace).getChild(className);
        } catch (ConfigurationParserException e) {
            throw new ManagerConfigurationException("ConfigurationParserException occurs, cause by " + e.getMessage(),
                    e);
        } catch (UnrecognizedFileTypeException e) {
            throw new ManagerConfigurationException("UnrecognizedFileTypeException occurs, cause by " + e.getMessage(),
                    e);
        } catch (NamespaceConflictException e) {
            throw new ManagerConfigurationException("NamespaceConflictException occurs, cause by "
                    + e.getMessage(), e);
        } catch (IOException e) {
            throw new ManagerConfigurationException(
                    "IOException occurs during retrieve ConfigruationObject , cause by " + e.getMessage(), e);
        } catch (UnrecognizedNamespaceException e) {
            throw new ManagerConfigurationException(
                    "The configuration with namespace=" + namespace + " does not exist", e);
        } catch (ConfigurationAccessException e) {
            throw new ManagerConfigurationException(
                    "ConfigurationAccessException occurs while get child ConfigurationObject with name="
                    + className, e);
        }
    }
}
