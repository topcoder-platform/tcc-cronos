/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices.mock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.topcoder.clients.dao.ClientStatusDAO;
import com.topcoder.clients.dao.DAOException;
import com.topcoder.clients.dao.EntityNotFoundException;
import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.ClientStatus;
import com.topcoder.search.builder.filter.Filter;

/**
 * Mock a ClientStatusDAO implementation for unit testing.
 *
 * @author  CaDenza
 * @version 1.0
 *
 */
public class MockClientStatusDAO implements ClientStatusDAO {

    /**
     * Mapping of ClientStatus and its ID.
     */
    private Map<String, ClientStatus> mapping = new HashMap<String, ClientStatus>();

    /**
     * Default constructor.
     */
    public MockClientStatusDAO() {
        // do nothing.
    }

    /**
     * Get all the ClientStatus.
     *
     * @return a list of ClientStatus, might be empty
     *
     * @throws DAOException
     *             for testing purpose
     */
    public List<ClientStatus> retrieveAll() throws DAOException {
        return new ArrayList<ClientStatus>();
    }

    /**
     * Search ClientStatus with given name.
     *
     * @param name
     *            the name
     * @return a list of ClientStatus , might be empty
     */
    public List<ClientStatus> searchByName(String name) {
        return new ArrayList<ClientStatus>();
    }

    /**
     * Search by filter.
     *
     * @param filter
     *            the filter
     * @return a list of ClientStatus , might be empty
     * @throws DAOException
     *             for testing purpose
     */
    public List<ClientStatus> search(Filter filter) throws DAOException {
        return new ArrayList<ClientStatus>();
    }

    /**
     *
     * Get clients with status.
     *
     * @param status
     *            the status to get the clients
     * @return a list of client, might be empty
     *
     * @throws DAOException
     *             for testing purpose
     */
    public List<Client> getClientsWithStatus(ClientStatus status) throws DAOException {
        return new ArrayList<Client>();
    }

    /**
     * Delete ClientStatus.
     *
     * @param entity
     *      The entity to be deleted.
     *
     * @throws EntityNotFoundException
     *      if entity not found.
     */
    public void delete(ClientStatus entity) throws EntityNotFoundException {
        ClientStatus status = mapping.get(String.valueOf(entity.getId()));
        if (status == null) {
            throw new EntityNotFoundException("");
        }
    }

    /**
     * Retrieve ClientStatus by its ID.
     *
     * @param id
     *      The id of client status.
     *
     * @return ClientStatus instance.
     *
     * @throws EntityNotFoundException
     *      if entity not found.
     */
    public ClientStatus retrieveById(Long id) throws EntityNotFoundException {
        ClientStatus status = mapping.get(String.valueOf(id));
        if (status == null) {
            throw new EntityNotFoundException("");
        }
        return status;
    }

    /**
     * Save ClientStatus.
     *
     * @param entity
     *      The entity to be saved.
     * @return persisted entity.
     * @throws DAOException
     *      for testing only.
     */
    public ClientStatus save(ClientStatus entity) throws DAOException {
        mapping.put(String.valueOf(entity.getId()), entity);
        return entity;
    }
}