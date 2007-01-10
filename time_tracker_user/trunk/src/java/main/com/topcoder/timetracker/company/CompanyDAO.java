/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.timetracker.company;

import com.topcoder.search.builder.filter.Filter;

/**
 * <p>
 * This interface defines the necessary methods that a Company DAO should support. Create, Retrieve, Update, Delete
 * and Enumerate (CRUDE) methods, and their respective batch-mode equivalents are specified. There is also a search
 * method that utilizes Filter classes from the Search Builder 1.2 component.
 * </p>
 * <p>
 * Thread Safety: - Implementations need not necessarily be thread safe. Each implementation should specify whether
 * it is thread-safe or not. The application should pick the correct implementation for it's requirements.
 * </p>
 *
 * @author ShindouHikaru
 * @author kr00tki
 * @version 2.0
 */
public interface CompanyDAO {

    /**
     * <p>
     * This is a constant for a search filter field name for the Company Name. Filters from the Search Builder
     * component may use this constant when building their search parameters. Implementations of the CompanyDAO
     * interface should be able to recognize search filters bearing the provided constant and adjust their searches
     * according to the searchCompanies method.
     * </p>
     *
     */
    String SEARCH_COMPANY_NAME = "search_company_name";

    /**
     * <p>
     * This is a constant for a search filter field name for the Company Contact's First Name.
     * </p>
     * <p>
     * Filters from the Search Builder component may use this constant when building their search parameters.
     * Implementations of the CompanyDAO interface should be able to recognize search filters bearing the provided
     * constant and adjust their searches according to the searchCompanies method.
     * </p>
     *
     */
    String SEARCH_CONTACT_FIRST_NAME = "search_contact_first_name";

    /**
     * <p>
     * This is a constant for a search filter field name for the Company Contact's Last Name.
     * </p>
     * <p>
     * Filters from the Search Builder component may use this constant when building their search parameters.
     * Implementations of the CompanyDAO interface should be able to recognize search filters bearing the provided
     * constant and adjust their searches according to the searchCompanies method.
     * </p>
     *
     */
    String SEARCH_CONTACT_LAST_NAME = "search_contact_last_name";

    /**
     * <p>
     * This is a constant for a search filter field name for the Company Contact's Phone Number.
     * </p>
     * <p>
     * Filters from the Search Builder component may use this constant when building their search parameters.
     * Implementations of the CompanyDAO interface should be able to recognize search filters bearing the provided
     * constant and adjust their searches according to the searchCompanies method.
     * </p>
     *
     */
    String SEARCH_CONTACT_PHONE = "search_contact_phone";

    /**
     * <p>
     * This is a constant for a search filter field name for the Company Contact's Email Address.
     * </p>
     * <p>
     * Filters from the Search Builder component may use this constant when building their search parameters.
     * Implementations of the CompanyDAO interface should be able to recognize search filters bearing the provided
     * constant and adjust their searches according to the searchCompanies method.
     * </p>
     *
     */
    String SEARCH_CONTACT_EMAIL = "search_contact_email";

    /**
     * <p>
     * This is a constant for a search filter field name for the Company Street Address Line 1.
     * </p>
     * <p>
     * Filters from the Search Builder component may use this constant when building their search parameters.
     * Implementations of the CompanyDAO interface should be able to recognize search filters bearing the provided
     * constant and adjust their searches according to the searchCompanies method.
     * </p>
     *
     */
    String SEARCH_STREET_ADDRESS1 = "search_street_address1";

    /**
     * <p>
     * This is a constant for a search filter field name for the Company Street Address Line 2.
     * </p>
     * <p>
     * Filters from the Search Builder component may use this constant when building their search parameters.
     * Implementations of the CompanyDAO interface should be able to recognize search filters bearing the provided
     * constant and adjust their searches according to the searchCompanies method.
     * </p>
     *
     */
    String SEARCH_STREET_ADDRESS2 = "search_street_address2";

    /**
     * <p>
     * This is a constant for a search filter field name for the Company's City.
     * </p>
     * <p>
     * Filters from the Search Builder component may use this constant when building their search parameters.
     * Implementations of the CompanyDAO interface should be able to recognize search filters bearing the provided
     * constant and adjust their searches according to the searchCompanies method.
     * </p>
     *
     */
    String SEARCH_CITY = "search_city";

