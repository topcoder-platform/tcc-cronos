/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.project;

import com.topcoder.timetracker.project.persistence.TimeTrackerProjectPersistence;

import com.topcoder.util.config.Property;

import java.lang.reflect.InvocationTargetException;


/**
 * <p>
 * This manager is responsible for reading the configuration file to determine the persistence for loading and saving
 * time tracker project information.
 * </p>
 *
 * <p>
 * Here is an example of the configuration file:
 * <pre>
 * &lt;?xml version="1.0"?&gt;
 * &lt;CMConfig&gt;
 *   &lt;Config name="com.topcoder.timetracker.project"&gt;
 *     &lt;!-- the class name of the persistence (required) --&gt;
 *     &lt;Property name="persistence_class"&gt;
 *       &lt;Value&gt;com.topcoder.timetracker.project.persistence.InformixTimeTrackerProjectPersistence&lt;/Value&gt;
 *     &lt;/Property&gt;
 *     &lt;!-- the namespace of the DB Connection Factory configuration file (required) --&gt;
 *     &lt;Property name="connection_factory_namespace"&gt;
 *       &lt;Value&gt;com.topcoder.db.connectionfactory&lt;/Value&gt;
 *     &lt;/Property&gt;
 *     &lt;!-- the name identifying a ConnectionProducer instance (optional) --&gt;
 *     &lt;Property name="connection_producer_name"&gt;
 *       &lt;Value&gt;Informix_Connection_Producer&lt;/Value&gt;
 *     &lt;/Property&gt;
 *     &lt;!-- the ConfigManager namespace of DatabaseSearchUtility used to search projects (optional) --&gt;
 *     &lt;Property name="project_search_utility_namespace"&gt;
 *       &lt;Value&gt;com.topcoder.timetracker.project.persistence.DatabaseSearchUtility.projects&lt;/Value&gt;
 *     &lt;/Property&gt;
 *     &lt;!-- the ConfigManager namespace of DatabaseSearchUtility used to search clients (optional) --&gt;
 *     &lt;Property name="client_search_utility_namespace"&gt;
 *       &lt;Value&gt;com.topcoder.timetracker.project.persistence.DatabaseSearchUtility.clients&lt;/Value&gt;
 *     &lt;/Property&gt;
 *   &lt;/Config&gt;
 * &lt;/CMConfig&gt;
 * </pre>
 * </p>
 *
 * <p>
 * The configuration file should be loaded by specifying its namespace in the constructor. Since version 1.1 only the
 * following combinations of configuration specification are considered valid:
 *
 * <ul>
 * <li>
 * persistence class, db namespace
 * </li>
 * <li>
 * persistence class, db namespace, connection producer name
 * </li>
 * <li>
 * persistence class, db namespace, connection producer name, project search utility namespace, client search utility
 * namespace
 * </li>
 * </ul>
 * </p>
 *
 * <p>
 * The ClientUtility and ProjectUtility classes will use the getPersistence() method to access the
 * TimeTrackerProjectPersistence implementation.
 * </p>
 *
 * @author DanLazar, colau
 * @author real_vg, TCSDEVELOPER
 * @version 1.1
 *
 * @since 1.0
 */
public class ProjectPersistenceManager {
    /**
     * <p>
     * Constant for the property specifying the class name of the persistence.
     * </p>
     */
    private static final String PERSISTENCE_CLASS_NAME_PROPERTY = "persistence_class";

    /**
     * <p>
     * Constant for the property specifying the namespace of the DB Connection Factory configuration file.
     * </p>
     */
    private static final String DB_NAMESPACE_PROPERTY = "connection_factory_namespace";

    /**
     * <p>
     * Constant for the property specifying the connection producer name.
     * </p>
     */
    private static final String CONNECTION_PRODUCER_NAME_PROPERTY = "connection_producer_name";

    /**
     * <p>
     * Constant for the property specifying the namespace for the project search utility.
     * </p>
     */
    private static final String PROJECT_SEARCH_NAMESPACE_PROPERTY = "project_search_utility_namespace";

    /**
     * <p>
     * Constant for the property specifying the namespace for the client search utility.
     * </p>
     */
    private static final String CLIENT_SEARCH_NAMESPACE_PROPERTY = "client_search_utility_namespace";

    /**
     * <p>
     * Represents an implementation of TimeTrackerProjectPersistence. This field will be used by the ClientUtility and
     * ProjectUtility classes to work with the database. It will be instantiated in the constructor according to the
     * class name specified in the configuration file. It cannot be null.
     * </p>
     */
    private TimeTrackerProjectPersistence persistence = null;

