/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.manager;

import java.util.List;

import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.Company;
import com.topcoder.clients.model.Project;
import com.topcoder.search.builder.filter.Filter;

/**
 * This interface defines functionality for managing company information, including CRUD functionality for companies,
 *  as  well as retrieving clients and projects for a provided company.
 *
 * Thread Safety: implementations of this interface should be thread safe.
 *
 * @author moonli, Chenhong
 * @version 1.0
 */
public interface CompanyManager {
    /**
     * Creates a new Company. After creation, a new ID will be generated for it.
     *
     * @param company
     *            the new Company to create, can not be null (passcode should be non-null, non-empty string, isDeleted
     *            should be false)
     * @return the Company instance created, with new ID assigned.
     * @throws IllegalArgumentException
     *             if the argument is invalid e.g. company param is null, id is negative, passcode is null or empty,
     *             isDeleted is true
     * @throws CompanyManagerException
     *             if any error occurred during this operation, e.g. error in persistence layer, or in ID generator
     */
    public Company createCompany(Company company) throws CompanyManagerException;

    /**
     * Updates a Company. Error will be thrown if the company to update does not exist in persistence.
     *
     * @param company
     *            the company to update, can not be null, id should not be negative (passcode should be non-null,
     *            non-empty string, isDeleted should be false)
     * @return the updated Company
     * @throws IllegalArgumentException
     *             if the argument is invalid e.g. company param is null, id is negative, passcode is null or empty,
     *             isDeleted is true
     * @throws CompanyEntityNotFoundException
     *             if the company to be updated does not exist in persistence
     * @throws CompanyManagerException
     *             if any error occurred during this operation, e.g. error in persistence layer
     */
    public Company updateCompany(Company company) throws CompanyEntityNotFoundException, CompanyManagerException;

    /**
     * Deletes given Company.
     *
     * @param company
     *            the company to delete, can't be null, isDeleted should be false
     * @return the deleted company, with isDeleted set to true
     * @throws IllegalArgumentException
     *             if company is null or it's isDeleted is true
     * @throws CompanyEntityNotFoundException
     *             if the company to be deleted does not exist in persistence
     * @throws CompanyManagerException
     *             if any error occurred during this operation, e.g. error in persistence layer
     */
    public Company deleteCompany(Company company) throws CompanyManagerException, CompanyEntityNotFoundException;

    /**
     * Gets the Company by its ID.
     *
     * @param id
     *            ID of the Company to retrieve, should not be negative
     * @return the Company whose ID matches given ID, or null if none exists with given ID
     *
     * @throws IllegalArgumentException
     *             if id is not positive
     * @throws CompanyManagerException
     *             if any error occurred during this operation, e.g. error in persistence layer
     */
    public Company retrieveCompany(long id) throws CompanyManagerException;

    /**
     * Gets all the Companies. If none exists, empty list will be returned.
     *
     * @return a list of Company objects, empty list if none exists
     * @throws CompanyManagerException
     *             if any error occurred during this operation, e.g. error in persistence layer
     */
    public List<Company> retrieveAllCompanies() throws CompanyManagerException;

    /**
     * Finds the Companies that match given name. Empty list will be returned if none is found.
     *
     * @param name
     *            name of the Companies to search, can't be null or empty
     * @return a list of Companies that match given name, empty list if none is found
     * @throws IllegalArgumentException
     *             if the name is null or empty
     * @throws CompanyManagerException
     *             if any error occurred during this operation, e.g. error in persistence layer
     */
    public List<Company> searchCompaniesByName(String name) throws CompanyManagerException;

    /**
     * Finds the companies with specified filter. Empty list will be returned if none is found
     *
     * @param filter
     *            the filter used to search for companies, can't be null
     *
     * @return a list of Company objects that match the filter, empty list if none is found
     * @throws IllegalArgumentException
     *             if the filter is null
     * @throws CompanyManagerException
     *             if any error occurred during this operation, e.g. error in persistence layer
     */
    public List<Company> searchCompanies(Filter filter) throws CompanyManagerException;

    /**
     * Gets the clients of specified company. Empty list will be returned if none is found
     *
     * @param company
     *            the Company whose clients will be retrieved, can't be null, id should not be negative
     * @return a list of Client objects, empty list if none is found
     * @throws IllegalArgumentException
     *             if the argument is invalid e.g. company param is null, id is negative, passcode is null or empty,
     *             isDeleted is true
     * @throws CompanyManagerException
     *             if any error occurred during this operation, e.g. error in persistence layer,
     */
    public List<Client> getClientsForCompany(Company company) throws CompanyManagerException;

    /**
     * Gets the projects of specified company. Empty list will be returned if none is found
     *
     * @param company
     *            the Company whose clients will be retrieved, can't be null, id should not be negative
     * @return a list of Project objects, empty list if none is found
     * @throws IllegalArgumentException
     *             if the argument is invalid e.g. company is null or its id is negative
     * @throws CompanyManagerException
     *             if any error occurred during this operation, e.g. error in persistence layer
     */
    public List<Project> getProjectsForCompany(Company company) throws CompanyManagerException;
}
