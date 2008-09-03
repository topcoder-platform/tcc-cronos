/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.manager.accuracytests;

import com.topcoder.clients.manager.ClientManager;
import com.topcoder.clients.manager.dao.DAOClientManager;
import com.topcoder.clients.manager.dao.Util;
import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.ClientStatus;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.defaults.DefaultConfigurationObject;

import com.topcoder.search.builder.filter.EqualToFilter;

import junit.framework.TestCase;

import java.util.Calendar;
import java.util.List;


/**
 * Accuracy test for ClientManager implementation.
 *
 * @author onsky
 * @version 1.0
 */
public class ClientManagerTests extends TestCase {
    /** Represents the ClientManager instance for testing. */
    private ClientManager manager = null;

    /**
     * Set up the environment.
     *
     * @throws Exception to junit
     */
    public void setUp() throws Exception {
        Util.clearConfigManager();
        Util.loadConfiguration();

        ConfigurationObject obj = new DefaultConfigurationObject("root");
        obj.setPropertyValue("id_generator_name", "test");
        obj.setPropertyValue("logger_name", "testlog");

        ConfigurationObject child = new DefaultConfigurationObject("object_factory_configuration");

        obj.addChild(child);
        obj.setPropertyValue("client_dao", "com.topcoder.clients.manager.accuracytests.MockClientDAO");
        obj.setPropertyValue("client_status_dao", "com.topcoder.clients.manager.accuracytests.MockClientStatusDAO");

        manager = new DAOClientManager(obj);
    }

    /**
     * Clear the ConfigManager.
     *
     * @throws Exception to junit
     */
    public void tearDown() throws Exception {
        Util.clearConfigManager();
    }

    /**
     * <p>Accuracy test for the constructor <code>DAOClientManager()</code>, expects the instance is created
     * properly.</p>
     *
     * @throws Exception to JUnit
     */
    public void testCtor1Accuracy() throws Exception {
        manager = new DAOClientManager();
        assertNotNull("Failed to create the DAOClientManager instance.", manager);
        assertNotNull("Failed to create the DAOClientManager instance.",
                TestHelper.getPropertyByReflection(manager, "clientDAO"));
        assertNotNull("Failed to create the DAOClientManager instance.",
                TestHelper.getPropertyByReflection(manager, "clientStatusDAO"));
    }

    /**
     * <p>Accuracy test for the constructor <code>DAOClientManager(ConfigurationObject)</code>, expects the
     * instance is created properly.</p>
     *
     * @throws Exception to JUnit
     */
    public void testCtor2Accuracy() throws Exception {
        assertNotNull("Failed to create the DAOClientManager instance.", manager);
        assertNotNull("Failed to create the DAOClientManager instance.",
                TestHelper.getPropertyByReflection(manager, "clientDAO"));
        assertNotNull("Failed to create the DAOClientManager instance.",
                TestHelper.getPropertyByReflection(manager, "clientStatusDAO"));
    }

    /**
     * <p>Accuracy test for method createClient.</p>
     *
     * @throws Exception to junit
     */
    public void testCreateClient() throws Exception {
        // create client
        Client client = new Client();
        Calendar calendar = Calendar.getInstance();
        client.setEndDate(calendar.getTime());
        calendar.set(2008, 1, 1);
        client.setStartDate(calendar.getTime());
        client.setName("client name");
        client.setDeleted(false);

        Client created = manager.createClient(client);
        assertTrue("client must be create with an generated id", created.getId() > 0);
        assertEquals("the start date should not be changed", client.getStartDate(), created.getStartDate());
        assertEquals("the end date should not be changed", client.getEndDate(), created.getEndDate());
    }

    /**
     * <p>Accuracy test for method retrieveClient and updateClient.</p>
     *
     * @throws Exception to junit
     */
    public void testFindAndUpdateClient() throws Exception {
        // find client by id
        Client found = manager.retrieveClient(1L);
        assertNotNull("client with given id must exist", found);
        // update client
        found.setName("update client name");

        Client updated = manager.updateClient(found);
        assertEquals("client must be updated", "update client name", updated.getName());
    }

    /**
     * <p>Accuracy test for method deleteClient.</p>
     *
     * @throws Exception to junit
     */
    public void testDeleteClient() throws Exception {
        // find client by id
        Client found = manager.retrieveClient(1L);

        // delete client
        Client deleted = manager.deleteClient(found);
        assertTrue("the client must be deleted", deleted.isDeleted());
    }

    /**
     * <p>Accuracy test for method retrieveAllClients.</p>
     *
     * @throws Exception to junit
     */
    public void testRetrieveAllClients() throws Exception {
        // get all clients
        List<Client> clients = manager.retrieveAllClients();
        assertEquals("there must be 3 clients in all", 3, clients.size());
    }

    /**
     * <p>Accuracy test for method searchClientsByName.</p>
     *
     * @throws Exception to junit
     */
    public void testSearchClientsByName() throws Exception {
        // search client by name
        List<Client> clients = manager.searchClientsByName("test");
        assertEquals("one record must return", 1, clients.size());
        assertEquals("client name must be correct", "test", clients.get(0).getName());
    }

    /**
     * <p>Accuracy test for method searchClientsByName.</p>
     *
     * @throws Exception to junit
     */
    public void testSearchClients() throws Exception {
        // search clients by filter
        List<Client> clients = manager.searchClients(new EqualToFilter("test", new Boolean(false)));
        assertEquals("two record must return", 2, clients.size());
    }

    /**
     * <p>Accuracy test for method getClientsForStatus.</p>
     *
     * @throws Exception to junit
     */
    public void testGetClientsForStatus() throws Exception {
        ClientStatus status = new ClientStatus();
        status.setName("test");
        status.setDeleted(false);
        status.setDescription("desc");

        List<Client> clients = manager.getClientsForStatus(status);
        assertEquals("two record must return", 2, clients.size());
    }

    /**
     * <p>Accuracy test for method createClientStatus.</p>
     *
     * @throws Exception to junit
     */
    public void testCreateClientStatus() throws Exception {
        // create client status
        ClientStatus status = new ClientStatus();
        status.setName("test");
        status.setDeleted(false);
        status.setDescription("desc");

        ClientStatus created = manager.createClientStatus(status);
        assertTrue("ClientStatus must be created", created.getId() > 0);
    }

    /**
     * <p>Accuracy test for method updateClientStatus.</p>
     *
     * @throws Exception to junit
     */
    public void testUpdateClientStatus() throws Exception {
        Client found = manager.retrieveClient(1L);
        ClientStatus status = found.getClientStatus();
        status.setName("update client status name");

        ClientStatus updated = manager.updateClientStatus(status);
        assertEquals("client status must be updated", "update client status name", updated.getName());
    }

    /**
     * <p>Accuracy test for method deleteClientStatus.</p>
     *
     * @throws Exception to junit
     */
    public void testDeleteClientStatus() throws Exception {
        Client found = manager.retrieveClient(1L);
        ClientStatus status = found.getClientStatus();

        // delete client
        ClientStatus deleted = manager.deleteClientStatus(status);
        assertTrue("the client must be deleted", deleted.isDeleted());
    }
}
