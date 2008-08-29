/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.manager.accuracytests;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.topcoder.clients.dao.CompanyDAO;
import com.topcoder.clients.dao.DAOException;
import com.topcoder.clients.model.AuditableEntity;
import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.Company;
import com.topcoder.clients.model.Project;
import com.topcoder.search.builder.filter.Filter;


/**
 * Mock Implementation of ClientStatusDAO.
 *
 * @author onsky
 * @version 1.0
 */
public class MockCompanyDAO implements CompanyDAO<Company, Long> {
    /**
     * Mock Method.
     *
     * @param company mock parameter
     *
     * @return Mock value
     *
     * @throws DAOException if such error
     * @throws com.topcoder.clients.dao.EntityNotFoundException if such error
     */
    public List<Client> getClientsForCompany(Company company)
        throws DAOException {
        List<Client> list = new ArrayList<Client>();
        list.add(MockClientDAO.getSampleClient(1));

        return list;
    }

    /**
     * Mock Method.
     *
     * @param company mock parameter
     *
     * @return Mock value
     *
     * @throws DAOException if such error
     * @throws com.topcoder.clients.dao.EntityNotFoundException if such error
     */
    public List<Project> getProjectsForCompany(Company company)
        throws DAOException {
        return new ArrayList<Project>();
    }

    /**
     * Mock Method.
     *
     * @param entity mock parameter
     *
     * @throws DAOException if such error
     */
    public void delete(AuditableEntity entity) throws DAOException {
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
        List<Company> list = new ArrayList<Company>();
        list.add(getSampleCompany(1));
        list.add(getSampleCompany(2));
        list.add(getSampleCompany(3));

        return list;
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
    public AuditableEntity retrieveById(Serializable id)
        throws DAOException {
        return getSampleCompany((Long) id);
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
    public AuditableEntity save(AuditableEntity entity)
        throws DAOException {
        entity.setId(10L);

        return entity;
    }

    /**
     * Mock Method.
     *
     * @param filter mock parameter
     *
     * @return Mock value
     *
     * @throws DAOException if such error
     */
    @SuppressWarnings("unchecked")
    public List search(Filter filter) throws DAOException {
        List<Company> list = new ArrayList<Company>();
        list.add(getSampleCompany(1));
        list.add(getSampleCompany(3));

        return list;
    }

    /**
     * Mock Method.
     *
     * @param name mock parameter
     *
     * @return Mock value
     *
     * @throws DAOException if such error
     */
    @SuppressWarnings("unchecked")
    public List searchByName(String name) throws DAOException {
        List<Company> list = new ArrayList<Company>();
        Company company = getSampleCompany(2);
        company.setName(name);
        list.add(company);

        return list;
    }

    /**
     * Get sample company.
     *
     * @param id the id
     * @return sample Company
     */
    private Company getSampleCompany(long id) {
        Company company = new Company();
        company.setPasscode("passcode");
        company.setName("company name");
        company.setDeleted(false);
        company.setId(id);

        return company;
    }
}
