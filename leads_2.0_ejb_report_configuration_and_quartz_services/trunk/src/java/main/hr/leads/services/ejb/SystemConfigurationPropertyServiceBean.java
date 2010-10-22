/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package hr.leads.services.ejb;

import hr.leads.services.LeadsServiceConfigurationException;
import hr.leads.services.LeadsServiceException;
import hr.leads.services.model.PipelineCycleStatus;
import hr.leads.services.model.jpa.SystemConfigurationProperty;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.apache.openjpa.persistence.InvalidStateException;

/**
 * <p>
 * This class is an EJB that implements SystemConfigurationPropertyService
 * business interface to manage SystemConfigurationProperty entity.
 * </p>
 * <p>
 * It extends BaseReportConfigurationServiceBean class.
 * </p>
 * <p>
 * It accesses entities in persistence using JPA EntityManager.
 * </p>
 *
 * <p>
 * Usage: examples shows below:
 * <pre>
 * Gets the property value via name:
 * String value = service.getSystemConfigurationPropertyValue(&quot;property1&quot;);
 *
 * Sets the property value:
 * service.setSystemConfigurationPropertyValue(&quot;property1&quot;, &quot;value1&quot;);
 *
 * Gets the pipeline status:
 * PipelineCycleStatus status = getPipelineCycleStatus();
 *
 * Updates the pipeline status:
 * updatePipelineCycleStatus(PilelineStatus.OPEN);
 * </pre>
 * </p>
 *
 * <p>
 * <b> Thread Safety: </b> This class is mutable and not thread safe. But it is
 * always used in thread safe manner in EJB container because its state doesn't
 * change after initialization. This bean assumes that transactions are managed
 * by the container.
 * </p>
 *
 * @author semi_sleep, TCSDEVELOPER
 * @version 1.0
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class SystemConfigurationPropertyServiceBean extends
        BaseReportConfigurationServiceBean implements
        SystemConfigurationPropertyServiceLocal,
        SystemConfigurationPropertyServiceRemote {
    /**
     * <p>
     * Represents the query string to select the property.
     * </p>
     */
    private static final String SELECT_VALUE_QUERY_STRING =
        "select s from SystemConfigurationProperty s where s.name=:name";

    /**
     * <p>
     * Represents the query string to select all the properties.
     * </p>
     */
    private static final String SELECT_ALL_QUERY_RESULT = "select s from SystemConfigurationProperty s";

    /**
     * <p>
     * Represents the EntityManager instance to access entities in persistence.
     * </p>
     * <p>
     * Cannot be null after initialization.
     * </p>
     * <p>
     * Initialized by EJB container injection.
     * </p>
     * <p>
     * It's used in the business methods of this service bean to access database.
     * </p>
    */
    @PersistenceContext(name = "persistenceUnit", type = PersistenceContextType.TRANSACTION)
    private EntityManager entityManager;

    /**
     * <p>
     * The system configuration property name for pipeline cycle status.
     * </p>
     * <p>
     * Can not be null / empty after initialization.
     * </p>
     * <p>
     * Initialized by EJB container injection.
     * </p>
     * <p>
     * It's used by updatePipelineCycleStatus() and getPipelineCycleStatus() method.
     * </p>
    */
    @Resource
    private String pipelineCycleStatusPropertyName;

    /**
     * <p>
     * Creates an instance of SystemConfigurationPropertyServiceBean.
     * </p>
    */
    public SystemConfigurationPropertyServiceBean() {
        // do nothing
    }

    /**
     * <p>
     * Gets the value for a system configuration property by given name.
     * </p>
     *
     * @param name
     *            the name for the system configuration property.
     *
     * @return the value for the system configuration property matching given
     *         name, or null if such system configuration property does not
     *         exist.
     *
     * @throws IllegalArgumentException
     *             if given name is null / empty.
     * @throws LeadsServiceException
     *             if any error occurs.
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public String getSystemConfigurationPropertyValue(String name)
        throws LeadsServiceException {

        // prepare for logging
        String methodName = getClass().getName()
                + ".getSystemConfigurationPropertyValue";
        Logger logger = getLogger();

        // log the entrance and request parameters
        ReportHelper.logEntrance(logger, methodName);
        ReportHelper.logParameters(logger, new Object[] {name},
                new String[] {"name"});

        SystemConfigurationProperty property = getSystemConfigurationProperty(name);
        if (property == null) {
            return null;
        }

        // log the value and exit
        String value = property.getValue();
        ReportHelper.logOutput(logger, value);
        ReportHelper.logExit(logger, methodName);

        return value;
    }

    /**
     * <p>
     * Retrieves the SystemConfigurationProperty of a specific name.
     * </p>
     * <p>
     * This is a helper method used in other methods.
     * </p>
     *
     * @param name
     *            the name of the property.
     * @return the SystemConfigurationProperty instance or null if it is not
     *         exist.
     * @throws IllegalArgumentException
     *             if the name is null or empty.
     *
     * @throws LeadsServiceException
     *             if any other occurs.
     */
    private SystemConfigurationProperty getSystemConfigurationProperty(
            String name) throws LeadsServiceException {
        // prepare for logging
        String methodName = getClass().getName() + ".getSystemConfigurationProperty";
        Logger logger = getLogger();

        ReportHelper.checkNullOrEmpty(logger, methodName, name, "name");
        try {
            // 1. Create query to retrieve SystemConfigurationProperty entity :
            Query query = entityManager.createQuery(SELECT_VALUE_QUERY_STRING);
            // 2. Set query parameter :
            query.setParameter("name", name);

            return (SystemConfigurationProperty) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (PersistenceException e) {
            // log the error and re-throw it
            ReportHelper.logError(logger, methodName,
                    "failed to do the query. :" + SELECT_VALUE_QUERY_STRING);
            throw new LeadsServiceException(
                    "failed to do the query. :" + SELECT_VALUE_QUERY_STRING, e);
        } catch (InvalidStateException e) {
            // log the error and re-throw it
            ReportHelper.logError(logger, methodName,
                    "failed to do the query. :" + SELECT_VALUE_QUERY_STRING);
            throw new LeadsServiceException(
                    "failed to do the query. :" + SELECT_VALUE_QUERY_STRING, e);
        }

    }

    /**
     * <p>
     * Sets given value to a system configuration property by given name.
     * </p>
     *
     * @param name
     *            the name for the system configuration property.
     * @param value
     *            the value to be set.
     * @throws IllegalArgumentException
     *             if name or value is null/empty.
     * @throws LeadsServiceException
     *             if any error occurs.
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void setSystemConfigurationPropertyValue(String name, String value)
        throws LeadsServiceException {

        // prepare for logging
        String methodName = getClass().getName()
                + ".setSystemConfigurationPropertyValue";
        Logger logger = getLogger();

        // log the entrance and the parameters
        ReportHelper.logEntrance(logger, methodName);
        ReportHelper.logParameters(logger, new Object[] {name, value},
                new String[] {"name", "value"});

        ReportHelper.checkNullOrEmpty(logger, methodName, name, "name");
        ReportHelper.checkNullOrEmpty(logger, methodName, value, "value");

        SystemConfigurationProperty property = getSystemConfigurationProperty(name);
        if (property == null) {
            // creates a SystemConfigurationProperty entity and set its name.
            property = new SystemConfigurationProperty();
            property.setName(name);
            property.setValue(value);
            try {
                // save the SystemConfigurationProperty entity
                entityManager.persist(property);
            } catch (PersistenceException e) {
                ReportHelper.logError(logger, methodName,
                        "Failed to persist the property. " + e.getMessage());
                throw new LeadsServiceException("Failed to persist the property", e);
            }
        } else {

            // set the value to SystemConfigurationProperty entity
            property.setValue(value);

            try {
                // save the SystemConfigurationProperty entity
                entityManager.merge(property);
            } catch (PersistenceException e) {
                ReportHelper.logError(logger, methodName,
                        "Failed to update the property. " + e.getMessage());
                throw new LeadsServiceException("Failed to update the property", e);
            }
        }

        ReportHelper.logExit(logger, methodName);
    }

    /**
     * <p>
     * Gets all the SystemConfigurationProperty entities.
     * </p>
     *
     * @return the list containing all the SystemConfigurationProperty entities.
     * @throws LeadsServiceException
     *             if any error occurs.
     */
    @Override
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<SystemConfigurationProperty> getAllSystemConfigurationPropertyValues()
        throws LeadsServiceException {

        // prepare for logging
        String methodName = getClass().getName() + ".getAllSystemConfigurationPropertyValues";
        Logger logger = getLogger();

        // log entrance
        ReportHelper.logEntrance(logger, methodName);

        List<SystemConfigurationProperty> result = null;
        try {
            // create query to retrieve all SystemConfigurationProperty entities
            Query query = entityManager.createQuery(SELECT_ALL_QUERY_RESULT);

            // retrieve all SystemConfigurationProperty entities
            result = query.getResultList();

        } catch (InvalidStateException e) {
            ReportHelper.logError(
                    logger, methodName, "Failed to perform query: " + SELECT_ALL_QUERY_RESULT);
            throw new LeadsServiceException(
                    "Failed to perform query: " + SELECT_ALL_QUERY_RESULT, e);
        } catch (PersistenceException e) {
            ReportHelper.logError(
                    logger, methodName, "Failed to perform query: " + SELECT_ALL_QUERY_RESULT);
            throw new LeadsServiceException(
                    "Failed to perform query: " + SELECT_ALL_QUERY_RESULT, e);
        }

        // log output and exit
        ReportHelper.logOutput(logger, result);
        ReportHelper.logExit(logger, methodName);

        return result;
    }

    /**
     * <p>
     * Updates the pipeline cycle status to given status value.
     * </p>
     *
     * @param status
     *            the new status to be updated to database.
     * @throws IllegalArgumentException
     *             if status is null.
     * @throws LeadsServiceException
     *             if any error occurs.
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updatePipelineCycleStatus(PipelineCycleStatus status)
        throws LeadsServiceException {

        // prepare for logging
        String methodName = getClass().getName() + ".updatePipelineCycleStatus";
        Logger logger = getLogger();

        // log entrance and the request parameters
        ReportHelper.logEntrance(logger, methodName);
        ReportHelper.logParameters(logger, new Object[] {status},
                new String[] {"status"});

        ReportHelper.checkNull(logger, methodName, status, "status");

        // delegates to the set method
        setSystemConfigurationPropertyValue(
                pipelineCycleStatusPropertyName, status.name());

        // log exit
        ReportHelper.logExit(logger, methodName);
    }

    /**
     * <p>
     * Gets the pipeline cycle status value.
     * </p>
     *
     * @return the pipeline cycle status value, or null if the status is not set
     *         before.
     * @throws LeadsServiceException
     *             if any error occurs.
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public PipelineCycleStatus getPipelineCycleStatus() throws LeadsServiceException {

        // prepare for logging
        String methodName = getClass().getName() + ".getPipelineCycleStatus";
        Logger logger = getLogger();

        ReportHelper.logEntrance(logger, methodName);
        String value = getSystemConfigurationPropertyValue(pipelineCycleStatusPropertyName);
        if (value == null) {
            return null;
        }

        PipelineCycleStatus status;
        try {
            status = Enum.valueOf(PipelineCycleStatus.class, value);
        } catch (IllegalArgumentException e) {
            ReportHelper.logError(logger, methodName,
                    "Invalid status of value: " + value);
            throw new LeadsServiceException("Invalid status of value: " + value);
        }

        // log the value and exit
        ReportHelper.logOutput(logger, status);
        ReportHelper.logExit(logger, methodName);

        return status;

    }

    /**
     * <p>
     * Checks to ensure that all dependencies are properly injected.
     * </p>
     * <p>
     * This method overrides corresponding method in base class to add more
     * validations.
     * </p>
     *
     * @throws LeadsServiceConfigurationException
     *             if any injected dependency is invalid, or if any error occurs
     *             when initializing this bean.
     */
    @Override
    @PostConstruct
    protected void afterBeanInitialized() {
        super.afterBeanInitialized();

        // prepare for logging
        String methodName = getClass().getName() + ".afterBeanInitialized";
        Logger logger = getLogger();

        // check if the entity manager successfully injected
        if (entityManager == null) {
            ReportHelper.logError(logger, methodName,
                    "entity manager is not successfully injected.");
            throw new LeadsServiceConfigurationException(
                    "entity manager is not successfully injected.");
        }

        // check if the pipelineCycleStatusPropertyName successfully injected
        if (pipelineCycleStatusPropertyName == null
                || pipelineCycleStatusPropertyName.trim().length() == 0) {
            ReportHelper.logError(logger, methodName,
                            "pipelineCycleStatusPropertyName is not successfully injected.");
            throw new LeadsServiceConfigurationException(
                    "pipelineCycleStatusPropertyName is not successfully injected.");
        }

        ReportHelper.logExit(logger, methodName);
    }
}
