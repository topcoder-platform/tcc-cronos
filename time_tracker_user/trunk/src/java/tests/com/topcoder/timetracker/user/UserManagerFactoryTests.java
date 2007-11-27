/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user;

import java.io.File;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

/**
 * <p>
 * Unit test cases for UserManagerFactory.
 * </p>
 *
 * @author biotrail, enefem21
 * @version 3.2.1
 * @since 3.2
 */
public class UserManagerFactoryTests extends TestCase {

    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     *
     */
    protected void setUp() throws Exception {
        TestHelper.loadXMLConfig(TestHelper.CONFIG_FILE);
        TestHelper.loadXMLConfig(TestHelper.CONFIG_FILE_3_2_1);
        TestHelper.setUpDataBase();
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit
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
        return new TestSuite(UserManagerFactoryTests.class);
    }

    /**
     * <p>
     * Tests UserManagerFactory#getUserManager() for accuracy.
     * </p>
     *
     * <p>
     * It verifies UserManagerFactory#getUserManager() is correct.
     * </p>
     *
     * @throws ConfigurationException
     *             to JUnit
     */
    public void testGetUserManager() throws ConfigurationException {
        UserManager manager1 = UserManagerFactory.getUserManager();
        UserManager manager2 = UserManagerFactory.getUserManager();
        assertNotNull("Failed to get the user manager correctly.", manager1);
        assertSame("Failed to get the user manager correctly.", manager1, manager2);
    }

    /**
     * <p>
     * Tests UserManagerFactory#getUserManager() for failure.
     * </p>
     *
     * <p>
     * It tests the case when the namespace does not exist and expects ConfigurationException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetUserManager_NamespaceNotExist() throws Exception {
        TestHelper.resetUserManagerToNull();
        TestHelper.clearConfigFile("com.topcoder.timetracker.user");
        try {
            UserManagerFactory.getUserManager();
            fail("ConfigurationException expected.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerFactory#getUserManager() for failure.
     * </p>
     *
     * <p>
     * It tests the case when the UserManager class name missing in config file and expects ConfigurationException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetUserManager_UserManagerMissing() throws Exception {
        TestHelper.resetUserManagerToNull();
        TestHelper.clearConfigFile("com.topcoder.timetracker.user");
        TestHelper.loadXMLConfig("test_files" + File.separator + "UserManager_missing.xml");
        try {
            UserManagerFactory.getUserManager();
            fail("ConfigurationException expected.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerFactory#getUserManager() for failure.
     * </p>
     *
     * <p>
     * It tests the case when the UserManager class name is wrong configed in config file and expects
     * ConfigurationException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetUserManager_UserManagerWrongConfig() throws Exception {
        TestHelper.resetUserManagerToNull();
        TestHelper.clearConfigFile("com.topcoder.timetracker.user");
        TestHelper.loadXMLConfig("test_files" + File.separator + "UserManager_wrong_config.xml");
        try {
            UserManagerFactory.getUserManager();
            fail("ConfigurationException expected.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerFactory#getUserManager() for failure.
     * </p>
     *
     * <p>
     * It tests the case when there is an invalid reference in the configuration file and expects
     * ConfigurationException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetUserManager_InvalidReference() throws Exception {
        TestHelper.resetUserManagerToNull();
        TestHelper.clearConfigFile("com.topcoder.timetracker.user");
        TestHelper.loadXMLConfig("test_files" + File.separator + "UserManager_invalid_reference.xml");
        try {
            UserManagerFactory.getUserManager();
            fail("ConfigurationException expected.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerFactory#getUserManager() for failure.
     * </p>
     *
     * <p>
     * It tests the case when the UserManager class name property is not UserManager type in config file and
     * expects ConfigurationException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetUserManager_UserManagerWrongType() throws Exception {
        TestHelper.resetUserManagerToNull();
        TestHelper.clearConfigFile("com.topcoder.timetracker.user");
        TestHelper.loadXMLConfig("test_files" + File.separator + "UserManager_wrong_type.xml");
        try {
            UserManagerFactory.getUserManager();
            fail("ConfigurationException expected.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerFactory#getUserStatusManager() for accuracy.
     * </p>
     * <p>
     * It verifies UserManagerFactory#getUserStatusManager() is correct.
     * </p>
     *
     * @throws ConfigurationException
     *             to JUnit
     * @since 3.2.1
     */
    public void testGetUserStatusManager() throws ConfigurationException {
        TestHelper.resetUserStatusManagerToNull();
        UserStatusManager manager1 = UserManagerFactory.getUserStatusManager();
        UserStatusManager manager2 = UserManagerFactory.getUserStatusManager();
        assertNotNull("Failed to get the user status manager correctly.", manager1);
        assertSame("Failed to get the user status manager correctly.", manager1, manager2);
    }

