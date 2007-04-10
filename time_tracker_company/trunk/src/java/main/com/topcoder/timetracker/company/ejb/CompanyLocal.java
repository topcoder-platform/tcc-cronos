/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.company.ejb;

import com.topcoder.search.builder.filter.Filter;

import com.topcoder.timetracker.company.BatchCompanyDAOException;
import com.topcoder.timetracker.company.Company;
import com.topcoder.timetracker.company.CompanyDAOException;

import javax.ejb.EJBLocalObject;


/**
 * <p>
 * The local component interface of the Company EJB, which provides access to the persistent store for companies
 * managed by the application.
 * </p>
 *
 * <p>
 * <b>Thread Safety: </b>The container assumes all responsibility for thread-safety of these implementations.
 * </p>
 *
 * @author bramandia, argolite, TCSDEVELOPER
 * @version 3.2
 */
public interface CompanyLocal extends EJBLocalObject {
    /**
     * <p>
     * Creates a datastore entry for the given company. An id is automatically generated to the company. The Company is
     * also considered to have been created by the specified user. Audit is to be performed if doAudit is true.
     * </p>
     *
     * @param company The company to define within the data store.
     * @param user The user responsible for creating the Company entry within the datastore.
     * @param doAudit Indicates whether audit is to be performed
     *
     * @return The same company object with the id, creationUser, modfiicationUser, creationDate, modificationDate
     *         modified appropriately.
     *
     * @throws IllegalArgumentException if the company or user is null, or user is an empty String, or if any field in
     *         company is null (except algorithmName).
     * @throws CompanyDAOException if a problem occurs while accessing the datastore.
     */
    Company createCompany(Company company, String user, boolean doAudit) throws CompanyDAOException;

    /**
     * <p>
     * Retrieves a company from the datastore with the provided id. If no company with that id exists, then a null is
     * returned.
     * </p>
     *
     * @param id The id of the company to retrieve.
     *
     * @return The company with specified id, or null if it wasn't found.
     *
     * @throws IllegalArgumentException if id is not positive.
     * @throws CompanyDAOException if a problem occurs while accessing the datastore.
     */
    Company retrieveCompany(long id) throws CompanyDAOException;

    /**
     * <p>
     * Updates the given company in the data store. The company is considered to have been modified by the specified
     * user. Audit is to be performed if doAudit is true.
     * </p>
     *
     * @param company The company to update in the data store.
     * @param user The user responsible for performing the update.
     * @param doAudit Indicates whether audit is to be performed
     *
     * @throws IllegalArgumentException if the company or user is null, or user is an empty String, or if any field in
     *         company is null (except algorithmName).
     * @throws CompanyDAOException if a problem occurs while accessing the datastore.
     * @throws CompanyNotFoundException if the company to update was not found in the data store.
     */
    void updateCompany(Company company, String user, boolean doAudit)
        throws CompanyDAOException;

    /**
     * <p>
     * Removes the provided company from the data store. Audit is to be performed if doAudit is true. The user argument
     * is used only if audit is true to record the user who performed the deletion.
     * </p>
     *
     * @param company The company to delete.
     * @param doAudit Indicates whether audit is to be performed
     * @param user the user who performed the deletion
     *
     * @throws IllegalArgumentException if the company is null, or if doAudit is true and either user is null or empty
     *         string, or if company's id is not positive.
     * @throws CompanyDAOException if a problem occurs while accessing the datastore.
     * @throws CompanyNotFoundException if the company to be deleted was not found in the data store.
     */
    void deleteCompany(Company company, boolean doAudit, String user)
        throws CompanyDAOException;

    /**
     * <p>
     * Enumerates all the companies that are present within the data store.
     * </p>
     *
     * @return A list of all the companies present in the data store.
     *
     * @throws CompanyDAOException if a problem occurs while accessing the datastore.
     */
    Company[] listCompanies() throws CompanyDAOException;

    /**
     * <p>
     * Returns a list of all the companies within the datastore that satisfy the filters that are provided. The filter
     * is defined using classes from the Search Builder v1.3 component.
     * </p>
     *
     * @param filter The filter that is used as criterion to facilitate the search.
     *
     * @return A list of companies that fulfill the given criterion. If no company fulfills it, then an empty array is
     *         returned.
     *
     * @throws IllegalArgumentException if the filter is null.
     * @throws CompanyDAOException if a problem occurs while accessing the datastore.
     */
    Company[] search(Filter filter) throws CompanyDAOException;

    /**
     * <p>
     * Creates a datastore entry for each of the given companies. An id is automatically generated and assigned to the
     * company. The Company is also considered to have been created by the specified user. Audit is to be performed if
     * doAudit is true.
     * </p>
     *
     * @param companies The companies to create.
     * @param user The user responsible for creating the companies.
     * @param doAudit Indicates whether audit is to be performed
     *
     * @return The same company objects with the id, creationUser, modfiicationUser, creationDate, modificationDate
     *         modified appropriately. The index of the company in the returned array corresponds to the index of the
     *         company in the method argument.
     *
     * @throws IllegalArgumentException if companies is null or contains null elements, or user is null or an empty
     *         String, or if any field in company is null (except algorithmName).
     * @throws CompanyDAOException if a problem occurs while accessing the datastore.
     */
    Company[] createCompanies(Company[] companies, String user, boolean doAudit) throws CompanyDAOException;

