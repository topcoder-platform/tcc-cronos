/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable.late.impl;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.management.deliverable.late.LateDeliverable;
import com.topcoder.management.deliverable.late.LateDeliverableManagementConfigurationException;

/**
 * <p>
 * This interface represents a late deliverable persistence. Currently it defines just a single method for updating
 * late deliverable in persistence. Other methods can be added in future.
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> Implementations of this interface are required to be thread safe when configure()
 * method is called just once right after instantiation and entities passed to them are used by the caller in thread
 * safe manner.
 * </p>
 *
 * @author saarixx, sparemax
 * @version 1.0
 */
public interface LateDeliverablePersistence {
    /**
     * <p>
     * Configures this instance with use of the given configuration object.
     * </p>
     *
     * @param configuration
     *            the configuration object.
     *
     * @throws IllegalArgumentException
     *             if configuration is null (is not thrown by implementations that don't use any configuration
     *             parameters).
     * @throws LateDeliverableManagementConfigurationException
     *             if some error occurred when initializing an instance using the given configuration (is not thrown
     *             by implementations that don't use any configuration parameters).
     */
    public void configure(ConfigurationObject configuration);

    /**
     * <p>
     * Updates the given late deliverable in persistence.
     * </p>
     *
     * @param lateDeliverable
     *            the late deliverable with updated data.
     *
     * @throws IllegalArgumentException
     *             if lateDeliverable is null, lateDeliverable.getId() &lt;= 0, lateDeliverable.getDeadline() is null,
     *             lateDeliverable.getCreateDate() is null.
     * @throws IllegalStateException
     *             if persistence was not configured properly.
     * @throws LateDeliverableNotFoundException
     *             if late deliverable with ID equal to lateDeliverable.getId() doesn't exist in persistence.
     * @throws LateDeliverablePersistenceException
     *             if some other error occurred when accessing the persistence.
     */
    public void update(LateDeliverable lateDeliverable) throws LateDeliverablePersistenceException;
}
