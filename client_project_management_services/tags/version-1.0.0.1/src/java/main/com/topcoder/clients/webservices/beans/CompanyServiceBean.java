/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices.beans;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.jws.WebMethod;
import javax.jws.WebService;

import com.topcoder.clients.manager.CompanyManager;
import com.topcoder.clients.model.Company;
import com.topcoder.clients.webservices.ClientProjectManagementServicesException;
import com.topcoder.clients.webservices.CompanyService;
import com.topcoder.clients.webservices.CompanyServiceException;
import com.topcoder.clients.webservices.CompanyServiceLocal;
import com.topcoder.clients.webservices.CompanyServiceRemote;
import com.topcoder.util.log.Log;

/**
 * <p>
 *  This class is a Stateless Session Bean endpoint realization of CompanyService web service interface.
 *  This class has a default no-arg constructor. It uses CompanyManager from Client Project Management
 *  component by delegating to namesake methods to perform the operations. This class implements
 *  the methods available for the CompanyService web service: create, update and delete company.
 * </p>
 *
 * <p>
 *  <strong>Logging:</strong>
 *  All the available web service operations performs logging using the Logging Wrapper component.
 * </p>
 *
 * <p>
 *  <strong>Authentication and Authorization:</strong>
 *  All the available web service operations are under the "User" and "Admin" security roles.
 * </p>
 *
 * <p>
 *  Below is example configuration in ejb-jar.xml for CompanyServiceBean:
 *  <pre>
 *  &lt;?xml version="1.0" encoding="UTF-8"?&gt;
 *  &lt;ejb-jar version="3.0" xmlns="http://java.sun.com/xml/ns/javaee"
 *      xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
 *      xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/ejb-jar_3_0.xsd"&gt;
 *      &lt;enterprise-beans&gt;
 *          &lt;session&gt;
 *              &lt;ejb-name&gt;CompanyServiceBean&lt;/ejb-name&gt;
 *              &lt;remote&gt;com.topcoder.clients.webservices.CompanyServiceRemote&lt;/remote&gt;
 *              &lt;local&gt;com.topcoder.clients.webservices.CompanyServiceLocal&lt;/local&gt;
 *              &lt;ejb-class&gt;com.topcoder.clients.webservices.beans.CompanyServiceBean&lt;/ejb-class&gt;
 *              &lt;session-type&gt;Stateless&lt;/session-type&gt;
 *              &lt;transaction-type&gt;Container&lt;/transaction-type&gt;
 *              &lt;env-entry&gt;
 *                  &lt;env-entry-name&gt;companyManagerFile&lt;/env-entry-name&gt;
 *                  &lt;env-entry-type&gt;java.lang.String&lt;/env-entry-type&gt;
 *                  &lt;env-entry-value&gt;
 *                      /Applications/Developer_Suite/Service/jboss-4.2.3.GA/server/default/
 *                      conf_tcs/client_project_management_services/bean.properties
 *                  &lt;/env-entry-value&gt;
 *              &lt;/env-entry&gt;
 *              &lt;env-entry&gt;
 *                  &lt;env-entry-name&gt;companyManagerNamespace&lt;/env-entry-name&gt;
 *                  &lt;env-entry-type&gt;java.lang.String&lt;/env-entry-type&gt;
 *                  &lt;env-entry-value&gt;companyManagerNamespace&lt;/env-entry-value&gt;
 *              &lt;/env-entry&gt;
 *              &lt;env-entry&gt;
 *                  &lt;env-entry-name&gt;logName&lt;/env-entry-name&gt;
 *                  &lt;env-entry-type&gt;java.lang.String&lt;/env-entry-type&gt;
 *                  &lt;env-entry-value&gt;System.out&lt;/env-entry-value&gt;
 *              &lt;/env-entry&gt;
 *          &lt;/session&gt;
 *      &lt;/enterprise-beans&gt;
 *  &lt;/ejb-jar&gt;
 *  </pre>
 *  <pre>
 *      This service bean can be used like below example:
 *
 *      // Assume, this bean is deployed in EAR package.
 *      Properties env = ....;
 *      InitialContext ctx = new InitialContext(env);
 *      CompanyService service = (CompanyService) ctx.lookup(EAR_NAME + "/" + CompanyServiceRemote.JNDI_NAME);
 *
 *      // Create Client
 *      service.createCompany(company);
 *
 *      // Update Client
 *      service.updateCompany(company);
 *
 *      // Delete Client
 *      service.deleteCompany(company);
 *  </pre>
 * </p>
 *
 * <p>
 *  <strong>Thread-safety:</strong>
 *  This class is technically mutable since the configuration properties
 *  and manager are set after construction, but the container will not initialize
 *  the properties more than once for the session beans and
 *  the EJB3 container ensure the thread safety in this case.
 * </p>
 *
 * @author  Mafy, CaDenza
 * @version 1.0
 */
