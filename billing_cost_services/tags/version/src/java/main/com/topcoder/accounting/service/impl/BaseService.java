/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.service.impl;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.topcoder.accounting.service.BillingCostConfigurationException;
import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.util.log.Log;

/**
 * <p>
 * This is a base class for all services. It provides the common field for the Log used for all logging, and the
 * hibernate template for all local DB interactions.
 * </p>
 * <p>
 * Thread Safety: This class is mutable but effectively thread-safe.
 * </p>
 *
 * @author argolite, stevenfrog
 * @version 1.0
 */
public abstract class BaseService implements InitializingBean {
    /**
     * Represents the logger used to log activity and errors. it is managed with a getter and setter. It may have
     * any value, but is expected to be set to a non-null value by dependency injection. It is fully mutable, but
     * not expected to change after dependency injection.
     */
    private Log logger;

    /**
     * Represents the HibernateTemplate used for all DB interactions. it is managed with a getter and setter. It
     * may have any value, but is expected to be set to a non-null value by dependency injection. It is fully
     * mutable, but not expected to change after dependency injection.
     */
    private HibernateTemplate hibernateTemplate;

    /**
     * Empty constructor.
     */
    protected BaseService() {
        // Empty
    }

    /**
     * This method checks that all required injection fields are in fact provided.
     *
     * @throws BillingCostConfigurationException
     *             If there are required injection fields that are not injected
     */
    public void afterPropertiesSet() {
        ValidationUtility.checkNotNull(logger, "logger", BillingCostConfigurationException.class);
        ValidationUtility.checkNotNull(hibernateTemplate, "hibernateTemplate",
            BillingCostConfigurationException.class);
    }

    /**
     * <p>
     * Getter method for logger, simply return the namesake instance variable.
     * </p>
     *
     * @return the logger
     */
    public Log getLogger() {
        return logger;
    }

    /**
     * <p>
     * Setter method for logger, simply assign the value to the instance variable.
     * </p>
     *
     * @param logger
     *            the logger to set
     */
    public void setLogger(Log logger) {
        this.logger = logger;
    }

    /**
     * <p>
     * Getter method for hibernateTemplate, simply return the namesake instance variable.
     * </p>
     *
     * @return the hibernateTemplate
     */
    public HibernateTemplate getHibernateTemplate() {
        return hibernateTemplate;
    }

    /**
     * <p>
     * Setter method for hibernateTemplate, simply assign the value to the instance variable.
     * </p>
     *
     * @param hibernateTemplate
     *            the hibernateTemplate to set
     */
    public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
        this.hibernateTemplate = hibernateTemplate;
    }

}
