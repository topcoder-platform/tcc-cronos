/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.company.ejb;

import com.topcoder.search.builder.filter.Filter;

import com.topcoder.timetracker.company.BatchCompanyDAOException;
import com.topcoder.timetracker.company.Company;
import com.topcoder.timetracker.company.CompanyDAO;
import com.topcoder.timetracker.company.CompanyDAOException;
import com.topcoder.timetracker.company.TimeTrackerCompanyHelper;

import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.impl.ConfigManagerSpecificationFactory;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import javax.naming.Context;
import javax.naming.InitialContext;


/**
 * <p>
 * This is a Stateless <code>SessionBean</code> that is used to provided business services to handle the actual company
 * manager requests within the Time Tracker Application. It simply delegates all operations to the
 * <code>CompanyDAO</code> it obtains from the <code>ObjectFactory</code>.
 * </p>
 *
 * <p>
 * Note: the look up value of SpecificationNamespace and CompanyDAOKey should be in the environmental entry.
 * </p>
 *
 * <p>
 * Transactions for this bean are handled by the EJB Container. It is expected that the transaction level declared in
 * the deployment descriptor for this class will be REQUIRED. Transactional level should be set to be NotSupported for
 * the three non-atomical batch method, which in turn forces the individual calls from them to spawn their own
 * separate transactions that can be rolled back individially if their fail, thus ensuring we have container-managed
 * non-atomic batch operations.
 * </p>
 *
 * <p>
 * The DAO will be instantiated in ejbCreate() and it will be available during use of the bean.
 * </p>
 *
 * <p>
 * <b>Thread Safety: </b>This object is mutable and thread-safe, as the container handles this.
 * </p>
 *
 * @author bramandia, argolite, TCSDEVELOPER
 * @version 3.2
 */
public class CompanyBean implements SessionBean {
    /**
     * <p>
     * Represents the property name to retrieve the SpecificationNamespace value from the environmental entry.
     * </p>
     */
    private static final String OF_NAMESPACE_PROPERTY = "SpecificationNamespace";

    /**
     * <p>
     * Represents the property name to retrieve the CompanyDAOKey value from the environmental entry.
     * </p>
     */
    private static final String DAO_KEY_PROPERTY = "CompanyDAOKey";

    /**
     * <p>
     * Represents the session context of this bean. It is used to indicate to the container if the bean wants to
     * rollback a transaction. This would usually occur if an application exception occurs.
     * </p>
     *
     * <p>
     * Set in the setSessionContext() method by the container right after instantiation, as per EJB specifications. The
     * container guarantees to set it right after instan, so it will never be null after being set.
     * </p>
     */
    private SessionContext sessionContext = null;

    /**
     * <p>
     * Represents the data access object for performing the persistence operations for the company entries.
     * </p>
     *
     * <p>
     * It is initialized in the ejbCreate method, and not changed afterwards. There will be one instantiation per one
     * session bean lifetime. It will not be null after being set. Should an error occur while instantiating, the
     * container will discard this bean and attept with another one. Either way, as far as the use is concerned, it
     * will not be null when the business methods are called.
     * </p>
     */
    private CompanyDAO companyDAO = null;

    /**
     * Empty constructor.
     */
    public CompanyBean() {
    }

    /**
     * <p>
     * Creates the bean. The dao field is instantiated in this method from the object factory with the specified
     * SpecificationNamespace and CompanyDAOKey value from the environmental entry.
     * </p>
     *
     * @throws CreateException If any error occurs while instantiating.
     */
    public void ejbCreate() throws CreateException {
        try {
            Context context = (Context) new InitialContext().lookup("java:comp/env");

            // obtain the namespace used for ConfigManagerSpecificationFactory to use with ObjectFactory
            String ofNamespace = (String) context.lookup(OF_NAMESPACE_PROPERTY);

            // obtain the key for audit DAO
            String daoKey = (String) context.lookup(DAO_KEY_PROPERTY);

            // create the audit DAO object
            ObjectFactory objectFactory = new ObjectFactory(new ConfigManagerSpecificationFactory(ofNamespace));
            this.companyDAO = (CompanyDAO) objectFactory.createObject(daoKey);
        } catch (Exception e) {
            throw new CreateException("Fails to create the CompanyDAO instance. Exception: "
                + TimeTrackerCompanyHelper.getExceptionStaceTrace(e));
        }
    }

