/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices.mock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.topcoder.clients.dao.CompanyDAO;
import com.topcoder.clients.dao.DAOException;
import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.Company;
import com.topcoder.clients.model.Project;
import com.topcoder.search.builder.filter.Filter;

/**
 * Mock a company dao for testing.
 *
 * @author  CaDenza
 * @version 1.0
 *
 */
public class MockCompanyDAO implements CompanyDAO {

    /**
     * Represents counter for entity to be persisted.
     */
    private static long companyCOUNTER = 1;

    /**
     * Pool to store entity.
     */
    private Map<String, Company> mapping = new HashMap<String, Company>();

    /**
     * Flag to make operation fail or not.
     */
    private boolean flag = false;

    /**
     * Default constructor.
     */
    public MockCompanyDAO() {
        // do nothing.
    }

    /**
     * Setter for flag.
     *
     * @param flag
     *      flag to make operation fail or not.
     */
    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    /**
     * Get the entity with id.
     *
     * @param id
     *            the id
     * @return the entity
     * @throws DAOException
     *             for testing
     */
    public Company retrieveById(Long id) throws DAOException {
        checkFlag();
        return mapping.get(String.valueOf(id));
    }

    /**
     * Get all the company.
     *
     * @return a list of company
     */
    public List<Company> retrieveAll() {
        return Arrays.asList(mapping.values().toArray(new Company[mapping.size()]));
    }

    /**
     * Search by name.
     *
     * @param name
     *            the name
     * @return a list
     * @throws DAOException
     *             for testing
     */
    public List<Company> searchByName(String name) throws DAOException {
        checkFlag();
        List<Company> result = new ArrayList<Company>();
        for (Map.Entry<String, Company> entry : mapping.entrySet()) {
            if (entry.getValue().getName().equals(name)) {
                result.add(entry.getValue());
            }
        }
        return result;
    }

    /**
     * Search with filter.
     *
     * @param filter
     *            the filter
     * @return a list
     * @throws DAOException
     *             for testing
     */
    @SuppressWarnings("unchecked")
    public List search(Filter filter) throws DAOException {
        checkFlag();
        return new ArrayList();
    }

    /**
     * Save the entity.
     *
     * @param entity
     *            the entity to save
     * @return entity
     * @throws DAOException
     *             for testing
     */
    public Company save(Company entity) throws DAOException {
        checkFlag();
        if (entity != null) {
            if (entity.getId() <= 0) {
                entity.setId(companyCOUNTER);
                companyCOUNTER++;
            }
            mapping.put(String.valueOf(entity.getId()), entity);
        }
        return entity;

    }

    /**
     * Delete the entity.
     *
     * @param entity
     *            the entity to delete
     * @throws DAOException
     *             for testing
     */
    public void delete(Company entity) throws DAOException {
        checkFlag();
        mapping.remove(String.valueOf(entity.getId()));
    }

    /**
     * Get the clients for company.
     *
     * @param company
     *            the company
     * @return a list of Client
     * @throws DAOException
     *             for testing
     */
    public List<Client> getClientsForCompany(Company company) throws DAOException {
        checkFlag();
        return new ArrayList<Client>();
    }

    /**
     * Get Projects with company.
     *
     * @param c
     *            the company
     * @return a list of Client
     * @throws DAOException
     *             for testing
     */
    public List<Project> getProjectsForCompany(Company c) throws DAOException {
        checkFlag();
        return new ArrayList<Project>();
    }

    /**
     * Checking flag status.
     *
     * @throws DAOException
     *      if flag is true.
     */
    private void checkFlag() throws DAOException {
        if (flag) {
            throw new DAOException("Fail from persistence.");
        }
    }
}
