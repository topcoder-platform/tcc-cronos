/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.manager.dao;

import java.util.List;

import com.topcoder.clients.dao.CompanyDAO;
import com.topcoder.clients.dao.DAOConfigurationException;
import com.topcoder.clients.dao.DAOException;
import com.topcoder.clients.dao.EntityNotFoundException;
import com.topcoder.clients.dao.ejb3.CompanyDAOBean;
import com.topcoder.clients.manager.CompanyEntityNotFoundException;
import com.topcoder.clients.manager.CompanyManager;
import com.topcoder.clients.manager.CompanyManagerException;
import com.topcoder.clients.manager.ManagerConfigurationException;
import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.Company;
import com.topcoder.clients.model.Project;
import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.util.idgenerator.IDGenerationException;
import com.topcoder.util.objectfactory.ObjectFactory;

/**
 * This is class is an implementation of CompanyManager interface.
 *
 * <p>
 * It provides convenient methods to manage Company entities. All operations will eventually delegate to the underlying
 * CompanyDAO appropriately.
 * </p>
 *
 * <p>
 * It uses IDGenerator to generate new IDs for entities, and has a logger used to log method entry/exit and all
 * exceptions.
 * </p>
 *
 * <p>
 * It can be configured via ConfigurationObject directly, as well as configuration file compatible with the format
 * defined in Configuration Persistence component.
 * </p>
 *
 * <p>
 * For checking whether the entity already exists in persistence, we call retrieveClient(id), if non-null entity is
 * returned, then the entity exists. This will affect the performance a bit, but we can not get the correct result by
 * just checking the ID.
 * </p>
 * <p>
 * <strong>Usage Sample:</strong>
 * <pre>
 *      ConfigurationObject obj = new DefaultConfigurationObject(&quot;root&quot;);
 *      obj.setPropertyValue(&quot;id_generator_name&quot;, &quot;test&quot;);
 *      obj.setPropertyValue(&quot;logger_name&quot;, &quot;System.out&quot;);
 *
 *      ConfigurationObject child = new DefaultConfigurationObject(&quot;object_factory_configuration&quot;);
 *
 *      obj.addChild(child);
 *      obj.setPropertyValue(&quot;company_dao&quot;, &quot;com.topcoder.clients.manager.MockCompanyDAO&quot;);
 *
 *      // create an instance of DAOCompanyManager by default
 *      DAOCompanyManager manager = new DAOCompanyManager();
 *      // create an instance of DAOCompanyManager with ConfigurationObject
 *      manager = new DAOCompanyManager(obj);
 *      // create an instance of DAOCompanyManager with configuration file
 *      manager = new DAOCompanyManager(&quot;test_files/daocompany.properties&quot;, &quot;daoCompany&quot;);
 *
 *      // get Company with id
 *      Company company = manager.retrieveCompany(99);

 *      // get all companies
 *      List&lt;Company&gt; companies = manager.retrieveAllCompanies();
 *
 *      // search companies with name=&quot;company-1&quot;
 *      companies = manager.searchCompaniesByName(&quot;company-1&quot;);
 *
 *      EqualToFilter filter = new EqualToFilter(&quot;Company-2&quot;, new Boolean(false));
 *
 *      // search companies with filter
 *      companies = manager.searchCompanies(filter);
 *
 *      // get clients for company
 *      List&lt;Client&gt; clients = manager.getClientsForCompany(company);
 *
 *      // get projects for company
 *      List&lt;Project&gt; projects = manager.getProjectsForCompany(company);
 *
 *      // update company's name
 *      company.setName(&quot;dsa&quot;);
 *      manager.updateCompany(company);
 *
 *      // deletes Company
 *      manager.deleteCompany(company);
 *
 *      Company newCompany = new Company();
 *      newCompany.setName(&quot;name&quot;);
 *      newCompany.setDeleted(false);
 *      newCompany.setPasscode(&quot;code&quot;);
 *      // create a new Company
 *      manager.createCompany(newCompany);
 * </pre>
 *
 * </p>
 *
 * <p>
 * Thread Safety: This class is thread-safe. All fields do not change after construction. Inner CompanyDAO, IDGenerator
 * and Log are effectively thread-safe too.
 * </p>
 *
 * @author moonli, Chenhong
 * @version 1.0
 */
public class DAOCompanyManager extends AbstractDAOManager implements CompanyManager {
    /**
     * Represents the name of this class. It will be used during entrance log and exist log.
     */
    private static final String CLASSNAME = DAOCompanyManager.class.getName();