    /**
     * <p>
     * Removes the bean. This is an empty implementation.
     * </p>
     */
    public void ejbRemove() {
    }

    /**
     * <p>
     * Activates the bean. This is an empty implementation.
     * </p>
     */
    public void ejbActivate() {
    }

    /**
     * <p>
     * Passivates the bean. This is an empty implementation.
     * </p>
     */
    public void ejbPassivate() {
    }

    /**
     * <p>
     * Sets the session context.
     * </p>
     *
     * @param context session context.
     */
    public void setSessionContext(SessionContext context) {
        this.sessionContext = context;
    }

    /**
     * <p>
     * Creates a datastore entry for the given company. An id is automatically generated by the DAO and assigned to the
     * company. The Company is also considered to have been created by the specified user. Audit is to be performed if
     * doAudit is true.
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
        try {
            return this.companyDAO.createCompany(company, user, doAudit);
        } catch (CompanyDAOException e) {
            sessionContext.setRollbackOnly();
            throw e;
        }
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
        try {
            return this.companyDAO.retrieveCompany(id);
        } catch (CompanyDAOException e) {
            sessionContext.setRollbackOnly();
            throw e;
        }
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
        try {
            this.companyDAO.updateCompany(company, user, doAudit);
        } catch (CompanyDAOException e) {
            sessionContext.setRollbackOnly();
            throw e;
        }
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
     * @throws CompanyNotFoundException if the company to be deleted was not found in the data store.
     */
    public void deleteCompany(Company company, boolean doAudit, String user) throws CompanyDAOException {
        try {
            this.companyDAO.deleteCompany(company, doAudit, user);
        } catch (CompanyDAOException e) {
            sessionContext.setRollbackOnly();
            throw e;
        }
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
        try {
            return this.companyDAO.listCompanies();
        } catch (CompanyDAOException e) {
            sessionContext.setRollbackOnly();
            throw e;
        }
    }

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
    public Company[] search(Filter filter) throws CompanyDAOException {
        try {
            return this.companyDAO.search(filter);
        } catch (CompanyDAOException e) {
            sessionContext.setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * Creates a datastore entry for each of the given companies. An id is automatically generated by the DAO and
     * assigned to the company. The Company is also considered to have been created by the specified user. Audit is to
     * be performed if doAudit is true.
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
    public Company[] createCompanies(Company[] companies, String user, boolean doAudit) throws CompanyDAOException {
        try {
            return this.companyDAO.createCompanies(companies, user, doAudit);
        } catch (CompanyDAOException e) {
            sessionContext.setRollbackOnly();
            throw e;
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
     * @throws CompanyDAOException if a problem occurs while accessing the datastore.
     * @throws IllegalArgumentException if ids is null or contains non positive element
     */
    public Company[] retrieveCompanies(long[] ids) throws CompanyDAOException {
        try {
            return this.companyDAO.retrieveCompanies(ids);
        } catch (CompanyDAOException e) {
            sessionContext.setRollbackOnly();
            throw e;
        }
    }

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
    public void updateCompanies(Company[] companies, String user, boolean doAudit) throws CompanyDAOException {
        try {
            this.companyDAO.updateCompanies(companies, user, doAudit);
        } catch (CompanyDAOException e) {
            sessionContext.setRollbackOnly();
            throw e;
        }
    }

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
    public void deleteCompanies(Company[] companies, boolean doAudit, String user) throws CompanyDAOException {
        try {
            this.companyDAO.deleteCompanies(companies, doAudit, user);
        } catch (CompanyDAOException e) {
            sessionContext.setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * Creates a datastore entry for each of the given companies. An id is automatically generated by the DAO and
     * assigned to the company. The Company is also considered to have been created by the specified user. Audit is to
     * be performed if doAudit is true.
     * </p>
     *
     * <p>
     * This operation is done non-atomically. Only the company where a failure occurred will be rolled back.
     * </p>
     *
     * <p>
     * Note: transactional level should be set to be NotSupported for this method, which in turn forces the individual
     * calls from it to spawn their own separate transactions that can be rolled back individially if their fail, thus
     * ensuring we have container-managed non-atomic batch operations.
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
    public Company[] createCompaniesNonAtomically(Company[] companies, String user, boolean doAudit)
        throws BatchCompanyDAOException {
        // argument validation
        TimeTrackerCompanyHelper.validateNotNull(companies, "companies");
        for (int i = 0; i < companies.length; i++) {
            TimeTrackerCompanyHelper.validateNotNull(companies[i], "The " + i + "th company");
        }
        TimeTrackerCompanyHelper.validateString(user, "user");

        // ignore any exception from the individual bean methods
        Throwable[] causes = new Throwable[companies.length];

        int count = 0;
        for (int i = 0; i < companies.length; i++) {
            try {
                // call the individual bean methods
                this.createCompany(companies[i], user, doAudit);
            } catch (CompanyDAOException e) {
                causes[i] = e;
                count ++;
            } catch (IllegalArgumentException e) {
                causes[i] = e;
                count ++;
            }
        }

        if (count > 0) {
            throwBatchCompanyDAOException(count, companies, causes, "createCompaniesNonAtomically");
        }
        return companies;
    }

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
     * <p>
     * Note: transactional level should be set to be NotSupported for this method, which in turn forces the individual
     * calls from it to spawn their own separate transactions that can be rolled back individially if their fail, thus
     * ensuring we have container-managed non-atomic batch operations.
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
    public void updateCompaniesNonAtomically(Company[] companies, String user, boolean doAudit)
        throws BatchCompanyDAOException {
        // argument validation
        TimeTrackerCompanyHelper.validateNotNull(companies, "companies");
        for (int i = 0; i < companies.length; i++) {
            TimeTrackerCompanyHelper.validateNotNull(companies[i], "The " + i + "th company");
        }
        TimeTrackerCompanyHelper.validateString(user, "user");

        // ignore any exception from the individual bean methods
        Throwable[] causes = new Throwable[companies.length];

        int count = 0;
        for (int i = 0; i < companies.length; i++) {
            try {
                // call the individual bean methods
                this.updateCompany(companies[i], user, doAudit);
            } catch (CompanyDAOException e) {
                causes[i] = e;
                count ++;
            } catch (IllegalArgumentException e) {
                causes[i] = e;
                count ++;
            }
        }

        if (count > 0) {
            throwBatchCompanyDAOException(count, companies, causes, "updateCompaniesNonAtomically");
        }
    }

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
     * <p>
     * Note: transactional level should be set to be NotSupported for this method, which in turn forces the individual
     * calls from it to spawn their own separate transactions that can be rolled back individially if their fail, thus
     * ensuring we have container-managed non-atomic batch operations.
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
    public void deleteCompaniesNonAtomically(Company[] companies, boolean doAudit, String user)
        throws BatchCompanyDAOException {        // argument validation
        TimeTrackerCompanyHelper.validateNotNull(companies, "companies");
        for (int i = 0; i < companies.length; i++) {
            TimeTrackerCompanyHelper.validateNotNull(companies[i], "The " + i + "th company");
        }
        if (doAudit) {
            TimeTrackerCompanyHelper.validateString(user, "user");
        }

        // ignore any exception from the individual bean methods
        Throwable[] causes = new Throwable[companies.length];

        int count = 0;
        for (int i = 0; i < companies.length; i++) {
            try {
                // call the individual bean methods
                this.deleteCompany(companies[i], doAudit, user);
            } catch (CompanyDAOException e) {
                causes[i] = e;
                count ++;
            } catch (IllegalArgumentException e) {
                causes[i] = e;
                count ++;
            }
        }

        if (count > 0) {
            throwBatchCompanyDAOException(count, companies, causes, "deleteCompaniesNonAtomically");
        }
    }

    /**
     * <p>
     * Throws the BatchCompanyDAOException as there is some problem in the given companies array.
     * </p>
     *
     * @param count the count of the errors.
     * @param companies the given companies array
     * @param cause the given causes.
     * @param operation the batch operation name.
     */
    private void throwBatchCompanyDAOException(int count, Company[] companies, Throwable[] causes, String operation)
        throws BatchCompanyDAOException {
        Company[] problemCompanies = new Company[count];
        int index = 0;
        for (int i = 0; i < companies.length; i++) {
            if (causes[i] != null) {
                problemCompanies[index ++] = companies[i];
            }
        }
        Throwable[] problemCauses = new Throwable[count];
        index = 0;
        for (int i = 0; i < companies.length; i++) {
            if (causes[i] != null) {
                problemCauses[index ++] = causes[i];
            }
        }
        throw new BatchCompanyDAOException("Exceptions in " + operation + ".",
                problemCauses, problemCompanies);
    }
}
