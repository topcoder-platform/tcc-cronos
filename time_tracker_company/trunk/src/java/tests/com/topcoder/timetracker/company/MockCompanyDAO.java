/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.company;

import com.topcoder.search.builder.filter.Filter;


/**
 * <p>
 * Defines a mock class which implements the <code>CompanyDAO</code> interface for testing.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class MockCompanyDAO implements CompanyDAO {
    /** Represents the flag whether method createCompanies is invoked. */
    private boolean isCreateCompaniesCalled = false;

    /** Represents the flag whether method createCompaniesNonAtomically is invoked. */
    private boolean isCreateCompaniesNonAtomicallyCalled = false;

    /** Represents the flag whether method createCompany is invoked. */
    private boolean isCreateCompanyCalled = false;

    /** Represents the flag whether method deleteCompanies is invoked. */
    private boolean isDeleteCompaniesCalled = false;

    /** Represents the flag whether method deleteCompaniesNonAtomically is invoked. */
    private boolean isDeleteCompaniesNonAtomicallyCalled = false;

    /** Represents the flag whether method deleteCompany is invoked. */
    private boolean isDeleteCompanyCalled = false;

    /** Represents the flag whether method listCompanies is invoked. */
    private boolean isListCompaniesCalled = false;

    /** Represents the flag whether method retrieveCompanies is invoked. */
    private boolean isRetrieveCompaniesCalled = false;

    /** Represents the flag whether method retrieveCompany is invoked. */
    private boolean isRetrieveCompanyCalled = false;

    /** Represents the flag whether method search is invoked. */
    private boolean isSearchCalled = false;

    /** Represents the flag whether method updateCompanies is invoked. */
    private boolean isUpdateCompaniesCalled = false;

    /** Represents the flag whether method updateCompaniesNonAtomically is invoked. */
    private boolean isUpdateCompaniesNonAtomicallyCalled = false;

    /** Represents the flag whether method updateCompany is invoked. */
    private boolean isUpdateCompanyCalled = false;

    /**
     * <p>
     * Gets the flag whether method createCompanies is invoked.
     * </p>
     *
     * @return the flag whether method createCompanies is invoked.
     */
    public boolean isCreateCompaniesCalled() {
        return this.isCreateCompaniesCalled;
    }

    /**
     * <p>
     * Mock method, mark this method is invoked.
     * </p>
     *
     * @param companies The companies to create.
     * @param user The user responsible for creating the companies.
     * @param doAudit Indicates whether audit is to be performed
     *
     * @return always null.
     */
    public Company[] createCompanies(Company[] companies, String user, boolean doAudit) {
        this.isCreateCompaniesCalled = true;

        return null;
    }

    /**
     * <p>
     * Gets the flag whether method createCompaniesNonAtomically is invoked.
     * </p>
     *
     * @return the flag whether method createCompaniesNonAtomically is invoked.
     */
    public boolean isCreateCompaniesNonAtomicallyCalled() {
        return this.isCreateCompaniesNonAtomicallyCalled;
    }

    /**
     * <p>
     * Mock method, mark this method is invoked.
     * </p>
     *
     * @param companies The companies to create.
     * @param user The user responsible for creating the companies.
     * @param doAudit Indicates whether audit is to be performed
     *
     * @return always null.
     */
    public Company[] createCompaniesNonAtomically(Company[] companies, String user, boolean doAudit) {
        this.isCreateCompaniesNonAtomicallyCalled = true;

        return null;
    }

    /**
     * <p>
     * Gets the flag whether method createCompany is invoked.
     * </p>
     *
     * @return the flag whether method createCompany is invoked.
     */
    public boolean isCreateCompanyCalled() {
        return this.isCreateCompanyCalled;
    }

    /**
     * <p>
     * Mock method, mark this method is invoked.
     * </p>
     *
     * @param company The company to define within the data store.
     * @param user The user responsible for creating the Company entry within the datastore.
     * @param doAudit Indicates whether audit is to be performed
     *
     * @return always null.
     */
    public Company createCompany(Company company, String user, boolean doAudit) {
        this.isCreateCompanyCalled = true;

        return null;
    }

    /**
     * <p>
     * Gets the flag whether method deleteCompanies is invoked.
     * </p>
     *
     * @return the flag whether method deleteCompanies is invoked.
     */
    public boolean isDeleteCompaniesCalled() {
        return this.isDeleteCompaniesCalled;
    }

    /**
     * <p>
     * Mock method, mark this method is invoked.
     * </p>
     *
     * @param companies An array of companies to delete.
     * @param doAudit Indicates whether audit is to be performed
     * @param user the user who performed the deletion
     */
    public void deleteCompanies(Company[] companies, boolean doAudit, String user) {
        this.isDeleteCompaniesCalled = true;
    }

    /**
     * <p>
     * Gets the flag whether method deleteCompaniesNonAtomically is invoked.
     * </p>
     *
     * @return the flag whether method deleteCompaniesNonAtomically is invoked.
     */
    public boolean isDeleteCompaniesNonAtomicallyCalled() {
        return this.isDeleteCompaniesNonAtomicallyCalled;
    }

    /**
     * <p>
     * Mock method, mark this method is invoked.
     * </p>
     *
     * @param companies An array of companies to delete.
     * @param doAudit Indicates whether audit is to be performed.
     * @param user the user who performed the deletion.
     */
    public void deleteCompaniesNonAtomically(Company[] companies, boolean doAudit, String user) {
        this.isDeleteCompaniesNonAtomicallyCalled = true;
    }

    /**
     * <p>
     * Gets the flag whether method deleteCompany is invoked.
     * </p>
     *
     * @return the flag whether method deleteCompany is invoked.
     */
    public boolean isDeleteCompanyCalled() {
        return this.isDeleteCompanyCalled;
    }

    /**
     * <p>
     * Mock method, mark this method is invoked.
     * </p>
     *
     * @param company The company to delete.
     * @param doAudit Indicates whether audit is to be performed
     * @param user the user who performed the deletion
     */
    public void deleteCompany(Company company, boolean doAudit, String user) {
        this.isDeleteCompanyCalled = true;
    }

    /**
     * <p>
     * Gets the flag whether method listCompanies is invoked.
     * </p>
     *
     * @return the flag whether method listCompanies is invoked.
     */
    public boolean isListCompaniesCalled() {
        return this.isListCompaniesCalled;
    }

    /**
     * <p>
     * Mock method, mark this method is invoked.
     * </p>
     *
     * @return always null.
     */
    public Company[] listCompanies() {
        this.isListCompaniesCalled = true;

        return null;
    }

    /**
     * <p>
     * Gets the flag whether method retrieveCompanies is invoked.
     * </p>
     *
     * @return the flag whether method retrieveCompanies is invoked.
     */
    public boolean isRetrieveCompaniesCalled() {
        return this.isRetrieveCompaniesCalled;
    }

    /**
     * <p>
     * Mock method, mark this method is invoked.
     * </p>
     *
     * @param ids The ids of the companies to retrieve.
     *
     * @return always null.
     */
    public Company[] retrieveCompanies(long[] ids) {
        this.isRetrieveCompaniesCalled = true;

        return null;
    }

    /**
     * <p>
     * Gets the flag whether method retrieveCompany is invoked.
     * </p>
     *
     * @return the flag whether method retrieveCompany is invoked.
     */
    public boolean isRetrieveCompanyCalled() {
        return this.isRetrieveCompanyCalled;
    }

    /**
     * <p>
     * Mock method, mark this method is invoked.
     * </p>
     *
     * @param id The id of the company to retrieve.
     *
     * @return always null.
     */
    public Company retrieveCompany(long id) {
        this.isRetrieveCompanyCalled = true;

        return null;
    }

    /**
     * <p>
     * Gets the flag whether method search is invoked.
     * </p>
     *
     * @return the flag whether method search is invoked.
     */
    public boolean isSearchCalled() {
        return this.isSearchCalled;
    }

    /**
     * <p>
     * Mock method, mark this method is invoked.
     * </p>
     *
     * @param filter The filter that is used as criterion to facilitate the search.
     *
     * @return always null.
     */
    public Company[] search(Filter filter) {
        this.isSearchCalled = true;

        return null;
    }

    /**
     * <p>
     * Gets the flag whether method updateCompanies is invoked.
     * </p>
     *
     * @return the flag whether method updateCompanies is invoked.
     */
    public boolean isUpdateCompaniesCalled() {
        return this.isUpdateCompaniesCalled;
    }

    /**
     * <p>
     * Mock method, mark this method is invoked.
     * </p>
     *
     * @param companies An array of companies to update.
     * @param user The user responsible for performing the update.
     * @param doAudit Indicates whether audit is to be performed
     */
    public void updateCompanies(Company[] companies, String user, boolean doAudit) {
        this.isUpdateCompaniesCalled = true;
    }

    /**
     * <p>
     * Gets the flag whether method updateCompaniesNonAtomically is invoked.
     * </p>
     *
     * @return the flag whether method updateCompaniesNonAtomically is invoked.
     */
    public boolean isUpdateCompaniesNonAtomicallyCalled() {
        return this.isUpdateCompaniesNonAtomicallyCalled;
    }

    /**
     * <p>
     * Mock method, mark this method is invoked.
     * </p>
     *
     * @param companies An array of companies to update.
     * @param user The user responsible for performing the update.
     * @param doAudit Indicates whether audit is to be performed
     */
    public void updateCompaniesNonAtomically(Company[] companies, String user, boolean doAudit) {
        this.isUpdateCompaniesNonAtomicallyCalled = true;
    }

    /**
     * <p>
     * Gets the flag whether method updateCompany is invoked.
     * </p>
     *
     * @return the flag whether method updateCompany is invoked.
     */
    public boolean isUpdateCompanyCalled() {
        return this.isUpdateCompanyCalled;
    }

    /**
     * <p>
     * Mock method, mark this method is invoked.
     * </p>
     *
     * @param company The company to update in the data store.
     * @param user The user responsible for performing the update.
     * @param doAudit Indicates whether audit is to be performed
     */
    public void updateCompany(Company company, String user, boolean doAudit) {
        this.isUpdateCompanyCalled = true;
    }
}
