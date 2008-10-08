/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices.mock;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.topcoder.clients.dao.ClientDAO;
import com.topcoder.clients.dao.DAOException;
import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.Project;
import com.topcoder.search.builder.filter.Filter;

/**
 * Mock a ClientDAO implementation for unit testing.
 *
 * @author  CaDenza
 * @version 1.0
 *
 */
public class MockClientDAO implements ClientDAO {

    /**
     * Represents counter while persist Client.
     */
    private static int counter = 1;

    /**
     * Cache client mapping.
     */
    private Map<String, Client> mapping = new HashMap<String, Client>();

    /**
     * The flag to control exception thrown.
     */
    private boolean flag = false;

    /**
     * Default constructor.
     */
    public MockClientDAO() {
        // do nothing.
    }

    /**
     * Set the flag value.
     *
     * @param flag
     *            the flag value to set.
     */
    public void setFlag(boolean flag) {
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
     */
    public Client retrieveById(Long id) throws DAOException {
        checkFlag();
        return mapping.get(String.valueOf(id));
    }

    /**
     * Get all the Client instance.
     *
     * @return a List of Client instance, can be empty if none is found
     * @throws DAOException
     *             if flag is set to 1
     */
    public List<Client> retrieveAll() throws DAOException {
        checkFlag();
        return Arrays.asList(mapping.values().toArray(new Client[mapping.size()]));
    }

    /**
     * Search by name.
     *
     * @param name
     *            the name for search
     * @return A list of Client, might be empty if non is found
     * @throws DAOException
     *             for testing purpose
     */
    public List<Client> searchByName(String name) throws DAOException {
        checkFlag();
        List<Client> result = new ArrayList<Client>();
        for (Map.Entry<String, Client> entry : mapping.entrySet()) {
            if (entry.getValue().getName().equals(name)) {
                result.add(entry.getValue());
            }
        }
        return result;
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
     */
    public List<Client> search(Filter filter) throws DAOException {
        checkFlag();
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
     */
    public Client save(Client entry) throws DAOException {
        checkFlag();
        if (entry.getId() <= 0) {
            entry.setId(counter++);
        }
        mapping.put(String.valueOf(entry.getId()), entry);
        return entry;
    }

    /**
     *
     * Delete the client.
     *
     * @param entry
     *            the AuditableEntity to delete
     * @throws DAOException
     *             for testing purpose
     */
    public void delete(Client entry) throws DAOException {
        checkFlag();
        mapping.remove(String.valueOf(entry.getId()));
    }

    /**
     * Get projects for client.
     *
     * @param client
     *            the client
     * @return a list of Project, might be empty
     * @throws DAOException
     *             for testing purpose
     */
    public List<Project> getProjectsForClient(Client client) throws DAOException {
        checkFlag();
        return new ArrayList<Project>();
    }

    /**
     * Checking whether DAO failure should be thrown.
     *
     * @throws DAOException if flag is true.
     */
    private void checkFlag() throws DAOException {
        if (flag) {
            throw new DAOException("Failure from persistence.");
        }
    }
}