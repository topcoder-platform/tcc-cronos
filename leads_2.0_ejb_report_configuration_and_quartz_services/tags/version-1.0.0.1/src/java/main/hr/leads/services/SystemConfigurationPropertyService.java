/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package hr.leads.services;

import hr.leads.services.model.PipelineCycleStatus;
import hr.leads.services.model.jpa.SystemConfigurationProperty;

import java.util.List;

/**
 * <p>
 * This service provides methods to manage SystemConfigurationProperty entity.
 * </p>
 * <p>
 * It also provides the getPipelineCycleStatus() and updatePipelineCycleStatus()
 * methods as convenience methods for managing configuration value for pipeline
 * cycle status.
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
 *
 * </p>
 * <p>
 * <b> Thread Safety: </b> Implementations of this interface must be thread safe
 * when their configurable properties do not change after initialization.
 * </p>
 *
 * @author semi_sleep, TCSDEVELOPER
 * @version 1.0
 */
public interface SystemConfigurationPropertyService {

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
    String getSystemConfigurationPropertyValue(String name)
        throws LeadsServiceException;

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
    void setSystemConfigurationPropertyValue(String name, String value)
        throws LeadsServiceException;

    /**
     * <p>
     * Gets all the SystemConfigurationProperty entities.
     * </p>
     *
     * @return the list containing all the SystemConfigurationProperty entities.
     * @throws LeadsServiceException
     *             if any error occurs.
     */
    List<SystemConfigurationProperty> getAllSystemConfigurationPropertyValues()
        throws LeadsServiceException;

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
    void updatePipelineCycleStatus(PipelineCycleStatus status)
        throws LeadsServiceException;

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
    PipelineCycleStatus getPipelineCycleStatus() throws LeadsServiceException;
}
