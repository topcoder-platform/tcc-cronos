/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.points;

import java.io.IOException;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.persistence.ConfigurationFileManager;
import com.topcoder.configuration.persistence.ConfigurationParserException;
import com.topcoder.configuration.persistence.NamespaceConflictException;
import com.topcoder.configuration.persistence.UnrecognizedFileTypeException;
import com.topcoder.configuration.persistence.UnrecognizedNamespaceException;

/**
 * <p>
 * This class holds retrieves and stores the configuration for the current bean (and possibly for
 * future beans and different implementations of DigitalRunPointsManager interface). It uses
 * Configuration Persistence component to retrieve the configuration from a file. The user can also
 * provide the configuration object by calling setConfiguration method. The configuration can be
 * retrieved by calling getConfiguration method. This class was created because file access in ejb
 * is forbidden by ejb specification.
 * </p>
 * <p>
 * Thread Safety: This class is thread safe because all the methods are synchronized.
 * </p>
 *
 * @author DanLazar, TCSDEVELOPER
 * @version 1.0
 */
public class ConfigurationProvider {
    /**
     * The default file from which configuration is retrieved in retrieveAndStoreConfiguration
     * methods. It is static and final.
     */
    public static final String DEFAULT_FILE = "com/topcoder/service/digitalrun/points/"
            + "DigitalRunPointsManager.properties";
    /**
     * The default namespace from which configuration is retrieved in retrieveAndStoreConfiguration
     * methods. It is static and final.
     */
    public static final String DEFAULT_NAMESPACE = "com.topcoder.service.digitalrun.points";
    /**
     * The configuration object retrieved by the provider. It will be set in setConfiguration and
     * retrieveConfigurationFromFile methods; it cannot be set to null in all these methods.It has a
     * getter. It is mutable.
     */
    private static ConfigurationObject configuration = null;

    /**
     * Empty constructor.
     */
    private ConfigurationProvider() {
        // Empty
    }

    /**
     * Sets the configuration field with the given configuration object. This method is
     * synchronized.
     *
     * @throws IllegalArgumentException
     *             if configuration is null
     * @param configuration
     *            the configuration object
     */
    public static synchronized void setConfiguration(ConfigurationObject configuration) {
        Helper.checkNull(configuration, "configuration");

        ConfigurationProvider.configuration = configuration;
    }

    /**
     * Gets the configuration from the default file and default namespace.
     *
     * @throws DigitalRunPointsManagerBeanConfigurationException
     *             if errors occur when getting the configuration from file.
     */
    public static synchronized void retrieveConfigurationFromFile() {
        retrieveConfigurationFromFile(DEFAULT_FILE, DEFAULT_NAMESPACE);
    }

    /**
     * Gets the configuration from the given file stored under the given namespace.
     *
     * @throws IllegalArgumentException
     *             if any argument is null/empty string
     * @throws DigitalRunPointsManagerBeanConfigurationException
     *             if errors occur when getting the configuration from file.
     * @param namespace
     *            the namespace under which the configurations stored
     * @param file
     *            the file from which the configuration is retrieved
     */
    public static synchronized void retrieveConfigurationFromFile(String file, String namespace) {
        Helper.checkNullAndEmpty(file, "file");
        Helper.checkNullAndEmpty(namespace, "namespace");

        try {
            // create ConfigurationFileManager instance
            ConfigurationFileManager cfm = new ConfigurationFileManager(file);
            // get and set configuration
            configuration = cfm.getConfiguration(namespace);
        } catch (UnrecognizedNamespaceException e) {
            throw new DigitalRunPointsManagerBeanConfigurationException(
                    "UnrecognizedNamespaceException occurs while getting configuration.", e);
        } catch (ConfigurationParserException e) {
            throw new DigitalRunPointsManagerBeanConfigurationException(
                    "ConfigurationParserException occurs while creating ConfigurationFileManager.", e);
        } catch (NamespaceConflictException e) {
            throw new DigitalRunPointsManagerBeanConfigurationException(
                    "NamespaceConflictException occurs while creating ConfigurationFileManager.", e);
        } catch (UnrecognizedFileTypeException e) {
            throw new DigitalRunPointsManagerBeanConfigurationException(
                    "UnrecognizedFileTypeException occurs while creating ConfigurationFileManager.", e);
        } catch (IOException e) {
            throw new DigitalRunPointsManagerBeanConfigurationException(
                    "IOException occurs while creating ConfigurationFileManager.", e);
        }
    }

    /**
     * Gets the configuration.
     *
     * @return the configuration field
     */
    public static synchronized ConfigurationObject getConfiguration() {
        if (configuration == null) {
            retrieveConfigurationFromFile();
        }
        return configuration;
    }
}
