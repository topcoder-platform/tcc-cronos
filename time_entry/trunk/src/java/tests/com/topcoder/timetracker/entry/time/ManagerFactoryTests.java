/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time;

import java.io.File;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

/**
 * <p>
 * Unit test cases for ManagerFactory.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class ManagerFactoryTests extends TestCase {

    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     * @throws Exception to JUnit
     *
     */
    protected void setUp() throws Exception {
        TestHelper.clearConfig();
        TestHelper.loadXMLConfig(TestHelper.CONFIG_FILE);
        TestHelper.loadXMLConfig(TestHelper.AUDIT_CONFIG_FILE);
        TestHelper.setUpDataBase();
        TestHelper.setUpEJBEnvironment(null, null, null);
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     * @throws Exception to JUnit
     *
     */
    protected void tearDown() throws Exception {
        TestHelper.tearDownDataBase();
        TestHelper.clearConfig();
    }

    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(ManagerFactoryTests.class);
    }

    /**
     * <p>
     * Tests ManagerFactory#getTimeEntryManager() for accuracy.
     * </p>
     *
     * <p>
     * It verifies ManagerFactory#getTimeEntryManager() is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetTimeEntryManager() throws Exception {
        TimeEntryManager manager1 = ManagerFactory.getTimeEntryManager();
        TimeEntryManager manager2 = ManagerFactory.getTimeEntryManager();
        assertNotNull("Failed to get the time entry manager correctly.", manager1);
        assertSame("Failed to get the time entry manager correctly.", manager1, manager2);
    }

    /**
     * <p>
     * Tests ManagerFactory#getTimeEntryManager() for failure.
     * </p>
     *
     * <p>
     * It tests the case when the namespace does not exist and expects ConfigurationException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetTimeEntryManager_NamespaceNotExist() throws Exception {
        TestHelper.resetTimeEntryManagerToNull();
        TestHelper.clearConfigFile("com.topcoder.timetracker.entry.time");
        try {
            ManagerFactory.getTimeEntryManager();
            fail("ConfigurationException expected.");
        } catch (ConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests ManagerFactory#getTimeEntryManager() for failure.
     * </p>
     *
     * <p>
     * It tests the case when the TimeEntryManager class name missing in config file
     * and expects ConfigurationException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetTimeEntryManager_TimeEntryManagerMissing() throws Exception {
        TestHelper.resetTimeEntryManagerToNull();
        TestHelper.clearConfigFile("com.topcoder.timetracker.entry.time");
        TestHelper.loadXMLConfig("test_files" + File.separator + "TimeEntryManager_missing.xml");
        try {
            ManagerFactory.getTimeEntryManager();
            fail("ConfigurationException expected.");
        } catch (ConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests ManagerFactory#getTimeEntryManager() for failure.
     * </p>
     *
     * <p>
     * It tests the case when the TimeEntryManager class name is wrong configed in config file
     * and expects ConfigurationException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetTimeEntryManager_TimeEntryManagerWrongConfig() throws Exception {
        TestHelper.resetTimeEntryManagerToNull();
        TestHelper.clearConfigFile("com.topcoder.timetracker.entry.time");
        TestHelper.loadXMLConfig("test_files" + File.separator + "TimeEntryManager_wrong_config.xml");
        try {
            ManagerFactory.getTimeEntryManager();
            fail("ConfigurationException expected.");
        } catch (ConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests ManagerFactory#getTimeEntryManager() for failure.
     * </p>
     *
     * <p>
     * It tests the case when there is an invalid reference in the configuration file
     * and expects ConfigurationException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetTimeEntryManager_InvalidReference() throws Exception {
        TestHelper.resetTimeEntryManagerToNull();
        TestHelper.clearConfigFile("com.topcoder.timetracker.entry.time");
        TestHelper.loadXMLConfig("test_files" + File.separator + "TimeEntryManager_invalid_reference.xml");
        try {
            ManagerFactory.getTimeEntryManager();
            fail("ConfigurationException expected.");
        } catch (ConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests ManagerFactory#getTimeEntryManager() for failure.
     * </p>
     *
     * <p>
     * It tests the case when the TimeEntryManager class name property is not TimeEntryManager type
     * in config file and expects ConfigurationException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetTimeEntryManager_TimeEntryManagerWrongType() throws Exception {
        TestHelper.resetTimeEntryManagerToNull();
        TestHelper.clearConfigFile("com.topcoder.timetracker.entry.time");
        TestHelper.loadXMLConfig("test_files" + File.separator + "TimeEntryManager_wrong_type.xml");
        try {
            ManagerFactory.getTimeEntryManager();
            fail("ConfigurationException expected.");
        } catch (ConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests ManagerFactory#getTimeStatusManager() for accuracy.
     * </p>
     *
     * <p>
     * It verifies ManagerFactory#getTimeStatusManager() is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetTimeStatusManager() throws Exception {
        TimeStatusManager manager1 = ManagerFactory.getTimeStatusManager();
        TimeStatusManager manager2 = ManagerFactory.getTimeStatusManager();
        assertNotNull("Failed to get the time status manager correctly.", manager1);
        assertSame("Failed to get the time status manager correctly.", manager1, manager2);
    }

    /**
     * <p>
     * Tests ManagerFactory#getTimeStatusManager() for failure.
     * </p>
     *
     * <p>
     * It tests the case when the namespace does not exist and expects ConfigurationException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetTimeStatusManager_NamespaceNotExist() throws Exception {
        TestHelper.resetTimeStatusManagerToNull();
        TestHelper.clearConfigFile("com.topcoder.timetracker.entry.time");
        try {
            ManagerFactory.getTimeStatusManager();
            fail("ConfigurationException expected.");
        } catch (ConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests ManagerFactory#getTimeStatusManager() for failure.
     * </p>
     *
     * <p>
     * It tests the case when the TimeStatusManager class name missing in config file
     * and expects ConfigurationException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetTimeStatusManager_TimeStatusManagerMissing() throws Exception {
        TestHelper.resetTimeStatusManagerToNull();
        TestHelper.clearConfigFile("com.topcoder.timetracker.entry.time");
        TestHelper.loadXMLConfig("test_files" + File.separator + "TimeStatusManager_missing.xml");
        try {
            ManagerFactory.getTimeStatusManager();
            fail("ConfigurationException expected.");
        } catch (ConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests ManagerFactory#getTimeStatusManager() for failure.
     * </p>
     *
     * <p>
     * It tests the case when the TimeStatusManager class name is wrong configed in config file
     * and expects ConfigurationException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetTimeStatusManager_TimeStatusManagerWrongConfig() throws Exception {
        TestHelper.resetTimeStatusManagerToNull();
        TestHelper.clearConfigFile("com.topcoder.timetracker.entry.time");
        TestHelper.loadXMLConfig("test_files" + File.separator + "TimeStatusManager_wrong_config.xml");
        try {
            ManagerFactory.getTimeStatusManager();
            fail("ConfigurationException expected.");
        } catch (ConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests ManagerFactory#getTimeStatusManager() for failure.
     * </p>
     *
     * <p>
     * It tests the case when there is an invalid reference in the configuration file
     * and expects ConfigurationException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetTimeStatusManager_InvalidReference() throws Exception {
        TestHelper.resetTimeStatusManagerToNull();
        TestHelper.clearConfigFile("com.topcoder.timetracker.entry.time");
        TestHelper.loadXMLConfig("test_files" + File.separator + "TimeStatusManager_invalid_reference.xml");
        try {
            ManagerFactory.getTimeStatusManager();
            fail("ConfigurationException expected.");
        } catch (ConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests ManagerFactory#getTimeStatusManager() for failure.
     * </p>
     *
     * <p>
     * It tests the case when the TimeStatusManager class name property is not TimeStatusManager type
     * in config file and expects ConfigurationException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetTimeStatusManager_TimeStatusManagerWrongType() throws Exception {
        TestHelper.resetTimeStatusManagerToNull();
        TestHelper.clearConfigFile("com.topcoder.timetracker.entry.time");
        TestHelper.loadXMLConfig("test_files" + File.separator + "TimeStatusManager_wrong_type.xml");
        try {
            ManagerFactory.getTimeStatusManager();
            fail("ConfigurationException expected.");
        } catch (ConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests ManagerFactory#getTaskTypeManager() for accuracy.
     * </p>
     *
     * <p>
     * It verifies ManagerFactory#getTaskTypeManager() is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetTaskTypeManager() throws Exception {
        TaskTypeManager manager1 = ManagerFactory.getTaskTypeManager();
        TaskTypeManager manager2 = ManagerFactory.getTaskTypeManager();
        assertNotNull("Failed to get the task type manager correctly.", manager1);
        assertSame("Failed to get the task type manager correctly.", manager1, manager2);
    }

    /**
     * <p>
     * Tests ManagerFactory#getTaskTypeManager() for failure.
     * </p>
     *
     * <p>
     * It tests the case when the namespace does not exist and expects ConfigurationException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetTaskTypeManager_NamespaceNotExist() throws Exception {
        TestHelper.resetTaskTypeManagerToNull();
        TestHelper.clearConfigFile("com.topcoder.timetracker.entry.time");
        try {
            ManagerFactory.getTaskTypeManager();
            fail("ConfigurationException expected.");
        } catch (ConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests ManagerFactory#getTaskTypeManager() for failure.
     * </p>
     *
     * <p>
     * It tests the case when the TaskTypeManager class name missing in config file
     * and expects ConfigurationException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetTaskTypeManager_TaskTypeManagerMissing() throws Exception {
        TestHelper.resetTaskTypeManagerToNull();
        TestHelper.clearConfigFile("com.topcoder.timetracker.entry.time");
        TestHelper.loadXMLConfig("test_files" + File.separator + "TaskTypeManager_missing.xml");
        try {
            ManagerFactory.getTaskTypeManager();
            fail("ConfigurationException expected.");
        } catch (ConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests ManagerFactory#getTaskTypeManager() for failure.
     * </p>
     *
     * <p>
     * It tests the case when the TaskTypeManager class name is wrong configed in config file
     * and expects ConfigurationException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetTaskTypeManager_TaskTypeManagerWrongConfig() throws Exception {
        TestHelper.resetTaskTypeManagerToNull();
        TestHelper.clearConfigFile("com.topcoder.timetracker.entry.time");
        TestHelper.loadXMLConfig("test_files" + File.separator + "TaskTypeManager_wrong_config.xml");
        try {
            ManagerFactory.getTaskTypeManager();
            fail("ConfigurationException expected.");
        } catch (ConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests ManagerFactory#getTaskTypeManager() for failure.
     * </p>
     *
     * <p>
     * It tests the case when there is an invalid reference in the configuration file
     * and expects ConfigurationException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetTaskTypeManager_InvalidReference() throws Exception {
        TestHelper.resetTaskTypeManagerToNull();
        TestHelper.clearConfigFile("com.topcoder.timetracker.entry.time");
        TestHelper.loadXMLConfig("test_files" + File.separator + "TaskTypeManager_invalid_reference.xml");
        try {
            ManagerFactory.getTaskTypeManager();
            fail("ConfigurationException expected.");
        } catch (ConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests ManagerFactory#getTaskTypeManager() for failure.
     * </p>
     *
     * <p>
     * It tests the case when the TaskTypeManager class name property is not TaskTypeManager type
     * in config file and expects ConfigurationException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetTaskTypeManager_TaskTypeManagerWrongType() throws Exception {
        TestHelper.resetTaskTypeManagerToNull();
        TestHelper.clearConfigFile("com.topcoder.timetracker.entry.time");
        TestHelper.loadXMLConfig("test_files" + File.separator + "TaskTypeManager_wrong_type.xml");
        try {
            ManagerFactory.getTaskTypeManager();
            fail("ConfigurationException expected.");
        } catch (ConfigurationException e) {
            //good
        }
    }
}