    /**
     * <p>
     * This is a constant for a search filter field name for the Company's State.
     * </p>
     * <p>
     * Filters from the Search Builder component may use this constant when building their search parameters.
     * Implementations of the CompanyDAO interface should be able to recognize search filters bearing the provided
     * constant and adjust their searches according to the searchCompanies method.
     * </p>
     *
     */
    String SEARCH_STATE = "search_state";

    /**
     * <p>
     * This is a constant for a search filter field name for the Company's Zip Code.
     * </p>
     * <p>
     * Filters from the Search Builder component may use this constant when building their search parameters.
     * Implementations of the CompanyDAO interface should be able to recognize search filters bearing the provided
     * constant and adjust their searches according to the searchCompanies method.
     * </p>
     *
     */
    String SEARCH_ZIP_CODE = "search_zip_code";

    /**
     * <p>
     * This is a constant for a search filter field name for the Company's Date of Creation.
     * </p>
     * <p>
     * Filters from the Search Builder component may use this constant when building their search parameters.
     * Implementations of the CompanyDAO interface should be able to recognize search filters bearing the provided
     * constant and adjust their searches according to the searchCompanies method.
     * </p>
     *
     */
    String SEARCH_CREATED_DATE = "search_created_date";

    /**
     * <p>
     * This is a constant for a search filter field name for the Company's User Creator.
     * </p>
     * <p>
     * Filters from the Search Builder component may use this constant when building their search parameters.
     * Implementations of the CompanyDAO interface should be able to recognize search filters bearing the provided
     * constant and adjust their searches according to the searchCompanies method.
     * </p>
     *
     */
    String SEARCH_CREATED_USER = "search_created_user";

    /**
     * <p>
     * This is a constant for a search filter field name for the Company's Date of Last Modification.
     * </p>
     * <p>
     * Filters from the Search Builder component may use this constant when building their search parameters.
     * Implementations of the CompanyDAO interface should be able to recognize search filters bearing the provided
     * constant and adjust their searches according to the searchCompanies method.
     * </p>
     *
     */
    String SEARCH_MODIFICATION_DATE = "search_modification_date";

    /**
     * <p>
     * This is a constant for a search filter field name for the User that performed the Last Modification to the
     * Company.
     * </p>
     * <p>
     * Filters from the Search Builder component may use this constant when building their search parameters.
     * Implementations of the CompanyDAO interface should be able to recognize search filters bearing the provided
     * constant and adjust their searches according to the searchCompanies method.
     * </p>
     *
     */
    String SEARCH_MODIFICATION_USER = "search_modification_user";

    /**
     * <p>
     * Creates a datastore entry for the given company. An id is automatically generated by the DAO and assigned to
     * the company. The Company is also considered to have been created by the specified user.
     * </p>
     *
     *
     *
     * @return The same company object with the id, creationUser, modfiicationUser, creationDate, modificationDate
     *         modified appropriately.
     * @param company The company to define within the data store.
     * @param user The user responsible for creating the Company entry within the datastore.
     * @throws IllegalArgumentException if the company or user is null, or user is an empty String.
     * @throws CompanyDAOException if a problem occurs while accessing the datastore.
     */
    Company createCompany(Company company, String user) throws CompanyDAOException;

    /**
     * <p>
     * Retrieves a company from the datastore with the provided id. If no company with that id exists, then a null
     * is returned.
     * </p>
     *
     *
     *
     * @return The company with specified id, or null if it wasn't found.
     * @param id The id of the company to retrieve.
     * @throws CompanyDAOException if a problem occurs while accessing the datastore.
     */
    Company retrieveCompany(long id) throws CompanyDAOException;

    /**
     * <p>
     * Updates the given company in the data store. The company is considered to have been modified by the
     * specified user.
     * </p>
     *
     *
     *
     * @param company The company to update in the data store.
     * @param user The user responsible for performing the update.
     * @throws IllegalArgumentException if the company or user is null, or user is an empty String.
     * @throws CompanyDAOException if a problem occurs while accessing the datastore.
     * @throws CompanyNotFoundException if the company to be updated was not found in the data store.
     */
    void updateCompany(Company company, String user) throws CompanyDAOException, CompanyNotFoundException;

    /**
     * <p>
     * Removes the provided company from the data store.
     * </p>
     *
     *
     *
     * @param company The company to delete.
     * @throws IllegalArgumentException if the company is null.
     * @throws CompanyDAOException if a problem occurs while accessing the datastore.
     * @throws CompanyNotFoundException if the company to be deleted was not found in the data store.
     */
    void deleteCompany(Company company) throws CompanyDAOException, CompanyNotFoundException;

