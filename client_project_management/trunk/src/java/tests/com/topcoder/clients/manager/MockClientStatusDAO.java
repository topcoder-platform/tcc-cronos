/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.manager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.topcoder.clients.dao.ClientStatusDAO;
import com.topcoder.clients.dao.DAOConfigurationException;
import com.topcoder.clients.dao.DAOException;
import com.topcoder.clients.dao.EntityNotFoundException;
import com.topcoder.clients.model.AuditableEntity;
import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.ClientStatus;
import com.topcoder.search.builder.filter.Filter;

/**
 * Mock a ClientStatusDAO implementation for unit testing.
 *
 * @author Chenhong
 * @version 1.0
 *
 */
public class MockClientStatusDAO implements ClientStatusDAO<ClientStatus, Long> {

    /**
     * The flag to control exception thrown.
     */
    private int flag = 0;

    /**
     * Set the flag value.
     *
     * @param flag
     *            the flag value to set.
     */
    public void setFlag(int flag) {
        this.flag = flag;
    }

    /**
     * Get the instance with id.
     *
     * @param id
     *            the id
     * @return AuditableEntity instance
     * @throws DAOException
     *             for testing purpose
     * @throws DAOConfigurationException
     *             for testing purpose
     */
    public AuditableEntity retrieveById(Serializable id) throws DAOException {

        long value = new Long(String.valueOf(id)).longValue();

        if (value < 0) {
            throw new IllegalArgumentException("The parameter id should not < 0");
        }

        if (value == 1) {
            Client client = new Client();
            client.setStartDate(new Date(System.currentTimeMillis() - 10000000000L));
            client.setEndDate(new Date(System.currentTimeMillis() + 10000000L));
            client.setName("ok");
            client.setDeleted(false);

            return client;
        }

        if (value == 2) {
            return null;
        }

        if (value == 3) {
            ClientStatus status = new ClientStatus();
            status.setDeleted(false);
            status.setId(3L);
            status.setDescription("des");
            status.setName("name");

            return status;
        }

        if (value == 101) {
            return new Client();
        }

        if (value == 999) {
            ClientStatus status = new ClientStatus();
            status.setDescription("e");
            return status;
        }

        if (value == 1000) {
            throw new EntityNotFoundException("Throw for testing", 1000L);
        }

        if (value == 10001) {
            throw new DAOException("Throw for testing", new NullPointerException("NPE Raised "));
        }
        if (value == 10002) {
            throw new DAOConfigurationException("Throw for testing", new NullPointerException("NPE Raised "));
        }

        return new ClientStatus();
    }

    /**
     * Get all the ClientStatus.
     *
     * @return a list of ClientStatus, might be empty
     *
     * @throws DAOException
     *             for testing purpose
     * @throws DAOConfigurationException
     *             for testing purpose
     */
    public List<ClientStatus> retrieveAll() throws DAOException {
        if (flag == 1) {
            throw new DAOException("Throw for testing", new NullPointerException("NPE Raised "));
        }

        if (flag == 2) {

            throw new DAOConfigurationException("Throw for testing", new NullPointerException("NPE Raised "));

        }

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
     * @throws DAOConfigurationException
     *             for testing purpose
     */
    public List<ClientStatus> search(Filter filter) throws DAOException {
        return new ArrayList<ClientStatus>();
    }

    /**
     * Save the AuditableEntity.
     *
     * @param entity
     *            the AuditableEntity to save
     * @return AuditableEntity instance
     * @throws DAOException
     *             for testing purpose
     * @throws DAOConfigurationException
     *             for testing purpose
     */
    public AuditableEntity save(AuditableEntity entity) throws DAOException {
        if ("DAOException".equals(entity.getName())) {
            throw new DAOException("Throw for testing");
        }

        if ("DAOConfigurationException".equals(entity.getName())) {
            throw new DAOConfigurationException("Throw for testing");
        }

        if ("ClassCastException".equals(entity.getName())) {
            return new Client();
        }
        entity.setCreateUsername("test");
        return entity;
    }

    /**
     * Delete the entity.
     *
     * @param entity
     *            the entity to delete
     * @throws DAOException
     *             for testing purpose
     * @throws DAOConfigurationException
     *             for testing purpose
     * @throws EntityNotFoundException for testing
     */
    public void delete(AuditableEntity entity) throws DAOException {

        if ("EntityNotFoundException".equals(entity.getName())) {
            throw new EntityNotFoundException("Throw for testing", 1L);
        }

        if ("DAOException".equals(entity.getName())) {
            throw new DAOException("Throw for testing");
        }

        if ("DAOConfigurationException".equals(entity.getName())) {
            throw new DAOConfigurationException("Throw for testing");
        }
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
     * @throws DAOConfigurationException
     *             for testing purpose
     */
    public List<Client> getClientsWithStatus(ClientStatus status) throws DAOException {
        if ("entity".equals(status.getName())) {
            throw new EntityNotFoundException("The entity is not found.", new IllegalArgumentException("IAE"), 1L);
        }

        if ("DAOException".equals(status.getName())) {
            throw new DAOException("Throw for testing", new NullPointerException("NPE Raised "));
        }

        if ("DAOConfigurationException".equals(status.getName())) {
            throw new DAOConfigurationException("Throw for testing", new NullPointerException("NPE Raised "));
        }
        return new ArrayList<Client>();
    }

}