    /**
     * <p>
     * Tests UserManagerFactory#getUserStatusManager() for failure.
     * </p>
     * <p>
     * It tests the case when the namespace does not exist and expects ConfigurationException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     * @since 3.2.1
     */
    public void testGetUserStatusManager_NamespaceNotExist() throws Exception {
        TestHelper.resetUserStatusManagerToNull();
        TestHelper.clearConfigFile("com.topcoder.timetracker.user");
        try {
            UserManagerFactory.getUserStatusManager();
            fail("ConfigurationException expected.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerFactory#getUserStatusManager() for failure.
     * </p>
     * <p>
     * It tests the case when the UserStatusManager class name missing in config file and expects
     * ConfigurationException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     * @since 3.2.1
     */
    public void testGetUserStatusManager_UserStatusManagerMissing() throws Exception {
        TestHelper.resetUserStatusManagerToNull();
        TestHelper.clearConfigFile("com.topcoder.timetracker.user");
        TestHelper.loadXMLConfig("test_files" + File.separator + "UserStatusManager_missing.xml");
        try {
            UserManagerFactory.getUserStatusManager();
            fail("ConfigurationException expected.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerFactory#getUserStatusManager() for failure.
     * </p>
     *
     * <p>
     * It tests the case when the UserStatusManager class name is wrong configed in config file and expects
     * ConfigurationException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     * @since 3.2.1
     */
    public void testGetUserStatusManager_UserStatusManagerWrongConfig() throws Exception {
        TestHelper.resetUserStatusManagerToNull();
        TestHelper.clearConfigFile("com.topcoder.timetracker.user");
        TestHelper.loadXMLConfig("test_files" + File.separator + "UserStatusManager_wrong_config.xml");
        try {
            UserManagerFactory.getUserStatusManager();
            fail("ConfigurationException expected.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerFactory#getUserStatusManager() for failure.
     * </p>
     *
     * <p>
     * It tests the case when there is an invalid reference in the configuration file and expects
     * ConfigurationException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     * @since 3.2.1
     */
    public void testGetUserStatusManager_InvalidReference() throws Exception {
        TestHelper.resetUserStatusManagerToNull();
        TestHelper.clearConfigFile("com.topcoder.timetracker.user");
        TestHelper.loadXMLConfig("test_files" + File.separator + "UserStatusManager_invalid_reference.xml");
        try {
            UserManagerFactory.getUserStatusManager();
            fail("ConfigurationException expected.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerFactory#getUserStatusManager() for failure.
     * </p>
     *
     * <p>
     * It tests the case when the UserStatusManager class name property is not UserStatusManager type in config
     * file and expects ConfigurationException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     * @since 3.2.1
     */
    public void testGetUserStatusManager_UserStatusManagerWrongType() throws Exception {
        TestHelper.resetUserStatusManagerToNull();
        TestHelper.clearConfigFile("com.topcoder.timetracker.user");
        TestHelper.loadXMLConfig("test_files" + File.separator + "UserStatusManager_wrong_type.xml");
        try {
            UserManagerFactory.getUserStatusManager();
            fail("ConfigurationException expected.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerFactory#getUserTypeManager() for accuracy.
     * </p>
     * <p>
     * It verifies UserManagerFactory#getUserTypeManager() is correct.
     * </p>
     *
     * @throws ConfigurationException
     *             to JUnit
     * @since 3.2.1
     */
    public void testGetUserTypeManager() throws ConfigurationException {
        TestHelper.resetUserTypeManagerToNull();
        UserTypeManager manager1 = UserManagerFactory.getUserTypeManager();
        UserTypeManager manager2 = UserManagerFactory.getUserTypeManager();
        assertNotNull("Failed to get the user type manager correctly.", manager1);
        assertSame("Failed to get the user type manager correctly.", manager1, manager2);
    }

    /**
     * <p>
     * Tests UserManagerFactory#getUserTypeManager() for failure.
     * </p>
     * <p>
     * It tests the case when the namespace does not exist and expects ConfigurationException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     * @since 3.2.1
     */
    public void testGetUserTypeManager_NamespaceNotExist() throws Exception {
        TestHelper.resetUserTypeManagerToNull();
        TestHelper.clearConfigFile("com.topcoder.timetracker.user");
        try {
            UserManagerFactory.getUserTypeManager();
            fail("ConfigurationException expected.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerFactory#getUserTypeManager() for failure.
     * </p>
     * <p>
     * It tests the case when the UserTypeManager class name missing in config file and expects
     * ConfigurationException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     * @since 3.2.1
     */
    public void testGetUserTypeManager_UserTypeManagerMissing() throws Exception {
        TestHelper.resetUserTypeManagerToNull();
        TestHelper.clearConfigFile("com.topcoder.timetracker.user");
        TestHelper.loadXMLConfig("test_files" + File.separator + "UserTypeManager_missing.xml");
        try {
            UserManagerFactory.getUserTypeManager();
            fail("ConfigurationException expected.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerFactory#getUserTypeManager() for failure.
     * </p>
     *
     * <p>
     * It tests the case when the UserTypeManager class name is wrong configed in config file and expects
     * ConfigurationException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     * @since 3.2.1
     */
    public void testGetUserTypeManager_UserTypeManagerWrongConfig() throws Exception {
        TestHelper.resetUserTypeManagerToNull();
        TestHelper.clearConfigFile("com.topcoder.timetracker.user");
        TestHelper.loadXMLConfig("test_files" + File.separator + "UserTypeManager_wrong_config.xml");
        try {
            UserManagerFactory.getUserTypeManager();
            fail("ConfigurationException expected.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerFactory#getUserTypeManager() for failure.
     * </p>
     *
     * <p>
     * It tests the case when there is an invalid reference in the configuration file and expects
     * ConfigurationException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     * @since 3.2.1
     */
    public void testGetUserTypeManager_InvalidReference() throws Exception {
        TestHelper.resetUserTypeManagerToNull();
        TestHelper.clearConfigFile("com.topcoder.timetracker.user");
        TestHelper.loadXMLConfig("test_files" + File.separator + "UserTypeManager_invalid_reference.xml");
        try {
            UserManagerFactory.getUserTypeManager();
            fail("ConfigurationException expected.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserManagerFactory#getUserTypeManager() for failure.
     * </p>
     *
     * <p>
     * It tests the case when the UserTypeManager class name property is not UserTypeManager type in config file
     * and expects ConfigurationException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     * @since 3.2.1
     */
    public void testGetUserTypeManager_UserTypeManagerWrongType() throws Exception {
        TestHelper.resetUserTypeManagerToNull();
        TestHelper.clearConfigFile("com.topcoder.timetracker.user");
        TestHelper.loadXMLConfig("test_files" + File.separator + "UserTypeManager_wrong_type.xml");
        try {
            UserManagerFactory.getUserTypeManager();
            fail("ConfigurationException expected.");
        } catch (ConfigurationException e) {
            // good
        }
    }

}