/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.manager.stresstests;

import java.util.List;

import com.topcoder.clients.manager.dao.DAOClientManager;
import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.ClientStatus;
import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.defaults.DefaultConfigurationObject;
import com.topcoder.search.builder.filter.EqualToFilter;

import junit.framework.TestCase;

/**
 * <p>
 * Stress tests for <code>DAOClientManager</code>.
 * </p>
 * @author mayday
 * @version 1.0
 *
 */
public class DAOClientManagerStressTest extends TestCase {

    /**
     * Number of repeat.
     */
    private static final int REPEAT_NUMBER = 10;

    /**
     * <p>
     * DAOClientManager used in test.
     * </p>
     */
    private DAOClientManager manager;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();
        StressTestHelper.loadConfig();

        ConfigurationObject obj = new DefaultConfigurationObject("root");
        obj.setPropertyValue("id_generator_name", "test");
        obj.setPropertyValue("logger_name", "logger");

        ConfigurationObject child = new DefaultConfigurationObject("object_factory_configuration");

        obj.addChild(child);
        obj.setPropertyValue("client_dao", "com.topcoder.clients.manager.stresstests.MockClientDAOStress");
        obj.setPropertyValue("client_status_dao", "com.topcoder.clients.manager.stresstests.MockClientStatusDAOStress");

