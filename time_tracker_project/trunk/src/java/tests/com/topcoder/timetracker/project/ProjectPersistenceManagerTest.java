/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.project;

import com.topcoder.timetracker.project.persistence.InformixTimeTrackerProjectPersistence;
import com.topcoder.timetracker.project.persistence.TimeTrackerProjectPersistence;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * Unit tests for ProjectPersistenceManager implementation.
 *
 * @author colau
 * @author TCSDEVELOPER
 * @version 1.1
 *
 * @since 1.0
 */
public class ProjectPersistenceManagerTest extends TestCase {
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
        } catch (NullPointerException e) {
            // good
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
            // good
        }
    }

    /**
     * Test of constructor with non-existing namespace. Expects ConfigurationException.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testConstructor_NonExistNamespace() throws Exception {
        try {
            new ProjectPersistenceManager("non-exist");
            fail("Creates ProjectPersistenceManager with non-existing namespace");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * Test of constructor with bad namespaces (missing properties and values). Expects ConfigurationException.
     */
    public void testConstructor_BadNamespaces() {
        String[] namespaces = TestHelper.BAD_NAMESPACES;

        for (int i = 0; i < namespaces.length; i++) {
            try {
                new ProjectPersistenceManager(namespaces[i]);
                fail("Creates ProjectPersistenceManager with bad namespace");
            } catch (ConfigurationException e) {
                // good
            }
        }
    }

    /**
     * Test of getPersistence method. Verifies if it returns an instance of InformixTimeTrackerProjectPersistence.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public void testGetPersistence() throws Exception {
        ProjectPersistenceManager manager = new ProjectPersistenceManager(TestHelper.NAMESPACE);
        TimeTrackerProjectPersistence persistence = manager.getPersistence();

        assertTrue("Returns an incorrect persistence", persistence instanceof InformixTimeTrackerProjectPersistence);

        persistence.closeConnection();
    }
}
