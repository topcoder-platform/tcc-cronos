/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.manager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.topcoder.clients.dao.CompanyDAO;
import com.topcoder.clients.dao.DAOConfigurationException;
import com.topcoder.clients.dao.DAOException;
import com.topcoder.clients.dao.EntityNotFoundException;
import com.topcoder.clients.model.AuditableEntity;
import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.Company;
import com.topcoder.clients.model.Project;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;

/**
 * Mock a company dao for testing.
 *
 * @author Chenhong
 * @version 1.0
 *
 */
public class MockCompanyDAO implements CompanyDAO<Company, Long> {

    /**
     * Get the entity with id.
     *
     * @param id
     *            the id
     * @return the entity
     * @throws DAOException
     *             for testing
     * @throws DAOConfigurationException
     *             for testing
     */
    public AuditableEntity retrieveById(Serializable id) throws DAOException {
        long value = new Long(String.valueOf(id)).longValue();

        if (value < 0) {
            throw new IllegalArgumentException("The id should not be negative.");
        }

        if (value == 1) {
            return null;
        }

        if (value == 2) {
            Company c = new Company();
            c.setDeleted(false);
            c.setPasscode("code");
            c.setId(2L);

            return c;
        }

        if (value == 99) {
            Company c = new Company();
            c.setDeleted(false);
            c.setPasscode("code");
            c.setId(99L);

            return c;
        }

        if (value == 3) {
            return new Client();
        }

        if (value == 4) {
            throw new DAOException("for test");
        }

        if (value == 5) {
            throw new DAOConfigurationException("test");
        }

        if (value == 6) {
            throw new EntityNotFoundException("For test", 6L);
        }

        return null;
    }

    /**
     * Get all the company.
     *
     * @return a list of company
     */
    public List<Company> retrieveAll() {
        return new ArrayList<Company>();
    }

    /**
     * Search by name.
     *
     * @param name
     *            the name
     * @return a list
     * @throws DAOException
     *             for testing
     * @throws DAOConfigurationException
     *             for testing
     */
    public List searchByName(String name) throws DAOException {

        if ("DAOException".equals(name)) {
            throw new DAOException("Throw for testing");
        }

        if ("DAOConfigurationException".equals(name)) {
            throw new DAOConfigurationException("Throw for testing");
        }

        return new ArrayList<Company>();
    }

    /**
     * Search with filter.
     *
     * @param filter
     *            the filter
     * @return a list
     * @throws DAOException
     *             for testing
     * @throws DAOConfigurationException
     *             for testing
     */
    public List search(Filter filter) throws DAOException {
        if (filter instanceof EqualToFilter) {

            EqualToFilter f = (EqualToFilter) filter;

            if ("DAOException".equals(f.getName())) {
                throw new DAOException("Throw for testing");
            }

            if ("DAOConfigurationException".equals(f.getName())) {
                throw new DAOConfigurationException("Throw for testing");
            }

        }
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
     * @throws DAOConfigurationException
     *             for testing
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
     *             for testing
     * @throws DAOConfigurationException
     *             for testing
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



        entity.setCreateUsername("delete");

    }

    /**
     * Get the clients for company.
     *
     * @param company
     *            the company
     * @return a list of Client
     * @throws DAOException
     *             for testing
     * @throws DAOConfigurationException
     *             for testing
     * @throws EntityNotFoundException
     *             to junit
     */
    public List<Client> getClientsForCompany(Company company) throws DAOException {
        if (company.getId() == 1) {
            throw new DAOException("For testing");
        }

        if (company.getId() == 2) {
            throw new DAOConfigurationException("for testing");
        }
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
     * @throws DAOConfigurationException
     *             for testing
     */
    public List<Project> getProjectsForCompany(Company c) throws DAOException {
        if (c.getId() == 1) {
            throw new DAOException("For testing");
        }

        if (c.getId() == 2) {
            throw new DAOConfigurationException("for testing");
        }
        return new ArrayList<Project>();
    }

}
