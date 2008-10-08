/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices.beans;

import java.util.ArrayList;
import java.util.List;

import com.topcoder.clients.manager.CompanyEntityNotFoundException;
import com.topcoder.clients.manager.CompanyManager;
import com.topcoder.clients.manager.CompanyManagerException;
import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.Company;
import com.topcoder.clients.model.Project;
import com.topcoder.search.builder.filter.Filter;

/**
 * <p>
 * Mock implementation of the CompanyManager interface.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class CompanyManagerMock implements CompanyManager {

    /**
     * <p>
     * If set to true, operations will throw ClientManagerException.
     * </p>
     */
    private static boolean failOnOperation = false;

    /**
     * <p>
     * Setter for failOnOperation field.
     * </p>
     *
     * @param fail
     *            value to set
     */
    public static void setFail(boolean fail) {
        failOnOperation = fail;
    }

    /**
     * <p>
     * Not used.
     * </p>
     *
     * @param company
     *            not used
     * @return null
     * @throws CompanyManagerException
     *             not used
     */
    public Company createCompany(Company company) throws CompanyManagerException {
        return null;
    }

    /**
     * <p>
     * Not used.
     * </p>
     *
     * @param company
     *            not used
     * @return null
     * @throws CompanyManagerException
     *             not used
     * @throws CompanyEntityNotFoundException
     *             not used
     */
    public Company deleteCompany(Company company) throws CompanyManagerException,
            CompanyEntityNotFoundException {
        return null;
    }

    /**
     * <p>
     * Gets clients for company.
     * </p>
     *
     * @param company
     *            company which clients to find
     * @return clients of the passed company
     * @throws CompanyManagerException
     *             if failOnOperation is true
     */
    public List<Client> getClientsForCompany(Company company) throws CompanyManagerException {
        checkFail();

        List<Client> result = (new UserMappingRetrieverMock()).getClientsForUserId(0);
        result.add(new Client());

        return result;
    }

    /**
     * <p>
     * Gets projects for company.
     * </p>
     *
     * @param company
     *            company which projects to find
     * @return projects of the passed company
     * @throws CompanyManagerException
     *             if failOnOperation is true
     */
    public List<Project> getProjectsForCompany(Company company) throws CompanyManagerException {
        checkFail();

        List<Project> result = (new UserMappingRetrieverMock()).getProjectsForUserId(0);
        result.add(new Project());

        return result;
    }

    /**
     * <p>
     * Gets all companies.
     * </p>
     *
     * @return all companies
     * @throws CompanyManagerException
     *             if failOnOperation is true
     */
    public List<Company> retrieveAllCompanies() throws CompanyManagerException {
        checkFail();

        Company s1 = new Company();
        s1.setId(15);
        Company s2 = new Company();
        s2.setId(17);
        List<Company> result = new ArrayList<Company>();
        result.add(s1);
        result.add(s2);

        return result;
    }

    /**
     * <p>
     * Gets a company with passed id.
     * </p>
     *
     * @param id
     *            id of company to get
     * @return company with given id
     * @throws CompanyManagerException
     *             if failOnOperation is true
     */
    public Company retrieveCompany(long id) throws CompanyManagerException {
        checkFail();

        Company result = new Company();
        result.setId(id);

        return result;
    }

    /**
     * <p>
     * Gets companies which match given filter.
     * </p>
     *
     * @param filter
     *            filter which company should match
     * @return companies which match given filter
     * @throws CompanyManagerException
     *             if failOnOperation is true
     */
    public List<Company> searchCompanies(Filter filter) throws CompanyManagerException {
        return retrieveAllCompanies();
    }

    /**
     * <p>
     * Gets companies with given name.
     * </p>
     *
     * @param name
     *            name of the company
     * @return companies with given name
     * @throws CompanyManagerException
     *             if failOnOperation is true
     */
    public List<Company> searchCompaniesByName(String name) throws CompanyManagerException {
        return retrieveAllCompanies();
    }

    /**
     * <p>
     * Not used.
     * </p>
     *
     * @param company
     *            not used
     * @return null
     * @throws CompanyEntityNotFoundException
     *             not used
     * @throws CompanyManagerException
     *             not used
     */
    public Company updateCompany(Company company) throws CompanyEntityNotFoundException,
            CompanyManagerException {
        return null;
    }

    /**
     * <p>
     * Throws CompanyManagerException if failOnOperation flag is true.
     * </p>
     *
     * @throws CompanyManagerException
     *             if failOnOperation flag is true
     */
    private void checkFail() throws CompanyManagerException {
        if (failOnOperation) {
            throw new CompanyManagerException("Test exception", null, null);
        }
    }

}