        manager = new DAOClientManager(obj);
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        StressTestHelper.clearConfig();
        manager = null;
    }

    /**
     * Test method for {@link com.topcoder.clients.manager.dao.DAOClientManager#
     * createClient(com.topcoder.clients.model.Client)}.
     *
     * Test create client and check result and see time.
     *
     * @throws Exception to JUnit
     */
    public void testCreateClient() throws Exception {

        long time = System.currentTimeMillis();

        for (int i = 0; i < REPEAT_NUMBER; i++) {
            Client client = createClient();
            Client ret = manager.createClient(client);
            assertEquals("should be client", "client", ret.getName());
        }

        time = System.currentTimeMillis() - time;

        System.out.println("retrieve took " + time + " ms");
    }

    /**
     * Test method for {@link com.topcoder.clients.manager.dao.DAOClientManager#
     * updateClient(com.topcoder.clients.model.Client)}.
     *
     * Test update client and check result and see time.
     *
     * @throws Exception to JUnit
     */
    public void testUpdateClient() throws Exception {

        long time = System.currentTimeMillis();

        for (int i = 0; i < REPEAT_NUMBER; i++) {
            Client client = createClient();
            Client ret = manager.updateClient(client);
            assertEquals("should be stress", "stress", ret.getModifyUsername());
        }

        time = System.currentTimeMillis() - time;

        System.out.println("retrieve took " + time + " ms");
    }

    /**
     * Test method for {@link com.topcoder.clients.manager.dao.DAOClientManager#
     * deleteClient(com.topcoder.clients.model.Client)}.
     *
     * Test delete client and check result and see time.
     *
     * @throws Exception to JUnit
     */
    public void testDeleteClient() throws Exception {

        long time = System.currentTimeMillis();

        for (int i = 0; i < REPEAT_NUMBER; i++) {
            Client client = createClient();
            Client ret = manager.deleteClient(client);
            assertEquals("should be stress", "stress", ret.getModifyUsername());
            assertTrue("should be true", ret.isDeleted());
        }

        time = System.currentTimeMillis() - time;

        System.out.println("retrieve took " + time + " ms");
    }

    /**
     * Test method for {@link com.topcoder.clients.manager.dao.DAOClientManager#
     * setClientCodeName(com.topcoder.clients.model.Client, java.lang.String)}.
     *
     * Test set client code name and check result and see time.
     *
     * @throws Exception to JUnit
     */
    public void testSetClientCodeName() throws Exception {

        long time = System.currentTimeMillis();

        for (int i = 0; i < REPEAT_NUMBER; i++) {
            Client client = createClient();
            Client ret = manager.setClientCodeName(client, "codeName");
            assertEquals("should be codeName", "codeName", ret.getCodeName());
        }

        time = System.currentTimeMillis() - time;

        System.out.println("retrieve took " + time + " ms");
    }

    /**
     * Test method for {@link com.topcoder.clients.manager.dao.DAOClientManager#
     * setClientStatus(com.topcoder.clients.model.Client, com.topcoder.clients.model.ClientStatus)}.
     *
     * Test set client status and check result and see time.
     *
     * @throws Exception to JUnit
     */
    public void testSetClientStatus() throws Exception {

        long time = System.currentTimeMillis();

        for (int i = 0; i < REPEAT_NUMBER; i++) {
            Client client = createClient();
            Client ret = manager.setClientStatus(client, createClientStatus());
            assertEquals("should be client status", "client status", ret.getClientStatus().getName());
        }

        time = System.currentTimeMillis() - time;

        System.out.println("retrieve took " + time + " ms");
    }

    /**
     * Test method for {@link com.topcoder.clients.manager.dao.DAOClientManager#retrieveClient(long)}.
     *
     * Test retrieve client and check result and see time.
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveClient() throws Exception {

        long time = System.currentTimeMillis();

        for (int i = 0; i < REPEAT_NUMBER; i++) {
            Client ret = manager.retrieveClient(1);
            assertEquals("should be client", "client", ret.getName());
        }

        time = System.currentTimeMillis() - time;

        System.out.println("retrieve took " + time + " ms");
    }

    /**
     * Test method for {@link com.topcoder.clients.manager.dao.DAOClientManager#retrieveAllClients()}.
     *
     * Test retrieve all clients and check result and see time.
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveAllClients() throws Exception {

        long time = System.currentTimeMillis();

        for (int i = 0; i < REPEAT_NUMBER; i++) {
            List<Client> ret = manager.retrieveAllClients();
            assertTrue("should be 1", ret.size() == 1);
            assertEquals("should be client", "client", ret.get(0).getName());
        }

        time = System.currentTimeMillis() - time;

        System.out.println("retrieve took " + time + " ms");
    }

    /**
     * Test method for {@link com.topcoder.clients.manager.dao.DAOClientManager#
     * searchClientsByName(java.lang.String)}.
     *
     * Test search clients by name and check result and see time.
     *
     * @throws Exception to JUnit
     */
    public void testSearchClientsByName() throws Exception {

        long time = System.currentTimeMillis();

        for (int i = 0; i < REPEAT_NUMBER; i++) {
            List<Client> ret = manager.searchClientsByName("client");
            assertTrue("should be 1", ret.size() == 1);
            assertEquals("should be client", "client", ret.get(0).getName());
        }

        time = System.currentTimeMillis() - time;

        System.out.println("retrieve took " + time + " ms");
    }

    /**
     * Test method for {@link com.topcoder.clients.manager.dao.DAOClientManager#
     * searchClients(com.topcoder.search.builder.filter.Filter)}.
     *
     * Test search clients and check result and see time.
     *
     * @throws Exception to JUnit
     */
    public void testSearchClients() throws Exception {

        long time = System.currentTimeMillis();

        for (int i = 0; i < REPEAT_NUMBER; i++) {
            List<Client> ret = manager.searchClients(new EqualToFilter("client", false));
            assertTrue("should be 1", ret.size() == 1);
            assertEquals("should be client", "client", ret.get(0).getName());
        }

        time = System.currentTimeMillis() - time;

        System.out.println("retrieve took " + time + " ms");
    }

    /**
     * Test method for {@link com.topcoder.clients.manager.dao.DAOClientManager#getClientStatus(long)}.
     *
     * Test get client status and check result and see time.
     *
     * @throws Exception to JUnit
     */
    public void testGetClientStatus() throws Exception {

        long time = System.currentTimeMillis();

        for (int i = 0; i < REPEAT_NUMBER; i++) {
            ClientStatus ret = manager.getClientStatus(3);
            assertEquals("should be client status", "client status", ret.getName());
        }

        time = System.currentTimeMillis() - time;

        System.out.println("retrieve took " + time + " ms");
    }

    /**
     * Test method for {@link com.topcoder.clients.manager.dao.DAOClientManager#getAllClientStatuses()}.
     *
     * Test get all client status and check result and see time.
     *
     * @throws Exception to JUnit
     */
    public void testGetAllClientStatuses() throws Exception {

        long time = System.currentTimeMillis();

        for (int i = 0; i < REPEAT_NUMBER; i++) {
            List<ClientStatus> ret = manager.getAllClientStatuses();
            assertTrue("should be 1", ret.size() == 1);
            assertEquals("should be client status", "client status", ret.get(0).getName());
        }

        time = System.currentTimeMillis() - time;

        System.out.println("retrieve took " + time + " ms");
    }

    /**
     * Test method for {@link com.topcoder.clients.manager.dao.DAOClientManager#
     * getClientsForStatus(com.topcoder.clients.model.ClientStatus)}.
     *
     * Test get clients for status and check result and see time.
     *
     * @throws Exception to JUnit
     */
    public void testGetClientsForStatus() throws Exception {

        long time = System.currentTimeMillis();

        for (int i = 0; i < REPEAT_NUMBER; i++) {
            List<Client> ret = manager.getClientsForStatus(createClientStatus());
            assertTrue("should be 1", ret.size() == 1);
            assertEquals("should be client", "client", ret.get(0).getName());
        }

        time = System.currentTimeMillis() - time;

        System.out.println("retrieve took " + time + " ms");
    }

    /**
     * Test method for {@link com.topcoder.clients.manager.dao.DAOClientManager#
     * createClientStatus(com.topcoder.clients.model.ClientStatus)}.
     *
     * Test create client status and check result and see time.
     *
     * @throws Exception to JUnit
     */
    public void testCreateClientStatus() throws Exception {

        long time = System.currentTimeMillis();

        for (int i = 0; i < REPEAT_NUMBER; i++) {
            ClientStatus ret = manager.createClientStatus(createClientStatus());
            assertEquals("should be client status", "client status", ret.getName());
        }

        time = System.currentTimeMillis() - time;

        System.out.println("retrieve took " + time + " ms");
    }

    /**
     * Test method for {@link com.topcoder.clients.manager.dao.DAOClientManager#
     * updateClientStatus(com.topcoder.clients.model.ClientStatus)}.
     *
     * Test update client status and check result and see time.
     *
     * @throws Exception to JUnit
     */
    public void testUpdateClientStatus() throws Exception {

        long time = System.currentTimeMillis();

        for (int i = 0; i < REPEAT_NUMBER; i++) {
            ClientStatus ret = manager.updateClientStatus(createClientStatus());
            assertEquals("should be stress", "stress", ret.getModifyUsername());
        }

        time = System.currentTimeMillis() - time;

        System.out.println("retrieve took " + time + " ms");
    }

    /**
     * Test method for {@link com.topcoder.clients.manager.dao.DAOClientManager#
     * deleteClientStatus(com.topcoder.clients.model.ClientStatus)}.
     *
     * Test create client and check result and see time.
     *
     * @throws Exception to JUnit
     */
    public void testDeleteClientStatus() throws Exception {

        long time = System.currentTimeMillis();

        for (int i = 0; i < REPEAT_NUMBER; i++) {
            ClientStatus ret = manager.deleteClientStatus(createClientStatus());
            assertEquals("should be stress", "stress", ret.getModifyUsername());
            assertTrue("should be true", ret.isDeleted());
        }

        time = System.currentTimeMillis() - time;

        System.out.println("retrieve took " + time + " ms");
    }

    /**
     * Create a client for test.
     * @return the created client.
     */
    private Client createClient() {
        Client client = new Client();
        client.setStartDate(StressTestHelper.STARTDATE);
        client.setEndDate(StressTestHelper.ENDDATE);
        client.setName("client");
        client.setDeleted(false);
        client.setId(1L);
        return client;
    }

    /**
     * Create a client status for test.
     * @return the created client status.
     */
    private ClientStatus createClientStatus() {
        ClientStatus status = new ClientStatus();
        status.setDeleted(false);
        status.setId(3L);
        status.setDescription("des");
        status.setName("client status");
        return status;
    }
}