    /**
     * <p>
     * Creates a new instance. This constructor will determine the persistence by reading the configuration file
     * specified by the given namespace.
     * </p>
     *
     * @param namespace the namespace of the Time Tracker Project configuration file
     *
     * @throws NullPointerException if the namespace is null
     * @throws IllegalArgumentException if the namespace is the empty string
     * @throws ConfigurationException if the property is missing or invalid, or something goes wrong when reading the
     *         configuration file
     */
    public ProjectPersistenceManager(String namespace)
        throws ConfigurationException {
        Util.checkString(namespace);

        // read the configuration file
        config(namespace);
    }

    /**
     * <p>
     * Getter for the implementation of TimeTrackerProjectPersistence loaded by this manager.
     * </p>
     *
     * @return the implementation of TimeTrackerProjectPersistence
     */
    public TimeTrackerProjectPersistence getPersistence() {
        return persistence;
    }

    /**
     * <p>
     * Reads the configuration file of the given namespace, and instantiate the persistence class according to the
     * properties.
     * </p>
     *
     * @param namespace the namespace of the Time Tracker Project configuration file
     *
     * @throws ConfigurationException if the property is missing or invalid, or something goes wrong when reading the
     *         configuration file
     */
    private void config(String namespace) throws ConfigurationException {
        Property root = ConfigUtil.getRootProperty(namespace);

        // read the required properties
        String persistenceClassName = ConfigUtil.getPropertyValue(root, PERSISTENCE_CLASS_NAME_PROPERTY);
        String dbNamespace = ConfigUtil.getPropertyValue(root, DB_NAMESPACE_PROPERTY);

        // read the optional properties
        String connectionProducerName = ConfigUtil.getOptionalPropertyValue(root, CONNECTION_PRODUCER_NAME_PROPERTY);
        String projectSearchNamespace = ConfigUtil.getOptionalPropertyValue(root, PROJECT_SEARCH_NAMESPACE_PROPERTY);
        String clientSearchNamespace = ConfigUtil.getOptionalPropertyValue(root, CLIENT_SEARCH_NAMESPACE_PROPERTY);

        // locate the persistence class
        Class persistenceClass = null;

        try {
            persistenceClass = Class.forName(persistenceClassName);
        } catch (ClassNotFoundException e) {
            throw new ConfigurationException("Unable to locate the class", e);
        }

        if (!TimeTrackerProjectPersistence.class.isAssignableFrom(persistenceClass)) {
            throw new ConfigurationException("Class " + persistenceClassName
                + " does not implement TimeTrackerProjectPersistence");
        }

        // prepare the constructor parameters
        Class[] parameterTypes = null;
        Object[] parameters = null;

        if ((connectionProducerName == null) && (projectSearchNamespace == null) && (clientSearchNamespace == null)) {
            // if nothing optional is specified, use the one-argument constructor
            parameterTypes = new Class[] {String.class};
            parameters = new String[] {dbNamespace};
        } else if ((connectionProducerName != null) && (projectSearchNamespace == null)
                && (clientSearchNamespace == null)) {
            // if only the connection producer name is specified, use the two-argument constructor
            parameterTypes = new Class[] {String.class, String.class};
            parameters = new String[] {dbNamespace, connectionProducerName};
        } else if ((connectionProducerName != null) && (projectSearchNamespace != null)
                && (clientSearchNamespace != null)) {
            // if all three are specified, use the four-argument constructor
            parameterTypes = new Class[] {String.class, String.class, String.class, String.class};
            parameters = new String[] {dbNamespace, connectionProducerName, projectSearchNamespace,
                    clientSearchNamespace};
        } else {
            // otherwise the configuration specification is invalid
            throw new ConfigurationException("Configuration specification is invalid");
        }

        // instantiate the persistence class
        try {
            persistence = (TimeTrackerProjectPersistence)
                    persistenceClass.getConstructor(parameterTypes).newInstance(parameters);
        } catch (NoSuchMethodException e) {
            throw new ConfigurationException("Unable to locate the constructor", e);
        } catch (InstantiationException e) {
            throw new ConfigurationException("Fails to create the instance", e);
        } catch (IllegalAccessException e) {
            throw new ConfigurationException("Fails to create the instance", e);
        } catch (InvocationTargetException e) {
            throw new ConfigurationException("Fails to create the instance", e);
        }
    }
}
