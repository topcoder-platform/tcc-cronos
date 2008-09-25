/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices.failuretests.mock;

import com.topcoder.clients.manager.CompanyEntityNotFoundException;
import com.topcoder.clients.manager.CompanyManager;
import com.topcoder.clients.manager.CompanyManagerException;
import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.Company;
import com.topcoder.clients.model.Project;

import com.topcoder.search.builder.filter.Filter;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>
 * Mock implementation of the CompanyManager interface.
 * </p>
 *
 * @author PE
 * @version 1.0
 */
public class MockCompanyManager implements CompanyManager {
    /**
     * <p>
     * If set to true, operations will throw CompanyManagerException.
     * </p>
     */
    private static boolean throwCME = false;

    /**
     * <p>
     * If set to true, operations will throw IllegalArgumentException.
     * </p>
     */
    private static boolean throwIAE = false;

    /**
     * <p>
     * Setter for throwCME field.
     * </p>
     *
     * @param fail value to set
     */
    public static void throwCME(boolean fail) {
        throwCME = fail;
    }

    /**
     * <p>
     * Setter for throwIAE field.
     * </p>
     *
     * @param fail value to set
     */
    public static void throwIAE(boolean fail) {
        throwIAE = fail;
    }

    /**
     * <p>
     * Create the company.
     * </p>
     *
     * @param company to create
     *
     * @return the created company
     *
     * @throws CompanyManagerException if throwCME is true
     */
    public Company createCompany(Company company) throws CompanyManagerException {
        checkFail();

        return company;
    }

    /**
     * <p>
     * Delete the company.
     * </p>
     *
     * @param company the company to delete
     *
     * @return the deleted company
     *
     * @throws CompanyManagerException if throwCME is true
     * @throws CompanyEntityNotFoundException won't be thrown
     */
    public Company deleteCompany(Company company) throws CompanyManagerException, CompanyEntityNotFoundException {
        checkFail();

        return company;
    }

    /**
     * <p>
     * Gets clients for company.
     * </p>
     *
     * @param company company which clients to find
     *
     * @return null
     *
     * @throws CompanyManagerException won't be thrown
     */
    public List<Client> getClientsForCompany(Company company)
        throws CompanyManagerException {
        return null;
    }

    /**
     * <p>
     * Gets projects for company.
     * </p>
     *
     * @param company company which projects to find
     *
     * @return null
     *
     * @throws CompanyManagerException won't be thrown
     */
    public List<Project> getProjectsForCompany(Company company)
        throws CompanyManagerException {
        return null;
    }

    /**
     * <p>
     * Gets all companies.
     * </p>
     *
     * @return all companies
     *
     * @throws CompanyManagerException if throwCME is true
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
     * @param id id of company to get
     *
     * @return company with given id
     *
     * @throws CompanyManagerException if throwCME is true
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
     * @param filter filter which company should match
     *
     * @return companies which match given filter
     *
     * @throws CompanyManagerException if throwCME is true
     */
    public List<Company> searchCompanies(Filter filter)
        throws CompanyManagerException {
        return retrieveAllCompanies();
    }

    /**
     * <p>
     * Gets companies with given name.
     * </p>
     *
     * @param name name of the company
     *
     * @return companies with given name
     *
     * @throws CompanyManagerException if throwCME is true
     */
    public List<Company> searchCompaniesByName(String name)
        throws CompanyManagerException {
        return retrieveAllCompanies();
    }

    /**
     * <p>
     * Update the company.
     * </p>
     *
     * @param company company to be updated
     *
     * @return the updated company
     *
     * @throws CompanyEntityNotFoundException won't be thrown
     * @throws CompanyManagerException not used
     */
    public Company updateCompany(Company company) throws CompanyEntityNotFoundException, CompanyManagerException {
        checkFail();

        return company;
    }

    /**
     * <p>
     * Throws CompanyManagerException if throwCME flag is true.
     * </p>
     *
     * @throws CompanyManagerException if throwCME is true
     * @throws IllegalArgumentException if throwIAE is true
     */
    private void checkFail() throws CompanyManagerException {
        if (throwCME) {
            throw new CompanyManagerException("Test exception", null, null);
        }

        if (throwIAE) {
            throw new IllegalArgumentException("Test exception");
        }
    }
}