    /**
     * Represents the object_factory_configuration property key when retrieving child ConfigurationObject to create
     * ObjectFactory instance.
     */
    private static final String OBJECT_FACTORY_CONFIGURATION = "object_factory_configuration";

    /**
     * Represents the company_dao property key.
     */
    private static final String COMPANY_DAO = "company_dao";

    /**
     * Represents the a instance of CompanyDAO, it provides the persistence APIs for managing Company entities.
     * <p>
     * It's set in the constructor, and do not change afterwards, can't be null. It's used to
     * create/update/delete/search Company entities.
     * </p>
     */
    private final CompanyDAO companyDAO;

    /**
     * Constructs an instance of this class by default.
     *
     * @throws ManagerConfigurationException
     *             if error occurred when creating IDGenerator
     */
    public DAOCompanyManager() throws ManagerConfigurationException {
        companyDAO = new CompanyDAOBean();
    }

    /**
     * Constructs an instance of this class with given configuration object.
     *
     * @param configuration
     *            the ConfigurationObject used to configure this class, can't be null
     *
     * @throws IllegalArgumentException
     *             if argument is null
     * @throws ManagerConfigurationException
     *             if error occurred when configuring this class, e.g. required configuration value is missing,
     *             or error occurred in object factory
     *
     */
    public DAOCompanyManager(ConfigurationObject configuration) throws ManagerConfigurationException {
        super(configuration);

        ConfigurationObject objectFactoryConfig = Helper.getChildConfigurationObject(configuration,
                OBJECT_FACTORY_CONFIGURATION);

        ObjectFactory factory = Helper.createObjectFactory(objectFactoryConfig);

        // get the config value for create companyDAO.
        String companyDAOValue = Helper.getConfigurationParameter(configuration, COMPANY_DAO, true);

        // create the companyDAO
        companyDAO = (CompanyDAO) Helper.createObject(factory, companyDAOValue, CompanyDAO.class);
    }

    /**
     * Constructs an instance of this class with configuration in a specified file.
     *
     * @param namespace
     *            namespace of the configuration for this class, can't be null or empty
     * @param filename
     *            path of the configuration file, it can be null, but can't be empty
     * @throws IllegalArgumentException
     *             if any parameter is invalid e.g. filename is empty, namespace is null or empty
     * @throws ManagerConfigurationException
     *             if error occurred when configuring this class, e.g. required configuration value is missing,
     *             or error occurred in object factory
     */
    public DAOCompanyManager(String filename, String namespace) throws ManagerConfigurationException {
        this(getConfiguration(filename, namespace, DAOCompanyManager.class.getName()));
    }

    /**
     * Creates a new Company. After creation, a new ID will be generated for it.
     *
     * @param company
     *            the new Company to create, can not be null (passcode should be non-null, non-empty string, isDeleted
     *            should be false)
     * @return the Company instance created, with new ID assigned.
     *
     * @throws IllegalArgumentException
     *             if the argument is invalid (null, passcode is null or empty string, or isDeleted is true)
     * @throws CompanyManagerException
     *             if any error occurred during this operation, e.g. error in persistence layer, or in ID generator
     */
    public Company createCompany(Company company) throws CompanyManagerException {
        String method = "createCompany(Company company)";
        Helper.logEntranceInfo(getLog(), CLASSNAME, method, new String[] {"company"}, new String[] {Helper
                .formatCompany(company)});

        try {
            Helper.validateCompany(company);

            // generate a new id for the company.
            company.setId(getIDGenerator().getNextID());

            // save the company to persistence
            Company newCompany = (Company) companyDAO.save(company);

            Helper.logExitInfo(getLog(), CLASSNAME, method, Helper.formatCompany(newCompany));

            return newCompany;
        } catch (IllegalArgumentException iae) {
            Helper.logException(getLog(), CLASSNAME, method, iae);
            throw iae;
        } catch (IDGenerationException e) {
            throw wrapInnerException(method, "Fail to generate id for company, cause by " + e.getMessage(), e, company);
        } catch (DAOException daoException) {
            throw wrapInnerException(method, "Fail to create the company, cause by " + daoException.getMessage(),
                    daoException, company);
        } catch (DAOConfigurationException daoConfigurationException) {
            throw wrapInnerException(method, "Fail to create the company, cause by "
                    + daoConfigurationException.getMessage(), daoConfigurationException, company);
        } catch (ClassCastException cce) {
            throw wrapInnerException(method, "Fail to create the company, cause by " + cce.getMessage(), cce, company);
        }
    }

