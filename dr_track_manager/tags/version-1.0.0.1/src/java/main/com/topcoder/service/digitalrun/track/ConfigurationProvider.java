/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.track;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.persistence.ConfigurationFileManager;
import com.topcoder.configuration.persistence.ConfigurationParserException;
import com.topcoder.configuration.persistence.NamespaceConflictException;
import com.topcoder.configuration.persistence.UnrecognizedFileTypeException;
import com.topcoder.configuration.persistence.UnrecognizedNamespaceException;

import java.io.IOException;


/**
 * <p>
 * This class holds retrieves and stores the configuration for the current bean (and possibly for future beans and
 * different implementations of <code>DigitalRunTrackManager</code> interface). It uses Configuration Persistence
 * component to retrieve the configuration from a file. The user can also provide the configuration object by calling
 * setConfiguration method. The configuration can be retrieved by calling getConfiguration method.
 * </p>
 *
 * <p>
 * This class was created because file access in ejb is forbidden by ejb specification. This class is thread safe
 * because all the methods are synchronized.
 * </p>
 * @author DanLazar, waits
 * @version 1.0
 */
public class ConfigurationProvider {
    /**
     * <p>
     * The default file from which configuration is retrieved in retrieveConfigurationFromFile methods. It is static
     * and final.
     * </p>
     */
    public static final String FILE = "com/topcoder/service/digitalrun/track/DigitalRunTrackManager.properties";

    /**
     * <p>
     * The default namespace from which configuration is retrieved in retrieveConfigurationFromFile methods. It is
     * static and final.
     * </p>
     */
    public static final String NAMESPACE = "com.topcoder.service.digitalrun.track";

    /**
     * <p>
     * The configuration object retrieved by the provider. It will be set in setConfiguration and
     * retrieveConfigurationFromFile methods; it cannot be set to null in all these methods.It has a getter. It is
     * mutable.
     * </p>
     */
    private static ConfigurationObject configuration = null;

    /**
     * <p>
     * Empty constructor. Stop from instantiate.
     * </p>
     */
    private ConfigurationProvider() {
    }

    /**
     * <p>
     * Gets the configuration.  It can return null if configuration object is null.
     * </p>
     *
     * @return the configuration field
     */
    public static synchronized ConfigurationObject getConfiguration() {
        if (configuration == null) {
            try {
                configuration = new ConfigurationFileManager(FILE).getConfiguration(NAMESPACE);
            } catch (ConfigurationParserException e) {
                throw new DigitalRunTrackManagerBeanConfigurationException(
                    "Fail to load configuration FILE " + FILE, e);
            } catch (NamespaceConflictException e) {
                throw new DigitalRunTrackManagerBeanConfigurationException(
                     "Fail to load namespace " + NAMESPACE + ", same namespace conflict.", e);
            } catch (UnrecognizedFileTypeException e) {
                throw new DigitalRunTrackManagerBeanConfigurationException(
                     "Un-recongnized configuration FILE type", e);
            } catch (IOException e) {
                throw new DigitalRunTrackManagerBeanConfigurationException(
                     "Fail to read from configuration FILE " + FILE, e);
            } catch (UnrecognizedNamespaceException e) {
                throw new DigitalRunTrackManagerBeanConfigurationException(
                      "Unknown configuration " + NAMESPACE + " in configuration FILE " + FILE, e);
            }

            if (configuration == null) {
                throw new DigitalRunTrackManagerBeanConfigurationException("There is no configuration.");
            }
        }

        return configuration;
    }
}
