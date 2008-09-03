/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.manager.accuracytests;

import java.util.ArrayList;
import java.util.List;

import com.topcoder.clients.dao.ClientStatusDAO;
import com.topcoder.clients.dao.DAOException;
import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.ClientStatus;
import com.topcoder.search.builder.filter.Filter;


/**
 * Mock Implementation of ClientStatusDAO.
 *
 * @author onsky
 * @version 1.0
 */
public class MockClientStatusDAO implements ClientStatusDAO {
    /**
     * Mock Method.
     *
     * @param status mock parameter
     *
     * @return Mock value
     *
     * @throws DAOException if such error
     * @throws com.topcoder.clients.dao.EntityNotFoundException if such error
     */
    public List<Client> getClientsWithStatus(ClientStatus status)
        throws DAOException {
        List<Client> list = new ArrayList<Client>();
        list.add(MockClientDAO.getSampleClient(1));
        list.add(MockClientDAO.getSampleClient(3));

        return list;
    }

    /**
     * Mock Method.
     *
     * @param entity mock parameter
     *
     * @throws DAOException if such error
     */
    public void delete(ClientStatus entity) throws DAOException {
    }

    /**
     * Mock Method.
     *
     * @return Mock value
     *
     * @throws DAOException if such error
     */
    @SuppressWarnings("unchecked")
    public List retrieveAll() throws DAOException {
        return null;
    }

    /**
     * Mock Method.
     *
     * @param id mock parameter
     *
     * @return Mock value
     *
     * @throws DAOException if such error
     */
    public ClientStatus retrieveById(Long id)
        throws DAOException {
        ClientStatus status = new ClientStatus();
        status.setName("test");
        status.setDeleted(false);
        status.setDescription("desc");
        status.setId((Long) id);

        return status;
    }

    /**
     * Mock Method.
     *
     * @param entity mock parameter
     *
     * @return Mock value
     *
     * @throws DAOException if such error
     */
    public ClientStatus save(ClientStatus entity)
        throws DAOException {
        entity.setId(10L);

        return entity;
    }

    /**
     * Mock Method.
     *
     * @param filter mock value
     *
     * @return Mock value
     *
     * @throws DAOException if such error
     */
    @SuppressWarnings("unchecked")
    public List search(Filter filter) throws DAOException {
        return null;
    }

    /**
     * Mock Method.
     *
     * @param name mock value
     *
     * @return Mock value
     *
     * @throws DAOException if such error
     */
    @SuppressWarnings("unchecked")
    public List searchByName(String name) throws DAOException {
        return null;
    }
}
