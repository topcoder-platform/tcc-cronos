/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.company.ejb;

import com.topcoder.search.builder.filter.Filter;

import com.topcoder.timetracker.company.BatchCompanyDAOException;
import com.topcoder.timetracker.company.Company;
import com.topcoder.timetracker.company.CompanyDAOException;
import com.topcoder.timetracker.company.CompanyManager;
import com.topcoder.timetracker.company.TimeTrackerCompanyHelper;

import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.UnknownNamespaceException;

import javax.ejb.CreateException;

import javax.naming.InitialContext;
import javax.naming.NamingException;


/**
 * <p>
 * Implements the CompanyManager interface to provide management of the Company objects through the use of a local
 * session EBJ. It will obtain the handle to the bean's local interface and will simply delegate all calls to it. It
 * implements all methods.
 * </p>
 *
 * <p>
 * <strong>Thread Safety</strong> This class is immutable and thread-safe. It is not expected that the data beans will
 * be used by more than one thread at a time.
 * </p>
 *
 * @author bramandia, argolite, TCSDEVELOPER
 * @version 3.2
 */
public class LocalCompanyManagerDelegate implements CompanyManager {
    /**
     * <p>
     * Represents the property name to retrieve the JndiReference value.
     * </p>
     */
    private static final String JNDI_REFERENCE_PROPERTY = "JndiReference";

    /**
     * Represents the local session ejb instance used for all calls. Created in the constructor, will not be null, and
     * will not change.
     */
    private final CompanyLocal localEJB;

    /**
     * Instantiates new CompanyLocal instance from the given namespace.
     *
     * @param namespace configuration namespace
     *
     * @throws InstantiationException If there is an error with construction
     * @throws IllegalArgumentException If namespace is null or empty.
     */
    public LocalCompanyManagerDelegate(String namespace) throws InstantiationException {
        TimeTrackerCompanyHelper.validateString(namespace, "namespace");

        // obtain jndi ejb reference from ConfigManager
        String jndiEjbReference = getStringPropertyValue(namespace, JNDI_REFERENCE_PROPERTY);

        try {
            InitialContext ic = new InitialContext();
            CompanyHomeLocal home = (CompanyHomeLocal) ic.lookup(jndiEjbReference);
            localEJB = home.create();
        } catch (ClassCastException e) {
            throw new InstantiationException("Fails to create the ejb.", e);
        } catch (NamingException e) {
            throw new InstantiationException("Fails to create the ejb.", e);
        } catch (CreateException e) {
            throw new InstantiationException("Fails to create the ejb.", e);
        }
    }

    /**
     * <p>
     * Get the string property value in <code>ConfigManager</code> with specified namespace and name.
     * </p>
     *
     * @param namespace the namespace of the config string property value .
     * @param name the name of the config string property value.
     *
     * @return the config string property value in <code>ConfigManager</code>.
     *
     * @throws InstantiationException if the namespace doesn't exist, or the parameter doesn't exist,
     *         or the parameter value is an empty string.
     */
    private String getStringPropertyValue(String namespace, String name)
        throws InstantiationException {
        try {
            String value = ConfigManager.getInstance().getString(namespace, name);

            if ((value == null)) {
                throw new InstantiationException("The required parameter " + name + " in namespace " + namespace
                    + " doesn't exist.");
            }

            if (value.trim().length() == 0) {
                throw new InstantiationException("The parameter value of " + name + " in namespace " + namespace
                    + " is an empty string.");
            }

            return value;
        } catch (UnknownNamespaceException une) {
            throw new InstantiationException("The namespace with the name of " + namespace + " doesn't exist.", une);
        }
    }

    /**
     * <p>
     * Creates a datastore entry for the given company. An id is generated and assigned to the company. The Company is
     * also considered to have been created by the specified user. Audit is to be performed if doAudit is true.<br/>
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
    public Company createCompany(Company company, String user, boolean doAudit) throws CompanyDAOException {
        return this.localEJB.createCompany(company, user, doAudit);
    }

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
    public Company retrieveCompany(long id) throws CompanyDAOException {
        return this.localEJB.retrieveCompany(id);
    }

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
    public void updateCompany(Company company, String user, boolean doAudit) throws CompanyDAOException {
        this.localEJB.updateCompany(company, user, doAudit);
    }

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
     * @throws CompanyNotFoundException if the company to delete was not found in the data store.
     */
    public void deleteCompany(Company company, boolean doAudit, String user) throws CompanyDAOException {
        this.localEJB.deleteCompany(company, doAudit, user);
    }

    /**
     * <p>
     * Enumerates all the companies that are present within the data store.
     * </p>
     *
     * @return A list of all the companies present in the data store.
     *
     * @throws CompanyDAOException if a problem occurs while accessing the datastore.
     */
    public Company[] listCompanies() throws CompanyDAOException {
        return this.localEJB.listCompanies();
    }

    /**
     * <p>
     * Returns a list of all the companies within the datastore that satisfy the filters that are provided. The filter
     * is defined using classes from the Search Builder v1.3 component.
     * </p>
     *
     * @param filter The filter that is used as criterion to facilitate the search..
     *
     * @return A list of companies that fulfill the given criterion. If no company fulfills it, then an empty array is
     *         returned.
     *
     * @throws IllegalArgumentException if the filter is null.
     * @throws CompanyDAOException if a problem occurs while accessing the datastore.
     */
    public Company[] search(Filter filter) throws CompanyDAOException {
        return this.localEJB.search(filter);
    }

