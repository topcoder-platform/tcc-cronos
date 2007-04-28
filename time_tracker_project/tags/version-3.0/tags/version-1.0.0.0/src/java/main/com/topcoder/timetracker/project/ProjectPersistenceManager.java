/*
 * ProjectPersistenceManager.java
 *
 * Copyright (c) 2005 TopCoder, Inc., All Rights Reserved
 */
package com.topcoder.timetracker.project;

import com.topcoder.timetracker.project.persistence.TimeTrackerProjectPersistence;

import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.ConfigManagerException;

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
 *
 *     &lt;!-- the class name of the persistence (required) --&gt;
 *     &lt;Property name="persistence_class"&gt;
 *       &lt;Value&gt;com.topcoder.timetracker.project.persistence.InformixTimeTrackerProjectPersistence&lt;/Value&gt;
 *     &lt;/Property&gt;
 *      &lt;!-- the namespace of the DB Connection Factory configuration file (required) --&gt;
 *     &lt;Property name="connection_factory_namespace"&gt;
 *       &lt;Value&gt;com.topcoder.db.connectionfactory&lt;/Value&gt;
 *     &lt;/Property&gt;
 *
 *     &lt;!-- the name identifying a ConnectionProducer instance (optional) --&gt;
 *     &lt;Property name="connection_producer_name"&gt;
 *       &lt;Value&gt;Informix_Connection_Producer&lt;/Value&gt;
 *     &lt;/Property&gt;
 *
 *   &lt;/Config&gt;
 * &lt;/CMConfig&gt;
 * </pre>
 * </p>
 *
 * <p>
 * The configuration file should be loaded by specifying its namespace in the constructor. The namespace of the
 * configuration file for DB Connection Factory component should be provided in the above configuration file to get
 * the actual connection from the connection producer name.
 * </p>
 *
 * <p>
 * The ClientUtility and ProjectUtility classes will use the getPersistence() method to access the
 * TimeTrackerProjectPersistence implementation.
 * </p>
 *
 * @author DanLazar, TCSDEVELOPER
 * @version 1.0
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
        throws ConfigurationException
    {
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
     * <p>
     * This method assumes that namespace argument is non-null and non-empty.
     * </p>
     *
     * @param namespace the namespace of the Time Tracker Project configuration file
     *
     * @throws ConfigurationException if the property is missing or invalid, or something goes wrong when reading the
     *         configuration file
     */
    private void config(String namespace) throws ConfigurationException {
        try {
            ConfigManager config = ConfigManager.getInstance();

            // check if namespace exists
            if (!config.existsNamespace(namespace)) {
                throw new ConfigurationException("Namespace " + namespace + " does not exist");
            }

            // make sure the properties are updated
            config.refresh(namespace);

            // get the properties - persistence class name, db namespace and connection producer name
            String persistenceClassName = config.getString(namespace, PERSISTENCE_CLASS_NAME_PROPERTY);
            String dbNamespace = config.getString(namespace, DB_NAMESPACE_PROPERTY);
            String connectionProducerName = config.getString(namespace, CONNECTION_PRODUCER_NAME_PROPERTY);

            if (persistenceClassName == null) {
                throw new ConfigurationException("Missing value for property " + PERSISTENCE_CLASS_NAME_PROPERTY);
            }
            if (dbNamespace == null) {
                throw new ConfigurationException("Missing value for property " + DB_NAMESPACE_PROPERTY);
            }

            // locate the persistence class
            Class persistenceClass = Class.forName(persistenceClassName);

            if (!TimeTrackerProjectPersistence.class.isAssignableFrom(persistenceClass)) {
                throw new ConfigurationException("Class " + persistenceClassName
                    + " does not implement TimeTrackerProjectPersistence");
            }

            // prepare the constructor parameters
            Class[] parameterTypes = null;
            Object[] parameters = null;

            if (connectionProducerName == null) {
                // if connection producer name is not specified, use the one-argument constructor
                parameterTypes = new Class[] {String.class};
                parameters = new String[] {dbNamespace};
            } else {
                // otherwise, use the two-argument constructor
                parameterTypes = new Class[] {String.class, String.class};
                parameters = new String[] {dbNamespace, connectionProducerName};
            }

            // instantiate the persistence class
            persistence =
                (TimeTrackerProjectPersistence) persistenceClass.getConstructor(parameterTypes).newInstance(parameters);
        } catch (ConfigManagerException e) {
            throw new ConfigurationException("Some error occurs in Configuration Manager", e);
        } catch (ClassNotFoundException e) {
            throw new ConfigurationException("Unable to locate the class", e);
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
