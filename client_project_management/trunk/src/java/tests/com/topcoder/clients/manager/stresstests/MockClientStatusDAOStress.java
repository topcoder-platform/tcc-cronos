/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.manager.stresstests;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.topcoder.clients.dao.ClientStatusDAO;
import com.topcoder.clients.dao.DAOException;
import com.topcoder.clients.model.AuditableEntity;
import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.ClientStatus;
import com.topcoder.search.builder.filter.Filter;

/**
 * Mock ClientStatusDAO implementation for stress testing.
 *
 * @author mayday
 * @version 1.0
 *
 */
public class MockClientStatusDAOStress implements ClientStatusDAO<ClientStatus, Long> {

    /**
     * Defines the operation that performs the retrieval of an entity using the given id from the persistence.
     *
     * @param id the id
     * @return AuditableEntity
     */
    public AuditableEntity retrieveById(Serializable id) throws DAOException {
        return createClientStatus();
    }

    /**
     * Defines the operation that performs the retrieval of all entities from the persistence.
     *
     * @return List
     */
    public List<ClientStatus> retrieveAll() throws DAOException {
        return buildClientStatusList();
    }

    /**
     * Defines the operation that performs the retrieval of all entities that have the given name in the persistence.
     *
     * @param name the name for search
     * @return List;
     */
    public List<ClientStatus> searchByName(String name) {
        return buildClientStatusList();
    }

    /**
     * Defines the operation that performs the retrieval of all entities that match the given filter in the persistence.
     *
     * @param filter the filter
     * @return List
     */
    public List<ClientStatus> search(Filter filter) throws DAOException {
        return buildClientStatusList();
    }

    /**
     * Defines the operation that performs the salvation of an entity in the persistence.
     *
     * @param entity the AuditableEntity to save.
     * @return the saved entity
     */
    public AuditableEntity save(AuditableEntity entity) throws DAOException {
        entity.setCreateDate(new Date());
        entity.setCreateUsername("test");
        entity.setModifyUsername("stress");
        return entity;
    }

    /**
     * Defines the operation that performs the deletion of an entity from the persistence.
     *
     * @param entity the entity to delete
     */
    public void delete(AuditableEntity entity) throws DAOException {
        entity.setModifyUsername("stress");
    }

    /**
     *
     * Defines the operation that performs the retrieval of the list with clients with the given
     * status from the persistence.
     *
     * @param status the status to get the clients
     * @return a list of client, might be empty
     */
    public List<Client> getClientsWithStatus(ClientStatus status) throws DAOException {
        return buildClientList();
    }

    /**
     * Build a client list for test.
     * @return the created client list.
     */
    private List<Client> buildClientList() {
        List<Client> list = new ArrayList<Client>();
        list.add(createClient());
        return list;
    }

    /**
     * Build a client status list for test.
     * @return the created client status list.
     */
    private List<ClientStatus> buildClientStatusList() {
        List<ClientStatus> list = new ArrayList<ClientStatus>();
        list.add(createClientStatus());
        return list;
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
