/*
 * ProjectPersistenceManagerTest.java
 *
 * Copyright (c) 2005 TopCoder, Inc., All Rights Reserved
 */
package com.topcoder.timetracker.project;

import com.topcoder.timetracker.project.persistence.InformixTimeTrackerProjectPersistence;

import com.topcoder.util.config.ConfigManager;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.File;

import java.util.Iterator;


/**
 * Unit tests for ProjectPersistenceManager implementation.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ProjectPersistenceManagerTest extends TestCase {
    /**
     * The namespace of the time tracker project.
     */
    private static final String NAMESPACE = "com.topcoder.timetracker.project";

    /**
     * The configuration file of the time tracker project.
     */
    private static final String CONFIG_FILE = "test_files/timetrackerproject.xml";

    /**
     * The namespace of the db connection factory.
     */
    private static final String DB_NAMESPACE = "com.topcoder.db.connectionfactory";

    /**
     * The configuration file of the db connection factory.
     */
    private static final String DB_CONFIG_FILE = "test_files/dbfactory.xml";

    /**
     * Creates a test suite for the tests in this test case.
     *
     * @return a TestSuite for this test case
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(ProjectPersistenceManagerTest.class);

        return suite;
    }

    /**
     * Loads the namespaces of time tracker project and db connection factory.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void setUp() throws Exception {
        TestHelper.loadConfig();
    }

    /**
     * Clears all the namespaces.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void tearDown() throws Exception {
        TestHelper.unloadConfig();
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
        } catch (NullPointerException e) {}
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
        } catch (IllegalArgumentException e) {}
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
        } catch (ConfigurationException e) {}
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
        } catch (ConfigurationException e) {}
    }

    /**
     * Test of getPersistence method. Verifies if it returns an instance of InformixTimeTrackerProjectPersistence.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testGetPersistence() throws Exception {
        ProjectPersistenceManager manager = new ProjectPersistenceManager(NAMESPACE);

        assertTrue("Returns an incorrect persistence",
            manager.getPersistence() instanceof InformixTimeTrackerProjectPersistence);
    }
}
