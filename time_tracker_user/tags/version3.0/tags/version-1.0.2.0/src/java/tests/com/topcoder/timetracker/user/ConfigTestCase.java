/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user;

import junit.framework.TestCase;

import com.topcoder.util.config.ConfigManager;

/**
 * This abstract base class is used when the subclass needs to clear out the default ConfigManager
 * namespace in the setUp and tearDown methods.  The default namespace name is from the UserManager.
 * The setUp method in this clsas is *not* responsible for configuring anything. It is the
 * responsibility of the subclass to configure the namespace(s) it needs.  This class' tearDown
 * method will remove the UserManager.CONFIG_NAMESPACE and the DBConnectionFactoryImpl
 * namespace (if they have been configured) from the ConfigManager.
 *
 * @see UserManager#CONFIG_NAMESPACE
 * @author TCSDEVELOPER
 * @version 1.0
 */
abstract class ConfigTestCase extends TestCase {

    /** This is just a shortcut to the required namespace. */
    protected static final String NAMESPACE = UserManager.CONFIG_NAMESPACE;

    /** The name of a UserStore that we add to the UserStoreManager. */
    protected static final String STORE_NAME = "userStore";

    /** A username that we can import. */
    protected static final String USERNAME = "username";

    /**
     * This is the namespace that the IDGenerator needs, to use the DBConnectionFactory.
     * It is used in some subclasses setUp method.
     */
    protected static final String DB_CONNECTION_FACTORY_NAMESPACE =
                                        "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl";

    /** This is the instance of the configmanager that we use throughout all the tests. */
    private ConfigManager configManager;


    /**
     * Get an instance of the ConfigManager, and clear the default namespace.
     * @throws Exception Never under normal conditions.
     */
    protected void setUp() throws Exception {
        super.setUp();
        configManager = ConfigManager.getInstance();
        removeNamespace(NAMESPACE);
        removeNamespace(DB_CONNECTION_FACTORY_NAMESPACE);
    }

    /**
     * Clear the default namespace.
     * @throws Exception Never under normal conditions.
     */
    protected void tearDown() throws Exception {
        removeNamespace(NAMESPACE);
        removeNamespace(DB_CONNECTION_FACTORY_NAMESPACE);
        super.tearDown();
    }


    /**
     * Clear the given namespace.
     * @param namespace the namespace to remove (if it already existed)
     * @throws Exception Never under normal conditions.
     */
    protected void removeNamespace(String namespace) throws Exception {
        if (configManager.existsNamespace(namespace)) {
            configManager.removeNamespace(namespace);
        }
    }


    /**
     * Add the configuration file to the given namespace. If the namespace
     * already existed, remove it first.  It is assumed that the file is XML format.
     * @param namespace the namespace to add
     * @param filename the filename to add
     * @throws Exception Never under normal conditions.
     */
    protected void addNamespace(String namespace, String filename) throws Exception {
        removeNamespace(namespace);
        configManager.add(namespace, filename, ConfigManager.CONFIG_XML_FORMAT);
    }
}