    /**
     * Wrap inner DAOException , DAOConfigurationException and IDGenerationException to CompanyManagerException and
     * return.
     *
     * @param method
     *            the method name , used when log the exception
     * @param message
     *            the error message
     * @param e
     *            the cause to wrap
     * @param company
     *            the Company used to create CompanyManagerException, can be null
     * @return CompanyManagerException created
     */
    private CompanyManagerException wrapInnerException(String method, String message, Exception e, Company company) {
        CompanyManagerException cmException = new CompanyManagerException(message, e, company);

        Helper.logException(getLog(), CLASSNAME, method, cmException);

        return cmException;
    }

    /**
     * Updates a Company. Error will be thrown if the company to update does not exist in persistence.
     *
     *
     * @param company
     *            the company to update, can not be null, id should be positive (passcode should be non-null,
     *            non-empty string, isDeleted should be false)
     * @return the updated Company
     * @throws IllegalArgumentException
     *             if the argument is invalid e.g. company param is null, id not positive, passcode is null or empty,
     *             isDeleted is true
     * @throws CompanyEntityNotFoundException
     *             if the company to be updated does not exist in persistence
     * @throws CompanyManagerException
     *             if any error occurred during this operation, e.g. error in persistence layer
     */
    public Company updateCompany(Company company) throws CompanyEntityNotFoundException, CompanyManagerException {
        String method = "updateCompany(Company company)";
        Helper.logEntranceInfo(getLog(), CLASSNAME, method, new String[] {"company"}, new String[] {Helper
                .formatCompany(company)});

        try {
            Helper.validateCompany(company);

            // check if the company exist in the persistence.
            checkCompanyExist(company, method);

            // save the updated company
            Company newCompany = (Company) companyDAO.save(company);

            Helper.logExitInfo(getLog(), CLASSNAME, method, Helper.formatCompany(newCompany));

            return newCompany;
        } catch (IllegalArgumentException iae) {
            Helper.logException(getLog(), CLASSNAME, method, iae);
            throw iae;
        } catch (DAOConfigurationException e) {
            throw wrapInnerException(method, "Fail to update the Company, cause by " + e.getMessage(), e, company);
        } catch (DAOException e) {
            throw wrapInnerException(method, "Fail to update the Company, cause by " + e.getMessage(), e, company);
        } catch (ClassCastException cce) {
            throw wrapInnerException(method, "Fail to update the Company, cause by " + cce.getMessage(), cce, company);
        }
    }

    /**
     * Check if the company entity exists in the persistence.
     *
     * @param company
     *            the company to be checked
     * @param method
     *            the method name
     * @throws CompanyEntityNotFoundException
     *             if the company does not existed
     * @throws DAOException
     *             if any other inner exception except IllegalArgumentException and EntityNotFoundException while
     *             perform companyDAO.retrieveById(id) function
     */
    private void checkCompanyExist(Company company, String method)
        throws CompanyEntityNotFoundException, DAOException {
        try {
            if (companyDAO.retrieveById(company.getId()) == null) {
                CompanyEntityNotFoundException cenf = new CompanyEntityNotFoundException("The company  with id="
                        + company.getId() + " does not exist in persistence.");

                Helper.logException(getLog(), CLASSNAME, method, cenf);

                // throw ClientEntityNotFoundException if the company can not be found in the persistence.
                throw cenf;
            }

        } catch (EntityNotFoundException e) {
            throw handleCompanyEntityNotFoundException(method, " The company with id=" + company.getId()
                    + " does not exist.", e);
        } catch (IllegalArgumentException iae) {
            throw handleCompanyEntityNotFoundException(method, " The company with id=" + company.getId()
                    + " does not exist.", iae);
        }
    }