    /**
     * <p>
     * Creates a datastore entry for each of the given companies. An id is automatically generated by the DAO and
     * assigned to the company. The Company is also considered to have been created by the specified user. Audit is to
     * be performed if doAudit is true.
     * </p>
     *
     * <p>
     * While in batch mode, the operation can be done atomically, or separately. If done atomically, then a failure at
     * any one of the specified entries will mean that the entire batch will be rolled back. Otherwise, only the
     * company where a failure occurred will be rolled back.
     * </p>
     *
     * <p>
     * If one of the company is not complete (there is a missing field other than algorithmName), and this is in
     * non-atomic batch mode, then throw IllegalArgumentException wrapped appropriately inside
     * BatchCompanyDAOException.
     * </p>
     *
     * @param companies The companies to create.
     * @param user The user responsible for creating the companies.
     * @param atomicBatchMode Whether the operation will be performed in atomic batch mode or not.
     * @param doAudit Indicates whether audit is to be performed
     *
     * @return The same company objects with the id, creationUser, modfiicationUser, creationDate, modificationDate
     *         modified appropriately. The index of the company in the returned array corresponds to the index of the
     *         company in the method argument.
     *
     * @throws IllegalArgumentException if companies is null or contains null elements, or user is null or an empty
     *         String, or if any field in company is null (except algorithmName) when in atomic batch mode.
     * @throws CompanyDAOException if a problem occurs while accessing the datastore.
     * @throws BatchCompanyDAOException if a problem occurs with multiple entities while processing them in non-atomic
     *         batch mode.
     */
    public Company[] createCompanies(Company[] companies, String user, boolean atomicBatchMode, boolean doAudit)
        throws CompanyDAOException, BatchCompanyDAOException {
        if (atomicBatchMode) {
            return this.localEJB.createCompanies(companies, user, doAudit);
        } else {
            return this.localEJB.createCompaniesNonAtomically(companies, user, doAudit);
        }
    }

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
     * @throws IllegalArgumentException if ids is null or contains non positive element.
     * @throws CompanyDAOException if a problem occurs while accessing the datastore.
     */
    public Company[] retrieveCompanies(long[] ids) throws CompanyDAOException {
        return this.localEJB.retrieveCompanies(ids);
    }

    /**
     * <p>
     * Updates the given companies in the data store. The companies are considered to have been modified by the
     * specified user. Audit is to be performed if doAudit is true
     * </p>
     *
     * <p>
     * While in batch mode, the operation can be done atomically, or separately. If done atomically, then a failure at
     * any one of the specified entries will mean that the entire batch will be rolled back. Otherwise, only the
     * company where a failure occurred will be rolled back.
     * </p>
     *
     * <p>
     * If one of the company is not complete (there is a missing field other than algorithmName), and this is in
     * non-atomic batch mode, then throw IllegalArgumentException wrapped appropriately inside
     * BatchCompanyDAOException.
     * </p>
     *
     * @param companies An array of companies to update.
     * @param user The user responsible for performing the update.
     * @param atomicBatchMode Whether the operation will be performed in atomic batch mode or not.
     * @param doAudit Indicates whether audit is to be performed
     *
     * @throws IllegalArgumentException if the companies array is null or contains null elements, or user is null or is
     *         an empty String, or if any field in company is null (except algorithmName) when in atomic batch mode.
     * @throws CompanyDAOException if a problem occurs while accessing the datastore.
     * @throws CompanyNotFoundException if some of the passed companies is not found when in atomic batch mode.
     * @throws BatchCompanyDAOException if a problem occurs with multiple entities while processing them in non-atomic
     *         batch mode.
     */
    public void updateCompanies(Company[] companies, String user, boolean atomicBatchMode, boolean doAudit)
        throws CompanyDAOException, BatchCompanyDAOException {
        if (atomicBatchMode) {
            this.localEJB.updateCompanies(companies, user, doAudit);
        } else {
            this.localEJB.updateCompaniesNonAtomically(companies, user, doAudit);
        }
    }

    /**
     * <p>
     * Deletes the specified companies from the data store. Audit is to be performed if doAudit is true. The user
     * argument is used only if audit is true to record the user who performed the deletion.
     * </p>
     *
     * <p>
     * While in batch mode, the operation can be done atomically, or separately. If done atomically, then a failure at
     * any one of the specified entries will mean that the entire batch will be rolled back. Otherwise, only the
     * company where a failure occurred will be rolled back.
     * </p>
     *
     * @param companies An array of companies to delete.
     * @param atomicBatchMode Whether the operation will be performed in atomic batch mode or not.
     * @param doAudit Indicates whether audit is to be performed
     * @param user the user who performed the deletion
     *
     * @throws IllegalArgumentException if companies is null or has null elements, or if doAudit is true and either
     *         user is null or empty string.
     * @throws CompanyDAOException if a problem occurs while accessing the datastore.
     * @throws CompanyNotFoundException if some of the passed companies is not found when in atomic batch mode.
     * @throws BatchCompanyDAOException if a problem occurs with multiple entities while processing them in non-atomic
     *         batch mode.
     */
    public void deleteCompanies(Company[] companies, boolean atomicBatchMode, boolean doAudit, String user)
        throws CompanyDAOException, BatchCompanyDAOException {
        if (atomicBatchMode) {
            this.localEJB.deleteCompanies(companies, doAudit, user);
        } else {
            this.localEJB.deleteCompaniesNonAtomically(companies, doAudit, user);
        }
    }
}
