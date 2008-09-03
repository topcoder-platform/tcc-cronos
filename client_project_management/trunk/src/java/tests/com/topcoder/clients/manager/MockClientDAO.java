/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.manager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.topcoder.clients.dao.ClientDAO;
import com.topcoder.clients.dao.DAOConfigurationException;
import com.topcoder.clients.dao.DAOException;
import com.topcoder.clients.dao.EntityNotFoundException;
import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.Project;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;

/**
 * Mock a ClientDAO implementation for unit testing.
 *
 * @author Chenhong
 * @version 1.0
 *
 */
public class MockClientDAO implements ClientDAO {
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
     * Get the AuditableEntity instance by id.
     *
     * @param id
     *            the id to retrieve AuditableEntity instance
     * @return AuditableEntity retrieved
     * @throws IllegalArgumentException
     *             if the is negative
     * @throws DAOException
     *             for testing purpose
     * @throws DAOConfigurationException
     *             for testing purpose
     */
    public Client retrieveById(Long id) throws DAOException {
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
            client.setId(1L);
            return client;
        }

        if (value == 1000) {
            throw new EntityNotFoundException("throw for testing.");
        }

        if (value == 1001) {
            throw new DAOConfigurationException("Throw for testing", new NullPointerException("NPE Raised "));
        }

        if (value == 10001) {
            throw new DAOException("Throw for testing", new NullPointerException("NPE Raised "));
        }
        return null;
    }

    /**
     * Get all the Client instance.
     *
     * @return a List of Client instance, can be empty if none is found
     * @throws DAOException
     *             if flag is set to 1
     * @throws DAOConfigurationException
     *             if flag is set to 2
     */
    public List<Client> retrieveAll() throws DAOException {
        if (flag == 1) {
            throw new DAOException("Throw for testing", new NullPointerException("NPE Raised "));
        }

        if (flag == 2) {
            throw new DAOConfigurationException("Throw for testing", new NullPointerException("NPE Raised "));

        }

        return new ArrayList<Client>();
    }

    /**
     * Search by name.
     *
     * @param name
     *            the name for search
     * @return A list of Client, might be empty if non is found
     * @throws DAOException
     *             for testing purpose
     * @throws DAOConfigurationException
     *             for testing purpose
     */
    public List<Client> searchByName(String name) throws DAOException {
        if ("DAOException".equals(name)) {

            throw new DAOException("Throw for testing", new NullPointerException("NPE Raised "));
        }

        if ("DAOConfigurationException".equals(name)) {
            throw new DAOConfigurationException("Throw for testing", new NullPointerException("NPE Raised "));
        }

        return new ArrayList<Client>();
    }

    /**
     * Search Client with filter.
     *
     * @param filter
     *            the filter for search
     * @return a list of client
     *
     * @throws DAOException
     *             for testing purpose
     * @throws DAOConfigurationException
     *             for testing purpose
     */
    public List<Client> search(Filter filter) throws DAOException {

        if (filter instanceof EqualToFilter) {
            EqualToFilter f = (EqualToFilter) filter;

            if ("DAOException".equals(f.getName())) {

                throw new DAOException("Throw for testing", new NullPointerException("NPE Raised "));
            }

            if ("DAOConfigurationException".equals(f.getName())) {
                throw new DAOConfigurationException("Throw for testing", new NullPointerException("NPE Raised "));
            }

        }

        return new ArrayList<Client>();
    }

    /**
     * Save the provided entry.
     *
     * @param entry
     *            the entity for saving
     * @return the save entry
     * @throws DAOException
     *             for testing purpose
     * @throws DAOConfigurationException
     *             for testing purpose
     */
    public Client save(Client entry) throws DAOException {
        if ("DAOException".equals(entry.getName())) {
            throw new DAOException("Throw for testing", new NullPointerException("NPE Raised "));
        }

        if ("DAOConfigurationException".equals(entry.getName())) {
            throw new DAOConfigurationException("Throw for testing", new NullPointerException("NPE Raised "));
        }

        entry.setModifyUsername("saved");
        return (Client) entry;
    }

    /**
     *
     * Delete the client.
     *
     * @param entry
     *            the AuditableEntity to delete
     * @throws DAOException
     *             for testing purpose
     * @throws DAOConfigurationException
     *             for testing purpose
     */
    public void delete(Client entry) throws DAOException {
        if ("entityNotFound".equals(entry.getName())) {
            throw new EntityNotFoundException("The entity is not found.", new IllegalArgumentException("IAE"));
        }

        if ("DAOException".equals(entry.getName())) {
            throw new DAOException("Throw for testing", new NullPointerException("NPE Raised "));
        }

        if ("DAOConfigurationException".equals(entry.getName())) {
            throw new DAOConfigurationException("Throw for testing", new NullPointerException("NPE Raised "));
        }

    }

    /**
     * Get projects for client.
     *
     * @param client
     *            the client
     * @return a list of Project, might be empty
     * @throws DAOException
     *             for testing purpose
     * @throws DAOConfigurationException
     *             for testing purpose
     */
    public List<Project> getProjectsForClient(Client client) throws DAOException {
        if ("DAOException".equals(client.getName())) {
            throw new DAOException("Throw for testing", new NullPointerException("NPE Raised "));
        }

        if ("DAOConfigurationException".equals(client.getName())) {
            throw new DAOConfigurationException("Throw for testing", new NullPointerException("NPE Raised "));
        }

        return new ArrayList<Project>();
    }

}