    /**
     * <p>
     * Retrieves the companies with the specified ids from the datastore. If there is no company with a given id, null
     * will be returned in the corresponding index.
     * </p>
     *
     * @param ids The ids of the companies to retrieve.
     *
     * @return A list of companies with the given ids. The index of the company corresponds to the index of the id in
     *         the method argument.
     *
     * @throws CompanyDAOException if a problem occurs while accessing the datastore.
     * @throws IllegalArgumentException if ids is null or contains non positive element
     */
    Company[] retrieveCompanies(long[] ids) throws CompanyDAOException;

    /**
     * <p>
     * Updates the given companies in the data store. The companies are considered to have been modified by the
     * specified user. Audit is to be performed if doAudit is true.
     * </p>
     *
     * @param companies An array of companies to update.
     * @param user The user responsible for performing the update.
     * @param doAudit Indicates whether audit is to be performed
     *
     * @throws IllegalArgumentException if the companies array is null or contains null elements, or user is null or is
     *         an empty String, or if any field in company is null (except algorithmName).
     * @throws CompanyDAOException if a problem occurs while accessing the datastore.
     * @throws CompanyNotFoundException if some of the passed companies is not found.
     */
    void updateCompanies(Company[] companies, String user, boolean doAudit)
        throws CompanyDAOException;

    /**
     * <p>
     * Deletes the specified companies from the data store. Audit is to be performed if doAudit is true. The user
     * argument is used only if audit is true to record the user who performed the deletion.
     * </p>
     *
     * @param companies An array of companies to delete.
     * @param doAudit Indicates whether audit is to be performed
     * @param user the user who performed the deletion
     *
     * @throws IllegalArgumentException if companies is null or has null elements, or if doAudit is true and either
     *         user is null or empty string, or the companies have duplicate entries, or if company's id is not
     *         positive.
     * @throws CompanyDAOException if a problem occurs while accessing the datastore.
     * @throws CompanyNotFoundException if some of the passed companies is not found.
     */
    void deleteCompanies(Company[] companies, boolean doAudit, String user)
        throws CompanyDAOException;

    /**
     * <p>
     * Creates a datastore entry for each of the given companies. An id is automatically generated and assigned to the
     * company. The Company is also considered to have been created by the specified user. Audit is to be performed if
     * doAudit is true.
     * </p>
     *
     * <p>
     * This operation is done non-atomically. Only the company where a failure occurred will be rolled back.
     * </p>
     *
     * @param companies The companies to create.
     * @param user The user responsible for creating the companies.
     * @param doAudit Indicates whether audit is to be performed
     *
     * @return The same company objects with the id, creationUser, modfiicationUser, creationDate, modificationDate
     *         modified appropriately. The index of the company in the returned array corresponds to the index of the
     *         company in the method argument.
     *
     * @throws IllegalArgumentException if companies is null or contains null elements, or user is null or an empty
     *         String.
     * @throws BatchCompanyDAOException if a problem occurs with multiple entities while processing them.
     */
    Company[] createCompaniesNonAtomically(Company[] companies, String user, boolean doAudit)
        throws BatchCompanyDAOException;

    /**
     * <p>
     * Updates the given companies in the data store. The companies are considered to have been modified by the
     * specified user. Audit is to be performed if doAudit is true.
     * </p>
     *
     * <p>
     * This operation is done non-atomically. Only the company where a failure occurred will be rolled back.
     * </p>
     *
     * @param companies An array of companies to update.
     * @param user The user responsible for performing the update.
     * @param doAudit Indicates whether audit is to be performed.
     *
     * @throws IllegalArgumentException if the companies array is null or contains null elements, or user is null or is
     *         an empty String.
     * @throws BatchCompanyDAOException if a problem occurs with multiple entities while processing them.
     */
    void updateCompaniesNonAtomically(Company[] companies, String user, boolean doAudit)
        throws BatchCompanyDAOException;

    /**
     * <p>
     * Deletes the specified companies from the data store. Audit is to be performed if doAudit is true.The user
     * argument is used only if audit is true to record the user who performed the deletion.
     * </p>
     *
     * <p>
     * This operation is done non-atomically. Only the company where a failure occurred will be rolled back.
     * </p>
     *
     * @param companies An array of companies to delete.
     * @param doAudit Indicates whether audit is to be performed.
     * @param user the user who performed the deletion.
     *
     * @throws IllegalArgumentException if companies is null or has null elements, or if doAudit is true and either
     *         user is null or empty string.
     * @throws BatchCompanyDAOException if a problem occurs with multiple entities while processing them.
     */
    void deleteCompaniesNonAtomically(Company[] companies, boolean doAudit, String user)
        throws BatchCompanyDAOException;
}