    /**
     * Wrap the IllegalArgumentException or EntityNotFoundException while checking entity exist.
     *
     * @param method
     *            the method name to log
     * @param message
     *            the message
     * @param cause
     *            the inner cause
     * @return CompanyEntityNotFoundException created with error message and inner cause
     */
    private CompanyEntityNotFoundException handleCompanyEntityNotFoundException(String method, String message,
            Exception cause) {
        CompanyEntityNotFoundException exception = new CompanyEntityNotFoundException(message, cause);

        Helper.logException(getLog(), CLASSNAME, method, exception);

        return exception;
    }

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
     *
     *
     */
    public Company deleteCompany(Company company) throws CompanyManagerException, CompanyEntityNotFoundException {
        String method = "deleteCompany(Company company)";
        Helper.logEntranceInfo(getLog(), CLASSNAME, method, new String[] {"company"}, new String[] {Helper
                .formatCompany(company)});

        try {
            Helper.checkNull(company, "company");
            if (company.isDeleted()) {
                throw new IllegalArgumentException("The isDeleted of company should not be true.");
            }

            // delete the company
            companyDAO.delete(company);

            // set the deleted value to true.
            company.setDeleted(true);

            Helper.logExitInfo(getLog(), CLASSNAME, method, Helper.formatCompany(company));
            return company;

        } catch (IllegalArgumentException iae) {
            Helper.logException(getLog(), CLASSNAME, method, iae);
            throw iae;
        } catch (EntityNotFoundException e) {
            throw handleCompanyEntityNotFoundException(method, " The company with id=" + company.getId()
                    + " does not exist.", e);
        } catch (DAOConfigurationException e) {
            throw wrapInnerException(method, "Fail to delete the Company, cause by " + e.getMessage(), e, company);
        } catch (DAOException e) {
            throw wrapInnerException(method, "Fail to delete the Company , cause by " + e.getMessage(), e, company);
        }
    }

    /**
     * Gets the Company by its ID.
     *
     * @param id
     *            id of the Company to retrieve, can't be negative
     * @return the Company whose ID matches given ID, or null if none exists with given ID
     *
     * @throws IllegalArgumentException
     *             if id is not positive
     * @throws CompanyManagerException
     *             if any error occurred during this operation, e.g. error in persistence layer
     */
    public Company retrieveCompany(long id) throws CompanyManagerException {
        String method = "retrieveCompany(long id)";
        Helper.logEntranceInfo(getLog(), CLASSNAME, method, new String[] {"id"}, new String[] {String.valueOf(id)});

        try {
            Helper.checkId(id);

            Company company = (Company) companyDAO.retrieveById(id);

            Helper.logExitInfo(getLog(), CLASSNAME, method, Helper.formatCompany(company));
            return company;

        } catch (IllegalArgumentException iae) {
            Helper.logException(getLog(), CLASSNAME, method, iae);
            throw iae;
        } catch (DAOConfigurationException e) {
            throw wrapInnerException(method, "Fail to retrieve company with id = " + id + " cause by "
                    + e.getMessage(), e, null);
        } catch (DAOException e) {
            throw wrapInnerException(method, "Fail to retrieve company with id = " + id + " cause by "
                    + e.getMessage(), e, null);
        } catch (ClassCastException cce) {
            throw wrapInnerException(method, "The entity retrieved is not a Company instance, cause by "
                    + cce.getMessage(), cce, null);
        }
    }

    /**
     * Gets all the Companies. If none exists, empty list will be returned.
     *
     * @return a list of Company objects, empty list if none exists
     * @throws CompanyManagerException
     *             if any error occurred during this operation, e.g. error in persistence layer
     */
    public List<Company> retrieveAllCompanies() throws CompanyManagerException {
        String method = "retrieveAllCompanies";
        Helper.logEntranceInfo(getLog(), CLASSNAME, method, new String[] {}, new String[] {});

        try {
            List<Company> companies = companyDAO.retrieveAll();

            Helper.logExitInfo(getLog(), CLASSNAME, method, Helper.formatCompanyList(companies));

            return companies;
        } catch (DAOConfigurationException e) {
            throw wrapInnerException(method, "Fail to retrieve all company, cause by " + e.getMessage(), e, null);
        } catch (DAOException e) {
            throw wrapInnerException(method, "Fail to retrieve all company cause by " + e.getMessage(), e, null);
        }
    }

