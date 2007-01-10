/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.project.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.timetracker.project.ProjectPersistenceManager;
import com.topcoder.timetracker.project.persistence.InformixTimeTrackerProjectPersistence;


/**
 * <p>
 * This class contains the accuracy unit tests for ProjectPersistenceManager.java.
 * </p>
 *
 * @author PE
 * @version 1.0
 */
public class ProjectPersistenceManagerTest extends TestCase {
    /** Creates an instance of ProjectPersistenceManager for testing. */
    private ProjectPersistenceManager persistenceManager = null;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     *
     * @throws Exception throw to JUnit
     */
    protected void setUp() throws Exception {
        Helper.clearConfig();
        Helper.addConfig();
    }

    /**
     * <p>
     * Clear the test environment.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        Helper.clearConfig();
    }

    /**
     * <p>
     * Creates a test suite of the tests contained in this class.
     * </p>
     *
     * @return a test suite of the tests contained in this class.
     */
    public static Test suite() {
        return new TestSuite(ProjectPersistenceManagerTest.class);
    }

    /**
     * <p>
     * Tests constructor.
     * </p>
     *
     * @throws Exception throw to JUnit
     */
    public void testConstructorAccuracy() throws Exception {
        persistenceManager = new ProjectPersistenceManager(Helper.NAMESPACE);
        assertNotNull("error happens in the constructor", persistenceManager);
    }

    /**
     * <p>
     * Tests accuracy of getPersistence() method.
     * </p>
     *
     * @throws Exception throw to JUnit
     */
    public void testGetPersistence() throws Exception {
        persistenceManager = new ProjectPersistenceManager(Helper.NAMESPACE);
        assertNotNull("The persistence instance is not null", persistenceManager.getPersistence());
        assertEquals(InformixTimeTrackerProjectPersistence.class, persistenceManager.getPersistence().getClass());
    }
}
