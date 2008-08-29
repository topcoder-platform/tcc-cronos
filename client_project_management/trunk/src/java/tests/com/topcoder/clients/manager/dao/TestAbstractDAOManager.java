/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.manager.dao;

import com.topcoder.clients.manager.ManagerConfigurationException;
import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.defaults.DefaultConfigurationObject;

import junit.framework.TestCase;

/**
 * Unit test cases for abstract class <code>AbstractDAOManager </code>.
 * <p>
 * A DAOClientManager instance will be created to test its super abstract class.
 * </p>
 *
 *
 * @author Chenhong
 * @version 1.0
 *
 */
public class TestAbstractDAOManager extends TestCase {

    /**
     * Represents the AbstractDAOManager instance for testing.
     */
    private AbstractDAOManager manager = null;

    /**
     * Set up the environment.
     *
     * @throws Exception
     *             to junit
     */
    public void setUp() throws Exception {
        Util.clearConfigManager();
        Util.loadConfiguration();

        ConfigurationObject obj = new DefaultConfigurationObject("root");
        obj.setPropertyValue("id_generator_name", "client_id");
        obj.setPropertyValue("logger_name", "System.out");

        ConfigurationObject child = new DefaultConfigurationObject("object_factory_configuration");

        obj.addChild(child);
        obj.setPropertyValue("client_dao", "com.topcoder.clients.manager.MockClientDAO");
        obj.setPropertyValue("client_status_dao", "com.topcoder.clients.manager.MockClientStatusDAO");

        manager = new DAOClientManager(obj);
    }

    /**
     * Clear the ConfigManager.
     *
     * @throws Exception
     *             to junit
     */
    public void tearDown() throws Exception {
        Util.clearConfigManager();
    }

    /**
     * Test method for 'AbstractDAOManager()'.
     *
     * <p>
     * The IDGenerator can not be created, ManagerConfigurationException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testAbstractDAOManager_1() throws Exception {
        Util.clearConfigManager();

        try {
            new DAOClientManager();
            fail("ManagerConfigurationException is expected.");
        } catch (ManagerConfigurationException e) {
            // ok
        }
    }

    /**
     * Test method for 'AbstractDAOManager()'.
     *
     * <p>
     * The method should run without any exception thrown.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testAbstractDAOManager_2() throws Exception {
        manager = new DAOClientManager();

        assertNotNull("The DAOClientManager should be created.", manager);
    }

    /**
     * Test method for 'AbstractDAOManager(ConfigurationObject)'.
     * <p>
     * In this test case, the logName is not set. The log will be created by logName as class name.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testAbstractDAOManagerConfigurationObject_1() throws Exception {
        ConfigurationObject obj = new DefaultConfigurationObject("root");
        obj.setPropertyValue("id_generator_name", "client_id");

        ConfigurationObject child = new DefaultConfigurationObject("object_factory_configuration");

        obj.addChild(child);
        obj.setPropertyValue("client_dao", "com.topcoder.clients.manager.MockClientDAO");
        obj.setPropertyValue("client_status_dao", "com.topcoder.clients.manager.MockClientStatusDAO");

        manager = new DAOClientManager(obj);

        // verify if the log is created.

        assertNotNull("The log should be created.", manager.getLog());

    }

    /**
     * Test method for 'AbstractDAOManager(ConfigurationObject)'.
     * <p>
     * In this test case, the logName is set to empty string,ManagerConfigurationException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testAbstractDAOManagerConfigurationObject_2() throws Exception {
        ConfigurationObject obj = new DefaultConfigurationObject("root");
        obj.setPropertyValue("id_generator_name", "client_id");
        obj.setPropertyValue("logger_name", "  ");

        ConfigurationObject child = new DefaultConfigurationObject("object_factory_configuration");

        obj.addChild(child);
        obj.setPropertyValue("client_dao", "com.topcoder.clients.manager.MockClientDAO");
        obj.setPropertyValue("client_status_dao", "com.topcoder.clients.manager.MockClientStatusDAO");

        try {
            new DAOClientManager(obj);
            fail("ManagerConfigurationException is expected.");
        } catch (ManagerConfigurationException e) {
            // ok
        }
    }

    /*
     * There are many test cases for testing AbstractDAOManager(ConfigurationObject), please refer to
     * TestDAOClientManager class.
     */

    /**
     * Test method for 'getIDGenerator()'.
     * <p>
     * A non-null IDGenerator instance will be returned.
     * </p>
     */
    public void testGetIDGenerator() {
        assertNotNull("The IDGenerator should be returned.", manager.getIDGenerator());
    }

