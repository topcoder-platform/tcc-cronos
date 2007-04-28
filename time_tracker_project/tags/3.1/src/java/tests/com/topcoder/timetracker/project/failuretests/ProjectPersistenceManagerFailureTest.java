/*
 * Copyright (c) 2005 TopCoder, Inc., All Rights Reserved
 */
package com.topcoder.timetracker.project.failuretests;

import junit.framework.TestCase;

import com.topcoder.timetracker.project.ConfigurationException;
import com.topcoder.timetracker.project.ProjectPersistenceManager;
import com.topcoder.util.config.ConfigManager;

/**
 * Failure tests for ProjectPersistenceManager implementation.
 *
 * @author dmks
 * @version 1.0
 * @author kr00tki
 * @version 1.1
 */
public class ProjectPersistenceManagerFailureTest extends TestCase {
    /**
     * The namespace of the time tracker project.
     */
    private static final String NAMESPACE = "com.topcoder.timetracker.project";

    /**
     * The configuration file of the time tracker project.
     */
    private static final String INVALID_CONFIG_FILE1 = "failure/TimeTrackerProjectInvalid1.xml";

    /**
     * The configuration file of the time tracker project.
     */
    private static final String INVALID_CONFIG_FILE2 = "failure/TimeTrackerProjectInvalid2.xml";

    /**
     * The configuration file of the time tracker project.
     */
    private static final String INVALID_CONFIG_FILE3 = "failure/TimeTrackerProjectInvalid3.xml";

    /**
     * The namespace of the db connection factory.
     */
    private static final String DB_NAMESPACE = "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl";

    /**
     * Loads the namespaces of time tracker project and db connection factory.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void setUp() throws Exception {
        FailureTestHelper.loadConfig();
    }

    /**
     * Clears all the namespaces.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void tearDown() throws Exception {
        FailureTestHelper.unloadConfig();
    }

    /**
     * Test of constructor with null namespace. Expects NullPointerException.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testConstructor_NullNamespace() throws Exception {
        try {
            new ProjectPersistenceManager(null);
            fail("Creates ProjectPersistenceManager with null namespace");
        } catch (NullPointerException e) {
        }
    }

    /**
     * Test of constructor with empty namespace. Expects IllegalArgumentException.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testConstructor_EmptyNamespace() throws Exception {
        try {
            new ProjectPersistenceManager("");
            fail("Creates ProjectPersistenceManager with empty namespace");
        } catch (IllegalArgumentException e) {
        }
    }

    /**
     * Test of constructor with non-existing namespace. Expects ConfigurationException.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testConstructor_NonExistNamespace() throws Exception {
        // remove the namespace
        ConfigManager.getInstance().removeNamespace(NAMESPACE);

        try {
            new ProjectPersistenceManager(NAMESPACE);
            fail("Creates ProjectPersistenceManager with non-existing namespace");
        } catch (ConfigurationException e) {
        }
    }

    /**
     * Test of constructor with non-existing db namespace. Expects ConfigurationException.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testConstructor_NonExistDbNamespace() throws Exception {
        // remove the db namespace
        ConfigManager.getInstance().removeNamespace(DB_NAMESPACE);

        try {
            new ProjectPersistenceManager(NAMESPACE);
            fail("Creates ProjectPersistenceManager with non-existing db namespace");
        } catch (ConfigurationException e) {
        }
    }

    /**
     * Test of constructor without persistence_class property. Expects ConfigurationException.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testConstructor_NonPersistenceClass() throws Exception {
        // remove the namespace
        ConfigManager.getInstance().removeNamespace(NAMESPACE);

        FailureTestHelper.loadConfig(INVALID_CONFIG_FILE1);

        try {
            new ProjectPersistenceManager(NAMESPACE);
            fail("Creates ProjectPersistenceManager with non-existing persistence_class property");
        } catch (ConfigurationException e) {
            // e.printStackTrace();
        }
    }

    /**
     * Test of constructor with illegal persistence_class property. Expects ConfigurationException.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testConstructor_IllegalPersistenceClass() throws Exception {
        // remove the namespace
        ConfigManager.getInstance().removeNamespace(NAMESPACE);

        FailureTestHelper.loadConfig(INVALID_CONFIG_FILE2);

        try {
            new ProjectPersistenceManager(NAMESPACE);
            fail("Creates ProjectPersistenceManager with illegal persistence_class property");
        } catch (ConfigurationException e) {
            // e.printStackTrace();
        }
    }

    // since 1.1
    /**
     * Test of constructor with illegal project_search_utility_namespace property. Expects ConfigurationException.
     *
     * @throws Exception if any unexpected exception occurs.
     * @since 1.1
     */
    public void testConstructor_InvalidProjectNamespace() throws Exception {
        FailureTestHelper.loadConfig(INVALID_CONFIG_FILE3);

        try {
            new ProjectPersistenceManager(NAMESPACE + ".1");
            fail("Creates ProjectPersistenceManager with illegal persistence_class property");
        } catch (ConfigurationException e) {
            // ok
        }
    }

    /**
     * Test of constructor with illegal client_search_utility_namespace property. Expects ConfigurationException.
     *
     * @throws Exception if any unexpected exception occurs.
     * @since 1.1
     */
    public void testConstructor_InvalidClientNamespace() throws Exception {
        FailureTestHelper.loadConfig(INVALID_CONFIG_FILE3);

        try {
            new ProjectPersistenceManager(NAMESPACE + ".2");
            fail("Creates ProjectPersistenceManager with illegal persistence_class property");
        } catch (ConfigurationException e) {
            // ok
        }
    }
}
