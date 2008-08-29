/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.manager.accuracytests;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.topcoder.clients.dao.ClientDAO;
import com.topcoder.clients.dao.DAOException;
import com.topcoder.clients.dao.EntityNotFoundException;
import com.topcoder.clients.model.AuditableEntity;
import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.ClientStatus;
import com.topcoder.clients.model.Project;
import com.topcoder.search.builder.filter.Filter;


/**
 * Mock Implementation of ClientDAO.
 *
 * @author onsky
 * @version 1.0
  */
public class MockClientDAO implements ClientDAO<Client, Long> {
    /**
     * Mock Method.
     *
     * @param client Mock parameter
     *
     * @return Mock value
     *
     * @throws EntityNotFoundException if such error
     * @throws DAOException if such error
     */
    public List<Project> getProjectsForClient(Client client)
        throws DAOException {
        List<Project> list = new ArrayList<Project>();
        list.add(MockProjectDAO.getSampleProject(1));
        list.add(MockProjectDAO.getSampleProject(3));

        return list;
    }

    /**
     * Mock Method.
     *
     * @param entity Mock parameter
     *
     * @throws DAOException if such error
     */
    public void delete(AuditableEntity entity) throws DAOException {
        entity.setId(0);
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
        List<Client> list = new ArrayList<Client>();
        list.add(getSampleClient(1));
        list.add(getSampleClient(2));
        list.add(getSampleClient(3));

        return list;
    }

    /**
     * Mock Method.
     *
     * @param id Mock parameter
     *
     * @return Mock value
     *
     * @throws DAOException if such error
     */
    public AuditableEntity retrieveById(Serializable id)
        throws DAOException {
        return getSampleClient((Long) id);
    }

    /**
     * Mock Method.
     *
     * @param entity Mock parameter
     *
     * @return Mock value
     *
     * @throws DAOException if such error
     */
    public AuditableEntity save(AuditableEntity entity)
        throws DAOException {
        entity.setId(1L);

        return entity;
    }

    /**
     * Mock Method.
     *
     * @param filter Mock parameter
     *
     * @return Mock value
     *
     * @throws DAOException if such error
     */
    @SuppressWarnings("unchecked")
    public List search(Filter filter) throws DAOException {
        List<Client> list = new ArrayList<Client>();
        list.add(getSampleClient(1));
        list.add(getSampleClient(2));

        return list;
    }

    /**
     * Mock Method.
     *
     * @param name Mock parameter
     *
     * @return Mock value
     *
     * @throws DAOException if such error
     */
    @SuppressWarnings("unchecked")
    public List searchByName(String name) throws DAOException {
        List<Client> list = new ArrayList<Client>();
        Client client = getSampleClient(10L);
        client.setName(name);
        list.add(client);

        return list;
    }

    /**
     * Mock Client instance.
     *
     * @param id the client id
     *
     * @return mocked client
     */
    static Client getSampleClient(long id) {
        Client client = new Client();
        Calendar calendar = Calendar.getInstance();
        client.setEndDate(calendar.getTime());
        calendar.set(2008, 1, 1);
        client.setStartDate(calendar.getTime());
        client.setName("client name");
        client.setDeleted(false);
        client.setId(id);

        ClientStatus status = new ClientStatus();
        status.setName("test");
        status.setDeleted(false);
        status.setDescription("desc");
        client.setClientStatus(status);

        return client;
    }
}