@WebService(name = "CompanyService",
            serviceName = "CompanyService",
            targetNamespace = "com.topcoder.clients.webservices.CompanyService",
            endpointInterface = "com.topcoder.clients.webservices.CompanyService")
@Stateless(name = CompanyService.BEAN_NAME)
@Local(CompanyServiceLocal.class)
@Remote(CompanyServiceRemote.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@DeclareRoles({ Roles.USER, Roles.ADMIN })
@RolesAllowed({ Roles.USER, Roles.ADMIN })
public class CompanyServiceBean implements CompanyServiceLocal, CompanyServiceRemote {

    /**
     * The token of company manager.
     */
    private static final String COMPANY_MANAGER_TOKEN = "company_manager_token";

    /**
     *  Represents the company manager instance.
     *  Cannot be null after this bean is constructed.
     *  REQUIRED.
     */
    private CompanyManager companyManager;

    /**
     *  Represents the company manager file.
     *  Cannot be null or empty String after injected when this bean is instantiated.
     *  REQUIRED.
     */
    @Resource(name = "companyManagerFile")
    private String companyManagerFile;

    /**
     *  Represents the company manager namespace.
     *  Cannot be null or empty String after injected when this bean is instantiated.
     *  REQUIRED.
     */
    @Resource(name = "companyManagerNamespace")
    private String companyManagerNamespace;

    /**
     * Represents the SessionContext injected by
     * the EJB container automatically and it is used
     * to retrieve the Principal needed for user authentication.
     * Cannot be null after injected when this bean is instantiated.
     */
    @Resource
    private SessionContext sessionContext;

    /**
     *  Represents the log name used to initialize the log.
     *  Will not change after injection.
     *  Can be null or empty String.
     *  OPTIONAL.
     */
    @Resource(name = "logName")
    private String logName;

    /**
     * This field represents the 'log' instance of the CompanyServiceBean.
     * It will be used to perform the logging operations.
     * Contains an instance of Log interface.
     * It can be enabled (not null) or disabled (null).
     *
     * Can be any value.
     * OPTIONAL.
     */
    private Log log;

    /**
     * This field represents the 'verboseLogging'
     * property of the CompanyServiceBean.
     * The control flag defining whether the detailed
     * logging actions should be performed (true value),
     * or standard logging (only exceptions) should be supported (false value).
     *
     * There are no restrictions at this moment.
     * It can take any value.
     * OPTIONAL.
     */
    private boolean verboseLogging = false;

    /**
     * Default no-arg constructor.
     */
    public CompanyServiceBean() {
    }

    /**
     * <p>
     *  Initialize the companyManager and log and makes sure
     *  the initialization of this session bean is ok.
     *  This method is called after this bean is constructed by the EJB container.
     * </p>
     *
     * @throws CompanyServiceBeanConfigurationException
     *      if the required String fields are null or empty,
     *      if needed configurations are missing or invalid (invalid means null here),
     *      if Object Factory cannot initialize the needed dependencies.
     */
    @PostConstruct
    protected void initialize() {
        // Create logger.
        log = BeanUtils.createLogger(logName);
        try {
            // Create company manager instance.
            companyManager = BeanUtils.createManager(CompanyManager.class,
                    companyManagerFile, companyManagerNamespace, COMPANY_MANAGER_TOKEN);
        } catch (ServiceBeanConfigurationException e) {
            throw new CompanyServiceBeanConfigurationException("Fail to initialize 'manager'.", e.getCause());
        }
    }

    /**
     * Performs the creation of the given company in the persistence.
     *
     * @param  company
     *      the company that should be created.
     *      Should not be null.
     * @return the company created in the persistence.
     *
     * @throws IllegalArgumentException
     *      if the company is null.
     * @throws IllegalStateException
     *      if the companyManager is not set (or it is set to a null value).
     * @throws CompanyServiceException
     *      if any error occurs while performing this operation.
     */
    @WebMethod
    public Company createCompany(Company company) throws CompanyServiceException {
        String method = this.getClass().getName() + "#createCompany(Company company)";
        Company retCompany = null;
        Date enterTime = null;

        try {
            // Log entrance.
            enterTime = BeanUtils.logEnter(log, verboseLogging, method, new Object[]{company});

            // Perform create operation for Company.
            retCompany = BeanUtils.createOperation(BeanUtils.getCurrentUser(sessionContext),
                    company, null, null, null, companyManager);
        } catch (ClientProjectManagementServicesException e) {
            throw BeanUtils.logException(log, new CompanyServiceException("Fail create Company.", e.getCause()));
        } catch (IllegalArgumentException e) {
            throw BeanUtils.logException(log, e);
        } catch (IllegalStateException e) {
            throw BeanUtils.logException(log, e);
        } finally {
            // Log exit method.
            BeanUtils.logExit(log, verboseLogging, method, enterTime, retCompany);
        }

        return retCompany;
    }

    /**
     *  Performs the update of the given company in the persistence.
     *
     *  @param  company
     *          the company that should be updated.
     *          Should not be null.
     *  @return the company updated in the persistence.
     *
     *  @throws IllegalArgumentException
     *          if the company is null.
     *  @throws IllegalStateException
     *          if the companyManager is not set (or it is set to a null value).
     *  @throws CompanyServiceException
     *          if any error occurs while performing this operation.
     */
    @WebMethod
    public Company updateCompany(Company company) throws CompanyServiceException {
        String method = this.getClass().getName() + "#updateCompany(Company company)";
        Company retCompany = null;
        Date enterTime = null;

        try {
            // Log entrance.
            enterTime = BeanUtils.logEnter(log, verboseLogging, method, new Object[]{company});

            // Perform update operation for Company.
            retCompany = BeanUtils.updateOperation(BeanUtils.getCurrentUser(sessionContext),
                    company, null, null, companyManager);
        } catch (ClientProjectManagementServicesException e) {
            throw BeanUtils.logException(log, new CompanyServiceException("Fail create Company.", e.getCause()));
        } catch (IllegalArgumentException e) {
            throw BeanUtils.logException(log, e);
        } catch (IllegalStateException e) {
            throw BeanUtils.logException(log, e);
        } finally {
            // Log exit method.
            BeanUtils.logExit(log, verboseLogging, method, enterTime, retCompany);
        }

        return retCompany;
    }

    /**
     * Performs the deletion of the given company from the persistence.
     *
     * @param  company
     *      the company that should be deleted.
     *      Should not be null.
     * @return the company deleted from the persistence.
     *
     * @throws IllegalArgumentException
     *      if the company is null.
     * @throws IllegalStateException
     *      if the companyManager is not set (or it is set to a null value).
     * @throws CompanyServiceException
     *      if any error occurs while performing this operation.
     */
    @WebMethod
    public Company deleteCompany(Company company) throws CompanyServiceException {
        String method = this.getClass().getName() + "#deleteCompany(Company company)";
        Company retCompany = null;
        Date enterTime = null;

        try {
            // Log entrance.
            enterTime = BeanUtils.logEnter(log, verboseLogging, method, new Object[]{company});

            // Perform delete operation for Company.
            retCompany = BeanUtils.deleteOperation(BeanUtils.getCurrentUser(sessionContext),
                    company, null, null, companyManager);
        } catch (ClientProjectManagementServicesException e) {
            throw BeanUtils.logException(log, new CompanyServiceException("Fail create Company.", e.getCause()));
        } catch (IllegalArgumentException e) {
            throw BeanUtils.logException(log, e);
        } catch (IllegalStateException e) {
            throw BeanUtils.logException(log, e);
        } finally {
            // Log exit method.
            BeanUtils.logExit(log, verboseLogging, method, enterTime, retCompany);
        }

        return retCompany;
    }

    /**
     * Getter for 'verboseLogging' property.
     *
     * @return the value of the 'verboseLogging' property.
     *      It can be any value.
     */
    public boolean isVerboseLogging() {
        return verboseLogging;
    }

    /**
     * Setter for 'verboseLogging' property.
     *
     * @param verboseLogging
     *      the new value of the control flag defining whether
     *      the detailed logging actions should be performed (true value),
     *      or standard logging (only exceptions) should be supported (false value).
     */
    public void setVerboseLogging(boolean verboseLogging) {
        this.verboseLogging = verboseLogging;
    }

    /**
     * Getter for name of 'log' property.
     *
     * @return the value of the 'log' property.
     *      It can be any value.
     */
    public Log getLog() {
        return log;
    }

    /**
     * Setter for 'log' property.
     *
     * @param log
     *      the new log to set for log property.
     *      It can be any value.
     */
    public void setLog(Log log) {
        this.log = log;
    }
}