/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time.failuretests;

import com.topcoder.timetracker.entry.time.ConfigurationException;
import com.topcoder.timetracker.entry.time.ManagerFactory;

import junit.framework.TestCase;

import java.io.File;


/**
 * <p>
 * Failure test cases for ManagerFactory.
 * </p>
 * @author KLW
 * @version 3.2
 */
public class ManagerFactoryFailureTests extends TestCase {
    /**
     * the namespace of the time entry in the cinfiguration file.
     */
    private final String NAMESPACE = "com.topcoder.timetracker.entry.time";

    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     * @throws Exception to JUnit
     *
     */
    protected void setUp() throws Exception {
        FailureTestHelper.clearConfig();
        FailureTestHelper.loadXMLConfig(FailureTestHelper.CONFIG_FILE);
        FailureTestHelper.loadXMLConfig(FailureTestHelper.AUDIT_CONFIG_FILE);
        FailureTestHelper.setUpDataBase();
        FailureTestHelper.setUpEJBEnvironment(null, null, null);
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     * @throws Exception to JUnit
     *
     */
    protected void tearDown() throws Exception {
        FailureTestHelper.tearDownDataBase();
        FailureTestHelper.clearConfig();
    }

    /**
     * <p>
     * Failure tests for ManagerFactory#getTimeEntryManager().
     * </p>
     *
     * <p>
     * It tests the case when the namespace does not exist and expects ConfigurationException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetTimeEntryManager1() throws Exception {
        FailureTestHelper.resetTimeEntryManagerToNull();
        FailureTestHelper.clearConfigFile(NAMESPACE);

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
    public void testGetTimeEntryManager2() throws Exception {
        FailureTestHelper.resetTimeEntryManagerToNull();
        FailureTestHelper.clearConfigFile(NAMESPACE);
        FailureTestHelper.loadXMLConfig("test_files" + File.separator + "failure" + File.separator +
            "TimeEntryManager_missing.xml");

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
    public void testGetTimeEntryManager3() throws Exception {
        FailureTestHelper.resetTimeEntryManagerToNull();
        FailureTestHelper.clearConfigFile(NAMESPACE);
        FailureTestHelper.loadXMLConfig("test_files" + File.separator + "failure" + File.separator +
            "TimeEntryManager_wrong_config.xml");

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
    public void testGetTimeEntryManager4() throws Exception {
        FailureTestHelper.resetTimeEntryManagerToNull();
        FailureTestHelper.clearConfigFile(NAMESPACE);
        FailureTestHelper.loadXMLConfig("test_files" + File.separator + "failure" + File.separator +
            "TimeEntryManager_invalid_reference.xml");

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
    public void testGetTimeEntryManager5() throws Exception {
        FailureTestHelper.resetTimeEntryManagerToNull();
        FailureTestHelper.clearConfigFile("com.topcoder.timetracker.entry.time");
        FailureTestHelper.loadXMLConfig("test_files" + File.separator + "failure" + File.separator +
            "TimeEntryManager_wrong_type.xml");

        try {
            ManagerFactory.getTimeEntryManager();
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
     * It tests the case when the namespace does not exist and expects ConfigurationException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetTimeStatusManager1() throws Exception {
        FailureTestHelper.resetTimeStatusManagerToNull();
        FailureTestHelper.clearConfigFile(NAMESPACE);

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
    public void testGetTimeStatusManager2() throws Exception {
        FailureTestHelper.resetTimeStatusManagerToNull();
        FailureTestHelper.clearConfigFile(NAMESPACE);
        FailureTestHelper.loadXMLConfig("test_files" + File.separator + "failure" + File.separator +
            "TimeStatusManager_missing.xml");

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
    public void testGetTimeStatusManager3() throws Exception {
        FailureTestHelper.resetTimeStatusManagerToNull();
        FailureTestHelper.clearConfigFile(NAMESPACE);
        FailureTestHelper.loadXMLConfig("test_files" + File.separator + "failure" + File.separator +
            "TimeStatusManager_wrong_config.xml");

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
    public void testGetTimeStatusManager4() throws Exception {
        FailureTestHelper.resetTimeStatusManagerToNull();
        FailureTestHelper.clearConfigFile(NAMESPACE);
        FailureTestHelper.loadXMLConfig("test_files" + File.separator + "failure" + File.separator +
            "TimeStatusManager_invalid_reference.xml");

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
    public void testGetTimeStatusManager5() throws Exception {
        FailureTestHelper.resetTimeStatusManagerToNull();
        FailureTestHelper.clearConfigFile(NAMESPACE);
        FailureTestHelper.loadXMLConfig("test_files" + File.separator + "failure" + File.separator +
            "TimeStatusManager_wrong_type.xml");

        try {
            ManagerFactory.getTimeStatusManager();
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
     * It tests the case when the namespace does not exist and expects ConfigurationException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetTaskTypeManager1() throws Exception {
        FailureTestHelper.resetTaskTypeManagerToNull();
        FailureTestHelper.clearConfigFile(NAMESPACE);

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
    public void testGetTaskTypeManager2() throws Exception {
        FailureTestHelper.resetTaskTypeManagerToNull();
        FailureTestHelper.clearConfigFile(NAMESPACE);
        FailureTestHelper.loadXMLConfig("test_files" + File.separator + "failure" + File.separator +
            "TaskTypeManager_missing.xml");

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
    public void testGetTaskTypeManager3() throws Exception {
        FailureTestHelper.resetTaskTypeManagerToNull();
        FailureTestHelper.clearConfigFile(NAMESPACE);
        FailureTestHelper.loadXMLConfig("test_files" + File.separator + "failure" + File.separator +
            "TaskTypeManager_wrong_config.xml");

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
    public void testGetTaskTypeManager4() throws Exception {
        FailureTestHelper.resetTaskTypeManagerToNull();
        FailureTestHelper.clearConfigFile(NAMESPACE);
        FailureTestHelper.loadXMLConfig("test_files" + File.separator + "failure" + File.separator +
            "TaskTypeManager_invalid_reference.xml");

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
    public void testGetTaskTypeManager5() throws Exception {
        FailureTestHelper.resetTaskTypeManagerToNull();
        FailureTestHelper.clearConfigFile(NAMESPACE);
        FailureTestHelper.loadXMLConfig("test_files" + File.separator + "failure" + File.separator +
            "TaskTypeManager_wrong_type.xml");

        try {
            ManagerFactory.getTaskTypeManager();
            fail("ConfigurationException expected.");
        } catch (ConfigurationException e) {
            //good
        }
    }
}