    /**
     * Test method for 'getLog()'.
     * <p>
     * A non-null log should be returned.
     * </p>
     */
    public void testGetLog() {
        assertNotNull("The log should be returned.", manager.getLog());
    }

    /**
     * Test method for 'getConfiguration(String, String, String)'.
     *
     * <p>
     * The filename is empty string, IAE is expected.
     * </p>
     * @throws Exception to junit
     */
    public void testGetConfiguration_1() throws Exception {
        try {
            AbstractDAOManager.getConfiguration("", "namespace", "className");
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Test method for 'getConfiguration(String, String, String)'.
     *
     * <p>
     * The namespace is null, IAE is expected.
     * </p>
     * @throws Exception to junit
     */
    public void testGetConfiguration_2() throws Exception {
        try {
            AbstractDAOManager.getConfiguration("config.properties", null, "className");
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }


    /**
     * Test method for 'getConfiguration(String, String, String)'.
     *
     * <p>
     * The namespace is an empty string, IAE is expected.
     * </p>
     * @throws Exception to junit
     */
    public void testGetConfiguration_3() throws Exception {
        try {
            AbstractDAOManager.getConfiguration("config.properties", " ", "className");
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }


    /**
     * Test method for 'getConfiguration(String, String, String)'.
     *
     * <p>
     * The className is null, IAE is expected.
     * </p>
     * @throws Exception to junit
     */
    public void testGetConfiguration_4() throws Exception {
        try {
            AbstractDAOManager.getConfiguration("config.properties", "namespace", null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Test method for 'getConfiguration(String, String, String)'.
     *
     * <p>
     * The className is empty string, IAE is expected.
     * </p>
     * @throws Exception to junit
     */
    public void testGetConfiguration_5() throws Exception {
        try {
            AbstractDAOManager.getConfiguration("config.properties", "namespace", "  ");
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }


    /**
     * Test method for 'getConfiguration(String, String, String)'.
     *
     * <p>
     * the file can not be located. ManagerConfigurationException is expected.
     * </p>
     * @throws Exception to junit
     */
    public void testGetConfiguration_6() throws Exception {
        try {
            AbstractDAOManager.getConfiguration("doest_1.xml", "namespace", "name");
            fail("ManagerConfigurationException is expected.");
        } catch (ManagerConfigurationException e) {
            // ok
        }
    }

    /**
     * Test method for 'getConfiguration(String, String, String)'.
     *
     * <p>
     * the file can not be located. ManagerConfigurationException is expected.
     * </p>
     * @throws Exception to junit
     */
    public void testGetConfiguration_7() throws Exception {
        try {
            AbstractDAOManager.getConfiguration("config2.properties", "namespace", "name");
            fail("ManagerConfigurationException is expected.");
        } catch (ManagerConfigurationException e) {
            // ok
        }
    }

    /**
     * Test method for 'getConfiguration(String, String, String)'.
     *
     * <p>
     * The file is an error file. ManagerConfigurationException is expected.
     * </p>
     * @throws Exception to junit
     */
    public void testGetConfiguration_8() throws Exception {
        try {
            AbstractDAOManager.getConfiguration("test_files/error.properties", "namespace", "name");
            fail("ManagerConfigurationException is expected.");
        } catch (ManagerConfigurationException e) {
            e.printStackTrace();
            // ok
        }
    }


    /**
     * Test method for 'getConfiguration(String, String, String)'.
     *
     * <p>
     * There is no ConfigurationObject with name ProjectManager. ManagerConfigurationException is expected.
     * </p>
     * @throws Exception to junit
     */
    public void testGetConfiguration_9() throws Exception {
        try {
            AbstractDAOManager.getConfiguration("test_files/config.properties", "doesnotExist", "name");
            fail("ManagerConfigurationException is expected.");
        } catch (ManagerConfigurationException e) {
            // ok
        }
    }


    /**
     * Test method for 'getConfiguration(String, String, String)'.
     *
     * <p>
     * the file format is not valid. ManagerConfigurationException is expected.
     * </p>
     * @throws Exception to junit
     */
    public void testGetConfiguration_10() throws Exception {
        try {
            AbstractDAOManager.getConfiguration("doest_1.abc", "namespace", "name");
            fail("ManagerConfigurationException is expected.");
        } catch (ManagerConfigurationException e) {
            // ok
        }
    }

}