    /**
     * Finds the Companies that match given name. Empty list will be returned if none is found.
     *
     * @param name
     *            name of the Companies to search, can't be null or empty
     * @return a list of Companies that match given name, empty list if none is found
     * @throws IllegalArgumentException
     *             if parameter name is null or empty
     * @throws CompanyManagerException
     *             if any error occurred during this operation, e.g. error in persistence layer
     */
    public List<Company> searchCompaniesByName(String name) throws CompanyManagerException {
        String method = "searchCompaniesByName(String name)";
        Helper.logEntranceInfo(getLog(), CLASSNAME, method, new String[] {"name"}, new String[] {name});

        try {
            Helper.checkString(name, "name");

            List<Company> companies = companyDAO.searchByName(name);

            Helper.logExitInfo(getLog(), CLASSNAME, method, Helper.formatCompanyList(companies));

            return companies;
        } catch (IllegalArgumentException iae) {
            Helper.logException(getLog(), CLASSNAME, method, iae);
            throw iae;
        } catch (DAOConfigurationException e) {
            throw wrapInnerException(method, "Fail to search companies by name=" + name + " cause by "
                    + e.getMessage(), e, null);
        } catch (DAOException e) {
            throw wrapInnerException(method, "Fail to search companies by name = " + name + " cause by "
                    + e.getMessage(), e, null);
        }

    }

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
    public List<Company> searchCompanies(Filter filter) throws CompanyManagerException {
        String method = "searchCompanies(Filter filter)";
        Helper.logEntranceInfo(getLog(), CLASSNAME, method, new String[] {"filter"}, new String[] {String
                .valueOf(filter)});

        try {
            Helper.checkNull(filter, "filter");

            List<Company> companies = companyDAO.search(filter);

            Helper.logExitInfo(getLog(), CLASSNAME, method, Helper.formatCompanyList(companies));

            return companies;
        } catch (IllegalArgumentException iae) {
            Helper.logException(getLog(), CLASSNAME, method, iae);
            throw iae;
        } catch (DAOException e) {
            throw wrapInnerException(method, "Fail to search companies by filter, cause by " + e.getMessage(), e,
                    null);
        } catch (DAOConfigurationException e) {
            throw wrapInnerException(method, "Fail to search companies by filter, cause by " + e.getMessage(), e,
                    null);
        }
    }

    /**
     * Gets the clients of specified company. Empty list will be returned if none is found.
     *
     *
     * @param company
     *            the Company whose clients will be retrieved, can't be null, id should not be negative
     * @return a list of Client objects, empty list if none is found
     * @throws IllegalArgumentException
     *             if company is not valid (null, or id is negative)
     * @throws CompanyManagerException
     *             if any error occurred during this operation, e.g. error in persistence layer,
     */
    public List<Client> getClientsForCompany(Company company) throws CompanyManagerException {
        String method = "getClientsForCompany(Company company)";
        Helper.logEntranceInfo(getLog(), CLASSNAME, method, new String[] {"company"}, new String[] {Helper
                .formatCompany(company)});

        try {
            Helper.checkNull(company, "company");
            Helper.checkId(company.getId());

            // get clients for company
            List<Client> clients = companyDAO.getClientsForCompany(company);

            Helper.logExitInfo(getLog(), CLASSNAME, method, Helper.formatClientList(clients));
            return clients;
        } catch (IllegalArgumentException iae) {
            Helper.logException(getLog(), CLASSNAME, method, iae);
            throw iae;
        } catch (DAOConfigurationException e) {
            throw wrapInnerException(method, "Fail to get clients for company, cause by " + e.getMessage(), e,
                    company);
        } catch (DAOException e) {
            throw wrapInnerException(method, "Fail to get clients for company, cause by " + e.getMessage(), e,
                    company);
        }
    }

    /**
     * Gets the projects of specified company. Empty list will be returned if none is found.
     *
     *
     * @param company
     *            the Company whose clients will be retrieved, can't be null, id should not be negative
     * @return a list of Project objects, empty list if none is found
     * @throws IllegalArgumentException
     *             if parameter company is null or its id is negative
     * @throws CompanyManagerException
     *             if any error occurred during this operation, e.g. error in persistence layer
     */
    public List<Project> getProjectsForCompany(Company company) throws CompanyManagerException {
        String method = "getProjectsForCompany(Company company)";
        Helper.logEntranceInfo(getLog(), CLASSNAME, method, new String[] {"company"}, new String[] {Helper
                .formatCompany(company)});

        try {
            Helper.checkNull(company, "company");
            Helper.checkId(company.getId());

            // get projects for company
            List<Project> projects = companyDAO.getProjectsForCompany(company);

            Helper.logExitInfo(getLog(), CLASSNAME, method, Helper.formatProjectList(projects));
            return projects;
        } catch (IllegalArgumentException iae) {
            Helper.logException(getLog(), CLASSNAME, method, iae);
            throw iae;
        } catch (DAOConfigurationException e) {
            throw wrapInnerException(method, "Fail to get projects for company , cause by " + e.getMessage(), e,
                    company);
        } catch (DAOException e) {
            throw wrapInnerException(method, "Fail to get projects for company , cause by " + e.getMessage(), e,
                    company);
        }
    }
}