    /**
     * <p>
     * Enumerates all the companies that are present within the data store.
     * </p>
     *
     *
     *
     * @return A list of all the companies present in the data store.
     * @throws CompanyDAOException if a problem occurs while accessing the datastore.
     */
    Company[] listCompanies() throws CompanyDAOException;

    /**
     * <p>
     * Returns a list of all the companies within the datastore that satisfy the filters that are provided. The
     * filters are defined using classes from the Search Builder v1.2 component and com.topcoder.timetracker.
     * common.search package.
     * </p>
     *
     *
     *
     * @return A list of companies that fulfill the given criterion. If no company fulfills it, then an empty array
     *         is returned.
     * @param filter The filter that is used as criterion to facilitate the search..
     * @throws IllegalArgumentException if the filter is null.
     * @throws CompanyDAOException if a problem occurs while accessing the datastore.
     */
    Company[] searchCompanies(Filter filter) throws CompanyDAOException;

    /**
     * <p>
     * Creates a datastore entry for each of the given companies. An id is automatically generated by the DAO and
     * assigned to the company. The Company is also considered to have been created by the specified user. While in
     * batch mode, the operation can be done atomically, or separately. If done atomically, then a failure at any
     * one of the specified entries will mean that the entire batch will be rolled back. Otherwise, only the
     * company where a failure occurred will be rolled back.
     * </p>
     *
     *
     *
     * @return The same company objects with the id, creationUser, modfiicationUser, creationDate, modificationDate
     *         modified appropriately. The index of the company in the returned array corresponds to the index of
     *         the company in the method argument.
     * @param companies The companies to create.
     * @param user The user responsible for creating the companies.
     * @param atomicBatchMode Whether the operation will be performed in atomic batch mode or not.
     * @throws IllegalArgumentException if companies is null or contains null elements, or user is null or an empty
     *         String.
     * @throws CompanyDAOException if a problem occurs while accessing the datastore.
     * @throws BatchCompanyDAOException if a problem occurs with multiple entities while processing them in
     *         non-atomic batch mode.
     */
    Company[] createCompanies(Company[] companies, String user, boolean atomicBatchMode)
            throws CompanyDAOException;

    /**
     * <p>
     * Retrieves the companies with the specified ids from the datastore.
     * </p>
     *
     *
     *
     * @return A list of companies with the given ids. The index of the company corresponds to the index of the id
     *         in the method argument.
     * @param ids The ids of the companies to retrieve.
     * @throws CompanyDAOException if a problem occurs while accessing the datastore.
     */
    Company[] retrieveCompanies(long[] ids) throws CompanyDAOException;

    /**
     * <p>
     * Updates the given companies in the data store. The companies are considered to have been modified by the
     * specified user. While in batch mode, the operation can be done atomically, or separately. If done
     * atomically, then a failure at any one of the specified entries will mean that the entire batch will be
     * rolled back. Otherwise, only the company where a failure occurred will be rolled back.
     * </p>
     *
     *
     *
     * @param companies An array of companies to update.
     * @param user The user responsible for performing the update.
     * @param atomicBatchMode Whether the operation will be performed in atomic batch mode or not.
     * @throws IllegalArgumentException if the companies array is null or contains null elements, or user is null
     *         or is an empty String.
     * @throws CompanyDAOException if a problem occurs while accessing the datastore.
     * @throws BatchCompanyDAOException if a problem occurs with multiple entities while processing them in
     *         non-atomic batch mode.
     */
    void updateCompanies(Company[] companies, String user, boolean atomicBatchMode) throws CompanyDAOException,
            BatchCompanyDAOException;

    /**
     * <p>
     * Deletes the specified companies from the data store. While in batch mode, the operation can be done
     * atomically, or separately. If done atomically, then a failure at any one of the specified entries will mean
     * that the entire batch will be rolled back. Otherwise, only the company where a failure occurred will be
     * rolled back.
     * </p>
     *
     *
     *
     * @param companies An array of companies to delete.
     * @param atomicBatchMode Whether the operation will be performed in atomic batch mode or not.
     * @throws IllegalArgumentException if companies is null or has null elements.
     * @throws CompanyDAOException if a problem occurs while accessing the datastore.
     * @throws BatchCompanyDAOException if a problem occurs with multiple entities while processing them in
     *         non-atomic batch mode.
     */
    void deleteCompanies(Company[] companies, boolean atomicBatchMode) throws CompanyDAOException,
            BatchCompanyDAOException;
}
