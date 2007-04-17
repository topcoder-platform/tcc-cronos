/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time.accuracytests;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;
import com.topcoder.timetracker.entry.time.ManagerFactory;
import com.topcoder.timetracker.entry.time.TaskTypeManager;
import com.topcoder.timetracker.entry.time.TimeEntryManager;
import com.topcoder.timetracker.entry.time.TimeStatusManager;

/**
 * <p>
 * Accuracy Unit test cases for ManagerFactory.
 * </p>
 *
 * @author victorsam
 * @version 3.2
 */
public class ManagerFactoryAccuracyTests extends TestCase {
    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     * @throws Exception to JUnit
     *
     */
    protected void setUp() throws Exception {
        AccuracyTestHelper.clearConfig();
        AccuracyTestHelper.loadXMLConfig(AccuracyTestHelper.CONFIG_FILE);
        AccuracyTestHelper.loadXMLConfig(AccuracyTestHelper.AUDIT_CONFIG_FILE);
        AccuracyTestHelper.setUpDataBase();
        AccuracyTestHelper.setUpEJBEnvironment(null, null, null);
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     * @throws Exception to JUnit
     *
     */
    protected void tearDown() throws Exception {
        AccuracyTestHelper.tearDownDataBase();
        AccuracyTestHelper.clearConfig();
    }

    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(ManagerFactoryAccuracyTests.class);
    }

    /**
     * <p>
     * Tests ManagerFactory#getTimeEntryManager() for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetTimeEntryManager() throws Exception {
        TimeEntryManager manager = ManagerFactory.getTimeEntryManager();
        assertNotNull("Failed to get the time entry manager.", manager);
    }

    /**
     * <p>
     * Tests ManagerFactory#getTimeStatusManager() for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetTimeStatusManager() throws Exception {
        TimeStatusManager manager = ManagerFactory.getTimeStatusManager();
        assertNotNull("Failed to get the time status manager.", manager);
    }

    /**
     * <p>
     * Tests ManagerFactory#getTaskTypeManager() for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetTaskTypeManager() throws Exception {
        TaskTypeManager manager = ManagerFactory.getTaskTypeManager();
        assertNotNull("Failed to get the task type manager.", manager);
    }

}