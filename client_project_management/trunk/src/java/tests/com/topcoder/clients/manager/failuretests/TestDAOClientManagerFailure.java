/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.manager.failuretests;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

import com.topcoder.clients.manager.ClientEntityNotFoundException;
import com.topcoder.clients.manager.ClientManagerException;
import com.topcoder.clients.manager.ManagerConfigurationException;
import com.topcoder.clients.manager.MockClientDAO;
import com.topcoder.clients.manager.MockClientStatusDAO;
import com.topcoder.clients.manager.dao.DAOClientManager;
import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.ClientStatus;
import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.defaults.DefaultConfigurationObject;
import com.topcoder.search.builder.filter.EqualToFilter;

import junit.framework.TestCase;

/**
 * <p>
 * Failure test cases for class <code>DAOClientManager</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class TestDAOClientManagerFailure extends TestCase {
    /**
     * Represents the DAOClientManager instance for testing.
     */
    private DAOClientManager manager;

    /**
     * Set up the environment.
     *
     * @throws Exception
     *             to junit
     */
    public void setUp() throws Exception {
        TestHelper.clearConfigManager();
        TestHelper.loadConfiguration();

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
        TestHelper.clearConfigManager();
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
    public void testCtor1WithConfigurationObjectNull() throws Exception {
        try {
            new DAOClientManager(null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // expected
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
    public void testCtor1WithConfigurationObjectInvalid1() throws Exception {
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

            // expected
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
    public void testCtor1WithConfigurationObjectInvalid2() throws Exception {
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
            // expected
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
    public void testCtor1WithConfigurationObjectInvalid3() throws Exception {
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
            // expected
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
    public void testCtor1WithConfigurationObjectInvalid4() throws Exception {
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
            // expected
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
    public void testCtor1WithConfigurationObjectInvalid5() throws Exception {
        ConfigurationObject obj = new DefaultConfigurationObject("root");
        obj.setPropertyValue("id_generator_name", "client_id");
        obj.setPropertyValue("logger_name", "System.out");

        obj.setPropertyValue("client_dao", "com.topcoder.clients.manager.MockClientDAO");
        obj.setPropertyValue("client_status_dao", "com.topcoder.clients.manager.MockClientStatusDAO");

        try {
            manager = new DAOClientManager(obj);
            fail(" ManagerConfigurationException is expected.");
        } catch (ManagerConfigurationException e) {
            // expected
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
    public void testCtor1WithConfigurationObjectInvalid6() throws Exception {
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
            // expected
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
    public void testCtor1WithConfigurationObjectInvalid7() throws Exception {
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
            // expected
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
    public void testCtor1WithConfigurationObjectInvalid8() throws Exception {
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
            // expected
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
    public void testCtor1WithConfigurationObjectInvalid9() throws Exception {
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
            // expected
        }
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
    public void testCtor2WithFilenameEmpty() throws Exception {
        try {
            new DAOClientManager("", "namespace");
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // expected
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
    public void testCtor2WithNamespaceNull() throws Exception {
        try {
            new DAOClientManager("abc", null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // expected
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
    public void testCtor2WithNamespaceEmpty() throws Exception {
        try {
            new DAOClientManager("abc", "  ");
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // expected
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
    public void testCtor2WithFileInvalid() throws Exception {
        try {
            new DAOClientManager("abc", "namespace");
            fail("ManagerConfigurationException is expected.");
        } catch (ManagerConfigurationException e) {
            // expected
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
    public void testCtor2WithNamespaceNotExist() throws Exception {
        try {
            new DAOClientManager("test_files/config.properties", "namespace");
            fail("ManagerConfigurationException is expected.");
        } catch (ManagerConfigurationException e) {
            // expected
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
    public void testCreateClientWithClientNull() throws Exception {
        try {
            manager.createClient(null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // expected
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
    public void testCreateClientWithClientInvalid2() throws Exception {
        Client client = new Client();
        client.setStartDate(new Date(System.currentTimeMillis() + 10000000000L));
        client.setEndDate(new Date(System.currentTimeMillis() - 10000000L));
        client.setName("name");
        client.setDeleted(false);

        try {
            manager.createClient(client);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // expected
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
    public void testCreateClientWithClientInvalid3() throws Exception {
        Client client = new Client();
        client.setStartDate(new Date(System.currentTimeMillis() - 10000000000L));
        client.setEndDate(new Date(System.currentTimeMillis() + 10000000L));
        client.setName(null);
        client.setDeleted(false);

        try {
            manager.createClient(client);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // expected
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
    public void testCreateClientWithClientInvalid4() throws Exception {
        Client client = new Client();
        client.setStartDate(new Date(System.currentTimeMillis() - 10000000000L));
        client.setEndDate(new Date(System.currentTimeMillis() + 10000000L));
        client.setName("  ");
        client.setDeleted(false);

        try {
            manager.createClient(client);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // expected
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
    public void testCreateClientWithClientInvalid5() throws Exception {
        Client client = new Client();
        client.setStartDate(new Date(System.currentTimeMillis() - 10000000000L));
        client.setEndDate(new Date(System.currentTimeMillis() + 10000000L));
        client.setName("name");
        client.setDeleted(true);

        try {
            manager.createClient(client);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // expected
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
    public void testCreateClientWithClientInvalid6() throws Exception {
        Client client = new Client();
        client.setStartDate(new Date(System.currentTimeMillis() - 10000000000L));
        client.setEndDate(new Date(System.currentTimeMillis() + 10000000L));
        client.setName("DAOException");
        client.setDeleted(false);

        try {
            manager.createClient(client);
            fail("ClientManagerException is expected.");
        } catch (ClientManagerException e) {
            // expected
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
    public void testCreateClientWithClientInvalid7() throws Exception {
        Client client = new Client();
        client.setStartDate(new Date(System.currentTimeMillis() - 10000000000L));
        client.setEndDate(new Date(System.currentTimeMillis() + 10000000L));
        client.setName("DAOConfigurationException");
        client.setDeleted(false);

        try {
            manager.createClient(client);
            fail("ClientManagerException is expected.");
        } catch (ClientManagerException e) {
            // expected
        }
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
    public void testUpdateClientWithClientInvalid1() throws Exception {
        try {
            manager.updateClient(null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // expected
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
    public void testUpdateClientWithClientInvalid2() throws Exception {
        try {
            Client client = new Client();
            client.setStartDate(new Date(System.currentTimeMillis() + 10000000000L));
            client.setEndDate(new Date(System.currentTimeMillis() - 10000000L));
            client.setName("name");
            client.setDeleted(false);

            manager.updateClient(client);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {

            // expected
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
    public void testUpdateClientWithClientInvalid3() throws Exception {
        try {
            Client client = new Client();
            client.setStartDate(new Date(System.currentTimeMillis() - 10000000000L));
            client.setEndDate(new Date(System.currentTimeMillis() + 10000000L));
            client.setName("  ");
            client.setDeleted(false);

            manager.updateClient(client);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {

            // expected
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
    public void testUpdateClientWithClientInvalid4() throws Exception {
        try {
            Client client = new Client();
            client.setStartDate(new Date(System.currentTimeMillis() - 10000000000L));
            client.setEndDate(new Date(System.currentTimeMillis() + 10000000L));
            client.setName("name");
            client.setDeleted(true);

            manager.updateClient(client);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {

            // expected
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
    public void testUpdateClientWithClientInvalid5() throws Exception {
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

            // expected
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
    public void testUpdateClientWithClientInvalid6() throws Exception {
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
            // expected
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
    public void testUpdateClientWithClientInvalid7() throws Exception {
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
            // expected
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
    public void testUpdateClientWithClientInvalid8() throws Exception {
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
            // expected
        }
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
    public void testDeleteClientWithClientInvalid1() throws Exception {
        try {
            manager.deleteClient(null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // expected
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
    public void testDeleteClientWithClientInvalid2() throws Exception {
        try {
            Client client = new Client();
            client.setDeleted(true);
            manager.deleteClient(client);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // expected
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
    public void testDeleteClientWithClientInvalid3() throws Exception {
        try {
            Client client = new Client();
            client.setName("entityNotFound");
            client.setDeleted(false);

            manager.deleteClient(client);

            fail("ClientEntityNotFoundException is expected.");
        } catch (ClientEntityNotFoundException e) {
            // expected
        }

    }

    /**
     * Test method for 'com.topcoder.clients.manager.dao.DAOClientManager.deleteClient(Client)'.
     * <p>
     * DAOException is thrown, ClientManagerException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testDeleteClientWithClientInvalid4() throws Exception {
        try {
            Client client = new Client();
            client.setName("DAOException");
            client.setDeleted(false);

            manager.deleteClient(client);

            fail("ClientManagerException is expected.");
        } catch (ClientManagerException e) {
            // expected
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
    public void testDeleteClientWithClientInvalid5() throws Exception {
        try {
            Client client = new Client();
            client.setName("DAOConfigurationException");
            client.setDeleted(false);

            manager.deleteClient(client);

            fail("ClientManagerException is expected.");
        } catch (ClientManagerException e) {
            // expected
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
    public void testSetClientCodeNameWithInvalid1() throws Exception {
        try {
            manager.setClientCodeName(null, "codeName");
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // expected
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
    public void testSetClientCodeNameWithInvalid2() throws Exception {
        try {
            Client client = new Client();
            client.setName("");
            manager.setClientCodeName(client, "codeName");
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // expected
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
    public void testSetClientCodeNameWithInvalid3() throws Exception {
        try {
            Client client = new Client();
            client.setName("name");
            client.setDeleted(true);
            manager.setClientCodeName(client, "codeName");
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // expected
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
    public void testSetClientCodeNameWithInvalid4() throws Exception {
        try {
            Client client = new Client();
            client.setName("name");

            manager.setClientCodeName(client, null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // expected
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
    public void testSetClientCodeNameWithInvalid5() throws Exception {
        try {
            Client client = new Client();
            client.setName("name");

            manager.setClientCodeName(client, "  ");
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // expected
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
    public void testSetClientCodeNameWithInvalid6() throws Exception {
        try {
            Client client = new Client();
            client.setName("name");
            client.setStartDate(new Date());
            client.setEndDate(new Date(System.currentTimeMillis() + 1000000000L));

            manager.setClientCodeName(client, "code");
            fail("ClientEntityNotFoundException is expected.");
        } catch (ClientEntityNotFoundException e) {
            // expected
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
    public void testSetClientCodeNameWithInvalid7() throws Exception {
        try {
            Client client = new Client();
            client.setName("DAOConfigurationException");
            client.setId(1L);
            client.setStartDate(new Date());
            client.setEndDate(new Date(System.currentTimeMillis() + 1000000000L));

            manager.setClientCodeName(client, "code");
            fail("ClientManagerException is expected.");
        } catch (ClientManagerException e) {
            // expected
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
    public void testSetClientStatusWithInvalid1() throws Exception {
        ClientStatus status = new ClientStatus();
        status.setName("s");
        status.setDescription("des");

        try {
            manager.setClientStatus(null, status);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // expected
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
    public void testSetClientStatusWithInvalid2() throws Exception {
        ClientStatus status = new ClientStatus();
        status.setName("s");
        status.setDescription("des");

        Client client = new Client();
        client.setName("  ");
        try {
            manager.setClientStatus(client, status);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // expected
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
    public void testSetClientStatusWithInvalid3() throws Exception {
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
            // expected
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
    public void testSetClientStatusWithInvalid4() throws Exception {
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
            // expected
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
    public void testSetClientStatusWithInvalid5() throws Exception {
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
            // expected
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
    public void testSetClientStatusWithInvalid6() throws Exception {
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
            // expected
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
    public void testSetClientStatusWithInvalid7() throws Exception {
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
            // expected
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
    public void testSetClientStatusWithInvalid8() throws Exception {
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
            // expected
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
    public void testSetClientStatusWithInvalid9() throws Exception {
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
            // expected
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
    public void testSetClientStatusWithInvalid10() throws Exception {
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
            // expected
        }
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
    public void testRetrieveClientWithInvalid1() throws Exception {
        try {
            manager.retrieveClient(-1);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // expected
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
    public void testRetrieveClientWithInvalid2() throws Exception {
        try {
            manager.retrieveClient(999);
            fail("ClientManagerException is expected.");
        } catch (ClientManagerException e) {
            // expected
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
    public void testRetrieveClientWithInvalid3() throws Exception {
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
    public void testRetrieveClientWithInvalid4() throws Exception {

        try {
            manager.retrieveClient(10001);
            fail("ClientManagerException is expected.");
        } catch (ClientManagerException e) {
            // expected
        }
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
    public void testRetrieveAllClientsWithInvalid1() throws Exception {
        MockClientDAO dao = new MockClientDAO();
        this.injectField("clientDAO", dao);

        dao.setFlag(1);

        try {
            manager.retrieveAllClients();
            fail("ClientManagerException is expected.");
        } catch (ClientManagerException e) {
            // expected
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
    public void testRetrieveAllClientsWithInvalid2() throws Exception {
        MockClientDAO dao = new MockClientDAO();
        this.injectField("clientDAO", dao);

        dao.setFlag(2);

        try {
            manager.retrieveAllClients();
            fail("ClientManagerException is expected.");
        } catch (ClientManagerException e) {
            // expected
        }
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
    public void testSearchClientsByNameWithInvalid1() throws Exception {
        try {
            manager.searchClientsByName(null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // expected
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
    public void testSearchClientsByNameWithInvalid2() throws Exception {
        try {
            manager.searchClientsByName("  ");
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // expected
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
    public void testSearchClientsByNameWithInvalid3() throws Exception {
        try {
            manager.searchClientsByName("DAOException");
            fail("ClientManagerException is expected.");
        } catch (ClientManagerException e) {
            // expected
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
    public void testSearchClientsByNameWithInvalid4() throws Exception {
        try {
            manager.searchClientsByName("DAOConfigurationException");
            fail("ClientManagerException is expected.");
        } catch (ClientManagerException e) {
            // expected
        }
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
    public void testSearchClientsFailure1() throws Exception {
        try {
            manager.searchClients(null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // expected
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
    public void testSearchClientsFailure2() throws Exception {
        try {
            EqualToFilter equalToFilter = new EqualToFilter("DAOException", new Boolean(false));
            manager.searchClients(equalToFilter);
            fail("ClientManagerException is expected.");
        } catch (ClientManagerException e) {
            // expected
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
    public void testSearchClientsFailure3() throws Exception {
        try {
            EqualToFilter equalToFilter = new EqualToFilter("DAOConfigurationException", new Boolean(false));
            manager.searchClients(equalToFilter);
            fail("ClientManagerException is expected.");
        } catch (ClientManagerException e) {

            // expected
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
    public void testGetClientStatusFailure1() throws Exception {
        try {
            manager.getClientStatus(-1);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {

            // expected
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
    public void testGetClientStatusFailure2() throws Exception {
        try {
            manager.getClientStatus(10001);
            fail(" ClientManagerException is expected.");
        } catch (ClientManagerException e) {

            // expected
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
    public void testGetClientStatusFailure3() throws Exception {
        try {
            manager.getClientStatus(10002);
            fail(" ClientManagerException is expected.");
        } catch (ClientManagerException e) {

            // expected
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
    public void testGetClientStatusFailure4() throws Exception {
        try {
            manager.getClientStatus(101);
            fail(" ClientManagerException is expected.");
        } catch (ClientManagerException e) {

            // expected
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
     * Inner dao throw DAOException, ClientManagerException is expected.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void testGetAllClientStatusesFailure1() throws Exception {
        MockClientStatusDAO dao = new MockClientStatusDAO();
        dao.setFlag(1);
        this.injectField("clientStatusDAO", dao);

        try {
            manager.getAllClientStatuses();
            fail(" ClientManagerException is expected.");
        } catch (ClientManagerException e) {
            // expected
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
    public void testGetAllClientStatusesFailure2() throws Exception {
        MockClientStatusDAO dao = new MockClientStatusDAO();
        dao.setFlag(2);
        this.injectField("clientStatusDAO", dao);

        try {
            manager.getAllClientStatuses();

            fail(" ClientManagerException is expected.");
        } catch (ClientManagerException e) {

            // expected
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
    public void testGetClientsForStatusFailure1() throws Exception {
        try {
            manager.getClientsForStatus(null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {

            // expected
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
    public void testGetClientsForStatusFailure2() throws Exception {
        try {
            ClientStatus s = new ClientStatus();
            s.setName(null);
            s.setDescription("des");
            s.setDeleted(false);
            manager.getClientsForStatus(s);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {

            // expected
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
    public void testGetClientsForStatusFailure3() throws Exception {
        try {
            ClientStatus s = new ClientStatus();
            s.setName(" ");
            s.setDescription("des");
            s.setDeleted(false);
            manager.getClientsForStatus(s);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // expected
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
    public void testGetClientsForStatusFailure4() throws Exception {
        try {
            ClientStatus s = new ClientStatus();
            s.setName("name");
            s.setDescription("   ");
            s.setDeleted(false);
            manager.getClientsForStatus(s);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // expected
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
    public void testGetClientsForStatusFailure5() throws Exception {
        try {
            ClientStatus s = new ClientStatus();
            s.setName("name");
            s.setDescription("des");
            s.setDeleted(true);
            manager.getClientsForStatus(s);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // expected
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
    public void testGetClientsForStatusFailure6() throws Exception {
        try {
            ClientStatus s = new ClientStatus();
            s.setName("name");
            s.setDescription("des");
            s.setDeleted(true);
            manager.getClientsForStatus(s);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // expected
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
    public void testGetClientsForStatusFailure7() throws Exception {
        try {
            ClientStatus s = new ClientStatus();
            s.setName("entity");
            s.setDescription("des");
            s.setDeleted(false);
            manager.getClientsForStatus(s);
            fail("ClientManagerException is expected.");
        } catch (ClientManagerException e) {

            // expected
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
    public void testGetClientsForStatusFailure8() throws Exception {
        try {
            ClientStatus s = new ClientStatus();
            s.setName("DAOException");
            s.setDescription("des");
            s.setDeleted(false);
            manager.getClientsForStatus(s);
            fail("ClientManagerException is expected.");
        } catch (ClientManagerException e) {

            // expected
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
    public void testGetClientsForStatusFailure9() throws Exception {
        try {
            ClientStatus s = new ClientStatus();
            s.setName("DAOConfigurationException");
            s.setDescription("des");
            s.setDeleted(false);
            manager.getClientsForStatus(s);
            fail("ClientManagerException is expected.");
        } catch (ClientManagerException e) {

            // expected
        }
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
    public void testCreateClientStatusFailure1() throws Exception {
        try {
            manager.createClientStatus(null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {

            // expected
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
    public void testCreateClientStatusFailure2() throws Exception {
        try {
            ClientStatus status = new ClientStatus();
            status.setName(null);
            status.setDeleted(false);
            status.setDescription("des");
            manager.createClientStatus(status);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {

            // expected
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
    public void testCreateClientStatusFailure3() throws Exception {
        try {
            ClientStatus status = new ClientStatus();
            status.setName(" ");
            status.setDeleted(false);
            status.setDescription("des");
            manager.createClientStatus(status);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {

            // expected
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
    public void testCreateClientStatusFailure4() throws Exception {
        try {
            ClientStatus status = new ClientStatus();
            status.setName("name");
            status.setDeleted(false);
            status.setDescription(null);
            manager.createClientStatus(status);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {

            // expected
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
    public void testCreateClientStatusFailure5() throws Exception {
        try {
            ClientStatus status = new ClientStatus();
            status.setName("name");
            status.setDeleted(false);
            status.setDescription("  ");
            manager.createClientStatus(status);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {

            // expected
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
    public void testCreateClientStatusFailure6() throws Exception {
        try {
            ClientStatus status = new ClientStatus();
            status.setName("name");
            status.setDeleted(true);
            status.setDescription("des");
            manager.createClientStatus(status);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {

            // expected
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
    public void testCreateClientStatusFailure7() throws Exception {
        try {
            ClientStatus status = new ClientStatus();
            status.setName("DAOException");
            status.setDeleted(false);
            status.setDescription("des");
            manager.createClientStatus(status);
            fail("ClientManagerException is expected.");
        } catch (ClientManagerException e) {

            // expected
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
    public void testCreateClientStatusFailure8() throws Exception {
        try {
            ClientStatus status = new ClientStatus();
            status.setName("DAOConfigurationException");
            status.setDeleted(false);
            status.setDescription("des");
            manager.createClientStatus(status);
            fail("ClientManagerException is expected.");
        } catch (ClientManagerException e) {

            // expected
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
    public void testUpdateClientStatusFailure1() throws Exception {
        try {
            manager.updateClientStatus(null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // expected
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
    public void testUpdateClientStatusFailure2() throws Exception {
        try {
            ClientStatus status = new ClientStatus();
            status.setName(null);
            status.setDeleted(false);
            status.setDescription("des");

            manager.updateClientStatus(status);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // expected
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
    public void testUpdateClientStatusFailure3() throws Exception {
        try {
            ClientStatus status = new ClientStatus();
            status.setName("  ");
            status.setDeleted(false);
            status.setDescription("des");

            manager.updateClientStatus(status);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // expected
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
    public void testUpdateClientStatusFailure4() throws Exception {
        try {
            ClientStatus status = new ClientStatus();
            status.setName("name");
            status.setDeleted(false);
            status.setDescription(null);

            manager.updateClientStatus(status);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // expected
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
    public void testUpdateClientStatusFailure5() throws Exception {
        try {
            ClientStatus status = new ClientStatus();
            status.setName("name");
            status.setDeleted(false);
            status.setDescription("   ");

            manager.updateClientStatus(status);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // expected
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
    public void testUpdateClientStatusFailure6() throws Exception {
        try {
            ClientStatus status = new ClientStatus();
            status.setName("name");
            status.setDeleted(true);
            status.setDescription("d");

            manager.updateClientStatus(status);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // expected
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
    public void testUpdateClientStatusFailure7() throws Exception {
        try {
            ClientStatus status = new ClientStatus();
            status.setName("name");
            status.setDeleted(false);
            status.setDescription("d");
            status.setId(-1);

            manager.updateClientStatus(status);
            fail("ClientEntityNotFoundException is expected.");
        } catch (ClientEntityNotFoundException e) {
            // expected
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
    public void testUpdateClientStatusFailure8() throws Exception {
        try {
            ClientStatus status = new ClientStatus();
            status.setName("DAOException");
            status.setDeleted(false);
            status.setDescription("d");
            status.setId(1L);

            manager.updateClientStatus(status);
            fail("ClientManagerException is expected.");
        } catch (ClientManagerException e) {
            // expected
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
    public void testUpdateClientStatusFailure9() throws Exception {
        try {
            ClientStatus status = new ClientStatus();
            status.setName("DAOConfigurationException");
            status.setDeleted(false);
            status.setDescription("d");
            status.setId(1L);

            manager.updateClientStatus(status);
            fail("ClientManagerException is expected.");
        } catch (ClientManagerException e) {
            // expected
        }
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
    public void testDeleteClientStatusFailure1() throws Exception {
        try {
            manager.deleteClientStatus(null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // expected
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
    public void testDeleteClientStatusFailure2() throws Exception {
        try {
            ClientStatus status = new ClientStatus();
            status.setName("Dok");
            status.setDeleted(true);
            status.setDescription("d");
            status.setId(1L);

            manager.deleteClientStatus(status);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // expected
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
    public void testDeleteClientStatusFailure3() throws Exception {
        try {
            ClientStatus status = new ClientStatus();
            status.setName("EntityNotFoundException");
            status.setDeleted(false);
            status.setDescription("d");
            status.setId(1L);

            manager.deleteClientStatus(status);
            fail("ClientEntityNotFoundException is expected.");
        } catch (ClientEntityNotFoundException e) {

            // expected
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
    public void testDeleteClientStatusFailure4() throws Exception {
        try {
            ClientStatus status = new ClientStatus();
            status.setName("DAOException");
            status.setDeleted(false);
            status.setDescription("d");
            status.setId(1L);

            manager.deleteClientStatus(status);
            fail("ClientManagerException is expected.");
        } catch (ClientManagerException e) {

            // expected
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
    public void testDeleteClientStatusFailure5() throws Exception {
        try {
            ClientStatus status = new ClientStatus();
            status.setName("DAOConfigurationException");
            status.setDeleted(false);
            status.setDescription("d");
            status.setId(1L);

            manager.deleteClientStatus(status);
            fail("ClientManagerException is expected.");
        } catch (ClientManagerException e) {

            // expected
        }
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
