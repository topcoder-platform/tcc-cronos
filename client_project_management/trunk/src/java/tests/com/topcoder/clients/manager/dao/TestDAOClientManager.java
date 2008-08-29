/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.manager.dao;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

import com.topcoder.clients.manager.ClientEntityNotFoundException;
import com.topcoder.clients.manager.ClientManagerException;
import com.topcoder.clients.manager.ManagerConfigurationException;
import com.topcoder.clients.manager.MockClientDAO;
import com.topcoder.clients.manager.MockClientStatusDAO;
import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.ClientStatus;
import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.defaults.DefaultConfigurationObject;
import com.topcoder.search.builder.filter.EqualToFilter;

import junit.framework.TestCase;

/**
 * Unit test cases for class <code>DAOClientManager </code>.
 *
 * @author Chenhong
 * @version 1.0
 *
 */
public class TestDAOClientManager extends TestCase {
    /**
     * Represents the DAOClientManager instance for testing.
     */
    private DAOClientManager manager = null;

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
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.DAOClientManager()'.
     * <p>
     * In this test case, the DAOClientManager instance will be created.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testDAOClientManager() throws Exception {
        manager = new DAOClientManager();
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.DAOClientManager(ConfigurationObject)'.
     * <p>
     * In this test case, the ConfigurationObject is valid, DAOClientManager will be created.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testDAOClientManagerConfigurationObject() throws Exception {
        ConfigurationObject obj = new DefaultConfigurationObject("root");
        obj.setPropertyValue("id_generator_name", "client_id");
        obj.setPropertyValue("logger_name", "logName");

        ConfigurationObject child = new DefaultConfigurationObject("object_factory_configuration");

        obj.addChild(child);

        obj.setPropertyValue("client_dao", "com.topcoder.clients.manager.MockClientDAO");
        obj.setPropertyValue("client_status_dao", "com.topcoder.clients.manager.MockClientStatusDAO");

        manager = new DAOClientManager(obj);
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.DAOClientManager(ConfigurationObject)'.
     * <p>
     * In this test case, the ConfigurationObject is null, IAE is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testDAOClientManagerConfigurationObject_2() throws Exception {
        try {
            new DAOClientManager(null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.DAOClientManager(ConfigurationObject)'.
     * <p>
     * In this test case, the IDGenerator instance can not be created. ManagerConfigurationException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testDAOClientManagerConfigurationObject_Failure_1() throws Exception {
        ConfigurationObject obj = new DefaultConfigurationObject("root");
        obj.setPropertyValue("logger_name", "logName");
        obj.setPropertyValue("id_generator_name", "client_id_notExisted");

        ConfigurationObject child = new DefaultConfigurationObject("object_factory_configuration");

        obj.addChild(child);

        obj.setPropertyValue("client_dao", "com.topcoder.clients.manager.MockClientDAO");
        obj.setPropertyValue("client_status_dao", "com.topcoder.clients.manager.MockClientStatusDAO");

        try {
            manager = new DAOClientManager(obj);
            fail(" ManagerConfigurationException is expected.");
        } catch (ManagerConfigurationException e) {

            // ok
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.DAOClientManager(ConfigurationObject)'.
     * <p>
     * In this test case, no configuration for id_generator_name. ManagerConfigurationException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testDAOClientManagerConfigurationObject_Failure_2() throws Exception {
        ConfigurationObject obj = new DefaultConfigurationObject("root");
        obj.setPropertyValue("logger_name", "logName");

        ConfigurationObject child = new DefaultConfigurationObject("object_factory_configuration");

        obj.addChild(child);

        obj.setPropertyValue("client_dao", "com.topcoder.clients.manager.MockClientDAO");
        obj.setPropertyValue("client_status_dao", "com.topcoder.clients.manager.MockClientStatusDAO");

        try {
            manager = new DAOClientManager(obj);
            fail(" ManagerConfigurationException is expected.");
        } catch (ManagerConfigurationException e) {

            // ok
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.DAOClientManager(ConfigurationObject)'.
     * <p>
     * In this test case, the logName is empty string. ManagerConfigurationException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testDAOClientManagerConfigurationObject_Failure_3() throws Exception {
        ConfigurationObject obj = new DefaultConfigurationObject("root");
        obj.setPropertyValue("id_generator_name", "client_id");
        obj.setPropertyValue("logger_name", "");

        ConfigurationObject child = new DefaultConfigurationObject("object_factory_configuration");

        obj.addChild(child);

        obj.setPropertyValue("client_dao", "com.topcoder.clients.manager.MockClientDAO");
        obj.setPropertyValue("client_status_dao", "com.topcoder.clients.manager.MockClientStatusDAO");

        try {
            manager = new DAOClientManager(obj);
            fail(" ManagerConfigurationException is expected.");
        } catch (ManagerConfigurationException e) {

            // ok
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.DAOClientManager(ConfigurationObject)'.
     * <p>
     * In this test case, the logName is not a String. ManagerConfigurationException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testDAOClientManagerConfigurationObject_Failure_4() throws Exception {
        ConfigurationObject obj = new DefaultConfigurationObject("root");
        obj.setPropertyValue("id_generator_name", "client_id");
        obj.setPropertyValue("logger_name", new Long(10));

        ConfigurationObject child = new DefaultConfigurationObject("object_factory_configuration");

        obj.addChild(child);

        obj.setPropertyValue("client_dao", "com.topcoder.clients.manager.MockClientDAO");
        obj.setPropertyValue("client_status_dao", "com.topcoder.clients.manager.MockClientStatusDAO");

        try {
            manager = new DAOClientManager(obj);
            fail(" ManagerConfigurationException is expected.");
        } catch (ManagerConfigurationException e) {

            // ok
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.DAOClientManager(ConfigurationObject)'.
     * <p>
     * In this test case, no object_factory_configuration child. ManagerConfigurationException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testDAOClientManagerConfigurationObject_Failure_5() throws Exception {
        ConfigurationObject obj = new DefaultConfigurationObject("root");
        obj.setPropertyValue("id_generator_name", "client_id");
        obj.setPropertyValue("logger_name", "System.out");

        obj.setPropertyValue("client_dao", "com.topcoder.clients.manager.MockClientDAO");
        obj.setPropertyValue("client_status_dao", "com.topcoder.clients.manager.MockClientStatusDAO");

        try {
            manager = new DAOClientManager(obj);
            fail(" ManagerConfigurationException is expected.");
        } catch (ManagerConfigurationException e) {

            // ok
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.DAOClientManager(ConfigurationObject)'.
     * <p>
     * In this test case, the client_dao property value is not valid. ManagerConfigurationException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testDAOClientManagerConfigurationObject_Failure_6() throws Exception {
        ConfigurationObject obj = new DefaultConfigurationObject("root");
        obj.setPropertyValue("id_generator_name", "client_id");
        obj.setPropertyValue("logger_name", "System.out");

        ConfigurationObject child = new DefaultConfigurationObject("object_factory_configuration");

        obj.addChild(child);

        obj.setPropertyValue("client_dao", "com.topcoder.clients.manager.MockClientDAO.notValid");
        obj.setPropertyValue("client_status_dao", "com.topcoder.clients.manager.MockClientStatusDAO");

        try {
            manager = new DAOClientManager(obj);
            fail(" ManagerConfigurationException is expected.");
        } catch (ManagerConfigurationException e) {

            // ok
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.DAOClientManager(ConfigurationObject)'.
     * <p>
     * In this test case, the client_dao property value is not valid. ManagerConfigurationException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testDAOClientManagerConfigurationObject_Failure_7() throws Exception {
        ConfigurationObject obj = new DefaultConfigurationObject("root");
        obj.setPropertyValue("id_generator_name", "client_id");
        obj.setPropertyValue("logger_name", "System.out");

        ConfigurationObject child = new DefaultConfigurationObject("object_factory_configuration");

        obj.addChild(child);

        obj.setPropertyValue("client_dao", new Long(10));
        obj.setPropertyValue("client_status_dao", "com.topcoder.clients.manager.MockClientStatusDAO");

        try {
            manager = new DAOClientManager(obj);
            fail(" ManagerConfigurationException is expected.");
        } catch (ManagerConfigurationException e) {

            // ok
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.DAOClientManager(ConfigurationObject)'.
     * <p>
     * In this test case, the client_dao property value is not valid. ManagerConfigurationException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testDAOClientManagerConfigurationObject_Failure_8() throws Exception {
        ConfigurationObject obj = new DefaultConfigurationObject("root");
        obj.setPropertyValue("id_generator_name", "client_id");
        obj.setPropertyValue("logger_name", "System.out");

        ConfigurationObject child = new DefaultConfigurationObject("object_factory_configuration");

        obj.addChild(child);

        obj.setPropertyValue("client_status_dao", "com.topcoder.clients.manager.MockClientStatusDAO");

        try {
            manager = new DAOClientManager(obj);
            fail(" ManagerConfigurationException is expected.");
        } catch (ManagerConfigurationException e) {

            // ok
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.DAOClientManager(ConfigurationObject)'.
     * <p>
     * In this test case, the client_dao property value is not valid. ManagerConfigurationException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testDAOClientManagerConfigurationObject_Failure_9() throws Exception {
        ConfigurationObject obj = new DefaultConfigurationObject("root");
        obj.setPropertyValue("id_generator_name", "client_id");
        obj.setPropertyValue("logger_name", "System.out");

        ConfigurationObject child = new DefaultConfigurationObject("object_factory_configuration");

        obj.addChild(child);
        obj.setPropertyValue("client_dao", "com.topcoder.clients.manager.MockClientStatusDAO");
        obj.setPropertyValue("client_status_dao", "com.topcoder.clients.manager.MockClientStatusDAO");

        try {
            manager = new DAOClientManager(obj);
            fail(" ManagerConfigurationException is expected.");
        } catch (ManagerConfigurationException e) {

            // ok
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.DAOClientManager(ConfigurationObject)'.
     * <p>
     * In this test case, the client_status_dao property value is not valid. ManagerConfigurationException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testDAOClientManagerConfigurationObject_Failure_10() throws Exception {
        ConfigurationObject obj = new DefaultConfigurationObject("root");
        obj.setPropertyValue("id_generator_name", "client_id");
        obj.setPropertyValue("logger_name", "System.out");

        ConfigurationObject child = new DefaultConfigurationObject("object_factory_configuration");

        obj.addChild(child);
        obj.setPropertyValue("client_dao", "com.topcoder.clients.manager.MockClientDAO");
        obj.setPropertyValue("client_status_dao", "com.topcoder.clients.manager.MockClientStatusDAO.invalid");

        try {
            manager = new DAOClientManager(obj);
            fail(" ManagerConfigurationException is expected.");
        } catch (ManagerConfigurationException e) {

            // ok
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.DAOClientManager(ConfigurationObject)'.
     * <p>
     * In this test case, the client_status_dao property value is not valid. ManagerConfigurationException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testDAOClientManagerConfigurationObject_Failure_11() throws Exception {
        ConfigurationObject obj = new DefaultConfigurationObject("root");
        obj.setPropertyValue("id_generator_name", "client_id");
        obj.setPropertyValue("logger_name", "System.out");

        ConfigurationObject child = new DefaultConfigurationObject("object_factory_configuration");

        obj.addChild(child);
        obj.setPropertyValue("client_dao", "com.topcoder.clients.manager.MockClientDAO");
        obj.setPropertyValue("client_status_dao", "com.topcoder.clients.manager.MockClientDAO");

        try {
            manager = new DAOClientManager(obj);
            fail(" ManagerConfigurationException is expected.");
        } catch (ManagerConfigurationException e) {

            // ok
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.DAOClientManager(ConfigurationObject)'.
     * <p>
     * In this test case, the client_status_dao property value is not valid. ManagerConfigurationException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testDAOClientManagerConfigurationObject_Failure_12() throws Exception {
        ConfigurationObject obj = new DefaultConfigurationObject("root");
        obj.setPropertyValue("id_generator_name", "client_id");
        obj.setPropertyValue("logger_name", "System.out");

        ConfigurationObject child = new DefaultConfigurationObject("object_factory_configuration");

        obj.addChild(child);
        obj.setPropertyValue("client_dao", "com.topcoder.clients.manager.MockClientDAO");
        obj.setPropertyValue("client_status_dao", "     ");

        try {
            manager = new DAOClientManager(obj);
            fail(" ManagerConfigurationException is expected.");
        } catch (ManagerConfigurationException e) {

            // ok
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.DAOClientManager(String, String)'.
     * <p>
     * In this test case, the parameters are valid, a DAOClientManager should be created.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testDAOClientManagerStringString() throws Exception {

        String fileName = "test_files/daoclient.properties";
        String namespace = "daoClient";

        manager = new DAOClientManager(fileName, namespace);

        assertNotNull("The DAOClientManager instance should be created.", manager);
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.DAOClientManager(String, String)'.
     * <p>
     * In this test case, the filename is empty string, IAE is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testDAOClientManagerStringString_Failure_1() throws Exception {
        try {
            new DAOClientManager("", "namespace");
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.DAOClientManager(String, String)'.
     * <p>
     * In this test case, the namespace is null, IAE is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testDAOClientManagerStringString_Failure_2() throws Exception {
        try {
            new DAOClientManager("abc", null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {

            // ok
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.DAOClientManager(String, String)'.
     * <p>
     * In this test case, the namespace is empty string, IAE is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testDAOClientManagerStringString_Failure_3() throws Exception {
        try {
            new DAOClientManager("abc", "  ");
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {

            // ok
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.DAOClientManager(String, String)'.
     * <p>
     * In this test case, the file is not valid, ManagerConfigurationException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testDAOClientManagerStringString_Failure_4() throws Exception {
        try {
            new DAOClientManager("abc", "namespace");
            fail("ManagerConfigurationException is expected.");
        } catch (ManagerConfigurationException e) {
            // ok
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.DAOClientManager(String, String)'.
     * <p>
     * The namespace does not exist, ManagerConfigurationException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testDAOClientManagerStringString_Failure_5() throws Exception {
        try {
            new DAOClientManager("test_files/config.properties", "namespace");
            fail("ManagerConfigurationException is expected.");
        } catch (ManagerConfigurationException e) {
            // ok
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.createClient(Client)'.
     * <p>
     * In this test case, the client is null. IAE is expected.
     * <p>
     *
     * @throws Exception
     *             to junit
     */
    public void testCreateClient_Failure_1() throws Exception {
        try {
            manager.createClient(null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {

            // ok
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.createClient(Client)'.
     * <p>
     * In this test case, startDate is after endDate. IAE is expected.
     * <p>
     *
     * @throws Exception
     *             to junit
     */
    public void testCreateClient_Failure_2() throws Exception {
        Client client = new Client();
        client.setStartDate(new Date(System.currentTimeMillis() + 10000000000L));
        client.setEndDate(new Date(System.currentTimeMillis() - 10000000L));
        client.setName("name");
        client.setDeleted(false);

        try {
            manager.createClient(client);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {

            // ok
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.createClient(Client)'.
     * <p>
     * In this test case, the name is set null. IAE is expected.
     * <p>
     *
     * @throws Exception
     *             to junit
     */
    public void testCreateClient_Failure_3() throws Exception {
        Client client = new Client();
        client.setStartDate(new Date(System.currentTimeMillis() - 10000000000L));
        client.setEndDate(new Date(System.currentTimeMillis() + 10000000L));
        client.setName(null);
        client.setDeleted(false);

        try {
            manager.createClient(client);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {

            // ok
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.createClient(Client)'.
     * <p>
     * In this test case, the name is set to empty string. IAE is expected.
     * <p>
     *
     * @throws Exception
     *             to junit
     */
    public void testCreateClient_Failure_4() throws Exception {
        Client client = new Client();
        client.setStartDate(new Date(System.currentTimeMillis() - 10000000000L));
        client.setEndDate(new Date(System.currentTimeMillis() + 10000000L));
        client.setName("  ");
        client.setDeleted(false);

        try {
            manager.createClient(client);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {

            // ok
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.createClient(Client)'.
     * <p>
     * In this test case, isDelete is set to true. IAE is expected.
     * <p>
     *
     * @throws Exception
     *             to junit
     */
    public void testCreateClient_Failure_5() throws Exception {
        Client client = new Client();
        client.setStartDate(new Date(System.currentTimeMillis() - 10000000000L));
        client.setEndDate(new Date(System.currentTimeMillis() + 10000000L));
        client.setName("name");
        client.setDeleted(true);

        try {
            manager.createClient(client);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {

            // ok
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.createClient(Client)'.
     * <p>
     * In this test case, startDate is not set. IAE is expected.
     * <p>
     *
     * @throws Exception
     *             to junit
     */
    public void testCreateClient_Failure_5_2() throws Exception {
        Client client = new Client();
        client.setStartDate(null);
        client.setEndDate(new Date(System.currentTimeMillis() + 10000000L));
        client.setName("name");
        client.setDeleted(false);

        try {
            manager.createClient(client);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {

            // ok
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.createClient(Client)'.
     * <p>
     * In this test case, endDate is not set. IAE is expected.
     * <p>
     *
     * @throws Exception
     *             to junit
     */
    public void testCreateClient_Failure_5_3() throws Exception {
        Client client = new Client();
        client.setStartDate(new Date());
        client.setEndDate(null);
        client.setName("name");
        client.setDeleted(false);

        try {
            manager.createClient(client);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {

            // ok
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.createClient(Client)'.
     * <p>
     * In this test case, DAOException will be thrown. ClientManagerException is expected.
     * <p>
     *
     * @throws Exception
     *             to junit
     */
    public void testCreateClient_Failure_6() throws Exception {
        Client client = new Client();
        client.setStartDate(new Date(System.currentTimeMillis() - 10000000000L));
        client.setEndDate(new Date(System.currentTimeMillis() + 10000000L));
        client.setName("DAOException");
        client.setDeleted(false);

        try {
            manager.createClient(client);
            fail("ClientManagerException is expected.");
        } catch (ClientManagerException e) {

            // ok
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.createClient(Client)'.
     * <p>
     * In this test case, DAOConfigurationException will be thrown. ClientManagerException is expected.
     * <p>
     *
     * @throws Exception
     *             to junit
     */
    public void testCreateClient_Failure_7() throws Exception {
        Client client = new Client();
        client.setStartDate(new Date(System.currentTimeMillis() - 10000000000L));
        client.setEndDate(new Date(System.currentTimeMillis() + 10000000L));
        client.setName("DAOConfigurationException");
        client.setDeleted(false);

        try {
            manager.createClient(client);
            fail("ClientManagerException is expected.");
        } catch (ClientManagerException e) {

            // ok
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.createClient(Client)'.
     * <p>
     * In this test case, ClassCastException will be thrown. ClientManagerException is expected.
     * <p>
     *
     * @throws Exception
     *             to junit
     */
    public void testCreateClient_Failure_8() throws Exception {
        Client client = new Client();
        client.setStartDate(new Date(System.currentTimeMillis() - 10000000000L));
        client.setEndDate(new Date(System.currentTimeMillis() + 10000000L));
        client.setName("ClassCastException");
        client.setDeleted(false);
        client.setId(999);

        try {
            manager.createClient(client);
            fail("ClientManagerException is expected.");
        } catch (ClientManagerException e) {
            // ok
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.createClient(Client)'.
     * <p>
     * In this test case, the Client should be saved.
     * <p>
     *
     * @throws Exception
     *             to junit
     */
    public void testCreateClient_Acc() throws Exception {
        Client client = new Client();
        client.setStartDate(new Date(System.currentTimeMillis() - 10000000000L));
        client.setEndDate(new Date(System.currentTimeMillis() + 10000000L));
        client.setName("ok");
        client.setDeleted(false);

        Client back = manager.createClient(client);

        assertEquals("The modify user should be 'saved'.", "saved", back.getModifyUsername());
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.updateClient(Client)'.
     * <p>
     * If the client is null, IAE is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testUpdateClient_Failure_1() throws Exception {
        try {
            manager.updateClient(null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {

            // ok
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.updateClient(Client)'.
     * <p>
     * If the client is not valid, IAE is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testUpdateClient_Failure_2() throws Exception {
        try {
            Client client = new Client();
            client.setStartDate(new Date(System.currentTimeMillis() + 10000000000L));
            client.setEndDate(new Date(System.currentTimeMillis() - 10000000L));
            client.setName("name");
            client.setDeleted(false);

            manager.updateClient(client);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {

            // ok
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.updateClient(Client)'.
     * <p>
     * If the client is not valid, IAE is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testUpdateClient_Failure_3() throws Exception {
        try {
            Client client = new Client();
            client.setStartDate(new Date(System.currentTimeMillis() - 10000000000L));
            client.setEndDate(new Date(System.currentTimeMillis() + 10000000L));
            client.setName("  ");
            client.setDeleted(false);

            manager.updateClient(client);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {

            // ok
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.updateClient(Client)'.
     * <p>
     * If the client is not valid, IAE is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testUpdateClient_Failure_4() throws Exception {
        try {
            Client client = new Client();
            client.setStartDate(new Date(System.currentTimeMillis() - 10000000000L));
            client.setEndDate(new Date(System.currentTimeMillis() + 10000000L));
            client.setName("name");
            client.setDeleted(true);

            manager.updateClient(client);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {

            // ok
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.updateClient(Client)'.
     * <p>
     * DAOConfigurationException will be thrown, ClientManagerException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testUpdateClient_Failure_5() throws Exception {
        try {
            Client client = new Client();
            client.setStartDate(new Date(System.currentTimeMillis() - 10000000000L));
            client.setEndDate(new Date(System.currentTimeMillis() + 10000000L));
            client.setName("DAOConfigurationException");
            client.setDeleted(false);
            client.setId(1L);

            manager.updateClient(client);
            fail("ClientManagerException is expected.");
        } catch (ClientManagerException e) {

            // ok
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.updateClient(Client)'.
     * <p>
     * DAOConfigurationException will be thrown, ClientManagerException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testUpdateClient_Failure_5_2() throws Exception {
        try {
            Client client = new Client();
            client.setStartDate(new Date(System.currentTimeMillis() - 10000000000L));
            client.setEndDate(new Date(System.currentTimeMillis() + 10000000L));
            client.setName("DAOConfigurationException");
            client.setDeleted(false);
            client.setId(1001L);

            manager.updateClient(client);
            fail("ClientManagerException is expected.");
        } catch (ClientManagerException e) {

            // ok
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.updateClient(Client)'.
     * <p>
     * DAOException will be thrown, ClientManagerException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testUpdateClient_Failure_6() throws Exception {
        try {
            Client client = new Client();
            client.setStartDate(new Date(System.currentTimeMillis() - 10000000000L));
            client.setEndDate(new Date(System.currentTimeMillis() + 10000000L));
            client.setName("DAOException");
            client.setDeleted(false);
            client.setId(1L);

            manager.updateClient(client);
            fail("ClientManagerException is expected.");
        } catch (ClientManagerException e) {
            // ok
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.updateClient(Client)'.
     * <p>
     * DAOException will be thrown, ClientManagerException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testUpdateClient_Failure_6_2() throws Exception {
        try {
            Client client = new Client();
            client.setStartDate(new Date(System.currentTimeMillis() - 10000000000L));
            client.setEndDate(new Date(System.currentTimeMillis() + 10000000L));
            client.setName("DAOException");
            client.setDeleted(false);
            client.setId(10001L);

            manager.updateClient(client);
            fail("ClientManagerException is expected.");
        } catch (ClientManagerException e) {
            // ok
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.updateClient(Client)'.
     * <p>
     * the client does not exist in the persistence, ClientEntityNotFoundException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testUpdateClient_Failure_7() throws Exception {
        try {
            Client client = new Client();
            client.setStartDate(new Date(System.currentTimeMillis() - 10000000000L));
            client.setEndDate(new Date(System.currentTimeMillis() + 10000000L));
            client.setName("DAOException");
            client.setDeleted(false);
            client.setId(2L);

            manager.updateClient(client);
            fail("ClientEntityNotFoundException is expected.");
        } catch (ClientEntityNotFoundException e) {
            // ok
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.updateClient(Client)'.
     * <p>
     * the client does not exist in the persistence, ClientEntityNotFoundException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testUpdateClient_Failure_8() throws Exception {
        try {
            Client client = new Client();
            client.setStartDate(new Date(System.currentTimeMillis() - 10000000000L));
            client.setEndDate(new Date(System.currentTimeMillis() + 10000000L));
            client.setName("DAOException");
            client.setDeleted(false);
            client.setId(-2L);

            manager.updateClient(client);
            fail("ClientEntityNotFoundException is expected.");
        } catch (ClientEntityNotFoundException e) {
            // ok
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.updateClient(Client)'.
     * <p>
     * The client does not exist in the persistence, ClientEntityNotFoundException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testUpdateClient_Failure_8_2() throws Exception {
        try {
            Client client = new Client();
            client.setStartDate(new Date(System.currentTimeMillis() - 10000000000L));
            client.setEndDate(new Date(System.currentTimeMillis() + 10000000L));
            client.setName("DAOException");
            client.setDeleted(false);
            client.setId(1000L);

            manager.updateClient(client);
            fail("ClientEntityNotFoundException is expected.");
        } catch (ClientEntityNotFoundException e) {
            // ok
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.updateClient(Client)'.
     * <p>
     * ClassCastException will be thrown, ClientManagerException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testUpdateClient_Failure_9() throws Exception {
        try {
            Client client = new Client();
            client.setStartDate(new Date(System.currentTimeMillis() - 10000000000L));
            client.setEndDate(new Date(System.currentTimeMillis() + 10000000L));
            client.setName("ClassCastException");
            client.setDeleted(false);
            client.setId(1L);

            manager.updateClient(client);
            fail("ClientManagerException is expected.");
        } catch (ClientManagerException e) {
            // ok
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.updateClient(Client)'.
     * <p>
     * The client should be updated.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testUpdateClient_acc() throws Exception {

        Client client = new Client();
        client.setStartDate(new Date(System.currentTimeMillis() - 10000000000L));
        client.setEndDate(new Date(System.currentTimeMillis() + 10000000L));
        client.setName("update");
        client.setDeleted(false);
        client.setId(1L);

        Client back = manager.updateClient(client);

        assertEquals("The modify user should be 'saved'", "saved", back.getModifyUsername());
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.deleteClient(Client)'.
     * <p>
     * The client is null, IAE is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testDeleteClient_Failure_1() throws Exception {
        try {
            manager.deleteClient(null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {

            // ok
        }

    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.deleteClient(Client)'.
     * <p>
     * The client has deleted true, IAE is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testDeleteClient_Failure_2() throws Exception {
        try {
            Client client = new Client();
            client.setDeleted(true);
            manager.deleteClient(client);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {

            // ok
        }

    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.deleteClient(Client)'.
     * <p>
     * EntityNotFoundException is thrown, ClientEntityNotFoundException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testDeleteClient_Failure_3() throws Exception {
        try {
            Client client = new Client();
            client.setName("entityNotFound");
            client.setDeleted(false);

            manager.deleteClient(client);

            fail("ClientEntityNotFoundException is expected.");
        } catch (ClientEntityNotFoundException e) {

            // ok
        }

    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.deleteClient(Client)'.
     * <p>
     * DAOException is thrown, ClientManagerException is expected.
     * </p>
     * @throws Exception to junit
     */
    public void testDeleteClient_Failure_4() throws Exception {
        try {
            Client client = new Client();
            client.setName("DAOException");
            client.setDeleted(false);

            manager.deleteClient(client);

            fail("ClientManagerException is expected.");
        } catch (ClientManagerException e) {

            // ok
        }

    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.deleteClient(Client)'.
     * <p>
     * DAOConfigurationException is thrown, ClientManagerException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testDeleteClient_Failure_5() throws Exception {
        try {
            Client client = new Client();
            client.setName("DAOConfigurationException");
            client.setDeleted(false);

            manager.deleteClient(client);

            fail("ClientManagerException is expected.");
        } catch (ClientManagerException e) {

            // ok
        }

    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.deleteClient(Client)'.
     * <p>
     * The client should be deleted.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testDeleteClient_Acc() throws Exception {

        Client client = new Client();
        client.setName("abc");
        client.setDeleted(false);

        Client back = manager.deleteClient(client);

        assertTrue("isDelete should be true.", back.isDeleted());

    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.setClientCodeName(Client, String)'.
     * <p>
     * The client is not valid, IAE is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testSetClientCodeName_F_1() throws Exception {
        try {
            manager.setClientCodeName(null, "codeName");
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {

            // ok
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.setClientCodeName(Client, String)'.
     * <p>
     * The client is not valid, IAE is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testSetClientCodeName_F_2() throws Exception {
        try {
            Client client = new Client();
            client.setName("");
            manager.setClientCodeName(client, "codeName");
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {

            // ok
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.setClientCodeName(Client, String)'.
     * <p>
     * The client is not valid, IAE is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testSetClientCodeName_F_3() throws Exception {
        try {
            Client client = new Client();
            client.setName("name");
            client.setDeleted(true);
            manager.setClientCodeName(client, "codeName");
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {

            // ok
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.setClientCodeName(Client, String)'.
     * <p>
     * The codeName is null, IAE is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testSetClientCodeName_F_4() throws Exception {
        try {
            Client client = new Client();
            client.setName("name");

            manager.setClientCodeName(client, null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {

            // ok
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.setClientCodeName(Client, String)'.
     * <p>
     * The codeName is empty, IAE is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testSetClientCodeName_F_5() throws Exception {
        try {
            Client client = new Client();
            client.setName("name");

            manager.setClientCodeName(client, "  ");
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {

            // ok
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.setClientCodeName(Client, String)'.
     * <p>
     * ClientEntityNotFoundException is expected as this client is not in the persistence.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testSetClientCodeName_F_6() throws Exception {
        try {
            Client client = new Client();
            client.setName("name");
            client.setStartDate(new Date());
            client.setEndDate(new Date(System.currentTimeMillis() + 1000000000L));

            manager.setClientCodeName(client, "code");
            fail("ClientEntityNotFoundException is expected.");
        } catch (ClientEntityNotFoundException e) {

            // ok
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.setClientCodeName(Client, String)'.
     * <p>
     * ClientManagerException is expected as DAOConfigurationException is thrown in the dao.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testSetClientCodeName_F_7() throws Exception {
        try {
            Client client = new Client();
            client.setName("DAOConfigurationException");
            client.setId(1L);
            client.setStartDate(new Date());
            client.setEndDate(new Date(System.currentTimeMillis() + 1000000000L));

            manager.setClientCodeName(client, "code");
            fail("ClientManagerException is expected.");
        } catch (ClientManagerException e) {

            // ok
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.setClientCodeName(Client, String)'.
     * <p>
     * The client should be updated.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testSetClientCodeName_acc() throws Exception {

        Client client = new Client();
        client.setName("Dok");
        client.setId(1L);
        client.setStartDate(new Date());
        client.setEndDate(new Date(System.currentTimeMillis() + 1000000000L));

        Client back = manager.setClientCodeName(client, "code");

        assertEquals("Equal to 'code'", "code", back.getCodeName());
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.setClientStatus(Client, ClientStatus)'.
     *
     * <p>
     * The client is not valid, IAE is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testSetClientStatus_F_1() throws Exception {
        ClientStatus status = new ClientStatus();
        status.setName("s");
        status.setDescription("des");

        try {
            manager.setClientStatus(null, status);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {

            // ok
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.setClientStatus(Client, ClientStatus)'.
     *
     * <p>
     * The client is not valid, IAE is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testSetClientStatus_F_2() throws Exception {
        ClientStatus status = new ClientStatus();
        status.setName("s");
        status.setDescription("des");

        Client client = new Client();
        client.setName("  ");
        try {
            manager.setClientStatus(client, status);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {

            // ok
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.setClientStatus(Client, ClientStatus)'.
     *
     * <p>
     * The client is not valid, IAE is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testSetClientStatus_F_3() throws Exception {
        ClientStatus status = new ClientStatus();
        status.setName("s");
        status.setDescription("des");

        Client client = new Client();
        client.setName("name");
        client.setDeleted(true);
        try {
            manager.setClientStatus(client, status);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {

            // ok
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.setClientStatus(Client, ClientStatus)'.
     *
     * <p>
     * The status is not valid, IAE is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testSetClientStatus_F_4() throws Exception {
        ClientStatus status = new ClientStatus();
        status.setName("s");
        status.setDescription("des");

        Client client = new Client();
        client.setName("name");
        client.setDeleted(false);
        try {
            manager.setClientStatus(client, null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {

            // ok
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.setClientStatus(Client, ClientStatus)'.
     *
     * <p>
     * The status is not valid, IAE is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testSetClientStatus_F_5() throws Exception {
        ClientStatus status = new ClientStatus();
        status.setName(null);
        status.setDescription("des");

        Client client = new Client();
        client.setName("name");
        client.setDeleted(false);
        try {
            manager.setClientStatus(client, status);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {

            // ok
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.setClientStatus(Client, ClientStatus)'.
     *
     * <p>
     * The status is not valid, IAE is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testSetClientStatus_F_6() throws Exception {
        ClientStatus status = new ClientStatus();
        status.setName("   ");
        status.setDescription("des");

        Client client = new Client();
        client.setName("name");
        client.setDeleted(false);
        try {
            manager.setClientStatus(client, status);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {

            // ok
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.setClientStatus(Client, ClientStatus)'.
     *
     * <p>
     * The status is not valid, IAE is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testSetClientStatus_F_7() throws Exception {
        ClientStatus status = new ClientStatus();
        status.setName("name");
        status.setDescription("   ");

        Client client = new Client();
        client.setName("name");
        client.setDeleted(false);
        try {
            manager.setClientStatus(client, status);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {

            // ok
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.setClientStatus(Client, ClientStatus)'.
     *
     * <p>
     * The status is not valid, IAE is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testSetClientStatus_F_8() throws Exception {
        ClientStatus status = new ClientStatus();
        status.setName("name");
        status.setDescription("des");
        status.setDeleted(true);

        Client client = new Client();
        client.setName("name");
        client.setDeleted(false);
        try {
            manager.setClientStatus(client, status);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {

            // ok
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.setClientStatus(Client, ClientStatus)'.
     *
     * <p>
     * ClientEntityNotFoundException is expected as the Client is not in the persistence.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testSetClientStatus_F_9() throws Exception {
        ClientStatus status = new ClientStatus();
        status.setName("name");
        status.setDescription("des");
        status.setDeleted(false);

        Client client = new Client();
        client.setName("name");
        client.setDeleted(false);
        client.setStartDate(new Date());
        client.setEndDate(new Date(System.currentTimeMillis() + 1000000000L));

        try {
            manager.setClientStatus(client, status);
            fail("ClientEntityNotFoundException is expected.");
        } catch (ClientEntityNotFoundException e) {

            // ok
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.setClientStatus(Client, ClientStatus)'.
     *
     * <p>
     * ClientManagerException is expected as DAOConfigurationException is thrown.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testSetClientStatus_F_10() throws Exception {
        ClientStatus status = new ClientStatus();
        status.setName("name");
        status.setDescription("des");
        status.setDeleted(false);

        Client client = new Client();
        client.setName("DAOConfigurationException");
        client.setDeleted(false);
        client.setId(1L);
        client.setStartDate(new Date());
        client.setEndDate(new Date(System.currentTimeMillis() + 1000000000L));

        try {
            manager.setClientStatus(client, status);
            fail("ClientManagerException is expected.");
        } catch (ClientManagerException e) {

            // ok
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.setClientStatus(Client, ClientStatus)'.
     *
     * <p>
     * The client should be set with ClientStatus.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testSetClientStatus_Acc() throws Exception {
        ClientStatus status = new ClientStatus();
        status.setName("name");
        status.setDescription("des");
        status.setDeleted(false);

        Client client = new Client();
        client.setName("ok");
        client.setDeleted(false);
        client.setId(1L);
        client.setStartDate(new Date());
        client.setEndDate(new Date(System.currentTimeMillis() + 1000000000L));

        Client back = manager.setClientStatus(client, status);

        assertEquals("Description equal to 'des'", "des", back.getClientStatus().getDescription());
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.retrieveClient(long)'.
     * <p>
     * If id is negative, IAE is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testRetrieveClient_F_1() throws Exception {

        try {
            manager.retrieveClient(-1);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {

            // ok
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.retrieveClient(long)'.
     * <p>
     * ClientManagerException is expected as non-client is returned.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testRetrieveClient_F_2() throws Exception {
        try {
            manager.retrieveClient(999);
            fail("ClientManagerException is expected.");
        } catch (ClientManagerException e) {

            // ok
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.retrieveClient(long)'.
     * <p>
     * ClientManagerException is expected as DAOConfigurationException will be raised.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testRetrieveClient_F_3() throws Exception {
        try {
            manager.retrieveClient(1001);
            fail(" ClientManagerException is expected.");
        } catch (ClientManagerException e) {
            // ignore
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.retrieveClient(long)'.
     * <p>
     * ClientManagerException is expected as DAOException raised.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testRetrieveClient_F_4() throws Exception {

        try {
            manager.retrieveClient(10001);
            fail("ClientManagerException is expected.");
        } catch (ClientManagerException e) {

            // ok
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.retrieveClient(long)'.
     * <p>
     * The client with given id should be returned.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testRetrieveClient_Accu() throws Exception {

        Client back = manager.retrieveClient(1);

        assertEquals("The name should be 'ok'.", "ok", back.getName());
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.retrieveAllClients()'.
     *
     * <p>
     * DAOException is thrown, ClientManagerException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testRetrieveAllClients_F_1() throws Exception {
        MockClientDAO dao = new MockClientDAO();
        this.injectField("clientDAO", dao);

        dao.setFlag(1);

        try {
            manager.retrieveAllClients();
            fail("ClientManagerException is expected.");
        } catch (ClientManagerException e) {

            // ok
        }

    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.retrieveAllClients()'.
     *
     * <p>
     * DAOConfigurationException is thrown, ClientManagerException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testRetrieveAllClients_F_2() throws Exception {
        MockClientDAO dao = new MockClientDAO();
        this.injectField("clientDAO", dao);

        dao.setFlag(2);

        try {
            manager.retrieveAllClients();
            fail("ClientManagerException is expected.");
        } catch (ClientManagerException e) {

            // ok
        }

    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.retrieveAllClients()'.
     *
     * <p>
     * An empty list will be returned.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testRetrieveAllClients_Acc() throws Exception {

        List<Client> ret = manager.retrieveAllClients();

        assertTrue("The list should be empty.", ret.isEmpty());

    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.searchClientsByName(String)'.
     * <p>
     * The name is null, IAE is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testSearchClientsByName_F_1() throws Exception {
        try {
            manager.searchClientsByName(null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {

            // ok
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.searchClientsByName(String)'.
     * <p>
     * The name is empty, IAE is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testSearchClientsByName_F_2() throws Exception {
        try {
            manager.searchClientsByName("  ");
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {

            // ok
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.searchClientsByName(String)'.
     * <p>
     * The dao will throw DAOException, ClientManagerException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testSearchClientsByName_F_3() throws Exception {
        try {
            manager.searchClientsByName("DAOException");
            fail("ClientManagerException is expected.");
        } catch (ClientManagerException e) {

            // ok
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.searchClientsByName(String)'.
     * <p>
     * The dao will throw DAOConfigurationException, ClientManagerException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testSearchClientsByName_F_4() throws Exception {
        try {
            manager.searchClientsByName("DAOConfigurationException");
            fail("ClientManagerException is expected.");
        } catch (ClientManagerException e) {

            // ok
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.searchClientsByName(String)'.
     * <p>
     * An empty list will be returned.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testSearchClientsByName_Accu() throws Exception {

        List<Client> ret = manager.searchClientsByName("ok");

        assertTrue("The list should be empty.", ret.isEmpty());
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.searchClients(Filter)'.
     * <p>
     * The filter is null, IAE is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testSearchClients_F_1() throws Exception {
        try {
            manager.searchClients(null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {

            // ok
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.searchClients(Filter)'.
     * <p>
     * The dao will throw DAOException, ClientManagerException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testSearchClients_F_2() throws Exception {
        try {
            EqualToFilter equalToFilter = new EqualToFilter("DAOException", new Boolean(false));
            manager.searchClients(equalToFilter);
            fail("ClientManagerException is expected.");
        } catch (ClientManagerException e) {

            // ok
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.searchClients(Filter)'.
     * <p>
     * The dao will throw DAOConfigurationException, ClientManagerException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testSearchClients_F_3() throws Exception {
        try {
            EqualToFilter equalToFilter = new EqualToFilter("DAOConfigurationException", new Boolean(false));
            manager.searchClients(equalToFilter);
            fail("ClientManagerException is expected.");
        } catch (ClientManagerException e) {

            // ok
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.searchClients(Filter)'.
     * <p>
     * An empty list will be returned.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testSearchClients_Accu() throws Exception {

        EqualToFilter equalToFilter = new EqualToFilter("ok", new Boolean(false));
        List<Client> ret = manager.searchClients(equalToFilter);

        assertTrue("The list should be empty.", ret.isEmpty());
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.getClientStatus(long)'.
     * <p>
     * If the id is negative, IAE is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testGetClientStatus_F_1() throws Exception {
        try {
            manager.getClientStatus(-1);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {

            // ok
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.getClientStatus(long)'.
     * <p>
     * Inner DAOException is thrown, ClientManagerException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testGetClientStatus_F_2() throws Exception {
        try {
            manager.getClientStatus(10001);
            fail(" ClientManagerException is expected.");
        } catch (ClientManagerException e) {

            // ok
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.getClientStatus(long)'.
     * <p>
     * Inner DAOConfigurationException is thrown, ClientManagerException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testGetClientStatus_F_3() throws Exception {
        try {
            manager.getClientStatus(10002);
            fail(" ClientManagerException is expected.");
        } catch (ClientManagerException e) {

            // ok
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.getClientStatus(long)'.
     * <p>
     * Inner ClassCastException is thrown, ClientManagerException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testGetClientStatus_F_4() throws Exception {
        try {
            manager.getClientStatus(101);
            fail(" ClientManagerException is expected.");
        } catch (ClientManagerException e) {

            // ok
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.getClientStatus(long)'.
     * <p>
     * A ClientStatus instance should be returned.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testGetClientStatus_Accu() throws Exception {

        ClientStatus ret = manager.getClientStatus(999);

        assertEquals("Equal to 'e'.", "e", ret.getDescription());
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.getAllClientStatuses()'.
     * <p>
     * An empty list will be returned.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testGetAllClientStatuses_Accu() throws Exception {
        List<ClientStatus> ret = manager.getAllClientStatuses();

        assertTrue("The list should be empty.", ret.isEmpty());
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.getAllClientStatuses()'.
     * <p>
     * Inner dao throw DAOException, ClientManagerException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testGetAllClientStatuses_F_1() throws Exception {
        MockClientStatusDAO dao = new MockClientStatusDAO();
        dao.setFlag(1);
        this.injectField("clientStatusDAO", dao);

        try {
            manager.getAllClientStatuses();
            fail(" ClientManagerException is expected.");
        } catch (ClientManagerException e) {
            // ok
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.getAllClientStatuses()'.
     * <p>
     * Inner dao throw DAOConfigurationException, ClientManagerException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testGetAllClientStatuses_F_2() throws Exception {
        MockClientStatusDAO dao = new MockClientStatusDAO();
        dao.setFlag(2);
        this.injectField("clientStatusDAO", dao);

        try {
            manager.getAllClientStatuses();

            fail(" ClientManagerException is expected.");
        } catch (ClientManagerException e) {

            // ok
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.getClientsForStatus(ClientStatus)'.
     * <p>
     * The ClientStatus is not valid, IAE is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testGetClientsForStatus_F_1() throws Exception {
        try {
            manager.getClientsForStatus(null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {

            // ok
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.getClientsForStatus(ClientStatus)'.
     * <p>
     * The ClientStatus is not valid, IAE is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testGetClientsForStatus_F_2() throws Exception {
        try {
            ClientStatus s = new ClientStatus();
            s.setName(null);
            s.setDescription("des");
            s.setDeleted(false);
            manager.getClientsForStatus(s);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {

            // ok
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.getClientsForStatus(ClientStatus)'.
     * <p>
     * The ClientStatus is not valid, IAE is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testGetClientsForStatus_F_3() throws Exception {
        try {
            ClientStatus s = new ClientStatus();
            s.setName(" ");
            s.setDescription("des");
            s.setDeleted(false);
            manager.getClientsForStatus(s);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.getClientsForStatus(ClientStatus)'.
     * <p>
     * The ClientStatus is not valid, IAE is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testGetClientsForStatus_F_4() throws Exception {
        try {
            ClientStatus s = new ClientStatus();
            s.setName("name");
            s.setDescription("   ");
            s.setDeleted(false);
            manager.getClientsForStatus(s);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.getClientsForStatus(ClientStatus)'.
     * <p>
     * The ClientStatus is not valid, IAE is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testGetClientsForStatus_F_5() throws Exception {
        try {
            ClientStatus s = new ClientStatus();
            s.setName("name");
            s.setDescription("des");
            s.setDeleted(true);
            manager.getClientsForStatus(s);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.getClientsForStatus(ClientStatus)'.
     * <p>
     * The ClientStatus is not valid, IAE is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testGetClientsForStatus_F_6() throws Exception {
        try {
            ClientStatus s = new ClientStatus();
            s.setName("name");
            s.setDescription("des");
            s.setDeleted(true);
            manager.getClientsForStatus(s);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.getClientsForStatus(ClientStatus)'.
     * <p>
     * The inner dao throw EntityNotFoundException, ClientManagerException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testGetClientsForStatus_F_7() throws Exception {
        try {
            ClientStatus s = new ClientStatus();
            s.setName("entity");
            s.setDescription("des");
            s.setDeleted(false);
            manager.getClientsForStatus(s);
            fail("ClientManagerException is expected.");
        } catch (ClientManagerException e) {

            // ok
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.getClientsForStatus(ClientStatus)'.
     * <p>
     * The inner dao throw DAOException, ClientManagerException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testGetClientsForStatus_F_8() throws Exception {
        try {
            ClientStatus s = new ClientStatus();
            s.setName("DAOException");
            s.setDescription("des");
            s.setDeleted(false);
            manager.getClientsForStatus(s);
            fail("ClientManagerException is expected.");
        } catch (ClientManagerException e) {

            // ok
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.getClientsForStatus(ClientStatus)'.
     * <p>
     * The inner dao throw AOConfigurationException", ClientManagerException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testGetClientsForStatus_F_9() throws Exception {
        try {
            ClientStatus s = new ClientStatus();
            s.setName("DAOConfigurationException");
            s.setDescription("des");
            s.setDeleted(false);
            manager.getClientsForStatus(s);
            fail("ClientManagerException is expected.");
        } catch (ClientManagerException e) {

            // ok
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.getClientsForStatus(ClientStatus)'.
     * <p>
     * An empty List will be returned.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testGetClientsForStatus_Acc() throws Exception {

        ClientStatus s = new ClientStatus();
        s.setName("correct");
        s.setDescription("des");
        s.setDeleted(false);
        List<Client> ret = manager.getClientsForStatus(s);

        assertTrue("Should be empty.", ret.isEmpty());
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.createClientStatus(ClientStatus)'.
     * <p>
     * The clientStatus is null, IAE is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testCreateClientStatus_F_1() throws Exception {
        try {
            manager.createClientStatus(null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {

            // ok
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.createClientStatus(ClientStatus)'.
     * <p>
     * The clientStatus 's name is null, IAE is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testCreateClientStatus_F_2() throws Exception {
        try {
            ClientStatus status = new ClientStatus();
            status.setName(null);
            status.setDeleted(false);
            status.setDescription("des");
            manager.createClientStatus(status);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {

            // ok
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.createClientStatus(ClientStatus)'.
     * <p>
     * The clientStatus 's name is empty, IAE is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testCreateClientStatus_F_3() throws Exception {
        try {
            ClientStatus status = new ClientStatus();
            status.setName(" ");
            status.setDeleted(false);
            status.setDescription("des");
            manager.createClientStatus(status);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {

            // ok
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.createClientStatus(ClientStatus)'.
     * <p>
     * The clientStatus 's description is null, IAE is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testCreateClientStatus_F_4() throws Exception {
        try {
            ClientStatus status = new ClientStatus();
            status.setName("name");
            status.setDeleted(false);
            status.setDescription(null);
            manager.createClientStatus(status);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {

            // ok
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.createClientStatus(ClientStatus)'.
     * <p>
     * The clientStatus 's description is empty, IAE is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testCreateClientStatus_F_5() throws Exception {
        try {
            ClientStatus status = new ClientStatus();
            status.setName("name");
            status.setDeleted(false);
            status.setDescription("  ");
            manager.createClientStatus(status);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {

            // ok
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.createClientStatus(ClientStatus)'.
     * <p>
     * The clientStatus 's isDeleted is true, IAE is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testCreateClientStatus_F_6() throws Exception {
        try {
            ClientStatus status = new ClientStatus();
            status.setName("name");
            status.setDeleted(true);
            status.setDescription("des");
            manager.createClientStatus(status);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {

            // ok
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.createClientStatus(ClientStatus)'.
     * <p>
     * Inner DAOException will be thrown, ClientManagerException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testCreateClientStatus_F_7() throws Exception {
        try {
            ClientStatus status = new ClientStatus();
            status.setName("DAOException");
            status.setDeleted(false);
            status.setDescription("des");
            manager.createClientStatus(status);
            fail("ClientManagerException is expected.");
        } catch (ClientManagerException e) {

            // ok
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.createClientStatus(ClientStatus)'.
     * <p>
     * Inner DAOConfigurationException will be thrown, ClientManagerException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testCreateClientStatus_F_8() throws Exception {
        try {
            ClientStatus status = new ClientStatus();
            status.setName("DAOConfigurationException");
            status.setDeleted(false);
            status.setDescription("des");
            manager.createClientStatus(status);
            fail("ClientManagerException is expected.");
        } catch (ClientManagerException e) {

            // ok
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.createClientStatus(ClientStatus)'.
     * <p>
     * The ClientStatus should be saved. The create user name sould be 'test'.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testCreateClientStatus_Acc() throws Exception {

        ClientStatus status = new ClientStatus();
        status.setName("ok");
        status.setDeleted(false);
        status.setDescription("des");
        ClientStatus back = manager.createClientStatus(status);

        assertEquals("Equal to 'test'.", "test", back.getCreateUsername());

    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.updateClientStatus(ClientStatus)'.
     * <p>
     * The clientStatus is null, IAE is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testUpdateClientStatus_1() throws Exception {
        try {
            manager.updateClientStatus(null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {

            // ok
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.updateClientStatus(ClientStatus)'.
     * <p>
     * The clientStatus 's name is null, IAE is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testUpdateClientStatus_2() throws Exception {
        try {
            ClientStatus status = new ClientStatus();
            status.setName(null);
            status.setDeleted(false);
            status.setDescription("des");

            manager.updateClientStatus(status);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {

            // ok
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.updateClientStatus(ClientStatus)'.
     * <p>
     * The clientStatus 's name is empty, IAE is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testUpdateClientStatus_3() throws Exception {
        try {
            ClientStatus status = new ClientStatus();
            status.setName("  ");
            status.setDeleted(false);
            status.setDescription("des");

            manager.updateClientStatus(status);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {

            // ok
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.updateClientStatus(ClientStatus)'.
     * <p>
     * The clientStatus 's description is null, IAE is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testUpdateClientStatus_4() throws Exception {
        try {
            ClientStatus status = new ClientStatus();
            status.setName("name");
            status.setDeleted(false);
            status.setDescription(null);

            manager.updateClientStatus(status);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {

            // ok
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.updateClientStatus(ClientStatus)'.
     * <p>
     * The clientStatus 's description is empty, IAE is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testUpdateClientStatus_5() throws Exception {
        try {
            ClientStatus status = new ClientStatus();
            status.setName("name");
            status.setDeleted(false);
            status.setDescription("   ");

            manager.updateClientStatus(status);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {

            // ok
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.updateClientStatus(ClientStatus)'.
     * <p>
     * The clientStatus 's isDeleted is true, IAE is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testUpdateClientStatus_6() throws Exception {
        try {
            ClientStatus status = new ClientStatus();
            status.setName("name");
            status.setDeleted(true);
            status.setDescription("d");

            manager.updateClientStatus(status);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {

            // ok
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.updateClientStatus(ClientStatus)'.
     * <p>
     * The id is < 0, ClientEntityNotFoundException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testUpdateClientStatus_7() throws Exception {
        try {
            ClientStatus status = new ClientStatus();
            status.setName("name");
            status.setDeleted(false);
            status.setDescription("d");
            status.setId(-1);

            manager.updateClientStatus(status);
            fail("ClientEntityNotFoundException is expected.");
        } catch (ClientEntityNotFoundException e) {

            // ok
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.updateClientStatus(ClientStatus)'.
     * <p>
     * Inner DAOException will be thrown, ClientManagerException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testUpdateClientStatus_8() throws Exception {
        try {
            ClientStatus status = new ClientStatus();
            status.setName("DAOException");
            status.setDeleted(false);
            status.setDescription("d");
            status.setId(1L);

            manager.updateClientStatus(status);
            fail("ClientManagerException is expected.");
        } catch (ClientManagerException e) {

            // ok
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.updateClientStatus(ClientStatus)'.
     * <p>
     * Inner DAOConfigurationException will be thrown, ClientManagerException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testUpdateClientStatus_9() throws Exception {
        try {
            ClientStatus status = new ClientStatus();
            status.setName("DAOConfigurationException");
            status.setDeleted(false);
            status.setDescription("d");
            status.setId(1L);

            manager.updateClientStatus(status);
            fail("ClientManagerException is expected.");
        } catch (ClientManagerException e) {

            // ok
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.updateClientStatus(ClientStatus)'.
     * <p>
     * the clientStatus cannot be found in the persistence, ClientEntityNotFoundException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testUpdateClientStatus_10() throws Exception {
        try {
            ClientStatus status = new ClientStatus();
            status.setName("DAOConfigurationException");
            status.setDeleted(false);
            status.setDescription("d");
            status.setId(2L);

            manager.updateClientStatus(status);
            fail("ClientEntityNotFoundException is expected.");
        } catch (ClientEntityNotFoundException e) {

            // ok
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.updateClientStatus(ClientStatus)'.
     * <p>
     * the clientStatus cannot be found in the persistence, ClientEntityNotFoundException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testUpdateClientStatus_11() throws Exception {
        try {
            ClientStatus status = new ClientStatus();
            status.setName("DAOConfigurationException");
            status.setDeleted(false);
            status.setDescription("d");
            status.setId(1000L);

            manager.updateClientStatus(status);
            fail("ClientEntityNotFoundException is expected.");
        } catch (ClientEntityNotFoundException e) {

            // ok
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.updateClientStatus(ClientStatus)'.
     * <p>
     * ClassCastException will be thrown, ClientManagerException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testUpdateClientStatus_12() throws Exception {
        try {
            ClientStatus status = new ClientStatus();
            status.setName("ClassCastException");
            status.setDeleted(false);
            status.setDescription("d");
            status.setId(3L);

            manager.updateClientStatus(status);
            fail("ClientManagerException is expected.");
        } catch (ClientManagerException e) {

            // ok
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.updateClientStatus(ClientStatus)'.
     * <p>
     * The ClientStatus will be updated. The create user will be 'test'.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testUpdateClientStatus_Acc() throws Exception {

        ClientStatus status = new ClientStatus();
        status.setName("Dok");
        status.setDeleted(false);
        status.setDescription("d");
        status.setId(1L);

        ClientStatus ret = manager.updateClientStatus(status);

        assertEquals("Equal to 'test'.", "test", ret.getCreateUsername());
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.deleteClientStatus(ClientStatus)'.
     *
     * <p>
     * The clientStatus is null, IAE is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testDeleteClientStatus_F_1() throws Exception {
        try {
            manager.deleteClientStatus(null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {

            // ok
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.deleteClientStatus(ClientStatus)'.
     *
     * <p>
     * The clientStatus' isDeleted is true, IAE is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testDeleteClientStatus_F_2() throws Exception {
        try {
            ClientStatus status = new ClientStatus();
            status.setName("Dok");
            status.setDeleted(true);
            status.setDescription("d");
            status.setId(1L);

            manager.deleteClientStatus(status);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.deleteClientStatus(ClientStatus)'.
     *
     * <p>
     * Inner EntityNotFoundException will be thrown, ClientEntityNotFoundException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testDeleteClientStatus_F_3() throws Exception {
        try {
            ClientStatus status = new ClientStatus();
            status.setName("EntityNotFoundException");
            status.setDeleted(false);
            status.setDescription("d");
            status.setId(1L);

            manager.deleteClientStatus(status);
            fail("ClientEntityNotFoundException is expected.");
        } catch (ClientEntityNotFoundException e) {

            // ok
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.deleteClientStatus(ClientStatus)'.
     *
     * <p>
     * Inner DAOException will be thrown, ClientManagerException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testDeleteClientStatus_F_4() throws Exception {
        try {
            ClientStatus status = new ClientStatus();
            status.setName("DAOException");
            status.setDeleted(false);
            status.setDescription("d");
            status.setId(1L);

            manager.deleteClientStatus(status);
            fail("ClientManagerException is expected.");
        } catch (ClientManagerException e) {

            // ok
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.deleteClientStatus(ClientStatus)'.
     *
     * <p>
     * Inner DAOConfigurationException will be thrown, ClientManagerException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testDeleteClientStatus_F_5() throws Exception {
        try {
            ClientStatus status = new ClientStatus();
            status.setName("DAOConfigurationException");
            status.setDeleted(false);
            status.setDescription("d");
            status.setId(1L);

            manager.deleteClientStatus(status);
            fail("ClientManagerException is expected.");
        } catch (ClientManagerException e) {

            // ok
        }
    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.deleteClientStatus(ClientStatus)'.
     *
     * <p>
     * The ClientStatus will be deleted. The isDelete will return true.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testDeleteClientStatus_Acc() throws Exception {
        ClientStatus status = new ClientStatus();
        status.setName("ok");
        status.setDeleted(false);
        status.setDescription("d");
        status.setId(1L);

        ClientStatus ret = manager.deleteClientStatus(status);

        assertTrue("Deleted.", ret.isDeleted());
    }

    /**
     * Inject the field with value.
     *
     * @param field
     *            the field to inject
     * @param value
     *            the value to inject
     * @throws Exception
     *             to junit
     */
    private void injectField(String field, Object value) throws Exception {
        Field f = manager.getClass().getDeclaredField(field);
        f.setAccessible(true);

        f.set(manager, value);
    }
}
